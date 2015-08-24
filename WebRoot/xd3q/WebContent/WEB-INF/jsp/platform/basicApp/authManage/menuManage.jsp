<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="search" class="search"> 
<form id="menuSearchForm" name="menuSearchForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">菜单名称:</td>
		<td width="30%">
			<input class="inputText"  type="text" id="menuName.fieldValue" name="menuName.fieldValue">
			<input type="hidden" id="menuName.fieldName" name="menuName.fieldName" value="menuName"></input>
			<input type="hidden" id="menuName.option" name="menuName.option" value="like"></input>
		</td>
		<td width="15%" align="right">是否标准菜单:</td>
		<td width="40%">
			<div id="div_isStandard"></div>
			<input type="hidden" id="isStandard.fieldName" name="isStandard.fieldName" value="isStandard"></input>
			<input type="hidden" id="isStandard.option" name="isStandard.option" value="like"></input>
		</td>
	</tr>
</table>
</form>
</div>

<div id="div_menu"></div>

<div id="div_west"></div>
<div id="div_east"></div>
</body>
</html>
<fisc:dictionary isNeedAuth="false" hiddenName="isStandard.fieldValue" dictionaryName="YDYESORNO" divId="div_isStandard"></fisc:dictionary>
<fisc:grid divId="div_menu" needAutoLoad="true" boName="Menu" needCheckBox="true" needToolbar="true"></fisc:grid>

<script type="text/javascript" defer="defer">
//工具条上面的查询动作
function _searchMenu(){
	var para = Form.serialize('menuSearchForm');
	reload_Menu_grid(para);
}

//工具条上面的清空动作
function _resetMenu(){
	$('menuSearchForm').reset();
}

//grid工具条上面的删除多条记录操作
function _deletes(){
	if (Menu_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '数据删除后将不可恢复，是否继续执行此操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (buttonid == 'yes'){
						var records = Menu_grid.selModel.getSelections();
		   				var menuList = '';			
						for(var i=0;i<records.length;i++){
							menuList = menuList + records[i].json.menuId + '|';
						}

						var param = '&menuIdList='+menuList;
						new AjaxEngine('<%= request.getContextPath() %>/basicApp/authManage/menuController.spr?action=_deleteMultiMenu', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo('请选择要删除的菜单信息！');
	}
}

//grid工具条上面的创建操作
function _create(){
	_getMainFrame().maintabs.addPanel('创建菜单信息',Menu_grid,'<%= request.getContextPath() %>/basicApp/authManage/menuController.spr?action=_create');
}

//grid工具条上面的复制创建操作
function _copyCreate(){
	if (Menu_grid.selModel.hasSelection() > 1){
		_getMainFrame().showInfo('只能选择一条菜单记录进行复制创建！');
	}else{
		if (Menu_grid.selModel.hasSelection() == 0){
			_getMainFrame().showInfo('请选择一条菜单记录进行复制创建！');
		}else{
			var records = Menu_grid.selModel.getSelections();
			
			_getMainFrame().maintabs.addPanel('复制创建菜单信息',Menu_grid,'<%= request.getContextPath() %>/basicApp/authManage/menuController.spr?action=_copyCreate&menuId='+records[0].json.menuId);
		}
	}
}

function customCallBackHandle(transport){
	var para = Form.serialize('menuSearchForm');
	reload_Menu_grid(para);
}

Ext.onReady(function(){

   	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_searchMenu',text:'查询',handler:_searchMenu,iconCls:'icon-search'},'-',
				{id:'_resetMenu',text:'清空',handler:_resetMenu,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});

	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
			region:"center",
			layout:'fit',
			border:false,
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
					contentEl:'search'
				},{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:"菜单详情",
		            height:420,
					items:[Menu_grid]
				}]
			}]
		}]
	});
});
</script>