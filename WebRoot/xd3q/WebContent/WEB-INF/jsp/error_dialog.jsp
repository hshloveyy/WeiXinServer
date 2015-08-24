<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script language="javascript"
	src='<%= request.getContextPath() %>/js/lib/prototype.js'></script>
<script language="javascript"
	src='<%= request.getContextPath() %>/js/lib/RunInfoUtil.js'></script>
<link rel="stylesheet" type="text/css"
	href='<%= request.getContextPath() %>/css/common.css'>
<script language="javascript">
  		var runInfoImgSrc = '<%= request.getContextPath() %>/images/info.gif';		
  		var runInfo = new RunInfoUtil("runInfoDiv", runInfoImgSrc);
  		
  		function closeDialog(){
			_getMainFrame().ExtModalDialog.close();
		}
		
		function showRunInfo(){
			runInfo.showError("${msg}");
		}
  	</script>
</head>

<body style="padding: 5px;" onload="showRunInfo()">
<div id="runInfoDiv"></div>
<div style="padding-top: 10px;"><input type="button" value="关  闭"
	class="button" onclick="closeDialog()" /></div>
</body>
</html>
