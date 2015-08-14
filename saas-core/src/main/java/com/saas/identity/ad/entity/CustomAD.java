package com.saas.identity.ad.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saas.tenant.entity.support.TenantAuditableEntitySupport;

@Entity
@DynamicInsert
@DynamicUpdate
@Cacheable
@Audited
@Table(name = "config_ad_map")
public class CustomAD extends TenantAuditableEntitySupport<String> {

    @ManyToOne
    @JoinColumn(name = "adconfig_id", updatable = false, nullable = false)
    @ForeignKey(name="none")
    @JsonIgnore
    protected ADConfig adConfig;
    
    @NotEmpty
    @Index(name = "custom_field")
    @Column(length = 100)
    private String customField;
    
    @NotEmpty
    @Index(name = "ad_field")
    @Column(length = 100)
    private String adField;
    
    @NotNull
    @Index(name = "ad_index")
    @Column(length = 100)
    private int adIndex;

    public ADConfig getAdConfig() {
        return adConfig;
    }

    public void setAdConfig(ADConfig adConfig) {
        this.adConfig = adConfig;
    }

    public String getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }

    public String getAdField() {
        return adField;
    }

    public void setAdField(String adField) {
        this.adField = adField;
    }

    public int getAdIndex() {
        return adIndex;
    }

    public void setAdIndex(int adIndex) {
        this.adIndex = adIndex;
    }

}