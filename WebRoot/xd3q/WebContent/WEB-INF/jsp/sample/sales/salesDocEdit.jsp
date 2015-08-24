<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月04日 16点13分43秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>SAP销售订单(SalesDoc)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SAP销售订单编辑页面</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/sales/salesDocEdit.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/sales/salesDocEditGen.js"></script>
<style type="text/css">
  .x-grid3-header-offset {width: auto;}
</style>
</head>
<body>
<fisc:grid divId="div_salesDocItem" boName="SalesDocItem" needCheckBox="true" editable="true" defaultCondition=" YVSOITEM.VBELN='${salesDoc.vbeln}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${salesDoc.vbeln}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%" >销售凭证：</td>
		<td width="30%">
			<input type="text" class="inputText" id="SalesDoc.vbeln" name="SalesDoc.vbeln" value="${salesDoc.vbeln}"   <fisc:authentication sourceName="SalesDoc.vbeln" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >审批状态：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="SalesDoc.endorsestate" name="SalesDoc.endorsestate" value="${salesDoc.endorsestate}"   <fisc:authentication sourceName="SalesDoc.endorsestate" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >销售订单日期：</td>
		<td width="30%">
			<input type="text" id="SalesDoc.erdat" name="SalesDoc.erdat" value="">
				<fisc:calendar applyTo="SalesDoc.erdat"  divId="" fieldName="" defaultValue="${salesDoc.erdat}"></fisc:calendar>
		</td>
		<td align="right" width="15%" >最后审批人：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="SalesDoc.lastappname" name="SalesDoc.lastappname" value="${salesDoc.lastappname}"   <fisc:authentication sourceName="SalesDoc.lastappname" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" > 客户：</td>
		<td width="30%">
			<div id="div_kunnr_sh"></div>
			<fisc:searchHelp divId="div_kunnr_sh" boName="SalesDoc" boProperty="kunnr"  value="${salesDoc.kunnr}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%" >最后审批时间：</td>
		<td  width="40%">
			<input type="text" id="SalesDoc.lastapptime" name="SalesDoc.lastapptime" value="">
				<fisc:calendar applyTo="SalesDoc.lastapptime"  divId="" fieldName="" defaultValue="${salesDoc.lastapptime}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >付款条件：</td>
		<td width="30%">
			<input type="text" class="inputText" id="SalesDoc.zterm" name="SalesDoc.zterm" value="${salesDoc.zterm}"   <fisc:authentication sourceName="SalesDoc.zterm" taskId="${workflowTaskId}"/>>
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="SalesDoc.client" name="SalesDoc.client" value="${salesDoc.client}">
	<input type="hidden" id="SalesDoc.processstate" name="SalesDoc.processstate" value="${salesDoc.processstate}">
</table>
</form>
</div>

	          
		        	         
							<div id="div_salesDocItem"></div>
                     
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${salesDoc.processstate}';
//当前对象主键ID
var vbeln = '${salesDoc.vbeln}';	

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
				            		title:'SAP销售订单信息',
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
						            		title:'SAP销售凭证行项目',
						            		layout:'fit',
						            		contentEl:'div_salesDocItem'
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
{id:'_submitProcess',text:'提交',handler:_submitProcessSalesDoc,iconCls:'task'},'-',
<%}%>
{id:'_cancel',text:'取消',handler:_cancelSalesDoc,iconCls:'icon-undo'},'-',
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