<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年09月19日 09点52分28秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象客户单清帐(CustomSingleClear)增加页面
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
<fisc:grid divId="div_unClearCustomBill"  pageSize="10000"   boName="UnClearCustomBill" needCheckBox="true" editable="true" defaultCondition=" YUNCLEARCUSTBILL.CUSTOMSCLEARID='${customSingleClear.customsclearid}'" needAutoLoad="true" height="285" nameSapceSupport="true" title="借贷" ></fisc:grid>
<fisc:grid divId="div_unClearCollect"  pageSize="10000"   boName="UnClearCollect" needCheckBox="true" editable="true" defaultCondition=" YUNCLEARCOLLECT.CUSTOMSCLEARID='${customSingleClear.customsclearid}'" needAutoLoad="true" height="285" nameSapceSupport="true" title="借贷"></fisc:grid>
<div id="div_top_south" ></div>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${customSingleClear.customsclearid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="CustomSingleClear.custom" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.custom}：</td>
		<td  width="30%" >
			<div id="div_custom_sh"></div>
			<fisc:searchHelp divId="div_custom_sh" boName="CustomSingleClear" boProperty="custom" value="${customSingleClear.custom}"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="CustomSingleClear.subject" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.subject}：</td>
		<td   width="40%" >
			<div id="div_subject_sh"></div>
			<fisc:searchHelp divId="div_subject_sh" boName="CustomSingleClear" boProperty="subject" value="${customSingleClear.subject}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="CustomSingleClear.depid" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.depid}：</td>
		<td  width="30%" >
			<div id="div_depid_sh"></div>
			<fisc:searchHelp divId="div_depid_sh" boName="CustomSingleClear" boProperty="depid" value="${customSingleClear.depid}"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="CustomSingleClear.companyno" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.companyno}：</td>
		<td   width="40%" >
			<div id="div_companyno_sh"></div>
			<fisc:searchHelp divId="div_companyno_sh" boName="CustomSingleClear" boProperty="companyno" value="${customSingleClear.companyno}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="CustomSingleClear.currencytext" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.currencytext}：</td>
		<td  width="30%" >
			<div id="div_currencytext_sh"></div>
			<fisc:searchHelp divId="div_currencytext_sh" boName="CustomSingleClear" boProperty="currencytext" value="${customSingleClear.currencytext}"></fisc:searchHelp>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="CustomSingleClear.text" nodeId="${workflowNodeDefId}"/> valign="top"><font color="red">★</font>${vt.property.text}：</td>
		<td  width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="CustomSingleClear.text" name="CustomSingleClear.text" <fisc:authentication sourceName="CustomSingleClear.text" nodeId="${workflowNodeDefId}"/>>${customSingleClear.text}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="CustomSingleClear.remark" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.remark}：</td>
		<td  width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="CustomSingleClear.remark" name="CustomSingleClear.remark" <fisc:authentication sourceName="CustomSingleClear.remark" nodeId="${workflowNodeDefId}"/>>${customSingleClear.remark}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="CustomSingleClear.accountdate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.accountdate}：</td>
		<td  width="30%" >
			<input type="text" id="CustomSingleClear.accountdate" name="CustomSingleClear.accountdate" value="">
				<fisc:calendar applyTo="CustomSingleClear.accountdate"  divId="" fieldName="" defaultValue="${customSingleClear.accountdate}"></fisc:calendar>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="CustomSingleClear.voucherdate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.voucherdate}：</td>
		<td   width="40%" >
			<input type="text" id="CustomSingleClear.voucherdate" name="CustomSingleClear.voucherdate" value="">
				<fisc:calendar applyTo="CustomSingleClear.voucherdate"  divId="" fieldName="" defaultValue="${customSingleClear.voucherdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="CustomSingleClear.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td  width="30%" >
			<fisc:user boProperty="creator" boName="CustomSingleClear" userId="${customSingleClear.creator}"></fisc:user>
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="CustomSingleClear.client" name="CustomSingleClear.client" value="${customSingleClear.client}">
	<input type="hidden" id="CustomSingleClear.customsclearid" name="CustomSingleClear.customsclearid" value="${customSingleClear.customsclearid}">
	<input type="hidden" id="CustomSingleClear.deptid" name="CustomSingleClear.deptid" value="${customSingleClear.deptid}">
	<input type="hidden" id="CustomSingleClear.processstate" name="CustomSingleClear.processstate" value="${customSingleClear.processstate}">
	<input type="hidden" id="CustomSingleClear.createtime" name="CustomSingleClear.createtime" value="${customSingleClear.createtime}">
	<input type="hidden" id="CustomSingleClear.specialaccount" name="CustomSingleClear.specialaccount" value="${customSingleClear.specialaccount}">
	<input type="hidden" id="CustomSingleClear.lastmodifytime" name="CustomSingleClear.lastmodifytime" value="${customSingleClear.lastmodifytime}">
	<input type="hidden" id="CustomSingleClear.lastmodifyer" name="CustomSingleClear.lastmodifyer" value="${customSingleClear.lastmodifyer}">
	<input type="hidden" id="CustomSingleClear.businessstate" name="CustomSingleClear.businessstate" value="${customSingleClear.businessstate}">
	<input type="hidden" id="calActivityId" name="calActivityId" value="${calActivityId}">
	<input type="hidden" id="CustomSingleClear.customclearno" name="CustomSingleClear.customclearno" value="${customSingleClear.customclearno}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_unClearCustomBill" ></div>
<div id="div_unClearCollect" ></div>
<div id="div_settleSubject" class="x-hide-display">
<form id="settleSubjectForm" name="settleSubjectForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.debtcredit1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit1}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit1_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit1_dict" isNeedAuth="false" value="${customSingleClear.settleSubject.debtcredit1}" ></fisc:dictionary>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.amount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount1" name="SettleSubject.amount1" value="${customSingleClear.settleSubject.amount1}" <fisc:authentication sourceName="SettleSubject.amount1" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.standardamount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount1}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount1" name="SettleSubject.standardamount1" value="${customSingleClear.settleSubject.standardamount1}" <fisc:authentication sourceName="SettleSubject.standardamount1" nodeId="${workflowNodeDefId}"/>>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.settlesubject1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject1}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject1_sh" boName="SettleSubject" boProperty="settlesubject1" value="${customSingleClear.settleSubject.settlesubject1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.costcenter1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter1}：</td>
<td width="30%" >
<div id="div_SettleSubjectcostcenter1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter1_sh" boName="SettleSubject" boProperty="costcenter1" value="${customSingleClear.settleSubject.costcenter1}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.depid1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid1}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid1_sh" boName="SettleSubject" boProperty="depid1" value="${customSingleClear.settleSubject.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.orderno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno1}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno1_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno1_sh" boName="SettleSubject" boProperty="orderno1" value="${customSingleClear.settleSubject.orderno1}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.rowno1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno1" name="SettleSubject.rowno1" value="${customSingleClear.settleSubject.rowno1}" <fisc:authentication sourceName="SettleSubject.rowno1" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.antiaccount1" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount1}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount1_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount1_dict" isNeedAuth="false" value="${customSingleClear.settleSubject.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.debtcredit2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit2}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit2_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit2_dict" isNeedAuth="false" value="${customSingleClear.settleSubject.debtcredit2}" ></fisc:dictionary>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.amount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount2" name="SettleSubject.amount2" value="${customSingleClear.settleSubject.amount2}" <fisc:authentication sourceName="SettleSubject.amount2" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.standardamount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount2}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount2" name="SettleSubject.standardamount2" value="${customSingleClear.settleSubject.standardamount2}" <fisc:authentication sourceName="SettleSubject.standardamount2" nodeId="${workflowNodeDefId}"/>>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.settlesubject2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject2}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject2_sh" boName="SettleSubject" boProperty="settlesubject2" value="${customSingleClear.settleSubject.settlesubject2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.costcenter2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter2}：</td>
<td width="30%" >
<div id="div_SettleSubjectcostcenter2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter2_sh" boName="SettleSubject" boProperty="costcenter2" value="${customSingleClear.settleSubject.costcenter2}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.depid2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid2}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid2_sh" boName="SettleSubject" boProperty="depid2" value="${customSingleClear.settleSubject.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.orderno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno2}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno2_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno2_sh" boName="SettleSubject" boProperty="orderno2" value="${customSingleClear.settleSubject.orderno2}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.rowno2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno2" name="SettleSubject.rowno2" value="${customSingleClear.settleSubject.rowno2}" <fisc:authentication sourceName="SettleSubject.rowno2" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.antiaccount2" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount2}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount2_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount2_dict" isNeedAuth="false" value="${customSingleClear.settleSubject.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.debtcredit3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit3}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit3_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit3_dict" isNeedAuth="false" value="${customSingleClear.settleSubject.debtcredit3}" ></fisc:dictionary>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.amount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount3" name="SettleSubject.amount3" value="${customSingleClear.settleSubject.amount3}" <fisc:authentication sourceName="SettleSubject.amount3" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.standardamount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount3}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount3" name="SettleSubject.standardamount3" value="${customSingleClear.settleSubject.standardamount3}" <fisc:authentication sourceName="SettleSubject.standardamount3" nodeId="${workflowNodeDefId}"/>>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.settlesubject3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject3}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject3_sh" boName="SettleSubject" boProperty="settlesubject3" value="${customSingleClear.settleSubject.settlesubject3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.costcenter3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.costcenter3}：</td>
<td width="30%" >
<div id="div_SettleSubjectcostcenter3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectcostcenter3_sh" boName="SettleSubject" boProperty="costcenter3" value="${customSingleClear.settleSubject.costcenter3}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.depid3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid3}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid3_sh" boName="SettleSubject" boProperty="depid3" value="${customSingleClear.settleSubject.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.orderno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno3}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno3_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno3_sh" boName="SettleSubject" boProperty="orderno3" value="${customSingleClear.settleSubject.orderno3}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.rowno3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno3" name="SettleSubject.rowno3" value="${customSingleClear.settleSubject.rowno3}" <fisc:authentication sourceName="SettleSubject.rowno3" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.antiaccount3" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.antiaccount3}：</td>
<td width="30%" >
<div id="div_SettleSubjectantiaccount3_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_SettleSubjectantiaccount3_dict" isNeedAuth="false" value="${customSingleClear.settleSubject.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.debtcredit4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.debtcredit4}：</td>
<td width="30%" >
<div id="div_SettleSubjectdebtcredit4_dict"></div>
<fisc:dictionary boName="SettleSubject" boProperty="debtcredit4" dictionaryName="YDDEBTCREDIT" divId="div_SettleSubjectdebtcredit4_dict" isNeedAuth="false" value="${customSingleClear.settleSubject.debtcredit4}" ></fisc:dictionary>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.amount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.amount4}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.amount4" name="SettleSubject.amount4" value="${customSingleClear.settleSubject.amount4}" <fisc:authentication sourceName="SettleSubject.amount4" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.standardamount4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.standardamount4}：</td>
<td width="30%" >
<input type="text" class="inputText" id="SettleSubject.standardamount4" name="SettleSubject.standardamount4" value="${customSingleClear.settleSubject.standardamount4}" <fisc:authentication sourceName="SettleSubject.standardamount4" nodeId="${workflowNodeDefId}"/>>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.settlesubject4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.settlesubject4}：</td>
<td width="40%" >
<div id="div_SettleSubjectsettlesubject4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectsettlesubject4_sh" boName="SettleSubject" boProperty="settlesubject4" value="${customSingleClear.settleSubject.settlesubject4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.profitcenter" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.profitcenter}：</td>
<td width="30%" >
<div id="div_SettleSubjectprofitcenter_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectprofitcenter_sh" boName="SettleSubject" boProperty="profitcenter" value="${customSingleClear.settleSubject.profitcenter}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.depid4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.depid4}：</td>
<td width="40%" >
<div id="div_SettleSubjectdepid4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectdepid4_sh" boName="SettleSubject" boProperty="depid4" value="${customSingleClear.settleSubject.depid4}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.orderno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.orderno4}：</td>
<td width="30%" >
<div id="div_SettleSubjectorderno4_sh"></div>
<fisc:searchHelp divId="div_SettleSubjectorderno4_sh" boName="SettleSubject" boProperty="orderno4" value="${customSingleClear.settleSubject.orderno4}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="SettleSubject.rowno4" nodeId="${workflowNodeDefId}"/> >${settleSubjectVt.property.rowno4}：</td>
<td width="40%" >
<input type="text" class="inputText" id="SettleSubject.rowno4" name="SettleSubject.rowno4" value="${customSingleClear.settleSubject.rowno4}" <fisc:authentication sourceName="SettleSubject.rowno4" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<input type="hidden" id="SettleSubject.settlesubjectid" name="SettleSubject.settlesubjectid" value="${customSingleClear.settleSubject.settlesubjectid}">
</table>
</form>
</div>
<div id="div_fundFlow" class="x-hide-display">
<form id="fundFlowForm" name="fundFlowForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.debtcredit1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit1}：</td>
<td width="30%" >
<div id="div_FundFlowdebtcredit1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit1" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit1_dict" isNeedAuth="false" value="${customSingleClear.fundFlow.debtcredit1}" ></fisc:dictionary>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.amount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount1}：</td>
<td width="40%" >
<input type="text" class="inputText" id="FundFlow.amount1" name="FundFlow.amount1" value="${customSingleClear.fundFlow.amount1}" <fisc:authentication sourceName="FundFlow.amount1" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.standardamount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount1}：</td>
<td width="30%" >
<input type="text" class="inputText" id="FundFlow.standardamount1" name="FundFlow.standardamount1" value="${customSingleClear.fundFlow.standardamount1}" <fisc:authentication sourceName="FundFlow.standardamount1" nodeId="${workflowNodeDefId}"/>>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.specialaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount1}：</td>
<td width="30%" >
<div id="div_FundFlowspecialaccount1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount1" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount1_dict" isNeedAuth="false" value="${customSingleClear.fundFlow.specialaccount1}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.customer1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer1}：</td>
<td width="30%" >
<div id="div_FundFlowcustomer1_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer1_sh" boName="FundFlow" boProperty="customer1" value="${customSingleClear.fundFlow.customer1}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.depid1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid1}：</td>
<td width="40%" >
<div id="div_FundFlowdepid1_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid1_sh" boName="FundFlow" boProperty="depid1" value="${customSingleClear.fundFlow.depid1}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.antiaccount1" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount1}：</td>
<td width="30%" >
<div id="div_FundFlowantiaccount1_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount1" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount1_dict" isNeedAuth="false" value="${customSingleClear.fundFlow.antiaccount1}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.debtcredit2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit2}：</td>
<td width="40%" >
<div id="div_FundFlowdebtcredit2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit2" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit2_dict" isNeedAuth="false" value="${customSingleClear.fundFlow.debtcredit2}" ></fisc:dictionary>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.amount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount2}：</td>
<td width="30%" >
<input type="text" class="inputText" id="FundFlow.amount2" name="FundFlow.amount2" value="${customSingleClear.fundFlow.amount2}" <fisc:authentication sourceName="FundFlow.amount2" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.standardamount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount2}：</td>
<td width="40%" >
<input type="text" class="inputText" id="FundFlow.standardamount2" name="FundFlow.standardamount2" value="${customSingleClear.fundFlow.standardamount2}" <fisc:authentication sourceName="FundFlow.standardamount2" nodeId="${workflowNodeDefId}"/>>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.specialaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount2}：</td>
<td width="30%" >
<div id="div_FundFlowspecialaccount2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount2" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount2_dict" isNeedAuth="false" value="${customSingleClear.fundFlow.specialaccount2}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.customer2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer2}：</td>
<td width="40%" >
<div id="div_FundFlowcustomer2_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer2_sh" boName="FundFlow" boProperty="customer2" value="${customSingleClear.fundFlow.customer2}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.depid2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid2}：</td>
<td width="30%" >
<div id="div_FundFlowdepid2_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid2_sh" boName="FundFlow" boProperty="depid2" value="${customSingleClear.fundFlow.depid2}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.antiaccount2" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount2}：</td>
<td width="40%" >
<div id="div_FundFlowantiaccount2_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount2" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount2_dict" isNeedAuth="false" value="${customSingleClear.fundFlow.antiaccount2}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.debtcredit3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.debtcredit3}：</td>
<td width="30%" >
<div id="div_FundFlowdebtcredit3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="debtcredit3" dictionaryName="YDDEBTCREDIT" divId="div_FundFlowdebtcredit3_dict" isNeedAuth="false" value="${customSingleClear.fundFlow.debtcredit3}" ></fisc:dictionary>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.amount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.amount3}：</td>
<td width="40%" >
<input type="text" class="inputText" id="FundFlow.amount3" name="FundFlow.amount3" value="${customSingleClear.fundFlow.amount3}" <fisc:authentication sourceName="FundFlow.amount3" nodeId="${workflowNodeDefId}"/>>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.standardamount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.standardamount3}：</td>
<td width="30%" >
<input type="text" class="inputText" id="FundFlow.standardamount3" name="FundFlow.standardamount3" value="${customSingleClear.fundFlow.standardamount3}" <fisc:authentication sourceName="FundFlow.standardamount3" nodeId="${workflowNodeDefId}"/>>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.specialaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.specialaccount3}：</td>
<td width="30%" >
<div id="div_FundFlowspecialaccount3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="specialaccount3" dictionaryName="YDUMSKZ" divId="div_FundFlowspecialaccount3_dict" isNeedAuth="false" value="${customSingleClear.fundFlow.specialaccount3}" ></fisc:dictionary>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.customer3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.customer3}：</td>
<td width="30%" >
<div id="div_FundFlowcustomer3_sh"></div>
<fisc:searchHelp divId="div_FundFlowcustomer3_sh" boName="FundFlow" boProperty="customer3" value="${customSingleClear.fundFlow.customer3}"></fisc:searchHelp>
</td>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.depid3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.depid3}：</td>
<td width="40%" >
<div id="div_FundFlowdepid3_sh"></div>
<fisc:searchHelp divId="div_FundFlowdepid3_sh" boName="FundFlow" boProperty="depid3" value="${customSingleClear.fundFlow.depid3}"></fisc:searchHelp>
</td>
</tr>
<tr>
<td align="right" width="15%" <fisc:authentication sourceName="FundFlow.antiaccount3" nodeId="${workflowNodeDefId}"/> >${fundFlowVt.property.antiaccount3}：</td>
<td width="30%" >
<div id="div_FundFlowantiaccount3_dict"></div>
<fisc:dictionary boName="FundFlow" boProperty="antiaccount3" dictionaryName="YDYESORNO" divId="div_FundFlowantiaccount3_dict" isNeedAuth="false" value="${customSingleClear.fundFlow.antiaccount3}" ></fisc:dictionary>
</td>
<td></td><td></td>
</tr>
<input type="hidden" id="FundFlow.fundflowid" name="FundFlow.fundflowid" value="${customSingleClear.fundFlow.fundflowid}">
</table>
</form>
</div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/singleClear/customSingleClearAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/singleClear/customSingleClearAddGen.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//是否已经提交流程
var isSubmited = '${customSingleClear.processstate}';

//页面文本
var Txt={
	//客户单清帐
	customSingleClear:'${vt.boText}',
	          
	//未清应收(借方)
	unClearCustomBill:'${unClearCustomBillVt.boText}',
          
	//未清收款(贷方)
	unClearCollect:'${unClearCollectVt.boText}',
          
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
	customSingleClear_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	customSingleClear_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	customSingleClear_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:0,
				            		contentEl:'div_center'
						}
						,{
									xtype:'tabpanel',
					            	height:410,
					            	id:'tabs',
					            	name:'tabs',
					            	defaults:{bodyStyle:"background-color:#DFE8F6"},
					            	autoScroll: true,					            	
									activeTab:0,
						           items:[
			              						                {
						            		title:'${unClearCustomBillVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'unClearCustomBillTab',
						            		contentEl:'div_unClearCustomBill'
						            	},
          						                {
						            		title:'${unClearCollectVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'unClearCollectTab',
						            		contentEl:'div_unClearCollect'
						            	},
          						                {
						            		title:'${settleSubjectVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'settleSubjectTab',
						            		autoHeight:true,
						            		contentEl:'div_settleSubject'
						            	},
          						                {
						            		title:'${fundFlowVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'fundFlowTab',
						            		autoHeight:true,
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
					          
          
          
          
					{id:'_saveOrUpdateCustomSingleClear',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateCustomSingleClear,iconCls:'icon-table-save'},'-',
          
          
          
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},
					          
					{id:'_queryUnClear',text:'查询',handler:_queryUnClearCustomSingleClear,iconCls:'icon-search'},'-',
          
					{id:'_autoAssign',text:'自动分配',handler:_autoAssignCustomSingleClear,iconCls:'icon-assign'},'-',
          
					{id:'_clearAssign',text:'清除分配',handler:_clearAssignCustomSingleClear,iconCls:'icon-deletes'},'-',
          
          
					{id:'_submitClear',text:'确认清帐',handler:_submitClearCustomSingleClear,iconCls:'task'},'-',
          
					{id:'_blankOut',text:'作废',handler:_blankOutCustomSingleClear,iconCls:'icon-delete'},'-',
          
					{id:'_voucherPreview',text:'模拟凭证',handler:_voucherPreviewCustomSingleClear,iconCls:'icon-view'},'-',
          			{id:'_voucherPreview2',text:'模拟凭证2',handler:_voucherPreviewCustomSingleClear2,iconCls:'icon-view'},'-',
          			{id:'_updateCT',text:'检验数据',handler:_updateCT,iconCls:'icon-view'},'-',
					' '],  
			renderTo:"div_toolbar"
	});
	
	
	var isTask = '${workflowTaskId}';
	if(!isTask||isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited&&isSubmited!=''&&isSubmited!=' '){
Ext.getCmp('_saveOrUpdate').disable();		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
	}

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
//});
</script>

