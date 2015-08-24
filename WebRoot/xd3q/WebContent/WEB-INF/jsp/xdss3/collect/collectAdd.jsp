<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年08月20日 01点22分46秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象收款(Collect)增加页面
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
<fisc:grid divId="div_collectItem" boName="CollectItem" needCheckBox="true" editable="true" defaultCondition=" YCOLLECTITEM.COLLECTID='${collect.collectid}'" needAutoLoad="true" height="285" nameSapceSupport="true" pageSize="10000"></fisc:grid>
<fisc:grid divId="div_collectCbill" boName="CollectCbill" needCheckBox="true" editable="true" defaultCondition=" YCOLLECTCBILL.COLLECTID='${collect.collectid}'" needAutoLoad="true" height="285" nameSapceSupport="true" pageSize="10000"></fisc:grid>
<fisc:grid divId="div_collectRelated" boName="CollectRelated" needCheckBox="true" editable="true" defaultCondition=" YCOLLECTRELATED.COLLECTID='${collect.collectid}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:grid divId="div_collectBankItem" boName="CollectBankItem" needCheckBox="true" editable="true" defaultCondition=" YCOLLECTBANKITEM.COLLECTID='${collect.collectid}'" needAutoLoad="true" height="285" nameSapceSupport="true" pageSize="10000"></fisc:grid>
<fisc:attachement businessId="${businessId}" boIdField="Collect.collectid" allowDelete="true" divId="div_attachement" boName="Collect" boProperty="attachement" gridPageSize="10" gridHeight="285" ></fisc:attachement>
<div id="div_top_south" ></div>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${collect.collectid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
   <tr>
		<td colspan="3" align="center"><font color="red">温馨提示：出口业务请慎重选择“收款方式”、非TT项下请选择正确的出单发票号！</font></td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.collectno" nodeId="${workflowNodeDefId}"/> >${vt.property.collectno}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Collect.collectno" name="Collect.collectno" value="${collect.collectno}" <fisc:authentication sourceName="Collect.collectno" nodeId="${workflowNodeDefId}"/>  readonly="readonly">
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.customer" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.customer}：</td>
		<td   width="40%" >
			<div id="div_customer_sh"></div>
			<fisc:searchHelp divId="div_customer_sh" boName="Collect" boProperty="customer" value="${collect.customer}" defaultCondition="BUKRS = '${companycode}'"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.oldcollectno" nodeId="${workflowNodeDefId}"/> >${vt.property.oldcollectno}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Collect.oldcollectno" name="Collect.oldcollectno" value="${collect.oldcollectno}" <fisc:authentication sourceName="Collect.oldcollectno" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.incsuretybond" nodeId="${workflowNodeDefId}"/> >${vt.property.incsuretybond}：</td>
		<td   width="40%" >
			<div id="div_incsuretybond_dict"></div>
			<fisc:dictionary boName="Collect" boProperty="incsuretybond" dictionaryName="YDYESORNO" divId="div_incsuretybond_dict" isNeedAuth="false"  value="${collect.incsuretybond}"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.collecttype" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.collecttype}：</td>
		<td  width="30%" >
			<div id="div_collecttype_dict"></div>
			<fisc:dictionary boName="Collect" boProperty="collecttype" dictionaryName="YDCOLLECTTYPE" divId="div_collecttype_dict" isNeedAuth="false"  value="${collect.collecttype}"  ></fisc:dictionary>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.applyamount" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.applyamount}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Collect.applyamount" name="Collect.applyamount" value="${collect.applyamount}" <fisc:authentication sourceName="Collect.applyamount" nodeId="${workflowNodeDefId}"/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.currency" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.currency}：</td>
		<td  width="30%" >
			<div id="div_currency_sh"></div>
			<fisc:searchHelp divId="div_currency_sh" boName="Collect" boProperty="currency" value="${collect.currency}" callBackHandler="setOtherCurrency"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.draft" nodeId="${workflowNodeDefId}"/> >${vt.property.draft}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Collect.draft" name="Collect.draft" value="${collect.draft}" <fisc:authentication sourceName="Collect.draft" nodeId="${workflowNodeDefId}"/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.export_apply_no" nodeId="${workflowNodeDefId}"/> >${vt.property.export_apply_no}：</td>
		<td  width="30%" >
			<div id="div_export_apply_no_sh"></div>
			<fisc:searchHelp divId="div_export_apply_no_sh" boName="Collect" boProperty="export_apply_no" value="${collect.export_apply_no}"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.billcurrency" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.billcurrency}：</td>
		<td   width="40%" >
			<div id="div_billcurrency_sh"></div>
			<fisc:searchHelp divId="div_billcurrency_sh" boName="Collect" boProperty="billcurrency" value="${collect.billcurrency}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.convertamount" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.convertamount}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Collect.convertamount" name="Collect.convertamount" value="${collect.convertamount}" <fisc:authentication sourceName="Collect.convertamount" nodeId="${workflowNodeDefId}"/> >
		</td>
		<!-- xx 
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.expiredate" nodeId="${workflowNodeDefId}"/> >${vt.property.expiredate}：</td>
		<td   width="40%" >
			<input type="text" id="Collect.expiredate" name="Collect.expiredate" value="">
				<fisc:calendar applyTo="Collect.expiredate"  divId="" fieldName="" defaultValue="${collect.expiredate}"></fisc:calendar>
		</td>-->
		<td align="right" width="15%"  <fisc:authentication sourceName="Collect.ticketbankid" nodeId="${workflowNodeDefId}"/> >${vt.property.ticketbankid}：</td>
		<td  width="30%">
			<div id="div_ticketbankid_sh"></div>
			<fisc:searchHelp divId="div_ticketbankid_sh" boName="Collect" boProperty="ticketbankid"  value="${collect.ticketbankid}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.billbc" nodeId="${workflowNodeDefId}"/> >${vt.property.billbc}：</td>
		<td  width="30%" >
			<div id="div_billbc_sh"></div>
			<fisc:searchHelp divId="div_billbc_sh" boName="Collect" boProperty="billbc" value="${collect.billbc}"></fisc:searchHelp>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.draftdate" nodeId="${workflowNodeDefId}"/> >${vt.property.draftdate}：</td>
		<td   width="40%" >
			<input type="text" id="Collect.draftdate" name="Collect.draftdate" value="">
				<fisc:calendar applyTo="Collect.draftdate"  divId="" fieldName="" defaultValue="${collect.draftdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.sendgoodsdate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.sendgoodsdate}：</td>
		<td  width="30%" >
			<input type="text" id="Collect.sendgoodsdate" name="Collect.sendgoodsdate" value="">
				<fisc:calendar applyTo="Collect.sendgoodsdate"  divId="" fieldName="" defaultValue="${collect.sendgoodsdate}"></fisc:calendar>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.billcheckdate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.billcheckdate}：</td>
		<td   width="40%" >
			<input type="text" id="Collect.billcheckdate" name="Collect.billcheckdate" value="">
				<fisc:calendar applyTo="Collect.billcheckdate"  divId="" fieldName="" defaultValue="${collect.billcheckdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.goodsamount" nodeId="${workflowNodeDefId}"/> >${vt.property.goodsamount}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Collect.goodsamount" name="Collect.goodsamount" value="${collect.goodsamount}" <fisc:authentication sourceName="Collect.goodsamount" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.replaceamount" nodeId="${workflowNodeDefId}"/> >${vt.property.replaceamount}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Collect.replaceamount" name="Collect.replaceamount" value="${collect.replaceamount}" <fisc:authentication sourceName="Collect.replaceamount" nodeId="${workflowNodeDefId}"/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.supplieramount" nodeId="${workflowNodeDefId}"/> >${vt.property.supplieramount}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Collect.supplieramount" name="Collect.supplieramount" value="${collect.supplieramount}" <fisc:authentication sourceName="Collect.supplieramount" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.dept_id" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.dept_id}：</td>
		<td   width="40%" >
			<div id="div_dept_id_sh"></div>
			<fisc:searchHelp divId="div_dept_id_sh" boName="Collect" boProperty="dept_id" value="${collect.dept_id}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
	<!-- 
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.curdisnegamount" nodeId="${workflowNodeDefId}"/> >${vt.property.curdisnegamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Collect.curdisnegamount" name="Collect.curdisnegamount" value="${collect.curdisnegamount}" <fisc:authentication sourceName="Collect.curdisnegamount" nodeId="${workflowNodeDefId}"/> readonly="readonly">
		</td>
	 -->
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.toldisnegamount" nodeId="${workflowNodeDefId}"/> >${vt.property.toldisnegamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Collect.toldisnegamount" name="Collect.toldisnegamount" value="${collect.toldisnegamount}" <fisc:authentication sourceName="Collect.toldisnegamount" nodeId="${workflowNodeDefId}"/> readonly="readonly">
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.text" nodeId="${workflowNodeDefId}"/> valign="top"><font color="red">★</font>${vt.property.text}：</td>
		<td  width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="Collect.text" name="Collect.text" <fisc:authentication sourceName="Collect.text" nodeId="${workflowNodeDefId}"/>>${collect.text}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.remark" nodeId="${workflowNodeDefId}"/> valign="top">${vt.property.remark}：</td>
		<td  width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="Collect.remark" name="Collect.remark" <fisc:authentication sourceName="Collect.remark" nodeId="${workflowNodeDefId}"/>>${collect.remark}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.collectrate" nodeId="${workflowNodeDefId}"/> >${vt.property.collectrate}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Collect.collectrate" name="Collect.collectrate" value="${collect.collectrate}" <fisc:authentication sourceName="Collect.collectrate" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.settlerate" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>${vt.property.settlerate}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Collect.settlerate" name="Collect.settlerate" value="${collect.settlerate}" <fisc:authentication sourceName="Collect.settlerate" nodeId="${workflowNodeDefId}"/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.voucherdate" nodeId="${workflowNodeDefId}"/> >${vt.property.voucherdate}：</td>
		<td  width="30%" >
			<input type="text" id="Collect.voucherdate" name="Collect.voucherdate" value="">
				<fisc:calendar applyTo="Collect.voucherdate"  divId="" fieldName="" defaultValue="${collect.voucherdate}"></fisc:calendar>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.accountdate" nodeId="${workflowNodeDefId}"/> >${vt.property.accountdate}：</td>
		<td   width="40%" >
			<input type="text" id="Collect.accountdate" name="Collect.accountdate" value="">
				<fisc:calendar applyTo="Collect.accountdate"  divId="" fieldName="" defaultValue="${collect.accountdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.actamount" nodeId="${workflowNodeDefId}"/> >${vt.property.actamount}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Collect.actamount" name="Collect.actamount" value="${collect.actamount}" <fisc:authentication sourceName="Collect.actamount" nodeId="${workflowNodeDefId}"/> >
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.actcurrency" nodeId="${workflowNodeDefId}"/> >${vt.property.actcurrency}：</td>
		<td   width="40%" >
			<div id="div_actcurrency_sh"></div>
			<fisc:searchHelp divId="div_actcurrency_sh" boName="Collect" boProperty="actcurrency" value="${collect.actcurrency}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td  width="30%" >
			<fisc:user boProperty="creator" boName="Collect" userId="${collect.creator}"></fisc:user>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="Collect.createtime" name="Collect.createtime" value="${collect.createtime}"  readonly="readonly" <fisc:authentication sourceName="Collect.createtime" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.lastmodifyer" nodeId="${workflowNodeDefId}"/> >${vt.property.lastmodifyer}：</td>
		<td  width="30%" >
			<fisc:user boProperty="lastmodifyer" boName="Collect" userId="${collect.lastmodifyer}"></fisc:user>
		</td>
		<td align="right"  width="15%" <fisc:authentication sourceName="Collect.lastmodifytime" nodeId="${workflowNodeDefId}"/> >${vt.property.lastmodifytime}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Collect.lastmodifytime" name="Collect.lastmodifytime" value="${collect.lastmodifytime}" readonly="readonly" <fisc:authentication sourceName="Collect.lastmodifytime" nodeId="${workflowNodeDefId}"/> >
		</td>
	</tr>

	<input type="hidden" id="Collect.oldcontract_no" name="Collect.oldcontract_no" value="${collect.oldcontract_no}">
	<input type="hidden" id="Collect.trade_type" name="Collect.trade_type" value="${collect.trade_type}">
	<input type="hidden" id="Collect.remainsuretybond" name="Collect.remainsuretybond" value="0">
	<input type="hidden" id="Collect.oldproject_no" name="Collect.oldproject_no" value="${collect.oldproject_no}">
	<input type="hidden" id="Collect.businessstate" name="Collect.businessstate" value="0">
	<input type="hidden" id="Collect.oldcollectitemid" name="Collect.oldcollectitemid" value="${collect.oldcollectitemid}">
	<input type="hidden" id="Collect.oldcollectid" name="Collect.oldcollectid" value="${collect.oldcollectid}">
	<input type="hidden" id="Collect.unnamercollectid" name="Collect.unnamercollectid" value="${collect.unnamercollectid}">
	<input type="hidden" id="Collect.processstate" name="Collect.processstate" value="${collect.processstate}">
	<input type="hidden" id="Collect.client" name="Collect.client" value="${collect.client}">
	<input type="hidden" id="Collect.collectid" name="Collect.collectid" value="${collect.collectid}">
	<input type="hidden" id="calActivityId" name="calActivityId" value="${calActivityId}">
	<input type="hidden" id="Collect.expiredate" name="Collect.expiredate" value="${collect.expiredate}">
	<input type="hidden" id="Collect.curdisnegamount" name="Collect.curdisnegamount" value="${collect.curdisnegamount}">
	<input type="hidden" id="toldisnegamount_bak" name="toldisnegamount_bak" value="${collect.toldisnegamount}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_collectItem" ></div>
<div id="div_collectCbill" ></div>
<div id="div_collectRelated" ></div>
<div id="div_settleSubject" class="x-hide-display">
</div>
<div id="div_fundFlow" class="x-hide-display">
</div>
<div id="div_collectBankItem" ></div>
<div id="div_attachement" ></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/collect/collectAddGen.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/collect/collectAdd.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//是否已经提交流程
var isSubmited = '${collect.processstate}';

var roletype = '${roletype}';

//页面文本
var Txt={
	//收款
	collect:'${vt.boText}',
	          
	//收款金额分配
	collectItem:'${collectItemVt.boText}',
	// 请选择需要进行【收款金额分配批量删除】操作的记录！
	collectItem_Deletes_SelectToOperation:'${collectItemVt.deletes_SelectToOperate}',
	// 您选择了【收款金额分配批量删除】操作，是否确定继续该操作？
	collectItem_Deletes_ConfirmOperation:'${collectItemVt.deletes_ConfirmOperation}',
          
	//收款清票
	collectCbill:'${collectCbillVt.boText}',
	// 请选择需要进行【收款清票批量删除】操作的记录！
	collectCbill_Deletes_SelectToOperation:'${collectCbillVt.deletes_SelectToOperate}',
	// 您选择了【收款清票批量删除】操作，是否确定继续该操作？
	collectCbill_Deletes_ConfirmOperation:'${collectCbillVt.deletes_ConfirmOperation}',
          
	//收款关联单据
	collectRelated:'${collectRelatedVt.boText}',
          
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
          
	//收款银行行项
	collectBankItem:'${collectBankItemVt.boText}',
	// 请选择需要进行【收款银行行项批量删除】操作的记录！
	collectBankItem_Deletes_SelectToOperation:'${collectBankItemVt.deletes_SelectToOperate}',
	// 您选择了【收款银行行项批量删除】操作，是否确定继续该操作？
	collectBankItem_Deletes_ConfirmOperation:'${collectBankItemVt.deletes_ConfirmOperation}',
	//boText复制创建
	collect_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	collect_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	collect_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
						            		title:'${collectItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'collectItemTab',
						            		contentEl:'div_collectItem'
						            	},
          						                {
						            		title:'${collectCbillVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'collectCbillTab',
						            		contentEl:'div_collectCbill'
						            	},
          						                {
						            		title:'${collectRelatedVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'collectRelatedTab',
						            		contentEl:'div_collectRelated'
						            	},
          						                {
						            		title:'${collectBankItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'collectBankItemTab',
						            		contentEl:'div_collectBankItem'
						            	},
          						                {
						            		title: '附件',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'attachementTab',
						            		disabled:true,
						            		contentEl:'div_attachement'
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
          
					{id:'_autoAssign',text:'自动分配',handler:_autoAssignCollect,iconCls:' '},'-',
          
					{id:'_clearAssign',text:'清除分配',handler:_clearAssignCollect,iconCls:' '},'-',          
          
					{id:'_voucherPreview',text:'模拟凭证',handler:_voucherPreviewCollect,iconCls:' '},'-',          
          
          			{id:'_cashJournal',text:'现金日记账',handler:_cashJournalCollect,iconCls:' '},'-',
          
          			<%-- {id:'_submitForReassign',text:'重分配提交',handler:_submitForReassignCollect,iconCls:'task'},'-', --%>
          			
					{id:'_saveOrUpdateCollect',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateCollect,iconCls:'icon-table-save'},'-',
          
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
					{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessCollect,disabled:true,iconCls:'task'},'-',
					{id:'_submitForReassign',text:'重分配提交',handler:_submitForReassignCollect,disabled:true,iconCls:'task'},'-',					
<%}%>		
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},
					          
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
    var tabsSize = 7;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 7 ; i++){
		   tabs.setActiveTab(7-1-i);
		}
	}
 });
//});
</script>

