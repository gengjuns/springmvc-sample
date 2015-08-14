<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<layout:page>
	<layout:update modelAttribute="reportTemplate" titleCode="update_template" multipart="true">
	
		<layout:row>
			<field:display labelCode="template_id" expression="reportTemplate.templateId"/>
		</layout:row>
		<layout:row>
			<field:input  path="templateSubject" styleId="templateSubject" labelCode="template_subject" placeholder="Template Subject" required="true" maxlength="255"/>
		</layout:row>
		<layout:row>
			<field:textarea path="templateDesc" styleId="templateDesc" labelCode="template_desc" placeholder="Description" required="false" maxlength="255"/>
		</layout:row>
		<layout:row>
			<field:upload dataObject="reportTemplate" operation="create" dataTableField="file" styleId="file" path="file" labelCode="template_content"/>
			<div class="control-group">
				<div class="controls">	
	                <span class="help-inline"><small>Your file must be a html file and less than 100kb.<br>If no upload file,the content will not be changed.</small></span>
				</div>
			</div>
		</layout:row>
		<layout:row>
			<field:select itemLabel="label" itemValue="code" items="${parameters}" path="parameters" styleId="parameters" labelCode="template_parameters" multiple="true" />
		</layout:row>
		<layout:row>
			<field:select itemLabel="label" itemValue="code" items="${categorys}" path="templateCategory" styleId="templateCategory" labelCode="template_category" multiple="false" required="false"/>
		</layout:row>
		<layout:row>
			<field:select itemLabel="label" itemValue="code" items="${modes}" path="templateMode" styleId="templateMode" labelCode="template_mode" multiple="false" required="true"/>
		</layout:row>
		<layout:row>
			<field:select itemLabel="label" itemValue="code" items="${status}" path="templateStatus" styleId="templateStatus" labelCode="template_status" multiple="false" />
		</layout:row>
		<layout:row>
			<field:input  path="templateEmailFrom" styleId="templateEmailFrom" labelCode="template_email_from" placeholder="Eamil From" required="true" maxlength="255"/>
		</layout:row>
		<layout:row>
			<field:input  path="templateEmailTo" styleId="templateEmailTo" labelCode="template_email_to" placeholder="Email To" required="false" maxlength="255"/>
		</layout:row>
		<layout:row>
			<field:input  path="templateEmailCc" styleId="templateEmailCc" labelCode="template_email_cc" placeholder="Email CC" required="false" maxlength="255"/>
		</layout:row>
		<layout:row>
			<field:input  path="templateEmailBcc" styleId="templateEmailBcc" labelCode="template_email_bcc" placeholder="Email BCC" required="false" maxlength="255"/>
		</layout:row>

	</layout:update>
</layout:page>

<script type="text/javascript">
	$(document).ready(function () {
	    $('#templateId').focus();
	    
	    //validate forms
	    $('#form').validate({
	      rules: {
	          templateSubject: {
	    		required: true
	          },
	          file: {
	    		accept:'html',
	    		filesize:102400
	          },
	          templateMode: {
	    		required: true
	          },
	          templateEmailFrom: {
	        	required:true,
	    		email: true
	          },
	          templateEmailTo: {
	        	email: true
	          },
	          templateEmailCc: {
	        	email: true
	          },
	          templateEmailBcc: {
	        	email: true
	          }
	        },
	
	      messages: {    
	    	  file: {
		    		accept:"Please select a html file",
		    		filesize:"The file size must be less than 100kb"
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
