<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

      
<%@attribute name="tenant" required="true" type="com.saas.tenant.entity.Tenant"%>
<%@attribute name="activeLiId" required="false"%>


<c:set var="tenantId">
  	<spring:eval expression="tenant['id']" />
</c:set>
<c:set var="tenantName">
  	<spring:eval expression="tenant['name']" />
</c:set>
<c:set var="status">
  	<spring:eval expression="tenant['tenantStatus']" />
</c:set>
<c:set var="parentTenantId">
  	<spring:eval expression="tenant['parentTenantId']" />
</c:set>

      
 <div class="OA-wrapper">
     <ul id="mytabs" class="nav nav-list OA-sidenav">
       <li id="li_1"><div class="sidenav-heading"><h4>${tenantName }</h4></div></li>
       <li id="li_2" class=""><a href="mailto:bat@man.com" style="white-space: normal;word-wrap: break-word;"><i class="icon-envelope"></i> bat@man.com</a></li>
       <li id="li_3" class=""><a href="tel:61432046761"><i class="icon-phone"></i> 0432046766</a></li>
       <li id="li_4" class=""><a href="${pageContext.request.contextPath}/tenant/update/${tenantId}" class="OA-scrolltotab">Edit profile <i class="icon-chevron-right pull-right"></i></a></li>  
       <li id="li_5" class=""><a href="${pageContext.request.contextPath}/tenant/user/list_${tenantId}" class="OA-scrolltotab">User List <i class="icon-chevron-right pull-right"></i></a></li> 
       <c:if test="${sessionScope.TENANT_ID.parentTenantId == null && parentTenantId == sessionScope.TENANT_ID.id}">
	       <li id="li_6" class=""><a href="${pageContext.request.contextPath}/tenant/customerlist_${tenantId}" class="OA-scrolltotab">Customer Tenant List <i class="icon-chevron-right pull-right"></i></a></li>            
       </c:if>           
     </ul>
   </div>
   <div class="OA-wrapper">
     <ul class="nav nav-list OA-sidenav">
     	<c:choose>
		    <c:when test="${status == 'ACTIVE'}">
       			<li id="li_7"><a href="${pageContext.request.contextPath}/tenant/suspend/${tenantId}"><i class="icon-repeat"></i> <spring:message code="label_btn_tenant_suspend"/></a></li>
		    </c:when>
		    <c:when test="${status == 'SUSPEND'}">
		       <li id="li_8"><a href="${pageContext.request.contextPath}/tenant/reinstatus/${tenantId}"><i class=icon-repeat></i> <spring:message code="label_btn_tenant_reinstate"/></a></li>
		    </c:when>
		</c:choose>
     </ul>
   </div>
   
   <script type="text/javascript">
	   $("li").each(function(){
		   if($(this).attr("id") == "${activeLiId}"){
			   $(this).toggleClass("active");
			   return;
		   }
		});
   </script>
      
