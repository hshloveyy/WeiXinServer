<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@
 page import="org.apache.commons.lang.StringUtils"%>
 <%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建业务流程定义</title>
</head>
<body>
<fisc:dictionary hiddenName="isDefault" dictionaryName="YDYESORNO" divId="isDefaultDiv" value="${main.isDefault}" allowBlank="false" isNeedAuth="false" defaultValue="Y"></fisc:dictionary>
<fisc:dictionary hiddenName="appModel" dictionaryName="YDAPPMODEL" divId="appModelDiv" value="${main.appModel}" allowBlank="false" isNeedAuth="false"></fisc:dictionary>
<fisc:dictionary hiddenName="processType" dictionaryName="YDBPWFTYPE" divId="processTypeDiv" value="${main.processType}" allowBlank="false" isNeedAuth="false"></fisc:dictionary>
<fisc:dictionary hiddenName="boId" dictionaryName="YBUSINESSOBJECT" divId="boIdDiv" value="${main.boId}" allowBlank="false" isNeedAuth="false" disabled="true"></fisc:dictionary>
<fisc:dictionary hiddenName="processDefinitionName" dictionaryName="YDCPROCESSDEFNAME" divId="processDefinitionIdDiv" value="${main.processDefinitionName}" allowBlank="false" isNeedAuth="false"></fisc:dictionary>
<fisc:searchHelp divId="boMethodIdDiv" boName="" boProperty="" searchType="field" searchHelpName="YHALLBOMETHOD" displayField="METHODDESC" valueField="MEDID" hiddenName="boMethodId"  callBackHandler="boMethodCallBackHandle" value="${main.boMethodId}"></fisc:searchHelp>
<fisc:searchHelp divId="orgDiv" boName="" boProperty="" searchType="field" searchHelpName="YHORGANIZATION" displayField="ORGANIZATIONNAME" valueField="ORGANIZATIONID" hiddenName="organizationId"  callBackHandler="" value="${main.organizationId}"></fisc:searchHelp>

<div id="toolbarDiv"></div>
<form name="mainForm" id="mainForm" action="" class="search">
	<input type="hidden" name="bpDefId" id="bpDefId" value="${bpDefId}">
	<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"><font color="red">★</font>业务流程名称：</td>
		<td width="30%" >
			<input class="inputText" type="text" id="bpName" name="bpName" value="${main.bpName}">
		</td>
		<td width="15%"  align="right"><font color="red">★</font>应用模块：</td>
		<td width="40%" >
			<div id="appModelDiv"></div>
		</td>
	</tr>
 	<tr>
		<td width="15%" align="right">流程描述：</td>
		<td width="30%" >
			<input class="inputText" type="text" id="bpDesc" name="bpDesc" value="${main.bpDesc}">
		</td>
		<td width="15%" align="right"><font color="red">★</font>流程类型：</td>
		<td width="40%" >
			<div id="processTypeDiv"></div>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"><font color="red">★</font>关联业务对象方法：</td>
		<td width="40%" >
			<div id="boMethodIdDiv"></div>
		</td>
		<td width="15%" align="right">关联业务对象：</td>
		<td width="30%" >
			<div id="boIdDiv"></div>
		</td>

	</tr>
	<tr>
		<td width="15%" align="right"><font color="red">★</font>JBPM流程名称:</td>
		<td width="30%" >
			<div id="processDefinitionIdDiv"></div>
		</td>
		<td width="15%" align="right"></td>
		<td width="30%" >
		</td>
	</tr>
		<tr>
		<td width="15%" align="right"><font color="red">★</font>是否默认流程:</td>
		<td width="30%" >
			<div id="isDefaultDiv"></div>
		</td>
		<td width="15%" align="right">组织:</td>
		<td width="30%" >
			<div id="orgDiv"></div>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">创建者：</td>
		<td width="30%" >
		    <fisc:user boProperty="creator" boName="" userId="${main.creator}"></fisc:user>
		</td>
		<td width="15%" align="right">创建时间：</td>
		<td width="40%" >
			<input class="inputText" type="text" id="createTime" name="createTime" readonly="readonly" value="${main.createTime}">
		</td>
	</tr>
	<tr>
		<td width="15%"  align="right">最后修改者：</td>
		<td width="30%" >
			<fisc:user boProperty="lastModifyer" boName="" userId="${main.lastModifyer}"></fisc:user>
		</td>
		<td width="15%" align="right">最后修改时间：</td>
		<td width="40%" >
			<input class="inputText" type="text" id="lastModifyTime" name="lastModifyTime" readonly="readonly" value="${main.lastModifyTime}">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" valign="middle">备注：</td>
		<td width="85%" colspan="3">
			<textarea cols="60" rows="5" name="memo" id="memo" >${main.memo}</textarea>
		</td>
	</tr>
	</table>
</form>
</body>
<script type="text/javascript" defer="defer">
function _save(){
	var param1 = Form.serialize('mainForm');
	param1+='&boId='+dict_boIdDiv.getValue();
	//alert(param1);
	//return;
	//var isDefault= $('isDefault').value;
	var param = '?action=saveOrUpdate&' + param1;
	new AjaxEngine('<%=request.getContextPath()%>/workflow/boProcessDefinitionController.spr', 
				{method:"post", parameters: param, onComplete: callBackHandle});
	
}

function _close(){
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

//回调函数
function customCallBackHandle(transport){
	//alert(transport.responseText);
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil1.getCustomField("coustom");
	//alert(result.lastmodifytime);
	document.getElementById('bpDefId').value = result.bpDefId;
	document.getElementById("creator_text").value = result.creator_text;
	document.getElementById("creator").value = result.creator;
	document.getElementById("createTime").value = result.createtime;
	document.getElementById("lastModifyer_text").value = result.lastmodifyer_text;
	document.getElementById("lastModifyer").value = result.lastmodifyer;
	document.getElementById("lastModifyTime").value = result.lastmodifytime;	
	//_getMainFrame().showInfo(result.returnMessage);
}

function boMethodCallBackHandle(object){
	//alert(Ext.util.JSON.encode(object));
	dict_boIdDiv.setValue(object.BOID);
}
</script>
<script type="text/javascript" defer="defer">
var toolbars = new Ext.Toolbar({
	items:[
			'-','->',<%=request.getAttribute("operationType")==null?"'-',":request.getAttribute("operationType").equals("view")?"":"'-',"%>
			{id:'_save',text:'保存',handler:_save,iconCls:'icon-table-save',hidden:<%=request.getAttribute("operationType")==null?"false":request.getAttribute("operationType").equals("view")?"true":"false"%>},'-',
			{id:'_close',text:'取消',handler:_close,iconCls:'icon-undo'},'-'
		],
	renderTo:"toolbarDiv"
});

Ext.onReady(function(){
	var aa = '${main.boMethodId}';
	if(aa)
	{
		boMethodIdDiv_sh.setValue(aa);
	}
	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
			region:"center",
			border:false,
			items:[{
					region:"north",
					contentEl:'toolbarDiv',
					height:28
				},{
					region:'center',
					contentEl:'mainForm',
					title:'节点信息'
				}]
			}]
	});
});
</script>
</html>