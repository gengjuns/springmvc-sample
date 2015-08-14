<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<layout:page>
    <div class="row-fluid">
      <div class="OA-wrapper span10 offset1">
		<form:form cssClass="form-horizontal" modelAttribute="user"  method="POST" action="/forgetpassword/retrievequestions" id="form">
			<h4>Forget password</h4><br>
            <hr class="OA-hr">
               
            <layout:row>
               	<field:input path="username" styleId="username" labelCode="forgetpw_username" placeholder="Email address" required="true" />
			</layout:row>
			
            <layout:row>
            	<div class="control-group">
	                  <div class="controls">
                  	  	<span class="help-block">
              				<button class="btn" type="submit" tabindex="2"><spring:message code='title_forgetpw_next'/></button>
                  	  	</span>
	                  </div>
                </div>
			</layout:row>
			
		</form:form>
      </div>
    </div>

</layout:page>

<script type="text/javascript">
	$(document).ready(function () {
	    $('#username').focus();
	    
		//validate forms
	    $('#form').validate({
	      rules: {
	    	  username: {
	    		  required: true,
	    		  email:true
	          }
	        },
	
	      messages: {     
	    	  username: {
	    		  
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
