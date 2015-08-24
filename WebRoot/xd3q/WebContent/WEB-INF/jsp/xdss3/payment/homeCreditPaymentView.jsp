<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2013年11月20日 15点47分20秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象国内信用证(HomeCreditPayment)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.viewPage}</title>
</head>
<body>
<fisc:grid divId="div_homeCreditPayItem" boName="HomeCreditPayItem" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YPAYMENTITEM.PAYMENTID='${homeCreditPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_homeCreditPayCbill" boName="HomeCreditPayCbill" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YPAYMENTCBILL.PAYMENTID='${homeCreditPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_homeCreditBankItem" boName="HomeCreditBankItem" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YPAYBANKITEM.PAYMENTID='${homeCreditPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_homeCreditDocuBank" boName="HomeCreditDocuBank" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YDOUCARYBANKITEM.PAYMENTID='${homeCreditPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:attachement businessId="${businessId}" allowDelete="true" divId="div_attachementAttachement" boName="HomeCreditPayment" boProperty="attachement" gridPageSize="10" gridHeight="285" needToolbar="false" ></fisc:attachement>
<fisc:grid divId="div_homeCreditRebank" boName="HomeCreditRebank" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YBILLPAYMENTBANK.PAYMENTID='${homeCreditPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_homeCreditRelatPay" boName="HomeCreditRelatPay" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YPAYMENTRELATED.PAYMENTID='${homeCreditPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${homeCreditPayment.paymentid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.supplier" nodeId="${workflowNodeDefId}"/> >${vt.property.supplier}：</td>
		<td width="30%">
			<div id="div_supplier_sh"></div>
			<fisc:searchHelp divId="div_supplier_sh" boName="HomeCreditPayment" boProperty="supplier"  value="${homeCreditPayment.supplier}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.paymentno" nodeId="${workflowNodeDefId}"/> >${vt.property.paymentno}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="HomeCreditPayment.paymentno" name="HomeCreditPayment.paymentno" value="${homeCreditPayment.paymentno}" <fisc:authentication sourceName="HomeCreditPayment.paymentno" nodeId="${workflowNodeDefId}"/>   readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.paymenttype" nodeId="${workflowNodeDefId}"/> >付款方式：</td>
		<td width="30%">
			<div id="div_paymenttype_dict"></div>
			<fisc:dictionary boName="HomeCreditPayment" boProperty="paymenttype" dictionaryName="YDPAYTRADETYPE" divId="div_paymenttype_dict" isNeedAuth="false"  value="${homeCreditPayment.paymenttype}"  ></fisc:dictionary>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.pay_type" nodeId="${workflowNodeDefId}"/> >${vt.property.pay_type}：</td>
		<td  width="40%">
			<div id="div_pay_type_dict"></div>
			<fisc:dictionary boName="HomeCreditPayment" boProperty="pay_type" dictionaryName="YDPAYTYPE" divId="div_pay_type_dict" isNeedAuth="false"  value="${homeCreditPayment.pay_type}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
        <td></td>
        <td></td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.applyamount" nodeId="${workflowNodeDefId}"/> >${vt.property.applyamount}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="HomeCreditPayment.applyamount" name="HomeCreditPayment.applyamount" value="${homeCreditPayment.applyamount}" <fisc:authentication sourceName="HomeCreditPayment.applyamount" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.currency" nodeId="${workflowNodeDefId}"/> >${vt.property.currency}：</td>
		<td width="30%">
			<div id="div_currency_sh"></div>
			<fisc:searchHelp divId="div_currency_sh" boName="HomeCreditPayment" boProperty="currency"  value="${homeCreditPayment.currency}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.closeexchangerat" nodeId="${workflowNodeDefId}"/> >${vt.property.closeexchangerat}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="HomeCreditPayment.closeexchangerat" name="HomeCreditPayment.closeexchangerat" value="${homeCreditPayment.closeexchangerat}" <fisc:authentication sourceName="HomeCreditPayment.closeexchangerat" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.collectbankid" nodeId="${workflowNodeDefId}"/> >${vt.property.collectbankid}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="HomeCreditPayment.collectbankid" name="HomeCreditPayment.collectbankid" value="${homeCreditPayment.collectbankid}" <fisc:authentication sourceName="HomeCreditPayment.collectbankid" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.collectbankacc" nodeId="${workflowNodeDefId}"/> >${vt.property.collectbankacc}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="HomeCreditPayment.collectbankacc" name="HomeCreditPayment.collectbankacc" value="${homeCreditPayment.collectbankacc}" <fisc:authentication sourceName="HomeCreditPayment.collectbankacc" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.doctaryinterest" nodeId="${workflowNodeDefId}"/> >${vt.property.doctaryinterest}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="HomeCreditPayment.doctaryinterest" name="HomeCreditPayment.doctaryinterest" value="${homeCreditPayment.doctaryinterest}" <fisc:authentication sourceName="HomeCreditPayment.doctaryinterest" nodeId="${workflowNodeDefId}"/>  >
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.musttaketickleda" nodeId="${workflowNodeDefId}"/> >${vt.property.musttaketickleda}：</td>
		<td width="30%">
			<input type="text" id="HomeCreditPayment.musttaketickleda" name="HomeCreditPayment.musttaketickleda" value="">
				<fisc:calendar applyTo="HomeCreditPayment.musttaketickleda"  divId="" fieldName="" defaultValue="${homeCreditPayment.musttaketickleda}"></fisc:calendar>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.arrivegoodsdate" nodeId="${workflowNodeDefId}"/> >${vt.property.arrivegoodsdate}：</td>
		<td  width="40%">
			<input type="text" id="HomeCreditPayment.arrivegoodsdate" name="HomeCreditPayment.arrivegoodsdate" value="">
				<fisc:calendar applyTo="HomeCreditPayment.arrivegoodsdate"  divId="" fieldName="" defaultValue="${homeCreditPayment.arrivegoodsdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.paydate" nodeId="${workflowNodeDefId}"/> >${vt.property.paydate}：</td>
		<td width="30%">
			<input type="text" id="HomeCreditPayment.paydate" name="HomeCreditPayment.paydate" value="">
				<fisc:calendar applyTo="HomeCreditPayment.paydate"  divId="" fieldName="" defaultValue="${homeCreditPayment.paydate}"></fisc:calendar>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.payer" nodeId="${workflowNodeDefId}"/> >${vt.property.payer}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="HomeCreditPayment.payer" name="HomeCreditPayment.payer" value="${homeCreditPayment.payer}" <fisc:authentication sourceName="HomeCreditPayment.payer" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.documentarydate" nodeId="${workflowNodeDefId}"/> >${vt.property.documentarydate}：</td>
		<td width="30%">
			<input type="text" id="HomeCreditPayment.documentarydate" name="HomeCreditPayment.documentarydate" value="">
				<fisc:calendar applyTo="HomeCreditPayment.documentarydate"  divId="" fieldName="" defaultValue="${homeCreditPayment.documentarydate}"></fisc:calendar>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.documentarylimit" nodeId="${workflowNodeDefId}"/> >${vt.property.documentarylimit}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="HomeCreditPayment.documentarylimit" name="HomeCreditPayment.documentarylimit" value="${homeCreditPayment.documentarylimit}" <fisc:authentication sourceName="HomeCreditPayment.documentarylimit" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.exchangerate" nodeId="${workflowNodeDefId}"/> >${vt.property.exchangerate}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="HomeCreditPayment.exchangerate" name="HomeCreditPayment.exchangerate" value="${homeCreditPayment.exchangerate}" <fisc:authentication sourceName="HomeCreditPayment.exchangerate" nodeId="${workflowNodeDefId}"/>  >
		</td>
        <td></td>
        <td></td>
	</tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.dept_id" nodeId="${workflowNodeDefId}"/> >${vt.property.dept_id}：</td>
		<td  width="40%">
			<div id="div_dept_id_sh"></div>
			<fisc:searchHelp divId="div_dept_id_sh" boName="HomeCreditPayment" boProperty="dept_id"  value="${homeCreditPayment.dept_id}"></fisc:searchHelp>
		</td>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.billbc" nodeId="${workflowNodeDefId}"/> >${vt.property.billbc}：</td>
		<td width="30%">
			<div id="div_billbc_sh"></div>
			<fisc:searchHelp divId="div_billbc_sh" boName="HomeCreditPayment" boProperty="billbc"  value="${homeCreditPayment.billbc}"></fisc:searchHelp>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.draft" nodeId="${workflowNodeDefId}"/> >${vt.property.draft}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="HomeCreditPayment.draft" name="HomeCreditPayment.draft" value="${homeCreditPayment.draft}" <fisc:authentication sourceName="HomeCreditPayment.draft" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.draftdate" nodeId="${workflowNodeDefId}"/> >${vt.property.draftdate}：</td>
		<td  width="40%">
			<input type="text" id="HomeCreditPayment.draftdate" name="HomeCreditPayment.draftdate" value="">
				<fisc:calendar applyTo="HomeCreditPayment.draftdate"  divId="" fieldName="" defaultValue="${homeCreditPayment.draftdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.ticketbankid" nodeId="${workflowNodeDefId}"/> >${vt.property.ticketbankid}：</td>
		<td width="30%">
			<div id="div_ticketbankid_sh"></div>
			<fisc:searchHelp divId="div_ticketbankid_sh" boName="HomeCreditPayment" boProperty="ticketbankid"  value="${homeCreditPayment.ticketbankid}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.writelistno" nodeId="${workflowNodeDefId}"/> >${vt.property.writelistno}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="HomeCreditPayment.writelistno" name="HomeCreditPayment.writelistno" value="${homeCreditPayment.writelistno}" <fisc:authentication sourceName="HomeCreditPayment.writelistno" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.redocarybc" nodeId="${workflowNodeDefId}"/> >${vt.property.redocarybc}：</td>
		<td width="30%">
			<div id="div_redocarybc_sh"></div>
			<fisc:searchHelp divId="div_redocarybc_sh" boName="HomeCreditPayment" boProperty="redocarybc"  value="${homeCreditPayment.redocarybc}"></fisc:searchHelp>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.redocaryamount" nodeId="${workflowNodeDefId}"/> >${vt.property.redocaryamount}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="HomeCreditPayment.redocaryamount" name="HomeCreditPayment.redocaryamount" value="${homeCreditPayment.redocaryamount}" <fisc:authentication sourceName="HomeCreditPayment.redocaryamount" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.replacedate" nodeId="${workflowNodeDefId}"/> >${vt.property.replacedate}：</td>
		<td  width="40%">
			<input type="text" id="HomeCreditPayment.replacedate" name="HomeCreditPayment.replacedate" value="">
				<fisc:calendar applyTo="HomeCreditPayment.replacedate"  divId="" fieldName="" defaultValue="${homeCreditPayment.replacedate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.currency2" nodeId="${workflowNodeDefId}"/> >${vt.property.currency2}：</td>
		<td width="30%">
			<div id="div_currency2_sh"></div>
			<fisc:searchHelp divId="div_currency2_sh" boName="HomeCreditPayment" boProperty="currency2"  value="${homeCreditPayment.currency2}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.expiredate" nodeId="${workflowNodeDefId}"/> >${vt.property.expiredate}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="HomeCreditPayment.expiredate" name="HomeCreditPayment.expiredate" value="${homeCreditPayment.expiredate}" <fisc:authentication sourceName="HomeCreditPayment.expiredate" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.documentarypaydt" nodeId="${workflowNodeDefId}"/> >${vt.property.documentarypaydt}：</td>
		<td  width="40%">
			<input type="text" id="HomeCreditPayment.documentarypaydt" name="HomeCreditPayment.documentarypaydt" value="">
				<fisc:calendar applyTo="HomeCreditPayment.documentarypaydt"  divId="" fieldName="" defaultValue="${homeCreditPayment.documentarypaydt}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.text" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.text}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="HomeCreditPayment.text" name="HomeCreditPayment.text" <fisc:authentication sourceName="HomeCreditPayment.text" nodeId="${workflowNodeDefId}"/>>${homeCreditPayment.text}</textarea>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.remark" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.remark}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="HomeCreditPayment.remark" name="HomeCreditPayment.remark" <fisc:authentication sourceName="HomeCreditPayment.remark" nodeId="${workflowNodeDefId}"/>>${homeCreditPayment.remark}</textarea>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.accountdate" nodeId="${workflowNodeDefId}"/> >${vt.property.accountdate}：</td>
		<td width="30%">
			<input type="text" id="HomeCreditPayment.accountdate" name="HomeCreditPayment.accountdate" value="">
				<fisc:calendar applyTo="HomeCreditPayment.accountdate"  divId="" fieldName="" defaultValue="${homeCreditPayment.accountdate}"></fisc:calendar>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.voucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherdate}：</td>
		<td  width="40%">
			<input type="text" id="HomeCreditPayment.voucherdate" name="HomeCreditPayment.voucherdate" value="">
				<fisc:calendar applyTo="HomeCreditPayment.voucherdate"  divId="" fieldName="" defaultValue="${homeCreditPayment.voucherdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.payrealdate" nodeId="${workflowNodeDefId}"/> >${vt.property.payrealdate}：</td>
		<td width="30%">
			<input type="text" id="HomeCreditPayment.payrealdate" name="HomeCreditPayment.payrealdate" value="">
				<fisc:calendar applyTo="HomeCreditPayment.payrealdate"  divId="" fieldName="" defaultValue="${homeCreditPayment.payrealdate}"></fisc:calendar>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.factamount" nodeId="${workflowNodeDefId}"/> >${vt.property.factamount}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="HomeCreditPayment.factamount" name="HomeCreditPayment.factamount" value="${homeCreditPayment.factamount}" <fisc:authentication sourceName="HomeCreditPayment.factamount" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.factcurrency" nodeId="${workflowNodeDefId}"/> >${vt.property.factcurrency}：</td>
		<td width="30%">
			<div id="div_factcurrency_sh"></div>
			<fisc:searchHelp divId="div_factcurrency_sh" boName="HomeCreditPayment" boProperty="factcurrency"  value="${homeCreditPayment.factcurrency}"></fisc:searchHelp>
		</td>
        <td align="right"  width="15%" <fisc:authentication sourceName="HomeCreditPayment.redocaryamount2" nodeId="${workflowNodeDefId}"/> >累计还押汇金额：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="HomeCreditPayment.redocaryamount2" name="HomeCreditPayment.redocaryamount2" readonly="readonly" value="${homeCreditPayment.redocaryamount2}" <fisc:authentication sourceName="HomeCreditPayment.redocaryamount2" nodeId="${workflowNodeDefId}"/> >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="HomeCreditPayment" userId="${homeCreditPayment.creator}"></fisc:user>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="HomeCreditPayment.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="HomeCreditPayment.createtime" name="HomeCreditPayment.createtime" value="${homeCreditPayment.createtime}"  readonly="readonly">
		</td>
	</tr>

	<input type="hidden" id="HomeCreditPayment.client" name="HomeCreditPayment.client" value="${homeCreditPayment.client}">
	<input type="hidden" id="HomeCreditPayment.paymentid" name="HomeCreditPayment.paymentid" value="${homeCreditPayment.paymentid}">
	<input type="hidden" id="HomeCreditPayment.paymentstate" name="HomeCreditPayment.paymentstate" value="${homeCreditPayment.paymentstate}">
	<input type="hidden" id="HomeCreditPayment.payrealamount" name="HomeCreditPayment.payrealamount" value="${homeCreditPayment.payrealamount}">
	<input type="hidden" id="HomeCreditPayment.doctaryreallimit" name="HomeCreditPayment.doctaryreallimit" value="${homeCreditPayment.doctaryreallimit}">
	<input type="hidden" id="HomeCreditPayment.doctaryrealrate" name="HomeCreditPayment.doctaryrealrate" value="${homeCreditPayment.doctaryrealrate}">
	<input type="hidden" id="HomeCreditPayment.trade_type" name="HomeCreditPayment.trade_type" value="${homeCreditPayment.trade_type}">
	<input type="hidden" id="HomeCreditPayment.factexchangerate" name="HomeCreditPayment.factexchangerate" value="${homeCreditPayment.factexchangerate}">
	<input type="hidden" id="HomeCreditPayment.businessstate" name="HomeCreditPayment.businessstate" value="${homeCreditPayment.businessstate}">
	<input type="hidden" id="HomeCreditPayment.processstate" name="HomeCreditPayment.processstate" value="${homeCreditPayment.processstate}">
	<input type="hidden" id="HomeCreditPayment.collectbankname" name="HomeCreditPayment.collectbankname" value="${homeCreditPayment.collectbankname}">
	<input type="hidden" id="HomeCreditPayment.istradesubsist" name="HomeCreditPayment.istradesubsist" value="${homeCreditPayment.istradesubsist}">
	<input type="hidden" id="HomeCreditPayment.isrepresentpay" name="HomeCreditPayment.isrepresentpay" value="${homeCreditPayment.isrepresentpay}">
	<input type="hidden" id="HomeCreditPayment.representpaycust" name="HomeCreditPayment.representpaycust" value="${homeCreditPayment.representpaycust}">
	<input type="hidden" id="HomeCreditPayment.collectbanksubje" name="HomeCreditPayment.collectbanksubje" value="${homeCreditPayment.collectbanksubje}">
	<input type="hidden" id="HomeCreditPayment.finumber" name="HomeCreditPayment.finumber" value="${homeCreditPayment.finumber}">
	<input type="hidden" id="HomeCreditPayment.ticketbankname" name="HomeCreditPayment.ticketbankname" value="${homeCreditPayment.ticketbankname}">
	<input type="hidden" id="HomeCreditPayment.ticketbanksubjec" name="HomeCreditPayment.ticketbanksubjec" value="${homeCreditPayment.ticketbanksubjec}">
	<input type="hidden" id="HomeCreditPayment.repaymentid" name="HomeCreditPayment.repaymentid" value="${homeCreditPayment.repaymentid}">
	<input type="hidden" id="HomeCreditPayment.isoverrepay" name="HomeCreditPayment.isoverrepay" value="${homeCreditPayment.isoverrepay}">
	<input type="hidden" id="HomeCreditPayment.convertamount" name="HomeCreditPayment.convertamount" value="${homeCreditPayment.convertamount}">
	<input type="hidden" id="HomeCreditPayment.documentaryrate" name="HomeCreditPayment.documentaryrate" value="${homeCreditPayment.documentaryrate}">
	<input type="hidden" id="HomeCreditPayment.redocaryrate" name="HomeCreditPayment.redocaryrate" value="${homeCreditPayment.redocaryrate}">
	<input type="hidden" id="HomeCreditPayment.lastmodifyer" name="HomeCreditPayment.lastmodifyer" value="${homeCreditPayment.lastmodifyer}">
	<input type="hidden" id="HomeCreditPayment.lastmodifytime" name="HomeCreditPayment.lastmodifytime" value="${homeCreditPayment.lastmodifytime}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_homeCreditPayItem"></div>
<div id="div_homeCreditPayCbill"></div>
<div id="div_homeCreditBankItem"></div>
<div id="div_homeCreditDocuBank"></div>
<div id="div_settleSubject" class="x-hide-display">
<form id="settleSubjectForm" name="settleSubjectForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.debtcredit1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit1}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit1_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit1_dict" isNeedAuth="false" value="${homeCreditPayment.settleSubject.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.amount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount1" name="SettleSubject.amount1" value="${homeCreditPayment.settleSubject.amount1}" <fisc:authentication sourceName="SettleSubject.amount1" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.standardamount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount1}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount1" name="SettleSubject.standardamount1" value="${homeCreditPayment.settleSubject.standardamount1}" <fisc:authentication sourceName="SettleSubject.standardamount1" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.settlesubject1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject1}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject1_sh" boName="SettleSubject" boProperty="settlesubject1" value="${homeCreditPayment.settleSubject.settlesubject1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.costcenter1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter1}：</td>
<td width="30%" >
<div id="div_SettleSubjectcostcenter1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter1_sh" boName="SettleSubject" boProperty="costcenter1" value="${homeCreditPayment.settleSubject.costcenter1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.depid1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid1}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid1_sh" boName="SettleSubject" boProperty="depid1" value="${homeCreditPayment.settleSubject.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.orderno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno1}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno1_sh" boName="SettleSubject" boProperty="orderno1" value="${homeCreditPayment.settleSubject.orderno1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.rowno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno1" name="SettleSubject.rowno1" value="${homeCreditPayment.settleSubject.rowno1}" <fisc:authentication sourceName="SettleSubject.rowno1" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.antiaccount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount1}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount1_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount1_dict" isNeedAuth="false" value="${homeCreditPayment.settleSubject.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.debtcredit2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit2}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit2_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit2_dict" isNeedAuth="false" value="${homeCreditPayment.settleSubject.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.amount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount2" name="SettleSubject.amount2" value="${homeCreditPayment.settleSubject.amount2}" <fisc:authentication sourceName="SettleSubject.amount2" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.standardamount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount2}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount2" name="SettleSubject.standardamount2" value="${homeCreditPayment.settleSubject.standardamount2}" <fisc:authentication sourceName="SettleSubject.standardamount2" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.settlesubject2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject2}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject2_sh" boName="SettleSubject" boProperty="settlesubject2" value="${homeCreditPayment.settleSubject.settlesubject2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.costcenter2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter2}：</td>
<td width="30%" >
<div id="div_SettleSubjectcostcenter2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter2_sh" boName="SettleSubject" boProperty="costcenter2" value="${homeCreditPayment.settleSubject.costcenter2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.depid2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid2}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid2_sh" boName="SettleSubject" boProperty="depid2" value="${homeCreditPayment.settleSubject.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.orderno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno2}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno2_sh" boName="SettleSubject" boProperty="orderno2" value="${homeCreditPayment.settleSubject.orderno2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.rowno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno2" name="SettleSubject.rowno2" value="${homeCreditPayment.settleSubject.rowno2}" <fisc:authentication sourceName="SettleSubject.rowno2" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.antiaccount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount2}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount2_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount2_dict" isNeedAuth="false" value="${homeCreditPayment.settleSubject.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.debtcredit3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit3}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit3_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit3_dict" isNeedAuth="false" value="${homeCreditPayment.settleSubject.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.amount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount3" name="SettleSubject.amount3" value="${homeCreditPayment.settleSubject.amount3}" <fisc:authentication sourceName="SettleSubject.amount3" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.standardamount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount3}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount3" name="SettleSubject.standardamount3" value="${homeCreditPayment.settleSubject.standardamount3}" <fisc:authentication sourceName="SettleSubject.standardamount3" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.settlesubject3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject3}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject3_sh" boName="SettleSubject" boProperty="settlesubject3" value="${homeCreditPayment.settleSubject.settlesubject3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.costcenter3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter3}：</td>
<td width="30%" >
<div id="div_SettleSubjectcostcenter3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter3_sh" boName="SettleSubject" boProperty="costcenter3" value="${homeCreditPayment.settleSubject.costcenter3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.depid3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid3}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid3_sh" boName="SettleSubject" boProperty="depid3" value="${homeCreditPayment.settleSubject.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.orderno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno3}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno3_sh" boName="SettleSubject" boProperty="orderno3" value="${homeCreditPayment.settleSubject.orderno3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.rowno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno3" name="SettleSubject.rowno3" value="${homeCreditPayment.settleSubject.rowno3}" <fisc:authentication sourceName="SettleSubject.rowno3" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.antiaccount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount3}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount3_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount3_dict" isNeedAuth="false" value="${homeCreditPayment.settleSubject.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.debtcredit4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit4}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit4_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit4" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit4_dict" isNeedAuth="false" value="${homeCreditPayment.settleSubject.debtcredit4}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.amount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount4}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount4" name="SettleSubject.amount4" value="${homeCreditPayment.settleSubject.amount4}" <fisc:authentication sourceName="SettleSubject.amount4" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.standardamount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount4}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount4" name="SettleSubject.standardamount4" value="${homeCreditPayment.settleSubject.standardamount4}" <fisc:authentication sourceName="SettleSubject.standardamount4" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.settlesubject4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject4}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject4_sh" boName="SettleSubject" boProperty="settlesubject4" value="${homeCreditPayment.settleSubject.settlesubject4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.profitcenter" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.profitcenter}：</td>
<td width="30%" >
<div id="div_SettleSubjectprofitcenter_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectprofitcenter_sh" boName="SettleSubject" boProperty="profitcenter" value="${homeCreditPayment.settleSubject.profitcenter}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.depid4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid4}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid4_sh" boName="SettleSubject" boProperty="depid4" value="${homeCreditPayment.settleSubject.depid4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.orderno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno4}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno4_sh" boName="SettleSubject" boProperty="orderno4" value="${homeCreditPayment.settleSubject.orderno4}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.rowno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno4}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno4" name="SettleSubject.rowno4" value="${homeCreditPayment.settleSubject.rowno4}" <fisc:authentication sourceName="SettleSubject.rowno4" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.antiaccount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount4}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount4_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount4" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount4_dict" isNeedAuth="false" value="${homeCreditPayment.settleSubject.antiaccount4}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
</table>
<input type="hidden" id="SettleSubject.settlesubjectid" name="SettleSubject.settlesubjectid" value="${homeCreditPayment.settleSubject.settlesubjectid}">
<input type="hidden" id="SettleSubject.flag" name="SettleSubject.flag" value="${homeCreditPayment.settleSubject.flag}">
</form>
</div>
<div id="div_fundFlow" class="x-hide-display" >
<form id="fundFlowForm" name="fundFlowForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.debtcredit1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit1}： </td>
<td width="30%">
<div id="div_FundFlowdebtcredit1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit1_dict" isNeedAuth="false" value="${homePayment.fundFlow.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.amount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlow.amount1" name="FundFlow.amount1" value="${homePayment.fundFlow.amount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.standardamount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount1}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlow.standardamount1" name="FundFlow.standardamount1" value="${homePayment.fundFlow.standardamount1}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.specialaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount1}： </td>
<td width="40%">
<div id="div_FundFlowspecialaccount1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount1" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount1_dict" isNeedAuth="false" value="${homePayment.fundFlow.specialaccount1}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.customer1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer1}： </td>
<td width="30%">
<div id="div_FundFlowcustomer1_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer1_sh" boName="FundFlow" boProperty="customer1" value="${homePayment.fundFlow.customer1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.depid1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid1}： </td>
<td width="40%">
<div id="div_FundFlowdepid1_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid1_sh" boName="FundFlow" boProperty="depid1" value="${homePayment.fundFlow.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.antiaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount1}： </td>
<td width="30%">
<div id="div_FundFlowantiaccount1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount1_dict" isNeedAuth="false" value="${homePayment.fundFlow.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.debtcredit2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit2}： </td>
<td width="30%">
<div id="div_FundFlowdebtcredit2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit2_dict" isNeedAuth="false" value="${homePayment.fundFlow.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.amount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlow.amount2" name="FundFlow.amount2" value="${homePayment.fundFlow.amount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.standardamount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount2}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlow.standardamount2" name="FundFlow.standardamount2" value="${homePayment.fundFlow.standardamount2}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.specialaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount2}： </td>
<td width="40%">
<div id="div_FundFlowspecialaccount2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount2" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount2_dict" isNeedAuth="false" value="${homePayment.fundFlow.specialaccount2}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.customer2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer2}： </td>
<td width="30%">
<div id="div_FundFlowcustomer2_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer2_sh" boName="FundFlow" boProperty="customer2" value="${homePayment.fundFlow.customer2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.depid2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid2}： </td>
<td width="40%">
<div id="div_FundFlowdepid2_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid2_sh" boName="FundFlow" boProperty="depid2" value="${homePayment.fundFlow.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.antiaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount2}： </td>
<td width="30%">
<div id="div_FundFlowantiaccount2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount2_dict" isNeedAuth="false" value="${homePayment.fundFlow.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.debtcredit3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit3}： </td>
<td width="30%">
<div id="div_FundFlowdebtcredit3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit3_dict" isNeedAuth="false" value="${homePayment.fundFlow.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.amount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlow.amount3" name="FundFlow.amount3" value="${homePayment.fundFlow.amount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.standardamount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount3}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlow.standardamount3" name="FundFlow.standardamount3" value="${homePayment.fundFlow.standardamount3}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.specialaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount3}： </td>
<td width="40%">
<div id="div_FundFlowspecialaccount3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount3" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount3_dict" isNeedAuth="false" value="${homePayment.fundFlow.specialaccount3}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.customer3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer3}： </td>
<td width="30%">
<div id="div_FundFlowcustomer3_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer3_sh" boName="FundFlow" boProperty="customer3" value="${homePayment.fundFlow.customer3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.depid3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid3}： </td>
<td width="40%">
<div id="div_FundFlowdepid3_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid3_sh" boName="FundFlow" boProperty="depid3" value="${homePayment.fundFlow.depid3}"></fisc:searchHelp>
</td>
</tr>
</table>
<input type="hidden" id="FundFlow.fundflowid" name="FundFlow.fundflowid" value="${homePayment.fundFlow.fundflowid}">
<input type="hidden" id="FundFlow.billclearid" name="FundFlow.billclearid" value="${homePayment.fundFlow.billclearid}">
<input type="hidden" id="FundFlow.collectid" name="FundFlow.collectid" value="${homePayment.fundFlow.collectid}">
<input type="hidden" id="FundFlow.paymentid" name="FundFlow.paymentid" value="${homePayment.paymentid}">
<input type="hidden" id="FundFlow.suppliersclearid" name="FundFlow.suppliersclearid" value="${homePayment.fundFlow.suppliersclearid}">
<input type="hidden" id="FundFlow.customsclearid" name="FundFlow.customsclearid" value="${homePayment.fundFlow.customsclearid}">
</form>
</div>
<div id="div_attachementAttachement"></div>
<div id="div_homeCreditRebank"></div>
<div id="div_homeCreditRelatPay"></div>

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/payment/homeCreditPaymentView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/payment/homeCreditPaymentViewGen.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${homeCreditPayment.processstate}';
//当前对象主键ID
var paymentid = '${homeCreditPayment.paymentid}';	

//页面文本
var Txt={
	//国内信用证
	homeCreditPayment:'${vt.boText}',
	          
	//国内证付款行项目
	homeCreditPayItem:'${homeCreditPayItemVt.boText}',
	// 请选择需要进行【国内证付款行项目批量删除】操作的记录！
	homeCreditPayItem_Deletes_SelectToOperation:'${homeCreditPayItemVt.deletes_SelectToOperate}',
	// 您选择了【国内证付款行项目批量删除】操作，是否确定继续该操作？
	homeCreditPayItem_Deletes_ConfirmOperation:'${homeCreditPayItemVt.deletes_ConfirmOperation}',
          
	//发票清帐
	homeCreditPayCbill:'${homeCreditPayCbillVt.boText}',
          
	//付款银行
	homeCreditBankItem:'${homeCreditBankItemVt.boText}',
          
	//押汇银行
	homeCreditDocuBank:'${homeCreditDocuBankVt.boText}',
	// 请选择需要进行【押汇银行批量删除】操作的记录！
	homeCreditDocuBank_Deletes_SelectToOperation:'${homeCreditDocuBankVt.deletes_SelectToOperate}',
	// 您选择了【押汇银行批量删除】操作，是否确定继续该操作？
	homeCreditDocuBank_Deletes_ConfirmOperation:'${homeCreditDocuBankVt.deletes_ConfirmOperation}',
          
	//结算科目
	settleSubject:'${settleSubjectVt.boText}',
          
	//纯资金往来
	fundFlow:'${fundFlowVt.boText}',
	//boText创建
	fundFlow_Create:'${fundFlowVt.boTextCreate}',
	//boText复制创建
	fundFlow_CopyCreate:'${fundFlowVt.boTextCopyCreate}',
	// 进行【纯资金往来复制创建】操作时，只允许选择一条记录！
	fundFlow_CopyCreate_AllowOnlyOneItemForOperation:'${fundFlowVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【纯资金往来复制创建】操作的记录！
	fundFlow_CopyCreate_SelectToOperation:'${fundFlowVt.copyCreate_SelectToOperate}',
	// 请选择需要进行【纯资金往来批量删除】操作的记录！
	fundFlow_Deletes_SelectToOperation:'${fundFlowVt.deletes_SelectToOperate}',
	// 您选择了【纯资金往来批量删除】操作，是否确定继续该操作？
	fundFlow_Deletes_ConfirmOperation:'${fundFlowVt.deletes_ConfirmOperation}',
          
          
	//国内证还押汇银行
	homeCreditRebank:'${homeCreditRebankVt.boText}',
          
	//相关单据
	homeCreditRelatPay:'${homeCreditRelatPayVt.boText}',
	//boText复制创建
	homeCreditPayment_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	homeCreditPayment_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	homeCreditPayment_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
					            layout:'fit',
					            border:false,
					            contentEl:'div_top_north'
					          },
				              {
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		border:false,
				            		//height:835,
				            		autoScroll: true,
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
						            		title:'${homeCreditPayItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_homeCreditPayItem'
						            	},
          						                {
						            		title:'${homeCreditPayCbillVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_homeCreditPayCbill'
						            	},
          						                {
						            		title:'${homeCreditBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_homeCreditBankItem'
						            	},
          						                {
						            		title:'${homeCreditDocuBankVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_homeCreditDocuBank'
						            	},
          						                {
						            		title:'${settleSubjectVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_settleSubject'
						            	},
          						                {
						            		title:'${fundFlowVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_fundFlow'
						            	},
          										{
						            		title:'附件',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'attachementattachementTab',
						            		contentEl:'div_attachementAttachement'
						            	},
          						                {
						            		title:'${homeCreditRebankVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_homeCreditRebank'
						            	},
          						                {
						            		title:'${homeCreditRelatPayVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_homeCreditRelatPay'
						            	}
						    ]}
						   ]}
						,{
							id:'historyWorkflowTask',
							region:'south',
							border:false,
							title:'${vt.processTrackInfo}',
		            		layout:'anchor',
			            	collapsible: true,
			            	collapsed:true,
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
'->','-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelHomeCreditPayment,iconCls:'icon-undo'},'-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessHomeCreditPayment,iconCls:'task'},'-',
<%}%>
{id:'_simulate',text:'模拟凭证',handler:_simulateHomeCreditPayment,iconCls:'icon-add'},'-',
{id:'_viewCredit',text:'查看信用额度',handler:_viewCreditHomeCreditPayment,iconCls:'icon-view'},'-',
{id:'_bookofaccount',text:'现金日记帐',handler:_bookofaccountHomeCreditPayment,iconCls:'icon-edit'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	var isTask = '${workflowTaskId}';
	if(!isTask||isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited&&isSubmited!=''&&isSubmited!=' '){
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
    var tabsSize = 9;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 9 ; i++){
		   tabs.setActiveTab(9-1-i);
		}
	}
 });
</script>