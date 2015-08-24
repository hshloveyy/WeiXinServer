<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" defer="defer">
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
});

function showFindSupplier(){
	top.ExtModalWindowUtil.show('查找供应商','masterQueryController.spr?action=toFindSupplier','',supplierCallback,{width:755,height:410});
}

function supplierCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	if(cb==''){
		Ext.getDom('providerName').value='';
		Ext.getDom('providerID').value='';
	}
	else {
		Ext.getDom('providerName').value=cb.NAME1;
		Ext.getDom('providerID').value=cb.LIFNR;
	}
}

function resetForm(form){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否清空窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
   				form.reset()
   			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}

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
	var  Letters  =  "-1234567890.";  //可以自己增加可输入值
	var  i;
	var  c;
	if(input.charAt(0)=='.')
		return  false;
	if( input.charAt(String.length-1 ) ==  '.' )
		return  false;
	for(  i  =  0;  i  <  input.length;  i  ++  )
	{  
		c  =  input.charAt(  i  );
		if  (Letters.indexOf(  c  )  <  0)
		return  false;
	}
	return  true;
}

function saveProviderPrepaymentFORM(btn){

	/*
	if(Ext.getDom('providerName').value == '')
	{
		showMsg("请选择供应商！");
		return false;
	}*/
	if(Ext.getDom('prepayment').value == '')
	{
		showMsg("请输入代垫发生金额！");
		return false;
	}
	
	if( isNumber(Ext.getDom('prepayment').value) == false )
	{
		showMsg("代垫发生金额必须为数字！");
		return false;
	}
	
	var param=Form.serialize('providercreditForm');
	new AjaxEngine('providerCreditConfController.spr?action=updatePrepayment', 
			{method:"post", parameters: param, onComplete: callBackHandle});
}
</script>

</head>
<body>
<form id="providercreditForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
		<td align="right"  width="15%" /> <font color="red">★</font>供应商名称：</td>
		<td  width="30%" >
			<div id="div_providerid_sh"></div>
			<fisc:searchHelp divId="div_providerid_sh" searchType="field" searchHelpName="YHPROVCREDCONF"  hiddenName="providerid" boName="" boProperty="" value="" displayField="NAME1" valueField="LIFNR"></fisc:searchHelp>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>项目名称：</td>
		<td width="30%" >
			<div id="div_projectid_sh"></div>
			<!--<fisc:searchHelp divId="div_projectid_sh" boName="ProviderCreditProj" boProperty="projectid" value=""></fisc:searchHelp>-->
			<fisc:searchHelp divId="div_projectid_sh" searchType="field" searchHelpName="YHXPROJECTINFO" hiddenName="projectid" displayField="PROJECT_NAME" valueField="PROJECT_ID" boName="" boProperty="" value=""></fisc:searchHelp>
		</td>
</tr>


<tr>
	<td width="15%" align="right"><font color="red">★</font>
		代垫发生金额：
	</td>
	<td>
		<input name="prepayment" id="prepayment" value="" type="text" class="text" size="30" />
	</td>
	
	<td width="15%" align="right"><font color="red">★</font>
		备注： 
	</td>
	<td>
		<input name="remark" id="remark" value="" type="text" class="text" size="30"/> 
	</td>
</tr>
</table>
<table width="100%">
<tr>
	<td><div align="center">
	  <input type="button" value="保存" onclick="saveProviderPrepaymentFORM(this)" id="saveProviderPrepayment"/>
	  <input type="button" value="清空" onclick="resetForm(this.form)" id="clearProviderPrepayment"/>
    </div></td>
</tr>
</table>
</form>
</body>
</html>

<script type="text/javascript" defer="defer">
Ext.onReady(function(){
	div_projectid_sh_sh.on('beforeclick',function(){
		div_projectid_sh_sh.defaultCondition = "PROVIDER_ID='" + div_providerid_sh_sh.getJsonValue().LIFNR + "' and DEPT_ID in (SELECT dept_id FROM t_sys_dept_user WHERE user_id = '${userId}' UNION SELECT DEPT_ID FROM T_SYS_USER_MANAGER_DEPT WHERE USER_ID = '${userId}')";
	});
});
</script>
