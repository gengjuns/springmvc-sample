<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@attribute name="titleCode" required="false" %>
<%@attribute name="title" required="false" %>
<%@attribute name="permission" required="false" %>
<%@attribute name="modelAttribute" required="true" %>

    <c:if test="${not empty titleCode}">
        <legend><spring:message code="title_${fn:toLowerCase(titleCode)}"/></legend>
    </c:if>
    <c:if test="${not empty title}">
        <legend><c:out value="${title}"/></legend>
    </c:if>
        <div id="grid"></div>
        
         <script>       
            $(document).ready(function () {
            		 
            		 $("#grid").kendoGrid({
                         dataSource: initializeDataSource(),
                         scrollable: {
                             virtual: true
                         },
                         height: 300,
                         sortable: true,
                         filterable: true,
                         columnMenu: false,
                         reorderable: true,
                         resizable: true,
                         toolbar: [{template:
                                <sec:authorize access="hasAnyRole('PERM_PORTAL_ALL','PERM_${fn:toUpperCase(modelAttribute)}_CREATE')">
                                 "<button class='btn' onclick='createEntity();'><i class='icon-plus'></i></button>" +
                                </sec:authorize>

                                 "<button class='btn' onclick='refreshGrid(\"grid\");'><i class='icon-refresh'></i></button>"}],
                         columns: 
                             [
                             <jsp:doBody/>
                             ,
                             {
                                command: [
                                            <sec:authorize access="hasAnyRole('PERM_PORTAL_ALL','PERM_${fn:toUpperCase(modelAttribute)}_READ')">
                                          	{
                                          		name:	"view",
			                          			text:"<i class='icon-eye-open' data-toggle='tooltip' title='View'></i>",
			                          			click:function(e){
			                          				var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
			          	                            var id = dataItem.get("id");
			          	                          	showEntity(id);
			                          			}
                                 			},
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('PERM_PORTAL_ALL','PERM_${fn:toUpperCase(modelAttribute)}_WRITE')">
			                                {
                                 				
			                          			name:	"modify",
			                          			text:"<i class='icon-edit' data-toggle='tooltip' title='Edit'></i>",
			                          			click:function(e){
			                          				var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
			          	                            var id = dataItem.get("id");
			          	                          	editEntity(id);
			                          			}
			                             	},
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('PERM_PORTAL_ALL','PERM_${fn:toUpperCase(modelAttribute)}_DELETE')">
			                             	{
				                                 name:"destroy",
				                                 text:"<i class='icon-trash' data-toggle='tooltip' title='Delete'></i>"
				                         	}
                                            </sec:authorize>
				                         ],
				                         title: "&nbsp;", 
				                         width: "130px"
				      		}
                             
                         	],
                   		editable: {
                         		mode: "inline",
                             	confirmation: "Are you sure you want to remove this record?"	
                         }
                     });

            });
            
        </script>

            
