<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Chart Detail</title>

  </head>
  
  <body>
    <div id="div_center"></div>
  </body>
</html>
<script type="text/javascript" defer="defer">
	Ext.onReady(function(){
		var viewport = new Ext.Viewport({
			layout:"fit",
			autoScroll:true,
			items:[{
				contentEl:'div_center'
			}]
		});
	});
</script>
<fisc:grid boName="${boName}" divId="div_center" editable="false" needCheckBox="false" needToolbar="false" defaultCondition="${defaultCondition} ${whereSql}" needAutoLoad="true"></fisc:grid>
