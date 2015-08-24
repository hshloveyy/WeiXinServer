<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.infolion.platform.basicApp.authManage.domain.MenuTreeText"%>
<%@page import="java.util.List" %>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>翻译菜单</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_menuForm" class="search">
<form id="menuForm" name="menuForm">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
	<td width="100%" colspan="4" align="left">
	翻译菜单，从${sourceLanguageIso}到${targerLanguageIso}:
	<input type="hidden" id="menuId" name="menuId" value="${menuText.menuId}">
	<input type="hidden" id="targerLanguageMenuTextId" name="targerLanguageMenuTextId" value="${menuText.targerLanguageMenuTextId}">
	<input type="hidden" id="targerLanguage" name="targerLanguage" value="${targerLanguage}">
	<input type="hidden" id="sourceLanguage" name="sourceLanguage" value="${sourceLanguage}">
	</td>
</tr>
<tr>
	<td width="15%" align="left">菜单名称:</td>
	<td width="30%" align="left">
	 <input type="text" class="inputText" value="${menuText.menuText}" readonly="readonly">
	</td>
	<td width="15%" align="left">翻译后菜单名称:</td>
	<td width="40%" align="left">
	 <input type="text" class="inputText" id="strMenuText" name="strMenuText" value="${menuText.targerLanguageMenuText}">
	</td>
</tr>
<%
List<MenuTreeText> menuTreeTexts = (List<MenuTreeText>) request.getAttribute("menuTreeTexts");
  for(MenuTreeText menuTreeText : menuTreeTexts)
  {
%>
	 <tr>
		<td width="15%" align="left">菜单节点名称:</td>
		<td width="30%">
			 <input type="text" class="inputText" value="<%=menuTreeText.getMenuTreeText()%>" readonly="readonly">
		</td>
		<td width="15%" align="left">翻译后菜单节点名称:</td>
		<td width="40%" >
			<input type="hidden" id="nodeId<%=menuTreeText.getMenuTreeTextId()%>" name="nodeId<%=menuTreeText.getMenuTreeTextId()%>" value="<%=menuTreeText.getNodeId()%>">
			<input type="hidden" id="tMenuTreeTextId<%=menuTreeText.getMenuTreeTextId()%>" name="tMenuTreeTextId<%=menuTreeText.getMenuTreeTextId()%>" value="<%=menuTreeText.getTargerLanguageMenuTreeTextId()%>">
			<input type="text" class="inputText" id="tMenuTreeText<%=menuTreeText.getMenuTreeTextId()%>" name="tMenuTreeText<%=menuTreeText.getMenuTreeTextId()%>" value="<%=menuTreeText.getTargerLanguageMenuTreeText()%>">
		</td>
	</tr>
<%
  }
%>
</table>
</form>
</div>
</body>
</html>
<script type="text/javascript" defer="defer">
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var result = responseUtil1.getCustomField("coustom");
<%
	  for(MenuTreeText menuTreeText : menuTreeTexts)
	  {
		 String  menuTreeTextId = menuTreeText.getMenuTreeTextId();
%>
document.getElementById("tMenuTreeTextId<%=menuTreeText.getMenuTreeTextId()%>").value = result.tMenuTreeTextId<%=menuTreeText.getMenuTreeTextId()%>;
<%
	  }
%>	
}

function _save()
{
	var param = Form.serialize('menuForm');
	new AjaxEngine(contextPath + '/basicApp/authManage/menuController.spr?action=_saveTranslateText', 
			   {method:"post", parameters: param, onComplete: callBackHandle});
}

function _cancel(){
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
}

var toolbars = new Ext.Toolbar({
	items:[
			'->',
			{id:'_save',text:'保存',handler:_save,iconCls:'icon-table-save'},'-'
			,{id:'_cancel',text:'取消',handler:_cancel,iconCls:'icon-undo'},'-'
			],
	renderTo:"div_toolbar"
});
</script>