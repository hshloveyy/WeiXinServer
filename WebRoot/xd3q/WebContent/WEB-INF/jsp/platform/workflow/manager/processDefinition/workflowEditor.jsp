
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作流编辑器</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/swfobject.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/json2.js"></script>
</head>
<body>

<div id="workflowEditor" class="chart"></div>
</body>
</html>

<script type="text/javascript" defer="defer">
	//WorkFlow.swf查看子流程调用此方法
	function _viewSubProcess(processName, version)
	{
		var para="&&processname="+processName+"&&version="+version;
		_getMainFrame().maintabs.addPanel("子流程查看："+processName,null,contextPath + '/platform/workflow/manager/processDefinitionController.spr?action=_viewSubProcess'+ para);
		
	}

</script>

<script type="text/javascript" defer="defer">
	swfobject.embedSWF('<%=request.getContextPath()%>/swf/WorkFlow.swf',
					'workflowEditor',
					'100%',
					'100%',
					'9.0.0',
					'expressInstall.swf',<%=request.getAttribute("flexPara")%>
					);
</script>
