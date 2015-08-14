package com.saas.core.entity.audit.listener;

import com.saas.core.entity.audit.AuditableEntity;
import com.saas.identity.user.UserContextHolder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import java.util.Date;

/**
 * JPA entity listener to automatically set the updated date/user
 * for {@link AuditableEntity} when it's created or updated.
 *
 * @author 
 * @since 19/11/2012 10:00 AM
 */
public class AuditableEntityListener {

    @PrePersist
    @PreUpdate
    @SuppressWarnings("unchecked")
    public void updateEntity(Object entity) {
        if (entity == null) {
            return;
        }

        if (AuditableEntity.class.isAssignableFrom(entity.getClass())) {
            AuditableEntity auditEntity = (AuditableEntity) entity;
            //automatically set the updated date. If created date is null, it will be updated as well
            auditEntity.setUpdatedDate(new Date());
            auditEntity.setUpdatedBy(UserContextHolder.getLoginUserId());
        }
    }
}
