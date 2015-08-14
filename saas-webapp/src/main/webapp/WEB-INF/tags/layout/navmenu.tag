<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar navbar-static-top">
    <div class="navbar-inner">
        <div class="container">
        <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"><i class="icon-reorder"></i></a>
   <!-- 		 <a class="btn btn-navbar" data-toggle="collapse" data-target="#header-nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a> -->


            <div id="header-nav-collapse" class="nav-collapse collapse">
                <ul class="nav">
                    <sec:authorize access="isAuthenticated()">
                        <li><a href="${pageContext.request.contextPath}/"><span class="icon-home icon-white icon-large"></span><spring:message code="label_menu_home" /></a></li>

                        <sec:authorize access="hasAnyRole('ROLE_PORTAL_ADMIN','ROLE_PORTAL_USER','ROLE_RESELLER_ADMIN','ROLE_TENANT_ADMIN')">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="icon-cogs icon-white icon-large"></span> <spring:message code="label_menu_administration" /><b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                    <sec:authorize access="hasAnyRole('ROLE_PORTAL_ADMIN')">
                                        <li><a href="${pageContext.request.contextPath}/tenant/list"><span class="icon-cloud"></span> <spring:message code="label_menu_resellers" /></a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_RESELLER_ADMIN')">
                                        <li><a href="${pageContext.request.contextPath}/tenant/list"><span class="icon-cloud"></span> <spring:message code="label_menu_tenants" /></a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_TENANT_ADMIN')">
                                        <li><a href="${pageContext.request.contextPath}/user/list"><span class="icon-user"></span> <spring:message code="label_menu_users" /></a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_RESELLER_ADMIN')">
                                        <li><a href="${pageContext.request.contextPath}/reseller/user/list"><span class="icon-user"></span> <spring:message code="label_menu_users" /></a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_PORTAL_ADMIN')">
                                        <li><a href="${pageContext.request.contextPath}/role/list"><span class="icon-lock"></span> <spring:message code="label_menu_roles" /></a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_PORTAL_ADMIN')">
                                        <li><a href="${pageContext.request.contextPath}/permission/list"><span class="icon-lock"></span> <spring:message code="label_menu_permissions" /></a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_PORTAL_ADMIN','ROLE_TENANT_ADMIN')">
                                        <li><a href="${pageContext.request.contextPath}/ad/list"><span class="icon-lock"></span> <spring:message code="label_menu_adconfigs" /></a></li>
                                    </sec:authorize>
                                    <li class="divider"></li>
                                    <sec:authorize access="hasAnyRole('ROLE_PORTAL_ADMIN')">
                                        <li><a href="${pageContext.request.contextPath}/codetype/list"><span class="icon-cog"></span> <spring:message code="label_menu_code_table_types" /></a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_PORTAL_ADMIN')">
                                        <li><a href="${pageContext.request.contextPath}/codevalue/list"><span class="icon-cog"></span> <spring:message code="label_menu_code_table_values" /></a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasAnyRole('ROLE_PORTAL_ADMIN')">
                                        <li><a href="${pageContext.request.contextPath}/reporttemplate/list"><span class="icon-cog"></span> <spring:message code="label_menu_report_template" /></a></li>
                                    </sec:authorize>
                                <sec:authorize access="hasAnyRole('ROLE_PORTAL_ADMIN')">
                                    <li class="divider"></li>
                                    <li><a href="${pageContext.request.contextPath}/statistics"><span class="icon-table"></span> <spring:message code="label_menu_statistics" /></a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                        </sec:authorize>
                    </sec:authorize>
                </ul>
                
                <ul class="nav pull-right">
                    <sec:authorize access="isAuthenticated()">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="icon-user icon-white"></span>
                                <sec:authentication property="principal.user.fullName"/>
                                <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                            	 <sec:authorize access="hasAnyRole('ROLE_TENANT_ADMIN','ROLE_TENANT_USER',)">
                                        <li><a href="${pageContext.request.contextPath}/user/update/<sec:authentication property="principal.user.id"/>"><span class="icon-user"></span> <spring:message code="label_menu_view_profile" /></a></li>
                                  </sec:authorize>
                            	 <sec:authorize access="hasAnyRole('ROLE_RESELLER_ADMIN')">
                                        <li><a href="${pageContext.request.contextPath}/reseller/user/update/<sec:authentication property="principal.user.id"/>"><span class="icon-user"></span> <spring:message code="label_menu_view_profile" /></a></li>
                                  </sec:authorize>
                                <li class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/logout"><span class="icon-signout"></span> <spring:message code="label_menu_sign_out" /></a></li>
                            </ul>
                        </li>
                    </sec:authorize>
                </ul>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/commons/about.jsp"/>
