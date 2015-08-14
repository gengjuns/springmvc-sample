<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<layout:page>
	 
	<layout:update modelAttribute="group" titleCode="edit_group">
		<layout:row>
			<field:input  path="name" styleId="name" labelCode="group_name" placeholder="Group Name" required="true"/>
			<field:select  path="parentGroup" items="${groups}" itemLabel="name" itemValue="id"  labelCode="group_parent" styleId="parentGroup"/>
		</layout:row>
		
		<layout:row>
			  <field:select  path="roles" items="${roles}" multiple="true" itemLabel="name" itemValue="id" labelCode="group_role" styleId="roles"/>
			  <field:select  path="permissions" items="${permissions}" multiple="true" itemLabel="name" itemValue="id" labelCode="group_permission" styleId="permissions"/>
		</layout:row>
		
		<layout:row>
			<field:textarea  path="description" labelCode="group_description" placeholder="Description"/>
		</layout:row>
			 
	</layout:update>
	<script>
		$(document).ready(function(){
			removeSelfFromOptions("${id}", "parentGroup");
			});
		
	</script>
</layout:page>
