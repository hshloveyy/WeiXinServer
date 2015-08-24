<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>子流程节点配置</title>
</head>
<%
	request.setAttribute("contextPath", request.getContextPath());
//AND J.CLASS_ not in('C')
%>
<body>
<fisc:grid divId="gridDiv" editable="false" needCheckBox="true"
	pageSize="12"
	tableName="JBPM_NODE J LEFT OUTER JOIN WF_NODEDEF N ON J.ID_=N.NODEID AND J.NAME_=N.NODEDEFINITIONNAME,DD07T D"
	handlerClass="com.infolion.platform.workflow.manager.web.grid.ExtProcessDefinitionGrid"
	whereSql=" "  defaultCondition="${whereSql}" orderBySql="ID_" height="260" needAutoLoad="true"></fisc:grid>
	
<div id="div_toolbar"></div>
<div id="div_center">
<form name="mainForm" id="mainForm" class="search" >
	<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">流程名：</td>
		<td width="30%">
			<input type="hidden" id="extProcessId" name="extProcessId" value="${main1.extProcessId}" >
			<input type="hidden" id="supperWfprocessId" name="supperWfprocessId" value="${main1.processId}" >
			<input type="hidden" id="processId" name="processId" value="${main1.subProcessDefinitionId}" >
			<input type="hidden" id="boId" name="boId" value="${subWfNodeDef.boId}">
			<input type="hidden" id="supperWfboId" name="supperWfboId" value="${main.boId}">
			<input type="hidden" id="subProcessDefinitionId" name="subProcessDefinitionId" value="${main1.subProcessDefinitionId}">
			<input class="inputText" type="text" id="bpName" name="bpName" value="${main.bpName}" readonly="readonly">
		</td>
		<td width="15%" align="right">流程描述：</td>
		<td width="40%">
			<input class="inputText" type="text" id="bpDesc" name="bpDesc" value="${main.bpDesc}" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">流程类型：</td>
		<td width="30%">
			<div id="processTypeDiv"></div>
		</td>
		<td width="15%" align="right"></td>
		<td width="40%">
		</td>
	</tr>
		<tr>
		<td width="15%" align="right">节点名称：</td>
		<td width="30%" >
			<input type="hidden" name="nodeDefId" value="${subWfNodeDef.nodeDefId}" id="nodeDefId">
		    <input type="hidden" id="nodeId" name="nodeId" value="${subWfNodeDef.nodeId}">
			<input class="inputText" type="text" id="nodeDefinitionName" name="nodeDefinitionName" value="${subWfNodeDef.nodeDefinitionName}" readonly="readonly">
		</td>
		<td width="15%" align="right">节点类型：</td>
		<td width="40%" >
			<div id="nodeTypeDiv"></div>
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
	<tr>
		<td width="15%" align="right">关联jbpm流程名：</td>
		<td width="30%">
			<input class="inputText" type="text" id="processDefinitionName" name="processDefinitionName" readonly="readonly" value="${main.processDefinitionName}" readonly="readonly">
		</td>
		<td width="15%" align="right">版本：</td>
		<td width="40%">
			<input class="inputText" type="text" id="version" name="version" value="${main1.version}" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"></td>
		<td colspan="2"  width="45%">
			<input type="button" value="查看流程图" onclick="showProcessImage($('subProcessDefinitionId').value);">
			<input type="button" value="查看父流程图" onclick="showProcessImage($('supperWfprocessId').value);">
		</td>
		<td width="40%"  align="left">
		</td>
	</tr>
	</table>
</form>
</div>
<div id="gridDiv"></div>
<div id="div_tree"></div>
</body>
<%
	String whereSql = request.getAttribute("whereSql").toString();
	request.setAttribute("whereSql", whereSql);
%>
<script type="text/javascript" defer="defer">
var operationType = ""

	function _manager(value,metadata,record){
	//alert("value="+value+"\r\nmetadata="+Ext.util.JSON.encode(metadata)+"\r\nrecord="+Ext.util.JSON.encode(record.data));
	var url = '';
	if(record.data.class_!='C')
    url='<a href="#" onclick="nodeLogicControl('+value+',\''+record.data.name_+'\',\''+record.data.class_+'\');" style="color:green">节点逻辑</a>'
    //||record.data.class_=='C'
	if(record.data.class_=='K'||record.data.class_=='R'){
		url += ' <a href="#" style="color:green" onclick="nodeActorControl('+value+',\''+record.data.extProcessId+'\');">参与者</a>';
		if(record.data.class_!='R')
			url += ' <a href="#" style="color:green" onclick="nodeDateAuthControl('+value+',\''+record.data.class_+'\',\''+record.data.extProcessId+'\');">数据权限</a>';
	}
    if(record.data.class_=='C'){
       //alert(value +" ,"+ record.data.name_+" ,"+record.data.class_);
       url='<a href="#" onclick="subWfnodeLogicControl('+value+',\''+record.data.name_+'\',\''+record.data.subProcessDefinition_+'\');" style="color:green">子流程节点逻辑</a>'
    } 
    
	return url;
}

function boMethodCallBackHandle(object){
	dict_boIdDiv.setValue(object.BOID);
	$('boId').value = object.BOID;
}

/**
 * 子流程节点逻辑
*/
function subWfnodeLogicControl(value,name_,subProcessDefinition_)
{
	var extProcessId = $('extProcessId').value;
	var boId = $('boId').value ;
	var processId = $('processId').value ;
	var url = '<%=request.getContextPath()%>/workflow/extNodeDefinitionController.spr?action=subWfnodeLogicControl&extProcessId='+extProcessId+
	'&nodeId='+value+'&nodeName='+escape(escape(name_))+'&subProcessDefinition_='+subProcessDefinition_+'&boId='+boId+'&processId='+processId;
	_getMainFrame().maintabs.addPanel('子流程节点逻辑配置',gridDiv_grid,url);
	
}

/**
 * 节点逻辑配置
 */
function nodeLogicControl(value,name_,class_){
	//alert(value+name_+class_);
	var extProcessId = $('extProcessId').value;//'${main1.extProcessId}';
	var boId = $('boId').value ;
	var processId = $('processId').value ;//'${main1.processId}';
	var url = '<%=request.getContextPath()%>/workflow/extNodeDefinitionController.spr?action=edit&extProcessId='+extProcessId+
	'&nodeId='+value+'&nodeName='+escape(escape(name_))+'&nodeClass='+class_+'&boId='+boId+'&processId='+processId;
	_getMainFrame().maintabs.addPanel('节点逻辑配置',gridDiv_grid,url);

}

/**
* 节点参与者分配
*/
function nodeActorControl(value,extProcessId){
	if(extProcessId!=null&&extProcessId!=''){
		var processId = $('processId').value ;//'${main1.processId}';
		var boId = $('boId').value ;
		var url = '<%=request.getContextPath()%>/workflow/taskActorController.spr?action=edit&boId='+boId+'&nodeId='+value+'&processId='+processId+'&extProcessId='+extProcessId;
		_getMainFrame().maintabs.addPanel('节点参与者分配',gridDiv_grid,url);

	}else
		_getMainFrame().showInfo('在进行"节点参与者分配"前请先进行"节点逻辑控制"');
}
/**
 * 节点数据权限控制
 */
function nodeDateAuthControl(value,class_,extProcessId){
	if(extProcessId!=null&&extProcessId!=''){
		var boId = $('boId').value ;
		var processId = $('processId').value ;
		var url = '<%=request.getContextPath()%>/workflow/taskAuthenticationController.spr?action=edit&nodeId='+value+'&nodeClass='+class_+'&boId='+boId+'&processId='+processId+'&extProcessId='+extProcessId;
		_getMainFrame().maintabs.addPanel('节点数据权限控制',gridDiv_grid,url);
	}else
		_getMainFrame().showInfo('在进行"节点数据权限控制"前请先进行"节点逻辑控制"');
}

/**
 * 激活流程
 */
function changeStatus(){
	_getMainFrame().Ext.Msg.show({
		title:'系统提示',
		msg: '是否确定激活此版本的流程？',
		buttons: {yes:'确定', no:'取消'},
		fn: function(buttonId,text){
			if(buttonId=='yes'){
				document.getElementById('active').value = 'Y';
				var param1 = Form.serialize('mainForm');
				var param = '?action=saveOrUpdate&' + param1;
				new AjaxEngine('<%=request.getContextPath()%>/workflow/extProcessDefinitionController.spr', 
							{method:"post", parameters: param, onComplete: callBackHandle});
				operationType = "changeStatus";
			}
		},
		icon: Ext.MessageBox.WARNING
    });
}

function customCallBackHandle(transport){		
	if(operationType=="changeStatus")
	{
		$('activeText').value = "激活";
		document.getElementById("btnActive").style.display="none";
		document.getElementById("activeText").style["color"]="red";
	}
	else{	
		var responseUtil1 = new AjaxResponseUtils(transport.responseText);
		var customField = responseUtil1.getCustomField("coustom");
		_getMainFrame().showInfo(customField.returnMessage);
	}
}

/**
 * 查看流程图
 */
function showProcessImage(processDefinitionId){
	if(processDefinitionId==null||processDefinitionId==''){
		_getMainFrame().showInfo('查看流程图前请先选择一个流程！');
		return false;
	}
	var url = '<%=request.getContextPath()%>/platform/workflow/manager/processDefinitionController.spr?action=_showProcess&processDefinitionId='+processDefinitionId;
	_getMainFrame().maintabs.addPanel('流程图展示','',url);
}

function addCallBack(){
	
}

function functionCallBack(transport){
	gridDiv_grid.getStore().reload();
}

function _cancel(){
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

/**
 * 保存节点类型为子流程的节点逻辑配置信息。
 */
function _saveOrUpdateSubWfNodeDef()
{
	var param = Form.serialize('mainForm');
	new AjaxEngine('<%=request.getContextPath()%>/workflow/extNodeDefinitionController.spr?action=_saveOrUpdateSubWfNodeDef', 
			{method:"post", parameters: param, onComplete: callBackHandle ,callback: customCallBackHandle});
}

//回调函数
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil1.getCustomField("coustom");
	var nodeDefId = result.nodeDefId ; 
	document.getElementById("nodeDefId").value = nodeDefId;
	//alert("保存成功：" + nodeDefId);
}

Ext.onReady(function(){

   	var toolbars = new Ext.Toolbar({
		items:[
				'-','->',
				{id:'_saveSubWfNodeDef',text:'保存',handler:_saveOrUpdateSubWfNodeDef, iconCls:'icon-table-save'},'&nbsp;',
				{id:'_cancel',text:'取消',handler:_cancel,iconCls:'icon-undo'},
				'&nbsp;','-'
				],
		renderTo:"div_toolbar"
	});
	
	//布局
   	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
			region:'center',
    		autoScroll: true,
    		autoWidth: true ,
    		autoHeight: true ,
			border:false,
			items:[{
				region:'north',
				height:25,
				border:false,
				contentEl:'div_toolbar'
			},{
	        		region:'north',
	        		title:'流程属性',
	        		layout:'fit',
	        		contentEl:'div_center'
		        },{
		        	region:'south',
		        	title:'流程节点',
		        	//height:300,
		        	border:false,
		        	contentEl:'gridDiv'
				}]
		}]
	});
});
</script>
</html>
<fisc:dictionary hiddenName="processType" dictionaryName="YDBPWFTYPE"
	divId="processTypeDiv" value="${main.processType}" allowBlank="false"
	disabled="true" isNeedAuth="false"></fisc:dictionary>
<fisc:dictionary dictionaryName="YDWFNODETYPE" divId="nodeTypeDiv" hiddenName="nodeType" value="${nodeClass}" isNeedAuth="false" disabled="true"></fisc:dictionary>
<fisc:dictionary hiddenName="boId" dictionaryName="YBUSINESSOBJECT" divId="boIdDiv" value="${subWfNodeDef.boId}" allowBlank="false" isNeedAuth="false" disabled="true"></fisc:dictionary>
<fisc:searchHelp divId="boMethodIdDiv" boName="" boProperty="" searchType="field" searchHelpName="YHALLBOMETHOD" displayField="METHODDESC" valueField="MEDID" hiddenName="boMethodId"  
callBackHandler="boMethodCallBackHandle" value="${subWfNodeDef.boMethodId}"></fisc:searchHelp>
	