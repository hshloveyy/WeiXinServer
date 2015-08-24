<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>关于</title>
</head>

<body>
<table width="100%" > 
<tr align="center" >
    <td colspan="2" width="100%" align="center">
    	<img alt="" src="../images/logo.jpg"  align="center">
    </td>
</tr>
<tr>
	<td align="right" width="30%">产品名:</td>
	<td width="70%">${productInfo.productDesc}</td>
</tr>
<tr>
	<td align="right" width="30%">版本:</td>
	<td width="70%">${productInfo.verNo}</td>
</tr>
<tr>
	<td align="right" width="30%">发布日期:</td>
	<td width="70%">${productInfo.buildDate}</td>
</tr>
<tr>
	<td align="right" width="30%">许可号:</td>
	<td width="70%">${productInfo.licenseno}</td>
</tr>
<tr>
	<td align="right" width="30%">授权给:</td>
	<td width="70%">${productInfo.licenseToCompany}</td>
</tr>
<tr>
	<td align="right" width="30%" >许可截止日期:</td>
	<td width="70%">${productInfo.permitDateEnd}</td>
</tr>
</table>
</body>
</html>


