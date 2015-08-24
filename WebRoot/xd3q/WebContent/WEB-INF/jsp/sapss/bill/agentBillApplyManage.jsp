<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>开票申请管理(服务税)</title>
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
		<td align="right">开票单编码：</td>
		<td><input type="text" id="exportApplyNo" name="exportApplyNo"
			value=""></input></td>
		<td align="right">SAP交货单编码：</td>
		<td><input type="text" id="sapReturnNo"
			name="sapReturnNo" value=""></input></td>
	</tr>
	<tr>
		<td align="right">合同编码：</td>
		<td><input type="text" id="contractNo" name="contractNo" value=""></input>
		</td>
		<td align="right">SAP订单号：</td>
		<td><input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
		</td>
		<td align="right">开票申请单编号：</td>
		<td><input type="text" id="billApplyNo" name="billApplyNo"
			value=""></input></td>
	</tr>
	<tr>
		<td align="right">客户名称：</td>
		<td><input type="text" id="cardCodeUnit" name="cardCodeUnit" value=""/></td>
		<td align="right">SAP开票凭证：</td>
		<td><input type="text" id="sapDocNo" name="sapDocNo" value=""/></td>
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
		<td align="right">纸质合同号</td>

		<td><input type="text" id="paperNo" name="paperNo" value=""/><input type="button" value="查找" onclick="findBillApply()"></input> <input type="reset" value="清空"></input>
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
//贸易类型
var tradeType = '${tradeType}';

var strBillApplyTitle ='';
var strOperType = '0';

var BillApplyRecord;

strBillApplyTitle = Utils.getTradeTypeTitle(tradeType);

var billApplygrid;		//开票申请单信息列表
var billApplyds;

//BillApply 查找按钮的单击事件
function findBillApply(){
	var sDate= Ext.getDom("sDate").value;
    var eDate= Ext.getDom("eDate").value;
    
	var param = Form.serialize('findBillApplyFrom');
	var requestUrl = 'billApplyController.spr?action=queryBillApply&' + param;
	requestUrl = requestUrl + "&tradeType=" + tradeType;
	requestUrl = requestUrl + "&billType=2";
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
    
    //增加
    var addBillApply = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('新增开票申请单',
			'billApplyController.spr?action=newAgentBillApplyCreate&isProduct=1&tradeType='+tradeType,
			'',
			funAddBillApplyCallBack,
			{width:900,height:550});
		}
   	});
      	
   	//删除
   	var deleteBillApply = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (billApplygrid.selModel.hasSelection()){
				var records = billApplygrid.selModel.getSelections();
				
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个开票申请单信息！');
				}else{
					if (records[0].data.EXAMINE_STATE != "1" )
						top.ExtModalWindowUtil.alert('提示','不能删除已送审的单据！');
					else					
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除选择的记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=delete";
								param = param + "&billApplyId=" + records[0].data.BILL_APPLY_ID;

								new AjaxEngine('billApplyController.spr', 
						   			{method:"post", parameters: param, onComplete: DeleteBillApplyCallbackFunction});
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的开票申请单信息！');
			}
		}
   	});
    
    var billApplytbar = new Ext.Toolbar({
		items:[addBillApply,'-',deleteBillApply]
	});
	
	var BillApplyPlant = Ext.data.Record.create([
		{name:'BILL_APPLY_ID'},  				//开票申请ID
		{name:'BILL_APPLY_NO'},  				//开票申请单号
		{name:'CONTRACT_SALES_ID'},
		{name:'CONTRACT_NO'},
		{name:'TAX_NO'},
		{name:'BILL_TO_PARTY'},
		{name:'BILL_TO_PARTY_NAME'},
		{name:'SAP_ORDER_NO'},
		{name:'SAP_RETURN_NO'},
		{name:'BILL_TYPE'},
		{name:'BILL_TIME'},
		{name:'QUANTITY_TOTAL'},
		{name:'TAX_TOTAL'},
		{name:'LOAN_TOTAL'},
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
		{name:'EXPORT_APPLY_NO'},
		{name:'REAL_NAME'},
		{name:'DEPT_NAME'},
		{name:'BILLAPPLYINFO'}
	]);

	billApplyds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'billApplyController.spr?action=queryBillApply&billType=2&tradeType='+tradeType}),
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
           width: 100,
           sortable: true,
           dataIndex: 'BILL_APPLY_NO'
           },
           {header: '客户编码',
           width: 100,
           sortable: true,
           dataIndex: 'BILL_TO_PARTY'
           },
           {header: '客户名称',
           width: 150,
           sortable: true,
           dataIndex: 'BILL_TO_PARTY_NAME'
           },           
           {header: 'SAP开票凭证',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_BILL_NO',
           hidden: true
           },
           {header: '出仓单编码',
           width: 150,
           sortable: true,
           dataIndex: 'EXPORT_APPLY_NO'
           },
           {header: 'SAP交货单编码',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_RETURN_NO'
           },
           {header: '合同编码',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_NO'
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
        tbar: billApplytbar,
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
			title: strBillApplyTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:133
		},{
			region:"center",
			layout:'fit',
			title: strBillApplyTitle + "开票申请单(服务税)列表",
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
	var state = record.data.EXAMINE_STATE;
	if('${loginer}'== record.data.CREATOR){
		if (state=='1'){    		
	   		return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" style="color:red" onclick="submitForm()">提交</a>';
	  	 	}else{
	   		return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	   	}		
	}else{
		return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	}
}

//流程跟踪
function viewHistory()
{
	var records = billApplygrid.selModel.getSelections();
//	alert(records[0].data.BILL_APPLY_ID);
	top.ExtModalWindowUtil.show(strBillApplyTitle + '审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.BILL_APPLY_ID,
		'',	'',	{width:880,height:400});
}

function viewForm(billApplyId){
	if (billApplyId == null || billApplyId == ''){
		var records = billApplygrid.selModel.getSelections();		
		billApplyId = records[0].data.BILL_APPLY_ID;
	}

	top.ExtModalWindowUtil.show(strBillApplyTitle + '开票申请单信息',
		'billApplyController.spr?action=addAgentBillApplyView&billApplyId='+ billApplyId +"&tradeType="+ tradeType +"&operType=001",
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
   
function operaForm(billApplyId){
	if (billApplyId == null || billApplyId == ''){
		var records = billApplygrid.selModel.getSelections();		
		billApplyId = records[0].data.BILL_APPLY_ID;
	}

	top.ExtModalWindowUtil.show(strBillApplyTitle + '开票申请单信息',
		'billApplyController.spr?action=addAgentBillApplyView&billApplyId='+ billApplyId +"&tradeType="+ tradeType+ "&operType=101",
		'',
		funBillApplyCallbackFunction,
		{width:900,height:550});
}
   
function submitForm(billApplyId){
	if (billApplyId == null || billApplyId == ''){
		var records = billApplygrid.selModel.getSelections();		
		billApplyId = records[0].data.BILL_APPLY_ID;
	}

	top.ExtModalWindowUtil.show(strBillApplyTitle + '开票申请单信息',
		'billApplyController.spr?action=addAgentBillApplyView&billApplyId='+ billApplyId +"&tradeType="+ tradeType+  "&operType=011",
		'',
		funBillApplyCallbackFunction,
		{width:900,height:550});
}
--></script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
