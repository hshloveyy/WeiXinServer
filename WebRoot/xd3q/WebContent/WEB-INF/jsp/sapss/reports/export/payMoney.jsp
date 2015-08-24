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
<title>采购付款详细信息</title>
</head>
<body>
<div id='div_toolbar'></div>
<div id='grid_div'></div>
<script type="text/javascript">
var store;
var serv_param='contract_no=${CONTRACT_NO}&project_no=${PROJECT_NO}&contract_group_no=${CONTRACT_GROUP_NO}&contract_purchase_id=${CONTRACT_PURCHASE_ID}';
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	var field=[{name:'OLD_CONTRACT_NO'},{name:'CONTRACT_NO'},{name:'EKKO_LIFNR_NAME'},{name:'PAY_DATE'},{name:'REAL_PAY_DATE'}
   				,{name:'PAY_TOTAL'},{name:'REAL_PAY_TOTAL'},{name:'PAY_BALANCE'},{name:'EKKO_LIFNR_NAME'},{name:'INNER_PAY_DATE'}
   				,{name:'EKPO_TXZ01'},{name:'PAYED_VALUE'},{name:'TRADE_TYPE'},{name:'PROJECT_NO'}
		];
   	var column=[new Ext.grid.RowNumberer(),
	            		{header:'项目号',sortable:true,dataIndex:'PROJECT_NO',width:80}
	            		,{header:'品名',sortable:true,dataIndex:'EKPO_TXZ01',width:80}
	            		,{header:'采购合同号',sortable:true,dataIndex:'CONTRACT_NO',width:80}
	            		,{header:'外部纸质合同号',sortable:true,dataIndex:'OLD_CONTRACT_NO',width:80}
	            		,{header:'供应商名称',sortable:true,dataIndex:'EKKO_LIFNR_NAME',width:150}
	            		,{header:'贸易方式',sortable:true,dataIndex:'TRADE_TYPE',width:80,renderer:tradeTypeRender}
	            		,{header:'对内付款日',sortable:true,dataIndex:'INNER_PAY_DATE',width:80}
	            		,{header:'已付款金额',sortable:true,dataIndex:'PAYED_VALUE',width:80}
	            		,{header:'应付余额',sortable:true,dataIndex:'',width:80}
					];
	function tradeTypeRender(value,metadata,record){
		switch(value){
			case '1':
			case '2':
			case '3':
			case '4':
			case '5': 
			case '6': 
			case '7':
				return '一般贸易';
			case '8':
				return '进料、来料';
		}	
	}
   
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'exportRController.spr?action=rtnPayMoney&'+serv_param}),
		reader: new Ext.data.JsonReader({
					root: 'root',
					totalProperty: 'totalProperty'
				},
				field
				)
	});
	
	store.on('beforeload',function(){
		var processingHint = new MessageBoxUtil();
		processingHint.waitShow();
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
	var param = serv_param;
	window.location.href('exportRController.spr?action=payMoneyToExcel&'+param);
	
}
function closeProcessBar(){
	var processingHint = new MessageBoxUtil();
	processingHint.Close();
}

</script>
</body>
</html>