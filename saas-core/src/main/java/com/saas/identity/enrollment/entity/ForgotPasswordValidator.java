package com.saas.identity.enrollment.entity;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.saas.core.util.StringUtils;
import com.saas.identity.user.entitiy.User;
import com.saas.identity.user.repository.UserRepository;

public class ForgotPasswordValidator implements Validator {


    protected UserRepository userRepository;

    public ForgotPasswordValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = ((User) target);
        if (null == user) {
            return;
        }
        String domain = user.getDomain();
        String username = user.getUsername();
        String checkCode = user.getCheckCode();
        if (StringUtils.isEmpty(domain) || StringUtils.isEmpty(username) || StringUtils.isEmpty(checkCode)) {
            if (StringUtils.isEmpty(domain)) {
                errors.rejectValue("domain", "error_enrollment_forgotpassword_isnull");
            }
            if (StringUtils.isEmpty(username)) {
                errors.rejectValue("username", "error_enrollment_forgotpassword_isnull");
            }
            if (StringUtils.isEmpty(username)) {
                errors.rejectValue("checkCode", "error_enrollment_forgotpassword_isnull");
            }
            return;
        }
     /*   User tempUser = userRepository.findByUsername(username);
        if (null == tempUser) {
            errors.rejectValue("username", "error_enrollment_forgotpassword_notexited");
            return;
        }*/

        User tempUser = userRepository.findByUsernameAndLoginType(username, user.getLoginType());
        if (null == tempUser) {
            errors.rejectValue("username", "error_enrollment_forgotpassword_notexited");
            return;
        }

        String email = tempUser.getEmail();

        String mobileNumber = tempUser.getMobileNumber();

        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(mobileNumber)) {
            errors.rejectValue("username", "error_enrollment_forgotpassword_nocontact");
            return;
        }
    }
}
