<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<html lang="en" xmlns:ext="http://extjs.com/docs">
<head>
  <title>index</title>
</head>
<body>
<div id="div"></div>
<div id="div2"></div>
<div id="div3"></div>

<div>
<input type="button" value="搜索帮助测试按钮" onclick="showSearchHelp()">
</body>
</html>
<script>
function winCallBack(jsonArrayData)
{
	alert(Ext.util.JSON.encode(jsonArrayData));
}
var searchHelpWin = new Ext.SearchHelpWindow({
	shlpName : 'YH_BOIDFORBONAME',
	callBack : winCallBack,
	defaultCondition:''
});
function showSearchHelp(){
	searchHelpWin.show(); 	
}
</script>