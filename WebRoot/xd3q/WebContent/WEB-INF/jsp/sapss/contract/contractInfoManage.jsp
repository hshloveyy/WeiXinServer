<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
.x-grid-row-bgcolor{  
    background-color:#ffffcc;
}
</style>
</head>
<body>
<div id="div_northForm">
<form action="" id="findContractFrom" name="findContractFrom">
<table width="100%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
<tr>
	<td align="right">部门名称：</td>
	<td>
	<div id="dept"></div>
	</td>
	
	<td align="right">SAP订单号：</td>
	<td>
	<input type="text" id="sapOrderNo" name="sapOrderNo" value=""></input>
	</td>
	<td align="right">客户/供应商名称</td>
	<td>
	<input type="text" id="unitName" name="unitName" value=""></input>
	</td>
</tr>
<tr>
	<td align="right">合同编码：</td>
	<td>
	<input type="text" id="contractNo" name="contractNo" value=""></input>
	</td>
	<td align="right">外合同号：</td>
	<td>
	<input type="text" id="outContractNo" name="outContractNo" value=""></input>
	</td>
	<td align="right"></td>
	<td>
	
	</td>
</tr>
<tr>
	
	<td align="right">立项名称：</td>
	<td>
	<input type="text" id="projectName" name="projectName" value=""></input>
	</td>
	<td align="right">物料名称：</td>
	<td>
	<input type="text" id="className" name="className" value=""></input>
	</td>
	<td>
	<input type="hidden" name="tradetype" id="tradetype" value="${contracttype}"/>
	<input type="button" value="查找" onclick="findContractInfo()"></input>
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
document.onkeydown = function(){if (event.keyCode == 13){findContractInfo();}}
var strContractType = '${contracttype}';	//合同类型
var strContractTitle ='';
var strOperType = '0';

var purchaserrecord;
var purchaserfieldName;

strContractTitle = Utils.getTradeTypeTitle(strContractType);

var contractInfogrid;		//合同期本信息列表
var contractInfods;
var contractgrid;			//合同列表
var contractds;

//项目查询回调函数
function selectProjectInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();

	document.findContractFrom.projectName.value = returnvalue.PROJECT_NAME;
	document.findContractFrom.projectId.value = returnvalue.PROJECT_ID;
}

//查询项目信息事件
function findProjectInfo(){
	top.ExtModalWindowUtil.show('查询所属登陆员工总门的立项信息',
	'contractController.spr?action=selectProjrctInfo&deptid=' + selectId_dept,
	'',
	selectProjectInfoCallBack,
	{width:560,height:300});
}

//查找按钮的单击事件
function findContractInfo(){
	var requestUrl = 'contractController.spr?action=selectContractGroup&';
	requestUrl += Form.serialize('findContractFrom');
	requestUrl = requestUrl + '&deptid=' + selectId_dept;

	contractInfods.proxy= new Ext.data.HttpProxy({url:requestUrl});
	contractInfods.load({params:{start:0, limit:10},arg:[]});
	
	
	if (document.findContractFrom.contractNo.value != '' ||
		document.findContractFrom.outContractNo.value != '' ||
		document.findContractFrom.sapOrderNo.value != ''){
		var requestUrl2 = 'contractController.spr?action=selectContractByCondition';
		requestUrl2 = requestUrl2 + '&deptid=' + selectId_dept;
		requestUrl2 = requestUrl2 + '&projectid=' + document.findContractFrom.projectId.value;
		requestUrl2 = requestUrl2 + '&contractgroupno=' + document.findContractFrom.contractGroupNo.value;
		requestUrl2 = requestUrl2 + '&contractno=' + document.findContractFrom.contractNo.value;
		requestUrl2 = requestUrl2 + '&outcontractno=' + document.findContractFrom.outContractNo.value;
		requestUrl2 = requestUrl2 + '&saporderno=' + document.findContractFrom.sapOrderNo.value;
		requestUrl2 = requestUrl2 + '&tradetype=' + strContractType;
	
		contractds.proxy= new Ext.data.HttpProxy({url:requestUrl2});
		contractds.load({params:{start:0, limit:10},arg:[]});
	}
}

function SalecallbackFunction(){
	/*
	var contractsalesid = purchaserrecord.get("CONTRACT_ID");
	var requestUrl = 'contractController.spr?action=querySalesBySalesId&contractsalesid='+contractsalesid;
	alert('sss');
	Ext.Ajax.request({
		url: requestUrl,
		success: function(response, options){
			var responseArray = Ext.util.JSON.decode(response.responseText);
			
			purchaserrecord.set('CONTRACT_NAME',responseArray.CONTRACT_NAME);
			purchaserrecord.set('OUT_CONTRACT_NO',responseArray.OUT_CONTRACT_NO);
			purchaserrecord.set('APPLY_TIME',responseArray.APPLY_TIME);
			purchaserrecord.set('ORDER_STATE_D_ORDER_STATE',responseArray.ORDER_STATE_D_ORDER_STATE);
	
			contractds.commitChanges();
		},
		failure:function(response, options){
			//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
		}
	});
	*/
	contractds.reload();
}

function BuycallbackFunction(){
	/*
	var contractpurchaserid = purchaserrecord.get("CONTRACT_ID");

	var requestUrl = 'contractController.spr?action=queryPurchaserByPurchaseId&contractpurchaserid='+contractpurchaserid;
	
	Ext.Ajax.request({
		url: requestUrl,
		success: function(response, options){
			var responseArray = Ext.util.JSON.decode(response.responseText);

			purchaserrecord.set('CONTRACT_NAME',responseArray.CONTRACT_NAME);
			purchaserrecord.set('OUT_CONTRACT_NO',responseArray.OUT_CONTRACT_NO);
			purchaserrecord.set('APPLY_TIME',responseArray.APPLY_TIME);
			purchaserrecord.set('ORDER_STATE_D_ORDER_STATE',responseArray.ORDER_STATE_D_ORDER_STATE);
	
			contractds.commitChanges();
		},
		failure:function(response, options){
			//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
		}
	});*/
	contractds.reload();
}

function openContractWin(){
	var records = contractgrid.selModel.getSelections();
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '采购合同信息',
			'contractController.spr?action=addPurchaseContractView&contractid='+records[0].data.CONTRACT_ID+'&contractType='+records[0].data.CONTRACT_TYPE,
			'',
			BuycallbackFunction,
			{width:900,height:550});
	}
	
	if (records[0].data.CONTRACT_TYPE.trim() == '代理出口协议'){
		top.ExtModalWindowUtil.show('代理出口协议',
			'contractController.spr?action=addPurchaseContractView&contractid='+records[0].data.CONTRACT_ID+'&contractType='+records[0].data.CONTRACT_TYPE,
			'',
			BuycallbackFunction,
			{width:900,height:550});
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '销售合同信息',
		'contractController.spr?action=addSaleContractView&contractid='+records[0].data.CONTRACT_ID+'&contractType='+records[0].data.CONTRACT_TYPE,
		'',
		SalecallbackFunction,
		{width:900,height:550});
	}
}
/**
 * 直接写SAP,链接合同变更
 */
function changeShortContract(){
	var records = contractgrid.selModel.getSelections();
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '采购合同信息',
			'contractController.spr?action=ArchPurchaseInfoView&from=changeW&shortSAP=true&businessRecordId='+records[0].data.CONTRACT_ID,
			'',
			BuycallbackFunction,
			{width:900,height:550});
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '销售合同信息',
			'contractController.spr?action=archSalesInfoView&from=changeW&shortSAP=true&businessRecordId='+records[0].data.CONTRACT_ID,
			'',
			SalecallbackFunction,
			{width:900,height:550});
	}
}

/**
 * 链接合同变更
 */
function changeContract(){
	var records = contractgrid.selModel.getSelections();
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		_getMainFrame().maintabs.addPanel(strContractTitle + '采购合同信息','', 'contractController.spr?action=ArchPurchaseInfoView&from=changeW&businessRecordId='+records[0].data.CONTRACT_ID);
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		_getMainFrame().maintabs.addPanel(strContractTitle + '销售合同信息','', 'contractController.spr?action=archSalesInfoView&from=changeW&businessRecordId='+records[0].data.CONTRACT_ID);
	}
}
/**
 * 链接合同变更查看
 */
function changeContractView(){
	var records = contractgrid.selModel.getSelections();
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '采购合同信息',
			'contractController.spr?action=ArchPurchaseInfoView&from=changeR&businessRecordId='+records[0].data.CONTRACT_ID,
			'','',
			{width:900,height:550});
	}
	
	if (records[0].data.CONTRACT_TYPE.trim() == '代理出口协议'){
		top.ExtModalWindowUtil.show('代理出口协议',
			'contractController.spr?action=ArchPurchaseInfoView&from=changeR&businessRecordId='+records[0].data.CONTRACT_ID,
			'','',
			{width:900,height:550});
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '销售合同信息',
		'contractController.spr?action=archSalesInfoView&from=changeR&businessRecordId='+records[0].data.CONTRACT_ID,
		'','',
		{width:900,height:550});
	}
}
/**
 * 变更通过合同查看
 */
function changedContractView(){
	var records = contractgrid.selModel.getSelections();
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '采购合同信息',
			'contractController.spr?action=ArchPurchaseInfoView&from=changeW&businessRecordId='+records[0].data.CONTRACT_ID,
			'','',
			{width:900,height:550});
	}
	
	if (records[0].data.CONTRACT_TYPE.trim() == '代理出口协议'){
		top.ExtModalWindowUtil.show('代理出口协议',
			'contractController.spr?action=ArchPurchaseInfoView&from=changeW&businessRecordId='+records[0].data.CONTRACT_ID,
			'','',
			{width:900,height:550});
	}
	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '销售合同信息',
		'contractController.spr?action=archSalesInfoView&from=changeW&businessRecordId='+records[0].data.CONTRACT_ID,
		'','',
		{width:900,height:550});
	}
}
/**
 * 未经审批写入SAP变更通过查看
 */
function changedShortSAPContractView(){
	var records = contractgrid.selModel.getSelections();
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '采购合同信息',
			'contractController.spr?action=ArchPurchaseInfoView&from=changeW&shortSAP=true&businessRecordId='+records[0].data.CONTRACT_ID,
			'','',
			{width:900,height:550});
	}
    if (records[0].data.CONTRACT_TYPE.trim() == '代理出口协议'){
		top.ExtModalWindowUtil.show('代理出口协议',
			'contractController.spr?action=ArchPurchaseInfoView&from=changeW&shortSAP=true&businessRecordId='+records[0].data.CONTRACT_ID,
			'','',
			{width:900,height:550});
	}
   
	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '销售合同信息',
		'contractController.spr?action=archSalesInfoView&from=changeW&shortSAP=true&businessRecordId='+records[0].data.CONTRACT_ID,
		'','',
		{width:900,height:550});
	}
}

/**
 *  变更合同工作流查看
 */
function changeWorkFlowView(){
	var records = contractgrid.selModel.getSelections();
	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		if(records[0].data.ORDER_STATE_D_ORDER_STATE=='变更' || records[0].data.ORDER_STATE_D_ORDER_STATE=='未经审批变更'){
			top.ExtModalWindowUtil.show(strContractTitle + '审批详情信息',
				'saleChangeController.spr?action=toWorkflowPictureViw&businessRecordId='+records[0].data.CONTRACT_ID,
				'','',
				{width:900,height:365});	
		}else{
			top.ExtModalWindowUtil.show(strContractTitle + '审批详情信息',
					'saleChangeController.spr?action=toWorkflowPictureViw&state=finish&businessRecordId='+records[0].data.CONTRACT_ID,
					'','',
					{width:900,height:365});	
			
		}
	}else if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		if(records[0].data.ORDER_STATE_D_ORDER_STATE=='变更' || records[0].data.ORDER_STATE_D_ORDER_STATE=='未经审批变更'){
			top.ExtModalWindowUtil.show(strContractTitle + '审批详情信息',
				'purchaseChangeController.spr?action=toWorkflowPictureViw&businessRecordId='+records[0].data.CONTRACT_ID,
				'','',
				{width:900,height:365});	
		}else{
			top.ExtModalWindowUtil.show(strContractTitle + '审批详情信息',
					'purchaseChangeController.spr?action=toWorkflowPictureViw&state=finish&businessRecordId='+records[0].data.CONTRACT_ID,
					'','',
					{width:900,height:365});	
			
		}
	}
}

function openContractWinByReadOnly(){
	var records = contractgrid.selModel.getSelections();
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '采购合同信息',
			'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_ID,
			'','',
			{width:900,height:550});
	}
	
	if (records[0].data.CONTRACT_TYPE.trim() == '代理出口协议'){
		top.ExtModalWindowUtil.show('代理出口协议',
			'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_ID,
			'','',
			{width:900,height:550});
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show(strContractTitle + '销售合同信息',
		'contractController.spr?action=archSalesInfoView&businessRecordId='+records[0].data.CONTRACT_ID,
		'','',
		{width:900,height:550});
	}
}
//打开特批页面
function openSubmitParticularWorkflow(){
	var records = contractgrid.selModel.getSelections();
	
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show('特批采购合同信息',
			'contractController.spr?action=openSubmitParticularWorkflow&view=ArchPurchaseInfoView&bizId='+records[0].data.CONTRACT_ID,
			'',BuycallbackFunction,
			{width:900,height:550});
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show('特批销售合同信息',
		'contractController.spr?action=openSubmitParticularWorkflow&view=archSalesInfoView&bizId='+records[0].data.CONTRACT_ID,
		'',SalecallbackFunction,
		{width:900,height:550});
	}
}
function openContractWinByAdd(lx,contractId,contractType){
	var records = contractgrid.selModel.getSelections();
	
	if (lx == '2'){
		top.ExtModalWindowUtil.show(strContractTitle + '采购合同信息',
			'contractController.spr?action=addPurchaseContractView&contractid='+ contractId+'&contractType='+contractType,
			'',
			BuycallbackFunction,
			{width:900,height:550});
	}

	if (lx == '1'){
		top.ExtModalWindowUtil.show(strContractTitle + '销售合同信息',
		'contractController.spr?action=addSaleContractView&contractid='+ contractId+'&contractType='+contractType,
		'',
		SalecallbackFunction,
		{width:900,height:550});
	}
}
function businessAllProcessRecords(){
	var records = contractgrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息',
	'workflowController.spr?action=businessAllProcessRecords&businessRecordId='+records[0].data.CONTRACT_ID,
	'',
	'',
	{width:800,height:500});	
}

function openWorkFlowWin(){
	var records = contractgrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息',
	'workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.CONTRACT_ID,
	'',
	'',
	{width:900,height:365});	
}
//

//删除合同组的回调函数
function customCallBackHandle(transport){
	var responseCustomUtil = new AjaxResponseUtils(transport.responseText);
	var customField = responseCustomUtil.getCustomField("coustom");
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	
	if (strOperType == '1'){
		contractInfods.reload();
		contractds.reload();
	}
	
	if (strOperType == '2'){
		contractds.reload();
	}
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	//增加合同组的回调函数
    function funAddContractGroupCallBack(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	
    	var contractinfopt = new contractInfoPlant({
             CONTRACT_GROUP_ID: returnvalue.contractGroupId,
             CONTRACT_GROUP_NAME: returnvalue.contractGroupName,
             CONTRACT_GROUP_NO: returnvalue.contractGroupNo,
             CMD: returnvalue.cmd,
             PROJECT_ID: returnvalue.projectId,
             PROJECT_NAME: returnvalue.projectName,
             CREATE_TIME: returnvalue.createTime,
             USER_NAME: returnvalue.creator
         });
        contractInfogrid.stopEditing();
        contractInfods.insert(0, contractinfopt);
        contractInfogrid.startEditing(0, 0);
    }
    
    var addContractInfo = new Ext.Toolbar.Button({
   		text:'增加',
	    iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('增加合同组',
			'contractController.spr?action=contractGroupManageView&tradeType='+strContractType,
			'',
			funAddContractGroupCallBack,
			{width:350,height:180});
		}
   	});
   	
   	var deleteContractInfo = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (contractInfogrid.selModel.hasSelection()){
				var records = contractInfogrid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个合同组信息！');
				}else{
					if(contractds.getCount()==0){
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								var param = "?action=deleteContractGroup";
								param = param + "&contractgroupid=" + records[0].data.CONTRACT_GROUP_ID;

								new AjaxEngine('contractController.spr', 
						   			{method:"post", parameters: param, onComplete: callBackHandle});
						   			
						   		strOperType = '1';
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
					}else{
						top.ExtModalWindowUtil.alert('提示','合同组已有下挂合同,不能删除');
						}
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的合同组信息！');
			}
		}
   	});
    
    var contractInfotbar = new Ext.Toolbar({
		items:[addContractInfo,'-',deleteContractInfo]
	});
	
	var contractInfoPlant = Ext.data.Record.create([
		{name:'CONTRACT_GROUP_ID'},
		{name:'CONTRACT_GROUP_NAME'},
		{name:'CONTRACT_GROUP_NO'},
		{name:'CMD'},
		{name:'PROJECT_ID'},
		{name:'PROJECT_NAME'},
		{name:'CREATE_TIME'},
		{name:'REAL_NAME'}
	]);

	contractInfods = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:'contractController.spr?action=selectContractGroup&tradetype='+strContractType}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},contractInfoPlant)
    });
    
    var contractInfosm = new Ext.grid.CheckboxSelectionModel();
    
    var contractInfocm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		contractInfosm,
		   {header: '合同组ID',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_GROUP_ID',
           hidden:true
           },
		　 {header: '合同组名称',
           width: 200,
           sortable: true,
           dataIndex: 'CONTRACT_GROUP_NAME'
           },
		　 {header: '合同组编码',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_GROUP_NO'
           },
		　 {header: '备注',
           width: 200,
           sortable: false,
           dataIndex: 'CMD'
           },
           {header: '项目ID',
           width: 100,
           sortable: true,
           dataIndex: 'PROJECT_ID',
           hidden:true
           },
           {header: '项目名称',
           width: 200,
           sortable: true,
           dataIndex: 'PROJECT_NAME'
           },
           {
           header: '创建时间',
           width: 200,
           sortable: true,
           dataIndex: 'CREATE_TIME'
           },
           {
           header: '创建者',
           width: 100,
           sortable: true,
           dataIndex: 'REAL_NAME'
           }
    ]);
    contractInfocm.defaultSortable = true;
    
    var contractInfobbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:contractInfods,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    contractInfogrid = new Ext.grid.GridPanel({
    	id:'contractInfoGrid',
        ds: contractInfods,
        cm: contractInfocm,
        sm: contractInfosm,
        bbar: contractInfobbar,
        tbar: contractInfotbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'center',
        el: 'div_center',
        autowidth:true,
        layout:'fit'
    });
    
    contractInfods.load({params:{start:0, limit:10},arg:[]});
    
    contractInfogrid.addListener('rowclick', contractInfogridrowclick);
	//    
    function contractInfogridrowclick(grid, rowIndex, e){
    	var record = grid.getStore().getAt(rowIndex);
    	var data = record.get('CONTRACT_GROUP_ID');
    	
    	var requestUrl = 'contractController.spr?action=queryContractInfoByGroupId&groupid=' + data;

		contractds.proxy= new Ext.data.HttpProxy({url:requestUrl});
		contractds.load({params:{start:0, limit:10},arg:[]});
    }
    
    var addSaleContract = new Ext.Toolbar.Button({
   		text:'增加销售合同',
	    iconCls:'add',
		handler:function(){
			if (contractInfogrid.selModel.hasSelection()){
				var records = contractInfogrid.selModel.getSelections();
				if (records.length == 1){
					//区别进料加工业务的采购合同
					if (strContractType == '8'){
					    addSalesContract('S');
						/**
						top.Ext.Msg.show({
							title:'提示',
	   						msg: '请选择是国内销售还是出口销售',
	   						buttons: {yes:'国内销售', no:'出口销售'},
	   						fn: function(buttonId,text){
	   							if(buttonId=='yes'){
	   								addSalesContract('S');
	   							}else if(buttonId=='no'){
	   								addSalesContract('E');
	   							}
	   						},
	   						icon: Ext.MessageBox.QUESTION
						});
						*/
					}else if(strContractType == '12'){
					    addSalesContract('E');
					}else{
						addSalesContract('');
					}
				}
				else{
					top.ExtModalWindowUtil.alert('提示','只能选择一个合同组信息！');
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择一个合同组再增加其下挂的合同！');
			}
		}
   	});
   	
   	function addSalesContract(SalesType){
	   	var records = contractInfogrid.selModel.getSelections();
		var requestUrl = 'contractController.spr?action=addSaleContract';
		requestUrl = requestUrl + '&contractgroupid=' + records[0].data.CONTRACT_GROUP_ID;
		requestUrl = requestUrl + '&projectid=' + records[0].data.PROJECT_ID;
		requestUrl = requestUrl + '&projectname=' + records[0].data.PROJECT_NAME;
		requestUrl = requestUrl + '&contracttype=' + strContractType;
		requestUrl = requestUrl + '&contractgroupno=' + records[0].data.CONTRACT_GROUP_NO;
		requestUrl = requestUrl + '&salestype=' + SalesType
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				
				if(responseArray.msg!=''&&responseArray.msg!=null){
				   Ext.MessageBox.alert('提示', responseArray.msg);
				   return;
				}
				var contractpt = new contractPlant({
	                    CONTRACT_ID: responseArray.CONTRACT_ID,
	                    CONTRACT_TYPE: responseArray.CONTRACT_TYPE,
	                    CONTRACT_NO: responseArray.CONTRACT_NO,
	                    CONTRACT_NAME: responseArray.CONTRACT_NAME,
	                    OUT_CONTRACT_NO: responseArray.OUT_CONTRACT_NO,
	                    APPLY_TIME:responseArray.APPLY_TIME,
	                    APPROVED_TIME: responseArray.APPROVED_TIME,
	                    SAP_ORDER_NO: responseArray.SAP_ORDER_NO,
	                    ORDER_STATE_D_ORDER_STATE: responseArray.ORDER_STATE,
	                    CONTRACT_INFO: responseArray.CONTRACT_INFO,
	                    CREATOR: responseArray.CREATOR
	                });
                contractgrid.stopEditing();
                contractds.insert(0, contractpt);
                contractgrid.startEditing(0, 0);
                openContractWinByAdd("1",responseArray.CONTRACT_ID,strContractType);
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
   	}
   	
   	function addPurchaserContract(PurchaserType){
   		var records = contractInfogrid.selModel.getSelections();
   	
   		var requestUrl = 'contractController.spr?action=addPurchaseContract';
		requestUrl = requestUrl + '&contractgroupid=' + records[0].data.CONTRACT_GROUP_ID;
		requestUrl = requestUrl + '&projectid=' + records[0].data.PROJECT_ID;
		requestUrl = requestUrl + '&projectname=' + records[0].data.PROJECT_NAME;
		requestUrl = requestUrl + '&contracttype=' + strContractType;
		requestUrl = requestUrl + '&contractgroupno=' + records[0].data.CONTRACT_GROUP_NO;
		requestUrl = requestUrl + '&purchasertype=' + PurchaserType
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				if(responseArray.msg!=''&&responseArray.msg!=null){
				   Ext.MessageBox.alert('提示', responseArray.msg);
				   return;
				}
				var contractpt = new contractPlant({
	                    CONTRACT_ID: responseArray.CONTRACT_ID,
	                    CONTRACT_TYPE: responseArray.CONTRACT_TYPE,
	                    CONTRACT_NO: responseArray.CONTRACT_NO,
	                    CONTRACT_NAME: responseArray.CONTRACT_NAME,
	                    OUT_CONTRACT_NO: responseArray.OUT_CONTRACT_NO,
	                    APPLY_TIME:responseArray.APPLY_TIME,
	                    APPROVED_TIME: responseArray.APPROVED_TIME,
	                    SAP_ORDER_NO: responseArray.SAP_ORDER_NO,
	                    ORDER_STATE_D_ORDER_STATE: responseArray.ORDER_STATE,
	                    CONTRACT_INFO: responseArray.CONTRACT_INFO,
	                    CREATOR: responseArray.CREATOR
	                });
                contractgrid.stopEditing();
                contractds.insert(0, contractpt);
                contractgrid.startEditing(0, 0);
			    openContractWinByAdd("2",responseArray.CONTRACT_ID,strContractType);                
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
   	}
   	
   	var addBuyContract = new Ext.Toolbar.Button({
   		text:'增加采购合同',
	    iconCls:'add',
		handler:function(){
			if (contractInfogrid.selModel.hasSelection()){
				var records = contractInfogrid.selModel.getSelections();
				if (records.length == 1){
					//区别进料加工业务的采购合同
					if (strContractType == '8'||strContractType == '12'){
						top.Ext.Msg.show({
							title:'提示',
	   						msg: '请选择是成品采购还是原材料采购',
	   						buttons: {yes:'成品采购', no:'原材料采购/进口',cancel:'原料采购/国内'},
	   						fn: function(buttonId,text){
	   							if(buttonId=='yes'){
	   								addPurchaserContract('P');
	   							}else if(buttonId=='no'){
	   								addPurchaserContract('I');
	   							}else {
	   							    addPurchaserContract('PI');
	   							}
	   						},
	   						icon: Ext.MessageBox.QUESTION
						});
					}else{
						addPurchaserContract('');
					}
				}
				else{
					top.ExtModalWindowUtil.alert('提示','只能选择一个合同组信息！');
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择一个合同组再增加其下挂的合同！');
			}
		}
   	});
   	
   	var deleteContract = new Ext.Toolbar.Button({
   		text:'删除',
	    iconCls:'delete',
		handler:function(){
			if (contractgrid.selModel.hasSelection()){
				var records = contractgrid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','一次只能删除一个合同信息！');
				}else{
					var orderState = records[0].data.ORDER_STATE_D_ORDER_STATE.trim();
					if(orderState=='新增'){
					top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定删除记录',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
   								if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
	   								var param = "?action=deletePurchaseContract";
									param = param + "&contractpurchaseid=" + records[0].data.CONTRACT_ID;
	
									new AjaxEngine('contractController.spr', 
							   			{method:"post", parameters: param, onComplete: callBackHandle});
							   			
							   		strOperType = '2';
							   	}
							   	
							   	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
	   								var param = "?action=deleteSalesContract";
									param = param + "&contractsalesid=" + records[0].data.CONTRACT_ID;
	
									new AjaxEngine('contractController.spr', 
							   			{method:"post", parameters: param, onComplete: callBackHandle});
							   		
							   		strOperType = '2';
							   	}
							   	
   							}
   						},
   						icon: Ext.MessageBox.QUESTION
					});
				}else{
					top.ExtModalWindowUtil.alert('提示','非新增合同不能删除！');
					}
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的合同信息！');
			}
		}
   	});
   	
   	
   	var referenceCreate = new Ext.Toolbar.Button({
   		text:'参考创建',
	    iconCls:'add',
		handler:function(){
			if (contractInfogrid.selModel.hasSelection()){
					var records = contractInfogrid.selModel.getSelections();
					if (records.length == 1){
					   var  requestUrl = '&contractgroupid=' + records[0].data.CONTRACT_GROUP_ID;
						requestUrl = requestUrl + '&projectid=' + records[0].data.PROJECT_ID;
						requestUrl = requestUrl + '&projectname=' + records[0].data.PROJECT_NAME;
						requestUrl = requestUrl + '&tradetype=' + strContractType;
						requestUrl = requestUrl + '&contractgroupno=' + records[0].data.CONTRACT_GROUP_NO;
					    requestUrl = encodeURI(requestUrl);
					    top.ExtModalWindowUtil.show(strContractTitle + '参考创建',
						'contractController.spr?action=addReferenceView'+requestUrl,
						'',
						BuycallbackFunction,
						{width:500,height:180});
		     
			         }
					else{
						top.ExtModalWindowUtil.alert('提示','只能选择一个合同组信息！');
					}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择一个合同组再增加其下挂的合同！');
			}
		}
		   
   	});
    
    var contracttbar = new Ext.Toolbar({
		items:[addSaleContract,'-',addBuyContract,'-',deleteContract,'-',referenceCreate]
	});
	
	var contractPlant = Ext.data.Record.create([
		{name:'CONTRACT_ID'},
        {name:'CONTRACT_TYPE'},
        {name:'CONTRACT_NO'},
		{name:'CONTRACT_NAME'},
		{name:'OUT_CONTRACT_NO'},
		{name:'APPLY_TIME'},
		{name:'APPROVED_TIME'},
		{name:'SAP_ORDER_NO'},
		{name:'ORDER_STATE_D_ORDER_STATE'},
		{name:'CONTRACT_INFO'},
		{name:'CREATOR'}
	]);

	contractds = new Ext.data.Store({
        		proxy : new Ext.data.HttpProxy({url:''}),
        		reader: new Ext.data.JsonReader({
            	root: 'root',
            	totalProperty: 'totalProperty'
            	},contractPlant)
    });
    
    var contractsm = new Ext.grid.CheckboxSelectionModel();
    
    var contractcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		contractsm,
		  {
           header: '合同ID',
           width: 60,
           sortable: true,
           dataIndex: 'CONTRACT_ID',
           hidden:true
           },{
               header: '操作',
               width: 140,
               dataIndex: 'CONTRACT_INFO',
               renderer:funContractOper
           },{header: '合同状态',
               width: 100,
               sortable: true,
               dataIndex: 'ORDER_STATE_D_ORDER_STATE'
           },{
               header: '合同类型',
           width: 60,
           sortable: true,
           dataIndex: 'CONTRACT_TYPE'
           },
		　 {header: '合同编码',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_NO'
           },
		　 {header: '合同名称',
           width: 200,
           sortable: true,
           dataIndex: 'CONTRACT_NAME'
           },
           {header: '外部纸质合同号',
           width: 100,
           sortable: true,
           dataIndex: 'OUT_CONTRACT_NO'
           },
           {header: '申报日期',
           width: 100,
           sortable: true,
           dataIndex: 'APPLY_TIME'
           },
           {header: '通过日期',
           width: 100,
           sortable: true,
           dataIndex: 'APPROVED_TIME'
           },
           {header: 'SAP订单编号',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_ORDER_NO'
           },
           {header: '创建者',
           width: 100,
           sortable: true,
           dataIndex: 'CREATOR',
           hidden: true
           }
    ]);
    contractcm.defaultSortable = true;
    
    var contractbbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:contractds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    contractgrid = new Ext.grid.EditorGridPanel({
    	id:'contractGrid',
        ds: contractds,
        cm: contractcm,
        sm: contractsm,
        bbar: contractbbar,
        tbar: contracttbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        region:'south',
        el: 'div_south',
        autowidth:true,
		viewConfig : {  
            forceFit : true,  
            enableRowBody:true,
            getRowClass : 
            	function(record,rowIndex,rowParams,store){ 
            		if(rowIndex%2==0)
            			return 'x-grid-row-bgcolor'; 
            		else
            			return '';	
            	}  
    	},          
        layout:'fit'
    });
    
    contractgrid.addListener('rowclick', contractcellclick);
    
    function contractcellclick(grid, rowIndex, e){
    	purchaserrecord = grid.getStore().getAt(rowIndex);
    }
    
    function funContractOper(value, p, record){
    	var state = record.data.ORDER_STATE_D_ORDER_STATE;
    	//alert(('${loginer}'== record.data.CREATOR)+'-----'+${loginer}+'---'+record.data.CREATOR);
    	if('${loginer}'== record.data.CREATOR){

    		if (state=='新增'){    		
        		return '<a href="#" onclick="openContractWin();" >修改</a> <a href="#" style="color:red" onclick="openContractWin()">提交</a>';
       	 	}else if(state=='审批通过'){
       	 		return '<a href="#" onclick="openContractWinByReadOnly();" >查看</a> <a href="#" style="color:red" onclick="changeContract()">变更</a> <a href="#" onclick="openWorkFlowWin();" >流程跟踪</a>';
           	}else if(state=='变更'){
           		return '<a href="#" onclick="changeContractView();" >查看</a> <a href="#" onclick="openWorkFlowWin();" >流程跟踪</a>';
            }else if(state=='变更通过'){
           		return '<a href="#" onclick="openContractWinByReadOnly();" >查看</a> <a href="#" onclick="changedContractView();" >变更</a> <a href="#" onclick="openWorkFlowWin();" >流程跟踪</a>';
            }else if(state=='授权部门变更通过'){
           		return '<a href="#" onclick="openContractWinByReadOnly();" >查看</a> <a href="#" onclick="changedShortSAPContractView();">变更</a> <a href="#" onclick="openWorkFlowWin();" >流程跟踪</a>';
            }else if(state=='授权部门审批通过'){
           		return '<a href="#" onclick="openContractWinByReadOnly();" >查看</a> <a href="#" style="color:red" onclick="changeShortContract()">变更</a> <a href="#" onclick="openWorkFlowWin();" >流程跟踪</a>';
            }else if(state=='授权部门变更'){
           		return '<a href="#" onclick="changeContractView();" >查看</a> <a href="#" onclick="openWorkFlowWin();" >流程跟踪</a>';
 //           }else if(state=='审批不通过'){
 //       		return '<a href="#" onclick="openSubmitParticularWorkflow();" >特批</a> <a href="#" onclick="openWorkFlowWin();" >流程跟踪</a>';
 //           }else if(state.indexOf('特批')!=-1){
 //       		return '<a href="#" onclick="openContractWinByReadOnly()">查看</a> <a href="#" onclick="businessAllProcessRecords();" >流程跟踪</a> ';
            }else{
        		return '<a href="#" onclick="openContractWinByReadOnly()">查看</a> <a href="#" onclick="openWorkFlowWin();" >流程跟踪</a> ';
        	}		
    	}else{
    		if (state=='新增'){    		
        		return '<a href="#" onclick="openContractWinByReadOnly()">查看</a>';
       	 	}else{
        		return '<a href="#" onclick="openContractWinByReadOnly()">查看</a> <a href="#" onclick="openWorkFlowWin();" >流程跟踪</a> ';
        	}	    	
    	}
    }    

   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			title: strContractTitle + "条件查询",
			collapsible: true,
			contentEl:'div_northForm',
			height:105
		},{
			region:"center",
			layout:'fit',
			title: strContractTitle + "合同组列表",
			items:[contractInfogrid]
		},
		{
			region:"south",
			layout:'fit',
			height:220,
			minSize: 210,
            maxSize: 400,
            split:true,
			collapsible: true,
			title: strContractTitle + "合同列表",
			items:[contractgrid]
		}]
	});
});
</script>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155"></fiscxd:dept>
