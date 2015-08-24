<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.x-grid-row-bgcolor{  
    background-color:#ffffcc;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内贸综合报表</title>
</head>
<body >
<div id="queryDiv">
<form name="queryForm">
	<table width="800">
	<tr>
		<td width="100" align="right">项目编号:</td>	
		<td><input type="text" name="projectNo"/></td>	
		<td>a</td>	
		<td><input type="text" name=""/></td>	
		<td>a</td>	
		<td><input type="text" name=""/></td>	
		<td>a</td>	
		<td><input type="text" name=""/></td>
		<td><input type="button" name="" value="查找" onclick="find()"/></td>
		<td><input type="button" name="" value="清空"/></td>
		<td></td>
	</tr>
	</table>
</form>
</div>
<div id="gridDiv1"></div>	
<div id="gridDiv2"></div>	
</body>
<script type="text/javascript">

</script>
<script type="text/javascript">
var grid;
var store;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	
	var fields=[
	        	{name:'PROJECT_ID'},
	        	{name:'PROJECT_NO'},
	        	{name:'TRADE_TYPE'},
	        	{name:'APPROVED_TIME'},
	        	{name:'CUSTOMER_LINK_MAN'},
	        	{name:'PURCHASE_CONTRACT_NO'},//purchase_contract_no,purchase_sap_no,ekko_lifnr,ekko_lifnr_name,total
	        	{name:'PURCHASE_SAP_NO'},
	        	{name:'SALE_CONTRACT_NO'},
	        	{name:'SALE_SAP_NO'},
	        	{name:'VBKD_INCO1_NAME'},
	        	{name:'VBAK_AUDAT'},
	        	{name:'EKKO_LIFNR'},
	        	{name:'EKKO_LIFNR_NAME'},
	        	{name:'VBAP_MATNR'},
	        	{name:'VBAP_ARKTX'},
	        	{name:'VBAP_ZMENG'},
	        	{name:'VBAP_VRKME'},
	        	{name:'KONV_KBETR'},
	        	{name:'TOTAL'},
	        	{name:'SHIPMENT_DATE'},//purchase_rows_id,ekpo_menge,ekpo_meins,ekpo_bprme,ekpo_netpr,
	        	{name:'SHIPMENT_PORT'},
	        	{name:'DESTINATION_PORT'},
	        	{name:'PURCHASE_ROWS_ID'},
	        	{name:'EKPO_MATNR'},
	        	{name:'EKPO_TXZ01'},//sale_contract_no,sale_sap_no,vbak_audat,kuagv_kunnr,kuagv_kunnr_name,vbkd_inco1_name,
	        	{name:'EKET_EINDT'},//vbkd_inco1_name,order_total,shipment_date,shipment_port,destination_port
	        	{name:'EKPO_MENGE'},
	        	{name:'EKPO_MEINS'},
	        	{name:'EKPO_BPRME'},
	        	{name:'EKPO_NETPR'},//vbap_matnr,vbap_arktx,vbap_kwmeng,vbap_vrkme,konv_kbetr
	        	{name:'EKPO_PEINH'},
	        	{name:'CUSTOM_CREATE_DATE'},
	        	{name:'CREDIT_INFO'},
	        	{name:'BAIL_DATE'},
	        	{name:'CREATE_BANK'},
	        	{name:'CREDIT_NO'},
	        	{name:'LOADING_PERIOD'},
	        	{name:'PERIOD'},
	        	{name:'KUAGV_KUNNR'},
	        	{name:'KUAGV_KUNNR_NAME'},
	        	{name:'ORDER_TOTAL'},
	        	{name:'OLD_CONTRACT_NO'},
	        	{name:'SAP_ORDER_NO'},
	        	{name:'VBAK_WAERK'},
	        	{name:'VBKD_ZLSCH_NAME'},
	        	{name:'EKKO_ZTERM_NAME'},
	        	{name:'PROJECT_NAME'},
	        	{name:'EKPO_PEINH'}
	        	,{name:'PURCHASEROWTOTAL'}
	        	,{name:'EKKO_ZTERM'}
	        	,{name:'VBAP_KPEIN'}
	        	,{name:'SALEROWTOTAL'}
	        	,{name:'VBKD_ZTERM'}
	        	,{name:'SHIP_NO'}
	        	,{name:'SHIP_CREATOR_TIME'}
	        	,{name:'SHIP_QUANTITY'}
	        	,{name:'RECEIPT_NO'}
	        	,{name:'RECEIPT_QUANTITY'}
	        	,{name:'RECEIPT_CREATOR_TIME'}
	        	,{name:'RECEIPT_PRICE'}
	        	,{name:'RECEIPT_DATE'}
	        	,{name:'REAL_RECEIPT_DATE'}
	        	,{name:'RECEIPT_TOTAL'}
	        	,{name:'REAL_RECEIPT_TOTAL'}
	        	,{name:'RECEIPT_BALANCE'}
	        	,{name:'SALE_CONTRACT_STAUS'}
	        	,{name:'PAY_DATE'}
	        	,{name:'REAL_PAY_DATE'}
	        	,{name:'PAY_TOTAL'}
	        	,{name:'REAL_PAY_TOTAL'}
	        	,{name:'PAY_BALANCE'}
	        	,{name:'PURCHASE_CONTRACT_STAUS'}
	        	
	        	];
	var columns=[new Ext.grid.RowNumberer(),
		{header:'立项ID',hidden:true,sortable:true,dataIndex:'PROJECT_ID'},
		{header:'合同类型',sortable:true,dataIndex:'',renderer:contractKind},//销售合同/采购合同
		{header:'立项编号',sortable:true,dataIndex:'PROJECT_NO'},
		{header:'立项名称',sortable:true,dataIndex:'PROJECT_NAME'},
		{header:'业务类型',sortable:true,dataIndex:'TRADE_TYPE',renderer:rendTradeType},
		{header:'立项生效日期',sortable:true,dataIndex:'APPROVED_TIME'},
		{header:'外部纸质合同号',sortable:true,dataIndex:'OLD_CONTRACT_NO'},
		{header:'SAP订单编号',sortable:true,dataIndex:'SAP_ORDER_NO'},
		{header:'销售合同号',sortable:true,dataIndex:'SALE_CONTRACT_NO'},
		{header:'销售合同日期',sortable:true,dataIndex:'VBAK_AUDAT'},
		{header:'客户编码',sortable:true,dataIndex:'KUAGV_KUNNR'},
		{header:'客户名称',sortable:true,dataIndex:'KUAGV_KUNNR_NAME'},
		{header:'物料编号',sortable:true,dataIndex:'VBAP_MATNR'},
		{header:'物料名称',sortable:true,dataIndex:'VBAP_ARKTX'},
		{header:'销售数量',sortable:true,dataIndex:'VBAP_ZMENG'},
		{header:'销售单位',sortable:true,dataIndex:'VBAP_VRKME'},
		{header:'销售币别',sortable:true,dataIndex:'VBAK_WAERK'},
		{header:'销售单价',sortable:true,dataIndex:'KONV_KBETR'},
		{header:'条件定价单位',sortable:true,dataIndex:'VBAP_KPEIN'},
		{header:'单行物料总金额',sortable:true,dataIndex:'SALEROWTOTAL'},
		//
		{header:'销售总金额',sortable:true,dataIndex:'ORDER_TOTAL'},
		{header:'交货日期',sortable:true,dataIndex:'SHIPMENT_DATE'},
		{header:'交货地点',sortable:true,dataIndex:'SHIPMENT_PORT'},
		{header:'收款条件',sortable:true,dataIndex:'VBKD_ZTERM'},//字典表
		{header:'收款方式',sortable:true,dataIndex:'VBKD_ZLSCH_NAME'},
	//
		{header:'交货单编号',sortable:true,dataIndex:'SHIP_NO'},
		{header:'实际发货时间',sortable:true,dataIndex:'SHIP_CREATOR_TIME'},
		{header:'实际发货数量',sortable:true,dataIndex:'SHIP_QUANTITY'},
		{header:'实际销售金额',sortable:true,dataIndex:''},
		{header:'已累计发货数量',sortable:true,dataIndex:''},
		{header:'已累计发货金额',sortable:true,dataIndex:''},
		{header:'未发数量',sortable:true,dataIndex:''},
		{header:'发票号',sortable:true,dataIndex:''},
	///收款信息维护表
		{header:'收款时间',sortable:true,dataIndex:'RECEIPT_DATE'},
		{header:'实际收款时间',sortable:true,dataIndex:'REAL_RECEIPT_DATE'},
		{header:'应收金额',sortable:true,dataIndex:'RECEIPT_TOTAL'},
		{header:'已收款金额',sortable:true,dataIndex:'REAL_RECEIPT_TOTAL'},
		{header:'应收余额',sortable:true,dataIndex:'RECEIPT_BALANCE'},
		{header:'销售合同状态',sortable:true,dataIndex:'SALE_CONTRACT_STAUS'},
	///	
		
		{header:'采购合同号',sortable:true,dataIndex:'PURCHASE_CONTRACT_NO'},
		{header:'采购订单日期',sortable:true,dataIndex:'EKKO_BEDAT'},
		{header:'供应商编码',sortable:true,dataIndex:'EKKO_LIFNR'},
		{header:'供应商名称',sortable:true,dataIndex:'EKKO_LIFNR_NAME'},
		{header:'物料编码',sortable:true,dataIndex:'EKPO_MATNR'},
		{header:'物料名称',sortable:true,dataIndex:'EKPO_TXZ01'},
		{header:'收购币别',sortable:true,dataIndex:'EKPO_BPRME'},
		{header:'收购数量',sortable:true,dataIndex:'EKPO_MENGE'},
		{header:'收购单位',sortable:true,dataIndex:'EKPO_MEINS'},
		{header:'收购单价',sortable:true,dataIndex:'EKPO_NETPR'},
		{header:'条件定价单位',sortable:true,dataIndex:'EKPO_PEINH'},
		{header:'单行物料总金额',sortable:true,dataIndex:'PURCHASEROWTOTAL'},
		{header:'收购总金额',sortable:true,dataIndex:'TOTAL'},
		{header:'付款条件',sortable:true,dataIndex:'EKKO_ZTERM'},//字典表
		{header:'付款方式',sortable:true,dataIndex:'EKKO_ZTERM_NAME'},
		//
		{header:'收货单编号',sortable:true,dataIndex:'RECEIPT_NO'},
		{header:'实际收货数量',sortable:true,dataIndex:'RECEIPT_QUANTITY'},
		{header:'实际收货时间',sortable:true,dataIndex:'RECEIPT_CREATOR_TIME'},
		{header:'实际采购金额',sortable:true,dataIndex:'RECEIPT_PRICE'},
		{header:'已累计收货数量',sortable:true,dataIndex:''},
		{header:'已累计收货金额',sortable:true,dataIndex:''},
		{header:'未收数量',sortable:true,dataIndex:''},
		///付款信息维护表
		{header:'付款时间',sortable:true,dataIndex:'PAY_DATE'},
		{header:'实际付款时间',sortable:true,dataIndex:'REAL_PAY_DATE'},
		{header:'应付金额',sortable:true,dataIndex:'PAY_TOTAL'},
		{header:'已付款金额',sortable:true,dataIndex:'REAL_PAY_TOTAL'},
		{header:'应付余额',sortable:true,dataIndex:'PAY_BALANCE'},
		{header:'采购合同状态',sortable:true,dataIndex:'PURCHASE_CONTRACT_STAUS'}
		
	];
	store = new Ext.data.Store({
    			proxy : new Ext.data.HttpProxy({url:''}),
    			reader: new Ext.data.JsonReader({
        					root: 'root',
        					totalProperty: 'totalProperty'
        				},
        				fields
				)
	});
	var cm = new Ext.grid.ColumnModel(
		columns
	);
	var pagingBar = new Ext.PagingToolbar({
         	displayInfo:true,
         	emptyMsg:'没有数据显示',
         	displayMsg:"显示从{0}条数据到{1}条数据，共{2}条数据",
         	store:store,
         	pageSize:20
     	});
	
	grid1 = new Ext.grid.GridPanel({
  		applyTo:'gridDiv1',
		ds:store,
		cm:cm,
		bbar:pagingBar,
		border:true,
		loadMask:true,
		autoScroll:true,
		height:200,
		frame:true,
	    iconCls:'icon-grid',
        autowidth:true,
		viewConfig : {  
               // forceFit : true,  
                enableRowBody:true,
                getRowClass : 
                	function(record,rowIndex,rowParams,store){ 
                		if(rowIndex%2==0)
                			return 'x-grid-row-bgcolor'; 
                		else
                			return '';	
                	}  
        }
	});   
	grid2 = new Ext.grid.GridPanel({
  		applyTo:'gridDiv2',
		ds:store,
		cm:cm,
		bbar:pagingBar,
		border:true,
		loadMask:true,
		autoScroll:true,
		height:200,
		frame:true,
	    iconCls:'icon-grid',
        autowidth:true,
		viewConfig : {  
               // forceFit : true,  
                enableRowBody:true,
                getRowClass : 
                	function(record,rowIndex,rowParams,store){ 
                		if(rowIndex%2==0)
                			return 'x-grid-row-bgcolor'; 
                		else
                			return '';	
                	}  
        }
	});   
	
//------------------------------------------------------
	var main=new Ext.form.FormPanel({
	renderTo:document.body,
	frame:true,
	baseCls:'x-plain',
	autoHeight:true,
	autoWidth:true,
	bodyStyle:'padding: 10px 10px 0 10px;',
	items:[{
		contentEl:'queryDiv'
	},{
		contentEl:'gridDiv1',
		height:200
	},{
		contentEl:'gridDiv2',
		height:200
	}]
});
/*	
var viewport = new Ext.Viewport({
	layout:"border",
	items:[{
		region:"north",
		collapsible: true,
		contentEl:'queryDiv'
	},
	{
		region:'center',
		layout:'fit',
	    split:true,
		collapsible: true,
//		height:220,
//		minSize: 210,
//        maxSize: 400,
		items:[{
				region:'north',
				height:100,
				layout:'fit',
				items:[grid1]
			},{
				region:'center',
				height:100,
				layout:'fit',
				itmes:[grid2]
			}]
	}]
});
*/
	
});
function contractKind(value,metadata,record){
	var no = record.data.SALE_CONTRACT_NO;
	if(no!=null)
		return '销售合同';
	else
		return '采购合同';
	
}

function rendTradeType(value,metadata,record){
	var strshipTitle = Utils.getTradeTypeTitle(value);
	return strshipTitle;
}
function find(){
	var params = Form.serialize('queryForm');
	store.proxy=new Ext.data.HttpProxy({url:'outerReportController.spr?action=rtnHomeTradeReport&'+params});
	store.load({params:{start:0, limit:20}});
}    			
function fullScreen(){
	//<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 id='webObject' name='webObject' width=0></OBJECT>
	$('webObject').execwb(7,1);
}
</script></html>
