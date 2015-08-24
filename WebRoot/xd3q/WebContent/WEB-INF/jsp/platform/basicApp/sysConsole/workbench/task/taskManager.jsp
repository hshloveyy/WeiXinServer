<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="toolbarDiv" ></div>
<form name="queryForm" id="queryForm" class="search">
	<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%">事务信息：</td>
		<td width="30%">
		  	<input type="hidden" id="businessNote.fieldName" name="businessNote.fieldName" value="businessNote">
			<input type="text" class="inputText" id="businessNote.fieldValue" name="businessNote.fieldValue" >
			<input type="hidden" id="businessNote.option" name="businessNote.option" value="like">
		</td>
		<td width="15%" align="right">待办任务：</td>
		<td width="40%">
		  	<input type="hidden" id="name_.fieldName" name="name_.fieldName" value="name_">
			<input type="text" class="inputText" id="name_.fieldValue" name="name_.fieldValue" >
			<input type="hidden" id="name_.option" name="name_.option" value="like">
		</td>
	</tr>
	<tr>
		<td align="right" width="15%">送达时间：</td>
		<td width="30%"> 
			<div id="taskCreateTimeDiv"></div>
		  	<input type="hidden" id="taskCreateTime.fieldName" name="taskCreateTime.fieldName" value="taskCreateTime">
	  		<input type="hidden" id="taskCreateTime.dataType" name="taskCreateTime.dataType" value="D"></input>
			<input type="hidden" id="taskCreateTime.option" name="taskCreateTime.option" value="equal">
		</td>
		<td width="15%" align="right">提交人：</td>
		<td width="40%"> 
			<div id="creatorIdDiv"></div>
		  	<input type="hidden" id="creatorId.fieldName" name="creatorId.fieldName" value="creatorId">
			<input type="hidden" id="creatorId.option" name="creatorId.option" value="equal">
		</td>
	</tr>
	</table>
</form>
<div id="gridDiv"></div>
</body>
<script type="text/javascript" defer="defer">

function _manager(value,metadata,record){
	return	'<a href="#" onclick="_view(\''+record.data.processUrl+'\',\''+record.data.task_id
		+'\',\''+record.data.businessId+'\',\''+record.data.name_+'\',\''+record.data.nodeId+'\');" style="color:green" title="'+record.data.name_+'">'
		+'办理</a> <a href="#" onclick="_viewMap(\''+record.data.task_id
		+'\');" style="color:green" title="'+record.data.name_+'">'
		+'查看流程图</a>';
}

function _view(processUrl,taskId,businessId,taskName,nodeId){
	var url = "<%=request.getContextPath()%>/"+processUrl+"&workflowNodeDefId="+nodeId+"&workflowTaskId="+taskId+"&businessId="+businessId+"&taskName="+taskName;
	_getMainFrame().maintabs.addPanel('办理待办任务',gridDiv_grid,url);	
}

function _viewMap(taskId){
	var url = "<%=request.getContextPath()%>/platform/workflow/manager/processDefinitionController.spr?action=_showProcessTask&taskId="+taskId;
	_getMainFrame().maintabs.addPanel('查看流程图',gridDiv_grid,url);	
}

function _search()
{
	var param = Form.serialize('queryForm');
	reload_gridDiv_grid(param);
}

function _resetForm()
{
	document.getElementById('queryForm').reset();
}

</script>
<fisc:dictionary dictionaryName="YUSER" divId="creatorIdDiv" hiddenName="creatorId.fieldValue" isNeedAuth="false"></fisc:dictionary>
<fisc:calendar applyTo="" divId="taskCreateTimeDiv" fieldName="taskCreateTime.fieldValue"></fisc:calendar>
<fisc:grid divId="gridDiv" handlerClass="com.infolion.platform.basicApp.sysConsole.workbench.task.web.grid.TaskGrid"
 height="350" pageSize="12" needAutoLoad="true" tableName="${tableName}" whereSql="${whereSql}" title="查看待办事项"></fisc:grid>
<script type="text/javascript" defer="defer">

var toolbars = new Ext.Toolbar({
	items:[
			'-',
			{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
			{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-'
		],
	renderTo:"toolbarDiv"
});


/**
* EXT 布局
*/
Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		id:'viewport',
		layout:"border",
		items:[{
			region:"center",
			items:[{
					region:"north",
					contentEl:'toolbarDiv',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					contentEl:'queryForm'
				},		
				{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            //title:"查看待办事项",
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'gridDiv'
				}]
			}]
	})

});
</script>
</html>