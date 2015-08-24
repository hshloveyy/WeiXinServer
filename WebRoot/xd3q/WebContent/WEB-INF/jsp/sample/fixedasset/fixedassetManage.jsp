<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月08日 08点18分11秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象固定资产(Fixedasset)管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>固定资产管理页面</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/fixedasset/fixedassetManage.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/fixedasset/fixedassetManageGen.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">资产类型：</td>
		<td  width="30%" >
		<!-- UITYPE:01 -->
			<input type="text" class="inputText" id="anlar.fieldValue" name="anlar.fieldValue" value="" <fisc:authentication sourceName="Fixedasset.anlar" taskId=""/>>
			<input type="hidden" id="anlar.fieldName" name="anlar.fieldName" value="YFIXEDASSET.ANLAR"> 
			<input type="hidden" id="anlar.dataType" name="anlar.dataType" value="S">  
			<input type="hidden" id="anlar.option" name="anlar.option" value="like">
		</td>
		<td width="15%" align="right"> 产品/物料：</td>
		<td  width="40%" >
		<!-- UITYPE:11 -->
			<div id="div_matnr_sh"></div>
			<input type="hidden" id="matnr.fieldName" name="matnr.fieldName" value="YFIXEDASSET.MATNR"> 
			<input type="hidden" id="matnr.dataType" name="matnr.dataType" value="S">  
			<input type="hidden" id="matnr.option" name="matnr.option" value="like">
			<fisc:searchHelp divId="div_matnr_sh" boName="Fixedasset" boProperty="matnr" searchType="field" hiddenName="matnr.fieldValue" valueField="MATERIELID" displayField="MATERIELNAME"></fisc:searchHelp>
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
<script type="text/javascript">

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
		            title:"固定资产明细",
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
<fisc:grid divId="div_southForm" boName="Fixedasset" defaultCondition="${sqlWhere}" editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
