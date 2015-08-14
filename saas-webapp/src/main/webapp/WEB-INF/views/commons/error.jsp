<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@page isErrorPage="true" language="java" import="java.util.*"%>

<layout:page>

	<div class="well well-small">
		<legend>Error Type : ${pageContext.errorData.statusCode}</legend>
		<div>URL : ${pageContext.errorData.requestURI}</div>
		<div>Message : ${exception}</div>
	</div>
</layout:page>