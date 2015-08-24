﻿<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2011年12月12日 16点14分48秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象期初已到税票未进仓(税额)(VatDetail)查看页面
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
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${vatDetail.tid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
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
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/vatdetail/vatDetailView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/vatdetail/vatDetailViewGen.js"></script>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
var isSubmited = '';
//当前对象主键ID
var tid = '${vatDetail.tid}';	

//页面文本
var Txt={
	//期初已到税票未进仓(税额)
	vatDetail:'${vt.boText}',
	//boText复制创建
	vatDetail_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	vatDetail_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	vatDetail_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		border:false,
				            		//height:10,
				            		autoScroll: true,
				            		contentEl:'div_center'
						}
							   ]
				   }]
				}
				 ]
	});
	
	var toolbars = new Ext.Toolbar({
			items:[
					'-',
{id:'_copyCreate',text:'${vt.mCopyCreate}',handler:_copyCreateVatDetail,iconCls:'icon-copyCreate'},'-',
{id:'_create',text:'${vt.mCreate}',handler:_createVatDetail,iconCls:'icon-add'},'-',
'->','-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelVatDetail,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	
//});
</script>