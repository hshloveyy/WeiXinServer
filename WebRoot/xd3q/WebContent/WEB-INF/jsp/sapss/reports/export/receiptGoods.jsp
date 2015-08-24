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
<title>采购收货信息</title>
</head>
<body>
<div id='div_toolbar'></div>
<div id='grid_div'></div>
<script type="text/javascript">
var processingHint = new MessageBoxUtil();
processingHint.waitShow();
var store;
var serv_param='contract_no=${CONTRACT_NO}&project_no=${PROJECT_NO}&contract_group_no=${CONTRACT_GROUP_NO}';
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	var field=[{name:'DEPT_NAME'},{name:'TRADE_TYPE'},{name:'CONTRACT_NO'},{name:'EKKO_UNSEZ'},{name:'EKKO_LIFNR_NAME'},{name:'EKPO_TXZ01'},
   	     ,{name:'RECEIPT_TIME'},{name:'RECEIPT_NO'},{name:'QUANTITY'},{name:'MATERIAL_UNIT'},{name:'CURRENCY'},{name:'TOTAL'}];
   	var column=[new Ext.grid.RowNumberer(),
   	         			{header:'部门',sortable:true,dataIndex:'DEPT_NAME',width:80}
						,{header:'业务类型',sortable:true,dataIndex:'TRADE_TYPE',width:80}
						,{header:'采购合同号',sortable:true,dataIndex:'CONTRACT_NO',width:80}
						,{header:'外部纸质合同号',sortable:true,dataIndex:'EKKO_UNSEZ',width:80}
						,{header:'供应商名称',sortable:true,dataIndex:'EKKO_LIFNR_NAME',width:150}
						,{header:'物料名称',sortable:true,dataIndex:'EKPO_TXZ01',width:80}
						,{header:'进仓日期',sortable:true,dataIndex:'RECEIPT_TIME',width:80}
						,{header:'进仓单号',sortable:true,dataIndex:'RECEIPT_NO',width:80}
						,{header:'进仓数量',sortable:true,dataIndex:'QUANTITY',width:80}
						,{header:'单位',sortable:true,dataIndex:'MATERIAL_UNIT',width:80}
						,{header:'币别',sortable:true,dataIndex:'CURRENCY',width:80}
						,{header:'进仓金额',sortable:true,dataIndex:'TOTAL',width:80}
					];
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'exportRController.spr?action=rtnReceiptGoods&'+serv_param}),
		reader: new Ext.data.JsonReader({
					root: 'root',
					totalProperty: 'totalProperty'
				},
				field
				)
	});
	
	store.on('beforeload',function(){

	});
	store.on('load',function(){
		var processingHint = new MessageBoxUtil();
		processingHint.Close();
	});
	store.load();
	var cm = new Ext.grid.ColumnModel(
			column
		);
	var pagingBar = new Ext.PagingToolbar({
     	displayInfo:true,
     	emptyMsg:'没有数据显示',
     	displayMsg:"显示从{0}条数据到{1}条数据，共{2}条数据",
     	store:store,
     	pageSize:100
	});	
	var grid = new Ext.grid.GridPanel({
  		applyTo:'grid_div',
		ds:store,
		cm:cm,
//		bbar:pagingBar,
		border:true,
		loadMask:true,
		autoScroll:true,
		layout:'fit',
		frame:true,
	    iconCls:'icon-grid',
        autowidth:true,
        height:500,
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
	var toolbars = new Ext.Toolbar({
		items:[
				'->','-',
				{id:'exp',text:'导出',handler:doExport,iconCls:'exp'},'-',
				{id:'cls',text:'关闭',handler:doClose,iconCls:'cls'}
				],
		renderTo:"div_toolbar"
	});

	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:'north',
			contentel:'div_toolbar',
			height:26
			},
		{
			region:'center',
			layout:'fit',
		    split:true,
		    height:500,
			collapsible: true,
			items:[grid]
		}]
	});
});
function doClose(){
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
}
function doExport(){
	var ProcessingHint = new MessageBoxUtil();
	ProcessingHint.waitShow();
	setTimeout("closeProcessBar()",2000);
	window.location.href('exportRController.spr?action=receiptGoodsToExcel&'+serv_param);
	
}
function closeProcessBar(){
	var processingHint = new MessageBoxUtil();
	processingHint.Close();
}

</script>
</body>
</html>