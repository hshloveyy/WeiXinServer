<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 <form name="mainForm" id="mainForm">
<table align="center">

 <tr valign="middle" height="150">
    <td>&nbsp;&nbsp;&nbsp;信用证号：<input type="text" name="creditNo"/></td>
    <td><input type="hidden" name="tradeType" value="${tradeType }"/><input type="button" value="确定" onclick="referenceCreate()"/></td>
 </tr>

</table>
 </form>
<script type="text/javascript">
function BuycallbackFunction(){
$('creditNo').value='';
top.ExtModalWindowUtil.markUpdated();
}


function customCallBackHandle(transport){
    
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.setReturnValue(customField);
	top.ExtModalWindowUtil.markUpdated();
	//top.ExtModalWindowUtil.close();
	top.ExtModalWindowUtil.show('参考创建信用证开证信息','creditEntryController.spr?action=modify&operate=modify&tradeType='+ ${tradeType} +'&creditId='+customField.creditId,'',BuycallbackFunction,{width:900,height:600});
	
}
function referenceCreate(){
    var creditNo = $('creditNo').value;
    if(creditNo=='') {
       top.ExtModalWindowUtil.alert('提示','信用证号不能为空！');
       return;
    }    

    var param = Form.serialize('mainForm');
	            param += "&action=addReferenceCredit";
    new AjaxEngine('creditEntryController.spr', 
	   {method:"post", parameters: param, onComplete: callBackHandle});
}

Ext.onReady(function(){	
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
});
</script>
</body>
</html>