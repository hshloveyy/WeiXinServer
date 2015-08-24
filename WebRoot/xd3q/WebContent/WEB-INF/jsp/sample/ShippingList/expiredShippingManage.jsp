<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月04日 14点50分31秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象过期出货清单(ExpiredShipping)管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>过期出货清单管理页面</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/ShippingList/expiredShippingManage.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/ShippingList/expiredShippingManageGen.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right"> 工厂：</td>
		<td  width="30%" >
		<!-- UITYPE:11 -->
			<div id="div_werks_sh"></div>
			<input type="hidden" id="werks.fieldName" name="werks.fieldName" value="YVSOITEM.WERKS"> 
			<input type="hidden" id="werks.dataType" name="werks.dataType" value="S">  
			<input type="hidden" id="werks.option" name="werks.option" value="like">
			<fisc:searchHelp divId="div_werks_sh" boName="ExpiredShipping" boProperty="werks" searchType="field" hiddenName="werks.fieldValue" valueField="WERKS" displayField="NAME1"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">销售凭证：</td>
		<td  width="40%" >
		<!-- UITYPE:01 -->
			<input type="text" class="inputText" id="vbeln.fieldValue" name="vbeln.fieldValue" value="" <fisc:authentication sourceName="ExpiredShipping.vbeln" taskId=""/>>
			<input type="hidden" id="vbeln.fieldName" name="vbeln.fieldName" value="YVSOITEM.VBELN"> 
			<input type="hidden" id="vbeln.dataType" name="vbeln.dataType" value="S">  
			<input type="hidden" id="vbeln.option" name="vbeln.option" value="like">
		</td>
</tr>
	<tr>
		<td width="15%" align="right">客户：</td>
		<td  width="30%" >
		<!-- UITYPE:01 -->
			<input type="text" class="inputText" id="kunnr.fieldValue" name="kunnr.fieldValue" value="" <fisc:authentication sourceName="ExpiredShipping.kunnr" taskId=""/>>
			<input type="hidden" id="kunnr.fieldName" name="kunnr.fieldName" value="YVSOITEM.KUNNR"> 
			<input type="hidden" id="kunnr.dataType" name="kunnr.dataType" value="S">  
			<input type="hidden" id="kunnr.option" name="kunnr.option" value="like">
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
		            title:"过期出货清单明细",
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
<fisc:grid divId="div_southForm" boName="ExpiredShipping" defaultCondition="${sqlWhere}" editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
