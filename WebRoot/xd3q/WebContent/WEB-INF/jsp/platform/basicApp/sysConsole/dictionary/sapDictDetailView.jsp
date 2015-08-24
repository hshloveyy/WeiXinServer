<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年11月04日 12点45分59秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象 (SapDictDetail)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 查看页面</title>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="40%" align="right" >域值：</td>
		<td width="60%" >
			<input type="text" class="inputText" id="SapDictDetail.domValue_l" name="SapDictDetail.domValue_l" value="${sapDictDetail.domValue_l}" <fisc:authentication sourceName="SapDictDetail.domValue_l" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="40%" align="right" >文本：</td>
		<td width="60%" >
			<input type="text" class="inputText" id="SapDictDetail.ddText" name="SapDictDetail.ddText" value="${sapDictDetail.ddText}" <fisc:authentication sourceName="SapDictDetail.ddText" taskId=""/>>
		</td>
	</tr>
<input type="hidden" id="SapDictDetail.domName" name="SapDictDetail.domName" value="${sapDictDetail.domName}">
<input type="hidden" id="SapDictDetail.ddLanguage" name="SapDictDetail.ddLanguage" value="${sapDictDetail.ddLanguage}">
<input type="hidden" id="SapDictDetail.valPos" name="SapDictDetail.valPos" value="${sapDictDetail.valPos}">
<input type="hidden" id="SapDictDetail.as4Local" name="SapDictDetail.as4Local" value="${sapDictDetail.as4Local}">
<input type="hidden" id="SapDictDetail.as4Vers" name="SapDictDetail.as4Vers" value="${sapDictDetail.as4Vers}">
</table>
</form>
</div>

<div id="div_east" class="x-hide-display"></div>
<div id="div_west" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">

/**
 * 取消
 */
function _cancel()
{
	_getMainFrame().ExtModalWindowUtil.close();
}
/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[{
			region:'center',
			border:false,
			buttonAlign:'center',
			items:[{
					layout:'fit',
					region:'center',
					height:137.5,
					border:false,
					bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_center'
				}],
			buttons:[
				     {
				        	text:'取消',
				        	minWidth: 40,
				        	name:'btn_cancel',
				        	handler:_cancel
				}]
		}]
	});
	
//});
</script>
