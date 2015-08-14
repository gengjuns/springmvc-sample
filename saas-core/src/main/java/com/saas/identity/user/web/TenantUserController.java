package com.saas.identity.user.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.saas.identity.role.entity.QRole;
import com.saas.identity.role.entity.Role;
import com.saas.identity.role.repository.RoleRepository;
import com.saas.identity.user.entitiy.QUser;
import com.saas.identity.user.entitiy.User;
import com.saas.identity.user.entitiy.UserValidator;
import com.saas.identity.user.repository.UserRepository;
import com.saas.otp.entity.Otp;
import com.saas.otp.repository.OtpRepository;
import com.saas.reporttemplate.entity.ReportTemplate;
import com.saas.reporttemplate.service.ReportTemplateService;
import com.saas.tenant.entity.Tenant;
import com.saas.tenant.repository.TenantRepository;

/**
 * 
 * @author tomchen
 * 
 */

@Controller
@RequestMapping(value = "/tenant/user")
public class TenantUserController extends
		ControllerSupport<User, String, UserRepository> {

	@Inject
	protected TenantRepository tenantRepository;

	@Inject
	protected UserRepository userRepository;

	@Inject
	protected RoleRepository roleRepository;

	@Inject
	protected PasswordEncoder passwordEncoder;

	@Inject
	protected OtpRepository otpRepository;

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

	private Tenant getTenantById(String id) {
		return tenantRepository.findById(id);
	}

	public List<Role> getRolesByTenantId(String tenantId) {

		// 1 get tenant by tenantId
		Tenant tenant = tenantRepository.findById(tenantId);

		// 2 get admin/root tenant by parentTenantId = null
		Tenant rootTenant = tenantRepository.findByParentTenantId(null);

		// 3 check tenant level if tenant is reseller tenant,return reseller
		// roles ; if tenant if customer tenant ,return tenant role
		if (tenant.getParentTenantId().equals(rootTenant.getId())) {
			return (List<Role>) roleRepository.findAll(
					QRole.role.category.eq("RESELLER"), true,
					QRole.role.name.asc());
		} else {
			return (List<Role>) roleRepository.findAll(
					QRole.role.category.eq("TENANT"), true,
					QRole.role.name.asc());
		}

	}

	@RequestMapping("/list_{id}")
	public String list(@PathVariable("id") String tenantId,
			EntitySearchRequest<User> request, Model model) {
		model.addAttribute("tenant", getTenantById(tenantId));
		return super.list(request, model);
	}

	@RequestMapping(value = "/create_{id}", method = RequestMethod.GET)
	public String createForm(@PathVariable("id") String tenantId, Model model) {
		User entity = new User();
		entity.setTenantId(tenantId);
		model.addAttribute("user", entity);
		initCreate(model);
		populateEditForm(entity, model);
		return getViewName("/create");
	}

	@RequestMapping(value = "/update/{id}/{userId}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String tenantId, @PathVariable("userId") String userId,Model model) {
		User entity = getEntityRepository().findOne(userId);
		model.addAttribute(getModelAttributeName(), entity);
		initUpdate(model);
		populateEditForm(entity, model);
		return getViewName("/update");
	}

	
	@RequestMapping(value = "/update/{id}/{userId}", method = RequestMethod.POST)
	public String update(@Valid User entity, @PathVariable("id") String tenantId, @PathVariable("userId") String userId,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		
		validate(entity, bindingResult, model);
		model.addAttribute("success", !bindingResult.hasErrors());
		if (bindingResult.hasErrors()) {
			handleUpdateError(entity, bindingResult, model);
			populateEditForm(entity, model);
			return getViewName("/update");
		} else {
			preUpdate(entity, bindingResult, model);
			entity.setId(userId);
			entity = getEntityRepository().saveAndFlush(entity);
			postUpdate(entity, bindingResult, model, redirectAttributes);
		}
		return getViewName("/update/"+tenantId+"/"+userId, true);
	}

	@Override
	protected void preRead(EntitySearchRequest<User> request) {
		String tenantId = null;
		try {
			tenantId = request.getParamMap().get("tenantId");
		} catch (Exception e) {
			tenantId = null;
		}

		BooleanBuilder userBuilder = new BooleanBuilder();
		userBuilder.and(QUser.user.tenantId.eq(tenantId));
		request.getBuilder().and(userBuilder.getValue());
	}

	@Override
	protected void populateEditForm(User entity, Model model) {
		super.populateEditForm(entity, model);
		model.addAttribute("tenant", getTenantById(entity.getTenantId()));
		model.addAttribute("roles", getRolesByTenantId(entity.getTenantId()));
	}

	@Override
	protected void preCreate(User entity, BindingResult bindingResult,
			Model model) {
		super.preCreate(entity, bindingResult, model);
		entity.setAccountStatus(Constants.ACCOUNT_STATUS_NEW);
		Tenant tenant = getTenantById(entity.getTenantId());
		if (tenant == null) {
			entity.setTenantId(null);
		} else {
			entity.setTenantId(tenant.getId());
		}
	}

	@Override
	protected void preUpdate(User entity, BindingResult bindingResult,
			Model model) {
		super.preUpdate(entity, bindingResult, model);
		if (StringUtils.hasText(entity.getPasswordConfirm())) {
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		} else {
			entity.setPassword(entity.getCurrentPassword());
		}
	}

	@Override
	protected void postCreate(User entity, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
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
		reportTemplateService.sendEmail(reportTemplate, mapParam, null, null,
				null, sendTOList, null, null);
		super.postCreate(entity, bindingResult, model, redirectAttributes);
	}

	@Override
	protected void validate(User entity, BindingResult bindingResult,
			Model model) {
		super.validate(entity, bindingResult, model);
		UserValidator validator = new UserValidator(dbUsernameValidationMode,
				dbUsernameValidationPattern, dbUsernameValidationMessage,
				userRepository);
		validator.validate(entity, bindingResult);
	}

	@Override
	protected String pathAfterCreate(User entity, BindingResult bindingResult,
			Model model) {
		return getViewName("/list_" + entity.getTenantId(), true);
	}

	@RequestMapping(value = "/delete/{id}/{userId}")
	public String delete(@PathVariable("id") String tenantId,
			@PathVariable("userId") String id) {
		getEntityRepository().delete(id);
		return getViewName("/list_" + tenantId, true);
	}

	@RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
	@ResponseBody
	public String checkUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user == null ? "true" : "false";
	}

}
