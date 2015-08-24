<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="com.infolion.platform.component.processor.impl.Button" %>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.save {
	background-image: url(/images/fam/save.gif) !important;
}
.add{
	background-image:url(images/fam/add.gif) !important;
}
.delete{
	background-image:url(images/fam/delete.gif) !important;
}
.update{
	background-image:url(images/fam/refresh.gif) !important;
}
</style>
</head>
<body>

<div id="div_basrForm">
<div id="div_contract"></div>
<form action="" id="baseForm" name="baseForm">
<table width="100%">
<tr>
	<td align="right">采购组织：</td>
	<td>
	<input type="text" readonly="readonly" id="ekkoEkorg" name="ekkoEkorg" value="${purchase.ekkoEkorg}"></input>
	</td>
	<td align="right">采购组：</td>
	<td>
	<div id="div_ekkoEkgrp"></div>
	</td>
	<td align="right">凭证类型：</td>
	<td>
	<input type="text" id="ekkoBsart" name="ekkoBsart" value="${purchase.ekkoBsart}"></input>
	</td>
	<td align="right">	
		物料组:
	</td>
	<td  >	
		<div id="div_ymatGroup"></div>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_contractInfo"  class="x-hide-display">
<form action="" id="contractForm" name="contractForm">
<table width="100%">
<tr>	
	<td align="right">项目名称：</td>
	<td>
	<input type="hidden" id="tradeType" name="tradeType" value="${purchase.tradeType}"/>
	<input type="hidden" id="contractPurchaseId" name="contractPurchaseId" value="${purchase.contractPurchaseId}"></input>
	<input readonly="readonly" type="text" id="projectName" name="projectName" value="${purchase.projectName}"></input>
	<a href="#" onclick="viewProjectForm()">查看</a>
	</td>
	<script>
	function viewProjectForm(){
			top.ExtModalWindowUtil.show('查看立项申请','projectController.spr?action=modify&from=view&projectId=${purchase.projectId}','','',{width:800,height:500});
    }
    function viewTradeMonint(){
    	_getMainFrame().maintabs.addPanel('贸易监控','', 'tradeMonitoringController.spr?action=_conditionManage&isdefault=05&queryValue=${purchase.contractNo}');
    }
    </script>
	<td align="right">合同组合称：</td>
	<td>
	<input readonly="readonly" type="text" id="contractGroupName" name="contractGroupName" value="${contractGroupName}"></input>
	</td>
	<td align="right">合同编码：</td>
	<td>
	<input readonly="readonly" type="text" id="contractNo" name="contractNo" value="${purchase.contractNo}"></input>
	</td>
</tr>
<tr>
	<td align="right">合同名称：</td>
	<td colspan="5">
	<input type="text" id="contractName" name="contractName" value="${purchase.contractName}" size="63"></input>
	<a href="#" onclick="viewTradeMonint()">贸易监控</a>
	</td>
</tr>
<tr>
	<td align="right">采购凭证日期：</td>
	<td>
	<input type="text" id="ekkoBedat" name="ekkoBedat" value="${purchase.ekkoBedat}"></input>
	</td>
	<td align="right">货币码:</td>
	<td>
	<div id="div_ekkoWaers"></div>
	</td>
	<td align="right">汇率：</td>
	<td>
	<input type="text" id="ekkoWkurs" name="ekkoWkurs" value="${purchase.ekkoWkurs}"></input>
	</td>
</tr>
<tr>
	<td align="right">手册号：</td>
	<td>
	<input type="text" id="ekkoTelf1" name="ekkoTelf1" value="${purchase.ekkoTelf1}"></input>
	</td>
	<td align="right">SAP订单号：</td>
	<td>
	<input type="text" readonly="readonly" id="sapOrderNo" name="sapOrderNo" value="${purchase.sapOrderNo}"></input>
	</td>
	<td align="right">付款条件：</td>
	<td>
	<div id="div_ekkoZterm"></div>
	</td>
</tr>
<tr>
	<td align="right">供应商帐户号：</td>
	<td>
	<input type="text" readonly="readonly" id="ekkoLifnr" name="ekkoLifnr" value="${purchase.ekkoLifnr}"></input>
	</td>
	<td align="right">供应商名称: </td>
	<td>
	<input readonly="readonly" type="text" id="ekkoLifnrName" name="ekkoLifnrName" value="${purchase.ekkoLifnrName}"></input>
	</td>
	<td align="right">外部纸质合同号：</td>
	<td>
	<input type="text" id="ekkoUnsez" name="ekkoUnsez" value="${purchase.ekkoUnsez}" readonly="readonly" ></input>
	</td>
</tr>
<tr>
	<td align="right">开票方：</td>
	<td>
	<input type="text" readonly="readonly" id="invoicingParty" name="invoicingParty" value="${purchase.invoicingParty}"></input>
	</td>
	<td align="right">开票方名称：</td>
	<td>
	<input type="text" readonly="readonly" id="invoicingPartyName" name="invoicingPartyName" value="${purchase.invoicingPartyName}"></input>
	</td>
	<td align="right"></td>
	<td>
	</td>
</tr>
<tr>
	<td align="right">收款方：</td>
	<td>
	<input type="text" readonly="readonly" id="payer" name="payer" value="${purchase.payer}"></input>
	</td>
	<td align="right">收款方名称：</td>
	<td>
	<input type="text" readonly="readonly" id="payerName" name="payerName" value="${purchase.payerName}"></input>
	</td>
	<td align="right"></td>
	<td>
	</td>
</tr>
<tr>
	<td align="right">国际贸易条件1：</td>
	<td>
	<div id="div_ekkoInco1"></div>
	</td>
	<td align="right">国际贸易条件2：</td>
	<td>
	<input type="text" readonly="readonly" id="ekkoInco2" name="ekkoInco2" value="${purchase.ekkoInco2}"></input>
	</td>
	<td align="right"></td>
	<td>
	</td>
</tr>
<!--
<tr>
	<td align="right">不含税金额汇总：</td>
	<td>
	<input type="text" readonly="readonly" id="totalAmount" name="totalAmount" value="${purchase.totalAmount}"></input>
	</td>
	<td align="right">税金汇总：</td>
	<td>
	<input type="text" readonly="readonly" id="totalTaxes" name="totalTaxes" value="${purchase.totalTaxes}"></input>
	</td>
	<td align="right">含税金额汇总：</td>
	<td>
	<input type="text" readonly="readonly" id="totalAmountTaxes" name="totalAmountTaxes" value="${purchase.totalAmountTaxes}"></input>
	</td>
</tr>
<tr>
	<td align="right">运费汇总：</td>
	<td>
	<input type="text" readonly="readonly" id="totalFreight" name="totalFreight" value="${purchase.totalFreight}"></input>
	</td>
	<td align="right">关税汇总：</td>
	<td>
	<input type="text" readonly="readonly" id="totalTariff" name="totalTariff" value="${purchase.totalTariff}"></input>
	</td>
	<td align="right">消费税汇总：</td>
	<td>
	<input type="text" readonly="readonly" id="totalCt" name="totalCt" value="${purchase.totalCt}"></input>
	</td>
</tr>-->
<tr>
	<!--  <td align="right">进项税汇总：</td>
	<td>
	<input type="text" readonly="readonly" id="totalIt" name="totalIt" value="${purchase.totalIt}"></input>
	</td>-->
	<td align="right">订单币别不含税总计：</td>
	<td>
		<input type="text" size="20" readonly="readonly" id="totalAmount" name="totalAmount" value="${purchase.totalAmount}"></input>
	</td>
	<td align="right">订单币别税费总计：</td>
	<td>
		<input type="text" size="20" readonly="readonly" id="totalTaxes" name="totalTaxes" value="${purchase.totalTaxes}"></input>
	</td>
	<td align="right">订单币别总计：</td>
	<td>
		<input type="text" size="20" readonly="readonly" id="total" name="total" value="${purchase.total}"></input>
	</td>
</tr>
<tr>
	<td align="right">装运港：</td>
	<td>
	<input type="text" readonly="readonly" id="shipmentPort" name="shipmentPort" value="${purchase.shipmentPort}"></input>
	</td>
	<td align="right">目的港：</td>
	<td>
	<input type="text" readonly="readonly" id="destinationPort" name="destinationPort" value="${purchase.destinationPort}"></input>
	</td>
	<td align="right">装运期：</td>
	<td>
	<input type="text" readonly="readonly" id="shipmentDate" name="shipmentDate" value="${purchase.shipmentDate}"></input>
	</td>
</tr>
<tr>
	<td align="right">备注: </td>
	<td colspan="3">
		<textarea name="mask" style="width:100%;overflow-y:visible;word-break:break-all;">${purchase.mask}</textarea>
    </td>
</tr>

</table>
</form>

<div id="div_product"></div>
</div>

<div id="div_accessory" class="x-hide-display"></div>
<div id="div_examHist" class="x-hide-display">
</body>
</html>
<fiscxd:fileUpload divId="div_accessory" resourceId="2222" increasable="false" erasable="false" 
	resourceName="33333" recordIdHiddenInputName="444" recordId="${purchase.contractPurchaseId}"></fiscxd:fileUpload>
<fiscxd:workflow-taskHistory divId="div_examHist" width="800" businessRecordId="${purchase.contractPurchaseId}"></fiscxd:workflow-taskHistory>
<script type="text/javascript">
var strOperType = '0';
var productId = '';
var productName = '';
var productUnit = '';
var productgrid;

var productds;
var contractds;
function openOtherChargeWin(){
	var records = productgrid.selModel.getSelections();

   	top.ExtModalWindowUtil.show('其他费用窗口',
	'contractController.spr?action=ArchPurchaseCaseView&purchaserowsid='+records[0].data.PURCHASE_ROWS_ID,
	'',
	'',
	{width:500,height:300});
}

function openBOMWin(){
	var records = productgrid.selModel.getSelections();

	top.ExtModalWindowUtil.show('BOM信息窗口',
	'contractController.spr?action=ArchBomInfoView&purchaserowsid='+records[0].data.PURCHASE_ROWS_ID,
	'',
	'',
	{width:700,height:250});
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
   	div_accessory_ns_ds.load({params:{start:0, limit:10,recordId:'111'}});
   	
   	var fm = Ext.form;
   	
   	var Bedat = new Ext.form.DateField({
   		format:'Ymd',
		name:"bedate",
		id:"bedate",
		width: 155,
		applyTo:'ekkoBedat'
   	});
   	
   	var ShipmentDate = new Ext.form.DateField({
   		format:'Ymd',
		name:"shipmentDate",
		id:"shipmentDate",
		width: 155,
		readOnly:true,
		applyTo:'shipmentDate'
   	});
   	
   	var productPlant = Ext.data.Record.create([
   		{name:'PURCHASE_ROWS_ID'},
   		{name:'CONTRACT_PURCHASE_ID'},
   		{name:'EKPO_PSTYP_D_ITEM_TYPE'},
   		{name:'EKPO_MATNR'},
   		{name:'EKPO_TXZ01'},
   		{name:'EKPO_MEINS_D_UNIT'},
   		{name:'EKPO_MENGE'},
   		{name:'EKPO_NETPR'},
   		{name:'EKPO_PEINH'},
   		{name:'EKPO_WERKS_D_FACTORY'},
   		{name:'EKET_EINDT'},
   		{name:'EKPO_WEBRE_D_INVOICE_VALID'},
   		{name:'EKPO_MWSKZ_D_SALES_TAX'},
   		{name:'FACTORY_LOCAL_D_WAREHOUSE'},
   		{name:'FLOW_NO'},
   		{name:'TOTAL_VALUE'},
   		{name:'PRICE'},
   		{name:'TAXES'},
   		{name:'TOTAL_TAXES'},
   		{name:'MATERIEL_UNIT_D_MATERIAL_GROUP'},
   		{name:'OTHERINFO'},
   		{name:'BOMINFO'}
	]);
	
	productds = new Ext.data.Store({
   		proxy : new Ext.data.HttpProxy({url:'contractController.spr?action=queryPurchaseMaterielInfo&contractPurchaseId='+document.contractForm.contractPurchaseId.value}),
   		reader: new Ext.data.JsonReader({
       	root: 'root',
       	totalProperty: 'totalProperty'
       	},productPlant)
    });
    
    var productsm = new Ext.grid.CheckboxSelectionModel();
    
    var productcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		productsm,
		   {header: '采购合同行项ID',
           	width: 100,
           	sortable: true,
           	dataIndex: 'PURCHASE_ROWS_ID',
           	hidden:true
		   },
           {header: '采购合同ID',
           width: 100,
           sortable: true,
           dataIndex: 'CONTRACT_PURCHASE_ID',
           hidden:true
           },
           {header: '项目类别',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_PSTYP_D_ITEM_TYPE'
           },
           {header: '物料号',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_MATNR'
           },
           {header: '物料描述',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_TXZ01'
           },
           {header: '采购订单计量单位',
           width: 150,
           sortable: true,
           dataIndex: 'EKPO_MEINS_D_UNIT'
           },
           {header: '采购订单数量',
           width: 150,
           sortable: true,
           dataIndex: 'EKPO_MENGE'
           },
           {header: '采购凭证中的净价(以凭证货币计)',
           width: 200,
           sortable: true,
           dataIndex: 'EKPO_NETPR'
           },
           {header: '价格单位',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_PEINH'
           },
           {header: '工厂',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_WERKS_D_FACTORY'
           },
           {header: '装船期/项目交货日期',
           width: 150,
           sortable: true,
           dataIndex: 'EKET_EINDT'
           },
           {header: '标识：基于收货的发票验证',
           width: 200,
           sortable: true,
           dataIndex: 'EKPO_WEBRE_D_INVOICE_VALID'
           },
           {header: '销售税代码',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_MWSKZ_D_SALES_TAX'
           },
           {header: '库存地点',
           width: 100,
           sortable: true,
           dataIndex: 'FACTORY_LOCAL_D_WAREHOUSE'
           },
           {header: '批次号',
           width: 100,
           sortable: true,
           dataIndex: 'FLOW_NO'
           },
           {header: '含税单价',
           width: 100,
           sortable: true,
           dataIndex: 'PRICE'
           },
           {header: '税金',
           width: 100,
           sortable: true,
           dataIndex: 'TAXES'
           },
           {header: '不含税金额汇总',
           width: 100,
           sortable: true,
           dataIndex: 'TOTAL_VALUE'
           },
           {header: '含税金额汇总',
           width: 100,
           sortable: true,
           dataIndex: 'TOTAL_TAXES'
           },
           {header: '物料组',
           width: 100,
           sortable: true,
           dataIndex: 'MATERIEL_UNIT_D_MATERIAL_GROUP'
           },
           {header: '其他费用',
           width: 100,
           sortable: true,
           dataIndex: 'OTHERINFO',
           renderer:renderotherOper
           },
           {header: 'BOM组件',
           width: 100,
           sortable: true,
           dataIndex: 'BOMINFO',
           renderer:renderBOMOper
           }
    ]);
    productcm.defaultSortable = true;
    
    var productbbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:productds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
    productgrid = new Ext.grid.EditorGridPanel({
    	id:'productGrid',
    	title:'物料信息',
        ds: productds,
        cm: productcm,
        sm: productsm,
        bbar: productbbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        el:'div_product',
        height:300,
        autoWidth:true,
        miniWidth:860
    });
    
    productgrid.render();
    
    productds.load({params:{start:0, limit:10}});

    function renderotherOper(value, p, record){
    	return String.format('<a href="#" onclick="openOtherChargeWin()">{0}</a>','详情');
    }
    
    function renderBOMOper(value, p, record){
    	return String.format('<a href="#" onclick="openBOMWin()">{0}</a>','详情');
    }
     /***/
    var contractPlant = Ext.data.Record.create([
   		{name:'CONTRACT_ID'},
        {name:'CONTRACT_TYPE'},
        {name:'CONTRACT_NO'},
		{name:'CONTRACT_NAME'},
		{name:'QUALITYTOTAL'},
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
           },
           {header: '合同类型',
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
           {header: '数量',
           width: 100,
           sortable: true,
           dataIndex: 'QUALITYTOTAL'
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
           {header: '合同状态',
           width: 100,
           sortable: true,
           dataIndex: 'ORDER_STATE_D_ORDER_STATE'
           },
           {header: '创建者',
           width: 100,
           sortable: true,
           dataIndex: 'CREATOR',
           hidden: true
           },
           {header: '操作',
           width: 140,
           dataIndex: 'CONTRACT_INFO',
           renderer:funContractOper
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
    	title:'合同组项下合同',
        ds: contractds,
        cm: contractcm,
        sm: contractsm,
        bbar: contractbbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        el: 'div_contract',
        layout:"fit",
        height:300,
        width:860
    });
    function funContractOper(value, p, record){
    	var state = record.data.ORDER_STATE_D_ORDER_STATE;
    	
        		return '<a href="#" onclick="openContractWinByReadOnly()">查看</a> <a href="#" onclick="openWorkFlowWin();" >流程跟踪</a> ';
    }   
    /***/
   	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"north",
			collapsible: true,
			height:25,
			border:false,
			contentEl: 'div_basrForm'
		},{
			region:"center",
			layout:'fit',
			border:false,
			buttonAlign:'center',
			items:[{
				region:'center',
				xtype:'tabpanel',
				id:'infotype',
				name:'infotype',
				plain:true,
	            height:300,
	            activeTab:0,
	            items:[{
					title:'合同变更',
					id:'alterId',
					layout:'fit',
					html:'<iframe src="purchaseChangeController.spr?action=toChangeTab&from=${from}&contractId=${purchase.contractPurchaseId}&shortSAP=${shortSAP}" width="100%" height="100%" id="alter" ></iframe>'
	            },{
	            	title:'合同项信息',
	            	contentEl: 'div_contractInfo',
	            	id:'productinfo',
					name:'productinfo',
					autoScroll:'true',
	            	layout:'fit'
	            },{
	            	title:'附件信息',
	            	contentEl: 'div_accessory',
	            	id:'accessoryinfo',
					name:'accessoryinfo',
	            	layout:'fit'
	            },{
	            	title:'同合同组项下合同',
	            	contentEl: '',
	            	id:'contractInGroup',
					name:'div_contractInGroup',
	            	layout:'fit',
	            	items:[contractgrid],
	            	listeners:{activate:handlerActivate}
	            },{
	            	title:'合同审批信息',
	            	contentEl: 'div_examHist',
	            	id:'examHist',
					name:'div_examHist',
	            	layout:'fit'
	            }]
			}],
			buttons:[{
            	text:'写入SAP',
             	hidden: ${!writeSap},
            	handler:function(){
            		var param1 = Form.serialize('baseForm');
            		var param2 = Form.serialize('contractForm');
					var param = param1 + "&" + param2 + "&action=submitToSapPurchaserInfo";
					new AjaxEngine('contractController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
            	}
            }]
		}]
	});
});
function handlerActivate(tab){
  
	var requestUrl = 'contractController.spr?action=queryContractInfoByGroupId&groupid=${purchase.contractGroupId}';
   
	contractds.proxy= new Ext.data.HttpProxy({url:requestUrl});
	contractds.load({params:{start:0, limit:10},arg:[]});
	
}
function openContractWinByReadOnly(){
    
	var records = contractgrid.selModel.getSelections();
	if (records[0].data.CONTRACT_TYPE.trim() == '采购合同'){
		top.ExtModalWindowUtil.show('采购合同信息',
			'contractController.spr?action=ArchPurchaseInfoView&businessRecordId='+records[0].data.CONTRACT_ID,
			'','',
			{width:900,height:550});
	}

	if (records[0].data.CONTRACT_TYPE.trim() == '销售合同'){
		top.ExtModalWindowUtil.show('销售合同信息',
		'contractController.spr?action=archSalesInfoView&businessRecordId='+records[0].data.CONTRACT_ID,
		'','',
		{width:900,height:550});
	}
}
function openWorkFlowWin(){
	var records = contractgrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('审批详情信息',
	'workflowController.spr?action=finishWorkDetailView&businessRecordId='+records[0].data.CONTRACT_ID,
	'',
	'',
	{width:900,height:365});	
}
function getTitle(sourceid,code)
{
	var sourceObj = document.getElementById(sourceid);
	var dropDownList = sourceObj.options;
	for(i=0;i<dropDownList.length;i++)
	{
		if(dropDownList[i].value==code)
			return dropDownList[i].text;
	}
}
</script>
<fiscxd:dictionary divId="div_ekkoZterm" fieldName="ekkoZterm" dictionaryName="BM_PAYMENT_TYPE" width="150" selectedValue="${purchase.ekkoZterm}" disable="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_ekkoInco1" fieldName="ekkoInco1" dictionaryName="BM_INCOTERM_TYPE" width="150" selectedValue="${purchase.ekkoInco1}" disable="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_ekkoWaers" fieldName="ekkoWaers" dictionaryName="BM_CURRENCY" width="150" selectedValue="${purchase.ekkoWaers}" disable="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_ekkoEkgrp" fieldName="ekkoEkgrp" dictionaryName="BM_PURCHASING_GROUP" width="150" selectedValue="${purchase.ekkoEkgrp}" disable="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_ymatGroup" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${purchase.ymatGroup}" disable="true" ></fiscxd:dictionary>
