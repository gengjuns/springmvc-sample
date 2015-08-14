package com.saas.core.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author 
 * @since 25/03/2013 3:38 PM
 */
public class WordNumberValidator implements ConstraintValidator<WordNumber, String> {

    private static final String WORD_NUMBER_PATTERN = "^([a-zA-Z0-9_]*)$";

    private Pattern wordNumberPattern = Pattern.compile(WORD_NUMBER_PATTERN);


    @Override
    public void initialize(WordNumber constraintAnnotation) {

    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return wordNumberPattern.matcher(value).matches();
    }


}
