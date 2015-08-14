<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<%@attribute name="path" required="true" %>
<%@attribute name="width" required="false" %>
<%@attribute name="labelCode" required="false" %>
<%@attribute name="styleId" required="false" %>
<%@attribute name="placeholder" required="false" %>
<%@attribute name="showPassword" required="false" %>
<%@attribute name="dataName" required="false" %>
<%@attribute name="required" required="false" %>
<%@attribute name="maxlength" required="false" %>

<c:if test="${empty showPassword }">
	<c:set var="showPassword" value="false"/>
</c:if>


<layout:column width="${width }" path="${path }" labelCode="${labelCode}" required="${required}">
	<form:password data-name="${dataName }" path="${path }" id="${styleId }" placeholder="${placeholder }" showPassword="${showPassword }" maxlength="${maxlength}"/>
</layout:column>