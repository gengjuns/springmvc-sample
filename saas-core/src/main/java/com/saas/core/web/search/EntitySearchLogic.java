package com.saas.core.web.search;

import com.saas.core.util.StringUtils;

/**
 * @author 
 * @since 21/01/2013 5:38 PM
 */
public enum EntitySearchLogic {

    and, or, andNot, orNot;


    public static EntitySearchLogic resolve(String name) {
        if (!StringUtils.hasText(name)) {
            return and;
        }

        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            return and;
        }
    }


}
