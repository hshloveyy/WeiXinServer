<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ 
include file="/common/commons.jsp"
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程定义</title>
</head>
<body>
<form action="" name="queryForm">
	<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
	<tr>
		<td align="right" width="11%">业务流程定义名：</td>
		<td width="22%">
			<input type="hidden" id="boProcessName.fieldName" name="boProcessName.fieldName" value="boProcessName"></input>
			<input type="text" id="boProcessName.fieldValue" name="boProcessName.fieldValue" ></input>
			<input type="hidden" id="boProcessName.option" name="boProcessName.option" value="like"></input>
		</td>
		<td align="right" width="11%">流程定义名：</td>
		<td width="22%">
			<input type="hidden" id="processDefinitionName.fieldName" name="processDefinitionName.fieldName" value="processDefinitionName"></input>
			<input type="text" id="processDefinitionName.fieldValue" name="processDefinitionName.fieldValue" ></input>
			<input type="hidden" id="processDefinitionName.option" name="processDefinitionName.option" value="like"></input>
		</td>
		<td align="right" width="11%">应用模块：</td>
		<td width="22%">
			<input type="hidden" id="appModel.fieldName" name="appModel.fieldName" value="appModel"></input>
			<input type="text" id="appModel.fieldValue" name="appModel.fieldValue" ></input>
			<input type="hidden" id="appModel.option" name="appModel.option" value="like"></input>
		</td>
	</tr>
	<tr>
		<td align="right"></td>
		<td>
		</td>
		<td align="right"></td>
		<td>
		</td>
		<td align="center">
			<input type="button" value="查找" onclick="getQueryCondition()"></input>
			<input type="reset" value="清空"></input>
		</td>
	    <td>
		</td>
	</tr>
	</table>
</form>
<div id="processgrid"></div>
</body>
<script type="text/javascript" defer="defer">
function sCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	customField.sql;
}
function findCreditEntryInfo()
{
	alert(Form.serialize('queryForm'));
	var para = Form.serialize('queryForm');
	new AjaxEngine('<%= request.getContextPath() %>/queryConditionController.spr?action=getQueryExpression&' ,
			 {method:"post", parameters: Form.serialize('queryForm'), onComplete: sCallBackHandle});
}
</script>
</html>
<fisc:grid divId="processgrid" handlerClass="" url="" whereSql="" title="流程定义" pageSize="20" height="400" width="100%"></fisc:grid>