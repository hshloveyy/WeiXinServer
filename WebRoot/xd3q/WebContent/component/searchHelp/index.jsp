<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/infolion" prefix="fisc"%>
<html lang="en" xmlns:ext="http://extjs.com/docs">
<head>
  <title>index</title>
  <%@ include file="/common/commons.jsp"%>
</head>
<body>
<fisc:searchHelp boName="AuthResource" boProperty="object" divId="div2"></fisc:searchHelp>
<fisc:searchHelp boName="" boProperty="" searchType="field" searchHelpName="YHALLBOMETHOD" displayField="BONAME" valueField="BOID" hiddenName="test" divId="div4"></fisc:searchHelp>
<div id="div"></div>
<div id="div2"></div>
<div id="div3"></div>
<div id="div4"></div>
<div id="ext-modal-dialog-win" class="x-hidden">
    <div class="x-window-header" id="ext-modal-dialog-win-header"></div>
    <div id="ext-modal-dialog-win-content"></div>
</div>
<div>

</body>
</html>
<script>
	var contextPath = '<%=request.getContextPath()%>';
	Ext.onReady(function(){
		var searchHelp = new Ext.form.TextField({
			id:'attachement.FILENAME',
			renderTo:'div3'
		});		
	});
</script>