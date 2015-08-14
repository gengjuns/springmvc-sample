package com.saas.core.module.entity;

import com.saas.core.entity.audit.support.AuditableEntitySupport;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Module configuration
 *
 * @author 
 * @since 26/12/2012 4:39 PM
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Cacheable
public class Module extends AuditableEntitySupport<String> {

    @NotEmpty
    @Length(min = 3, max = 255)
    @Column(length = 255, unique = true, updatable = false)
    protected String name;

    @NotEmpty
    @Column(length = 50)
    protected String label;

    @Length(max = 255)
    @Column(length = 255)
    protected String description;

    @NotNull
    protected Integer moduleVersion = 1;


    public Module() {
    }


    public Module(String name, String label, String description, Integer moduleVersion) {
        this.name = name;
        this.label = label;
        this.description = description;
        this.moduleVersion = moduleVersion;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getModuleVersion() {
        return moduleVersion;
    }


    public void setModuleVersion(Integer moduleVersion) {
        this.moduleVersion = moduleVersion;
    }


}
