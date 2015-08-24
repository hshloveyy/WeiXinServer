<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<%@ page import="com.infolion.platform.dpframework.language.LangConstants"%>
<%@ page import="com.infolion.platform.dpframework.language.LanguageService"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>模板选择</title>
	</head>

	<body>
		<div id="div_toolbar"></div>
		<div id="div_center"></div>
	</body>
</html>
<%
// 系统文本
String strOk = LanguageService.getText(LangConstants.SYS_OK);
String strCancel = LanguageService.getText(LangConstants.SYS_CANCEL);
%>
<script type="text/javascript" defer="defer">
   	function _save(){
   		var records = Attachement_grid.selModel.getSelections();
   		var param = records[0].json.attachementId;
		_getMainFrame().ExtModalWindowUtil.setReturnValue(param);
		_getMainFrame().ExtModalWindowUtil.markUpdated();
		_getMainFrame().ExtModalWindowUtil.close();
   	}
   	
   	function _cancel(){
   		_getMainFrame().ExtModalWindowUtil.close();
   	}
   	
    Ext.onReady(function(){
    	
    	var viewport = new Ext.Viewport({
			layout:'border',
			items:[{
				layout:'border',
				region:'center',
				items:[{
					region:'north',
	            	layout:'fit',
					height:28,
	            	contentEl:'div_toolbar'
				},{
					region:'center',
					layout: 'fit',
		            autoScroll: true,
					contentEl:'div_center'
				}]
			}]
		});
		var toolbars = new Ext.Toolbar({
			items:[	'->','-',
				{id:'_save',text:'<%=strOk%>',handler:_save,iconCls:'task'},'-',
				{id:'_cancel',text:'<%=strCancel%>',handler:_cancel,iconCls:'icon-undo'},'-'
				],
			renderTo:"div_toolbar"
		});
	});
	
	function _uploadAttachement(){}
	function _deletesAttachement(){}
	//alert('${excelTemplate.tempId}');
</script>
<fisc:grid boName="Attachement" divId="div_center" height="300"
	pageSize="10" needCheckBox="false" needAutoLoad="true"
	nameSapceSupport="true" needOperationColumn="false"
	defaultCondition=" BUSINESSID='${excelTemplate.tempId}'"
	needToolbar="false"></fisc:grid>
