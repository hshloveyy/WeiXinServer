<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年07月16日 12点02分38秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象重分配(Reassign)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.viewPage}</title>
</head>
<body>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${reassign.reassignid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="Reassign.reassigntype" nodeId="${workflowNodeDefId}"/> >${vt.property.reassigntype}：</td>
		<td  width="40%">
			<div id="div_reassigntype_dict"></div>
			<fisc:dictionary boName="Reassign" boProperty="reassigntype" dictionaryName="YDREASSIGNTYPE" divId="div_reassigntype_dict" isNeedAuth="false"  value="${reassign.reassigntype}"  ></fisc:dictionary>
		</td>
		<td width="15%" align="right"  <fisc:authentication sourceName="Reassign.reassignboid" nodeId="${workflowNodeDefId}"/> >${vt.property.reassignboid}：</td>
		<td width="30%">
			<div id="div_reassignboid_sh"></div>
			<fisc:searchHelp divId="div_reassignboid_sh" boName="" boProperty="" searchHelpName="YHREASSIGNNO" searchType="field" valueField = "REASSIGNBOID"  displayField = "REASSIGNBONO" hiddenName="Reassign.reassignboid"  value="${reassign.reassignboid}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right"  <fisc:authentication sourceName="Reassign.reassigntmethod" nodeId="${workflowNodeDefId}"/> >${vt.property.reassigntmethod}：</td>
		<td width="30%">
			<div id="div_reassigntmethod_dict"></div>
			<fisc:dictionary  width ="300" boName="Reassign" boProperty="reassigntmethod" dictionaryName="YDREASSIGNMETHOD" divId="div_reassigntmethod_dict" isNeedAuth="false"  value="${reassign.reassigntmethod}"  ></fisc:dictionary>
		</td>
        <td></td>
        <td></td>
	</tr>
	<tr>
		<td align="right"  width="15%" <fisc:authentication sourceName="Reassign.text" nodeId="${workflowNodeDefId}"/> valign="top"><font color="red">★</font>${vt.property.text}：</td>
		<td  width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="Reassign.text" name="Reassign.text" <fisc:authentication sourceName="Reassign.text" nodeId="${workflowNodeDefId}"/>>${reassign.text}</textarea>
		</td>
	</tr>
	<input type="hidden" id="Reassign.client" name="Reassign.client" value="${reassign.client}">
	<input type="hidden" id="Reassign.reassignid" name="Reassign.reassignid" value="${reassign.reassignid}">
	<input type="hidden" id="Reassign.reassignbono" name="Reassign.reassignbono" value="${reassign.reassignbono}">
	<input type="hidden" id="Reassign.processstate" name="Reassign.processstate" value="${reassign.processstate}">
	<input type="hidden" id="Reassign.bussinessstate" name="Reassign.bussinessstate" value="${reassign.bussinessstate}">
	<input type="hidden" id="Reassign.createtime" name="Reassign.createtime" value="${reassign.createtime}">
	<input type="hidden" id="Reassign.creator" name="Reassign.creator" value="${reassign.creator}">	
	<input type="hidden" id="Reassign.lastmodifyer" name="Reassign.lastmodifyer" value="${reassign.lastmodifyer}">
	<input type="hidden" id="Reassign.lastmodifytime" name="Reassign.lastmodifytime" value="${reassign.lastmodifytime}">
	<input type="hidden" id="Reassign.ocreator" name="Reassign.ocreator" value="${reassign.ocreator}">
	<input type="hidden" id="Reassign.dept_id" name="Reassign.dept_id" value="${reassign.dept_id}">	
</table>
</form>
<!-- 生成子对象分组布局-->
</div>

<div id="div_south" class="x-hide-display"></div>
<div id="div_top_south" class="x-hide-display"></div>
<div id="div_typeDescription">
	<table style="margin: 20px; padding: 30px;">
		<tr>
			<td>重置（到业务部门重新分配）</td>
			<td>&nbsp;：&nbsp;</td>
			<td>财务人员手工重置清帐凭证（原凭证完全清帐的情况），并提交至业务人员重新清账，不重新生成凭证。</td>
		</tr>
		<tr>
			<td>重置并冲销（到业务部门重新分配）</td>
			<td>&nbsp;：&nbsp;</td>
			<td>财务人员手工重置清帐凭证（原凭证完全清帐的情况），对原凭证冲销，并提交至业务人员重新分配。重新生成凭证。</td>
		</tr>
		<tr>
			<td>重置（财务部直接解除分配关系）</td>
			<td>&nbsp;：&nbsp;</td>
			<td>财务人员手工重置清帐凭证（原凭证完全清帐的情况）。</td>
		</tr>
		<tr>
			<td>冲销（财务部冲销并作废）</td>
			<td>&nbsp;：&nbsp;</td>
			<td>财务人员手工重置清帐凭证（原凭证完全清帐的情况），对原凭证冲销。</td>
		</tr>
		<tr>
			<td>重置并冲销（到业务部门重新分配，过资金部）</td>
			<td>&nbsp;：&nbsp;</td>
			<td>收付款单和退款单可以选择这个方式。财务人员手工重置清帐凭证（原凭证完全清帐的情况），对原凭证冲销，并提交至业务人员重新分配。重新生成凭证。</td>
		</tr>
	</table>
</div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/reassign/reassignView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/reassign/reassignViewGen.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '${reassign.processstate}';
//当前对象主键ID
var reassignid = '${reassign.reassignid}';	

//页面文本
var Txt={
	//重分配
	reassign:'${vt.boText}',
	//boText复制创建
	reassign_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	reassign_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	reassign_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:0,
				            		contentEl:'div_center'
							   },{
									xtype:'tabpanel',
					            	height:310,
					            	id:'tabs',
					            	name:'tabs',
					            	defaults:{bodyStyle:"background-color:#DFE8F6"},
					            	autoScroll: true,
									activeTab:0,
						            items:[{
						            		title:'重分配方式描述',
						            		layout:'fit',
						            		autoWidth:true,
						            		id:'typeDescription',
						            		contentEl:'div_typeDescription'
						            	}
						   			 ]}
								,{
								id:'historyWorkflowTask',
								region:'south',
								border:false,
								title:'${vt.processTrackInfo}',
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
'->','-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelReassign,iconCls:'icon-undo'},'-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessReassign,iconCls:'task'},'-',
<%}%>
' '
],
			renderTo:'div_toolbar'
	});

	var isTask = '${workflowTaskId}';
	if(!isTask||isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited&&isSubmited!=''&&isSubmited!=' '){
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

