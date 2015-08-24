<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年08月23日 02点16分36秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象进口付款(ImportPayment)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
</head>
<body>
<fisc:grid divId="div_importPaymentItem" boName="ImportPaymentItem" needCheckBox="true" editable="true" defaultCondition=" YPAYMENTITEM.PAYMENTID='${importPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true"  pageSize="10000"></fisc:grid>
<fisc:grid divId="div_importPaymentCbill" boName="ImportPaymentCbill" needCheckBox="true" editable="true" defaultCondition=" YPAYMENTCBILL.PAYMENTID='${importPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true"  pageSize="10000"></fisc:grid>
<fisc:grid divId="div_importPayBankItem" boName="ImportPayBankItem" needCheckBox="true" editable="true" defaultCondition=" YPAYBANKITEM.PAYMENTID='${importPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" pageSize="10000" ></fisc:grid>
<fisc:grid divId="div_importDocuBankItem" boName="ImportDocuBankItem" needCheckBox="true" editable="true" defaultCondition=" YDOUCARYBANKITEM.PAYMENTID='${importPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" pageSize="10000" ></fisc:grid>
<fisc:grid divId="div_billPayReBankItem" boName="BillPayReBankItem" needCheckBox="true" editable="true" defaultCondition=" YBILLPAYMENTBANK.PAYMENTID='${importPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" pageSize="10000"></fisc:grid>
<fisc:grid divId="div_importRelatPayment" boName="ImportRelatPayment" needCheckBox="true" editable="true" defaultCondition=" YPAYMENTRELATED.PAYMENTID='${importPayment.paymentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:attachement businessId="${businessId}" allowDelete="true" allowUpload="true" divId="div_attachement" boName="ImportPayment" boProperty="attachement" gridPageSize="10" gridHeight="285" ></fisc:attachement>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${importPayment.paymentid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.supplier" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.supplier}：</td>
		<td width="30%">
			<div id="div_supplier_sh"></div>
			<fisc:searchHelp divId="div_supplier_sh" boName="ImportPayment" boProperty="supplier"  value="${importPayment.supplier}" editable="false"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.paymenttype" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.paymenttype}：</td>
		<td  width="40%">
			<div id="div_paymenttype_dict"></div>
			<fisc:dictionary boName="ImportPayment" boProperty="paymenttype" dictionaryName="YDPAYTRADETYPE" divId="div_paymenttype_dict" isNeedAuth="false"  value="${importPayment.paymenttype}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.dept_id" nodeId="${workflowNodeDefId}"/> >${vt.property.dept_id}：</td>
		<td width="30%">
			<div id="div_dept_id_sh"></div>
			<fisc:searchHelp divId="div_dept_id_sh" boName="ImportPayment" boProperty="dept_id"  value="${importPayment.dept_id}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.pay_type" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.pay_type}：</td>
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
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.representpaycust" nodeId="${workflowNodeDefId}"/> >${vt.property.representpaycust}：</td>
		<td width="30%">
			<div id="div_representpaycust_sh"></div>
			<fisc:searchHelp divId="div_representpaycust_sh" boName="ImportPayment" boProperty="representpaycust"  value="${importPayment.representpaycust}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.isoverrepay" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.isoverrepay}：</td>
		<td  width="40%">
			<div id="div_isoverrepay_dict"></div>
			<fisc:dictionary boName="ImportPayment" boProperty="isoverrepay" dictionaryName="YDPAYMENTYESORNO" divId="div_isoverrepay_dict" isNeedAuth="false"  value="${importPayment.isoverrepay}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.applyamount" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.applyamount}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.applyamount" name="ImportPayment.applyamount" value="${importPayment.applyamount}"   <fisc:authentication sourceName="ImportPayment.applyamount" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.currency" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.currency}：</td>
		<td  width="40%">
			<div id="div_currency_sh"></div>
			<fisc:searchHelp divId="div_currency_sh" boName="ImportPayment" boProperty="currency"  value="${importPayment.currency}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.closeexchangerat" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.closeexchangerat}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.closeexchangerat" name="ImportPayment.closeexchangerat" value="${importPayment.closeexchangerat}"   <fisc:authentication sourceName="ImportPayment.closeexchangerat" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.collectbankid" nodeId="${workflowNodeDefId}"/> >${vt.property.collectbankid}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="ImportPayment.collectbankid" name="ImportPayment.collectbankid" value="${importPayment.collectbankid}"   <fisc:authentication sourceName="ImportPayment.collectbankid" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.collectbankacc" nodeId="${workflowNodeDefId}"/> >${vt.property.collectbankacc}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.collectbankacc" name="ImportPayment.collectbankacc" value="${importPayment.collectbankacc}"   <fisc:authentication sourceName="ImportPayment.collectbankacc" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.writelistno" nodeId="${workflowNodeDefId}"/> >${vt.property.writelistno}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="ImportPayment.writelistno" name="ImportPayment.writelistno" value="${importPayment.writelistno}"   <fisc:authentication sourceName="ImportPayment.writelistno" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.arrivegoodsdate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.arrivegoodsdate}：</td>
		<td width="30%">
			<input type="text" id="ImportPayment.arrivegoodsdate" name="ImportPayment.arrivegoodsdate" value="">
				<fisc:calendar applyTo="ImportPayment.arrivegoodsdate"  divId="" fieldName="" defaultValue="${importPayment.arrivegoodsdate}"></fisc:calendar>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.musttaketickleda" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.musttaketickleda}：</td>
		<td  width="40%">
			<input type="text" id="ImportPayment.musttaketickleda" name="ImportPayment.musttaketickleda" value="">
				<fisc:calendar applyTo="ImportPayment.musttaketickleda"  divId="" fieldName="" defaultValue="${importPayment.musttaketickleda}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.replacedate" nodeId="${workflowNodeDefId}"/> >${vt.property.replacedate}：</td>
		<td width="30%">
			<input type="text" id="ImportPayment.replacedate" name="ImportPayment.replacedate" value="">
				<fisc:calendar applyTo="ImportPayment.replacedate"  divId="" fieldName="" defaultValue="${importPayment.replacedate}"></fisc:calendar>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.paydate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.paydate}：</td>
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
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.payer" nodeId="${workflowNodeDefId}"/> >${vt.property.payer}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.payer" name="ImportPayment.payer" value="${importPayment.payer}"   <fisc:authentication sourceName="ImportPayment.payer" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.currency2" nodeId="${workflowNodeDefId}"/> >${vt.property.currency2}：</td>
		<td  width="40%">
			<div id="div_currency2_sh"></div>
			<fisc:searchHelp divId="div_currency2_sh" boName="ImportPayment" boProperty="currency2"  value="${importPayment.currency2}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.documentarylimit" nodeId="${workflowNodeDefId}"/> >${vt.property.documentarylimit}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.documentarylimit" name="ImportPayment.documentarylimit" value="${importPayment.documentarylimit}"   <fisc:authentication sourceName="ImportPayment.documentarylimit" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.documentarydate" nodeId="${workflowNodeDefId}"/> >${vt.property.documentarydate}：</td>
		<td  width="40%">
			<input type="text" id="ImportPayment.documentarydate" name="ImportPayment.documentarydate" value="">
				<fisc:calendar applyTo="ImportPayment.documentarydate"  divId="" fieldName="" defaultValue="${importPayment.documentarydate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.documentaryrate" nodeId="${workflowNodeDefId}"/> >${vt.property.documentaryrate}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.documentaryrate" name="ImportPayment.documentaryrate" value="${importPayment.documentaryrate}"   <fisc:authentication sourceName="ImportPayment.documentaryrate" nodeId="${workflowNodeDefId}"/>>
		</td>
		<!-- 邱杰烜  2010-09-08  新增"押汇利率"输入框 -->
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.doctaryinterest" nodeId="${workflowNodeDefId}"/> >${vt.property.doctaryinterest}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.doctaryinterest" name="ImportPayment.doctaryinterest" value="${importPayment.doctaryinterest}"   <fisc:authentication sourceName="ImportPayment.doctaryinterest" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.documentarypaydt" nodeId="${workflowNodeDefId}"/> >${vt.property.documentarypaydt}：</td>
		<td  width="40%">
			<input type="text" id="ImportPayment.documentarypaydt" name="ImportPayment.documentarypaydt" value="">
				<fisc:calendar applyTo="ImportPayment.documentarypaydt"  divId="" fieldName="" defaultValue="${importPayment.documentarypaydt}"></fisc:calendar>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.redocarybc" nodeId="${workflowNodeDefId}"/> >${vt.property.redocarybc}：</td>
		<td width="30%">
			<div id="div_redocarybc_sh"></div>
			<fisc:searchHelp divId="div_redocarybc_sh" boName="ImportPayment" boProperty="redocarybc"  value="${importPayment.redocarybc}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.payrealdate" nodeId="${workflowNodeDefId}"/> >${vt.property.payrealdate}：</td>
		<td  width="40%">
			<input type="text" id="ImportPayment.payrealdate" name="ImportPayment.payrealdate" value="">
				<fisc:calendar applyTo="ImportPayment.payrealdate"  divId="" fieldName="" defaultValue="${importPayment.payrealdate}"></fisc:calendar>
		</td>
	
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.redocaryamount" nodeId="${workflowNodeDefId}"/> >${vt.property.redocaryamount}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.redocaryamount" name="ImportPayment.redocaryamount" value="${importPayment.redocaryamount}"   <fisc:authentication sourceName="ImportPayment.redocaryamount" nodeId="${workflowNodeDefId}"/>>
		</td>
        
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.redocaryrate" nodeId="${workflowNodeDefId}"/> >${vt.property.redocaryrate}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="ImportPayment.redocaryrate" name="ImportPayment.redocaryrate" value="${importPayment.redocaryrate}"   <fisc:authentication sourceName="ImportPayment.redocaryrate" nodeId="${workflowNodeDefId}"/>>
		</td>
		
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td width="30%">
		    <input type="text" class="inputText" id="ImportPayment.createtime" name="ImportPayment.createtime" value="${importPayment.createtime}"  readonly="readonly">
		</td>
		
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td  width="40%">
			<fisc:user boProperty="creator" boName="ImportPayment" userId="${importPayment.creator}"></fisc:user>
		</td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.text" nodeId="${workflowNodeDefId}"/> valign="top"><font color="red">★</font>${vt.property.text}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="ImportPayment.text" name="ImportPayment.text"  <fisc:authentication sourceName="ImportPayment.text" nodeId="${workflowNodeDefId}"/>>${importPayment.text}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.remark" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.remark}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="ImportPayment.remark" name="ImportPayment.remark"  <fisc:authentication sourceName="ImportPayment.remark" nodeId="${workflowNodeDefId}"/>>${importPayment.remark}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.factamount" nodeId="${workflowNodeDefId}"/> >${vt.property.factamount}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.factamount" name="ImportPayment.factamount" value="${importPayment.factamount}"   <fisc:authentication sourceName="ImportPayment.factamount" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.factcurrency" nodeId="${workflowNodeDefId}"/> >${vt.property.factcurrency}：</td>
		<td  width="40%">
			<div id="div_factcurrency_sh"></div>
			<fisc:searchHelp divId="div_factcurrency_sh" boName="ImportPayment" boProperty="factcurrency"  value="${importPayment.factcurrency}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.exchangerate" nodeId="${workflowNodeDefId}"/> >${vt.property.exchangerate}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="ImportPayment.exchangerate" name="ImportPayment.exchangerate" value="${importPayment.exchangerate}"   <fisc:authentication sourceName="ImportPayment.exchangerate" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.voucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherdate}：</td>
		<td  width="40%">
			<input type="text" id="ImportPayment.voucherdate" name="ImportPayment.voucherdate" value="">
				<fisc:calendar applyTo="ImportPayment.voucherdate"  divId="" fieldName="" defaultValue="${importPayment.voucherdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="ImportPayment.accountdate" nodeId="${workflowNodeDefId}"/> >${vt.property.accountdate}：</td>
		<td width="30%">
			<input type="text" id="ImportPayment.accountdate" name="ImportPayment.accountdate" value="">
				<fisc:calendar applyTo="ImportPayment.accountdate"  divId="" fieldName="" defaultValue="${importPayment.accountdate}"></fisc:calendar>
		</td>
        <td align="right"  width="15%" <fisc:authentication sourceName="ImportPayment.redocaryamount2" nodeId="${workflowNodeDefId}"/> >累计还押汇金额：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="ImportPayment.redocaryamount2" name="ImportPayment.redocaryamount2" readonly="readonly" value="${importPayment.redocaryamount2}" <fisc:authentication sourceName="ImportPayment.redocaryamount2" nodeId="${workflowNodeDefId}"/> >
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
<div id="div_importSettSubj" class="x-hide-display" >

</div>
<div id="div_importFundFlow" class="x-hide-display" >

</div>
<div id="div_importRelatPayment"></div>
<div id="div_attachement" ></div>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/payment/importPaymentywEditGen.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/payment/importPaymentywEdit.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${importPayment.processstate}';
//当前对象主键ID
var paymentid = '${importPayment.paymentid}';	
var isReassign = '${isReassign}';
var strRoleType = '${roletype}';
var xjrj = '${xjrj}';
var username = '${username}';
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
				            		//height:835,
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
						            		title:'${importPaymentItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'importPaymentItemTab',
						            		contentEl:'div_importPaymentItem'
						            	},
          						        {
						            		title:'${importPaymentCbillVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'importPaymentCbillTab',
						            		contentEl:'div_importPaymentCbill'
						            	},
          						        {
						            		title:'${importPayBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'importPayBankItemTab',
						            		contentEl:'div_importPayBankItem'
						            	},
          						        {
						            		title:'${importDocuBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'importDocuBankItemTab',
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
						            		autoHeight:true,
						            		id:'importSettSubjTab',
						            		contentEl:'div_importSettSubj',
						            		autoScroll: true
						            	},
          						        {
						            		title:'${importFundFlowVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		autoHeight:true,
						            		id:'importFundFlowTab',
						            		contentEl:'div_importFundFlow',
						            		autoScroll: true
						            	},
          						        {
						            		title:'${importRelatPaymentVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'importRelatPaymentTab',
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
'->','-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessImportPayment,iconCls:'task'},'-',
<%}%>
{id:'_saveOrUpdate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateImportPayment,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelImportPayment,iconCls:'icon-undo'},'-',
{id:'_autoassign',text:'自动分配',handler:_autoassignImportPayment,iconCls:'icon-add'},'-',
{id:'_clearassign',text:'清除分配',handler:_clearassignImportPayment,iconCls:'icon-delete'},'-',
{id:'_viewCredit',text:'查看信用额度',handler:_viewCreditImportPayment,iconCls:'icon-view'},'-',
{id:'_bookofaccount',text:'现金日记帐',handler:_bookofaccountImportPayment,iconCls:'icon-edit'},'-',
{id:'_simulate',text:'模拟凭证',handler:_simulateImportPayment,iconCls:'icon-add'},'-',
{id:'_submitForReassign',text:'重分配提交',handler:_submitForReassignImportPayment,iconCls:'task'},'-',
{id:'_print',text:'打印',handler:_printImportPayment,iconCls:'task'},'-',

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
<%}%>
		}
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

