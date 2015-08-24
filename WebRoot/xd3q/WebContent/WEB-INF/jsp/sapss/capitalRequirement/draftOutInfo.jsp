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
<title>押汇详细信息</title>
</head>
<body>
<div id='div_toolbar'></div>
<div id='grid_div'></div>
<script type="text/javascript">
var store;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	var field=[{name:'BILLPURID'},{name:'MATURITY'},{name:'BILLPUR_NO'},{name:'BMONEY'},{name:'CURRENCY'},{name:'RMONEY'},{name:'SUM_VALUE'}];
   	var column=[new Ext.grid.RowNumberer()
						,{header:'操作',sortable:true,dataIndex:'BILLPURID',renderer:paymentIdRender}
	            		,{header:'到期日',sortable:true,dataIndex:'MATURITY',width:80}
	            		,{header:'单号',sortable:true,dataIndex:'BILLPUR_NO',width:100}
	            		,{header:'押汇金额',sortable:true,dataIndex:'BMONEY',width:80}
	            		,{header:'币别',sortable:true,dataIndex:'CURRENCY',width:80}
						,{header:'已还金额',sortable:true,dataIndex:'RMONEY',width:80}
						,{header:'未还金额',sortable:true,dataIndex:'SUM_VALUE',width:80}
					];
   	function paymentIdRender(value,metadata,record){
   		if(value!=null)
   			return "<a href='#' onclick='colClick(\""+value+"\")'>查看</a>";
   	}
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:'fundsRequireQueryController.spr?action=rtnDraftOutInfo&date=${date}'}),
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
	
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:'center',
			layout:'fit',
		    split:true,
		    height:500,
			collapsible: true,
			items:[grid]
		}]
	});

});
function colClick(paymentId){
	top.ExtModalWindowUtil.show("押汇详细","xdss3/billpurchased/billPurchasedController.spr?action=_view&billpurid="+paymentId,
			'','',{width:900,height:600});
}
function doClose(){
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
}
function doExport(){
	var ProcessingHint = new MessageBoxUtil();
	ProcessingHint.waitShow();
	setTimeout("closeProcessBar()",2000);
	var param = serv_param;
	window.location.href('homeTradeController.spr?action=payMoneyToExcel&'+param);
	
}
function closeProcessBar(){
	var processingHint = new MessageBoxUtil();
	processingHint.Close();
}

</script>
</body>
</html>