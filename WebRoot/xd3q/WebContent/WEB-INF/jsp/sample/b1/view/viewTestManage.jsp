<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月22日 15点37分23秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象视图测试(ViewTest)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/view/viewTestManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/view/viewTestManageGen.js"></script>
</head>
<body>
<fisc:grid divId="div_southForm" boName="ViewTest"  editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.yeusername}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="yeusername.fieldValue" name="yeusername.fieldValue" value="" <fisc:authentication sourceName="ViewTest.yeusername" taskId=""/>>
			<input type="hidden" id="yeusername.fieldName" name="yeusername.fieldName" value="YVLJX.USERNAME"> 
			<input type="hidden" id="yeusername.dataType" name="yeusername.dataType" value="S">  
			<input type="hidden" id="yeusername.option" name="yeusername.option" value="like">
		</td>
		<td width="15%" align="right">${vt.property.yebyname}：</td>
		<td  width="40%" >
			<input type="text" class="inputText" id="yebyname.fieldValue" name="yebyname.fieldValue" value="" <fisc:authentication sourceName="ViewTest.yebyname" taskId=""/>>
			<input type="hidden" id="yebyname.fieldName" name="yebyname.fieldName" value="YVLJX.BYNAME"> 
			<input type="hidden" id="yebyname.dataType" name="yebyname.dataType" value="S">  
			<input type="hidden" id="yebyname.option" name="yebyname.option" value="like">
		</td>
</tr>
	<tr>
		<td width="15%" align="right">${vt.property.yerolename}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="yerolename.fieldValue" name="yerolename.fieldValue" value="" <fisc:authentication sourceName="ViewTest.yerolename" taskId=""/>>
			<input type="hidden" id="yerolename.fieldName" name="yerolename.fieldName" value="YVLJX.ROLENAME"> 
			<input type="hidden" id="yerolename.dataType" name="yerolename.dataType" value="S">  
			<input type="hidden" id="yerolename.option" name="yerolename.option" value="like">
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
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	viewTest:'${vt.boText}',
	// 您选择了【视图测试删除】操作，是否确定继续该操作？
	viewTest_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
	// 提示
	cue:'${vt.sCue}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}',
	// 创建
	mCreate:'${vt.mCreate}'
};

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
		            title:'${vt.boTextDetail}',
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'${vt.mQuery}',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'${vt.sClean}',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});
});
</script>
