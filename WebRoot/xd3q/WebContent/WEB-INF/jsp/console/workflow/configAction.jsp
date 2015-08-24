<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%
	request.setCharacterEncoding("GBK");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="" name="userform">
<table>
<tr>
	<td width="20%"></td>
	<td>动作类型:</td>
	<td>
		<select name="actionType" id="actionType">
		   <option value=1>SQL</option>
		   <option value=2>存储过程</option>
		</select>
		<script type="text/javascript">document.getElementById("actionType").value=${actionType}</script>
	</td>
	<td width="20%"></td>	
</tr>
<tr>
	<td width="20%"></td>
	<td>语句:</td>
	<td >
	    <textarea rows="15" cols="100" id="actionSQL" name="actionSQL">${actionSQL}</textarea>
	</td>
	<td width="20%"></td>	
</tr>
<tr>	
	<td width="20%"></td>
	<td width="20%"></td>	
	<td  align="center">
	    <input type="hidden" value="${actionid }" name="actionid" id="actionid"/>
	    <input type="hidden" value="${nodeid }" name="nodeid" id="nodeid"/>
	    <input type="hidden" value="${processid }" name="processid" id="processid"/>
		<input type="button" name="submituser" id="submituser" value="提交" onclick="addActionSQL()"></input>		
		<input type="button" name="close" id="close" value="关闭" onclick="closeWindow()"></input>
	</td>
	<td width="20%"></td>	
</tr>

<tr>
	<td><div id="runInfoDiv" onclick="this.hide();"></div></td>
</tr>

</table>
</form>
</body>
</html>
<script type="text/javascript">

function addActionSQL(){	
		var param = Form.serialize('userform');
		param += "&action=saveOrUpdateAction";
		new AjaxEngine('workflowController.spr', 
			   {method:"post", parameters: param, onComplete: callBackHandle});
}

function customCallBackHandle(transport){
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
}
function closeWindow(){
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
}
</script>
