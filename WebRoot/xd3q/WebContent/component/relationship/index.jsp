<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关联对象</title>
</head>
<body>
<form>
<div id="relation"></div>
<div id="gridDiv"></div>
</form>
</body>
<script type="text/javascript">
	function _upload(){
	}
	function _download(){
	}
	function _delete(){
	}
	function _deletes(){
	}
	function _create(){}
	function _copyCreate(){}
</script>
<fisc:relationship divId="relation" width="750" boName="PurchaseOrder" height="350"></fisc:relationship>
<fisc:grid divId="gridDiv" boName="OrderItems" width="750" height="350" pageSize="10" ></fisc:grid>
</html>