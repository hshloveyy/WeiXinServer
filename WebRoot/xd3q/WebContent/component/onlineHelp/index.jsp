<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fisc" tagdir="/WEB-INF/tags/infolion"%>
<%@ include file="/common/commons.jsp"%>
<html lang="en" xmlns:ext="http://extjs.com/docs">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="2" background="red">
	<tr>
		<td id="lastModifyor">测试帮助</td>
		<td id="purchaseOrderId">CESHIBANGZHU</td>
		<td id="creator">帮助测试</td>
		<td id="certificateType">2222</td>
		
		<td id="lastModifyTime">3333</td>
		<td id="purchaseOrderNo">4444</td>
		<td id="EDITOR">5555</td>
		<td id="CREATE_TIME">66666</td>
		<td id="GROUP_COLUMN">77777</td>
		<td id="DICT_NAME_COLUMN">8888</td>
		<td id="MEMO">99999</td>
		<td id="DICT_TABLE">1111111</td>
		<td><input type="text" id="IS_ROMATE_DICT" name="IS_ROMATE_DICT"> </td>
		<!--  
		<td id="IS_ROMATE_DICT">BANGZHUCESHI</td>
		<td id="IS_GROUP_DICT">BANGZHUCESHI</td>
		<td id="CREATOR">BANGZHUCESHI</td>
		<td id="DICT_NAME">BANGZHUCESHI</td>
		
		<td id="DICT_PA_COLUMN">BANGZHUCESHI</td>
		-->
	</tr>
</table>
</body>
</html>
<fisc:onlineHelp boName="PurchaseOrder" closable="true" width="200" showDelay="500" extendField="animCollapse:true"></fisc:onlineHelp>