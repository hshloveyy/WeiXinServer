<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
</head>
<body>
<!------------------手工修改------------------>
<fisc:grid 
	   divId="div_southForm" 
	   pageSize="10"
	   editable="false"
	   tableName="ypayment " 
	   handlerClass="com.infolion.xdss3.payment.domain.ImportPaymentGrid"
	   needCheckBox="true"
	   needAutoLoad="false" height="240">
</fisc:grid>
<!----------------手工修改------------------>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<input type="hidden" name="trade_type.fieldValue" value="02"/>
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr style= "Display:none">
		<td width="10%" ></td>
		<td width="23%" colspan="3"></td>
		
		<td width="10%" ></td>
		<td width="23%" colspan="3"></td>
		
		<td width="10%" ></td>
		<td width="23%" colspan="3"></td>
	</tr>
	<tr>
		<td width="10%" align="right">${vt.property.paymentno}：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="paymentno.fieldValue" name="paymentno.fieldValue" value="" >
		</td>
		<td width="10%" align="right">${vt.property.supplier}：</td>
		<td  width="23%" colspan="3">
			<div id="div_supplier_sh"></div>
			<fisc:searchHelp divId="div_supplier_sh" boName="ImportPayment" boProperty="supplier" searchType="field" hiddenName="supplier.fieldValue" valueField="LIFNR" displayField="NAME1"></fisc:searchHelp>
		</td>
		<td width="10%" align="right">${vt.property.paymenttype}：</td>
		<td  width="23%" colspan="3">
			<div id="div_paymenttype_dict"></div>
			<fisc:dictionary hiddenName="paymenttype.fieldValue" dictionaryName="YDPAYTRADETYPE" divId="div_paymenttype_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="10%" align="right">${vt.property.pay_type}：</td>
		<td  width="23%" colspan="3">
			<div id="div_pay_type_dict"></div>
			<fisc:dictionary hiddenName="pay_type.fieldValue" dictionaryName="YDPAYTYPE" divId="div_pay_type_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
		<td width="10%" align="right">${vt.property.creator}：</td>
		<td  width="23%" colspan="3">
			<div id="div_creator_sh"></div>
			<fisc:searchHelp divId="div_creator_sh" searchType="field" searchHelpName="YHUSER" hiddenName="creator.fieldValue" displayField="USERNAME" valueField="USERID" boName="" boProperty="" value=""></fisc:searchHelp>
		</td>
		<td width="10%" align="right">${vt.property.dept_id}：</td>
		<td  width="23%" colspan="3">
			<div id="div_dept_id_sh"></div>
			<fisc:searchHelp divId="div_dept_id_sh" boName="ImportPayment" boProperty="dept_id" searchType="field" hiddenName="dept_id.fieldValue" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="10%" align="right">立项号：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="project_no.fieldValue" name="project_no.fieldValue" value="" >
		</td>
		<td width="10%" align="right">合同编码：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="contract_no.fieldValue" name="contract_no.fieldValue" value="" >
		</td>
		<td width="10%" align="right">外部纸质合同号：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="old_contract_no.fieldValue" name="old_contract_no.fieldValue" value="" >
		</td>
	</tr>
	<tr>
		<td width="10%" align="right">到单号：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="pickListNo.fieldValue" name="pickListNo.fieldValue" value="" >
		</td>
		<td width="10%" align="right">信用证号：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="lc_no.fieldValue" name="lc_no.fieldValue" value="" >
		</td>
		<td width="10%" align="right">开证行：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="issuing_bank.fieldValue" name="issuing_bank.fieldValue" value="" >
		</td>
	</tr>
	<tr>
		<td width="10%" align="right">DP/DA号：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="dp_da_no.fieldValue" name="dp_da_no.fieldValue" value="" >
		</td>
		<td width="10%" align="right">代收行：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="collection_bank.fieldValue" name="collection_bank.fieldValue" value="" >
		</td>
		<td width="10%" align="right">凭证号：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="voucherno.fieldValue" name="voucherno.fieldValue" value="" >
		</td>
	</tr>
	<tr>
		<td width="10%" align="right">付款金额：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="applyamount.fieldValue" name="applyamount.fieldValue" value="" >
		</td>
		<td width="10%" align="right">币别：</td>
		<td  width="23%" colspan="3">
			<div id="div_currency_sh"></div>
			<fisc:searchHelp divId="div_currency_sh" boName="ImportPayment" boProperty="currency" searchType="field" hiddenName="currency.fieldValue" valueField="WEARS" displayField="KTEXT"></fisc:searchHelp>
		</td>
		<td width="10%" align="right">${vt.property.text}：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="text.fieldValue" name="text.fieldValue" value="" >
		</td>
	</tr>
	<tr>
		<td width="10%" align="right">到单日：</td>
		<td >
			<input type="text" id="pick_list_rec_date.fieldValue" name="pick_list_rec_date.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="pick_list_rec_date.fieldValue"  divId="" fieldName=""  width="100"  ></fisc:calendar>
		</td>
		<td align="center">&nbsp;至&nbsp;</td>
		<td>
			<input type="text" id="pick_list_rec_dateEnd.fieldValue" name="pick_list_rec_dateEnd.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="pick_list_rec_dateEnd.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td width="10%" align="right">开证日期：</td>
		<td >
			<input type="text" id="issuing_date.fieldValue" name="issuing_date.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="issuing_date.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td align="center">&nbsp;至&nbsp;</td>
		<td>
			<input type="text" id="issuing_dateEnd.fieldValue" name="issuing_dateEnd.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="issuing_dateEnd.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td width="10%" align="right">申请时间：</td>
		<td >
			<input type="text" id="createtime.fieldValue" name="createtime.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="createtime.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td align="center">&nbsp;至&nbsp;</td>
		<td>
			<input type="text" id="createtimeEnd.fieldValue" name="createtimeEnd.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="createtimeEnd.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="10%" align="right">审批通过时间：</td>
		<td >
			<input type="text" id="lastmodifytime.fieldValue" name="lastmodifytime.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="lastmodifytime.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td align="center">&nbsp;至&nbsp;</td>
		<td>
			<input type="text" id="lastmodifytimeEnd.fieldValue" name="lastmodifytimeEnd.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="lastmodifytimeEnd.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td width="10%" align="right">银行对外付款日：</td>
		<td >
			<input type="text" id="paydate.fieldValue" name="paydate.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="paydate.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td align="center">&nbsp;至&nbsp;</td>
		<td>
			<input type="text" id="paydateEnd.fieldValue" name="paydateEnd.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="paydateEnd.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td width="10%" align="right">物料组名称：</td>
		<td  width="23%" colspan="3">
		    <div id="div_ymatGroup"></div>
		    <fisc:dictionary width="100" hiddenName = "ymatGroup.fieldValue" isTextValue="true" dictionaryName="YDMATGROUP" divId="div_ymatGroup" isNeedAuth="false"   ></fisc:dictionary>
        </td>
	</tr>
	<tr>
		<td width="10%" align="right">押汇到期付款日：</td>
		<td >
			<input type="text" id="documentarypaydt.fieldValue" name="documentarypaydt.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="documentarypaydt.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td align="center">&nbsp;至&nbsp;</td>
		<td>
			<input type="text" id="documentarypaydtEnd.fieldValue" name="documentarypaydtEnd.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="documentarypaydtEnd.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td width="10%" align="right">预计到货日：</td>
		<td >
			<input type="text" id="arrivegoodsdate.fieldValue" name="arrivegoodsdate.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="arrivegoodsdate.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td align="center">&nbsp;至&nbsp;</td>
		<td>
			<input type="text" id="arrivegoodsdateEnd.fieldValue" name="arrivegoodsdateEnd.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="arrivegoodsdateEnd.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td width="10%" align="right">是否海外代付：</td>
		<td  width="23%" colspan="3">
			<div id="div_isoverrepay_dict"></div>
			<fisc:dictionary hiddenName="isoverrepay.fieldValue" dictionaryName="YDPAYMENTYESORNO" divId="div_isoverrepay_dict" isNeedAuth="false"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="10%" align="right">预付天数：</td>
		<td  width="23%" colspan="3">
			<input type="text" id="yufuTS.fieldValue" name="yufuTS.fieldValue" value="" size="10">
			-<input type="text" id="yufuTS1.fieldValue" name="yufuTS1.fieldValue" value="" size="10">
		</td>
		<td width="10%" align="right">延付天数：</td>
		<td  width="23%" colspan="3">
			<input type="text" id="yanfuTS.fieldValue" name="yanfuTS.fieldValue" value="" size="10">
			-<input type="text" id="yanfuTS1.fieldValue" name="yanfuTS1.fieldValue" value="" size="10">
		</td>
		<td width="" align="right">贸易类型：</td>
				<td width="" colspan="3">
					<div id="div_tradeType_dict"></div>
					<fisc:dictionary hiddenName="tradeType" dictionaryName="YDTRADETYPE" divId="div_tradeType_dict" isNeedAuth="false"></fisc:dictionary>
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
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/payment/importPaymentCompositeQuery.js"></script>
<script type="text/javascript" defer="defer">
//----------------手工修改------------------
var strPaymentType = '${paymenttype}';
var strTradeType = '${tradetype}';
//----------------手工修改------------------
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';


/* 操作列链接 */
function operaRD(value,metadata,record){
	return '<a href="#" style="color: green;" onclick="_commonMethodOperation(\'1\',\'进口付款查看\',div_southForm_grid,\'xdss3/payment/importPaymentController.spr?action=_view&paymentid='+value+'\',\'_viewHomePaymentpCallBack\',\'_view\',\'false\')">查看</a> '
	         +'<a href="#" style="color: green;" onclick="_viewProcessState(\''+value+'\');">流程跟踪</a>';
}

/**
 * EXT 布局
 */
Ext.onReady(function(){
	
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'border',
			border:false,
			items:[{
					region:"north",
					contentEl:'div_toolbar',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					autoScroll: true,
					contentEl:'div_center'
				},		
				{
					region:"south",
					border:false,
					autoScroll: true,
		            title:'${vt.boTextDetail}',
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'${vt.mQuery}',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'${vt.sClean}',handler:_resetForm,iconCls:'icon-clean'},'-',
				{id:'_export',text:'导出',handler:_export,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});
	
});
</script>
