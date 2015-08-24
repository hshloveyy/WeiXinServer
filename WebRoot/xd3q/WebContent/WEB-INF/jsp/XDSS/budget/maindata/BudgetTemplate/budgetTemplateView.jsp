﻿<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月05日 10点28分25秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象预算模版(BudgetTemplate)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.viewPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetTemplate/budgetTemplateView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetTemplate/budgetTemplateViewGen.js"></script>
</head>
<body>
<fisc:grid divId="div_budgetTemplateItem" 
           boName="BudgetTemplateItem" 
           needToolbar="false" 
           needCheckBox="true" 
           editable="false" 
           defaultCondition=" YBUDTEMITEM.BUDTEMID='${budgetTemplate.budtemid}'" 
           needAutoLoad="true" 
           height="285" 
           pageSize="200"
           nameSapceSupport="true">
           </fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${budgetTemplate.budtemid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right" >${vt.property.budtemname}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="BudgetTemplate.budtemname" name="BudgetTemplate.budtemname" value="${budgetTemplate.budtemname}" <fisc:authentication sourceName="BudgetTemplate.budtemname" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >${vt.property.budtemtype}：</td>
		<td  width="40%">
			<div id="div_budtemtype_dict"></div>
			<fisc:dictionary boName="BudgetTemplate" boProperty="budtemtype" dictionaryName="YDBUDTEMTYPE" divId="div_budtemtype_dict" isNeedAuth="false"  value="${budgetTemplate.budtemtype}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.boid}：</td>
		<td width="30%">
			<div id="div_boid_sh"></div>
			<fisc:searchHelp divId="div_boid_sh" boName="BudgetTemplate" boProperty="boid"  value="${budgetTemplate.boid}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right" >${vt.property.budconcycle}：</td>
		<td  width="40%">
			<div id="div_budconcycle_dict"></div>
			<fisc:dictionary boName="BudgetTemplate" boProperty="budconcycle" dictionaryName="YDBUDCONCYCLE" divId="div_budconcycle_dict" isNeedAuth="false"  value="${budgetTemplate.budconcycle}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="BudgetTemplate" userId="${budgetTemplate.creator}"></fisc:user>
		</td>
		<td width="15%" align="right" >${vt.property.createtime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="BudgetTemplate.createtime" name="BudgetTemplate.createtime" value="${budgetTemplate.createtime}"  readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.lastmodifyer}：</td>
		<td width="30%">
			<fisc:user boProperty="lastmodifyer" boName="BudgetTemplate" userId="${budgetTemplate.lastmodifyer}"></fisc:user>
		</td>
		<td width="15%" align="right" >${vt.property.lastmodifytime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="BudgetTemplate.lastmodifytime" name="BudgetTemplate.lastmodifytime" value="${budgetTemplate.lastmodifytime}"  readonly="readonly">
		</td>
	</tr>

	<input type="hidden" id="BudgetTemplate.client" name="BudgetTemplate.client" value="${budgetTemplate.client}">
	<input type="hidden" id="BudgetTemplate.budtemid" name="BudgetTemplate.budtemid" value="${budgetTemplate.budtemid}">
	<input type="hidden" id="BudgetTemplate.budgetClass.budclassid" name="BudgetTemplate.budgetClass.budclassid" value="${budgetTemplate.budgetClass.budclassid}">
</table>
</form>
</div>

	          
		        	         
							<div id="div_budgetTemplateItem"></div>
                     

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var budtemid = '${budgetTemplate.budtemid}';	

//页面文本
var Txt={
	//预算模版
	budgetTemplate:'${vt.boText}',
	          
	//模版预算项
	budgetTemplateItem:'${budgetTemplateItemVt.boText}',
	//boText创建
	budgetTemplateItem_Create:'${budgetTemplateItemVt.boTextCreate}',
	//boText复制创建
	budgetTemplateItem_CopyCreate:'${budgetTemplateItemVt.boTextCopyCreate}',
	// 进行【模版预算项复制创建】操作时，只允许选择一条记录！
	budgetTemplateItem_CopyCreate_AllowOnlyOneItemForOperation:'${budgetTemplateItemVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【模版预算项复制创建】操作的记录！
	budgetTemplateItem_CopyCreate_SelectToOperation:'${budgetTemplateItemVt.copyCreate_SelectToOperate}',
	//boText复制创建
	budgetTemplate_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	budgetTemplate_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	budgetTemplate_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
						            		title:'${budgetTemplateItemVt.boText}',
						            		layout:'fit',
						            		contentEl:'div_budgetTemplateItem'
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
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelBudgetTemplate,iconCls:'icon-undo'},'-',
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
