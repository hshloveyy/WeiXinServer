<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月04日 10点48分28秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象SAP采购凭证(PurchasingDoc)管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SAP采购凭证管理页面</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchasingDocManage.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchasingDocManageGen.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">采购凭证号：</td>
		<td  width="30%" >
		<!-- UITYPE:13 -->
			<input type="text" class="inputText" id="ebeln.fieldValue" name="ebeln.fieldValue" value="" <fisc:authentication sourceName="PurchasingDoc.ebeln" taskId=""/>>
			<input type="hidden" id="ebeln.fieldName" name="ebeln.fieldName" value="EKKO.EBELN"> 
			<input type="hidden" id="ebeln.dataType" name="ebeln.dataType" value="S">  
			<input type="hidden" id="ebeln.option" name="ebeln.option" value="like">
		</td>
		<td width="15%" align="right">记录的创建日期：</td>
		<td  width="40%" >
		<!-- UITYPE:04 -->
			<input type="text" id="aedat.fieldValue" name="aedat.fieldValue" value="">
			<input type="hidden" id="aedat.fieldName" name="aedat.fieldName" value="EKKO.AEDAT"> 
			<input type="hidden" id="aedat.dataType" name="aedat.dataType" value="D">  
			<input type="hidden" id="aedat.option" name="aedat.option" value="like">
				<fisc:calendar applyTo="aedat.fieldValue"  divId="" fieldName="" ></fisc:calendar>
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
		            title:"SAP采购凭证明细",
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
<fisc:grid divId="div_southForm" boName="PurchasingDoc" defaultCondition="${sqlWhere}" editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
