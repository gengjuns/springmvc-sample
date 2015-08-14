<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@attribute name="styleId" required="true"%>
<%@attribute name="url" required="true"%>
<%@attribute name="type"%>

<c:if test="${empty type}">
	<c:set var="type" value="line"></c:set>
</c:if>
<div id="${styleId}" style="width: 800px; height: 400px; margin: 0 auto" data-url="${url}" class="chart"></div>
