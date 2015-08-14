<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<%@attribute name="path" required="true" %>
<%@attribute name="width" required="false" %>
<%@attribute name="labelCode" required="false" %>
<%@attribute name="styleId" required="true" %>
<%@attribute name="time" required="false" type="java.lang.Boolean" %>
<%@attribute name="label" required="false" %>
<%@attribute name="helpText" required="false" %>
<%@attribute name="placeholder" required="false" %>
<%@attribute name="onchange" required="false" %>
<%@attribute name="onblur" required="false" %>
<%@attribute name="onclick" required="false" %>
<%@attribute name="onfocus" required="false" %>
<%@attribute name="dataName" required="false" %>
<%@attribute name="required" required="false" %>

<c:if test="${empty time }">
	<c:set var="time" value="${false}"/>
</c:if>

<c:choose>
	<c:when test="${time}">
		<c:set var="data_type" value="datetime"/>
	</c:when>
	<c:otherwise>
		<c:set var="data_type" value="date"/>
	</c:otherwise>
</c:choose>


<layout:column width="${width }" path="${path }" labelCode="${labelCode}" label="${label}" helpText="${helpText }" required="${required}">
	<div>
    	<form:input data-name="${dataName }" path="${path }" id="${styleId }" cssClass="${time ? 'time':'date'}" placeholder="${placeholder }" onblur="${onblur }" onchange="${onchange }" onclick="${onclick }" onfocus="${onfocus }" data-type="${data_type}"/>
    </div>
    
</layout:column>