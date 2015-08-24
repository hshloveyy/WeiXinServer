<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<html>
<head>
<title>XMDP</title>
<%
    String cognosUrl=new String("");
	String contextPath=(String)request.getAttribute("contextPath");
    cognosUrl="http://cognos.ffcs.cn/cognos8/cgi-bin/cognos.cgi";
    System.out.println("cognosUrl>>>>>>>>"+cognosUrl);
%>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="loginCognos" action="<%=cognosUrl%>" method="post">
<input type="hidden" name="CAMUsername">
<input type="hidden" name="CAMPassword">
<input type="hidden" name="m_redirect">
</form>
</body>
</html>

<script language="javascript">
document.forms[0].CAMUsername.value='admin';
document.forms[0].CAMPassword.value='ffcs';
document.forms[0].m_redirect.value='http://localhost:8000/xmcrm/index/indexMainController.spr';
document.forms.loginCognos.submit();
</script>