<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%= request.getContextPath() %>/js/ext-2.0/resources/css/ext-all.css" />
<script type="text/javascript"
	src="<%= request.getContextPath() %>/js/ext-2.0/adapter/ext/ext-base.js"></script>
<script type="text/javascript"
	src="<%= request.getContextPath() %>/js/ext-2.0/ext-all.js"></script>
<title>Insert title here</title>
</head>
<body>
<div id="pane_div">
<table>
	<tr>
		<td><input type="button" value="关闭" onclick="closewindows();">
		</td>
	</tr>
</table>
</div>
</body>
</html>
<script>
function closewindows(){
	window.top.ExtModalWindowUtil.close();
}

Ext.onReady(function(){	
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '/infolionPlatform/images/fam/s.gif';
   	
   	
   	var simple = new Ext.FormPanel({
        frame:true,
        title: '测试',
        bodyStyle:'padding:5px 5px 0',
        width: 360,
        defaults: {width: 220}
    });
    simple.render('pane_div');
		
});
   	
</script>