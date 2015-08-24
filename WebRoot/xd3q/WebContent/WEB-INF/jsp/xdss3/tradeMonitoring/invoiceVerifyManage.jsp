<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年07月06日 11点02分31秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象发票校验(InvoiceVerify)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/tradeMonitoring/invoiceVerifyManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/tradeMonitoring/invoiceVerifyManageGen.js"></script>
</head>
<body>
<fisc:grid divId="div_southForm" boName="InvoiceVerify"  editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true" ></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.belnr}：</td>
		<td  width="30%" >
			<div id="div_belnr_sh"></div>
			<input type="hidden" id="belnr.fieldName" name="belnr.fieldName" value="YGETBELNR.BELNR"> 
			<input type="hidden" id="belnr.dataType" name="belnr.dataType" value="S">  
			<input type="hidden" id="belnr.option" name="belnr.option" value="like">
			<fisc:searchHelp divId="div_belnr_sh" boName="InvoiceVerify" boProperty="belnr" searchType="field" hiddenName="belnr.fieldValue" valueField="BELNR" displayField="BELNR"></fisc:searchHelp>
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
	invoiceVerify:'${vt.boText}',
	// 您选择了【发票校验删除】操作，是否确定继续该操作？
	invoiceVerify_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
