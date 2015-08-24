<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年01月09日 11点03分53秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象采购订单(SAP)(PurchaseInfo)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.viewPage}</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchaseInfoView.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchaseInfoViewGen.js"></script>
</head>
<body>
<fisc:grid divId="div_purchaseRows" boName="PurchaseRows" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YPURCHASEROWS.PURCHASEINFOID='${purchaseInfo.purchaseinfoId}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${purchaseInfo.purchaseinfoId}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right" >${vt.property.contractNo}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="PurchaseInfo.contractNo" name="PurchaseInfo.contractNo" value="${purchaseInfo.contractNo}" <fisc:authentication sourceName="PurchaseInfo.contractNo" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >${vt.property.contractName}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchaseInfo.contractName" name="PurchaseInfo.contractName" value="${purchaseInfo.contractName}" <fisc:authentication sourceName="PurchaseInfo.contractName" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.invoicingParty}：</td>
		<td width="30%">
			<div id="div_invoicingParty_sh"></div>
			<fisc:searchHelp divId="div_invoicingParty_sh" boName="PurchaseInfo" boProperty="invoicingParty"  value="${purchaseInfo.invoicingParty}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right" >${vt.property.payer}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchaseInfo.payer" name="PurchaseInfo.payer" value="${purchaseInfo.payer}" <fisc:authentication sourceName="PurchaseInfo.payer" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.bsart}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="PurchaseInfo.bsart" name="PurchaseInfo.bsart" value="${purchaseInfo.bsart}" <fisc:authentication sourceName="PurchaseInfo.bsart" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >${vt.property.bstyp}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchaseInfo.bstyp" name="PurchaseInfo.bstyp" value="${purchaseInfo.bstyp}" <fisc:authentication sourceName="PurchaseInfo.bstyp" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.lifnr}：</td>
		<td width="30%">
			<div id="div_lifnr_sh"></div>
			<fisc:searchHelp divId="div_lifnr_sh" boName="PurchaseInfo" boProperty="lifnr"  value="${purchaseInfo.lifnr}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right" >${vt.property.lifnrName}：</td>
		<td  width="40%">
			<div id="div_lifnrName_sh"></div>
			<fisc:searchHelp divId="div_lifnrName_sh" boName="PurchaseInfo" boProperty="lifnrName"  value="${purchaseInfo.lifnrName}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.bedat}：</td>
		<td width="30%">
			<input type="text" id="PurchaseInfo.bedat" name="PurchaseInfo.bedat" value="">
				<fisc:calendar applyTo="PurchaseInfo.bedat"  divId="" fieldName="" defaultValue="${purchaseInfo.bedat}"></fisc:calendar>
		</td>
		<td width="15%" align="right" >${vt.property.ekorg}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchaseInfo.ekorg" name="PurchaseInfo.ekorg" value="${purchaseInfo.ekorg}" <fisc:authentication sourceName="PurchaseInfo.ekorg" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.ekgrp}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="PurchaseInfo.ekgrp" name="PurchaseInfo.ekgrp" value="${purchaseInfo.ekgrp}" <fisc:authentication sourceName="PurchaseInfo.ekgrp" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >${vt.property.zterm}：</td>
		<td  width="40%">
			<div id="div_zterm_sh"></div>
			<fisc:searchHelp divId="div_zterm_sh" boName="PurchaseInfo" boProperty="zterm"  value="${purchaseInfo.zterm}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.inco1}：</td>
		<td width="30%">
			<div id="div_inco1_sh"></div>
			<fisc:searchHelp divId="div_inco1_sh" boName="PurchaseInfo" boProperty="inco1"  value="${purchaseInfo.inco1}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right" >${vt.property.waers}：</td>
		<td  width="40%">
			<div id="div_waers_sh"></div>
			<fisc:searchHelp divId="div_waers_sh" boName="PurchaseInfo" boProperty="waers"  value="${purchaseInfo.waers}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.wkurs}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="PurchaseInfo.wkurs" name="PurchaseInfo.wkurs" value="${purchaseInfo.wkurs}" <fisc:authentication sourceName="PurchaseInfo.wkurs" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >${vt.property.ihrez}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchaseInfo.ihrez" name="PurchaseInfo.ihrez" value="${purchaseInfo.ihrez}" <fisc:authentication sourceName="PurchaseInfo.ihrez" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.unsez}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="PurchaseInfo.unsez" name="PurchaseInfo.unsez" value="${purchaseInfo.unsez}" <fisc:authentication sourceName="PurchaseInfo.unsez" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >${vt.property.telf1}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchaseInfo.telf1" name="PurchaseInfo.telf1" value="${purchaseInfo.telf1}" <fisc:authentication sourceName="PurchaseInfo.telf1" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.shipmentPort}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="PurchaseInfo.shipmentPort" name="PurchaseInfo.shipmentPort" value="${purchaseInfo.shipmentPort}" <fisc:authentication sourceName="PurchaseInfo.shipmentPort" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >${vt.property.destinationPort}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchaseInfo.destinationPort" name="PurchaseInfo.destinationPort" value="${purchaseInfo.destinationPort}" <fisc:authentication sourceName="PurchaseInfo.destinationPort" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.shipmentDate}：</td>
		<td width="30%">
			<input type="text" id="PurchaseInfo.shipmentDate" name="PurchaseInfo.shipmentDate" value="">
				<fisc:calendar applyTo="PurchaseInfo.shipmentDate"  divId="" fieldName="" defaultValue="${purchaseInfo.shipmentDate}"></fisc:calendar>
		</td>
		<td width="15%" align="right" >${vt.property.totalAmount}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchaseInfo.totalAmount" name="PurchaseInfo.totalAmount" value="${purchaseInfo.totalAmount}" <fisc:authentication sourceName="PurchaseInfo.totalAmount" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="PurchaseInfo" userId="${purchaseInfo.creator}"></fisc:user>
		</td>
		<td width="15%" align="right" >${vt.property.createTime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="PurchaseInfo.createTime" name="PurchaseInfo.createTime" value="${purchaseInfo.createTime}"  readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.lastmodifyTime}：</td>
		<td width="30%">
		    <input type="text" class="inputText" id="PurchaseInfo.lastmodifyTime" name="PurchaseInfo.lastmodifyTime" value="${purchaseInfo.lastmodifyTime}"  readonly="readonly">
		</td>
		<td width="15%" align="right" >${vt.property.lastmodifyer}：</td>
		<td  width="40%">
			<fisc:user boProperty="lastmodifyer" boName="PurchaseInfo" userId="${purchaseInfo.lastmodifyer}"></fisc:user>
		</td>
	</tr>

	<input type="hidden" id="PurchaseInfo.pincr" name="PurchaseInfo.pincr" value="${purchaseInfo.pincr}">
	<input type="hidden" id="PurchaseInfo.oldcontractNo" name="PurchaseInfo.oldcontractNo" value="${purchaseInfo.oldcontractNo}">
	<input type="hidden" id="PurchaseInfo.tradeType" name="PurchaseInfo.tradeType" value="${purchaseInfo.tradeType}">
	<input type="hidden" id="PurchaseInfo.projectName" name="PurchaseInfo.projectName" value="${purchaseInfo.projectName}">
	<input type="hidden" id="PurchaseInfo.payerName" name="PurchaseInfo.payerName" value="${purchaseInfo.payerName}">
	<input type="hidden" id="PurchaseInfo.ztermName" name="PurchaseInfo.ztermName" value="${purchaseInfo.ztermName}">
	<input type="hidden" id="PurchaseInfo.invoicingName" name="PurchaseInfo.invoicingName" value="${purchaseInfo.invoicingName}">
	<input type="hidden" id="PurchaseInfo.inco1Name" name="PurchaseInfo.inco1Name" value="${purchaseInfo.inco1Name}">
	<input type="hidden" id="PurchaseInfo.inco2" name="PurchaseInfo.inco2" value="${purchaseInfo.inco2}">
	<input type="hidden" id="PurchaseInfo.waersName" name="PurchaseInfo.waersName" value="${purchaseInfo.waersName}">
	<input type="hidden" id="PurchaseInfo.purchaseinfoId" name="PurchaseInfo.purchaseinfoId" value="${purchaseInfo.purchaseinfoId}">
	<input type="hidden" id="PurchaseInfo.contractgroupId" name="PurchaseInfo.contractgroupId" value="${purchaseInfo.contractgroupId}">
	<input type="hidden" id="PurchaseInfo.projectId" name="PurchaseInfo.projectId" value="${purchaseInfo.projectId}">
	<input type="hidden" id="PurchaseInfo.processState" name="PurchaseInfo.processState" value="${purchaseInfo.processState}">
</table>
</form>
</div>

	          
		        	         
							<div id="div_purchaseRows"></div>
                     

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${purchaseInfo.processState}';
//当前对象主键ID
var purchaseinfoId = '${purchaseInfo.purchaseinfoId}';	

//页面文本
var Txt={
	//采购订单(SAP)
	purchaseInfo:'${vt.boText}',
	          
	//采购订单行项目信息(SAP)
	purchaseRows:'${purchaseRowsVt.boText}',
	//boText创建
	purchaseRows_Create:'${purchaseRowsVt.boTextCreate}',
	//boText复制创建
	purchaseRows_CopyCreate:'${purchaseRowsVt.boTextCopyCreate}',
	// 进行【采购订单行项目信息(SAP)复制创建】操作时，只允许选择一条记录！
	purchaseRows_CopyCreate_AllowOnlyOneItemForOperation:'${purchaseRowsVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【采购订单行项目信息(SAP)复制创建】操作的记录！
	purchaseRows_CopyCreate_SelectToOperation:'${purchaseRowsVt.copyCreate_SelectToOperate}',
	//boText复制创建
	purchaseInfo_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	purchaseInfo_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	purchaseInfo_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		border:false,
				            		//height:497.5,
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
						            		title:'${purchaseRowsVt.boText}',
						            		layout:'fit',
						            		contentEl:'div_purchaseRows'
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
{id:'_copyCreate',text:'${vt.mCopyCreate}',handler:_copyCreatePurchaseInfo,iconCls:'icon-copyCreate'},'-',
{id:'_create',text:'${vt.mCreate}',handler:_createPurchaseInfo,iconCls:'icon-add'},'-',
'->','-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelPurchaseInfo,iconCls:'icon-undo'},'-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessPurchaseInfo,iconCls:'task'},'-',
<%}%>
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
    var tabsSize = 1;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 1 ; i++){
		   tabs.setActiveTab(1-1-i);
		}
	}
 });
</script>
