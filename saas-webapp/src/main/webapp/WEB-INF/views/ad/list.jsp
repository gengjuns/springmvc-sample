<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:page>
	<div class="OA-wrapper span12">

		<div class="btn-group pull-right">					
			<a href="${pageContext.request.contextPath}/ad/create" class="btn btn-primary"><i class="icon-plus"></i> Add AD Config</a>
		</div>

		<h4>Manage AD Config
			<br> <small> &nbsp; </small>
		</h4>
		<hr class="OA-hr">
			<div class="controls pull-right OA-hr">
				 <input type="search" placeholder="Search AD Config" class="OA-input-filter search-query">
			</div>

			<table class="table">
				<thead>
					<tr class="OA-header-background">
						<th><spring:message code='label_ad_name' /></th>
						<th><spring:message code='label_ad_host' /></th>
						<th><spring:message code='label_ad_port' /></th>
						<th><spring:message code='label_ad_logindn' /></th>
						<th><spring:message code='label_ad_searchdn' /></th>
						<th style="text-align: right;"><spring:message code='label_table_action'/></th>
					</tr>								
				</thead>
				<tbody>
				<c:forEach items="${dataList}" var="adconfig">
					<tr class="">
						<td>
							<div class="pull-left">												
								<a href="${pageContext.request.contextPath}/ad/update/${adconfig.id}">${adconfig.name }</a>
							</div>
						</td>
						<td>
							${adconfig.host  }
						</td>
						<td>
							${adconfig.port  }
						</td>
						<td>
							${adconfig.loginDN  }
						</td>
						<td>
							${adconfig.searchDN  }
						</td>
						<td class="">
							<div class="btn-group pull-right">
								<a href="${pageContext.request.contextPath}/ad/update/${adconfig.id}" class="btn btn-small"><i class="icon-edit"></i><spring:message code='label_btn_editAD'/></a>
								<a href="#" data-toggle="dropdown" class="btn dropdown-toggle btn-small"><span class="caret"></span></a>
								<ul class="dropdown-menu">
									<li><a href="${pageContext.request.contextPath}/ad/delete/${adconfig.id}"><i class="icon-trash"></i><spring:message code='label_btn_deleteAD'/></a></li>
								</ul>
							</div>
						</td>
					</tr>																			
					
				</c:forEach>
					
				</tbody>
			</table>		

			<hr class="OA-hr">
			<%-- <table class="table">
				<thead>
					<tr class="OA-header-background">
						<td><a href="${pageContext.request.contextPath}/user/create"><i class="icon-plus"></i> Add a user</a></td>
					</tr>								
				</thead>
			</table> --%>
			

	</div>
</layout:page>