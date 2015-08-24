<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年07月21日 19点26分29秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>缓存刷新管理页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务对象缓存管理页面</title>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="center" height="10" width="15%">业务对象名称:</td>
		<td colspan="3" align="left" height="10" width="75%">
			<div id="div_businessobject"></div>
			<input class="inputText" type="text" id="boName" name="boName" value="">
		</td>
	</tr>
	<tr>
		<td width="15%" align="center" height="10"><input type="button" onclick="refreshCacheByBoName()" value="刷新选中业务对象">
		</td>
		<td width="15%" align="center" height="10"><input type="button" onclick="refreshCache()" value="刷新所有业务对象 ">
		</td>
		<td width="15%" align="center" height="10"><input type="button" onclick="_refreshAllDict()" value="刷新所有字典表">
		</td>
		<td width="55%" align="left" height="10"><input type="button" onclick="refreshTextCache()" value="刷新文本缓存">
		</td>
	</tr>
</table>
</form>
</div>
<div id="div_southForm"></div>
</body>
<fisc:searchHelp boName="" boProperty="" searchHelpName="YH_BOIDFORBONAME" searchType="field" hiddenName="boName1" valueField="BONAME" displayField="DESCRIPTION" divId="div_businessobject"  callBackHandler="boSerachHelpCallBack"></fisc:searchHelp>
<script type="text/javascript" defer="defer">
/**
 * 查询
 */
function refreshCache()
{
	new AjaxEngine('<%= request.getContextPath() %>/basicApp/sysConsole/cacheManage/cacheManageController.spr?action=refreshCache', 
			{method:"post", parameters: '', onComplete: callBackHandle});
}

function refreshCacheByBoName()
{ 
	 var param  = Form.serialize('mainForm');
	new AjaxEngine('<%= request.getContextPath() %>/basicApp/sysConsole/cacheManage/cacheManageController.spr?action=refreshCacheByBoName', 
			{method:"post", parameters: param, onComplete: callBackHandle});
}

function refreshTextCache()
{
	new AjaxEngine('<%= request.getContextPath() %>/basicApp/sysConsole/cacheManage/cacheManageController.spr?action=refreshTextCache', 
			{method:"post", parameters: '', onComplete: callBackHandle});
}

function _refreshAllDict()
{
	var param="";
	new AjaxEngine(contextPath + '/platform/basicApp/sysConsole/dictionary/sapDictController.spr?action=_refreshAllDict', 
			   {method:"post", parameters: param, onComplete: callBackHandle});
}

function boSerachHelpCallBack(sjson)
{
    $('boName').value = sjson.BONAME ;
}

</script>
</html>
