<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年12月20日 11点02分31秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象出口押汇(BillPurchased)查看页面
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
<fisc:grid divId="div_billPurBillItem" boName="BillPurBillItem" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YBILLPURBILLITEM.BILLPURID='${billPurchased.billpurid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_billPurBankItem" boName="BillPurBankItem" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YBILLPURBANKITEM.BILLPURID='${billPurchased.billpurid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_billPurReBankItem" boName="BillPurReBankItem" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YBILLPURREBANK.BILLPURID='${billPurchased.billpurid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_billPurReBankTwo" boName="BillPurReBankTwo" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YBILLPURREBANK.BILLPURID='${billPurchased.billpurid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${billPurchased.billpurid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.billpur_no" nodeId="${workflowNodeDefId}"/> >${vt.property.billpur_no}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="BillPurchased.billpur_no" name="BillPurchased.billpur_no" value="${billPurchased.billpur_no}" <fisc:authentication sourceName="BillPurchased.billpur_no" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.applyamount" nodeId="${workflowNodeDefId}"/> >${vt.property.applyamount}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="BillPurchased.applyamount" name="BillPurchased.applyamount" value="${billPurchased.applyamount}" <fisc:authentication sourceName="BillPurchased.applyamount" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.currency" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.currency}：</td>
		<td width="30%">
			<div id="div_currency_sh"></div>
			<fisc:searchHelp divId="div_currency_sh" boName="BillPurchased" boProperty="currency"  value="${billPurchased.currency}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.billpurrate" nodeId="${workflowNodeDefId}"/> >${vt.property.billpurrate}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="BillPurchased.billpurrate" name="BillPurchased.billpurrate" value="${billPurchased.billpurrate}" <fisc:authentication sourceName="BillPurchased.billpurrate" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="BillPurchased.documentaryinter" nodeId="${workflowNodeDefId}"/> >${vt.property.documentaryinter}：</td>
		<td   width="30%" >
			<input type="text" class="inputText" id="BillPurchased.documentaryinter" name="BillPurchased.documentaryinter" value="${billPurchased.documentaryinter}" <fisc:authentication sourceName="BillPurchased.documentaryinter" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="BillPurchased.documentarylimit" nodeId="${workflowNodeDefId}"/> >${vt.property.documentarylimit}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="BillPurchased.documentarylimit" name="BillPurchased.documentarylimit" value="${billPurchased.documentarylimit}" <fisc:authentication sourceName="BillPurchased.documentarylimit" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.payrealdate" nodeId="${workflowNodeDefId}"/> >${vt.property.payrealdate}：</td>
		<td width="30%">
			<input type="text" id="BillPurchased.payrealdate" name="BillPurchased.payrealdate" value="">
				<fisc:calendar applyTo="BillPurchased.payrealdate"  divId="" fieldName="" defaultValue="${billPurchased.payrealdate}"></fisc:calendar>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.maturity" nodeId="${workflowNodeDefId}"/> >${vt.property.maturity}：</td>
		<td  width="40%">
			<input type="text" id="BillPurchased.maturity" name="BillPurchased.maturity" value="">
				<fisc:calendar applyTo="BillPurchased.maturity"  divId="" fieldName="" defaultValue="${billPurchased.maturity}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.voucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherdate}：</td>
		<td width="30%">
			<input type="text" id="BillPurchased.voucherdate" name="BillPurchased.voucherdate" value="">
				<fisc:calendar applyTo="BillPurchased.voucherdate"  divId="" fieldName="" defaultValue="${billPurchased.voucherdate}"></fisc:calendar>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.accountdate" nodeId="${workflowNodeDefId}"/> >${vt.property.accountdate}：</td>
		<td  width="40%">
			<input type="text" id="BillPurchased.accountdate" name="BillPurchased.accountdate" value="">
				<fisc:calendar applyTo="BillPurchased.accountdate"  divId="" fieldName="" defaultValue="${billPurchased.accountdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.text" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.text}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="BillPurchased.text" name="BillPurchased.text" <fisc:authentication sourceName="BillPurchased.text" nodeId="${workflowNodeDefId}"/>>${billPurchased.text}</textarea>
		</td>
	</tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.revoucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.revoucherdate}：</td>
		<td  width="40%">
			<input type="text" id="BillPurchased.revoucherdate" name="BillPurchased.revoucherdate" value="">
				<fisc:calendar applyTo="BillPurchased.revoucherdate"  divId="" fieldName="" defaultValue="${billPurchased.revoucherdate}"></fisc:calendar>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.reaccountdate" nodeId="${workflowNodeDefId}"/> >${vt.property.reaccountdate}：</td>
		<td width="30%">
			<input type="text" id="BillPurchased.reaccountdate" name="BillPurchased.reaccountdate" value="">
				<fisc:calendar applyTo="BillPurchased.reaccountdate"  divId="" fieldName="" defaultValue="${billPurchased.reaccountdate}"></fisc:calendar>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.retext" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.retext}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="BillPurchased.retext" name="BillPurchased.retext" <fisc:authentication sourceName="BillPurchased.retext" nodeId="${workflowNodeDefId}"/>>${billPurchased.retext}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="BillPurchased.remark" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.remark}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="BillPurchased.remark" name="BillPurchased.remark" <fisc:authentication sourceName="BillPurchased.remark" nodeId="${workflowNodeDefId}"/>>${billPurchased.remark}</textarea>
		</td>
	</tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td  width="40%">
			<fisc:user boProperty="creator" boName="BillPurchased" userId="${billPurchased.creator}"></fisc:user>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td width="30%">
		    <input type="text" class="inputText" id="BillPurchased.createtime" name="BillPurchased.createtime" value="${billPurchased.createtime}"  readonly="readonly">
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillPurchased.dept_id" nodeId="${workflowNodeDefId}"/> >${vt.property.dept_id}：</td>
		<td  width="40%">
			<div id="div_dept_id_sh"></div>
			<fisc:searchHelp divId="div_dept_id_sh" boName="BillPurchased" boProperty="dept_id"  value="${billPurchased.dept_id}"></fisc:searchHelp>
		</td>
	</tr>

	<input type="hidden" id="BillPurchased.client" name="BillPurchased.client" value="${billPurchased.client}">
	<input type="hidden" id="BillPurchased.billpurid" name="BillPurchased.billpurid" value="${billPurchased.billpurid}">
	<input type="hidden" id="BillPurchased.businessstate" name="BillPurchased.businessstate" value="${billPurchased.businessstate}">
	<input type="hidden" id="BillPurchased.processstate" name="BillPurchased.processstate" value="${billPurchased.processstate}">
	
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_billPurBillItem"></div>
<div id="div_billPurBankItem"></div>
<div id="div_billPurReBankItem" ></div>
<div id="div_billPurReBankTwo" ></div>
<div id="div_settleSubject" class="x-hide-display">
<form id="settleSubjectForm" name="settleSubjectForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.debtcredit1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit1}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit1_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit1_dict" isNeedAuth="false" value="${billPurchased.settleSubject.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.amount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount1" name="SettleSubject.amount1" value="${billPurchased.settleSubject.amount1}" <fisc:authentication sourceName="SettleSubject.amount1" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.standardamount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount1}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount1" name="SettleSubject.standardamount1" value="${billPurchased.settleSubject.standardamount1}" <fisc:authentication sourceName="SettleSubject.standardamount1" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.settlesubject1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject1}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject1_sh" boName="SettleSubject" boProperty="settlesubject1" value="${billPurchased.settleSubject.settlesubject1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.costcenter1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter1}：</td>
<td width="30%" >
<div id="div_SettleSubjectcostcenter1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter1_sh" boName="SettleSubject" boProperty="costcenter1" value="${billPurchased.settleSubject.costcenter1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.depid1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid1}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid1_sh" boName="SettleSubject" boProperty="depid1" value="${billPurchased.settleSubject.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.orderno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno1}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno1_sh" boName="SettleSubject" boProperty="orderno1" value="${billPurchased.settleSubject.orderno1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.rowno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno1" name="SettleSubject.rowno1" value="${billPurchased.settleSubject.rowno1}" <fisc:authentication sourceName="SettleSubject.rowno1" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.antiaccount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount1}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount1_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount1_dict" isNeedAuth="false" value="${billPurchased.settleSubject.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.debtcredit2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit2}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit2_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit2_dict" isNeedAuth="false" value="${billPurchased.settleSubject.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.amount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount2" name="SettleSubject.amount2" value="${billPurchased.settleSubject.amount2}" <fisc:authentication sourceName="SettleSubject.amount2" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.standardamount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount2}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount2" name="SettleSubject.standardamount2" value="${billPurchased.settleSubject.standardamount2}" <fisc:authentication sourceName="SettleSubject.standardamount2" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.settlesubject2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject2}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject2_sh" boName="SettleSubject" boProperty="settlesubject2" value="${billPurchased.settleSubject.settlesubject2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.costcenter2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter2}：</td>
<td width="30%" >
<div id="div_SettleSubjectcostcenter2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter2_sh" boName="SettleSubject" boProperty="costcenter2" value="${billPurchased.settleSubject.costcenter2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.depid2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid2}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid2_sh" boName="SettleSubject" boProperty="depid2" value="${billPurchased.settleSubject.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.orderno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno2}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno2_sh" boName="SettleSubject" boProperty="orderno2" value="${billPurchased.settleSubject.orderno2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.rowno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno2" name="SettleSubject.rowno2" value="${billPurchased.settleSubject.rowno2}" <fisc:authentication sourceName="SettleSubject.rowno2" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.antiaccount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount2}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount2_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount2_dict" isNeedAuth="false" value="${billPurchased.settleSubject.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.debtcredit3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit3}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit3_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit3_dict" isNeedAuth="false" value="${billPurchased.settleSubject.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.amount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount3" name="SettleSubject.amount3" value="${billPurchased.settleSubject.amount3}" <fisc:authentication sourceName="SettleSubject.amount3" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.standardamount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount3}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount3" name="SettleSubject.standardamount3" value="${billPurchased.settleSubject.standardamount3}" <fisc:authentication sourceName="SettleSubject.standardamount3" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.settlesubject3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject3}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject3_sh" boName="SettleSubject" boProperty="settlesubject3" value="${billPurchased.settleSubject.settlesubject3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.costcenter3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter3}：</td>
<td width="30%" >
<div id="div_SettleSubjectcostcenter3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter3_sh" boName="SettleSubject" boProperty="costcenter3" value="${billPurchased.settleSubject.costcenter3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.depid3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid3}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid3_sh" boName="SettleSubject" boProperty="depid3" value="${billPurchased.settleSubject.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.orderno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno3}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno3_sh" boName="SettleSubject" boProperty="orderno3" value="${billPurchased.settleSubject.orderno3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.rowno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno3" name="SettleSubject.rowno3" value="${billPurchased.settleSubject.rowno3}" <fisc:authentication sourceName="SettleSubject.rowno3" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.antiaccount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount3}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount3_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount3_dict" isNeedAuth="false" value="${billPurchased.settleSubject.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.debtcredit4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit4}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit4_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit4" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit4_dict" isNeedAuth="false" value="${billPurchased.settleSubject.debtcredit4}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.amount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount4}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount4" name="SettleSubject.amount4" value="${billPurchased.settleSubject.amount4}" <fisc:authentication sourceName="SettleSubject.amount4" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.standardamount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount4}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount4" name="SettleSubject.standardamount4" value="${billPurchased.settleSubject.standardamount4}" <fisc:authentication sourceName="SettleSubject.standardamount4" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.settlesubject4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject4}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject4_sh" boName="SettleSubject" boProperty="settlesubject4" value="${billPurchased.settleSubject.settlesubject4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.profitcenter" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.profitcenter}：</td>
<td width="30%" >
<div id="div_SettleSubjectprofitcenter_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectprofitcenter_sh" boName="SettleSubject" boProperty="profitcenter" value="${billPurchased.settleSubject.profitcenter}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.depid4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid4}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid4_sh" boName="SettleSubject" boProperty="depid4" value="${billPurchased.settleSubject.depid4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.orderno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno4}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno4_sh" boName="SettleSubject" boProperty="orderno4" value="${billPurchased.settleSubject.orderno4}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.rowno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno4}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno4" name="SettleSubject.rowno4" value="${billPurchased.settleSubject.rowno4}" <fisc:authentication sourceName="SettleSubject.rowno4" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
</table>
<input type="hidden" id="SettleSubject.settlesubjectid" name="SettleSubject.settlesubjectid" value="${billPurchased.settleSubject.settlesubjectid}">
</form>
</div>
<div id="div_fundFlow" class="x-hide-display">
<form id="fundFlowForm" name="fundFlowForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.debtcredit1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit1}：</td>
<td width="40%" >
<div id="div_FundFlowdebtcredit1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit1_dict" isNeedAuth="false" value="${billPurchased.fundFlow.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.amount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlow.amount1" name="FundFlow.amount1" value="${billPurchased.fundFlow.amount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.standardamount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount1}：</td>
<td width="30%">
<input type="text" class="inputText" id="FundFlow.standardamount1" name="FundFlow.standardamount1" value="${billPurchased.fundFlow.standardamount1}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.specialaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount1}： </td>
<td width="40%">
<div id="div_FundFlowspecialaccount1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount1" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount1_dict" isNeedAuth="false" value="${billPurchased.fundFlow.specialaccount1}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.customer1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer1}： </td>
<td width="30%">
<div id="div_FundFlowcustomer1_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer1_sh" boName="FundFlow" boProperty="customer1" value="${billPurchased.fundFlow.customer1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.depid1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid1}： </td>
<td width="40%">
<div id="div_FundFlowdepid1_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid1_sh" boName="FundFlow" boProperty="depid1" value="${billPurchased.fundFlow.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.antiaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount1}： </td>
<td width="30%">
<div id="div_FundFlowantiaccount1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount1_dict" isNeedAuth="false" value="${billPurchased.fundFlow.antiaccount1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.debtcredit2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit2}： </td>
<td width="40%">
<div id="div_FundFlowdebtcredit2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit2_dict" isNeedAuth="false" value="${billPurchased.fundFlow.debtcredit2}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.amount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount2}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlow.amount2" name="FundFlow.amount2" value="${billPurchased.fundFlow.amount2}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.standardamount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlow.standardamount2" name="FundFlow.standardamount2" value="${billPurchased.fundFlow.standardamount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.specialaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount2}： </td>
<td width="30%">
<div id="div_FundFlowspecialaccount2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount2" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount2_dict" isNeedAuth="false" value="${billPurchased.fundFlow.specialaccount2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.customer2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer2}： </td>
<td width="40%">
<div id="div_FundFlowcustomer2_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer2_sh" boName="FundFlow" boProperty="customer2" value="${billPurchased.fundFlow.customer2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.depid2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid2}： </td>
<td width="30%">
<div id="div_FundFlowdepid2_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid2_sh" boName="FundFlow" boProperty="depid2" value="${billPurchased.fundFlow.depid2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.antiaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount2}： </td>
<td width="40%">
<div id="div_FundFlowantiaccount2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount2_dict" isNeedAuth="false" value="${billPurchased.fundFlow.antiaccount2}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.debtcredit3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit3}： </td>
<td width="30%">
<div id="div_FundFlowdebtcredit3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit3_dict" isNeedAuth="false" value="${billPurchased.fundFlow.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.amount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlow.amount3" name="FundFlow.amount3" value="${billPurchased.fundFlow.amount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.standardamount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount3}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlow.standardamount3" name="FundFlow.standardamount3" value="${billPurchased.fundFlow.standardamount3}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.specialaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount3}： </td>
<td width="40%">
<div id="div_FundFlowspecialaccount3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount3" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount3_dict" isNeedAuth="false" value="${billPurchased.fundFlow.specialaccount3}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.customer3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer3}： </td>
<td width="30%">
<div id="div_FundFlowcustomer3_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer3_sh" boName="FundFlow" boProperty="customer3" value="${billPurchased.fundFlow.customer3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.depid3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid3}： </td>
<td width="40%">
<div id="div_FundFlowdepid3_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid3_sh" boName="FundFlow" boProperty="depid3" value="${billPurchased.fundFlow.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.antiaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount3}： </td>
<td width="30%">
<div id="div_FundFlowantiaccount3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount3_dict" isNeedAuth="false" value="${billPurchased.fundFlow.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
</table>
<input type="hidden" id="FundFlow.fundflowid" name="FundFlow.fundflowid" value="${billPurchased.fundFlow.fundflowid}">
</form>
</div>

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/billpurchased/billPurchasedView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/billpurchased/billPurchasedViewGen.js"></script>

<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${billPurchased.processstate}';
//当前对象主键ID
var billpurid = '${billPurchased.billpurid}';	

//页面文本
var Txt={
	//出口押汇
	billPurchased:'${vt.boText}',
	          
	//发票
	billPurBillItem:'${billPurBillItemVt.boText}',
	// 请选择需要进行【发票批量删除】操作的记录！
	billPurBillItem_Deletes_SelectToOperation:'${billPurBillItemVt.deletes_SelectToOperate}',
	// 您选择了【发票批量删除】操作，是否确定继续该操作？
	billPurBillItem_Deletes_ConfirmOperation:'${billPurBillItemVt.deletes_ConfirmOperation}',
          
	//押汇银行
	billPurBankItem:'${billPurBankItemVt.boText}',
	// 请选择需要进行【押汇银行批量删除】操作的记录！
	billPurBankItem_Deletes_SelectToOperation:'${billPurBankItemVt.deletes_SelectToOperate}',
	// 您选择了【押汇银行批量删除】操作，是否确定继续该操作？
	billPurBankItem_Deletes_ConfirmOperation:'${billPurBankItemVt.deletes_ConfirmOperation}',
          
	//还押汇银行
	billPurReBankItem:'${billPurReBankItemVt.boText}',
	// 请选择需要进行【还押汇银行批量删除】操作的记录！
	billPurReBankItem_Deletes_SelectToOperation:'${billPurReBankItemVt.deletes_SelectToOperate}',
	// 您选择了【还押汇银行批量删除】操作，是否确定继续该操作？
	billPurReBankItem_Deletes_ConfirmOperation:'${billPurReBankItemVt.deletes_ConfirmOperation}',
          
	//还押汇银行2
	billPurReBankTwo:'${billPurReBankTwoVt.boText}',
	// 请选择需要进行【还押汇银行批量删除】操作的记录！
	billPurReBankTwo_Deletes_SelectToOperation:'${billPurReBankTwoVt.deletes_SelectToOperate}',
	// 您选择了【还押汇银行批量删除】操作，是否确定继续该操作？
	billPurReBankTwo_Deletes_ConfirmOperation:'${billPurReBankTwoVt.deletes_ConfirmOperation}',
          
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
	//boText复制创建
	billPurchased_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	billPurchased_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	billPurchased_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		id:'centercontent',
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		border:false,
				            		//height:310,
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
						            		title:'${billPurBillItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'billPurBillItemTab',
						            		contentEl:'div_billPurBillItem'
						            	},
          						                {
						            		title:'${billPurBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'billPurBankItemTab',
						            		contentEl:'div_billPurBankItem'
						            	},
          						                {
						            		title:'${billPurReBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'billPurReBankItemTab',
						            		contentEl:'div_billPurReBankItem'
						            	},
          						        {
						            		title:'${billPurReBankTwoVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'billPurReBankTwoTab',
						            		contentEl:'div_billPurReBankTwo'
						            	},
          						                {
						            		title:'${settleSubjectVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		autoHeight:true,
						            		autoScroll: true,
						            		id:'settleSubjectTab',
						            		contentEl:'div_settleSubject'
						            	},
          						                {
						            		title:'${fundFlowVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		autoHeight:true,
						            		autoScroll: true,
						            		id:'fundFlowTab',
						            		contentEl:'div_fundFlow'
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
{id:'_copyCreate',text:'${vt.mCopyCreate}',handler:_copyCreateBillPurchased,iconCls:'icon-copyCreate'},'-',
{id:'_create',text:'${vt.mCreate}',handler:_createBillPurchased,iconCls:'icon-add'},'-',
'->','-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelBillPurchased,iconCls:'icon-undo'},'-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessBillPurchased,iconCls:'task'},'-',
<%}%>
{id:'_cashJournal',text:'现金日记帐',handler:_cashJournalBillPurchased,iconCls:' '},'-',
{id:'_voucherPreview',text:'模拟凭证',handler:_voucherPreviewBillPurchased,iconCls:' '},'-',
' '
],
			renderTo:'div_toolbar'
	});

	var isTask = '${workflowTaskId}';
	if(!isTask||isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited&&isSubmited!=''&&isSubmited!=' '){
Ext.getCmp('_copyCreate').disable();
Ext.getCmp('_create').disable();
<%
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
Ext.getCmp('_submitProcess').disable();
<%}%>		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
Ext.getCmp('_copyCreate').disable();
Ext.getCmp('_create').disable();	}
	Ext.getCmp('_copyCreate').hide();
	Ext.getCmp('_create').hide();
//});
Ext.onReady(function(){
    var tabsSize = 6;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < tabsSize ; i++){
		   tabs.setActiveTab(tabsSize-1-i);
		}
	}
 });
</script>

