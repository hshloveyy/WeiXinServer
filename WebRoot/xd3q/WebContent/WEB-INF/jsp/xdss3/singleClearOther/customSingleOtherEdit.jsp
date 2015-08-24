<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2012年06月11日 09点24分58秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象其他公司客户单清帐(CustomSingleOther)编辑页面
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
<fisc:grid divId="div_unCustomBillOther" boName="UnCustomBillOther" needCheckBox="true" editable="true" defaultCondition=" YUNCUSTBILLOTHER.CUSTOMSCLEARID='${customSingleOther.customsclearid}'" needAutoLoad="true" height="285" nameSapceSupport="true" orderBySql=" YUNCUSTBILLOTHER.BWBJE ,YUNCUSTBILLOTHER.UNBWBJE " title="借贷" ></fisc:grid>
<fisc:grid divId="div_unCleaCollectOther" boName="UnCleaCollectOther" needCheckBox="true" editable="true" defaultCondition=" YUNCOLLECTOTHER.CUSTOMSCLEARID='${customSingleOther.customsclearid}'" needAutoLoad="true" height="285" nameSapceSupport="true" title="借贷" ></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${customSingleOther.customsclearid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomSingleOther.custom" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.custom}：</td>
		<td width="30%">
			<div id="div_custom_sh"></div>
			<fisc:searchHelp divId="div_custom_sh" boName="CustomSingleOther" boProperty="custom"  value="${customSingleOther.custom}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomSingleOther.subject" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.subject}：</td>
		<td  width="40%">
			<div id="div_subject_sh"></div>
			<fisc:searchHelp divId="div_subject_sh" boName="CustomSingleOther" boProperty="subject"  value="${customSingleOther.subject}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomSingleOther.currencytext" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.currencytext}：</td>
		<td width="30%">
			<div id="div_currencytext_sh"></div>
			<fisc:searchHelp divId="div_currencytext_sh" boName="CustomSingleOther" boProperty="currencytext"  value="${customSingleOther.currencytext}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomSingleOther.companyno" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.companyno}：</td>
		<td  width="40%">
			<div id="div_companyno_sh"></div>
			<fisc:searchHelp divId="div_companyno_sh" boName="CustomSingleOther" boProperty="companyno"  value="${customSingleOther.companyno}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomSingleOther.text" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.text}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="CustomSingleOther.text" name="CustomSingleOther.text" value="${customSingleOther.text}"   <fisc:authentication sourceName="CustomSingleOther.text" nodeId="${workflowNodeDefId}"/>>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomSingleOther.remark" nodeId="${workflowNodeDefId}"/> >${vt.property.remark}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="CustomSingleOther.remark" name="CustomSingleOther.remark" value="${customSingleOther.remark}"   <fisc:authentication sourceName="CustomSingleOther.remark" nodeId="${workflowNodeDefId}"/>>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomSingleOther.accountdate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.accountdate}：</td>
		<td width="30%">
			<input type="text" id="CustomSingleOther.accountdate" name="CustomSingleOther.accountdate" value="">
				<fisc:calendar applyTo="CustomSingleOther.accountdate"  divId="" fieldName="" defaultValue="${customSingleOther.accountdate}"></fisc:calendar>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomSingleOther.voucherdate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.voucherdate}：</td>
		<td  width="40%">
			<input type="text" id="CustomSingleOther.voucherdate" name="CustomSingleOther.voucherdate" value="">
				<fisc:calendar applyTo="CustomSingleOther.voucherdate"  divId="" fieldName="" defaultValue="${customSingleOther.voucherdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomSingleOther.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="CustomSingleOther" userId="${customSingleOther.creator}"></fisc:user>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomSingleOther.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td  width="40%">
			<input type="text" id="CustomSingleOther.createtime" name="CustomSingleOther.createtime" value="${customSingleOther.createtime}">
				
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomSingleOther.customclearno" nodeId="${workflowNodeDefId}"/> >${vt.property.customclearno}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="CustomSingleOther.customclearno" name="CustomSingleOther.customclearno" value="${customSingleOther.customclearno}"   readonly="readonly" <fisc:authentication sourceName="CustomSingleOther.customclearno" nodeId="${workflowNodeDefId}"/>>
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="CustomSingleOther.client" name="CustomSingleOther.client" value="${customSingleOther.client}">
	<input type="hidden" id="CustomSingleOther.customsclearid" name="CustomSingleOther.customsclearid" value="${customSingleOther.customsclearid}">
	<input type="hidden" id="CustomSingleOther.depid" name="CustomSingleOther.depid" value="${customSingleOther.depid}">
	<input type="hidden" id="CustomSingleOther.businessstate" name="CustomSingleOther.businessstate" value="${customSingleOther.businessstate}">
	<input type="hidden" id="CustomSingleOther.processstate" name="CustomSingleOther.processstate" value="${customSingleOther.processstate}">
	<input type="hidden" id="CustomSingleOther.lastmodifytime" name="CustomSingleOther.lastmodifytime" value="${customSingleOther.lastmodifytime}">
	<input type="hidden" id="CustomSingleOther.lastmodifyer" name="CustomSingleOther.lastmodifyer" value="${customSingleOther.lastmodifyer}">
	<input type="hidden" id="CustomSingleOther.deptid" name="CustomSingleOther.deptid" value="${customSingleOther.deptid}">
	<input type="hidden" id="CustomSingleOther.specialaccount" name="CustomSingleOther.specialaccount" value="${customSingleOther.specialaccount}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_unCustomBillOther"></div>
<div id="div_unCleaCollectOther"></div>
<div id="div_settleSubjectOther" class="x-hide-display" >
<form id="settleSubjectOtherForm" name="settleSubjectOtherForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.debtcredit1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.debtcredit1}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherdebtcredit1_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectOtherdebtcredit1_dict" isNeedAuth="false" value="${customSingleOther.settleSubjectOther.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.amount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.amount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.amount1" name="SettleSubjectOther.amount1" value="${customSingleOther.settleSubjectOther.amount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.standardamount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.standardamount1}： </td>
<td width="30%">
<input type="text" class="inputText" id="SettleSubjectOther.standardamount1" name="SettleSubjectOther.standardamount1" value="${customSingleOther.settleSubjectOther.standardamount1}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.settlesubject1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.settlesubject1}： </td>
<td width="40%">
<div id="div_SettleSubjectOthersettlesubject1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthersettlesubject1_sh" boName="SettleSubjectOther" boProperty="settlesubject1" value="${customSingleOther.settleSubjectOther.settlesubject1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.costcenter1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.costcenter1}： </td>
<td width="30%">
<div id="div_SettleSubjectOthercostcenter1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthercostcenter1_sh" boName="SettleSubjectOther" boProperty="costcenter1" value="${customSingleOther.settleSubjectOther.costcenter1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.depid1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.depid1}： </td>
<td width="40%">
<div id="div_SettleSubjectOtherdepid1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherdepid1_sh" boName="SettleSubjectOther" boProperty="depid1" value="${customSingleOther.settleSubjectOther.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.orderno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.orderno1}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherorderno1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherorderno1_sh" boName="SettleSubjectOther" boProperty="orderno1" value="${customSingleOther.settleSubjectOther.orderno1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.rowno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.rowno1}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.rowno1" name="SettleSubjectOther.rowno1" value="${customSingleOther.settleSubjectOther.rowno1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.antiaccount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.antiaccount1}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherantiaccount1_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_SettleSubjectOtherantiaccount1_dict" isNeedAuth="false" value="${customSingleOther.settleSubjectOther.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.debtcredit2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.debtcredit2}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherdebtcredit2_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectOtherdebtcredit2_dict" isNeedAuth="false" value="${customSingleOther.settleSubjectOther.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.amount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.amount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.amount2" name="SettleSubjectOther.amount2" value="${customSingleOther.settleSubjectOther.amount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.standardamount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.standardamount2}： </td>
<td width="30%">
<input type="text" class="inputText" id="SettleSubjectOther.standardamount2" name="SettleSubjectOther.standardamount2" value="${customSingleOther.settleSubjectOther.standardamount2}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.settlesubject2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.settlesubject2}： </td>
<td width="40%">
<div id="div_SettleSubjectOthersettlesubject2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthersettlesubject2_sh" boName="SettleSubjectOther" boProperty="settlesubject2" value="${customSingleOther.settleSubjectOther.settlesubject2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.costcenter2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.costcenter2}： </td>
<td width="30%">
<div id="div_SettleSubjectOthercostcenter2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthercostcenter2_sh" boName="SettleSubjectOther" boProperty="costcenter2" value="${customSingleOther.settleSubjectOther.costcenter2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.depid2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.depid2}： </td>
<td width="40%">
<div id="div_SettleSubjectOtherdepid2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherdepid2_sh" boName="SettleSubjectOther" boProperty="depid2" value="${customSingleOther.settleSubjectOther.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.orderno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.orderno2}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherorderno2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherorderno2_sh" boName="SettleSubjectOther" boProperty="orderno2" value="${customSingleOther.settleSubjectOther.orderno2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.rowno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.rowno2}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.rowno2" name="SettleSubjectOther.rowno2" value="${customSingleOther.settleSubjectOther.rowno2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.antiaccount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.antiaccount2}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherantiaccount2_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_SettleSubjectOtherantiaccount2_dict" isNeedAuth="false" value="${customSingleOther.settleSubjectOther.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.amount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.amount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.amount3" name="SettleSubjectOther.amount3" value="${customSingleOther.settleSubjectOther.amount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.debtcredit3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.debtcredit3}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherdebtcredit3_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectOtherdebtcredit3_dict" isNeedAuth="false" value="${customSingleOther.settleSubjectOther.debtcredit3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.standardamount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.standardamount3}： </td>
<td width="30%">
<input type="text" class="inputText" id="SettleSubjectOther.standardamount3" name="SettleSubjectOther.standardamount3" value="${customSingleOther.settleSubjectOther.standardamount3}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.settlesubject3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.settlesubject3}： </td>
<td width="40%">
<div id="div_SettleSubjectOthersettlesubject3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthersettlesubject3_sh" boName="SettleSubjectOther" boProperty="settlesubject3" value="${customSingleOther.settleSubjectOther.settlesubject3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.costcenter3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.costcenter3}： </td>
<td width="30%">
<div id="div_SettleSubjectOthercostcenter3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthercostcenter3_sh" boName="SettleSubjectOther" boProperty="costcenter3" value="${customSingleOther.settleSubjectOther.costcenter3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.depid3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.depid3}： </td>
<td width="40%">
<div id="div_SettleSubjectOtherdepid3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherdepid3_sh" boName="SettleSubjectOther" boProperty="depid3" value="${customSingleOther.settleSubjectOther.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.orderno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.orderno3}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherorderno3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherorderno3_sh" boName="SettleSubjectOther" boProperty="orderno3" value="${customSingleOther.settleSubjectOther.orderno3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.rowno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.rowno3}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.rowno3" name="SettleSubjectOther.rowno3" value="${customSingleOther.settleSubjectOther.rowno3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.antiaccount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.antiaccount3}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherantiaccount3_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_SettleSubjectOtherantiaccount3_dict" isNeedAuth="false" value="${customSingleOther.settleSubjectOther.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.debtcredit4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.debtcredit4}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherdebtcredit4_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="debtcredit4" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectOtherdebtcredit4_dict" isNeedAuth="false" value="${customSingleOther.settleSubjectOther.debtcredit4}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.amount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.amount4}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.amount4" name="SettleSubjectOther.amount4" value="${customSingleOther.settleSubjectOther.amount4}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.standardamount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.standardamount4}： </td>
<td width="30%">
<input type="text" class="inputText" id="SettleSubjectOther.standardamount4" name="SettleSubjectOther.standardamount4" value="${customSingleOther.settleSubjectOther.standardamount4}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.settlesubject4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.settlesubject4}： </td>
<td width="40%">
<div id="div_SettleSubjectOthersettlesubject4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthersettlesubject4_sh" boName="SettleSubjectOther" boProperty="settlesubject4" value="${customSingleOther.settleSubjectOther.settlesubject4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.profitcenter" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.profitcenter}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherprofitcenter_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherprofitcenter_sh" boName="SettleSubjectOther" boProperty="profitcenter" value="${customSingleOther.settleSubjectOther.profitcenter}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.depid4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.depid4}： </td>
<td width="40%">
<div id="div_SettleSubjectOtherdepid4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherdepid4_sh" boName="SettleSubjectOther" boProperty="depid4" value="${customSingleOther.settleSubjectOther.depid4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.orderno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.orderno4}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherorderno4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherorderno4_sh" boName="SettleSubjectOther" boProperty="orderno4" value="${customSingleOther.settleSubjectOther.orderno4}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.rowno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.rowno4}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.rowno4" name="SettleSubjectOther.rowno4" value="${customSingleOther.settleSubjectOther.rowno4}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.antiaccount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.antiaccount4}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherantiaccount4_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="antiaccount4" dictionaryName="YDYESORNO" divId="div_SettleSubjectOtherantiaccount4_dict" isNeedAuth="false" value="${customSingleOther.settleSubjectOther.antiaccount4}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
</table>
<input type="hidden" id="SettleSubjectOther.settlesubjectid" name="SettleSubjectOther.settlesubjectid" value="${customSingleOther.settleSubjectOther.settlesubjectid}">
</form>
</div>
<div id="div_fundFlowOther" class="x-hide-display" >
<form id="fundFlowOtherForm" name="fundFlowOtherForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.debtcredit1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.debtcredit1}： </td>
<td width="30%">
<div id="div_FundFlowOtherdebtcredit1_dict"></div>
<fisc:dictionary boName="FundFlowOther" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowOtherdebtcredit1_dict" isNeedAuth="false" value="${customSingleOther.fundFlowOther.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.amount1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.amount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlowOther.amount1" name="FundFlowOther.amount1" value="${customSingleOther.fundFlowOther.amount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.standardamount1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.standardamount1}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlowOther.standardamount1" name="FundFlowOther.standardamount1" value="${customSingleOther.fundFlowOther.standardamount1}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.specialaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.specialaccount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlowOther.specialaccount1" name="FundFlowOther.specialaccount1" value="${customSingleOther.fundFlowOther.specialaccount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.customer1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.customer1}： </td>
<td width="30%">
<div id="div_FundFlowOthercustomer1_sh"></div>
<fisc:searchHelp divId="div_FundFlowOthercustomer1_sh" boName="FundFlowOther" boProperty="customer1" value="${customSingleOther.fundFlowOther.customer1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.depid1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.depid1}： </td>
<td width="40%">
<div id="div_FundFlowOtherdepid1_sh"></div>
<fisc:searchHelp divId="div_FundFlowOtherdepid1_sh" boName="FundFlowOther" boProperty="depid1" value="${customSingleOther.fundFlowOther.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.antiaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.antiaccount1}： </td>
<td width="30%">
<div id="div_FundFlowOtherantiaccount1_dict"></div>
<fisc:dictionary boName="FundFlowOther" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_FundFlowOtherantiaccount1_dict" isNeedAuth="false" value="${customSingleOther.fundFlowOther.antiaccount1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.supplier1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.supplier1}： </td>
<td width="40%">
<div id="div_FundFlowOthersupplier1_sh"></div>
<fisc:searchHelp divId="div_FundFlowOthersupplier1_sh" boName="FundFlowOther" boProperty="supplier1" value="${customSingleOther.fundFlowOther.supplier1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.debtcredit2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.debtcredit2}： </td>
<td width="30%">
<div id="div_FundFlowOtherdebtcredit2_dict"></div>
<fisc:dictionary boName="FundFlowOther" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowOtherdebtcredit2_dict" isNeedAuth="false" value="${customSingleOther.fundFlowOther.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.amount2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.amount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlowOther.amount2" name="FundFlowOther.amount2" value="${customSingleOther.fundFlowOther.amount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.standardamount2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.standardamount2}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlowOther.standardamount2" name="FundFlowOther.standardamount2" value="${customSingleOther.fundFlowOther.standardamount2}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.specialaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.specialaccount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlowOther.specialaccount2" name="FundFlowOther.specialaccount2" value="${customSingleOther.fundFlowOther.specialaccount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.customer2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.customer2}： </td>
<td width="30%">
<div id="div_FundFlowOthercustomer2_sh"></div>
<fisc:searchHelp divId="div_FundFlowOthercustomer2_sh" boName="FundFlowOther" boProperty="customer2" value="${customSingleOther.fundFlowOther.customer2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.depid2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.depid2}： </td>
<td width="40%">
<div id="div_FundFlowOtherdepid2_sh"></div>
<fisc:searchHelp divId="div_FundFlowOtherdepid2_sh" boName="FundFlowOther" boProperty="depid2" value="${customSingleOther.fundFlowOther.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.antiaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.antiaccount2}： </td>
<td width="30%">
<div id="div_FundFlowOtherantiaccount2_dict"></div>
<fisc:dictionary boName="FundFlowOther" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_FundFlowOtherantiaccount2_dict" isNeedAuth="false" value="${customSingleOther.fundFlowOther.antiaccount2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.supplier2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.supplier2}： </td>
<td width="40%">
<div id="div_FundFlowOthersupplier2_sh"></div>
<fisc:searchHelp divId="div_FundFlowOthersupplier2_sh" boName="FundFlowOther" boProperty="supplier2" value="${customSingleOther.fundFlowOther.supplier2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.debtcredit3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.debtcredit3}： </td>
<td width="30%">
<div id="div_FundFlowOtherdebtcredit3_dict"></div>
<fisc:dictionary boName="FundFlowOther" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowOtherdebtcredit3_dict" isNeedAuth="false" value="${customSingleOther.fundFlowOther.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.amount3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.amount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlowOther.amount3" name="FundFlowOther.amount3" value="${customSingleOther.fundFlowOther.amount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.standardamount3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.standardamount3}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlowOther.standardamount3" name="FundFlowOther.standardamount3" value="${customSingleOther.fundFlowOther.standardamount3}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.specialaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.specialaccount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlowOther.specialaccount3" name="FundFlowOther.specialaccount3" value="${customSingleOther.fundFlowOther.specialaccount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.customer3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.customer3}： </td>
<td width="30%">
<div id="div_FundFlowOthercustomer3_sh"></div>
<fisc:searchHelp divId="div_FundFlowOthercustomer3_sh" boName="FundFlowOther" boProperty="customer3" value="${customSingleOther.fundFlowOther.customer3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.depid3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.depid3}： </td>
<td width="40%">
<div id="div_FundFlowOtherdepid3_sh"></div>
<fisc:searchHelp divId="div_FundFlowOtherdepid3_sh" boName="FundFlowOther" boProperty="depid3" value="${customSingleOther.fundFlowOther.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.antiaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.antiaccount3}： </td>
<td width="30%">
<div id="div_FundFlowOtherantiaccount3_dict"></div>
<fisc:dictionary boName="FundFlowOther" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_FundFlowOtherantiaccount3_dict" isNeedAuth="false" value="${customSingleOther.fundFlowOther.antiaccount3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.supplier3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.supplier3}： </td>
<td width="40%">
<div id="div_FundFlowOthersupplier3_sh"></div>
<fisc:searchHelp divId="div_FundFlowOthersupplier3_sh" boName="FundFlowOther" boProperty="supplier3" value="${customSingleOther.fundFlowOther.supplier3}"></fisc:searchHelp>
</td>
</tr>
</table>
<input type="hidden" id="FundFlowOther.fundflowid" name="FundFlowOther.fundflowid" value="${customSingleOther.fundFlowOther.fundflowid}">
</form>
</div>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/singleClearOther/customSingleOtherEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/singleClearOther/customSingleOtherEditGen.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${customSingleOther.processstate}';
//当前对象主键ID
var customsclearid = '${customSingleOther.customsclearid}';	

//页面文本
var Txt={
	//其他公司客户单清帐
	customSingleOther:'${vt.boText}',
	          
	//其他公司未清应收(借方)
	unCustomBillOther:'${unCustomBillOtherVt.boText}',
          
	//其他公司未清收款(贷方)
	unCleaCollectOther:'${unCleaCollectOtherVt.boText}',
          
	//其他公司结算科目
	settleSubjectOther:'${settleSubjectOtherVt.boText}',
          
	//其他公司纯资金往来
	fundFlowOther:'${fundFlowOtherVt.boText}',
	//boText复制创建
	customSingleOther_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	customSingleOther_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	customSingleOther_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:272.5,
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
						            		title:'${unCustomBillOtherVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_unCustomBillOther'
						            	},
          						                {
						            		title:'${unCleaCollectOtherVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_unCleaCollectOther'
						            	},
          						                {
						            		title:'${settleSubjectOtherVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_settleSubjectOther'
						            	},
          						                {
						            		title:'${fundFlowOtherVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_fundFlowOther'
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
{id:'_saveOrUpdateCustomSingleOther',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateCustomSingleOther,iconCls:'icon-table-save'},'-',
                   
{id:'_queryUnClear',text:'查询',handler:_queryUnClearCustomSingleOther,iconCls:'icon-search'},'-',
{id:'_voucherPreview',text:'模拟凭证',handler:_voucherPreviewCustomSingleOther,iconCls:' '},'-',
' '
],
			renderTo:'div_toolbar'
	});

	var isTask = '${workflowTaskId}';
	if(!isTask || isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited && isSubmited!='' && isSubmited!=' '){
		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
	}
//});
Ext.onReady(function(){
    var tabsSize = 4;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 4 ; i++){
		   tabs.setActiveTab(4-1-i);
		}
	}
 });
</script>
