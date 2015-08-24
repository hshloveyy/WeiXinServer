<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


</head>
<body>
<!--form name="loginForm" action="http://<%=request.getServerName()%>/xjrj/login.do"-->
<form name="loginForm" action="http://172.16.18.199:81/xjrj/login.do"-->
  <input type="hidden" name="userName" value="${userName}">
  <input type="hidden" name="password" value="${password}">
  <input type="hidden" name="isMD5" value="1">
</form>
<script>
   document.getElementById('loginForm').submit();
</script>
</body>
</html>