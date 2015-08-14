<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<%@attribute name="path" required="true" %>
<%@attribute name="width" required="false" %>
<%@attribute name="labelCode" required="false" %>
<%@attribute name="styleId" required="false" %>
<%@attribute name="placeholder" required="false" %>
<%@attribute name="label" required="false" %>
<%@attribute name="helpText" required="false" %>
<%@attribute name="onchange" required="false" %>
<%@attribute name="onblur" required="false" %>
<%@attribute name="onclick" required="false" %>
<%@attribute name="onfocus" required="false" %>
<%@attribute name="dataName" required="false" %>
<%@attribute name="required" required="false" %>


<layout:column width="${width }" path="${path }" labelCode="${labelCode}" label="${label}" helpText="${helpText}" required="${required}">
	<div class="input-append">
	<form:input data-name="${dataName}" path="${path }" id="${styleId }" placeholder="${placeholder }" onblur="${onblur }" onchange="${onchange }" onclick="${onclick }" onfocus="${onfocus }"/><span class="add-on">.00</span>
     </div>
</layout:column>


