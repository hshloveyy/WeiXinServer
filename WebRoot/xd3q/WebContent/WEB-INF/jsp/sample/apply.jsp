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
		<td align="center">
		<h2>请假单</h2>
		</td>
	</tr>
	<tr>
		<td>
		<table width="90%" border="1">
			<tr>
				<td colspan="6" align="center"><h3>表单信息</h3></td>
			</tr>
			<tr>
				<td align="right">姓名：</td>
				<td align="left"><input type="text" name="name"
					value="${apply.name }"></td>
				<td align="right">请假天数：</td>
				<td align="left"><input type="text" name="applyLong"
					value="${apply.applyLong}"></td>
				<td align="right">请假时间：</td>
				<td align="left"><input type="text" name="applyTime"
					value="${apply.applyTime }"></td>
			</tr>
			<tr>
				<td align="right">请假原因：</td>
				<td align="left" colspan="5"><textarea rows="2" cols="40"
					name="applyReson">${apply.applyReson}</textarea></td>
			</tr>
			<tr>
				<td colspan="6" align="center"><h3>审批意见</h3></td>
			</tr>
			<tr>
				<td align="right">是否同意：</td>
				<td align="left"><select name="workflowIsAllow">
					<option value="yes">同意</option>
					<option value="no">不同意</option>
				</select></td>
				<td align="right">审批意见：</td>
				<td align="left" colspan="3"><textarea rows="2" cols="40"
					name="workflowExamine"></textarea></td>
			</tr>
			<tr>
				<td align="center" colspan="6"><input type="button" value="提交审批" onclick="doSubmit();"></td>
			</tr>
			<tr>
				<td colspan="6" align="center"><h3>流程历史</h3></td>
			</tr>
			<tr>
				<td colspan="6">
				<table width="90%" border="1">
					<tr>
						<td align="center">任务名称</td>
						<td align="center">任务创建时间</td>
						<td align="center">任务结束时间</td>
						<td align="center">是否同意</td>
						<td align="center">审批意见</td>
						<td align="center">审批人</td>
					</tr>
					<c:forEach items="${hisTasks}" var="task">
						<tr>
							<td align="center">${task.taskName }</td>
							<td align="center">${task.taskCreateTime }</td>
							<td align="center">${task.taskEndTime }</td>
							<td align="center">${task.isAllow }</td>
							<td align="center">${task.examine }</td>
							<td align="center">${task.examinePerson }</td>
						</tr>
					</c:forEach>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="6"><!-- 运行信息 -->
		<div id="runInfoDiv" onclick="this.hide();"></div>
		</td>
	</tr>
</table>
<input type="hidden" name="id" value="${apply.id }"> 
<input type="hidden" name="workflowTaskId" value="${taskId }"></form>
</body>
</html>
<script language="javascript">
	function doSubmit() {
		dataForm.action = 'sampleController.spr?action=saveApply';
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
	//用户自定义回调
	function customCallBackHandle(transport) {
		//alert(transport.responseText);
	}
</script>