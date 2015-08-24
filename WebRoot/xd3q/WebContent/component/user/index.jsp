<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>用户组件示例页面</title>
</head>
<body>
<table>
	<tr>
		<td>
			<fisc:user boProperty="PurchaseOrder" boName="creator" userId="1" isLoginUser="true"></fisc:user>
		</td>
	</tr>
</table>
</body>
</html>