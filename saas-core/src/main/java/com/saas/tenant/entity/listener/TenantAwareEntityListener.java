package com.saas.tenant.entity.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.stereotype.Component;

import com.saas.Constants;
import com.saas.tenant.TenantContextHolder;
import com.saas.tenant.entity.TenantAwareEntity;


/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Component
public class TenantAwareEntityListener {

    @PrePersist
    @PreUpdate
    @SuppressWarnings("unchecked")
    public void updateEntity(Object entity) {
        if (entity == null) {
            return;
        }

        if (TenantAwareEntity.class.isAssignableFrom(entity.getClass())) {
            TenantAwareEntity tenantEntity = (TenantAwareEntity) entity;
            if (tenantEntity.getTenantId() == null) {
                String loginTenantId = TenantContextHolder.getLoginTenantId();
                tenantEntity.setTenantId(loginTenantId);
            }
        }
    }


}
