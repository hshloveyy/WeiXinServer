<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年07月25日 09点52分19秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:   chartPageTemplate
  - <功能>进口付款(ImportPayment)图表页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.chartPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/payment/importPaymentManage.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_south" class="chart_select"> 
<form id="mainForm" name="mainForm">
<table width="210" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right">${vt.property.paymentno}：</td>
		<td>
		<!-- UITYPE:01 -->
			<input type="text" id="paymentno.fieldValue" name="paymentno.fieldValue" value="">
			<input type="hidden" id="paymentno.fieldName" name="paymentno.fieldName" value="YPAYMENT.PAYMENTNO"> 
			<input type="hidden" id="paymentno.dataType" name="paymentno.dataType" value="S">  
			<input type="hidden" id="paymentno.option" name="paymentno.option" value="like">
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.supplier}：</td>
		<td>
		<!-- UITYPE:11 -->
			<div id="div_supplier_sh"></div>
			<input type="hidden" id="supplier.fieldName" name="supplier.fieldName" value="YPAYMENT.SUPPLIER"> 
			<input type="hidden" id="supplier.dataType" name="supplier.dataType" value="S">  
			<input type="hidden" id="supplier.option" name="supplier.option" value="like">
			<fisc:searchHelp divId="div_supplier_sh" boName="ImportPayment" boProperty="supplier" searchType="field" hiddenName="supplier.fieldValue" valueField="LIFNR" displayField="NAME1"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.paymentstate}：</td>
		<td>
		<!-- UITYPE:06 -->
			<div id="div_paymentstate_dict"></div>
			<input type="hidden" id="paymentstate.fieldName" name="paymentstate.fieldName" value="YPAYMENT.PAYMENTSTATE"> 
			<input type="hidden" id="paymentstate.dataType" name="paymentstate.dataType" value="S">  
			<input type="hidden" id="paymentstate.option" name="paymentstate.option" value="like">
			<fisc:dictionary hiddenName="paymentstate.fieldValue" dictionaryName="YDPAYMENTSTATE" divId="div_paymentstate_dict" isNeedAuth="false" ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.applyamount}：</td>
		<td>
		<!-- UITYPE:01 -->
			<input type="text" id="applyamount.fieldValue" name="applyamount.fieldValue" value="">
			<input type="hidden" id="applyamount.fieldName" name="applyamount.fieldName" value="YPAYMENT.APPLYAMOUNT"> 
			<input type="hidden" id="applyamount.dataType" name="applyamount.dataType" value="N">  
			<input type="hidden" id="applyamount.option" name="applyamount.option" value="like">
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.currency}：</td>
		<td>
		<!-- UITYPE:11 -->
			<div id="div_currency_sh"></div>
			<input type="hidden" id="currency.fieldName" name="currency.fieldName" value="YPAYMENT.CURRENCY"> 
			<input type="hidden" id="currency.dataType" name="currency.dataType" value="S">  
			<input type="hidden" id="currency.option" name="currency.option" value="like">
			<fisc:searchHelp divId="div_currency_sh" boName="ImportPayment" boProperty="currency" searchType="field" hiddenName="currency.fieldValue" valueField="WEARS" displayField="KTEXT"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.dept_id}：</td>
		<td>
		<!-- UITYPE:11 -->
			<div id="div_dept_id_sh"></div>
			<input type="hidden" id="dept_id.fieldName" name="dept_id.fieldName" value="YPAYMENT.DEPT_ID"> 
			<input type="hidden" id="dept_id.dataType" name="dept_id.dataType" value="S">  
			<input type="hidden" id="dept_id.option" name="dept_id.option" value="like">
			<fisc:searchHelp divId="div_dept_id_sh" boName="ImportPayment" boProperty="dept_id" searchType="field" hiddenName="dept_id.fieldValue" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.pay_type}：</td>
		<td>
		<!-- UITYPE:06 -->
			<div id="div_pay_type_dict"></div>
			<input type="hidden" id="pay_type.fieldName" name="pay_type.fieldName" value="YPAYMENT.PAY_TYPE"> 
			<input type="hidden" id="pay_type.dataType" name="pay_type.dataType" value="S">  
			<input type="hidden" id="pay_type.option" name="pay_type.option" value="like">
			<fisc:dictionary hiddenName="pay_type.fieldValue" dictionaryName="YDPAYTYPE" divId="div_pay_type_dict" isNeedAuth="false" ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.trade_type}：</td>
		<td>
		<!-- UITYPE:13 -->
			<input type="text" id="trade_type.fieldValue" name="trade_type.fieldValue" value="">
			<input type="hidden" id="trade_type.fieldName" name="trade_type.fieldName" value="YPAYMENT.TRADE_TYPE"> 
			<input type="hidden" id="trade_type.dataType" name="trade_type.dataType" value="S">  
			<input type="hidden" id="trade_type.option" name="trade_type.option" value="like">
		</td>
	</tr>


</table>
</form>
</div>

<div id="div_chart_container" class="chart">
	<div id="ImportPaymentChart_div"></div>
</div>

<div id="ImportPaymentChartDetail_div" style="width: 100%"></div>
</body>
</html>
<script type="text/javascript" defer="defer">

/**
 * EXT 布局
 */
Ext.onReady(function(){
		var viewport = new Ext.Viewport({
			layout:"fit",
			autoScroll:true,
			items:[{
				layout:"column",
				autoScroll:true,
				split:true,
				items:[{
					width:236,					
					split:true,
					title:'${vt.sCondSelect}',
					items:[{
						height:28,
						region:"north",
						contentEl:"div_toolbar"
					},{
						region:"south",
						contentEl:"div_south"
					}]
				},{
					columnWidth:1,
					split:true,
					items:[{
						contentEl:"div_chart_container",
						id:'ImportPaymentChart_div_ext'
					},{
						title:'${vt.chartDetail}',
						contentEl:"ImportPaymentChartDetail_div"
					}]
				}]
			}]
		});
		Ext.getDom('ImportPaymentChart_div_ext').align='center';
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'${vt.mQuery}',handler:_queryChart,iconCls:'icon-cls'},'-',
				{id:'_cls',text:'${vt.sClean}',handler:_resetForm,iconCls:'icon-cls'},'-'
				],
		renderTo:"div_toolbar"
	});
	
	/**
	*图表查询
	**/
	function _queryChart()
	{
	    //图表标题
	    var chartTitle ="" ;
		var queryParam = Form.serialize('mainForm');
		//根据查询条件在属性和属性描述构成
        var paymentno = $("paymentno.fieldValue").value + " 付款单号" ;
        chartTitle = chartTitle + paymentno;
        var supplier = $("supplier.fieldValue").value + " 供应商" ;
        chartTitle = chartTitle + supplier;
        var paymentstate = $("paymentstate.fieldValue").value + " 付款单状态" ;
        chartTitle = chartTitle + paymentstate;
        var applyamount = $("applyamount.fieldValue").value + " 申请付款金额" ;
        chartTitle = chartTitle + applyamount;
        var currency = $("currency.fieldValue").value + " 币别" ;
        chartTitle = chartTitle + currency;
        var dept_id = $("dept_id.fieldValue").value + " 部门ID" ;
        chartTitle = chartTitle + dept_id;
        var pay_type = $("pay_type.fieldValue").value + " 付款类型" ;
        chartTitle = chartTitle + pay_type;
        var trade_type = $("trade_type.fieldValue").value + " 贸易方式" ;
        chartTitle = chartTitle + trade_type;
		//在配置中描述在标题（加在图表标题在后面）
		var configTitle = " ";
		chartTitle = chartTitle + configTitle;
		//divid + "_reload_chart"为函数名
		ImportPaymentChart_div_reload_chart({queryParameters:queryParam,title:chartTitle});
	}
	//清空操作
	function _resetForm()
	{
		document.all.mainForm.reset();
	}
</script>
<fisc:chart divId="ImportPaymentChart_div" divDetailId="ImportPaymentChartDetail_div" width="400" height="300" boName="ImportPayment" defaultCondition=""></fisc:chart>