<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年04月12日 11点34分35秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象部门预算编制(DeptBudgetting)增加页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/sample/deptBudgetting/deptBudgettingAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/sample/deptBudgetting/deptBudgettingAddGen.js"></script>
</head>
<body>
<div id="div_east" class="x-hide-display"></div>
<fisc:relationship divId="div_east" boName="DeptBudgetting"></fisc:relationship>
<div id="div_top_south" ></div>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${deptBudgetting.deptbudgettingid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div><div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td align="right"  width="15%" >${vt.property.ayear}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="DeptBudgetting.ayear" name="DeptBudgetting.ayear" value="${deptBudgetting.ayear}" <fisc:authentication sourceName="DeptBudgetting.ayear" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.budorgid}：</td>
		<td   width="40%" >
			<div id="div_budorgid_sh"></div>
			<fisc:searchHelp divId="div_budorgid_sh" boName="DeptBudgetting" boProperty="budorgid" value="${deptBudgetting.budorgid}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.processstate}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="DeptBudgetting.processstate" name="DeptBudgetting.processstate" value="${deptBudgetting.processstate}" <fisc:authentication sourceName="DeptBudgetting.processstate" taskId=""/> >
		</td>
		<td align="right"  width="15%" >${vt.property.budclassid}：</td>
		<td   width="40%" >
			<div id="div_budclassid_sh"></div>
			<fisc:searchHelp divId="div_budclassid_sh" boName="DeptBudgetting" boProperty="budclassid" value="${deptBudgetting.budclassid}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right"  width="15%" >${vt.property.version}：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="DeptBudgetting.version" name="DeptBudgetting.version" value="${deptBudgetting.version}" <fisc:authentication sourceName="DeptBudgetting.version" taskId=""/> >
		</td>
        <td></td>
        <td></td>
	</tr>

	<input type="hidden" id="DeptBudgetting.client" name="DeptBudgetting.client" value="${deptBudgetting.client}">
	<input type="hidden" id="DeptBudgetting.deptbudgettingid" name="DeptBudgetting.deptbudgettingid" value="${deptBudgetting.deptbudgettingid}">
	<input type="hidden" id="calActivityId" name="calActivityId" value="${calActivityId}">
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

//是否已经提交流程
var isSubmited = '${deptBudgetting.processstate}';

//页面文本
var Txt={
	//部门预算编制
	deptBudgetting:'${vt.boText}',
	//boText复制创建
	deptBudgetting_CopyCreate:'${vt.boTextCopyCreate}',
	//boText创建
	deptBudgetting_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	deptBudgetting_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:0,
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
					'-','->','-',
					          
          
          
          
					{id:'_saveOrUpdateDeptBudgetting',text:'${vt.mSaveOrUpdate}',handler:_saveOrUpdateDeptBudgetting,iconCls:'icon-table-save'},'-',
          
					{id:'_cancel',text:'${vt.mCancel}',handler:_cancel,iconCls:'icon-undo'},'-',
          
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
					{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessDeptBudgetting,iconCls:'task'},'-',					
<%}%>		
					' '],  
			renderTo:"div_toolbar"
	});
	
	
	var isTask = '${workflowTaskId}';
	if(!isTask||isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited&&isSubmited!=''&&isSubmited!=' '){
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
	}

//});
</script>
