<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户组</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_form" class="search">
  <form id="addUserGroupForm" name="addUserGroupForm">
  <table width="100%" border="0" cellpadding="4" cellspacing="1">
  <tr>
  		<td  width="15%" align="right"><font color="red">★</font>用户组名:</td>
	  	<td  width="85%" align="left" colspan="3">
		  	<input type="hidden" id="UserGroup.client" name="UserGroup.client" value="${userGroup.client}">
		  	<input type="hidden" id="UserGroup.userGroupId" name="UserGroup.userGroupId" value="${userGroup.userGroupId}">
	  		<input class="inputText" type="text" id="UserGroup.userGroupName" name="UserGroup.userGroupName" value="${userGroup.userGroupName}" width="72">
  		</td>
  </tr>
    <tr>
  		<td width="15%" align="right" valign="top">用户组描述:</td>
	  	<td width="85%" align="left" colspan="3">
	  		<textarea rows="5" cols="60" id="UserGroup.userGroupDesc" name="UserGroup.userGroupDesc">${userGroup.userGroupDesc}</textarea>
  		</td>  
  </tr>
  <tr>
  		<td width="15%" align="right">创建人:</td>
	  	<td width="30%" align="left">
	  		<fisc:user boProperty="creator" boName="UserGroup" userId="${userGroup.creator}"></fisc:user>
	  	</td>
	  	<td width="15%" align="right">创建时间:</td>
	  	<td width="40%" align="left">
	  		<input class="inputText" type="text" id="UserGroup.createTime" name="UserGroup.createTime" value="${userGroup.createTime}" readOnly="readonly">
	  	</td>
  </tr>
  <tr>
    	<td width="15%" align="right">最后修改人:</td>
	  	<td width="30%" align="left">
	  		<fisc:user boProperty="lastModifyor" boName="UserGroup" userId="${userGroup.lastModifyor}"></fisc:user>
	  	</td>
	  	<td width="15%" align="right">最后修改时间:</td>
	  	<td width="40%" align="left">
	  		<input class="inputText" type="text" id="UserGroup.lastModifyTime" name="UserGroup.lastModifyTime" value="${userGroup.lastModifyTime}" readOnly="readonly">
	  	</td>
  </tr>
  </table>
  </form>
</div>
  
<div id="div_tabPanel">
  	<div id="div_user"></div>
  	<div id="div_role"></div>
</div>

<div id="div_west"></div>
<div id="div_east"></div>
</body>
</html>
<fisc:grid divId="div_user" pageSize="200" 
 tableName="(select b.USERGROUPID,b.USERGROUPUSERID,a.USERID,a.USERNAME,a.ISLOCK,a.USERTYPE,b.MANDT from YUSER A,YUSERGROUPUSER b where 1=1  and a.USERID = b.USERID and b.MANDT='${client}') T"
 handlerClass="com.infolion.platform.basicApp.authManage.web.grid.UserUserGroupGrid"
 whereSql="" defaultCondition=" T.USERGROUPID = '${userGroup.userGroupId}'"
 needCheckBox="true" needToolbar="${isToolBar}" needAutoLoad="${isAutoLoad}"
 entityName="UserGroupUser"></fisc:grid>
 
<fisc:grid divId="div_role" pageSize="200" 
 tableName="(select b.USERGROUPID,b.USERGROUPROLEID,a.ROLEID,a.ROLENAME,a.ROLEDESC,b.MANDT from YROLE a,YUSERGROUPROLE b  where 1=1 and a.ROLEID = b.ROLEID and b.MANDT='${client}') T"
 handlerClass="com.infolion.platform.basicApp.authManage.web.grid.RoleUserGroupGrid"
 whereSql="" defaultCondition=" T.USERGROUPID = '${userGroup.userGroupId}'"
 needCheckBox="true" needToolbar="${isToolBar}" needAutoLoad="${isAutoLoad}"
 entityName="UserGroupRole"></fisc:grid>
 
<script type="text/javascript" defer="defer">
 //操作类型变量
 var strOperType = '${operType}';

//根据操作类型判断工具条上按钮的可操作性
 if (strOperType == 'create'){
 	showFlag = false;
 }
 if (strOperType == 'view'){
 	showFlag = true;
 }
 if (strOperType == 'edit'){
 	showFlag = false;
 }
 if (strOperType == 'copycreate'){
 	showFlag = false;
 }
 
//保存按钮的动作t
function _saveUserGroup(){
	if (strOperType == 'copycreate'){
		document.getElementById('UserGroup.userGroupId').value = '';
		var count = UserGroupUser_store.getCount();
		for(var i=0;i<count;i++){
			var record =UserGroupUser_store.getAt(i);
			record.set("userGroupUserId", '');
		}
		count = UserGroupRole_store.getCount();
		for(i=0;i<count;i++){
			record =UserGroupRole_store.getAt(i);
			record.set("userGroupRoleId", '');
		}
		//return true;
	}
	
	var param = "&"+Form.serialize('addUserGroupForm');
	if (strOperType == 'copycreate')
	{
		param += getAllUserGroupUserGridData();
		param += getAllUserGroupRoleGridData();
		strOperType = "edit" ;
	}
	else
	{
		param += getUserGroupUserGridData();
		param += getUserGroupRoleGridData();
	}
	param = "&action=_saveOrUpdate"+param;
	
	new AjaxEngine('<%= request.getContextPath() %>/basicApp/authManage/userGroupController.spr', 
		   {method:"post", parameters: param, onComplete: callBackHandle});
}

function _cancel(){
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

//清空按钮的动作
function _resetUserGroup(){
	document.addUserGroupForm.userGroupName.value = '';
	document.addUserGroupForm.userGroupDesc.value = '';
}
 
//角色grid上的增加和删除操作
function winRoleCallBack(jsonArrayData)
{
 	for(var i=0;i<jsonArrayData.length;i++){
 		var isExists = false;
 		for (var j = 0;j<UserGroupRole_store.getCount();j++){
 			if (UserGroupRole_store.getAt(j).get('roleId') == jsonArrayData[i].ROLEID){
 				isExists = true;
 				break;
 			}
 		}

 		if (isExists == false){
 			var roleFields = new UserGroupRole_fields({
				userGroupId:'',
				userGroupRoleId:'',
				roleId:'',
				roleName:'',
				roleDesc:''
			});

			UserGroupRole_grid.getStore().insert(0, roleFields);
			var record = UserGroupRole_store.getAt(0);
			record.set("userGroupId",'${userGroup.userGroupId}');
			record.set("roleId",jsonArrayData[i].ROLEID);
			record.set("roleName",jsonArrayData[i].ROLENAME);			
			record.set("roleDesc",jsonArrayData[i].ROLEDESC);			
 		}
 	}
}
 
var searchRoleHelpWin = new Ext.SearchHelpWindow({
		shlpName : 'YHROLE',
		callBack : winRoleCallBack
});
 
function _assigrole(){
	searchRoleHelpWin.show();
}

function _deleterole(){
	if (UserGroupRole_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
			title:'系统提示',
			msg: '数据删除后将不可恢复，是否继续执行此操作？',
			buttons: {yes:'确定', no:'取消'},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					var records = UserGroupRole_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						UserGroupRole_grid.getStore().remove(records[i]);
					}
				}
			}
		});
	}else{
		_getMainFrame().showInfo('请选择要删除的角色信息！');
	}
}

//用户grid上的增加和删除操作
function winUserCallBack(jsonArrayData)
{
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<UserGroupUser_store.getCount();j++){
			if (UserGroupUser_store.getAt(j).get('userId') == jsonArrayData[i].USERID){
				isExists = true;
				break;
			}
		}

		if (isExists == false){
			var userFields = new UserGroupUser_fields({
				userGroupUserId:'',
				userId:'',//jsonArrayData[i].USERID,
				userName:'',//jsonArrayData[i].USERNAME,
				isLock:'',//jsonArrayData[i].ISLOCK,
				userType:''//jsonArrayData[i].USERTYPE
			});

			UserGroupUser_grid.getStore().insert(0, userFields);
			var record = UserGroupUser_store.getAt(0);
			record.set("userId",jsonArrayData[i].USERID);
			record.set("userName",jsonArrayData[i].USERNAME);
			record.set("isLock",jsonArrayData[i].ISLOCK);			
			record.set("userType",jsonArrayData[i].USERTYPE);			
		}
	}
}

var searchUserHelpWin = new Ext.SearchHelpWindow({
		shlpName : 'YHUSER',
		callBack : winUserCallBack
});

function _assiguser(){
	searchUserHelpWin.show();
}

function _deleteuser(){
	if (UserGroupUser_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
			title:'系统提示',
			msg: '数据删除后将不可恢复，是否继续执行此操作？',
			buttons: {yes:'确定', no:'取消'},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					var records = UserGroupUser_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						UserGroupUser_grid.getStore().remove(records[i]);
					}
				}
			}
		});

	}else{
		_getMainFrame().showInfo('请选择要删除的用户信息！');
	}
}

function customCallBackHandle(transport){
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");

	var userGroupId = result.userGroupId ;
	document.getElementById("UserGroup.userGroupId").value = userGroupId;
	
	document.getElementById("UserGroup.creator_text").value = result.creator_text;
	document.getElementById("UserGroup.creator").value = result.creator;
	document.getElementById("UserGroup.createTime").value = result.createtime;
	document.getElementById("UserGroup.lastModifyor_text").value = result.lastmodifyer_text;
	document.getElementById("UserGroup.lastModifyor").value = result.lastmodifyer;
	document.getElementById("UserGroup.lastModifyTime").value = result.lastmodifytime;

	reload_UserGroupUser_grid("defaultCondition=T.USERGROUPID='"+ userGroupId +"'");
	reload_UserGroupRole_grid("defaultCondition=T.USERGROUPID='"+ userGroupId +"'");
	
}

Ext.onReady(function(){

   	var toolbars = new Ext.Toolbar({
		items:[
				'-','->',
				{id:'_saveUserGroup',text:'保存',handler:_saveUserGroup, iconCls:'icon-table-save',hidden:showFlag},'&nbsp;',
				{id:'_resetUserGroup',text:'取消',handler:_cancel,iconCls:'icon-undo'},
				'&nbsp;','-'
				],
		renderTo:"div_toolbar"
	});

   	var tabs = new Ext.TabPanel({
		id:'gridPanel',
		renderTo:'div_tabPanel',
		frame:true,
		border:false,
		activeItem:0,
		defaults:{bodyStyle:"background-color:#DFE8F6"},
		enableTabScroll:false,
		deferredRender:false,
		items:[{
            title: '用户',
            id:'user',
            name:'user',
            layout:'fit',
            border:false,
            contentEl:'div_user'
    	},{
            title: '角色',
            id:'role',
            name:'role',
            layout:'fit',
            border:false,
            contentEl:'div_role'
    	}]
   	});

   	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
			region:'center',
			//layout:'border',
			border:false,
			items:[{
					region:'north',
					height:25,
					border:false,
					contentEl:'div_toolbar'
				},{
					title:'用户组信息',
					region:'center',
		            layout:'fit',
		            //border:false,
		            contentEl:'div_form'
				},{
					region:'south',
					layout:'fit',
					height:380,
					contentEl:'div_tabPanel'
				}]
		}]
	});
});
 </script>