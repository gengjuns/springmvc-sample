package com.saas.otp.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.saas.core.entity.audit.support.AuditableEntitySupport;

@Entity
@DynamicInsert
@DynamicUpdate
@Cacheable
@Table(name = "otp")
public class Otp extends AuditableEntitySupport<String> {

    private static final long serialVersionUID = -440155986661071660L;

    @NotEmpty
    @Length(max = 32)
    @Column(length = 32)
    protected String otp;
    
    @NotEmpty
    @Length(max = 32)
    @Column(length = 32)
    protected String application;
    
    @NotEmpty
    @Length(max = 100)
    @Column(length = 100)
    protected String loginId;
    
    @NotEmpty
    @Length(max = 1)
    @Column(length = 1)
    protected String deleteIndicator;

    public String getDeleteIndicator() {
        return deleteIndicator;
    }

    public void setDeleteIndicator(String deleteIndicator) {
        this.deleteIndicator = deleteIndicator;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

}
