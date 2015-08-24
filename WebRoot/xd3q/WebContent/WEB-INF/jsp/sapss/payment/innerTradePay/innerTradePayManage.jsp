<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%
	response.setContentType("text/html;charset=UTF-8");
%><%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内贸付款管理</title>
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
<form action="" id="findInnerTradePayFrom" name="findInnerTradePayFrom">
<input type="hidden" name="payType" value="${payType}">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right" width="11%">部门名称：</td>
	<td colspan="3">
		<input type="text" id="deptName" name="deptName" value="${deptName}" readonly="readonly">
		<input type="hidden" id="deptId" name="deptId" value="${deptId}">
	</td>
	<td align="right" width="11%">申请人：</td>
	<td colspan="3">
		<input type="text" id="realName" name="realName" value="${realName}">
		<input type="hidden" id="creatorName" name="creatorName" value="${userName}">
		<input type="hidden" id="creatorId" name="creatorId" value="${userId}">
	</td>
	<td align="right" width="11%">合同号：</td>
	<td colspan="3">
		<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right" width="11%">合同时间：</td>
	<td>
		<div id="contractTimeDiv"></div>
	</td>
	<td>至</td>
	<td>
		<div id="endContractTimeDiv"></div>
	</td>
	<td align="right">付款形式：</td>
	<td colspan="3">
		${payMethodControl}
	</td>
	<td align="right">付款用途：</td>
	<td colspan="3">
		<input type="text" id="payUse" name="payUse" value=""></input>
	</td>
</tr>
<tr>
	<td align="right" width="11%">申请时间：</td>
	<td>
		<div id="applyTimeDiv"></div>
	</td>
	<td>至</td>
	<td>
		<div id="endApplyTimeDiv"></div>
	</td>
	<td align="right" width="11%">审批通过时间：</td>
	<td>
		<div id="approvedTimeDiv"></div>
	</td>
	<td>至</td>
	<td>
		<div id="endApprovedTimeDiv"></div>
	</td>
	<td align="right" width="11%">实际付款日：</td>
	<td>
		<div id="payRealTimeDiv"></div>
	</td>
	<td>至</td>
	<td>
		<div id="endPayRealTimeDiv"></div>
	</td>
</tr>
<tr>
	<td align="right" width="11%">收款单位：</td>
	<td colspan="3">
		<input type="text" id="recBank" name="recBank" value=""></input>
	</td>
	<td align="right" width="11%">财务编号：</td>
	<td colspan="3">
		<input type="text" id="financeNo" name="financeNo" value=""></input>
	</td>
	<td align="center" colspan="4">
		<input type="button" value="查找" onclick="findInnerTradePayInfo()"></input>
		<input type="reset" value="清空"></input>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_center"></div>
<div id="div_south"></div>

</body>
</html>
<script type="text/javascript">
document.onkeydown = function(){if (event.keyCode == 13){findInnerTradePayInfo();}}
//付款类型
var payType = "${payType}";

//付款方式
var payMethod = "${payMethod}";

//部门ID
var deptId = "${deptId}";

var strInnerTradePayTitle ='';
if (payType == '1') {
	switch(payMethod){
		case '1':
			strInnerTradePayTitle = '国内信用证';
			break;
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '9':
			strInnerTradePayTitle = '现金/背书/转账/电汇/网银/银行即期汇票';
			break;
		case '7':
			strInnerTradePayTitle = '银行/商业承兑汇票';
			break;
	}
}else if(payType == '2'){
	if(payMethod=='1')
		strInnerTradePayTitle = '普通非货款';
	else
		strInnerTradePayTitle = '期货保证金';
}else if(payType=='3'){
	strInnerTradePayTitle = '进口预付款';
}
document.title = strInnerTradePayTitle;
var purchaserrecord;
var purchaserfieldName;


var innerTradePayInfogrid;		//内贸付款信息列表
var innerTradePayInfods;

//innerTradePay 查找按钮的单击事件
function findInnerTradePayInfo()
{
  	var requestUrl = 'innerTradePayController.spr?action=query&deptId=' + Ext.getDom('deptId').value 
                     + '&'+Form.serialize('findInnerTradePayFrom');
	innerTradePayInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	innerTradePayInfods.load({params:{start:0, limit:10},arg:[]});
	//payMethod = dict_payMethodDiv.getSelectedValue();
	payMethod = Ext.getDom("payMethod").value;
	//var param1 = Form.serialize(findInnerTradePayFrom);
	//var param = '?action=query&deptId=' + Ext.getDom('deptId').value + '&' + param1;
	//new AjaxEngine('innerTradePayController.spr', 
	//			{method:"post", parameters: param, onComplete: callBackHandle});
}

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
} 
//innerTradePay 删除信用证开证数据的回调函数
function funDeleteInnerTradePayCallBack(transport){
	callBackHandle(transport);
	innerTradePayInfods.reload();
}
//增加内贸付款的回调函数
function funAddInnerTradePayCallBack(){
	innerTradePayInfods.reload();
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';


    
    //增加
    var addinnerTradePayInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show(strInnerTradePayTitle + '付款申请表',
			'innerTradePayController.spr?action=create&payType='+payType+'&payMethod='+payMethod,
			'',
			findInnerTradePayInfo,
			{width:900,height:600});
		}
   	});

   	//删除
   	var deleteinnerTradePayInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (innerTradePayInfogrid.selModel.hasSelection()){
				var records = innerTradePayInfogrid.selModel.getSelections();
		
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个内贸付款信息！');
				}else{
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除选择的记录!   ',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=delete";
								param = param + "&paymentId=" + records[0].data.PAYMENT_ID;

								new AjaxEngine('innerTradePayController.spr', 
						   			{method:"post", parameters: param, onComplete: funDeleteInnerTradePayCallBack});
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的内贸付款信息！');
			}
		}
   	});
    
    var innerTradePayInfotbar = new Ext.Toolbar({
		items:[addinnerTradePayInfo,'-',deleteinnerTradePayInfo]
	});
	
	var innerTradePayInfoPlant = Ext.data.Record.create([
		{name:'PAYMENT_ID'},					//付款ID
		{name:'DEPT_NAME'},						//部门名称
		{name:'CREATOR_NAME'},					//申请人姓名
		{name:'CREATOR'},						//申请人
		//{name:'CONTRACT_NO'},					//合同号	
		//{name:'CONTRACT_TIME'},					//合同时间
		{name:'PAY_METHOD'},					//付款形式
		{name:'APPLY_TIME'},					//申请时间
		{name:'APPROVED_TIME'},					//审批通过时间
		{name:'PAY_REAL_TIME'},					//实际付款日
		{name:'PAY_VALUE'},						//实际付款金额
		{name:'REC_BANK'},						//收款单位名
		{name:'OPEN_ACCOUNT_BANK'},				//开户行
		{name:'OPEN_ACCOUNT_NO'},				//帐号
		{name:'EXAMINE_STATE'},					//审批状态
		{name:'EXAMINE_STATE_D_EXAMINE_STATE'}	//审批状态
	]);

	innerTradePayInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'innerTradePayController.spr?action=getInnerTradePayList&payType='+payType+'&deptId='+deptId+"&payMethod="+payMethod}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},innerTradePayInfoPlant)
    });
    
    var innerTradePayInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var innerTradePayInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		innerTradePayInfosm,
			{
	           header: '付款ID',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PAYMENT_ID',
	           hidden:true
			},
			{
	       		header: '审批状态',
	       		width: 100,
	       		sortable: false,
	       		dataIndex: 'EXAMINE_STATE_D_EXAMINE_STATE'
	   		},
			{
	       		header: '操作',
	       		width: 100,
	       		sortable: false,
	       		dataIndex: 'EXPORTINFO',
				renderer: operaRD
	   		},
			{
	           header: '部门名称',
	           width: 100,
	           sortable: true,
	           dataIndex: 'DEPT_NAME'
	           // hidden:true
			},
			{
			   header: '申请人',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CREATOR_NAME'
	   		},
			{
			   header: '申请人ID',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CREATOR',
				hidden:true
	   		},/*
			{
			   header: '合同号',
	           width: 100,
	           sortable: false,
	           dataIndex: 'CONTRACT_NO'
	   		},
			{
	           header: '合同时间',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CONTRACT_TIME',
	           renderer:renderHallName
			},*/
			{
	           header: '付款形式',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PAY_METHOD',
	           renderer:renderHallName
			},
			{
	           header: '申请时间',
	           width: 100,
	           sortable: true,
	           dataIndex: 'APPLY_TIME'
	   		},
			{
	           header: '审批通过时间',
	           width: 100,
	           sortable: true,
	           dataIndex: 'APPROVED_TIME'
	   		},
			{
	       		header: '实际付款日',
	       		width: 100,
	       		sortable: false,
	       		dataIndex: 'PAY_REAL_TIME'
	   		},
			{
	       		header: '实际付款金额',
	       		width: 100,
	       		sortable: false,
	       		dataIndex: 'PAY_VALUE'
	   		},
			{
	       		header: '收款单位名',
	       		width: 100,
	       		sortable: false,
	       		dataIndex: 'REC_BANK'
	   		},
			{
	       		header: '开户行',
	       		width: 100,
	       		sortable: false,
	       		dataIndex: 'OPEN_ACCOUNT_BANK'
	   		},
			{
	       		header: '账号',
	       		width: 100,
	       		sortable: false,
	       		dataIndex: 'OPEN_ACCOUNT_NO'
			}
    ]);
    innerTradePayInfocm.defaultSortable = true;
    
    var innerTradePayInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:innerTradePayInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    innerTradePayInfogrid = new Ext.grid.EditorGridPanel({
    	id:'innerTradePayInfoGrid',
        ds: innerTradePayInfods,
        cm: innerTradePayInfocm,
        sm: innerTradePayInfosm,
        bbar: innerTradePayInfobbar,
        tbar: innerTradePayInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    innerTradePayInfods.load({params:{start:0, limit:10},arg:[]});
    
 //innerTradePayInfogrid.addListener('rowclick', innerTradePayInfogridrowclick);

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: strInnerTradePayTitle + "付款条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:135
		},{
			region:"center",
			layout:'fit',
			title: strInnerTradePayTitle + "付款列表",
			items:[innerTradePayInfogrid]
		}]
	});
	
	Ext.apply(Ext.form.VTypes, {
		daterange: function(val, field) {
			var date = field.parseDate(val);
			
			var dispUpd = function(picker) {
				var ad = picker.activeDate;
				picker.activeDate = null;
				picker.update(ad);
			};
			
			if (field.startDateField) {
				var sd = Ext.getCmp(field.startDateField);
				sd.maxValue = date;
				if (sd.menu && sd.menu.picker) {
					sd.menu.picker.maxDate = date;
					dispUpd(sd.menu.picker);
				}
			} else if (field.endDateField) {
				var ed = Ext.getCmp(field.endDateField);
				ed.minValue = date;
				if (ed.menu && ed.menu.picker) {
					ed.menu.picker.minDate = date;
					dispUpd(ed.menu.picker);
				}
			}
			return true;
		}
	});
	
	//合同时间
	var contractTime = new Ext.form.DateField({
		format:'Y-m-d',
		id:'contractTime',
		name:'contractTime',
		width: 95,
		readOnly:true,
		vtype: 'daterange',
		renderTo:'contractTimeDiv',
		endDateField: 'endContractTime'
   	});
	//合同时间
	var endContractTime = new Ext.form.DateField({
		format:'Y-m-d',
		id:'endContractTime',
		name:'endContractTime',
		width: 95,
		readOnly:true,
		vtype: 'daterange',
		renderTo:'endContractTimeDiv',
		startDateField: 'contractTime'
   	});
   	//申请时间
   	var applyTime = new Ext.form.DateField({
		format:'Y-m-d',
		id:'applyTime',
		name:'applyTime',
		width: 95,
		readOnly:true,
		vtype: 'daterange',
		renderTo:'applyTimeDiv',
		endDateField: 'endApplyTime'
   	});
   	//申请时间
   	var endApplyTime = new Ext.form.DateField({
		format:'Y-m-d',
		id:'endApplyTime',
		name:'endApplyTime',
		width: 95,
		readOnly:true,
		vtype: 'daterange',
		renderTo:'endApplyTimeDiv',
		startDateField: 'applyTime'
   	});
   	//审批通过时间
    var approvedTime = new Ext.form.DateField({
		format:'Y-m-d',
		id:'approvedTime',
		name:'approvedTime',
		width: 95,
		readOnly:true,
		vtype: 'daterange',
		renderTo:'approvedTimeDiv',
		endDateField: 'endApprovedTime'
   	});
   	//审批通过时间
    var endApprovedTime = new Ext.form.DateField({
		format:'Y-m-d',
		id:'endApprovedTime',
		name:'endApprovedTime',
		width: 95,
		readOnly:true,
		renderTo:'endApprovedTimeDiv',
		startDateField: 'approvedTime'
   	});
   	//实际付款时间
    var payRealTime = new Ext.form.DateField({
		format:'Y-m-d',
		id:'payRealTime',
		name:'payRealTime',
		width: 95,
		readOnly:true,
		vtype: 'daterange',
		renderTo:'payRealTimeDiv',
		endDateField: 'endPayRealTime'
   	});   	
   	//实际付款时间
    var endPayRealTime = new Ext.form.DateField({
		format:'Y-m-d',
		id:'endPayRealTime',
		name:'endPayRealTime',
		width: 95,
		readOnly:true,
		vtype: 'daterange',
		renderTo:'endPayRealTimeDiv',
		startDateField: 'payRealTime'
   	});   	

});

//innerTradePayManage 修改内贸付款链接
function operaRD(value,metadata,record){
	var state = record.data.EXAMINE_STATE;
	//"'+record.data.PAYMENT_ID+'"
	if('${userId}'== record.data.CREATOR){
		if (state=='1'){    		
			return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" style="color:red" onclick="operaForm();">提交</a>';
		}
		/**
		else if(state=='4' || state=='6'){//未通过
			return '<a href="#" onclick="openSubmitParticularWorkflow();"><font color="red">特批</font></a> <a href="#" onclick="viewForm();">查看</a> <a href="#" onclick="viewHistory();">流程跟踪</a>';
		}
		**/
		else if(state=='7' || state=='8'|| state=='9'){
			return '<a href="#" onclick="viewForm();">查看</a> <a href="#" onclick="businessAllProcessRecords();">流程跟踪</a>';
		}else{
			return '<a href="#" onclick="viewForm();">查看</a> <a href="#" onclick="viewHistory();">流程跟踪</a>';
		}		
	}else{
		return '<a href="#" onclick="viewForm();">查看</a>';
	}
}
function openSubmitParticularWorkflow(){
	var records = innerTradePayInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('提交特批审批',
	'innerTradePayController.spr?action=openSubmitParticularWorkflow&payType='+payType+'&payMethod='+payMethod+'&paymentId='+records[0].data.PAYMENT_ID,
	'',
	modifyCallback,
	{width:900,height:600});
}	
function businessAllProcessRecords(){
	var records = innerTradePayInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息',
	'workflowController.spr?action=businessAllProcessRecords&businessRecordId='+records[0].data.PAYMENT_ID,
	'',
	'',
	{width:900,height:600});	
}
	
function operaForm(paymentId){
	var records = innerTradePayInfogrid.selModel.getSelections();		
	top.ExtModalWindowUtil.show('内贸付款管理',
		'innerTradePayController.spr?action=modify&payType='+payType+'&payMethod='+payMethod+'&paymentId='+records[0].data.PAYMENT_ID,
		'',
		modifyCallback,
		{width:900,height:600});
}	

function viewForm(){
	var records = innerTradePayInfogrid.selModel.getSelections();		
	top.ExtModalWindowUtil.show('内贸付款管理',
		'innerTradePayController.spr?action=view&payType='+payType+'&payMethod='+payMethod+'&paymentId='+records[0].data.PAYMENT_ID,
		'',
		modifyCallback,
		{width:900,height:600});
}

function viewHistory(){
	var records = innerTradePayInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息',
	'workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.PAYMENT_ID,
	'',
	'',
	{width:900,height:600});	
}
//innerTradePayManage 修改动作完成后回调函数
function modifyCallback(){
	innerTradePayInfods.load({params:{start:0, limit:10}});
}

function renderHallName(value, meta, rec, rowIdx, colIdx, ds)
{
   return '<div ext:qtitle="" ext:qtip="' + value + '">'+ value +'</div>';
} 
</script>