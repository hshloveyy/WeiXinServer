<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月17日 10点34分51秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象职务(Post)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>职务查看页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/postView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/postViewGen.js"></script>
<style type="text/css">
  .x-grid3-header-offset {width: auto;}
</style>
</head>
<body>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${post.postid}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
</div>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right" >职务编号：</td>
		<td width="30%">
			<input type="text" class="inputText" id="Post.postno" name="Post.postno" value="${post.postno}" <fisc:authentication sourceName="Post.postno" taskId="${workflowTaskId}"/>   readonly="readonly">
		</td>
		<td width="15%" align="right" >描述：</td>
		<td  width="40%">
			<input type="text" class="inputText" id="Post.desc" name="Post.desc" value="${post.desc}" <fisc:authentication sourceName="Post.desc" taskId="${workflowTaskId}"/>  >
		</td>
	</tr>
	<tr>
		<td width="15%" align="right" valign="top">备注：</td>
		<td width="30%" colspan="3" >
			<textarea rows="4" cols="54" id="Post.memo" name="Post.memo" <fisc:authentication sourceName="Post.memo" taskId="${workflowTaskId}"/>>${post.memo}</textarea>
		</td>
	</tr>

	<input type="hidden" id="Post.client" name="Post.client" value="${post.client}">
	<input type="hidden" id="Post.postid" name="Post.postid" value="${post.postid}">
</table>
</form>
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
var postid = '${post.postid}';	

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
				            		title:'职务信息',
				            		layout:'fit',
				            		border:false,
				            		//height:47.5,
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
{id:'_create',text:'创建',handler:_createPost,iconCls:'icon-add'},'-',
{id:'_copyCreate',text:'复制创建',handler:_copyCreatePost,iconCls:'icon-table-itemadd'},'-',
'->','-',
{id:'_cancel',text:'取消',handler:_cancelPost,iconCls:'icon-undo'},'-',
' '
],
			renderTo:'div_toolbar'
	});

	
//});
</script>
