<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<%@attribute name="path" required="true" %>
<%@attribute name="width" required="false" %>
<%@attribute name="labelCode" required="false" %>
<%@attribute name="items" required="true" type="java.util.Collection"%>
<%@attribute name="itemLabel" required="false" %>
<%@attribute name="itemValue" required="false" %>
<%@attribute name="styleId" required="false" %>
<%@attribute name="label" required="false" %>
<%@attribute name="helpText" required="false" %>
<%@attribute name="dataName" required="false" %>
<%@attribute name="required" required="false" %>


<layout:column width="${width }" path="${path }" labelCode="${labelCode}" label="${label }" helpText="${helpText}" required="${required}">
	<div class="checkbox">
	<c:choose>
		<c:when test="${not empty itemLabel and not empty itemValue}">
			<form:checkboxes data-name="${dataName }" items="${items}" path="${path }" itemLabel="${itemLabel }" itemValue="${itemValue }" id="${styleId }"/>
		</c:when>
		<c:otherwise>
			<form:checkboxes data-name="${dataName }" items="${items}" path="${path}" id="${styleId }"/>
		</c:otherwise>
	</c:choose>
	
	</div>
</layout:column>