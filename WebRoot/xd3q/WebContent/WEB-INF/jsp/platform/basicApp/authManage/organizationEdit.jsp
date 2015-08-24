<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年04月16日 09点50分38秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象组织(Organization)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/organizationEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/organizationEditGen.js"></script>
</head>
<body>
<fisc:grid divId="div_orgPersonnel" boName="OrgPersonnel" needCheckBox="true" editable="false" defaultCondition=" YORGPERSONNEL.ORGANIZATIONID='${organization.organizationId}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${organization.organizationId}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.organizationName}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Organization.organizationName" name="Organization.organizationName" value="${organization.organizationName}"   <fisc:authentication sourceName="Organization.organizationName" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >${vt.property.organizationDesc}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Organization.organizationDesc" name="Organization.organizationDesc" value="${organization.organizationDesc}"   <fisc:authentication sourceName="Organization.organizationDesc" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.organizationCode}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Organization.organizationCode" name="Organization.organizationCode" value="${organization.organizationCode}"   <fisc:authentication sourceName="Organization.organizationCode" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >${vt.property.orderNo}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Organization.orderNo" name="Organization.orderNo" value="${organization.orderNo}"   <fisc:authentication sourceName="Organization.orderNo" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.busineseArea}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Organization.busineseArea" name="Organization.busineseArea" value="${organization.busineseArea}"   <fisc:authentication sourceName="Organization.busineseArea" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >${vt.property.costCenter}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Organization.costCenter" name="Organization.costCenter" value="${organization.costCenter}"   <fisc:authentication sourceName="Organization.costCenter" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.companyCode}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Organization.companyCode" name="Organization.companyCode" value="${organization.companyCode}"   <fisc:authentication sourceName="Organization.companyCode" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >${vt.property.controllingArea}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Organization.controllingArea" name="Organization.controllingArea" value="${organization.controllingArea}"   <fisc:authentication sourceName="Organization.controllingArea" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.profitCenter}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Organization.profitCenter" name="Organization.profitCenter" value="${organization.profitCenter}"   <fisc:authentication sourceName="Organization.profitCenter" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >${vt.property.purchasingOrganization}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Organization.purchasingOrganization" name="Organization.purchasingOrganization" value="${organization.purchasingOrganization}"   <fisc:authentication sourceName="Organization.purchasingOrganization" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.saleOrganization}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Organization.saleOrganization" name="Organization.saleOrganization" value="${organization.saleOrganization}"   <fisc:authentication sourceName="Organization.saleOrganization" taskId="${workflowTaskId}"/>>
		</td>
		<td align="right" width="15%" >${vt.property.budgetOrganization}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Organization.budgetOrganization" name="Organization.budgetOrganization" value="${organization.budgetOrganization}"   <fisc:authentication sourceName="Organization.budgetOrganization" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="Organization" userId="${organization.creator}"></fisc:user>
		</td>
		<td align="right" width="15%" >${vt.property.createTime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="Organization.createTime" name="Organization.createTime" value="${organization.createTime}"  readonly="readonly" <fisc:authentication sourceName="Organization.createTime" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.lastModifyor}：</td>
		<td width="30%">
			<fisc:user boProperty="lastModifyor" boName="Organization" userId="${organization.lastModifyor}"></fisc:user>
		</td>
		<td align="right" width="15%" >${vt.property.lastmodifytime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="Organization.lastmodifytime" name="Organization.lastmodifytime" value="${organization.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="Organization.lastmodifytime" taskId="${workflowTaskId}"/>>
		</td>
	</tr>

	<input type="hidden" id="Organization.organizationId" name="Organization.organizationId" value="${organization.organizationId}">
	<input type="hidden" id="Organization.client" name="Organization.client" value="${organization.client}">
	<input type="hidden" id="Organization.parentOrganizationId" name="Organization.parentOrganizationId" value="${organization.parentOrganizationId}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_orgPersonnel"></div>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var organizationId = '${organization.organizationId}';	

//页面文本
var Txt={
	//组织
	organization:'${vt.boText}',
	          
	//组织员工
	orgPersonnel:'${orgPersonnelVt.boText}',
	// 请选择需要进行【组织员工批量删除】操作的记录！
	orgPersonnel_Deletes_SelectToOperation:'${orgPersonnelVt.deletes_SelectToOperate}',
	// 您选择了【组织员工批量删除】操作，是否确定继续该操作？
	orgPersonnel_Deletes_ConfirmOperation:'${orgPersonnelVt.deletes_ConfirmOperation}',
	//boText复制创建
	organization_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	organization_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	organization_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:310,
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
						            		title:'${orgPersonnelVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_orgPersonnel'
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
{id:'_saveOrUpdate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateOrganization,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelOrganization,iconCls:'icon-undo'},'-',
{id:'_addNode',text:'增加同级节点',handler:_addNodeOrganization,iconCls:' '},'-',
{id:'_addSubNode',text:'增加下级节点',handler:_addSubNodeOrganization,iconCls:' '},'-',
{id:'_deleteNode',text:'删除节点',handler:_deleteNodeOrganization,iconCls:' '},'-',
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