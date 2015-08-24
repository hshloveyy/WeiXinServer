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

<div id="div_basrForm">
<form action="" id="baseForm" name="baseForm">
<table width="100%">
<tr>
	<td align="right" width="13%" >	
	 销售组织：
	</td>
	<td width="20%">
	<input type="text" readonly="readonly" id="vbakVkorg" name="vbakVkorg" value="${sales.vbakVkorg}"></input>
	</td>
	<td align="right" width="13%">
		分销渠道：
	</td>
	<td width="20%">
	<div id="div_vbakVtweg"></div>
	</td>
	<td align="right"width="13%">
		产品组：
	</td>
	<td width="20%">
	<div id="div_vbakSpart"></div>
	</td>
</tr>
<tr>
	<td align="right">	
		销售部门：
	</td>
	<td>
	<div id="div_vbakVkbur"></div>
	</td>
	<td align="right">
		销售组：
	</td>
	<td>
	<div id="div_vbakVkgrp"></div>
	</td>
	<td align="right">
		凭证类型：
	</td>
	<td>
		<div id="div_vbakAuart"></div>     
	</td>
</tr>
<tr>
	<td align="right">	
		物料组:
	</td>
	<td  >	
		<div id="div_ymatGroup2"></div>
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
	<td align="right" width="13%">项目名称：</td>
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
	<input type="text" readonly="readonly" id="projectName" name="projectName" value="${sales.projectName}"></input>
	</td>
	<td align="right"width="13%" >信达项目号：</td>
	<td width="20%"><nobr>
	<input type="text" readonly="readonly"  id="vbakBname" name="vbakBname" value="${sales.vbakBname}" size="15"></input> <a href="#" onclick="viewProjectForm()">查看</a>
	</nobr>
	</td>
	<script>
	function viewProjectForm(){
			top.ExtModalWindowUtil.show('查看立项申请','projectController.spr?action=modify&from=view&projectId=${sales.projectId}','','',{width:800,height:500});
    }
    </script>
	<td align="right">原合同编码：</td>
	<td>
	<input  type="text" readonly="readonly" id="oldContractNo" name="oldContractNo" value="${sales.oldContractNo}"></input>
	</td>

</tr>
<tr>
	<td align="right">合同编码：</td>
	<td>
	<input type="text" readonly="readonly" id="contractNo" name="contractNo" value="${sales.contractNo}"></input>
	</td>
	<td align="right">
		合同名称：
	</td>
	<td>
	<input type="text" id="contractName" name="contractName" value="${sales.contractName}" readonly="readonly" ></input>
	</td>
	<td align="right">
		单据日期：
	</td>
	<td>
	<input type="text" id="vbakAudat" name="vbakAudat" value="${sales.vbakAudat}" readonly="readonly"></input>
	</td>	
</tr>
<tr>
	<td align="right">
		手册号：
	</td>
	<td>
	<input type="text" id="vbkdBstkdE" name="vbkdBstkdE" value="${sales.vbkdBstkdE}" readonly="readonly"></input>
	</td>
	<td align="right">
		外部纸质合同号：
	</td>
	<td>
	<input type="text" id="vbkdIhrez" name="vbkdIhrez" value="${sales.vbkdIhrez}" readonly="readonly" ></input>
	</td>
	<td align="right">SAP订单号：
	</td>
	<td>
	<input type="text" readonly="readonly" id="sapOrderNo" name="sapOrderNo" value="${sales.sapOrderNo}"></input>
	</td>
</tr>
<tr>

	<td align="right" nowrap="nowrap">
		<input type="text" id="txtPayCon" name="txtPayCon" readonly="true"  style="border-style:none;border-Width:0PX;text-align:right"  value="付款条件"></input>
	</td>
	<td>
	<div id="div_vbkdZterm"></div>
	</td>
	<td align="right" nowrap="nowrap">
		<input type="text" id="txtPayStyle" name="txtPayStyle" readonly="true"  style="border-style:none;border-Width:0PX;text-align:right"  value="付款方式"></input>
	</td>
	<td>
		<div id="div_vbkdZlsch"></div>
	</td>
	<td align="right">销售地区:</td>
	<td>
	<div id="div_vbkdBzirk"></div>
	</td>	
</tr>
<tr>
	<td align="right">货币码：</td>
	<td>
	<div id="div_vbakWaerk"></div>
	</td>
	<td align="right">会计汇率：</td>
	<td>
	<input type="text" id="vbkdKurrf" name="vbkdKurrf" value="${sales.vbkdKurrf}"></input>
	</td>
	<td align="right">
		<input type="text" id="txtShipmentDate" name="txtShipmentDate" readonly="true"  style="border-style:none;border-Width:0PX;text-align:right"  value="装运期" size="10"></input><font color="red">▲</font>
	</td>
	<td>
	<input type="text" id="shipmentDate" name="shipmentDate" value="${sales.shipmentDate}" readonly="readonly" ></input>
	</td>	 
</tr>
<tr>
	<td align="right">国际贸易条件1：</td>
	<td>
	<div id="div_vbkdInco1"></div>
	</td>
	<td align="right">国际贸易条件2：</td>
	<td>
		<input type="text" id="vbkdInco2" name="vbkdInco2" value="${sales.vbkdInco2}" readonly="readonly" ></input>
	</td>
	<td align="right">
		<input type="text" id="txtShipmentPort" name="txtShipmentPort" readonly="true"  style="border-style:none;border-Width:0PX;text-align:right"  value="装运港" size="10"></input><font color="red">▲</font>
	</td>
	<td>
		<input type="text" id="shipmentPort" name="shipmentPort" value="${sales.shipmentPort}" readonly="readonly" ></input>
	</td>
</tr>
<tr id="trView1">
	<td align="right" id="tdView1">合同规定收款日</td>
	<td id="tdView2">
		<input type="text" id="collectionDate" name="collectionDate" value="${sales.collectionDate}" readonly="readonly"></input>
	</td>
	<td align="right" id="tdView3">最迟开证日</td>
	<td id="tdView4">
		<input type="text" id="laterDate" name="laterDate" value="${sales.laterDate}" readonly="readonly"></input>
	</td>
	<td align="right" id="tdView5">保证金比例%<font color="red">▲</font></td>
	<td id="tdView6">
		<input type="text" id="marginRatio" name="marginRatio" value="${sales.marginRatio}"></input>
	</td>	
	<td align="right" id="tdView7" style="display: none;">是否约定跌价保证金<font color="red">▲</font></td>
	<td id="tdView8" style="display: none;">
		<select name="isPromise" id="isPromise">
		  <option value="0">否</option>
		  <option value="1">是</option>
		</select>
		<script type="text/javascript">$("isPromise").value='${sales.isPromise}';</script>
	</td>
</tr>
<tr>
	<td align="right">售达方：</td>
	<td>
	<input type="text" id="kuagvKunnr" name="kuagvKunnr" value="${sales.kuagvKunnr}" size="15" readonly="readonly"></input>
	</td>
	<td align="right">售达方名称：</td>
	<td>
	<input type="text" id="kuagvKunnrName" name="kuagvKunnrName" value="${sales.kuagvKunnrName}" readonly="readonly"></input>
	</td>
	<td align="right">目的港：</td>
	<td>
	<input type="text" id="destinationPort" name="destinationPort" value="${sales.destinationPort}" readonly="readonly" ></input>
	</td>		
</tr>
<tr>
	<td align="right">送达方：</td>
	<td>
	<input type="text" id="kuwevKunnr" name="kuwevKunnr" value="${sales.kuwevKunnr}" size="15" readonly="readonly"></input>
	</td>
	<td align="right">送达方名称：</td>
	<td>
	<input type="text" id="kuwevKunnrName" name="kuwevKunnrName" value="${sales.kuwevKunnrName}" readonly="readonly"></input>
	</td>
	<td align="right"><input type="text" id="txtOrderNet" name="txtOrderNet" readonly="true"  style="border-style:none;border-Width:0PX;text-align:right"  value="净金额" size="10"></input>：</td>
	<td>
	<input type="hidden" id="orderNet" name="orderNet" value="${sales.orderNet}" readonly="readonly" ></input>
	<input type="text" id="orderNet1" name="orderNet1" value="${sales.orderNet}" readonly="readonly" ></input>
	<script>$("orderNet1").value=Utils.commafy(${sales.orderNet})</script>
	</td>
</tr>
<tr>
	<td align="right">付费方：</td>
	<td>
	<input type="text" id="payer" name="payer" value="${sales.payer}" size="15" readonly="readonly"></input>
	</td>
	<td align="right">付费方名称：</td>
	<td >
	<input type="text" id="payerName" name="payerName" value="${sales.payerName}" readonly="readonly"></input>
	</td>
	<td align="right"><input type="text" id="txtOrderTaxes" name="txtOrderTaxes" readonly="true"  style="border-style:none;border-Width:0PX;text-align:right"  value="销项税金" size="10"></input>：</td>
	<td>
	<input type="hidden" id="orderTaxes" name="orderTaxes" value="${sales.orderTaxes}" readonly="readonly"></input>
	<input type="text" id="orderTaxes1" name="orderTaxes1" value="${sales.orderTaxes}" readonly="readonly"></input>
	<script>$("orderTaxes1").value=Utils.commafy(${sales.orderTaxes})</script>
	</td>	
</tr>
<tr>
	<td align="right">收票方：</td>
	<td>
	<input type="text" id="bllToParty" name="bllToParty" value="${sales.bllToParty}" size="15" readonly="readonly"></input>
	</td>
	<td align="right">收票方名称：</td>
	<td>
	<input type="text" id="bllToPartyName" name="bllToPartyName" value="${sales.bllToPartyName}" readonly="readonly"></input>
	</td>
	<td align="right">总金额：</td>
	<td>                                         
	<input type="hidden" id="orderTotal" name="orderTotal" value="${sales.orderTotal}" readonly="readonly" ></input>
	<input type="text" id="orderTotal1" name="orderTotal1" value="${sales.orderTotal}" readonly="readonly" ></input>
	<script>$("orderTotal1").value=Utils.commafy(${sales.orderTotal})</script>
	</td>	
</tr>
<tr>
	<td align="right">备注: </td>
	<td colspan="3">
		<textarea name="mask" style="width:95%;overflow-y:visible;word-break:break-all;" readonly="readonly">${sales.mask}</textarea>
    </td>
    <td colspan="2"><div id="balanceMSG" align="left">${balaceMsg}</div></td>
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
<div id="div_accessory" class="x-hide-display">
<select name="vbapPstyv" id="vbapPstyv" style="display: none;">
	<c:forEach items="${ItemType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbapPstyvAgent" id="vbapPstyvAgent" style="display: none;">
	<c:forEach items="${ItemType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbapPstyv11" id="vbapPstyv11" style="display: none;">
	<c:forEach items="${ItemType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>

<select name="vbapWerks" id="vbapWerks" style="display: none;">
	<c:forEach items="${Factory}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbapWerksAgent" id="vbapWerksAgent" style="display: none;">
	<c:forEach items="${Factory}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbapWerks11" id="vbapWerks11" style="display: none;">
	<c:forEach items="${Factory}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbkdZterm" id="vbkdZterm" style="display: none;">
	<c:forEach items="${PayMentType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbkdZtermAgent" id="vbkdZtermAgent" style="display: none;">
	<c:forEach items="${PayMentType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
<select name="vbkdZterm11" id="vbkdZterm11" style="display: none;">
	<c:forEach items="${PayMentType}" var="row">
		<option value="${row.code}">${row.title}</option>
	</c:forEach>
</select>
</div>
<div id="detail" class="x-hide-display">
<form id="detailForm">
<input type="hidden" name="tradeType" value="${sales.tradeType}"/>
<c:if test="${sales.tradeType=='7'||sales.tradeType=='10'||sales.tradeType=='8'}">
<c:if test="${sales.deptId=='65A90F40-CF91-4F4F-B5B0-43DC62C273D9'}">
<%@ include file="../../project/costEvaluation/hKhomeTrade.jsp"%>
</c:if>
<c:if test="${sales.deptId!='65A90F40-CF91-4F4F-B5B0-43DC62C273D9'}">
<%@ include file="../../project/costEvaluation/homeTrade.jsp"%>
</c:if>
</c:if>
<c:if test="${sales.tradeType=='1'||sales.tradeType=='3'||sales.tradeType=='9'||sales.tradeType=='11'||sales.tradeType=='12'}">
<c:if test="${fn:indexOf(sales.vbakVkbur,'26')!=0}">
<%@ include file="../../project/costEvaluation/import.jsp"%>
</c:if>
<c:if test="${fn:indexOf(sales.vbakVkbur,'26')==0}">
<%@ include file="../../project/costEvaluation/hKimport.jsp"%>
</c:if>
</c:if>
<c:if test="${sales.tradeType=='2'||sales.tradeType=='4'}">
<%@ include file="../../project/costEvaluation/export.jsp"%>
</c:if>
<c:if test="${sales.tradeType=='5'||sales.tradeType=='6'}">
<%@ include file="../../project/costEvaluation/proxy.jsp"%>
</c:if>

</form>
</div>
<div id="div_contract"></div>
<div id="div_accessory" class="x-hide-display"></div>
<div id="div_examHist" class="x-hide-display"></div>
</body>
</html>
<fiscxd:fileUpload divId="div_accessory" increasable="${fileEdit}" erasable="${fileEdit}" 
 resourceId="2222" resourceName="33333" recordIdHiddenInputName="444" recordId="${sales.contractSalesId}"></fiscxd:fileUpload>
<fiscxd:workflow-taskHistory divId="div_examHist" width="800" businessRecordId="${sales.contractSalesId}"></fiscxd:workflow-taskHistory>
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
var contractds;
function openOtherChargeWin(){
	var records = productgrid.selModel.getSelections();
   	top.ExtModalWindowUtil.show('其他费用',
	'contractController.spr?action=archSaleCaseView&salesrowsid='+records[0].data.SALES_ROWS_ID + "&saprowno="+records[0].data.SAP_ROW_NO,
	'',
	'',
	{width:500,height:300});
}
function openAgentOtherChargeWin(){
	var records = surrogategrid.selModel.getSelections();
   	top.ExtModalWindowUtil.show('其他费用',
	'contractController.spr?action=archAgentCaseView&salesrowsid='+records[0].data.SALES_ROWS_ID,
	'',
	'',
	{width:500,height:300});
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
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	if (strOperType == '1'){
		productds.reload();	
	} else if (strOperType == '2'){
		surrogateds.reload();
	}
	if (strOperType == '3'){
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	}	
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

   	var collectionDate = new Ext.form.DateField({
   		format:'Ymd',
		name:"collectionDate",
		id:"collectionDate",
		width: 155,
		readOnly:false,
		applyTo:'collectionDate'
   	});

   	var laterDate = new Ext.form.DateField({
   		format:'Ymd',
		name:"laterDate",
		id:"laterDate",
		width: 155,
		readOnly:false,
		applyTo:'laterDate'
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
    var taxColunm = '含税';
    if('${sales.tradeType}'=='5'||'${sales.tradeType}'=='6'){
			    taxColunm='开票';
	}
    var productsm = new Ext.grid.CheckboxSelectionModel();
    
    var productcm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),

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
			{header: '行项目ID',
           	width: 50,
           	sortable: true,
           	dataIndex: 'SAP_ROW_NO'
			},			
            {header: '项目类别',
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
           {header: '订单数量',
           width: 60,
           sortable: true,
           dataIndex: 'VBAP_ZMENG'           
           },

           {header: '项目交货日期',
           width: 80,
           sortable: true,
           dataIndex: 'RV45A_ETDAT'                  
           },
           {header: '工厂',
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
           {header: '条过量交货限度（百分比）',
           width: 150,
           sortable: true,
           dataIndex: 'VBAP_UEBTO'           
           },
           {header: '交货不足限度',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_UNTTO'            
           },
           {header: '行项目数量',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_KWMENG'           
           },
           {header: '<font color="red">▲</font>'+taxColunm+'单价',
           width: 100,
           sortable: true,
           dataIndex: 'KONV_KBETR'          
           },
           {header: '付款条件',
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
           {header: '条件定价单位',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_KPEIN'          
           },
           {header: '在凭证中的条件单位',
           width: 130,
           sortable: true,
           dataIndex: 'VBAP_KMEIN'
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
           {header: '行项目净值',
           width: 100,
           sortable: true,
           dataIndex: 'ROW_NET',
           hidden:true           
           },
           {header: '行项目销项税金',
           width: 100,
           sortable: true,
           dataIndex: 'ROW_TAXES',
           hidden:true
           },
           {header: '税码(%)',
           width: 60,
           sortable: true,
           dataIndex: 'ROW_TAXES_RALE'
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
				for (var i=0;i<records.length;i++){
					idList = idList + records[i].data.SALES_ROWS_ID + '|';
				}
				
				top.Ext.Msg.show({
					title:'提示',
  						msg: '是否确定删除记录',
  						buttons: {yes:'是', no:'否'},
  						fn: function(buttonId,text){
  							if(buttonId=='yes'){
  								var param = "?action=deleteSalesMateriel";
							param = param + "&idList=" + idList;

							new AjaxEngine('contractController.spr', 
					   			{method:"post", parameters: param, onComplete: callBackHandle});
  							}
  							
  							strOperType = '1';
  						},
  						icon: Ext.MessageBox.QUESTION
				});
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
        bbar: productbbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        el: 'div_product',
        layout:"fit",
        height:200,
        width:850
    });
    
    productgrid.render();
    

    
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
   		{name:'AGENT_CURRENCY'},
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
    
    var surrogatesm = new Ext.grid.CheckboxSelectionModel();
    var saleColunm = '含税单价';
	if('${sales.tradeType}'=='5'){
			    saleColunm='出口销售单价';
	}
    if('${sales.tradeType}'=='6'){
			    saleColunm='进口采购单价';
	}
    
    var surrogatecm = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
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
            {header: '项目类别',
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
           {header: '订单数量',
           width: 60,
           sortable: true,
           dataIndex: 'VBAP_ZMENG'          
           },
           {header: '项目交货日期',
           width: 80,
           sortable: true,
           dataIndex: 'RV45A_ETDAT'          
           },
           {header: '工厂',
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
           {header: '条过量交货限度（百分比）',
           width: 150,
           sortable: true,
           dataIndex: 'VBAP_UEBTO'            
           },
           {header: '交货不足限度',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_UNTTO'            
           },
           {header: '行项目数量',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_KWMENG'           
           },
           {header: saleColunm,
           width: 100,
           sortable: true,
           dataIndex: 'KONV_KBETR'          
           }
           ,
           {header: '币别',
           width: 100,
           sortable: true,
           dataIndex: 'AGENT_CURRENCY'          
           },
           {header: '付款条件',
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
           {header: '条件定价单位',
           width: 100,
           sortable: true,
           dataIndex: 'VBAP_KPEIN'         
           },
           {header: '在凭证中的条件单位',
           width: 130,
           sortable: true,
           dataIndex: 'VBAP_KMEIN'
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
           {header: '行项目净值',
           width: 100,
           sortable: true,
           dataIndex: 'ROW_NET',
           hidden:true
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
    }
 
    function AgentMaterielcallbackFunction(){
    	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
    	
    	var requestUrl = 'contractController.spr?action=addAgentMaterielInfo';
		requestUrl = requestUrl + '&contractsalesid=' + document.contractForm.contractSalesId.value;
		requestUrl = requestUrl + '&vbapMarnr=' + returnvalue.MATNR;
		requestUrl = requestUrl + '&vbapArktx=' + returnvalue.MAKTX;
		requestUrl = requestUrl + '&vbapVrkme=' + returnvalue.MEINS;
		requestUrl = requestUrl + '&vbapWerks=' + returnvalue.WERKS;
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
				Ext.MessageBox.alert('提示', '与服务器交互失败，请查看具体提示原因！');
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
        pageSize: 50,
        store:surrogateds,
        displayInfo: true,
        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
        emptyMsg: "没有记录"
    });
    
     surrogategrid = new Ext.grid.EditorGridPanel({
    	id:'surrogateGrid',
    	title:'代理物料信息',
        ds: surrogateds,
        sm: surrogatesm,
        cm: surrogatecm,
        bbar: surrogatebbar,
        border:false,
        loadMask:true,
        autoScroll:true,
        el: 'div_surrogateInfo',
        layout:"fit",
        height:200,
        width:850
    });
    
    surrogategrid.render();
    //surrogategrid.addListener('celldblclick', surrogategridcelldbclick);        
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
    //surrogategrid.on("afteredit", afterAgentEdit, surrogategrid);
    
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
			height:80,
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
					autoScroll:'true',
	            	layout:'fit'
	            },{
	            	title:'审批信息',
	            	contentEl: 'div_examHist',
	            	id:'examHist',
					name:'examHist',
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
					title:'合同变更信息',
					id:'alterId',
					layout:'fit',
					html:'<iframe src="saleChangeController.spr?action=toChangeTab&from=changeR&contractId=${sales.contractSalesId}" width="100%" height="100%" id="alter" ></iframe>'
	            },{contentEl:'detail',id:'detailEl',title: '测算表',disabled:false,autoScroll:true}]
			}],
			buttons:[{
            	text:'写入SAP',
             	hidden: true,
            	handler:function(){
            		var param1 = Form.serialize('baseForm');
            		var param2 = Form.serialize('contractForm');
					var param = param1 + "&" + param2 + "&action=submitToSapSalesInfo";
					new AjaxEngine('contractController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
            	}
            },{
            	text:'更改信息',
             	hidden: ${!mgModify},
            	handler:function(){
            		var param1 = Form.serialize('baseForm');
            		var param2 = Form.serialize('contractForm');
					var param = param1 + "&" + param2 + "&action=mgModifySalesInfo";
					new AjaxEngine('contractController.spr', 
						   {method:"post", parameters: param, onComplete: callBackHandle});
            	}
            },{
            	text:'更改汇率',
             	hidden: ${!rateEdit},
            	handler:function(){
            	    top.Ext.Msg.show({
						title:'提示',
   						msg: '是否确定更改当前订单汇率',
   						buttons: {yes:'是', no:'否'},
   						fn: function(buttonId,text){
   							if(buttonId=='yes'){
								var param = "&action=changeSalesRate&contractSalesId=${sales.contractSalesId}&sapOrderNo=${sales.sapOrderNo}&rate="+$('vbkdKurrf').value;
								new AjaxEngine('contractController.spr', 
									   {method:"post", parameters: param, onComplete: callBackHandle});
            	           }
   						},
   						icon: Ext.MessageBox.QUESTION
					});
            	}
            }]
		}]
	});
});
function handlerActivate(tab){
  
	var requestUrl = 'contractController.spr?action=queryContractInfoByGroupId&groupid=${sales.contractGroupId}';
   
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

function mainInit(){
	var tradeType = '${sales.tradeType}';
	$("trView1").style.display = "none";
	//进口，内贸业务
	if (tradeType == '1' || tradeType == '3' || tradeType == '6' || tradeType == '7'|| tradeType == '8' || tradeType == '9' || tradeType == '10'){
		$("txtPayCon").value = "收款条件";
		$("txtPayStyle").value = "收款方式";
		$("txtShipmentPort").value = "交货地点";
		$("txtShipmentDate").value = "交货日期";
		$("vbkdInco2").disabled=true;
		$("vbkdInco2").style.background="#CCCCCC";
		$("destinationPort").disabled  = true;
		$("destinationPort").style.background="#CCCCCC";
		dict_div_vbkdInco1.disable(true);
		$("trView1").style.display = "";
		$("tdView3").style.display = "none";
		$("tdView4").style.display = "none";
		$("tdView7").style.display = "";
		$("tdView8").style.display = "";
	}
    if (tradeType == '5' || tradeType == '6'){
		$("txtOrderNet").value = "开票金额";
		$("txtOrderTaxes").value = "税金";
	}
	//出口
	if (tradeType == '2' || tradeType == '4' || tradeType == '5'|| tradeType == '11'|| tradeType == '12'){
		$("trView1").style.display = "";		
	}
}
window.onload = function() {
	mainInit();
}
</script>

<fiscxd:dictionary divId="div_vbakSpart" fieldName="vbakSpart" dictionaryName="BM_PRODUCT_GROUP" width="150" selectedValue="${sales.vbakSpart}" disable="true" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbakVtweg" fieldName="vbakVtweg" dictionaryName="BM_DISTRIBUTION_CHANNEL" width="150" selectedValue="${sales.vbakVtweg}" disable="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbakVkbur" fieldName="vbakVkbur" dictionaryName="BM_SALES_AREAS" width="150" selectedValue="${sales.vbakVkbur}" disable="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbakVkgrp" fieldName="vbakVkgrp" dictionaryName="BM_SALES_GROUP" width="150" selectedValue="${sales.vbakVkgrp}" disable="true"></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbkdZterm" fieldName="vbkdZterm" dictionaryName="BM_PAYMENT_TYPE" width="150" selectedValue="${sales.vbkdZterm}" disable="true" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbakWaerk" fieldName="vbakWaerk" dictionaryName="BM_CURRENCY" width="150" selectedValue="${sales.vbakWaerk}" needDisplayCode="true" disable="true" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbkdBzirk" fieldName="vbkdBzirk" dictionaryName="BM_SALES_DISTRICT" width="150" selectedValue="${sales.vbkdBzirk}" disable="true" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbkdInco1" fieldName="vbkdInco1" dictionaryName="BM_INCOTERM_TYPE" width="150" selectedValue="${sales.vbkdInco1}" needDisplayCode="true" disable="true" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbkdZlsch" fieldName="vbkdZlsch" dictionaryName="BM_PAY_TYPE" width="150" selectedValue="${sales.vbkdZlsch}" disable="true" ></fiscxd:dictionary>
<fiscxd:dictionary divId="div_vbakAuart" fieldName="vbakAuart" dictionaryName="BM_SALES_JOURNAL_TYPE" width="150" selectedValue="${sales.vbakAuart}" disable="true" ></fiscxd:dictionary>

<fiscxd:dictionary divId="div_ymatGroup2" fieldName="ymatGroup" dictionaryName="YMATGROUP" width="150" selectedValue="${sales.ymatGroup}" disable="true" ></fiscxd:dictionary>

