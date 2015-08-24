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
   	var field=[{name:'DEPT_NAME'},{name:'TRADE_TYPE'},{name:'VBKD_IHREZ'},{name:'MATERIAL_NAME'},{name:'CUSTOM_BIZ_NO'}
   				,{name:'CUSTOM_NO'},{name:'PRE_WRITE_NO'},{name:'CUSTOM_TOTAL'},{name:'CURRENCY'},{name:'CUSTOM_AMOUNT'},
   				{name:'CUSTOM_COMPANY'},{name:'VBKD_INCO1'},{name:'EXPORT_PORT'},{name:'DESTINATIONS'},{name:'ORIGIN'},{name:'EXPORT_DATE'}
   				,{name:'DRAWBACK_NO'},{name:'GET_SHEET_TIME'},{name:'RETURN_SHEET_NO'},{name:'DRAWBACK_VALUE'},{name:'BILL_VALUE'},{name:'DRAWBACK_APPLY_DATE'}
   				,{name:'DRAWBACK_APPLY_VALUE'},{name:'REAL_DRAWBACK_VALUE'},{name:'DRAWBACK_RATE'},{name:'CHANGE_RATE'},{name:'REMARK'}
				,{name:'DRAWBACK_DATE'},{name:'DRAWBACK_ARR_DATE'}
   				];
   	var column=[new Ext.grid.RowNumberer(),
	            		{header:'部门',sortable:true,dataIndex:'DEPT_NAME',width:80}
	            		,{header:'业务类型',sortable:true,dataIndex:'TRADE_TYPE',width:80}
	            		,{header:'外部纸质合同号',sortable:true,dataIndex:'VBKD_IHREZ',width:80}
	            		,{header:'物料名称',sortable:true,dataIndex:'MATERIAL_NAME',width:80}
	            		,{header:'海关商编',sortable:true,dataIndex:'CUSTOM_BIZ_NO',width:80}
	            		,{header:'报关单号',sortable:true,dataIndex:'CUSTOM_NO',width:80}
	            		,{header:'预录入号',sortable:true,dataIndex:'PRE_WRITE_NO',width:80}
	            		,{header:'报关金额',sortable:true,dataIndex:'CUSTOM_TOTAL',width:80}
	            		,{header:'成交币别',sortable:true,dataIndex:'CURRENCY',width:80}
	            		,{header:'报关数量',sortable:true,dataIndex:'CUSTOM_AMOUNT',width:80}
	            		,{header:'报关单位',sortable:true,dataIndex:'CUSTOM_COMPANY',width:80}
	            		,{header:'成交方式',sortable:true,dataIndex:'VBKD_INCO1',width:80}
	            		,{header:'出口口岸',sortable:true,dataIndex:'EXPORT_PORT',width:80}
	            		,{header:'出口国别',sortable:true,dataIndex:'DESTINATIONS',width:80}
	            		,{header:'境内货源地',sortable:true,dataIndex:'ORIGIN',width:80}
	            		,{header:'出口日期',sortable:true,dataIndex:'EXPORT_DATE',width:80}
	            		,{header:'核销单号',sortable:true,dataIndex:'DRAWBACK_NO',width:80}
	            		,{header:'领单日期',sortable:true,dataIndex:'GET_SHEET_TIME',width:80}
	            		,{header:'回单日期',sortable:true,dataIndex:'RETURN_SHEET_NO',width:80}
	            		,{header:'核销金额',sortable:true,dataIndex:'DRAWBACK_VALUE',width:80}
	            		,{header:'核销日期',sortable:true,dataIndex:'DRAWBACK_DATE',width:80}
	            		,{header:'增值税发票金额',sortable:true,dataIndex:'BILL_VALUE',width:80}
	            		,{header:'退税申报日',sortable:true,dataIndex:'DRAWBACK_APPLY_DATE',width:80}
	            		,{header:'退税申报额',sortable:true,dataIndex:'DRAWBACK_APPLY_VALUE',width:80}
	            		,{header:'实际退税额',sortable:true,dataIndex:'REAL_DRAWBACK_VALUE',width:80}
	            		,{header:'核销到帐日',sortable:true,dataIndex:'DRAWBACK_ARR_DATE',width:80}
	            		,{header:'退税率',sortable:true,dataIndex:'DRAWBACK_RATE',width:80}
	            		,{header:'换汇比',sortable:true,dataIndex:'CHANGE_RATE',width:80}
	            		,{header:'备注',sortable:true,dataIndex:'REMARK',width:80}
					];
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'exportRController.spr?action=rtnDrawback&'+serv_param}),
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
	window.location.href('exportRController.spr?action=drawbackToExcel&'+param);
	
}
function closeProcessBar(){
	var processingHint = new MessageBoxUtil();
	processingHint.Close();
}

</script>
</body>
</html>