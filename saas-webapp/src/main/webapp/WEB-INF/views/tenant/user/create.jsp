<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<layout:page>
	<div class="span3">
        <layout:tenantmenu tenant="${tenant}" activeLiId="li_5"/>
    </div>
    
    <div id="tabs" class="span9 OA-wrapper">
         <layout:create modelAttribute="user" titleCode="create_user" multipart="true" action="/tenant/user/create" btnCancelId="btnCancle">
			<input type="hidden" name="tenantId" value="${tenant.id}" id="tenantId"/>
			<layout:row>
				<field:input  path="username" styleId="username" labelCode="user_username" placeholder="User Name" required="true" maxlength="50"/>
			</layout:row>
			<layout:row>
				<field:input  path="firstName"  labelCode="user_firstname" placeholder="First Name" required="true" maxlength="50"/>
			</layout:row>
			<layout:row>
				<field:input  path="lastName" styleId="lastName" labelCode="user_lastname" placeholder="Last Name" required="true" maxlength="50"/>
			</layout:row>
	
			<layout:row>
				<field:input  path="email" styleId="email" labelCode="user_email" placeholder="Email" required="true" maxlength="50"/>
			</layout:row>
	
			<layout:row>
				<field:select  path="roles" items="${roles}" itemLabel="description" itemValue="id"  labelCode="user_role" styleId="roles" required="true"/>
			</layout:row>
		</layout:create>
      </div>
</layout:page>

<script type="text/javascript">
	$("#btnCancle").click(function(){
		var suffix = "_${tenant.id}";
		var url = document.location + "";
		url = url.substring(0, url.lastIndexOf("/"));
		document.location = url + "/list" + suffix;
		return;
	});
	
	$(document).ready(function () {
	    $('#username').focus();
	    
	  //validate forms
	    $('#form').validate({
	      rules: {
	    	  username: {
	    		  required: true,
	    		  email:true,
	    		  remote:{
	    			  type:"POST",
	    			  url:"${pageContext.request.contextPath}/tenant/user/checkUsername",
	    			  data:{
	    				  username:function(){return $("#username").val();}
	    			  }
	    		  }
	          },
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
	    	  username: {
	    		  remote:"This username has been exsited"
	          }
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


