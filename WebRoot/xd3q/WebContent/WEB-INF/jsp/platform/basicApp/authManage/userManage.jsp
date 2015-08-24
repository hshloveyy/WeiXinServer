<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
</head>
<body>
<div id="div_toolbar"></div>

<div id="search" class="search">
<form id="userSearchForm" name="userSearchForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">用户名：</td>
		<td width="30%" >
			<input type="hidden" id="userName.fieldName" name="userName.fieldName" value="userName"></input>
			<input type="text" class="inputText" id="userName.fieldValue" name="userName.fieldValue">
			<input type="hidden" id="userName.option" name="userName.option" value="like"></input>
		</td>
		<td width="15%" align="right">创建人：</td>
		<td width="40%">
			<div id="div_userId"></div>
			<input type="hidden" id="creator.fieldName" name="creator.fieldName" value="creator"></input>
			<input type="hidden" id="creator.fieldValue" name="creator.fieldValue">
			<input type="hidden" id="creator.option" name="creator.option" value="like"></input>
		</td>
	</tr>
	<tr>
		<td align="center" colspan="4" height="10"></td>
	</tr>	
</table>
</form>
</div>

<div id="div_user"></div>
<div id="div_west"></div>
<div id="div_east"></div>
</body>
</html>
<fisc:searchHelp boName="" searchType="field"  searchHelpName="YHUSER" boProperty="" hiddenName="creator.fieldValue" divId="div_userId" displayField="USERNAME" valueField="USERID" callBackHandler="callbackfuncion"></fisc:searchHelp>
<fisc:grid divId="div_user" height="360" needAutoLoad="true" boName="User" needCheckBox="true" needToolbar="true" defaultCondition="(ISDELETED is null or ISDELETED <> 'Y')" ></fisc:grid>
<script type="text/javascript" defer="defer">
function callbackfuncion(){
	$('creator.fieldValue').value = div_userId_sh.getValue() ;
}

//工具条上面的查询动作
function _searchUser(){
	var para = Form.serialize('userSearchForm');
	reload_User_grid(para);
}

function _create(){
	var url = '<%=request.getContextPath()%>/basicApp/authManage/userController.spr?action=_create';
	_getMainFrame().maintabs.addPanel('创建用户',User_grid,url);
}

function _edit(value,userName){
	var url = '<%=request.getContextPath()%>/basicApp/authManage/userController.spr?action=_edit&userId='
		+value+'&userName='+userName;
	_getMainFrame().maintabs.addPanel('编辑用户',User_grid,url);
}

function _copyCreate(value){
	//alert(value);
	if (User_grid.selModel.hasSelection() > 0){
		var records = User_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo('进行【用户复制创建】操作时，只允许选择一条记录！');
			return;
		}
		var userId = records[0].json.userId;
		_getMainFrame().maintabs.addPanel('复制创建用户',User_grid,'<%=request.getContextPath()%>/basicApp/authManage/userController.spr?action=_copyCreate&userId='+userId);
	}else{
		_getMainFrame().showInfo('请选择需要进行【用户复制创建】操作的记录！');
	}	
}

function _deletes(){
	var mygrid = User_grid;
	if (mygrid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【用户删除】操作，是否确定继续该操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
				    if (buttonid == 'yes'){
						var records = mygrid.selModel.getSelections();
		   				var idList = '';
		   				var tag = '';		
						for(var i=0;i<records.length;i++){
							//alert(records[i]);
							idList += tag + records[i].json.userId;
							tag = ',';
						}
						var param = '?action=_deletes&userIds='+idList;
						new AjaxEngine('<%=request.getContextPath()%>/basicApp/authManage/userController.spr?', 
								{method:"post", parameters: param, onComplete: callBackHandle});	
					}
				}
		});

	}else{
		_getMainFrame().showInfo('请选择需要进行【用户删除】操作的记录！');
	}
}

function _locked(){
	var mygrid = User_grid;
	if (mygrid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【用户锁定】操作，是否确定要锁定这些用户？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
						var records = mygrid.selModel.getSelections();
		   				var idList = '';
		   				var tag = '';		
						for(var i=0;i<records.length;i++){
							//alert(records[i]);
							idList += tag + records[i].json.userId;
							tag = ',';
						}
						var param = '?action=_locked&userIds='+idList;
						new AjaxEngine('<%=request.getContextPath()%>/basicApp/authManage/userController.spr?', 
								{method:"post", parameters: param, onComplete: callBackHandle});	
					}
				}
		});

	}else{
		_getMainFrame().showInfo('请选择需要进行【用户锁定】操作的记录！');
	}
	
}

function _unlock(){
	var mygrid = User_grid;
	if (mygrid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【用户批量解锁】操作，是否确定要解锁这些用户？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
						var records = mygrid.selModel.getSelections();
		   				var idList = '';
		   				var tag = '';		
						for(var i=0;i<records.length;i++){
							//alert(records[i]);
							idList += tag + records[i].json.userId;
							tag = ',';
						}
						var param = '?action=_unlock&userIds='+idList;
						new AjaxEngine('<%=request.getContextPath()%>/basicApp/authManage/userController.spr?', 
								{method:"post", parameters: param, onComplete: callBackHandle});	
					}
				}
		});

	}else{
		_getMainFrame().showInfo('请选择需要进行【用户批量解锁】操作的记录');
	}
	
}

function _view(value){
	var url = '<%=request.getContextPath()%>/basicApp/authManage/userController.spr?action=_view&userId='
		+value;
	_getMainFrame().maintabs.addPanel('查看用户',User_grid,url);
}

function _manage(value,metadata,record){
	return	'<a href="#" onclick="_view(\''+value+'\');" style="color:green">查看</a> <a href="#" onclick="_edit(\''
			+ value + '\',\''+record.data.userName+'\');" style="color:green">编辑</a>';
}

//工具条上面的清空动作
function _resetUser(){
	document.userSearchForm.reset();
}

Ext.onReady(function(){

   	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_searchUser',text:'查询',handler:_searchUser,iconCls:'icon-search'},'-',
				{id:'_resetUser',text:'清空',handler:_resetUser,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});

   	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
			region:"center",
			border:false,
			items:[{
				region:"center",
				border:false,
				items:[{
					region:"north",
					contentEl:'div_toolbar',
					border:false,
					height:25
				},{
					region:'center',
					//border:false,
					contentEl:'search'
				},{
					region:"south",
					layout:'fit',
					autoScroll: true,
		            title:"用户详情",
		            height:420,
		            border:false,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_user'
				}]
			}]
		}]
	});
});

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	//_getMainFrame().ExtModalWindowUtil.alert('[提示]',customField.returnMessage);
	//showInfo(customField.returnMessage);
	User_grid.getStore().reload();
}
</script>