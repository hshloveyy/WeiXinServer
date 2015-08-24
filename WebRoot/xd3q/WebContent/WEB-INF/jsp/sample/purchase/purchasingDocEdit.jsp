<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月04日 10点48分25秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>SAP采购凭证(PurchasingDoc)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SAP采购凭证编辑页面</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchasingDocEdit.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchasingDocEditGen.js"></script>
<style type="text/css">
  .x-grid3-header-offset {width: auto;}
</style>
</head>
<body>
<fisc:grid divId="div_purchasingDocItem" boName="PurchasingDocItem" needCheckBox="true" editable="true" defaultCondition=" EKPO.EBELN='${purchasingDoc.ebeln}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${purchasingDoc.ebeln}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%" >采购凭证号：</td>
		<td width="30%">
			<input type="text" class="inputText" id="PurchasingDoc.ebeln" name="PurchasingDoc.ebeln" value="${purchasingDoc.ebeln}"   <fisc:authentication sourceName="PurchasingDoc.ebeln" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >采购凭证的状态：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchasingDoc.statu" name="PurchasingDoc.statu" value="${purchasingDoc.statu}"   <fisc:authentication sourceName="PurchasingDoc.statu" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >记录的创建日期：</td>
		<td width="30%">
			<input type="text" id="PurchasingDoc.aedat" name="PurchasingDoc.aedat" value="">
				<fisc:calendar applyTo="PurchasingDoc.aedat"  divId="" fieldName="" defaultValue="${purchasingDoc.aedat}"></fisc:calendar>
		</td>
		<td align="right" width="15%" >最后审批人：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchasingDoc.lastappname" name="PurchasingDoc.lastappname" value="${purchasingDoc.lastappname}"   <fisc:authentication sourceName="PurchasingDoc.lastappname" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >公司代码：</td>
		<td width="30%">
			<input type="text" class="inputText" id="PurchasingDoc.bukrs" name="PurchasingDoc.bukrs" value="${purchasingDoc.bukrs}"   <fisc:authentication sourceName="PurchasingDoc.bukrs" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >最后审批时间：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="PurchasingDoc.lastapptime" name="PurchasingDoc.lastapptime" value="${purchasingDoc.lastapptime}"   <fisc:authentication sourceName="PurchasingDoc.lastapptime" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >付款条件代码：</td>
		<td width="30%">
			<input type="text" class="inputText" id="PurchasingDoc.zterm" name="PurchasingDoc.zterm" value="${purchasingDoc.zterm}"   <fisc:authentication sourceName="PurchasingDoc.zterm" taskId="${workflowTaskId}"/>>
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="PurchasingDoc.client" name="PurchasingDoc.client" value="${purchasingDoc.client}">
	<input type="hidden" id="PurchasingDoc.waers" name="PurchasingDoc.waers" value="${purchasingDoc.waers}">
	<input type="hidden" id="PurchasingDoc.processstate" name="PurchasingDoc.processstate" value="${purchasingDoc.processstate}">
</table>
</form>
</div>

	          
		        	         
							<div id="div_purchasingDocItem"></div>
                     
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${purchasingDoc.processstate}';
//当前对象主键ID
var ebeln = '${purchasingDoc.ebeln}';	

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
					            title: '流程审批信息',
					            border:false,
					            layout:'fit',
					            contentEl:'div_top_north'
					          },
				              {
				            		title:'SAP采购凭证信息',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:160,
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
						            		title:'SAP采购凭证行项目',
						            		layout:'fit',
						            		contentEl:'div_purchasingDocItem'
						            	}
				          
						    ]}
						   ]}
						,{
							id:'historyWorkflowTask',
							region:'south',
							title:'流程跟踪信息',
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
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'提交',handler:_submitProcessPurchasingDoc,iconCls:'task'},'-',
<%}%>
{id:'_cancel',text:'取消',handler:_cancelPurchasingDoc,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	var isTask = '${workflowTaskId}';
	if(!isTask || isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited && isSubmited!='' && isSubmited!=' '){
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