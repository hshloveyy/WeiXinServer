<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月17日 10点34分51秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象职务(Post)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>职务增加页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/postAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/postAddGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 5(调试用)序号:2;formRowNo:1 ;rowNo: 1;columnNo: 1;1X -->
		<td align="right"  width="15%" >职务编号：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="Post.postno" name="Post.postno" value="${post.postno}" <fisc:authentication sourceName="Post.postno" taskId=""/>  readonly="readonly">
		</td>
<!-- column.property.numberObjectId column.visibility=="X" 5(调试用)序号:3;formRowNo:1 ;rowNo: 1;columnNo: 2;1X -->
		<td align="right"  width="15%" ><font color="red">★</font>描述：</td>
		<td   width="40%" >
			<input type="text" class="inputText" id="Post.desc" name="Post.desc" value="${post.desc}" <fisc:authentication sourceName="Post.desc" taskId=""/> >
		</td>
	</tr>
	<tr>
<!-- column.property.numberObjectId column.visibility=="X" 5(调试用)序号:4;formRowNo:2 ;rowNo: 2;columnNo: 1;1X -->
		<td align="right"  width="15%" valign="top">备注：</td>
		<td  width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="Post.memo" name="Post.memo" <fisc:authentication sourceName="Post.memo" taskId=""/>>${post.memo}</textarea>
		</td>
	</tr>

	<input type="hidden" id="Post.client" name="Post.client" value="${post.client}">
	<input type="hidden" id="Post.postid" name="Post.postid" value="${post.postid}">
</table>
</form>
</div>

<div id="div_south" class="x-hide-display"></div>

</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${isCreateCopy}';

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
							layout: 'anchor',
				            height:600,
				            border:false,
				            autoScroll: true,
				            items:[{
				            		title: '职务信息',
				            		layout:'fit',
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
					'-','->','-',
					          
          
          
          
					{id:'_save',text:'保存',handler:_save,iconCls:'icon-table-save'},'-',
          
					{id:'_cancel',text:'取消',handler:_cancel,iconCls:'icon-undo'},
					'-'],  
			renderTo:"div_toolbar"
	});
//});
</script>
