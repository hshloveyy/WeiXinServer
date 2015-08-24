<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2011年05月26日 10点55分54秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象打包贷款(PackingLoan)增加页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
</head>
<body>
<fisc:grid divId="div_packingBankItem" boName="PackingBankItem" needToolbar="false" needCheckBox="true" editable="true" defaultCondition=" YPACKINGBANKITEM.PACKINGID='${packingLoan.packingid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_packingReBankItem" boName="PackingReBankItem" needToolbar="false" needCheckBox="true" editable="true" defaultCondition=" YPACKINGREBANK.PACKINGID='${packingLoan.packingid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_packingReBankTwo" boName="PackingReBankTwo" needCheckBox="false" editable="true" defaultCondition=" YPACKINGREBANK.PACKINGID='${packingLoan.packingid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<div id="div_top_south" ></div>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${packingLoan.packingid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.packing_no" nodeId="${workflowNodeDefId}"/> >${vt.property.packing_no}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PackingLoan.packing_no" name="PackingLoan.packing_no" value="${packingLoan.packing_no}" <fisc:authentication sourceName="PackingLoan.packing_no" nodeId="${workflowNodeDefId}"/>  readonly="readonly">
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.creditrecdate" nodeId="${workflowNodeDefId}"/> >${vt.property.creditrecdate}：</td>
		<td   width="40%" >
			<input type="text" id="PackingLoan.creditrecdate" name="PackingLoan.creditrecdate" value="">
				<fisc:calendar applyTo="PackingLoan.creditrecdate"  divId="" fieldName="" defaultValue="${packingLoan.creditrecdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.creditno" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.creditno}：</td>
		<td  width="30%" >
			<div id="div_creditno_sh"></div>
			<fisc:searchHelp divId="div_creditno_sh" boName="PackingLoan" boProperty="creditno"  value="${packingLoan.creditno}"  callBackHandler="_creditnoCallBack"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.contractno" nodeId="${workflowNodeDefId}"/> >${vt.property.contractno}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PackingLoan.contractno" name="PackingLoan.contractno" value="${packingLoan.contractno}" <fisc:authentication sourceName="PackingLoan.contractno" nodeId="${workflowNodeDefId}"/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.createbank" nodeId="${workflowNodeDefId}"/> >${vt.property.createbank}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PackingLoan.createbank" name="PackingLoan.createbank" value="${packingLoan.createbank}" <fisc:authentication sourceName="PackingLoan.createbank" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.currency" nodeId="${workflowNodeDefId}"/> >${vt.property.currency}：</td>
		<td   width="40%" >
			<div id="div_currency_sh"></div>
			<fisc:searchHelp divId="div_currency_sh" boName="PackingLoan" boProperty="currency" value="${packingLoan.currency}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.applyamount" nodeId="${workflowNodeDefId}"/> >${vt.property.applyamount}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PackingLoan.applyamount" name="PackingLoan.applyamount" value="${packingLoan.applyamount}" <fisc:authentication sourceName="PackingLoan.applyamount" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.dealine" nodeId="${workflowNodeDefId}"/> >${vt.property.dealine}：</td>
		<td   width="40%" >
			<input type="text" id="PackingLoan.dealine" name="PackingLoan.dealine" value="">
				<fisc:calendar applyTo="PackingLoan.dealine"  divId="" fieldName="" defaultValue="${packingLoan.dealine}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.mature" nodeId="${workflowNodeDefId}"/> >${vt.property.mature}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PackingLoan.mature" name="PackingLoan.mature" value="${packingLoan.mature}" <fisc:authentication sourceName="PackingLoan.mature" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.dealdate" nodeId="${workflowNodeDefId}"/> >${vt.property.dealdate}：</td>
		<td   width="40%" >
			<input type="text" id="PackingLoan.dealdate" name="PackingLoan.dealdate" value="">
				<fisc:calendar applyTo="PackingLoan.dealdate"  divId="" fieldName="" defaultValue="${packingLoan.dealdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.interestrate" nodeId="${workflowNodeDefId}"/> >${vt.property.interestrate}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PackingLoan.interestrate" name="PackingLoan.interestrate" value="${packingLoan.interestrate}" <fisc:authentication sourceName="PackingLoan.interestrate" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.packingreate" nodeId="${workflowNodeDefId}"/> >${vt.property.packingreate}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PackingLoan.packingreate" name="PackingLoan.packingreate" value="${packingLoan.packingreate}" <fisc:authentication sourceName="PackingLoan.packingreate" nodeId="${workflowNodeDefId}"/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.clearvendorvalue" nodeId="${workflowNodeDefId}"/> >${vt.property.clearvendorvalue}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="PackingLoan.clearvendorvalue" name="PackingLoan.clearvendorvalue" value="${packingLoan.clearvendorvalue}" <fisc:authentication sourceName="PackingLoan.clearvendorvalue" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.repackingreate" nodeId="${workflowNodeDefId}"/> >${vt.property.repackingreate}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="PackingLoan.repackingreate" name="PackingLoan.repackingreate" value="${packingLoan.repackingreate}" <fisc:authentication sourceName="PackingLoan.repackingreate" nodeId="${workflowNodeDefId}"/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.voucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherdate}：</td>
		<td  width="30%" >
			<input type="text" id="PackingLoan.voucherdate" name="PackingLoan.voucherdate" value="">
				<fisc:calendar applyTo="PackingLoan.voucherdate"  divId="" fieldName="" defaultValue="${packingLoan.voucherdate}"></fisc:calendar>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.accountdate" nodeId="${workflowNodeDefId}"/> >${vt.property.accountdate}：</td>
		<td   width="40%" >
			<input type="text" id="PackingLoan.accountdate" name="PackingLoan.accountdate" value="">
				<fisc:calendar applyTo="PackingLoan.accountdate"  divId="" fieldName="" defaultValue="${packingLoan.accountdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.text" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.text}：</td>
		<td  width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="PackingLoan.text" name="PackingLoan.text" <fisc:authentication sourceName="PackingLoan.text" nodeId="${workflowNodeDefId}"/>>${packingLoan.text}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.revoucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.revoucherdate}：</td>
		<td  width="30%" >
			<input type="text" id="PackingLoan.revoucherdate" name="PackingLoan.revoucherdate" value="">
				<fisc:calendar applyTo="PackingLoan.revoucherdate"  divId="" fieldName="" defaultValue="${packingLoan.revoucherdate}"></fisc:calendar>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.reaccountdate" nodeId="${workflowNodeDefId}"/> >${vt.property.reaccountdate}：</td>
		<td   width="40%" >
			<input type="text" id="PackingLoan.reaccountdate" name="PackingLoan.reaccountdate" value="">
				<fisc:calendar applyTo="PackingLoan.reaccountdate"  divId="" fieldName="" defaultValue="${packingLoan.reaccountdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.retext" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.retext}：</td>
		<td  width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="PackingLoan.retext" name="PackingLoan.retext" <fisc:authentication sourceName="PackingLoan.retext" nodeId="${workflowNodeDefId}"/>>${packingLoan.retext}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.remark" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.remark}：</td>
		<td  width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="PackingLoan.remark" name="PackingLoan.remark" <fisc:authentication sourceName="PackingLoan.remark" nodeId="${workflowNodeDefId}"/>>${packingLoan.remark}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td  width="30%" >
		    <input type="text" class="inputText" id="PackingLoan.createtime" name="PackingLoan.createtime" value="${packingLoan.createtime}"  readonly="readonly" <fisc:authentication sourceName="PackingLoan.createtime" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td   width="40%" >
			<fisc:user boProperty="creator" boName="PackingLoan" userId="${packingLoan.creator}"></fisc:user>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="PackingLoan.dept_id" nodeId="${workflowNodeDefId}"/> >${vt.property.dept_id}：</td>
		<td  width="30%" >
			<div id="div_dept_id_sh"></div>
			<fisc:searchHelp divId="div_dept_id_sh" boName="PackingLoan" boProperty="dept_id" value="${packingLoan.dept_id}"></fisc:searchHelp>
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="PackingLoan.client" name="PackingLoan.client" value="${packingLoan.client}">
	<input type="hidden" id="PackingLoan.packingid" name="PackingLoan.packingid" value="${packingLoan.packingid}">
	<input type="hidden" id="PackingLoan.businessstate" name="PackingLoan.businessstate" value="${packingLoan.businessstate}">
	<input type="hidden" id="PackingLoan.processstate" name="PackingLoan.processstate" value="${packingLoan.processstate}">
	<input type="hidden" id="PackingLoan.lastmodiyer" name="PackingLoan.lastmodiyer" value="${packingLoan.lastmodiyer}">
	<input type="hidden" id="PackingLoan.lastmodifytime" name="PackingLoan.lastmodifytime" value="${packingLoan.lastmodifytime}">
	<input type="hidden" id="calActivityId" name="calActivityId" value="${calActivityId}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_packingBankItem" ></div>
<div id="div_packingReBankItem" ></div>
<div id="div_packingReBankTwo" ></div>
<div id="div_settleSubject" class="x-hide-display">
<form id="settleSubjectForm" name="settleSubjectForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.debtcredit1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit1}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit1_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit1_dict" isNeedAuth="false" value="${packingLoan.settleSubject.debtcredit1}" ></fisc:dictionary>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.amount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount1" name="SettleSubject.amount1" value="${packingLoan.settleSubject.amount1}" <fisc:authentication sourceName="SettleSubject.amount1" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.standardamount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount1}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount1" name="SettleSubject.standardamount1" value="${packingLoan.settleSubject.standardamount1}" <fisc:authentication sourceName="SettleSubject.standardamount1" nodeId="${workflowNodeDefId}"/>>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.settlesubject1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject1}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject1_sh" boName="SettleSubject" boProperty="settlesubject1" value="${packingLoan.settleSubject.settlesubject1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.costcenter1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter1}：</td>
<td width="30%" >
<div id="div_SettleSubjectcostcenter1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter1_sh" boName="SettleSubject" boProperty="costcenter1" value="${packingLoan.settleSubject.costcenter1}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.depid1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid1}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid1_sh" boName="SettleSubject" boProperty="depid1" value="${packingLoan.settleSubject.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.orderno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno1}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno1_sh" boName="SettleSubject" boProperty="orderno1" value="${packingLoan.settleSubject.orderno1}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.rowno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno1" name="SettleSubject.rowno1" value="${packingLoan.settleSubject.rowno1}" <fisc:authentication sourceName="SettleSubject.rowno1" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.antiaccount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount1}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount1_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount1_dict" isNeedAuth="false" value="${packingLoan.settleSubject.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.debtcredit2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit2}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit2_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit2_dict" isNeedAuth="false" value="${packingLoan.settleSubject.debtcredit2}" ></fisc:dictionary>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.amount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount2" name="SettleSubject.amount2" value="${packingLoan.settleSubject.amount2}" <fisc:authentication sourceName="SettleSubject.amount2" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.standardamount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount2}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount2" name="SettleSubject.standardamount2" value="${packingLoan.settleSubject.standardamount2}" <fisc:authentication sourceName="SettleSubject.standardamount2" nodeId="${workflowNodeDefId}"/>>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.settlesubject2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject2}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject2_sh" boName="SettleSubject" boProperty="settlesubject2" value="${packingLoan.settleSubject.settlesubject2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.costcenter2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter2}：</td>
<td width="30%" >
<div id="div_SettleSubjectcostcenter2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter2_sh" boName="SettleSubject" boProperty="costcenter2" value="${packingLoan.settleSubject.costcenter2}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.depid2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid2}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid2_sh" boName="SettleSubject" boProperty="depid2" value="${packingLoan.settleSubject.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.orderno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno2}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno2_sh" boName="SettleSubject" boProperty="orderno2" value="${packingLoan.settleSubject.orderno2}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.rowno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno2" name="SettleSubject.rowno2" value="${packingLoan.settleSubject.rowno2}" <fisc:authentication sourceName="SettleSubject.rowno2" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.antiaccount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount2}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount2_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount2_dict" isNeedAuth="false" value="${packingLoan.settleSubject.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.debtcredit3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit3}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit3_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit3_dict" isNeedAuth="false" value="${packingLoan.settleSubject.debtcredit3}" ></fisc:dictionary>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.amount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount3" name="SettleSubject.amount3" value="${packingLoan.settleSubject.amount3}" <fisc:authentication sourceName="SettleSubject.amount3" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.standardamount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount3}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount3" name="SettleSubject.standardamount3" value="${packingLoan.settleSubject.standardamount3}" <fisc:authentication sourceName="SettleSubject.standardamount3" nodeId="${workflowNodeDefId}"/>>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.settlesubject3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject3}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject3_sh" boName="SettleSubject" boProperty="settlesubject3" value="${packingLoan.settleSubject.settlesubject3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.costcenter3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter3}：</td>
<td width="30%" >
<div id="div_SettleSubjectcostcenter3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter3_sh" boName="SettleSubject" boProperty="costcenter3" value="${packingLoan.settleSubject.costcenter3}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.depid3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid3}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid3_sh" boName="SettleSubject" boProperty="depid3" value="${packingLoan.settleSubject.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.orderno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno3}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno3_sh" boName="SettleSubject" boProperty="orderno3" value="${packingLoan.settleSubject.orderno3}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.rowno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno3" name="SettleSubject.rowno3" value="${packingLoan.settleSubject.rowno3}" <fisc:authentication sourceName="SettleSubject.rowno3" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.antiaccount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount3}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount3_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount3_dict" isNeedAuth="false" value="${packingLoan.settleSubject.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.debtcredit4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit4}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit4_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit4" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit4_dict" isNeedAuth="false" value="${packingLoan.settleSubject.debtcredit4}" ></fisc:dictionary>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.amount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount4}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount4" name="SettleSubject.amount4" value="${packingLoan.settleSubject.amount4}" <fisc:authentication sourceName="SettleSubject.amount4" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.standardamount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount4}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount4" name="SettleSubject.standardamount4" value="${packingLoan.settleSubject.standardamount4}" <fisc:authentication sourceName="SettleSubject.standardamount4" nodeId="${workflowNodeDefId}"/>>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.settlesubject4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject4}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject4_sh" boName="SettleSubject" boProperty="settlesubject4" value="${packingLoan.settleSubject.settlesubject4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.profitcenter" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.profitcenter}：</td>
<td width="30%" >
<div id="div_SettleSubjectprofitcenter_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectprofitcenter_sh" boName="SettleSubject" boProperty="profitcenter" value="${packingLoan.settleSubject.profitcenter}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.depid4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid4}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid4_sh" boName="SettleSubject" boProperty="depid4" value="${packingLoan.settleSubject.depid4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.orderno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno4}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno4_sh" boName="SettleSubject" boProperty="orderno4" value="${packingLoan.settleSubject.orderno4}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.rowno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno4}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno4" name="SettleSubject.rowno4" value="${packingLoan.settleSubject.rowno4}" <fisc:authentication sourceName="SettleSubject.rowno4" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.antiaccount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount4}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount4_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount4" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount4_dict" isNeedAuth="false" value="${packingLoan.settleSubject.antiaccount4}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<input type="hidden" id="SettleSubject.settlesubjectid" name="SettleSubject.settlesubjectid" value="${packingLoan.settleSubject.settlesubjectid}">
<input type="hidden" id="SettleSubject.flag" name="SettleSubject.flag" value="${packingLoan.settleSubject.flag}">
</table>
</form>
</div>
<div id="div_fundFlow" class="x-hide-display">
<form id="fundFlowForm" name="fundFlowForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.debtcredit1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit1}：</td>
<td width="40%" >
<div id="div_FundFlowdebtcredit1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit1_dict" isNeedAuth="false" value="${packingLoan.fundFlow.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.amount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlow.amount1" name="FundFlow.amount1" value="${packingLoan.fundFlow.amount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.standardamount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount1}：</td>
<td width="30%">
<input type="text" class="inputText" id="FundFlow.standardamount1" name="FundFlow.standardamount1" value="${packingLoan.fundFlow.standardamount1}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.specialaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount1}： </td>
<td width="40%">
<div id="div_FundFlowspecialaccount1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount1" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount1_dict" isNeedAuth="false" value="${packingLoan.fundFlow.specialaccount1}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.customer1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer1}： </td>
<td width="30%">
<div id="div_FundFlowcustomer1_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer1_sh" boName="FundFlow" boProperty="customer1" value="${packingLoan.fundFlow.customer1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.depid1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid1}： </td>
<td width="40%">
<div id="div_FundFlowdepid1_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid1_sh" boName="FundFlow" boProperty="depid1" value="${packingLoan.fundFlow.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.antiaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount1}： </td>
<td width="30%">
<div id="div_FundFlowantiaccount1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount1_dict" isNeedAuth="false" value="${packingLoan.fundFlow.antiaccount1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.debtcredit2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit2}： </td>
<td width="40%">
<div id="div_FundFlowdebtcredit2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit2_dict" isNeedAuth="false" value="${packingLoan.fundFlow.debtcredit2}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.amount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount2}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlow.amount2" name="FundFlow.amount2" value="${packingLoan.fundFlow.amount2}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.standardamount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlow.standardamount2" name="FundFlow.standardamount2" value="${packingLoan.fundFlow.standardamount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.specialaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount2}： </td>
<td width="30%">
<div id="div_FundFlowspecialaccount2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount2" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount2_dict" isNeedAuth="false" value="${packingLoan.fundFlow.specialaccount2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.customer2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer2}： </td>
<td width="40%">
<div id="div_FundFlowcustomer2_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer2_sh" boName="FundFlow" boProperty="customer2" value="${packingLoan.fundFlow.customer2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.depid2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid2}： </td>
<td width="30%">
<div id="div_FundFlowdepid2_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid2_sh" boName="FundFlow" boProperty="depid2" value="${packingLoan.fundFlow.depid2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.antiaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount2}： </td>
<td width="40%">
<div id="div_FundFlowantiaccount2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount2_dict" isNeedAuth="false" value="${packingLoan.fundFlow.antiaccount2}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.debtcredit3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit3}： </td>
<td width="30%">
<div id="div_FundFlowdebtcredit3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit3_dict" isNeedAuth="false" value="${packingLoan.fundFlow.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.amount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlow.amount3" name="FundFlow.amount3" value="${packingLoan.fundFlow.amount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.standardamount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount3}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlow.standardamount3" name="FundFlow.standardamount3" value="${packingLoan.fundFlow.standardamount3}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.specialaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount3}： </td>
<td width="40%">
<div id="div_FundFlowspecialaccount3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount3" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount3_dict" isNeedAuth="false" value="${packingLoan.fundFlow.specialaccount3}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.customer3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer3}： </td>
<td width="30%">
<div id="div_FundFlowcustomer3_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer3_sh" boName="FundFlow" boProperty="customer3" value="${packingLoan.fundFlow.customer3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.depid3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid3}： </td>
<td width="40%">
<div id="div_FundFlowdepid3_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid3_sh" boName="FundFlow" boProperty="depid3" value="${packingLoan.fundFlow.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.antiaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount3}： </td>
<td width="30%">
<div id="div_FundFlowantiaccount3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount3_dict" isNeedAuth="false" value="${packingLoan.fundFlow.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
</table>
<input type="hidden" id="FundFlow.fundflowid" name="FundFlow.fundflowid" value="${packingLoan.fundFlow.fundflowid}">
<input type="hidden" id="FundFlow.flag" name="FundFlow.flag" value="${packingLoan.fundFlow.flag}">
</table>
</form>
</div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/packingloan/packingLoanAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/packingloan/packingLoanAddGen.js"></script>

<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//是否已经提交流程
var isSubmited = '${packingLoan.processstate}';

//页面文本
var Txt={
	//打包贷款
	packingLoan:'${vt.boText}',
	          
	//打包贷款银行
	packingBankItem:'${packingBankItemVt.boText}',
	// 请选择需要进行【打包贷款银行批量删除】操作的记录！
	packingBankItem_Deletes_SelectToOperation:'${packingBankItemVt.deletes_SelectToOperate}',
	// 您选择了【打包贷款银行批量删除】操作，是否确定继续该操作？
	packingBankItem_Deletes_ConfirmOperation:'${packingBankItemVt.deletes_ConfirmOperation}',
          
	//还打包贷款银行
	packingReBankItem:'${packingReBankItemVt.boText}',
	// 请选择需要进行【还打包贷款银行批量删除】操作的记录！
	packingReBankItem_Deletes_SelectToOperation:'${packingReBankItemVt.deletes_SelectToOperate}',
	// 您选择了【还打包贷款银行批量删除】操作，是否确定继续该操作？
	packingReBankItem_Deletes_ConfirmOperation:'${packingReBankItemVt.deletes_ConfirmOperation}',
          
	//还打包银行2
	packingReBankTwo:'${packingReBankTwoVt.boText}',
          
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
	packingLoan_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	packingLoan_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	packingLoan_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:0,
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
						            		title:'${packingBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'packingBankItemTab',
						            		contentEl:'div_packingBankItem'
						            	},
          						                {
						            		title:'${packingReBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'packingReBankItemTab',
						            		contentEl:'div_packingReBankItem'
						            	},
          						                {
						            		title:'${packingReBankTwoVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'packingReBankTwoTab',
						            		contentEl:'div_packingReBankTwo'
						            	},
          						                {
						            		title:'${settleSubjectVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'settleSubjectTab',
						            		contentEl:'div_settleSubject'
						            	},
          						                {
						            		title:'${fundFlowVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'fundFlowTab',
						            		contentEl:'div_fundFlow'
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
					'-','->','-',
					          
          
          
          
					{id:'_saveOrUpdatePackingLoan',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdatePackingLoan,iconCls:'icon-table-save'},'-',
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},'-',
          
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
					{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessPackingLoan,disabled:true,iconCls:'task'},'-',					
<%}%>		
          
          
					          
          
          
          
          
          
          

					' '],  
			renderTo:"div_toolbar"
	});
	
	
	var isTask = '${workflowTaskId}';
	if(!isTask||isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited&&isSubmited!=''&&isSubmited!=' '){
Ext.getCmp('_saveOrUpdate').disable();
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

Ext.onReady(function(){
    var tabsSize = 5;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < tabsSize ; i++){
		   tabs.setActiveTab(tabsSize-1-i);
		}
	}
 });
//});
</script>
