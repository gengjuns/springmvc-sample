<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<layout:page>
	<form:form method="POST" modelAttribute="user" enctype="multipart/form-data" cssClass="form-horizontal well-small" id="form" action="challenge">
	<fieldset>
		<legend>
			<spring:message code="title_enrollment_forgotpassword"
				htmlEscape="false" />
		</legend>
		<layout:row>
			<field:display labelCode="enrollment_forgotpassword_domain" expression="user.domain"/>
		</layout:row>
		<layout:row>
			<field:display labelCode="enrollment_forgotpassword_account" expression="user.username"/>
		</layout:row>
		<layout:row>
			<field:display labelCode="user_question1" expression="user.question1"/>
		</layout:row>
		<layout:row>
			<field:textarea  path="answerTmp1" labelCode="user_answer1" placeholder="Answer 1" required="true"/>
		</layout:row>
		<layout:row>
			<field:display labelCode="question2" expression="user.question2"/>
		</layout:row>
		<layout:row>
			<field:textarea  path="answerTmp2" labelCode="user_answer2" placeholder="Answer 2" required="true"/>
		</layout:row>
		<layout:row>
			<field:input path="securityCodeTmp" labelCode="enrollment_challenge_securitycode" placeholder="Security Code"></field:input>
		</layout:row>
		<layout:requiredmsg/>
		<div class="form-actions">
            <layout:submit text="Verify" />
        </div>
	</fieldset>
	</form:form>
</layout:page>
