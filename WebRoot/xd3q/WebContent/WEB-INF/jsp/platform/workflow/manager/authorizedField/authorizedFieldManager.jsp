<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>节点数据权限配置</title>
</head>
<body>
<fisc:dictionary dictionaryName="YDWFNODETYPE" divId="nodeTypeDiv" hiddenName="nodeType" value="${nodeClass}" isNeedAuth="false" disabled="true"></fisc:dictionary>
<fisc:dictionary dictionaryName="YBUSINESSOBJECT" divId="boIdDiv" hiddenName="boId" value="${main.boId}" allowBlank="false" isNeedAuth="false" disabled="true"></fisc:dictionary>
<fisc:dictionary dictionaryName="YMETHOD" divId="boMethodIdDiv" hiddenName="boMethodId" value="${main.boMethodId}" isNeedAuth="false" disabled="true"></fisc:dictionary>
<fisc:grid divId="div_southForm" pageSize="10" tableName="WF_AUTHFIELD A" needAutoLoad="true" 
 handlerClass="com.infolion.platform.workflow.manager.web.grid.TaskAuthenticationGrid" needCheckBox="true"
 whereSql=" and EXTPROCESSID='${extProcessId}' and NODEID=${nodeId}" height="380" entityName="WfAuthorizedField" editable="true"></fisc:grid>

<div id="div_toolbar"></div>
<div id="div_center">
<form name="mainForm" id="mainForm" class="search">
	<table width="100%" border="0" cellpadding="4" cellspacing="1">
		<tr>
			<td align="right" width="20%">节点名称：</td>
			<td width="30%">
				<input type="hidden" name="nodeDefId" value="${main.nodeDefId}" id="nodeDefId">
				<input type="hidden" name="nodeId" value="${main.nodeId}" id="nodeId">
				<input type="hidden" name="extProcessId" value="${main.extProcessId}" id="extProcessId">
				<input class="inputText" type="text" id="nodeDefinitionName" name="nodeDefinitionName" value="${main.nodeDefinitionName}" readonly="readonly">
			</td>
			<td align="right" width="20%">节点类型：</td>
			<td width="30%">
				<div id="nodeTypeDiv"></div>
			</td>
		</tr>
		<tr>
			<td align="right" width="20%">关联业务对象：</td>
			<td width="30%">
				<div id="boIdDiv"></div>
			</td>
			<td align="right" width="20%">业务对象方法：</td>
			<td width="30%">
				<div id="boMethodIdDiv"></div>
			</td>
		</tr>
	</table>
</form>
</div>
<div id="div_southForm"></div>
</body>
</html>
<script type="text/javascript" >

function _create(){
	searchHelpAuth.show();
}

function _save(){
	var param1 = getWfAuthorizedFieldGridData();
	var param = '?action=saveOrUpdate' + param1;
	//alert(param);
	new AjaxEngine('<%=request.getContextPath()%>/workflow/taskAuthenticationController.spr', 
			{method:"post", parameters: param, onComplete: callBackHandle});
}

function _close(){
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

function authCallBack(jsonArrayData)
{
	//alert(Ext.util.JSON.encode(jsonArrayData));
	var param1 = '';
	for(var i=0;i<jsonArrayData.length;i++){
		var authFields = new WfAuthorizedField_fields({
			fieldName : '',
			fieldDesc : '',
			boId : '',
			nodeId : '',
			extProcessId:''
		});

		//WfAuthorizedField_grid.stopEditing();
		WfAuthorizedField_grid.getStore().insert(i, authFields);
		//WfAuthorizedField_grid.startEditing(0, 0);
		var record = WfAuthorizedField_grid.getStore().getAt(i);
		record.set("fieldName", jsonArrayData[i].PRONAME);
		record.set("fieldDesc", jsonArrayData[i].FIELDTEXT);
		record.set("boId", '${main.boId}');
		record.set("nodeId", '${nodeId}');
		record.set("extProcessId", '${extProcessId}');
	}
}

var searchHelpAuth = new Ext.SearchHelpWindow({
	shlpName : 'YHBOPROPERTIES',
	defaultCondition : "BOID='${main.boId}'",
	callBack : authCallBack
});

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	reload_WfAuthorizedField_grid("");
}
</script>

<script type="text/javascript" defer="defer">
var toolbars = new Ext.Toolbar({
	items:['-','->','-',
		   {id:'_save',text:'保存',handler:_save,iconCls:'icon-table-save'},'-',
		   {id:'_close',text:'取消',handler:_close,iconCls:'icon-undo'},'-'
		  ],
	renderTo:"div_toolbar"
});

var viewport = new Ext.Viewport({
	layout:"border",
	items:[{
		region:"center",
		items:[{
				region:"north",
				contentEl:'div_toolbar',
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
	            title:'节点数据权限',
	            height:410,
	            bodyStyle:"background-color:#DFE8F6",
				contentEl:'div_southForm'
			}]
		}]
});

Ext.onReady(function(){
	Ext.getCmp('WfAuthorizedFieldaddRow').setVisible(false);
});
</script>
