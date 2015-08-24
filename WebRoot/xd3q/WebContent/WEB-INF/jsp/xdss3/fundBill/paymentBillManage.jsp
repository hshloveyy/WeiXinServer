<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>付款清票状态查询</title>
</head>
<body>
	<fisc:grid 
		   divId="FundBill" 
		   pageSize="10" 
		   tableName="VPAYMENTBILL " 
		   handlerClass="com.infolion.xdss3.fundBill.paymentBill.PaymentBillGrid"
 		   whereSql="" 
 		   needCheckBox="false"
 		   needAutoLoad="true" >
	</fisc:grid>
	<div id="toolBar" style="overflow: hidden ;width: 100%"></div>
	<form id="mainForm" name="mainForm">
		<table width="100%" border="0" cellpadding="4" cellspacing="1">
			<tr>
				<td>&nbsp;</td><td>&nbsp;</td>
			</tr>
			<tr>
				<td width="15%" align="right">发票号：</td>
				<td width="40%" >
				<input type="text" class="inputText" id="billno.fieldValue" name="billno.fieldValue" value="">
					<input type="hidden" id="billno.fieldName" name="billno.fieldName" value="BILLNO"> 
					<input type="hidden" id="billno.dataType" name="billno.dataType" value="S">  
					<input type="hidden" id="billno.option" name="billno.option" value="like">
				</td>
				<td width="15%" align="right">付款单号：</td>
				<td  width="30%" >
					<input type="text" class="inputText" id="paymentno.fieldValue" name="paymentno.fieldValue" value="">
					<input type="hidden" id="paymentno.fieldName" name="paymentno.fieldName" value="PAYMENTNO"> 
					<input type="hidden" id="paymentno.dataType" name="paymentno.dataType" value="S">  
					<input type="hidden" id="paymentno.option" name="paymentno.option" value="like">
				</td>
			</tr>
			<tr>
				<td width="15%" align="right">发票凭证号：</td>
				<td width="40%" >
				<input type="text" class="inputText" id="belnr.fieldValue" name="belnr.fieldValue" value="">
					<input type="hidden" id="belnr.fieldName" name="belnr.fieldName" value="BELNR"> 
					<input type="hidden" id="belnr.dataType" name="belnr.dataType" value="S">  
					<input type="hidden" id="belnr.option" name="belnr.option" value="like">
				</td>
				<td width="15%" align="right">供应商代码：</td>
				<td  width="30%" >
					<input type="text" class="inputText" id="lifnr.fieldValue" name="lifnr.fieldValue" value="">
					<input type="hidden" id="lifnr.fieldName" name="lifnr.fieldName" value="LIFNR"> 
					<input type="hidden" id="lifnr.dataType" name="lifnr.dataType" value="S">  
					<input type="hidden" id="lifnr.option" name="lifnr.option" value="like">
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td><td>&nbsp;</td>
			</tr>
		</table>
	</form>
  	<div id="FundBill"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/fundBill/paymentBillManage.js"></script>
<script type="text/javascript" defer="defer">
var context = '<%= request.getContextPath() %>';
Ext.onReady(function(){
 	// 工具栏
	var toolbars = new Ext.Toolbar({
		items:['-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_clear' ,text:'清空',handler:_clear ,iconCls:'icon-clean'} ,'-'],
		renderTo:"toolBar"
	});   
	
	// 查询主体框
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[{
			region:"center",
			items:[{
					region:"north",
					contentEl:'toolBar',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					contentEl:'mainForm',
					bodyStyle:"background-color:#DFE8F6"
				},{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:'付款清票状态查询',
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'FundBill'
				}]
			}]
    });
    viewport.doLayout();
});


function _clearamountRender(value,metadata,record){
	var billclearamount = record.get("billclearamount");
	var clearamount = record.get("clearamount");
	var amount = parseFloat(billclearamount) - parseFloat(clearamount);
	if(amount<=0){
		return billclearamount;
	}else{
		return clearamount;
	}
}
</script> 
