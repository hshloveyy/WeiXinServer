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
<title>信用证收证一览表</title>
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
   	var field=[{name:'DEPT_NAME'},{name:'TRADE_TYPE'},{name:'PROJECT_NO'},{name:'CONTRACT_NO'},{name:'VBKD_IHREZ'},{name:'KUAGV_KUNNR_NAME'}
   		,{name:'VBAP_ARKTX'},{name:'CREATE_DATE'},{name:'CREDIT_REC_DATE'},{name:'CREDIT_NO'}
   		,{name:'CREATE_BANK'},{name:'LOADING_PERIOD'},{name:'PERIOD'},{name:'VALID_DATE'},{name:'CURRENCY'},
   		{name:'AMOUNT'},{name:'CREDIT_BALANCE'},{name:'CREDIT_STATE'}
   	];
   	var column=[new Ext.grid.RowNumberer(),
	            		{header:'部门',sortable:true,dataIndex:'DEPT_NAME',width:80}
	            		,{header:'业务类型',sortable:true,dataIndex:'TRADE_TYPE',width:80}
	            		,{header:'立项号',sortable:true,dataIndex:'PROJECT_NO',width:80}
	            		,{header:'销售合同号',sortable:true,dataIndex:'CONTRACT_NO',width:80}
	            		,{header:'外部纸质合同号',sortable:true,dataIndex:'VBKD_IHREZ',width:80}
	            		,{header:'客户名称',sortable:true,dataIndex:'KUAGV_KUNNR_NAME',width:150}
	            		,{header:'货物品名',sortable:true,dataIndex:'VBAP_ARKTX',width:80}
	            		,{header:'开证日期',sortable:true,dataIndex:'CREATE_DATE',width:80}
	            		,{header:'收证日期',sortable:true,dataIndex:'CREDIT_REC_DATE',width:80}//
	            		,{header:'信用证号',sortable:true,dataIndex:'CREDIT_NO',width:80}
	            		,{header:'开证行',sortable:true,dataIndex:'CREATE_BANK',width:80}
	            		,{header:'装期',sortable:true,dataIndex:'LOADING_PERIOD',width:80}
	            		,{header:'效期',sortable:true,dataIndex:'VALID_DATE',width:80}
	            		,{header:'信用证期限',sortable:true,dataIndex:'PERIOD',width:80}
	            		,{header:'信用证币别',sortable:true,dataIndex:'CURRENCY',width:80}
	            		,{header:'信用证金额',sortable:true,dataIndex:'AMOUNT',width:80}
	            		,{header:'信用证余额',sortable:true,dataIndex:'CREDIT_BALANCE',width:80}
	            		,{header:'信用证状态',sortable:true,dataIndex:'CREDIT_STATE',width:80}
					];
   		
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'exportRController.spr?action=rtnCreditReceiptView&'+serv_param}),
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
	window.location.href('exportRController.spr?action=creditReceiptViewToExcel&'+param);
	
}
function closeProcessBar(){
	var processingHint = new MessageBoxUtil();
	processingHint.Close();
}

</script>
</body>
</html>