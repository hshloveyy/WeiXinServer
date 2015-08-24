<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年10月12日 09点30分40秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象票清收款(BillClearCollect)查看页面
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
<fisc:grid divId="div_billClearItem"   pageSize="10000"   boName="BillClearItem" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YBILLCLEARITEM.BILLCLEARID='${billClearCollect.billclearid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_billInCollect"   pageSize="10000"   boName="BillInCollect" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YBILLINCOLLECT.BILLCLEARID='${billClearCollect.billclearid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${billClearCollect.billclearid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillClearCollect.billclearno" nodeId="${workflowNodeDefId}"/> >${vt.property.billclearno}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="BillClearCollect.billclearno" name="BillClearCollect.billclearno" value="${billClearCollect.billclearno}" <fisc:authentication sourceName="BillClearCollect.billclearno" nodeId="${workflowNodeDefId}"/>   readonly="readonly">
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillClearCollect.customer" nodeId="${workflowNodeDefId}"/> >${vt.property.customer}：</td>
		<td  width="40%">
			<div id="div_customer_sh"></div>
			<fisc:searchHelp divId="div_customer_sh" boName="BillClearCollect" boProperty="customer"  value="${billClearCollect.customer}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillClearCollect.text" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.text}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="BillClearCollect.text" name="BillClearCollect.text" <fisc:authentication sourceName="BillClearCollect.text" nodeId="${workflowNodeDefId}"/>>${billClearCollect.text}</textarea>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillClearCollect.remark" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.remark}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="BillClearCollect.remark" name="BillClearCollect.remark" <fisc:authentication sourceName="BillClearCollect.remark" nodeId="${workflowNodeDefId}"/>>${billClearCollect.remark}</textarea>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillClearCollect.accountdate" nodeId="${workflowNodeDefId}"/> >${vt.property.accountdate}：</td>
		<td width="30%">
			<input type="text" id="BillClearCollect.accountdate" name="BillClearCollect.accountdate" value="">
				<fisc:calendar applyTo="BillClearCollect.accountdate"  divId="" fieldName="" defaultValue="${billClearCollect.accountdate}"></fisc:calendar>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillClearCollect.voucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherdate}：</td>
		<td  width="40%">
			<input type="text" id="BillClearCollect.voucherdate" name="BillClearCollect.voucherdate" value="">
				<fisc:calendar applyTo="BillClearCollect.voucherdate"  divId="" fieldName="" defaultValue="${billClearCollect.voucherdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillClearCollect.deptid" nodeId="${workflowNodeDefId}"/> >${vt.property.deptid}：</td>
		<td width="30%">
			<div id="div_deptid_sh"></div>
			<fisc:searchHelp divId="div_deptid_sh" boName="BillClearCollect" boProperty="deptid"  value="${billClearCollect.deptid}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillClearCollect.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td  width="40%">
			<fisc:user boProperty="creator" boName="BillClearCollect" userId="${billClearCollect.creator}"></fisc:user>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="BillClearCollect.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td width="30%">
		    <input type="text" class="inputText" id="BillClearCollect.createtime" name="BillClearCollect.createtime" value="${billClearCollect.createtime}"  readonly="readonly">
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="BillClearCollect.cleartype" name="BillClearCollect.cleartype" value="${billClearCollect.cleartype}">
	<input type="hidden" id="BillClearCollect.businessstate" name="BillClearCollect.businessstate" value="${billClearCollect.businessstate}">
	<input type="hidden" id="BillClearCollect.oldbillclearno" name="BillClearCollect.oldbillclearno" value="${billClearCollect.oldbillclearno}">
	<input type="hidden" id="BillClearCollect.oldbillclearid" name="BillClearCollect.oldbillclearid" value="${billClearCollect.oldbillclearid}">
	<input type="hidden" id="BillClearCollect.processstate" name="BillClearCollect.processstate" value="${billClearCollect.processstate}">
	<input type="hidden" id="BillClearCollect.client" name="BillClearCollect.client" value="${billClearCollect.client}">
	<input type="hidden" id="BillClearCollect.billclearid" name="BillClearCollect.billclearid" value="${billClearCollect.billclearid}">
	<input type="hidden" id="BillClearCollect.lastmodifyer" name="BillClearCollect.lastmodifyer" value="${billClearCollect.lastmodifyer}">
	<input type="hidden" id="BillClearCollect.lastmodifytime" name="BillClearCollect.lastmodifytime" value="${billClearCollect.lastmodifytime}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_settleSubjectBcc" class="x-hide-display">
<form id="settleSubjectBccForm" name="settleSubjectBccForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.debtcredit1" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.debtcredit1}：</td>
<td width="30%" >
<div id="div_SettleSubjectBccdebtcredit1_dict"></div>
<fisc:dictionary boName="SettleSubjectBcc" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectBccdebtcredit1_dict" isNeedAuth="false" value="${billClearCollect.settleSubjectBcc.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.amount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.amount1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubjectBcc.amount1" name="SettleSubjectBcc.amount1" value="${billClearCollect.settleSubjectBcc.amount1}" <fisc:authentication sourceName="SettleSubjectBcc.amount1" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.standardamount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.standardamount1}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubjectBcc.standardamount1" name="SettleSubjectBcc.standardamount1" value="${billClearCollect.settleSubjectBcc.standardamount1}" <fisc:authentication sourceName="SettleSubjectBcc.standardamount1" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.settlesubject1" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.settlesubject1}：</td>
<td width="40%" >
<div id="div_SettleSubjectBccsettlesubject1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectBccsettlesubject1_sh" boName="SettleSubjectBcc" boProperty="settlesubject1" value="${billClearCollect.settleSubjectBcc.settlesubject1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.costcenter1" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.costcenter1}：</td>
<td width="30%" >
<div id="div_SettleSubjectBcccostcenter1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectBcccostcenter1_sh" boName="SettleSubjectBcc" boProperty="costcenter1" value="${billClearCollect.settleSubjectBcc.costcenter1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.depid1" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.depid1}：</td>
<td width="40%" >
<div id="div_SettleSubjectBccdepid1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectBccdepid1_sh" boName="SettleSubjectBcc" boProperty="depid1" value="${billClearCollect.settleSubjectBcc.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.orderno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.orderno1}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubjectBcc.orderno1" name="SettleSubjectBcc.orderno1" value="${billClearCollect.settleSubjectBcc.orderno1}" <fisc:authentication sourceName="SettleSubjectBcc.orderno1" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.rowno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.rowno1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubjectBcc.rowno1" name="SettleSubjectBcc.rowno1" value="${billClearCollect.settleSubjectBcc.rowno1}" <fisc:authentication sourceName="SettleSubjectBcc.rowno1" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.antiaccount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.antiaccount1}：</td>
<td width="30%" >
<div id="div_SettleSubjectBccantiaccount1_dict"></div>
<fisc:dictionary boName="SettleSubjectBcc" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_SettleSubjectBccantiaccount1_dict" isNeedAuth="false" value="${billClearCollect.settleSubjectBcc.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.debtcredit2" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.debtcredit2}：</td>
<td width="30%" >
<div id="div_SettleSubjectBccdebtcredit2_dict"></div>
<fisc:dictionary boName="SettleSubjectBcc" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectBccdebtcredit2_dict" isNeedAuth="false" value="${billClearCollect.settleSubjectBcc.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.amount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.amount2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubjectBcc.amount2" name="SettleSubjectBcc.amount2" value="${billClearCollect.settleSubjectBcc.amount2}" <fisc:authentication sourceName="SettleSubjectBcc.amount2" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.standardamount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.standardamount2}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubjectBcc.standardamount2" name="SettleSubjectBcc.standardamount2" value="${billClearCollect.settleSubjectBcc.standardamount2}" <fisc:authentication sourceName="SettleSubjectBcc.standardamount2" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.settlesubject2" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.settlesubject2}：</td>
<td width="40%" >
<div id="div_SettleSubjectBccsettlesubject2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectBccsettlesubject2_sh" boName="SettleSubjectBcc" boProperty="settlesubject2" value="${billClearCollect.settleSubjectBcc.settlesubject2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.costcenter2" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.costcenter2}：</td>
<td width="30%" >
<div id="div_SettleSubjectBcccostcenter2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectBcccostcenter2_sh" boName="SettleSubjectBcc" boProperty="costcenter2" value="${billClearCollect.settleSubjectBcc.costcenter2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.depid2" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.depid2}：</td>
<td width="40%" >
<div id="div_SettleSubjectBccdepid2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectBccdepid2_sh" boName="SettleSubjectBcc" boProperty="depid2" value="${billClearCollect.settleSubjectBcc.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.orderno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.orderno2}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubjectBcc.orderno2" name="SettleSubjectBcc.orderno2" value="${billClearCollect.settleSubjectBcc.orderno2}" <fisc:authentication sourceName="SettleSubjectBcc.orderno2" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.rowno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.rowno2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubjectBcc.rowno2" name="SettleSubjectBcc.rowno2" value="${billClearCollect.settleSubjectBcc.rowno2}" <fisc:authentication sourceName="SettleSubjectBcc.rowno2" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.antiaccount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.antiaccount2}：</td>
<td width="30%" >
<div id="div_SettleSubjectBccantiaccount2_dict"></div>
<fisc:dictionary boName="SettleSubjectBcc" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_SettleSubjectBccantiaccount2_dict" isNeedAuth="false" value="${billClearCollect.settleSubjectBcc.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.debtcredit3" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.debtcredit3}：</td>
<td width="30%" >
<div id="div_SettleSubjectBccdebtcredit3_dict"></div>
<fisc:dictionary boName="SettleSubjectBcc" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectBccdebtcredit3_dict" isNeedAuth="false" value="${billClearCollect.settleSubjectBcc.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.amount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.amount3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubjectBcc.amount3" name="SettleSubjectBcc.amount3" value="${billClearCollect.settleSubjectBcc.amount3}" <fisc:authentication sourceName="SettleSubjectBcc.amount3" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.standardamount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.standardamount3}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubjectBcc.standardamount3" name="SettleSubjectBcc.standardamount3" value="${billClearCollect.settleSubjectBcc.standardamount3}" <fisc:authentication sourceName="SettleSubjectBcc.standardamount3" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.settlesubject3" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.settlesubject3}：</td>
<td width="40%" >
<div id="div_SettleSubjectBccsettlesubject3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectBccsettlesubject3_sh" boName="SettleSubjectBcc" boProperty="settlesubject3" value="${billClearCollect.settleSubjectBcc.settlesubject3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.costcenter3" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.costcenter3}：</td>
<td width="30%" >
<div id="div_SettleSubjectBcccostcenter3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectBcccostcenter3_sh" boName="SettleSubjectBcc" boProperty="costcenter3" value="${billClearCollect.settleSubjectBcc.costcenter3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.depid3" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.depid3}：</td>
<td width="40%" >
<div id="div_SettleSubjectBccdepid3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectBccdepid3_sh" boName="SettleSubjectBcc" boProperty="depid3" value="${billClearCollect.settleSubjectBcc.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.orderno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.orderno3}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubjectBcc.orderno3" name="SettleSubjectBcc.orderno3" value="${billClearCollect.settleSubjectBcc.orderno3}" <fisc:authentication sourceName="SettleSubjectBcc.orderno3" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.rowno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.rowno3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubjectBcc.rowno3" name="SettleSubjectBcc.rowno3" value="${billClearCollect.settleSubjectBcc.rowno3}" <fisc:authentication sourceName="SettleSubjectBcc.rowno3" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.antiaccount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.antiaccount3}：</td>
<td width="30%" >
<div id="div_SettleSubjectBccantiaccount3_dict"></div>
<fisc:dictionary boName="SettleSubjectBcc" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_SettleSubjectBccantiaccount3_dict" isNeedAuth="false" value="${billClearCollect.settleSubjectBcc.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.debtcredit4" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.debtcredit4}：</td>
<td width="30%" >
<div id="div_SettleSubjectBccdebtcredit4_dict"></div>
<fisc:dictionary boName="SettleSubjectBcc" boProperty="debtcredit4" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectBccdebtcredit4_dict" isNeedAuth="false" value="${billClearCollect.settleSubjectBcc.debtcredit4}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.amount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.amount4}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubjectBcc.amount4" name="SettleSubjectBcc.amount4" value="${billClearCollect.settleSubjectBcc.amount4}" <fisc:authentication sourceName="SettleSubjectBcc.amount4" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.standardamount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.standardamount4}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubjectBcc.standardamount4" name="SettleSubjectBcc.standardamount4" value="${billClearCollect.settleSubjectBcc.standardamount4}" <fisc:authentication sourceName="SettleSubjectBcc.standardamount4" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.settlesubject4" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.settlesubject4}：</td>
<td width="40%" >
<div id="div_SettleSubjectBccsettlesubject4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectBccsettlesubject4_sh" boName="SettleSubjectBcc" boProperty="settlesubject4" value="${billClearCollect.settleSubjectBcc.settlesubject4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.profitcenter" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.profitcenter}：</td>
<td width="30%" >
<div id="div_SettleSubjectBccprofitcenter_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectBccprofitcenter_sh" boName="SettleSubjectBcc" boProperty="profitcenter" value="${billClearCollect.settleSubjectBcc.profitcenter}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.depid4" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.depid4}：</td>
<td width="40%" >
<div id="div_SettleSubjectBccdepid4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectBccdepid4_sh" boName="SettleSubjectBcc" boProperty="depid4" value="${billClearCollect.settleSubjectBcc.depid4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.orderno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.orderno4}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubjectBcc.orderno4" name="SettleSubjectBcc.orderno4" value="${billClearCollect.settleSubjectBcc.orderno4}" <fisc:authentication sourceName="SettleSubjectBcc.orderno4" nodeId="${workflowNodeDefId}"/> >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.rowno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.rowno4}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubjectBcc.rowno4" name="SettleSubjectBcc.rowno4" value="${billClearCollect.settleSubjectBcc.rowno4}" <fisc:authentication sourceName="SettleSubjectBcc.rowno4" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectBcc.antiaccount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectBccVt.property.antiaccount4}：</td>
<td width="30%" >
<div id="div_SettleSubjectBccantiaccount4_dict"></div>
<fisc:dictionary boName="SettleSubjectBcc" boProperty="antiaccount4" dictionaryName="YDYESORNO" divId="div_SettleSubjectBccantiaccount4_dict" isNeedAuth="false" value="${billClearCollect.settleSubjectBcc.antiaccount4}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
</table>
<input type="hidden" id="SettleSubjectBcc.settlesubjectid" name="SettleSubjectBcc.settlesubjectid" value="${billClearCollect.settleSubjectBcc.settlesubjectid}">
<input type="hidden" id="SettleSubjectBcc.billclearid" name="SettleSubjectBcc.billclearid" value="${billClearCollect.billclearid}">
<input type="hidden" id="SettleSubjectBcc.paymentid" name="SettleSubjectBcc.paymentid" value="${billClearCollect.settleSubjectBcc.paymentid}">
<input type="hidden" id="SettleSubjectBcc.collectid" name="SettleSubjectBcc.collectid" value="${billClearCollect.settleSubjectBcc.collectid}">
</form>
</div>
<div id="div_billClearItem"></div>
<div id="div_fundFlowBcc" class="x-hide-display">
<form id="fundFlowBccForm" name="fundFlowBccForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.debtcredit1" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.debtcredit1}：</td>
<td width="30%" >
<div id="div_FundFlowBccdebtcredit1_dict"></div>
<fisc:dictionary boName="FundFlowBcc" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowBccdebtcredit1_dict" isNeedAuth="false" value="${billClearCollect.fundFlowBcc.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.amount1" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.amount1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="FundFlowBcc.amount1" name="FundFlowBcc.amount1" value="${billClearCollect.fundFlowBcc.amount1}" <fisc:authentication sourceName="FundFlowBcc.amount1" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.standardamount1" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.standardamount1}：</td>
<td width="30%" >
<input type="text" class="inputText" id="FundFlowBcc.standardamount1" name="FundFlowBcc.standardamount1" value="${billClearCollect.fundFlowBcc.standardamount1}" <fisc:authentication sourceName="FundFlowBcc.standardamount1" nodeId="${workflowNodeDefId}"/> >
</td>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlowBcc.specialaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.specialaccount1}：</td>
<td width="30%" >
<div id="div_FundFlowBccspecialaccount1_dict"></div>
<fisc:dictionary boName="FundFlowBcc" boProperty="specialaccount1" dictionaryName="YDUMSKZ" divId="div_FundFlowBccspecialaccount1_dict" isNeedAuth="false" value="${billClearCollect.fundFlowBcc.specialaccount1}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.customer1" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.customer1}：</td>
<td width="30%" >
<div id="div_FundFlowBcccustomer1_sh"></div>
<fisc:searchHelp divId="div_FundFlowBcccustomer1_sh" boName="FundFlowBcc" boProperty="customer1" value="${billClearCollect.fundFlowBcc.customer1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.depid1" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.depid1}：</td>
<td width="40%" >
<div id="div_FundFlowBccdepid1_sh"></div>
<fisc:searchHelp divId="div_FundFlowBccdepid1_sh" boName="FundFlowBcc" boProperty="depid1" value="${billClearCollect.fundFlowBcc.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.antiaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.antiaccount1}：</td>
<td width="30%" >
<div id="div_FundFlowBccantiaccount1_dict"></div>
<fisc:dictionary boName="FundFlowBcc" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_FundFlowBccantiaccount1_dict" isNeedAuth="false" value="${billClearCollect.fundFlowBcc.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.debtcredit2" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.debtcredit2}：</td>
<td width="30%" >
<div id="div_FundFlowBccdebtcredit2_dict"></div>
<fisc:dictionary boName="FundFlowBcc" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowBccdebtcredit2_dict" isNeedAuth="false" value="${billClearCollect.fundFlowBcc.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.amount2" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.amount2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="FundFlowBcc.amount2" name="FundFlowBcc.amount2" value="${billClearCollect.fundFlowBcc.amount2}" <fisc:authentication sourceName="FundFlowBcc.amount2" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.standardamount2" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.standardamount2}：</td>
<td width="30%" >
<input type="text" class="inputText" id="FundFlowBcc.standardamount2" name="FundFlowBcc.standardamount2" value="${billClearCollect.fundFlowBcc.standardamount2}" <fisc:authentication sourceName="FundFlowBcc.standardamount2" nodeId="${workflowNodeDefId}"/> >
</td>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlowBcc.specialaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.specialaccount2}：</td>
<td width="30%" >
<div id="div_FundFlowBccspecialaccount2_dict"></div>
<fisc:dictionary boName="FundFlowBcc" boProperty="specialaccount2" dictionaryName="YDUMSKZ" divId="div_FundFlowBccspecialaccount2_dict" isNeedAuth="false" value="${billClearCollect.fundFlowBcc.specialaccount2}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.customer2" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.customer2}：</td>
<td width="30%" >
<div id="div_FundFlowBcccustomer2_sh"></div>
<fisc:searchHelp divId="div_FundFlowBcccustomer2_sh" boName="FundFlowBcc" boProperty="customer2" value="${billClearCollect.fundFlowBcc.customer2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.depid2" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.depid2}：</td>
<td width="40%" >
<div id="div_FundFlowBccdepid2_sh"></div>
<fisc:searchHelp divId="div_FundFlowBccdepid2_sh" boName="FundFlowBcc" boProperty="depid2" value="${billClearCollect.fundFlowBcc.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.antiaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.antiaccount2}：</td>
<td width="30%" >
<div id="div_FundFlowBccantiaccount2_dict"></div>
<fisc:dictionary boName="FundFlowBcc" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_FundFlowBccantiaccount2_dict" isNeedAuth="false" value="${billClearCollect.fundFlowBcc.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.debtcredit3" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.debtcredit3}：</td>
<td width="30%" >
<div id="div_FundFlowBccdebtcredit3_dict"></div>
<fisc:dictionary boName="FundFlowBcc" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowBccdebtcredit3_dict" isNeedAuth="false" value="${billClearCollect.fundFlowBcc.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.amount3" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.amount3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="FundFlowBcc.amount3" name="FundFlowBcc.amount3" value="${billClearCollect.fundFlowBcc.amount3}" <fisc:authentication sourceName="FundFlowBcc.amount3" nodeId="${workflowNodeDefId}"/> >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.standardamount3" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.standardamount3}：</td>
<td width="30%" >
<input type="text" class="inputText" id="FundFlowBcc.standardamount3" name="FundFlowBcc.standardamount3" value="${billClearCollect.fundFlowBcc.standardamount3}" <fisc:authentication sourceName="FundFlowBcc.standardamount3" nodeId="${workflowNodeDefId}"/> >
</td>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlowBcc.specialaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.specialaccount3}：</td>
<td width="30%" >
<div id="div_FundFlowBccspecialaccount3_dict"></div>
<fisc:dictionary boName="FundFlowBcc" boProperty="specialaccount3" dictionaryName="YDUMSKZ" divId="div_FundFlowBccspecialaccount3_dict" isNeedAuth="false" value="${billClearCollect.fundFlowBcc.specialaccount3}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.customer3" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.customer3}：</td>
<td width="30%" >
<div id="div_FundFlowBcccustomer3_sh"></div>
<fisc:searchHelp divId="div_FundFlowBcccustomer3_sh" boName="FundFlowBcc" boProperty="customer3" value="${billClearCollect.fundFlowBcc.customer3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.depid3" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.depid3}：</td>
<td width="40%" >
<div id="div_FundFlowBccdepid3_sh"></div>
<fisc:searchHelp divId="div_FundFlowBccdepid3_sh" boName="FundFlowBcc" boProperty="depid3" value="${billClearCollect.fundFlowBcc.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowBcc.antiaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowBccVt.property.antiaccount3}：</td>
<td width="30%" >
<div id="div_FundFlowBccantiaccount3_dict"></div>
<fisc:dictionary boName="FundFlowBcc" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_FundFlowBccantiaccount3_dict" isNeedAuth="false" value="${billClearCollect.fundFlowBcc.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
</table>
<input type="hidden" id="FundFlowBcc.fundflowid" name="FundFlowBcc.fundflowid" value="${billClearCollect.fundFlowBcc.fundflowid}">
<input type="hidden" id="FundFlowBcc.billclearid" name="FundFlowBcc.billclearid" value="${billClearCollect.billclearid}">
<input type="hidden" id="FundFlowBcc.paymentid" name="FundFlowBcc.paymentid" value="${billClearCollect.fundFlowBcc.paymentid}">
<input type="hidden" id="FundFlowBcc.collectid" name="FundFlowBcc.collectid" value="${billClearCollect.fundFlowBcc.collectid}">
</form>
</div>
<div id="div_billInCollect"></div>

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/billclearcollect/billClearCollectView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/billclearcollect/billClearCollectViewGen.js"></script>

<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${billClearCollect.processstate}';
//当前对象主键ID
var billclearid = '${billClearCollect.billclearid}';	

//页面文本
var Txt={
	//票清收款
	billClearCollect:'${vt.boText}',
	          
	//结算科目
	settleSubjectBcc:'${settleSubjectBccVt.boText}',
	//boText创建
	settleSubjectBcc_Create:'${settleSubjectBccVt.boTextCreate}',
	//boText复制创建
	settleSubjectBcc_CopyCreate:'${settleSubjectBccVt.boTextCopyCreate}',
	// 进行【结算科目复制创建】操作时，只允许选择一条记录！
	settleSubjectBcc_CopyCreate_AllowOnlyOneItemForOperation:'${settleSubjectBccVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【结算科目复制创建】操作的记录！
	settleSubjectBcc_CopyCreate_SelectToOperation:'${settleSubjectBccVt.copyCreate_SelectToOperate}',
          
	//发票清帐
	billClearItem:'${billClearItemVt.boText}',
          
	//纯资金往来
	fundFlowBcc:'${fundFlowBccVt.boText}',
	// 请选择需要进行【纯资金往来批量删除】操作的记录！
	fundFlowBcc_Deletes_SelectToOperation:'${fundFlowBccVt.deletes_SelectToOperate}',
	// 您选择了【纯资金往来批量删除】操作，是否确定继续该操作？
	fundFlowBcc_Deletes_ConfirmOperation:'${fundFlowBccVt.deletes_ConfirmOperation}',
          
	//未清收款
	billInCollect:'${billInCollectVt.boText}',
	//boText复制创建
	billClearCollect_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	billClearCollect_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	billClearCollect_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:197.5,
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
						            		title:'${billClearItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_billClearItem'
						            	},
				          						                {
						            		title:'${billInCollectVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_billInCollect'
						            	},
          						              
          						                {
						            		title:'${fundFlowBccVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		autoHeight:true,
						            		contentEl:'div_fundFlowBcc'
						            	},
          						                {
						            		
						            		title:'${settleSubjectBccVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		autoHeight:true,
						            		contentEl:'div_settleSubjectBcc'
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
{id:'_copyCreate',text:'${vt.mCopyCreate}',handler:_copyCreateBillClearCollect,iconCls:'icon-copyCreate'},'-',
{id:'_create',text:'${vt.mCreate}',handler:_createBillClearCollect,iconCls:'icon-add'},'-',
'->','-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessBillClearCollect,iconCls:'task'},'-',
<%}%>
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelBillClearCollect,iconCls:'icon-undo'},'-',
{id:'_voucherPreview',text:'模拟凭证',handler:_voucherPreviewBillClearCollect,iconCls:'icon-view'},'-',
{id:'_showVoucher',text:'查看凭证',handler:_showVoucherBillClearCollect,iconCls:'icon-view'},'-',
{id:'_autoAssign',text:'自动分配',handler:_autoAssignBillClearCollect,iconCls:' '},'-',
{id:'_clearAssign',text:'清除分配',handler:_clearAssignBillClearCollect,iconCls:' '},'-',
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


