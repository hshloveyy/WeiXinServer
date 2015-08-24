<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page
	import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@ page import="com.infolion.platform.basicApp.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增用户</title>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_userheadform" class="x-hide-display">
<form id="addUserHeadForm" name="addUserHeadForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="center" colspan="4" height="10"></td>
	</tr>
	<tr>
		<td align="right" width="15%"><font color="red">★</font>用户名：</td>
		<td align="left" width="30%"><input type="hidden"
			id="User.client" name="User.client" value=""> <input
			type="hidden" id="User.userId" name="User.userId"
			value="${user.userId}"> <input type="text" class="inputText"
			id="User.userName" name="User.userName" value="${user.userName}">
		</td>
		<td align="right" width="15%">用户描述：</td>
		<td align="left" width="40%"><input type="text" class="inputText"
			id="User.memo" name="User.memo" value="${user.memo}"></td>
	</tr>
	<tr>
		<td align="right" width="15%">创建人：</td>
		<td align="left" width="30%"><fisc:user boProperty="creator"
			boName="User" userId="${user.creator}"></fisc:user></td>
		<td align="right" width="15%">创建时间：</td>
		<td align="left" width="40%"><input type="text" class="inputText"
			id="User.createTime" name="User.createTime"
			value="${user.createTime}" readOnly="readonly"></td>
	</tr>
	<tr>
		<td align="right" width="15%">最后修改人：</td>
		<td align="left" width="30%"><fisc:user boProperty="lastModifyer"
			boName="User" userId="${user.lastModifyer}"></fisc:user></td>
		<td align="right" width="15%">最后修改时间：</td>
		<td align="left" width="40%"><input type="text" class="inputText"
			id="User.lastModifyTime" name="User.lastModifyTime"
			value="${user.lastModifyTime}" readOnly="readonly"></td>
	</tr>
	<tr>
		<td align="center" colspan="4" height="10"></td>
	</tr>
</table>
</form>
</div>

<div id="div_tabPanel" style="overflow: hidden; width: 100%">
<div id="div_role"></div>
<div id="div_usergroup"></div>
<div id="div_userSuperintend"></div>
<div id="div_userbodyform" class="x-hide-display">
<form id="addUserBodyForm" name="addUserBodyForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="center" colspan="4" height="10"></td>
	</tr>
	<tr>
		<td align="right" width="15%">别名：</td>
		<td align="left" width="30%"><input class="inputText" type="text"
			id="User.alias" name="User.alias" value="${user.alias}"></td>
		<td align="right" width="15%">初始密码：</td>
		<td align="left" width="40%"><input class="inputText"
			type="password" id="newPassWord" name="newPassWord" value=""
			size="22"> <input type="hidden" id="User.passWord"
			name="User.passWord" value="${user.passWord}"></td>
	</tr>
	<tr>
		<td align="right" width="15%">有效期起始时间：</td>
		<td align="left" width="30%">
		<div id="div_start"></div>
		</td>
		<td align="right" width="15%">有效期结束时间：</td>
		<td align="left" width="40%">
		<div id="div_end"></div>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"><font color="red">★</font>是否锁定：</td>
		<td align="left" width="30%">
		<div id="div_islock"></div>
		</td>
		<td align="right" width="15%">用户类别：</td>
		<td align="left" width="40%">
		<div id="div_usertype"></div>
		</td>
	</tr>
</table>
</form>
</div>

<div id="div_userpersonnel" class="x-hide-display">
<form id="addUserPersonnelForm" name="addUserPersonnelForm"><input
	type="hidden" name="UserPersonnel.userPersonnelId"
	id="UserPersonnel.userPersonnelId"
	value="${userPersonnel.userPersonnelId}">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="center" colspan="4" height="10"></td>
	</tr>
	<tr>
		<td align="right" width="15%"><font color="red">★</font>姓名：</td>
		<td align="left" width="30%"><input type="text" class="inputText"
			id="UserPersonnel.realName" name="UserPersonnel.realName"
			value="${userPersonnel.realName}"></td>
		<td align="right" width="15%">性别：</td>
		<td align="left" width="40%">
		<div id="div_sex"></div>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%">身份证：</td>
		<td align="left" width="30%"><input type="text" class="inputText"
			id="UserPersonnel.cardId" name="UserPersonnel.cardId"
			value="${userPersonnel.cardId}"></td>
		<td align="right" width="15%">地址：</td>
		<td align="left" width="40%"><input type="text" class="inputText"
			id="UserPersonnel.address" name="UserPersonnel.address"
			value="${userPersonnel.address}"></td>
	</tr>
	<tr>
		<td align="right" width="15%">电话：</td>
		<td align="left" width="30%"><input type="text" class="inputText"
			id="UserPersonnel.phone" name="UserPersonnel.phone"
			value="${userPersonnel.phone}"></td>
		<td align="right" width="15%">邮件地址：</td>
		<td align="left" width="40%"><input type="text" class="inputText"
			id="UserPersonnel.email" name="UserPersonnel.email"
			value="${userPersonnel.email}"></td>
	</tr>
	<tr>
		<td align="right" width="15%">职位：</td>
		<td align="left" width="30%"><!-- div id="div_place"></div --> <input
			type="text" class="inputText" id="UserPersonnel.place"
			name="UserPersonnel.place" value="${userPersonnel.place}"></td>
		<td align="right" width="15%">职务：</td>
		<td align="left" width="40%"><input type="text" class="inputText"
			id="UserPersonnel.job" name="UserPersonnel.job"
			value="${userPersonnel.job}"></td>
	</tr>
</table>
</form>
</div>
</div>
</body>
</html>

<fisc:calendar applyTo="" showTime="true" format="Y-m-d H:i:s"
	divId="div_start" fieldName="User.validDateFrom"
	defaultValue="${user.validDateFrom}"></fisc:calendar>
<fisc:calendar applyTo="" showTime="true" format="Y-m-d H:i:s"
	divId="div_end" fieldName="User.validDateTo"
	defaultValue="${user.validDateTo}"></fisc:calendar>
<fisc:dictionary boName="User" boProperty="isLock" defaultValue="N"
	dictionaryName="YDYESORNO" divId="div_islock" value="${user.isLock}"
	isNeedAuth="false"></fisc:dictionary>
<fisc:dictionary boName="User" boProperty="userType"
	dictionaryName="YDUSERTYPE" divId="div_usertype"
	value="${user.userType}" isNeedAuth="false"></fisc:dictionary>

<fisc:dictionary boName="UserPersonnel" boProperty="sex"
	dictionaryName="YDSEX" divId="div_sex" value="${userPersonnel.sex}"
	isNeedAuth="false"></fisc:dictionary>
<%--fisc:dictionary boName="UserPersonnel" boProperty="place" dictionaryName="YDSEX" divId="div_place" value="${userPersonnel.place}" isNeedAuth="false"></fisc:dictionary>
<fisc:dictionary boName="UserPersonnel" boProperty="job" dictionaryName="YDSEX" divId="div_job" value="${userPersonnel.job}" isNeedAuth="false"></fisc:dictionary--%>

<fisc:grid divId="div_role" pageSize="200"
	tableName="(select b.USERROLEID,b.USERID,a.ROLEID,a.ROLENAME,a.ROLEDESC,b.MANDT from YROLE a,YUSERROLE b where a.ROLEID = b.ROLEID and b.MANDT='${client}') T"
	handlerClass="com.infolion.platform.basicApp.authManage.web.grid.RoleUserGrid"
	whereSql="" defaultCondition="T.USERID = '${user.userId}'"
	needCheckBox="true" needToolbar="${isToolBar}"
	needAutoLoad="${isAutoLoad}" entityName="UserRole"></fisc:grid>

<fisc:grid divId="div_userSuperintend" boName="UserSuperintend"
	needCheckBox="true"
	defaultCondition=" YUSERSUPERINTEND.USERID='${user.userId}'"
	needAutoLoad="true" height="285" nameSapceSupport="false"></fisc:grid>

<fisc:grid divId="div_usergroup" pageSize="200"
	tableName="(select b.USERGROUPUSERID,b.USERID,a.USERGROUPID,a.USERGROUPNAME,b.MANDT from YUSERGROUP a,YUSERGROUPUSER b where a.USERGROUPID = b.USERGROUPID and b.MANDT='${client}') T"
	handlerClass="com.infolion.platform.basicApp.authManage.web.grid.UserGroupUserGrid"
	whereSql="" defaultCondition="T.USERID = '${user.userId}'"
	needCheckBox="true" needToolbar="${isToolBar}"
	needAutoLoad="${isAutoLoad}" entityName="UserGroupUser"></fisc:grid>

<script type="text/javascript" defer="defer">
//操作类型变量
var strOperType = '${operType}';
var islock = '${user.isLock}';

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
if ('${user.userName}'=='dpadmin'){
	showFlag = true;
	document.getElementById('User.userName').readOnly=true;
}
function removeDash(v,m){
	var s = v.split(m);
	var t='';
	for(var i=0;i<s.size();i++){
		t = t+s[i];
	}
	return t;
}

function compareTime(from,to){
	if(from=='' || to=='')
		return true;
	var _trim_from=from.split(' ');
	var _trim_to=to.split(' ');
	var _compare_YMD = parseInt(removeDash(_trim_from[0],'-'))-parseInt(removeDash(_trim_to[0],'-'));
	if(_compare_YMD<0)
		return true;
	else if(_compare_YMD>0)
		return false;
	else{
		var _compare_HmS = parseInt(removeDash(_trim_from[1],':'))-parseInt(removeDash(_trim_to[1],':'));
		if(_compare_HmS>0 || _compare_HmS==0){
			return false;
		}else
			return true;	
	}
}	


//保存按钮的动作
function _saveUser(){
	if(!compareTime(Ext.get('User.validDateFrom').getValue(),Ext.get('User.validDateTo').getValue())){
		_getMainFrame().showInfo('有效期结束时间不能小于开始时间！');
		return;
	}
	if (strOperType == 'copycreate'){
		document.getElementById('User.userId').value = '';
		document.getElementById('UserPersonnel.userPersonnelId').value = '';
		var count = UserGroupUser_store.getCount();
		for(var i=0;i<count;i++){
			var record =UserGroupUser_store.getAt(i);
			record.set("userGroupUserId", '');
		}
		count = UserRole_store.getCount();
		for(i=0;i<count;i++){
			record =UserRole_store.getAt(i);
			record.set("userRoleId", '');
		}
	}
	
	var param = Form.serialize('addUserHeadForm');
	param += "&" + Form.serialize('addUserBodyForm');
	param += "&" + Form.serialize('addUserPersonnelForm');
	

	param += getUserRoleGridData();
	param += getUserGroupUserGridData();
	param += getUserSuperintendGridData();
	param = "&" + param;
	//alert(param);
	//return;
	new AjaxEngine('<%=request.getContextPath()%>/basicApp/authManage/userController.spr?action=_saveOrUpdate', 
			{method:"post", parameters: param, onComplete: callBackHandle});
}

//清空信息
function _resetUser(){
	var tempUserId = document.addUserHeadForm.userId.value;

	document.addUserHeadForm.reset();
	document.addUserBodyForm.reset();
	document.addUserPersonnelForm.reset();

	document.addUserHeadForm.userId.value = tempUserId;
}

function _cancel(){
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

//角色grid上的增加和删除操作
function winRoleCallBack(jsonArrayData)
{
 	for(var i=0;i<jsonArrayData.length;i++){
 		var isExists = false;
 		for (var j = 0;j<UserRole_store.getCount();j++){
  			if (UserRole_store.getAt(j).get('roleId') == jsonArrayData[i].ROLEID){
 				isExists = true;
  				break;
 			}
 		}

 		if (isExists == false){
 			var roleFields = new UserRole_fields({
				userRoleId:'',
				userId:'',
				roleId:'',
				roleDesc:'',
				roleName:''
			});
 			//UserRole_grid.stopEditing();
			UserRole_grid.getStore().insert(0, roleFields);
			//UserRole_grid.startEditing(0, 0);
			var record = UserRole_grid.getStore().getAt(0);
			record.set("userId",'${user.userId}');
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
		_getMainFrame().showInfo('请选择要删除的角色信息！');
	}
}

//用户组grid上的增加和删除操作
function winUserGroupCallBack(jsonArrayData)
{
	for(var i=0;i<jsonArrayData.length;i++){
		var isExists = false;
		for (var j = 0;j<UserGroupUser_store.getCount();j++){
			if (UserGroupUser_store.getAt(j).get('userGroupId') == jsonArrayData[i].USERGROUPID){
				isExists = true;
				break;
			}
		}

		if (isExists == false){
			var userGroupFields = new UserGroupUser_fields({
				userGroupUserId:'',
				userId:'',
				userGroupId:'',
				userGroupName:''
			});
	
			UserGroupUser_grid.getStore().insert(0, userGroupFields);
			var record = UserGroupUser_store.getAt(0);
			record.set("userId",'${user.userId}');
			record.set("userGroupId",jsonArrayData[i].USERGROUPID);
			record.set("userGroupName",jsonArrayData[i].USERGROUPNAME);			
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

//分管用户功能：

/**
 * 分管用户 searchHelpCallBack
 */
function userShlWinCallBack(jsonArrayData)
{
 	for(var i=0;i<jsonArrayData.length;i++){
 		var isExists = false;
 		for (var j = 0;j<UserSuperintend_store.getCount();j++){
  			if (UserSuperintend_store.getAt(j).get('managedObjectId') == jsonArrayData[i].USERID){
 				isExists = true;
  				break;
 			}
 		}

 		if (isExists == false){
 			var roleFields = new UserSuperintend_fields({
 				superintendId:'',
				userId:'',
				managedObjectId:'',
				managedObjectName:'',
				managedObjectType:''
			});
			
 			UserSuperintend_grid.stopEditing();
			UserSuperintend_grid.getStore().insert(0, roleFields);
			UserSuperintend_grid.startEditing(0, 0);
			var record = UserSuperintend_grid.getStore().getAt(0);
			record.set("superintendId",jsonArrayData[i].SUPERINTENDID);
			record.set("userId",'${user.userId}');
			record.set("managedObjectId",jsonArrayData[i].USERID);
			record.set("managedObjectName",jsonArrayData[i].USERNAME);
			record.set("managedObjectType",'<%=Constants.ManagedObjectType.managedUser%>');
 		}
 	}
}

var userHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHUSER',
	defaultCondition: " DDLANGUAGE='<%=LanguageService.getCurrentLangCode()%>'",
	callBack : userShlWinCallBack
});
/**
 * 删除分管
 */
function _deleteSuperintend(){
	if (UserSuperintend_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
			title:'系统提示',
			msg: '数据删除后将不可恢复，是否继续执行此操作？',
			buttons: {yes:'确定', no:'取消'},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					var records = UserSuperintend_grid.selModel.getSelections();
					for (var i=0;i<records.length;i++){
						UserSuperintend_grid.getStore().remove(records[i]);
					}
				}
			}
		});

	}else{
		_getMainFrame().showInfo('请选择要删除的分管信息！');
	}
}
/**
 * 分管用户
 */
function _superintendUser(){
	userHelpWin.show();
}


//分管角色功能：
/**
 * 分管角色 searchHelpCallBack
 */
function roleShlWinCallBack(jsonArrayData)
{
 	for(var i=0;i<jsonArrayData.length;i++){
 		var isExists = false;
 		for (var j = 0;j<UserSuperintend_store.getCount();j++){
  			if (UserSuperintend_store.getAt(j).get('managedObjectId') == jsonArrayData[i].ROLEID){
 				isExists = true;
  				break;
 			}
 		}

 		if (isExists == false){
 			var roleFields = new UserSuperintend_fields({
 				superintendId:'',
				userId:'',
				managedObjectId:'',
				managedObjectName:'',
				managedObjectType:''
			});
			
 			UserSuperintend_grid.stopEditing();
			UserSuperintend_grid.getStore().insert(0, roleFields);
			UserSuperintend_grid.startEditing(0, 0);
			var record = UserSuperintend_grid.getStore().getAt(0);
			record.set("superintendId",jsonArrayData[i].SUPERINTENDID);
			record.set("userId",'${user.userId}');
			record.set("managedObjectId",jsonArrayData[i].ROLEID);
			record.set("managedObjectName",jsonArrayData[i].ROLENAME);
			record.set("managedObjectType",'<%=Constants.ManagedObjectType.managedRole%>');
 		}
 	}
}

var userRoleHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHROLE',
	callBack : roleShlWinCallBack
});


/**
* 分管角色
*/

function _superintendRole(){
	userRoleHelpWin.show();
}


//分管用户组功能：

/**
 * 分管用户组 searchHelpCallBack
 */
function userGroupShlWinCallBack(jsonArrayData)
{
 	for(var i=0;i<jsonArrayData.length;i++){
 		var isExists = false;
 		for (var j = 0;j<UserSuperintend_store.getCount();j++){
  			if (UserSuperintend_store.getAt(j).get('managedObjectId') == jsonArrayData[i].ROLEID){
 				isExists = true;
  				break;
 			}
 		}

 		if (isExists == false){
 			var roleFields = new UserSuperintend_fields({
 				superintendId:'',
				userId:'',
				managedObjectId:'',
				managedObjectName:'',
				managedObjectType:''
			});
			
 			UserSuperintend_grid.stopEditing();
			UserSuperintend_grid.getStore().insert(0, roleFields);
			UserSuperintend_grid.startEditing(0, 0);
			var record = UserSuperintend_grid.getStore().getAt(0);
			record.set("superintendId",jsonArrayData[i].SUPERINTENDID);
			record.set("userId",'${user.userId}');
			record.set("managedObjectId",jsonArrayData[i].USERGROUPID);
			record.set("managedObjectName",jsonArrayData[i].USERGROUPNAME);
			record.set("managedObjectType",'<%=Constants.ManagedObjectType.managedUserGroup%>');
 		}
 	}
}


var userGroupHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHUSERGROUP',
	callBack : userGroupShlWinCallBack
});

/**
* 分管用户组
*/
function _superintendUserGroup(){
	userGroupHelpWin.show();
}

//分管组织功能：
var userOrgHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHORGANIZATION',
	callBack : orgShlWinCallBack
});

/**
 * 分管组织 searchHelpCallBack
 */
function orgShlWinCallBack(jsonArrayData)
{
 	for(var i=0;i<jsonArrayData.length;i++){
 		var isExists = false;
 		for (var j = 0;j<UserSuperintend_store.getCount();j++){
  			if (UserSuperintend_store.getAt(j).get('managedObjectId') == jsonArrayData[i].ROLEID){
 				isExists = true;
  				break;
 			}
 		}

 		if (isExists == false){
 			var roleFields = new UserSuperintend_fields({
 				superintendId:'',
				userId:'',
				managedObjectId:'',
				managedObjectName:'',
				managedObjectType:''
			});
			
 			UserSuperintend_grid.stopEditing();
			UserSuperintend_grid.getStore().insert(0, roleFields);
			UserSuperintend_grid.startEditing(0, 0);
			var record = UserSuperintend_grid.getStore().getAt(0);
			record.set("superintendId",jsonArrayData[i].SUPERINTENDID);
			record.set("userId",'${user.userId}');
			record.set("managedObjectId",jsonArrayData[i].ORGANIZATIONID);
			record.set("managedObjectName",jsonArrayData[i].ORGANIZATIONNAME);
			record.set("managedObjectType",'<%=Constants.ManagedObjectType.managedOrganization%>');
 		}
 	}
}

/**
* 分管组织
*/
function _superintendOrg(){
	userOrgHelpWin.show();
}


//分管预算组织功能：
var userBudgetOrgHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YHBUDGETORGANIZATION',
	callBack : budgetOrgShlWinCallBack
});

/**
 * 分管组织 searchHelpCallBack
 */
function budgetOrgShlWinCallBack(jsonArrayData)
{
 	for(var i=0;i<jsonArrayData.length;i++){
 		var isExists = false;
 		for (var j = 0;j<UserSuperintend_store.getCount();j++){
  			if (UserSuperintend_store.getAt(j).get('managedObjectId') == jsonArrayData[i].BUDORGID){
 				isExists = true;
  				break;
 			}
 		}

 		if (isExists == false){
 			var roleFields = new UserSuperintend_fields({
 				superintendId:'',
				userId:'',
				managedObjectId:'',
				managedObjectName:'',
				managedObjectType:''
			});
			
 			UserSuperintend_grid.stopEditing();
			UserSuperintend_grid.getStore().insert(0, roleFields);
			UserSuperintend_grid.startEditing(0, 0);
			var record = UserSuperintend_grid.getStore().getAt(0);
			record.set("superintendId",jsonArrayData[i].SUPERINTENDID);
			record.set("userId",'${user.userId}');
			record.set("managedObjectId",jsonArrayData[i].BUDORGID);
			record.set("managedObjectName",jsonArrayData[i].BUDORGNAME);
			record.set("managedObjectType",'<%=Constants.ManagedObjectType.managedBudgetOrganization%>');
 		}
 	}
}

/**
* 分管预算组织
*/
function _superintendBudgetOrg(){
	userBudgetOrgHelpWin.show();
}

function _deleteusergroup(){
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
		_getMainFrame().showInfo('请选择要删除的用户组信息！');
	}
}


                                 
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil1.getCustomField("coustom");
	var userId = result.userId ; 
	document.getElementById("User.userId").value = userId;
	document.getElementById("UserPersonnel.userPersonnelId").value = result.userPersonnelId;

	document.getElementById("User.creator_text").value = result.creator_text;
	document.getElementById("User.creator").value = result.creator;
	document.getElementById("User.createTime").value = result.createtime;
	document.getElementById("User.lastModifyer_text").value = result.lastmodifyer_text;
	document.getElementById("User.lastModifyer").value = result.lastmodifyer;
	document.getElementById("User.lastModifyTime").value = result.lastmodifytime;

	reload_UserGroupUser_grid("defaultCondition=T.USERID='"+ userId +"'");
	reload_UserRole_grid("defaultCondition=T.USERID='"+ userId +"'");
	reload_UserSuperintend_grid("defaultCondition=YUSERSUPERINTEND.USERID='"+ userId +"'");
}


Ext.onReady(function(){

   	var toolbars = new Ext.Toolbar({
		items:[
				'-','->',
				{id:'_saveUser',text:'保存',handler:_saveUser, iconCls:'icon-table-save',hidden:showFlag},'&nbsp;',
				{id:'_resetUser',text:'取消',handler:_cancel,iconCls:'icon-undo'},
				'&nbsp;','-'
				],
		renderTo:"div_toolbar"
	});

   	/**
   	var tabs = new Ext.TabPanel({
		id:'gridPanel',
		renderTo:'div_tabPanel',
		//autoWidth:true,
		//loadMask:true,
		frame:true,
		//border:false,
		activeItem:0,
		//enableTabScroll:false,
		defaults:{bodyStyle:"background-color:#DFE8F6"},
		deferredRender:false,
		items:[{
            title: '用户信息',
            id:'user',
            name:'user',
            layout:'fit',
            height:400,
            autoWidth:true,
            border:false,
            contentEl:'div_userbodyform'
    	},{
            title: '人员信息',
            id:'personnel',
            name:'personnel',
            layout:'fit',
            height:400,
            autoWidth:true,
            border:false,
            contentEl:'div_userpersonnel'
    	},{
            title: '角色',
            id:'role',
            name:'role',
            layout:'fit',
            autoWidth:true,
            border:false,
            contentEl:'div_role'
    	},{
            title: '用户组',
            id:'usergroup',
            name:'usergroup',
            layout:'fit',
            autoWidth:true,
            border:false,
            contentEl:'div_usergroup'
    	}]
   	});
**/

   	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
			region:'center',
			border:false,
			items:[{
					region:'north',
					height:25,
					border:false,
					contentEl:'div_toolbar'
				},{
					title:'用户信息',
					region:'center',
					//border:false,
		            contentEl:'div_userheadform'
				},{
					region:'south',
					layout:'fit',
					border:false,
					height:400,
					items:[{
						id:'gridPanel',
						xtype:'tabpanel',
						autoScroll:true,
						loadMask:true,
						frame:true,
						border:false,
						activeItem:0,
						enableTabScroll:false,
						defaults:{bodyStyle:"background-color:#DFE8F6"},
						deferredRender:false,
						items:[{
				            title: '用户信息',
				            id:'user',
				            name:'user',
				            layout:'fit',
				            height:400,
				            autoWidth:true,
				            border:false,
				            contentEl:'div_userbodyform'
				    	},{
				            title: '人员信息',
				            id:'personnel',
				            name:'personnel',
				            layout:'fit',
				            height:400,
				            hidden:true,
				            autoWidth:true,
				            border:false,
				            contentEl:'div_userpersonnel'
				    	},{
				            title: '角色',
				            id:'role',
				            name:'role',
				            layout:'fit',
				            autoWidth:true,
				            border:false,
				            contentEl:'div_role'
				    	},{
				            title: '用户组',
				            id:'usergroup',
				            name:'usergroup',
				            layout:'fit',
				            autoWidth:true,
				            border:false,
				            contentEl:'div_usergroup'
				    	},
				    	{
			            title: '用户分管',
			            id:'userSuperintend',
			            name:'userSuperintend',
			            layout:'fit',
			            autoWidth:true,
			            border:false,
			            contentEl:'div_userSuperintend'
				    	}]
					  }]
				}]
		}]
	});
});
</script>