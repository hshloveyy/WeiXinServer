<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购开票申请</title>
<style type="text/css">
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
.reset{
	background-image:url(images/fam/refresh.gif) !important;
}
.find{
	background-image:url(images/fam/find.png) !important;
}
</style>
</head>
<body>
<div id="div_northForm">
<form action="" id="findpurchaseBillFrom" name="findpurchaseBillFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1"
	bordercolor="#6699FF" class="datatable">
	<tr>
		<td align="right">部门名称：</td>
		<td>
		<div id="dept"></div>
		</td>
		<td align="right">开票申请单号：</td>
		<td><input type="text" id="purchaseBillNo" name="purchaseBillNo"
			value=""></input></td>
		<td align="right">纸质合同号：</td>
		<td><input type="text" id="paperContractNo"
			name="paperContractNo" value=""></input></td>
	</tr>
	<tr>
		<td align="right">合同编码：</td>
		<td><input type="text" id="contractNo" name="contractNo" value=""></input>
		</td>
		<td align="right">SAP订单号：</td>
		<td><input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
		</td>
		<td align="right">身份证号：</td>
		<td><input type="text" id="cardNo" name="cardNo" value=""></input></td>
	</tr>
	<tr>
		<td align="right">供应商编码：</td>
		<td><input type="text" id="billToParty" name="billToParty" value=""/></td>
		<td align="right">供应商名称：</td>
		<td><input type="text" id="billToPartyName" name="billToPartyName" value=""/></td>
		<td align="right">申请人：</td>
		<td><input type="text" id="creator" name="creator" value=""/></td>
	</tr>
	<tr>
		<td align="right">申报日期从：</td>
		<td>
		<div id="sDateDiv"></div>
		</td>
		<td align="right">到：</td>
		<td>
		<div id="eDateDiv"></div>
		</td>
		<td align="center"></td>

		<td><input type="button" value="查找"
			onclick="findpurchaseBill()"></input> <input type="reset" value="清空"></input>
		</td>
	</tr>
</table>
</form>
</div>

<div id="div_center"></div>

<div id="div_south"></div>
</body>
</html>
<script type="text/javascript"><!--
document.onkeydown = function(){if (event.keyCode == 13){findpurchaseBill();}}
//贸易类型
//var tradeType = '${tradeType}';

var strOperType = '0';

var purchaseBillRecord;
strpurchaseBillTitle = '采购开票申请';

var purchaseBillgrid;		//开票申请单信息列表
var purchaseBillds;

//BillApply 查找按钮的单击事件
function findpurchaseBill(){
	var sDate= Ext.getDom("sDate").value;
    var eDate= Ext.getDom("eDate").value;
    
	var param = Form.serialize('findpurchaseBillFrom');
	var requestUrl = 'purchaseBillController.spr?action=queryPurchaseBill&' + param;
	//requestUrl = requestUrl + "&tradeType=" + tradeType;
	//requestUrl = requestUrl + "&billType=1";
	requestUrl = requestUrl + '&deptId=' + selectId_dept;		
	purchaseBillds.proxy= new Ext.data.HttpProxy({url:requestUrl});
	purchaseBillds.load({params:{start:0, limit:10},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	
	//增加开票申请单的回调函数
    function funAddpurchaseBillCallBack(){
    	purchaseBillds.reload();
    }

	
	var purchaseBillPlant = Ext.data.Record.create([
		{name:'PURCHASE_BILL_ID'},  				//开票申请ID
		{name:'PURCHASE_BILL_NO'},  				//开票申请单号
		{name:'CONTRACT_PURCHASE_ID'},
		{name:'CONTRACT_PURCHASE_NO'},
		{name:'TAX_NO'},
		{name:'BILL_TO_PARTY'},
		{name:'BILL_TO_PARTY_NAME'},
		{name:'SAP_ORDER_NO'},
		{name:'BILL_TIME'},
		{name:'QUANTITY_TOTAL'},
		{name:'PRICE_TOTAL'},
		{name:'CMD'},
	    {name:'DEPT_ID'},
	    {name:'EXAMINE_STATE'},
	    {name:'EXAMINE_STATE_D_EXAMINE_STATE'},
	    {name:'APPLY_TIME'},
		{name:'APPROVED_TIME'},
	    {name:'IS_AVAILABLE'},
		{name:'CREATOR_TIME'},
		{name:'CREATOR'},
		{name:'DEPT_NAME'},
		{name:'purchaseBillINFO'}
	]);

	purchaseBillds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'purchaseBillController.spr?action=queryPurchaseBill'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},purchaseBillPlant)
    });
    
    var purchaseBillsm = new Ext.grid.CheckboxSelectionModel();    
    var purchaseBillcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		purchaseBillsm,
		　 {header: '部门代码',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_ID',
           hidden:true
           },           
           {
               header: '操作',
               width: 100,
               sortable: true,
               dataIndex: 'purchaseBillINFO',
               renderer: operaRD
               },
           {
               header: '状态',
               width: 100,
               sortable: true,
               dataIndex: 'EXAMINE_STATE_D_EXAMINE_STATE'
               },                       
  		　 {header: '开票申请单编号',
           width: 100,
           sortable: true,
           dataIndex: 'PURCHASE_BILL_NO'
           },
           {header: '供应商编码',
           width: 100,
           sortable: true,
           dataIndex: 'BILL_TO_PARTY'
           },
           {header: '供应商名称',
           width: 150,
           sortable: true,
           dataIndex: 'BILL_TO_PARTY_NAME'
           },
           {header: '合同编码',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_PURCHASE_NO'
           },
           {
           header: 'SAP订单号',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_ORDER_NO'
           },
           {
           header: '申报时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_TIME'
           }, 
           {
           header: '审批时间',
           width: 120,
           sortable: true,
           dataIndex: 'APPROVED_TIME'
           },  
           {
           header: '创建时间',
           width: 120,
           sortable: true,
           dataIndex: 'CREATOR_TIME'
           },                                
           {
           header: '创建人ID',
           width: 100,
           sortable: true,
           dataIndex: 'CREATOR',
           hidden:true           
           },
           {
           header: '创建人',
           width: 100,
           sortable: true,
           dataIndex: 'REAL_NAME'
           },
      		{
           header: '状态',
           width: 100,
           sortable: true,
           dataIndex: 'EXAMINE_STATE',
           hidden: true
           }
    ]);
    purchaseBillcm.defaultSortable = true;
    
    var purchaseBillbbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:purchaseBillds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    purchaseBillgrid = new Ext.grid.EditorGridPanel({
    	id:'purchaseBillgrid',
        ds: purchaseBillds,
        cm: purchaseBillcm,
        sm: purchaseBillsm,
        bbar: purchaseBillbbar,
       // tbar: purchaseBilltbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    purchaseBillds.load({params:{start:0, limit:10},arg:[]});
    
 //purchaseBillgrid.addListener('rowclick', purchaseBillgridrowclick);

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: strpurchaseBillTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:133
		},{
			region:"center",
			layout:'fit',
			title: strpurchaseBillTitle + "采购开票申请单列表",
			items:[purchaseBillgrid]
		}]
	});
	
	var sDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'sDate',
		name:'sDate',
		width: 160,
	    readOnly:true,
		renderTo:'sDateDiv'
   	});
   	
   	var sDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'eDate',
	    name:'eDate',
		width: 160,
	    readOnly:true,
		renderTo:'eDateDiv'
   	});

});

function operaRD(value,metadata,record){
	var state = record.data.EXAMINE_STATE;
	if('${loginer}'== record.data.CREATOR){
		if (state=='1'){    		
	   		return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" style="color:red" onclick="submitForm()">提交</a>';
	  	 	}else{
	   		return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	   	}		
	}else{
		return '<a href="#" onclick="viewForm()">查看</a>';
	}
}

//流程跟踪
function viewHistory()
{
	var records = purchaseBillgrid.selModel.getSelections();
//	alert(records[0].data.PURCHASE_BILL_ID);
	top.ExtModalWindowUtil.show(strpurchaseBillTitle + '审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.PURCHASE_BILL_ID,
		'',	'',	{width:880,height:400});
}

function viewForm(purchaseBillId){
	if (purchaseBillId == null || purchaseBillId == ''){
		var records = purchaseBillgrid.selModel.getSelections();		
		purchaseBillId = records[0].data.PURCHASE_BILL_ID;
	}

	top.ExtModalWindowUtil.show(strpurchaseBillTitle + '开票申请单信息',
		'purchaseBillController.spr?action=addPurchaseBillView&purchaseBillId='+ purchaseBillId+"&operType=001",
		'',
		funpurchaseBillCallbackFunction,
		{width:900,height:550});
}

//purchaseBill 开票申请单数据的回调函数
function DeletepurchaseBillCallbackFunction(transport){
	callBackHandle(transport);
	purchaseBillds.reload();
}

//增加开票申请单的回调函数
function funpurchaseBillCallbackFunction(){
	purchaseBillds.reload();
}
   
function operaForm(purchaseBillId){
	if (purchaseBillId == null || purchaseBillId == ''){
		var records = purchaseBillgrid.selModel.getSelections();		
		purchaseBillId = records[0].data.PURCHASE_BILL_ID;
	}

	top.ExtModalWindowUtil.show(strpurchaseBillTitle + '开票申请单信息',
		'purchaseBillController.spr?action=addPurchaseBillView&purchaseBillId='+ purchaseBillId + "&operType=101",
		'',
		funpurchaseBillCallbackFunction,
		{width:900,height:550});
}
   
function submitForm(purchaseBillId){
	if (purchaseBillId == null || purchaseBillId == ''){
		var records = purchaseBillgrid.selModel.getSelections();		
		purchaseBillId = records[0].data.PURCHASE_BILL_ID;
	}

	top.ExtModalWindowUtil.show(strpurchaseBillTitle + '开票申请单信息',
		'purchaseBillController.spr?action=addPurchaseBillView&purchaseBillId='+ purchaseBillId +  "&operType=011",
		'',
		funpurchaseBillCallbackFunction,
		{width:900,height:550});
}
--></script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
