<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<layout:page>
	
	<layout:create modelAttribute="tenant" titleCode="create_tenant">
		<input type="hidden" name="parentTenantId" id="parentTenantId" value="${sessionScope.TENANT_ID.id}" />
		<layout:row>
			<field:input path="name" styleId="name" labelCode="tenant_name" placeholder="Tenant Name" required="true" maxlength="50"/>
            <field:textarea path="description" labelCode="tenant_description" placeholder="Description" maxlength="255"/>
		</layout:row>
	</layout:create>
</layout:page>


<script type="text/javascript">
	$(document).ready(function () {
	    $('#name').focus();
	    
	    //validate forms
	    $('#form').validate({
	      rules: {
	    	  name: {
	    		required: true
	          }
	        },
	
	      messages: {     
	        },     
	
	      highlight: function(label) {
	        $(label).closest('.control-group').addClass('error');
	        $(label).closest('.control-group').removeClass('success');
	      },
	
	      success: function(label) {
	        label
	          .text('').addClass('valid')
	          .closest('.control-group').addClass('success')
	          .closest('.control-group').removeClass('error');
	        }
	      }); 
	});
</script>

