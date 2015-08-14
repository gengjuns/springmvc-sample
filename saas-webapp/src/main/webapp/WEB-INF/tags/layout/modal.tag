<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@attribute name="createURL" required="true"%>
<%@attribute name="updateURL" required="true"%>
<%@attribute name="deleteURL" required="true"%>
<%@attribute name="defaultURL" required="true"%>
<%@attribute name="titleCode" required="true"%>
<%@attribute name="styleId" required="true"%>
<%@attribute name="targetTbl" required="true"%>




<div id="${styleId}" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" 
	create_url="${createURL }" edit_url="${updateURL }" delete_url="${deleteURL}" default_url="${ defaultURL}" target_tbl="${targetTbl}">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3 id="myModalLabel"><spring:message code="title_${titleCode}"/></h3>
			</div>
			<div class="modal-body">
				<p>Loading</p>
			</div>
			<div class="modal-footer">
				<button class="btn modal_cancel" data-dismiss="modal" aria-hidden="true"><spring:message code="button_cancel"/></button>
				<button class="btn btn-primary modal_submit"><spring:message code="button_save"/></button>
			</div>
		</div>
