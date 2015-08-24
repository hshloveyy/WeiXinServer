<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年08月13日 17点15分10秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>excel模版(ExcelTemplate)管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>excel模版管理页面</title>
</head>
<body>
<fisc:grid divId="div_southForm" boName="ExcelTemplate" editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>

<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">模版描述：</td>
		<td width="30%">
			<input class="inputText" type="text" id="tempDesc.fieldValue" name="tempDesc.fieldValue" value="">
			<input type="hidden" id="tempDesc.fieldName" name="tempDesc.fieldName" value="TEMPDESC"> 
			<input type="hidden" id="tempDesc.dataType" name="tempDesc.dataType" value="S">
			<input type="hidden" id="tempDesc.option" name="tempDesc.option" value="like">
		</td>
		<td width="15%" align="right">业务对象名称：</td>
		<td width="40%">
			<input class="inputText" type="text" id="boName.fieldValue" name="boName.fieldValue" value="">
			<input type="hidden" id="boName.fieldName" name="boName.fieldName" value="BONAME"> 
			<input type="hidden" id="boName.dataType" name="boName.dataType" value="S">
			<input type="hidden" id="boName.option" name="boName.option" value="like">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">方法名：</td>
		<td width="30%">
			<input class="inputText" type="text" id="methodName.fieldValue" name="methodName.fieldValue" value="">
			<input type="hidden" id="methodName.fieldName" name="methodName.fieldName" value="METHODNAME"> 
			<input type="hidden" id="methodName.dataType" name="methodName.dataType" value="S">
			<input type="hidden" id="methodName.option" name="methodName.option" value="like">
		</td> 
    <td width="15%"></td>
    <td width="40%"></td>
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
</html>
<script type="text/javascript" defer="defer">
/**
 * 查询
 */
function _search()
{
	var para = Form.serialize('mainForm');
	reload_ExcelTemplate_grid(para);
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_ExcelTemplate_grid("");
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
 * 新增excel模版
 */
function _create()
{
	//增加页签
	_getMainFrame().maintabs.addPanel('excel模版新增',ExcelTemplate_grid,'<%= request.getContextPath() %>/excelTemplateController.spr?action=_create');
}

/**
 * grid 上的 删除按钮调用方法，批量删除
 * 批量删除excel模版
 */
function _deletes()
{
	if (ExcelTemplate_grid.selModel.hasSelection() > 0){
		_getMainFrame().Ext.MessageBox.show({
				title:'系统提示',
				msg: '您选择了【excel模版批量删除】操作，是否确定继续该操作？',
				buttons: {yes:'确定', no:'取消'},
				icon: Ext.MessageBox.QUESTION,
				fn:function(buttonid){
					if (result == 'yes'){
						var records = ExcelTemplate_grid.selModel.getSelections();
		   				var ids = '';			
						for(var i=0;i<records.length;i++){
							ids = ids + records[i].json.tempId + '|';
						}

						var param = '&excelTemplateIds='+ids;
						new AjaxEngine('<%= request.getContextPath() %>/excelTemplateController.spr?action=_deletes', 
						{method:"post", parameters: param, onComplete: callBackHandle});
					}
				}
		});
	}else{
		_getMainFrame().showInfo('请选择需要进行【excel模版批量删除】操作的记录！');
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
					border:false,
					contentEl:'div_toolbar',
					height:26
				},{
					region:'center',
					border:false,
					contentEl:'div_center'
				},		
				{
					region:"south",
					border:false,
					layout:'fit',
					autoScroll: true,
		            title:"excel模版明细",
		            height:380,
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});
});
</script>
