<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月31日 15点00分58秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:   chartPageTemplate
  - <功能>采购订单(PurchaseOrder)图表页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.chartPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/orderManage/purchaseOrder/purchaseOrderManage.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_south" class="chart_select"> 
<form id="mainForm" name="mainForm">
<table width="210" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right">${vt.property.purchaseOrderNo}：</td>
		<td>
		<!-- UITYPE:01 -->
			<input type="text" id="purchaseOrderNo.fieldValue" name="purchaseOrderNo.fieldValue" value="">
			<input type="hidden" id="purchaseOrderNo.fieldName" name="purchaseOrderNo.fieldName" value="YPURCHASEORDER.PURCHASEORDERNO"> 
			<input type="hidden" id="purchaseOrderNo.dataType" name="purchaseOrderNo.dataType" value="S">  
			<input type="hidden" id="purchaseOrderNo.option" name="purchaseOrderNo.option" value="like">
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.supplierNo}：</td>
		<td>
		<!-- UITYPE:01 -->
			<input type="text" id="supplierNo.fieldValue" name="supplierNo.fieldValue" value="">
			<input type="hidden" id="supplierNo.fieldName" name="supplierNo.fieldName" value="YPURCHASEORDER.SUPPLIERNO"> 
			<input type="hidden" id="supplierNo.dataType" name="supplierNo.dataType" value="S">  
			<input type="hidden" id="supplierNo.option" name="supplierNo.option" value="like">
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.certificateType}：</td>
		<td>
		<!-- UITYPE:01 -->
			<input type="text" id="certificateType.fieldValue" name="certificateType.fieldValue" value="">
			<input type="hidden" id="certificateType.fieldName" name="certificateType.fieldName" value="YPURCHASEORDER.CERTIFICATETYPE"> 
			<input type="hidden" id="certificateType.dataType" name="certificateType.dataType" value="S">  
			<input type="hidden" id="certificateType.option" name="certificateType.option" value="like">
		</td>
	</tr>

	<tr>
		<td align="right">${vt.property.certificateDate} ${vt.sFrom}：</td>
		<td>
			<input type="hidden" id="certificateDate.fieldName" name="certificateDate.fieldName" value="YPURCHASEORDER.CERTIFICATEDATE"> 
			<input type="hidden" id="certificateDate.isRangeValue" name="certificateDate.isRangeValue" value="true">
			<!-- UITYPE:04 -->
    		<input type="text" id="certificateDate_minValue" name="certificateDate.minValue">
    		<input type="hidden" id="certificateDate.dataType" name="certificateDate.dataType" value="D"> 
			<fisc:calendar applyTo="certificateDate_minValue" format="Ymd" divId="" fieldName=""></fisc:calendar>
		</td>
		</tr>
		<tr>
		<td align="right">${vt.sTo}：</td>
		<td>
			<input type="text" id="certificateDate_maxValue" name="certificateDate.maxValue">			
			<fisc:calendar applyTo="certificateDate_maxValue" format="Ymd" divId="" fieldName=""></fisc:calendar>
		</td>
	</tr>

</table>
</form>
</div>

<div id="div_chart_container" class="chart">
	<div id="PurchaseOrderChart_div"></div>
</div>

<div id="PurchaseOrderChartDetail_div" style="width: 100%"></div>
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
						id:'PurchaseOrderChart_div_ext'
					},{
						title:'${vt.chartDetail}',
						contentEl:"PurchaseOrderChartDetail_div"
					}]
				}]
			}]
		});
		Ext.getDom('PurchaseOrderChart_div_ext').align='center';
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
        var purchaseOrderNo = $("purchaseOrderNo.fieldValue").value + " 订单号" ;
        chartTitle = chartTitle + purchaseOrderNo;
        var supplierNo = $("supplierNo.fieldValue").value + " 供应商或债权人的帐号" ;
        chartTitle = chartTitle + supplierNo;
        var certificateType = $("certificateType.fieldValue").value + " 订单类型（采购）" ;
        chartTitle = chartTitle + certificateType;
        var certificateDate = $("certificateDate.fieldValue").value + " 凭证日期" ;
        if(!chartTitle){
       	 chartTitle = chartTitle + certificateDate;
        }
		//在配置中描述在标题（加在图表标题在后面）
		var configTitle = "TEST";
		chartTitle = chartTitle + configTitle;
		//divid + "_reload_chart"为函数名
		PurchaseOrderChart_div_reload_chart({queryParameters:queryParam,title:chartTitle});
	}
	//清空操作
	function _resetForm()
	{
		document.all.mainForm.reset();
	}
</script>
<fisc:chart divId="PurchaseOrderChart_div" divDetailId="PurchaseOrderChartDetail_div" width="400" height="300" boName="PurchaseOrder" defaultCondition=""></fisc:chart>