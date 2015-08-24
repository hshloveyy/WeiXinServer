<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年11月04日 14点42分05秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能> (SapDict)管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 管理页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/platform/basicApp/sysConsole/dictionary/sapDictManage.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">	<tr>
		<td width="15%" align="right">字典表名称：</td>
		<td  width="30%" >
			<input type="text" class="inputText" id="domName.fieldValue" name="domName.fieldValue" value="" <fisc:authentication sourceName="SapDict.domName" taskId=""/>>
			<input type="hidden" id="domName.fieldName" name="domName.fieldName" value="YDICT.DOMNAME"> 
			<input type="hidden" id="domName.dataType" name="domName.dataType" value="S">  
			<input type="hidden" id="domName.option" name="domName.option" value="like">
		</td>
				<td width="15%" align="right">字典表描述：</td>
		<td  width="40%" >
			<input type="text" class="inputText" id="ddText.fieldValue" name="ddText.fieldValue" value="" <fisc:authentication sourceName="SapDict.ddText" taskId=""/>>
			<input type="hidden" id="ddText.fieldName" name="ddText.fieldName" value="YDICT.DDTEXT"> 
			<input type="hidden" id="ddText.dataType" name="ddText.dataType" value="S">  
			<input type="hidden" id="ddText.option" name="ddText.option" value="like">
		</td>
	</tr>
		<tr>
		<td width="15%" align="right">开发类：</td>
		<td  width="30%" >
			<div id="div_devClass_dict"></div>
			<fisc:dictionary hiddenName="devClass.fieldValue" dictionaryName="YDDEVCLASS" divId="div_devClass_dict" isNeedAuth="false" ></fisc:dictionary>
			<input type="hidden" id="devClass.fieldName" name="devClass.fieldName" value="YDICT.DEVCLASS"> 
			<input type="hidden" id="devClass.dataType" name="devClass.dataType" value="S">  
			<input type="hidden" id="devClass.option" name="devClass.option" value="like">
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


function _refreshAllDict()
{
	var param="";
	new AjaxEngine(contextPath + '/platform/basicApp/sysConsole/dictionary/sapDictController.spr?action=_refreshAllDict', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
}

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
		            title:"字典表明细",
		            height:420,
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
<fisc:grid divId="div_southForm" pageSize="10" 
 tableName="(select a.DOMNAME,a.DATATYPE,a.LENG,b.DDTEXT,c.DEVCLASS from DD01L a left outer join DD01T b on a.DOMNAME = b.DOMNAME and b.DDLANGUAGE='${ddLanguage}' left outer join TADIR c on a.DOMNAME = c.OBJ_NAME where c.DEVCLASS in(select DOMVALUE_L as DEVCLASS  from DD07T where DD07T.DOMNAME='YDDEVCLASS') ) YDICT"
 handlerClass="com.infolion.platform.basicApp.sysConsole.dictionary.web.grid.SapDictManageGrid"
 whereSql="" defaultCondition=""
 needCheckBox="false" needToolbar="true" needAutoLoad="true" nameSapceSupport="false"
 entityName="SapDict"></fisc:grid> 
 
 