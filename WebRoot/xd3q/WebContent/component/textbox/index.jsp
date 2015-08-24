<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>控件权限控制测试</title>
</head>
<body>
<form name="myform">
<input type="text" value="testvalue" name="input1" <fisc:authentication sourceName="purchaseOrder.YAUTEST1"></fisc:authentication> >
<input type="text" value="testvalue" name="input2" <fisc:authentication sourceName="purchaseOrder.YAUTEST1" isNumber="true"></fisc:authentication> >
<input type="text" value="testvalue" name="input3" <fisc:authentication sourceName="purchaseOrder.YAUTEST1" isNumber="Integer"></fisc:authentication> >
<input type="checkbox" value="c1" name="input4" title="aa" checked="checked" <fisc:authentication sourceName="purchaseOrder.YAUTEST1" type="checkbox"></fisc:authentication> >
<input type="checkbox" value="c2" name="input4" title="bb" <fisc:authentication sourceName="purchaseOrder.YAUTEST1" type="checkbox"></fisc:authentication> >
<input type="checkbox" value="c3" name="input4" title="cc" <fisc:authentication sourceName="purchaseOrder.YAUTEST1" type="checkbox"></fisc:authentication> >
<input type="radio" value="c2" name="input5" title="bb" <fisc:authentication sourceName="purchaseOrder.YAUTEST1" type="radio"></fisc:authentication> >
<input type="radio" value="c3" name="input5" title="cc" <fisc:authentication sourceName="purchaseOrder.YAUTEST1" type="radio"></fisc:authentication> >
<textarea rows="5" name="input6" cols="20" <fisc:authentication sourceName="purchaseOrder.FIELD_NAME" type="textarea"></fisc:authentication>>abcd</textarea>
<div id="relation"></div>
</form>
</body>
<script type="text/javascript">
var panel = new Ext.Panel({
	title: '协作对象',//标题
	collapsible:false,//右上角上的那个收缩按钮，设为false则不显示
	renderTo: 'relation',//这个panel显示在html中id为${divId}的层中
	id:'relationship',
	width:280,
	height:500,
	<fisc:authentication sourceName="purchaseOrder.YAUTEST1" isExt="true"></fisc:authentication>
	frame:true,
	html: "<p>panel主体中的内容，可以执行html代码</p>"
});
</script>
</html>