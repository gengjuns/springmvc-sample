<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<layout:page>
    <%-- <div id="login-modal" class="modal login-modal">
        <form class="form-horizontal" action="${pageContext.request.contextPath}/dologin" method="post">
        <div class="modal-header">
            <h3><spring:message code="title_login"/></h3>
        </div>
        <div class="modal-body">
            <c:if test="${error}">
                <div class="alert alert-error"><spring:message code="msg_login_error"/></div>
            </c:if>
                <div class="control-group">
                    <label class="control-label" for="username_"><spring:message code="label_login_username"/></label>

                    <div class="input-prepend">
                        <span class="add-on"><i class="icon-envelope"></i></span>
                        <input type="text" id="username_" name="username_" placeholder="<spring:message code="msg_login_username_help"/>" value="${username_}" tabindex="1">
                    </div>
                </div>
                
                <div class="control-group">
                    <label class="control-label" for="password_"><spring:message code="label_login_password"/></label>

                    <div class="input-prepend">
                        <span class="add-on"><i class="icon-key"></i></span>
                            <input type="password" id="password_" name="password_" placeholder="<spring:message code="msg_login_password_help"/>" tabindex="2">
                    </div>
                </div>
                <div class="control-group">
					<div class="controls">
						<div class="controls" style="margin-left: 0px;">												
							<label class="checkbox">
                                      <input type="checkbox" name="rememberme_" value="1" tabindex="4"> <spring:message code="label_login_rememberme"/></label>
						</div>
					</div>
				</div>
                
	        <div class="form-actions1">
				<div class="controls1" style="margin-left:70px;width: 290px;">
					<button class="btn btn-primary btn-large btn-block" type="button" id="btnLogin"><i class="icon-lock"></i> Sign in</button>
				</div>
				<br/>
				<div style="margin-left:70px;width: 150px;">
                    <label>
                        <a href="${pageContext.request.contextPath}/forgetpassword" tabindex="5"><spring:message code="label_login_forgetpassword"/></a>
                    </label>
                </div>
			</div>
			
            <script>
                $(document).ready(function () {
                    $('#username_').focus();
                });
                
                $("#btnLogin").click(function(){
               		var loginForm = document.forms[0];
       				loginForm.submit();                
                });
            </script>
        </div>
        </form>
    </div> --%>
    
    <div id="login-modal" class="modal login-modal">
        <form class="form-horizontal" action="${pageContext.request.contextPath}/dologin" method="post">
        <div class="modal-header">
            <h3><spring:message code="title_login"/></h3>
        </div>
        <div class="modal-body">
            <c:if test="${error}">
                <div class="alert alert-error">
                	<c:set var="loginError">
                		${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
					</c:set>
                	<div>
	                	<c:choose>
		                	<c:when test="${loginError == 'notFound'}"><spring:message code="msg_login_username_notfound"/></c:when>
		                	<c:when test="${loginError == 'new'}"><spring:message code="msg_login_account_new"/></c:when>
		                	<c:when test="${loginError == 'locked'}"><spring:message code="msg_login_account_locked"/></c:when>
		                	<c:otherwise><spring:message code="msg_login_error"/></c:otherwise>
	                	</c:choose>
                	</div>
                </div>
            </c:if>
			<div id="login-form" style="margin-bottom: 0px;">			
				<div class="control-group">
					<div class="controls input-prepend" style="margin-left: 80px;">	
						<span class="add-on big-input"><i class="icon-envelope"></i></span>											
						<input class="login-input big-input" placeholder="<spring:message code="msg_login_username_help"/>" id="username_" name="username_" type="text" maxlength="100" value="${username_}"  tabindex="1"/>
						<label class="error valid" for="username_" generated="true"></label>
					</div>
				</div>
				<div class="control-group">
					<div class="controls input-prepend" style="margin-left: 80px;">
						<span class="add-on big-input"><i class="icon-key"></i></span>
						<input class="login-input big-input" placeholder="<spring:message code="msg_login_password_help"/>" id="password_" name="password_" type="password" maxlength="30" autocomplete="off" value=""  tabindex="2"/>
						<label class="error valid" for="password_" generated="true"></label>
					</div>
				</div>
				
				<div class="control-group">
					<div class="controls" style="margin-left: 80px;">
						<div class="controls" style="margin-left: 0px;">												
							<label class="checkbox"><input type="checkbox" name="rememberme_" id="rememberme_" tabindex="3"/><spring:message code="label_login_rememberme"/></label>
						</div>
					</div>
				</div>
				<div class="form-actions1">
					<div class="controls1" style="width: 220px;margin-left: 78px;">
						<button class="btn btn-primary btn-large btn-block" type="submit" onclick="javascript:submitLogin();" tabindex="4"><i class="icon-lock"></i> Sign in</button>
					</div>
				</div>								
				<br />
				<div style="margin-left: 80px;">
					<a href="${pageContext.request.contextPath}/forgetpassword" tabindex="5"><spring:message code="label_login_forgetpassword"/></a>
				</div>	
			</div>
        </div>
        </form>
    </div>
    <script type="text/javascript">
	    $(document).ready(function () {
	        $('#username_').focus();
	    });
	    
    </script>
    
</layout:page>