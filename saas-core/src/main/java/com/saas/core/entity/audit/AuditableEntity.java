package com.saas.core.entity.audit;

import com.saas.core.entity.Entity;
import com.saas.core.entity.EntityState;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity marker interface to keep track of create/update date and user info
 *
 * @author 
 * @since 19/11/2012 10:00 AM
 */
public interface AuditableEntity<ID extends Serializable> extends Entity<ID> {

    ID getCreatedBy();


    void setCreatedBy(ID createdBy);


    Date getCreatedDate();


    void setCreatedDate(Date createdDate);


    ID getUpdatedBy();


    void setUpdatedBy(ID updatedBy);


    Date getUpdatedDate();


    void setUpdatedDate(Date updatedDate);


    EntityState getState();


    void setState(EntityState state);


    int getVersion();


}
