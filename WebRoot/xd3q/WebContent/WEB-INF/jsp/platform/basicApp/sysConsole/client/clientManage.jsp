<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年07月21日 19点26分29秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>客户端信息(Client)管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户端信息管理页面</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">客户端编号：${clientNo}</td>
		<td width="30%" >
		<!-- UITYPE:01 -->
			<input type="text" id="clientNo.fieldValue" name="clientNo.fieldValue" value="">
			<input type="hidden" id="clientNo.fieldName" name="clientNo.fieldName" value="CLIENT"> 
			<input type="hidden" id="clientNo.dataType" name="clientNo.dataType" value="S">  
			<input type="hidden" id="clientNo.option" name="clientNo.option" value="like">
		</td>
		<td width="15%" align="right">客户端描述：${clientDesc}</td>
		<td width="40%" >
		<!-- UITYPE:01 -->
			<input type="text" id="clientDesc.fieldValue" name="clientDesc.fieldValue" value="">
			<input type="hidden" id="clientDesc.fieldName" name="clientDesc.fieldName" value="CLIENTDESC"> 
			<input type="hidden" id="clientDesc.dataType" name="clientDesc.dataType" value="S">  
			<input type="hidden" id="clientDesc.option" name="clientDesc.option" value="like">
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
</body>
<script type="text/javascript" defer="defer">
/**
 * 查询
 */
function _search()
{
	var para = Form.serialize('mainForm');
	reload_Client_grid(para);
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_Client_grid("");
}

/**
 * 清空操作
 */
function _resetForm()
{
	document.all.mainForm.reset();
}

/**
 * grid 上的 创建按钮调用方法 
 * 新增客户端信息
 */
function _create()
{
	//增加页签
	_getMainFrame().maintabs.addPanel('客户端信息新增',Client_grid,'<%= request.getContextPath() %>/basicApp/client/clientController.spr?action=_create');
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
	if (Client_grid.selModel.hasSelection() > 0){
		var records = Client_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo('进行【客户端信息复制创建】操作时，只允许选择一条记录！');
			return;
		}
		var clientId = records[0].json.clientId;
		_getMainFrame().maintabs.addPanel('复制创建客户端信息',Client_grid,'<%= request.getContextPath() %>/basicApp/client/clientController.spr?action=_copyCreate&clientId='+clientId);
	}else{
		_getMainFrame().showInfo('请选择需要进行【客户端信息复制创建】操作的记录！');
	}	
}



/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除客户端信息
 */
function _deletes()
{
	if (Client_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【客户端信息批量删除】操作，是否确定继续该操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (result == 'yes'){
						var records = Client_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.clientId + '|';
						}

						var param = '&clientIds='+ids;
						new AjaxEngine('<%= request.getContextPath() %>/basicApp/client/clientController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});

	}else{
		_getMainFrame().showInfo('请选择需要进行【客户端信息批量删除】操作的记录！');
	}
}

/**
 * EXT 布局
 */
Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
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
					contentEl:'div_center'
				},		
				{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:"客户端信息明细",
		            height:380,
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-cls'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-cls'},'-'
				],
		renderTo:"div_toolbar"
	});
});
</script>
<fisc:grid divId="div_southForm" boName="Client" editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
</html>
