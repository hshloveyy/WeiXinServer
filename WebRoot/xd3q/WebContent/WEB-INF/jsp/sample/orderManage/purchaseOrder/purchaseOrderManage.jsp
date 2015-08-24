<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月31日 15点11分58秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象采购订单(PurchaseOrder)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/orderManage/purchaseOrder/purchaseOrderManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/orderManage/purchaseOrder/purchaseOrderManageGen.js"></script>
</head>
<body>
<fisc:grid divId="div_southForm" boName="PurchaseOrder" defaultCondition="${sqlWhere}" editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true" ></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.purchaseOrderNo}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="purchaseOrderNo.fieldValue" name="purchaseOrderNo.fieldValue" value="" <fisc:authentication sourceName="PurchaseOrder.purchaseOrderNo" taskId=""/>>
			<input type="hidden" id="purchaseOrderNo.fieldName" name="purchaseOrderNo.fieldName" value="YPURCHASEORDER.PURCHASEORDERNO"> 
			<input type="hidden" id="purchaseOrderNo.dataType" name="purchaseOrderNo.dataType" value="S">  
			<input type="hidden" id="purchaseOrderNo.option" name="purchaseOrderNo.option" value="like">
		</td>
		<td width="15%" align="right">${vt.property.supplierNo}：</td>
		<td  width="40%" >
			<input type="text" class="inputText" id="supplierNo.fieldValue" name="supplierNo.fieldValue" value="" <fisc:authentication sourceName="PurchaseOrder.supplierNo" taskId=""/>>
			<input type="hidden" id="supplierNo.fieldName" name="supplierNo.fieldName" value="YPURCHASEORDER.SUPPLIERNO"> 
			<input type="hidden" id="supplierNo.dataType" name="supplierNo.dataType" value="S">  
			<input type="hidden" id="supplierNo.option" name="supplierNo.option" value="like">
		</td>
</tr>
	<tr>
		<td width="15%" align="right">${vt.property.certificateType}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="certificateType.fieldValue" name="certificateType.fieldValue" value="" <fisc:authentication sourceName="PurchaseOrder.certificateType" taskId=""/>>
			<input type="hidden" id="certificateType.fieldName" name="certificateType.fieldName" value="YPURCHASEORDER.CERTIFICATETYPE"> 
			<input type="hidden" id="certificateType.dataType" name="certificateType.dataType" value="S">  
			<input type="hidden" id="certificateType.option" name="certificateType.option" value="like">
		</td>
    <td></td>
    <td></td>
	</tr>
	<tr>
		<td width="15%" align="right">${vt.property.certificateDate} ${vt.sFrom}：</td>
		<td width="30%" >
			<input type="hidden" id="certificateDate.fieldName" name="certificateDate.fieldName" value="YPURCHASEORDER.CERTIFICATEDATE"> 
			<input type="hidden" id="certificateDate.isRangeValue" name="certificateDate.isRangeValue" value="true">
    		<input type="text" id="certificateDate_minValue" name="certificateDate.minValue">
    		<input type="hidden" id="certificateDate.dataType" name="certificateDate.dataType" value="D"> 
			<fisc:calendar applyTo="certificateDate_minValue"  divId="" fieldName=""></fisc:calendar>
		</td>
		<td width="15%"  align="right">${vt.sTo}：</td>
		<td width="40%">
			<input type="text" id="certificateDate_maxValue" name="certificateDate.maxValue">			
			<fisc:calendar applyTo="certificateDate_maxValue"  divId="" fieldName=""></fisc:calendar>
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
<script type="text/javascript" defer="defer">
//开始取得从_manage网址上带过来的参数，并传递给控制器_create方法。
var aa='${aa}';
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	purchaseOrder:'${vt.boText}',
	purchaseOrder_Create:'${vt.boTextCreate}',
	// 复制创建
	purchaseOrder_CopyCreate:'${vt.boTextCopyCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 请选择需要进行【采购订单复制创建】操作的记录！
	purchaseOrder_CopyCreate_SelectToOperation:'${vt.copyCreate_SelectToOperate}',
	// 进行【采购订单复制创建】操作时，只允许选择一条记录！
	purchaseOrder_CopyCreate_AllowOnlyOneItemForOperation:'${vt.copyCreate_AllowOnlyOneItemForOperation}',
	// 您选择了【采购订单批量删除】操作，是否确定继续该操作？
	purchaseOrder_Deletes_ConfirmOperation:'${vt.deletes_ConfirmOperation}',
	// 请选择需要进行【采购订单批量删除】操作的记录！
	purchaseOrder_Deletes_SelectToOperation:'${vt.deletes_SelectToOperate}',
	// 凭证日期,结束日期要大于起始日期!
	certificateDate_EndDateShouldLargerStartDate:'${vt.endDateShouldLargerStartDate.certificateDate}',
	// 您选择了【采购订单删除】操作，是否确定继续该操作？
	purchaseOrder_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
