<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典表</title>
</head>
<body>
&nbsp;<div id ="dict" style="margin: 5px"></div>
<div id ="sdict" style="margin: 5px"></div>
<fisc:dictionary boName="PurchaseOrder" boProperty="purchaseOrderNo" dictionaryName="YBUSINESSOBJECT" divId="dict"></fisc:dictionary>
<fisc:dictionary dictionaryName="YMETHOD" boName="PurchaseOrder" boProperty="certificateType" parentBoProperty="purchaseOrderNo" divId="sdict"></fisc:dictionary>
</body>
</html>