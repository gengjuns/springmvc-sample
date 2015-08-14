<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@attribute name="styleId" required="true"%>


<div id="${styleId}" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="sessionModal" aria-hidden="true"> 
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3 id="sessionModal"><spring:message code="title_session_timeout"/></h3>
			</div>
			<div class="modal-body">
				<spring:eval expression="@webConfigInterceptor.sessionLogoutTimeout" var="min"/>
				<p><spring:message code="msg_session_timeout_notification" arguments='${min }'/></p>
			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true"><spring:message code="button_cancel"/></button>
				<button class="btn btn-primary" onclick="refreshSession();"><spring:message code="button_ok"/></button>
			</div>
		</div>
		
		<div id="${styleId}_timeout" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="timeoutModal" aria-hidden="true"> 
			<div class="modal-header">
				<h3 id="timeoutModal"><spring:message code="title_session_timeout"/></h3>
			</div>
			<div class="modal-body">
				<p><spring:message code="msg_session_timeout"/></p>
			</div>
			<div class="modal-footer">
				<a class="btn btn-primary" href="${pageContext.request.contextPath}/logout"><spring:message code="button_ok"/></a>
			</div>
		</div>
		
		<script>
		$(document).ready(function(){
				sessionReminder();
			});
		
		$(document).ajaxSuccess(function(){
			sessionReminder();
		});
		</script>
