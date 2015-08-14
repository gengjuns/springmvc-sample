<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<%@attribute name="property" required="true"%>
<%@attribute name="label" required="true"%>

<c:choose>
	<c:when test="${empty columnProperties and empty columnLabels}">
		<c:set var="columnProperties" value="${property}" scope="request"/>
		<c:set var="columnLabels" value="${label}" scope="request"/>
	</c:when>
	<c:otherwise>
		<c:set var="columnProperties" value="${columnProperties}&#9999;${property}" scope="request"/>
		<c:set var="columnLabels" value="${columnLabels}&#9999;${label}" scope="request"/>
	</c:otherwise>
</c:choose>

