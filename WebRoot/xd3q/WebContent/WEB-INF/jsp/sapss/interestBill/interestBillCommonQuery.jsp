<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>利息税开票申请管理</title>
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
<form action="" id="findBillApplyFrom" name="findBillApplyFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1"
	bordercolor="#6699FF" class="datatable">
	<tr>
		<td align="right">部门名称：</td>
		<td>
		<div id="dept"></div>
		</td>
		<td align="right">开票申请单编码：</td>
		<td><input type="text" id="interestBillNo" name="interestBillNo"
			value=""></input></td>
		<td align="right">SAP开票凭证号：</td>
		<td><input type="text" id="sapReturnNo"
			name="sapReturnNo" value=""></input></td>
	</tr>
	<tr>
		<td align="right">立项号：</td>
		<td><input type="text" id="projectNo" name="projectNo" value=""></input>
		</td>
		<td align="right">纸质合同号：</td>
		<td><input type="text" id="paperNo" name="paperNo" value=""></input>
		</td>
		<td align="right">发票号：</td>
		<td><input type="text" id="taxNo" name="taxNo"
			value=""></input></td>
	</tr>
	<tr>
	    <td align="right">客户/供应商名称：</td>
		<td><input type="text" id="unitName" name="unitName"
			value=""></input></td>
		<td align="right">客户/供应商编码：</td>
		<td><input type="text" id="unitNo" name="unitNo" value=""/></td>
		<td align="right"></td>
		<td></td>
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
			onclick="findBillApply()"></input> <input type="reset" value="清空"></input>
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
document.onkeydown = function(){if (event.keyCode == 13){findBillApply();}}


var strBillApplyTitle ='';
var strOperType = '0';

var BillApplyRecord;

var billApplygrid;		//开票申请单信息列表
var billApplyds;

//BillApply 查找按钮的单击事件
function findBillApply(){
	var sDate= Ext.getDom("sDate").value;
    var eDate= Ext.getDom("eDate").value;
    
	var param = Form.serialize('findBillApplyFrom');
	var requestUrl = 'interestBillController.spr?action=query&' + param;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;		
	billApplyds.proxy= new Ext.data.HttpProxy({url:requestUrl});
	billApplyds.load({params:{start:0, limit:10},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	
	//增加开票申请单的回调函数
    function funAddBillApplyCallBack(){
    	billApplyds.reload();
    }
   
    
    
	var BillApplyPlant = Ext.data.Record.create([
		{name:'INTEREST_BILL_ID'}, 
		{name:'EXAMINE_STATE_D_EXAMINE_STATE'}, 
		
		{name:'PROJECT_NO'}, 
		{name:'PROJECT_ID'}, 
		{name:'INTEREST_BILL_NO'}, 
		{name:'DEPT_ID'}, 
		{name:'DEPT_NAME'}, 
		{name:'UNIT_NAME'}, 
		{name:'UNIT_NO'}, 
		{name:'UNIT_TYPE'}, 
		{name:'BILL_PARTY_NO'}, 
		{name:'APPLY_DATE'}, 
		{name:'SAP_RETURN_NO'}, 
		{name:'TAX_NO'}, 
		{name:'MAKE_DATE'}, 
		{name:'PAPER_NO'}, 
		{name:'RECEIPT_DATE'}, 
		{name:'VALUE'}, 
		{name:'MEMO'}, 
		{name:'APPLY_TIME'}, 
		{name:'APPROVED_TIME'}, 
		{name:'CREATE_TIME'}, 
		{name:'EXAM_STATE'}, 
		{name:'CREATOR'}, 
		{name:'IS_AVAILABLE'},
		{name:'BILLAPPLYINFO'}
	]);

	billApplyds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'interestBillController.spr?action=query'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},BillApplyPlant)
    });
    
    var billApplysm = new Ext.grid.CheckboxSelectionModel();    
    var billApplycm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		billApplysm,
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
           dataIndex: 'BILLAPPLYINFO',
           renderer: operaRD
           },
           {
           header: '状态',
           width: 100,
           sortable: true,
           dataIndex: 'EXAMINE_STATE_D_EXAMINE_STATE'
           },                       
  		　 {header: '开票申请单编号',
           width: 80,
           sortable: true,
           dataIndex: 'INTEREST_BILL_NO'
           },
           {header: '客户编码',
           width: 80,
           sortable: true,
           dataIndex: 'UNIT_NO'
           },
           {header: '客户名称',
           width: 150,
           sortable: true,
           dataIndex: 'UNIT_NAME'
           },           
           {header: 'SAP开票凭证',
           width: 80,
           sortable: true,
           dataIndex: 'SAP_RETURN_NO',
           hidden: true
           },
           {header: '立项号',
           width: 80,
           sortable: true,
           dataIndex: 'PROJECT_NO'
           },
           {header: '纸质合同号',
           width: 80,
           sortable: true,
           dataIndex: 'PAPER_NO'
           },
           {header: '发票号',
           width: 80,
           sortable: true,
           dataIndex: 'TAX_NO'
           },
           {
           header: '开票金额',
           width: 80,
           sortable: true,
           dataIndex: 'VALUE'
           },
           {
           header: '申报时间',
           width: 80,
           sortable: true,
           dataIndex: 'APPLY_TIME'
           }, 
           {
           header: '审批时间',
           width: 80,
           sortable: true,
           dataIndex: 'APPROVED_TIME'
           },  
           {
           header: '创建时间',
           width: 80,
           sortable: true,
           dataIndex: 'CREATE_TIME'
           },                                
           {
           header: '创建人ID',
           width: 100,
           sortable: true,
           dataIndex: 'CREATOR',
           hidden:true           
           },
      		{
           header: '状态',
           width: 100,
           sortable: true,
           dataIndex: 'EXAM_STATE',
           hidden: true
           }
    ]);
    billApplycm.defaultSortable = true;
    
    var billApplybbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:billApplyds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    billApplygrid = new Ext.grid.EditorGridPanel({
    	id:'billApplygrid',
        ds: billApplyds,
        cm: billApplycm,
        sm: billApplysm,
        bbar: billApplybbar,
        //tbar: billApplytbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    billApplyds.load({params:{start:0, limit:10},arg:[]});
    
 //billApplygrid.addListener('rowclick', billApplygridrowclick);

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:133
		},{
			region:"center",
			layout:'fit',
			title: "开票申请单(利息税)列表",
			items:[billApplygrid]
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
	return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	 
}

//流程跟踪
function viewHistory()
{
	var records = billApplygrid.selModel.getSelections();
//	alert(records[0].data.INTEREST_BILL_ID);
	top.ExtModalWindowUtil.show('审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.INTEREST_BILL_ID,
		'',	'',	{width:880,height:400});
}

function viewForm(interestBillId){
	if (interestBillId == null || interestBillId == ''){
		var records = billApplygrid.selModel.getSelections();		
		interestBillId = records[0].data.INTEREST_BILL_ID;
	}

	top.ExtModalWindowUtil.show('开票申请单信息',
		'interestBillController.spr?action=interestBillView&interestBillId='+ interestBillId +"&operType=001",
		'',
		funBillApplyCallbackFunction,
		{width:900,height:550});
}

//BillApply 开票申请单数据的回调函数
function DeleteBillApplyCallbackFunction(transport){
	callBackHandle(transport);
	billApplyds.reload();
}

//增加开票申请单的回调函数
function funBillApplyCallbackFunction(){
	billApplyds.reload();
}
   
function operaForm(interestBillId){
	if (interestBillId == null || interestBillId == ''){
		var records = billApplygrid.selModel.getSelections();
		interestBillId = records[0].data.INTEREST_BILL_ID;
	}

	top.ExtModalWindowUtil.show('开票申请单信息',
		'interestBillController.spr?action=updateInterestBillView&interestBillId='+ interestBillId +"&operType=101",
		'',
		funBillApplyCallbackFunction,
		{width:900,height:550});
}
   
function submitForm(interestBillId){
	if (interestBillId == null || interestBillId == ''){
		var records = billApplygrid.selModel.getSelections();		
		interestBillId = records[0].data.INTEREST_BILL_ID;
	}

	top.ExtModalWindowUtil.show('开票申请单信息',
		'interestBillController.spr?action=updateInterestBillView&interestBillId='+ interestBillId + "&operType=011",
		'',
		funBillApplyCallbackFunction,
		{width:900,height:550});
}
--></script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
