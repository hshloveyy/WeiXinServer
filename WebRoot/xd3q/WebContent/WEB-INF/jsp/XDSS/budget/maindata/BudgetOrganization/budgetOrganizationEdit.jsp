<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月05日 13点59分05秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>预算组织(BudgetOrganization)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetOrganization/budgetOrganizationEditGen.js"></script>
</head>
<body>
<fisc:grid divId="div_budgetOrgTemp" boName="BudgetOrgTemp" needCheckBox="true" editable="true" defaultCondition=" YBUDORGTEM.BUDORGID='${budgetOrganization.budorgid}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${budgetOrganization.budorgid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.budorgname}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="BudgetOrganization.budorgname" name="BudgetOrganization.budorgname" value="${budgetOrganization.budorgname}"   <fisc:authentication sourceName="BudgetOrganization.budorgname" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.budorgtype}：</td>
		<td  width="40%">
			<div id="div_budorgtype_dict"></div>
			<fisc:dictionary boName="BudgetOrganization" boProperty="budorgtype" dictionaryName="YDBUDORGTYPE" divId="div_budorgtype_dict" isNeedAuth="false"  value="${budgetOrganization.budorgtype}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.budcontype}：</td>
		<td width="30%">
			<div id="div_budcontype_dict"></div>
			<fisc:dictionary boName="BudgetOrganization" boProperty="budcontype" dictionaryName="YDBUDCONTYPE" divId="div_budcontype_dict" isNeedAuth="false"  value="${budgetOrganization.budcontype}"></fisc:dictionary>
		</td>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.deptid}：</td>
		<td  width="40%">
			<div id="div_deptid_sh"></div>
			<fisc:searchHelp divId="div_deptid_sh" boName="BudgetOrganization" boProperty="deptid"  value="${budgetOrganization.deptid}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.companycode}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="BudgetOrganization.companycode" name="BudgetOrganization.companycode" value="${budgetOrganization.companycode}"   <fisc:authentication sourceName="BudgetOrganization.companycode" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.costcenter}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="BudgetOrganization.costcenter" name="BudgetOrganization.costcenter" value="${budgetOrganization.costcenter}"   <fisc:authentication sourceName="BudgetOrganization.costcenter" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.status}：</td>
		<td width="30%">
			<div id="div_status_dict"></div>
			<fisc:dictionary boName="BudgetOrganization" boProperty="status" dictionaryName="YDBUDSTATUS" divId="div_status_dict" isNeedAuth="false"  value="${budgetOrganization.status}"></fisc:dictionary>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="BudgetOrganization" userId="${budgetOrganization.creator}"></fisc:user>
		</td>
		<td align="right" width="15%" >${vt.property.createtime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="BudgetOrganization.createtime" name="BudgetOrganization.createtime" value="${budgetOrganization.createtime}"  readonly="readonly" <fisc:authentication sourceName="BudgetOrganization.createtime" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.lastmodifyer}：</td>
		<td width="30%">
			<fisc:user boProperty="lastmodifyer" boName="BudgetOrganization" userId="${budgetOrganization.lastmodifyer}"></fisc:user>
		</td>
		<td align="right" width="15%" >${vt.property.lastmodifytime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="BudgetOrganization.lastmodifytime" name="BudgetOrganization.lastmodifytime" value="${budgetOrganization.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="BudgetOrganization.lastmodifytime" taskId="${workflowTaskId}"/>>
		</td>
	</tr>

	<input type="hidden" id="BudgetOrganization.client" name="BudgetOrganization.client" value="${budgetOrganization.client}">
	<input type="hidden" id="BudgetOrganization.budorgid" name="BudgetOrganization.budorgid" value="${budgetOrganization.budorgid}">
	<input type="hidden" id="BudgetOrganization.buduporgid" name="BudgetOrganization.buduporgid" value="${budgetOrganization.buduporgid}">
</table>
</form>
</div>

	          
		        	         
							<div id="div_budgetOrgTemp"></div>
                     
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var budorgid = '${budgetOrganization.budorgid}';	

//页面文本
var Txt={
	//预算组织
	budgetOrganization:'${vt.boText}',
	          
	//预算组织模版
	budgetOrgTemp:'${budgetOrgTempVt.boText}',
	//boText创建
	budgetOrgTemp_Create:'${budgetOrgTempVt.boTextCreate}',
	//boText复制创建
	budgetOrgTemp_CopyCreate:'${budgetOrgTempVt.boTextCopyCreate}',
	// 进行【预算组织模版复制创建】操作时，只允许选择一条记录！
	budgetOrgTemp_CopyCreate_AllowOnlyOneItemForOperation:'${budgetOrgTempVt.copyCreate_AllowOnlyOneItemForOperation}',
	// 请选择需要进行【预算组织模版复制创建】操作的记录！
	budgetOrgTemp_CopyCreate_SelectToOperation:'${budgetOrgTempVt.copyCreate_SelectToOperate}',
	//boText复制创建
	budgetOrganization_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	budgetOrganization_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	budgetOrganization_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:235,
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
						            		title:'${budgetOrgTempVt.boText}',
						            		layout:'fit',
						            		contentEl:'div_budgetOrgTemp'
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
{id:'_copyCreate',text:'${vt.mCopyCreate}',handler:_copyCreateBudgetOrganization,iconCls:'icon-copyCreate'},'-',
{id:'_delete',text:'${vt.mDelete}',handler:_deleteBudgetOrganization,iconCls:'icon-delete'},'-',
{id:'_create',text:'${vt.mCreate}',handler:_createBudgetOrganization,iconCls:'icon-add'},'-',
'->','-',
{id:'_saveOrUpdate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateBudgetOrganization,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelBudgetOrganization,iconCls:'icon-undo'},'-',
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