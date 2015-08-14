package com.saas.core.entity.audit.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saas.core.annotation.DateTimeFormat;
import com.saas.core.entity.EntityState;
import com.saas.core.entity.audit.AuditableEntity;
import com.saas.core.entity.audit.listener.AuditableEntityListener;
import com.saas.core.entity.support.EntitySupport;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Resolution;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

/**
 * Support class for {@link AuditableEntity}
 *
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Audited
@MappedSuperclass
@EntityListeners({AuditableEntityListener.class})
/*@FilterDef(name = "entityStateFilter",
        parameters = {@ParamDef(name = "entityStateId", type = "int")},
        defaultCondition = ":entityStateId = state_id")
@Filter(name = "entityStateFilter")*/
public abstract class AuditableEntitySupport<ID extends Serializable> extends EntitySupport<ID> implements AuditableEntity<ID> {

    @NotAudited
    @JsonIgnore
    @Field(analyze = Analyze.NO)
    @Column(nullable = false)
    protected ID createdBy;

    @NotAudited
    @JsonIgnore
    @Field(analyze = Analyze.NO)
    @DateBridge(resolution = Resolution.MINUTE)
    @DateTimeFormat
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    protected Date createdDate;

    @Field(analyze = Analyze.NO)
    @Column(nullable = false)
    protected ID updatedBy;

    @Field(analyze = Analyze.NO)
    @DateBridge(resolution = Resolution.MINUTE)
    @DateTimeFormat
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    protected Date updatedDate;

    @JsonIgnore
    @Field(analyze = Analyze.NO)
    @NotNull
    @Column(name = "state_id")
    protected EntityState state = EntityState.Active;

    @JsonIgnore
    @Version
    protected int version;


    @Override
    public ID getCreatedBy() {
        return createdBy;
    }


    @Override
    public void setCreatedBy(ID createdBy) {
        this.createdBy = createdBy;
    }


    @Override
    public Date getCreatedDate() {
        return createdDate;
    }


    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    @Override
    public ID getUpdatedBy() {
        return updatedBy;
    }


    @Override
    public void setUpdatedBy(ID updatedBy) {
        this.updatedBy = updatedBy;
        if (this.createdBy == null) {
            this.createdBy = this.updatedBy;
        }
    }


    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }


    @Override
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
        if (this.createdDate == null) {
            this.createdDate = this.updatedDate;
        }
    }


    public EntityState getState() {
        return state;
    }


    public void setState(EntityState state) {
        this.state = state;
    }


    @Override
    public int getVersion() {
        return version;
    }


    public void setVersion(int version) {
        this.version = version;
    }


}
