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
<title>进口跟踪主页</title>
</head>
<body>
<div id="queryDiv">
<form name="queryForm">
	<table width="100%" >
	<tr>
		<td align="right" width="100">查询时间从:</td>	
		<td><input type="text" name="projectTimeStart" id="projectTimeStart"/></td>	
		<td align="right" width="100">截止:</td>	
		<td><input type="text" name="projectTimeEnd" id="projectTimeEnd"/></td>	
		<td align="right" width="80">客户/供应商:</td>	
		<td><input type="text" name="relater"/></td>	
		<td align="right" width="80">项目号:</td>	
		<td><input type="text" name="projectNo"/></td>
	</tr>
	<tr>
		<td align="right">部门:</td>
		<td><div id="deptDiv"></div></td>
		<td align="right">纸质合同号:</td>
		<td><input type="text" name="paperContract"/></td>
		<td align="right">合同号:</td>
		<td><input type="text" name="contract"/></td>
		<td align="right">贸易方式:</td>
		<td>
			<select name="tradeType" width="160">
				<option value="">请选择...</option>
				<option value="1">外贸自营进口*业务</option>
				<option value="3">外贸自营进口业务</option>
				<option value="8">进料加工业务(内销原材料)</option>
				<option value="12">进料加工业务(外销原材料)</option>
				<option value="6">外贸代理进口业务</option>
				<option value="9">外贸自营进口业务_敞口</option>
			</select>

		</td>
	</tr>
	<tr>	
		<td align="right">合同组号:</td>
		<td><input type="text" name="contractGroup"/></td>
		<td align="right">SAP订单号:</td>
		<td><input type="text" name="sapOrderNo"/></td>
		<td align="right">明细项:</td>
		<td>
			<select name="detailItem" width="160">
				<option value="">请选择...</option>
				<option value="1">采购收货</option>
				<option value="2">信用证信息表</option>
				<option value="3">付款明细</option>
				<option value="4">信用证到单一览表</option>
				<option value="5">托收到单一览表</option>
				<option value="6">T/T到单一览表</option>
				<option value="7">销售发货</option>
				<option value="8">收款明细</option>
				<option value="9">开票</option>
				<option value="10">进口核销统计</option>
				<option value="">--导出--</option>
				<option value="a">采购收货</option>
				<option value="b">信用证信息表</option>
				<option value="c">付款明细</option>
				<option value="d">信用证到单一览表(含国内证)</option>
				<option value="e">托收到单一览表</option>
				<option value="f">T/T到单一览表</option>
				<option value="g">销售发货</option>
				<option value="h">收款明细</option>
				<option value="i">开票</option>
				<option value="j">进口核销统计</option>
			</select>
		</td>
		<td align="right">立项状态:</td>
		<td><div id="projectStateDiv"></div></td>
	</tr>
	<tr>
		<td colspan="8" align="center">
			<input type="button" name="" value="查找" onclick="find()"/>
			<input type="reset" name="" value="清空"/>
			<input type="button" value="导出" onclick="doExport()"/>
		</td>
	</tr>
	</table>
</form>
</div>
<div id='grid_div_sale'></div>
<div id='grid_div_purchase'></div>
<script type="text/javascript">
document.onkeydown = function(){if (event.keyCode == 13){find();}}
var store_sale;
var store_purchaes;
var grid_sale;
var grid_purchaes;
var loading = false;
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	//--------------
	var field_sale=[{name:'TRADE_TYPE'},{name:'PROJECT_NO'},{name:'APPROVED_TIME'},{name:'OLD_CONTRACT_NO'},{name:'SALE_CONTRACT_STAUS'}
	,{name:'CONTRACT_NO'},{name:'RELATER'},{name:'VBAP_MATNR'},{name:'VBAP_ARKTX'},{name:'CONTRACT_NO'},{name:'SAP_ORDER_NO'}
	,{name:'OLD_CONTRACT_NO'},{name:'KUAGV_KUNNR'},{name:'KUAGV_KUNNR_NAME'},{name:'VBAP_ZMENG'},{name:'VBAP_VRKME'},{name:'VBAK_WAERK'}
	,{name:'KONV_KBETR'},{name:'SAME_CONTRACT_MATERIEL_CT'},{name:'SUM_TOTAL'},{name:'VBAP_KPEIN'},{name:'SALEROWTOTAL'}
	,{name:'ORDER_TOTAL'},{name:'VBKD_ZTERM'},{name:'VBKD_ZLSCH'},{name:'PRICE_TOTAL'},{name:'CONTRACT_SALES_ID'}
	,{name:'RECEIPT_TOTAL'},{name:'REAL_RECEIPT_TOTAL'},{name:'RECEIPT_BALANCE'}
	,{name:'CONTRACT_GROUP_NO'},{name:'DEPT_NAME'},{name:'CONTRACT_DATE'},{name:'SHIPMENT_PORT'},{name:'SHIPMENT_DATE'}
	,{name:'LEFTCT'},{name:'RECEIPT_TOTAL'},{name:'CUSTOMER_LINK_MAN'}
	];
    
	var column_sale=[new Ext.grid.RowNumberer(),
	                 	{header:'部门',dataIndex:'DEPT_NAME'},
	            		{header:'业务类型',sortable:true,dataIndex:'TRADE_TYPE',width:120},
	            		{header:'立项号',sortable:true,dataIndex:'PROJECT_NO',renderer:projectNoRenderS},
	            		{header:'买方/委托方',sortable:true,dataIndex:'CUSTOMER_LINK_MAN',width:150},
	            		{header:'合同组号',sortable:true,dataIndex:'CONTRACT_GROUP_NO',renderer:contractGroupNoRenderS},
	            		{header:'销售合同日期',sortable:true,dataIndex:'CONTRACT_DATE'},
	            		{header:'销售合同号',sortable:true,dataIndex:'CONTRACT_NO',renderer:contractNoRenderS},
	            		{header:'外部纸质合同号',sortable:true,dataIndex:'OLD_CONTRACT_NO'},
	            		{header:'销售订单号',sortable:true,dataIndex:'SAP_ORDER_NO'},
	            		{header:'客户名称',sortable:true,dataIndex:'KUAGV_KUNNR_NAME'},
	            		{header:'物料名称',sortable:true,dataIndex:'VBAP_ARKTX'},
	            		{header:'合同数量',sortable:true,dataIndex:'VBAP_ZMENG',width:150},
	            		{header:'单位',sortable:true,dataIndex:'VBAP_VRKME'},
	            		{header:'合同币别',sortable:true,dataIndex:'VBAK_WAERK'},
	            		{header:'单价',sortable:true,dataIndex:'KONV_KBETR'},
	            		{header:'条件定价单位',sortable:true,dataIndex:'VBAP_KPEIN'},
	            		{header:'单行物料总金额',sortable:true,dataIndex:'SALEROWTOTAL'},
	            		{header:'合同总金额',sortable:true,dataIndex:'ORDER_TOTAL'},
	            		{header:'合同交货日期',sortable:true,dataIndex:'SHIPMENT_DATE'},
	            		{header:'合同交货地点',sortable:true,dataIndex:'SHIPMENT_PORT'},
	            		{header:'收款条件',sortable:true,dataIndex:'VBKD_ZTERM'},
	            		{header:'收款方式',sortable:true,dataIndex:'VBKD_ZLSCH'},
	            		{header:'累计出仓数量',sortable:true,dataIndex:'SAME_CONTRACT_MATERIEL_CT'},
	            		{header:'累计出仓金额',sortable:true,dataIndex:'SUM_TOTAL'},
	            		{header:'合同未执行数量',sortable:true,dataIndex:'LEFTCT'},
	            		{header:'累计应收金额',sortable:true,dataIndex:'RECEIPT_TOTAL'},
	            		{header:'累计已收金额',sortable:true,dataIndex:'REAL_RECEIPT_TOTAL'},
	            		{header:'应收余额',sortable:true,dataIndex:'RECEIPT_BALANCE'},
	            		{header:'销售合同状态',sortable:true,dataIndex:'SALE_CONTRACT_STAUS'}

  		];
	function projectNoRenderS(value,metadata,record){
		if(value!=null)
			return "<a href='#' onclick='colClick(\"s\",\"PROJECT_NO\",\""+value+"\")'>"+value+"</a>";
		else
			return '';
	}
	function contractNoRenderS(value,metadata,record){
		if(value!=null)
			return "<a href='#' onclick='colClick(\"s\",\"CONTRACT_NO\",\""+value+"\")'>"+value+"</a>";
		else
			return '';
	}
	function contractGroupNoRenderS(value,metadata,record){
		if(value!=null)
		 	return "<a href='#' onclick='colClick(\"s\",\"CONTRACT_GROUP_NO\",\""+value+"\")'>"+value+"</a>";
		else
			return '';
	}
	store_sale = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:''}),
		reader: new Ext.data.JsonReader({
					root: 'root',
					totalProperty: 'totalProperty'
				},
				field_sale
				)
	});
	var cm_sale = new Ext.grid.ColumnModel(
			column_sale
		);
	var pagingBar_sale = new Ext.PagingToolbar({
	         	displayInfo:true,
	         	emptyMsg:'没有数据显示',
	         	displayMsg:"显示从{0}条数据到{1}条数据，共{2}条数据",
	         	store:store_sale,
	         	pageSize:20
	});
	//--------------
	var field_purchase=[{name:'TRADE_TYPE'},{name:'PROJECT_NO'},{name:'PROJECT_NAME'},{name:'APPROVED_TIME'},{name:'OLD_CONTRACT_NO'},{name:'PURCHASE_CONTRACT_STAUS'}
						,{name:'CONTRACT_NO'},{name:'RELATER'},{name:'EKKO_INCO1'},{name:'SHIPMENT_DATE'},{name:'SHIPMENT_PORT'}
	                    ,{name:'SAP_ORDER_NO'},{name:'EKKO_BEDAT'},{name:'EKKO_LIFNR'},{name:'DESTINATION_PORT'},{name:'EKKO_ZTERM'}
						,{name:'EKKO_LIFNR_NAME'},{name:'EKPO_MATNR'},{name:'EKPO_TXZ01'},{name:'EKPO_MENGE'},{name:'EKPO_MEINS'}
						,{name:'EKPO_BPRME'},{name:'EKPO_NETPR'},{name:'EKPO_PEINH'},{name:'PURCHASEROWTOTAL'},{name:'TOTAL'}
						,{name:'EKET_EINDT'},{name:'FACTORY_LOCAL'},{name:'EKKO_ZTERM'}
						,{name:'GROUP_MATERIAL_CT'},{name:'SUM_QUANTITY'},{name:'SUM_TOTAL'}
						,{name:'CONTRACT_PURCHASE_ID'},{name:'PURCHASEROWTOTAL'},{name:'PURCHSE_AMOUNT'}
						,{name:'CREDIT_NO'},{name:'LEFTCT'},{name:'CONTRACT_GROUP_NO'},{name:'CUSTOMER_LINK_MAN'}
						,{name:'DEPT_NAME'},{name:'PAY_WAY'},{name:'PAY_TOTAL'},{name:'REAL_PAY_TOTAL'},{name:'PAY_BALANCE'}
					];
	
	var column_purchase=[new Ext.grid.RowNumberer(),
	                     	{header:'部门',dataIndex:'DEPT_NAME'},
		            		{header:'业务类型',sortable:true,dataIndex:'TRADE_TYPE',width:120},
		            		{header:'立项号',sortable:true,dataIndex:'PROJECT_NO',renderer:projectNoRenderP},
		            		//{header:'卖方/委托方',sortable:true,dataIndex:'PROVIDER_LINK_MAN',width:150},
		            		{header:'买方/委托方',sortable:true,dataIndex:'CUSTOMER_LINK_MAN',width:150},
		            		{header:'合同组号',sortable:true,dataIndex:'CONTRACT_GROUP_NO',renderer:contractGroupNoRenderP},
		            		{header:'采购合同日期',sortable:true,dataIndex:'EKKO_BEDAT'},
		            		{header:'采购合同号',sortable:true,dataIndex:'CONTRACT_NO',renderer:contractNoRenderP},
		            		{header:'外部纸质合同号',sortable:true,dataIndex:'OLD_CONTRACT_NO'},
		            		{header:'采购订单号',sortable:true,dataIndex:'SAP_ORDER_NO'},
		            		{header:'供应商名称',sortable:true,dataIndex:'EKKO_LIFNR_NAME'},
		            		{header:'物料名称',sortable:true,dataIndex:'EKPO_TXZ01'},
		            		{header:'合同数量',sortable:true,dataIndex:'EKPO_MENGE'},
		            		{header:'单位',sortable:true,dataIndex:'EKPO_MEINS'},
		            		{header:'合同币别',sortable:true,dataIndex:'EKPO_BPRME'},
		            		{header:'单价',sortable:true,dataIndex:'EKPO_NETPR'},
		            		{header:'条件定价单位',sortable:true,dataIndex:'EKPO_PEINH'},
		            		{header:'单行物料总金额',sortable:true,dataIndex:'PURCHASEROWTOTAL'},
		            		{header:'合同总金额',sortable:true,dataIndex:'TOTAL'},
		            		{header:'价格术语',sortable:true,dataIndex:'EKKO_INCO1'},
		            		{header:'装运期限',sortable:true,dataIndex:'SHIPMENT_DATE'},
		            		{header:'装运港',sortable:true,dataIndex:'SHIPMENT_PORT'},
		            		{header:'目的港',sortable:true,dataIndex:'DESTINATION_PORT'},//字典表
		            		{header:'付汇条件',sortable:true,dataIndex:'EKKO_ZTERM'},
		            		{header:'付汇方式',sortable:true,dataIndex:'PAY_WAY'},
		            		{header:'信用证号',sortable:true,dataIndex:'CREDIT_NO'},
		            		{header:'累计进仓数量',sortable:true,dataIndex:'SUM_QUANTITY'},
		            		{header:'累计进仓金额',sortable:true,dataIndex:'SUM_TOTAL'},
		            		{header:'合同未执行数量',sortable:true,dataIndex:'LEFTCT'},
		            		{header:'累计应付金额',sortable:true,dataIndex:'PAY_TOTAL'},
		            		{header:'累计已付金额',sortable:true,dataIndex:'REAL_PAY_TOTAL'},
		            		{header:'应付余额',sortable:true,dataIndex:'PAY_BALANCE'},
		            		{header:'采购合同状态',sortable:true,dataIndex:'PURCHASE_CONTRACT_STAUS'}
	            	];	
	function projectNoRenderP(value,metadata,record){
		if(value!=null)
			return "<a href='#' onclick='colClick(\"p\",\"PROJECT_NO\",\""+value+"\")'>"+value+"</a>";
		else
			return '';
	}
	function contractNoRenderP(value,metadata,record){
		if(value!=null)
			return "<a href='#' onclick='colClick(\"p\",\"CONTRACT_NO\",\""+value+"\")'>"+value+"</a>";
		else
			return '';
	}
	function contractGroupNoRenderP(value,metadata,record){
		if(value!=null)
			return "<a href='#' onclick='colClick(\"p\",\"CONTRACT_GROUP_NO\",\""+value+"\")'>"+value+"</a>";
		else
			return '';
	}
	
	store_purchase = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({url:''}),
		reader: new Ext.data.JsonReader({
					root: 'root',
					totalProperty: 'totalProperty'
				},
				field_purchase
				)
	});
	var cm_purchase = new Ext.grid.ColumnModel(
			column_purchase
		);
	var pagingBar_purchase = new Ext.PagingToolbar({
	         	displayInfo:true,
	         	emptyMsg:'没有数据显示',
	         	displayMsg:"显示从{0}条数据到{1}条数据，共{2}条数据",
	         	store:store_purchase,
	         	pageSize:20
	});
	var grid_sale = new Ext.grid.GridPanel({
  		applyTo:'grid_div_sale',
		ds:store_sale,
		cm:cm_sale,
		bbar:pagingBar_sale,
		border:true,
		loadMask:true,
		autoScroll:true,
		height:200,
		frame:true,
	    iconCls:'icon-grid',
        autowidth:true,
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
//	grid_sale.getSelectionModel().on('rowselect', function(sm, rowIdx, r){
//		gridClick('sale',r);
//	});
	var grid_purchase = new Ext.grid.GridPanel({
  		applyTo:'grid_div_purchase',
		ds:store_purchase,
		cm:cm_purchase,
		bbar:pagingBar_purchase,
		border:true,
		loadMask:true,
		autoScroll:true,
		height:200,
		frame:true,
	    iconCls:'icon-grid',
        autowidth:true,
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
//	grid_purchase.getSelectionModel().on('rowselect', function(sm, rowIdx, r){
//		gridClick('purchase',r);
//	});
/*		
	var main=new Ext.form.FormPanel({
		renderTo:document.body,
		frame:true,
		baseCls:'x-plain',
		autoHeight:true,
		autoWidth:true,
		bodyStyle:'padding: 10px 10px 0 10px;',
		items:[{
			contentEl:'queryDiv'
		},{
			title:'销售合同',
			contentEl:'grid_div_sale',
			height:240
		},{
			title:'采购合同',
			contentEl:'grid_div_purchase',
			height:240
		}]
	});
	*/
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			collapsible: true,
			contentEl:'queryDiv'
		},{
			title:'销售',
			region:'center',
			layout:'fit',
		    split:true,
			collapsible: true,
			height:200,
			items:[grid_sale]
		},{
			title:'采购',
			region:'south',
			layout:'fit',
		    split:true,
			collapsible: true,
			height:200,
			items:[grid_purchase]
		}]
	});
    var projectTimeStart = new Ext.form.DateField({
   		format:'Ymd',
		width: 160,
		applyTo:'projectTimeStart'
   	});
    var projectTimeEnd = new Ext.form.DateField({
   		format:'Ymd',
		width: 160,
		applyTo:'projectTimeEnd'
   	});
	store_sale.on('load',function(){
		if(!loading)
			return;
		else
			loading = false;
		var params = Form.serialize('queryForm');
		params+='&deptId='+selectId_deptDiv;
		store_purchase.proxy=new Ext.data.HttpProxy({url:'importController.spr?action=rtnPurchase&'+params});
		store_purchase.load({params:{start:0, limit:20}});
		});
	store_purchase.on('load',function(){
		closeProcessBar();
	});
});	
function viewInfo(title,url){
	//var records = tempGrid.selModel.getSelections();
	top.ExtModalWindowUtil.show(title,url,'','',{width:900,height:500});
}

function colClick(contractType,colName,colValue){
	var id = $('detailItem').value;
	var base='importController.spr?action=';
	var url;
	var title;
	switch (id) {
		case '1':
			url='toReceiptGoods&'+colName+'='+colValue;
			title='采购收货信息';
			break;
		case '2':
			url='toCreditView&'+colName+'='+colValue;
			title='信用证信息表';
			break;
		case '3':
			url='toPayDetail&'+colName+'='+colValue;
			title='付款明细';	
			break;
		case '4':
			url='toCreditPicklistView&'+colName+'='+colValue;
			title='信用证到单一览表';	
			break;
		case '5':
			url='toDPDAPicklistView&'+colName+'='+colValue;
			title='托收到单一览表';	
			break;
		case '6':
			url='toTTPicklistView&'+colName+'='+colValue;
			title='T/T到单一览表';	
			break;
		case '7':
			url='toShipGoods&'+colName+'='+colValue;
			title='销售发货';	
			break;
		case '8':
			url='toReceiptDetail&'+colName+'='+colValue;
			title='收款明细';	
			break;
		case '9':
			url='toOpenBill&'+colName+'='+colValue;
			title='开票';	
			break;
		case '10':
			url='toDrawback&'+colName+'='+colValue;
			title ='进口核销统计';
			break;			
	}
	if(contractType=='s' &&(id=='7' || id=='8' || id=='9')){
		viewInfo(title,base + url);
		return;
	}
	if(contractType=='p' &&(id=='1' || id=='2'||id=='3'||id=='4'||id=='5'||id=='6'||id=='10')){
		viewInfo(title,base + url);
	}
}
function find(){
	loading = true;
	var ProcessingHint = new MessageBoxUtil();
	ProcessingHint.waitShow();
	var params = Form.serialize('queryForm');
	params+='&deptId='+selectId_deptDiv;
	store_sale.proxy=new Ext.data.HttpProxy({url:'importController.spr?action=rtnSale&'+params});
	store_sale.load({params:{start:0, limit:20}});
}    			
function doExport(){
	var ProcessingHint = new MessageBoxUtil();
	ProcessingHint.waitShow();
	setTimeout("closeProcessBar()",5000);
	var param = Form.serialize('queryForm');
	param +='&deptId='+selectId_deptDiv;
	var item = $('detailItem').value;
	var href = 'importController.spr?action=exportToExcel&'+param;
	switch (item) {
		case 'a':
			href='importController.spr?action=receiptGoodsToExcel&'+param;
			break; 
		case 'b':
			href='importController.spr?action=creditViewToExcel&'+param;
			break; 
		case 'c'://付款明细
			href='importController.spr?action=payDetailToExcel&'+param;
			break; 
		case 'd':
			href='importController.spr?action=creditPicklistViewToExcel&'+param;
			break; 
		case 'e':
			href='importController.spr?action=DPDAPicklistViewToExcel&'+param;
			break; 
		case 'f':
			href='importController.spr?action=TTPicklistViewToExcel&'+param;
			break; 
		case 'g':
			href='importController.spr?action=shipGoodsToExcel&'+param;
			break; 
		case 'h':
			href='importController.spr?action=receiptDetailToExcel&'+param;
			break; 
		case 'i':
			href='importController.spr?action=openBillToExcel&'+param;
			break; 
		case 'j':
			href='importController.spr?action=drawbackToExcel&'+param;
			break;
			
	}
	window.location.href(href);
}
function closeProcessBar(){
	var processingHint = new MessageBoxUtil();
	processingHint.Close();
}

</script>
<fiscxd:dept divId="deptDiv" rootTitle="部门" width="160" isMutilSelect="true"></fiscxd:dept>
<fiscxd:dictionary divId="projectStateDiv" fieldName="projectState" dictionaryName="BM_PROJECT_STATE"></fiscxd:dictionary>
</body>
</html>