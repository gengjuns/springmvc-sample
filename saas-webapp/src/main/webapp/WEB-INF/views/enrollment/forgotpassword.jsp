<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<layout:page>
	<form:form method="POST" modelAttribute="user" enctype="multipart/form-data" cssClass="form-horizontal well-small" id="form" action="forgotpassword">
	<fieldset>
		<legend>
			<spring:message code="title_enrollment_forgotpassword"
				htmlEscape="false" />
		</legend>
		<layout:row>
			<spring:message code="label_enrollment_forgotpassword_tips"/>
		</layout:row>
		<br>
		<layout:row>
			<field:input path="domain" labelCode="enrollment_forgotpassword_domain" placeholder="Domain" required="true" />
		</layout:row>
		<layout:row>
			<field:input path="username" labelCode="enrollment_forgotpassword_account" placeholder="Account" required="true"/>
		</layout:row>
		<layout:row>
			<img src="checkcode" onclick="changeValidateCode(this);" style="cursor:hand; width: 220px;height: 30px;padding-left: 180px"/>
		</layout:row>
		<layout:row>
			<field:input path="checkCode" labelCode="enrollment_forgotpassword_checkcode" placeholder="Enter the text in this image" required="true"/>
		</layout:row>
		<layout:requiredmsg/>
		<div class="form-actions">
            <layout:submit text="Retrieve" />
        </div>
	</fieldset>
	</form:form>
	<script type="text/javascript">
		function changeValidateCode(obj)
		{
			var timeNow = new Date().getTime();
			obj.src="checkcode?time="+timeNow;
		}
	</script>
</layout:page>
