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
<title>出口综合报表</title>
</head>
<body >
<div id="queryDiv">
<form name="queryForm">
	<table width="1024">
	<tr>
		<td width="200" align="right">项目编号:</td>	
		<td><input type="text" name="projectNo"/></td>	
		<td width="200" align="right">合同编号:</td>	
		<td><input type="text" name="contractNo"/></td>	
		<td width="200" align="right">销售SAP编号:</td>	
		<td><input type="text" name="saleSAPNo"/></td>	
		<td width="200" align="right">采购SAP编号:</td>	
		<td><input type="text" name="purchaseSAPNo"/></td>	
		<td><input type="button" name="" value="查找" onclick="find()"/></td>
		<td><input type="button" name="" value="清空"/></td>
		<td></td>
	</tr>
	</table>
</form>
</div>
<div id="gridDiv"></div>	
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
	        	{name:'APPLY_TIME'},
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
	        	{name:'VBAP_KWMENG'},
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
	        	{name:'SALE_CONTRACT_NO'},
	        	{name:'KUAGV_KUNNR'},
	        	{name:'KUAGV_KUNNR_NAME'},
	        	{name:'ORDER_TOTAL'},
	        	{name:'CUSTOM_CREATE_DATE'},
	        	{name:'CREDIT_REC_DATE'},
	        	{name:'CREDIT_NO'},
	        	{name:'LOADING_PERIOD'},
	        	{name:'PERIOD'}
	        	];
	var columns=[new Ext.grid.RowNumberer(),
		{header:'立项ID',hidden:true,sortable:true,dataIndex:'PROJECT_ID'},
		{header:'业务类型',sortable:true,dataIndex:'TRADE_TYPE',renderer:rendTradeType},
		{header:'立项日期',sortable:true,dataIndex:'APPLY_TIME'},
		{header:'立项编号',sortable:true,dataIndex:'PROJECT_NO'},
		{header:'卖方/委托方',sortable:true,dataIndex:'CUSTOMER_LINK_MAN'},
		{header:'销售合同号',sortable:true,dataIndex:'SALE_CONTRACT_NO'},
		{header:'销售订单号',sortable:true,dataIndex:'SALE_SAP_NO'},
		{header:'销售合同日期',sortable:true,dataIndex:'VBAK_AUDAT'},
		{header:'客户编码',sortable:true,dataIndex:'KUAGV_KUNNR'},
		{header:'客户名称',sortable:true,dataIndex:'KUAGV_KUNNR_NAME'},
		{header:'国际贸易条件',sortable:true,dataIndex:'VBKD_INCO1_NAME'},
		{header:'物料编号',sortable:true,dataIndex:'VBAP_MATNR'},
		{header:'品名',sortable:true,dataIndex:'VBAP_ARKTX'},
		{header:'销售数量',sortable:true,dataIndex:'VBAP_KWMENG'},
		{header:'销售单位',sortable:true,dataIndex:'VBAP_VRKME'},
		{header:'销售币别',sortable:true,dataIndex:'VBAK_WAERK_NAME'},
		{header:'出口单价',sortable:true,dataIndex:'KONV_KBETR'},
		{header:'销售总金额',sortable:true,dataIndex:'ORDER_TOTAL'},
		{header:'采购合同号',sortable:true,dataIndex:'PURCHASE_CONTRACT_NO'},
		{header:'采购订单',sortable:true,dataIndex:'PURCHASE_SAP_NO'},
		{header:'供应商编码',sortable:true,dataIndex:'EKKO_LIFNR'},
		{header:'供应商名称',sortable:true,dataIndex:'EKKO_LIFNR_NAME'},
		{header:'收购币别',sortable:true,dataIndex:'EKPO_BPRME'},
		{header:'收购数量',sortable:true,dataIndex:'EKPO_MENGE'},
		{header:'收购单位',sortable:true,dataIndex:'EKPO_MEINS'},
		{header:'收购单价',sortable:true,dataIndex:'EKPO_NETPR'},
		{header:'收购总金额',sortable:true,dataIndex:'TOTAL'},
		{header:'装运期限',sortable:true,dataIndex:'SHIPMENT_DATE'},
		{header:'收购期限',sortable:true,dataIndex:''},
		{header:'装运港',sortable:true,dataIndex:'SHIPMENT_PORT'},
		{header:'目的港',sortable:true,dataIndex:'DESTINATION_PORT'},
		{header:'对外结算条件',sortable:true,dataIndex:''},
		{header:'对内结算条件',sortable:true,dataIndex:''},
		{header:'客户开证日期',sortable:true,dataIndex:'CUSTOM_CREATE_DATE'},
		{header:'到证日期',sortable:true,dataIndex:'CREDIT_REC_DATE'},
		{header:'信用证号',sortable:true,dataIndex:'CREDIT_NO'},
		{header:'L/C装期',sortable:true,dataIndex:'LOADING_PERIOD'},
		{header:'L/C效期',sortable:true,dataIndex:'PERIOD'}
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
	
	grid = new Ext.grid.GridPanel({
  		applyTo:'gridDiv',
		ds:store,
		cm:cm,
		bbar:pagingBar,
		border:true,
		loadMask:true,
		autoScroll:true,
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
/*	var main=new Ext.form.FormPanel({
	renderTo:document.body,
	frame:true,
	baseCls:'x-plain',
	autoHeight:true,
	width:window.screen.width-50,
	bodyStyle:'padding: 10px 10px 0 10px;',
	defaults:{
		anchor:'95%',
		msgTarget:'side',
		align:'center'
	},
	items:[{
		contentEl:'queryDiv'
	},{
		contentEl:'gridDiv'
	}]
});
*/	
var viewport = new Ext.Viewport({
	layout:"border",
	items:[{
		region:"north",
		collapsible: true,
		contentEl:'queryDiv'
	},
	{
		region:"center",
		layout:'fit',
	    split:true,
		collapsible: true,
//		height:220,
//		minSize: 210,
//        maxSize: 400,
		items:[grid]
	}]
});

	
});
function rendTradeType(value,metadata,record){
	var strshipTitle = Utils.getTradeTypeTitle(value);
	return strshipTitle;
}
function find(){
	var params = Form.serialize('queryForm');
	store.proxy=new Ext.data.HttpProxy({url:'outerReportController.spr?action=rtnFindExport&'+params});
	store.load({params:{start:0, limit:20}});
}    			
function fullScreen(){
	//<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 id='webObject' name='webObject' width=0></OBJECT>
	$('webObject').execwb(7,1);
}
</script></html>
