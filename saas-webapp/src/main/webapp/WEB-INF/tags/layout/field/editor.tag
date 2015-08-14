<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<%@attribute name="path" required="true" %>
<%@attribute name="valuePath" required="false" %>
<%@attribute name="width" required="false" %>
<%@attribute name="labelCode" required="false" %>
<%@attribute name="styleId" required="false" %>
<%@attribute name="rows" required="false" %>
<%@attribute name="display" required="false" %>
<%@attribute name="label" required="false" %>
<%@attribute name="helpText" required="false" %>
<%@attribute name="onchange" required="false" %>
<%@attribute name="onblur" required="false" %>
<%@attribute name="onclick" required="false" %>
<%@attribute name="onfocus" required="false" %>
<%@attribute name="dataName" required="false" %>
<%@attribute name="required" required="false" %>


<c:set var="var_row" value="5"/>
<c:if test="${not empty rows }">
<c:set var="var_row" value="${rows}"/>
</c:if>

<c:choose>
	<c:when test="${display}">
		<c:set value="editor readonly" var="data_type"/>
	</c:when>
	<c:otherwise>
		<c:set value="editor" var="data_type"/>
	</c:otherwise>
</c:choose>

<layout:column path="${valuePath }" labelCode="${labelCode}" width="${width }" label="${label }" helpText="${helpText }" required="${required}">
	<div class="k-content">
		<textarea data-name="${dataName }" class="editorTextarea" name="${path}" id="${styleId}" rows="${var_row}" style="width:100%;height:380px" 
		onblur="${onblur }" onchange="${onchange }" onclick="${onclick }" onfocus="${onfocus }" data-type="${data_type}">${status.value}</textarea>
	</div>
</layout:column>
