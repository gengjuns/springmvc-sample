<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:page>
	<div class="OA-wrapper span12">

		<div class="btn-group pull-right">					
			<a href="${pageContext.request.contextPath}/codevalue/create" class="btn btn-primary"><i class="icon-plus"></i> Add a CodeValue</a>
		</div>

		<h4>Manage CodeValues
			<br> <small> &nbsp; </small>
		</h4>
		<hr class="OA-hr">
	    <div class="controls pull-right OA-hr">
			 <input maxlength="100" type="search" placeholder="Search a role" class="OA-input-filter search-query" onkeyup="javascript:searchTemplates(this);">
		</div>

			<!-- <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="saas_datatable"> -->
			<table cellpadding="0" cellspacing="0" border="0" class="table" id="saas_datatable">
				<thead>
					<tr class="OA-header-background">
						<th width="20%" class="hidden-phone"><spring:message code='label_codevalue_code' /></th>
						<th width="20%"><spring:message code='label_codevalue_label' /></th>
						<th width="20%" class="hidden-phone"><spring:message code='label_codevalue_codetype' /></th>
						<th width="20%" class="hidden-phone"><spring:message code='label_codetype_lastupdated_date' /></th>
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
	                       {"mDataProp": "code", "sClass": "hidden-phone"},  
	                       {"mDataProp": "label"},  
	                       {"mDataProp": "type.name", "sClass": "hidden-phone"},  
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
	                             				return renderAction(id,data.tenant_status);
	                             			}
	                             }
	                        ];
			
			oDataTable = $('#saas_datatable').dataTable( {  
						    "aoColumns": columns,  
						    "aoColumnDefs": columnsDefs
						});  
		} );
 
		
		function renderAction(id,status) {
			var actionHtml = 
			'<div class="btn-group pull-right">' + 
				'<a href="${pageContext.request.contextPath}/codevalue/update/'+ id + '" class="btn btn-small"><i class="icon-edit"></i> <spring:message code="label_btn_codevalue_edit"/></a>' + 
				'<a href="#" data-toggle="dropdown" class="btn dropdown-toggle btn-small"><span class="caret"></span></a>'+
				'<ul class="dropdown-menu">'+
					'<li><a href="${pageContext.request.contextPath}/codevalue/delete/'+ id + '"><i class="icon-trash"></i><spring:message code="label_btn_codevalue_delete"/></a></li>'+
				'</ul>'+
			'</div>'
			return actionHtml;
		}  
		
		function searchTemplates(obj){
			oDataTable.fnFilter($(obj).val(),1);
			//oDataTable.fnDraw("dd");
			}
</script>