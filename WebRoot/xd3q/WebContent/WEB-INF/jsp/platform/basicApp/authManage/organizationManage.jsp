<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年04月16日 09点50分38秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象组织(Organization)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/organizationManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/organizationManageGen.js"></script>
</head>
<body>
<fisc:tree tableName="YORGANIZATION" 
	entityName="com.infolion.platform.basicApp.authManage.domain.Organization" height="500"
	rootValue="-1" idColumnName="ORGANIZATIONID"
	parentColumnName="PARENTORGID" titleColumnName="ORGANIZATIONNAME"
	treeTitle="${vt.boText}" divId="div_center_weast" needCheckChilden="true"
	needAutoLoad="true" rootVisible="true"  onclick="organizationTreeClick"></fisc:tree>
	          
		    <fisc:grid divId="div_orgPersonnel" boName="OrgPersonnel" needCheckBox="true" editable="false" defaultCondition=" YORGPERSONNEL.ORGANIZATIONID='${organization.organizationId}'" needAutoLoad="true" height="285" nameSapceSupport="true" ></fisc:grid>        
<!-- dpPageFtlLib.ftl -->
<div id="tree-div">
<div id="div_center_weast"></div>
</div>
<div id="div_center_north">
<div id="div_centerToolbar"></div>
	<div id="div_centerForm" class="search">
		<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.organizationName}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Organization.organizationName" name="Organization.organizationName" value="${organization.organizationName}" <fisc:authentication sourceName="Organization.organizationName" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.organizationDesc}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Organization.organizationDesc" name="Organization.organizationDesc" value="${organization.organizationDesc}" <fisc:authentication sourceName="Organization.organizationDesc" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.organizationCode}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Organization.organizationCode" name="Organization.organizationCode" value="${organization.organizationCode}" <fisc:authentication sourceName="Organization.organizationCode" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.orderNo}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Organization.orderNo" name="Organization.orderNo" value="${organization.orderNo}" <fisc:authentication sourceName="Organization.orderNo" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.busineseArea}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Organization.busineseArea" name="Organization.busineseArea" value="${organization.busineseArea}" <fisc:authentication sourceName="Organization.busineseArea" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.costCenter}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Organization.costCenter" name="Organization.costCenter" value="${organization.costCenter}" <fisc:authentication sourceName="Organization.costCenter" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" ><font color="red">★</font>${vt.property.companyCode}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Organization.companyCode" name="Organization.companyCode" value="${organization.companyCode}" <fisc:authentication sourceName="Organization.companyCode" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.controllingArea}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Organization.controllingArea" name="Organization.controllingArea" value="${organization.controllingArea}" <fisc:authentication sourceName="Organization.controllingArea" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.profitCenter}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Organization.profitCenter" name="Organization.profitCenter" value="${organization.profitCenter}" <fisc:authentication sourceName="Organization.profitCenter" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.purchasingOrganization}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Organization.purchasingOrganization" name="Organization.purchasingOrganization" value="${organization.purchasingOrganization}" <fisc:authentication sourceName="Organization.purchasingOrganization" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.saleOrganization}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Organization.saleOrganization" name="Organization.saleOrganization" value="${organization.saleOrganization}" <fisc:authentication sourceName="Organization.saleOrganization" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.budgetOrganization}：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Organization.budgetOrganization" name="Organization.budgetOrganization" value="${organization.budgetOrganization}" <fisc:authentication sourceName="Organization.budgetOrganization" taskId=""/> >
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.creator}：</td>
		<td  width="30%" >
			<fisc:user boProperty="creator" boName="Organization" userId="${organization.creator}"></fisc:user>
		</td>
		<td align="right"  width="15%" >${vt.property.createTime}：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="Organization.createTime" name="Organization.createTime" value="${organization.createTime}"  readonly="readonly" <fisc:authentication sourceName="Organization.createTime" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.lastModifyor}：</td>
		<td  width="30%" >
			<fisc:user boProperty="lastModifyor" boName="Organization" userId="${organization.lastModifyor}"></fisc:user>
		</td>
		<td align="right"  width="15%" >${vt.property.lastmodifytime}：</td>
		<td   width="40%" >
		    <input type="text" class="inputText" id="Organization.lastmodifytime" name="Organization.lastmodifytime" value="${organization.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="Organization.lastmodifytime" taskId=""/>>
		</td>
	</tr>

	<input type="hidden" id="Organization.organizationId" name="Organization.organizationId" value="${organization.organizationId}">
	<input type="hidden" id="Organization.client" name="Organization.client" value="${organization.client}">
	<input type="hidden" id="Organization.parentOrganizationId" name="Organization.parentOrganizationId" value="${organization.parentOrganizationId}">
</table>
		</form>
	</div>
</div>
<div id="div_orgPersonnel" ></div><div id="div_center_south"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//存放当前选 中树的节点ID
var treeNodeId = '';
//全局路径
var context = '<%= request.getContextPath() %>';
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
	//请选择节点后再进行此功能的操作！
	organization_PlaseSelectTreeNode:'${vt.plaseSelectTreeNode}',
	//非叶结点，不能删除！
	organization_NotDeleteTreeNode:'${vt.notDeleteTreeNode}',
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
		var centerToolbars = new Ext.Toolbar({
		items:[
{id:'_addNodeOrganization',text:'增加同级节点',disabled:true,handler:_addNodeOrganization,iconCls:' '},'-',
{id:'_addSubNodeOrganization',text:'增加下级节点',disabled:true,handler:_addSubNodeOrganization,iconCls:' '},'-',
{id:'_deleteNodeOrganization',text:'删除节点',disabled:true,handler:_deleteNodeOrganization,iconCls:' '},'-',
'->','-',
{id:'_saveOrUpdateOrganization',text:'${vt.mSaveOrUpdate}',disabled:true,handler:_saveOrUpdateOrganization,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'${vt.mCancel}',disabled:true,handler:_cancel,iconCls:'icon-undo'},				' '],  
		       renderTo:"div_centerToolbar"
		});

	var viewport = new Ext.Viewport({
	   		layout:"border",
			items:[{
				region:"west",
	            split:true,
	            collapsible: true,
	            width: 200,
	            minSize: 175,
	            maxSize: 400,
	            margins:'0 0 0 5',
	            layout:'fit',
	            layoutConfig:{
	               animate:true
	            },
	            items:[{
	                title:'${vt.boTextDetail}',
	                border:false,
	                layout:'fit',
	                contentEl: 'tree-div',
	                tools:[{
	                    id:'refresh',   
	                    qtip: '刷新${vt.boTextDetail}',
	                     handler: function(event, toolEl, panel) {
		                	div_center_weast_tree.body.mask('刷新中...');
		                    reload_div_center_weast_tree();
		                    div_center_weast_tree.root.collapse(true, false);
		                    setTimeout(function(){
		                        div_center_weast_tree.body.unmask();
		                    }, 1000);
	                     }
	                },{
	                	id:'maximize',
	                	qtip:'展开树',
	                	handler:function(event, toolEl, panel) {
	                		div_center_weast_tree.expandAll();
	                	}
	                },{
	                	id:'minimize',
	                	qtip:'收起树',
	                	handler:function(event, toolEl, panel) {
	                		div_center_weast_tree.collapseAll();
	                	}
	                }],
	                items:[{contentEl:'div_center_weast'}]
	            }]
			},{
				region:'center',
				border:false,
	            items:[{
	            	region:'north',
		            layout:'fit',
		            contentEl:'div_centerToolbar'
	            }
 				,{
						region:'center',
						layout: 'anchor',
			            height:600,
			            border:false,
			            autoScroll: true,
			            items:[{
			            		title: '${vt.boTextInfo}',
			            		layout:'fit',
			            		autoScroll: true,
			            		contentEl:'div_centerForm'
							 }
							 ,{
								xtype:'tabpanel',
				            	height:460,
				            	id:'tabs',
				            	name:'tabs',
				            	defaults:{bodyStyle:"background-color:#DFE8F6"},
				            	autoScroll: true,
								activeTab:0,
					            items:[
				          
						             {
						            		title: '${orgPersonnelVt.boText}',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'orgPersonnelTab',
						            		contentEl:'div_orgPersonnel'
						             }
						            ]
	           		     }]
			}]
		}]
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
</script>
