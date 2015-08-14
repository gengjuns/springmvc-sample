package com.saas.code.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saas.Constants;
import com.saas.core.entity.audit.support.AuditableEntitySupport;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 * @since 11/01/2013 1:55 AM
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Cacheable
public class CodeType extends AuditableEntitySupport<String> {

    @NotEmpty
    @Length(max = 50)
    @Column(length = 50)
    protected String name;

    @Length(max = 255)
    @Column(length = 255)
    protected String description;

    @OneToMany(mappedBy = "type")
    @ForeignKey(name="none")
    @JsonIgnore
    protected List<CodeValue> values;


    public CodeType() {
    }


    public CodeType(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public CodeType(String name, String description, Long tenantId) {
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


    public List<CodeValue> getValues() {
        if (values == null) {
            values = new ArrayList<CodeValue>();
        }
        return values;
    }


    public void setValues(List<CodeValue> values) {
        this.values = values;
    }


    public void addValue(CodeValue value) {
        if (!getValues().contains(value)) {
            value.setType(this);
            getValues().add(value);
        }
    }


    public void removeValue(CodeValue value) {
        value.setType(null);
        getValues().remove(value);
    }


}
