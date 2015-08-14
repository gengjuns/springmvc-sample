package com.saas.core.web.search;

import com.saas.core.util.StringUtils;

/**
 * @author 
 * @since 21/01/2013 5:20 PM
 */
public enum EntitySearchOperator {
    eq, neq, gt, gte, lt, lte, startswith, endswith, contains, doesnotcontain;


    public static EntitySearchOperator resolve(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }

        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


}
