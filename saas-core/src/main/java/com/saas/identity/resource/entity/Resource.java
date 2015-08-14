package com.saas.identity.resource.entity;

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
@Table(name = "aa_resource")
public class Resource extends AuditableEntitySupport<String> {

    @WordNumber
    @NotEmpty
    @Index(name = "idx_resource_name")
    @Column(length = 50)
    // must be in uppercase, without space
    protected String name;

    @NotEmpty
    @Column(length = 50)
    protected String url;

    public Resource() {
    }

    public Resource(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
