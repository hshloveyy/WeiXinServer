<!-- 此JSP功能已经被 extProcessDefMgr.jsp 替代。 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程节点配置</title>
</head>
<frameset cols="201,*">
<frame name="tree" src="<%=request.getContextPath()%>/workflow/extProcessDefinitionController.spr?action=tree">
<frame name="dateView" src="<%=request.getContextPath()%>/workflow/extProcessDefinitionController.spr?action=page">
</frameset>
<body>

</body>

</html>