<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年10月01日 04点51分07秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象进口付款查询(ImportPaymentQuery)查看页面
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
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${importPaymentQuery.paymentid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<fisc:workflow-operationBar taskInstanceId="${workflowTaskId}"></fisc:workflow-operationBar>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">

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
var isSubmited = '${importPaymentQuery.processstate}';
//当前对象主键ID
var paymentid = '${importPaymentQuery.paymentid}';	

//页面文本
var Txt={
	//进口付款查询
	importPaymentQuery:'${vt.boText}',
	//boText复制创建
	importPaymentQuery_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	importPaymentQuery_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	importPaymentQuery_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
					            contentEl:'div_top_north'
					          },
				              {
				            		title:'${vt.boTextInfo}',
				            		layout:'fit',
				            		border:false,
				            		//height:10,
				            		autoScroll: true,
				            		contentEl:'div_center'
						}
								,{
								id:'historyWorkflowTask',
								region:'south',
								border:false,
								title:'${vt.processTrackInfo}',
			            		layout:'anchor',
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
				 ]
	});
	
	var toolbars = new Ext.Toolbar({
			items:[
					'-',
{id:'_copyCreate',text:'${vt.mCopyCreate}',handler:_copyCreateImportPaymentQuery,iconCls:'icon-copyCreate'},'-',
{id:'_create',text:'${vt.mCreate}',handler:_createImportPaymentQuery,iconCls:'icon-add'},'-',
'->','-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelImportPaymentQuery,iconCls:'icon-undo'},'-',
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
{id:'_submitProcess',text:'${vt.mSubmitProcess}',handler:_submitProcessImportPaymentQuery,iconCls:'task'},'-',
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
Ext.getCmp('_copyCreate').disable();
Ext.getCmp('_create').disable();
<%
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
Ext.getCmp('_submitProcess').disable();
<%}%>		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
Ext.getCmp('_copyCreate').disable();
Ext.getCmp('_create').disable();	}
	
//});
</script>

<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/importpaymentquery/importPaymentQueryView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/importpaymentquery/importPaymentQueryViewGen.js"></script>

