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
<table width="70%">
	<tr>
		<td align="center"><h2>流程审批</h2></td>
	</tr>
	<tr>
		<td>
		<table width="90%" border="1">
			<tr>
				<td align="right">当前状态：</td>
				<td align="center"><input type="text" name="state"></td>
				<td align="right">当前办理人：</td>
				<td align="center"><input type="text" name="currEditor"></td>
				<td align="right">经办人：</td>
				<td align="center"><input type="text" name="agentEditor"></td>
			</tr>
			<tr>
				<td align="right">下一步操作：</td>
				<td align="center"><input type="text" name="nextStep"></td>
				<td align="right">办理人：</td>
				<td align="center"><input type="text" name="selectEditor"></td>
				<td align="right">是否关注：</td>
				<td align="center"><select name="isFoces"><option value="1">是</option><option value="0">否</option> </select> </td>
			</tr>
			<tr>
				<td align="right">是否同意：</td>
				<td align="center"><select name="isAllow"><option value="1">是</option><option value="0">否</option> </select> </td>
				<td align="right">处理意见：</td>
				<td align="center" colspan="3"><textarea rows="2" cols="40" name="msg"></textarea> 
				</td>
			</tr>

			<tr>
				<td colspan="6" align="center"><input type="button" value="提交审批" 	onclick="doSubmit();"></td>
			</tr>
			<tr>
				<td colspan="6"><!-- 运行信息 -->
				<div id="runInfoDiv" onclick="this.hide();"></div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center"><h2>请假单</h2></td>
	</tr>
	<tr>
		<td>
		<table width="90%" border="1">
			<tr>
				<td align="right">姓名：</td>
				<td align="center"><input type="text" name="name" value="${apply.name }" readonly="readonly"></td>
				<td align="right">请假天数：</td>
				<td align="center"><input type="text" name="applyLong" value="${apply.applyLong }" readonly="readonly"></td>
				<td align="right">请假时间：</td>
				<td align="left"><input type="text" name="applyTime" value="${apply.applyTime}" readonly="readonly"></td>
			</tr>
			<tr>
				<td align="right">请假原因：</td>
				<td align="center" colspan="5"><textarea rows="2" cols="40" name="applyReson" readonly="readonly">${apply.applyReson}</textarea></td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td align="center"><h2>流程审批历史</h2></td>
	</tr>
	<tr>
		<td>
		<table width="70%">
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
<script language="javascript">
	function doSubmit() {
		//dataForm.action = 'sampleController.spr?action=sampleMethod';
		//dataForm.submit();
		//form参数序列化，成为url方式提交
		var param = Form.serialize("dataForm");
		param += "&action=sampleMethod";
		new AjaxEngine('sampleController.spr', {
			method :"post",
			parameters :param,
			onComplete :callBackHandle
		});
	}
	//用户自定义回调
	function customCallBackHandle(transport) {
		//alert(transport.responseText);
	}
</script>