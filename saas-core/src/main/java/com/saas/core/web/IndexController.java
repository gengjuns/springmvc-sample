package com.saas.core.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saas.Constants;
import com.saas.code.entity.CodeValue;
import com.saas.code.repository.CodeTypeRepository;
import com.saas.code.repository.CodeValueRepository;
import com.saas.core.util.DateFormatUtils;
import com.saas.core.util.StringUtils;
import com.saas.core.util.UUIDGenerator;
import com.saas.core.validation.validator.ActiveAccountValidator;
import com.saas.identity.auth.service.CustomInvocationSecurityMetadataSourceService;
import com.saas.identity.user.UserContextHolder;
import com.saas.identity.user.entitiy.User;
import com.saas.identity.user.entitiy.UserLoginType;
import com.saas.identity.user.repository.UserRepository;
import com.saas.otp.entity.Otp;
import com.saas.otp.repository.OtpRepository;
import com.saas.reporttemplate.entity.ReportTemplate;
import com.saas.reporttemplate.service.ReportTemplateService;


/**
 * @author 
 * @since 30/01/2013 2:36 PM
 */
@Controller
public class IndexController {

    @Value("${ui.theme.cookie.name:SaaSApp_THEME_COOKIE}")
    protected String themeCookieName;

    @Value("${ui.theme.cookie.duration:3600}")
    protected int themeCookieDuration;

    @Value("${ui.secure.cookie:false}")
    protected boolean useSecureCookie;
    
    @Value("${saas.context}")
    protected String contextPage;

    @PersistenceContext
    protected EntityManager entityManager;
    
    @Inject
    protected UserRepository userRepository;

    @Inject
    protected PasswordEncoder passwordEncoder;
    
    @Inject
    protected OtpRepository otpRepository;
    
    @Inject
    protected CodeTypeRepository codeTypeRepository;
    
    @Inject
    protected CodeValueRepository codeValueRepository;
    
    @Inject
    protected ReportTemplateService reportTemplateService;
    @Inject
    protected CustomInvocationSecurityMetadataSourceService customSecurityMetadataSource;
    

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "commons/login";
    }


    @RequestMapping(value = "/loginerror", method = RequestMethod.GET)
    public String loginerror(Model model) {
        model.addAttribute("error", true);
        return login();
    }


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "commons/home";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "commons/logout";
    }


    @RequestMapping(value = "/theme/{theme}")
    public String switchTheme(@PathVariable("theme") String theme, HttpServletRequest request, HttpServletResponse response) {
        Cookie themeCookie = null;
        for (Cookie cookie : request.getCookies()) {
            if (themeCookieName.equalsIgnoreCase(cookie.getName())) {
                themeCookie = cookie;
                break;
            }
        }
        if (themeCookie == null) {
            themeCookie = new Cookie(themeCookieName, theme);
        } else {
            themeCookie.setValue(theme);
        }
        themeCookie.setSecure(useSecureCookie || request.isSecure());
        themeCookie.setMaxAge(themeCookieDuration);
        themeCookie.setPath(request.getContextPath() + "/");

        response.addCookie(themeCookie);

        return "redirect:/home";
    }


    @RequestMapping(value = "/ping")
    @ResponseBody
    public String ping(HttpServletRequest request) {
        return Boolean.toString(UserContextHolder.getLoginUser() != null);
    }


    @RequestMapping(value = "/statistics")
    public String statistics(HttpServletRequest request) {
        Statistics statistics = ((Session) entityManager.getDelegate()).getSessionFactory().getStatistics();
        request.setAttribute("statistics", statistics);
        return "commons/statistics";
    }
    @RequestMapping(value = "/statistics/flush/aa")
    public String flushaa(HttpServletRequest request) {
        customSecurityMetadataSource.loadResourceDefine();
        Statistics statistics = ((Session) entityManager.getDelegate()).getSessionFactory().getStatistics();
        request.setAttribute("statistics", statistics);
        return "commons/statistics";
    }


    @RequestMapping(value = "/init")
    public String init() {
        return "commons/init";
    }

    
    @RequestMapping(value = "/forgetpassword", method = RequestMethod.GET)
    public String forgetpassword(Model model) {
    	User user = new User();
    	model.addAttribute("user",user);
        return "commons/forgetpassword";
    }
    
    @RequestMapping(value = "/forgetpassword/retrievequestions", method = RequestMethod.POST)
    public String retrieveRecallQuestion(User user,BindingResult result,Model model){
    	if(StringUtils.isEmpty(user.getUsername())){
    		result.rejectValue("username", "msg_forgetpw_username_empty");
    		return "commons/forgetpassword";
    	}
    	
    	user = userRepository.findByUsernameAndLoginType(user.getUsername(), UserLoginType.Database);
    	if(null == user ){
    		result.rejectValue("username", "msg_forgetpw_username_not_exist");
    		return "commons/forgetpassword";
    	}
    	if(user.getAccountStatus().equalsIgnoreCase(Constants.ACCOUNT_STATUS_LOCKED)){
    		result.rejectValue("username", "msg_forgetpw_user_locked");
    		return "commons/forgetpassword";
    	}
    	if(StringUtils.isEmpty(user.getQuestion1()) || StringUtils.isEmpty(user.getQuestion2())){
    		result.rejectValue("username", "msg_forgetpw_no_question");
    		return "commons/forgetpassword";
    	}
    	
    	//get user's question1/2
    	String question1 = user.getQuestion1();
    	String question2 = user.getQuestion2();
    	user.setQuestion1(codeValueRepository.findById(question1).getLabel());
    	user.setQuestion2(codeValueRepository.findById(question2).getLabel());
    	user.setAnswer1("");
    	user.setAnswer2("");
    	
    	model.addAttribute("user", user);
    	return "commons/retrivequestions";
    }
    
    
    @RequestMapping(value = "/forgetpassword/checkanswers", method = RequestMethod.POST)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String checkRecallAnswer(User user,BindingResult result,Model model){
    	//validate answer1/2 is not null
    	boolean isValidate = true;
    	if(StringUtils.isEmpty(user.getAnswer1().trim())){
    		result.rejectValue("answer1", "msg_active_answer1_empty");
    		isValidate = false;
    	}
    	if(StringUtils.isEmpty(user.getAnswer2().trim())){
    		result.rejectValue("answer2", "msg_active_answer2_empty");
    		isValidate = false;
    	}
    	
    	//check answer1/2 is correct
    	User userTO = userRepository.findByUsername(user.getUsername());
    	if(!user.getAnswer1().trim().equals(userTO.getAnswer1().trim())){
    		result.rejectValue("answer1", "msg_forgetpw_answer1_error");
    		isValidate = false;
    	}
    	if(!user.getAnswer2().trim().equals(userTO.getAnswer2().trim())){
    		result.rejectValue("answer2", "msg_forgetpw_answer2_error");
    		isValidate = false;
    	}
    	
    	if(!isValidate){
    		return "commons/retrivequestions";
    	}
    	
    	//generate otp
    	Otp otp = new Otp();
		otp.setLoginId(userTO.getUsername());
		otp.setApplication(Constants.OTP_TYPE_RESET_PASSWORD);
		otp.setOtp(UUIDGenerator.generateUUID());
		otp.setDeleteIndicator(Constants.OTP_TYPE_DELETE_INDICATOR_N);
		otpRepository.saveAndFlush(otp);
		
		//update user set account_state="Locked"
		userTO.setAccountStatus(Constants.ACCOUNT_STATUS_LOCKED);
		userRepository.saveAndFlush(userTO);
		
    	//send mail
		ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId(Constants.TEMPLATE_ID_RESET_PASSWORD);
		
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("firstName", userTO.getFirstName());
		mapParam.put("lastName", userTO.getLastName());
		mapParam.put("loginId", userTO.getUsername());
		mapParam.put("expiryDt",DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm"));
		mapParam.put("rsetPwdDt",DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm"));
		mapParam.put("url", contextPage + "/resetpassword/" + otp.getOtp());
		
		List<String> sendTOList = new ArrayList<String>();
        sendTOList.add(userTO.getEmail());
        reportTemplateService.sendEmail(reportTemplate, mapParam, null, null, null, sendTOList, null, null);
    	
    	return "commons/login";
    }
    
    @RequestMapping(value = "/resetpassword/{otp}", method = RequestMethod.GET)
    public String resetPasswordForm(@PathVariable("otp") String otp,Model model){
    	String path = populateResetForm(otp, model);
    	User user = new User();
    	model.addAttribute("user", user);
    	return StringUtils.isEmpty(path) ? "commons/resetpassword" : path;
    }
    
    @RequestMapping(value="/resetpassword",method=RequestMethod.POST)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String resetPassword(User user,BindingResult result,Model model, HttpServletRequest request){
    	String otp = request.getParameter("otp");
    	//check opt
		Otp otpTo = otpRepository.findByOtp(otp);
		if(otpTo == null || otpTo.getDeleteIndicator().equalsIgnoreCase("Y")){
			result.reject("msg_active_otp_valid");
			populateResetForm(otp, model);
			return "commons/resetpassword";
		}
		
		boolean isValidate = true;
    	
    	//validate password
		if(StringUtils.isEmpty(user.getPassword().trim())){
			result.rejectValue("password", "msg_active_password_empty");
			isValidate = false;
		}
		if(StringUtils.isEmpty(user.getPasswordConfirm().trim())){
			result.rejectValue("passwordConfirm", "msg_active_passwordConfirm_empty");
			isValidate = false;
		}
		if(!isValidate){
			populateResetForm(otp, model);
			return "commons/resetpassword";
		}
		
    	if(!user.getPassword().trim().equals(user.getPasswordConfirm().trim())){
    		result.rejectValue("passwordConfirm", "msg_active_password_confirmed_valid");
    		isValidate = false;
    	}
    	if(user.getPassword().trim().length() < 8){
			result.rejectValue("password", "msg_active_password_password_length");
			isValidate = false;
		}
		
        Pattern p1 = Pattern.compile("\\S*[A-Z]\\S*");
        Pattern p2 = Pattern.compile("\\S*[a-z]\\S*");
        Pattern p3 = Pattern.compile("\\S*\\d\\S*");
        Pattern p4 = Pattern.compile("\\S*[^A-Z a-z 0-9]\\S*");

        int number = 0;
        if (p1.matcher(user.getPassword().trim()).matches()) {
            number++;
        }
        if (p2.matcher(user.getPassword().trim()).matches()) {
            number++;
        }
        if (p3.matcher(user.getPassword().trim()).matches()) {
            number++;
        }
        if (p4.matcher(user.getPassword().trim()).matches()) {
            number++;
        }

        if (number < 3) {
        	result.rejectValue("password", "msg_active_password_password_regex");
        	isValidate = false;
        }
        if(!isValidate){
        	populateResetForm(otp, model);
        	return "commons/resetpassword";
        }
        
    	//update user
        User userTO = userRepository.findByUsername(user.getUsername());
		userTO.setPassword(passwordEncoder.encode(user.getPassword().trim()));
		userTO.setPasswordConfirm(passwordEncoder.encode(user.getPassword().trim()));
		userTO.setPasswordModifiedDate(new Date());
		userTO.setPasswordReset(userTO.getPasswordReset() + 1);
		userTO.setAccountStatus(Constants.ACCOUNT_STATUS_ACTIVE);
		userRepository.saveAndFlush(userTO);
        		
		//update otp delete_indicator to "Y"
		otpTo.setDeleteIndicator("Y");
		otpRepository.saveAndFlush(otpTo);
    	
    	return "commons/login";
    }
    
    private String populateResetForm(String otp, Model model) {
		Otp otpTo = otpRepository.findByOtp(otp);
    	if(otp == null || otpTo.getDeleteIndicator().equals("Y")){
    		return "commons/error";
    	}
    	model.addAttribute("otp", otpTo.getOtp());
    	model.addAttribute("username", otpTo.getLoginId());
    	return "";
	}
    
    @RequestMapping(value = "/error")
    public String error() {
    	return "commons/error";
    }
    
    /**
     * forword to active account page
     *
     * @return
     */
    @RequestMapping(value = "/activeAccount/{otp}", method = RequestMethod.GET)
    public String activeAccountForm(@PathVariable("otp") String otp, Model model) {
    	String path = populateActiveForm(otp, model);
    	User user = new User();
    	model.addAttribute("user", user);
        return StringUtils.isEmpty(path) ? "commons/activeAccount" : path;
    }

    /**
     * active account
     * @return
     */
    @RequestMapping(value = "/activeAccount", method = RequestMethod.POST)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String activeAccount(User user,BindingResult result,Model model, HttpServletRequest request) {
    	String otp = request.getParameter("otp");
    	
    	//1 validate
    	validate(user, result, model,otp);
    	
    	if(result.hasErrors()){
    		String path = populateActiveForm(otp,model);
    		return StringUtils.isEmpty(path) ? "commons/activeAccount" : path;
    	}else{
    		//update user
    		User userTO = userRepository.findByUsername(user.getUsername());
    		userTO.setQuestion1(user.getQuestion1().trim());
    		userTO.setQuestion2(user.getQuestion2().trim());
    		userTO.setAnswer1(user.getAnswer1().trim());
    		userTO.setAnswer2(user.getAnswer2().trim());
    		userTO.setPassword(passwordEncoder.encode(user.getPassword().trim()));
    		userTO.setPasswordConfirm(passwordEncoder.encode(user.getPassword().trim()));
    		userTO.setPasswordModifiedDate(new Date());
    		userTO.setPasswordReset(userTO.getPasswordReset() + 1);
    		userTO.setAccountStatus(Constants.ACCOUNT_STATUS_ACTIVE);
    		userRepository.saveAndFlush(userTO);
    		
    		//update otp delete_indicator to "Y"
    		Otp otpTo = otpRepository.findByOtp(otp);
    		otpTo.setDeleteIndicator("Y");
    		otpRepository.saveAndFlush(otpTo);
    		return "commons/login";
    	}
    }
    
    private String populateActiveForm(String otp, Model model) {
    	String path = "";
		Otp otpTo = otpRepository.findByOtp(otp);
    	if(otp == null || otpTo.getDeleteIndicator().equals("Y")){
    		return path="commons/error";
    	}
    	String codeTypeName = Constants.CHALLENGE_QUESTION;
    	List<CodeValue> list =codeValueRepository.findByGlobalCodeTypeName(codeTypeName);
    	if(list == null || list.size() == 0){
    		return path="commons/error";
    	}
    	
    	model.addAttribute("otp", otpTo.getOtp());
    	model.addAttribute("loginId", otpTo.getLoginId());
    	model.addAttribute("questions", list);
    	
    	return path;
	}
    
    
    private void validate(User user,BindingResult result,Model model,String otp) {
        ActiveAccountValidator validator = new ActiveAccountValidator(otp, otpRepository);
        validator.validate(user, result);
    }


}
