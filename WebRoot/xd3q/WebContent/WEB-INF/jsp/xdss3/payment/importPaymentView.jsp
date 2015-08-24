<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年08月23日 02点16分36秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象进口付款(ImportPayment)查看页面
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
<fisc:grid divId="div_importPaymentItem" boName="ImportPaymentItem" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YPAYMENTITEM.PAYMENTID='${importPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true"  pageSize="10000"></fisc:grid>
<fisc:grid divId="div_importPaymentCbill" boName="ImportPaymentCbill" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YPAYMENTCBILL.PAYMENTID='${importPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true"  pageSize="10000" ></fisc:grid>
<fisc:grid divId="div_importPayBankItem" boName="ImportPayBankItem" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YPAYBANKITEM.PAYMENTID='${importPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_importDocuBankItem" boName="ImportDocuBankItem" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YDOUCARYBANKITEM.PAYMENTID='${importPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_billPayReBankItem" boName="BillPayReBankItem" needToolbar="false" needCheckBox="false" editable="false" defaultCondition=" YBILLPAYMENTBANK.PAYMENTID='${importPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" pageSize="10000"></fisc:grid>
<fisc:grid divId="div_importRelatPayment" boName="ImportRelatPayment" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YPAYMENTRELATED.PAYMENTID='${importPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:attachement businessId="${businessId}" allowDelete="false" allowUpload="false" divId="div_attachement" boName="ImportPayment" boProperty="attachement" gridPageSize="10" gridHeight="285" ></fisc:attachement>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${importPayment.paymentid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.supplier" nodeId="${workflowNodeDefId}"/> >${vt.property.supplier}：</td>
		<td width="30%">
			<div id="div_supplier_sh"></div>
			<fisc:searchHelp divId="div_supplier_sh" boName="ImportPayment" boProperty="supplier"  value="${importPayment.supplier}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.paymenttype" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.paymenttype}：</td>
		<td  width="40%">
			<div id="div_paymenttype_dict"></div>
			<fisc:dictionary boName="ImportPayment" boProperty="paymenttype" dictionaryName="YDPAYTRADETYPE" divId="div_paymenttype_dict" isNeedAuth="false"  value="${importPayment.paymenttype}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.dept_id" nodeId="${workflowNodeDefId}"/> >${vt.property.dept_id}：</td>
		<td width="30%">
			<div id="div_dept_id_sh"></div>
			<fisc:searchHelp divId="div_dept_id_sh" boName="ImportPayment" boProperty="dept_id"  value="${importPayment.dept_id}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.pay_type" nodeId="${workflowNodeDefId}"/> >${vt.property.pay_type}：</td>
		<td  width="40%">
			<div id="div_pay_type_dict"></div>
			<fisc:dictionary boName="ImportPayment" boProperty="pay_type" dictionaryName="YDPAYTYPE" divId="div_pay_type_dict" isNeedAuth="false"  value="${importPayment.pay_type}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="ImportPayment.paymentno" nodeId="${workflowNodeDefId}"/> >${vt.property.paymentno}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="ImportPayment.paymentno" name="ImportPayment.paymentno" value="${importPayment.paymentno}" <fisc:authentication sourceName="ImportPayment.paymentno" nodeId="${workflowNodeDefId}"/>  readonly="readonly">
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="ImportPayment.isrepresentpay" nodeId="${workflowNodeDefId}"/> >${vt.property.isrepresentpay}：</td>
		<td   width="40%" >
			<div id="div_isrepresentpay_dict"></div>
			<fisc:dictionary boName="ImportPayment" boProperty="isrepresentpay" dictionaryName="YDPAYMENTYESORNO" divId="div_isrepresentpay_dict" isNeedAuth="false"  value="${importPayment.isrepresentpay}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.representpaycust" nodeId="${workflowNodeDefId}"/> >${vt.property.representpaycust}：</td>
		<td width="30%">
			<div id="div_representpaycust_sh"></div>
			<fisc:searchHelp divId="div_representpaycust_sh" boName="ImportPayment" boProperty="representpaycust"  value="${importPayment.representpaycust}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.isoverrepay" nodeId="${workflowNodeDefId}"/> >${vt.property.isoverrepay}：</td>
		<td  width="40%">
			<div id="div_isoverrepay_dict"></div>
			<fisc:dictionary boName="ImportPayment" boProperty="isoverrepay" dictionaryName="YDPAYMENTYESORNO" divId="div_isoverrepay_dict" isNeedAuth="false"  value="${importPayment.isoverrepay}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.applyamount" nodeId="${workflowNodeDefId}"/> >${vt.property.applyamount}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.applyamount" name="ImportPayment.applyamount" value="${importPayment.applyamount}" <fisc:authentication sourceName="ImportPayment.applyamount" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.currency" nodeId="${workflowNodeDefId}"/> >${vt.property.currency}：</td>
		<td  width="40%">
			<div id="div_currency_sh"></div>
			<fisc:searchHelp divId="div_currency_sh" boName="ImportPayment" boProperty="currency"  value="${importPayment.currency}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.closeexchangerat" nodeId="${workflowNodeDefId}"/> >${vt.property.closeexchangerat}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.closeexchangerat" name="ImportPayment.closeexchangerat" value="${importPayment.closeexchangerat}" <fisc:authentication sourceName="ImportPayment.closeexchangerat" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.collectbankid" nodeId="${workflowNodeDefId}"/> >${vt.property.collectbankid}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="ImportPayment.collectbankid" name="ImportPayment.collectbankid" value="${importPayment.collectbankid}" <fisc:authentication sourceName="ImportPayment.collectbankid" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.collectbankacc" nodeId="${workflowNodeDefId}"/> >${vt.property.collectbankacc}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.collectbankacc" name="ImportPayment.collectbankacc" value="${importPayment.collectbankacc}" <fisc:authentication sourceName="ImportPayment.collectbankacc" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.writelistno" nodeId="${workflowNodeDefId}"/> >${vt.property.writelistno}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="ImportPayment.writelistno" name="ImportPayment.writelistno" value="${importPayment.writelistno}" <fisc:authentication sourceName="ImportPayment.writelistno" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.arrivegoodsdate" nodeId="${workflowNodeDefId}"/> >${vt.property.arrivegoodsdate}：</td>
		<td width="30%">
			<input type="text" id="ImportPayment.arrivegoodsdate" name="ImportPayment.arrivegoodsdate" value="">
				<fisc:calendar applyTo="ImportPayment.arrivegoodsdate"  divId="" fieldName="" defaultValue="${importPayment.arrivegoodsdate}"></fisc:calendar>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.musttaketickleda" nodeId="${workflowNodeDefId}"/> >${vt.property.musttaketickleda}：</td>
		<td  width="40%">
			<input type="text" id="ImportPayment.musttaketickleda" name="ImportPayment.musttaketickleda" value="">
				<fisc:calendar applyTo="ImportPayment.musttaketickleda"  divId="" fieldName="" defaultValue="${importPayment.musttaketickleda}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.replacedate" nodeId="${workflowNodeDefId}"/> >${vt.property.replacedate}：</td>
		<td width="30%">
			<input type="text" id="ImportPayment.replacedate" name="ImportPayment.replacedate" value="">
				<fisc:calendar applyTo="ImportPayment.replacedate"  divId="" fieldName="" defaultValue="${importPayment.replacedate}"></fisc:calendar>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.paydate" nodeId="${workflowNodeDefId}"/> >${vt.property.paydate}：</td>
		<td  width="40%">
			<input type="text" id="ImportPayment.paydate" name="ImportPayment.paydate" value="">
				<fisc:calendar applyTo="ImportPayment.paydate"  divId="" fieldName="" defaultValue="${importPayment.paydate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="ImportPayment.ticketbankid" nodeId="${workflowNodeDefId}"/> >${vt.property.ticketbankid}：</td>
		<td  width="30%" >
			<div id="div_ticketbankid_sh"></div>
			<fisc:searchHelp divId="div_ticketbankid_sh" boName="ImportPayment" boProperty="ticketbankid" value="${importPayment.ticketbankid}"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="ImportPayment.billbc" nodeId="${workflowNodeDefId}"/> >${vt.property.billbc}：</td>
		<td  width="30%" >
			<div id="div_billbc_sh"></div>
			<fisc:searchHelp divId="div_billbc_sh" boName="ImportPayment" boProperty="billbc" value="${importPayment.billbc}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.payer" nodeId="${workflowNodeDefId}"/> >${vt.property.payer}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.payer" name="ImportPayment.payer" value="${importPayment.payer}" <fisc:authentication sourceName="ImportPayment.payer" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.currency2" nodeId="${workflowNodeDefId}"/> >${vt.property.currency2}：</td>
		<td  width="40%">
			<div id="div_currency2_sh"></div>
			<fisc:searchHelp divId="div_currency2_sh" boName="ImportPayment" boProperty="currency2"  value="${importPayment.currency2}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.documentarylimit" nodeId="${workflowNodeDefId}"/> >${vt.property.documentarylimit}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.documentarylimit" name="ImportPayment.documentarylimit" value="${importPayment.documentarylimit}" <fisc:authentication sourceName="ImportPayment.documentarylimit" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.documentarydate" nodeId="${workflowNodeDefId}"/> >${vt.property.documentarydate}：</td>
		<td  width="40%">
			<input type="text" id="ImportPayment.documentarydate" name="ImportPayment.documentarydate" value="">
				<fisc:calendar applyTo="ImportPayment.documentarydate"  divId="" fieldName="" defaultValue="${importPayment.documentarydate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.documentaryrate" nodeId="${workflowNodeDefId}"/> >${vt.property.documentaryrate}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.documentaryrate" name="ImportPayment.documentaryrate" value="${importPayment.documentaryrate}" <fisc:authentication sourceName="ImportPayment.documentaryrate" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<!-- 邱杰烜  2010-09-08  新增"押汇利率"输入框 -->
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.doctaryinterest" nodeId="${workflowNodeDefId}"/> >${vt.property.doctaryinterest}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.doctaryinterest" name="ImportPayment.doctaryinterest" value="${importPayment.doctaryinterest}"   <fisc:authentication sourceName="ImportPayment.doctaryinterest" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.documentarypaydt" nodeId="${workflowNodeDefId}"/> >${vt.property.documentarypaydt}：</td>
		<td  width="40%">
			<input type="text" id="ImportPayment.documentarypaydt" name="ImportPayment.documentarypaydt" value="">
				<fisc:calendar applyTo="ImportPayment.documentarypaydt"  divId="" fieldName="" defaultValue="${importPayment.documentarypaydt}"></fisc:calendar>
		</td>
		
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.redocarybc" nodeId="${workflowNodeDefId}"/> >${vt.property.redocarybc}：</td>
		<td width="30%">
			<div id="div_redocarybc_sh"></div>
			<fisc:searchHelp divId="div_redocarybc_sh" boName="ImportPayment" boProperty="redocarybc"  value="${importPayment.redocarybc}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		 <td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.payrealdate" nodeId="${workflowNodeDefId}"/> >${vt.property.payrealdate}：</td>
		<td  width="40%">
			<input type="text" id="ImportPayment.payrealdate" name="ImportPayment.payrealdate" value="">
				<fisc:calendar applyTo="ImportPayment.payrealdate"  divId="" fieldName="" defaultValue="${importPayment.payrealdate}"></fisc:calendar>
		</td>
		
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.redocaryamount" nodeId="${workflowNodeDefId}"/> >${vt.property.redocaryamount}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.redocaryamount" name="ImportPayment.redocaryamount" value="${importPayment.redocaryamount}" <fisc:authentication sourceName="ImportPayment.redocaryamount" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		 <td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.redocaryrate" nodeId="${workflowNodeDefId}"/> >${vt.property.redocaryrate}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="ImportPayment.redocaryrate" name="ImportPayment.redocaryrate" value="${importPayment.redocaryrate}" <fisc:authentication sourceName="ImportPayment.redocaryrate" nodeId="${workflowNodeDefId}"/>  >
		</td>
		
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td width="30%">
		    <input type="text" class="inputText" id="ImportPayment.createtime" name="ImportPayment.createtime" value="${importPayment.createtime}"  readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td  width="40%">
			<fisc:user boProperty="creator" boName="ImportPayment" userId="${importPayment.creator}"></fisc:user>
		</td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.text" nodeId="${workflowNodeDefId}"/> valign="top"><font color="red">★</font>${vt.property.text}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="ImportPayment.text" name="ImportPayment.text" <fisc:authentication sourceName="ImportPayment.text" nodeId="${workflowNodeDefId}"/>>${importPayment.text}</textarea>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.remark" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.remark}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="ImportPayment.remark" name="ImportPayment.remark" <fisc:authentication sourceName="ImportPayment.remark" nodeId="${workflowNodeDefId}"/>>${importPayment.remark}</textarea>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.factamount" nodeId="${workflowNodeDefId}"/> >${vt.property.factamount}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.factamount" name="ImportPayment.factamount" value="${importPayment.factamount}" <fisc:authentication sourceName="ImportPayment.factamount" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.factcurrency" nodeId="${workflowNodeDefId}"/> >${vt.property.factcurrency}：</td>
		<td  width="40%">
			<div id="div_factcurrency_sh"></div>
			<fisc:searchHelp divId="div_factcurrency_sh" boName="ImportPayment" boProperty="factcurrency"  value="${importPayment.factcurrency}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.exchangerate" nodeId="${workflowNodeDefId}"/> >${vt.property.exchangerate}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.exchangerate" name="ImportPayment.exchangerate" value="${importPayment.exchangerate}" <fisc:authentication sourceName="ImportPayment.exchangerate" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.voucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherdate}：</td>
		<td  width="40%">
			<input type="text" id="ImportPayment.voucherdate" name="ImportPayment.voucherdate" value="">
				<fisc:calendar applyTo="ImportPayment.voucherdate"  divId="" fieldName="" defaultValue="${importPayment.voucherdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="ImportPayment.accountdate" nodeId="${workflowNodeDefId}"/> >${vt.property.accountdate}：</td>
		<td width="30%">
			<input type="text" id="ImportPayment.accountdate" name="ImportPayment.accountdate" value="">
				<fisc:calendar applyTo="ImportPayment.accountdate"  divId="" fieldName="" defaultValue="${importPayment.accountdate}"></fisc:calendar>
		</td>
        <td align="right"  width="15%" <fisc:authentication sourceName="ImportPayment.redocaryamount2" nodeId="${workflowNodeDefId}"/> >累计还押汇金额：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="ImportPayment.redocaryamount2" name="ImportPayment.redocaryamount2" readonly="true" value="${importPayment.redocaryamount2}" <fisc:authentication sourceName="ImportPayment.redocaryamount2" nodeId="${workflowNodeDefId}"/> >
		</td>
	</tr>

	<input type="hidden" id="ImportPayment.client" name="ImportPayment.client" value="${importPayment.client}">
	<input type="hidden" id="ImportPayment.paymentid" name="ImportPayment.paymentid" value="${importPayment.paymentid}">
	<input type="hidden" id="ImportPayment.paymentstate" name="ImportPayment.paymentstate" value="${importPayment.paymentstate}">
	<input type="hidden" id="ImportPayment.businessstate" name="ImportPayment.businessstate" value="${importPayment.businessstate}">
	<input type="hidden" id="ImportPayment.processstate" name="ImportPayment.processstate" value="${importPayment.processstate}">
	<input type="hidden" id="ImportPayment.collectbankname" name="ImportPayment.collectbankname" value="${importPayment.collectbankname}">
	<input type="hidden" id="ImportPayment.collectbanksubje" name="ImportPayment.collectbanksubje" value="${importPayment.collectbanksubje}">
	<input type="hidden" id="ImportPayment.finumber" name="ImportPayment.finumber" value="${importPayment.finumber}">
	
	<input type="hidden" id="ImportPayment.ticketbankname" name="ImportPayment.ticketbankname" value="${importPayment.ticketbankname}">
	<input type="hidden" id="ImportPayment.ticketbanksubjec" name="ImportPayment.ticketbanksubjec" value="${importPayment.ticketbanksubjec}">
	<input type="hidden" id="ImportPayment.draft" name="ImportPayment.draft" value="${importPayment.draft}">
	<input type="hidden" id="ImportPayment.draftdate" name="ImportPayment.draftdate" value="${importPayment.draftdate}">
	<input type="hidden" id="ImportPayment.repaymentid" name="ImportPayment.repaymentid" value="${importPayment.repaymentid}">
	<input type="hidden" id="ImportPayment.expiredate" name="ImportPayment.expiredate" value="${importPayment.expiredate}">

	<input type="hidden" id="ImportPayment.convertamount" name="ImportPayment.convertamount" value="${importPayment.convertamount}">
	<input type="hidden" id="ImportPayment.payrealamount" name="ImportPayment.payrealamount" value="${importPayment.payrealamount}">
	<input type="hidden" id="ImportPayment.doctaryreallimit" name="ImportPayment.doctaryreallimit" value="${importPayment.doctaryreallimit}">
	<input type="hidden" id="ImportPayment.doctaryrealrate" name="ImportPayment.doctaryrealrate" value="${importPayment.doctaryrealrate}">
	<input type="hidden" id="ImportPayment.trade_type" name="ImportPayment.trade_type" value="${importPayment.trade_type}">
	<input type="hidden" id="ImportPayment.factexchangerate" name="ImportPayment.factexchangerate" value="${importPayment.factexchangerate}">
	<input type="hidden" id="ImportPayment.lastmodifyer" name="ImportPayment.lastmodifyer" value="${importPayment.lastmodifyer}">
	<input type="hidden" id="ImportPayment.lastmodifytime" name="ImportPayment.lastmodifytime" value="${importPayment.lastmodifytime}">
	
	<input type="hidden" id="ImportPayment.istradesubsist" name="ImportPayment.istradesubsist" value="${importPayment.istradesubsist}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_importPaymentItem"></div>
<div id="div_importPaymentCbill"></div>
<div id="div_importPayBankItem"></div>
<div id="div_importDocuBankItem"></div>
<div id="div_billPayReBankItem" ></div>
<div id="div_importSettSubj" class="x-hide-display">
<form id="importSettSubjForm" name="importSettSubjForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.debtcredit1" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.debtcredit1}：</td>
<td width="30%" >
<div id="div_ImportSettSubjdebtcredit1_dict"></div>
<fisc:dictionary boName="ImportSettSubj" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_ImportSettSubjdebtcredit1_dict" isNeedAuth="false" value="${importPayment.importSettSubj.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.amount1" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.amount1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="ImportSettSubj.amount1" name="ImportSettSubj.amount1" value="${importPayment.importSettSubj.amount1}" <fisc:authentication sourceName="ImportSettSubj.amount1" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.standardamount1" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.standardamount1}：</td>
<td width="30%" >
<input type="text" class="inputText" id="ImportSettSubj.standardamount1" name="ImportSettSubj.standardamount1" value="${importPayment.importSettSubj.standardamount1}" <fisc:authentication sourceName="ImportSettSubj.standardamount1" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.settlesubject1" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.settlesubject1}：</td>
<td width="40%" >
<div id="div_ImportSettSubjsettlesubject1_sh"></div>
<fisc:searchHelp divId="div_ImportSettSubjsettlesubject1_sh" boName="ImportSettSubj" boProperty="settlesubject1" value="${importPayment.importSettSubj.settlesubject1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.costcenter1" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.costcenter1}：</td>
<td width="30%" >
<div id="div_ImportSettSubjcostcenter1_sh"></div>
<fisc:searchHelp divId="div_ImportSettSubjcostcenter1_sh" boName="ImportSettSubj" boProperty="costcenter1" value="${importPayment.importSettSubj.costcenter1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.depid1" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.depid1}：</td>
<td width="40%" >
<div id="div_ImportSettSubjdepid1_sh"></div>
<fisc:searchHelp divId="div_ImportSettSubjdepid1_sh" boName="ImportSettSubj" boProperty="depid1" value="${importPayment.importSettSubj.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.orderno1" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.orderno1}： </td>
<td width="30%">
<input type="text" class="inputText" id="ImportSettSubj.orderno1" name="ImportSettSubj.orderno1" value="${importPayment.importSettSubj.orderno1}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.rowno1" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.rowno1}： </td>
<td width="40%">
<input type="text" class="inputText" id="ImportSettSubj.rowno1" name="ImportSettSubj.rowno1" value="${importPayment.importSettSubj.rowno1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.antiaccount1" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.antiaccount1}：</td>
<td width="30%" >
<div id="div_ImportSettSubjantiaccount1_dict"></div>
<fisc:dictionary boName="ImportSettSubj" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_ImportSettSubjantiaccount1_dict" isNeedAuth="false" value="${importPayment.importSettSubj.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.debtcredit2" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.debtcredit2}：</td>
<td width="30%" >
<div id="div_ImportSettSubjdebtcredit2_dict"></div>
<fisc:dictionary boName="ImportSettSubj" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_ImportSettSubjdebtcredit2_dict" isNeedAuth="false" value="${importPayment.importSettSubj.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.amount2" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.amount2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="ImportSettSubj.amount2" name="ImportSettSubj.amount2" value="${importPayment.importSettSubj.amount2}" <fisc:authentication sourceName="ImportSettSubj.amount2" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.standardamount2" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.standardamount2}：</td>
<td width="30%" >
<input type="text" class="inputText" id="ImportSettSubj.standardamount2" name="ImportSettSubj.standardamount2" value="${importPayment.importSettSubj.standardamount2}" <fisc:authentication sourceName="ImportSettSubj.standardamount2" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.settlesubject2" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.settlesubject2}：</td>
<td width="40%" >
<div id="div_ImportSettSubjsettlesubject2_sh"></div>
<fisc:searchHelp divId="div_ImportSettSubjsettlesubject2_sh" boName="ImportSettSubj" boProperty="settlesubject2" value="${importPayment.importSettSubj.settlesubject2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.costcenter2" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.costcenter2}：</td>
<td width="30%" >
<div id="div_ImportSettSubjcostcenter2_sh"></div>
<fisc:searchHelp divId="div_ImportSettSubjcostcenter2_sh" boName="ImportSettSubj" boProperty="costcenter2" value="${importPayment.importSettSubj.costcenter2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.depid2" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.depid2}：</td>
<td width="40%" >
<div id="div_ImportSettSubjdepid2_sh"></div>
<fisc:searchHelp divId="div_ImportSettSubjdepid2_sh" boName="ImportSettSubj" boProperty="depid2" value="${importPayment.importSettSubj.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.orderno2" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.orderno2}： </td>
<td width="30%">
<input type="text" class="inputText" id="ImportSettSubj.orderno2" name="ImportSettSubj.orderno2" value="${importPayment.importSettSubj.orderno2}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.rowno2" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.rowno2}： </td>
<td width="40%">
<input type="text" class="inputText" id="ImportSettSubj.rowno2" name="ImportSettSubj.rowno2" value="${importPayment.importSettSubj.rowno2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.antiaccount2" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.antiaccount2}：</td>
<td width="30%" >
<div id="div_ImportSettSubjantiaccount2_dict"></div>
<fisc:dictionary boName="ImportSettSubj" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_ImportSettSubjantiaccount2_dict" isNeedAuth="false" value="${importPayment.importSettSubj.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.debtcredit3" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.debtcredit3}：</td>
<td width="30%" >
<div id="div_ImportSettSubjdebtcredit3_dict"></div>
<fisc:dictionary boName="ImportSettSubj" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_ImportSettSubjdebtcredit3_dict" isNeedAuth="false" value="${importPayment.importSettSubj.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.amount3" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.amount3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="ImportSettSubj.amount3" name="ImportSettSubj.amount3" value="${importPayment.importSettSubj.amount3}" <fisc:authentication sourceName="ImportSettSubj.amount3" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.standardamount3" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.standardamount3}：</td>
<td width="30%" >
<input type="text" class="inputText" id="ImportSettSubj.standardamount3" name="ImportSettSubj.standardamount3" value="${importPayment.importSettSubj.standardamount3}" <fisc:authentication sourceName="ImportSettSubj.standardamount3" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.settlesubject3" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.settlesubject3}：</td>
<td width="40%" >
<div id="div_ImportSettSubjsettlesubject3_sh"></div>
<fisc:searchHelp divId="div_ImportSettSubjsettlesubject3_sh" boName="ImportSettSubj" boProperty="settlesubject3" value="${importPayment.importSettSubj.settlesubject3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.costcenter3" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.costcenter3}：</td>
<td width="30%" >
<div id="div_ImportSettSubjcostcenter3_sh"></div>
<fisc:searchHelp divId="div_ImportSettSubjcostcenter3_sh" boName="ImportSettSubj" boProperty="costcenter3" value="${importPayment.importSettSubj.costcenter3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.depid3" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.depid3}：</td>
<td width="40%" >
<div id="div_ImportSettSubjdepid3_sh"></div>
<fisc:searchHelp divId="div_ImportSettSubjdepid3_sh" boName="ImportSettSubj" boProperty="depid3" value="${importPayment.importSettSubj.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.orderno3" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.orderno3}： </td>
<td width="30%">
<input type="text" class="inputText" id="ImportSettSubj.orderno3" name="ImportSettSubj.orderno3" value="${importPayment.importSettSubj.orderno3}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.rowno3" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.rowno3}： </td>
<td width="40%">
<input type="text" class="inputText" id="ImportSettSubj.rowno3" name="ImportSettSubj.rowno3" value="${importPayment.importSettSubj.rowno3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.antiaccount3" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.antiaccount3}：</td>
<td width="30%" >
<div id="div_ImportSettSubjantiaccount3_dict"></div>
<fisc:dictionary boName="ImportSettSubj" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_ImportSettSubjantiaccount3_dict" isNeedAuth="false" value="${importPayment.importSettSubj.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.debtcredit4" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.debtcredit4}：</td>
<td width="30%" >
<div id="div_ImportSettSubjdebtcredit4_dict"></div>
<fisc:dictionary boName="ImportSettSubj" boProperty="debtcredit4" dictionaryName="YDDEBTCREDIT" divId="div_ImportSettSubjdebtcredit4_dict" isNeedAuth="false" value="${importPayment.importSettSubj.debtcredit4}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.amount4" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.amount4}：</td>
<td width="40%" >
<input type="text" class="inputText" id="ImportSettSubj.amount4" name="ImportSettSubj.amount4" value="${importPayment.importSettSubj.amount4}" <fisc:authentication sourceName="ImportSettSubj.amount4" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.standardamount4" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.standardamount4}：</td>
<td width="30%" >
<input type="text" class="inputText" id="ImportSettSubj.standardamount4" name="ImportSettSubj.standardamount4" value="${importPayment.importSettSubj.standardamount4}" <fisc:authentication sourceName="ImportSettSubj.standardamount4" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.settlesubject4" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.settlesubject4}：</td>
<td width="40%" >
<div id="div_ImportSettSubjsettlesubject4_sh"></div>
<fisc:searchHelp divId="div_ImportSettSubjsettlesubject4_sh" boName="ImportSettSubj" boProperty="settlesubject4" value="${importPayment.importSettSubj.settlesubject4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.profitcenter" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.profitcenter}：</td>
<td width="30%" >
<div id="div_ImportSettSubjprofitcenter_sh"></div>
<fisc:searchHelp divId="div_ImportSettSubjprofitcenter_sh" boName="ImportSettSubj" boProperty="profitcenter" value="${importPayment.importSettSubj.profitcenter}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.depid4" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.depid4}：</td>
<td width="40%" >
<div id="div_ImportSettSubjdepid4_sh"></div>
<fisc:searchHelp divId="div_ImportSettSubjdepid4_sh" boName="ImportSettSubj" boProperty="depid4" value="${importPayment.importSettSubj.depid4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.orderno4" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.orderno4}： </td>
<td width="30%">
<input type="text" class="inputText" id="ImportSettSubj.orderno4" name="ImportSettSubj.orderno4" value="${importPayment.importSettSubj.orderno4}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportSettSubj.rowno4" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.rowno4}： </td>
<td width="40%">
<input type="text" class="inputText" id="ImportSettSubj.rowno4" name="ImportSettSubj.rowno4" value="${importPayment.importSettSubj.rowno4}" >
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="ImportSettSubj.antiaccount4" nodeId="${workflowNodeDefId}"/> >${importSettSubjVt.property.antiaccount4}：</td>
<td width="30%" >
<div id="div_ImportSettSubjantiaccount4_dict"></div>
<fisc:dictionary boName="ImportSettSubj" boProperty="antiaccount4" dictionaryName="YDYESORNO" divId="div_ImportSettSubjantiaccount4_dict" isNeedAuth="false" value="${importPayment.importSettSubj.antiaccount4}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
</table>
<input type="hidden" id="ImportSettSubj.settlesubjectid" name="ImportSettSubj.settlesubjectid" value="${importPayment.importSettSubj.settlesubjectid}">
<input type="hidden" id="ImportSettSubj.billclearid" name="ImportSettSubj.billclearid" value="${importPayment.importSettSubj.billclearid}">
<input type="hidden" id="ImportSettSubj.paymentid" name="ImportSettSubj.paymentid" value="${importPayment.paymentid}">
<input type="hidden" id="ImportSettSubj.collectid" name="ImportSettSubj.collectid" value="${importPayment.importSettSubj.collectid}">
</form>
</div>
<div id="div_importFundFlow" class="x-hide-display">
<form id="importFundFlowForm" name="importFundFlowForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.debtcredit1" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.debtcredit1}：</td>
<td width="30%" >
<div id="div_ImportFundFlowdebtcredit1_dict"></div>
<fisc:dictionary boName="ImportFundFlow" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_ImportFundFlowdebtcredit1_dict" isNeedAuth="false" value="${importPayment.importFundFlow.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.amount1" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.amount1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="ImportFundFlow.amount1" name="ImportFundFlow.amount1" value="${importPayment.importFundFlow.amount1}" <fisc:authentication sourceName="ImportFundFlow.amount1" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.standardamount1" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.standardamount1}：</td>
<td width="30%" >
<input type="text" class="inputText" id="ImportFundFlow.standardamount1" name="ImportFundFlow.standardamount1" value="${importPayment.importFundFlow.standardamount1}" <fisc:authentication sourceName="ImportFundFlow.standardamount1" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.specialaccount1" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.specialaccount1}：</td>
<td width="40%" >
<div id="div_ImportFundFlowspecialaccount1_dict"></div>
<fisc:dictionary boName="ImportFundFlow" boProperty="specialaccount1" dictionaryName="YDUMSKZ" divId="div_ImportFundFlowspecialaccount1_dict" isNeedAuth="false" value="${importPayment.importFundFlow.specialaccount1}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.customer1" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.customer1}：</td>
<td width="30%" >
<div id="div_ImportFundFlowcustomer1_sh"></div>
<fisc:searchHelp divId="div_ImportFundFlowcustomer1_sh" boName="ImportFundFlow" boProperty="customer1" value="${importPayment.importFundFlow.customer1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.depid1" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.depid1}：</td>
<td width="40%" >
<div id="div_ImportFundFlowdepid1_sh"></div>
<fisc:searchHelp divId="div_ImportFundFlowdepid1_sh" boName="ImportFundFlow" boProperty="depid1" value="${importPayment.importFundFlow.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.antiaccount1" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.antiaccount1}：</td>
<td width="30%" >
<div id="div_ImportFundFlowantiaccount1_dict"></div>
<fisc:dictionary boName="ImportFundFlow" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_ImportFundFlowantiaccount1_dict" isNeedAuth="false" value="${importPayment.importFundFlow.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.debtcredit2" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.debtcredit2}：</td>
<td width="30%" >
<div id="div_ImportFundFlowdebtcredit2_dict"></div>
<fisc:dictionary boName="ImportFundFlow" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_ImportFundFlowdebtcredit2_dict" isNeedAuth="false" value="${importPayment.importFundFlow.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.amount2" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.amount2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="ImportFundFlow.amount2" name="ImportFundFlow.amount2" value="${importPayment.importFundFlow.amount2}" <fisc:authentication sourceName="ImportFundFlow.amount2" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.standardamount2" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.standardamount2}：</td>
<td width="30%" >
<input type="text" class="inputText" id="ImportFundFlow.standardamount2" name="ImportFundFlow.standardamount2" value="${importPayment.importFundFlow.standardamount2}" <fisc:authentication sourceName="ImportFundFlow.standardamount2" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.specialaccount2" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.specialaccount2}：</td>
<td width="40%" >
<div id="div_ImportFundFlowspecialaccount2_dict"></div>
<fisc:dictionary boName="ImportFundFlow" boProperty="specialaccount2" dictionaryName="YDUMSKZ" divId="div_ImportFundFlowspecialaccount2_dict" isNeedAuth="false" value="${importPayment.importFundFlow.specialaccount2}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.customer2" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.customer2}：</td>
<td width="30%" >
<div id="div_ImportFundFlowcustomer2_sh"></div>
<fisc:searchHelp divId="div_ImportFundFlowcustomer2_sh" boName="ImportFundFlow" boProperty="customer2" value="${importPayment.importFundFlow.customer2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.depid2" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.depid2}：</td>
<td width="40%" >
<div id="div_ImportFundFlowdepid2_sh"></div>
<fisc:searchHelp divId="div_ImportFundFlowdepid2_sh" boName="ImportFundFlow" boProperty="depid2" value="${importPayment.importFundFlow.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.antiaccount2" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.antiaccount2}：</td>
<td width="30%" >
<div id="div_ImportFundFlowantiaccount2_dict"></div>
<fisc:dictionary boName="ImportFundFlow" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_ImportFundFlowantiaccount2_dict" isNeedAuth="false" value="${importPayment.importFundFlow.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.debtcredit3" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.debtcredit3}：</td>
<td width="30%" >
<div id="div_ImportFundFlowdebtcredit3_dict"></div>
<fisc:dictionary boName="ImportFundFlow" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_ImportFundFlowdebtcredit3_dict" isNeedAuth="false" value="${importPayment.importFundFlow.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.amount3" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.amount3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="ImportFundFlow.amount3" name="ImportFundFlow.amount3" value="${importPayment.importFundFlow.amount3}" <fisc:authentication sourceName="ImportFundFlow.amount3" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.standardamount3" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.standardamount3}：</td>
<td width="30%" >
<input type="text" class="inputText" id="ImportFundFlow.standardamount3" name="ImportFundFlow.standardamount3" value="${importPayment.importFundFlow.standardamount3}" <fisc:authentication sourceName="ImportFundFlow.standardamount3" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.specialaccount3" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.specialaccount3}：</td>
<td width="40%" >
<div id="div_ImportFundFlowspecialaccount3_dict"></div>
<fisc:dictionary boName="ImportFundFlow" boProperty="specialaccount3" dictionaryName="YDUMSKZ" divId="div_ImportFundFlowspecialaccount3_dict" isNeedAuth="false" value="${importPayment.importFundFlow.specialaccount3}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.customer3" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.customer3}：</td>
<td width="30%" >
<div id="div_ImportFundFlowcustomer3_sh"></div>
<fisc:searchHelp divId="div_ImportFundFlowcustomer3_sh" boName="ImportFundFlow" boProperty="customer3" value="${importPayment.importFundFlow.customer3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="ImportFundFlow.depid3" nodeId="${workflowNodeDefId}"/> >${importFundFlowVt.property.depid3}：</td>
<td width="40%" >
<div id="div_ImportFundFlowdepid3_sh"></div>
<fisc:searchHelp divId="div_ImportFundFlowdepid3_sh" boName="ImportFundFlow" boProperty="depid3" value="${importPayment.importFundFlow.depid3}"></fisc:searchHelp>
</td>
</tr>
</table>
<input type="hidden" id="ImportFundFlow.fundflowid" name="ImportFundFlow.fundflowid" value="${importPayment.importFundFlow.fundflowid}">
<input type="hidden" id="ImportFundFlow.billclearid" name="ImportFundFlow.billclearid" value="${importPayment.importFundFlow.billclearid}">
<input type="hidden" id="ImportFundFlow.collectid" name="ImportFundFlow.collectid" value="${importPayment.importFundFlow.collectid}">
<input type="hidden" id="ImportFundFlow.paymentid" name="ImportFundFlow.paymentid" value="${importPayment.paymentid}">
<input type="hidden" id="ImportFundFlow.suppliersclearid" name="ImportFundFlow.suppliersclearid" value="${importPayment.importFundFlow.suppliersclearid}">
<input type="hidden" id="ImportFundFlow.customsclearid" name="ImportFundFlow.customsclearid" value="${importPayment.importFundFlow.customsclearid}">
</form>
</div>
<div id="div_importRelatPayment"></div>
<div id="div_attachement" ></div>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/xdss3/payment/importPaymentView.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/xdss3/payment/importPaymentViewGen.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${importPayment.processstate}';
//当前对象主键ID
var paymentid = '${importPayment.paymentid}';

var strRoleType = '${roletype}';
var isReassign = '${isReassign}';
//页面文本
var Txt={
	//进口付款
	importPayment:'${vt.boText}',
	          
	//付款金额分配
	importPaymentItem:'${importPaymentItemVt.boText}',
	// 请选择需要进行【付款金额分配批量删除】操作的记录！
	importPaymentItem_Deletes_SelectToOperation:'${importPaymentItemVt.deletes_SelectToOperate}',
	// 您选择了【付款金额分配批量删除】操作，是否确定继续该操作？
	importPaymentItem_Deletes_ConfirmOperation:'${importPaymentItemVt.deletes_ConfirmOperation}',
          
	//发票清帐
	importPaymentCbill:'${importPaymentCbillVt.boText}',
	// 请选择需要进行【发票清帐批量删除】操作的记录！
	importPaymentCbill_Deletes_SelectToOperation:'${importPaymentCbillVt.deletes_SelectToOperate}',
	// 您选择了【发票清帐批量删除】操作，是否确定继续该操作？
	importPaymentCbill_Deletes_ConfirmOperation:'${importPaymentCbillVt.deletes_ConfirmOperation}',
          
	//付款银行
	importPayBankItem:'${importPayBankItemVt.boText}',
	// 请选择需要进行【付款银行批量删除】操作的记录！
	importPayBankItem_Deletes_SelectToOperation:'${importPayBankItemVt.deletes_SelectToOperate}',
	// 您选择了【付款银行批量删除】操作，是否确定继续该操作？
	importPayBankItem_Deletes_ConfirmOperation:'${importPayBankItemVt.deletes_ConfirmOperation}',
          
	//押汇/海外代付银行
	importDocuBankItem:'${importDocuBankItemVt.boText}',
	// 请选择需要进行【押汇/海外代付银行批量删除】操作的记录！
	importDocuBankItem_Deletes_SelectToOperation:'${importDocuBankItemVt.deletes_SelectToOperate}',
	// 您选择了【押汇/海外代付银行批量删除】操作，是否确定继续该操作？
	importDocuBankItem_Deletes_ConfirmOperation:'${importDocuBankItemVt.deletes_ConfirmOperation}',
          
	//付款结算科目
	importSettSubj:'${importSettSubjVt.boText}',
          
	//付款纯资金
	importFundFlow:'${importFundFlowVt.boText}',
          
	//相关单据
	importRelatPayment:'${importRelatPaymentVt.boText}',
	//boText复制创建
	importPayment_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	importPayment_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	importPayment_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		title:'<c:if test="${importPayment.paymenttype=='15'||importPayment.paymenttype=='16'}">国内证信息</c:if><c:if test="${importPayment.paymenttype!='15'&&importPayment.paymenttype!='16'}">进口付款信息</c:if>',
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
					            	deferredRender : false,
									activeTab:0,
						           items:[
				          						                {
						            		title:'${importPaymentItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_importPaymentItem'
						            	},
          						                {
						            		title:'${importPaymentCbillVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_importPaymentCbill'
						            	},
          						                {
						            		title:'${importPayBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_importPayBankItem'
						            	},
          						                {
						            		title:'${importDocuBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_importDocuBankItem'
						            	},
						            	{
						            		title:'${billPayReBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'billPayReBankItemTab',
						            		contentEl:'div_billPayReBankItem'
						            	},
          						                {
						            		title:'${importSettSubjVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_importSettSubj'
						            	},
          						                {
						            		title:'${importFundFlowVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_importFundFlow'
						            	},
          						                {
						            		title:'${importRelatPaymentVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_importRelatPayment'
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
function _saveDate(){
	var param='?action=_saveDate&paymentid='+paymentid+'&musttaketickleda='+$('ImportPayment.musttaketickleda').value+'&arrivegoodsdate='+$('ImportPayment.arrivegoodsdate').value+'&replacedate='+$('ImportPayment.replacedate').value;
	new AjaxEngine(contextPath + '/xdss3/payment/importPaymentController.spr', 
			   {method:"post", parameters: param, onComplete: callBackHandle});
}

var toolbars = new Ext.Toolbar({
			items:[
					'-',
'-','-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessImportPayment,iconCls:'task'},'-',
<%}%>
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelImportPayment,iconCls:'icon-undo'},'-',
{id:'_autoassign',text:'自动分配',handler:_autoassignImportPayment,iconCls:'icon-add'},'-',
{id:'_clearassign',text:'清除分配',handler:_clearassignImportPayment,iconCls:'icon-delete'},'-',
{id:'_viewCredit',text:'查看信用额度',handler:_viewCreditImportPayment,iconCls:'icon-view'},'-',
{id:'_bookofaccount',text:'现金日记帐',handler:_bookofaccountImportPayment,iconCls:'icon-edit'},'-',
{id:'_simulate',text:'模拟凭证',handler:_simulateImportPayment,iconCls:'icon-add'},'-',
{id:'_submitForReassign',text:'重分配提交',handler:_submitForReassignImportPayment,iconCls:'task'},'-',
{id:'_print',text:'打印',handler:_printImportPayment,iconCls:'task'},'-',
{id:'_print',text:'打印至出纳付款',handler:_printImportPayment1,iconCls:'task'},'-',
{id:'_print',text:'打印至出纳确认办理押汇',handler:_printImportPayment2,iconCls:'task'},'-',
{id:'_print',text:'打印出纳付押汇到期款',handler:_printImportPayment3,iconCls:'task'},'-',
{id:'_saveDate',text:'保存日期',handler:_saveDate,iconCls:'icon-table-save',hidden:${!saveDate}},'-',

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
    var tabsSize = 8;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 8 ; i++){
		   tabs.setActiveTab(8-1-i);
		}
	}
	/*
	 * 将付款金额分配的合同号、立项号与到单号渲染成链接形式，并在点击时弹出出详情查看窗口xx
	 */
	var contIndex = ImportPaymentItem_grid.getColumnModel().findColumnIndex('contract_no');
	var projIndex = ImportPaymentItem_grid.getColumnModel().findColumnIndex('project_no');
	var pickIndex = ImportPaymentItem_grid.getColumnModel().findColumnIndex('pick_list_no_text');
	var relaIndex = ImportRelatPayment_grid.getColumnModel().findColumnIndex('relatedno');
	ImportPaymentItem_grid.getColumnModel().setRenderer(contIndex,function(contNo){
		return '<a href="#" onclick="viewContractInfo(\''+contNo+'\');"><u style="border-bottom:1px;">'+contNo+'</u></a>';
	});
	ImportPaymentItem_grid.getColumnModel().setRenderer(projIndex,function(projNo){
		return '<a href="#" onclick="viewProjectInfo(\''+projNo+'\');"><u style="border-bottom:1px;">'+projNo+'</u></a>';
	});
	ImportPaymentItem_grid.getColumnModel().setRenderer(pickIndex,function(pickNo, metadata, record){
		if(record.data.pick_list_no.trim()==''){
			return '';
		}else{
			return '<a href="#" onclick="viewPickListInfo(\''+record.data.pick_list_no+'\');"><u style="border-bottom:1px;">'+pickNo+'</u></a>';
		}
	});
	ImportRelatPayment_grid.getColumnModel().setRenderer(relaIndex,function(relaNo){
		return '<a href="#" onclick="viewRelatedInfo(\''+relaNo+'\');"><u style="border-bottom:1px;">'+relaNo+'</u></a>';
	});
	
	/*************/
	div_currency2_sh_sh.editable = false;							// 押汇币别（搜索帮助）
	/**
	 * @创建人：陈非
	 * @修改日期：2010-09-08
	 * 进口付款保存时控制点：
	 * TT、DP、即期信用证只能选择押汇、一般付款；
	 * 远期信用证与DA只能选择承兑；
	 * 进口预付款只能选择一般付款。
	 */
	var store = dict_div_pay_type_dict.getStore();
	store.removeAll(false);
	var pt = document.getElementById('ImportPayment.paymenttype');
	var TopicRecord = Ext.data.Record.create([{name: 'id',mapping:'text'}]); 
	if(pt.value == '01' || pt.value == '02' || pt.value == '03'){
		var myNewRecord1 = new TopicRecord({id: '3',text: '一般付款'}); 
		var myNewRecord2 = new TopicRecord({id: '2',text: '押汇'}); 
		store.add(myNewRecord1);
		store.add(myNewRecord2);
	}
	
	if(pt.value == '04' || pt.value == '05'){
		var myNewRecord1 = new TopicRecord({id: '1',text: '承兑'}); 
		store.add(myNewRecord1);
	}
	if(pt.value == '14'){
		var myNewRecord1 = new TopicRecord({id: '3',text: '一般付款'}); 
		store.add(myNewRecord1);
	}
 });
</script>

