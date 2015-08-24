<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年10月16日 09点40分32秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>业务对象Community(Community)首页页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社区首页</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/sysConsole/portlet/communityIndex.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">

</form>
</div>

<div id="div_east" class="x-hide-display"></div>
<div id="div_west" class="x-hide-display"></div>
</body>
<script type="text/javascript" defer="defer">
	var communityid = '${community.communityid}';
</script>
</html>
