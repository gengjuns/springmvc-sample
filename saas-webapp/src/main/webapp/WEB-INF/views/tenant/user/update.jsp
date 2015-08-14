<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<layout:page>

	<div class="span3">
        <layout:tenantmenu tenant="${tenant}" activeLiId="li_5"/>
    </div>
    
    <div id="tabs" class="span9 OA-wrapper">
		<layout:update modelAttribute="user" titleCode="edit_user" cssClass="form-horizontal" btnCancelId="btnCancle">
			<layout:row>
	            <field:display labelCode="user_name" expression="user.username"/>
			</layout:row>
			<layout:row>
				<field:input  path="firstName" styleId="firstName" labelCode="user_firstname" placeholder="First Name" required="true" />
			</layout:row>
			<layout:row>
				<field:input  path="lastName" styleId="lastName" labelCode="user_lastname" placeholder="Last Name" required="true"/>
			</layout:row>
	
			<layout:row>
				<field:input  path="email" styleId="email" labelCode="user_email" placeholder="Email" required="true"/>
			</layout:row>
	
			<layout:row>
				<field:select  path="roles" styleId="roles" items="${roles}" itemLabel="description" itemValue="id"  labelCode="user_role" required="true"/>
			</layout:row>
		</layout:update>
    </div>
	 
</layout:page>


<script type="text/javascript">
	$("#btnCancle").click(function(){
		var suffix = "_${tenant.id}";
		var url = document.location + "";
		for ( var i = 0; i < 3; i++) {
			url = url.substring(0, url.lastIndexOf("/"));
		}
		document.location = url + "/list" + suffix;
		return;
	});
	
	
	$(document).ready(function () {
	    $('#firstName').focus();
	    
	    //validate forms
	    $('#form').validate({
	      rules: {
	          firstName: {
	    		required: true
	          },
	          lastName: {
	        	required: true
	             },
	             email: {
	           	  required: true,
	           	  email:true
	             },
	             roles: {
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