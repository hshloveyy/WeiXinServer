<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程状态图</title>
<style>
<!--
.cToken {
	border: 4px solid red;
	border-style:groove;
	background-color:transparent;
}

.cToken1 {
	border: 4px solid transparent;
	border-style:groove;
	background-color:transparent;
}
-->
</style>

</head>
<body>
<table cellpadding="1" border="1" cellspacing="1" align="left">
<tr><td>流程名称</td><td>打开</td></tr>
<c:forEach items="${imgs}" var="img_name">
			<tr>
				<td align="left">${img_name }</td><td><a href="#" onclick="showImg('${img_name }')">显示</a></td>
			</tr>
</c:forEach>
<script>
function showImg(flow_name){
	top.ExtModalWindowUtil.show(flow_name,
	'common/showImg.jsp?img_url=${path}' + escape(escape(flow_name)),
	'',
	'',
	{width:800,height:600});
	alert('/common/showImg.jsp?img_url=${path}' + escape(flow_name));
}
</script>
</table>
</body>
</html>