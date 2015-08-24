<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
});

function showFindCustomer(){
	top.ExtModalWindowUtil.show('查找客户','masterQueryController.spr?action=toFindCustomer','',customerCallback,{width:755,height:410});
}

function customerCallback(){
	var cb = top.ExtModalWindowUtil.getReturnValue();
	if(cb==''){
	    Ext.getDom('customerName').value='';
		Ext.getDom('customerid').value='';
	}
	else {
		Ext.getDom('customerName').value=cb.NAME1;
		Ext.getDom('customerid').value=cb.KUNNR;
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


function saveCustomerPrepaymentFORM(btn){	
	/*
	if(Ext.getDom('customerName').value == '')
	{
		showMsg("请选择客户！");
		return false;
	}
	*/
	if(Ext.getDom('prepayValue').value == '' || Ext.getDom('sendValue').value == '')
	{
		showMsg("请输入发生金额！");
		return false;
	}
	
	if(isNumber(Ext.getDom('prepayValue').value) == false || isNumber(Ext.getDom('sendValue').value) == false)
	{
		showMsg("发生金额必须为数字！");
		return false;
	}
	
   var param=Form.serialize('custcreditForm');
	new AjaxEngine('customerCreditConfController.spr?action=updateCredit', 
			{method:"post", parameters: param, onComplete: callBackHandle});
}

function setValueType(){
	var valueType = Ext.getDom('valueType').value;
	if(valueType==1){	// 放货类型
		Ext.getDom('prepayValue').readOnly = true;
		Ext.getDom('sendValue').readOnly = false;
		Ext.getDom('prepayValue').value = 0;
		Ext.getDom('sendValue').value = '';
		Ext.getDom('sendValue').focus();
	}else if(valueType==2){	// 代垫类型
		Ext.getDom('prepayValue').readOnly = false;
		Ext.getDom('sendValue').readOnly = true;
		Ext.getDom('prepayValue').value = '';
		Ext.getDom('sendValue').value = 0;
		Ext.getDom('prepayValue').focus();
	}else{	// 放货+代垫
		Ext.getDom('prepayValue').readOnly = false;
		Ext.getDom('sendValue').readOnly = false;
		Ext.getDom('prepayValue').focus();
	}
}


</script>

</head>
<body onload="setValueType()">
<form id="custcreditForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<tr>
	<td align="right"  <fisc:authentication sourceName="CustomerCreditConf.customerid" nodeId="${workflowNodeDefId}"/> ><font color="red">★</font>客户名称：</td>
		<td  >
			<div id="div_customerid_sh"></div>
			<!--<fisc:searchHelp divId="div_customerid_sh" boName="CustomerCreditConf" boProperty="customerid" value=""></fisc:searchHelp>-->
			<fisc:searchHelp divId="div_customerid_sh" searchType="field" searchHelpName="YHOTHECHANRECO" hiddenName="customerid" displayField="NAME1" valueField="KUNNR" boName="" boProperty="" value=""></fisc:searchHelp>
			</td>
	
	<td align="right" ><font color="red">★</font>项目名称：</td>
	<td >
			<div id="div_projectid_sh"></div>
			<!--<fisc:searchHelp divId="div_projectid_sh" boName="CustomerCreditProj" boProperty="projectid" value=""></fisc:searchHelp>-->
			<fisc:searchHelp divId="div_projectid_sh" searchType="field" searchHelpName="YHCOSTPROJ" hiddenName="projectid" displayField="PROJECT_NAME" valueField="PROJECT_ID" boName="" boProperty="" value=""></fisc:searchHelp>
	</td>
</tr>
<tr>
	<td align="right"><font color="red">★</font>
		授信类型： 
	</td>
	<td align="left">
		<select id="valueType" name="valueType" onchange="setValueType()">
			<option value="1" selected="selected">放货</option>
			<option value="2">代垫</option>
			<option value="3">放货+代垫</option>
		</select>
	</td>
	
	<td align="right"><font color="red">★</font>
		放货金额：
	</td>
	<td>
		<input name="sendValue" id="sendValue" value="" type="text" class="text" size="30" />
	</td>
</tr>
<tr>
	<td align="right"><font color="red">★</font>
		代垫金额：
	</td>
	<td>
		<input name="prepayValue" id="prepayValue" value="" type="text" class="text" size="30" />
	</td>

	<td align="right"><font color="red">★</font>
		备注： 
	</td>
	<td align="left">
		<input name="remark" id="remark" value="" type="text" class="text" size = "30"/> 
	</td>
</tr>
</table>
<table width="100%">
<tr>
	<td><div align="center">
	  <input type="button" value="保存" onclick="saveCustomerPrepaymentFORM(this)" id="saveCustomerPrepayment"/>
	  <input type="button" value="清空" onclick="resetForm(this.form)" id="clearCustomerPrepayment"/>
    </div></td>
</tr>
</table>
</form>
</body>
</html>

<script type="text/javascript" defer="defer">
Ext.onReady(function(){
	div_projectid_sh_sh.on('beforeclick',function(){
		div_projectid_sh_sh.defaultCondition = "CUSTOMER_ID='" + div_customerid_sh_sh.getJsonValue().KUNNR + "' and DEPT_ID in (SELECT dept_id FROM t_sys_dept_user WHERE user_id = '${userId}' UNION SELECT DEPT_ID FROM T_SYS_USER_MANAGER_DEPT WHERE USER_ID = '${userId}')";
	});
});
</script>
