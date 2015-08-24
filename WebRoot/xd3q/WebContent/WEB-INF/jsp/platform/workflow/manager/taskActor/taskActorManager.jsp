<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/commons.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>节点参与者配置</title>
</head>
<body>
<form action="" name="mainForm" id="mainForm">
<input type="hidden" name="processId" id="processId" value="${processId}">
<input type="hidden" name="extProcessId" id="extProcessId" value="${extProcessId}">
<!-- table border="0">
	<tr>
		<td align="right" width="20%">参与人审批方式：</td>
		<td width="30%">
			<select>
				<option value="0">多人单审</option>
				<option value="1">多人并审</option>
			</select>
		</td>
		<td align="right" width="20%">配置类型：</td>
		<td width="30%">
			<select>
				<option value="0">人工</option>
				<option value="1">系统</option>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" width="20%">动作类型：</td>
		<td width="30%">
			<div id="conditionTypeDiv"></div>
		</td>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td align="right" width="20%">动作内容：</td>
		<td width="80%" colspan="3">
			<textarea rows="5" cols="60"></textarea>
		</td>
	</tr>
</table -->
	<div id="toolbarDiv"></div>
	<div id="dynamicDiv">
	</div>
	<div id="staticDiv">
		<div id="userDiv"></div>
		<div id="userGroupDiv"></div>
		<div id="roleDiv"></div>
		<div id="orgDiv"></div>
	</div>
</form>
</body>
<script type="text/javascript" defer="defer">



function customCallBackHandle(transport){
	//alert(transport);
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	//_getMainFrame().ExtModalWindowUtil.alert('[提示]',customField.returnMessage);
	_getMainFrame().showInfo(customField.returnMessage);
	reload_userDiv_grid("");
	reload_userGroupDiv_grid("");
	reload_roleDiv_grid("");
	reload_orgDiv_grid("");
}

function editActor(tskActCondId){//tskActCondId,ifStatementJson,typeId,thenStatement
	_getMainFrame().ExtModalWindowUtil.show('动态参与人配置',
			    '<%=request.getContextPath()%>/workflow/taskActorController.spr?action=addTaskActor&boId=${boId}&nodeId=${nodeId}&processId=${processId}&extProcessId=${extProcessId}&tskActCondId='+tskActCondId,
				'',
				createActorCallBack,
				{width:650,height:400});
}

function createActorCallBack(){
	dynamicDiv_grid.getStore().reload();
}

/**
 * 创建动态参与人配置。
 */
function _createActor(){
	_getMainFrame().ExtModalWindowUtil.show('动态参与人配置',
			    '<%=request.getContextPath()%>/workflow/taskActorController.spr?action=addTaskActor&boId=${boId}&nodeId=${nodeId}&processId=${processId}&extProcessId=${extProcessId}',
				'',
				createActorCallBack,
				{width:650,height:400});
}

/**
 * 编辑动态参与人配置。
 */
function _editActor(value,metadata,record){
	return '<a href="#" onclick="editActor(\''+record.data.tskActCondId+'\');" style="color:green">编辑</a>';
}

/**
 * 删除动态参与人。
 */
function _deleteActor(){
	var records = dynamicDiv_grid.getSelectionModel().getSelections();        			
	for(i=0;i<records.size();i++)
    {
		Ext.Ajax.request({
	    	url : contextPath+'/workflow/taskActorController.spr?action=removeTaskActorCondition',
	        params : {tskActCondId:records[i].data.tskActCondId },
	        success : function(xhr){
		    	//dynamicDiv_grid.reload();
		    	_getMainFrame().showInfo('成功删除记录！');
	        },
	        scope : this
	    });
    	
		dynamicDiv_grid.getStore().remove(records[i]);
    }
}

function _delete(mygrid){
	if (mygrid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '是否确定要删除？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
						var records = mygrid.selModel.getSelections();
		   				var idList = '';
		   				var tag = '';
						for(var i=0;i<records.length;i++){
							idList += tag + records[i].json.taskActorId;
							tag = ',';
						}
						var param = '?action=delete&idList='+idList;
						new AjaxEngine('<%=request.getContextPath()%>/workflow/taskActorController.spr', 
								{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});

	}else{
		_getMainFrame().showInfo('请选择要删除的记录！');
	}
}
function _close(){
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

function _createUser(){
	searchHelpUser.show();
}

function _deleteUser(){
	_delete(userDiv_grid);
}

function _createUserGroup(){
	searchHelpUserGroup.show();
}

function _deleteUserGroup(){
	_delete(userGroupDiv_grid);
}

function _createRole(){
	searchHelpRole.show();
}

function _deleteRole(){
	_delete(roleDiv_grid);
}

function _createOrg(){
	searchHelpOrg.show();
}

function _deleteOrg(){
	_delete(orgDiv_grid);
}

function userCallBack(jsonArrayData)
{
	var param1 = '';//Ext.util.JSON.encode(jsonArrayData);
	for(var i=0;i<jsonArrayData.length;i++){
		param1+="&actorId="+jsonArrayData[i].USERID;
		param1+="&actorName="+jsonArrayData[i].USERNAME;
	}
	var param = '?action=saveOrUpdate' + param1+"&actorType=1&nodeId=${nodeId}&processId=${processId}&extProcessId=${extProcessId}";
	new AjaxEngine('<%=request.getContextPath()%>/workflow/taskActorController.spr', 
			{method:"post", parameters: param, onComplete: callBackHandle});
}

function userGroupCallBack(jsonArrayData)
{
	var param1 = '';//Ext.util.JSON.encode(jsonArrayData);
	for(var i=0;i<jsonArrayData.length;i++){
		param1+="&actorId="+jsonArrayData[i].USERGROUPID;
		param1+="&actorName="+jsonArrayData[i].USERGROUPNAME;
	}
	var param = '?action=saveOrUpdate' + param1+"&actorType=2&nodeId=${nodeId}&processId=${processId}&extProcessId=${extProcessId}";
	new AjaxEngine('<%=request.getContextPath()%>/workflow/taskActorController.spr', 
				{method:"post", parameters: param, onComplete: callBackHandle});
}

function roleCallBack(jsonArrayData)
{
	var param1 = '';//Ext.util.JSON.encode(jsonArrayData);
	for(var i=0;i<jsonArrayData.length;i++){
		param1+="&actorId="+jsonArrayData[i].ROLEID;
		param1+="&actorName="+jsonArrayData[i].ROLENAME;
	}
	var param = '?action=saveOrUpdate' + param1+"&actorType=3&nodeId=${nodeId}&processId=${processId}&extProcessId=${extProcessId}";
	new AjaxEngine('<%=request.getContextPath()%>/workflow/taskActorController.spr', 
				{method:"post", parameters: param, onComplete: callBackHandle});
}

function orgCallBack(jsonArrayData)
{
	var param1 = '';//Ext.util.JSON.encode(jsonArrayData);
	for(var i=0;i<jsonArrayData.length;i++){
		param1+="&actorId="+jsonArrayData[i].ORGANIZATIONID;
		param1+="&actorName="+jsonArrayData[i].ORGANIZATIONNAME;
	}
	var param = '?action=saveOrUpdate' + param1+"&actorType=4&nodeId=${nodeId}&processId=${processId}&extProcessId=${extProcessId}";
	new AjaxEngine('<%=request.getContextPath()%>/workflow/taskActorController.spr', 
				{method:"post", parameters: param, onComplete: callBackHandle});
}

var searchHelpUser = new Ext.SearchHelpWindow({
	shlpName : 'YHUSER',
	callBack : function (jsonArrayData)
{
	var param1 = '';//Ext.util.JSON.encode(jsonArrayData);
	for(var i=0;i<jsonArrayData.length;i++){
		param1+="&actorId="+jsonArrayData[i].USERID;
		param1+="&actorName="+jsonArrayData[i].USERNAME;
	}
	var param = '?action=saveOrUpdate' + param1+"&actorType=1&nodeId=${nodeId}&processId=${processId}&extProcessId=${extProcessId}";
	new AjaxEngine('<%=request.getContextPath()%>/workflow/taskActorController.spr', 
			{method:"post", parameters: param, onComplete: callBackHandle});
}

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

</script>
<%--fisc:dictionary dictionaryName="YDWFCONDITIONTYPE" divId="conditionTypeDiv" sourceName="conditionType" selectedValue="" isNeedAuth="false"></fisc:dictionary--%>

<fisc:grid divId="dynamicDiv" pageSize="10" tableName="WF_TASKCONDITION A" needCheckBox="true"
 handlerClass="com.infolion.platform.workflow.manager.web.grid.TaskActorConditionGrid" needAutoLoad="true"
 whereSql="AND A.PROCESSID=${processId} AND A.NODEID=${nodeId} AND A.EXTPROCESSID='${extProcessId}'" height="150"></fisc:grid>

<fisc:grid divId="userDiv" pageSize="10" tableName="${tableName}" needAutoLoad="true"
 handlerClass="com.infolion.platform.workflow.manager.web.grid.UserGrid" needCheckBox="true"
 whereSql="${whereSql} and A.ACTORTYPE='1' and A.NODEID=${nodeId} AND A.EXTPROCESSID='${extProcessId}'"></fisc:grid>
<fisc:grid divId="userGroupDiv" pageSize="10" tableName="${tableName}" needAutoLoad="true"
 handlerClass="com.infolion.platform.workflow.manager.web.grid.UserGroupGrid" needCheckBox="true"
 whereSql="${whereSql} and A.ACTORTYPE='2' and A.NODEID=${nodeId} AND A.EXTPROCESSID='${extProcessId}'"></fisc:grid>
<fisc:grid divId="roleDiv" pageSize="10" tableName="${tableName}" needAutoLoad="true"
 handlerClass="com.infolion.platform.workflow.manager.web.grid.RoleGrid" needCheckBox="true"
 whereSql="${whereSql} and A.ACTORTYPE='3' and A.NODEID=${nodeId} AND A.EXTPROCESSID='${extProcessId}'"></fisc:grid>
<fisc:grid divId="orgDiv" pageSize="10" tableName="${tableName}" needAutoLoad="true"
 handlerClass="com.infolion.platform.workflow.manager.web.grid.OrganizationGrid" needCheckBox="true"
 whereSql="${whereSql} and A.ACTORTYPE='4' and A.NODEID=${nodeId} AND A.EXTPROCESSID='${extProcessId}'"></fisc:grid>
 
<script type="text/javascript" defer="defer">
Ext.onReady(function(){
	var toolbars = new Ext.Toolbar({
		items:[
				'-','->','-',
				{id:'_close',text:'取消',handler:_close,iconCls:'icon-undo'},'-'
			],
		renderTo:"toolbarDiv"
	});

	var tabs = new Ext.TabPanel({
		id:'gridPanel',
		renderTo:'staticDiv',
		//contentEl: 'staticDiv',
		autoWidth:false,
		loadMask:true,
		frame:true,
		border:false,
		activeItem:0,
		enableTabScroll:false,
		deferredRender:false,
		items:[
			{
				title:'用户',
				contentEl: 'userDiv',
				id:'user',
				border:false,
				layout:"fit",
				autoScroll:true
			},
			{
				title:'用户组',
				contentEl: 'userGroupDiv',
				id:'userGroup',
				border:false,
				layout:"fit",
				autoScroll:true
			},
			{
				title:'角色',
				contentEl: 'roleDiv',
				id:'role',
				border:false,
				layout:"fit",
				autoScroll:true
			},
			{
				title:'组织',
				contentEl: 'orgDiv',
				id:'rganization',
				border:false,
				layout:"fit",
				autoScroll:true
			}
		]
	});
    
	var viewport = new Ext.Viewport({
		renderTo:document.body,
		layout:"border",
		items:[{
			region:"center",
			items:[{
				region:"north",
				contentEl:'toolbarDiv',
				height:28
			},{
				region:'center',
				contentEl:'dynamicDiv',
				title:'动态分配参与人',
				autowidth:true
			},{
				region:'south',
				contentEl:'staticDiv',
				title:'静态分配参与人',
				autowidth:true
			}]
		}]
	});
});
</script>
</html>