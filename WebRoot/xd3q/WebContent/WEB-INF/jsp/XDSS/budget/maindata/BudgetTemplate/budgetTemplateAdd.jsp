<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月05日 10点28分25秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象预算模版(BudgetTemplate)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetTemplate/budgetTemplateAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetTemplate/budgetTemplateAddGen.js"></script>
</head>
<body>
<fisc:grid divId="div_budgetTemplateItem" 
           boName="BudgetTemplateItem" 
           needCheckBox="true" 
           editable="true" 
           defaultCondition=" YBUDTEMITEM.BUDTEMID='${budgetTemplate.budtemid}'" 
           needAutoLoad="true" 
           height="285" 
           pageSize="200"
           nameSapceSupport="true">
           </fisc:grid>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.budtemname}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BudgetTemplate.budtemname" name="BudgetTemplate.budtemname" value="${budgetTemplate.budtemname}" <fisc:authentication sourceName="BudgetTemplate.budtemname" taskId=""/>>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.budtemtype}：</td>
		<td width="30%" >
			<div id="div_budtemtype_dict"></div>
			<fisc:dictionary boName="BudgetTemplate" boProperty="budtemtype" dictionaryName="YDBUDTEMTYPE" divId="div_budtemtype_dict" isNeedAuth="false"  value="${budgetTemplate.budtemtype}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.boid}：</td>
		<td width="30%" >
			<div id="div_boid_sh"></div>
			<fisc:searchHelp divId="div_boid_sh" boName="BudgetTemplate" boProperty="boid" value="${budgetTemplate.boid}"></fisc:searchHelp>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.budconcycle}：</td>
		<td width="30%" >
			<div id="div_budconcycle_dict"></div>
			<fisc:dictionary boName="BudgetTemplate" boProperty="budconcycle" dictionaryName="YDBUDCONCYCLE" divId="div_budconcycle_dict" isNeedAuth="false"  value="${budgetTemplate.budconcycle}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.creator}：</td>
		<td width="30%" >
			<fisc:user boProperty="creator" boName="BudgetTemplate" userId="${budgetTemplate.creator}"></fisc:user>		
		</td>
		<td width="20%" align="right" >${vt.property.createtime}：</td>
		<td width="30%" >
			<input type="text" id="BudgetTemplate.createtime" name="BudgetTemplate.createtime" value="">
				<fisc:calendar applyTo="BudgetTemplate.createtime" format="Ymd" divId="" fieldName=""  defaultValue="${budgetTemplate.createtime}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.lastmodifyer}：</td>
		<td width="30%" >
			<fisc:user boProperty="lastmodifyer" boName="BudgetTemplate" userId="${budgetTemplate.lastmodifyer}"></fisc:user>		
		</td>
		<td width="20%" align="right" >${vt.property.lastmodifytime}：</td>
		<td width="30%" >
			<input type="text" id="BudgetTemplate.lastmodifytime" name="BudgetTemplate.lastmodifytime" value="">
				<fisc:calendar applyTo="BudgetTemplate.lastmodifytime" format="Ymd" divId="" fieldName=""  defaultValue="${budgetTemplate.lastmodifytime}"></fisc:calendar>
		</td>
	</tr>
<input type="hidden" id="BudgetTemplate.budtemid" name="BudgetTemplate.budtemid" value="">
<input type="hidden" id="BudgetTemplate.budgetClass.budclassid" name="BudgetTemplate.budgetClass.budclassid" value="${budgetTemplate.budgetClass.budclassid}">
</table>
</form>
</div>

	          
		        	         
						<div id="div_budgetTemplateItem" ></div>
                     
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

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
							layout: 'anchor',
				            height:600,
				            border:false,
				            autoScroll: true,
				            items:[{
				            		title: '${vt.boTextInfo}',
				            		layout:'fit',
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
						            		title: '${budgetTemplateItemVt.boText}',
						            		layout:'fit',
						            		id:'budgetTemplateItemTab',
						            		contentEl:'div_budgetTemplateItem'
						            	}
				          
						]
						}]
						}]
				}
				 ]
	});
	var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					          
					{id:'_save',text:'${vt.mSaveOrUpdate}',handler:_save,iconCls:'icon-table-save'},'-',
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},
					' '],  
			renderTo:"div_toolbar"
	});
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
//});
</script>
