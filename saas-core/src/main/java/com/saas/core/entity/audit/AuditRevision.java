package com.saas.core.entity.audit;

import com.saas.core.entity.audit.listener.AuditableRevisionListener;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

/**
 * Hibernate's Envers Audit revision object to keep track of each update
 *
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Entity
@RevisionEntity(AuditableRevisionListener.class)
public class AuditRevision implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @RevisionNumber
    protected Long revId;

    @NotNull
    protected String userId;

    protected String tenantId;

    @Temporal(TemporalType.TIMESTAMP)
    @RevisionTimestamp
    protected Date updatedDate;


    public Long getRevId() {
        return revId;
    }


    public void setRevId(Long revId) {
        this.revId = revId;
    }


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getTenantId() {
        return tenantId;
    }


    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }


    public Date getUpdatedDate() {
        return updatedDate;
    }


    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }


}
