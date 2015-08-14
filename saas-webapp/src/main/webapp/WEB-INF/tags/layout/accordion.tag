<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 


<%@attribute name="readUrl" required="true"%>
<%@attribute name="styleId" required="true" %>
<%@attribute name="label" required="true" %>
<%@attribute name="width" required="false"%>
<%@attribute name="hierarchy" required="false" type="java.lang.Boolean"%>
<%@attribute name="viewfieldLabel" required="false"%>
<%@attribute name="fieldValueLabel" required="false"%>


	<c:if test="${empty width }">
		<c:set var="width" value="span12"/>
	</c:if>
	<c:if test="${empty hierarchy }">
		<c:set var="hierarchy" value="false"/>
	</c:if>
	<c:if test="${hierarchy}">
		<c:if test="${empty viewfieldLabel }">
			<spring:message code='label_auditTrail_viewfieldlabel' var="viewfieldLabel"/>
		</c:if>
		<c:if test="${empty fieldValueLabel }">
			<spring:message code='label_auditTrail_fieldvaluelabel' var="fieldValueLabel"/>
		</c:if>
	</c:if>

	
	<layout:row>
		<div class="${width}">
			<div class="accordion" id="accordion_${styleId}">
				<div class="accordion-group">
					<div class="accordion-heading">
						<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion_${styleId}" href="#collapse_${styleId}" style="font-size:1.2em;font-weight:bold;"><b>${label}</b></a>
					</div>
					<div id="collapse_${styleId}" class="accordion-body collapse">
						<div class="accordion-inner">
							<div id="viewgroup_${styleId}"></div>
						</div>
					</div>
				</div>
			</div>
	    </div>
    </layout:row>

    <script>
    $(document).ready(function(){
    	$('#collapse_${styleId}').on('show',function(){
    		if ($("#viewgroup_${styleId}").children().length == 0) {
    			var url = SaaSApp.contextPath + "/" + "${readUrl}";
       			var dataSource = new kendo.data.DataSource({
       	            transport: {
       	                read: {
       	                    url: url,
       	                    dataType: "json",
       	                    contentType: "application/json",
       	                    type: "POST"
       	                },
       	                parameterMap: function (options, type) {
       	                    return JSON.stringify(options);
       	                }
       	            },
       	            pageSize: 25,
       	            schema: {
       	                model: {
       	                    id: "id",
       	                    fields: {
       	                        id: { field: "id" },
       	                     	createTime: { type: "date", parse: function(data){return kendo.parseDate(data, "dd/MM/yyyy HH:mm")} },
       	                     	dueDate: { type: "date", parse: function(data){return kendo.parseDate(data, "dd/MM/yyyy HH:mm")} },
       	                     	updatedDate: { type: "date", parse: function(data){return kendo.parseDate(data, "dd/MM/yyyy HH:mm")} }
       	                    }
       	                },
       	                data: "data",
       	                total: "total"
       	            }
       	         });
				
       			if ("true" == "${hierarchy}") {
       				$("#viewgroup_${styleId}").kendoGrid({
                        dataSource: dataSource,
                        pageable: true,
                        sortable: true,
                        filterable: true,
                        detailInit: function (e){
                        	$("<div/>").appendTo(e.detailCell).kendoGrid({
                                dataSource: {
                                    data: e.data.children,
                                    pageSize: 5,
                                    schema: {
                       	                model: {
                       	                    id: "id"
                       	                }
                       	            }
                                },
                                scrollable: {
                                    virtual: true
                                },
                                columns: [
                                    { field: "label", title: "${viewfieldLabel}" },
                                    { field: "value", title: "${fieldValueLabel}" }
                                ]
                            });
                        },
                        columns: 
                            [
      						<jsp:doBody/>
      						,
                        	],
                 		 editable: {
                        		mode: "inline"
                        }
                    });
       				
       			} else {
       				$("#viewgroup_${styleId}").kendoGrid({
                        dataSource: dataSource,
                        scrollable: {
                            virtual: true
                        },
                        height: 300,
                        sortable: true,
                        filterable: true,
                        columns: 
                            [
      						<jsp:doBody/>
      						,
                        	],
                 		 editable: {
                        		mode: "inline"
                        }
                    });
       			}
       		}
    	});
    	
    });
    	 

    </script>
