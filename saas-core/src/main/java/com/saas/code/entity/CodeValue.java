package com.saas.code.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saas.Constants;
import com.saas.core.annotation.DateTimeFormat;
import com.saas.core.entity.audit.support.AuditableEntitySupport;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 
 * @since 11/01/2013 1:55 AM
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Cacheable
public class CodeValue extends AuditableEntitySupport<String> {

    @NotEmpty
    @Length(max = 100)
    @Index(name = "idx_codevalue_code")
    @Column(length = 100)
    protected String code;

    @NotEmpty
    @Length(max = 255)
    @Column(length = 255)
    protected String label;

    @NotNull
    protected Integer position = 0;

    @Index(name = "idx_codevalue_effectivedate")
    @DateTimeFormat
    @Temporal(TemporalType.TIMESTAMP)
    protected Date effectiveDate;

    @Index(name = "idx_codevalue_expirydate")
    @DateTimeFormat
    @Temporal(TemporalType.TIMESTAMP)
    protected Date expiryDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @ForeignKey(name="none")
    @JoinColumn(name = "code_type_id", nullable = false, updatable = false)
    protected CodeType type;

    public CodeValue() {
    }


    public CodeValue(String code, String label, Integer position, CodeType type) {
        this.code = code;
        this.label = label;
        this.position = position;
        this.type = type;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String value) {
        this.label = value;
    }


    public Integer getPosition() {
        return position;
    }


    public void setPosition(Integer position) {
        this.position = position;
    }



    public Date getEffectiveDate() {
        return effectiveDate;
    }


    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }


    public Date getExpiryDate() {
        return expiryDate;
    }


    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }


    public CodeType getType() {
        return type;
    }


    public void setType(CodeType type) {
        this.type = type;
    }
}
