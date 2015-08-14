<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/layout/field"%>

<%@attribute name="field" required="true" type="com.saas.dynamic.view.entity.DataViewField"%>
<%@attribute name="fieldId" required="true"%>
<%@attribute name="fieldPath" required="false"%>
<%@attribute name="dataView" required="true" type="com.saas.dynamic.view.entity.DataView"%>
<%@attribute name="tenantId" required="false"%>
<%@attribute name="group" required="false" type="com.saas.dynamic.view.entity.DataViewGroup"%>
<%@attribute name="readonly" required="false" type="java.lang.Boolean"%>
<%@attribute name="updateable" required="false" type="java.lang.Boolean"%>
<%@attribute name="width" required="false"%>
<%@attribute name="dataName" required="false" %>
<%@attribute name="expression" required="false" %>


						<c:choose>
							<c:when test="${(not empty group and group.readonly) or 
											(not empty field.readOnly and field.readOnly) or 
											(not empty readonly and readonly) or
											(not empty updateable and not updateable)}">
								<c:choose>
									<c:when test="${field.type == 'Hidden'}">
										
									</c:when>
									<c:when test="${field.type == 'PickListMultiple'}">
										<field:display expression="${expression }" label="${field.label}" multiple="true"/>									
									</c:when>
									<c:when test="${field.type == 'DataObjectReference' or field.type == 'DataObjectStatusReference'
													or field.type == 'DataObjectTypeReference' or field.type == 'CategoryReference'
													or field.type == 'GroupReference' or field.type == 'RoleReference'
													or field.type == 'CodeValueReference'}">
										<field:display label="${field.label}" expression="${expression }" itemValue="name"></field:display>
									</c:when>
									<c:when test="${field.type == 'UserReference'}">
										<field:display label="${field.label}" expression="${expression}" itemValue="fullName"></field:display>
									</c:when>
									<c:when test="${field.type == 'File'}">
										<field:download label="${field.label}" path="${fieldId}" dataObject="${dataObject.id}" dataTableField="${field.dataTableField.id}"></field:download>
									</c:when>
									<c:otherwise>
										<field:display label="${field.label}" expression="${expression}"/>
									</c:otherwise>
								</c:choose>
							</c:when>
							
							<c:otherwise>
								<c:choose>
                        	<c:when test="${field.type == 'Number'}">
								<field:number path="${fieldPath}" styleId="${fieldId}" placeholder="${field.label}" label="${field.label}" helpText="${field.helpText}"
								onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
								dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'Percent'}">
								<field:percent path="${fieldPath}" styleId="${fieldId}" placeholder="${field.label}" label="${field.label}" helpText="${field.helpText}"
								onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
								dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'Currency'}">
								<field:currency path="${fieldPath}" styleId="${fieldId}" placeholder="${field.label}" label="${field.label}" helpText="${field.helpText}"
								onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
								dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'Date' }">
                       			<field:date styleId="${fieldId}" path="${fieldPath}" label="${field.label}" helpText="${field.helpText}" placeholder="${field.label}"
                       			onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'DateTime' }">
                       			<field:date time="true" styleId="${fieldId}" path="${fieldPath}" label="${field.label}" helpText="${field.helpText}" placeholder="${field.label}"
                       			onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'Email' }">
                       			<field:email path="${fieldPath}" label="${field.label}" helpText="${field.helpText}" styleId="${fieldId}" placeholder="${field.label}"
                       			onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'Radio' }">
                       			<field:radio items="${field.optionList}" itemLabel="value" itemValue="key" path="${fieldPath}" styleId="${fieldId}" helpText="${field.helpText}" label="${field.label}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'Picklist'}">
                       			<field:select items="${field.optionList}" itemLabel="value" itemValue="key" path="${fieldPath}" label="${field.label}" styleId="${fieldId}"
                       			onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'PickListMultiple' }">
                       			<field:select items="${field.optionList}" itemLabel="value" itemValue="key" path="${fieldPath}" multiple="true" styleId="${fieldId}" label="${field.label}"
                       			onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'URL' }">
                       			<field:url path="${fieldPath}" helpText="${field.helpText}" label="${field.label}" styleId="${fieldId}" placeholder="${field.label}"
                       			onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'Checkbox'}">
                       			<field:checkboxes items="${field.optionList}" itemLabel="value" itemValue="key" path="${fieldPath}" helpText="${field.helpText}" label="${field.label}" styleId="${fieldId}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'TextLong' or field.type == 'TextArea'}">
								<field:textarea path="${fieldPath}" helpText="${field.helpText}" label="${field.label}" styleId="${fieldId}" placeholder="${field.label}"
								onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
								dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'TextRich'}">
                       			<field:editor path="${fieldPath}" helpText="${field.helpText}" label="${field.label}" styleId="${fieldId}" 
                       			onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'DataObjectReference'}">
                       			<field:reference entityName="dataobject" path="${fieldPath}" label="${field.label}" styleId="${fieldId}"
                       			onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'UserReference'}"> 
                       			<field:reference entityName="user" path="${fieldPath}" label="${field.label}" styleId="${fieldId}"
                       			onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'GroupReference'}">
                       			<field:reference entityName="group" path="${fieldPath}" label="${field.label}" styleId="${fieldId}"
                       			onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'RoleReference'}">
                       			<field:reference entityName="role" path="${fieldPath}" label="${field.label}" styleId="${fieldId}"
                       			onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'PartnerReference'}">
                       			<field:reference entityName="partnership" path="${fieldPath}" label="${field.label}" styleId="${fieldId}"
                       			onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'DataObjectStatusReference'}">
                       			<field:reference entityName="dataobjectstatus" condition="${dataView.dataTable.id }" path="${fieldPath}" label="${field.label}" styleId="${fieldId}"  width="${width}"
                       			onblur="${field.onBlur}" onchange="javascript:changeDataObjectStatus();${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" clientSide="true"
                       			dataName="${dataName }" required="${field.required}"/>
                       		</c:when>
                       		<c:when test="${field.type == 'CategoryReference'}">
                       			<field:reference entityName="category" condition="${dataView.dataTable.id }" path="${fieldPath}" label="${field.label}" styleId="${fieldId}" width="${width}"
                       			onblur="${field.onBlur}" onchange="javascript:categoryOnChange(this);${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" clientSide="true"
                       			dataName="${dataName }" required="${field.required}"/>
                       			<form:hidden path="${fieldPath}" id="reference_category" disabled="true" />
                       		</c:when>
                       		<c:when test="${field.type == 'DataObjectTypeReference'}">
                       			<field:reference entityName="dataobjecttype" condition="${dataView.dataTable.id }" path="${fieldPath}" label="${field.label}" styleId="${fieldId}" width="${width}"
                       			onblur="${field.onBlur}" onchange="javascript:changeDataObjectType();${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" clientSide="true"
                       			dataName="${dataName }" categorySupport="true" required="${field.required}"/>
                       		</c:when>
                       		 <c:when test="${field.type == 'CodeValueReference'}">
                       			 <c:choose>
	                       		 	<c:when test="${not empty tenantId}">
	                       		 		<c:set var="condition" value="${field.dataTableField.referenceParameter}|${tenantId}"></c:set>
	                       		 	</c:when>
	                       		 	<c:otherwise>
	                       		 		<c:set var="condition" value="${field.dataTableField.referenceParameter}"></c:set>
	                       		 	</c:otherwise>
                       		 	</c:choose>
					            <field:reference entityName="codevalue" condition="${condition}" path="${fieldPath}" label="${field.label}" styleId="${fieldId}" width="${width}"
					            onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" clientSide="true"
					            dataName="${dataName }" required="${field.required}"/>
					        </c:when>
					        <c:when test="${field.type == 'File'}">
					        	<c:if test="${empty dataObject.id}">
                       				<field:upload styleId="${fieldId}" path="${fieldPath}" operation="create" dataName="${dataName }" label="${field.label}" fileTypes="${field.uploadFileTypes}" maxFileSize="${field.uploadSize}" maxUpload="${field.uploadLimit}" 
                       				dataTableField="${field.dataTableField.id}" width="${width}" required="${field.required}" dataObject="${dataObject.id}"/>
                       			</c:if>
                       			<c:if test="${!empty dataObject.id}">
                       				<field:upload styleId="${fieldId}" path="${fieldPath}" operation="update" dataName="${dataName }" label="${field.label}" fileTypes="${field.uploadFileTypes}" maxFileSize="${field.uploadSize}" maxUpload="${field.uploadLimit}" 
                       				 dataTableField="${field.dataTableField.id}" width="${width}" required="${field.required}" dataObject="${dataObject.id}" validResult="${sucess}"/>
                       			</c:if>
                       		</c:when>
                       		<c:when test="${field.type == 'Hidden'}">
                       			<field:hidden path="${fieldPath}" styleId="${fieldId}" dataName="${dataName}"/>
                       		</c:when>
                       		<c:otherwise>
                                <field:input path="${fieldPath}" styleId="${fieldId}" helpText="${field.helpText}" label="${field.label}" placeholder="${field.label}"
                                onblur="${field.onBlur}" onchange="${field.onChange}" onclick="${field.onClick }" onfocus="${field.onFocus}" width="${width}"
                                dataName="${dataName }" required="${field.required}"/>
                       		</c:otherwise>
                       	</c:choose>
							</c:otherwise>
						
						</c:choose>
						
	
						