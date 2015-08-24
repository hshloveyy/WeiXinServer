<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年08月02日 13点55分34秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象内贸付款(HomePayment)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/payment/homePaymentManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/payment/homePaymentManageGen.js"></script>
</head>
<body>
<!------------------手工修改------------------>
<fisc:grid divId="div_southForm" 
boName="HomePayment"  
editable="false" 
needCheckBox="true" 
needToolbar="true" 
needAutoLoad="true" 
needAuthentication="true" orderBySql="YPAYMENT.CREATETIME desc"
defaultCondition="YPAYMENT.TRADE_TYPE = '${tradetype}' AND YPAYMENT.PAYMENTTYPE='${paymenttype}'"></fisc:grid>
<!----------------手工修改------------------>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.paymentno}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="paymentno.fieldValue" name="paymentno.fieldValue" value="" <fisc:authentication sourceName="HomePayment.paymentno" taskId=""/>>
			<input type="hidden" id="paymentno.fieldName" name="paymentno.fieldName" value="YPAYMENT.PAYMENTNO"> 
			<input type="hidden" id="paymentno.dataType" name="paymentno.dataType" value="S">  
			<input type="hidden" id="paymentno.option" name="paymentno.option" value="like">
		</td>
		<td width="15%" align="right">${vt.property.supplier}：</td>
		<td  width="40%" >
			<div id="div_supplier_sh"></div>
			<input type="hidden" id="supplier.fieldName" name="supplier.fieldName" value="YPAYMENT.SUPPLIER"> 
			<input type="hidden" id="supplier.dataType" name="supplier.dataType" value="S">  
			<input type="hidden" id="supplier.option" name="supplier.option" value="like">
			<fisc:searchHelp divId="div_supplier_sh" boName="ImportPayment" boProperty="supplier" searchType="field" hiddenName="supplier.fieldValue" valueField="LIFNR" displayField="NAME1"></fisc:searchHelp>
		</td>
</tr>
	<tr>
		<td width="15%" align="right">${vt.property.paymentstate}：</td>
		<td  width="30%" >
			<div id="div_paymentstate_dict"></div>
			<input type="hidden" id="paymentstate.fieldName" name="paymentstate.fieldName" value="YPAYMENT.PAYMENTSTATE"> 
			<input type="hidden" id="paymentstate.dataType" name="paymentstate.dataType" value="S">  
			<input type="hidden" id="paymentstate.option" name="paymentstate.option" value="like">
			<fisc:dictionary hiddenName="paymentstate.fieldValue" dictionaryName="YDPAYMENTSTATE" divId="div_paymentstate_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
		<td width="15%" align="right">${vt.property.dept_id}：</td>
		<td  width="40%" >
			<div id="div_dept_id_sh"></div>
			<input type="hidden" id="dept_id.fieldName" name="dept_id.fieldName" value="YPAYMENT.DEPT_ID"> 
			<input type="hidden" id="dept_id.dataType" name="dept_id.dataType" value="S">  
			<input type="hidden" id="dept_id.option" name="dept_id.option" value="like">
			<fisc:searchHelp divId="div_dept_id_sh" boName="HomePayment" boProperty="dept_id" searchType="field" hiddenName="dept_id.fieldValue" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME"></fisc:searchHelp>
		</td>
</tr>
<tr>
		<td width="15%" align="right">${vt.property.pay_type}：</td>
		<td  width="30%" >
			<div id="div_pay_type_dict"></div>
			<input type="hidden" id="pay_type.fieldName" name="pay_type.fieldName" value="YPAYMENT.PAY_TYPE"> 
			<input type="hidden" id="pay_type.dataType" name="pay_type.dataType" value="S">  
			<input type="hidden" id="pay_type.option" name="pay_type.option" value="like">
			<fisc:dictionary hiddenName="pay_type.fieldValue" dictionaryName="YDPAYTYPE" divId="div_pay_type_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
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
//----------------手工修改------------------
var strPaymentType = '${paymenttype}';
var strTradeType = '${tradetype}';
//----------------手工修改------------------
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	homePayment:'${vt.boText}',
	homePayment_Create:'${vt.boTextCreate}',
	// 复制创建
	homePayment_CopyCreate:'${vt.boTextCopyCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 请选择需要进行【内贸付款复制创建】操作的记录！
	homePayment_CopyCreate_SelectToOperation:'${vt.copyCreate_SelectToOperate}',
	// 进行【内贸付款复制创建】操作时，只允许选择一条记录！
	homePayment_CopyCreate_AllowOnlyOneItemForOperation:'${vt.copyCreate_AllowOnlyOneItemForOperation}',
	// 您选择了【内贸付款批量删除】操作，是否确定继续该操作？
	homePayment_Deletes_ConfirmOperation:'${vt.deletes_ConfirmOperation}',
	// 请选择需要进行【内贸付款批量删除】操作的记录！
	homePayment_Deletes_SelectToOperation:'${vt.deletes_SelectToOperate}',
	// 您选择了【内贸付款删除】操作，是否确定继续该操作？
	homePayment_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
