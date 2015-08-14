<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<layout:page>
    
    <div class="row-fluid">

      <div class="OA-wrapper span10 offset1">
      
      
		<form:form cssClass="form-horizontal" modelAttribute="user"  method="POST" action="/activeAccount" id="form">
			<input type="hidden" name="otp" value="${otp}"/>
			<input type="hidden" name="username" value="${loginId}"/>
               <h4>Activate your account<br>
               <small><c:out value='${loginId}' /></small></h4>
               <hr class="OA-hr">
               
            <layout:row>
               	<field:password path="password" styleId="password" labelCode="password" placeholder="New password" required="true" maxlength="50"/>
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
             	<field:select itemLabel="label" itemValue="id" items="${questions}" path="question1" styleId="question1" required="true" labelCode="question1"/>
             	<div class="control-group">
					<div class="controls">	
		                <span class="help-inline"><small>These questions will help us verify your identity should you forget your password.</small></span>  
					</div>
				</div>
			</layout:row>
			<layout:row>
               	<field:input path="answer1" styleId="answer1" labelCode="answer1" placeholder="Answer 1" required="true" maxlength="50"/>
			</layout:row>
			<layout:row>
               	<field:select itemLabel="label" itemValue="id" items="${questions}" path="question2" styleId="question2" required="true" labelCode="question2"/>
			</layout:row>
			<layout:row>
               	<field:input path="answer2" styleId="answer2" labelCode="answer2" placeholder="Answer 2" required="true" maxlength="50"/>
			</layout:row>

               <div class="form-actions OA-form">
                 <button type="submit" class="btn btn-large btn-primary span6 disabled" id="activate-btn">Activate my account</button>
               </div>
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
	          },
	          question1: {
	        	required: true,
              	notEqual: "#question2"
              },
              question2: {
            	  required: true,
              	notEqual: "#question1"
              },
              answer1: {
            	  required: true,
              	notEqual: "#answer2"
              },
              answer2: {
            	required: true,
              	notEqual: "#answer1"
              }
	        },

	      messages: {     
	    	  question1: {
    	        notEqual: "You cannot select the same secret question. Please select a different question."
	   	      },
	   	      question2: {
	   	        notEqual: "You cannot select the same secret question. Please select a different question."
	   	      },
	   	      answer1: {
	   	        notEqual: "Secret answers must be unique. Please provide a unique answer."
	   	      },
	   	      answer2: {
	   	        notEqual: "Secret answers must be unique. Please provide a unique answser."
	   	      },
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
