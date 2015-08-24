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
<title>资金需求查询</title>
</head>
<body>
<div id='div_toolbar'>
	<form name="queryForm">
		<table width="100%">
			<tr>
				<td align="right">年度:</td>
				<td>
					<select name="year"> 
						<option value="2009">2009年</option>
						<option value="2010">2010年</option>
						<option value="2011">2011年</option>
						<option value="2012">2012年</option>
					</select>
				</td>
				<td align="right">月份:</td>
				<td>
					<select name="month">
						<option value="1">01月</option>
						<option value="2">02月</option>
						<option value="3">03月</option>
						<option value="4">04月</option>
						<option value="5">05月</option>
						<option value="6">06月</option>
						<option value="7">07月</option>
						<option value="8">08月</option>
						<option value="9">09月</option>
						<option value="10">10月</option>
						<option value="11">11月</option>
						<option value="12">12月</option>
					</select>
				</td>
				<td align="right">明细表:</td>
				<td>
					<select id="detailItem">
						<option value="">请选择...</option>
						<option value="1">到期信用证</option>
						<option value="2">到期银行承兑汇票</option>
						<option value="3">到期国内信用证</option>
						<option value="4">到期人民币短期借款</option>
						<option value="5">到期人民币长期借款</option>
						<option value="6">到期外币短期借款</option>
						<option value="7">到期进口押汇</option>
						<option value="8">到期出口押汇</option>
					</select>
				</td>
				<td>
					<input type="button" id="btn1" value="查询" onClick="doFind()"/>
					<input type="button" id="btn2" value="导出" onClick="doExport()"/>
				</td>
			</tr>
		</table>
	
	</form>
</div>
<div id='grid_div'></div>
<script type="text/javascript">
document.onkeydown = function(){if (event.keyCode == 13){doFind();}}
var store;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	var field=[{name:'date'},{name:'payment_value'},{name:'bankAcceptance_value'},{name:'homeCredit_value'},
   	       	{name:'cnyShortLoan_value'},{name:'cnyLongLoan_value'},{name:'foreignLoan_value'},{name:'draft_value'},{name:'draftOut_value'},{name:'day_sum'},
   	       	{name:'bapi_receive'},{name:'bapi_pay'}];
   	var column=[new Ext.grid.RowNumberer(),
	            		{header:'日期',sortable:true,dataIndex:'date',width:80}
	            		,{header:'到期信用证',sortable:true,dataIndex:'payment_value',width:100}
	            		,{header:'到期银行承兑汇票',sortable:true,dataIndex:'bankAcceptance_value',width:120}
	            		,{header:'到期国内信用证',sortable:true,dataIndex:'homeCredit_value',width:100}
	            		,{header:'到期人民币短期借款',sortable:true,dataIndex:'cnyShortLoan_value',width:120}
	            		,{header:'到期人民币长期借款',sortable:true,dataIndex:'cnyLongLoan_value',width:130}
	            		,{header:'到期外币短期借款',sortable:true,dataIndex:'foreignLoan_value',width:120}
						,{header:'到期进口押汇',sortable:true,dataIndex:'draft_value',width:100}
						,{header:'到期出口押汇',sortable:true,dataIndex:'draftOut_value',width:100}
	            		,{header:'日合计',sortable:true,dataIndex:'day_sum',width:100}
	            		,{header:'应付款',sortable:true,dataIndex:'bapi_receive',width:100}
	            		,{header:'应收款',sortable:true,dataIndex:'bapi_pay',width:100}
					];
   		
	store = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:''}),
		reader: new Ext.data.JsonReader({
					root: 'root',
					totalProperty: 'totalProperty'
				},
				field
				)
	});
	
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
	grid.getSelectionModel().on('rowselect', function(sm, rowIdx, r){
		rowselect(r);
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
	store.on('load',function(){
		$('btn1').disabled=false;
		$('btn2').disabled=false;
	});
});
function doFind(){
	$('btn1').disabled=true;
	$('btn2').disabled=true;
	var params = Form.serialize('queryForm');
	store.proxy=new Ext.data.HttpProxy({url:'fundsRequireQueryController.spr?action=rtnData&'+params});
	store.load();
}
function viewInfo(title,url){
	//var records = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show(title,url,'','',{width:900,height:500});
}
function rowselect(record){
	var id = $('detailItem').value;
	var base='fundsRequireQueryController.spr?action=';
	var url;
	var title;
	var val=false;
	switch (id) {
		case '1':
			val = record.data.payment_value==null?false:true;
			url='showImportPaymentInfo&date='+record.data.date;
			title='到期信用证';
			break;
		case '2':
			val = record.data.bankAcceptance_value==null?false:true;
			url='showBankAcceptancInfo&date='+record.data.date;
			title='到期银行承兑汇票';
			break;
		case '3':
			val = record.data.homeCredit_value==null?false:true;
			url='showHomeCreditInfo&date='+record.data.date;
			title='到期国内信用证';
			break;
		case '4':
			val = record.data.cnyShortLoan_value==null?false:true;
			url='showTableInfo&date='+record.data.date+'&classify=4';
			title='到期人民币短期借款';	
			break;
		case '5':
			val = record.data.cnyLongLoan_value==null?false:true;
			url='showTableInfo&date='+record.data.date+'&classify=5';
			title='到期人民币长期借款';	
			break;
		case '6':
			val = record.data.foreignLoan_value==null?false:true;
			url='showForeignShortLoanInfo&date='+record.data.date;
			title='到期外币短期借款';	
			break;
		case '7':
			val = record.data.draft_value==null?false:true;
			url='showDraftInfo&date='+record.data.date;
			title='到期进口押汇';	
			break;
		case '8':
			val = record.data.draftOut_value==null?false:true;
			url='showDraftOutInfo&date='+record.data.date;
			title='到期出口押汇';	
			break;
	}
	if(val)
		val = record.data.date=='合计'?false:true;
	if(val)
		viewInfo(title,base + url);
}
function doClose(){
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
}
function doExport(){
	var ProcessingHint = new MessageBoxUtil();
	ProcessingHint.waitShow();
	setTimeout("closeProcessBar()",2000);
	var params = Form.serialize('queryForm');
	window.location.href('fundsRequireQueryController.spr?action=toExcel&'+params);
	
}
function closeProcessBar(){
	var processingHint = new MessageBoxUtil();
	processingHint.Close();
}

</script>
</body>
</html>