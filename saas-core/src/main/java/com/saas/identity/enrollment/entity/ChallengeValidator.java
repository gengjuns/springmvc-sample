package com.saas.identity.enrollment.entity;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.saas.core.util.StringUtils;
import com.saas.identity.user.entitiy.User;

public class ChallengeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = ((User) target);
        String answerTmp1 = user.getAnswerTmp1();
        String answerTmp2 = user.getAnswerTmp2();
        if (StringUtils.isEmpty(answerTmp1) || StringUtils.isEmpty(answerTmp2)) {
            if (StringUtils.isEmpty(answerTmp1)) {
                errors.rejectValue("answerTmp1", "error_enrollment_forgotpassword_isnull");
            }
            if (StringUtils.isEmpty(answerTmp2)) {
                errors.rejectValue("answerTmp2", "error_enrollment_forgotpassword_isnull");
            }
            return;
        }
        else
        {
            if (!answerTmp1.equals(user.getAnswer1())) {
                errors.rejectValue("answerTmp1", "error_enrollment_forgotpassword_isfalse");
            }
            if (!answerTmp2.equals(user.getAnswer2())) {
                errors.rejectValue("answerTmp2", "error_enrollment_forgotpassword_isfalse");
            }
            return;
        }
        // TODO validate security code
        
        
    }

}
