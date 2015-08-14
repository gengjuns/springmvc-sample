<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
  
<%@attribute name="titleCode" required="true"%>
<%@attribute name="modelAttribute" required="true" %>
<%@attribute name="update" required="false" type="java.lang.Boolean"%>

	
		<c:if test="${empty update}">
		  <c:set var="update" value="true"/>
		</c:if>



      <form:form method="POST" modelAttribute="${modelAttribute}" cssClass="form-horizontal well well-small" id="form">
        <fieldset>
    	<legend><spring:message code="title_${fn:toLowerCase(titleCode)}"/></legend>
        <jsp:doBody />
        <div class="form-actions">
            <c:if test="${update}">
                <layout:submit text="Edit" styleId="edit" entityId="${id}" cssClass="btn btn-primary"/>
            </c:if>
            <layout:submit text="Cancel" styleId="cancel" cssClass="btn"/>
        </div>

        </fieldset>
        
      </form:form>
