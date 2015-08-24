<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
var warnInfoId = '${warnInfoId}';
var warnId = '${warnids}';
var primarykey = '${primarykeys}';

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
});

function showMsg(msg){
	top.Ext.Msg.show({
			title:'提示',
			closable:false,
			msg:msg,
			buttons:{yes:'关闭'},
			fn:Ext.emptyFn,
			icon:Ext.MessageBox.INFO
	});
}

function  isNumber(input)
{  
	var  Letters  =  "1234567890";  //可以自己增加可输入值
	var  i;
	var  c;
	for(  i  =  0;  i  <  input.length;  i  ++  )
	{  
		c  =  input.charAt(  i  );
		if  (Letters.indexOf(  c  )  <  0)
		return  false;
	}
	return  true;
}

function confirmForm(btn)
{
	if(Ext.getDom('againWarnDay').value == '')
	{
		showMsg("请输入再次提醒天数！");
	}
	if( isNumber(Ext.getDom('againWarnDay').value) == false )
	{
		showMsg("再次提醒天数必须为数字！");
		return false;
	}	
	var param=Form.serialize('warnProcessForm');
	new AjaxEngine('advanceWarnInfoController.spr?action=processWarnInfo&warnInfoId=' + warnInfoId + '&warnId=' + warnId + '&primarykey=' + primarykey, 
			{method:"post", parameters: param, onComplete: callBackHandle});	
	
	_getMainFrame().ExtModalWindowUtil.markUpdated();	
}

</script>

</head>
<body>
<form id="warnProcessForm" class="search">
<table width="100%" border="0" cellpadding="3" cellspacing="1">
<tr>
	<td width="70%" align="right" ><font color="red">★</font>再次提醒天数：</td>
	<td width="10%" >
		<input type= "text" value ="0" id="againWarnDay" name="againWarnDay"/>
	</td>
	<td width="30%"></td>
</tr>
</table>
<table width="100%">
<tr>
	<td><div align="center">
	  <input type="button" value="确定" onclick="confirmForm(this)" id="confirm"/>
    </div></td>
</tr>
</table>
</form>
</body>
</html>
