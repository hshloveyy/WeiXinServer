<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
String operationType = request.getAttribute("operationType")==null?"": request.getAttribute("operationType").toString();
request.setAttribute("needToolbar",false);
if("view".equals(operationType))
	request.setAttribute("buttonDisabled",true);
else{
	request.setAttribute("buttonDisabled",false);
	request.setAttribute("needToolbar",true);
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建用户消息</title>
</head>
<body>
<div id="_toolbarDiv"></div>
<form name="mainForm" id="mainForm" class="search">
<input type="hidden" name="isSended" id="isSended" value="${main.isSended}">
<input type="hidden" name="client" id="client" value="${main.client}">
<input type="hidden" name="operationType" id="operationType" value="${operationType}">
<input type="hidden" name="userMessageId" id="userMessageId" value="${userMessageId}">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right">标题：</td>
		<td colspan="3">
			<input type="text" id="title" name="title" value="${main.title}" size="71">
		</td>
	</tr>
	<tr>
		<td align="right">创建时间：</td>
		<td>
			<input type="text" id="createTime" name="createTime" value="${main.createTime}" readonly="readonly">
		</td>
		<td align="right">消息类型：</td>
		<td>
			<div id="messageTypeDiv"></div>
		</td>
	</tr>
	<tr>
		<td align="right">收件人：</td>
		<td colspan="3">
			<div id="toolbarDiv"></div>
			<input type="hidden" id="addresseeNames" name="addresseeNames" size="60" value="${addresseeName}" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td align="right" valign="middle">内容：</td>
		<td colspan="3">
			<textarea rows="17" cols="60" id="content" name="content">${main.content}</textarea>
		</td>
	</tr>
</table>
</form>
<div id="attachementDiv" class="x-hide-display"></div>
<div id="gridDiv"></div>
</body>
<script type="text/javascript" defer="defer">
function getAddresseeName(){
	var count = Addressee_grid.getStore().getCount();
	var addresseeNames = Ext.getDom('addresseeNames');
	addresseeNames.value = '';
	var tag = '';
	for(var i=count;i>0;i--){
		var record = Addressee_grid.getStore().getAt(i-1);
		addresseeNames.value += tag + record.get('addresseeName');
		tag = ',';
	}
}

function _delete(){
	var mygrid = Addressee_grid;
	if (mygrid.selModel.hasSelection() > 0){
		var records = mygrid.selModel.getSelections();
		for(var i=0;i<records.length;i++){
			mygrid.getStore().remove(records[i]);
		}
		getAddresseeName();
	}else{
		_getMainFrame().showInfo('请选择要删除的记录！');
	}
}

var expandStatue = 1;
function _expand(){
	var panel = Ext.getCmp('addresseePanel');
	var button = Ext.getCmp('_edit');
	if(expandStatue===1){
		panel.expand();
		expandStatue=0;
		button.setText('收起收件人列表');
	}else{
		panel.collapse();
		expandStatue=1;
		button.setText('编辑收件人...');
	}
}

function _beforeSave(){
	//如果是回复消息要将userMessageId清空
	if('reply'=='${operationType}'){
		var record = Addressee_grid.getStore().getAt(0);
		var addresseeName = record.data['addresseeName'];
		record.set("userMessageId", "");		
		document.getElementById('userMessageId').value = "";
	}
	if('copyCreate'=='${operationType}'){
		var count = Addressee_grid.getStore().getCount();
		for(var i=0;i<count;i++){
			var record = Addressee_grid.getStore().getAt(i);
			record.set("userMessageId", "");
			record.set("addresseeId", "");
		}
		document.getElementById('userMessageId').value = "";
	}
	if(dict_messageTypeDiv.getValue()=='1'){
		var count = Addressee_grid.getStore().getCount();
		for(var i=0;i<count;i++){
			var record = Addressee_grid.getStore().getAt(i);
			Addressee_grid.getStore().remove(record);
		}		
	}	
}

function _save(){
	if(!document.getElementById('title').value){
		_getMainFrame().showInfo('请输入标题！');
		return false;
	}
	_beforeSave();
	var param1 = Form.serialize('mainForm');
	//alert(param1);
	//return;
	var param = '?action=_saveOrUpdate&' + param1 + getAddresseeGridData();
	if('copyCreate'=='${operationType}'){
		param+="&"+getAllAttachementGridData();
	}else
		param+="&"+getAttachementGridData();		
	new AjaxEngine('<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?', 
				{method:"post", parameters: param, onComplete: callBackHandle});
}

function _send(userMessageId){
	if(!document.getElementById('title').value){
		_getMainFrame().showInfo('请输入标题！');
		return false;
	}
	if(dict_messageTypeDiv.getValue()!='1'&&!document.getElementById('addresseeNames').value){
		_getMainFrame().showInfo('请选择收件人！');
		return false;
	}
	_beforeSave();
	var param1 = Form.serialize('mainForm');
	var param = '?action=_send&' + param1 + getAddresseeGridData();
	//alert(param);
	Ext.getDom('_sendButton').disabled=true; 
	Ext.getDom('_saveButton').disabled=true; 
	new AjaxEngine('<%=request.getContextPath()%>/basicApp/workbench/userMessage/userMessageController.spr?', 
				{method:"post", parameters: param, onComplete: callBackHandle});
}

function _close(){
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

function _checkMessageType(){
	if(dict_messageTypeDiv.getValue()=='1'){
		_getMainFrame().showInfo("系统消息无需选择收件人！");
		return false;
	}
	else
		return true;
}
function _createUser(){
	if(_checkMessageType())
		searchHelpUser.show();
}

function _createUserGroup(){
	if(_checkMessageType())
		searchHelpUserGroup.show();
}

function _createRole(){
	if(_checkMessageType())
		searchHelpRole.show();
}

function _createOrg(){
	if(_checkMessageType())
		searchHelpOrg.show();
}

function addAddressee(addressee,type,name,count){
	//alert("addresseeName="+name);
	var addresseeFields = new Addressee_fields({
		addressee : '',
		addresseeType : '',
		addresseeName : '',
		userMessageId : ''
	});

	//WfAuthorizedField_grid.stopEditing();
	Addressee_grid.getStore().insert(count, addresseeFields);
	//WfAuthorizedField_grid.startEditing(0, 0);
	var record = Addressee_grid.getStore().getAt(count);
	record.set("addressee", addressee);
	record.set("addresseeType", type);
	record.set("addresseeName", name);
	record.set("userMessageId", '${main.userMessageId}');
	getAddresseeName();
}

function userCallBack(jsonArrayData)
{
	//alert("jsonArrayData.length="+jsonArrayData.length);
	var isExists = false;
	for(var i=0;i<jsonArrayData.length;i++){
		var count = Addressee_store.getCount();
		for (var j = 0;j<count;j++){
	 		var record = Addressee_store.getAt(j);
 			if (record.get('addressee') == jsonArrayData[i].USERID
		 			&&record.get('addresseeType')==1)
				isExists = true;
		}
		if(!isExists)
			addAddressee(jsonArrayData[i].USERID,1,jsonArrayData[i].USERNAME,count);
	}
}

function userGroupCallBack(jsonArrayData)
{
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		var count = Addressee_store.getCount();
		for (var j = 0;j<count;j++){
	 		var record = Addressee_store.getAt(j);
 			if (record.get('addressee') == jsonArrayData[i].USERGROUPID
		 			&&record.get('addresseeType')==2)
				isExists = true;
		}
		if(!isExists)
			addAddressee(jsonArrayData[i].USERGROUPID,2,jsonArrayData[i].USERGROUPNAME,count);
	}
}

function roleCallBack(jsonArrayData)
{
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		var count = Addressee_store.getCount();
		for (var j = 0;j<count;j++){
	 		var record = Addressee_store.getAt(j);
 			if (record.get('addressee') == jsonArrayData[i].ROLEID
		 			&&record.get('addresseeType')==3)
				isExists = true;
		}
		if(!isExists)
			addAddressee(jsonArrayData[i].ROLEID,3,jsonArrayData[i].ROLENAME,count);
	}
}

function orgCallBack(jsonArrayData)
{
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		var count = Addressee_store.getCount();
		for (var j = 0;j<count;j++){
	 		var record = Addressee_store.getAt(j);
 			if (record.get('addressee') == jsonArrayData[i].ORGANIZATIONID
		 			&&record.get('addresseeType')==4)
				isExists = true;
		}
		if(!isExists)
			addAddressee(jsonArrayData[i].ORGANIZATIONID,4,jsonArrayData[i].ORGANIZATIONNAME,count);
	}
}

var searchHelpUser = new Ext.SearchHelpWindow({
	shlpName : 'YHUSER',
	callBack : userCallBack
});

var searchHelpRole = new Ext.SearchHelpWindow({
	shlpName : 'YHROLE',
	callBack : roleCallBack
});

var searchHelpUserGroup = new Ext.SearchHelpWindow({
	shlpName : 'YHUSERGROUP',
	callBack : userGroupCallBack
});

var searchHelpOrg = new Ext.SearchHelpWindow({
	shlpName : 'YHORGANIZATION',
	callBack : orgCallBack
});

function customCallBackHandle(transport){
	//alert(transport);
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	//_getMainFrame().ExtModalWindowUtil.alert('[提示]',customField.returnMessage);
	var id = customField.userMessageId;
	document.getElementById('userMessageId').value = id;
	document.getElementById('createTime').value = customField.createTime;
	
	var count = Addressee_store.getCount();
	for (var j = 0;j<count;j++){
 		var record = Addressee_store.getAt(j);
 		record.set("userMessageId",id);
	}
	//alert(document.getElementById('userMessageId').value);
	//_getMainFrame().showInfo(customField.returnMessage);
	Addressee_grid.getStore().commitChanges();
}
//防止用户在发送消息后刷新页面再次发送消息
var sendDisabled = false;
if('${main.isSended}'=='Y'&&'${operationType}'!='reply')
	sendDisabled = true;

</script>
<fisc:grid divId="gridDiv" entityName="Addressee" needAutoLoad="true" pageSize="1000" height="490" orderBySql="${orderBySql}"
 whereSql="${whereSql}" tableName = "${tableName}" width="221" needCheckBox="true" needToolbar="${needToolbar}" groupBySql="${groupBySql}"
 handlerClass="com.infolion.platform.basicApp.sysConsole.workbench.userMessage.web.grid.AddresseeGrid"></fisc:grid>
<fisc:dictionary hiddenName="messageType" dictionaryName="YDMESSAGETYPE" divId="messageTypeDiv" isNeedAuth="false" 
 value='${((main.messageType)==null?"2":(main.messageType))}' width="160"></fisc:dictionary>
<fisc:attachement businessId="${main.userMessageId}" allowDelete="true" divId="attachementDiv" boName="UserMessage" allowUpload="${needToolbar}"></fisc:attachement>
<script type="text/javascript" defer="defer">
var _width = document.body.clientWidth - 240;

//Ext.onReady(function(){
	var _toolbars = new Ext.Toolbar({
		items:[
				'-','->',
				{id:'_saveButton',text:'保存',handler:_save,iconCls:'icon-table-save',hidden:${buttonDisabled}||sendDisabled},'&nbsp;',
				{id:'_sendButton',text:'发送',handler:_send,iconCls:'icon-export',hidden:${buttonDisabled}||sendDisabled},'&nbsp;',
				{id:'_cancelButton',text:'取消',handler:_close,iconCls:'icon-undo'},'-'
			],
		renderTo:"_toolbarDiv"
	});

	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search1',text:'添加用户',handler:_createUser,iconCls:'icon-add'},'-',
				{id:'_search2',text:'添加用户组',handler:_createUserGroup,iconCls:'icon-add'},'-',
				{id:'_search3',text:'添加角色',handler:_createRole,iconCls:'icon-add'},'-',
				{id:'_search4',text:'添加组织',handler:_createOrg,iconCls:'icon-add'},'-',
				{id:'_edit',text:'编辑收件人...',handler:_expand,iconCls:'icon-edit'},'-'
			],
		width:461,
		renderTo:"toolbarDiv"
	});

var viewport = new Ext.Viewport({
    renderTo: document.body,
	layout:'border',
    //baseCls:'scarch',
    autoWidth:true,
	items:[{
		region:"north",
		contentEl:'_toolbarDiv',
		height:28
	},{
		region:'center',
		height:500,
		autoScroll:true,
		xtype:'tabpanel',
		//anchor: '-20',
		activeTab:0,
		items:[{
			title:'用户消息',
			region:'center',
			layout:'border',
			id:'mainPanel',
			height:500,
			autoScroll:true,
			items:[{
				region:'center',
				//title:'用户消息',
				layout:'fit',
				contentEl:'mainForm'
			},{
				id:"addresseePanel",
				region:'east',
				height:500,
				width:240,
				plain:true,
				autoScroll:true,
				contentEl:'gridDiv',
				xtype:'panel',
				title:'收件人',
				collapsed:false,
				collapsible: true
			}]
		},{
			title: '附件',
			layout:'fit',
			contentEl:'attachementDiv'
		}]
	}]
});
//});
Ext.onReady(function(){
_expand();
});
</script>
</html>