package com.saas.identity.user.entitiy;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.saas.core.util.StringUtils;
import com.saas.core.validation.util.ValidationUtils;
import com.saas.identity.user.repository.UserRepository;

public class UserValidator implements Validator {

    protected String dbUsernameValidationMode;

    protected String dbUsernameValidationPattern;

    protected String dbUsernameValidationMessage;

    protected UserRepository userRepository;


    public UserValidator(String dbUsernameValidationMode,
                         String dbUsernameValidationPattern,
                         String dbUsernameValidationMessage,
                         UserRepository userRepository) {
        this.dbUsernameValidationMode = dbUsernameValidationMode;
        this.dbUsernameValidationPattern = dbUsernameValidationPattern;
        this.dbUsernameValidationMessage = dbUsernameValidationMessage;
        this.userRepository = userRepository;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        //check password
        User user = ((User) target);
        if (null != user) {
         
            if (user.getId() == null && user.getLoginType() == UserLoginType.Database) {
                //check if db username is email or pattern
                if ("email".equalsIgnoreCase(dbUsernameValidationMode)) {
                    if (!ValidationUtils.validateEmail(user.getUsername())) {
                        errors.rejectValue("username", dbUsernameValidationMessage, null);
                        return;
                    }
                } else {
                    if (StringUtils.hasText(dbUsernameValidationPattern)) {
                        if (!Pattern.compile(dbUsernameValidationPattern).matcher(user.getUsername()).matches()) {
                            errors.rejectValue("username", dbUsernameValidationMessage, null);
                            return;
                        }
                    }
                }
            }
            User tmpUser = userRepository.findByUsernameAndLoginType(user.getUsername(), user.getLoginType());
            //unique constraint
            if (null != tmpUser && (tmpUser.getId() != ((User) target).getId())) {
                errors.rejectValue("username", "msg_username_unique_valid");
            }
        }
    }

}
