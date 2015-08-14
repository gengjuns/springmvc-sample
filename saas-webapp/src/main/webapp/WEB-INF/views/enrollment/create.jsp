<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<layout:page>
	<form:form method="POST" modelAttribute="user" enctype="multipart/form-data" cssClass="form-horizontal well-small" id="form" action="create">
	<fieldset>
		<legend>
			<spring:message code="title_enrollment_create"
				htmlEscape="false" />
		</legend>
		<layout:row>
			<field:input  path="firstName" labelCode="user_firstname" placeholder="First Name" required="true" />
		</layout:row>
		<layout:row>
			<field:input  path="lastName" labelCode="user_lastname" placeholder="Last Name" required="true"/>
		</layout:row>
		<layout:row>
			<field:input  path="username" labelCode="user_name" placeholder="Login Name" required="true"/>
		</layout:row>
		<layout:row>
			<field:password  path="password" labelCode="user_password" placeholder="Password" required="true"/>
		</layout:row>
		<layout:row>
			<field:password  path="passwordConfirm" labelCode="user_password_confirmed" placeholder="Password" required="true"/>
		</layout:row>
		<layout:row>
			<field:input  path="domain" labelCode="user_domain" placeholder="Domain" required="true" />
		</layout:row>
		<layout:row>
			<field:input  path="email" labelCode="user_email" placeholder="Email" required="true"/>
		</layout:row>
		<layout:row>
			<field:input  path="mobileNumber" labelCode="user_mobilenumber" placeholder="Mobile Number" required="true"/>
		</layout:row>
		<%-- 
		<layout:row>
			<sec:authorize access="hasAnyRole('PERM_PORTAL_ALL', 'PERM_TENANT_ADMIN')">
				<field:select  path="tenants" multiple="true" items="${tenants}" itemLabel="name" itemValue="id"  labelCode="user_tenant" styleId="tenants"/>
			</sec:authorize>
		</layout:row>
		<layout:row>
			<field:select  path="roles" multiple="true" items="${roles}" itemLabel="name" itemValue="id"  labelCode="user_role" styleId="roles"/>
		</layout:row>
		<layout:row>
			<field:select  path="groups" multiple="true" items="${groups}" itemLabel="name" itemValue="id"  labelCode="user_group" styleId="groups"/>
		</layout:row>
		<layout:row>
			<field:textarea  path="recallQuestion" labelCode="user_recall_question" placeholder="Recall Question" required="false"/>
		</layout:row>
		<layout:row>
			<field:textarea  path="recallAnswer" labelCode="user_recall_answer" placeholder="Recall Answer" required="false"/>
		</layout:row> 
		--%>
		
		<layout:row>
			<field:textarea  path="question1" labelCode="user_question1" placeholder="Question 1" required="true"/>
		</layout:row>
		<layout:row>
			<field:textarea  path="answer1" labelCode="user_answer1" placeholder="Answer 1" required="true"/>
		</layout:row>
		<layout:row>
			<field:textarea  path="question2" labelCode="user_question2" placeholder="Question 2" required="true"/>
		</layout:row>
		<layout:row>
			<field:textarea  path="answer2" labelCode="user_answer2" placeholder="Answer 2" required="true"/>
		</layout:row>
		<layout:requiredmsg/>
		<div class="form-actions">
            <layout:submit text="Save" />
            <layout:submit text="Cancel" styleId="cancel" cssClass="btn" />
        </div>
	</fieldset>
</form:form>
</layout:page>




