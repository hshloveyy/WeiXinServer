<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ taglib prefix="ffcs" tagdir="/WEB-INF/tags/infolion"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.save {
	background-image: url(/infolionPlatform/images/fam/save.gif) !important;
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

<div id="div_basrForm" >
<form action="" id="baseForm" name="baseForm">
<table width="100%">
<tr>
	<td align="right" width="13%" >	
		<font color="red">*</font> 销售组织：
	</td>
	<td width="20%">
	<input type="text" readonly="readonly" id="vbakVkorg" name="vbakVkorg" value="${sales.vbakVkorg}"></input>
	</td>
	<td align="right" width="13%">
		<font color="red">*</font>分销渠道：
	</td>
	<td width="20%">
	<div id="div_vbakVtweg"></div>
	</td>
	<td align="right"width="13%">
		<font color="red">*</font>产品组：
	</td>
	<td width="20%">
	<div id="div_vbakSpart"></div>
	</td>
</tr>
<tr>
	<td align="right">	
		<font color="red">*</font>销售部门：
	</td>
	<td>
	<div id="div_vbakVkbur"></div>
	</td>
		<td align="right">	
		物料组<font color="red">▲</font>
	</td>
	<td  >	
		<div id="div_ymatGroup"></div>
	</td>
	
	
	<td align="right">
		<font color="red">*</font>凭证类型：
	</td>
	<td>
         <c:choose>
         	<c:when test="${sales.tradeType=='3'}">
	            <select id = "vbakAuart" name="vbakAuart">
	               <option value="">请选择</option>
                   <option value="Z002" <c:if test="${sales.vbakAuart=='Z002'}"> selected </c:if> >Z002</option>
                   <option value="Z003" <c:if test="${sales.vbakAuart=='Z003'}"> selected </c:if> >Z003</option>
	           </select> 
      		</c:when>
       		<c:otherwise>
       			 <input type="text" readonly="readonly"  id="vbakAuart" name="vbakAuart" value="${sales.vbakAuart}"></input>       			
       		</c:otherwise>
         </c:choose>	
	</td>
</tr>
<tr>
<td align="right">
		<font color="red">*</font>销售组：
	</td>
	<td>
	<input type="text" readonly="readonly" size="16" id="vbakVkgrp" name="vbakVkgrp" value="${sales.vbakVkgrp}"/>
	<input type="button" value="..." onclick="showFindGroup()"/>
	<div id="div_vbakVkgrp"></div>
	</td>
	<td align="right" colspan="4">	</td>
</tr>
</table>
</form>
</div>

<div id="div_contractInfo" class="x-hide-display">
<form action="" id="contractForm" name="contractForm">
<table width="100%">
<tr>
	<td align="right" width="13%"><font color="red">*</font>项目名称：</td>
	<td width="20%">
	<input type="hidden" id="contractSalesId" name="contractSalesId" value="${sales.contractSalesId}"></input>
	<input type="hidden" id="projectId" name="projectId" value="${sales.projectId}"></input>
	<input type="hidden" id="contractGroupId" name="contractGroupId" value="${sales.contractGroupId}"></input>
	<input type="hidden" id="deptId" name="deptId" value="${sales.deptId}"></input>
	<input type="hidden" id="createTime" name="createTime" value="${sales.createTime}"></input>	
	<input type="hidden" id="creator" name="creator" value="${sales.creator}"></input>
	<input type="hidden" id="orderState" name="orderState" value="${sales.orderState}"></input>	
	<input type="hidden" id="isAvailable" name="isAvailable" value="${sales.isAvailable}"></input>
	<input type="hidden" id="tradeType" name="tradeType" value="${sales.tradeType}"></input>								
    <input type="hidden" id="applyTime" name="applyTime" value="${sales.applyTime}"></input>	
	<input type="hidden" id="approvedTime" name="approvedTime" value="${sales.approvedTime}"></input>						
	
	<input type="text" readonly="readonly" id="projectName" name="projectName" value="${sales.projectName}"></input>
	</td>
	<td align="right"width="13%" ><font color="red">*</font>信达项目号：</td>
	<td width="20%">
	<nobr>
	<input type="text" readonly="readonly"  id="vbakBname" name="vbakBname" value="${sales.vbakBname}"></input><a href="#" onclick="viewProjectForm()">查看</a>
	</nobr>
	</td>
	<script>
	function viewProjectForm(){
			top.ExtModalWindowUtil.show('查看立项申请','projectController.spr?action=modify&from=view&projectId=${sales.projectId}','','',{width:800,height:500});
    }
    </script>
	<td align="right" width="13%" >合同组名称：</td>
	<td width="20%">
	<input type="text" readonly="readonly" id="contractGroupName" name="contractGroupName" value="${contractGroup.contractGroupName}"></input>
	</td>	


</tr>
<tr>
	<td align="right">原合同编码：</td>
	<td>
	<input type="text" id="oldContractNo" name="oldContractNo" value="${sales.oldContractNo}"></input>
	</td>
	<td align="right"><font color="red">*</font>合同编码：</td>
	<td>
	<input type="text" readonly="readonly" id="contractNo" name="contractNo" value="${sales.contractNo}"></input>
	</td>
	<td align="right">
		<font color="red">*</font>合同名称：
	</td>
	<td>
	<input type="text" id="contractName" name="contractName" value="${sales.contractName}"></input>
	</td>	
</tr>
<tr>
	<td align="right">
		手册号：
	</td>
	<td>
	<input type="text" id="vbkdBstkdE" name="vbkdBstkdE" value="${sales.vbkdBstkdE}"></input>
	</td>
	<td align="right">
		外部纸质合同号：
	</td>
	<td>
	<input type="text" id="vbkdIhrez" name="vbkdIhrez" value="${sales.vbkdIhrez}"></input>
	</td>
	<td align="right">
		<font color="red">*</font>SAP订单号：
	</td>
	<td>
	<input type="hidden" id="vbakAudat" name="vbakAudat" value="${sales.vbakAudat}"></input>
	<input type="text" readonly="readonly" id="sapOrderNo" name="sapOrderNo" value="${sales.sapOrderNo}"></input>
	</td>		
</tr>
<tr>
	<td align="right"><font color="red">*</font>付款条件:</td>
	<td>
	<div id="div_vbkdZterm"></div>
	</td>
	<td align="right"><font color="red">*</font>付款方式：</td>
	<td>
		<div id="div_vbkdZlsch"></div>
	</td>
	<td align="right">销售地区:</td>
	<td>
	<div id="div_vbkdBzirk"></div>
	</td>	
</tr>
<tr>
	<td align="right"><font color="red">*</font>货币码：</td>
	<td>
	<div id="div_vbakWaerk"></div>
	</td>
	<td align="right"><font color="red">*</font>会计汇率：</td>
	<td>
	<input type="text" id="vbkdKurrf" name="vbkdKurrf" value="${sales.vbkdKurrf}"></input>
	</td>
	<td align="right">装运期：</td>
	<td>
	<input type="text" id="shipmentDate" name="shipmentDate" value="${sales.shipmentDate}"></input>
	</td>	
</tr>
<tr>
	<td align="right">国际贸易条件1：</td>
	<td>
	<div id="div_vbkdInco1"></div>
	</td>
	<td align="right">国际贸易条件2：</td>
	<td>
		<input type="text" id="vbkdInco2" name="vbkdInco2" value="${sales.vbkdInco2}"></input>
	</td>
	<td align="right">装运港：</td>
	<td>
		<input type="text" id="shipmentPort" name="shipmentPort" value="${sales.shipmentPort}"></input>
	</td>
</tr>
<tr>
	<td align="right"><font color="red">*</font>售达方：</td>
	<td>
	<input type="text" id="kuagvKunnr" name="kuagvKunnr" value="${sales.kuagvKunnr}" size="15" readonly="readonly"></input>
	<input type="button" value="..." onclick="showFindCustomer(1);"></input>
	</td>
	<td align="right">售达方名称：</td>
	<td>
	<input type="text" id="kuagvKunnrName" name="kuagvKunnrName" value="${sales.kuagvKunnrName}" readonly="readonly"></input>
	</td>
	<td align="right">目的港：</td>
	<td>
	<input type="text" id="destinationPort" name="destinationPort" value="${sales.destinationPort}"></input>
	</td>		
</tr>
<tr>
	<td align="right"><font color="red">*</font>送达方：</td>
	<td>
	<input type="text" id="kuwevKunnr" name="kuwevKunnr" value="${sales.kuwevKunnr}" size="15" readonly="readonly"></input>
	<input type="button" value="..." onclick="showFindCustomer(2);"></input>
	</td>
	<td align="right">送达方名称：</td>
	<td>
	<input type="text" id="kuwevKunnrName" name="kuwevKunnrName" value="${sales.kuwevKunnrName}" readonly="readonly"></input>
	</td>
	<td align="right">净金额：</td>
	<td>
	<input type="text" id="orderNet" name="orderNet" value="${sales.orderNet}" readonly="readonly"></input>
	</td>
</tr>
<tr>
	<td align="right"><font color="red">*</font>付费方：</td>
	<td>
	<input type="text" id="payer" name="payer" value="${sales.payer}" size="15" readonly="readonly"></input>
	<input type="button" value="..." onclick="showFindCustomer(3);"></input>
	</td>
	<td align="right">付费方名称：</td>
	<td >
	<input type="text" id="payerName" name="payerName" value="${sales.payerName}" readonly="readonly"></input>
	</td>
	<td align="right">销项税金：</td>
	<td>
	<input type="text" id="orderTaxes" name="orderTaxes" value="${sales.orderTaxes}" readonly="readonly" ></input>
	</td>	
</tr>
<tr>
	<td align="right"><font color="red">*</font>收票方：</td>
	<td>
	<input type="text" id="bllToParty" name="bllToParty" value="${sales.bllToParty}" size="15" readonly="readonly"></input>
	<input type="button" value="..." onclick="showFindCustomer(4);"></input>
	</td>
	<td align="right">收票方名称：</td>
	<td>
	<input type="text" id="bllToPartyName" name="bllToPartyName" value="${sales.bllToPartyName}" readonly="readonly"></input>
	</td>
	<td align="right">总金额：</td>
	<td>                                         
	<input type="text" id="orderTotal" name="orderTotal" value="${sales.orderTotal}" readonly="readonly"></input>
	</td>	
</tr>
<tr>
	<td align="right">备注: </td>
	<td colspan="3">
		<textarea name="mask" style="width:100%;overflow-y:visible;word-break:break-all;">${sales.mask}</textarea>
    </td>
</tr>

</table>
</form>
<div id="div_product"></div>
<c:choose>
	<c:when test="${sales.tradeType=='5' || sales.tradeType == '6' }">
		<div id="div_surrogateInfo"></div>
	</c:when>
	<c:otherwise>
		<div id="div_surrogateInfo" class="x-hide-display" ></div>        			
   	</c:otherwise>
</c:choose>
</div>

<div id="div_accessory" class="x-hide-display"></div>

<select name="vbapPstyv" id="vbapPstyv" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${ItemType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbapPstyvAgent" id="vbapPstyvAgent" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${ItemType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbapPstyv11" id="vbapPstyv11" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${ItemType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>

<select name="vbapWerks" id="vbapWerks" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Factory}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbapWerksAgent" id="vbapWerksAgent" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Factory}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbapWerks11" id="vbapWerks11" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${Factory}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbkdZterm" id="vbkdZterm" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${PayMentType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbkdZtermAgent" id="vbkdZtermAgent" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${PayMentType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbkdZterm11" id="vbkdZterm11" style="display: none;">
	<option value="">请选择</option>
	<c:forEach items="${PayMentType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>

</body>
</html>
<fiscxd:fileUpload divId="div_accessory" erasable="false" resourceId="2222" resourceName="33333" recordIdHiddenInputName="444" recordId="${sales.contractSalesId}"></fiscxd:fileUpload>
<script type="text/javascript">
var renderFlag = false;
var productId = '';
var productName = '';
var productUnit = '';
var productds;
var surrogateds;

var productgrid;
var surrogategrid;
var num = "0";
function openOtherChargeWin(){
	var records = productgrid.selModel.getSelections();
   	top.ExtModalWindowUtil.show('增加其他费用',
	'contractController.spr?action=addSaleCaseView&salesrowsid='+records[0].data.SALES_ROWS_ID + "&saprowno="+records[0].data.SAP_ROW_NO,
	'','',{width:800,height:300});
}
function openAgentOtherChargeWin(){
	var records = surrogategrid.selModel.getSelections();
   	top.ExtModalWindowUtil.show('增加其他费用',
	'contractController.spr?action=addAgentCaseView&salesrowsid='+records[0].data.SALES_ROWS_ID,
	'',
	'',
	{width:800,height:300});
}
function showFindCustomer(inputNum){
	num = inputNum;
	if (num == 1){
		top.ExtModalWindowUtil.show('查找售达方','masterQueryController.spr?action=toFindCustomer','',customerCallback,{width:755,height:410});	
	} else if (num == 2){
		top.ExtModalWindowUtil.show('查找送达方','masterQueryController.spr?action=toFindCustomer','',customerCallback,{width:755,height:410});		
	} else if (num == 3){
		top.ExtModalWindowUtil.show('查找付款方','masterQueryController.spr?action=toFindCustomer','',customerCallback,{width:755,height:410});	
	} else if (num ==4){
		top.ExtModalWindowUtil.show('查找收票方','masterQueryController.spr?action=toFindCustomer','',customerCallback,{width:755,height:410});		
	}

}
function customerCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	if (num ==1){
		Ext.getDom('kuagvKunnr').value=cb.KUNNR;
		Ext.getDom('kuagvKunnrName').value=cb.NAME1;	
		Ext.getDom('kuwevKunnr').value=cb.KUNNR;
		Ext.getDom('kuwevKunnrName').value=cb.NAME1;
		Ext.getDom('payer').value=cb.KUNNR;
		Ext.getDom('payerName').value=cb.NAME1;	
		Ext.getDom('bllToParty').value=cb.KUNNR;
		Ext.getDom('bllToPartyName').value=cb.NAME1;
	} else if (num == 2){
		Ext.getDom('kuwevKunnr').value=cb.KUNNR;
		Ext.getDom('kuwevKunnrName').value=cb.NAME1;	
	} else if (num == 3){
		Ext.getDom('payer').value=cb.KUNNR;
		Ext.getDom('payerName').value=cb.NAME1;	
	} else if (num == 4){
		Ext.getDom('bllToParty').value=cb.KUNNR;
		Ext.getDom('bllToPartyName').value=cb.NAME1;		
	}
}

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	document.contractForm.orderTotal.value=customField.moneyTotal;
	document.contractForm.orderTaxes.value=customField.taxTotal;
	document.contractForm.orderNet.value=customField.netTotal;
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if (strOperType == '1'){
		productds.reload();	
//		setContractSaleMoney();
	} else if (strOperType == '2'){
		surrogateds.reload();
	}
	if (strOperType == '3'){
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	}	
}
function showFindGroup(){
	top.ExtModalWindowUtil.show('查找销售组','masterQueryController.spr?action=toFindGroup&dept=${sales.vbakVkorg}','',groupCallback,{width:320,height:380});
}
function groupCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('vbakVkgrp').value=cb.VKGRP;
}

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

   	div_accessory_ns_ds.load({params:{start:0, limit:10,recordId:'${sales.contractSalesId}'}});
   		
   	var fm = Ext.form;
   	
   	
   	
   	var ShipmentDate = new Ext.form.DateField({
   		format:'Ymd',
		name:"shipmentDate",
		id:"shipmentDate",
		width: 155,
		readOnly:true,
		applyTo:'shipmentDate'
   	});
   	
   	var productPlant = Ext.data.Record.create([
   		{name:'SALES_ROWS_ID'},
   		{name:'CONTRACT_SALES_ID'}, 
   		{name:'SAP_ROW_NO'},    		   		   		
   		{name:'VBAP_PSTYV_D_ITEM_TYPE'},
   		{name:'VBAP_MATNR'},
   		{name:'VBAP_ARKTX'},
   		{name:'VBAP_VRKME'},   		
   		{name:'VBAP_ZMENG'},
   		{name:'RV45A_ETDAT'},  		
   		{name:'VBAP_WERKS_D_FACTORY'},
   		{name:'VBAP_UEBTO'},
   		{name:'VBAP_UNTTO'},
   		{name:'VBAP_KWMENG'},
   		{name:'KONV_KBETR'},
   		{name:'VBKD_ZTERM_D_PAYMENT_TYPE'},
		{name:'VBKD_ZTERM_NAME'},   		
   		{name:'VBAP_KPEIN'},
   		{name:'VBAP_KMEIN'},
   		{name:'PAYER'},
   		{name:'PAYER_NAME'},
   		{name:'BILL_TO_PARTY'},
   		{name:'BILL_TO_PARTY_NAME'},   		
   		{name:'ROW_TOTAL'},
   		{name:'ROW_NET'},
   		{name:'ROW_TAXES'},
   		{name:'ROW_TAXES_RALE'},
   		{name:'OTHERINFO'}
	]);
	
	productds = new Ext.data.Store({
   		proxy : new Ext.data.HttpProxy({url:'contractController.spr?action=quertSalesMateriel&contractsalesid='+document.contractForm.contractSalesId.value}),
   		reader: new Ext.data.JsonReader({
       	root: 'root',
       	totalProperty: 'totalProperty'
       	},productPlant)
    });
    
    var productsm = new Ext.grid.CheckboxSelectionModel();
    
    var productcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		productsm,
			{header: '销售合同行项ID',
           	width: 10,
           	sortable: true,
           	dataIndex: 'SALES_ROWS_ID',
           	hidden:true
			},
			{header: '销售合同ID',
           	width: 10,
           	sortable: true,
           	dataIndex: 'CONTRACT_SALES_ID',
           	hidden:true
			},
			{header: '关联ID',
           	width: 10,
           	sortable: true,
           	dataIndex: 'SAP_ROW_NO',
           	hidden:true
			},			
            {header: '*项目类别',
            width: 80,
            sortable: true,
            dataIndex: 'VBAP_PSTYV_D_ITEM_TYPE',
            editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'vbapPstyv',
               lazyRender:true,
               listClass: 'x-combo-list-small'
             })            
           },
           {header: '物料号',
           width: 80,
           sortable: true,
           dataIndex: 'VBAP_MATNR'          
           },
           {header: '物料描述',
           width: 60,
           sortable: true,
           dataIndex: 'VBAP_ARKTX'
           },
           {header: '单位',
           width: 60,
           sortable: true,
           dataIndex: 'VBAP_VRKME'
           },           
           {header: '*订单数量',
           width: 80,
           sortable: true,
           dataIndex: 'VBAP_ZMENG',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:3,
               maxValue: 999999999999
           })           
           },

           {header: '*项目交货日期',
           width: 100,
           sortable: true,
           dataIndex: 'RV45A_ETDAT',
           editor: new fm.DateField({format: 'Ymd',renderer: Ext.util.Format.dateRenderer('Ymd')})                     
           },
           {header: '*工厂',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_WERKS_D_FACTORY',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'vbapWerks',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
           },
           {header: '*条过量交货限度（百分比）',
           width: 150,
           sortable: true,
           dataIndex: 'VBAP_UEBTO',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 99
           })             
           },
           {header: '*交货不足限度',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_UNTTO',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 99
           })             
           },
           {header: '*行项目数量',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_KWMENG',
           hidden:true,
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:3,
               maxValue:999999999999
           })            
           },
           {header: '*税码(%)',
           width: 100,
           sortable: true,
           dataIndex: 'ROW_TAXES_RALE',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 100
           }) 
           },           
           {header: '*含税单价',
           width: 100,
           sortable: true,
           dataIndex: 'KONV_KBETR',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               decimalPrecision:2,
               maxValue: 999999999999
           })           
           },
           {header: '税金',
           width: 100,
           sortable: true,
           dataIndex: 'ROW_TAXES'
           },            
           {header: '净价',
           width: 100,
           sortable: true,
           dataIndex: 'ROW_NET'         
           },          
           {header: '*付款条件',
           width: 80,
           sortable: true,
           dataIndex: 'VBKD_ZTERM_D_PAYMENT_TYPE',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'vbkdZterm',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })          
           },
           {header: '付款条件名称',
           width: 60,
           sortable: true,          
           dataIndex: 'VBKD_ZTERM_NAME',
           hidden:true 
           },           
           {header: '*条件定价单位',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_KPEIN',
           editor: new fm.NumberField({
        	   maxValue: 10000,
        	   decimalPrecision:0,
               allowBlank: true
           })           
           },
           {header: '*在凭证中的条件单位',
           width: 130,
           sortable: true,
           dataIndex: 'VBAP_KMEIN',
           editor: new fm.TextField({
               allowBlank: true
           })
           },
           {header: '付款方',
           width: 60,
           sortable: true,
           dataIndex: 'PAYER'
           }, 
           {header: '付款方名称',
           width: 100,
           sortable: true,
           dataIndex: 'PAYER_NAME'
           },   
           {header: '收票方',
           width: 60,
           sortable: true,
           dataIndex: 'BILL_TO_PARTY'
           }, 
           {header: '收票方名称',
           width: 100,
           sortable: true,
           dataIndex: 'BILL_TO_PARTY_NAME'
           },                                
           {header: '行项目总金额',
           width: 80,
           sortable: true,
           dataIndex: 'ROW_TOTAL',
           hidden:true
           },
           {header: '其他费用',
           width: 100,
           sortable: true,
           dataIndex: 'OTHERINFO',
           renderer:renderotherOper
           }
    ]);
    productcm.defaultSortable = true;
    
    function MaterielcallbackFunction(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	var requestUrl = 'contractController.spr?action=addSalesMateriel';
		requestUrl = requestUrl + '&contractsalesid=' + document.contractForm.contractSalesId.value;
		requestUrl = requestUrl + '&vbapMarnr=' + returnvalue.MATNR;
		requestUrl = requestUrl + '&vbapArktx=' + returnvalue.MAKTX;
		requestUrl = requestUrl + '&vbapVrkme=' + returnvalue.MEINS;
		requestUrl = requestUrl + '&vbapWerks=' + returnvalue.WERKS;
		requestUrl = requestUrl + '&payer=' + Ext.getDom('payer').value;
		requestUrl = requestUrl + '&payerName=' + Ext.getDom('payerName').value;
		requestUrl = requestUrl + '&billToParty=' + Ext.getDom('bllToParty').value;
		requestUrl = requestUrl + '&billToPartyName=' + Ext.getDom('bllToPartyName').value;
		requestUrl = requestUrl + '&vbkdZterm=' + dict_div_vbkdZterm.getSelectedValue().trim()

		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);

				var p = new productPlant({
	                    SALES_ROWS_ID: responseArray.salesRowsId,
						CONTRACT_SALES_ID: responseArray.contractSalesId,
						SAP_ROW_NO:responseArray.sapRowNo,
						VBAP_PSTYV_D_ITEM_TYPE: responseArray.vbapPstyv,
						VBAP_MATNR: responseArray.vbapMatnr,
						VBAP_ARKTX: responseArray.vbapArktx,
						VBAP_ZMENG: responseArray.vbapZmeng,
						VBAP_VRKME: responseArray.vbapVrkme,
						RV45A_ETDAT: responseArray.rv45aEtdat,
						VBAP_WERKS_D_FACTORY: responseArray.vbapWerks,
						VBAP_UEBTO: responseArray.vbapUebto,
						VBAP_UNTTO: responseArray.vbapUntto,
						VBAP_KWMENG: responseArray.vbapKwmeng,
						KONV_KBETR: responseArray.konvKbetr,
						VBKD_ZTERM_D_PAYMENT_TYPE: responseArray.vbkdZterm,
						VBAP_KPEIN: responseArray.vbapKpein,
						VBAP_KMEIN: responseArray.vbapKmein,
						ROW_TOTAL: responseArray.rowTotal,
						ROW_NET: responseArray.rowNet,
						ROW_TAXES: responseArray.rowTaxes,
						TOTAL_TAXES: responseArray.totalTaxes,
						ROW_TAXES_RALE: responseArray.rowTaxesRale,
						PAYER: responseArray.payer,
						PAYER_NAME: responseArray.payerName,
						BILL_TO_PARTY: responseArray.billToParty,
						BILL_TO_PARTY_NAME: responseArray.billToPartyName,
						OTHERINFO:'其他费用'
	                });
				productgrid.stopEditing();
				productds.insert(0, p);
				productgrid.startEditing(0, 0);
				var records = productgrid.getStore().getAt(0);
				records.set("VBAP_WERKS_D_FACTORY",getTitle('vbapWerks11',responseArray.vbapWerks));
				records.set("VBKD_ZTERM_D_PAYMENT_TYPE",getTitle('vbkdZterm11',responseArray.vbkdZterm));
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
			top.ExtModalWindowUtil.show('查询物料信息',
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
				var idList = '';
				if(records.length>1){
					top.ExtModalWindowUtil.alert('提示','只能选择一条记录！');
				}else{
				for (var i=0;i<records.length;i++){
					idList = idList + records[i].data.SALES_ROWS_ID + '|';
				}
				
				top.Ext.Msg.show({
					title:'提示',
  						msg: '是否确定删除记录',
  						buttons: {yes:'是', no:'否'},
  						fn: function(buttonId,text){
  							if(buttonId=='yes'){
  			            		var param1 = Form.serialize('baseForm');
  			            		var param2 = Form.serialize('contractForm');
  								var param = "?action=deleteSalesMateriel";
								param = param + "&idList=" + idList+'&'+param1+'&'+param2;

							new AjaxEngine('contractController.spr', 
					   			{method:"post", parameters: param, onComplete: callBackHandle});
  							}
  							
  							strOperType = '1';
  						},
  						icon: Ext.MessageBox.QUESTION
				});
			}}
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
        height:200,
        autoWidth:true,
        miniWidth:850
    });
    
    productgrid.render();
    
    productgrid.addListener('cellclick', productgridcellclick);
    productgrid.addListener('celldblclick', productgridcelldbclick);
    
    productds.load({params:{start:0, limit:10}});    
    
    function productgridcelldbclick(grid, rowIndex, columnIndex, e){
    	record = grid.getStore().getAt(rowIndex);
    	fieldName = grid.getColumnModel().getDataIndex(columnIndex);
    	var salesrowsid = record.get("SALES_ROWS_ID");

    	if (fieldName == 'PAYER'){
    		top.ExtModalWindowUtil.show('付款方查询',
			'masterQueryController.spr?action=toFindCustomer',
			'',
			callpayer,
			{width:755,height:410});
    	}
    	if (fieldName == 'BILL_TO_PARTY'){
    		top.ExtModalWindowUtil.show('开票方查询',
			'masterQueryController.spr?action=toFindCustomer',
			'',
			callbillToparty,
			{width:755,height:410});
    	}    	
    	
    } 
    
    function updateMaterielValue(salesRowsId,colName,colValue){
    	var requestUrl = 'contractController.spr?action=updateSalesMateriel';
		requestUrl = requestUrl + '&salesrowsid=' + salesRowsId;
		requestUrl = requestUrl + '&colname=' + colName;
		requestUrl = requestUrl + '&colvalue=' + colValue;
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				productds.commitChanges();
			},
			failure:function(response, options){
			}
		});    
    }
    function callpayer(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	
    	record.set('PAYER',returnvalue.KUNNR);
    	record.set('PAYER_NAME',returnvalue.NAME1);
    	var salesrowsid = record.get("SALES_ROWS_ID");
    	var requestUrl = 'contractController.spr?action=updateSalesMateriel';
		requestUrl = requestUrl + '&salesrowsid=' + salesrowsid;
		requestUrl = requestUrl + '&colname=PAYER';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.KUNNR;
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				productds.commitChanges();
			},
			failure:function(response, options){
			}
		});
		
		requestUrl = 'contractController.spr?action=updateSalesMateriel';
		requestUrl = requestUrl + '&salesrowsid=' + salesrowsid;
		requestUrl = requestUrl + '&colname=PAYER_NAME';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.NAME1;
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				productds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }
    
    function callbillToparty(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	
    	record.set('BILL_TO_PARTY',returnvalue.KUNNR);
    	record.set('BILL_TO_PARTY_NAME',returnvalue.NAME1);
    	var salesrowsid = record.get("SALES_ROWS_ID");
    	var requestUrl = 'contractController.spr?action=updateSalesMateriel';
		requestUrl = requestUrl + '&salesrowsid=' + salesrowsid;
		requestUrl = requestUrl + '&colname=BILL_TO_PARTY';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.KUNNR;

		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				productds.commitChanges();
			},
			failure:function(response, options){
			}
		});
		
		requestUrl = 'contractController.spr?action=updateSalesMateriel';
		requestUrl = requestUrl + '&salesrowsid=' + salesrowsid;
		requestUrl = requestUrl + '&colname=BILL_TO_PARTY_NAME';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.NAME1;
		
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				productds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }    
        
    function productgridcellclick(grid, rowIndex, columnIndex, e){
    	/*var record = grid.getStore().getAt(rowIndex);
    	var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
    	var data = record.get(fieldName);
    	if (fieldName == '15')
    		grid.getStore().setDefaultSort(fieldName,'1234');
		else
    		Ext.MessageBox.alert('show','当前选中的数据是'+data);*/
    }
        
    function renderotherOper(value, p, record){
    	return String.format('<a href="#" onclick="openOtherChargeWin()">{0}</a>','其他费用');
    }
    
    productgrid.on("afteredit", afterEdit, productgrid);
    
    function afterEdit(obj){
    	var row = obj.record;//获取被修改的行
        var colname = obj.field;//获取被修改的列
        var salesrowsid = row.get("SALES_ROWS_ID");
        var colvalue = row.get(colname);
        
        if (colname == 'VBAP_PSTYV_D_ITEM_TYPE'){
        	row.set(colname,getTitle('vbapPstyv11',colvalue));
        	colname = 'VBAP_PSTYV';
        }
        
        if (colname == 'VBAP_WERKS_D_FACTORY'){
        	row.set(colname,getTitle('vbapWerks11',colvalue));
        	colname = 'VBAP_WERKS';
        }

        if (colname == 'VBKD_ZTERM_D_PAYMENT_TYPE'){
        	row.set(colname,getTitle('vbkdZterm11',colvalue));
        	colname = 'VBKD_ZTERM';
        }
                
        if (colname == 'RV45A_ETDAT'){
        	colvalue = Ext.util.Format.date(colvalue, "Ymd");
            row.set(colname,colvalue);
        }    
        //税码或含税单价
        if (colname == 'ROW_TAXES_RALE' || colname == 'KONV_KBETR' ){
        	setMaterielMoney(row);
 //       	setContractSaleMoney();
        }   
        //数量或单价
        if (colname == 'VBAP_ZMENG' || colname == 'KONV_KBETR' ){
//        	setContractSaleMoney();
        }           
        var requestUrl = 'contractController.spr?action=updateSalesMateriel';
			requestUrl = requestUrl + '&salesrowsid=' + salesrowsid;
			requestUrl = requestUrl + '&colname=' + colname;
			requestUrl = requestUrl + '&colvalue=' + colvalue;
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				productds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }
	  //直接去尾
	 function formatnumber(value,num) {
		var a,b,c,i
		a = value.toString();
		b = a.indexOf('.');
		c = a.length;
		if (num==0){
		if (b!=-1)
			a = a.substring(0,b);
		}
		else{
			if (b==-1){
				a = a + ".";
				for (i=1;i<=num;i++)
				a = a + "0";
			}
				else{
				a = a.substring(0,b+num+1);
				for (i=c;i<=b+num;i++)
				a = a + "0";
			}
		}
		return a
	}    
    //设置金额
     function setMaterielMoney(row){
        var salesRowsId = row.get("SALES_ROWS_ID");
     	var taxesRale = row.get("ROW_TAXES_RALE");
     	var price = row.get("KONV_KBETR");
     	if (taxesRale == null || taxesRale == ''){
     		taxesRale = "0";
     	}
     	if (price == null || price == ''){
     		price = "0";
     	}
     	//净价
     	var rowNet = parseFloat(price) /(1+parseFloat(taxesRale)/100);
     	//税金
     	var rowTaxes = parseFloat(price) - parseFloat(rowNet);
     	row.set("ROW_TAXES",formatnumber(rowTaxes,2));
     	updateMaterielValue(salesRowsId,'ROW_TAXES',formatnumber(rowTaxes,2));
     	row.set("ROW_NET",formatnumber(rowNet,2)); 
     	updateMaterielValue(salesRowsId,'ROW_NET',formatnumber(rowNet,2));
     }  
     //设置销售合同金额
     function setContractSaleMoney(){
     	var materielCount = productds.getCount();
     	var totalMoney = 0;
     	var totalTaxes = 0;
     	var totalNet =0;
     	for (var i = 0; i < materielCount; i++) {
        	var record = productds.getAt(i);
            var quantity = record.get("VBAP_ZMENG");
	     	var rowTaxes = record.get("ROW_TAXES");
	     	var rowNet = record.get("ROW_NET");
	     	var price = record.get("KONV_KBETR");
	     	if (rowTaxes == null || rowTaxes == ''){
	     		rowTaxes = '0';
	     	}
	     	if (rowNet == null || rowNet == ''){
	     		rowNet = '0';
	     	}
	     	totalMoney = parseFloat(totalMoney) + parseFloat(quantity) * parseFloat(price);
	     	totalTaxes = parseFloat(totalTaxes) + parseFloat(quantity) * parseFloat(rowTaxes);
	        totalNet = parseFloat(totalNet) + parseFloat(quantity) * parseFloat(rowNet);
         }
         Ext.getDom('orderTotal').value=formatnumber(totalMoney,2);
         Ext.getDom('orderTaxes').value=formatnumber(totalTaxes,2);
         Ext.getDom('orderNet').value=formatnumber(totalNet,2);     	
     }    

    /***************************增加代理业务的服务物料**********************************/
    
    var surrogatePlant = Ext.data.Record.create([
   		{name:'SALES_ROWS_ID'},
   		{name:'CONTRACT_SALES_ID'},    		   		
   		{name:'VBAP_PSTYV_D_ITEM_TYPE'},
   		{name:'VBAP_MATNR'},
   		{name:'VBAP_ARKTX'},
   		{name:'VBAP_ZMENG'},
   		{name:'VBAP_VRKME'},
   		{name:'RV45A_ETDAT'},
   		{name:'VBAP_WERKS_D_FACTORY'},
   		{name:'VBAP_UEBTO'},
   		{name:'VBAP_UNTTO'},
   		{name:'VBAP_KWMENG'},
   		{name:'KONV_KBETR'},
   		{name:'VBKD_ZTERM_D_PAYMENT_TYPE'},
		{name:'VBKD_ZTERM_NAME'},   		
   		{name:'VBAP_KPEIN'},
   		{name:'VBAP_KMEIN'},
   		{name:'PAYER'},
   		{name:'PAYER_NAME'},
   		{name:'BILL_TO_PARTY'},
   		{name:'BILL_TO_PARTY_NAME'},   		
   		{name:'ROW_TOTAL'},
   		{name:'ROW_NET'},
   		{name:'ROW_TAXES'},
   		{name:'ROW_TAXES_RALE'},
   		{name:'OTHERINFO'}
	]);
	
	surrogateds = new Ext.data.Store({
   		proxy : new Ext.data.HttpProxy({url:'contractController.spr?action=quertAgentMateriel&contractsalesid='+document.contractForm.contractSalesId.value}),
   		reader: new Ext.data.JsonReader({
       	root: 'root',
       	totalProperty: 'totalProperty'
       	},surrogatePlant)
    });
    
    var saleColunm = '含税单价';
	if('${sales.tradeType}'=='5'){
			    saleColunm='出口销售单价';
	}
    if('${sales.tradeType}'=='6'){
			    saleColunm='进口采购单价';
	}
    var surrogatesm = new Ext.grid.CheckboxSelectionModel();
    
    var surrogatecm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		surrogatesm,
			{header: '销售合同行项ID',
           	width: 100,
           	sortable: true,
           	dataIndex: 'SALES_ROWS_ID',
           	hidden:true
			},
			{header: '销售合同ID',
           	width: 100,
           	sortable: true,
           	dataIndex: 'CONTRACT_SALES_ID',
           	hidden:true
			},
            {header: '*项目类别',
            width: 80,
            sortable: true,
            dataIndex: 'VBAP_PSTYV_D_ITEM_TYPE',
            editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'vbapPstyvAgent',
               lazyRender:true,
               listClass: 'x-combo-list-small'
             })            
           },
           {header: '物料号',
           width: 80,
           sortable: true,
           dataIndex: 'VBAP_MATNR'          
           },
           {header: '物料描述',
           width: 60,
           sortable: true,
           dataIndex: 'VBAP_ARKTX'
           },
           {header: '单位',
           width: 60,
           sortable: true,
           dataIndex: 'VBAP_VRKME'
           },           
           {header: '*订单数量',
           width: 60,
           sortable: true,
           dataIndex: 'VBAP_ZMENG',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 100000
           })           
           },
           {header: '*项目交货日期',
           width: 80,
           sortable: true,
           dataIndex: 'RV45A_ETDAT',
           editor: new fm.DateField({
                format: 'Ymd',
                disabledDaysText: '请输入日期'
            })           
           },
           {header: '*工厂',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_WERKS_D_FACTORY',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'vbapWerksAgent',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })
           },
           {header: '*条过量交货限度（百分比）',
           width: 150,
           sortable: true,
           dataIndex: 'VBAP_UEBTO',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 99
           })             
           },
           {header: '*交货不足限度',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_UNTTO',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 99
           })             
           },
           {header: '行项目数量',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_KWMENG',
           hidden:true,
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 100000
           })             
           },
           {header: '*'+saleColunm,
           width: 100,
           sortable: true,
           dataIndex: 'KONV_KBETR',
           editor: new fm.NumberField({
               allowBlank: false,
               allowNegative: false,
               maxValue: 1000000000
           })           
           },
           {header: '*付款条件',
           width: 60,
           sortable: true,
           dataIndex: 'VBKD_ZTERM_D_PAYMENT_TYPE',
           editor: new Ext.form.ComboBox({
               typeAhead: true,
               maxHeight: 100,
               triggerAction: 'all',
               transform:'vbkdZtermAgent',
               lazyRender:true,
               listClass: 'x-combo-list-small'
            })           
           },
           {header: '付款条件名称',
           width: 100,
           sortable: true,
           dataIndex: 'VBKD_ZTERM_NAME',
           	hidden:true           
           },           
           {header: '*条件定价单位',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_KPEIN',
           editor: new fm.TextField({
               allowBlank: true
           })           
           },
           {header: '*在凭证中的条件单位',
           width: 130,
           sortable: true,
           dataIndex: 'VBAP_KMEIN',
           editor: new fm.TextField({
               allowBlank: true
           })
           },
           {header: '付款方',
           width: 60,
           sortable: true,
           dataIndex: 'PAYER'
           }, 
           {header: '付款方名称',
           width: 100,
           sortable: true,
           dataIndex: 'PAYER_NAME'
           },   
           {header: '收票方',
           width: 60,
           sortable: true,
           dataIndex: 'BILL_TO_PARTY'
           }, 
           {header: '收票方名称',
           width: 100,
           sortable: true,
           dataIndex: 'BILL_TO_PARTY_NAME'
           },                                
           {header: '行项目总金额',
           width: 100,
           sortable: true,
           dataIndex: 'ROW_TOTAL',
           hidden:true
           },
           {header: '净价',
           width: 100,
           sortable: true,
           dataIndex: 'ROW_NET'
           },
           {header: '行项目销项税金',
           width: 100,
           sortable: true,
           dataIndex: 'ROW_TAXES',
           hidden:true
           },
           {header: '销项税（百分率）',
           width: 100,
           sortable: true,
           dataIndex: 'ROW_TAXES_RALE',
           hidden:true
           },
           {header: '其他费用',
           width: 100,
           sortable: true,
           dataIndex: 'OTHERINFO',
           renderer:renderAgentotherOper
           }
    ]);
    surrogatecm.defaultSortable = true;
    
    function surrogatecallbackFunction(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	alert(returnvalue);
    }
 
    function AgentMaterielcallbackFunction(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	
    	var requestUrl = 'contractController.spr?action=addAgentMaterielInfo';
		requestUrl = requestUrl + '&contractsalesid=' + document.contractForm.contractSalesId.value;
		requestUrl = requestUrl + '&vbapMarnr=' + returnvalue.MATNR;
		requestUrl = requestUrl + '&vbapArktx=' + returnvalue.MAKTX;
		requestUrl = requestUrl + '&vbapVrkme=' + returnvalue.MEINS;
		requestUrl = requestUrl + '&vbapWerks=' + returnvalue.WERKS;
		requestUrl = requestUrl + '&payer=' + Ext.getDom('payer').value;
		requestUrl = requestUrl + '&payerName=' + Ext.getDom('payerName').value;
		requestUrl = requestUrl + '&billToParty=' + Ext.getDom('bllToParty').value;
		requestUrl = requestUrl + '&billToPartyName=' + Ext.getDom('bllToPartyName').value;		
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				var responseArray = Ext.util.JSON.decode(response.responseText);

				var p = new surrogatePlant({
	                    SALES_ROWS_ID: responseArray.salesRowsId,
						CONTRACT_SALES_ID: responseArray.contractSalesId,
						VBAP_PSTYV_D_ITEM_TYPE: responseArray.vbapPstyv,
						VBAP_MATNR: responseArray.vbapMatnr,
						VBAP_ARKTX: responseArray.vbapArktx,
						VBAP_ZMENG: responseArray.vbapZmeng,
						VBAP_VRKME: responseArray.vbapVrkme,
						RV45A_ETDAT: responseArray.rv45aEtdat,
						VBAP_WERKS_D_FACTORY: responseArray.vbapWerks,
						VBAP_UEBTO: responseArray.vbapUebto,
						VBAP_UNTTO: responseArray.vbapUntto,
						VBAP_KWMENG: responseArray.vbapKwmeng,
						KONV_KBETR: responseArray.konvKbetr,
						VBKD_ZTERM_D_PAYMENT_TYPE: responseArray.vbkdZterm,
						VBAP_KPEIN: responseArray.vbapKpein,
						VBAP_KMEIN: responseArray.vbapKmein,
						ROW_TOTAL: responseArray.rowTotal,
						ROW_NET: responseArray.rowNet,
						ROW_TAXES: responseArray.rowTaxes,
						TOTAL_TAXES: responseArray.totalTaxes,
						ROW_TAXES_RALE: responseArray.rowTaxesRale,
						PAYER: responseArray.payer,
						PAYER_NAME: responseArray.payerName,
						BILL_TO_PARTY: responseArray.billToParty,
						BILL_TO_PARTYNAME: responseArray.billToPartyName,
						OTHERINFO:'其他费用'
	                });
				surrogategrid.stopEditing();
				surrogateds.insert(0, p);
				surrogategrid.startEditing(0, 0);
				var records = surrogategrid.getStore().getAt(0);
				records.set("VBAP_WERKS_D_FACTORY",getTitle('vbapWerks11',responseArray.vbapWerks));
				records.set("VBKD_ZTERM_D_PAYMENT_TYPE",getTitle('vbkdZterm11',responseArray.vbkdZterm));				
			},
			failure:function(response, options){
				//Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
			}
		});
    }
        
    var addSurrogate = new Ext.Toolbar.Button({
   		text:'增加',
   		iconCls:'add',
		handler:function(){
			top.ExtModalWindowUtil.show('查询物料信息',
				'masterQueryController.spr?action=toFindMaterial',
				'',
				AgentMaterielcallbackFunction,
			{width:755,height:478});
		}
   	});
   	
   	var deleteSurrogate = new Ext.Toolbar.Button({
   		text:'删除',
   		iconCls:'delete',
		handler:function(){
			if (surrogategrid.selModel.hasSelection()){
				var records = surrogategrid.selModel.getSelections();
				var idList = '';
				for (var i=0;i<records.length;i++){
					idList = idList + records[i].data.SALES_ROWS_ID + '|';					
				}
				top.Ext.Msg.show({
					title:'提示',
  						msg: '是否确定删除记录',
  						buttons: {yes:'是', no:'否'},
  						fn: function(buttonId,text){
  							if(buttonId=='yes'){
  								var param = "?action=deleteAgentMateriel";
							param = param + "&idList=" + idList;

							new AjaxEngine('contractController.spr', 
					   			{method:"post", parameters: param, onComplete: callBackHandle});
  							}
  							
  							strOperType = '2';
  						},
  						icon: Ext.MessageBox.QUESTION
				});				
			}
			else{
				top.ExtModalWindowUtil.alert('提示','请选择要删除的物料组信息！');
			}
		}
   	});
   	
   	var surrogatetbar = new Ext.Toolbar({
		items:[addSurrogate,'-',deleteSurrogate]
	});
    
    var surrogatebbar = new Ext.PagingToolbar({
        pageSize: 10,
        store:surrogateds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
     surrogategrid = new Ext.grid.EditorGridPanel({
    	id:'surrogateGrid',
    	title:'代理物料信息',
        ds: surrogateds,
        cm: surrogatecm,
        sm: surrogatesm,
        tbar: surrogatetbar,
        bbar: surrogatebbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        el: 'div_surrogateInfo',
        layout:"fit",
        clicksToEdit:1,
        height:200,
        width:850
    });
    
    surrogategrid.render();
    surrogategrid.addListener('celldblclick', surrogategridcelldbclick);        
    surrogateds.load({params:{start:0, limit:10}}); 

    function surrogategridcelldbclick(grid, rowIndex, columnIndex, e){
    	record = grid.getStore().getAt(rowIndex);
    	fieldName = grid.getColumnModel().getDataIndex(columnIndex);
    	var salesrowsid = record.get("SALES_ROWS_ID");

    	if (fieldName == 'PAYER'){
    		top.ExtModalWindowUtil.show('付款方查询',
			'masterQueryController.spr?action=toFindCustomer',
			'',
			callAgentPayer,
			{width:755,height:410});
    	}
    	if (fieldName == 'BILL_TO_PARTY'){
    		top.ExtModalWindowUtil.show('开票方查询',
			'masterQueryController.spr?action=toFindCustomer',
			'',
			callAgentBillToparty,
			{width:755,height:410});
    	}    	    	
    } 
    
    function callAgentPayer(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	
    	record.set('PAYER',returnvalue.KUNNR);
    	record.set('PAYER_NAME',returnvalue.NAME1);
    	var salesrowsid = record.get("SALES_ROWS_ID");
    	var requestUrl = 'contractController.spr?action=updateAgentMateriel';
		requestUrl = requestUrl + '&salesrowsid=' + salesrowsid;
		requestUrl = requestUrl + '&colname=PAYER';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.KUNNR;
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				surrogateds.commitChanges();
			},
			failure:function(response, options){
			}
		});
		
		requestUrl = 'contractController.spr?action=updateAgentMateriel';
		requestUrl = requestUrl + '&salesrowsid=' + salesrowsid;
		requestUrl = requestUrl + '&colname=PAYER_NAME';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.NAME1;
		
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				surrogateds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    }
    
    function callAgentBillToparty(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	
    	record.set('BILL_TO_PARTY',returnvalue.KUNNR);
    	record.set('BILL_TO_PARTY_NAME',returnvalue.NAME1);
    	var salesrowsid = record.get("SALES_ROWS_ID");
    	var requestUrl = 'contractController.spr?action=updateAgentMateriel';
		requestUrl = requestUrl + '&salesrowsid=' + salesrowsid;
		requestUrl = requestUrl + '&colname=BILL_TO_PARTY';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.KUNNR;
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				otherds.commitChanges();
			},
			failure:function(response, options){
			}
		});
		
		requestUrl = 'contractController.spr?action=updateAgentMateriel';
		requestUrl = requestUrl + '&salesrowsid=' + salesrowsid;
		requestUrl = requestUrl + '&colname=BILL_TO_PARTY_NAME';
		requestUrl = requestUrl + '&colvalue=' + returnvalue.NAME1;
		
		Ext.Ajax.request({
			url: encodeURI(requestUrl),
			success: function(response, options){
				surrogateds.commitChanges();
			},
			failure:function(response, options){
			}
		});
    } 
    
    function renderAgentotherOper(value, p, record){
    	return String.format('<a href="#" onclick="openAgentOtherChargeWin()">{0}</a>','其他费用');
    }    
    surrogategrid.on("afteredit", afterAgentEdit, surrogategrid);
    
    function afterAgentEdit(obj){
    	var row = obj.record;//获取被修改的行
        var colname = obj.field;//获取被修改的列
        var salesrowsid = row.get("SALES_ROWS_ID");
        var colvalue = row.get(colname);
        
        if (colname == 'VBAP_PSTYV_D_ITEM_TYPE'){
        	row.set(colname,getTitle('vbapPstyv11',colvalue));
        	colname = 'VBAP_PSTYV';
        }
        
        if (colname == 'VBAP_WERKS_D_FACTORY'){
        	row.set(colname,getTitle('vbapWerks11',colvalue));
        	colname = 'VBAP_WERKS';
        }
        
        if (colname == 'VBKD_ZTERM_D_PAYMENT_TYPE'){
        	row.set(colname,getTitle('vbkdZterm11',colvalue));
        	colname = 'VBKD_ZTERM';
        }
                
        if (colname == 'RV45A_ETDAT'){
        	colvalue = Ext.util.Format.date(colvalue, "Ymd");
            row.set(colname,colvalue);
        }         
        var requestUrl = 'contractController.spr?action=updateAgentMateriel';
			requestUrl = requestUrl + '&salesrowsid=' + salesrowsid;
			requestUrl = requestUrl + '&colname=' + colname;
			requestUrl = requestUrl + '&colvalue=' + colvalue;
		Ext.Ajax.request({
			url: requestUrl,
			success: function(response, options){
				surrogateds.commitChanges();
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
			height:50,
			contentEl: 'div_basrForm'
		},{
			region:"center",
			layout:'fit',
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
					html:'<iframe src="saleChangeController.spr?action=toChangeTab&from=${from}&contractId=${sales.contractSalesId}" width="100%" height="100%" id="alter" ></iframe>'
		        },{
	            	title:'订单信息',
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
					var param = param1 + "&" + param2 + "&action=updateSalesContract";
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
</script>

<fiscxd:dictionary divId="div_vbakSpart" fieldName="vbakSpart" dictionaryName="BM_PRODUCT_GROUP" width="150" selectedValue="${sales.vbakSpart}" disable="true" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbakVtweg" fieldName="vbakVtweg" dictionaryName="BM_DISTRIBUTION_CHANNEL" width="150" selectedValue="${sales.vbakVtweg}" disable="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbakVkbur" fieldName="vbakVkbur" dictionaryName="BM_SALES_AREAS" width="150" selectedValue="${sales.vbakVkbur}" disable="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbkdZterm" fieldName="vbkdZterm" dictionaryName="BM_PAYMENT_TYPE" width="150" selectedValue="${sales.vbkdZterm}"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbakWaerk" fieldName="vbakWaerk" dictionaryName="BM_CURRENCY" needDisplayCode="true" width="150" selectedValue="${sales.vbakWaerk}"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbkdBzirk" fieldName="vbkdBzirk" dictionaryName="BM_SALES_DISTRICT" width="150" selectedValue="${sales.vbkdBzirk}"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbkdInco1" fieldName="vbkdInco1" dictionaryName="BM_INCOTERM_TYPE" needDisplayCode="true" width="150" selectedValue="${sales.vbkdInco1}"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbkdZlsch" fieldName="vbkdZlsch" dictionaryName="BM_PAY_TYPE" width="150" selectedValue="${sales.vbkdZlsch}"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_ymatGroup" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${sales.ymatGroup}" disable="false" ></fiscxd:dictionary>


