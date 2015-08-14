package com.saas.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper utils for Class manipulation
 *
 * @author 
 * @since 26/12/2012 11:57 AM
 */
public abstract class ClassUtils extends org.springframework.util.ClassUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);


    public static final Class findClass(String name) {
        try {
            return forName(name, getDefaultClassLoader());
        } catch (ClassNotFoundException e) {
            logger.trace("Unable to load class '" + name + "'", e);
        }
        return null;
    }


}
