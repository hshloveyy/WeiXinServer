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
<title>进口综合报表</title>
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
	        	{name:'PROVIDER_LINK_MAN'},
	        	{name:'PURCHASE_CONTRACT_NO'},
	        	{name:'EKKO_LIFNR'},
	        	{name:'EKKO_LIFNR_NAME'},
	        	{name:'TOTAL'},
	        	{name:'SHIPMENT_DATE'},
	        	{name:'SHIPMENT_PORT'},
	        	{name:'DESTINATION_PORT'},
	        	{name:'PURCHASE_ROWS_ID'},
	        	{name:'EKPO_MATNR'},
	        	{name:'EKPO_TXZ01'},
	        	{name:'EKET_EINDT'},
	        	{name:'EKPO_MENGE'},
	        	{name:'EKPO_MEINS'},
	        	{name:'EKPO_BPRME'},
	        	{name:'EKPO_NETPR'},
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
	        	{name:'ORDER_TOTAL'}
	        	];
	var columns=[new Ext.grid.RowNumberer(),
		{header:'立项ID',hidden:true,sortable:true,dataIndex:'PROJECT_ID'},
		{header:'业务类型',sortable:true,dataIndex:'TRADE_TYPE',renderer:rendTradeType},
		{header:'立项日期',sortable:true,dataIndex:'APPLY_TIME'},
		{header:'立项编号',sortable:true,dataIndex:'PROJECT_NO'},
		{header:'买方/委托方',sortable:true,dataIndex:'PROVIDER_LINK_MAN'},
		{header:'物料行编号',hidden:true,sortable:true,dataIndex:'PURCHASE_ROWS_ID'},
		{header:'物料编码',sortable:true,dataIndex:'EKPO_MATNR'},
		{header:'品名',sortable:true,dataIndex:'EKPO_TXZ01'},
		{header:'成交日期',sortable:true,dataIndex:'EKET_EINDT'},
		{header:'采购合同号',sortable:true,dataIndex:'PURCHASE_CONTRACT_NO'},
		{header:'销售合同号',sortable:true,dataIndex:'SALE_CONTRACT_NO'},
		{header:'供应商编码',sortable:true,dataIndex:'EKKO_LIFNR'},
		{header:'供应商名称',sortable:true,dataIndex:'EKKO_LIFNR_NAME'},
		{header:'客户编码',sortable:true,dataIndex:'KUAGV_KUNNR'},
		{header:'客户名称',sortable:true,dataIndex:'KUAGV_KUNNR_NAME'},
		{header:'数量',sortable:true,dataIndex:'EKPO_MENGE'},
		{header:'单位',sortable:true,dataIndex:'EKPO_MEINS'},
		{header:'币别',sortable:true,dataIndex:'EKPO_BPRME'},
		{header:'进口单价',sortable:true,dataIndex:'EKPO_NETPR'},
		{header:'价格条件',sortable:true,dataIndex:'EKPO_PEINH'},
		{header:'内销单价',sortable:true,dataIndex:''},
		{header:'销售总金额',sortable:true,dataIndex:'ORDER_TOTAL'},
		{header:'进口总金额',sortable:true,dataIndex:'TOTAL'},
		{header:'装运期限',sortable:true,dataIndex:'SHIPMENT_DATE'},
		{header:'装运港',sortable:true,dataIndex:'SHIPMENT_PORT'},
		{header:'目的港',sortable:true,dataIndex:'DESTINATION_PORT'},
		{header:'对外结算方式',sortable:true,dataIndex:''},
		{header:'对内结算方式',sortable:true,dataIndex:''},
		{header:'实际开证日期',sortable:true,dataIndex:'CUSTOM_CREATE_DATE'},
		{header:'保证金金额',sortable:true,dataIndex:'CREDIT_INFO'},
		{header:'实际收到保证金日期',sortable:true,dataIndex:'BAIL_DATE'},
		{header:'开证行',sortable:true,dataIndex:'CREATE_BANK'},
		{header:'信用证号',sortable:true,dataIndex:'CREDIT_NO'},
		{header:'装期',sortable:true,dataIndex:'LOADING_PERIOD'},
		{header:'有效期',sortable:true,dataIndex:'PERIOD'}
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
	store.proxy=new Ext.data.HttpProxy({url:'outerReportController.spr?action=rtnFindImport&'+params});
	store.load({params:{start:0, limit:20}});
}    			
function fullScreen(){
	//<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 id='webObject' name='webObject' width=0></OBJECT>
	$('webObject').execwb(7,1);
}
</script></html>
