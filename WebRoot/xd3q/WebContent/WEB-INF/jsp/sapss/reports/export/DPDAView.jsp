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
<title>托收项下出口出单一览表</title>
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
   	var field=[{name:'DEPT_NAME'},{name:'TRADE_TYPE'},{name:'SALES_NO'},{name:'CONTRACT_PAPER_NO'},{name:'CONTRACT_TOTAL'},{name:'CUSTOMER'}
   			,{name:'MATERIAL_NAME'},{name:'LCDPDA_NO'},{name:'AMOUNT'},{name:'VALID_DATE'},{name:'INV_NO'},{name:'CURRENCY'}
   			,{name:'TOTAL'},{name:'EXAM_DATE'},{name:'BANK'},{name:'IS_NEGOTIAT'},{name:'SHOULD_INCOME_DATE'}
   			,{name:'REAL_RECEIPT_DATE'},{name:'REAL_RECEIPT_TOTAL'}];
   	var column=[new Ext.grid.RowNumberer(),
   	         {header:'部门',sortable:true,dataIndex:'DEPT_NAME',width:80}
			,{header:'业务类型',sortable:true,dataIndex:'TRADE_TYPE',width:80}
			,{header:'销售合同号',sortable:true,dataIndex:'SALES_NO',width:80}
			,{header:'外部纸质合同号',sortable:true,dataIndex:'CONTRACT_PAPER_NO',width:80}
			,{header:'合同金额',sortable:true,dataIndex:'CONTRACT_TOTAL',width:150}
			,{header:'客户名称',sortable:true,dataIndex:'CUSTOMER',width:150}
			,{header:'货物品名',sortable:true,dataIndex:'MATERIAL_NAME',width:80}
			,{header:'D/P或D/A NO.',sortable:true,dataIndex:'LCDPDA_NO',width:80}
			,{header:'托收期限',sortable:true,dataIndex:'VALID_DATE',width:80}
			,{header:'发票号',sortable:true,dataIndex:'INV_NO',width:80}
			,{header:'币别',sortable:true,dataIndex:'CURRENCY',width:80}
			,{header:'发票金额',sortable:true,dataIndex:'TOTAL',width:80}
			,{header:'交单日期',sortable:true,dataIndex:'EXAM_DATE',width:80}
			,{header:'交单银行',sortable:true,dataIndex:'BANK',width:80}
			,{header:'是否押汇',sortable:true,dataIndex:'IS_NEGOTIAT',width:80}
			,{header:'应收汇日',sortable:true,dataIndex:'SHOULD_INCOME_DATE',width:80}
			,{header:'实收汇日',sortable:true,dataIndex:'REAL_RECEIPT_DATE',width:80}
			,{header:'实收金额',sortable:true,dataIndex:'REAL_RECEIPT_TOTAL',width:80}
		];
   		
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'exportRController.spr?action=rtnDPDAView&'+serv_param}),
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
	window.location.href('exportRController.spr?action=DPDAViewToExcel&'+param);
	
}
function closeProcessBar(){
	var processingHint = new MessageBoxUtil();
	processingHint.Close();
}

</script>
</body>
</html>