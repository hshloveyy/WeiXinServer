<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>进口货物付款</title>
</head>
<body>

<div id="div_northForm">
<form id="queryform" name="queryform">
<table width="100%"  border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门名称：</td>
	<td>
		<div id="dept"></div>
	</td>
	<td align="right">单号：</td>
	<td>
		<input type="text" id="pickLiskNo" name="pickLiskNo" value=""></input>
	</td>
	<td align="right">合同号：</td>
	<td>
		<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">信用证号：</td>
	<td>
	    <input type="text" id="creditNO" name="creditNO" value=""></input>
	</td>
	<td align="right">到单类型：</td>
	<td>
		<div id="pickListTypeDiv"></div>
	</td>
	<td align="right" >付款类型：</td>
	<td>
		<select name="payType">
			<option value=''>请选择</option>
			<c:forEach items="${payTypes}" var="row">
				<option value="${row.code}">${row.title}</option>
			</c:forEach>
		</select>
	</td>
</tr>
<tr>
	<td align="right">核销单号：</td>
	<td>
		<input type="text" id="writeListNo" name="writeListNo" value=""></input>
	</td>
	<td align="right">开证日期：</td>
	<td>
	    <input type="text" id="issuingDate" name="issuingDate" value=""></input>
	</td>
	<td align="right">开证行：</td>
	<td>
		<input type="text" id="issuingBank" name="issuingBank" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">到单日：</td>
	<td>
		<input type="text" id="pickListRecDate" name="pickListRecDate" value=""></input>
	</td>
	<td align="right">审批通过时间：</td>
	<td>
	<table cellpadding="0" cellspacing="0" border="0">
	      <tr>
	         <td><input type="text" id="approvedTime" name="approvedTime" value=""></input></td>
	         <td>-</td>
	         <td><input type="text" id="approvedTime1" name="approvedTime1" value=""></input></td>
	      </tr>
	    </table>
	</td>
	<td align="right">付款日：</td>
	<td><table cellpadding="0" cellspacing="0" border="0">
	      <tr>
	         <td><input type="text" id="payTime" name="payTime" value=""></input></td>
	         <td>-</td>
	         <td><input type="text" id="payTime1" name="payTime1" value=""></input></td>
	      </tr>
	    </table>
	</td>
</tr>
<tr>
	<td align="right">实际付款日：</td>
	<td>
		<input type="text" id="payRealTime" name="payRealTime" value=""></input>
	</td>
	<td align="right">品名：</td>
	<td>
		<input type="text" id="goodsName" name="goodsName" value=""></input>
	</td>
	<td align="center" colspan="2">
		<input type="button" value="查找" onclick="findPaymentImportsInfo()"></input>
		<input type="reset" value="清空"><a onbeforeactivate="onbeforClickA()" href="paymentImportsInfoController.spr?action=dealOutToExcel" id="aaa" target="_self">下载</a></input>
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
	var param = Form.serialize('queryform');
	var requestUrl = 'paymentImportsInfoController.spr?action=dealOutToExcel&' + param;
	//requestUrl = requestUrl + '&deptId=' + selectId_dept;
	$('aaa').href=requestUrl;
}
document.onkeydown = function(){if (event.keyCode == 13){findPaymentImportsInfo();}}

var paymentImportsInfogrid;
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
	top.ExtModalWindowUtil.show('付款信息',
	'paymentImportsInfoController.spr?action=findPaymentImportInfo&paymentId='+records[0].json.PAYMENT_ID,
	'',
	funAddpaymentImportsCallBack,
	{width:900,height:600});
}
//查看
function view(){
	var records = paymentImportsInfogrid.selModel.getSelections();
	var param='&status='+records[0].json.EXAMINE_STATE_D_EXAMINE_STATE;
	top.ExtModalWindowUtil.show('付款信息',
	'paymentImportsInfoController.spr?action=findPaymentImportInfo&type=view&paymentId='+records[0].json.PAYMENT_ID+param,
	'',
	'',
	{width:900,height:600});
	
}
//流程查看
function viewWorkflow(){
	var records = paymentImportsInfogrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].json.PAYMENT_ID,
		'',	'',	{width:900,height:600});
}
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
	
	var paymentImportsInfoPlant = Ext.data.Record.create([
		{name:'EXAMINE_STATE_D_EXAMINE_STATE'},
		{name:'PAYMENT_ID'},
		{name:'PICK_LIST_NO'},
		{name:'PAY_VALUE'},
		{name:'PAY_TYPE_D_PAY_TYPE_DPDA'}, 
		{name:'PAY_TIME'},
		{name:'PAY_DATE'},
		{name:'PAY_REAL_VALUE'}, 
		{name:'PAY_REAL_TIME'},
		{name:'APPLY_TIME'},
		{name:'APPROVED_TIME'},
		{name:'DEPT_NAME'},
		{name:'DEPT_DATE'},
		{name:'REAL_NAME'},
		{name:'DEPT_NAME'},
		{name:'CONTRACT_NO'},
		{name:'EKPO_TXZ01'},
		{name:'WRITE_LIST_NO'},
		{name:'PICK_LIST_REC_DATE'},
   		{name:'CREDIT_NO'},//
   		{name:'PICK_LIST_TYPE_D_PICK_LTYPE'},//
   		{name:'ISSUING_DATE'},//
   		{name:'ISSUING_BANK'},//
		{name:'CREATOR'},
		{name:'opera'}
	]);

	paymentImportsInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'paymentImportsInfoController.spr?action=queryImportInfo'}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},paymentImportsInfoPlant)
    });
    
    var paymentImportsInfosm = new Ext.grid.CheckboxSelectionModel();
    var renderOpera=function(value,metadata,record){
    	var status = record.data.EXAMINE_STATE_D_EXAMINE_STATE;
		return '<a href="#" onclick="view()"> 查看</a> <a href="#" onclick="viewWorkflow()"> 流程查看</a>';
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
			   header: '付款金额',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PAY_VALUE'
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
			   header: '核销单号',
	           width: 100,
	           sortable: true,
	           dataIndex: 'WRITE_LIST_NO'
	       },{
   			   header: '开证日期',
   	           width: 100,
   	           sortable: true,
   	           dataIndex: 'ISSUING_DATE'
   	       },{
   			   header: '开证行',
   	           width: 100,
   	           sortable: true,
   	           dataIndex: 'ISSUING_BANK'
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
			   header: '(即期/承兑到期)付款日',
	           width: 130,
	           sortable: true,
	           dataIndex: 'PAY_TIME'
	       },{
			   header: '押汇到期付款日',
	           width: 120,
	           sortable: true,
	           dataIndex: 'PAY_DATE'
	       },{
			   header: '付款类型',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PAY_TYPE_D_PAY_TYPE_DPDA'
	       },{
			   header: '实际付款日',
	           width: 100,
	           sortable: true,
	           dataIndex: 'PAY_REAL_TIME'
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
    
    var print = new Ext.Toolbar.Button({
   		text:'打印',
	    iconCls:'find',
		handler:function(){
   				if (paymentImportsInfogrid.selModel.hasSelection()){
					var records = paymentImportsInfogrid.selModel.getSelections();
					if (records.length > 1){
						top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
					}else{
					    if(records[0].json.EXAMINE_STATE_D_EXAMINE_STATE.indexOf('特批')>=0){
					      window.open('paymentImportsInfoController.spr?action=dealParticularPrint&paymentId='+records[0].json.PAYMENT_ID,'_blank','');
					    }
					    else {
					      window.open('paymentImportsInfoController.spr?action=dealPrint&paymentId='+records[0].json.PAYMENT_ID,'_blank','');
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
    
    paymentImportsInfogrid = new Ext.grid.EditorGridPanel({
    	id:'paymentImportsInfoGrid',
        ds: paymentImportsInfods,
        cm: paymentImportsInfocm,
        sm: paymentImportsInfosm,
        bbar: paymentImportsInfobbar,
        border:false,
        tbar: itemTbar,
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
			title: "付款条件查询",
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
			title: "付款列表",
			items:[paymentImportsInfogrid]
		}]
	});
	
	var issuingDate = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 160,
	    readOnly:true,
		applyTo:'issuingDate'
   	});
	var pickListRecDate = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 160,
	    readOnly:true,
		applyTo:'pickListRecDate'
   	});
	var approvedTime = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 88,
	    readOnly:true,
		applyTo:'approvedTime'
   	});
   	var approvedTime1 = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 88,
	    readOnly:true,
		applyTo:'approvedTime1'
   	});
	var payTime = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 88,
	    readOnly:true,
		applyTo:'payTime'
   	});
   	var payTime1 = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 88,
	    readOnly:true,
		applyTo:'payTime1'
   	});
	var payRealTime = new Ext.form.DateField({
   		format:'Y-m-d',
		width: 160,
	    readOnly:true,
		applyTo:'payRealTime'
   	});
});

</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155" isMutilSelect="true"></fiscxd:dept>
<fiscxd:dictionary divId="pickListTypeDiv" fieldName="pickListType" dictionaryName="BM_PICK_LTYPE" width="153" ></fiscxd:dictionary>
