package com.saas.identity.user.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysema.query.BooleanBuilder;
import com.saas.Constants;
import com.saas.core.util.DateFormatUtils;
import com.saas.core.util.StringUtils;
import com.saas.core.util.UUIDGenerator;
import com.saas.core.web.ControllerSupport;
import com.saas.core.web.search.EntitySearchRequest;
import com.saas.identity.group.entity.Group;
import com.saas.identity.group.entity.QGroup;
import com.saas.identity.group.repository.GroupRepository;
import com.saas.identity.role.entity.QRole;
import com.saas.identity.role.entity.Role;
import com.saas.identity.role.repository.RoleRepository;
import com.saas.identity.user.entitiy.QUser;
import com.saas.identity.user.entitiy.User;
import com.saas.identity.user.entitiy.UserLoginType;
import com.saas.identity.user.entitiy.UserValidator;
import com.saas.identity.user.repository.UserRepository;
import com.saas.otp.entity.Otp;
import com.saas.otp.repository.OtpRepository;
import com.saas.reporttemplate.entity.ReportTemplate;
import com.saas.reporttemplate.service.ReportTemplateService;
import com.saas.tenant.entity.QTenant;
import com.saas.tenant.entity.Tenant;
import com.saas.tenant.repository.TenantRepository;

@Controller
@RequestMapping("/user")
public class UserController extends ControllerSupport<User, String, UserRepository> {

    @Inject
    protected RoleRepository roleRepository;

    @Inject
    protected GroupRepository groupRepository;

    @Inject
    protected UserRepository userRepository;

    @Inject
    protected PasswordEncoder passwordEncoder;

    @Inject
    protected TenantRepository tenantRepository;

    @Inject
    protected OtpRepository otpRepository ;
    
    @Inject
    protected ReportTemplateService reportTemplateService;

    @Value("${auth.db.username.validation.mode}")
    protected String dbUsernameValidationMode;

    @Value("${auth.db.username.validation.pattern}")
    protected String dbUsernameValidationPattern;

    @Value("${auth.db.username.validation.message}")
    protected String dbUsernameValidationMessage;
    
    @Value("${saas.context}")
    protected String contextPage;
    
    protected List<UserLoginType> logintypes;

    public List<Role> getRoles() {
        return (List<Role>) roleRepository.findAll(QRole.role.category.eq("TENANT"), true, QRole.role.name.asc());
    }

    public Iterable<Group> getGroups() {
        return groupRepository.findAll(null, true, QGroup.group.name.asc());
    }

    public List<Tenant> getTenants() {
        return (List<Tenant>) tenantRepository.findAll(null, true, QTenant.tenant.name.asc());
    }

    public List<UserLoginType> getLoginTypes() {
        if (logintypes == null) {
            logintypes = new ArrayList<UserLoginType>();
            logintypes.add(UserLoginType.Database);
            logintypes.add(UserLoginType.Ldap);
            logintypes.add(UserLoginType.OpenID);
        }

        return logintypes;
    }

    @Override
    protected void populateEditForm(User entity, Model model) {
        super.populateEditForm(entity, model);
        model.addAttribute("roles", getRoles());
        model.addAttribute("groups", getGroups());
        model.addAttribute("logintypes", getLoginTypes());
        model.addAttribute("tenants", getTenants());
    }

    @Override
    protected void preCreate(User entity, BindingResult bindingResult, Model model) {
        super.preCreate(entity, bindingResult, model);
        entity.setAccountStatus(Constants.ACCOUNT_STATUS_NEW);
        Tenant tenant = (Tenant) getSession().getAttribute(Constants.TENANT);
        if(tenant == null){
        	entity.setTenantId(null);
        }else{
        	entity.setTenantId(tenant.getId());
        }

    }

    @Override
    protected void validate(User entity, BindingResult bindingResult, Model model) {
        super.validate(entity, bindingResult, model);
        UserValidator validator = new UserValidator(dbUsernameValidationMode, dbUsernameValidationPattern, dbUsernameValidationMessage, userRepository);
        validator.validate(entity, bindingResult);
    }

    @Override
    protected void preUpdate(User entity, BindingResult bindingResult, Model model) {
        super.preUpdate(entity, bindingResult, model);
        if (StringUtils.hasText(entity.getPasswordConfirm())) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        } else {
            entity.setPassword(entity.getCurrentPassword());
        }
    }

    @Override
    protected void postCreate(User entity, BindingResult bindingResult, Model model,RedirectAttributes redirectAttributes ) {
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId(Constants.TEMPLATE_ID_ACTIVE_ACCOUNT);
		
		Otp otp = new Otp();
		otp.setLoginId(entity.getUsername());
		otp.setApplication(Constants.OTP_TYPE_RESET_PASSWORD);
		otp.setOtp(UUIDGenerator.generateUUID());
		otp.setDeleteIndicator(Constants.OTP_TYPE_DELETE_INDICATOR_N);
		otpRepository.saveAndFlush(otp);
		
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("firstName", entity.getFirstName());
		mapParam.put("lastName", entity.getLastName());
		mapParam.put("loginId", entity.getUsername());
		mapParam.put("expiryDt", DateFormatUtils.format(new Date(), "yy-MM-dd"));
		mapParam.put("url", contextPage + "/activeAccount/" + otp.getOtp());
		
		List<String> sendTOList = new ArrayList<String>();
        sendTOList.add(entity.getEmail());
        reportTemplateService.sendEmail(reportTemplate, mapParam, null, null, null, sendTOList, null, null);
        super.postCreate(entity, bindingResult, model, redirectAttributes);
    }

    protected void preRead(EntitySearchRequest<User> request) {
    	
    	HttpSession session = getRequest().getSession();
    	Tenant tenant = (Tenant) session.getAttribute(Constants.TENANT);
    	
        BooleanBuilder userBuilder = new BooleanBuilder();
        userBuilder.and(QUser.user.tenantId.eq(tenant.getId()));
        request.getBuilder().and(userBuilder.getValue());
    }
    
    
    @RequestMapping(value = "/checkUsername",method = RequestMethod.POST)
    @ResponseBody
    public String checkUsername(String username){
    	User user = userRepository.findByUsername(username);
    	return user == null ? "true" : "false";
    }
}
