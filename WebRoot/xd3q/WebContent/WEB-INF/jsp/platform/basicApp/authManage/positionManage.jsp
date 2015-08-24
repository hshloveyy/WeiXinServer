<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月17日 10点34分51秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象职位(Position)管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>职位管理页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/positionManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/authManage/positionManageGen.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"> 部门组织：</td>
		<td  width="30%" >
		<!-- UITYPE:11 -->
			<div id="div_organizationid_sh"></div>
			<input type="hidden" id="organizationid.fieldName" name="organizationid.fieldName" value="YPOSITION.ORGANIZATIONID"> 
			<input type="hidden" id="organizationid.dataType" name="organizationid.dataType" value="S">  
			<input type="hidden" id="organizationid.option" name="organizationid.option" value="like">
			<fisc:searchHelp divId="div_organizationid_sh" boName="Position" boProperty="organizationid" searchType="field" hiddenName="organizationid.fieldValue" valueField="ORGANIZATIONCODE" displayField="ORGANIZATIONNAME"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">描述：</td>
		<td  width="40%" >
		<!-- UITYPE:01 -->
			<input type="text" class="inputText" id="desc.fieldValue" name="desc.fieldValue" value="" <fisc:authentication sourceName="Position.desc" taskId=""/>>
			<input type="hidden" id="desc.fieldName" name="desc.fieldName" value="YPOSITION.DESC_"> 
			<input type="hidden" id="desc.dataType" name="desc.dataType" value="S">  
			<input type="hidden" id="desc.option" name="desc.option" value="like">
		</td>
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
		            title:"职位明细",
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});
});
</script>
<fisc:grid divId="div_southForm" boName="Position"  editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
