<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程管理页面</title>
<link href="<%=request.getContextPath()%>/css/public.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript">
Ext.onReady(function(){
	var mainForm = new Ext.form.FormPanel({
		renderTo:document.body,
		layout:'form',
		autoHeight:true,
		items:[{
			contentEl:'workflowHistory',
			title:'流程历史',
			height:330,
			autoWidth:true,
			collapsed:false,
			collapsible: true
		},{
			html:'<iframe src="<%=request.getContextPath()%>/common/showProcessStateImg.jsp?taskId=${taskId}" width="100%"  height="100%" id="content" ></iframe>',
			title:'流程图',
			height:800,
			collapsible: true
		}]
	});
});//end of ext

</script>
<div id="workflowHistory" style="position: relative" class="x-hide-display"></div>
</body>
</html>
<fiscxd:workflow-taskHistory divId="workflowHistory" businessRecordId="${businessRecordId}" width="825" url="${url}"></fiscxd:workflow-taskHistory>
