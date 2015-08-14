<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="application_name"/></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <spring:eval expression="T(com.saas.Constants).BOOTSTRAP_THEME_NAME" var="themeName"/>
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/${requestScope[themeName]}/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/font-awesome.min.css" rel="stylesheet" media="screen">
    <!--[if IE 7]>
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/font-awesome-ie7.min.css" rel="stylesheet" media="screen">
    <![endif]-->
    <link href="${pageContext.request.contextPath}/resources/kendoui/styles/kendo.common.min.css" rel="stylesheet">
    <spring:eval expression="T(com.saas.Constants).KENDO_THEME_NAME" var="themeName"/>
    <link href="${pageContext.request.contextPath}/resources/kendoui/styles/kendo.${requestScope[themeName]}.min.css" rel="stylesheet" media="screen">
    <%-- <link href="${pageContext.request.contextPath}/resources/select2/css/select2.css" rel="stylesheet" media="screen"> --%>
    <link href="${pageContext.request.contextPath}/resources/site/css/site.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath}/resources/site/css/styleportal.css" rel="stylesheet" media="screen">
    <%-- <link href="${pageContext.request.contextPath}/resources/datatable/css/jquery.dataTables.css" rel="stylesheet" media="screen"> --%>
    <link href="${pageContext.request.contextPath}/resources/datatable/css/jquery.dataTables.bootstrap.css" rel="stylesheet" media="screen">
    <spring:eval expression="@webConfigInterceptor.uiResponsiveEnabled" var="uiResponsiveEnabled"/>
    <c:if test="${uiResponsiveEnabled}">
    <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
    </c:if>

    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/resources/commons/js/html5shiv-printshiv.js"></script>
    <!--[if IE 7]>
    <script src="${pageContext.request.contextPath}/resources/commons/js/json2.min.js"></script>
    <![endif]-->
    <script src="${pageContext.request.contextPath}/resources/jquery/jquery-1.8.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/kendoui/js/kendo.web.min.js"></script>
    <%-- <script src="${pageContext.request.contextPath}/resources/select2/js/select2.min.js"></script> --%>
    <script src="${pageContext.request.contextPath}/resources/kendoui/js/cultures/kendo.culture.en-GB.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/commons/js/kendoui.culture.js"></script>
    <!--upload  -->
    <script src="${pageContext.request.contextPath}/resources/jquery/upload/jquery.ui.widget.js"></script>
	<script src="${pageContext.request.contextPath}/init"></script>
	<script src="${pageContext.request.contextPath}/resources/site/js/site.js"></script>
	<script src="${pageContext.request.contextPath}/resources/highcharts/js/highcharts.js"></script>
	<script src="${pageContext.request.contextPath}/resources/site/js/initcharts.js"></script>
	<script src="${pageContext.request.contextPath}/resources/datatable/js/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/datatable/js/jquery.dataTables.bootstrap.js"></script>
	<!--jquery validate  -->
	<script src="${pageContext.request.contextPath}/resources/jquery/validate/jquery.validate.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/site/js/oa.js"></script>
	
    <jsp:include page="/WEB-INF/extension/commons/header.jsp"/>
    
</head>

<body style="background-color: #FFFFFF">
<layout:navmenu />
	
<div id="Wrapper" class="container OA-container">
		<div id="Content" class="content-officeapps row-fluid">
			<div id="message_content" class="row-fluid">
				<c:if test="${not empty MESSAGE_SUCCESS}">
					<div id="successContainer" class="alert alert-success message-container">
						<button type="button" class="close" data-dismiss="alert">&times;</button>
						 
						<c:forEach var="message" items="${MESSAGE_SUCCESS}">
							<i class="icon-ok"></i> <c:out value="${message }"></c:out><br>
						</c:forEach>
					</div>
				</c:if>
			</div>
			<div class="row-fluid">
				<jsp:doBody/>
			</div>
		</div>
		
	    <div id="footer">
	        <div class="container">
	            <jsp:include page="/WEB-INF/extension/commons/footer.jsp"/>
	        </div>
   		</div>
</div>

</body>
</html>
