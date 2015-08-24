<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年01月09日 11点03分54秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象采购订单(SAP)(PurchaseInfo)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchaseInfoManage.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchaseInfoManageGen.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.invoicingParty}：</td>
		<td  width="30%" >
		<!-- UITYPE:11 -->
			<div id="div_invoicingParty_sh"></div>
			<input type="hidden" id="invoicingParty.fieldName" name="invoicingParty.fieldName" value="YPURCHASEINFO.INVOICINGPARTY"> 
			<input type="hidden" id="invoicingParty.dataType" name="invoicingParty.dataType" value="S">  
			<input type="hidden" id="invoicingParty.option" name="invoicingParty.option" value="like">
			<fisc:searchHelp divId="div_invoicingParty_sh" boName="PurchaseInfo" boProperty="invoicingParty" searchType="field" hiddenName="invoicingParty.fieldValue" valueField="PARTNERID" displayField="PARTNERNAME"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">${vt.property.bstyp}：</td>
		<td  width="40%" >
		<!-- UITYPE:01 -->
			<input type="text" class="inputText" id="bstyp.fieldValue" name="bstyp.fieldValue" value="" <fisc:authentication sourceName="PurchaseInfo.bstyp" taskId=""/>>
			<input type="hidden" id="bstyp.fieldName" name="bstyp.fieldName" value="YPURCHASEINFO.BSTYP"> 
			<input type="hidden" id="bstyp.dataType" name="bstyp.dataType" value="S">  
			<input type="hidden" id="bstyp.option" name="bstyp.option" value="like">
		</td>
</tr>
	<tr>
		<td width="15%" align="right">${vt.property.lifnrName}：</td>
		<td  width="30%" >
		<!-- UITYPE:11 -->
			<div id="div_lifnrName_sh"></div>
			<input type="hidden" id="lifnrName.fieldName" name="lifnrName.fieldName" value="YPURCHASEINFO.LIFNRNAME"> 
			<input type="hidden" id="lifnrName.dataType" name="lifnrName.dataType" value="S">  
			<input type="hidden" id="lifnrName.option" name="lifnrName.option" value="like">
			<fisc:searchHelp divId="div_lifnrName_sh" boName="PurchaseInfo" boProperty="lifnrName" searchType="field" hiddenName="lifnrName.fieldValue" valueField="COUNTRYCODE" displayField="SUPPLIERNAME"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">${vt.property.waers}：</td>
		<td  width="40%" >
		<!-- UITYPE:11 -->
			<div id="div_waers_sh"></div>
			<input type="hidden" id="waers.fieldName" name="waers.fieldName" value="YPURCHASEINFO.WAERS"> 
			<input type="hidden" id="waers.dataType" name="waers.dataType" value="S">  
			<input type="hidden" id="waers.option" name="waers.option" value="like">
			<fisc:searchHelp divId="div_waers_sh" boName="PurchaseInfo" boProperty="waers" searchType="field" hiddenName="waers.fieldValue" valueField="CURRENCYCODE" displayField="CURRENCYCODETEXT"></fisc:searchHelp>
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
</html>
<script type="text/javascript">

//页面文本
var Txt={
	// 采购订单
	purchaseInfo:'${vt.boText}',
	purchaseInfo_Create:'${vt.boTextCreate}',
	// 复制创建
	purchaseInfo_CopyCreate:'${vt.boTextCopyCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 请选择需要进行【采购订单(SAP)复制创建】操作的记录！
	purchaseInfo_CopyCreate_SelectToOperation:'${vt.copyCreate_SelectToOperate}',
	// 进行【采购订单(SAP)复制创建】操作时，只允许选择一条记录！
	purchaseInfo_CopyCreate_AllowOnlyOneItemForOperation:'${vt.copyCreate_AllowOnlyOneItemForOperation}',
	// 您选择了【采购订单(SAP)批量删除】操作，是否确定继续该操作？
	purchaseInfo_Deletes_ConfirmOperation:'${vt.deletes_ConfirmOperation}',
	// 请选择需要进行【采购订单(SAP)批量删除】操作的记录！
	purchaseInfo_Deletes_SelectToOperation:'${vt.deletes_SelectToOperate}',
	// 您选择了【采购订单(SAP)删除】操作，是否确定继续该操作？
	purchaseInfo_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
<fisc:grid divId="div_southForm" boName="PurchaseInfo"  editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
