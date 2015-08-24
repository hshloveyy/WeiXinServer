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
	<font color="red">*</font>
	采购组：
	</td>
	<td>
	<div id="div_ekkoEkgrp"></div>
    <!--<input type="text" size="16" id="ekkoEkgrp" name="ekkoEkgrp" value="${purchase.ekkoEkgrp}"/>
	<input type="button" value="..." onclick="showFindGroup()"/>
 -->
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
<fiscxd:dictionary divId="div_ymatGroup" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${purchase.ymatGroup}" disable="false" ></fiscxd:dictionary>

<div id="div_contractInfo"  class="x-hide-display">
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
	<td align="right"><font color="red">*</font>合同名称：</td>
	<td colspan="3">
	<input type="text" id="contractName" name="contractName" value="${purchase.contractName}" size="62"></input>
	</td>
</tr>
<tr>
	<td align="right"><font color="red">*</font>采购凭证日期：</td>
	<td>
	<input type="text" id="ekkoBedat" name="ekkoBedat" value="${purchase.ekkoBedat}"></input>
	</td>
	<td align="right"><font color="red">*</font>货币码:</td>
	<td>
	<div id="div_ekkoWaers"></div>
	</td>
	<td align="right">外部纸质合同号：</td>
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
	<td align="right"><font color="red">*</font>付款条件：</td>
	<td>
	<div id="div_ekkoZterm"></div>
	</td>
</tr>
<tr>
	<td align="right"><font color="red">*</font>供应商编码：</td>
	<td>
	<input type="text" readonly="readonly" id="ekkoLifnr" name="ekkoLifnr" value="${purchase.ekkoLifnr}" size="15"></input>
	<input type="button" value="..." onclick="openekkoLifnrWin()"></input>
	</td>
	<td align="right"><font color="red">*</font>供应商名称: </td>
	<td>
	<input readonly="readonly" type="text" id="ekkoLifnrName" name="ekkoLifnrName" value="${purchase.ekkoLifnrName}"></input>
	</td>
	<td align="right"><font color="red">*</font>汇率：</td>
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
	<td align="right"></td>
	<td>
	</td>
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
	<input type="text" id="ekkoInco2" name="ekkoInco2" value="${purchase.ekkoInco2}"></input>
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
	<td align="right" id="tdView1">合同规定收款日:</td>
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
		<textarea name="mask" style="width:100%;overflow-y:visible;word-break:break-all;">${purchase.mask}</textarea>
    </td>
    <td align="right">手册号：</td>
	<td>
	<input type="text" id="ekkoTelf1" name="ekkoTelf1" value="${purchase.ekkoTelf1}"></input>
	</td>
</tr>
</table>
</form>

<div id="div_product"></div>
</div>

<div id="div_accessory" class="x-hide-display"></div>
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
		<option value="${row.code}">${row.code} ( ${row.title} )</option>
	</c:forEach>
</select>
<select name="ekpoBprme1" id="ekpoBprme1" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Currency}" var="row">
		<option value="${row.code}">${row.code} ( ${row.title} )</option>
	</c:forEach>
</select>
</body>
</html>
<fiscxd:fileUpload divId="div_accessory" increasable="false" erasable="false" resourceId="2222" resourceName="33333" recordIdHiddenInputName="444" recordId="${purchase.contractPurchaseId}"></fiscxd:fileUpload>
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
	//if (strOperType == '3'){
		document.contractForm.total.value = customField.SumTotal;
	//}
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
           {header: '*项目类别',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_PSTYP_D_ITEM_TYPE',
           editor: new Ext.form.ComboBox({
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
           {header: '*采购订单数量',
           width: 150,
           sortable: true,
           dataIndex: 'EKPO_MENGE',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:3,
               maxValue: 999999999999
           })
           },
           {header: '*采购凭证中的净价(以凭证货币计)',
           width: 200,
           sortable: true,
           dataIndex: 'EKPO_NETPR',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 999999999999
           })
           },
           {header: '价格单位',
           width: 100,
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
 		   {header: '*每价格单位',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_PEINH',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 999999999999
           })
           },           
           {header: '*工厂',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_WERKS_D_FACTORY',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'ekpoWerks',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
           },
           {header: '*装船期/项目交货日期',
           width: 150,
           sortable: true,
           dataIndex: 'EKET_EINDT',
           editor: new fm.DateField({format: 'Ymd',renderer: Ext.util.Format.dateRenderer('Ymd')})
           },
           {header: '*标识：基于收货的发票验证',
           width: 200,
           sortable: true,
           dataIndex: 'EKPO_WEBRE_D_INVOICE_VALID',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'ekpoWebre',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
           },
           {header: '*销售税代码',
           width: 100,
           sortable: true,
           dataIndex: 'EKPO_MWSKZ_D_SALES_TAX',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'ekpoMwskz',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
           },
           {header: '*库存地点',
           width: 100,
           sortable: true,
           dataIndex: 'FACTORY_LOCAL_D_WAREHOUSE',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'factoryLocal',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
           },
           {header: '*批次号',
           width: 100,
           sortable: true,
           dataIndex: 'FLOW_NO',
           editor: new fm.TextField({
               allowBlank: true
           })
           },
           {header: '*含税单价',
           width: 100,
           sortable: true,
           dataIndex: 'PRICE',
           hidden:true,
           editor: new fm.NumberField({
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
           width: 100,
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

		requestUrl = requestUrl + '&EkpoBprme=' + dict_div_ekkoWaers.getSelectedValue().trim()

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
						EKET_EINDT: responseArray.eketEindt,
						EKPO_WEBRE_D_INVOICE_VALID: responseArray.ekpoWebre,
						EKPO_MWSKZ_D_SALES_TAX: responseArray.ekpoMwskz,
						FACTORY_LOCAL_D_WAREHOUSE: responseArray.factoryLocal,
						FLOW_NO: responseArray.flowNo,
						TOTAL_VALUE: responseArray.totalValue,
						PRICE: responseArray.price,
						TAXES: responseArray.taxes,
						TOTAL_TAXES: responseArray.totalTaxes,
						MATERIEL_UNIT_D_MATERIAL_GROUP: responseArray.materielUnit,
						EKPO_PEINH:1,
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
			top.ExtModalWindowUtil.show('物料信息',
			'masterQueryController.spr?action=toFindMaterial',
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
   	
   	var producttbar = new Ext.Toolbar({
		items:[addProduct,'-',commitProduct]
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
        autoWidth:true,
        miniWidth:860
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
	            autoScroll:'true',
	            activeTab: 0,
	            items:[{
					title:'合同变更',
					id:'alterId',
					html:'<iframe src="purchaseChangeController.spr?action=toChangeTab&from=${from}&contractId=${purchase.contractPurchaseId}" width="100%" height="100%" id="alter" ></iframe>'
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
	            }]
			}],
			buttons:[{
            	text:'保存',
            	handler:function(){
            		var param1 = Form.serialize('baseForm');
            		var param2 = Form.serialize('contractForm');
					var param = param1 + "&" + param2 + "&action=updatePurchaseContract";
					strOperType = '3';
					new AjaxEngine('contractController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
            	}
            }]
		}]
	});
});

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
	if (tradeType == '2' || tradeType == '4' || tradeType == '5' || tradeType == '7' || tradeType == '8'|| tradeType == '10' ){
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
	if (tradeType == '1' || tradeType == '3' || tradeType == '6' || tradeType == '9'){
		$("trView1").style.display = "";		
	}
}
window.onload = function() {
	mainInit();
}
</script>
<fiscxd:dictionary divId="div_ekkoZterm" fieldName="ekkoZterm" dictionaryName="BM_PAYMENT_TYPE" width="153" selectedValue="${purchase.ekkoZterm}"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_ekkoInco1" fieldName="ekkoInco1" needDisplayCode="true" dictionaryName="BM_INCOTERM_TYPE" width="153" selectedValue="${purchase.ekkoInco1}"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_ekkoWaers" fieldName="ekkoWaers"  dictionaryName="BM_CURRENCY" width="153" selectedValue="${purchase.ekkoWaers}" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_ekkoEkgrp" fieldName="ekkoEkgrp" dictionaryName="BM_PURCHASING_GROUP" width="153" selectedValue="${purchase.ekkoEkgrp}" needDisplayCode="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbkdZlsch" fieldName="payType" dictionaryName="BM_PAY_TYPE" width="150" selectedValue="${purchase.payType}"></fiscxd:dictionary>