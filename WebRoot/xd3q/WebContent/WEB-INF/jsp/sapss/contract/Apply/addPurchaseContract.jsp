<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>fiscxdxd title here</title>
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
.copy{
	background-image:url(images/fam/application_go.png) !important;
}
</style>
</head>
<body>

<div id="div_basrForm">
<form action="" id="baseForm" name="baseForm">
<table width="100%">
<tr>
	<td align="right">采购组织：</td>
	<td>
	<input type="text" readonly="readonly" id="ekkoEkorg" name="ekkoEkorg" value="${purchase.ekkoEkorg}"></input>
	</td>
	<td align="right">
	采购组
	</td>
	<td>
	<input type="text" name="ekkoEkgrp" id="ekkoEkgrp" value="${purchase.ekkoEkgrp}" readonly="readonly"/>

	</td>
	<td align="right">凭证类型：</td>
	<td>
	<input type="text" readonly="readonly" id="ekkoBsart" name="ekkoBsart" value="${purchase.ekkoBsart}"></input>
	</td>
	<td align="right">	
		物料组<font color="red">▲</font>
	</td>
	<td  >	
		<div id="div_ymatGroup"></div>
	</td>
</tr>
</table>
</form>
</div>

<div id="div_contractInfo">
<form action="" id="contractForm" name="contractForm">
<table width="100%">
<tr>	
	<td align="right" width="13%">项目名称：</td>
	<td>
	<input type="hidden" id="contractPurchaseId" name="contractPurchaseId" value="${purchase.contractPurchaseId}"></input>
	<input type="hidden" id="projectId" name="projectId" value="${purchase.projectId}"></input>
	<input type="hidden" id="tradeType" name="tradeType" value="${purchase.tradeType}"></input>
	<input readonly="readonly" type="text" id="projectName" name="projectName" value="${purchase.projectName}"></input>
	</td>
	<td align="right">合同组编码：</td>
	<td>
	<input readonly="readonly" type="text" id="contractGroupName" name="contractGroupName" value="${contractGroupNo}"></input>
	</td>
	<td align="right">原合同编码：</td>
	<td>
	<input  type="text" id="oldContractNo" name="oldContractNo" value="${purchase.oldContractNo}"></input>
	</td>
</tr>
<tr>
	<td align="right">合同编码：</td>
	<td>
	<input readonly="readonly" type="text" id="contractNo" name="contractNo" value="${purchase.contractNo}"></input>
	</td>
	<td align="right">合同名称<font color="red">▲</font></td>
	<td colspan="3">
	<input type="text" id="contractName" name="contractName" value="${purchase.contractName}" size="62"></input>
	</td>
</tr>
<tr>
	<td align="right">采购凭证日期<font color="red">▲</font></td>
	<td>
	<input type="text" id="ekkoBedat" name="ekkoBedat" value="${purchase.ekkoBedat}"></input>
	</td>
	<td align="right">货币码<font color="red">▲</font></td>
	<td>
	<select name="ekkoWaers" id="ekkoWaers">
		<option value="">请选择</option>
		<c:forEach items="${Currency}" var="row">
			<option value="${row.code}">${row.code}-${row.title}</option>
		</c:forEach>
	</select>
	</td>
	<td align="right">外部纸质合同号<font color="red">▲</font></td>
	<td>
	<input type="text" id="ekkoUnsez" name="ekkoUnsez" value="${purchase.ekkoUnsez}"></input>
	</td>
	
</tr>
<tr>
	
	<td align="right">SAP订单号：</td>
	<td>
	<input type="text" readonly="readonly" id="sapOrderNo" name="sapOrderNo" value="${purchase.sapOrderNo}"></input>
	</td>
	<td align="right">付款方式<font color="red">▲</font></td>
	<td>
		<div id="div_vbkdZlsch"></div>
	</td>
	<td align="right">付款条件<font color="red">▲</font></td>
	<td>
	<div id="div_ekkoZterm"></div>
	</td>
</tr>
<tr>
	<td align="right">供应商编码<font color="red">▲</font></td>
	<td>
	<input type="text" readonly="readonly" id="ekkoLifnr" name="ekkoLifnr" value="${purchase.ekkoLifnr}" size="15"></input>
	<input type="button" value="..." onclick="openekkoLifnrWin()"></input>
	</td>
	<td align="right">供应商名称<font color="red">▲</font></td>
	<td>
	<input readonly="readonly" type="text" id="ekkoLifnrName" name="ekkoLifnrName" value="${purchase.ekkoLifnrName}"></input>
	</td>
	<td align="right">汇率<font color="red">▲</font></td>
	<td>
	<input type="text" id="ekkoWkurs" name="ekkoWkurs" value="${purchase.ekkoWkurs}"></input>
	</td>
</tr>
<tr>
	<td align="right">开票方：</td>
	<td>
	<input type="text" readonly="readonly" id="invoicingParty" name="invoicingParty" value="${purchase.invoicingParty}" size="15"></input>
	<input type="button" value="..." onclick="openinvoicingPartyWin()"></input>
	</td>
	<td align="right">开票方名称：</td>
	<td>
	<input type="text" readonly="readonly" id="invoicingPartyName" name="invoicingPartyName" value="${purchase.invoicingPartyName}"></input>
	</td>
	<td align="right" rowspan="3" colspan="2"><div id="balanceMSG" align="left">${balaceMsg}</div></td>
</tr>
<tr>
	<td align="right">收款方：</td>
	<td>
	<input type="text" readonly="readonly" id="payer" name="payer" value="${purchase.payer}" size="15"></input>
	<input type="button" value="..." onclick="openpayerWin()"></input>
	</td>
	<td align="right">收款方名称：</td>
	<td>
	<input type="text" readonly="readonly" id="payerName" name="payerName" value="${purchase.payerName}"></input>
	</td>
</tr>
<tr>
	<td align="right">国际贸易条件1：</td>
	<td>
	<div id="div_ekkoInco1"></div>
	</td>
	<td align="right">国际贸易条件2：</td>
	<td>
	<input type="text" id="ekkoInco2" name="ekkoInco2" value="${purchase.ekkoInco2}"></input>
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
	<!--<td align="right">进项税汇总：</td>
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
	<td align="right">
		<input type="text" id="txtShipmentPort" name="txtShipmentPort" readonly="true"  style="border-style:none;border-Width:0PX;text-align:right"  value="装运港" size="10"></input><font color="red">▲</font>
	</td>
	<td>
	<input type="text" id="shipmentPort" name="shipmentPort" value="${purchase.shipmentPort}"></input>
	</td>
	<td align="right">目的港：</td>
	<td>
	<input type="text" id="destinationPort" name="destinationPort" value="${purchase.destinationPort}"></input>
	</td>
	<td align="right" nowrap="nowrap">
		<input type="text" id="txtShipmentDate" name="txtShipmentDate" readonly="true"  style="border-style:none;border-Width:0PX;text-align:right"  value="装运期" size="10"></input><font color="red">▲</font>
	</td>
	<td>
	<input type="text" id="shipmentDate" name="shipmentDate" value="${purchase.shipmentDate}"></input>
	</td>
</tr>
<tr id="trView1">
	<td align="right" id="tdView1">合同规定付款日<font color="red">▲</font></td>
	<td id="tdView2">
		<input type="text" id="collectionDate" name="collectionDate" value="${purchase.collectionDate}"></input>
	</td>
	<td align="right" id="tdView3">最迟开证日:</td>
	<td id="tdView4">
		<input type="text" id="laterDate" name="laterDate" value="${purchase.laterDate}"></input>
	</td>
	<td align="right" id="tdView5"></td>
	<td id="tdView6">
	</td>	
</tr>
<tr>
	<td align="right">备注: </td>
	<td colspan="3">
		<textarea name="mask" style="width:93%;overflow-y:visible;word-break:break-all;">${purchase.mask}</textarea>
    </td>
    <td align="right">手册号：</td>
	<td>
	<input type="text" id="ekkoTelf1" name="ekkoTelf1" value="${purchase.ekkoTelf1}"></input>
	</td>
</tr>
</table>
</form>

<div id="div_product"></div>
<div id="div_contract"></div>
</div>

<div id="div_accessory" class="x-hide-display">
<div id="div_examHist" class="x-hide-display">

<select name="ekpoPstyp" id="ekpoPstyp" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${ItemType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="ekpoPstyp11" id="ekpoPstyp11" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${ItemType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>

<select name="ekpoWerks" id="ekpoWerks" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Factory}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="ekpoWerks1" id="ekpoWerks1" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Factory}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>

<select name="materielUnit" id="materielUnit" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${MaterielGroup}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="materielUnit11" id="materielUnit11" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${MaterielGroup}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>

<select name="factoryLocal" id="factoryLocal" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${WareHouse}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="factoryLocal1" id="factoryLocal1" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${WareHouse}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>

<select name="ekpoMwskz" id="ekpoMwskz" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${SelasTax}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="ekpoMwskz1" id="ekpoMwskz1" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${SelasTax}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>

<select name="ekpoMeins" id="ekpoMeins" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Unit}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="ekpoMeins1" id="ekpoMeins1" style="display: none;">
	<option value=""></option>
	<c:forEach items="${Unit}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>

<select name="ekpoWebre" id="ekpoWebre" style="display: none;">
	<option value=""></option>
	<c:forEach items="${InvoiceValid}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="ekpoWebre1" id="ekpoWebre1" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${InvoiceValid}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="ekpoBprme" id="ekpoBprme" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Currency}" var="row">
		<option value="${row.code}">${row.code}-${row.title}</option>
	</c:forEach>
</select>
<select name="ekpoBprme1" id="ekpoBprme1" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Currency}" var="row">
		<option value="${row.code}">${row.code}-${row.title}</option>
	</c:forEach>
</select>

</body>
</html>

<fiscxd:fileUpload divId="div_accessory" resourceId="2222" resourceName="33333" recordIdHiddenInputName="444" recordId="${purchase.contractPurchaseId}"></fiscxd:fileUpload>
<fiscxd:workflow-taskHistory divId="div_examHist" width="800" businessRecordId="${purchase.contractPurchaseId}"></fiscxd:workflow-taskHistory>
<script type="text/javascript">
var strOperType = '0';
var productId = '';
var productName = '';
var productUnit = '';
var productgrid;

var productds;

var record;
var fieldName;
var purchaserowsid;
function callEkkoLifnr(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	document.contractForm.ekkoLifnr.value= returnvalue.LIFNR;
	document.contractForm.ekkoLifnrName.value = returnvalue.NAME1;
	document.contractForm.invoicingParty.value = returnvalue.LIFNR;
	document.contractForm.invoicingPartyName.value = returnvalue.NAME1;
	document.contractForm.payer.value = returnvalue.LIFNR;
	document.contractForm.payerName.value = returnvalue.NAME1;
}

function openekkoLifnrWin(){
	top.ExtModalWindowUtil.show('供应商查询',
	'masterQueryController.spr?action=toFindSupplier',
	'',
	callEkkoLifnr,
	{width:755,height:410});
}


function callinvoicingParty(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	document.contractForm.invoicingParty.value = returnvalue.LIFNR;
	document.contractForm.invoicingPartyName.value = returnvalue.NAME1;
}

function openinvoicingPartyWin(){
	top.ExtModalWindowUtil.show('供应商查询',
	'masterQueryController.spr?action=toFindSupplier',
	'',
	callinvoicingParty,
	{width:755,height:410});
}

function callpayer(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	document.contractForm.payer.value = returnvalue.LIFNR;
	document.contractForm.payerName.value = returnvalue.NAME1;
}

function openpayerWin(){
	top.ExtModalWindowUtil.show('供应商查询',
	'masterQueryController.spr?action=toFindSupplier',
	'',
	callpayer,
	{width:755,height:410});
}

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if (strOperType == '1')
		productds.reload();
		
	if (strOperType == '2'){
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	}
	if (strOperType != '4'){
		document.contractForm.totalAmount.value = customField.SumTotal;
		document.contractForm.totalTaxes.value = customField.taxTotal;
		document.contractForm.total.value = customField.total1;
		document.getElementById('balanceMSG').innerHTML=customField.balanceMSG;
		$('ekkoEkgrp').value = customField.ekkoEkgrp;
	}
}

function openOtherChargeWin(){
	var records = productgrid.selModel.getSelections();

   	top.ExtModalWindowUtil.show('增加其他费用',
	'contractController.spr?action=addPurchaseCaseView&purchaserowsid='+records[0].data.PURCHASE_ROWS_ID + '&saprowno=' +records[0].data.SAP_ROW_NO ,
	'',
	'',
	{width:800,height:300});
}

function openBOMWin(){
	var records = productgrid.selModel.getSelections();
	top.ExtModalWindowUtil.show('增加BOM信息',
	'contractController.spr?action=addBomInfoView&purchaserowsid='+records[0].data.PURCHASE_ROWS_ID + '&saprowno=' +records[0].data.SAP_ROW_NO+'&ekpomeinsdunit='+records[0].data.EKPO_MEINS_D_UNIT,
	'',
	'',
	{width:700,height:250});
}
/*function showFindGroup(){
	top.ExtModalWindowUtil.show('查找采购组','masterQueryController.spr?action=toFindGroup&dept=${purchase.ekkoEkorg}','',groupCallback,{width:320,height:380});
}*/
function groupCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('ekkoEkgrp').value=cb.VKGRP;
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	//附件记录
   	div_accessory_ns_ds.load({params:{start:0, limit:10,recordId:'${purchase.contractPurchaseId}'}});
   	
   	var fm = Ext.form;

   	
   	var laterDate = new Ext.form.DateField({
   		format:'Ymd',
		name:"laterDate",
		id:"laterDate",
		width: 155,
		applyTo:'laterDate'
   	});

   	var collectionDate = new Ext.form.DateField({
   		format:'Ymd',
		name:"collectionDate",
		id:"collectionDate",
		width: 155,
		applyTo:'collectionDate'
   	});
   	
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
   		{name:'SAP_ROW_NO'},
   		{name:'EKPO_PSTYP_D_ITEM_TYPE'},
   		{name:'EKPO_MATNR'},
   		{name:'EKPO_TXZ01'},
   		{name:'EKPO_MEINS_D_UNIT'},
   		{name:'EKPO_MENGE'},
   		{name:'EKPO_NETPR'},
   		{name:'EKPO_PEINH'},
   		{name:'EKPO_BPRME_D_CURRENCY'},   		
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
           {header: 'SAP物料行号',
           width: 100,
           sortable: true,
           dataIndex: 'SAP_ROW_NO',
           hidden:true
           },
           {header: '<font color="red">▲</font>项目类别',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_PSTYP_D_ITEM_TYPE',
           editor: new Ext.form.ComboBox({
        	   blankText:'项目类别必填',
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'ekpoPstyp',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
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
           {header: '<font color="red">▲</font>采购订单数量',
           width: 150,
           sortable: true,
           dataIndex: 'EKPO_MENGE',
           editor: new fm.NumberField({
        	   blankText:'采购订单数量必填',
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:3,
               maxValue: 999999999999
           })
           },
           {header: '<font color="red">▲</font>采购凭证中的净价(以凭证货币计)',
           width: 200,
           sortable: true,
           dataIndex: 'EKPO_NETPR',
           editor: new fm.NumberField({
        	   blankText:'采购凭证中的净价(以凭证货币计)必填',
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 999999999999
           })
           },
           {header: '价格单位',
           width: 200,
           sortable: true,
           dataIndex: 'EKPO_BPRME_D_CURRENCY',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'ekpoBprme',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })           
           },
 		   {header: '<font color="red">▲</font>每价格单位',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_PEINH',
           editor: new fm.NumberField({
        	   blankText:'每价格单位必填',
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:0,
               maxValue: 10000
           })
           },           
           {header: '<font color="red">▲</font>工厂',
           width: 200,
           sortable: true,
           dataIndex: 'EKPO_WERKS_D_FACTORY',
           editor: new Ext.form.ComboBox({
        	   blankText:'工厂必填',
               typeAhead: true,
               maxHeight: 150,
               triggerAction: 'all',
               transform:'ekpoWerks',
               lazyRender:true,
               listClass: 'x-combo-list-small',
               allowBlank: false
            })
           },
           {header: '<font color="red">▲</font>装船期/项目交货日期',
           width: 150,
           sortable: true,
           dataIndex: 'EKET_EINDT',
           editor: new fm.DateField({format: 'Ymd',renderer: Ext.util.Format.dateRenderer('Ymd')})
           },
           {header: '<font color="red">▲</font>标识：基于收货的发票验证',
           width: 200,
           sortable: true,
           dataIndex: 'EKPO_WEBRE_D_INVOICE_VALID',
           editor: new Ext.form.ComboBox({
        	   blankText:'标识：基于收货的发票验证必填',
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'ekpoWebre',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
           },
           {header: '<font color="red">▲</font>销售税代码',
           width: 200,
           sortable: true,
           dataIndex: 'EKPO_MWSKZ_D_SALES_TAX',
           editor: new Ext.form.ComboBox({
        	   blankText:'销售税代码必填',
               typeAhead: true,
               maxHeight:200,
               triggerAction: 'all',
               transform:'ekpoMwskz',
               lazyRender:true,
               listClass: 'x-combo-list-small',
               allowBlank: true
            })
           },
           {header: '<font color="red">▲</font>库存地点',
           width: 200,
           sortable: true,
           dataIndex: 'FACTORY_LOCAL_D_WAREHOUSE',
           editor: new Ext.form.ComboBox({
        	   blankText:'库存地点必填',
               typeAhead: true,
               maxHeight:150,
               triggerAction: 'all',
               transform:'factoryLocal',
               lazyRender:true,
               listClass: 'x-combo-list-small',
               allowBlank: true
            })
           },
           {header: '批次号',
           width:100,
           sortable: true,
           dataIndex: 'FLOW_NO',
           editor: new fm.TextField({
               allowBlank: true
           })
           },
           {header: '<font color="red">▲</font>含税单价',
           width: 100,
           sortable: true,
           dataIndex: 'PRICE',
           hidden:true,
           editor: new fm.NumberField({
        	   blankText:'含税单价必填',
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 9999999999999
           })
           },
           {header: '税金',
           width: 100,
           sortable: true,
           dataIndex: 'TAXES',
           hidden:true,
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 999999999999
           })
           },
           {header: '不含税金额汇总',
           width: 100,
           sortable: true,
           dataIndex: 'TOTAL_VALUE',
           hidden:true,
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 99999999999
           })
           },
           {header: '含税金额汇总',
           width: 100,
           sortable: true,
           dataIndex: 'TOTAL_TAXES',
           hidden:true,
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 9999999999999
           })
           },
           {header: '物料组',
           width: 100,
           sortable: true,
           dataIndex: 'MATERIEL_UNIT_D_MATERIAL_GROUP',
           hidden:true,
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'materielUnit',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
           },
           {header: '其他费用',
           width: 50,
           sortable: true,
           dataIndex: 'OTHERINFO',
           renderer:renderotherOper
           },
           {header: 'BOM组件',
           width: 100,
           sortable: true,
           dataIndex: 'BOMINFO',
           <c:if test="${purchase.ekkoBsart!='Z006'}">
            hidden:true,          
           </c:if>
           renderer:renderBOMOper
           }
    ]);
    productcm.defaultSortable = true;
    
    function MaterielcallbackFunction(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	var requestUrl = 'contractController.spr?action=addPurchaseMaterielInfo';
		requestUrl = requestUrl + '&contractpurchaseid=' + document.contractForm.contractPurchaseId.value;
		requestUrl = requestUrl + '&EkpoMatnr=' + returnvalue.MATNR;
		requestUrl = requestUrl + '&EkpoTxz01=' + returnvalue.MAKTX;
		requestUrl = requestUrl + '&EkpoMeins=' + returnvalue.MEINS;
		requestUrl = requestUrl + '&EkpoWerks=' + returnvalue.WERKS;
		requestUrl = requestUrl + '&eketEindt=' + $('shipmentDate').value;

		requestUrl = requestUrl + '&EkpoBprme=' + ekkoWaers_combo.getValue();

		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);
				var p = new productPlant({
	                    PURCHASE_ROWS_ID: responseArray.purchaseRowsId,
						CONTRACT_PURCHASE_ID: responseArray.contractPurchaseId,
						EKPO_PSTYP_D_ITEM_TYPE: responseArray.ekpoPstyp,
						SAP_ROW_NO: responseArray.sapRowNo,
						EKPO_MATNR: responseArray.ekpoMatnr,
						EKPO_TXZ01: responseArray.ekpoTxz01,
						EKPO_MEINS_D_UNIT: responseArray.ekpoMeins,
						EKPO_MENGE: responseArray.ekpoMenge,
						EKPO_NETPR: responseArray.ekpoNetpr,
						EKPO_BPRME_D_CURRENCY: responseArray.ekpoBprme,
						EKPO_WERKS_D_FACTORY: responseArray.ekpoWerks,
						EKET_EINDT:$('shipmentDate').value,//responseArray.eketEindt
						EKPO_WEBRE_D_INVOICE_VALID: responseArray.ekpoWebre,
						EKPO_MWSKZ_D_SALES_TAX: responseArray.ekpoMwskz,
						FACTORY_LOCAL_D_WAREHOUSE: responseArray.factoryLocal,
						FLOW_NO: responseArray.flowNo,
						TOTAL_VALUE: responseArray.totalValue,
						PRICE: responseArray.price,
						TAXES: responseArray.taxes,
						TOTAL_TAXES: responseArray.totalTaxes,
						MATERIEL_UNIT_D_MATERIAL_GROUP: responseArray.materielUnit,
						EKPO_PEINH:10000,
						OTHERINFO:'详情',
						BOMINFO:'详情'
	                });
				productgrid.stopEditing();
				productds.insert(0, p);
				productgrid.startEditing(0, 0);
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
    }
    
    var addProduct = new Ext.Toolbar.Button({
   		text:'增加',
   		iconCls:'add',
		handler:function(){
			var materialOrg=dict_div_ymatGroup.getActualValue();			
			if(materialOrg==""){
				top.ExtModalWindowUtil.alert('提示','请选择物料组！');
				return;
			}
			top.ExtModalWindowUtil.show('物料信息',
			'masterQueryController.spr?action=toFindMaterial&materialOrg=' + materialOrg,
			'',
			MaterielcallbackFunction,
			{width:755,height:478});
		}
   	});
   	
   	var commitProduct = new Ext.Toolbar.Button({
   		text:'删除',
   		iconCls:'delete',
		handler:function(){
			if (productgrid.selModel.hasSelection()){
				var records = productgrid.selModel.getSelections();
				if (records.length > 1){
					top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
				}else{
					
					var idList = '';
					for (var i=0;i<records.length;i++){
						idList = idList + records[i].data.PURCHASE_ROWS_ID + '|';
					}
				
				top.Ext.Msg.show({
					title:'提示',
  						msg: '是否确定删除记录',
  						buttons: {yes:'是', no:'否'},
  						fn: function(buttonId,text){
  							if(buttonId=='yes'){
  				        		var param1 = Form.serialize('baseForm');
  			            		var param2 = Form.serialize('contractForm');
  				
  								var param = "?action=deletePurchaseMaterielInfo";
								param = param + "&idList=" + idList+'&'+param1+'&'+param2;

								new AjaxEngine('contractController.spr', 
					   				{method:"post", parameters: param, onComplete: callBackHandle});
  							}
  							
  							strOperType = '1';
  						},
  						icon: Ext.MessageBox.QUESTION
				});
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的物料信息！');
			}
		}
   	});
   	
    var copyProduct = new Ext.Toolbar.Button({
   		text:'复制',
   		iconCls:'copy',
		handler:function(){
			if (productgrid.selModel.hasSelection()){
				var records = productgrid.selModel.getSelections();
				if(records.length>1){
					top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
				}else{
				    var record = records[0];

					var requestUrl = 'contractController.spr?action=copyPurchaseMaterielInfo';
					requestUrl = requestUrl + '&contractpurcharseMaterielid=' + record.data.PURCHASE_ROWS_ID;
                    
                    
					Ext.Ajax.request({
						url: encodeURI(requestUrl),
						success: function(response, options){
							productds.reload();
						}
					});
					setTimeout(function(){
						productgrid.selModel.selectRow(0,true);
						}, 1000);
					
				}
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要复制的物料信息！');
			}
			
		}
   	});
   	
   	var producttbar = new Ext.Toolbar({
		items:[addProduct,'-',commitProduct,'-',copyProduct]
	});
    
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
        tbar: producttbar,
        bbar: productbbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        el: 'div_product',
        layout:"fit",
        clicksToEdit:1,
        height:300,
        width:860
    });
    
    productgrid.render();
    
    productgrid.addListener('celldblclick', productgridcelldbclick);
    
    productds.load({params:{start:0, limit:10}});
    
    function updateMaterielcallbackFunction(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	
    	record.set('EKPO_MATNR',returnvalue.MATNR);
    	record.set('EKPO_TXZ01',returnvalue.MAKTX);
    	
    	var requestUrl = 'contractController.spr?action=updatePurchaseMaterielInfo';
		requestUrl = requestUrl + '&purchaserowsid=' + purchaserowsid;
		requestUrl = requestUrl + '&colname=EKPO_MATNR ';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.MATNR;
		
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				productds.commitChanges();
			},
			failure:function(response, options){
			}
		});
		
		requestUrl = 'contractController.spr?action=updatePurchaseMaterielInfo';
		requestUrl = requestUrl + '&purchaserowsid=' + purchaserowsid;
		requestUrl = requestUrl + '&colname=EKPO_TXZ01';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.MAKTX;
		
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				productds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }
    
    /*function updateFlowNoFunction(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	
    	record.set('FLOW_NO',returnvalue.BATCH);
    	
    	var requestUrl = 'contractController.spr?action=updatePurchaseMaterielInfo';
		requestUrl = requestUrl + '&purchaserowsid=' + purchaserowsid;
		requestUrl = requestUrl + '&colname=FLOW_NO ';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.BATCH;
		
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				productds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }*/
    
    function productgridcelldbclick(grid, rowIndex, columnIndex, e){
    	record = grid.getStore().getAt(rowIndex);
    	fieldName = grid.getColumnModel().getDataIndex(columnIndex);
    	purchaserowsid = record.get("PURCHASE_ROWS_ID");
    	
    	if (fieldName == 'EKPO_MATNR'){
    		top.ExtModalWindowUtil.show('物料信息',
			'masterQueryController.spr?action=toFindMaterial',
			'',
			updateMaterielcallbackFunction,
			{width:755,height:478});
    	}
    	
    	/*if (fieldName == 'FLOW_NO'){
    		var materiel = record.get("EKPO_MATNR");
    		var factory = getValue('ekpoWerks1',record.get('EKPO_WERKS_D_FACTORY'));
    		top.ExtModalWindowUtil.show('批次号信息',
			'masterQueryController.spr?action=toFindBatchNo&factory='+ factory + '&materialNo=' + materiel,
			'',
			updateFlowNoFunction,
			{width:200,height:350});
    	}*/
    }

    function renderotherOper(value, p, record){
    	return String.format('<a href="#" onclick="openOtherChargeWin()">{0}</a>','详情');
    }
    
    function renderBOMOper(value, p, record){
    	return String.format('<a href="#" onclick="openBOMWin()">{0}</a>','详情');
    }
    
    productgrid.on("afteredit", afterEdit, productgrid);
    
    function afterEdit(obj){
    	var row = obj.record;//获取被修改的行
        var colname = obj.field;//获取被修改的列
        var purchaserowsid = row.get("PURCHASE_ROWS_ID");
        var colvalue = row.get(colname);

        if (colname == 'EKPO_PSTYP_D_ITEM_TYPE'){
        	row.set(colname,getTitle('ekpoPstyp11',colvalue));
        	colname = 'EKPO_PSTYP';
        }
        
        if (colname == 'EKPO_WERKS_D_FACTORY'){
        	row.set(colname,getTitle('ekpoWerks1',colvalue));
        	colname = 'EKPO_WERKS';
        }
        
        if (colname == 'EKET_EINDT'){
        	colvalue = Ext.util.Format.date(colvalue, "Ymd");
        	row.set(colname,colvalue);
        }
        
        if (colname == 'MATERIEL_UNIT_D_MATERIAL_GROUP'){
        	row.set(colname,getTitle('materielUnit11',colvalue));
        	colname = 'MATERIEL_UNIT';
        }
        
        if (colname == 'FACTORY_LOCAL_D_WAREHOUSE'){
        	row.set(colname,getTitle('factoryLocal1',colvalue));
        	colname = 'FACTORY_LOCAL';
        }
        
        if (colname == 'EKPO_MWSKZ_D_SALES_TAX'){
        	row.set(colname,getTitle('ekpoMwskz1',colvalue));
        	colname = 'EKPO_MWSKZ';
        }
        
        if (colname == 'EKPO_MEINS_D_UNIT'){
        	row.set(colname,getTitle('ekpoMeins1',colvalue));
        	colname = 'EKPO_MEINS';
        }
        
        if (colname == 'EKPO_WEBRE_D_INVOICE_VALID'){
        	row.set(colname,getTitle('ekpoWebre1',colvalue));
        	colname = 'EKPO_WEBRE';
        }
        
        if (colname == 'EKPO_BPRME_D_CURRENCY'){
        	row.set(colname,getTitle('ekpoBprme1',colvalue));
        	colname = 'EKPO_BPRME';
        }        
        
        var requestUrl = 'contractController.spr?action=updatePurchaseMaterielInfo';
			requestUrl = requestUrl + '&purchaserowsid=' + purchaserowsid;
			requestUrl = requestUrl + '&colname=' + colname;
			requestUrl = requestUrl + '&colvalue=' + colvalue;
			
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				productds.commitChanges();
			},
			failure:function(response, options){
			}
		});
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
	            activeTab: 0,
	            items:[{
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
	            	title:'审批信息',
	            	contentEl: 'div_examHist',
	            	id:'examHist',
					name:'div_examHist',
	            	layout:'fit'
	            },{
	            	title:'同合同组项下合同',
	            	contentEl: '',
	            	id:'contractInGroup',
					name:'div_contractInGroup',
	            	layout:'fit',
	            	items:[contractgrid],
	            	listeners:{activate:handlerActivate}
	            }]
			}],
			buttons:[{
            	text:'保存',
            	handler:function(){
            		var materialOrg=dict_div_ymatGroup.getActualValue();			
					if(materialOrg==""){
						top.ExtModalWindowUtil.alert('提示','请选择物料组！');
						return;
					}
					//productds
					if(dict_div_vbkdZlsch.getActualValue()=='Z'&&$('mask').value==''){
			            top.ExtModalWindowUtil.alert('提示','请在备注描述混合支付！');
				        return;
			        }
			        var tradeType =$('tradeType').value;
					if((tradeType=='7'||tradeType=='10')&&$('collectionDate').value==''){
			            top.ExtModalWindowUtil.alert('提示','合同规定付款日必须填写！');
				        return;
			        }
			        if($('shipmentDate').value==''){
			            top.ExtModalWindowUtil.alert('提示','装运期/交货期必须填写！');
				        return;
			        }
			        //20140717进口业务国际贸易条件必填
			        if((tradeType=='1'||tradeType=='3'||tradeType=='6'||tradeType=='9'||tradeType=='11')&&dict_div_ekkoInco1.getSelectedValue()==''){
			            top.ExtModalWindowUtil.alert('提示','国际贸易条件1必须填写！');
				        return;
			        }
					var cnt = productds.getCount();
					var flag=false;
				    var taxFlag = false;
					for(var i=0;i<cnt;i++){
						var r = productds.getAt(i);
						if(r.data.EKPO_MENGE==0){
							flag = true;
							break;
						}
						taxFlagV = r.data.EKPO_MWSKZ_D_SALES_TAX;
						if(taxFlagV==null||taxFlagV=='请选择'||taxFlagV==''){
							taxFlag = true;
							break;
						}
					}
					if(flag){
						top.Ext.Msg.alert('提示','采购订单数量不能为0');
						return;
					}
					else if(taxFlag){
						top.Ext.Msg.alert('提示','销售税代码不能为空！');
						return;
					}
					else{
	            		var param1 = Form.serialize('baseForm');
	            		var param2 = Form.serialize('contractForm');
						var param = param1 + "&" + param2 + "&action=updatePurchaseContract";
						strOperType = '3';
						new AjaxEngine('contractController.spr', 
							   {method:"post", parameters: param, onComplete: callBackHandle});
					}   
            	}
            },{
            	text:'提交',
            	hidden: ${!submit},
            	id:'submitBtn',
            	handler:function(){
            		
            		var materialOrg=dict_div_ymatGroup.getActualValue();			
					if(materialOrg==""){
						top.ExtModalWindowUtil.alert('提示','请选择物料组！');
						return;
					}
					//productds
					if(dict_div_vbkdZlsch.getActualValue()=='Z'&&$('mask').value==''){
			            top.ExtModalWindowUtil.alert('提示','请在备注描述混合支付！');
				        return;
			        }
			        var tradeType =$('tradeType').value;
					if((tradeType=='7'||tradeType=='10')&&$('collectionDate').value==''){
			            top.ExtModalWindowUtil.alert('提示','合同规定付款日必须填写！');
				        return;
			        }
			        if($('shipmentDate').value==''){
			            top.ExtModalWindowUtil.alert('提示','装运期/交货期必须填写！');
				        return;
			        }
			        //20140717进口业务国际贸易条件必填
			        if((tradeType=='1'||tradeType=='3'||tradeType=='6'||tradeType=='9'||tradeType=='11')&&dict_div_ekkoInco1.getSelectedValue()==''){
			            top.ExtModalWindowUtil.alert('提示','国际贸易条件1必须填写！');
				        return;
			        }
            		if (div_accessory_ns_ds.getTotalCount() > 0){
            			var cnt = productds.getCount();
            			if (cnt> 0){
        					var flag=false;
        					var taxFlag = false;
        					for(var i=0;i<cnt;i++){
        						var r = productds.getAt(i);
        						if(r.data.EKPO_MENGE==0){
        							flag = true;
        							break;
        						}
        						taxFlagV = r.data.EKPO_MWSKZ_D_SALES_TAX;
								if(taxFlagV==null||taxFlagV=='请选择'||taxFlagV==''){
									taxFlag = true;
									break;
								}
        					}
        					if(flag){
        						top.Ext.Msg.alert('提示','采购订单数量不能为0');
        					}else if(taxFlag){
								top.Ext.Msg.alert('提示','销售税代码不能为空！');
								return;
							}
        					else{		
			            		var param1 = Form.serialize('baseForm');
			            		var param2 = Form.serialize('contractForm');
								var param = param1 + "&" + param2 + "&action=submitOperationPurchaseInfo";
								new AjaxEngine('contractController.spr', 
									   {method:"post", parameters: param, onComplete: callBackHandle});
									   
								strOperType = '2';
        					}
						}else{
							top.Ext.Msg.show({
								title:'提示',
			  					msg: '没有增加物料信息,请确认是否真的要提交审批?',
			  					buttons: {yes:'是', no:'否'},
			  					fn: function(buttonId,text){
			  						if(buttonId=='yes'){
			  							var param1 = Form.serialize('baseForm');
		            					var param2 = Form.serialize('contractForm');
										var param = param1 + "&" + param2 + "&action=submitOperationPurchaseInfo";
										new AjaxEngine('contractController.spr', 
								   			{method:"post", parameters: param, onComplete: callBackHandle});
								   				
								   		strOperType = '2';
			  						}
			  					},
			  					icon: Ext.MessageBox.QUESTION
							});
						}
					}else{
						top.ExtModalWindowUtil.alert('提示','请上传合同附件后再提交审批！');
					}
            	}
            },{
            	text:'写入SAP',
             	hidden: ${writeSap},           	
            	handler:function(){
             		strOperType='4';
            		var param1 = Form.serialize('baseForm');
            		var param2 = Form.serialize('contractForm');
					var param = param1 + "&" + param2 + "&action=submitToSapPurchaserInfo";
					new AjaxEngine('contractController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
            	}
            },{
            	text:'关闭',
            	hidden: ${show},
            	handler:function(){
            		top.ExtModalWindowUtil.markUpdated();
	                top.ExtModalWindowUtil.close();
	            }
            }]
		}]
	});
    var ekkoWaers_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'ekkoWaers',
        width:157,
        allowBlank:false,
        blankText:'请输入货币',
        forceSelection:true
     });
    ekkoWaers_combo.setValue('${purchase.ekkoWaers}'); 		

    var ekkoEkgrp_combo = new Ext.form.ComboBox({
        typeAhead: true,
        triggerAction: 'all',
        transform:'ekkoEkgrp',
        width:157,
        allowBlank:false,
        blankText:'请输入采购组',
        forceSelection:true
     });
    ekkoEkgrp_combo.setValue('${purchase.ekkoEkgrp}'); 		
    
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


function getValue(sourceid,code)
{
	var sourceObj = document.getElementById(sourceid);
	var dropDownList = sourceObj.options;
	for(i=0;i<dropDownList.length;i++)
	{
		if(dropDownList[i].text==code)
			return dropDownList[i].value;
	}
}
function mainInit(){
	var tradeType = '${purchase.tradeType}';
	$("trView1").style.display = "none";
	//出口，内贸业务,进料加工
	if (tradeType == '2' || tradeType == '4' || tradeType == '5' || tradeType == '7' || tradeType == '8'|| tradeType == '10'|| tradeType == '12' ){
		$("txtShipmentPort").value = "交货地点";
		$("txtShipmentDate").value = "交货日期";
		$("ekkoInco2").disabled=true;
		$("ekkoInco2").style.background="#CCCCCC";
		$("destinationPort").disabled  = true;
		$("destinationPort").style.background="#CCCCCC";
		dict_div_ekkoInco1.disable(true);
		$("trView1").style.display = "";
		$("tdView3").style.display = "none";
		$("tdView4").style.display = "none";
	}
	//进口
	if (tradeType == '1' || tradeType == '3' || tradeType == '6' || tradeType == '9'|| tradeType == '11'){
		$("trView1").style.display = "";		
	}
}
window.onload = function() {
	mainInit();
}
</script>
<fiscxd:dictionary divId="div_ekkoZterm" fieldName="ekkoZterm" dictionaryName="BM_PAYMENT_TYPE" width="153" selectedValue="${purchase.ekkoZterm}"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_ekkoInco1" fieldName="ekkoInco1" needDisplayCode="true" dictionaryName="BM_INCOTERM_TYPE" width="153" selectedValue="${purchase.ekkoInco1}"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbkdZlsch" fieldName="payType" dictionaryName="BM_PAY_TYPE" width="150" selectedValue="${purchase.payType}"></fiscxd:dictionary>


<fiscxd:dictionary divId="div_ymatGroup" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${purchase.ymatGroup}" disable="false" ></fiscxd:dictionary>
