<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户组管理</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="search">
<form id="userGroupSearchForm" name="userGroupSearchForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">用户组名:</td>
		<td width="30%">
			<input class="inputText" type="text" id="userGroupName.fieldValue" name="userGroupName.fieldValue">
			<input type="hidden" id="userGroupName.fieldName" name="userGroupName.fieldName" value="userGroupName"></input>
			<input type="hidden" id="userGroupName.option" name="userGroupName.option" value="like"></input>
		</td>
		<td width="15%" align="right">创建人:</td>
		<td width="40%">
			<input class="inputText" type="text" id="creator.fieldValue" name="creator.fieldValue">
			<input type="hidden" id="creator.fieldName" name="creator.fieldName" value="creator"></input>
			<input type="hidden" id="creator.option" name="creator.option" value="equal"></input>
		</td>
	</tr>
</table>
</form>
</div>

<div id="div_usergroup"></div>
<div id="div_west"></div>
<div id="div_east"></div>
</body>
<script type="text/javascript" defer="defer">
//工具条上面的查询动作
function _searchUserGroup(){
	var para = Form.serialize('userGroupSearchForm');
	reload_UserGroup_grid(para);
}

//工具条上面的清空动作
function _resetUserGroup(){
	document.userGroupSearchForm.reset();
}

function _create(value){
	var url = '<%=request.getContextPath()%>/basicApp/authManage/userGroupController.spr?action=_create&userGroupId='
		+value;
	_getMainFrame().maintabs.addPanel('创建用户组',UserGroup_grid,url);
}


function _copyCreate(value){
	if (UserGroup_grid.selModel.hasSelection() > 0){
		var records = UserGroup_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo('进行【用户组复制创建】操作时，只允许选择一条记录！');
			return;
		}
		var userGroupId = records[0].json.userGroupId;
		_getMainFrame().maintabs.addPanel('复制创建用户组',UserGroup_grid,'<%=request.getContextPath()%>/basicApp/authManage/userGroupController.spr?action=_copyCreate&userGroupId='+userGroupId);
	}else{
		_getMainFrame().showInfo('请选择需要进行【用户组复制创建】操作的记录！');
	}	
}

function _deletes(){
	var mygrid = UserGroup_grid;
	if (mygrid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【用户组删除】操作，是否确定继续该操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
						var records = mygrid.selModel.getSelections();
		   				var idList = '';
		   				var tag = '';		
						for(var i=0;i<records.length;i++){
							//alert(records[i]);
							idList += tag + records[i].json.userGroupId;
							tag = ',';
						}
						var param = '?action=_deletes&userGroupIds='+idList;
						new AjaxEngine('<%=request.getContextPath()%>/basicApp/authManage/userGroupController.spr', 
								{method:"post", parameters: param, onComplete: callBackHandle});	
					}
				}
		});

	}else{
		_getMainFrame().showInfo('请选择需要进行【用户组批量删除】操作的记录！');
	}
}

</script>
<fisc:grid divId="div_usergroup" needAutoLoad="true" boName="UserGroup" needCheckBox="true" needToolbar="true"></fisc:grid>
<script type="text/javascript" defer="defer">
Ext.onReady(function(){

   	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_searchUserGroup',text:'查询',handler:_searchUserGroup,iconCls:'icon-search'},'-',
				{id:'_resetUserGroup',text:'清空',handler:_resetUserGroup,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			border:false,
			layout:'fit',
			items:[{
				region:"center",
				border:false,
				items:[{
					region:"north",
					border:false,
					contentEl:'div_toolbar',
					height:25
				},{
					region:'center',
					//border:false,
					contentEl:'search'
				},{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:"用户组详情",
		            bodyStyle:"background-color:#DFE8F6",
		            height:420,
					items:[UserGroup_grid]
				}]
			}]
		}]
	});
});

function customCallBackHandle(transport){
	//alert(transport);
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	//_getMainFrame().ExtModalWindowUtil.alert('[提示]',customField.returnMessage);
	//showInfo(customField.returnMessage);
	reload_UserGroup_grid("");
}
</script>
</html>