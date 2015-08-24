<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建表</title>
</head>
<body>
<div id="">
<form name="fromPanel">
<table width="80%">
	<tr>
		<td>系统名称:</td>
		<td>
			<input type="text" name="systemName">
		</td>
		<td>数据库类型:</td>
		<td>
			<input type="text" name="dbType">
		</td>
	</tr>
	<tr>
		<td>连接串</td>
		<td colspan="3">
			<input type="text" name="dbUrl" size="80" value="jdbc:oracle:thin:@192.168.39.154:1521:orcl"/>
		</td>
	</tr>
	<tr>	
		<td>用户名:</td>
		<td>
			<input type="text" name="userName" value="xmdp1">
		</td>
		<td>密码:</td>
		<td>
			<input type="password" name="password" value="xmdp1">
		</td>
	</tr>
	<tr><td>模块:</td><td>
						  CRM:<input type="checkbox" id="modular1" value="YCRM" />
					 </td>
				 </tr>
	<tr>
		<td colspan="4"><input type="button" value="执行" onclick="execute()"></input><input type="button" value="取消"></input></td>
	</tr>
</table>
</form>
</div>



<script type="text/javascript" defer="defer">
function execute(){
	var selModuled='';
	if($('modular1').checked==true){
		selModuled = $('modular1').value;
	}
	var para = Form.serialize('fromPanel')+'&modular='+selModuled;
	var param = '?action=execute&'+para;
	new AjaxEngine('<%=request.getContextPath()%>/platform/basicApp/dpframework/dataTransfers/dataTransferController.spr', 
			{method:"post", parameters: param, onComplete: callBackHandle});	
}

</script>
</body>
</html>