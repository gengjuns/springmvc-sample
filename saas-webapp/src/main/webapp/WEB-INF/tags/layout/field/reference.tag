<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="path" required="true"%>
<%@attribute name="entityName" required="true"%>
<%@attribute name="width" required="false"%>
<%@attribute name="labelCode" required="false"%>
<%@attribute name="styleId" required="false"%>
<%@attribute name="placeholder" required="false"%>
<%@attribute name="label" required="false"%>
<%@attribute name="helpText" required="false"%>
<%@attribute name="condition" required="false" %>
<%@attribute name="onchange" required="false" %>
<%@attribute name="onblur" required="false" %>
<%@attribute name="onclick" required="false" %>
<%@attribute name="onfocus" required="false" %>
<%@attribute name="clientSide" required="false" type="java.lang.Boolean"%>
<%@attribute name="dataName" required="false" %>
<%@attribute name="categorySupport" required="false" %>
<%@attribute name="required" required="false" %>


<c:set var="defaultValue">
	<spring:message code="option_please_select"/>
</c:set>

<c:if test="${empty clientSide}">
	<c:set var="clientSide" value="${false}"/>
</c:if>

<layout:column width="${width}" path="${path}" labelCode="${labelCode}" helpText="${helpText }" label="${label }" required="${required}">
	<form:input data-name="${dataName }" path="${path }" id="${styleId }" placeholder="${placeholder }" onblur="${onblur }" onchange="${onchange }" onclick="${onclick }" 
	onfocus="${onfocus }" data-type="reference" entityName="${entityName}" condition="${condition}" client="${clientSide}" data-placeholder="${defaultValue }" categorySupport="${categorySupport }"/>
</layout:column>