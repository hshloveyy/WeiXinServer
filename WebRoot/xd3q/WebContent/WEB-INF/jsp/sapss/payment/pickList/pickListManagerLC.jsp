<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%><%
String pickListType=request.getParameter("pickListType");
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>进口到单管理</title>
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
<form action="" id="findFrom" name="findFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门名称：</td>
	<td>
		<div id="dept"></div>
	</td>
	<td align="right">单号：</td>
	<td>
        <input type="hidden" name="paymentType" id="paymentType" value="<%=pickListType%>">
		<input type="hidden" name="tradeType" id="tradeType" value="<%=pickListType%>">	
		<input type="text" id="pickListNo" name="pickListNo" value=""></input>
	</td>
	<td align="right">合同号：</td>
	<td>
	    <input type="hidden" name="tradeType" id="tradeType" value="${pickListType}">	
		<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">信用证号：</td>
	<td>
	    <input type="text" id="lcNo" name="lcNo" value=""></input>
	</td>
	<td align="right">到单类型：</td>
	<td>
		<div id="pickListTypeDiv"></div>
	</td>
	<td align="right">SAP订单号：</td>
	<td>
		<input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">开证日期：</td>
	<td>
	    <input type="text" id="issuingDate" name="issuingDate" value=""></input>
	</td>
	<td align="right">开证行：</td>
	<td>
		<input type="text" id="issuingBank" name="issuingBank" value=""></input>
	</td>
	<td align="right">到单日：</td>
	<td>
		<div id="pickListRecDateDiv"></div>
	</td>
</tr>
<tr>
	<td align="right">提交时间：</td>
	<td>
		<div id="applyTimeDiv"></div>
	</td>
	<td align="right">审核通过时间：</td>
	<td>
		<div id="approvedTimeDiv"></div>
	</td>
	<td align="right">审批状态：</td>
	<td>
      <select name="examineState">
         <option value="">请选择</option>
         <option value="1">新增</option>
         <option value="2">审批</option>
         <option value="3">审批通过</option>
         <option value="4">审批不通过</option>
      </select>
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
document.onkeydown = function(){if (event.keyCode == 13){findPaymentImportsInfo();}}
//贸易类型
var pickListType = '<%=pickListType%>';
var paymentTitle = '';
var strPickListTitle ='';
var strOperType = '0';

var purchaserrecord;
var purchaserfieldName;

switch (pickListType) {
   case 'tt':
	   paymentTitle = 'TT';
       break;
   case 'dpda':
	   paymentTitle = 'DP/DA';
       break;
   case 'lc':
	   paymentTitle = 'LC';   
 }
strPickListTitle = paymentTitle;
var grid;		//出仓单信息列表
var ds;

function findPaymentImportsInfo(){
	var param = Form.serialize('findFrom');
    var requestUrl = 'pickListInfoController.spr?action=query&' + param;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;	
    ds.proxy= new Ext.data.HttpProxy({url:requestUrl});
    ds.load({params:{start:0, limit:10},arg:[]});
}

//自定义回调
function customCallBackHandle(transport){
	var responseCustomUtil = new AjaxResponseUtils(transport.responseText);
	var customField = responseCustomUtil.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	
	if (strOperType == '1'){
		ds.reload();
	}
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

     //增加
     var addButton = new Ext.Toolbar.Button({
         text: '增加',
         iconCls: 'add',
         handler: function(){
             top.ExtModalWindowUtil.show(paymentTitle + '到单管理', 'pickListInfoController.spr?action=addPickListInfo&pickListType=' + Ext.getDom('paymentType').value, '', funAddPickListCallBack, {
                 width: 900,
                 height: 600
             });
         }
     });
      //增加出仓单的回调函数
      function funAddPickListCallBack(){
		ds.reload(); 
      }  
   	
       //删除
       var delButton = new Ext.Toolbar.Button({
           text: '删除',
           iconCls: 'delete',
           handler: function(){
               if (grid.selModel.hasSelection()) {
                   var records = grid.selModel.getSelections();
                   if (records.length > 1) {
                       top.ExtModalWindowUtil.alert('提示', '一次只能删除一个出仓单信息！');
                   }
                   else if(records[0].data.EXAMINE_STATE_D_EXAMINE_STATE=='新增'){
                       top.Ext.Msg.show({
                           title: '提示',
                           msg: '是否确定删除记录',
                           buttons: {
                               yes: '是',
                               no: '否'
                           },
                           fn: function(buttonId, text){
                               if (buttonId == 'yes') {
                                   var param = "?action=delete";
                                   param = param + "&pickListId=" + records[0].data.PICK_LIST_ID;
                                   
                                   new AjaxEngine('pickListInfoController.spr', {
                                       method: "post",
                                       parameters: param,
                                       onComplete: callBackHandle
                                   });
                                   
                                   strOperType = '1';
                               }
                           },
                           icon: Ext.MessageBox.QUESTION
                       });
                   }else{
                	   top.Ext.Msg.alert('提示','非新增记录不能删除');
                   }
               }
               else {
                   top.ExtModalWindowUtil.alert('提示', '请选择要删除的出仓单信息！');
               }
           }
       });
    
    var tbar = new Ext.Toolbar({
		items:[addButton,'-',delButton]
	});
	
	var Plant = Ext.data.Record.create([
         	{name: 'PICK_LIST_ID'}, 
         	{name: 'CONTRACT_PURCHASE_ID'}, 
         	{name: 'TRADE_TYPE'},   
         	{name: 'PICK_LIST_TYPE'},   
         	{name: 'PICK_LIST_NO'}, 
         	{name: 'ISSUING_BANK'},             	         	         	            	
         	{name: 'LC_NO'}, 
         	{name: 'COLLECTION_BANK'}, 
         	{name: 'CONTRACT_NO'}, 
         	{name: 'SAP_ORDER_NO'}, 
         	{name: 'DP_DA_NO'},
         	{name: 'ISSUING_DATE'}, 
         	{name: 'SHIPMENT_PORT'}, 
         	{name: 'DESTINATION_PORT'}, 
         	{name: 'ARRIVAL_DATE'},   
         	{name: 'PICK_LIST_REC_DATE'},   
         	{name: 'SECURITY_PICK_DATE'}, 
         	{name: 'ACCEPTANCE_DATE'},             	         	         	            	
         	{name: 'PAY_DATE'}, 
         	{name: 'HAS_REC_WRITE'}, 
         	{name: 'WRITE_LIST_NO'}, 
         	{name: 'EXAMINE_ADVICE'}, 
         	{name: 'DEPT_ID'},
         	{name: 'APPLY_TIME'},
         	{name: 'APPROVED_TIME'}, 
         	{name: 'CMD'},
         	{name: 'IS_AVAILABLE'}, 
         	{name: 'CREATOR_TIME'}, 
         	{name: 'CREATOR'},
         	{name: 'EXAMINE_STATE'},               	           	
         	{name: 'DEPT_NAME'},
         	{name: 'REAL_NAME'}, 
         	{name: 'TYPE_NAME'},             
         	{name: 'EXAMINE_STATE_D_EXAMINE_STATE'}
	]);

    ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'pickListInfoController.spr?action=query&tradeType=' + pickListType
        }),
        reader: new Ext.data.JsonReader({
            root: 'root',
            totalProperty: 'totalProperty'
        }, Plant)
    });
    
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    var cm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
            {header: '提货单ID',
             width: 100,
             sortable: true,
             hidden: true,
             dataIndex: 'PICK_LIST_ID'
            },            
            {
 	           header: '操作',
 	           width: 100,
 	           sortable: true,
 	           dataIndex: 'EXPORTINFO',
 	           renderer: operaRD
 	           },{
 	               header: '审批状态',
 	               width: 100,
 	               sortable: true,
 	               dataIndex: 'EXAMINE_STATE_D_EXAMINE_STATE'
 	           }, 		
		   {header: '部门名称',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_NAME'
           },
           {header: '提交人',
           width: 100,
           sortable: true,
           dataIndex: 'REAL_NAME'
           // hidden:true
           },
		   {header: '单号',
           width: 100,
           sortable: true,
           dataIndex: 'PICK_LIST_NO'
           },
		   {header: '合同号',
           width: 100,
           sortable: false,
           dataIndex: 'CONTRACT_NO'
           },
           {header: '信用证号',
           width: 100,
           sortable: true,
           dataIndex: 'LC_NO'
           },
           {
           header: '到单类型',
           width: 100,
           sortable: true,
           dataIndex: 'TYPE_NAME'
           },
           {
           header: '开证日期',
           width: 100,
           sortable: true,
           dataIndex: 'ISSUING_DATE'
           },
           {
           header: '开证行',
           width: 100,
           sortable: true,
           dataIndex: 'ISSUING_BANK'
           },
           {
           header: '到单日',
           width: 100,
           sortable: true,
           dataIndex: 'PICK_LIST_REC_DATE'
           },
           {
           header: '提交时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_TIME'
           },
           {
           header: '审核通过时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPROVED_TIME'
           },
           {
           header: '付款日',
           width: 100,
           sortable: true,
           dataIndex: 'PAY_DATE'
           },
           {
                header: '创建者',
                width: 100,
                sortable: true,
                hidden: true,
                dataIndex: 'CREATOR'
            }
    ]);
    cm.defaultSortable = true;
    
    var bbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:ds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    grid = new Ext.grid.EditorGridPanel({
    	id:'grid',
        ds: ds,
        cm: cm,
        sm: sm,
        bbar: bbar,
        tbar: tbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    ds.load({params:{start:0, limit:10},arg:[]});
    
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: strPickListTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:150
		},
		{
			region:"center",
			layout:'fit',
		    split:true,
			collapsible: true,
			height:220,
			minSize: 210,
            maxSize: 400,
			title: strPickListTitle + "进口到单列表",
			items:[grid]
		}]
	});
	
	var pickListRecDate = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'pickListRecDate',
		name:'pickListRecDate',
		width: 160,
	    readOnly:true,
		renderTo:'pickListRecDateDiv'
   	});
   	

   	var applyTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'applyTime',
	    name:'applyTime',
		width: 160,
	    readOnly:true,
		renderTo:'applyTimeDiv'
   	});
   	
   	var approvedTime = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'approvedTime',
	    name:'approvedTime',
		width: 160,
	    readOnly:true,
		renderTo:'approvedTimeDiv'
   	});

});

	function operaRD(value,metadata,record){
		var state = record.data.EXAMINE_STATE;
		if('${loginer}'== record.data.CREATOR){
			if (state=='1'){    		
		   		return '<a href="#" onclick="operaForm();" >修改</a> <a href="#" style="color:red" onclick="operaForm()">提交</a>';
		  	 	}else{
		   		return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
		   	}		
		}else{
			return '<a href="#" onclick="viewForm()">查看</a>';
		}
	}
	
	function operaForm(pickListId){
		if (pickListId == null || pickListId == ''){
			var records = grid.selModel.getSelections();		
			pickListId = records[0].data.PICK_LIST_ID;
		}
		top.ExtModalWindowUtil.show(paymentTitle + '到单信息',
			'pickListInfoController.spr?action=addPickListInfoView&pickListId='+ pickListId + '&pickListType=' + Ext.getDom('paymentType').value,
			'',
			PickListInfocallbackFunction,
			{width:900,height:550});
	}	
	
	function viewForm(){
		var records = grid.selModel.getSelections();		
		var pickListId = records[0].data.PICK_LIST_ID;
		top.ExtModalWindowUtil.show(paymentTitle + '到单管理',
			'pickListInfoController.spr?action=viewPickListInfo&pickListId='+ pickListId + '&pickListType=' + Ext.getDom('paymentType').value,
			'',
			PickListInfocallbackFunction,
			{width:900,height:550});
	}
	
	function viewHistory(){
		var records = grid.selModel.getSelections();
		top.ExtModalWindowUtil.show('审批详情信息',
		'workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.PICK_LIST_ID,
		'',
		'',
		{width:900,height:600});	
	}
		
	function PickListInfocallbackFunction(){
		ds.reload();
	}	


</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
<fiscxd:dictionary divId="pickListTypeDiv" fieldName="pickListType" dictionaryName="BM_PICK_LIST_TYPE_LC" ></fiscxd:dictionary>
