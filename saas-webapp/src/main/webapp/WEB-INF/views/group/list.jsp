<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<layout:page>
<layout:list titleCode="group" modelAttribute="group">
{ field: "name", title: "<spring:message code='label_group_name' />"},
{ field: "description", title: "<spring:message code='label_group_description' />"},
{ field: "updatedDate", title: "<spring:message code='label_group_lastupdated_date' />",format: "{0:dd/MM/yyyy HH:mm}",filterable:{ ui: "datetimepicker"}}
</layout:list>
</layout:page>