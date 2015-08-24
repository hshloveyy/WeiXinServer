<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年07月06日 11点02分32秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象发票校验(InvoiceVerify)查看页面
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
<fisc:grid divId="div_invoiceVerifyItem" boName="InvoiceVerifyItem" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YGETITEM.MAINCODE='${invoiceVerify.maincode}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${invoiceVerify.maincode}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="InvoiceVerify.belnr" nodeId="${workflowNodeDefId}"/> >${vt.property.belnr}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="InvoiceVerify.belnr" name="InvoiceVerify.belnr" value="${invoiceVerify.belnr}" <fisc:authentication sourceName="InvoiceVerify.belnr" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="InvoiceVerify.budat" nodeId="${workflowNodeDefId}"/> >${vt.property.budat}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="InvoiceVerify.budat" name="InvoiceVerify.budat" value="${invoiceVerify.budat}" <fisc:authentication sourceName="InvoiceVerify.budat" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="InvoiceVerify.bktxt" nodeId="${workflowNodeDefId}"/> >${vt.property.bktxt}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="InvoiceVerify.bktxt" name="InvoiceVerify.bktxt" value="${invoiceVerify.bktxt}" <fisc:authentication sourceName="InvoiceVerify.bktxt" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="InvoiceVerify.bldat" nodeId="${workflowNodeDefId}"/> >${vt.property.bldat}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="InvoiceVerify.bldat" name="InvoiceVerify.bldat" value="${invoiceVerify.bldat}" <fisc:authentication sourceName="InvoiceVerify.bldat" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="InvoiceVerify.taxin_dmbtr" nodeId="${workflowNodeDefId}"/> >${vt.property.taxin_dmbtr}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="InvoiceVerify.taxin_dmbtr" name="InvoiceVerify.taxin_dmbtr" value="${invoiceVerify.taxin_dmbtr}" <fisc:authentication sourceName="InvoiceVerify.taxin_dmbtr" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="InvoiceVerify.waers" nodeId="${workflowNodeDefId}"/> >${vt.property.waers}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="InvoiceVerify.waers" name="InvoiceVerify.waers" value="${invoiceVerify.waers}" <fisc:authentication sourceName="InvoiceVerify.waers" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="InvoiceVerify.tax" nodeId="${workflowNodeDefId}"/> >${vt.property.tax}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="InvoiceVerify.tax" name="InvoiceVerify.tax" value="${invoiceVerify.tax}" <fisc:authentication sourceName="InvoiceVerify.tax" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="InvoiceVerify.taxout_dmbtr" nodeId="${workflowNodeDefId}"/> >${vt.property.taxout_dmbtr}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="InvoiceVerify.taxout_dmbtr" name="InvoiceVerify.taxout_dmbtr" value="${invoiceVerify.taxout_dmbtr}" <fisc:authentication sourceName="InvoiceVerify.taxout_dmbtr" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="InvoiceVerify.kursf" nodeId="${workflowNodeDefId}"/> >${vt.property.kursf}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="InvoiceVerify.kursf" name="InvoiceVerify.kursf" value="${invoiceVerify.kursf}" <fisc:authentication sourceName="InvoiceVerify.kursf" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="InvoiceVerify.gjahr" nodeId="${workflowNodeDefId}"/> >${vt.property.gjahr}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="InvoiceVerify.gjahr" name="InvoiceVerify.gjahr" value="${invoiceVerify.gjahr}" <fisc:authentication sourceName="InvoiceVerify.gjahr" nodeId="${workflowNodeDefId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="InvoiceVerify.kursf" nodeId="${workflowNodeDefId}"/> >${vt.property.stblg}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="InvoiceVerify.stblg" name="InvoiceVerify.stblg" value="${invoiceVerify.stblg}" <fisc:authentication sourceName="InvoiceVerify.stblg" nodeId="${workflowNodeDefId}"/>  >
		</td>
		<td width="15%" align="right" ></td>
		<td  width="40%">
		</td>
	</tr>

	<input type="hidden" id="InvoiceVerify.client" name="InvoiceVerify.client" value="${invoiceVerify.client}">
	<input type="hidden" id="InvoiceVerify.maincode" name="InvoiceVerify.maincode" value="${invoiceVerify.maincode}">
	<input type="hidden" id="InvoiceVerify.stblg" name="InvoiceVerify.stblg" value="${invoiceVerify.stblg}">
	<input type="hidden" id="InvoiceVerify.stjah" name="InvoiceVerify.stjah" value="${invoiceVerify.stjah}">
	<input type="hidden" id="InvoiceVerify.yfkr" name="InvoiceVerify.yfkr" value="${invoiceVerify.yfkr}">
	<input type="hidden" id="InvoiceVerify.ydhr" name="InvoiceVerify.ydhr" value="${invoiceVerify.ydhr}">
	<input type="hidden" id="InvoiceVerify.ebeln" name="InvoiceVerify.ebeln" value="${invoiceVerify.ebeln}">
	<input type="hidden" id="InvoiceVerify.verkf" name="InvoiceVerify.verkf" value="${invoiceVerify.verkf}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_invoiceVerifyItem"></div>

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var maincode = '${invoiceVerify.maincode}';	

//页面文本
var Txt={
	//发票校验
	invoiceVerify:'${vt.boText}',
	          
	//发票校验行项目
	invoiceVerifyItem:'${invoiceVerifyItemVt.boText}',
	//boText复制创建
	invoiceVerify_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	invoiceVerify_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	invoiceVerify_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
						            		title:'${invoiceVerifyItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_invoiceVerifyItem'
						            	}
						    ]}
						   ]}
						]
				}
				 ]
	});
	
	var toolbars = new Ext.Toolbar({
			items:[
					'-',
'->','-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelInvoiceVerify,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	
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

<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/tradeMonitoring/invoiceVerifyView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/tradeMonitoring/invoiceVerifyViewGen.js"></script>

