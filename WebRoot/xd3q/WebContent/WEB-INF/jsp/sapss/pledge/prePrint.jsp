<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>货物进仓单打印</title>
<style type="text/css">
.add{
	background-image:url(<%=request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%=request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%=request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%=request.getContextPath()%>/images/fam/find.png) !important;
}
</style>
</head>
<body>
<br>
<table><tr  align="center" valign="middle"><td>
<input type="button" value="打印存根联" onclick="printForm(1)">
</td>
<td>
<input type="button" value="打印财务联" onclick="printForm(2)">
</td>
<td>
<input type="button" value="打印业务联" onclick="printForm(3)">
</td>
<td>
<input type="button" value="打印全部" onclick="printForm(4)">
</td>
</tr></table>
<script>
function printForm(printType){
    window.open('pledgeReceiptsController.spr?action=dealPrint&receiptId=${receiptId}&printType='+printType,'_blank','location=no,resizable=yes');
}
</script>
</body>
</html>
