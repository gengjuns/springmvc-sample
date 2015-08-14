package com.saas.tenant.entity.support;

import com.saas.core.entity.support.EntitySupport;
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
 * @since 13/02/2013 3:41 PM
 */
@MappedSuperclass
@EntityListeners({TenantAwareEntityListener.class})
@FilterDef(name = "entityStateFilter",
        parameters = {@ParamDef(name = "entityStateId", type = "int")},
        defaultCondition = ":entityStateId = state_id")
@Filter(name = "entityStateFilter")
public abstract class TenantEntitySupport<ID extends Serializable> extends EntitySupport<ID> implements TenantAwareEntity<ID> {

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
