<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年04月21日 15点06分41秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象项目（测试）(TestProject)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.viewPage}</title>
<script language="javascript" type="text/javascript" defer="defer"
	src="<%=request.getContextPath()%>/js/TEMP/Temp/testProjectView.js"></script>
<script language="javascript" type="text/javascript" defer="defer"
	src="<%=request.getContextPath()%>/js/TEMP/Temp/testProjectViewGen.js"></script>
</head>
<body>
<div id="div_east" class="x-hide-display"></div>
<fisc:relationship divId="div_east" boName="TestProject"></fisc:relationship>
<fisc:workflow-taskHistory divId="div_top_south" width=""
	businessRecordId="${testProject.projectid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search"><fisc:workflow-operationBar
	taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar></div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display">
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.projectname}：</td>
		<td width="30%"><input type="text" class="inputText"
			id="TestProject.projectname" name="TestProject.projectname"
			value="${testProject.projectname}"
			<fisc:authentication sourceName="TestProject.projectname" taskId="${workflowTaskId}"/>>
		</td>
		<td width="15%" align="right">${vt.property.processstate}：</td>
		<td width="40%"><input type="text" class="inputText"
			id="TestProject.processstate" name="TestProject.processstate"
			value="${testProject.processstate}"
			<fisc:authentication sourceName="TestProject.processstate" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">${vt.property.creator}：</td>
		<td width="30%"><fisc:user boProperty="creator"
			boName="TestProject" userId="${testProject.creator}"></fisc:user></td>
		<td width="15%" align="right">${vt.property.createtime}：</td>
		<td width="40%"><input type="text" class="inputText"
			id="TestProject.createtime" name="TestProject.createtime"
			value="${testProject.createtime}" readonly="readonly"></td>
	</tr>
	<tr>
		<td width="15%" align="right">${vt.property.lastmodifyer}：</td>
		<td width="30%"><fisc:user boProperty="lastmodifyer"
			boName="TestProject" userId="${testProject.lastmodifyer}"></fisc:user>
		</td>
		<td width="15%" align="right">${vt.property.lastmodifytime}：</td>
		<td width="40%"><input type="text" class="inputText"
			id="TestProject.lastmodifytime" name="TestProject.lastmodifytime"
			value="${testProject.lastmodifytime}" readonly="readonly"></td>
	</tr>

	<input type="hidden" id="TestProject.projectid"
		name="TestProject.projectid" value="${testProject.projectid}">
</table>
</form>
<!-- 生成子对象分组布局--></div>

<div id="div_south" class="x-hide-display"></div>

<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%=request.getContextPath()%>';
//是否已经提交流程
var isSubmited = '${testProject.processstate}';
//当前对象主键ID
var projectid = '${testProject.projectid}';	

//页面文本
var Txt={
	//项目（测试）
	testProject:'${vt.boText}',
	//boText复制创建
	testProject_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	testProject_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	testProject_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
					            layout:'fit',
					            border:false,
			            		anchor: '-20',
					            contentEl:'div_top_north'
					          },
				              {
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		border:false,
				            		//height:122.5,
				            		autoScroll: true,
			            			anchor: '-20',
				            		contentEl:'div_center'
						}
								,{
								id:'historyWorkflowTask',
								region:'south',
								border:false,
								title:'${vt.processTrackInfo}',
			            		layout:'anchor',
				            	anchor: '-20',
				            	collapsible: true,
				            	collapsed:true,
				            	border:false,
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
{id:'_copyCreate',text:'${vt.mCopyCreate}',handler:_copyCreateTestProject,iconCls:'icon-copyCreate'},'-',
{id:'_create',text:'${vt.mCreate}',handler:_createTestProject,iconCls:'icon-add'},'-',
'->','-',
<%Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
			if (Boolean.parseBoolean(haveBindWorkFlow.toString())) {%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessTestProject,iconCls:'task'},'-',
<%}%>
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelTestProject,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	var isTask = '${workflowTaskId}';
	alert(isTask);
	
	if(!isTask||isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited&&isSubmited!=''&&isSubmited!=' '){
<%if (Boolean.parseBoolean(haveBindWorkFlow.toString())) {%>
Ext.getCmp('_submitProcess').disable();
<%}%>
Ext.getCmp('_copyCreate').disable();
Ext.getCmp('_create').disable();		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
Ext.getCmp('_copyCreate').disable();
Ext.getCmp('_create').disable();	}
	
//});
</script>
