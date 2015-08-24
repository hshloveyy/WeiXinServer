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
<title>出货详细信息</title>
</head>
<body>
<div id='div_toolbar'></div>
<div id='grid_div'></div>
<script type="text/javascript">
var store;
var serv_param='contract_no=${CONTRACT_NO}&project_no=${PROJECT_NO}&contract_group_no=${CONTRACT_GROUP_NO}';
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	var field=[{name:'OLD_CONTRACT_NO'},{name:'CONTRACT_NO'},{name:'KUAGV_KUNNR_NAME'}
   				,{name:'VBAP_ARKTX'},{name:'SHIP_NO'},{name:'QUANTITY'},{name:'SHIP_TIME'}
   				,{name:'TOTAL'},{name:'QUANTITY'},{name:'MATERIAL_UNIT'},{name:'CURRENCY'},{name:'TOTAL'}	
   				,{name:'DEPT_NAME'},{name:'TRADE_TYPE'}
   				];
   	var column=[new Ext.grid.RowNumberer(),
   	         			{header:'部门',sortable:true,dataIndex:'DEPT_NAME',width:100}
   	         			,{header:'业务类型',sortable:true,dataIndex:'TRADE_TYPE',width:100}
	            		,{header:'销售合同号',sortable:true,dataIndex:'CONTRACT_NO',width:100}
	            		,{header:'外部纸质合同号',sortable:true,dataIndex:'OLD_CONTRACT_NO',width:100}
	            		,{header:'客户名称',sortable:true,dataIndex:'KUAGV_KUNNR_NAME',width:150}
	            		,{header:'物料名称',sortable:true,dataIndex:'VBAP_ARKTX',width:80}
	            		,{header:'出仓日期',sortable:true,dataIndex:'SHIP_TIME',width:80}
	            		,{header:'出仓单号',sortable:true,dataIndex:'SHIP_NO',width:80}
	            		,{header:'数量',sortable:true,dataIndex:'QUANTITY',width:80}
	            		,{header:'单位',sortable:true,dataIndex:'MATERIAL_UNIT',width:80}
	            		,{header:'币别',sortable:true,dataIndex:'CURRENCY',width:80}
	            		,{header:'出仓金额',sortable:true,dataIndex:'TOTAL',width:80}
	            		
					];
   	function memoRenderer(value,metadata,record){
			return '审批通过时间:'+record.data.APPROVED_TIME;
   	   	}
	function divideUnit1(value,metadata,record){
		if(record.data.EKPO_PEINH=='')return '';
		return record.data.PRICE/record.data.EKPO_PEINH;
	}
	function divideUnit2(value,metadata,record){
		if(record.data.VBAP_KPEIN=='')return '';
		return record.data.KONV_KBETR/record.data.VBAP_KPEIN;
	}
   	
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'homeTradeController.spr?action=rtnShipGoods&'+serv_param}),
		reader: new Ext.data.JsonReader({
					root: 'root',
					totalProperty: 'totalProperty'
				},
				field
				)
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
			contentEl:'div_toolbar',
			height:'26'
			},{
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
	var param = serv_param;
	window.location.href('homeTradeController.spr?action=shipGoodsToExcel&'+param);
	
}
function closeProcessBar(){
	var processingHint = new MessageBoxUtil();
	processingHint.Close();
}

</script>
</body>
</html>