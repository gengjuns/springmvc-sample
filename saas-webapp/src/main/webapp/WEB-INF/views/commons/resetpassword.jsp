<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<layout:page>
    <div class="row-fluid">
      <div class="OA-wrapper span10 offset1">
		<form:form cssClass="form-horizontal" modelAttribute="user"  method="POST" action="/resetpassword" id="form">
			<input type="hidden" name="otp" value="${otp}"/>
			<input type="hidden" name="username" value="${username}"/>
               <h4>Reset your password<br>
               <small><c:out value='${username}' /></small></h4>
               <hr class="OA-hr">
               
            <layout:row>
               	<field:password path="password" styleId="password" labelCode="password" placeholder="New password" required="true" />
               	<div class="control-group">
					<div class="controls">	
		                <span class="help-inline"><small>Your password must be at least 8 characters and contain 3 of the following:an upper case letter, a lower case letter, a number or a symbol.</small></span>
					</div>
				</div>
			</layout:row>
			<layout:row>
               	<field:password path="passwordConfirm" styleId="passwordConfirm" labelCode="passwordconfirm" placeholder="Confirm new password" required="true" />
			</layout:row>
			<layout:row>
               <div class="form-actions OA-form">
                 <button type="submit" class="btn btn-large btn-primary span6 disabled" id="activate-btn">Reset your password</button>
               </div>
			</layout:row>
		</form:form>
      </div>
    </div>

    
    
</layout:page>

<script type="text/javascript">
	$(document).ready(function () {
	    $('#password').focus();
	    
	  //validate forms
	    $('#form').validate({
	      rules: {
	    	  password: {
	    		  required: true,
	              minlength: 8,
	              OApassword: true
	          },
	          passwordConfirm: {
	        	  required: true,
		          equalTo: "#password"
	          }
	       },
	
	      messages: {     
	    	  password: {
	   	        minlength: "Your password does not meet the criterion",
	   	        OApassword: "Your password does not meet the criterion"
	   	      },
              passwordConfirm: {
  	           	equalTo: "Please enter the same password again."
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
