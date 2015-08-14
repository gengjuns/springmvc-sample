<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>


<%@attribute name="path" required="true" %>
<%@attribute name="dataTableField" required="true" %>
<%@attribute name="dataObject" required="true" %>
<%@attribute name="width" required="false" %>
<%@attribute name="labelCode" required="false" %>
<%@attribute name="helpText" required="false" %>
<%@attribute name="label" required="false" %>
<%@attribute name="required" required="false" %>


<layout:column width="${width }" path="${path }" labelCode="${labelCode}" label="${label}" helpText="${helpText }" required="${required}">
	<ul class="divider-vertical k-upload-files k-reset" path="${path}" id="${dataTableField}" data-type="download" dataTableField="${dataTableField}" dataObject="${dataObject}" items="${status.value}">
	</ul>
</layout:column>			

