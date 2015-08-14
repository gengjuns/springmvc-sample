<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<layout:page>
	<layout:create modelAttribute="codeValue" titleCode="create_codevalue">
		<layout:row>
			<field:input path="code" styleId="code" labelCode="codevalue_code"  required="true" maxlength="100"/>
			<field:input path="label" styleId="label" labelCode="codevalue_label" required="true" label="255"/>
		</layout:row>
		<layout:row>
            <field:select itemLabel="name" itemValue="id" items="${allCodeTypes}" path="type" styleId="type" labelCode="codevalue_codetype" required="true"/>
		</layout:row>
		<layout:row>
            <field:date path="effectiveDate" styleId="effectiveDate" labelCode="codevalue_effective_date" placeholder="Effective Date" time="true" />
            <field:date path="expiryDate" styleId="expiryDate" labelCode="codevalue_expiry_date" placeholder="Effective Date" time="true" />
		</layout:row>
		<layout:row>
            <field:input path="position" styleId="position" labelCode="codevalue_position" placeholder="Position"  />
		</layout:row>
	</layout:create>
</layout:page>

<script type="text/javascript">
	$(document).ready(function () {
	    $('#code').focus();
	    
	    //validate forms
	    $('#form').validate({
	      rules: {
	    	  code: {
	    		required: true
	          },
	          label: {
	    		required: true
	          },
	          type: {
	    		required: true
	          },
	          effectiveDate: {
	        	date: true,
	          },
	          expiryDate: {
	        	date: true
	          },
	          position: {
	        	digits: true
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
	
	$(function() {
		
	});
</script>


