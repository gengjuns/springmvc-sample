<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<layout:page>

	<div class="span3">
        <layout:tenantmenu tenant="${resellerTenant}" activeLiId="li_6"/>
    </div>
    
    <div id="tabs" class="span9 OA-wrapper">
    	
    	<div class="btn-group pull-right">					
			<a href="${pageContext.request.contextPath}/tenant/customerlist_${resellerTenant.id}/create" class="btn btn-primary"><i class="icon-plus"></i> Add a Tenant</a>
		</div>

		<h4>
			Manage Tanants
			<br> <small> &nbsp; </small>
		</h4>
		<hr class="OA-hr">
	    <div class="controls pull-right OA-hr">
			 <input maxlength="100" type="search" placeholder="Search a tenant" class="OA-input-filter search-query" onkeyup="javascript:searchTemplates(this);">
		</div>

			<!-- <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="saas_datatable"> -->
			<table cellpadding="0" cellspacing="0" border="0" class="table" id="saas_datatable">
				<thead>
					<tr class="OA-header-background">
						<th width="15%"><spring:message code='label_tenant_name' /></th>
						<th width="30%" class="hidden-phone"><spring:message code='label_tenant_description' /></th>
						<th width="15%" class="hidden-phone"><spring:message code='label_tenant_status' /></th>
						<th width="20%" class="hidden-phone"><spring:message code='label_tenant_lastupdated_date' /></th>
						<th width="20%" class="pull-center"> <spring:message code='label_table_action'/></th>
					</tr>								
				</thead>
				<tbody>
					
				</tbody>
			</table>		
			<hr class="OA-hr">
			
	</div>
</layout:page>

<script type="text/javascript">
<!--

//-->

		//renderDataTables();
		var oDataTable;
		$(document).ready(function() {
			var columns = [                          
	                       {"mDataProp": "name"},  
	                       {"mDataProp": "description", "sClass": "hidden-phone"},  
	                       {"mDataProp": "tenantStatus", "sClass": "hidden-phone"},  
	                       {"mDataProp": "updatedDate", "sClass": "hidden-phone"},  
	                       {"mDataProp": "id"}
	                 	 ];
			
			var columnsDefs = [                          
	                           {
	                           	"aTargets": [4],
	                             	"mData" : null,
	                             	"bSearchable": false,
	                             	"bSortable": false,
	                             	"mRender": function(id,type,data){
	                             				return renderAction(id,data.tenantStatus);
	                             			}
	                             }
	                        ];
			
			oDataTable = $('#saas_datatable').dataTable( {  
						    "aoColumns": columns,  
						    "aoColumnDefs": columnsDefs,
						    "fnServerParams" : function(aoData){
                            	aoData.push( { "name": "parentTenantId", "value": "${resellerTenant.id}"} ); 
                       		 }
						});  
		} );
 
		
		function renderAction(id,status) {
			var statusHtml = "";
			if(status == "ACTIVE"){
				statusHtml = '<li><a href="${pageContext.request.contextPath}/tenant/suspend/'+ id + '"><i class="icon-repeat"></i><spring:message code="label_btn_tenant_suspend"/></a></li>';
			}else if(status == "SUSPEND"){
				statusHtml = '<li><a href="${pageContext.request.contextPath}/tenant/reinstatus/'+ id + '"><i class="icon-share"></i><spring:message code="label_btn_tenant_reinstate"/></a></li>'
			}
			
			var actionHtml = 
			'<div class="btn-group pull-right">' + 
				'<a href="${pageContext.request.contextPath}/tenant/update/'+ id + '" class="btn btn-small"><i class="icon-edit"></i> <spring:message code="label_btn_tenant_edit"/></a>' + 
				'<a href="#" data-toggle="dropdown" class="btn dropdown-toggle btn-small"><span class="caret"></span></a>'+
				'<ul class="dropdown-menu">'+
								statusHtml +
					'<li class="divider"></li>'+
					'<li><a href="${pageContext.request.contextPath}/tenant/delete/'+ id + '"><i class="icon-trash"></i><spring:message code="label_btn_tenant_delete"/></a></li>'+
				'</ul>'+
			'</div>'
			return actionHtml;
		}  
		
		function searchTemplates(obj){
			oDataTable.fnFilter($(obj).val(),0);
			//oDataTable.fnDraw("dd");
			}
</script>
 