package com.saas.tenant.entity.filter;

import com.saas.core.entity.Entity;
import com.saas.core.entity.filter.EntityFilter;
import com.saas.tenant.TenantContextHolder;
import com.saas.tenant.entity.TenantAwareEntity;

import org.springframework.stereotype.Component;

/**
 * @author 
 * @since 15/01/2013 2:15 PM
 */
@Component
public class MultiTenantFilter implements EntityFilter {

    @Override
    public String getFilterName() {
        return "tenantFilter";
    }


    @Override
    public String getParameterName() {
        return "loginTenantId";
    }


    @Override
    public Object getParameterValue() {
        return TenantContextHolder.getLoginTenantId();
    }


    @Override
    public boolean filter(Class<? extends Entity> entityClass) {
        return TenantContextHolder.isMultiTenantEnabled()
                && TenantAwareEntity.class.isAssignableFrom(entityClass)
                && TenantContextHolder.getLoginTenantId() != null
                && !TenantContextHolder.isSkipTenant();
    }


}
