<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<layout:page>
		<layout:update modelAttribute="codeValue" titleCode="edit_codevalue">
		<layout:row>
			<field:input path="code" labelCode="codevalue_code" placeholder="Code"  styleId="code" required="true"/>
			<field:input path="label" styleId="label" labelCode="codevalue_label" placeholder="Label" required="true"/>
		</layout:row>
		<layout:row>
            <field:display labelCode="codevalue_codetype" expression="codeValue['type']" itemValue="name"/>
		</layout:row>
        <layout:row>
            <field:date path="effectiveDate" styleId="effectiveDate" labelCode="codevalue_effective_date" placeholder="Effective Date" time="true" />
            <field:date path="expiryDate" styleId="expiryDate" labelCode="codevalue_expiry_date" placeholder="Effective Date" time="true" />
        </layout:row>
		<layout:row>
            <field:input path="position" styleId="position" labelCode="codevalue_position" placeholder="Position"  />
		</layout:row>
		</layout:update>
</layout:page>
<script type="text/javascript">
jQuery(document).ready(function(){
	//$("#type").attr("disabled","disabled");
});
</script>