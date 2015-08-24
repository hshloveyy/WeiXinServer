<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
</head>
<body >
<div id="div1">
	<table align="center" width="400">
		<tr>
			<td><a href="#" onclick="importR()">进口报表</a></td>
			<td><a href="#" onclick="exportR()">出口报表</a></td>
			<td><a href="#" onclick="homeTradeR()">内贸报表</a></td>
		</tr>
	</table>
</div>	
<div id="div2">
<form name="form2">
	<table align="center" width="400">
		<tr>
			<td>立项编号:</td>
			<td><input type="text" name="projectNo"/></td>
			<td></td>
			<td><input type="button" value="树报表" onclick="treeGridR()"/></a></td>
		</tr>
	</table>
</form>	
</div>	

</body>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';

	var main=new Ext.form.FormPanel({
		renderTo:document.body,
		frame:true,
		baseCls:'x-plain',
		autoHeight:true,
		labelWidth:40,
		width:800,
		bodyStyle:'padding: 10px 10px 0 10px;',
		defaults:{
			anchor:'95%',
			msgTarget:'side',
			align:'center'
		},
		items:[{
			contentEl:'div1'
		},{
			contentEl:'div2'
			}]
	});
});
function importR(){
	window.open('outerReportController.spr?action=toImportReport','','fullscreen');
/*	top.ExtModalWindowUtil.show('进口报表',
			'outerReportController.spr?action=toImportReport',
			'','',{width:window.screen.availWidth,height:window.screen.availHeight-200});
*/
}
function exportR(){
	window.open('outerReportController.spr?action=toExportReport','','fullscreen');
}	
function homeTradeR(){
	window.open('outerReportController.spr?action=toHomeTradeReport','','fullscreen');
}	
</script>
</html>