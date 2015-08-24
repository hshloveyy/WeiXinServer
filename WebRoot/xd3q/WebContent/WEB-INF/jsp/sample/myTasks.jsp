<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="" method="post" name="dataForm">
<table width="90%">
	
	<tr>
		<td align="center"><h2>任务列表</h2></td>
	</tr>
	<tr>
		<td>
		<table width="90%" border="1">
			<tr>
				<td align="center">功能模块</td>
				<td align="center">任务名称</td>
				<td align="center">任务创建时间</td>
				<td align="center">审批</td>
				<td align="center">流程状态</td>
			</tr>
			<c:forEach items="${tasks}" var="task">
			<tr>
				<td align="center">${task.boId }</td>
				<td align="center">${task.taskName }</td>
				<td align="center">${task.insCreateTime }</td>
				<td align="center"><a href="#" onclick="doJob('${task.processUrl}','${task.processId}','${task.taskId}','${task.businessId }')">审批</a> </td>	
				<td align="center"><a href="#" onclick="showImg('${task.taskId}')">流程图</a> </td>			
			</tr>
			</c:forEach>
		</table>
		</td>
	</tr>
	<tr>
			<td colspan="6" align="center"><input type="button" value="增加请假单" 	onclick="doSubmit();"></td>
	</tr>
	<tr>
			<td colspan="6"><!-- 运行信息 -->
			<div id="runInfoDiv" onclick="this.hide();"></div>
			</td>
	</tr>
</table>
</form>
</body>
</html>
<script language="javascript">
	function doSubmit() {
		dataForm.action = 'sampleController.spr?action=addApply';
		dataForm.submit();
		//form参数序列化，成为url方式提交
		//var param = Form.serialize("dataForm");
		//param += "&action=saveApply";
		//new AjaxEngine('sampleController.spr', {
		//	method :"post",
		//	parameters :param,
		//	onComplete :callBackHandle
		//});
	}
	function doJob(url,processId,taskId,businessRecordId)
	{
		//alert(id);
		//alert(userid);
		//alert(url+'&processId='+processId+'&taskId='+taskId+'&businessRecordId='+businessRecordId);
		dataForm.action = url+'&processId='+processId+'&taskId='+taskId+'&businessRecordId='+businessRecordId;
		dataForm.submit();
	}
	function showImg(taskId)
	{
		var par="toobar=no,menubar=no,top=200,left=100,width=880,height=500,resizable=yes,titlebar=no,scrollbars=yes";
		var url = "common/showProcessStateImg.jsp?taskId="+taskId;
		window.open(url,"CreateWin",par);
	}
	//用户自定义回调
	function customCallBackHandle(transport) {
		//alert(transport.responseText);
	}
</script>