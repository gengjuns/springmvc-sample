<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@attribute name="modelAttribute" required="true" %>
<%@attribute name="titleCode" required="true" %>
<%@attribute name="styleId" required="false" %>
<%@attribute name="multipart" required="false" %>
<%@attribute name="cssClass" required="false" %>
<%@attribute name="btnCancelId" required="false" %>

  
		<c:set var="enctype" value="application/x-www-form-urlencoded"/>
		<c:if test="${multipart}">
		  <c:set var="enctype" value="multipart/form-data"/>
		</c:if>
		<c:set var="cssStyle" value="form-horizontal"/>
		<c:if test="${empty cssClass}">
			<c:set var="cssStyle" value="form-horizontal  well well-small"/>
		</c:if>
		<c:set var="btnId" value="cancel"/>
		<c:if test="${!empty btnCancelId}">
			<c:set var="btnId" value="${btnCancelId}"/>
		</c:if>
		
      <form:form method="POST" modelAttribute="${modelAttribute}" enctype="${enctype}" cssClass="${cssStyle}" id="form">
          <spring:bind path="id">
              <input type="hidden" name="${modelAttribute}" value="${status.value}">
          </spring:bind>
          <form:hidden id="version" path="version" />
        <fieldset>
    	<legend><spring:message code="title_${fn:toLowerCase(titleCode)}"/></legend>
        <jsp:doBody />
        <layout:requiredmsg/>
        <div class="form-actions">
           <%--  <sec:authorize access="hasAnyRole('PERM_PORTAL_ALL','PERM_${fn:toUpperCase(modelAttribute)}_WRITE')"> --%>
                <layout:submit text="Save" />
         <%--    </sec:authorize> --%>
            <layout:submit text="Cancel" styleId="${btnId}" cssClass="btn"/>
        </div>
        </fieldset>
      </form:form>
      
