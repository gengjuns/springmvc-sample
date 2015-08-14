package com.saas.reporttemplate.entity;

import java.io.File;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.saas.core.entity.audit.support.AuditableEntitySupport;

@Entity
@DynamicInsert
@DynamicUpdate
@Cacheable
@Table(name = "report_template")
public class ReportTemplate extends AuditableEntitySupport<String> {
	
	private static final long serialVersionUID = 7795006484236113512L;
	/**
	 * 
	 */
    @NotEmpty
    @Length(max = 50)
    @Column(length = 50)
	public String templateId;


    @Length(max = 255)
    @Column(length = 255)
	public String templateDesc;
    
    @Length(max = 50)
    @Column(length = 50)
	public String templateCategory;
    
    @Lob
	public byte[] templateContent;
    
    transient
    private MultipartFile file;
    
    @Length(max = 255)
    @Column(length = 255)
	public String templateParameter;
    
    transient
    public String [] parameters;
    
    @Length(max = 50)
    @Column(length = 50)
	public String templateStatus;
    
    @NotEmpty
    @Length(max = 10)
    @Column(length = 10)
	public String templateMode;
    
    @Length(max = 255)
    @Column(length = 255)
    @NotEmpty
	public String templateSubject;// TEMPLATE_SUBJECT
    
    @Length(max = 255)
    @Column(length = 255)
	public String templateEmailTo;// TEMPLATE_EMAIL_TO
    
    @Length(max = 255)
    @Column(length = 255)
	public String templateEmailCc;// TEMPLATE_EMAIL_CC
    
    @Length(max = 255)
    @Column(length = 255)
	public String templateEmailBcc;// TEMPLATE_EMAIL_BCC
    
    @Length(max = 255)
    @Column(length = 255)
    @NotEmpty
	public String templateEmailFrom;// TEMPLATE_EMAIL_FROM
    
    
	public ReportTemplate() {
	}

	public ReportTemplate(String templateId, String templateDesc,
			String templateCategory, byte[] templateContent,
			String templateParameter, String templateStatus,
			String templateMode, String templateSubject,
			String templateEmailTo, String templateEmailCc,
			String templateEmailBcc, String templateEmailFrom) {
		this.templateId = templateId;
		this.templateDesc = templateDesc;
		this.templateCategory = templateCategory;
		this.templateContent = templateContent;
		this.templateParameter = templateParameter;
		this.templateStatus = templateStatus;
		this.templateMode = templateMode;
		this.templateSubject = templateSubject;
		this.templateEmailTo = templateEmailTo;
		this.templateEmailCc = templateEmailCc;
		this.templateEmailBcc = templateEmailBcc;
		this.templateEmailFrom = templateEmailFrom;
	}


	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateDesc() {
		return templateDesc;
	}

	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}

	public String getTemplateCategory() {
		return templateCategory;
	}

	public void setTemplateCategory(String templateCategory) {
		this.templateCategory = templateCategory;
	}

	public byte[] getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(byte[] templateContent) {
		this.templateContent = templateContent;
	}

	public String getTemplateParameter() {
		return templateParameter;
	}

	public void setTemplateParameter(String templateParameter) {
		this.templateParameter = templateParameter;
	}

	public String getTemplateStatus() {
		return templateStatus;
	}

	public void setTemplateStatus(String templateStatus) {
		this.templateStatus = templateStatus;
	}

	public String getTemplateMode() {
		return templateMode;
	}

	public void setTemplateMode(String templateMode) {
		this.templateMode = templateMode;
	}

	public String getTemplateSubject() {
		return templateSubject;
	}

	public void setTemplateSubject(String templateSubject) {
		this.templateSubject = templateSubject;
	}

	public String getTemplateEmailTo() {
		return templateEmailTo;
	}

	public void setTemplateEmailTo(String templateEmailTo) {
		this.templateEmailTo = templateEmailTo;
	}

	public String getTemplateEmailCc() {
		return templateEmailCc;
	}

	public void setTemplateEmailCc(String templateEmailCc) {
		this.templateEmailCc = templateEmailCc;
	}

	public String getTemplateEmailBcc() {
		return templateEmailBcc;
	}

	public void setTemplateEmailBcc(String templateEmailBcc) {
		this.templateEmailBcc = templateEmailBcc;
	}

	public String getTemplateEmailFrom() {
		return templateEmailFrom;
	}

	public void setTemplateEmailFrom(String templateEmailFrom) {
		this.templateEmailFrom = templateEmailFrom;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}
	
}
