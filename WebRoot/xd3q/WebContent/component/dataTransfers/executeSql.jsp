<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年07月21日 19点26分29秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>执行SQL脚本文件
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>执行SQL脚本文件</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="center" height="10" width="15%">SQL脚本文件名称:</td>
		<td colspan="3" align="left" height="10" width="75%">
			<input class="inputText" type="text" id="fileName" name="fileName" value="insert.sql">
		</td>
	</tr>
	<tr>
		<td width="15%" align="center" height="10"><input type="button" onclick="_executeSqlScript()" value="执行SQL脚本文件到当前容器连接的DB">
		</td>
	</tr>
</table>
</form>
</div>
<script type="text/javascript" defer="defer">
/**
 * 执行SQL脚本文件
 */
function _executeSqlScript()
{
	var param = Form.serialize("mainForm");
	//alert(param);
	new AjaxEngine('<%=request.getContextPath()%>/authManage/userLoginController.spr?action=executeSqlScript', 
			{method:"post", parameters: param, onComplete: callBackHandle});
}
</script>
</html>
