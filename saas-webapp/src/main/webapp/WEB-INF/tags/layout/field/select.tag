<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<%@attribute name="path" required="true" %>
<%@attribute name="width" required="false" %>
<%@attribute name="labelCode" required="false" %>
<%@attribute name="styleId" required="false" %>
<%@attribute name="placeholder" required="false" %>
<%@attribute name="items" required="true" type="java.util.Collection"%>
<%@attribute name="itemLabel" required="false" %>
<%@attribute name="itemValue" required="false" %>
<%@attribute name="multiple" required="false" type="java.lang.Boolean"%>
<%@attribute name="label" required="false" %>
<%@attribute name="dataName" required="false" %>

<%@attribute name="onchange" required="false" %>
<%@attribute name="onblur" required="false" %>
<%@attribute name="onclick" required="false" %>
<%@attribute name="onfocus" required="false" %>
<%@attribute name="required" required="false" %>


<c:if test="${empty multiple }">
	<c:set var="multiple" value="false"/>
</c:if>

<c:set var="defaultValue">
	<spring:message code="option_please_select"/>
</c:set>

<layout:column width="${width}" path="${path}" labelCode="${labelCode}" label="${label}" required="${required}">
    <form:select data-name="${dataName }" path="${path }" multiple="${multiple}" id="${styleId }" onblur="${onblur }" onchange="${onchange }" 
    onclick="${onclick }" onfocus="${onfocus }" data-type="select" data-placeholder="${defaultValue }">
		<c:if test="${not multiple}">
			<form:option value="">- Select -</form:option>
		</c:if>
		<c:choose>
			<c:when test="${not empty itemLabel and not empty itemValue }">
				<form:options items="${items }" itemLabel="${itemLabel}" itemValue="${itemValue}"/>
			</c:when>
			<c:otherwise>
				<form:options items="${items }"/>
			</c:otherwise>
		</c:choose>
	</form:select>
</layout:column>