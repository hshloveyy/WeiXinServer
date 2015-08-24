
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预算费用</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/swfobject.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/json2.js"></script>
</head>
<body>

<div id="flexDiv" class="chart"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
	swfobject.embedSWF('<%=request.getContextPath()%>/swf/SpreadSheet.swf',
					'flexDiv',
					'100%',
					'100%',
					'9.0.0',
					'expressInstall.swf',<%=request.getAttribute("flexPara")%>
					);
</script>
