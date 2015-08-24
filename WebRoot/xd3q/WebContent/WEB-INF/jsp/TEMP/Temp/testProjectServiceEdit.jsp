<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年04月21日 15点06分43秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象项目服务（测试）(TestProjectService)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/TEMP/Temp/testProjectServiceEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/TEMP/Temp/testProjectServiceEditGen.js"></script>
</head>
<body>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${testProjectService.tsid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right" width="15%" ><font color="red">★</font>${vt.property.projectid}：</td>
		<td width="30%">
			<div id="div_projectid_sh"></div>
			<fisc:searchHelp divId="div_projectid_sh" boName="TestProjectService" boProperty="projectid"  value="${testProjectService.projectid}"></fisc:searchHelp>
		</td>
		<td align="right" width="15%" >${vt.property.tsname}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="TestProjectService.tsname" name="TestProjectService.tsname" value="${testProjectService.tsname}"   <fisc:authentication sourceName="TestProjectService.tsname" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.creator}：</td>
		<td width="30%">
			<fisc:user boProperty="creator" boName="TestProjectService" userId="${testProjectService.creator}"></fisc:user>
		</td>
		<td align="right" width="15%" >${vt.property.createtime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="TestProjectService.createtime" name="TestProjectService.createtime" value="${testProjectService.createtime}"  readonly="readonly" <fisc:authentication sourceName="TestProjectService.createtime" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.lastmodifyer}：</td>
		<td width="30%">
			<fisc:user boProperty="lastmodifyer" boName="TestProjectService" userId="${testProjectService.lastmodifyer}"></fisc:user>
		</td>
		<td align="right" width="15%" >${vt.property.lastmodifytime}：</td>
		<td  width="40%">
		    <input type="text" class="inputText" id="TestProjectService.lastmodifytime" name="TestProjectService.lastmodifytime" value="${testProjectService.lastmodifytime}"  readonly="readonly" <fisc:authentication sourceName="TestProjectService.lastmodifytime" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td align="right" width="15%" >${vt.property.processstate}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="TestProjectService.processstate" name="TestProjectService.processstate" value="${testProjectService.processstate}"   <fisc:authentication sourceName="TestProjectService.processstate" taskId="${workflowTaskId}"/>>
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="TestProjectService.tsid" name="TestProjectService.tsid" value="${testProjectService.tsid}">
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
var isSubmited = '${testProjectService.processstate}';
//当前对象主键ID
var tsid = '${testProjectService.tsid}';	

//页面文本
var Txt={
	//项目服务（测试）
	testProjectService:'${vt.boText}',
	//boText复制创建
	testProjectService_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	testProjectService_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	testProjectService_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
					            contentEl:'div_top_north'
					          },
				              {
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:160,
				            		contentEl:'div_center'
						}
								,{
								id:'historyWorkflowTask',
								region:'south',
								title:'${vt.processTrackInfo}',
								border:false,
			            		layout:'anchor',
				            	collapsible: true,
				            	collapsed:true,
				            	autoScroll: true,
								height:200,
								contentEl:'div_top_south'
							   }
							   ]
				   }]
				}
                 ]
	});

	var toolbars = new Ext.Toolbar({
			items:[
					'-',
{id:'_create',text:'${vt.mCreate}',handler:_createTestProjectService,iconCls:'icon-add'},'-',
{id:'_copyCreate',text:'${vt.mCopyCreate}',handler:_copyCreateTestProjectService,iconCls:'icon-copyCreate'},'-',
{id:'_delete',text:'${vt.mDelete}',handler:_deleteTestProjectService,iconCls:'icon-delete'},'-',
'->','-',
{id:'_saveOrUpdate',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateTestProjectService,iconCls:'icon-table-save'},'-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelTestProjectService,iconCls:'icon-undo'},'-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessTestProjectService,iconCls:'task'},'-',
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
<%
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
Ext.getCmp('_submitProcess').disable();
<%}%>
Ext.getCmp('_create').disable();
Ext.getCmp('_copyCreate').disable();
Ext.getCmp('_delete').disable();
Ext.getCmp('_saveOrUpdate').disable();		}
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