<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/js/extex/extex.css"/>
<script language="javascript" src="<%= request.getContextPath() %>/js/ext/adapter/ext/ext-base.js"></script>
<script language="javascript" src="<%= request.getContextPath() %>/js/ext/ext-all.js"></script>
<link rel="stylesheet" type="text/css"	href='<%= request.getContextPath() %>/css/layout.css'>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
</head>
<body>
<form action="<%= request.getContextPath() %>/deployWorkflowServlet" method="post" enctype="multipart/form-data" name="form1" class="search">
<table width="600" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr>
		<td align="right">请选择要发布的流程定义文件：</td>
		<td><input name="workflowfile" type="file" id="workflowfile">
		</td>
		<td align="right"><input type="submit" name="Submit"
			value="提 交 "></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
</table>
</form>
</body>
<script type="text/javascript" defer="defer">
   	
	var tabs = new Ext.Panel({
		id:'mainPanel',
        renderTo: document.body,
        autoWidth:true,
        loadMask:true,
        frame:true,
        baseCls:'scarch',
        items:[
            	{contentEl:'form1',id:'form',width:'100%',title: '流程发布'}
              ]
    });
</script>
</html>
