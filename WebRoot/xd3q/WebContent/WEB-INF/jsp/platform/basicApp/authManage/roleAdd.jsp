<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加角色</title>
</head>
<body>
<div id="div_toolbar" style="overflow: hidden ;width: 100%"></div>
<div id="div_form" class="search" style="overflow: hidden ;width: 100%">
  <form id="addRoleForm" name="addRoleForm"> 
  <table width="100%" border="0" cellpadding="4" cellspacing="1">
  <tr>
  		<td width="15%" align="right"><font color="red">★</font>角色名:</td>
	  	<td width="30%" align="left">
	  	<input type="hidden" id="Role.client" name="Role.client" value="${role.client}">
	  	<input type="hidden" id="Role.roleId" name="Role.roleId" value="${role.roleId}">
  		<input class="inputText" type="text" id="Role.roleName" name="Role.roleName" value="${role.roleName}"}>
  		</td>
  		<td width="15%" align="right">角色描述:</td>
  		<td width="40%" align="left" colspan="3">
  		<input class="inputText" type="text" id="Role.roleDesc" name="Role.roleDesc" value="${role.roleDesc}">
  		</td>
  </tr>
  <tr>
  		<td width="15%" align="right">创建人:</td>
	  	<td width="30%" align="left">
	  	<fisc:user boProperty="creator" boName="Role" userId="${role.creator}"></fisc:user>
	  	</td>
	  	<td width="15%" align="right">创建时间:</td>
	  	<td width="40%" align="left">
	  	<input class="inputText" type="text" id="Role.createTime" name="Role.createTime" value="${role.createTime}" readOnly="true">
	  	</td>
  </tr>
  <tr>
  		<td width="15%" align="right">最后修改人:</td>
	  	<td width="30%" align="left">
	  	<fisc:user boProperty="lastModifyor" boName="Role" userId="${role.lastModifyor}"></fisc:user>
	  	</td>
	  	<td width="15%" align="right">最后修改时间:</td>
	  	<td width="40%" align="left">
	  	<input class="inputText" type="text" id="Role.lastModifyTime" name="Role.lastModifyTime" value="${role.lastModifyTime}" readOnly="true">
	  	</td>
  </tr>
  </table>
  </form>
  </div>

  
  <div id="div_tabPanel" style="overflow: hidden ;width: 100%">
  	<div id="div_menu" style="width: 100%"></div>
  	<div id="div_resource" style="width: 100%"></div>
  	<div id="div_property" style="width: 100%"></div>
  	<div id="div_user" style="width: 100%"></div>
  	<div id="div_usergroup" style="width: 100%"></div>
  </div>
  
  <div id="div_east"></div>
  <div id="div_west"></div>

</body>
</html>
 <fisc:grid divId="div_menu" pageSize="200" 
 tableName="YROLEMENU A,YMENU B"
 handlerClass="com.infolion.platform.basicApp.authManage.web.grid.MenuRoleGrid"
 defaultCondition="  A.MENUID = B.MENUID AND A.ROLEID = '${role.roleId}' AND A.MANDT='${client}' "
 needCheckBox="true" needToolbar="${isToolBar}" needAutoLoad="${isAutoLoad}"
 entityName="RoleMenu"></fisc:grid>
 
 <fisc:grid divId="div_resource" pageSize="200" 
 tableName="YROLEMETHODRES A,YAUTHRESOURCE B"
 handlerClass="com.infolion.platform.basicApp.authManage.web.grid.MethodRoleGrid"
 defaultCondition="  A.AUTHRESOURCEID = B.AUTHRESOURCEID AND A.ROLEID = '${role.roleId}' AND A.MANDT='${client}' "
 needCheckBox="true" needToolbar="${isToolBar}" needAutoLoad="${isAutoLoad}"
 entityName="RoleMethodResource"></fisc:grid>

<fisc:grid divId="div_property" pageSize="200" 
 tableName="YPROPERTYAUTH A,YROLE B,YBUSINESSOBJECT C,YPROPERTIES D"
 handlerClass="com.infolion.platform.basicApp.authManage.web.grid.PropertyAuthRoleGrid"
 defaultCondition=" C.BOID=A.BOID AND C.BOID=D.BOID AND A.PROPERTYID = D.PROID AND A.ROLEID = B.ROLEID AND B.ROLEID = '${role.roleId}' AND B.MANDT='${client}'"
 needCheckBox="true" needToolbar="${isToolBar}" needAutoLoad="${isAutoLoad}"
 entityName="PropertyAuth"></fisc:grid>

<fisc:grid divId="div_user" pageSize="200" 
 tableName="YUSER A,YUSERROLE B"
 handlerClass="com.infolion.platform.basicApp.authManage.web.grid.UserRoleGrid"
 defaultCondition="  A.USERID = B.USERID AND B.ROLEID = '${role.roleId}' AND B.MANDT='${client}'"
 needCheckBox="true" needToolbar="${isToolBar}" needAutoLoad="${isAutoLoad}"
 entityName="UserRole"></fisc:grid>
 
<fisc:grid divId="div_usergroup" pageSize="200" 
 tableName="YUSERGROUPROLE A,YUSERGROUP B"
 handlerClass="com.infolion.platform.basicApp.authManage.web.grid.UserGroupRoleGrid"
 defaultCondition="  A.USERGROUPID = B.USERGROUPID AND A.ROLEID = '${role.roleId}' AND A.MANDT='${client}' "
 needCheckBox="true" needToolbar="${isToolBar}" needAutoLoad="${isAutoLoad}"
 entityName="UserGroupRole"></fisc:grid>
 
<script type="text/javascript" defer="defer">
var strOperType = '${operType}';
var strRoleId;
var showFlag;

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
if ('${role.roleName}'=='dp-all'){
	document.getElementById("Role.roleName").readOnly=true;
}
function customCallBackHandle(transport){
	var responseUtil = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil.getCustomField("coustom");
	var roleId = result.roleId;
	$('Role.roleId').value = result.roleId;

	if (strOperType == 'create'){
		reload_RoleMenu_grid("defaultCondition=A.MENUID = B.MENUID AND A.ROLEID = +'" +roleId + "' AND A.MANDT='${client}'");
		reload_RoleMethodResource_grid("defaultCondition=A.AUTHRESOURCEID = B.AUTHRESOURCEID AND A.ROLEID = +'" +roleId + "' AND A.MANDT='${client}'");
		reload_UserRole_grid("defaultCondition=A.USERID = B.USERID AND B.ROLEID = +'" +roleId + "' AND B.MANDT='${client}'");
		reload_UserGroupRole_grid("defaultCondition=A.USERGROUPID = B.USERGROUPID AND A.ROLEID = +'" +roleId + "'  AND A.MANDT='${client}' ");
		reload_PropertyAuth_grid("defaultCondition=C.BOID=A.BOID AND C.BOID=D.BOID AND A.PROPERTYID = D.PROID AND A.ROLEID = B.ROLEID AND B.ROLEID = +'" +roleId + "' AND B.MANDT='${client}'");

	}
	else
	{
		RoleMenu_grid.getStore().commitChanges();
		RoleMenu_grid.getStore().reload();
		RoleMethodResource_grid.getStore().commitChanges();
		RoleMethodResource_grid.getStore().reload();
		UserRole_grid.getStore().commitChanges();
		UserRole_grid.getStore().reload();
		UserGroupRole_grid.getStore().commitChanges();
		UserGroupRole_grid.getStore().reload();
		PropertyAuth_grid.getStore().commitChanges();
		PropertyAuth_grid.getStore().reload();
	}

	document.getElementById("Role.creator_text").value = result.creator_text;
	document.getElementById("Role.creator").value = result.creator;
	document.getElementById("Role.createTime").value = result.createtime;
	document.getElementById("Role.lastModifyor_text").value = result.lastmodifyer_text;
	document.getElementById("Role.lastModifyor").value = result.lastmodifyer;
	document.getElementById("Role.lastModifyTime").value = result.lastmodifytime;

	if (typeof(_getChildFrame('datalimit')["tree"]) == 'object'){
		var t = _getChildFrame('datalimit')["tree"];
	   	t.fireEvent('commitchanges');
	   	t.root.reload();
	}

}

function _saveRole(){
	if (strOperType == 'copycreate'){
		$('Role.roleId').value = '';
	}
	
	var param = Form.serialize('addRoleForm');
	param += "&strOperType="+strOperType;
	if (strOperType == 'copycreate'){
		param += getAllRoleMenuGridData();
		param += getAllRoleMethodResourceGridData();
		param += getAllUserRoleGridData();
		param += getAllUserGroupRoleGridData();
		param += getAllPropertyAuthGridData();
		//alert(param);
	}else{
		param += getRoleMenuGridData();
		param += getRoleMethodResourceGridData();
		param += getUserRoleGridData();
		param += getUserGroupRoleGridData();
		param += getPropertyAuthGridData();
	}
	
	if (typeof(_getChildFrame('datalimit')["tree"]) == 'object'){
		var t = _getChildFrame('datalimit')["tree"];
		
		if (strOperType == 'copycreate'){
			var rootNode = t.getRootNode();
			t.updateAllNodes(rootNode, false);
		}
		
	  	var modifies = [];
	  	for (var i=0;i<t.modified.length;i++){
	  		modifies.push(t.modified[i].attributes.entityAttributes);
	  	}
	  	var removes = [];
	  	for (var i=0;i<t.removed.length;i++){
	  		removes.push(t.removed[i].attributes.entityAttributes);
	  	}
	  	
	  	var dataModify= Ext.util.JSON.encode({objectName:'DataAUTHXobjectValue',operType:'modify',values:modifies});
	  	var dataRemove= Ext.util.JSON.encode({objectName:'DataAUTHXobjectValue',operType:'delete',values:removes});
	  	//alert(dataModify);
	  	param += "&subObject="+dataModify;
	  	param += "&subObject="+dataRemove;
	}

	//alert(param);

	new AjaxEngine('<%= request.getContextPath() %>/basicApp/authManage/roleController.spr?action=_saveOrUpdate', 
		   {method:"post", parameters: param, onComplete: callBackHandle});
}

function _cancelRole(){
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

function winCallBack(jsonArrayData)
{
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<UserRole_store.getCount();j++){
			if (UserRole_store.getAt(j).get('userId') == jsonArrayData[i].USERID){
				isExists = true;
				break;
			}
		}

		if (isExists == false){
			var userFields = new UserRole_fields({
				userRoleId:'',
				userId:'',
				userName:'',
				isLock:'',
				userType:''
			});

			UserRole_grid.stopEditing();
			UserRole_grid.getStore().insert(0, userFields);
			var record = UserRole_grid.getStore().getAt(0);
			record.set("userRoleId", '');
			record.set("userId",   jsonArrayData[i].USERID);
			record.set("userName", jsonArrayData[i].USERNAME);
			record.set("isLock", jsonArrayData[i].ISLOCK);
			record.set("userType", jsonArrayData[i].USERTYPE);
			UserRole_grid.startEditing(0, 0);
		}
	}
}

var searchHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHUSER',
	callBack : winCallBack
});

function _assignuser(){
	searchHelpWin.show();
}

function _deleteuser(){
	if (UserRole_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
			title:'系统提示',
			msg: '数据删除后将不可恢复，是否继续执行此操作？',
			buttons: {yes:'确定', no:'取消'},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					var records = UserRole_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						UserRole_grid.getStore().remove(records[i]);
					}
				}
			}
		});
	}else{
		_getMainFrame().showInfo('请选择要删除的用户信息！');
	}
}

function winUserGroupCallBack(jsonArrayData)
{
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<UserGroupRole_store.getTotalCount();j++){
			if (UserGroupRole_store.getAt(j).get('userGroupId') == jsonArrayData[i].USERGROUPID){
				isExists = true;
				break;
			}
		}

		if (isExists == false){
			var userGroupFields = new UserGroupRole_fields({
				usergrouproleid:'',
				userGroupId:'',
				usergroupname:''
			});
	
			UserGroupRole_grid.stopEditing();
			UserGroupRole_grid.getStore().insert(0, userGroupFields);
			var record = UserGroupRole_grid.getStore().getAt(0);
			record.set("usergrouproleid", '');
			record.set("userGroupId", jsonArrayData[i].USERGROUPID);
			record.set("usergroupname", jsonArrayData[i].USERGROUPNAME);
			UserGroupRole_grid.startEditing(0, 0);
		}
	}
}

var searchUserGroupHelpWin = new Ext.SearchHelpWindow({
   		shlpName : 'YHUSERGROUP',
   		callBack : winUserGroupCallBack
});

function _assignusergroup(){
	searchUserGroupHelpWin.show();
}

function _deleteusergroup(){
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
		_getMainFrame().showInfo('请选择要删除的用户组信息！');
	}
}

function winMenuCallBack(jsonArrayData)
{
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<RoleMenu_store.getCount();j++){
			if (RoleMenu_store.getAt(j).get('menuId') == jsonArrayData[i].MENUID){
				isExists = true;
				break;
			}
		}

		if (isExists == false){
			var menuFields = new RoleMenu_fields({
				roleMenuId:'',
				menuId:'',
				menuname:'',
				menudesc:'',
				isstandard:''
			});
	
			RoleMenu_grid.stopEditing();
			RoleMenu_grid.getStore().insert(0, menuFields);
			var record = RoleMenu_grid.getStore().getAt(0);
			record.set("roleMenuId", '');
			record.set("menuId", jsonArrayData[i].MENUID);
			record.set("menuname", jsonArrayData[i].MENUNAME);
			record.set("menudesc", jsonArrayData[i].MENUDESC);
			record.set("isstandard", jsonArrayData[i].ISSTANDARD);
			RoleMenu_grid.startEditing(0, 0);
		}
	}
}

var searchMenuHelpWin = new Ext.SearchHelpWindow({
		shlpName : 'YHMENU',
		callBack : winMenuCallBack
});

function _assignmenu(){
	searchMenuHelpWin.show();
}

function _deletemenu(){
	if (RoleMenu_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
			title:'系统提示',
			msg: '数据删除后将不可恢复，是否继续执行此操作？',
			buttons: {yes:'确定', no:'取消'},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					var records = RoleMenu_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						RoleMenu_grid.getStore().remove(records[i]);
					}
				}
			}
		});

	}else{
		_getMainFrame().showInfo('请选择要删除的菜单信息！');
	}
}

function winResourceCallBack(jsonArrayData){
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<RoleMethodResource_store.getCount();j++){
			if (RoleMethodResource_store.getAt(j).get('authResourceId') == jsonArrayData[i].AUTHRESOURCEID){
				isExists = true;
				break;
			}
		}

		if (isExists == false){
			var resourceFields = new RoleMethodResource_fields({
				roleAuthResourceId:'',
				authResourceId:'',
				authresourcedesc:'',
				methodname:'',
				methoddesc:''
			});
		
			RoleMethodResource_grid.stopEditing();
			RoleMethodResource_grid.getStore().insert(0, resourceFields);
			var record = RoleMethodResource_grid.getStore().getAt(0);
			record.set("roleAuthResourceId", '');
			record.set("authResourceId", jsonArrayData[i].AUTHRESOURCEID);
			record.set("authresourcedesc", jsonArrayData[i].AUTHRESOURCEDESC);
			record.set("methodname", jsonArrayData[i].METHODNAME);
			record.set("methoddesc", jsonArrayData[i].METHODDESC);
			
			RoleMethodResource_grid.startEditing(0, 0);
		}
	}
}

var searchResourceHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHAUTHMETHOD',
	callBack : winResourceCallBack
});

function _assigresource(){
	searchResourceHelpWin.show();
}

function _deleteresource(){
	if (RoleMethodResource_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
			title:'系统提示',
			msg: '数据删除后将不可恢复，是否继续执行此操作？',
			buttons: {yes:'确定', no:'取消'},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					var records = RoleMethodResource_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						RoleMethodResource_grid.getStore().remove(records[i]);
					}
				}
			}
		});

	}else{
		_getMainFrame().showInfo('请选择要删除的方法信息！');
	}
}

function winPropertyAuthCallBack(jsonArrayData)
{
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<PropertyAuth_store.getCount();j++){
			if (PropertyAuth_store.getAt(j).get('propertyId') == jsonArrayData[i].PROPERTYID){
				isExists = true;
				break;
			}
		}

		if (isExists == false){
			var propertyAuthFields = new PropertyAuth_fields({
				propertyAuthId:'',
				roleId:'',
				boId:'',
				boName:'',
				propertyId:'',
				propertyName:''
			});
	
			PropertyAuth_grid.stopEditing();
			PropertyAuth_grid.getStore().insert(0, propertyAuthFields);
			var record = PropertyAuth_grid.getStore().getAt(0);
			record.set("roleId", '${role.roleId}');
			record.set("boId", jsonArrayData[i].BOID);
			record.set("boName", jsonArrayData[i].BONAME);
			record.set("propertyId", jsonArrayData[i].PROID);
			record.set("propertyName", jsonArrayData[i].FIELDTEXT);
			PropertyAuth_grid.startEditing(0, 0);
		}
	}
}

var searchPropertyAuthHelpWin = new Ext.SearchHelpWindow({
		shlpName : 'YHBOPROPERTIES',
		callBack : winPropertyAuthCallBack
});

function _assignPropertyAuth(){
	searchPropertyAuthHelpWin.show();
}

function _deletePropertyAuth(){
	if (PropertyAuth_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
			title:'系统提示',
			msg: '数据删除后将不可恢复，是否继续执行此操作？',
			buttons: {yes:'确定', no:'取消'},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					var records = PropertyAuth_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						PropertyAuth_grid.getStore().remove(records[i]);
					}
				}
			}
		});

	}else{
		_getMainFrame().showInfo('请选择要删除的属性访问权限信息！');
	}
}

Ext.onReady(function(){
   	var toolbars = new Ext.Toolbar({
		items:['->',
				{id:'_saveRole',text:'保存',handler:_saveRole, iconCls:'icon-table-save',disabled:showFlag,hidden:(strOperType == 'view')},'-',
				{id:'_cancelRole',text:'取消',handler:_cancelRole,iconCls:'icon-undo'},'-'
				],
		renderTo:"div_toolbar"
	});

   	var tabs = new Ext.TabPanel({
		id:'gridPanel',
		renderTo:'div_tabPanel',
		autoScroll:true,
		loadMask:true,
		frame:true,
		border:false,
		activeItem:0,
		enableTabScroll:false,
		deferredRender:false,
		items:[{
    		title: '菜单',
    		id:'menu',
    		name:'menu',
    		layout:'fit',
    		border:false,
            contentEl:'div_menu'
    	},{
            title: '方法',
            id:'resource',
            name:'resource',
            layout:'fit',
            border:false,
            contentEl:'div_resource'
    	},{
    		xtype:'iframepanel',
    		title: '数据权限',
            id:'datalimit',
            name:'datalimit',
            border:false,
            anchor: '100% 100%',
            closable: false
    	},{
            title: '属性访问权限',
            id:'property',
            name:'property',
            layout:'fit',
            border:false,
            contentEl:'div_property'
    	},{
            title: '用户',
            id:'user',
            name:'user',
            layout:'fit',
            border:false,
            contentEl:'div_user'
    	},{
            title: '用户组',
            id:'usergroup',
            name:'usergroup',
            layout:'fit',
            border:false,
            contentEl:'div_usergroup'
    	}]
   	});

   	var tabHeight = tabs.getInnerHeight();
	var url = contextPath  + '/basicApp/authManage/roleController.spr?action=dataAUTHXobjectManageView&roleId=${role.roleId}&isToolBar=${isToolBar}';
	tabs.on('tabchange', function (){
   		var tab = tabs.getActiveTab();
   	   	if(tab.name=='datalimit'){ 
   	   		tab.setHeight(tabHeight);
 	   		if(!tab.iframe.src)tab.setSrc(url);
   	   	}
	});
		
   	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
			region:'center',
			border:false,
			//layout:'border',
			items:[{
					region:'north',
					height:25,
					border:false,
					contentEl:'div_toolbar'
				},{
					title:'角色信息',
					region:'center',
		            layout:'fit',
		            //border:false,
		            contentEl:'div_form'
				},{
					region:'south',
					layout:'fit',
					border:false,
					height:400,
					contentEl:'div_tabPanel'
				}]
		}]
	});
});
</script>

