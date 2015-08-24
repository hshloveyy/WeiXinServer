<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月23日 15点52分47秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象OrdrRep(OrdrRep)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.viewPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/ordr/ordrRepView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/ordr/ordrRepViewGen.js"></script>
</head>
<body>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${ordrRep.docentry}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right" >${vt.property.taxdate}：</td>
		<td width="30%">
			<input type="text" id="OrdrRep.taxdate" name="OrdrRep.taxdate" value="">
				<fisc:calendar applyTo="OrdrRep.taxdate"  divId="" fieldName="" defaultValue="${ordrRep.taxdate}"></fisc:calendar>
		</td>
		<td width="15%" align="right" >${vt.property.comments}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="OrdrRep.comments" name="OrdrRep.comments" value="${ordrRep.comments}" <fisc:authentication sourceName="OrdrRep.comments" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.cardcode}：</td>
		<td width="30%">
			<div id="div_cardcode_sh"></div>
			<fisc:searchHelp divId="div_cardcode_sh" boName="OrdrRep" boProperty="cardcode"  value="${ordrRep.cardcode}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right" >${vt.property.cardname}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="OrdrRep.cardname" name="OrdrRep.cardname" value="${ordrRep.cardname}" <fisc:authentication sourceName="OrdrRep.cardname" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.docdate}：</td>
		<td width="30%">
			<input type="text" id="OrdrRep.docdate" name="OrdrRep.docdate" value="">
				<fisc:calendar applyTo="OrdrRep.docdate"  divId="" fieldName="" defaultValue="${ordrRep.docdate}"></fisc:calendar>
		</td>
		<td width="15%" align="right" >${vt.property.numatcard}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="OrdrRep.numatcard" name="OrdrRep.numatcard" value="${ordrRep.numatcard}" <fisc:authentication sourceName="OrdrRep.numatcard" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" >${vt.property.slpcode}：</td>
		<td width="30%">
			<input type="text" class="inputText" id="OrdrRep.slpcode" name="OrdrRep.slpcode" value="${ordrRep.slpcode}" <fisc:authentication sourceName="OrdrRep.slpcode" taskId="${workflowTaskId}"/>  >
		</td>
		<td width="15%" align="right" >${vt.property.slpname}：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="OrdrRep.slpname" name="OrdrRep.slpname" value="${ordrRep.slpname}" <fisc:authentication sourceName="OrdrRep.slpname" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>

	<input type="hidden" id="OrdrRep.docentry" name="OrdrRep.docentry" value="${ordrRep.docentry}">
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
var isSubmited = '';
//当前对象主键ID
var docentry = '${ordrRep.docentry}';	

//页面文本
var Txt={
	//OrdrRep
	ordrRep:'${vt.boText}',
	//boText复制创建
	ordrRep_CopyCreate:'${vt.boTextCopyCreate}',
    //boText创建
	ordrRep_Create:'${vt.boTextCreate}',
	// 复制创建
	mCopyCreate:'${vt.mCopyCreate}',
	// 创建
	mCreate:'${vt.mCreate}',
	// 编辑
	mEdit:'${vt.mEdit}',
	// 查看
	mView:'${vt.mView}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	ordrRep_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				            		//height:160,
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
'->','-',
{id:'_cancel',text:'${vt.mCancel}',handler:_cancelOrdrRep,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	
//});
</script>
