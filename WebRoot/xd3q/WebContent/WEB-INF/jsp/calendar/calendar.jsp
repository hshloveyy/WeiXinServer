
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.infolion.platform.bo.service.BusinessObjectService"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日历</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/lib/swfobject.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/lib/json2.js"></script>
</head>
<body>

<div id="flexDiv" class="chart"></div>
</body>
</html>
<script type="text/javascript" defer="defer">

	// CalendarApp.swf创建业务活动时调用此方法
	function _businessActivityCreate(calActivityId,boName,boText)
	{
		var para="&&calActivityId="+calActivityId+"&&boName="+boName;
		_getMainFrame().maintabs.addPanel(boText+"业务活动创建",null,contextPath + '/BDP/calendar/calendarController.spr?action=_businessActivityCreate'+ para,saveCallBack,true);
	}

	// 业务活动创建成功后调用此方法
	function saveCallBack()
	{
		flexDiv.focus();
		flexDiv.refreshCalActivity();
	}
	
	// CalendarApp.swf查看业务活动时调用此方法
	function _businessActivityView(businessId,boName,boText)
	{
		var para="&&businessId="+businessId+"&&boName="+boName;
		_getMainFrame().maintabs.addPanel(boText+"业务活动查看",null,contextPath + '/BDP/calendar/calendarController.spr?action=_businessActivityView'+ para);
	}
</script>

<script type="text/javascript" defer="defer">
	swfobject.embedSWF('<%=request.getContextPath()%>/swf/CalendarApp.swf',
					'flexDiv',
					'100%',
					'100%',
					'9.0.0',
					'expressInstall.swf',<%=request.getAttribute("flexPara")%>
					);
</script>
