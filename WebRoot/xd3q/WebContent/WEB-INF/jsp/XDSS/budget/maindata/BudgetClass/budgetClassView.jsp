<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月11日 12点13分09秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象预算分类(BudgetClass)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.viewPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetClass/budgetClassView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetClass/budgetClassViewGen.js"></script>
</head>
<body>
<fisc:grid divId="div_budgetTemplate" boName="BudgetTemplate" needToolbar="false" needCheckBox="true" editable="false" defaultCondition=" YBUDTEM.BUDCLASSID='${budgetClass.budclassid}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${budgetClass.budclassid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right" >${vt.property.budclassname}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="BudgetClass.budclassname" name="BudgetClass.budclassname" value="${budgetClass.budclassname}" <fisc:authentication sourceName="BudgetClass.budclassname" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >${vt.property.budclassdesc}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="BudgetClass.budclassdesc" name="BudgetClass.budclassdesc" value="${budgetClass.budclassdesc}" <fisc:authentication sourceName="BudgetClass.budclassdesc" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.boid}：</td>
		<td width="30%">
			<div id="div_boid_sh"></div>
			<fisc:searchHelp divId="div_boid_sh" boName="BudgetClass" boProperty="boid"  value="${budgetClass.boid}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right" >${vt.property.version}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="BudgetClass.version" name="BudgetClass.version" value="${budgetClass.version}" <fisc:authentication sourceName="BudgetClass.version" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="BudgetClass" userId="${budgetClass.creator}"></fisc:user>
		</td>
		<td width="15%" align="right" >${vt.property.createtime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="BudgetClass.createtime" name="BudgetClass.createtime" value="${budgetClass.createtime}"  readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.lastmodifyer}：</td>
		<td width="30%">
			<fisc:user boProperty="lastmodifyer" boName="BudgetClass" userId="${budgetClass.lastmodifyer}"></fisc:user>
		</td>
		<td width="15%" align="right" >${vt.property.lastmodifytime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="BudgetClass.lastmodifytime" name="BudgetClass.lastmodifytime" value="${budgetClass.lastmodifytime}"  readonly="readonly">
		</td>
	</tr>

	<input type="hidden" id="BudgetClass.client" name="BudgetClass.client" value="${budgetClass.client}">
	<input type="hidden" id="BudgetClass.budclassid" name="BudgetClass.budclassid" value="${budgetClass.budclassid}">
	<input type="hidden" id="BudgetClass.budupclassid" name="BudgetClass.budupclassid" value="${budgetClass.budupclassid}">
</table>
</form>
</div>

	          
		        	         
							<div id="div_budgetTemplate"></div>
                     

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var budclassid = '${budgetClass.budclassid}';	

//页面文本
var Txt={
	//预算分类
	budgetClass:'${vt.boText}',
	          
	//预算模版
	budgetTemplate:'${budgetTemplateVt.boText}',
	//boText创建
	budgetTemplate_Create:'${budgetTemplateVt.boTextCreate}',
	//boText复制创建
	budgetTemplate_CopyCreate:'${budgetTemplateVt.boTextCopyCreate}',
	// 进行【预算模版复制创建】操作时，只允许选择一条记录！
	budgetTemplate_CopyCreate_AllowOnlyOneItemForOperation:'${budgetTemplateVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【预算模版复制创建】操作的记录！
	budgetTemplate_CopyCreate_SelectToOperation:'${budgetTemplateVt.copyCreate_SelectToOperate}',
	// 请选择需要进行【预算模版批量删除】操作的记录！
	budgetTemplate_Deletes_SelectToOperation:'${budgetTemplateVt.deletes_SelectToOperate}',
	// 您选择了【预算模版批量删除】操作，是否确定继续该操作？
	budgetTemplate_Deletes_ConfirmOperation:'${budgetTemplateVt.deletes_ConfirmOperation}',
	//boText复制创建
	budgetClass_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	budgetClass_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	budgetClass_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:160,
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
						            		title:'${budgetTemplateVt.boText}',
						            		layout:'fit',
						            		contentEl:'div_budgetTemplate'
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
{id:'_copyCreate',text:'${vt.mCopyCreate}',handler:_copyCreateBudgetClass,iconCls:'icon-copyCreate'},'-',
{id:'_create',text:'${vt.mCreate}',handler:_createBudgetClass,iconCls:'icon-add'},'-',
'->','-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelBudgetClass,iconCls:'icon-undo'},'-',
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
