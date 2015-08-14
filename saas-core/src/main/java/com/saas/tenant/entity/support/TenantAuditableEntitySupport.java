package com.saas.tenant.entity.support;

import com.saas.core.entity.audit.support.AuditableEntitySupport;
import com.saas.tenant.entity.TenantAwareEntity;
import com.saas.tenant.entity.listener.TenantAwareEntityListener;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.ParamDef;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import java.io.Serializable;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@MappedSuperclass
@EntityListeners({TenantAwareEntityListener.class})
@FilterDef(name = "tenantFilter",
parameters = {@ParamDef(name = "loginTenantId", type = "long")},
defaultCondition = ":loginTenantId = tenant_id")
@Filter(name = "tenantFilter")
public abstract class TenantAuditableEntitySupport<ID extends Serializable> extends AuditableEntitySupport<ID> implements TenantAwareEntity<ID> {

    @Field(analyze = Analyze.NO)
    @Index(name = "idx_tenant_id")
    @Column(name = "tenant_id", nullable = false)
    protected ID tenantId;


    @Override
    public ID getTenantId() {
        return tenantId;
    }


    @Override
    public void setTenantId(ID tenantId) {
        this.tenantId = tenantId;
    }
    

}
