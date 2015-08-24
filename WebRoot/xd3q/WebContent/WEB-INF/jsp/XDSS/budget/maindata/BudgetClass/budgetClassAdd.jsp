<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月15日 08点54分58秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象预算分类(BudgetClass)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetClass/budgetClassAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetClass/budgetClassAddGen.js"></script>
</head>
<body>
<fisc:grid divId="div_budgetTemplate" boName="BudgetTemplate" needCheckBox="true" editable="true" defaultCondition=" YBUDTEM.BUDCLASSID='${budgetClass.budclassid}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:4;formRowNo:1 ;rowNo: 01;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.budclassname}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="BudgetClass.budclassname" name="BudgetClass.budclassname" value="${budgetClass.budclassname}" <fisc:authentication sourceName="BudgetClass.budclassname" taskId=""/> >
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:5;formRowNo:1 ;rowNo: 01;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.budclassdesc}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="BudgetClass.budclassdesc" name="BudgetClass.budclassdesc" value="${budgetClass.budclassdesc}" <fisc:authentication sourceName="BudgetClass.budclassdesc" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:6;formRowNo:2 ;rowNo: 02;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.boid}：</td>
		<td  width="30%" >
			<div id="div_boid_sh"></div>
			<fisc:searchHelp divId="div_boid_sh" boName="BudgetClass" boProperty="boid" value="${budgetClass.boid}"></fisc:searchHelp>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:7;formRowNo:2 ;rowNo: 02;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.version}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="BudgetClass.version" name="BudgetClass.version" value="${budgetClass.version}" <fisc:authentication sourceName="BudgetClass.version" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:8;formRowNo:3 ;rowNo: 03;columnNo: 1;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.sourcetype}：</td>
		<td  width="30%" >
			<div id="div_sourcetype_dict"></div>
			<fisc:dictionary boName="BudgetClass" boProperty="sourcetype" dictionaryName="YDBUDGETSOURCE" divId="div_sourcetype_dict" isNeedAuth="false"  value="${budgetClass.sourcetype}"></fisc:dictionary>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:10;formRowNo:4 ;rowNo: 04;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.creator}：</td>
		<td  width="30%" >
			<fisc:user boProperty="creator" boName="BudgetClass" userId="${budgetClass.creator}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:11;formRowNo:4 ;rowNo: 04;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.createtime}：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="BudgetClass.createtime" name="BudgetClass.createtime" value="${budgetClass.createtime}"  readonly="readonly" <fisc:authentication sourceName="BudgetClass.createtime" taskId=""/>>
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:12;formRowNo:5 ;rowNo: 05;columnNo: 1;1X -->
		<td align="right"  width="15%" >${vt.property.lastmodifyer}：</td>
		<td  width="30%" >
			<fisc:user boProperty="lastmodifyer" boName="BudgetClass" userId="${budgetClass.lastmodifyer}"></fisc:user>
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 14(调试用)序号:13;formRowNo:5 ;rowNo: 05;columnNo: 2;1X -->
		<td align="right"  width="15%" >${vt.property.lastmodifytime}：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="BudgetClass.lastmodifytime" name="BudgetClass.lastmodifytime" value="${budgetClass.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="BudgetClass.lastmodifytime" taskId=""/>>
		</td>
	</tr>

	<input type="hidden" id="BudgetClass.client" name="BudgetClass.client" value="${budgetClass.client}">
	<input type="hidden" id="BudgetClass.budclassid" name="BudgetClass.budclassid" value="${budgetClass.budclassid}">
	<input type="hidden" id="BudgetClass.budupclassid" name="BudgetClass.budupclassid" value="${budgetClass.budupclassid}">
	<input type="hidden" id="BudgetClass.active" name="BudgetClass.active" value="${budgetClass.active}">
</table>
</form>
</div>

	          
		        	         
						<div id="div_budgetTemplate" ></div>
                     
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

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
						            		title: '${budgetTemplateVt.boText}',
						            		layout:'fit',
						            		id:'budgetTemplateTab',
						            		contentEl:'div_budgetTemplate'
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
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},'-',
          
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
