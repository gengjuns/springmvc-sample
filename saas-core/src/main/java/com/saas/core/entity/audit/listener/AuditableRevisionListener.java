package com.saas.core.entity.audit.listener;

import com.saas.core.entity.audit.AuditRevision;
import com.saas.identity.user.UserContextHolder;
import com.saas.tenant.TenantContextHolder;

import org.hibernate.envers.RevisionListener;

/**
 * Wired by {@link org.hibernate.envers.RevisionEntity} annotation to automatically
 * set the user/tenant id from the {@link com.saas.tenant.TenantContextHolder} and {@link com.saas.identity.user.UserContextHolder}.
 *
 * @author 
 * @since 19/11/2012 10:00 AM
 */
public class AuditableRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevision revision = ((AuditRevision) revisionEntity);
        revision.setUserId(UserContextHolder.getLoginUserId());
        revision.setTenantId(TenantContextHolder.getLoginTenantId());
    }


}
