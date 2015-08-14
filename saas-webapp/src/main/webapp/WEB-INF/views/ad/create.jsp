<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@	taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@	taglib prefix="field" tagdir="/WEB-INF/tags/layout/field" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<layout:page>
	<layout:create modelAttribute="aDConfig" titleCode="create_ad" multipart="true">
		<layout:row>
			<field:input styleId="name" path="name" labelCode="ad_name" placeholder="Name" required="true"/>
		</layout:row>
		<layout:row>
			<field:input styleId="host" path="host" labelCode="ad_host" placeholder="Host" required="true"/>
		</layout:row>
		<layout:row>
			<field:input styleId="port" path="port" labelCode="ad_port" placeholder="Port" required="true" />
		</layout:row>
		<layout:row>
			<field:input styleId="loginDN" path="loginDN" labelCode="ad_logindn" placeholder="LoginDN" required="true"/>
		</layout:row>
		<layout:row>
			<field:password styleId="password" path="password" labelCode="ad_password" placeholder="Password" required="true" showPassword="true"/>
		</layout:row>
		<layout:row>
			<field:input styleId="searchDN" path="searchDN" labelCode="ad_searchdn" placeholder="SearchDN" required="true"/>
		</layout:row>
		<layout:row>
			<div id="condition">
				<div class="row-fluid" style="margin-left:70px">
						<font>Custom - AD</font><button type="button" id="btnAdd" value="" class="btn" style="margin-left:30px"><span class="icon-plus"></span></button>
						<button type="button" id="btnTest" value="" class="btn btn-primary" style="margin-left:70px">Test Connect</button>
				</div>
				<div id="relevance" class="control-group">
				</div>
			</div>	
		</layout:row>
		
	</layout:create>
</layout:page>
<script type="text/javascript">

	var customOptions = "";
	var adOptions = "";
	var size = <c:out value='${fn:length(aDConfig.customADs)}'/>;
	var canConnect = ${aDConfig.canConnect};
	
	$(function(){
		
		if(!canConnect){
			disabledBtn();
		}else{
			unDisabledBtn();
		}
		
		$("#btnAdd").click(function(){
			addRow("#relevance");
		});
		
		$("#btnRemove").live('click', function() {
			removeRow($(this));
		});
		
		$("input[name!='name']").live('change',function(){
			disabledBtn();
		});
		
		$("#btnTest").click(function(){
			testConnect();
			$(".controls-row").remove();
		});
	});
	
	function disabledBtn()
	{
		$('#Submit').attr('disabled',true);
		$('#btnAdd').attr('disabled',true);
		$('#btnTest').attr('disabled',false);
	}
	function unDisabledBtn()
	{
		$('#Submit').attr('disabled',false);
		$('#btnAdd').attr('disabled',false);
		$('#btnTest').attr('disabled',true);
	}
	
	function testConnect() {
		var flag = true;
		var data = $("#form").serializeArray();
		$("label[name='connectADFailed']").remove();
		jQuery.each( data, function(i, field){
			if(field.value == ""){
				flag = false;
				var str = "input[name='"+field.name+"']";
				$(str).parent().append("<label name='connectADFailed' style='color:red'>May not be null</label>");
			}
		});
		if(!flag){
			return;
		}
		
		jQuery.ajax({
			type : "post",
			async : false,
			data : data,
			url :  "${pageContext.request.contextPath}/ad/testconnect",
			success : function(data) {
				if (data == "0") {
					$("#btnTest").parent().append("<label name='connectADFailed' style='color:red;margin-left:220px;'>Connect AD Failed</label>");
				} else if (data == "1") {
					unDisabledBtn();
				} else if (data == "2") {
					$("#btnTest").parent().append("<label name='connectADFailed' style='color:red;margin-left:220px;'>No ADFields</label>");
				}
			}
		});
	}
	
	function populateOptis(){
		customOptions = "" ;
		adOptions = "";
		jQuery.ajax({
			type : "get",
			async : false,
			url :  "${pageContext.request.contextPath}/ad/getoptions",
			success : function(data) {
				$.each(data, function(index, list) {
					if (index == 0) {
						$.each(list, function(index1, field) {
							customOptions = customOptions + '<option value='
									+ field.value + '>' + field.key + '</option>';
						});
					} else if (index == 1) {
						$.each(list, function(index1, field) {
							adOptions = adOptions + '<option value='
									+ field.value + '>' + field.key + '</option>';
						});
					} 
				});
			}
		});
	}
	
	function formatRowStr(i) {
		var customField = 'customADs[' + i + '].customField';
		var adField = 'customADs[' + i + '].adField';

		var str = "";
		str += "<div class='controls-row' id='filter" + i
			+ "' style='margin-top:10px;margin-bottom:5px'>";
		str+="<div style='float:left;margin-top:5px;margin-left:80px;margin-right:10px;'>Custom Field</div>";
		str += "<div style='float:left;'>"
				+ "<select data-type='select' id='operator" + i + "0'"
				+ " name='" + customField + "'>" + customOptions
				+ "</select>" + "</div>";
		str+="<div style='float:left;margin-top:5px;margin-left:10px;margin-right:10px;'>AD Field</div>";
		str += "<div style='float:left;'>"
				+ "<select data-type='select' id='operator" + i + "1'"
				+ " name='" + adField + "'>" + adOptions
				+ "</select>" + "</div>";
		str += "<div style='float:left;' class='span1'>"
				+ "<button type='button' id='btnRemove' class='btn' value=''><span class='icon-minus'></span></button>"
				+ "</div>";
		str += "</div>";
		return str;
	}

	function addRow(containerId) {
		populateOptis();
		var rowStr = formatRowStr(size);
		$(containerId).append(rowStr);// last .row-fluid div is btnAdd'div
		size++;

	}
	
	function removeRow($clickRow) {
		$clickRow.closest(".controls-row").remove();// delete btnRemove's cloest
		// .row-fluid div
	}
</script>




