<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年08月02日 13点55分28秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象内贸付款(HomePayment)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<style type="text/css">  
*{margin:0;padding:0;}   
html,body {height:100%;overflow:hidden;}   
body {overflow:auto;};   
  
</style>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
</head>
<body>
<fisc:grid divId="div_homePaymentItem" boName="HomePaymentItem" needCheckBox="true" editable="true" defaultCondition=" YPAYMENTITEM.PAYMENTID='${homePayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" pageSize="10000"></fisc:grid>
<fisc:grid divId="div_homePaymentCbill" boName="HomePaymentCbill" needCheckBox="true" editable="true" defaultCondition=" YPAYMENTCBILL.PAYMENTID='${homePayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" pageSize="10000" ></fisc:grid>
<fisc:grid divId="div_homePayBankItem" boName="HomePayBankItem" needCheckBox="true" editable="true" defaultCondition=" YPAYBANKITEM.PAYMENTID='${homePayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" pageSize="10000"></fisc:grid>
<fisc:grid divId="div_homeDocuBankItem" boName="HomeDocuBankItem" needCheckBox="true" editable="true" defaultCondition=" YDOUCARYBANKITEM.PAYMENTID='${homePayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" pageSize="10000"></fisc:grid>
<fisc:grid divId="div_homePaymentRelat" boName="HomePaymentRelat" needCheckBox="true" editable="true" defaultCondition=" YPAYMENTRELATED.PAYMENTID='${homePayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:attachement businessId="${businessId}" allowDelete="true" allowUpload="true" divId="div_attachement" boName="HomePayment" boProperty="attachement" gridPageSize="10" gridHeight="285" ></fisc:attachement>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${homePayment.paymentid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.supplier" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.supplier}：</td>
		<td width="30%">
			<div id="div_supplier_sh"></div>
			<fisc:searchHelp divId="div_supplier_sh" boName="HomePayment" boProperty="supplier"  value="${homePayment.supplier}" editable="false"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.dept_id" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.dept_id}：</td>
		<td  width="40%">
			<div id="div_dept_id_sh"></div>
			<fisc:searchHelp divId="div_dept_id_sh" boName="HomePayment" boProperty="dept_id"  value="${homePayment.dept_id}"></fisc:searchHelp>
		</td>	
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.paymenttype" nodeId="${workflowNodeDefId}"/> >${vt.property.paymenttype}：</td>
		<td width="30%">
			<div id="div_paymenttype_dict"></div>
			<fisc:dictionary boName="HomePayment" boProperty="paymenttype" dictionaryName="YDPAYTRADETYPE" divId="div_paymenttype_dict" isNeedAuth="false"  value="${homePayment.paymenttype}" editable="true" ></fisc:dictionary>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.pay_type" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.pay_type}：</td>
		<td  width="40%">
			<div id="div_pay_type_dict"></div>
			<fisc:dictionary boName="HomePayment" boProperty="pay_type" dictionaryName="YDPAYTYPE" divId="div_pay_type_dict" isNeedAuth="false"  value="${homePayment.pay_type}" editable="true" ></fisc:dictionary>
		</td>
	</tr>

	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="HomePayment.paymentno" nodeId="${workflowNodeDefId}"/> >${vt.property.paymentno}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="HomePayment.paymentno" name="HomePayment.paymentno" value="${homePayment.paymentno}" <fisc:authentication sourceName="HomePayment.paymentno" nodeId="${workflowNodeDefId}"/>  readonly="readonly">
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="HomePayment.applyamount" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.applyamount}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="HomePayment.applyamount" name="HomePayment.applyamount" value="${homePayment.applyamount}" <fisc:authentication sourceName="HomePayment.applyamount" nodeId="${workflowNodeDefId}"/> >
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.currency" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.currency}：</td>
		<td width="30%">
			<div id="div_currency_sh"></div>
			<fisc:searchHelp divId="div_currency_sh" boName="HomePayment" boProperty="currency"  value="${homePayment.currency}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.closeexchangerat" nodeId="${workflowNodeDefId}"/> >${vt.property.closeexchangerat}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="HomePayment.closeexchangerat" name="HomePayment.closeexchangerat" value="${homePayment.closeexchangerat}"   <fisc:authentication sourceName="HomePayment.closeexchangerat" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.collectbankid" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.collectbankid}：</td>
		<td width="30%">
			<div id="div_collectbankid_sh"></div>
			<%/*<fisc:searchHelp divId="div_collectbankid_sh" boName="HomePayment" boProperty="collectbankid"  value="${homePayment.collectbankid}"></fisc:searchHelp>*/ %>
			<input type="text" class="inputText" id="HomePayment.collectbankid" name="HomePayment.collectbankid" value="${homePayment.collectbankid}"   <fisc:authentication sourceName="HomePayment.collectbankid" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.collectbankacc" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.collectbankacc}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="HomePayment.collectbankacc" name="HomePayment.collectbankacc" value="${homePayment.collectbankacc}"   <fisc:authentication sourceName="HomePayment.collectbankacc" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.finumber" nodeId="${workflowNodeDefId}"/> >${vt.property.finumber}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="HomePayment.finumber" name="HomePayment.finumber" value="${homePayment.finumber}"   <fisc:authentication sourceName="HomePayment.finumber" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.replacedate" nodeId="${workflowNodeDefId}"/> >${vt.property.replacedate}：</td>
		<td  width="40%">
			<input type="text" id="HomePayment.replacedate" name="HomePayment.replacedate" value="">
				<fisc:calendar applyTo="HomePayment.replacedate"  divId="" fieldName="" defaultValue="${homePayment.replacedate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.musttaketickleda" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.musttaketickleda}：</td>
		<td width="30%">
			<input type="text" id="HomePayment.musttaketickleda" name="HomePayment.musttaketickleda" value="">
				<fisc:calendar applyTo="HomePayment.musttaketickleda"  divId="" fieldName="" defaultValue="${homePayment.musttaketickleda}"></fisc:calendar>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.arrivegoodsdate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.arrivegoodsdate}：</td>
		<td  width="40%">
			<input type="text" id="HomePayment.arrivegoodsdate" name="HomePayment.arrivegoodsdate" value="">
				<fisc:calendar applyTo="HomePayment.arrivegoodsdate"  divId="" fieldName="" defaultValue="${homePayment.arrivegoodsdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="HomePayment.draft" nodeId="${workflowNodeDefId}"/> >${vt.property.draft}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="HomePayment.draft" name="HomePayment.draft" value="${homePayment.draft}" <fisc:authentication sourceName="HomePayment.draft" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.draftdate" nodeId="${workflowNodeDefId}"/> >${vt.property.draftdate}：</td>
		<td  width="40%">
			<input type="text" id="HomePayment.draftdate" name="HomePayment.draftdate" value="">
				<fisc:calendar applyTo="HomePayment.draftdate"  divId="" fieldName="" defaultValue="${homePayment.draftdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.ticketbankid" nodeId="${workflowNodeDefId}"/> >${vt.property.ticketbankid}：</td>
		<td width="30%">
			<div id="div_ticketbankid_sh"></div>
			<fisc:searchHelp divId="div_ticketbankid_sh" boName="HomePayment" boProperty="ticketbankid"  value="${homePayment.ticketbankid}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.billbc" nodeId="${workflowNodeDefId}"/> >${vt.property.billbc}：</td>
		<td width="30%">
			<div id="div_billbc_sh"></div>
			<fisc:searchHelp divId="div_billbc_sh" boName="HomePayment" boProperty="billbc"  value="${homePayment.billbc}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="HomePayment" userId="${homePayment.creator}"></fisc:user>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="HomePayment.createtime" name="HomePayment.createtime" value="${homePayment.createtime}"  readonly="readonly">
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.remark" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.remark}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="HomePayment.remark" name="HomePayment.remark"  <fisc:authentication sourceName="HomePayment.remark" nodeId="${workflowNodeDefId}"/>>${homePayment.remark}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.text" nodeId="${workflowNodeDefId}"/> valign="top"><font color="red">★</font>${vt.property.text}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="HomePayment.text" name="HomePayment.text"  <fisc:authentication sourceName="HomePayment.text" nodeId="${workflowNodeDefId}"/>>${homePayment.text}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.factamount" nodeId="${workflowNodeDefId}"/> >${vt.property.factamount}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="HomePayment.factamount" name="HomePayment.factamount" value="${homePayment.factamount}"   <fisc:authentication sourceName="HomePayment.factamount" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.factcurrency" nodeId="${workflowNodeDefId}"/> >${vt.property.factcurrency}：</td>
		<td  width="40%">
			<div id="div_factcurrency_sh"></div>
			<fisc:searchHelp divId="div_factcurrency_sh" boName="HomePayment" boProperty="factcurrency"  value="${homePayment.factcurrency}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="HomePayment.exchangerate" nodeId="${workflowNodeDefId}"/> >${vt.property.exchangerate}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="HomePayment.exchangerate" name="HomePayment.exchangerate" value="${homePayment.exchangerate}"   <fisc:authentication sourceName="HomePayment.exchangerate" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="HomePayment.payer" nodeId="${workflowNodeDefId}"/> >${vt.property.payer}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="HomePayment.payer" name="HomePayment.payer" value="${homePayment.payer}" <fisc:authentication sourceName="HomePayment.payer" nodeId="${workflowNodeDefId}"/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="HomePayment.voucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherdate}：</td>
		<td   width="40%" >
			<input type="text" id="HomePayment.voucherdate" name="HomePayment.voucherdate" value="">
				<fisc:calendar applyTo="HomePayment.voucherdate"  divId="" fieldName="" defaultValue="${homePayment.voucherdate}"></fisc:calendar>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="HomePayment.accountdate" nodeId="${workflowNodeDefId}"/> >${vt.property.accountdate}：</td>
		<td  width="30%" >
			<input type="text" id="HomePayment.accountdate" name="HomePayment.accountdate" value="">
				<fisc:calendar applyTo="HomePayment.accountdate"  divId="" fieldName="" defaultValue="${homePayment.accountdate}"></fisc:calendar>
		</td>
	</tr>
	<!-- start yanghancai 2010-09-17 隐藏“即期/承兑到期付款日”、“付款基准日”-->
	<input type="hidden" id="HomePayment.paydate" name=HomePayment.paydate value="${homePayment.paydate}">
	<input type="hidden" id="HomePayment.expiredate" name="HomePayment.expiredate" value="${homePayment.expiredate}">		
	<!-- end yanghancai 2010-09-17 隐藏“即期/承兑到期付款日”、“付款基准日”-->	
	<input type="hidden" id="HomePayment.client" name="HomePayment.client" value="${homePayment.client}">
	<input type="hidden" id="HomePayment.paymentid" name="HomePayment.paymentid" value="${homePayment.paymentid}">
	<input type="hidden" id="HomePayment.paymentstate" name="HomePayment.paymentstate" value="${homePayment.paymentstate}">
	<input type="hidden" id="HomePayment.trade_type" name="HomePayment.trade_type" value="${homePayment.trade_type}">
	<input type="hidden" id="HomePayment.currency2" name="HomePayment.currency2" value="${homePayment.currency2}">
	<input type="hidden" id="HomePayment.documentarydate" name="HomePayment.documentarydate" value="${homePayment.documentarydate}">
	<input type="hidden" id="HomePayment.documentarypaydt" name="HomePayment.documentarypaydt" value="${homePayment.documentarypaydt}">
	<input type="hidden" id="HomePayment.documentarylimit" name="HomePayment.documentarylimit" value="${homePayment.documentarylimit}">
	<input type="hidden" id="HomePayment.documentaryrate" name="HomePayment.documentaryrate" value="${homePayment.documentaryrate}">
	<input type="hidden" id="HomePayment.payrealdate" name="HomePayment.payrealdate" value="${homePayment.payrealdate}">
	<input type="hidden" id="HomePayment.payrealamount" name="HomePayment.payrealamount" value="${homePayment.payrealamount}">
	<input type="hidden" id="HomePayment.doctaryreallimit" name="HomePayment.doctaryreallimit" value="${homePayment.doctaryreallimit}">
	<input type="hidden" id="HomePayment.doctaryrealrate" name="HomePayment.doctaryrealrate" value="${homePayment.doctaryrealrate}">
	<input type="hidden" id="HomePayment.doctaryinterest" name="HomePayment.doctaryinterest" value="${homePayment.doctaryinterest}">
	<input type="hidden" id="HomePayment.writelistno" name="HomePayment.writelistno" value="${homePayment.writelistno}">
	<input type="hidden" id="HomePayment.factexchangerate" name="HomePayment.factexchangerate" value="${homePayment.factexchangerate}">
	<input type="hidden" id="HomePayment.businessstate" name="HomePayment.businessstate" value="${homePayment.businessstate}">
	<input type="hidden" id="HomePayment.processstate" name="HomePayment.processstate" value="${homePayment.processstate}">
	<input type="hidden" id="HomePayment.collectbankname" name="HomePayment.collectbankname" value="${homePayment.collectbankname}">
	<input type="hidden" id="HomePayment.isrepresentpay" name="HomePayment.isrepresentpay" value="${homePayment.isrepresentpay}">
	<input type="hidden" id="HomePayment.representpaycust" name="HomePayment.representpaycust" value="${homePayment.representpaycust}">
	<input type="hidden" id="HomePayment.collectbanksubje" name="HomePayment.collectbanksubje" value="${homePayment.collectbanksubje}">
	<input type="hidden" id="HomePayment.ticketbankname" name="HomePayment.ticketbankname" value="${homePayment.ticketbankname}">
	<input type="hidden" id="HomePayment.ticketbanksubjec" name="HomePayment.ticketbanksubjec" value="${homePayment.ticketbanksubjec}">
	<input type="hidden" id="HomePayment.repaymentid" name="HomePayment.repaymentid" value="${homePayment.repaymentid}">
	<input type="hidden" id="HomePayment.isoverrepay" name="HomePayment.isoverrepay" value="${homePayment.isoverrepay}">
	<input type="hidden" id="HomePayment.redocaryamount" name="HomePayment.redocaryamount" value="${homePayment.redocaryamount}">
	<input type="hidden" id="HomePayment.convertamount" name="HomePayment.convertamount" value="${homePayment.convertamount}">
	<input type="hidden" id="HomePayment.redocaryrate" name="HomePayment.redocaryrate" value="${homePayment.redocaryrate}">
	<input type="hidden" id="HomePayment.redocarybc" name="HomePayment.redocarybc" value="${homePayment.redocarybc}">
	<input type="hidden" id="HomePayment.lastmodifytime" name="HomePayment.lastmodifytime" value="${homePayment.lastmodifytime}">
	<input type="hidden" id="HomePayment.lastmodifyer" name="HomePayment.lastmodifyer" value="${homePayment.lastmodifyer}">
	
	<input type="hidden" id="HomePayment.istradesubsist" name="HomePayment.istradesubsist" value="${homePayment.istradesubsist}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_homePaymentItem"></div>
<div id="div_homePaymentCbill"></div>
<div id="div_homePayBankItem"></div>
<div id="div_homeDocuBankItem"></div>
<div id="div_homeSettSubj" class="x-hide-display" >
<form id="homeSettSubjForm" name="homeSettSubjForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.debtcredit1" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.debtcredit1}： </td>
<td width="30%">
<div id="div_HomeSettSubjdebtcredit1_dict"></div>
<fisc:dictionary boName="HomeSettSubj" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_HomeSettSubjdebtcredit1_dict" isNeedAuth="false" value="${homePayment.homeSettSubj.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.amount1" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.amount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="HomeSettSubj.amount1" name="HomeSettSubj.amount1" value="${homePayment.homeSettSubj.amount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.standardamount1" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.standardamount1}： </td>
<td width="30%">
<input type="text" class="inputText" id="HomeSettSubj.standardamount1" name="HomeSettSubj.standardamount1" value="${homePayment.homeSettSubj.standardamount1}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.settlesubject1" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.settlesubject1}： </td>
<td width="40%">
<div id="div_HomeSettSubjsettlesubject1_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjsettlesubject1_sh" boName="HomeSettSubj" boProperty="settlesubject1" value="${homePayment.homeSettSubj.settlesubject1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.costcenter1" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.costcenter1}： </td>
<td width="30%">
<div id="div_HomeSettSubjcostcenter1_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjcostcenter1_sh" boName="HomeSettSubj" boProperty="costcenter1" value="${homePayment.homeSettSubj.costcenter1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.depid1" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.depid1}： </td>
<td width="40%">
<div id="div_HomeSettSubjdepid1_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjdepid1_sh" boName="HomeSettSubj" boProperty="depid1" value="${homePayment.homeSettSubj.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.orderno1" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.orderno1}： </td>
<td width="30%">
<div id="div_HomeSettSubjorderno1_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjorderno1_sh" boName="HomeSettSubj" boProperty="orderno1" value="${homePayment.homeSettSubj.orderno1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.rowno1" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.rowno1}： </td>
<td width="40%">
<input type="text" class="inputText" id="HomeSettSubj.rowno1" name="HomeSettSubj.rowno1" value="${homePayment.homeSettSubj.rowno1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.antiaccount1" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.antiaccount1}： </td>
<td width="30%">
<div id="div_HomeSettSubjantiaccount1_dict"></div>
<fisc:dictionary boName="HomeSettSubj" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_HomeSettSubjantiaccount1_dict" isNeedAuth="false" value="${homePayment.homeSettSubj.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.debtcredit2" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.debtcredit2}： </td>
<td width="30%">
<div id="div_HomeSettSubjdebtcredit2_dict"></div>
<fisc:dictionary boName="HomeSettSubj" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_HomeSettSubjdebtcredit2_dict" isNeedAuth="false" value="${homePayment.homeSettSubj.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.amount2" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.amount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="HomeSettSubj.amount2" name="HomeSettSubj.amount2" value="${homePayment.homeSettSubj.amount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.standardamount2" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.standardamount2}： </td>
<td width="30%">
<input type="text" class="inputText" id="HomeSettSubj.standardamount2" name="HomeSettSubj.standardamount2" value="${homePayment.homeSettSubj.standardamount2}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.settlesubject2" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.settlesubject2}： </td>
<td width="40%">
<div id="div_HomeSettSubjsettlesubject2_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjsettlesubject2_sh" boName="HomeSettSubj" boProperty="settlesubject2" value="${homePayment.homeSettSubj.settlesubject2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.costcenter2" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.costcenter2}： </td>
<td width="30%">
<div id="div_HomeSettSubjcostcenter2_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjcostcenter2_sh" boName="HomeSettSubj" boProperty="costcenter2" value="${homePayment.homeSettSubj.costcenter2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.depid2" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.depid2}： </td>
<td width="40%">
<div id="div_HomeSettSubjdepid2_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjdepid2_sh" boName="HomeSettSubj" boProperty="depid2" value="${homePayment.homeSettSubj.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.orderno2" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.orderno2}： </td>
<td width="30%">
<div id="div_HomeSettSubjorderno2_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjorderno2_sh" boName="HomeSettSubj" boProperty="orderno2" value="${homePayment.homeSettSubj.orderno2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.rowno2" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.rowno2}： </td>
<td width="40%">
<input type="text" class="inputText" id="HomeSettSubj.rowno2" name="HomeSettSubj.rowno2" value="${homePayment.homeSettSubj.rowno2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.antiaccount2" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.antiaccount2}： </td>
<td width="30%">
<div id="div_HomeSettSubjantiaccount2_dict"></div>
<fisc:dictionary boName="HomeSettSubj" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_HomeSettSubjantiaccount2_dict" isNeedAuth="false" value="${homePayment.homeSettSubj.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.debtcredit3" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.debtcredit3}： </td>
<td width="30%">
<div id="div_HomeSettSubjdebtcredit3_dict"></div>
<fisc:dictionary boName="HomeSettSubj" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_HomeSettSubjdebtcredit3_dict" isNeedAuth="false" value="${homePayment.homeSettSubj.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.amount3" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.amount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="HomeSettSubj.amount3" name="HomeSettSubj.amount3" value="${homePayment.homeSettSubj.amount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.standardamount3" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.standardamount3}： </td>
<td width="30%">
<input type="text" class="inputText" id="HomeSettSubj.standardamount3" name="HomeSettSubj.standardamount3" value="${homePayment.homeSettSubj.standardamount3}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.settlesubject3" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.settlesubject3}： </td>
<td width="40%">
<div id="div_HomeSettSubjsettlesubject3_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjsettlesubject3_sh" boName="HomeSettSubj" boProperty="settlesubject3" value="${homePayment.homeSettSubj.settlesubject3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.costcenter3" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.costcenter3}： </td>
<td width="30%">
<div id="div_HomeSettSubjcostcenter3_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjcostcenter3_sh" boName="HomeSettSubj" boProperty="costcenter3" value="${homePayment.homeSettSubj.costcenter3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.depid3" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.depid3}： </td>
<td width="40%">
<div id="div_HomeSettSubjdepid3_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjdepid3_sh" boName="HomeSettSubj" boProperty="depid3" value="${homePayment.homeSettSubj.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.orderno3" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.orderno3}： </td>
<td width="30%">
<div id="div_HomeSettSubjorderno3_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjorderno3_sh" boName="HomeSettSubj" boProperty="orderno3" value="${homePayment.homeSettSubj.orderno3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.rowno3" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.rowno3}： </td>
<td width="40%">
<input type="text" class="inputText" id="HomeSettSubj.rowno3" name="HomeSettSubj.rowno3" value="${homePayment.homeSettSubj.rowno3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.antiaccount3" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.antiaccount3}： </td>
<td width="30%">
<div id="div_HomeSettSubjantiaccount3_dict"></div>
<fisc:dictionary boName="HomeSettSubj" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_HomeSettSubjantiaccount3_dict" isNeedAuth="false" value="${homePayment.homeSettSubj.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.debtcredit4" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.debtcredit4}： </td>
<td width="30%">
<div id="div_HomeSettSubjdebtcredit4_dict"></div>
<fisc:dictionary boName="HomeSettSubj" boProperty="debtcredit4" dictionaryName="YDDEBTCREDIT" divId="div_HomeSettSubjdebtcredit4_dict" isNeedAuth="false" value="${homePayment.homeSettSubj.debtcredit4}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.amount4" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.amount4}： </td>
<td width="40%">
<input type="text" class="inputText" id="HomeSettSubj.amount4" name="HomeSettSubj.amount4" value="${homePayment.homeSettSubj.amount4}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.standardamount4" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.standardamount4}： </td>
<td width="30%">
<input type="text" class="inputText" id="HomeSettSubj.standardamount4" name="HomeSettSubj.standardamount4" value="${homePayment.homeSettSubj.standardamount4}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.settlesubject4" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.settlesubject4}： </td>
<td width="40%">
<div id="div_HomeSettSubjsettlesubject4_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjsettlesubject4_sh" boName="HomeSettSubj" boProperty="settlesubject4" value="${homePayment.homeSettSubj.settlesubject4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.profitcenter" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.profitcenter}： </td>
<td width="30%">
<div id="div_HomeSettSubjprofitcenter_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjprofitcenter_sh" boName="HomeSettSubj" boProperty="profitcenter" value="${homePayment.homeSettSubj.profitcenter}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.depid4" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.depid4}： </td>
<td width="40%">
<div id="div_HomeSettSubjdepid4_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjdepid4_sh" boName="HomeSettSubj" boProperty="depid4" value="${homePayment.homeSettSubj.depid4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.orderno4" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.orderno4}： </td>
<td width="30%">
<div id="div_HomeSettSubjorderno4_sh"></div>
<fisc:searchHelp divId="div_HomeSettSubjorderno4_sh" boName="HomeSettSubj" boProperty="orderno4" value="${homePayment.homeSettSubj.orderno4}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeSettSubj.rowno4" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.rowno4}： </td>
<td width="40%">
<input type="text" class="inputText" id="HomeSettSubj.rowno4" name="HomeSettSubj.rowno4" value="${homePayment.homeSettSubj.rowno4}" >
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="HomeSettSubj.antiaccount4" nodeId="${workflowNodeDefId}"/> >${homeSettSubjVt.property.antiaccount4}：</td>
<td width="30%" >
<div id="div_HomeSettSubjantiaccount4_dict"></div>
<fisc:dictionary boName="HomeSettSubj" boProperty="antiaccount4" dictionaryName="YDYESORNO" divId="div_HomeSettSubjantiaccount4_dict" isNeedAuth="false" value="${homePayment.homeSettSubj.antiaccount4}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
</table>
<input type="hidden" id="HomeSettSubj.settlesubjectid" name="HomeSettSubj.settlesubjectid" value="${homePayment.homeSettSubj.settlesubjectid}">
<input type="hidden" id="HomeSettSubj.billclearid" name="HomeSettSubj.billclearid" value="${homePayment.homeSettSubj.billclearid}">
<input type="hidden" id="HomeSettSubj.paymentid" name="HomeSettSubj.paymentid" value="${homePayment.paymentid}">
<input type="hidden" id="HomeSettSubj.collectid" name="HomeSettSubj.collectid" value="${homePayment.homeSettSubj.collectid}">
<input type="hidden" id="HomeSettSubj.suppliersclearid" name="HomeSettSubj.suppliersclearid" value="${homePayment.homeSettSubj.suppliersclearid}">
<input type="hidden" id="HomeSettSubj.customsclearid" name="HomeSettSubj.customsclearid" value="${homePayment.homeSettSubj.customsclearid}">
</form>
</div>
<div id="div_homeFundFlow" class="x-hide-display" >
<form id="homeFundFlowForm" name="homeFundFlowForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.debtcredit1" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.debtcredit1}： </td>
<td width="30%">
<div id="div_HomeFundFlowdebtcredit1_dict"></div>
<fisc:dictionary boName="HomeFundFlow" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_HomeFundFlowdebtcredit1_dict" isNeedAuth="false" value="${homePayment.homeFundFlow.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.amount1" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.amount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="HomeFundFlow.amount1" name="HomeFundFlow.amount1" value="${homePayment.homeFundFlow.amount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.standardamount1" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.standardamount1}： </td>
<td width="30%">
<input type="text" class="inputText" id="HomeFundFlow.standardamount1" name="HomeFundFlow.standardamount1" value="${homePayment.homeFundFlow.standardamount1}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.specialaccount1" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.specialaccount1}： </td>
<td width="40%">
<div id="div_HomeFundFlowspecialaccount1_dict"></div>
<fisc:dictionary boName="HomeFundFlow" boProperty="specialaccount1" dictionaryName="YDUMSKZ" divId="div_HomeFundFlowspecialaccount1_dict" isNeedAuth="false" value="${homePayment.homeFundFlow.specialaccount1}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.customer1" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.customer1}： </td>
<td width="30%">
<div id="div_HomeFundFlowcustomer1_sh"></div>
<fisc:searchHelp divId="div_HomeFundFlowcustomer1_sh" boName="HomeFundFlow" boProperty="customer1" value="${homePayment.homeFundFlow.customer1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.depid1" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.depid1}： </td>
<td width="40%">
<div id="div_HomeFundFlowdepid1_sh"></div>
<fisc:searchHelp divId="div_HomeFundFlowdepid1_sh" boName="HomeFundFlow" boProperty="depid1" value="${homePayment.homeFundFlow.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.antiaccount1" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.antiaccount1}： </td>
<td width="30%">
<div id="div_HomeFundFlowantiaccount1_dict"></div>
<fisc:dictionary boName="HomeFundFlow" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_HomeFundFlowantiaccount1_dict" isNeedAuth="false" value="${homePayment.homeFundFlow.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.debtcredit2" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.debtcredit2}： </td>
<td width="30%">
<div id="div_HomeFundFlowdebtcredit2_dict"></div>
<fisc:dictionary boName="HomeFundFlow" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_HomeFundFlowdebtcredit2_dict" isNeedAuth="false" value="${homePayment.homeFundFlow.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.amount2" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.amount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="HomeFundFlow.amount2" name="HomeFundFlow.amount2" value="${homePayment.homeFundFlow.amount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.standardamount2" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.standardamount2}： </td>
<td width="30%">
<input type="text" class="inputText" id="HomeFundFlow.standardamount2" name="HomeFundFlow.standardamount2" value="${homePayment.homeFundFlow.standardamount2}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.specialaccount2" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.specialaccount2}： </td>
<td width="40%">
<div id="div_HomeFundFlowspecialaccount2_dict"></div>
<fisc:dictionary boName="HomeFundFlow" boProperty="specialaccount2" dictionaryName="YDUMSKZ" divId="div_HomeFundFlowspecialaccount2_dict" isNeedAuth="false" value="${homePayment.homeFundFlow.specialaccount2}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.customer2" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.customer2}： </td>
<td width="30%">
<div id="div_HomeFundFlowcustomer2_sh"></div>
<fisc:searchHelp divId="div_HomeFundFlowcustomer2_sh" boName="HomeFundFlow" boProperty="customer2" value="${homePayment.homeFundFlow.customer2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.depid2" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.depid2}： </td>
<td width="40%">
<div id="div_HomeFundFlowdepid2_sh"></div>
<fisc:searchHelp divId="div_HomeFundFlowdepid2_sh" boName="HomeFundFlow" boProperty="depid2" value="${homePayment.homeFundFlow.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.antiaccount2" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.antiaccount2}： </td>
<td width="30%">
<div id="div_HomeFundFlowantiaccount2_dict"></div>
<fisc:dictionary boName="HomeFundFlow" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_HomeFundFlowantiaccount2_dict" isNeedAuth="false" value="${homePayment.homeFundFlow.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.debtcredit3" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.debtcredit3}： </td>
<td width="30%">
<div id="div_HomeFundFlowdebtcredit3_dict"></div>
<fisc:dictionary boName="HomeFundFlow" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_HomeFundFlowdebtcredit3_dict" isNeedAuth="false" value="${homePayment.homeFundFlow.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.amount3" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.amount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="HomeFundFlow.amount3" name="HomeFundFlow.amount3" value="${homePayment.homeFundFlow.amount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.standardamount3" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.standardamount3}： </td>
<td width="30%">
<input type="text" class="inputText" id="HomeFundFlow.standardamount3" name="HomeFundFlow.standardamount3" value="${homePayment.homeFundFlow.standardamount3}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.specialaccount3" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.specialaccount3}： </td>
<td width="40%">
<div id="div_HomeFundFlowspecialaccount3_dict"></div>
<fisc:dictionary boName="HomeFundFlow" boProperty="specialaccount3" dictionaryName="YDUMSKZ" divId="div_HomeFundFlowspecialaccount3_dict" isNeedAuth="false" value="${homePayment.homeFundFlow.specialaccount3}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.customer3" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.customer3}： </td>
<td width="30%">
<div id="div_HomeFundFlowcustomer3_sh"></div>
<fisc:searchHelp divId="div_HomeFundFlowcustomer3_sh" boName="HomeFundFlow" boProperty="customer3" value="${homePayment.homeFundFlow.customer3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="HomeFundFlow.depid3" nodeId="${workflowNodeDefId}"/> >${homeFundFlowVt.property.depid3}： </td>
<td width="40%">
<div id="div_HomeFundFlowdepid3_sh"></div>
<fisc:searchHelp divId="div_HomeFundFlowdepid3_sh" boName="HomeFundFlow" boProperty="depid3" value="${homePayment.homeFundFlow.depid3}"></fisc:searchHelp>
</td>
</tr>
</table>
<input type="hidden" id="HomeFundFlow.fundflowid" name="HomeFundFlow.fundflowid" value="${homePayment.homeFundFlow.fundflowid}">
<input type="hidden" id="HomeFundFlow.billclearid" name="HomeFundFlow.billclearid" value="${homePayment.homeFundFlow.billclearid}">
<input type="hidden" id="HomeFundFlow.collectid" name="HomeFundFlow.collectid" value="${homePayment.homeFundFlow.collectid}">
<input type="hidden" id="HomeFundFlow.paymentid" name="HomeFundFlow.paymentid" value="${homePayment.paymentid}">
<input type="hidden" id="HomeFundFlow.suppliersclearid" name="HomeFundFlow.suppliersclearid" value="${homePayment.homeFundFlow.suppliersclearid}">
<input type="hidden" id="HomeFundFlow.customsclearid" name="HomeFundFlow.customsclearid" value="${homePayment.homeFundFlow.customsclearid}">
</form>
</div>
<div id="div_homePaymentRelat"></div>
<div id="div_attachement" ></div>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/payment/homePaymentywEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/payment/homePaymentywEditGen.js"></script>
<script type="text/javascript" defer="defer">
//----------------手工修改------------------
var strRoleType = '${roletype}';
var xjrj = '${xjrj}';
var username = '${username}';
var isReassign = '${isReassign}';

//----------------手工修改------------------
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${homePayment.processstate}';
//当前对象主键ID
var paymentid = '${homePayment.paymentid}';	



//页面文本
var Txt={
	//内贸付款
	homePayment:'${vt.boText}',
	          
	//内贸付款分配
	homePaymentItem:'${homePaymentItemVt.boText}',
	// 请选择需要进行【内贸付款分配批量删除】操作的记录！
	homePaymentItem_Deletes_SelectToOperation:'${homePaymentItemVt.deletes_SelectToOperate}',
	// 您选择了【内贸付款分配批量删除】操作，是否确定继续该操作？
	homePaymentItem_Deletes_ConfirmOperation:'${homePaymentItemVt.deletes_ConfirmOperation}',
          
	//内贸付款清票
	homePaymentCbill:'${homePaymentCbillVt.boText}',
	// 请选择需要进行【内贸付款清票批量删除】操作的记录！
	homePaymentCbill_Deletes_SelectToOperation:'${homePaymentCbillVt.deletes_SelectToOperate}',
	// 您选择了【内贸付款清票批量删除】操作，是否确定继续该操作？
	homePaymentCbill_Deletes_ConfirmOperation:'${homePaymentCbillVt.deletes_ConfirmOperation}',
          
	//内贸付款银行
	homePayBankItem:'${homePayBankItemVt.boText}',
	// 请选择需要进行【内贸付款银行批量删除】操作的记录！
	homePayBankItem_Deletes_SelectToOperation:'${homePayBankItemVt.deletes_SelectToOperate}',
	// 您选择了【内贸付款银行批量删除】操作，是否确定继续该操作？
	homePayBankItem_Deletes_ConfirmOperation:'${homePayBankItemVt.deletes_ConfirmOperation}',
          
	//押汇/海外代付银行
	homeDocuBankItem:'${homeDocuBankItemVt.boText}',
	// 请选择需要进行【押汇/海外代付银行批量删除】操作的记录！
	homeDocuBankItem_Deletes_SelectToOperation:'${homeDocuBankItemVt.deletes_SelectToOperate}',
	// 您选择了【押汇/海外代付银行批量删除】操作，是否确定继续该操作？
	homeDocuBankItem_Deletes_ConfirmOperation:'${homeDocuBankItemVt.deletes_ConfirmOperation}',
          
	//内贸付款结算科目
	homeSettSubj:'${homeSettSubjVt.boText}',
          
	//内贸纯资金往来
	homeFundFlow:'${homeFundFlowVt.boText}',
	//boText复制创建
	homeFundFlow_CopyCreate:'${homeFundFlowVt.boTextCopyCreate}',
	// 进行【内贸纯资金往来复制创建】操作时，只允许选择一条记录！
	homeFundFlow_CopyCreate_AllowOnlyOneItemForOperation:'${homeFundFlowVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【内贸纯资金往来复制创建】操作的记录！
	homeFundFlow_CopyCreate_SelectToOperation:'${homeFundFlowVt.copyCreate_SelectToOperate}',
	// 请选择需要进行【内贸纯资金往来批量删除】操作的记录！
	homeFundFlow_Deletes_SelectToOperation:'${homeFundFlowVt.deletes_SelectToOperate}',
	// 您选择了【内贸纯资金往来批量删除】操作，是否确定继续该操作？
	homeFundFlow_Deletes_ConfirmOperation:'${homeFundFlowVt.deletes_ConfirmOperation}',
          
	//付款相关单据
	homePaymentRelat:'${homePaymentRelatVt.boText}',
	//boText复制创建
	homePayment_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	homePayment_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	homePayment_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
	// '提交成功！'
	submitSuccess:'${vt.submitSuccess}',
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 提示
	cue:'${vt.sCue}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}'
};

/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
					region:'center',
					layout:'border',
					border:false,
					items:[{
							region:'north',
							layout:'fit',
							height:26,
							border:false,
							contentEl:'div_toolbar'
						},{
							region:'center',
							layout:'anchor',
				            height:600,
				            border:false,
				            autoScroll:true,
				            items:[
				               {
				            	id:'currentWorkflowTask',
					            title: '${vt.processApproveInfo}',
					            border:false,
					            layout:'fit',
					            contentEl:'div_top_north'
					          },
				              {
				              		id:'centercontent',
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:610,
				            		contentEl:'div_center'
						}
						,{
									xtype:'tabpanel',
					            	height:310,
					            	id:'tabs',
					            	name:'tabs',
					            	defaults:{bodyStyle:"background-color:#DFE8F6"},
					            	autoScroll: true,
									activeTab:0,
						           items:[
			              						                {
						            		title:'${homePaymentItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'homePaymentItemTab',
						            		contentEl:'div_homePaymentItem'
						            	},
          						                {
						            		title:'${homePaymentCbillVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'homePaymentCbillTab',
						            		contentEl:'div_homePaymentCbill'
						            	},
          						                {
						            		title:'${homePayBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'homePayBankItemTab',
						            		contentEl:'div_homePayBankItem'
						            	},
          						                {
						            		title:'${homeDocuBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'homeDocuBankItemTab',
						            		contentEl:'div_homeDocuBankItem'
						            	},
          						                {
						            		title:'${homeSettSubjVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		autoHeight:true,
						            		id:'homeSettSubjTab',
						            		contentEl:'div_homeSettSubj',
						            		autoScroll: true
						            	},
          						                {
						            		title:'${homeFundFlowVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		autoHeight:true,
						            		id:'homeFundFlowTab',
						            		contentEl:'div_homeFundFlow',
						            		autoScroll: true
						            	},
          						                {
						            		title:'${homePaymentRelatVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'homePaymentRelatTab',
						            		contentEl:'div_homePaymentRelat'
						            	},
          						                {
						            		title:'附件',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'attachementTab',
						            		contentEl:'div_attachement'
						            	}
						    ]}
						   ]}
						,{
							id:'historyWorkflowTask',
							region:'south',
							title:'${vt.processTrackInfo}',
		            		layout:'anchor',
			            	collapsible: true,
			            	collapsed:true,
			            	border:false,
			            	autoScroll: true,
							height:200,
							contentEl:'div_top_south'
						}			
]
				}
                 ]
	});

	var toolbars = new Ext.Toolbar({
			items:[
					'-',
'-','-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessHomePayment,iconCls:'task'},'-',
<%}%>
{id:'_saveOrUpdate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateHomePayment,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelHomePayment,iconCls:'icon-undo'},'-',
{id:'_autoassign',text:'自动分配',handler:_autoassignHomePayment,iconCls:'icon-add'},'-',
{id:'_clearassign',text:'清除分配',handler:_clearassignHomePayment,iconCls:'icon-delete'},'-',
{id:'_viewCredit',text:'查看信用额度',handler:_viewCreditHomePayment,iconCls:'icon-view'},'-',
{id:'_bookofaccount',text:'现金日记帐',handler:_bookofaccountHomePayment,iconCls:'icon-edit'},'-',
{id:'_simulate',text:'模拟凭证',handler:_simulateHomePayment,iconCls:'icon-edit'},'-',
{id:'_submitForReassign',text:'重分配提交',handler:_submitForReassignHomePayment,iconCls:'task'},'-',
{id:'_print',text:'打印',handler:_printHomePayment,iconCls:'task'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	var isTask = '${workflowTaskId}';
	if(!isTask || isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited && isSubmited!='' && isSubmited!=' '){
<%
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
Ext.getCmp('_submitProcess').disable();
<%}%>		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
	}
//});
Ext.onReady(function(){
    var tabsSize = 8;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 8 ; i++){
		   tabs.setActiveTab(8-1-i);
		}
	}
 });
</script>

