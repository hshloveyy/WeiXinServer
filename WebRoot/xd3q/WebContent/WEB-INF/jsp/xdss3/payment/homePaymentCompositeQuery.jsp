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
	   handlerClass="com.infolion.xdss3.payment.domain.HomePaymentGrid"
	   needCheckBox="true"
	   needAutoLoad="false" height="240">
</fisc:grid>
<!----------------手工修改------------------>
<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<input type="hidden" name="trade_type.fieldValue" value="01"/>
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
			<fisc:searchHelp divId="div_supplier_sh" boName="HomePayment" boProperty="supplier" searchType="field" hiddenName="supplier.fieldValue" valueField="LIFNR" displayField="NAME1"></fisc:searchHelp>
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
			<fisc:searchHelp divId="div_dept_id_sh" boName="HomePayment" boProperty="dept_id" searchType="field" hiddenName="dept_id.fieldValue" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME"></fisc:searchHelp>
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
		<td width="10%" align="right">最后更改时间：</td>
		<td>
			<input type="text" id="lastmodifytime.fieldValue" name="lastmodifytime.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="lastmodifytime.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td align="center">&nbsp;至&nbsp;</td>
		<td>
			<input type="text" id="lastmodifytimeEnd.fieldValue" name="lastmodifytimeEnd.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="lastmodifytimeEnd.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
<%--		<td width="10%" align="right">实际付款日：</td>--%>
<%--		<td>--%>
<%--			<input type="text" id="paydate.fieldValue" name="paydate.fieldValue" value="" width="100">--%>
<%--				<fisc:calendar readonly="false" applyTo="paydate.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>--%>
<%--		</td>--%>
<%--		<td align="center">&nbsp;至&nbsp;</td>--%>
<%--		<td>--%>
<%--			<input type="text" id="paydateEnd.fieldValue" name="paydateEnd.fieldValue" value="" width="100">--%>
<%--				<fisc:calendar readonly="false" applyTo="paydateEnd.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>--%>
<%--		</td>--%>
		<td width="10%" align="right">创建时间：</td>
		<td>
			<input type="text" id="createtime.fieldValue" name="createtime.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="createtime.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td align="center">&nbsp;至&nbsp;</td>
		<td>
			<input type="text" id="createtimeEnd.fieldValue" name="createtimeEnd.fieldValue" value="" width="100">
				<fisc:calendar readonly="false" applyTo="createtimeEnd.fieldValue"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td width="10%" align="right">${vt.property.text}：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="text.fieldValue" name="text.fieldValue" value="" >
		</td>
	</tr>
	<tr>
		<td width="10%" align="right">付款金额：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="applyamount.fieldValue" name="applyamount.fieldValue" value="" >
		</td>
		<td width="10%" align="right">凭证号：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="voucherno.fieldValue" name="voucherno.fieldValue" value="" >
		</td>
		<td width="10%" align="right">物料组名称：</td>
		<td  width="23%" colspan="3">
		    <div id="div_ymatGroup"></div>
		    <fisc:dictionary width="100" hiddenName = "ymatGroup.fieldValue" isTextValue="true" dictionaryName="YDMATGROUP" divId="div_ymatGroup" isNeedAuth="false"   ></fisc:dictionary>
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
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/payment/homePaymentCompositeQuery.js"></script>
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
	var paymenttype = record.get('paymenttype');
	
	if(paymenttype == '15' || paymenttype == '16' ){
		return '<a href="#" style="color: green;" onclick="_commonMethodOperation(\'1\',\'内贸付款查看\',div_southForm_grid,\'xdss3/payment/importPaymentController.spr?action=_view&paymentid='+value+'\',\'_viewHomePaymentpCallBack\',\'_view\',\'false\')">查看</a> '
		         +'<a href="#" style="color: green;" onclick="_viewProcessState(\''+value+'\');">流程跟踪</a>';
		         
	}else{
		return '<a href="#" style="color: green;" onclick="_commonMethodOperation(\'1\',\'内贸付款查看\',div_southForm_grid,\'xdss3/payment/homePaymentController.spr?action=_view&paymentid='+value+'\',\'_viewHomePaymentpCallBack\',\'_view\',\'false\')">查看</a> '
		         +'<a href="#" style="color: green;" onclick="_viewProcessState(\''+value+'\');">流程跟踪</a>';
	         
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
				{id:'_export',text:'导出',handler:_export,iconCls:'icon-clean'}
				],
		renderTo:"div_toolbar"
	});
	
});
</script>
