package com.saas.core.annotation;

import javax.inject.Named;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Replace the need of @Configurable using aspectj. For event that does not use spring beans, use direct JPA annotation
 *
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Named
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener {

}

