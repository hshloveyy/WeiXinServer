<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务流程定义</title>
</head>
<body>
<fisc:grid divId="gridDiv" editable="false" needCheckBox="true" pageSize="12" 
 tableName="WF_BOPROCESSDEF F,YBUSINESSOBJECT B" handlerClass="com.infolion.platform.workflow.manager.web.grid.BPDefinitionGrid"
 whereSql=" AND F.BOID = B.BOID " height="380" needAutoLoad="true"></fisc:grid>
<fisc:dictionary hiddenName="processDefinitionName.fieldValue" dictionaryName="YDCPROCESSDEFNAME" divId="processDefinitionNameDiv" value="${main.processDefinitionName}" isNeedAuth="false"></fisc:dictionary>
<fisc:dictionary hiddenName="appModel.fieldValue" dictionaryName="YDAPPMODEL" divId="appModelDiv" value="${main.appModel}" isNeedAuth="false" ></fisc:dictionary>
<div id="toolbarDiv"></div>
<div id="div_center" class="search">
<form name="queryForm" id="queryForm" action="">
	<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">业务流程名称：</td>
		<td width="30%" >
		  	<input type="hidden" id="boProcessName.fieldName" name="boProcessName.fieldName" value="bpName">
			<input class="inputText" type="text" id="boProcessName.fieldValue" name="boProcessName.fieldValue" >
			<input type="hidden" id="boProcessName.option" name="boProcessName.option" value="like">
		</td>
		<td width="15%" align="right">JBPM流程名称：</td>
		<td width="40%" >
			<div id="processDefinitionNameDiv"></div>
		  	<input type="hidden" id="processDefinitionName.fieldName" name="processDefinitionName.fieldName" value="processDefinitionName">
			<input type="hidden" id="processDefinitionName.option" name="processDefinitionName.option" value="like">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">应用模块：</td>
		<td width="30%" >
		  	<div id="appModelDiv"></div>
		  	<input type="hidden" id="appModel.fieldName" name="appModel.fieldName" value="f.appModel">
			<input type="hidden" id="appModel.option" name="appModel.option" value="like">
		</td>
		<td width="55%" align="center" colspan="2">
		</td>
	</tr>
	</table>
</form>
</div>
<div id="gridDiv"></div>
</body>
<script type="text/javascript" defer="defer">
Ext.onReady(function(){
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"toolbarDiv"
	});

	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
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
					contentEl:'div_center'
				},		
				{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:"流程定义",
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'gridDiv'
				}]
			}]
	});

	/*
	var tabs = new Ext.Panel({
		id:'mainPanel',
        renderTo: document.body,
        autoWidth:false,
        loadMask:true,
        frame:true,
        baseCls:'scarch',
        //defaults:{autoHeight: true},
        items:[
				toolbars,
            	{contentEl:'queryForm',id:'queryForm1'},
            	{contentEl:'gridDiv',id:'gridDiv1'}
              ]
    });
*/
});

function _edit(value){
	var url = '<%=request.getContextPath()%>/workflow/boProcessDefinitionController.spr?action=edit&bpDefId='+value;
	_getMainFrame().maintabs.addPanel('编辑业务流程定义',gridDiv_grid,url);
	/*_getMainFrame().ExtModalWindowUtil.show('编辑业务流程定义',
			'<%=request.getContextPath()%>/workflow/boProcessDefinitionController.spr?action=edit&bpDefId='+value,
			'',
			callBackHandle,
			{width:700,height:304}
		);*/
}

function _view(value){
	var url = '<%=request.getContextPath()%>/workflow/boProcessDefinitionController.spr?action=view&bpDefId='+value;
	_getMainFrame().maintabs.addPanel('查看业务流程定义',null,url);
	/*_getMainFrame().ExtModalWindowUtil.show('查看业务流程定义',
			'<%=request.getContextPath()%>/workflow/boProcessDefinitionController.spr?action=view&bpDefId='+value,
			'',
			callBackHandle,
			{width:700,height:304}
		);*/
}

function createCallBack(transport){
	reload_gridDiv_grid("");
}

function _search()
{
	//alert("queryForm");
	var param = Form.serialize('queryForm');
	//alert(param);
	reload_gridDiv_grid(param);
}

function _resetForm()
{
	document.getElementById('queryForm').reset();
	dict_appModelDiv.select(0,true);
	dict_processDefinitionNameDiv.select(0,true);
}

function _create(){
	var url = '<%=request.getContextPath()%>/workflow/boProcessDefinitionController.spr?action=create';
	_getMainFrame().maintabs.addPanel('创建新的业务流程定义',gridDiv_grid,url);
	/*_getMainFrame().ExtModalWindowUtil.show('创建新的业务流程定义',
			'<%=request.getContextPath()%>/workflow/boProcessDefinitionController.spr?action=create',
			'',
			createCallBack,
			{width:700,height:304}
		);*/
}

function _copyCreate(){
	if (gridDiv_grid.selModel.hasSelection()){
		var records = gridDiv_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo('只能选择一条要复制创建的流程配置信息！');
		}
		var url = '<%=request.getContextPath()%>/workflow/boProcessDefinitionController.spr?action=copyCreate&bpDefId='+records[0].data.bpDefId;
		_getMainFrame().maintabs.addPanel('复制创建新的业务流程定义',gridDiv_grid,url);
		/*_getMainFrame().ExtModalWindowUtil.show('复制创建新的业务流程定义',
				'<%=request.getContextPath()%>/workflow/boProcessDefinitionController.spr?action=copyCreate&bpDefId='+records[0].data.bpDefId,
				'',
				createCallBack,
				{width:700,height:304}
				);*/
	}else{
		_getMainFrame().showInfo('请选择一条要复制创建的流程配置信息！');
	}
}

function _delete(){
	if (gridDiv_grid.selModel.hasSelection()){
		var records = gridDiv_grid.selModel.getSelections();
		var idList = '';
		var tag = ""; 					
		for(var i=0;i<records.length;i++){
			idList += tag + records[i].data.bpDefId;
			tag = ",";
		}
		_getMainFrame().Ext.Msg.show({
			title:'系统提示',
			msg: '是否确定删除？',
			buttons: {yes:'确定', no:'取消'},
			fn: function(buttonId,text){
				if(buttonId=='yes'){
					var param = '?action=deletes&bpDefId='+idList;
					new AjaxEngine('<%=request.getContextPath()%>/workflow/boProcessDefinitionController.spr', 
							{method:"post", parameters: param, onComplete: callBackHandle});
				}
			},
			icon: Ext.MessageBox.WARNING
	    });
	}else{
		_getMainFrame().showInfo('请选择要删除的业务流程信息！');
	}
}

function _manager(value,metadata,record){
	return '<a href="#" style="color:green;" onclick="_view(\''+value+'\');">查看</a>'
		+' <a href="#" style="color:green;" onclick="_edit(\''+value+'\');">编辑</a>'
		+' <a href="#" style="color:green;" onclick="_delete(\''+value+'\');">删除</a>';
}

function customCallBackHandle(transport){
	//alert(transport);
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	//_getMainFrame().ExtModalWindowUtil.alert('[提示]',customField.returnMessage);
	reload_gridDiv_grid("");
}
</script>
</html>
