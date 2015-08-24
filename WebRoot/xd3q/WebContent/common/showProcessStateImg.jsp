<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/jbpm.tld" prefix="jbpm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程状态图</title>
<style>
<!--
.cToken {
	border: 4px solid red;
	border-style:groove;
	background-color:transparent;
}

.cToken1 {
	border: 4px solid transparent;
	border-style:groove;
	background-color:transparent;
}
-->
</style>
<script type="text/javascript">
	function blinklink() {
		var currentToken = document.getElementById('currentToken');

		if (currentToken.className == 'cToken1') {
			currentToken.className = 'cToken'
		} else {
			currentToken.className = 'cToken1'
		}

		timer = setTimeout("blinklink()", 500)
	}
	function stoptimer() {
		clearTimeout(timer);
	}
	//window.setTimeout("stoptimer()", 1000);
</script>
</head>
<body onload="blinklink()">
<%
	long taskId = Long.parseLong(request.getParameter("taskId"));
%>
<jbpm:processimage task="<%=taskId%>" />
</body>
</html>