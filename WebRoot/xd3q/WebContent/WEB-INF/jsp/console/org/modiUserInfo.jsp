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
	<td>
		<input type="hidden" name="deptId" id="deptId" value="${user.deptId}"></input>
	所属部门:
	</td><td>
		<input readonly="readonly" type="test" name="deptName" id="deptName" value="${user.deptName}" size="15"></input>
	</td>
	<td width="20%"></td>	
</tr>
<tr>
	<td width="20%"></td>
	<td>用户名:</td>
	<td>
		<input type="hidden" name="userId" id="userId" value="${user.userId}"></input>
		<input type="text" name="userName" id="userName" value="${user.userName}" size="15"></input>
		<input type="hidden" name="olduserName" id="olduserName" value="${user.userName}"></input>
		<input type="hidden" name="deptUserId" id="deptUserId" value="${user.deptUserId}"></input>
	</td>
	<td width="20%"></td>
</tr>
<tr>
	<td width="20%"></td>	
	<td>密码:</td>
	<td>
		<input type="password" name="password" id="password" value="${user.password}" size="15"></input>
	</td>
	<td width="20%"></td>	
</tr>
<tr>
	<td width="20%"></td>	
	<td>姓名:</td>
	<td>
		<input type="text" name="realName" id="realName" value="${user.realName}" size="15"></input>
	</td>
	<td width="20%"></td>	
</tr>
<tr>
	<td width="20%"></td>
	<td>性别:</td>
	<td>
		<div id="sexDiv"></div>
	</td>
	<td width="20%"></td>	
</tr>
<tr>
	<td width="20%"></td>
	<td>职位描述:</td>
	<td >
		<input type="text" name="positionDes" id="positionDes" value="${user.positionDes}" size="30"></input>
	</td>
	<td width="20%"></td>	
</tr>
<tr>
	<td width="20%"></td>
	<td>身份证:</td>
	<td>
		<input type="text" name="idCard" id="idCard" value="${user.idCard}" size="15"></input>
	</td>
	<td width="20%"></td>	
</tr>
<tr>
	<td width="20%"></td>
	<td>E_MAIL:</td>
	<td>
		<input type="text" name="EMail" id="EMail" value="${user.EMail}" size="15"></input>
	</td>
	<td width="20%"></td>	
</tr>
<tr>
	<td width="20%"></td>
	<td>住址:</td>
	<td >
		<input type="text" name="address" id="address" value="${user.address}" size="30"></input>
	</td>
	<td width="20%"></td>	
</tr>
<tr>
	<td width="20%"></td>
	<td>备注:</td>
	<td >
		<input type="text" name="cmd" id="cmd" value="${user.cmd}" size="30"></input>
	</td>
	<td width="20%"></td>	
</tr>
<tr>
	<td width="20%"></td>
	<td>工号:</td>
	<td >
		<input type="text" name="employeeNo" id="employeeNo" value="${user.employeeNo}" size="30"></input>
	</td>
	<td width="20%"></td>	
</tr>
<tr>
	<td width="20%"></td>
	<td>手机号码:</td>
	<td >
		<input type="text" name="mobile" id="mobile" value="${user.mobile}" size="15"></input>
	</td>
	<td width="20%"></td>	
</tr>
<tr>	
	<td width="20%"></td>
	<td width="20%"></td>	
	<td  align="center">
		<input type="button" name="submituser" id="submituser" value="提交" onclick="addUser()"></input>
		
		<input type="button" name="close" id="close" value="关闭" onclick="closeWindow()"></input>
		<input type="button" name="saveNo" id="saveNo" value="保存工号" onclick="save1No()"></input>
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
function clearUser(){
	document.userform.userId.value = '';
	document.userform.userName.value = '';
	document.userform.realName.value = '';
	document.userform.sex.value = '';
	document.userform.address.value = '';
	document.userform.idCard.value = '';
	document.userform.EMail.value = '';
}
function save1No(){
		var param = Form.serialize('userform');
		param += "&action=saveNo";
		new AjaxEngine('orgController.spr', 
			   {method:"post", parameters: param, onComplete: callBackHandle});

}

function addUser(){
	//先判断新旧的员工用户名是否相同如果不同就要再判断员工是否存在
	if (document.userform.olduserName.value != document.userform.userName.value){
		Ext.Ajax.request({
			url: 'orgController.spr?action=queryUserIsExist&username='+document.userform.userName.value,
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				if (responseArray.count > 0){
					top.ExtModalWindowUtil.alert('提示','已经有相同的用户名的用户存在！');
				}
				else{
					var param = Form.serialize('userform');
						param += "&action=addUser";
						new AjaxEngine('orgController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
				}
			}
		});
	}
	else{
		var param = Form.serialize('userform');
		param += "&action=addUser";
		new AjaxEngine('orgController.spr', 
			   {method:"post", parameters: param, onComplete: callBackHandle});
	}
}

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	if (document.userform.userId.value == ''){
		document.userform.resetuser.disabled = false;
	}
	top.ExtModalWindowUtil.markUpdated();
}
function closeWindow(){
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
}
</script>
<fiscxd:dictionary divId="sexDiv" fieldName="sex" dictionaryName="BM_SYS_SEX" selectedValue="${user.sex}"></fiscxd:dictionary>
