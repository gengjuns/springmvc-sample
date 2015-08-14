<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="text" required="true" %>
<%@attribute name="styleId" required="false" %>
<%@attribute name="cssClass" required="false" %>
<%@attribute name="entityId" required="false" %>
<%@attribute name="type" required="false" %>

<c:if test="${empty styleId }">
	<c:set var="styleId" value="Submit"/>
</c:if>

<c:if test="${empty cssClass}">
	<c:set var="cssClass" value="btn-primary"/>
</c:if>

<c:if test="${empty type}">
	<c:set var="type" value="button"/>
</c:if>


<button type="${type}" class="btn ${cssClass}" id="${styleId}" entity_id="${entityId}" >${text}</button>

