<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String json=JSONUtils.valueToString(request.getAttribute("logicStrCode"));
System.out.println("AAAAAAAAAAA:"+json);
System.out.println("BBBBBBBBBBB:"+request.getAttribute("logicStrCode"));
%>

<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSON"%>
<%@page import="net.sf.json.util.JSONUtils"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>节点逻辑管理</title>
</head>
<body>
<div id="toolbarDiv"></div>
<form action="" name="mainForm" id="mainForm" class="search">
	<input type="hidden" name="nodeDefId" value="${main.nodeDefId}" id="nodeDefId">
	<input type="hidden" name="nodeId" value="${main.nodeId}" id="nodeId">    
	<input type="hidden" name="extProcessId" value="${main.extProcessId}" id="extProcessId">
	<input type="hidden" name="processId" value="${main.processId}" id="processId">
	<table id="nodeInfo" width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">节点名称：</td>
		<td width="30%" >
			<input class="inputText" type="text" id="nodeDefinitionName" name="nodeDefinitionName" value="${main.nodeDefinitionName}" readonly="readonly">
		</td>
		<td width="15%" align="right">节点类型：</td>
		<td width="40%" >
			<div id="nodeTypeDiv"></div><!-- input type=button value=test onclick="alert(document.getElementById('nodeDefId').value)" -->
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">关联业务对象：</td>
		<td width="30%" >
			<div id="boIdDiv"></div>
		</td>
		<td width="15%" align="right">业务对象方法：</td>
		<td width="40%" >
			<div id="boMethodIdDiv"></div>
		</td>
	</tr>
	</table>
</form>
<form action="" name="logicForm" id="logicForm" class="search">
	<input type="hidden" value="${groupValue}" >
	<table id="nodeLogic" width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%"  align="right">参与人审批方式：</td>
		<td width="30%" >
			<div id="examineTypeDiv"></div>
		</td>
		<td width="15%"  align="right">是否自动执行：</td>
		<td width="40%" >
			<div id="autoSignalDiv"></div>
		</td>
	</tr>
	<tr>
		<td width="15%"  align="right">执行类型：</td>
		<td width="30%" >
			<div id="exeLogicTypeDiv"></div>
		</td>
		<td width="15%"  align="right">是否邮件通知：</td>
		<td width="40%" >
			<div id="isSendMailDiv"></div>
			
		</td>
	</tr>
	<tr>
		<td width="15%"  align="right" valign="middle">执行内容：</td>
		<td width="85%" colspan="3">
			<span id="exeLogicTDiv"><textarea rows="4" cols="60" id="exeLogicT" name="exeLogicT" ></textarea></span>
			<span id="exeLogicSDiv"></span>
			<fisc:searchHelp divId="exeLogicSDiv" boName="" boProperty="" searchType="field" searchHelpName="YHOSINTERFACE" 
			valueField="BOMETHODNAME" displayField="BOMETHODNAME"  hiddenName="exeLogicS" defaultCondition="BOID = '${main.boId}' AND CALLTIME = '2'"></fisc:searchHelp>
		</td>
	</tr>
	</table>
<div id="ExtendTransition"></div>
</form>
</body>
<script type="text/javascript" defer="defer">
var operationObject = "";
	
function functionCallBack(transport){
	reload_ExtendTransition_grid("");
	_save(customCallBackHandle);
}
	
function _save(callBackFunction){
	var param1 = Form.serialize('mainForm');
	var param2 = Form.serialize('logicForm');
	param2 = param2 + "&" + getAllExtendTransitionGridData();
	if(callBackFunction!=customCallBackHandle)
		callBackFunction = callBackHandle;
	var param = '?action=saveOrUpdate&' + param1 + "&" + param2;
	//alert(param);
	if(dict_exeLogicTypeDiv.getValue()==4) param += "&exeLogic="+exeLogicSDiv_sh.getValue();
	else param += "&exeLogic="+encodeURIComponent($('exeLogicT').value);
	param+='&boId='+ dict_boIdDiv.getValue();
	new AjaxEngine('<%=request.getContextPath()%>/workflow/extNodeDefinitionController.spr', 
				{method:"post", parameters: param, onComplete: callBackFunction});
}

function _close(){
	//_getMainFrame().ExtModalWindowUtil.markUpdated();
	//_getMainFrame().ExtModalWindowUtil.close();
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

//回调函数
function customCallBackHandle(transport){
	if(operationObject=="delete"){
		gridDiv_store.reload();
		return;
	}else if(operationObject=="grid"){
		try{
			var promptMessagebox = new MessageBoxUtil();
			promptMessagebox.Close();
		}catch(e){}
		operationObject = "";
	}
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var id = customField.nodeDefId;
	document.getElementById("nodeDefId").value = id;
	reload_ExtendTransition_grid("");
}

function _manager(value,metadata,record){
	return	'<a href="#" onclick="_edit('+value+',\''+record.data.extendTransitionId+'\',\''+record.data.transitionName+'\',\''
			+record.data.nextNodeName+'\');" style="color:green">设置</a> <!--a href="#" onclick="_delete('+value+
			',\''+record.data.extendTransitionId+'\');" style="color:green">删除</a-->';
}

function _edit(value,id,name,nextnodename){
	var processId = '${main.processId}';
	var extProcessId = '${main.extProcessId}';
	_getMainFrame().ExtModalWindowUtil.show('节点路径配置',
			'<%=request.getContextPath()%>/workflow/extNodeDefinitionController.spr?action=editTransition&boId='+dict_boIdDiv.getValue()+'&nodeId='+value+'&processId='+processId
				+'&extendTransitionId='+id+'&extProcessId'+extProcessId+'&transitionName='+escape(escape(name))+'&nextNodeName='+escape(escape(nextnodename)),
			'',
			functionCallBack,
			{width:650,height:500}
		);
	operationObject = "grid";
}

function _delete(value,id){
	if(id==null||id==''){
		_getMainFrame().showInfo('该流路径的配置还未定义，无法删除');
		return false;
	}
	_getMainFrame().Ext.MessageBox.show({
		title:'系统提示',
		msg: '是否确定要删除？',
		buttons: {yes:'确定', no:'取消'},
		icon: Ext.MessageBox.QUESTION,
		fn:function(buttonid){
			if (buttonid == 'yes'){
				var param = '?action=delete&nodeId=' + value + "&extendTransitionId=" + id;
				new AjaxEngine('<%=request.getContextPath()%>/workflow/extNodeDefinitionController.spr', 
							{method:"post", parameters: param, onComplete: callBackHandle});
				operationObject = "delete";
			}
		}
	});
}

function boMethodCallBackHandle(object){
	dict_boIdDiv.setValue(object.BOID);
}

</script>
<fisc:dictionary hiddenName="autoSignal" dictionaryName="YDYESORNO" divId="autoSignalDiv" value='${main.autoSignal==null?"N":main.autoSignal}' isNeedAuth="false"></fisc:dictionary>
<fisc:searchHelp divId="boMethodIdDiv" boName="" boProperty="" searchType="field" searchHelpName="YHALLBOMETHOD" displayField="METHODDESC" valueField="MEDID" hiddenName="boMethodId"  callBackHandler="boMethodCallBackHandle" value="${main.boMethodId}"></fisc:searchHelp>
<fisc:dictionary hiddenName="boId" dictionaryName="YBUSINESSOBJECT" divId="boIdDiv" value="${main.boId}" allowBlank="false" isNeedAuth="false" disabled="true"></fisc:dictionary>
<fisc:dictionary dictionaryName="YDWFEXAMTYPE" divId="examineTypeDiv" hiddenName="examineType" value='${main.examineType==null?"1":main.examineType}' isNeedAuth="false"></fisc:dictionary>
<fisc:dictionary dictionaryName="YDWFNODETYPE" divId="nodeTypeDiv" hiddenName="nodeType" value="${nodeClass}" isNeedAuth="false" disabled="true"></fisc:dictionary>
<fisc:dictionary dictionaryName="YDWFCONDITIONTYPE" divId="exeLogicTypeDiv" hiddenName="exeLogicType" value='${main.exeLogicType==null?" ":main.exeLogicType}' isNeedAuth="false"></fisc:dictionary>
<fisc:dictionary dictionaryName="YDYESORNO" divId="isSendMailDiv" hiddenName="isSendMail" value="${main.isSendMail}" isNeedAuth="false"></fisc:dictionary>
<fisc:grid divId="ExtendTransition" pageSize="5" title="以该节点起始的路径的逻辑配置" 
 tableName="${tableName}"
 handlerClass="com.infolion.platform.workflow.manager.web.grid.ExtTransitionGrid" needAutoLoad="true"
 whereSql=" AND A.ID_=${main.nodeId} AND T.EXTPROCESSID='${main.extProcessId}' AND TT.FROM_ = A.ID_ AND TT.TO_ = AA.ID_ " height="200"></fisc:grid>
<script type="text/javascript" defer="defer">
Ext.onReady(function(){
	if(dict_exeLogicTypeDiv.getValue()==4){
		$('exeLogicTDiv').style.display="none";
		exeLogicSDiv_sh.setValue(${logicStrCode});
	}else{
		$('exeLogicSDiv').style.display="none";
		$('exeLogicT').value = ${logicStrCode};
	}

	dict_exeLogicTypeDiv.on('select',function(c,r,i){
		var n = c.getValue();
		if (n==4){
			$('exeLogicSDiv').style.display="block";
			$('exeLogicTDiv').style.display="none";
		}else{
			$('exeLogicSDiv').style.display="none";
			$('exeLogicTDiv').style.display="block";
		}
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-','->','-',
				{id:'_save',text:'保存',handler:_save,iconCls:'icon-table-save'},'-',
				{id:'_close',text:'取消',handler:_close,iconCls:'icon-undo'},'-'
			],
		renderTo:"toolbarDiv"
	});
	
	var viewport = new Ext.Viewport({
		renderTo:document.body,
		layout:"border",
		border:false,
		items:[{
			region:"north",
			contentEl:'toolbarDiv',
			height:28
		},{
			region:"center",
			border:false,
			items:[{
					contentEl:'mainForm',
					id:'mainForm1',
					title: '节点信息'
				},{
					contentEl:'logicForm',
					id:'logicForm1',
					title: '节点逻辑'
				},{
					region:'south',
					//layout:'fix',
					border:false,
					id:'gridDiv',
					contentEl:'gridDiv'
				}]
		}]
	});
});
</script>
</html>