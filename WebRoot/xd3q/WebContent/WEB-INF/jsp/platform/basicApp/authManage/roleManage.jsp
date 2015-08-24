<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="search" class="search"> 
<form id="roleSearchFrom" name="roleSearchFrom">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">角色名称:</td>
		<td width="85%">
		<input type="hidden" id="roleName.fieldName" name="roleName.fieldName" value="roleName"></input>
		<input class="inputText" type="text" id="roleName.fieldValue" name="roleName.fieldValue"></input>
		<input type="hidden" id="roleName.option" name="roleName.option" value="like"></input>
		</td>
	</tr>
	<tr>
		<td align="center" colspan="4" height="10">
		</td>
	</tr>	
</table>
</form>
</div>

<div id="div_southForm"></div>

<div id="div_east"></div>
<div id="div_west"></div>
</body>
</html>
<fisc:grid divId="div_southForm" needAutoLoad="true" boName="Role" needCheckBox="true" needToolbar="true" pageSize="50"></fisc:grid>

<script type="text/javascript" defer="defer">
/**
 * 查询角色信息
 */
function _searchRole()
{
	var para = Form.serialize('roleSearchFrom');
	reload_Role_grid(para);
}

/**
 * 清空角色查询条件
 */
function _resetRole(){
	document.roleSearchFrom.reset();
}

function _create(){
	_getMainFrame().maintabs.addPanel('创建角色信息',Role_grid,'<%= request.getContextPath() %>/basicApp/authManage/roleController.spr?action=_create');
}

function _copyCreate(){
	if (Role_grid.selModel.hasSelection() > 1){
		_getMainFrame().showInfo('只能选择一条角色记录进行复制创建！');
	}else{
		if (Role_grid.selModel.hasSelection() == 0){
			_getMainFrame().showInfo('请选择一条角色记录进行复制创建！');
		}else{
			var records = Role_grid.selModel.getSelections();
			
			_getMainFrame().maintabs.addPanel('复制创建角色信息',Role_grid,'<%= request.getContextPath() %>/basicApp/authManage/roleController.spr?action=_copyCreate&roleId='+records[0].json.roleId);
		}
	}
}

function _deletes(){
	if (Role_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '数据删除后将不可恢复，是否继续执行此操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
						var records = Role_grid.selModel.getSelections();
		   				var roleList = '';			
						for(var i=0;i<records.length;i++){
							roleList = roleList + records[i].json.roleId + '|';
						}

						var param = '&roleIdList='+roleList;
						new AjaxEngine('<%= request.getContextPath() %>/basicApp/authManage/roleController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});

	}else{
		_getMainFrame().showInfo('请选择要删除的角色信息！');
	}
}

/*function _edit(id,url){
	_getMainFrame().maintabs.addPanel('编角色信息',Role_grid,'<%= request.getContextPath() %>/' + url + '&roleId=' + id);
}*/

/*function _view(id,url){
	_getMainFrame().maintabs.addPanel('查看角色信息','','<%= request.getContextPath() %>/' + url + '&roleId=' + id);
}*/

function _delete(id,url){
	_getMainFrame().Ext.MessageBox.show({
			title:'系统提示',
			msg: '数据删除后将不可恢复，是否继续执行此操作？',
			buttons: {yes:'确定', no:'取消'},
			icon: Ext.MessageBox.QUESTION,
			fn:function(buttonid){
				if (buttonid == 'yes'){
					var param = '&roleId='+id;
					new AjaxEngine('<%= request.getContextPath() %>/'+url, 
						   {method:"post", parameters: param, onComplete: callBackHandle});
				}
			}
	});
}

function customCallBackHandle(transport){
	var para = Form.serialize('roleSearchFrom');
	reload_Role_grid(para);
}

Ext.onReady(function(){

   	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_searchRole',text:'查询',handler:_searchRole,iconCls:'icon-search'},'-',
				{id:'_resetRole',text:'清空',handler:_resetRole,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});

   	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
			region:"center",
			border:false,
			layout:'fit',
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
						border:false,
						layout:'fit',
						autoScroll: true,
			            title:"角色详情",
			            height:420,
						items:[Role_grid]
					}]
			}]
		}]
	});
});
</script>
