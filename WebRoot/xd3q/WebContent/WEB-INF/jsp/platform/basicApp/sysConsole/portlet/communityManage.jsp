<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年10月16日 09点40分31秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>社区管理(Community)管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社区管理管理页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/sysConsole/portlet/communityManage.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="15%" align="right">社区定义名称：</td>
		<td  width="30%" >
		<!-- UITYPE:01 -->
			<input type="text" class="inputText" id="communityname.fieldValue" name="communityname.fieldValue" value="" <fisc:authentication sourceName="Community.communityname" taskId=""/>>
			<input type="hidden" id="communityname.fieldName" name="communityname.fieldName" value="YCOMMUNITY.COMMUNITYNAME"> 
			<input type="hidden" id="communityname.dataType" name="communityname.dataType" value="S">  
			<input type="hidden" id="communityname.option" name="communityname.option" value="like">
		</td>
    <td></td>
    <td></td>
	</tr>
	<tr>
		<td align="center" colspan="4" height="10">
		</td>
	</tr>
</table>
</form>
</div>
<div id="div_southForm"></div>
</body>
</html>
<script type="text/javascript" defer="defer">

/**
 * EXT 布局
 */
Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			items:[{
					region:"north",
					contentEl:'div_toolbar',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					contentEl:'div_center'
				},		
				{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:"社区管理明细",
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-cls'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-cls'},'-'
				],
		renderTo:"div_toolbar"
	});
});
</script>
<fisc:grid divId="div_southForm" boName="Community" defaultCondition="${sqlWhere}" editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
