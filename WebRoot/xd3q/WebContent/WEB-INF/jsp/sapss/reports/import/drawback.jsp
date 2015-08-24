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
<title>核销退税详细信息</title>
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
   	var field=[{name:'DEPT_NAME'},{name:'TRADE_TYPE'},{name:'EKKO_UNSEZ'},{name:'EKPO_TXZ01'},{name:'CUSTOM_BIZ_NO'}
   				,{name:'CUSTOM_NO'},{name:'PRE_WR_CD'},{name:'CUSTOME_CASH'},{name:'CURRENCY'},{name:'CUSTOM_AMOUNT'}
   				,{name:'CUSTOM_COMPANY'},{name:'REACH_WAY'},{name:'CUSTOME_PORT'},{name:'IMPORT_COUNTRY'},{name:'IMPORT_DATE'},
   				{name:'DRAWBACK_NO'},{name:'PAY_DATE'}
   				,{name:'PICK_LIST_TYPE'},{name:'PAY_BANK'},{name:'PAY_REAL_VALUE'},{name:'DRAWBACK_VALUE'},{name:'DRAWBACK_DATE'},
   				{name:'RTN_VALUE'},{name:'RTN_DATE'},{name:'RUN_COMPANY'},{name:'RECEIPT_COMPANY'},{name:'CMD'}
   			];
   	var column=[new Ext.grid.RowNumberer(),
        		{header:'部门',sortable:true,dataIndex:'DEPT_NAME',width:80}
				,{header:'业务类型',sortable:true,dataIndex:'TRADE_TYPE',width:80}
				,{header:'外部纸质合同号',sortable:true,dataIndex:'EKKO_UNSEZ',width:80}
				,{header:'物料名称',sortable:true,dataIndex:'EKPO_TXZ01',width:80}
				,{header:'海关商编',sortable:true,dataIndex:'CUSTOM_BIZ_NO',width:80}
				,{header:'报关单号',sortable:true,dataIndex:'CUSTOM_NO',width:80}
				,{header:'预录入号',sortable:true,dataIndex:'PRE_WR_CD',width:80}
				,{header:'报关金额',sortable:true,dataIndex:'CUSTOME_CASH',width:80}
				,{header:'成交币别',sortable:true,dataIndex:'CURRENCY',width:80}
				,{header:'报关数量',sortable:true,dataIndex:'CUSTOM_AMOUNT',width:80}
				,{header:'报关单位',sortable:true,dataIndex:'CUSTOM_COMPANY',width:80}
				,{header:'成交方式',sortable:true,dataIndex:'REACH_WAY',width:80}
				,{header:'进口口岸',sortable:true,dataIndex:'CUSTOME_PORT',width:80}
				,{header:'进口国别',sortable:true,dataIndex:'IMPORT_COUNTRY',width:80}
				,{header:'进口日期',sortable:true,dataIndex:'IMPORT_DATE',width:80}
				,{header:'核销单号',sortable:true,dataIndex:'DRAWBACK_NO',width:80}
				,{header:'付汇日期',sortable:true,dataIndex:'PAY_DATE',width:80}
				,{header:'付汇方式',sortable:true,dataIndex:'PICK_LIST_TYPE',width:80}
				,{header:'付汇银行',sortable:true,dataIndex:'PAY_BANK',width:80}
				,{header:'付汇金额',sortable:true,dataIndex:'PAY_REAL_VALUE',width:80}
				,{header:'核销金额',sortable:true,dataIndex:'DRAWBACK_VALUE',width:80}
				,{header:'核销日期',sortable:true,dataIndex:'DRAWBACK_DATE',width:80}
				,{header:'退汇金额',sortable:true,dataIndex:'RTN_VALUE',width:80}
				,{header:'退汇日期',sortable:true,dataIndex:'RTN_DATE',width:80}
				,{header:'经营单位',sortable:true,dataIndex:'RUN_COMPANY',width:80}
				,{header:'收货单位',sortable:true,dataIndex:'RECEIPT_COMPANY',width:80}
				,{header:'备注',sortable:true,dataIndex:'CMD',width:80}
			];
   		
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'importController.spr?action=rtnDrawback&'+serv_param}),
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
	window.location.href('importController.spr?action=drawbackToExcel&'+param);
	
}
function closeProcessBar(){
	var processingHint = new MessageBoxUtil();
	processingHint.Close();
}

</script>
</body>
</html>