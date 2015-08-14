<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<layout:page>
	<form:form method="POST" modelAttribute="user" enctype="multipart/form-data" cssClass="form-horizontal well-small" id="form" action="resendotp">
	<fieldset>
		<legend>
			<spring:message code="title_enrollment_forgotpassword"
				htmlEscape="false" />
		</legend>
		<layout:row>
			<spring:message code="success_enrollment_forgotpassword_tips"/>
		</layout:row>
		<div class="form-actions">
            <layout:submit text="Resend Password" />
        </div>
	</fieldset>
	</form:form>
</layout:page>
