<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
</head>
<script type="text/javascript">
	function closeForm(){
			top.ExtModalWindowUtil.markUpdated();
	 		top.ExtModalWindowUtil.close();
	}
	function saveForm(){
		var param=Form.serialize('mainForm');
		param = '?action=saveProject&'+param;
		new AjaxEngine('investmentController.spr', 
				   {method:"post", parameters: param, onComplete: callBackHandle});
	}
	//回调函数
	function customCallBackHandle(transport){
		var responseUtil1 = new AjaxResponseUtils(transport.responseText);
		var customField = responseUtil1.getCustomField("coustom");
		
		top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
		$('pid').value=customField.PID;
	}
	Ext.onReady(function(){
		var d1 = new Ext.form.DateField({
	   		format:'Y-m-d',
			id:"projectTime",
			name:"projectTime",
			width: 150,
			applyTo:'projectTime'
	   	});
	});	
</script>
<body>
	<form name="mainForm">
		<input type="hidden" name="ipId" value="${ipId}"/>
		<table>
			<tr>
				<td align="right">投资项目编号:</td>
				<td><input type="text" name="investmentCode" /></td>
			</tr>
			<tr>
				<td align="right">项目时间:</td>
				<td><input type="text" name="projectTime" id="projectTime"/></td>
			</tr>
			<tr>
				<td align="right">金额:</td>
				<td><input type="text" name="count" /></td>
			</tr>
			<tr>
				<input type="hidden" name="pid" id="pid"/>
				<td align="right"><input type="button" value="保存" onclick="saveForm()"/></td>
				<td align="left"><input type="button" value="关闭" onclick="closeForm()"/></td>
			</tr>
			
		</table>
	
	</form>
</body>
</html>