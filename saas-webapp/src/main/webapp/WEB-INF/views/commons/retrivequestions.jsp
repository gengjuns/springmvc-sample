<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field"%>
<layout:page>
	<div class="row-fluid">
		<div class="OA-wrapper span10 offset1">
			<form:form cssClass="form-horizontal" modelAttribute="user"
				method="POST" action="/forgetpassword/checkanswers" id="form">
				<input type="hidden" name="username" value="${user.username}" />
				<h4>
					Forget password<br> <small><c:out
							value='${user.username}' /></small>
				</h4>
				<hr class="OA-hr">

				<layout:row>
					<field:display labelCode="question1" expression="user.question1"/>
				</layout:row>
				<layout:row>
					<field:textarea path="answer1" styleId="answer1" required="true" labelCode="answer1"/>
				</layout:row>

				<layout:row>
					<field:display labelCode="question2" expression="user.question2"/>
				</layout:row>
				<layout:row>
					<field:textarea path="answer2" styleId="answer2" required="true" labelCode="answer2"/>
				</layout:row>

				<layout:row>
					<div class="control-group">
						<div class="controls">
							<span class="help-block">
								<button class="btn" type="submit" tabindex="2">
									<spring:message code='title_forgetpw_next' />
								</button>
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
	    $('#answer1').focus();
	    
	  //validate forms
	    $('#form').validate({
	      rules: {
	    	  answer1: {
	    		  required: true
	          },
	    	  answer2: {
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
