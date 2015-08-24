<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年10月10日 14点59分08秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>数据传输记录表(DataTransfer)管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据传输记录表管理页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/dpframework/dataTransfers/dataTransferManage.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="15%" align="right">系统名称：</td>
		<td  width="30%" >
		<!-- UITYPE:01 -->
			<input type="text" class="inputText" id="systemname.fieldValue" name="systemname.fieldValue" value="">
			<input type="hidden" id="systemname.fieldName" name="systemname.fieldName" value="YDATATRANSFER.SYSTEMNAME"> 
			<input type="hidden" id="systemname.dataType" name="systemname.dataType" value="S">  
			<input type="hidden" id="systemname.option" name="systemname.option" value="like">
		</td>
    <td></td>
    <td></td>
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
	reload_DataTransfer_grid(para);
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle()
{
	reload_DataTransfer_grid("");
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
 * 新增数据传输记录表
 */
function _create()
{
   var para = "";
	//增加页签
	_getMainFrame().maintabs.addPanel('数据传输记录表新增',DataTransfer_grid,contextPath + '/platform/dpframework/dataTransfers/dataTransferController.spr?action=_create'+ para);
}

/**
 * grid上的 复制创建按钮调用方法
 */
function _copyCreate()
{
	if (DataTransfer_grid.selModel.hasSelection() > 0){
		var records = DataTransfer_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo('进行【数据传输记录表复制创建】操作时，只允许选择一条记录！');
			return;
		}
		var id = records[0].json.datatransferid;
		_getMainFrame().maintabs.addPanel('复制创建数据传输记录表',DataTransfer_grid,contextPath + '/platform/dpframework/dataTransfers/dataTransferController.spr?action=_copyCreate&id='+id);
	}else{
		_getMainFrame().showInfo( '请选择需要进行【数据传输记录表复制创建】操作的记录！');
	}	
}
/**
 * 正在进行数据传输的操作
 * @return
 */
function _dataTransfer(){
	if (DataTransfer_grid.selModel.hasSelection() > 0){
		var records = DataTransfer_grid.selModel.getSelections();
		if(records.length>1){
			_getMainFrame().showInfo('进行【数据传输】操作时，只允许选择一条记录！');
			return;
		}
		var id = records[0].json.datatransferid;
		_getMainFrame().maintabs.addPanel('数据传输进行中的记录',DataTransfer_grid,contextPath + '/platform/dpframework/dataTransfers/dataTransferController.spr?action=_view&id='+id);
	}else{
		_getMainFrame().showInfo('请选择需要进行【数据传输】操作的记录！');
	}	
}


/**
 * EXT 布局
 */
Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			items:[{
					region:"north",
					contentEl:'div_toolbar',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					contentEl:'div_center'
				},		
				{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:"数据传输记录表明细",
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_clean',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});
});
</script>
<fisc:grid divId="div_southForm" boName="DataTransfer" defaultCondition="${sqlWhere}" editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
