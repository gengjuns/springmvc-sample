package com.saas.core.validation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 
 * @since 25/03/2013 3:36 PM
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = WordNumberValidator.class)
@Documented
public @interface WordNumber {

    String message() default "{com.saas.validation.constraints.WordNumber.message}";


    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};


}
