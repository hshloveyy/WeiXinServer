<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年04月22日 17点19分06秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象项目管理（测试）(TestProjectManage)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/TEMP/Temp/testProjectManageEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/TEMP/Temp/testProjectManageEditGen.js"></script>
</head>
<body>
<div id="div_east" class="x-hide-display"></div>
<fisc:relationship divId="div_east" boName="TestProjectManage"></fisc:relationship>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${testProjectManage.projectid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="TestProjectManage.projectname" nodeId="${workflowNodeDefId}"/> >${vt.property.projectname}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="TestProjectManage.projectname" name="TestProjectManage.projectname" value="${testProjectManage.projectname}"   <fisc:authentication sourceName="TestProjectManage.projectname" nodeId="${workflowNodeDefId}"/>>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="TestProjectManage.processstate" nodeId="${workflowNodeDefId}"/> >${vt.property.processstate}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="TestProjectManage.processstate" name="TestProjectManage.processstate" value="${testProjectManage.processstate}"   <fisc:authentication sourceName="TestProjectManage.processstate" nodeId="${workflowNodeDefId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="TestProjectManage.creator" nodeId="${workflowNodeDefId}"/> >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="TestProjectManage" userId="${testProjectManage.creator}"></fisc:user>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="TestProjectManage.createtime" nodeId="${workflowNodeDefId}"/> >${vt.property.createtime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="TestProjectManage.createtime" name="TestProjectManage.createtime" value="${testProjectManage.createtime}"  readonly="readonly">
		</td>
	</tr>
	<tr>
		<td align="right" width="15%"  <fisc:authentication sourceName="TestProjectManage.lastmodifyer" nodeId="${workflowNodeDefId}"/> >${vt.property.lastmodifyer}：</td>
		<td width="30%">
			<fisc:user boProperty="lastmodifyer" boName="TestProjectManage" userId="${testProjectManage.lastmodifyer}"></fisc:user>
		</td>
		<td align="right" width="15%"  <fisc:authentication sourceName="TestProjectManage.lastmodifytime" nodeId="${workflowNodeDefId}"/> >${vt.property.lastmodifytime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="TestProjectManage.lastmodifytime" name="TestProjectManage.lastmodifytime" value="${testProjectManage.lastmodifytime}"  readonly="readonly">
		</td>
	</tr>

	<input type="hidden" id="TestProjectManage.projectid" name="TestProjectManage.projectid" value="${testProjectManage.projectid}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_south" class="x-hide-display"></div>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${testProjectManage.processstate}';
//当前对象主键ID
var projectid = '${testProjectManage.projectid}';	

//页面文本
var Txt={
	//项目管理（测试）
	testProjectManage:'${vt.boText}',
	//boText复制创建
	testProjectManage_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	testProjectManage_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	testProjectManage_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            	id:'currentWorkflowTask',
					            title: '${vt.processApproveInfo}',
					            border:false,
					            layout:'fit',
			            		anchor: '-20',
					            contentEl:'div_top_north'
					          },
				              {
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:122.5,
			            			anchor: '-20',
				            		contentEl:'div_center'
						}
								,{
								id:'historyWorkflowTask',
								region:'south',
								title:'${vt.processTrackInfo}',
								border:false,
			            		layout:'anchor',
			            		anchor: '-20',
				            	collapsible: true,
				            	collapsed:true,
				            	autoScroll: true,
								height:200,
								contentEl:'div_top_south'
							   }
							   ]
				   }]
				}
				,{
					region:'east',
					width:200,
					contentEl:'div_east',
					collapsible: true,
					collapsed:true,
					autoScroll: true,
					title:'${vt.sRelOperation}'
				}]
	});

	var toolbars = new Ext.Toolbar({
			items:[
					'-',
{id:'_create',text:'${vt.mCreate}',handler:_createTestProjectManage,iconCls:'icon-add'},'-',
{id:'_copyCreate',text:'${vt.mCopyCreate}',handler:_copyCreateTestProjectManage,iconCls:'icon-copyCreate'},'-',
{id:'_delete',text:'${vt.mDelete}',handler:_deleteTestProjectManage,iconCls:'icon-delete'},'-',
'->','-',
{id:'_saveOrUpdate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateTestProjectManage,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelTestProjectManage,iconCls:'icon-undo'},'-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessTestProjectManage,iconCls:'task'},'-',
<%}%>
' '
],
			renderTo:'div_toolbar'
	});

	var isTask = '${workflowTaskId}';
	if(!isTask || isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited && isSubmited!='' && isSubmited!=' '){
Ext.getCmp('_create').disable();
Ext.getCmp('_copyCreate').disable();
Ext.getCmp('_delete').disable();
Ext.getCmp('_saveOrUpdate').disable();
<%
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
Ext.getCmp('_submitProcess').disable();
<%}%>		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
Ext.getCmp('_create').disable();
Ext.getCmp('_copyCreate').disable();
Ext.getCmp('_delete').disable();
Ext.getCmp('_saveOrUpdate').disable();	}
//});
</script>