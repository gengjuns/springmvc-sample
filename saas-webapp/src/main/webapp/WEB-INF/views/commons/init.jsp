<%@page contentType="application/x-javascript; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authorize access="isAuthenticated()" var="isLogin"/>
var SaaSApp = {
    contextPath : '${pageContext.request.contextPath}',
    isLogin : ${isLogin},
    timeoutInterval	: '${pageContext.request.session.maxInactiveInterval}',
    timeoutLogout : <spring:eval expression="@webConfigInterceptor.sessionLogoutTimeout"/>
};
