<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2012年06月11日 09点32分22秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象其他公司供应商单清帐(SupplierSinglOther)编辑页面
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
<fisc:grid divId="div_unSupplieBillOther" boName="UnSupplieBillOther" needCheckBox="true" editable="true" defaultCondition=" YUNSUPPBILLOTHER.SUPPLIERSCLEARID='${supplierSinglOther.suppliersclearid}'" needAutoLoad="true" height="285" nameSapceSupport="true" title="借贷" ></fisc:grid>
<fisc:grid divId="div_unCleaPaymentOther" boName="UnCleaPaymentOther" needCheckBox="true" editable="true" defaultCondition=" YUNPAYMENTOTHER.SUPPLIERSCLEARID='${supplierSinglOther.suppliersclearid}'" needAutoLoad="true" height="285" nameSapceSupport="true" title="借贷" ></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${supplierSinglOther.suppliersclearid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="SupplierSinglOther.supplier" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.supplier}：</td>
		<td width="30%">
			<div id="div_supplier_sh"></div>
			<fisc:searchHelp divId="div_supplier_sh" boName="SupplierSinglOther" boProperty="supplier"  value="${supplierSinglOther.supplier}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="SupplierSinglOther.subject" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.subject}：</td>
		<td  width="40%">
			<div id="div_subject_sh"></div>
			<fisc:searchHelp divId="div_subject_sh" boName="SupplierSinglOther" boProperty="subject"  value="${supplierSinglOther.subject}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="SupplierSinglOther.currencytext" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.currencytext}：</td>
		<td width="30%">
			<div id="div_currencytext_sh"></div>
			<fisc:searchHelp divId="div_currencytext_sh" boName="SupplierSinglOther" boProperty="currencytext"  value="${supplierSinglOther.currencytext}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="SupplierSinglOther.companyno" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.companyno}：</td>
		<td  width="40%">
			<div id="div_companyno_sh"></div>
			<fisc:searchHelp divId="div_companyno_sh" boName="SupplierSinglOther" boProperty="companyno"  value="${supplierSinglOther.companyno}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="SupplierSinglOther.depid" nodeId="${workflowNodeDefId}"/> >${vt.property.depid}：</td>
		<td width="30%">
			<div id="div_depid_sh"></div>
			<fisc:searchHelp divId="div_depid_sh" boName="SupplierSinglOther" boProperty="depid"  value="${supplierSinglOther.depid}"></fisc:searchHelp>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="SupplierSinglOther.text" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.text}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="SupplierSinglOther.text" name="SupplierSinglOther.text" value="${supplierSinglOther.text}"   <fisc:authentication sourceName="SupplierSinglOther.text" nodeId="${workflowNodeDefId}"/>>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="SupplierSinglOther.remark" nodeId="${workflowNodeDefId}"/> >${vt.property.remark}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="SupplierSinglOther.remark" name="SupplierSinglOther.remark" value="${supplierSinglOther.remark}"   <fisc:authentication sourceName="SupplierSinglOther.remark" nodeId="${workflowNodeDefId}"/>>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="SupplierSinglOther.accountdate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.accountdate}：</td>
		<td width="30%">
			<input type="text" id="SupplierSinglOther.accountdate" name="SupplierSinglOther.accountdate" value="">
				<fisc:calendar applyTo="SupplierSinglOther.accountdate"  divId="" fieldName="" defaultValue="${supplierSinglOther.accountdate}"></fisc:calendar>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="SupplierSinglOther.voucherdate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.voucherdate}：</td>
		<td  width="40%">
			<input type="text" id="SupplierSinglOther.voucherdate" name="SupplierSinglOther.voucherdate" value="">
				<fisc:calendar applyTo="SupplierSinglOther.voucherdate"  divId="" fieldName="" defaultValue="${supplierSinglOther.voucherdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="SupplierSinglOther.supplierclearno" nodeId="${workflowNodeDefId}"/> >${vt.property.supplierclearno}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="SupplierSinglOther.supplierclearno" name="SupplierSinglOther.supplierclearno" value="${supplierSinglOther.supplierclearno}"   readonly="readonly" <fisc:authentication sourceName="SupplierSinglOther.supplierclearno" nodeId="${workflowNodeDefId}"/>>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="SupplierSinglOther.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="SupplierSinglOther" userId="${supplierSinglOther.creator}"></fisc:user>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="SupplierSinglOther.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="SupplierSinglOther.createtime" name="SupplierSinglOther.createtime" value="${supplierSinglOther.createtime}"   <fisc:authentication sourceName="SupplierSinglOther.createtime" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>

	<input type="hidden" id="SupplierSinglOther.lastmodifytime" name="SupplierSinglOther.lastmodifytime" value="${supplierSinglOther.lastmodifytime}">
	<input type="hidden" id="SupplierSinglOther.deptid" name="SupplierSinglOther.deptid" value="${supplierSinglOther.deptid}">
	<input type="hidden" id="SupplierSinglOther.businessstate" name="SupplierSinglOther.businessstate" value="${supplierSinglOther.businessstate}">
	<input type="hidden" id="SupplierSinglOther.lastmodifyer" name="SupplierSinglOther.lastmodifyer" value="${supplierSinglOther.lastmodifyer}">
	<input type="hidden" id="SupplierSinglOther.specialaccount" name="SupplierSinglOther.specialaccount" value="${supplierSinglOther.specialaccount}">
	<input type="hidden" id="SupplierSinglOther.processstate" name="SupplierSinglOther.processstate" value="${supplierSinglOther.processstate}">
	<input type="hidden" id="SupplierSinglOther.client" name="SupplierSinglOther.client" value="${supplierSinglOther.client}">
	<input type="hidden" id="SupplierSinglOther.suppliersclearid" name="SupplierSinglOther.suppliersclearid" value="${supplierSinglOther.suppliersclearid}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_unSupplieBillOther"></div>
<div id="div_unCleaPaymentOther"></div>
<div id="div_settleSubjectOther" class="x-hide-display" >
<form id="settleSubjectOtherForm" name="settleSubjectOtherForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.debtcredit1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.debtcredit1}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherdebtcredit1_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectOtherdebtcredit1_dict" isNeedAuth="false" value="${supplierSinglOther.settleSubjectOther.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.amount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.amount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.amount1" name="SettleSubjectOther.amount1" value="${supplierSinglOther.settleSubjectOther.amount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.standardamount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.standardamount1}： </td>
<td width="30%">
<input type="text" class="inputText" id="SettleSubjectOther.standardamount1" name="SettleSubjectOther.standardamount1" value="${supplierSinglOther.settleSubjectOther.standardamount1}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.settlesubject1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.settlesubject1}： </td>
<td width="40%">
<div id="div_SettleSubjectOthersettlesubject1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthersettlesubject1_sh" boName="SettleSubjectOther" boProperty="settlesubject1" value="${supplierSinglOther.settleSubjectOther.settlesubject1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.costcenter1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.costcenter1}： </td>
<td width="30%">
<div id="div_SettleSubjectOthercostcenter1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthercostcenter1_sh" boName="SettleSubjectOther" boProperty="costcenter1" value="${supplierSinglOther.settleSubjectOther.costcenter1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.depid1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.depid1}： </td>
<td width="40%">
<div id="div_SettleSubjectOtherdepid1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherdepid1_sh" boName="SettleSubjectOther" boProperty="depid1" value="${supplierSinglOther.settleSubjectOther.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.orderno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.orderno1}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherorderno1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherorderno1_sh" boName="SettleSubjectOther" boProperty="orderno1" value="${supplierSinglOther.settleSubjectOther.orderno1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.rowno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.rowno1}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.rowno1" name="SettleSubjectOther.rowno1" value="${supplierSinglOther.settleSubjectOther.rowno1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.antiaccount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.antiaccount1}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherantiaccount1_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_SettleSubjectOtherantiaccount1_dict" isNeedAuth="false" value="${supplierSinglOther.settleSubjectOther.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.debtcredit2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.debtcredit2}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherdebtcredit2_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectOtherdebtcredit2_dict" isNeedAuth="false" value="${supplierSinglOther.settleSubjectOther.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.amount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.amount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.amount2" name="SettleSubjectOther.amount2" value="${supplierSinglOther.settleSubjectOther.amount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.standardamount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.standardamount2}： </td>
<td width="30%">
<input type="text" class="inputText" id="SettleSubjectOther.standardamount2" name="SettleSubjectOther.standardamount2" value="${supplierSinglOther.settleSubjectOther.standardamount2}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.settlesubject2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.settlesubject2}： </td>
<td width="40%">
<div id="div_SettleSubjectOthersettlesubject2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthersettlesubject2_sh" boName="SettleSubjectOther" boProperty="settlesubject2" value="${supplierSinglOther.settleSubjectOther.settlesubject2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.costcenter2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.costcenter2}： </td>
<td width="30%">
<div id="div_SettleSubjectOthercostcenter2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthercostcenter2_sh" boName="SettleSubjectOther" boProperty="costcenter2" value="${supplierSinglOther.settleSubjectOther.costcenter2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.depid2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.depid2}： </td>
<td width="40%">
<div id="div_SettleSubjectOtherdepid2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherdepid2_sh" boName="SettleSubjectOther" boProperty="depid2" value="${supplierSinglOther.settleSubjectOther.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.orderno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.orderno2}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherorderno2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherorderno2_sh" boName="SettleSubjectOther" boProperty="orderno2" value="${supplierSinglOther.settleSubjectOther.orderno2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.rowno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.rowno2}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.rowno2" name="SettleSubjectOther.rowno2" value="${supplierSinglOther.settleSubjectOther.rowno2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.antiaccount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.antiaccount2}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherantiaccount2_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_SettleSubjectOtherantiaccount2_dict" isNeedAuth="false" value="${supplierSinglOther.settleSubjectOther.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.amount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.amount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.amount3" name="SettleSubjectOther.amount3" value="${supplierSinglOther.settleSubjectOther.amount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.debtcredit3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.debtcredit3}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherdebtcredit3_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectOtherdebtcredit3_dict" isNeedAuth="false" value="${supplierSinglOther.settleSubjectOther.debtcredit3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.standardamount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.standardamount3}： </td>
<td width="30%">
<input type="text" class="inputText" id="SettleSubjectOther.standardamount3" name="SettleSubjectOther.standardamount3" value="${supplierSinglOther.settleSubjectOther.standardamount3}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.settlesubject3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.settlesubject3}： </td>
<td width="40%">
<div id="div_SettleSubjectOthersettlesubject3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthersettlesubject3_sh" boName="SettleSubjectOther" boProperty="settlesubject3" value="${supplierSinglOther.settleSubjectOther.settlesubject3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.costcenter3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.costcenter3}： </td>
<td width="30%">
<div id="div_SettleSubjectOthercostcenter3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthercostcenter3_sh" boName="SettleSubjectOther" boProperty="costcenter3" value="${supplierSinglOther.settleSubjectOther.costcenter3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.depid3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.depid3}： </td>
<td width="40%">
<div id="div_SettleSubjectOtherdepid3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherdepid3_sh" boName="SettleSubjectOther" boProperty="depid3" value="${supplierSinglOther.settleSubjectOther.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.orderno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.orderno3}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherorderno3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherorderno3_sh" boName="SettleSubjectOther" boProperty="orderno3" value="${supplierSinglOther.settleSubjectOther.orderno3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.rowno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.rowno3}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.rowno3" name="SettleSubjectOther.rowno3" value="${supplierSinglOther.settleSubjectOther.rowno3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.antiaccount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.antiaccount3}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherantiaccount3_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_SettleSubjectOtherantiaccount3_dict" isNeedAuth="false" value="${supplierSinglOther.settleSubjectOther.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.debtcredit4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.debtcredit4}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherdebtcredit4_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="debtcredit4" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectOtherdebtcredit4_dict" isNeedAuth="false" value="${supplierSinglOther.settleSubjectOther.debtcredit4}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.amount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.amount4}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.amount4" name="SettleSubjectOther.amount4" value="${supplierSinglOther.settleSubjectOther.amount4}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.standardamount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.standardamount4}： </td>
<td width="30%">
<input type="text" class="inputText" id="SettleSubjectOther.standardamount4" name="SettleSubjectOther.standardamount4" value="${supplierSinglOther.settleSubjectOther.standardamount4}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.settlesubject4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.settlesubject4}： </td>
<td width="40%">
<div id="div_SettleSubjectOthersettlesubject4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOthersettlesubject4_sh" boName="SettleSubjectOther" boProperty="settlesubject4" value="${supplierSinglOther.settleSubjectOther.settlesubject4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.profitcenter" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.profitcenter}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherprofitcenter_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherprofitcenter_sh" boName="SettleSubjectOther" boProperty="profitcenter" value="${supplierSinglOther.settleSubjectOther.profitcenter}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.depid4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.depid4}： </td>
<td width="40%">
<div id="div_SettleSubjectOtherdepid4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherdepid4_sh" boName="SettleSubjectOther" boProperty="depid4" value="${supplierSinglOther.settleSubjectOther.depid4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.orderno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.orderno4}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherorderno4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectOtherorderno4_sh" boName="SettleSubjectOther" boProperty="orderno4" value="${supplierSinglOther.settleSubjectOther.orderno4}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.rowno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.rowno4}： </td>
<td width="40%">
<input type="text" class="inputText" id="SettleSubjectOther.rowno4" name="SettleSubjectOther.rowno4" value="${supplierSinglOther.settleSubjectOther.rowno4}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="SettleSubjectOther.antiaccount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectOtherVt.property.antiaccount4}： </td>
<td width="30%">
<div id="div_SettleSubjectOtherantiaccount4_dict"></div>
<fisc:dictionary boName="SettleSubjectOther" boProperty="antiaccount4" dictionaryName="YDYESORNO" divId="div_SettleSubjectOtherantiaccount4_dict" isNeedAuth="false" value="${supplierSinglOther.settleSubjectOther.antiaccount4}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
</table>
<input type="hidden" id="SettleSubjectOther.settlesubjectid" name="SettleSubjectOther.settlesubjectid" value="${supplierSinglOther.settleSubjectOther.settlesubjectid}">
</form>
</div>
<div id="div_fundFlowOther" class="x-hide-display" >
<form id="fundFlowOtherForm" name="fundFlowOtherForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.debtcredit1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.debtcredit1}： </td>
<td width="30%">
<div id="div_FundFlowOtherdebtcredit1_dict"></div>
<fisc:dictionary boName="FundFlowOther" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowOtherdebtcredit1_dict" isNeedAuth="false" value="${supplierSinglOther.fundFlowOther.debtcredit1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.amount1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.amount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlowOther.amount1" name="FundFlowOther.amount1" value="${supplierSinglOther.fundFlowOther.amount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.standardamount1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.standardamount1}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlowOther.standardamount1" name="FundFlowOther.standardamount1" value="${supplierSinglOther.fundFlowOther.standardamount1}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.specialaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.specialaccount1}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlowOther.specialaccount1" name="FundFlowOther.specialaccount1" value="${supplierSinglOther.fundFlowOther.specialaccount1}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.customer1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.customer1}： </td>
<td width="30%">
<div id="div_FundFlowOthercustomer1_sh"></div>
<fisc:searchHelp divId="div_FundFlowOthercustomer1_sh" boName="FundFlowOther" boProperty="customer1" value="${supplierSinglOther.fundFlowOther.customer1}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.depid1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.depid1}： </td>
<td width="40%">
<div id="div_FundFlowOtherdepid1_sh"></div>
<fisc:searchHelp divId="div_FundFlowOtherdepid1_sh" boName="FundFlowOther" boProperty="depid1" value="${supplierSinglOther.fundFlowOther.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.antiaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.antiaccount1}： </td>
<td width="30%">
<div id="div_FundFlowOtherantiaccount1_dict"></div>
<fisc:dictionary boName="FundFlowOther" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_FundFlowOtherantiaccount1_dict" isNeedAuth="false" value="${supplierSinglOther.fundFlowOther.antiaccount1}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.supplier1" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.supplier1}： </td>
<td width="40%">
<div id="div_FundFlowOthersupplier1_sh"></div>
<fisc:searchHelp divId="div_FundFlowOthersupplier1_sh" boName="FundFlowOther" boProperty="supplier1" value="${supplierSinglOther.fundFlowOther.supplier1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.debtcredit2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.debtcredit2}： </td>
<td width="30%">
<div id="div_FundFlowOtherdebtcredit2_dict"></div>
<fisc:dictionary boName="FundFlowOther" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowOtherdebtcredit2_dict" isNeedAuth="false" value="${supplierSinglOther.fundFlowOther.debtcredit2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.amount2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.amount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlowOther.amount2" name="FundFlowOther.amount2" value="${supplierSinglOther.fundFlowOther.amount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.standardamount2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.standardamount2}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlowOther.standardamount2" name="FundFlowOther.standardamount2" value="${supplierSinglOther.fundFlowOther.standardamount2}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.specialaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.specialaccount2}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlowOther.specialaccount2" name="FundFlowOther.specialaccount2" value="${supplierSinglOther.fundFlowOther.specialaccount2}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.customer2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.customer2}： </td>
<td width="30%">
<div id="div_FundFlowOthercustomer2_sh"></div>
<fisc:searchHelp divId="div_FundFlowOthercustomer2_sh" boName="FundFlowOther" boProperty="customer2" value="${supplierSinglOther.fundFlowOther.customer2}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.depid2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.depid2}： </td>
<td width="40%">
<div id="div_FundFlowOtherdepid2_sh"></div>
<fisc:searchHelp divId="div_FundFlowOtherdepid2_sh" boName="FundFlowOther" boProperty="depid2" value="${supplierSinglOther.fundFlowOther.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.antiaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.antiaccount2}： </td>
<td width="30%">
<div id="div_FundFlowOtherantiaccount2_dict"></div>
<fisc:dictionary boName="FundFlowOther" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_FundFlowOtherantiaccount2_dict" isNeedAuth="false" value="${supplierSinglOther.fundFlowOther.antiaccount2}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.supplier2" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.supplier2}： </td>
<td width="40%">
<div id="div_FundFlowOthersupplier2_sh"></div>
<fisc:searchHelp divId="div_FundFlowOthersupplier2_sh" boName="FundFlowOther" boProperty="supplier2" value="${supplierSinglOther.fundFlowOther.supplier2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.debtcredit3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.debtcredit3}： </td>
<td width="30%">
<div id="div_FundFlowOtherdebtcredit3_dict"></div>
<fisc:dictionary boName="FundFlowOther" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowOtherdebtcredit3_dict" isNeedAuth="false" value="${supplierSinglOther.fundFlowOther.debtcredit3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.amount3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.amount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlowOther.amount3" name="FundFlowOther.amount3" value="${supplierSinglOther.fundFlowOther.amount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.standardamount3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.standardamount3}： </td>
<td width="30%">
<input type="text" class="inputText" id="FundFlowOther.standardamount3" name="FundFlowOther.standardamount3" value="${supplierSinglOther.fundFlowOther.standardamount3}" >
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.specialaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.specialaccount3}： </td>
<td width="40%">
<input type="text" class="inputText" id="FundFlowOther.specialaccount3" name="FundFlowOther.specialaccount3" value="${supplierSinglOther.fundFlowOther.specialaccount3}" >
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.customer3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.customer3}： </td>
<td width="30%">
<div id="div_FundFlowOthercustomer3_sh"></div>
<fisc:searchHelp divId="div_FundFlowOthercustomer3_sh" boName="FundFlowOther" boProperty="customer3" value="${supplierSinglOther.fundFlowOther.customer3}"></fisc:searchHelp>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.depid3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.depid3}： </td>
<td width="40%">
<div id="div_FundFlowOtherdepid3_sh"></div>
<fisc:searchHelp divId="div_FundFlowOtherdepid3_sh" boName="FundFlowOther" boProperty="depid3" value="${supplierSinglOther.fundFlowOther.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.antiaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.antiaccount3}： </td>
<td width="30%">
<div id="div_FundFlowOtherantiaccount3_dict"></div>
<fisc:dictionary boName="FundFlowOther" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_FundFlowOtherantiaccount3_dict" isNeedAuth="false" value="${supplierSinglOther.fundFlowOther.antiaccount3}" ></fisc:dictionary>
</td>
<td width="15%" align="right" <fisc:authentication sourceName="FundFlowOther.supplier3" nodeId="${workflowNodeDefId}"/> >${fundFlowOtherVt.property.supplier3}： </td>
<td width="40%">
<div id="div_FundFlowOthersupplier3_sh"></div>
<fisc:searchHelp divId="div_FundFlowOthersupplier3_sh" boName="FundFlowOther" boProperty="supplier3" value="${supplierSinglOther.fundFlowOther.supplier3}"></fisc:searchHelp>
</td>
</tr>
</table>
<input type="hidden" id="FundFlowOther.fundflowid" name="FundFlowOther.fundflowid" value="${supplierSinglOther.fundFlowOther.fundflowid}">
</form>
</div>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/singleClearOther/supplierSinglOtherEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/singleClearOther/supplierSinglOtherEditGen.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${supplierSinglOther.processstate}';
//当前对象主键ID
var suppliersclearid = '${supplierSinglOther.suppliersclearid}';	

//页面文本
var Txt={
	//其他公司供应商单清帐
	supplierSinglOther:'${vt.boText}',
	          
	//其他公司未清应付（贷方）
	unSupplieBillOther:'${unSupplieBillOtherVt.boText}',
          
	//其他公司未清付款（借方）
	unCleaPaymentOther:'${unCleaPaymentOtherVt.boText}',
          
	//其他公司结算科目
	settleSubjectOther:'${settleSubjectOtherVt.boText}',
          
	//其他公司纯资金往来
	fundFlowOther:'${fundFlowOtherVt.boText}',
	//boText复制创建
	supplierSinglOther_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	supplierSinglOther_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	supplierSinglOther_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:310,
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
						            		title:'${unSupplieBillOtherVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_unSupplieBillOther'
						            	},
          						                {
						            		title:'${unCleaPaymentOtherVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_unCleaPaymentOther'
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
{id:'_saveOrUpdateSupplierSinglOther',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateSupplierSinglOther,iconCls:'icon-table-save'},'-',
                   
{id:'_queryUnClear',text:'查询',handler:_queryUnClearSupplierSinglOther,iconCls:' '},'-',
{id:'_voucherPreview',text:'模拟凭证',handler:_voucherPreviewSupplierSinglOther,iconCls:' '},'-',
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
