<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月16日 11点36分09秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象预算项分组(BudgetItemGroup)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetItemGroup/budgetItemGroupEditGen.js"></script>
</head>
<body>
<fisc:grid divId="div_budgetItem" boName="BudgetItem" needCheckBox="true" editable="true" defaultCondition=" YBUDITEM.BUDGROUPID='${budgetItemGroup.budgroupid}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${budgetItemGroup.budgroupid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.budgroupname}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="BudgetItemGroup.budgroupname" name="BudgetItemGroup.budgroupname" value="${budgetItemGroup.budgroupname}"   <fisc:authentication sourceName="BudgetItemGroup.budgroupname" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >${vt.property.budgroupdesc}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="BudgetItemGroup.budgroupdesc" name="BudgetItemGroup.budgroupdesc" value="${budgetItemGroup.budgroupdesc}"   <fisc:authentication sourceName="BudgetItemGroup.budgroupdesc" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="BudgetItemGroup" userId="${budgetItemGroup.creator}"></fisc:user>
		</td>
		<td align="right" width="15%" >${vt.property.createtime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="BudgetItemGroup.createtime" name="BudgetItemGroup.createtime" value="${budgetItemGroup.createtime}"  readonly="readonly" <fisc:authentication sourceName="BudgetItemGroup.createtime" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.lastmodifyer}：</td>
		<td width="30%">
			<fisc:user boProperty="lastmodifyer" boName="BudgetItemGroup" userId="${budgetItemGroup.lastmodifyer}"></fisc:user>
		</td>
		<td align="right" width="15%" >${vt.property.lastmodifytime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="BudgetItemGroup.lastmodifytime" name="BudgetItemGroup.lastmodifytime" value="${budgetItemGroup.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="BudgetItemGroup.lastmodifytime" taskId="${workflowTaskId}"/>>
		</td>
	</tr>

	<input type="hidden" id="BudgetItemGroup.client" name="BudgetItemGroup.client" value="${budgetItemGroup.client}">
	<input type="hidden" id="BudgetItemGroup.budgroupid" name="BudgetItemGroup.budgroupid" value="${budgetItemGroup.budgroupid}">
	<input type="hidden" id="BudgetItemGroup.budupgroupid" name="BudgetItemGroup.budupgroupid" value="${budgetItemGroup.budupgroupid}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_budgetItem"></div>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var budgroupid = '${budgetItemGroup.budgroupid}';	

//页面文本
var Txt={
	//预算项分组
	budgetItemGroup:'${vt.boText}',
	          
	//预算项
	budgetItem:'${budgetItemVt.boText}',
	//boText创建
	budgetItem_Create:'${budgetItemVt.boTextCreate}',
	//boText复制创建
	budgetItem_CopyCreate:'${budgetItemVt.boTextCopyCreate}',
	// 进行【预算项复制创建】操作时，只允许选择一条记录！
	budgetItem_CopyCreate_AllowOnlyOneItemForOperation:'${budgetItemVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【预算项复制创建】操作的记录！
	budgetItem_CopyCreate_SelectToOperation:'${budgetItemVt.copyCreate_SelectToOperate}',
	// 请选择需要进行【预算项批量删除】操作的记录！
	budgetItem_Deletes_SelectToOperation:'${budgetItemVt.deletes_SelectToOperate}',
	// 您选择了【预算项批量删除】操作，是否确定继续该操作？
	budgetItem_Deletes_ConfirmOperation:'${budgetItemVt.deletes_ConfirmOperation}',
	//boText复制创建
	budgetItemGroup_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	budgetItemGroup_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	budgetItemGroup_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		autoScroll: true,
				            		border:false,
				            		//height:122.5,
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
						            		title:'${budgetItemVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_budgetItem'
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
{id:'_saveOrUpdate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateBudgetItemGroup,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelBudgetItemGroup,iconCls:'icon-undo'},'-',
{id:'_addNode',text:'增加同级节点',handler:_addNodeBudgetItemGroup,iconCls:' '},'-',
{id:'_addSubNode',text:'增加下级节点',handler:_addSubNodeBudgetItemGroup,iconCls:' '},'-',
{id:'_deleteNode',text:'删除节点',handler:_deleteNodeBudgetItemGroup,iconCls:' '},'-',
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