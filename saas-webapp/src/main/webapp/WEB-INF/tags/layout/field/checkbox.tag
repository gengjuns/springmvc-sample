<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<%@attribute name="path" required="true" %>
<%@attribute name="width" required="false" %>
<%@attribute name="labelCode" required="false" %>
<%@attribute name="styleId" required="false" %>
<%@attribute name="label" required="false" %>
<%@attribute name="helpText" required="false" %>
<%@attribute name="dataName" required="false" %>
<%@attribute name="required" required="false" %>

<layout:column width="${width }" path="${path }" labelCode="${labelCode}" helpText="${helpText }" label="${label }" required="${required}">
	<div class="checkbox">
	<form:checkbox path="${path}" value="${true}" id="${styleId}" data-name="${dataName}"/>
	</div>
</layout:column>