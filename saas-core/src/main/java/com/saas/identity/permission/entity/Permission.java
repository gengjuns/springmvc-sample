package com.saas.identity.permission.entity;

import com.saas.core.entity.audit.support.AuditableEntitySupport;
import com.saas.core.validation.validator.WordNumber;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Cacheable
@Audited
@Table(name = "aa_permission")
public class Permission extends AuditableEntitySupport<String> {

    @WordNumber
    @NotEmpty
    @Index(name = "idx_permission_name")
    @Column(length = 50)
    //must be in uppercase, without space
    protected String name;

    @Column(length = 255)
    protected String description;


    public Permission() {
    }


    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


}
