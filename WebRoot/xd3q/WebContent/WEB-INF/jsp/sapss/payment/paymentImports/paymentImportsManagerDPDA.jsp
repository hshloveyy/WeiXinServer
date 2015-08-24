<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>进口货物付款</title>
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
<form id="queryform" name="queryform">
<input type="hidden" name="tradeType" id="tradeType" value="${tradeType}">
<table width="100%"  border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门名称：</td>
	<td>
		<div id="dept"></div>
	</td>
	<td align="right">到单号：</td>
	<td>
		<input type="text" id="pickLiskNo" name="pickLiskNo" value=""></input>
	</td>
	<td align="right">合同号：</td>
	<td>
		<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">DP/DA号：</td>
	<td>
	    <input type="text" id="dpDaNo" name="dpDaNo" value=""></input>
	</td>
	<td align="right">到单类型：</td>
	<td>
		<div id="pickListTypeDiv"></div>
	</td>
	<td align="right">物料描述：</td>
	<td>
		<input type="text" id="ekpoTxz01" name="ekpoTxz01" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">核销单号：</td>
	<td>
		<input type="text" id="writeListNo" name="writeListNo" />
	</td>
	<td align="right">代收行：</td>
	<td>
		<input type="text" id="collectionBank" name="collectionBank" />
	</td>
	<td align="right">到单日：</td>
	<td>
		<input type="text" id="pickListRecDate" name="pickListRecDate" />
	</td>
</tr>
<tr>
	<td align="right">审核通过时间：</td>
	<td>
		<input type="text" id="approvedTime" name="approvedTime" />
	</td>
	<td align="right">即期/承兑到期付款日：</td>
	<td>
		<input type="text" id="payTime" name="payTime" />
	</td>
	<td align="right">实际付款日：</td>
	<td>
		<input type="text" id="payRealTime" name="payRealTime" />
	</td>
</tr>
<tr>
	<td align="right" >付款形式：</td>
	<td>
		<select name="payType">
			<option value=''>请选择</option>
			<c:forEach items="${payTypes}" var="row">
				<option value="${row.code}">${row.title}</option>
			</c:forEach>
		</select>
	</td>
	<td align="center" colspan=4">
		<input type="button" value="查找" onclick="findPaymentImportsInfo()"></input>
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
document.onkeydown = function(){if (event.keyCode == 13){find();}}
//贸易类型
var paymentTitle ='';
var strOperType = '0';

var purchaserrecord;
var purchaserfieldName;

switch ('${businessType}') {
   case 'tt':
	   paymentTitle = 'TT';
       break;
   case 'dp':
	   paymentTitle = 'DP';
       break;
   case 'da':
	   paymentTitle = 'DA';
       break;
   case 'sight_lc':
	   paymentTitle = '即期信用证';
       break;
   case 'usance_lc':
	   paymentTitle = '远期信用证';
 }

var paymentImportsInfogrid;		//出仓单信息列表
var paymentImportsInfods;
//查询方法
function findPaymentImportsInfo(){
	var url = 'paymentImportsInfoController.spr?action=queryImportInfo&'+Form.serialize('queryform');
	paymentImportsInfods.proxy= new Ext.data.HttpProxy({url:url});
	paymentImportsInfods.load({params:{start:0, limit:10}});
}
//回调
var customCallBackHandle=function(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('提示',customField);
	paymentImportsInfods.reload();
}
var funAddpaymentImportsCallBack= function(){
	paymentImportsInfods.reload();
}
//修改 
function modify(){
	var records = paymentImportsInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show(paymentTitle+'付款信息',
	'paymentImportsInfoController.spr?action=findPaymentImportInfo&paymentId='+records[0].json.PAYMENT_ID,
	'',
	funAddpaymentImportsCallBack,
	{width:900,height:600});
}
//查看
function view(){
	var records = paymentImportsInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show(paymentTitle+'付款信息',
	'paymentImportsInfoController.spr?action=findPaymentImportInfo&type=view&paymentId='+records[0].json.PAYMENT_ID,
	'',
	'',
	{width:900,height:600});
	
}
//流程查看
function viewWorkflow(){
	var records = paymentImportsInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show(paymentTitle+'审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.PAYMENT_ID,
		'',	'',	{width:900,height:600});
}
//提交特批审批
function openSubmitParticularWorkflow(){
	var records = paymentImportsInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('提交特批审批',
	'paymentImportsInfoController.spr?action=openSubmitParticularWorkflow&bizId='+records[0].json.PAYMENT_ID,
	'',
	funAddpaymentImportsCallBack,
	{width:900,height:600});
}	
function businessAllProcessRecords(){
	var records = paymentImportsInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息',
	'workflowController.spr?action=businessAllProcessRecords&businessRecordId='+records[0].data.PAYMENT_ID,
	'',
	'',
	{width:800,height:500});	
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
    //增加
    var addpaymentImportsInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show(paymentTitle+'付款信息',
			'paymentImportsInfoController.spr?action=add&tradeType=${tradeType}',
			'',
			funAddpaymentImportsCallBack,
			{width:900,height:600});
		}
   	});
   	
   	//删除
   	var deletepaymentImportsInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (paymentImportsInfogrid.selModel.hasSelection()){
				var records = paymentImportsInfogrid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个出仓单信息！');
				}else{
					if(records[0].data.EXAMINE_STATE_D_EXAMINE_STATE=='新增'){
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=delete&paymentId=" + records[0].data.PAYMENT_ID;
								new AjaxEngine('paymentImportsInfoController.spr', {method:"post", parameters: param, onComplete: callBackHandle});
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
					}else{
						top.Ext.Msg.alert('提示','非新增记录不能删除');
					}
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的出仓单信息！');
			}
		}
   	});
    
    var paymentImportsInfotbar = new Ext.Toolbar({
		items:[addpaymentImportsInfo,'-',deletepaymentImportsInfo]
	});
	
	var paymentImportsInfoPlant = Ext.data.Record.create([
		{name:'EXAMINE_STATE_D_EXAMINE_STATE'},
		{name:'PAYMENT_ID'},
		{name:'PICK_LIST_NO'},
		{name:'PAY_VALUE'},
		{name:'${colPayType}'}, 
		{name:'PAY_TIME'},
		{name:'PAY_REAL_VALUE'}, 
		{name:'PAY_REAL_TIME'},
		{name:'APPLY_TIME'},
		{name:'APPROVED_TIME'},
		{name:'DEPT_NAME'},
		{name:'REAL_NAME'},
		{name:'DEPT_NAME'},
		{name:'CONTRACT_NO'},
		{name:'EKPO_TXZ01'},
		{name:'WRITE_LIST_NO'},
		{name:'PICK_LIST_REC_DATE'},
   		{name:'CREDIT_NO'},//
   		{name:'PICK_LIST_TYPE_D_PICK_LTYPE'},//
   		{name:'COLLECTION_BANK'},//
		{name:'CREATOR'},
		{name:'opera'}
	]);

	paymentImportsInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'paymentImportsInfoController.spr?action=queryImportInfo&tradeType=${tradeType}'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},paymentImportsInfoPlant)
    });
    
    var paymentImportsInfosm = new Ext.grid.CheckboxSelectionModel();
    var renderOpera=function(value,metadata,record){
    	var status = record.data.EXAMINE_STATE_D_EXAMINE_STATE;
    	if('${loginer}'==record.data.CREATOR){
        	if(status=='新增')
				return '<a href="#" onclick="modify()" >修改</a>';
			else if(status.indexOf('特批')!=-1){
				return '<a href="#" onclick="view()">查看</a> <a href="#" onclick="businessAllProcessRecords()"> 流程查看</a>';
			}else if(status=='审批未通过'){
				return '<a href="#" onclick="openSubmitParticularWorkflow()"><font color="red">特批</font></a> <a href="#" onclick="view()">查看</a> <a href="#" onclick="viewWorkflow()"> 流程查看</a>';
			}else{
				return '<a href="#" onclick="view()"> 查看</a> <a href="#" onclick="viewWorkflow()"> 流程查看</a>';
			}
        }else{
    		return '<a href="#" onclick="view()">查看</a>';
        }
    }
    var paymentImportsInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),paymentImportsInfosm,
		   {	
			   header: 'payment_id',
	           width: 100,
	           sortable: true,
	           hidden:true,
	           dataIndex: 'PAYMENT_ID'
	       },{
			   header: '操作',
	           width: 100,
	           sortable: true,
	           renderer:renderOpera
	       },{
			   header: '审批状态',
	           width: 100,
	           sortable: true,
	           dataIndex: 'EXAMINE_STATE_D_EXAMINE_STATE'
	       },{
			   header: '部门名称',
	           width: 100,
	           sortable: true,
	           dataIndex: 'DEPT_NAME'
	       },{
			   header: '申请人',
	           width: 100,
	           sortable: true,
	           dataIndex: 'REAL_NAME'
	       },{
			   header: '单号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PICK_LIST_NO'
	       },{
			   header: '合同号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'CONTRACT_NO'
	       },{
   			   header: '信用证号',
   	           width: 100,
   	           sortable: true,
   	           dataIndex: 'CREDIT_NO'
   	       },{
   			   header: '到单类型',
   	           width: 100,
   	           sortable: true,
   	           dataIndex: 'PICK_LIST_TYPE_D_PICK_LTYPE'
   	       },{
			   header: '物料描述',
	           width: 100,
	           sortable: true,
	           dataIndex: 'EKPO_TXZ01'
	       },{
   			   header: '代收行',
   	           width: 100,
   	           sortable: true,
   	           dataIndex: 'COLLECTION_BANK'
   	       },{
			   header: '核销单号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'WRITE_LIST_NO'
	       },{
			   header: '到单日',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PICK_LIST_REC_DATE'
	       },{
			   header: '申请时间',
	           width: 100,
	           sortable: true,
	           dataIndex: 'APPLY_TIME'
	       },{
			   header: '审批通过时间',
	           width: 100,
	           sortable: true,
	           dataIndex: 'APPROVED_TIME'
	       },{
			   header: '付款日',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PAY_TIME'
	       },{
			   header: '付款类型',
	           width: 100,
	           sortable: true,
	           dataIndex: '${colPayType}'
	       },{
			   header: '实际付款日',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PAY_REAL_TIME'
	       },{
			   header: '实际付款金额',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PAY_REAL_VALUE'
	       }
	    ]);
    paymentImportsInfocm.defaultSortable = true;
    
    var paymentImportsInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:paymentImportsInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    paymentImportsInfogrid = new Ext.grid.EditorGridPanel({
    	id:'paymentImportsInfoGrid',
        ds: paymentImportsInfods,
        cm: paymentImportsInfocm,
        sm: paymentImportsInfosm,
        bbar: paymentImportsInfobbar,
        tbar: paymentImportsInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    paymentImportsInfods.load({params:{start:0, limit:10},arg:[]});
 
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: paymentTitle + "付款条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:160
		},
		{
			region:"center",
			layout:'fit',
		    split:true,
			collapsible: true,
			height:220,
			minSize: 210,
            maxSize: 400,
			title: paymentTitle + "付款列表",
			items:[paymentImportsInfogrid]
		}]
	});
	
	var pickListRecDate = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 160,
	    readOnly:true,
		applyTo:'pickListRecDate'
   	});
	var approvedTime = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 160,
	    readOnly:true,
		applyTo:'approvedTime'
   	});
	var payTime = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 160,
	    readOnly:true,
		applyTo:'payTime'
   	});
	var payRealTime = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 160,
	    readOnly:true,
		applyTo:'payRealTime'
   	});
});

</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
<fiscxd:dictionary divId="pickListTypeDiv" fieldName="pickListType" dictionaryName="BM_PICK_LTYPE" width="153" ></fiscxd:dictionary>
