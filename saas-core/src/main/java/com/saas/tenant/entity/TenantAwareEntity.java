package com.saas.tenant.entity;

import com.saas.core.entity.Entity;

import java.io.Serializable;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
public interface TenantAwareEntity<ID extends Serializable> extends Entity<ID> {

    ID getTenantId();


    void setTenantId(ID tenantId);


}
