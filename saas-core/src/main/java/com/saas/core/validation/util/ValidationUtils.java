package com.saas.core.validation.util;

import org.hibernate.validator.internal.constraintvalidators.EmailValidator;
import org.hibernate.validator.internal.constraintvalidators.URLValidator;

/**
 * @author 
 * @since 21/03/2013 4:19 PM
 */
public class ValidationUtils {

    private static final EmailValidator emailValidator = new EmailValidator();

    private static final URLValidator urlValidator = new URLValidator();


    public static boolean validateEmail(String email) {
        return emailValidator.isValid(email, null);
    }


    public static boolean validateURL(String url) {
        return urlValidator.isValid(url, null);
    }


}
