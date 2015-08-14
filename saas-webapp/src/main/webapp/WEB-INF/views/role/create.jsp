<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<layout:page>
	<layout:create modelAttribute="role" titleCode="create_role">
		<layout:row>
			<field:input path="name" styleId="name" labelCode="role_name" placeholder="Role Name" required="true" maxlength="50"/>
			<field:select itemLabel="name" itemValue="id" items="${permissions}" path="permissions" styleId="permissions" labelCode="role_permissions" multiple="true" />
		</layout:row>
		<layout:row>
			<field:textarea path="description" styleId="description" labelCode="role_description" placeholder="Description"  rows="3" maxlength="255"/>
		</layout:row>
	</layout:create>
</layout:page>

<script type="text/javascript">
	$(document).ready(function () {
	    $('#code').focus();
	    
	    //validate forms
	    $('#form').validate({
	      rules: {
	    	  name: {
	    		required: true
	          },
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




