<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="aboutModal" class="modal hide fade" tabindex="-1">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h3><spring:message code="application_name"/></h3>
    </div>
    <div class="modal-body">
        <div class="well well-large">
            <div class="row">
                <div class="span2 brand">
                    <img class="logo" src="${pageContext.request.contextPath}/resources/site/img/logo.png">
                </div>
                <div class="span3">
                    <h4>Version:&nbsp;<spring:eval expression="@webConfigInterceptor.versionNo" var="versionNo"/><c:out value="${versionNo}"/></h4>
                    <br/>
                    <p class="muted credit">&copy;2015 <spring:message code="application_name"/> SaaS PTE LTD.</p>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn btn-primary" data-dismiss="modal"><spring:message code="button_close"/></a>
    </div>
</div>