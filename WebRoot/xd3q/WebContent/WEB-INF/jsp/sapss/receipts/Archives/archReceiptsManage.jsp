<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@page import="com.infolion.sapss.ReceiptShipConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收货管理</title>
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
<form action="" id="findreceiptsFrom" name="findreceiptsFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门名称：</td>
	<td>
		<div id="dept"></div>
	</td>
	<td align="right">立项号：</td>
	<td>
		<input type="text" id="projectName" name="projectName" value=""></input>
		<input type="hidden" id="projectNo" name="projectNo" value=""></input>
		<input type="hidden" id="isReturn" name="isReturn" value="${isReturn }"></input>
	</td>
	<td align="right">批次号：</td>
	<td>
		<input type="text" id="batchNo" name="batchNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">合同编码：</td>
	<td>
		<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
	<td align="right">SAP订单号：</td>
	<td>
		<input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
	</td>
	<td align="right">进仓单编号/序号：</td>
	<td>
		<input type="text" id="receiptNo" name="receiptNo" value=""></input>
	</td>
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
    <td align="right">物料凭证号：</td>
	<td>
		<input type="text" id="sapReturnNo" name="sapReturnNo" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">贸易方式：</td>
	<td>
	<div id="div_tradeType"></div>
	</td>
	<td align="right">外合同号：</td>
	<td>
		<input type="text" id="paperNo" name="paperNo" value=""></input>
	</td>
	<td align="right">是否有效：</td>
	<td>
		<select name="isAvailable" id="isAvailable"><option value="1" selected="selected">有效</option><option value="0">无效</option></select>
		<input type="button" value="查找" onclick="findreceiptsInfo()"></input>
		<input type="reset" value="清空"></input></nobr>
	</td>
</tr>
<tr>
	<td align="right">审批结束日期从：</td>
	<td>
		<table><tr><td><div id="sDateDiv1"></div></td><td>-</td><td><div id="eDateDiv1"></div></td></tr></table>
	</td>
	<td align="right">仓库：</td>
	<td>
		<select name="wareHouse" id="wareHouse">
		<option value="">请选择</option>
		<c:forEach items="${warehouse}" var="row">
			<option value="${row.code}">${row.code}-${row.title}</option>
		</c:forEach>
	    </select>
	</td>
    <td align="right">审批状态:</td>
	<td>
	   <select id="examState" name="examState">
	      <option value="">请选择</option>
	      <option value="1">新增</option>
	      <option value="2">审批</option>
	      <option value="3">审批通过</option>
	      <option value="4">审批未通过</option>
	      <%if(!ReceiptShipConstants.ins().isShouldHide()){ %>
	      <option value="5">二次结算</option>
	      <%} %>
	   </select>
		
	</td>
<tr>
</table>
</form>
</div>

<div id="div_center"></div>

<div id="div_south"></div>
</body>
</html>
<script type="text/javascript"><!--
document.onkeydown = function(){if (event.keyCode == 13){findreceiptsInfo();}}
//贸易类型
var tradeType = '${tradeType}';

var strreceiptsTitle ='';
var strOperType = '0';

var receiptsRecord;
var purchaserrecord;
var purchasefrieldName;
strreceiptsTitle = Utils.getTradeTypeTitle(tradeType);


var receiptsInfogrid;		//进仓单信息列表
var receiptsInfods;

//receipts 查找按钮的单击事件
function findreceiptsInfo(){
	var sDate= Ext.getDom("sDate").value;
    var eDate= Ext.getDom("eDate").value;
	var param = Form.serialize('findreceiptsFrom');
	var requestUrl = 'receiptsController.spr?action=query&' + param;
	requestUrl = requestUrl + "&tradeType=" + tradeType;
	requestUrl = requestUrl + '&deptId=' + selectId_dept;
	receiptsInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	receiptsInfods.load({params:{start:0, limit:10},arg:[]});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

   /*
    //TEST
   top.Ext.Msg.show({
			title:'提示',
			closable:false,
			msg:tradeType,
			buttons:{yes:'关闭'},
			fn:Ext.emptyFn,
			icon:Ext.MessageBox.INFO
	});
	*/
	
	//增加进仓单的回调函数
    function funAddreceiptsCallBack(){
    	receiptsInfods.reload();
    }
    
    //增加
    var addreceiptsInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('新增入库申请单',
			'receiptsController.spr?action=create&tradeType='+tradeType,
			'',
			funAddreceiptsCallBack,
			{width:900,height:600});
		}
   	});
   	
   	//删除
   	var deletereceiptsInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (receiptsInfogrid.selModel.hasSelection()){
				var records = receiptsInfogrid.selModel.getSelections();
				
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个进仓单信息！');
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
								param = param + "&receiptId=" + records[0].data.RECEIPT_ID;

								new AjaxEngine('receiptsController.spr', 
						   			{method:"post", parameters: param, onComplete: DeleteReceiptCallbackFunction});
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的进仓单信息！');
			}
		}
   	});
    
    var receiptsInfotbar = new Ext.Toolbar({
		//items:[addreceiptsInfo,'-',deletereceiptsInfo]
		items:['-']
	});
	
	var receiptsInfoPlant = Ext.data.Record.create([
		{name:'RECEIPT_ID'},  				//进仓ID
		{name:'SERIALNO'},  				//进仓单号
		{name:'RECEIPT_NO'},  				//进仓单号
		{name:'BILL_STATE'},  				//冲销标识
		{name:'SAP_RETURN_NO'},  				//进仓单号
		{name:'DEPT_ID'},                 	//部门代码
		{name:'DEPT_NAME'},  				//进仓单号
		{name:'PROJECT_NO'},            	//立项号
		{name:'PROJECT_NAME'},            	//立项名称
		{name:'EKKO_UNSEZ'},         //合同组编码
		{name:'CONTRACT_NO'},           	//合同编码
		{name:'SAP_ORDER_NO'},             	//SAP订单号
		{name:'APPLY_TIME'},             	//申报日期
	    {name:'CREATOR'},             		//创建者
	    {name:'REAL_NAME'},             	//创建者姓名
	    {name:'WAREHOUSE'},             	//仓库
	    {name:'WAREHOUSE_ADD'},             //仓库地址
		{name:'CREATOR_TIME'},
	    {name:'IS_AVAILABLE'},
		{name:'APPROVED_TIME'},
		{name:'EXAMINE_STATE'},
		{name:'RSEXAM_STATE_D_RSEXAM_STATE'},              
		{name:'EXPORTINFO'}  
	]);

	receiptsInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'receiptsController.spr?action=query&isReturn=${isReturn}&tradeType='+tradeType}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},receiptsInfoPlant)
    });
    
    var receiptsInfosm = new Ext.grid.CheckboxSelectionModel();    
    var receiptsInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		receiptsInfosm,
		　 {header: '进仓ID',
           width: 100,
           sortable: true,
           dataIndex: 'RECEIPT_ID',
           hidden:true
           },{           
               header: '操作',
               width: 100,
               sortable: true,
               dataIndex: 'EXPORTINFO',
               renderer: operaRD
          },{
               header: '状态',
               width: 50,
               sortable: true,
               dataIndex: 'RSEXAM_STATE_D_RSEXAM_STATE'
          },{
               header: '冲销标识',
               width: 60,
               sortable: true,
               dataIndex: 'BILL_STATE',
               renderer: operaRD1
          },{                       
   		　  header: '序号',
           width: 100,
           sortable: true,
           dataIndex: 'SERIALNO'
           },
           {                       
   		　  header: '进仓单编号',
           width: 100,
           sortable: true,
           dataIndex: 'RECEIPT_NO'
           },
           {header: '物料凭证号',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_RETURN_NO'
           },
           {header: '部门代码',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_ID',
           hidden: true
           },
           {header: '部门名称',
           width: 100,
           sortable: true,
           dataIndex: 'DEPT_NAME'
           },
           {header: '立项号',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_NO'
           },
           {header: '立项名称',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_NAME'
           },
           {
           header: '纸质合同号',
           width: 100,
           sortable: true,
           dataIndex: 'EKKO_UNSEZ'
           },
           {
           header: '合同编码',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_NO'
           },
           {
           header: 'SAP订单编号',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_ORDER_NO'
           },
           {
           header: '仓库',
           width: 100,
           sortable: true,
           dataIndex: 'WAREHOUSE'
           },
           {
           header: '仓库地址',
           width: 100,
           sortable: true,
           dataIndex: 'WAREHOUSE_ADD'
           },
           {
           header: '申报时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_TIME'
           }, 
           {
           header: '审批时间',
           width: 100,
           sortable: true,
           dataIndex: 'APPROVED_TIME'
           },  
           {
           header: '创建时间',
           width: 100,
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
    receiptsInfocm.defaultSortable = true;
    
    var receiptsInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:receiptsInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    receiptsInfogrid = new Ext.grid.EditorGridPanel({
    	id:'receiptsInfoGrid',
        ds: receiptsInfods,
        cm: receiptsInfocm,
        sm: receiptsInfosm,
        bbar: receiptsInfobbar,
        tbar: receiptsInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    receiptsInfods.load({params:{start:0, limit:10},arg:[]});
    
 //receiptsInfogrid.addListener('rowclick', receiptsInfogridrowclick);

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: strreceiptsTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:160
		},{
			region:"center",
			layout:'fit',
			title: strreceiptsTitle + "进仓单列表",
			items:[receiptsInfogrid]
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
   	
   	var sDate1 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'sDate1',
		name:'sDate1',
		width: 90,
	    readOnly:true,
		renderTo:'sDateDiv1'
   	});
   	
   	var eDate1 = new Ext.form.DateField({
   		format:'Y-m-d',
		id:'eDate1',
	    name:'eDate1',
		width: 90,
	    readOnly:true,
		renderTo:'eDateDiv1'
   	});
   	
   	var wareHouse_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'wareHouse',
        width:157,
        allowBlank:false,
        blankText:'请输入仓库',
        forceSelection:true
     });

});

function operaRD(value,metadata,record){
	var state = record.data.EXAMINE_STATE;
	if('${loginer}'== record.data.CREATOR){
		if (state=='1'){    		
	   		return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	   	}		
	}else{
		if (state=='1'&&('${deptCode}'!='ZH1'&&'${deptCode}'!='XDAZH'&&'${deptCode}'!='XDNZH'&&'${deptCode}'!='WM'))
			return '<a href="#" onclick="viewForm()">查看</a>';
		else if('${deptCode}'=='ZH1'||'${deptCode}'=='XDAZH'||'${deptCode}'=='XDNZH'||'${deptCode}'=='WM'||'${deptCode}'=='SYZH')
	        return '<a href="#" onclick="printForm()">打印</a> <a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
		else
			return '<a href="#" onclick="viewForm()">查看</a> <a href="#" onclick="viewHistory()">流程跟踪</a>';
	}
}
function operaRD1(value,metadata,record){
	var state = record.data.BILL_STATE;
	if(state=='1'){   		
	    return '冲销';
	}
}
function printForm(){
   var records = receiptsInfogrid.selModel.getSelections();	
    var receiptId = records[0].data.RECEIPT_ID;
    top.ExtModalWindowUtil.show('进仓单打印',
		'receiptsController.spr?action=preDealPrint&receiptId='+ receiptId ,
		'',
		'',
		{width:400,height:85});
    //window.open('shipController.spr?action=dealPrint&shipId='+shipId,'_blank','location=no,resizable=yes');
}
//流程跟踪
function viewHistory()
{
	var records = receiptsInfogrid.selModel.getSelections();
//	alert(records[0].data.RECEIPT_ID);
	top.ExtModalWindowUtil.show(strreceiptsTitle + '审批详情信息','workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.RECEIPT_ID+'&isRSHis=1',
		'',	'',	{width:880,height:400});
}

function viewForm(receiptId){
	if (receiptId == null || receiptId == ''){
		var records = receiptsInfogrid.selModel.getSelections();		
		receiptId = records[0].data.RECEIPT_ID;
	}

	top.ExtModalWindowUtil.show(strreceiptsTitle + '进仓单信息',
		'receiptsController.spr?action=addReceiptView&receiptId='+ receiptId + "&operType=001&specialdepartment=yes",
		'',
		'',
		{width:900,height:550});
}

//receipts 进仓单数据的回调函数
function DeleteReceiptCallbackFunction(transport){
	callBackHandle(transport);
	receiptsInfods.reload();
}

//增加进仓单的回调函数
function funReceiptsCallbackFunction(){
	receiptsInfods.reload();
}
   
function operaForm(receiptId){
	if (receiptId == null || receiptId == ''){
		var records = receiptsInfogrid.selModel.getSelections();		
		receiptId = records[0].data.RECEIPT_ID;
	}

	top.ExtModalWindowUtil.show(strreceiptsTitle + '进仓单信息',
		'receiptsController.spr?action=addReceiptView&receiptId='+ receiptId + "&operType=101",
		'',
		funReceiptsCallbackFunction,
		{width:900,height:550});
}
   
function submitForm(receiptId){
	if (receiptId == null || receiptId == ''){
		var records = receiptsInfogrid.selModel.getSelections();		
		receiptId = records[0].data.RECEIPT_ID;
	}

	top.ExtModalWindowUtil.show(strreceiptsTitle + '进仓单信息',
		'receiptsController.spr?action=addReceiptView&receiptId='+ receiptId +  "&operType=011",
		'',
		funReceiptsCallbackFunction,
		{width:900,height:550});
}
--></script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155" isMutilSelect="true"></fiscxd:dept>
<fiscxd:dictionary divId="div_tradeType" fieldName="tradeType" dictionaryName="BM_BUSINESS_TYPE" width="153"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_examState" fieldName="examState" dictionaryName="BM_EXAMINE_STATE" width="153"></fiscxd:dictionary>