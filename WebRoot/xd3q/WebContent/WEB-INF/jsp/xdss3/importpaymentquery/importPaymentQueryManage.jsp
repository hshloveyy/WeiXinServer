<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年10月01日 06点47分52秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象进口付款查询(ImportPaymentQuery)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/importpaymentquery/importPaymentQueryManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/importpaymentquery/importPaymentQueryManageGen.js"></script>
</head>
<body>
<fisc:grid 
	divId="div_southForm" 
	boName="ImportPaymentQuery" 
	editable="false" 
	needCheckBox="true" 
	needToolbar="true" 
	needAutoLoad="true"  
	needAuthentication="true" 
	defaultCondition="TRADE_TYPE='02'"></fisc:grid>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
		<td width="15%" align="right">${vt.property.paymentno}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="paymentno.fieldValue" name="paymentno.fieldValue" value="" <fisc:authentication sourceName="ImportPaymentQuery.paymentno" taskId=""/>>
			<input type="hidden" id="paymentno.fieldName" name="paymentno.fieldName" value="YPAYMENT.PAYMENTNO"> 
			<input type="hidden" id="paymentno.dataType" name="paymentno.dataType" value="S">  
			<input type="hidden" id="paymentno.option" name="paymentno.option" value="like">
		</td>
		<td width="15%" align="right">付款方式：</td>
		<td  width="40%" >
			<div id="div_paymenttype_dict"></div>
			<input type="hidden" id="paymenttype.fieldName" name="paymenttype.fieldName" value="YPAYMENT.PAYMENTTYPE"> 
			<input type="hidden" id="paymenttype.dataType" name="paymenttype.dataType" value="S">  
			<input type="hidden" id="paymenttype.option" name="paymenttype.option" value="like">
			<fisc:dictionary hiddenName="paymenttype.fieldValue" dictionaryName="YDIMPORTPAYMENTTYPE" divId="div_paymenttype_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
</tr>
<tr>
		<td width="15%" align="right">${vt.property.businessstate}：</td>
		<td  width="30%" >
			<div id="div_businessstate_dict"></div>
			<input type="hidden" id="businessstate.fieldName" name="businessstate.fieldName" value="YPAYMENT.BUSINESSSTATE"> 
			<input type="hidden" id="businessstate.dataType" name="businessstate.dataType" value="S">  
			<input type="hidden" id="businessstate.option" name="businessstate.option" value="equal">
			<fisc:dictionary hiddenName="businessstate.fieldValue" dictionaryName="YDPAYMENTBUZSTATE" divId="div_businessstate_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
		<td width="15%" align="right">${vt.property.supplier}：</td>
		<td  width="40%" >
			<div id="div_supplier_sh"></div>
			<input type="hidden" id="supplier.fieldName" name="supplier.fieldName" value="YPAYMENT.SUPPLIER"> 
			<input type="hidden" id="supplier.dataType" name="supplier.dataType" value="S">  
			<input type="hidden" id="supplier.option" name="supplier.option" value="like">
			<fisc:searchHelp divId="div_supplier_sh" boName="ImportPaymentQuery" boProperty="supplier" searchType="field" hiddenName="supplier.fieldValue" valueField="LIFNR" displayField="NAME1"></fisc:searchHelp>
		</td>
</tr>
<tr>
		<td width="15%" align="right">${vt.property.payrealdate}：</td>
		<td  width="30%" >
			<input type="text" id="payrealdate.fieldValue" name="payrealdate.fieldValue" value="">
			<input type="hidden" id="payrealdate.fieldName" name="payrealdate.fieldName" value="YPAYMENT.PAYREALDATE"> 
			<input type="hidden" id="payrealdate.dataType" name="payrealdate.dataType" value="D">  
			<input type="hidden" id="payrealdate.option" name="payrealdate.option" value="like">
				<fisc:calendar applyTo="payrealdate.fieldValue"  divId="" fieldName="" ></fisc:calendar>
		</td>
		<td width="15%" align="right">${vt.property.pay_type}：</td>
		<td  width="40%" >
			<div id="div_pay_type_dict"></div>
			<input type="hidden" id="pay_type.fieldName" name="pay_type.fieldName" value="YPAYMENT.PAY_TYPE"> 
			<input type="hidden" id="pay_type.dataType" name="pay_type.dataType" value="S">  
			<input type="hidden" id="pay_type.option" name="pay_type.option" value="like">
			<fisc:dictionary hiddenName="pay_type.fieldValue" dictionaryName="YDPAYTYPE" divId="div_pay_type_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
</tr>
<tr>
		<td width="15%" align="right">${vt.property.dept_id}：</td>
		<td  width="30%" >
			<div id="div_dept_id_sh"></div>
			<input type="hidden" id="dept_id.fieldName" name="dept_id.fieldName" value="YPAYMENT.DEPT_ID"> 
			<input type="hidden" id="dept_id.dataType" name="dept_id.dataType" value="S">  
			<input type="hidden" id="dept_id.option" name="dept_id.option" value="like">
			<fisc:searchHelp divId="div_dept_id_sh" boName="ImportPaymentQuery" boProperty="dept_id" searchType="field" hiddenName="dept_id.fieldValue" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME"></fisc:searchHelp>
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
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	importPaymentQuery:'${vt.boText}',
	// 您选择了【进口付款查询删除】操作，是否确定继续该操作？
	importPaymentQuery_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
