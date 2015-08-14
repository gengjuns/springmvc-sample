<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/layout/field"%>

<%@attribute name="path" required="true" %>
<%@attribute name="styleId" required="true" %>
<%@attribute name="dataTableField" required="true" %>
<%@attribute name="dataObject" required="true" %>
<%@attribute name="operation" required="true" %>   <!--update / create -->
<%@attribute name="width" required="false" %>
<%@attribute name="labelCode" required="false" %>
<%@attribute name="helpText" required="false" %>
<%@attribute name="label" required="false" %>
<%@attribute name="fileTypes" required="false"%>    	<!-- fileTypes is String,format like: .jsp.xml.doc -->
<%@attribute name="maxFileSize" required="false" %> 	<!-- maxFileSize: the unit is byte. -->
<%@attribute name="maxUpload" required="false" %>
<%@attribute name="dataName" required="false" %>
<%@attribute name="required" required="false" %>
<%@attribute name="validResult" required="false" %>


<c:if test="${empty styleId }">
	<c:set var="styleId" value="files"/>
</c:if>


<layout:column width="${width }" path="${path }" labelCode="${labelCode}" label="${label}" helpText="${helpText }" required="${required}">
			
			<div class="divider-vertical">
				<form:input data-name="${dataName }" path="${path }" id="${styleId }" type="file" data-type="upload" data_file_type="${fileTypes}" data_max_filesize="${maxFileSize}"
					data_max_upload="${maxUpload}" dataTableField="${dataTableField}"/>
				<c:if test="${operation == 'update'}">
					<ul class="divider-vertical k-upload-files k-reset" path="${path}" id="${dataTableField}" data-type="download" dataObject="${dataObject}" dataTableField="${dataTableField}" 
					items="${status.value}" operation="${operation}" validResult="${validResult}">
					</ul>
					
				</c:if>
				<input id="upload_size_${dataTableField}" type="hidden" value="0" />
			</div>
			
</layout:column>
