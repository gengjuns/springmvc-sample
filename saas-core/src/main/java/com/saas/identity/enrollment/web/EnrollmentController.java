package com.saas.identity.enrollment.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.saas.core.util.BeanUtils;
import com.saas.core.util.StringUtils;
import com.saas.core.web.ControllerSupport;
import com.saas.identity.enrollment.entity.ChallengeValidator;
import com.saas.identity.enrollment.entity.ForgotPasswordValidator;
import com.saas.identity.group.repository.GroupRepository;
import com.saas.identity.role.repository.RoleRepository;
import com.saas.identity.user.entitiy.User;
import com.saas.identity.user.entitiy.UserValidator;
import com.saas.identity.user.repository.UserRepository;
import com.saas.tenant.repository.TenantRepository;

@Controller
@RequestMapping("/enrollment")
public class EnrollmentController extends ControllerSupport<User, String, UserRepository> {

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

    @Value("${auth.db.username.validation.mode}")
    protected String dbUsernameValidationMode;

    @Value("${auth.db.username.validation.pattern}")
    protected String dbUsernameValidationPattern;

    @Value("${auth.db.username.validation.message}")
    protected String dbUsernameValidationMessage;

    @Override
    protected void preCreate(User entity, BindingResult bindingResult, Model model) {
        super.preCreate(entity, bindingResult, model);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
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
    public String pathAfterCreate(User entity, BindingResult bindingResult, Model model) {
        return "redirect:/login";
    }

    @RequestMapping(value = "/checkcode", method = RequestMethod.GET)
    public void checkCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            CaptchaServiceSingleton.getInstance().writeCaptchaImage(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
    public String forgotPassword(Model model) {
        User entity = BeanUtils.instantiate(getEntityClass());
        model.addAttribute(getModelAttributeName(), entity);
        initCreate(model);
        populateEditForm(entity, model);
        return getViewName("/forgotpassword");
    }

    @RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
    public String forgotPassword(@ModelAttribute User entity, BindingResult bindingResult, Model model, HttpSession session) {
        ForgotPasswordValidator forgotPasswordValidator = new ForgotPasswordValidator(userRepository);
        forgotPasswordValidator.validate(entity, bindingResult);
        String checkCode = entity.getCheckCode();
        if (!StringUtils.isEmpty(checkCode)) {
            CaptchaServiceSingleton.getInstance().validateCaptchaResponse(checkCode, session, bindingResult);
        }
        if (bindingResult.hasErrors()) {
            handleCreateError(entity, bindingResult, model);
            populateEditForm(entity, model);
            return getViewName("/forgotpassword");
        }
        String username = entity.getUsername();
        User user = userRepository.findByUsernameAndLoginType(username, entity.getLoginType());
        
        String email = user.getEmail();
        String mobileNumber = user.getMobileNumber();
        String securityCode = buildCode();
        if(!StringUtils.isEmpty(email))
        {
         // TODO send security code
        }
        if(!StringUtils.isEmpty(mobileNumber))
        {
         // TODO send security code
        }
        user.setSecurityCodeTmp(securityCode);
        
        session.setAttribute("user", user);
        return getViewName("/challenge", true);

    }

    @RequestMapping(value = "/challenge", method = RequestMethod.GET)
    public String challenge(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return getViewName("/forgotpassword", true);
        }
        model.addAttribute(getModelAttributeName(), user);
        initCreate(model);
        populateEditForm(user, model);
        return getViewName("/challenge");
    }

    @RequestMapping(value = "/challenge", method = RequestMethod.POST)
    public String challenge(@ModelAttribute User entity, BindingResult bindingResult, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return getViewName("/forgotpassword", true);
        }
        user.setAnswerTmp1(entity.getAnswerTmp1());
        user.setAnswerTmp2(entity.getAnswerTmp2());
        
        ChallengeValidator challengeValidator = new ChallengeValidator();
        challengeValidator.validate(user, bindingResult);
        
        if (bindingResult.hasErrors()) {
            entity.setDomain(user.getDomain());
            entity.setUsername(user.getUsername());
            entity.setQuestion1(user.getQuestion1());
            entity.setQuestion2(user.getQuestion2());
            
            handleCreateError(entity, bindingResult, model);
            populateEditForm(entity, model);
            return getViewName("/challenge");
        }

        return getViewName("/success", true);
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return getViewName("/forgotpassword", true);
        }
        model.addAttribute(getModelAttributeName(), user);
        initCreate(model);
        populateEditForm(user, model);
        return getViewName("/success");
    }

    @RequestMapping(value = "/resendotp", method = RequestMethod.POST)
    public String resendOTP(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return getViewName("/forgotpassword", true);
        }
        return getViewName("/success", true);
    }

    private String buildCode() {
        int random = (int) (Math.random() * 1000000);
        return String.valueOf(random);
    }
}
