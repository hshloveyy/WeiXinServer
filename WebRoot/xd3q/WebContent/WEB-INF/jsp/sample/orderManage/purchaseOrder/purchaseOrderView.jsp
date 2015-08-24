﻿<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月31日 15点00分58秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象采购订单(PurchaseOrder)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.viewPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/orderManage/purchaseOrder/purchaseOrderView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/orderManage/purchaseOrder/purchaseOrderViewGen.js"></script>
</head>
<body>
<fisc:grid divId="div_orderItems" boName="OrderItems" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YORDERITEMS.PURCHASEORDERID='${purchaseOrder.purchaseOrderId}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:attachement businessId="${businessId}" allowDelete="true" divId="div_attachement" boName="PurchaseOrder" gridPageSize="10" gridHeight="285" ></fisc:attachement>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${purchaseOrder.purchaseOrderId}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right" >${vt.property.purchaseOrderNo}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="PurchaseOrder.purchaseOrderNo" name="PurchaseOrder.purchaseOrderNo" value="${purchaseOrder.purchaseOrderNo}" <fisc:authentication sourceName="PurchaseOrder.purchaseOrderNo" taskId="${workflowTaskId}"/>   readonly="readonly">
		</td>
		<td width="15%" align="right" >${vt.property.supplierNo}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchaseOrder.supplierNo" name="PurchaseOrder.supplierNo" value="${purchaseOrder.supplierNo}" <fisc:authentication sourceName="PurchaseOrder.supplierNo" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.certificateType}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="PurchaseOrder.certificateType" name="PurchaseOrder.certificateType" value="${purchaseOrder.certificateType}" <fisc:authentication sourceName="PurchaseOrder.certificateType" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >${vt.property.certificateDate}：</td>
		<td  width="40%">
			<input type="text" id="PurchaseOrder.certificateDate" name="PurchaseOrder.certificateDate" value="">
				<fisc:calendar applyTo="PurchaseOrder.certificateDate"  divId="" fieldName="" defaultValue="${purchaseOrder.certificateDate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.salespeople}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="PurchaseOrder.salespeople" name="PurchaseOrder.salespeople" value="${purchaseOrder.salespeople}" <fisc:authentication sourceName="PurchaseOrder.salespeople" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >${vt.property.client}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchaseOrder.client" name="PurchaseOrder.client" value="${purchaseOrder.client}" <fisc:authentication sourceName="PurchaseOrder.client" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" valign="top">${vt.property.address}：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="PurchaseOrder.address" name="PurchaseOrder.address" <fisc:authentication sourceName="PurchaseOrder.address" taskId="${workflowTaskId}"/>>${purchaseOrder.address}</textarea>
		</td>
	</tr>

	<input type="hidden" id="PurchaseOrder.purchaseOrderId" name="PurchaseOrder.purchaseOrderId" value="${purchaseOrder.purchaseOrderId}">
</table>
</form>
<!-- 生成子对象分组布局-->
	          
           
	    <form id="orderItemsForm" name="orderItemsForm" class="search">
		 <div style="background-color:#DFE8F6;width:100%">
         <BR>
         </div>
		 <fieldset><legend>${orderItemsVt.boText}</legend>
			<BR>
				<div id="div_orderItems" ></div>
			<BR>
		</fieldset>
		<div style="background-color:#DFE8F6;width:100%"><BR></div>
		</form>
</div>

<div id="div_attachement"></div>
<div id="div_purchaseOrder" class="x-hide-display"> 
<form id="purchaseOrderForm" name="purchaseOrderForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
			<tr>
				<td width="15%" align="right" valign="top">${vt.property.memo}：</td>
				<td width="30%"  colspan="3" >
					<textarea rows="4" cols="54" id="PurchaseOrder.memo" name="PurchaseOrder.memo" <fisc:authentication sourceName="PurchaseOrder.memo" taskId="${workflowTaskId}"/>>${purchaseOrder.memo}</textarea>
				</td>
				</tr>
			<tr>
				<td width="15%" align="right" >${vt.property.creator}：</td>
				<td width="30%" >
					<fisc:user boProperty="creator" boName="PurchaseOrder" userId="${purchaseOrder.creator}"></fisc:user>
				</td>
				<td width="15%" align="right" >${vt.property.createTime}：</td>
				<td  width="40%" >
		   			 <input type="text" class="inputText" id="PurchaseOrder.createTime" name="PurchaseOrder.createTime" value="${purchaseOrder.createTime}"  <fisc:authentication sourceName="PurchaseOrder.createTime" taskId="${workflowTaskId}"/> readonly="readonly">
				</td>
				</tr>
			<tr>
				<td width="15%" align="right" >${vt.property.lastModifyor}：</td>
				<td width="30%" >
					<fisc:user boProperty="lastModifyor" boName="PurchaseOrder" userId="${purchaseOrder.lastModifyor}"></fisc:user>
				</td>
				<td width="15%" align="right" >${vt.property.lastModifyTime}：</td>
				<td  width="40%" >
		   			 <input type="text" class="inputText" id="PurchaseOrder.lastModifyTime" name="PurchaseOrder.lastModifyTime" value="${purchaseOrder.lastModifyTime}"  <fisc:authentication sourceName="PurchaseOrder.lastModifyTime" taskId="${workflowTaskId}"/> readonly="readonly">
				</td>
				</tr>
	</table>
	</form>
	</div> 

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${purchaseOrder.processState}';
//当前对象主键ID
var purchaseOrderId = '${purchaseOrder.purchaseOrderId}';	

//页面文本
var Txt={
	//采购订单
	purchaseOrder:'${vt.boText}',
	          
	//采购行项目
	orderItems:'${orderItemsVt.boText}',
	//boText创建
	orderItems_Create:'${orderItemsVt.boTextCreate}',
	//boText复制创建
	orderItems_CopyCreate:'${orderItemsVt.boTextCopyCreate}',
	// 进行【采购行项目复制创建】操作时，只允许选择一条记录！
	orderItems_CopyCreate_AllowOnlyOneItemForOperation:'${orderItemsVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【采购行项目复制创建】操作的记录！
	orderItems_CopyCreate_SelectToOperation:'${orderItemsVt.copyCreate_SelectToOperate}',
          
	//boText复制创建
	purchaseOrder_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	purchaseOrder_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	purchaseOrder_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:122.5,
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
							            		title:'${vt.boText}',
							            		layout:'fit',
							            		autoWidth:true,
							            		contentEl:'div_purchaseOrder'
							             },
				          
						                {
						            		title:'${attachementVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_attachement'
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
{id:'_create',text:'${vt.mCreate}',handler:_createPurchaseOrder,iconCls:'icon-add'},'-',
{id:'_copyCreate',text:'${vt.mCopyCreate}',handler:_copyCreatePurchaseOrder,iconCls:'icon-copyCreate'},'-',
{id:'_print',xtype:'printbutton',text:'${vt.sPrint}',boName:'PurchaseOrder',methodName:'_print',className:'com.infolion.sample.orderManage.purchaseOrder.PurchaseOrder',businessId:'${purchaseOrder.purchaseOrderId}'},'-',
'->','-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelPurchaseOrder,iconCls:'icon-undo'},'-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessPurchaseOrder,iconCls:'task'},'-',
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
<%
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
Ext.getCmp('_submitProcess').disable();
<%}%>
Ext.getCmp('_create').disable();
Ext.getCmp('_copyCreate').disable();		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
Ext.getCmp('_create').disable();
Ext.getCmp('_copyCreate').disable();	}
	
//});
Ext.onReady(function(){
    var tabsSize = 2;
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < 2 ; i++){
		   tabs.setActiveTab(2-1-i);
		}
	}
 });
</script>
