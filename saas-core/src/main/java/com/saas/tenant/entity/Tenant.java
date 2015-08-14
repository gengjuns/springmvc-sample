package com.saas.tenant.entity;

import com.saas.core.entity.audit.support.AuditableEntitySupport;
import com.saas.core.module.entity.Module;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.util.Set;

/**
 * @author 
 * @since 19/11/2012 10:00 AM
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Cacheable
@Audited
@Table(name = "tenant")
public class Tenant extends AuditableEntitySupport<String> {

    @NotEmpty
    @Index(name = "idx_name_name")
    @Column(length = 50)
    protected String name;

    @Column(length = 255)
    protected String description;
    
    @Column(length = 20)
    protected String tenantStatus;
    
    @Column()
    protected String parentTenantId;


    public Tenant() {
    }
    
	public Tenant(String name, String description, String tenantStatus) {
		this.name = name;
		this.description = description;
		this.tenantStatus = tenantStatus;
	}

	public Tenant(String name, String description, String tenantStatus,
			String parentTenantId) {
		this.name = name;
		this.description = description;
		this.tenantStatus = tenantStatus;
		this.parentTenantId = parentTenantId;
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


	public String getTenantStatus() {
		return tenantStatus;
	}


	public void setTenantStatus(String tenantStatus) {
		this.tenantStatus = tenantStatus;
	}

	public String getParentTenantId() {
		return parentTenantId;
	}

	public void setParentTenantId(String parentTenantId) {
		this.parentTenantId = parentTenantId;
	}

}
