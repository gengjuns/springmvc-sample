<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<%@attribute name="path" required="true" %>
<%@attribute name="width" required="false" %>
<%@attribute name="labelCode" required="false" %>
<%@attribute name="items" required="true" type="java.util.Collection"%>
<%@attribute name="itemLabel" required="false" %>
<%@attribute name="itemValue" required="false" %>
<%@attribute name="styleId" required="false" %>
<%@attribute name="helpText" required="false" %>
<%@attribute name="label" required="false" %>
<%@attribute name="dataName" required="false" %>
<%@attribute name="required" required="false" %>



<layout:column width="${width }" path="${path}" labelCode="${labelCode}" label="${label }" helpText="${helpText }" required="${required}">
	<div class="radio">
	<form:radiobuttons data-name="${dataName }" path="${path}" items="${items}" id="${styleId }" itemLabel="${itemLabel}" itemValue="${itemValue}"/>
	</div>
</layout:column>


