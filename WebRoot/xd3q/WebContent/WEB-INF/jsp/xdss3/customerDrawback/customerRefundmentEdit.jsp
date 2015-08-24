<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年07月05日 16点15分58秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象客户退款(CustomerRefundment)编辑页面
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
<fisc:grid divId="div_customerDbBankItem" boName="CustomerDbBankItem" needCheckBox="true" editable="true" defaultCondition=" YREFUNDBANKITEM.REFUNDMENTID='${customerRefundment.refundmentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_customerRefundItem" boName="CustomerRefundItem" needCheckBox="true" editable="true" defaultCondition=" YREFUNDMENTITEM.REFUNDMENTID='${customerRefundment.refundmentid}'" needAutoLoad="true" height="285" nameSapceSupport="true" pageSize="10000"></fisc:grid>
<fisc:attachement businessId="${businessId}" allowDelete="true" allowUpload="true" divId="div_attachementAttachement" boName="CustomerRefundment" boProperty="attachement" gridPageSize="10" gridHeight="285" ></fisc:attachement>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${customerRefundment.refundmentid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.customer" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.customer}：</td>
		<td width="30%">
			<div id="div_customer_sh"></div>
			<fisc:searchHelp divId="div_customer_sh" boName="CustomerRefundment" boProperty="customer"  value="${customerRefundment.customer}"></fisc:searchHelp>
		</td>
		<td></td>
		<td></td>		
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.refundcurrency" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.refundcurrency}：</td>
		<td width="30%">
			<div id="div_refundcurrency_sh"></div>
			<fisc:searchHelp divId="div_refundcurrency_sh" boName="CustomerRefundment" boProperty="refundcurrency" value="${customerRefundment.refundcurrency}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.refundamount" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.refundamount}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="CustomerRefundment.refundamount" name="CustomerRefundment.refundamount" value="${customerRefundment.refundamount}">
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.collectbankname" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.collectbankname}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="CustomerRefundment.collectbankname" name="CustomerRefundment.collectbankname" value="${customerRefundment.collectbankname}">
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.collectbankacc" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.collectbankacc}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="CustomerRefundment.collectbankacc" name="CustomerRefundment.collectbankacc" value="${customerRefundment.collectbankacc}">
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.text" nodeId="${workflowNodeDefId}"/> valign="top"><font color="red">★</font>${vt.property.text}：</td>
		<td width="30%" colspan="1" >
			<textarea rows="4" cols="54" id="CustomerRefundment.text" name="CustomerRefundment.text"  <fisc:authentication sourceName="CustomerRefundment.text" nodeId="${workflowNodeDefId}"/>>${customerRefundment.text}</textarea>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.exchangerate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.exchangerate}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="CustomerRefundment.exchangerate" name="CustomerRefundment.exchangerate" value="${customerRefundment.exchangerate}">
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.remark" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.remark}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="CustomerRefundment.remark" name="CustomerRefundment.remark"  <fisc:authentication sourceName="CustomerRefundment.remark" nodeId="${workflowNodeDefId}"/>>${customerRefundment.remark}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.dept_id" nodeId="${workflowNodeDefId}"/> >${vt.property.dept_id}：</td>
		<td  width="40%">
			<div id="div_dept_id_sh"></div>
			<fisc:searchHelp divId="div_dept_id_sh" boName="CustomerRefundment" boProperty="dept_id"  value="${customerRefundment.dept_id}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.cashflowitem" nodeId="${workflowNodeDefId}"/> >${vt.property.cashflowitem}：</td>
		<td  width="40%">
			<div id="div_cashflowitem_sh"></div>
			<fisc:searchHelp divId="div_cashflowitem_sh" boName="CustomerRefundment" boProperty="cashflowitem"  value="${customerRefundment.cashflowitem}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.voucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherdate}：</td>
		<td width="30%">
			<input type="text" id="CustomerRefundment.voucherdate" name="CustomerRefundment.voucherdate" value="">
				<fisc:calendar applyTo="CustomerRefundment.voucherdate"  divId="" fieldName="" defaultValue="${customerRefundment.voucherdate}"></fisc:calendar>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.accountdate" nodeId="${workflowNodeDefId}"/> >${vt.property.accountdate}：</td>
		<td  width="40%">
			<input type="text" id="CustomerRefundment.accountdate" name="CustomerRefundment.accountdate" value="">
				<fisc:calendar applyTo="CustomerRefundment.accountdate"  divId="" fieldName="" defaultValue="${customerRefundment.accountdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td width="30%">
		    <input type="text" class="inputText" id="CustomerRefundment.createtime" name="CustomerRefundment.createtime" value="${customerRefundment.createtime}"  readonly="readonly">
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="CustomerRefundment.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="CustomerRefundment" userId="${customerRefundment.creator}"></fisc:user>
		</td>
	</tr>

	<input type="hidden" id="CustomerRefundment.client" name="CustomerRefundment.client" value="${customerRefundment.client}">
	<input type="hidden" id="CustomerRefundment.refundmentid" name="CustomerRefundment.refundmentid" value="${customerRefundment.refundmentid}">
	<input type="hidden" id="CustomerRefundment.refundmentno" name="CustomerRefundment.refundmentno" value="${customerRefundment.refundmentno}">
	<input type="hidden" id="CustomerRefundment.supplier" name="CustomerRefundment.supplier" value="${customerRefundment.supplier}">
	<input type="hidden" id="CustomerRefundment.processstate" name="CustomerRefundment.processstate" value="${customerRefundment.processstate}">
	<input type="hidden" id="CustomerRefundment.lastmodiyer" name="CustomerRefundment.lastmodiyer" value="${customerRefundment.lastmodiyer}">
	<input type="hidden" id="CustomerRefundment.lastmodifytime" name="CustomerRefundment.lastmodifytime" value="${customerRefundment.lastmodifytime}">
	<input type="hidden" id="CustomerRefundment.tradetype" name="CustomerRefundment.tradetype" value="${customerRefundment.tradetype}">
	<input type="hidden" id="CustomerRefundment.businessstate" name="CustomerRefundment.businessstate" value="${customerRefundment.businessstate}">
	<input type="hidden" id="CustomerRefundment.reassigneddbid" name="CustomerRefundment.reassigneddbid" value="${customerRefundment.reassigneddbid}">
	<input type="hidden" id="CustomerRefundment.exchangerate" name="CustomerRefundment.exchangerate" value="${customerRefundment.exchangerate}">	
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_customerDbBankItem"></div>
<div id="div_customerRefundItem"></div>
<div id="div_attachementAttachement"></div>
<div id="div_settleSubject" class="x-hide-display" >
<form id="settleSubjectForm" name="settleSubjectForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.debtcredit1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit1}： </td>
<td width="30%">
<div id="div_SettleSubjectdebtcredit1_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit1_dict" isNeedAuth="false" value="${customerRefundment.settleSubject.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.amount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubject.amount1" name="SettleSubject.amount1" value="${customerRefundment.settleSubject.amount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.standardamount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount1}： </td>
<td width="30%">
<input type="text" class="inputText" id="SettleSubject.standardamount1" name="SettleSubject.standardamount1" value="${customerRefundment.settleSubject.standardamount1}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.settlesubject1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject1}： </td>
<td width="40%">
<div id="div_SettleSubjectsettlesubject1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject1_sh" boName="SettleSubject" boProperty="settlesubject1" value="${customerRefundment.settleSubject.settlesubject1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.costcenter1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter1}： </td>
<td width="30%">
<div id="div_SettleSubjectcostcenter1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter1_sh" boName="SettleSubject" boProperty="costcenter1" value="${customerRefundment.settleSubject.costcenter1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.depid1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid1}： </td>
<td width="40%">
<div id="div_SettleSubjectdepid1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid1_sh" boName="SettleSubject" boProperty="depid1" value="${customerRefundment.settleSubject.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.orderno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno1}： </td>
<td width="30%">
<div id="div_SettleSubjectorderno1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno1_sh" boName="SettleSubject" boProperty="orderno1" value="${customerRefundment.settleSubject.orderno1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.rowno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno1}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubject.rowno1" name="SettleSubject.rowno1" value="${customerRefundment.settleSubject.rowno1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.antiaccount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount1}： </td>
<td width="30%">
<div id="div_SettleSubjectantiaccount1_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount1_dict" isNeedAuth="false" value="${customerRefundment.settleSubject.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.debtcredit2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit2}： </td>
<td width="30%">
<div id="div_SettleSubjectdebtcredit2_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit2_dict" isNeedAuth="false" value="${customerRefundment.settleSubject.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.amount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubject.amount2" name="SettleSubject.amount2" value="${customerRefundment.settleSubject.amount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.standardamount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount2}： </td>
<td width="30%">
<input type="text" class="inputText" id="SettleSubject.standardamount2" name="SettleSubject.standardamount2" value="${customerRefundment.settleSubject.standardamount2}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.settlesubject2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject2}： </td>
<td width="40%">
<div id="div_SettleSubjectsettlesubject2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject2_sh" boName="SettleSubject" boProperty="settlesubject2" value="${customerRefundment.settleSubject.settlesubject2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.costcenter2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter2}： </td>
<td width="30%">
<div id="div_SettleSubjectcostcenter2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter2_sh" boName="SettleSubject" boProperty="costcenter2" value="${customerRefundment.settleSubject.costcenter2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.depid2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid2}： </td>
<td width="40%">
<div id="div_SettleSubjectdepid2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid2_sh" boName="SettleSubject" boProperty="depid2" value="${customerRefundment.settleSubject.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.orderno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno2}： </td>
<td width="30%">
<div id="div_SettleSubjectorderno2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno2_sh" boName="SettleSubject" boProperty="orderno2" value="${customerRefundment.settleSubject.orderno2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.rowno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno2}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubject.rowno2" name="SettleSubject.rowno2" value="${customerRefundment.settleSubject.rowno2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.antiaccount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount2}： </td>
<td width="30%">
<div id="div_SettleSubjectantiaccount2_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount2_dict" isNeedAuth="false" value="${customerRefundment.settleSubject.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.debtcredit3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit3}： </td>
<td width="30%">
<div id="div_SettleSubjectdebtcredit3_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit3_dict" isNeedAuth="false" value="${customerRefundment.settleSubject.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.amount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubject.amount3" name="SettleSubject.amount3" value="${customerRefundment.settleSubject.amount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.standardamount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount3}： </td>
<td width="30%">
<input type="text" class="inputText" id="SettleSubject.standardamount3" name="SettleSubject.standardamount3" value="${customerRefundment.settleSubject.standardamount3}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.settlesubject3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject3}： </td>
<td width="40%">
<div id="div_SettleSubjectsettlesubject3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject3_sh" boName="SettleSubject" boProperty="settlesubject3" value="${customerRefundment.settleSubject.settlesubject3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.costcenter3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter3}： </td>
<td width="30%">
<div id="div_SettleSubjectcostcenter3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter3_sh" boName="SettleSubject" boProperty="costcenter3" value="${customerRefundment.settleSubject.costcenter3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.depid3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid3}： </td>
<td width="40%">
<div id="div_SettleSubjectdepid3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid3_sh" boName="SettleSubject" boProperty="depid3" value="${customerRefundment.settleSubject.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.orderno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno3}： </td>
<td width="30%">
<div id="div_SettleSubjectorderno3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno3_sh" boName="SettleSubject" boProperty="orderno3" value="${customerRefundment.settleSubject.orderno3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.rowno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno3}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubject.rowno3" name="SettleSubject.rowno3" value="${customerRefundment.settleSubject.rowno3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.antiaccount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount3}： </td>
<td width="30%">
<div id="div_SettleSubjectantiaccount3_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount3_dict" isNeedAuth="false" value="${customerRefundment.settleSubject.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.debtcredit4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit4}： </td>
<td width="30%">
<div id="div_SettleSubjectdebtcredit4_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit4" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit4_dict" isNeedAuth="false" value="${customerRefundment.settleSubject.debtcredit4}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.amount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount4}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubject.amount4" name="SettleSubject.amount4" value="${customerRefundment.settleSubject.amount4}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.standardamount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount4}： </td>
<td width="30%">
<input type="text" class="inputText" id="SettleSubject.standardamount4" name="SettleSubject.standardamount4" value="${customerRefundment.settleSubject.standardamount4}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.settlesubject4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject4}： </td>
<td width="40%">
<div id="div_SettleSubjectsettlesubject4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject4_sh" boName="SettleSubject" boProperty="settlesubject4" value="${customerRefundment.settleSubject.settlesubject4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.profitcenter" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.profitcenter}： </td>
<td width="30%">
<div id="div_SettleSubjectprofitcenter_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectprofitcenter_sh" boName="SettleSubject" boProperty="profitcenter" value="${customerRefundment.settleSubject.profitcenter}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.depid4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid4}： </td>
<td width="40%">
<div id="div_SettleSubjectdepid4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid4_sh" boName="SettleSubject" boProperty="depid4" value="${customerRefundment.settleSubject.depid4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.orderno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno4}： </td>
<td width="30%">
<div id="div_SettleSubjectorderno4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno4_sh" boName="SettleSubject" boProperty="orderno4" value="${customerRefundment.settleSubject.orderno4}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.rowno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno4}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubject.rowno4" name="SettleSubject.rowno4" value="${customerRefundment.settleSubject.rowno4}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubject.antiaccount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount4}： </td>
<td width="30%">
<div id="div_SettleSubjectantiaccount4_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount4" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount4_dict" isNeedAuth="false" value="${customerRefundment.settleSubject.antiaccount4}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
</table>
<input type="hidden" id="SettleSubject.settlesubjectid" name="SettleSubject.settlesubjectid" value="${customerRefundment.settleSubject.settlesubjectid}">
</form>
</div>
<div id="div_top_south" class="x-hide-display"></div>
<div id="div_fundFlow" class="x-hide-display" >
<form id="fundFlowForm" name="fundFlowForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.debtcredit1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit1}： </td>
<td width="30%">
<div id="div_FundFlowdebtcredit1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit1_dict" isNeedAuth="false" value="${customerRefundment.fundFlow.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.amount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlow.amount1" name="FundFlow.amount1" value="${customerRefundment.fundFlow.amount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.standardamount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount1}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlow.standardamount1" name="FundFlow.standardamount1" value="${customerRefundment.fundFlow.standardamount1}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.specialaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount1}： </td>
<td width="40%">
<div id="div_FundFlowspecialaccount1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount1" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount1_dict" isNeedAuth="false" value="${customerRefundment.fundFlow.specialaccount1}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.customer1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer1}： </td>
<td width="30%">
<div id="div_FundFlowcustomer1_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer1_sh" boName="FundFlow" boProperty="customer1" value="${customerRefundment.fundFlow.customer1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.depid1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid1}： </td>
<td width="40%">
<div id="div_FundFlowdepid1_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid1_sh" boName="FundFlow" boProperty="depid1" value="${customerRefundment.fundFlow.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.antiaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount1}： </td>
<td width="30%">
<div id="div_FundFlowantiaccount1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount1_dict" isNeedAuth="false" value="${customerRefundment.fundFlow.antiaccount1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.debtcredit2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit2}： </td>
<td width="40%">
<div id="div_FundFlowdebtcredit2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit2_dict" isNeedAuth="false" value="${customerRefundment.fundFlow.debtcredit2}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.amount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount2}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlow.amount2" name="FundFlow.amount2" value="${customerRefundment.fundFlow.amount2}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.standardamount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlow.standardamount2" name="FundFlow.standardamount2" value="${customerRefundment.fundFlow.standardamount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.specialaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount2}： </td>
<td width="30%">
<div id="div_FundFlowspecialaccount2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount2" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount2_dict" isNeedAuth="false" value="${customerRefundment.fundFlow.specialaccount2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.customer2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer2}： </td>
<td width="40%">
<div id="div_FundFlowcustomer2_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer2_sh" boName="FundFlow" boProperty="customer2" value="${customerRefundment.fundFlow.customer2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.depid2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid2}： </td>
<td width="30%">
<div id="div_FundFlowdepid2_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid2_sh" boName="FundFlow" boProperty="depid2" value="${customerRefundment.fundFlow.depid2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.antiaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount2}： </td>
<td width="40%">
<div id="div_FundFlowantiaccount2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount2_dict" isNeedAuth="false" value="${customerRefundment.fundFlow.antiaccount2}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.debtcredit3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit3}： </td>
<td width="30%">
<div id="div_FundFlowdebtcredit3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit3_dict" isNeedAuth="false" value="${customerRefundment.fundFlow.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.amount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlow.amount3" name="FundFlow.amount3" value="${customerRefundment.fundFlow.amount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.standardamount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount3}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlow.standardamount3" name="FundFlow.standardamount3" value="${customerRefundment.fundFlow.standardamount3}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.specialaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount3}： </td>
<td width="40%">
<div id="div_FundFlowspecialaccount3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount3" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount3_dict" isNeedAuth="false" value="${customerRefundment.fundFlow.specialaccount3}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.customer3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer3}： </td>
<td width="30%">
<div id="div_FundFlowcustomer3_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer3_sh" boName="FundFlow" boProperty="customer3" value="${customerRefundment.fundFlow.customer3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.depid3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid3}： </td>
<td width="40%">
<div id="div_FundFlowdepid3_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid3_sh" boName="FundFlow" boProperty="depid3" value="${customerRefundment.fundFlow.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlow.antiaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount3}： </td>
<td width="30%">
<div id="div_FundFlowantiaccount3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount3_dict" isNeedAuth="false" value="${customerRefundment.fundFlow.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
</table>
<input type="hidden" id="FundFlow.fundflowid" name="FundFlow.fundflowid" value="${customerRefundment.fundFlow.fundflowid}">
</form>
</div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/customerDrawback/customerRefundmentEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/customerDrawback/customerRefundmentEditGen.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${customerRefundment.processstate}';
//当前对象主键ID
var refundmentid = '${customerRefundment.refundmentid}';	

//页面文本
var Txt={
	//客户退款
	customerRefundment:'${vt.boText}',
	          
	//客户退款银行
	customerDbBankItem:'${customerDbBankItemVt.boText}',
	// 请选择需要进行【客户退款银行批量删除】操作的记录！
	customerDbBankItem_Deletes_SelectToOperation:'${customerDbBankItemVt.deletes_SelectToOperate}',
	// 您选择了【客户退款银行批量删除】操作，是否确定继续该操作？
	customerDbBankItem_Deletes_ConfirmOperation:'${customerDbBankItemVt.deletes_ConfirmOperation}',
    
	//结算科目
	settleSubject:'${settleSubjectVt.boText}',
	//纯资金往来
	fundFlow:'${fundFlowVt.boText}',
	//客户退款行项目
	customerRefundItem:'${customerRefundItemVt.boText}',
	// 请选择需要进行【客户退款行项目批量删除】操作的记录！
	customerRefundItem_Deletes_SelectToOperation:'${customerRefundItemVt.deletes_SelectToOperate}',
	// 您选择了【客户退款行项目批量删除】操作，是否确定继续该操作？
	customerRefundItem_Deletes_ConfirmOperation:'${customerRefundItemVt.deletes_ConfirmOperation}',
	//boText复制创建
	customerRefundment_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	customerRefundment_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	customerRefundment_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:235,
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
											title:'${customerRefundItemVt.boText}',
											layout:'fit',
											autoWidth:true,
											contentEl:'div_customerRefundItem'
										},
			              						                {
						            		title:'${customerDbBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_customerDbBankItem'
						            	},
   						                {
						            		title:'附件',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'attachementattachementTab',
						            		contentEl:'div_attachementAttachement'
						            	},
						            	{
						            		title:'${settleSubjectVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		autoHeight:true,
						            		id:'settleSubjectTab',
						            		contentEl:'div_settleSubject'
						            	},
   						                {
						            		title:'${fundFlowVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		autoHeight:true,
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
					'-',
'->','-',
{id:'_saveOrUpdate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateCustomerRefundment,iconCls:'icon-table-save'},'-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessCustomerRefundment,iconCls:'task'},'-',
<%}%>
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelCustomerRefundment,iconCls:'icon-undo'},'-',
{id:'_cashJournal',text:'现金日记账',handler:_cashJournalCustomerRefundment,iconCls:' '},'-',
{id:'_preview',text:'模拟凭证',handler:_previewCustomerRefundment,iconCls:'icon-view'},'-',
{id:'_print',text:'打印',handler:_printCustomerRefundment,iconCls:'task'},'-',
//{id:'_submitForReassign',text:'重分配提交 ',handler:_submitForReassignCustomerRefundment,iconCls:'task'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	var isTask = '${workflowTaskId}';
	if(!isTask || isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited && isSubmited!='' && isSubmited!=' '){
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
Ext.getCmp('_saveOrUpdate').disable();	}
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
	
	Ext.getCmp("CustomerRefundItem._addProj").hide();
    Ext.getCmp("CustomerRefundItem._addCont").hide();
   
 });
</script>