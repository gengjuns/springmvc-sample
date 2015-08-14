package com.saas.core.entity.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AuditTrail implements Serializable{
	
	protected String id;
	
	protected Long revisionId;
	
	protected Date updatedDate;
	
	protected String updatedBy;
	
	protected String owner;
	
	protected String businessKey;
	
	protected String status;
	
	protected String type;
	
	protected String label;
	
	protected String value;

	protected List<AuditTrail> children;
	
	public AuditTrail (){
	
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(Long revisionId) {
		this.revisionId = revisionId;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public List<AuditTrail> getChildren() {
		if (null == children) {
			children = new ArrayList<AuditTrail>();
		}
		return children;
	}


	public void setChildren(List<AuditTrail> children) {
		this.children = children;
	}
	
	public AuditTrail addChild(AuditTrail child){
		if (!getChildren().contains(child)) {
			getChildren().add(child);
		}
		return child;
	}
	

}
