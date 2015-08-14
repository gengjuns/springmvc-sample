<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@attribute name="path" required="true" %>
<%@attribute name="styleId" required="false" %>
<%@attribute name="dataName" required="false" %>



<form:hidden path="${path}" id="${styleId }" data-name="${dataName}" />