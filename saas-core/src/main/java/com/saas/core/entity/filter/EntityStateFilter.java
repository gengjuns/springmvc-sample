package com.saas.core.entity.filter;

import com.saas.core.entity.Entity;
import com.saas.core.entity.EntityState;
import com.saas.core.entity.EntityStateContextHolder;
import com.saas.core.entity.audit.AuditableEntity;

import org.springframework.stereotype.Component;

/**
 * @author 
 * @since 15/01/2013 2:13 PM
 */
@Component
public class EntityStateFilter implements EntityFilter {

    @Override
    public String getFilterName() {
        return "entityStateFilter";
    }


    @Override
    public String getParameterName() {
        return "entityStateId";
    }


    @Override
    public Object getParameterValue() {
        return EntityStateContextHolder.getEntityState().ordinal();
    }


    @Override
    public boolean filter(Class<? extends Entity> entityClass) {
        return AuditableEntity.class.isAssignableFrom(entityClass) &&
                EntityStateContextHolder.getEntityState() != EntityState.Any;
    }


}
