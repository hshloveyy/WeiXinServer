<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%
	response.setContentType("text/html;charset=UTF-8");
%><%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内贸付款综合查询</title>
</head>
<body>
<div id="div_northForm">
<form action="" id="findInnerTradePayFrom" name="findInnerTradePayFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门：</td>
	<td colspan="3">
		<div id="dept"></div>
	</td>
	<td align="right">立项号：</td>
	<td colspan="3">
		<input type="text" id="projectNo" name="projectNo" value=""></input>
	</td>
	<td align="right">合同号：</td>
	<td colspan="3">
		<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">合同时间：</td>
	<td>
		<div id="contractTimeDiv"></div>
	</td>
	<td>至</td>
	<td>
		<div id="endContractTimeDiv"></div>
	</td>
	<td align="right">付款形式：</td>
	<td colspan="3">
		<input type="hidden" name="payType" value="${payType}"/>
		<%if("1".equals(request.getAttribute("payType"))){%>
			<select name="payMethod" id="payMethod">
				<option value=>请选择</option>
				<option value=1>国内信用证</option>
				<option value=2>现金</option>
				<option value=3>背书</option>
				<option value=4>转账</option>
				<option value=5>电汇</option>
				<option value=6>网银</option>
				<option value=7>银行/商业承兑汇票</option>
				<option value=9>银行即期汇票</option>
			</select>
		<%}else if("2".equals(request.getAttribute("payType"))){ %>
			<select name="payMethod" id="payMethod">
				<option value=>请选择</option>
				<option value=1>普通非货款</option>
				<option value=3>非货款/背书</option>
				<option value=4>非货款/转账</option>
				<option value=5>非货款/电汇</option>
				<option value=6>非货款/网银</option>
				<option value=7>非货款/现金</option>
				<option value=2>期货保证金</option>
			</select>
		<%}else if("3".equals(request.getAttribute("payType"))){ %>
			<select name="payMethod" id="payMethod">
				<option value=8>进口预付款/TT</option>
			</select>
		<%}%>
	</td>
	<td align="right">付款用途：</td>
	<td colspan="3">
		<input type="text" id="payUse" name="payUse" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">申请时间：</td>
	<td>
		<div id="applyTimeDiv"></div>
	</td>
	<td>至</td>
	<td>
		<div id="endApplyTimeDiv"></div>
	</td>
	<td align="right">审批通过时间：</td>
	<td>
		<div id="approvedTimeDiv"></div>
	</td>
	<td>至</td>
	<td>
		<div id="endApprovedTimeDiv"></div>
	</td>
	<td align="right">实际付款日：</td>
	<td>
		<div id="payRealTimeDiv"></div>
	</td>
	<td>至</td>
	<td>
		<div id="endPayRealTimeDiv"></div>
	</td>
</tr>
<tr>
	<td align="right">收款单位：</td>
	<td colspan="3">
		<input type="text" id="recBank" name="recBank" value=""></input>
	</td>
	<td align="right">审批状态：</td>
	<td colspan="3"><div id="div_examState"></div></td>
	<td align="center" colspan="4" >付款金额：<input type="text" id="payValue" name="payValue" size="10">-<input type="text" id="payValue1" name="payValue1" size="10">
		<input type="button" value="查找" onclick="findInnerTradePayInfo()"></input>
		<input type="reset" value="清空"><a onbeforeactivate="onbeforClickA()" href="innerTradePayController.spr?action=dealOutToExcel" id="aaa" target="_self">下载</a></input>
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
function onbeforClickA(){
	var param = Form.serialize('findInnerTradePayFrom');
	var requestUrl = 'innerTradePayController.spr?action=dealOutToExcel&' + param;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;
	$('aaa').href=requestUrl;
}
document.onkeydown = function(){if (event.keyCode == 13){findInnerTradePayInfo();}}
//付款类型
var payType = "${payType}";

//付款方式
var payMethod = "${payMethod}";


var strInnerTradePayTitle ='';
document.title = strInnerTradePayTitle;
var purchaserrecord;
var purchaserfieldName;


var innerTradePayInfogrid;		//内贸付款信息列表
var innerTradePayInfods;

//innerTradePay 查找按钮的单击事件
function findInnerTradePayInfo()
{
  	var requestUrl = 'innerTradePayController.spr?action=query&deptId='+selectId_dept + '&'+Form.serialize('findInnerTradePayFrom');
	innerTradePayInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	innerTradePayInfods.load({params:{start:0, limit:10},arg:[]});
}

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
} 

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';


	//增加内贸付款的回调函数
    function funAddInnerTradePayCallBack(){
		innerTradePayInfods.reload();
    }
    
    
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
		{name:'EXAMINE_STATE_D_EXAMINE_STATE'},	//审批状态
		{name:'PAY_METHOD_VIEW'}	//审批状态
	]);
//innerTradePayController.spr?action=getInnerTradePayList&payType=${payType}'+'&deptId='+selectId_dept+"&payMethod="+$('payMethod').value
	innerTradePayInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'innerTradePayController.spr?action=query&payType=${payType}'}),
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
	   		},
			/*{
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
    
   var print = new Ext.Toolbar.Button({
   		text:'打印',
	    iconCls:'find',
		handler:function(){
   				if (innerTradePayInfogrid.selModel.hasSelection()){
					var records = innerTradePayInfogrid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
					}else{
					    if(records[0].json.EXAMINE_STATE_D_EXAMINE_STATE.indexOf('特批')>=0){
					      window.open('innerTradePayController.spr?action=dealParticularPrint&paymentId='+records[0].json.PAYMENT_ID,'_blank','');
					    }
					    else {
					      window.open('innerTradePayController.spr?action=dealPrint&paymentId='+records[0].json.PAYMENT_ID,'_blank','');
					    }
					}
				}else{
					top.ExtModalWindowUtil.alert('提示','请选择一条记录');
				}
				//selectWindow.hide();
		}
   	});
   	
   	 var itemTbar = new Ext.Toolbar({
		items:[print]
	});
    
    innerTradePayInfogrid = new Ext.grid.EditorGridPanel({
    	id:'innerTradePayInfoGrid',
        ds: innerTradePayInfods,
        cm: innerTradePayInfocm,
        sm: innerTradePayInfosm,
        bbar: innerTradePayInfobbar,
        tbar: itemTbar,
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
			height:140
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

//innerTradePayManage 修改内贸付款链接<a href="#" onclick="printHandler();">打印</a>
function operaRD(value,metadata,record){
	var state = record.data.EXAMINE_STATE;
	return '<a href="#" onclick="viewForm();">查看</a> <a href="#" onclick="viewHistory();">流程跟踪</a>';
}
	
function printHandler(){
    var records = innerTradePayInfogrid.selModel.getSelections();
    window.open('innerTradePayController.spr?action=dealPrint&paymentId='+records[0].data.PAYMENT_ID,'_blank','location=no,resizable=yes');
}
function viewForm(){
	var records = innerTradePayInfogrid.selModel.getSelections();		
	top.ExtModalWindowUtil.show('内贸付款查看',
		'innerTradePayController.spr?action=view&payType=${payType}'+'&payMethod='+records[0].data.PAY_METHOD_VIEW+'&paymentId='+records[0].data.PAYMENT_ID,
		'',
		modifyCallback,
		{width:800,height:540});
}

function viewHistory(){
	var records = innerTradePayInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息',
	'workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.PAYMENT_ID,
	'',
	'',
	{width:900,height:365});	
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
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155" isMutilSelect="true"></fiscxd:dept>
<fiscxd:dictionary divId="div_examState" fieldName="examState" dictionaryName="BM_EXAMINE_STATE" width="153"></fiscxd:dictionary>

