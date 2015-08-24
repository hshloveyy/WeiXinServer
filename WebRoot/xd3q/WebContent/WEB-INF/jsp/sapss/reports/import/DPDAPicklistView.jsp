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
<title>托收(D/P,D/A)到单一览表</title>
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
   	var field=[{name:'DEPT_NAME'},{name:'TRADE_TYPE'},{name:'PICK_LIST_NO'},{name:'CONTRACT_NO'},{name:'EKKO_IHREZ'},{name:'GOODS_NAME'}
		,{name:'EKKO_LIFNR_NAME'},{name:'TOTAL'},{name:'COLLECTION_BANK'},{name:'COLLECTION_BANK_NO'},{name:'COLLECTION_DATE'},{name:'PICK_LIST_REC_DATE'}
		,{name:'EKPO_MENGE'},{name:'EKPO_MEINS'},{name:'EKPO_BPRME'},{name:'TOTAL_VALUE'},{name:'PAY_DATE'},{name:'NPAY_DATE'},{name:'DP_OR_DA'},{name:'PICK_STATE'}];
	var column=[new Ext.grid.RowNumberer(),
        		{header:'部门',sortable:true,dataIndex:'DEPT_NAME',width:80}
        		,{header:'业务类型',sortable:true,dataIndex:'TRADE_TYPE',width:80}
        		,{header:'到单号',sortable:true,dataIndex:'PICK_LIST_NO',width:150}
        		,{header:'采购合同号',sortable:true,dataIndex:'CONTRACT_NO',width:80}
        		,{header:'外部纸质合同号',sortable:true,dataIndex:'EKKO_IHREZ',width:80}
        		,{header:'货物品名',sortable:true,dataIndex:'GOODS_NAME',width:160}
        		,{header:'供应商名称',sortable:true,dataIndex:'EKKO_LIFNR_NAME',width:80}
        		,{header:'合同金额',sortable:true,dataIndex:'TOTAL',width:80}
        		,{header:'代收行',sortable:true,dataIndex:'COLLECTION_BANK',width:80}
        		,{header:'代收行号',sortable:true,dataIndex:'COLLECTION_BANK_NO',width:80}
        		,{header:'代收期限',sortable:true,dataIndex:'COLLECTION_DATE',width:80}
        		,{header:'到单日期',sortable:true,dataIndex:'PICK_LIST_REC_DATE',width:80}
        		,{header:'到单数量',sortable:true,dataIndex:'EKPO_MENGE',width:80}
        		,{header:'单位',sortable:true,dataIndex:'EKPO_MEINS',width:80}
        		,{header:'币别',sortable:true,dataIndex:'EKPO_BPRME',width:80}
        		,{header:'到单金额',sortable:true,dataIndex:'TOTAL_VALUE',width:80}
        		,{header:'即期付款日/远期承兑到期日',sortable:true,dataIndex:'PAY_DATE',width:80}
        		,{header:'押汇到期日',sortable:true,dataIndex:'NPAY_DATE',width:80}
        		,{header:'DP/DA说明',sortable:true,dataIndex:'DP_OR_DA',width:80}
        		,{header:'审批状态',sortable:true,dataIndex:'PICK_STATE',width:80}
			];
  		
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'importController.spr?action=rtnDPDAPicklistView&'+serv_param}),
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
	window.location.href('importController.spr?action=DPDAPicklistViewToExcel&'+param);
	
}
function closeProcessBar(){
	var processingHint = new MessageBoxUtil();
	processingHint.Close();
}

</script>
</body>
</html>