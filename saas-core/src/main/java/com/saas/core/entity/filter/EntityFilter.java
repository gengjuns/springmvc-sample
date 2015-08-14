package com.saas.core.entity.filter;

import com.saas.core.entity.Entity;

/**
 * @author 
 * @since 15/01/2013 2:10 PM
 */
public interface EntityFilter {

    String getFilterName();


    String getParameterName();


    Object getParameterValue();


    boolean filter(Class<? extends Entity> entityClass);


}
