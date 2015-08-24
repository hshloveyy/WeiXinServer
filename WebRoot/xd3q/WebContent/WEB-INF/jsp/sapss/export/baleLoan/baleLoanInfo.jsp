<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>打包贷款页面</title>
</head>
<body>
<form name="mainForm">
<table width="585" border="0" cellpadding="0" cellspacing="0" class="datatable">
  <!--DWLayoutTable-->
  <tr>
    <td width="585" height="100%" valign="top"><table width="585" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td><div align="right">打包贷款编号:</div></td>
        <td>
        	<input type="text" name="baleLoanNo" value="${tBaleLoan.baleLoanNo}" readonly="readonly"/>
        </td>
      </tr>      
      <tr>
        <td width="30%"><div align="right"><font color="red">*</font>信用证号码:</div></td>
        <td width="70%">
        	<input type="text" name="creditNo" value="${tCreditInfo.creditNo}" readonly="readonly"/>
        	<input type="button" value="..." onclick="selectCreditInfo()" />
        </td>
        </tr>
      <tr>
        <td><div align="right">到证日期:</div></td>
        <td>
        	<input type="text" name="creditRecDate" value="${tCreditInfo.creditRecDate}" readonly="readonly"/>
        </td>
      </tr>
      <tr>
        <td><div align="right">开证行:</div></td>
        <td>
        	<input type="text" name="createBank" value="${tCreditInfo.createBank}" readonly="readonly"/>
        </td>
      </tr>  
      <tr>
        <td><div align="right">关联合同号:</div></td>
        <td>
        	<input type="text" name="contractNo" value="${tCreditInfo.contractNo}" readonly="readonly"/>
        </td>
      </tr>           
      <tr>
        <td><div align="right">发票号:</div></td>
        <td><input type="text" name="invoice" value="${tCreditInfo.invoice}" /></td>
      </tr>
      <tr>
        <td><div align="right">申请日期:</div></td>
        <td><input type="text" name="applyTime" value="${tBaleLoan.applyTime}" readonly="readonly"/></td>
     </tr>
     <tr>
        <td><div align="right"><font color="red">*</font>银行:</div></td>
        <td><input type="text" name="bank" value="${tBaleLoan.bank}" /></td>
      </tr>
     <tr>
        <td><div align="right"><font color="red">*</font>币别:</div></td>
        	
        <td><div id="div_currency"></div></td>
     </tr>     
     <tr>
        <td><div align="right"><font color="red">*</font>申请金额:</div></td>
        <td><input type="text" name="applyMoney" value="${tBaleLoan.applyMoney}" /></td>
     </tr>

      <tr height="25">  
      </tr>
      <tr>
        <td colspan="2" align="center">
        	<input type="button" name="Submit1" value="保存" onclick="saveForm()"/>
			<input type="button" name="Submit2" value="提交" onclick="submitForm()"/>        	
          	<input type="button" name="Submit3" value="关闭" onclick="closeForm()"/></td>
          	
          	<input type="hidden" name="baleLoanId" value="${tBaleLoan.baleLoanId}"/>
          	<input type="hidden" name="creditId" value="${tBaleLoan.creditId}"/>          	
          	<input type="hidden" name="realMoney" value="${tBaleLoan.realMoney}"/>
          	<input type="hidden" name="cmd" value="${tBaleLoan.cmd}"/>
          	<input type="hidden" name="deptId" value="${tBaleLoan.deptId}"/>
          	<input type="hidden" name="examineState" value="${tBaleLoan.examineState}"/>
          	<input type="hidden" name="approvedTime" value="${tBaleLoan.approvedTime}"/>
          	<input type="hidden" name="isAvailable" value="${tBaleLoan.isAvailable}"/>
          	<input type="hidden" name="creatorTime" value="${tBaleLoan.creatorTime}"/>
          	<input type="hidden" name="creator" value="${tBaleLoan.creator}"/>          	          	          	          	          	
        </tr>
      
    </table>
    </td>
  </tr>
</table>	
</form>
</body>
<script type="text/javascript">
function selectCreditInfo(){
	top.ExtModalWindowUtil.show('查询信用证开证信息',
	'baleLoanController.spr?action=selectCreditInfo',
	'',
	selectCreditInfoCallback,
	{width:750,height:400});
}
var selectCreditInfoCallback=function(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('creditId').value = returnvalue.CREDIT_ID;
	$('creditNo').value = returnvalue.CREDIT_NO;
	$('creditRecDate').value = returnvalue.CREDIT_REC_DATE;
	$('createBank').value = returnvalue.CREATE_BANK;
	$('contractNo').value = returnvalue.CONTRACT_NO;	
	$('invoice').value = returnvalue.INVOICE;					
}


function saveForm(){
	var parm="?action=save&"+Form.serialize('mainForm');
	new AjaxEngine('baleLoanController.spr',
			 {method:"post", parameters: parm,onComplete: callBackHandle});
}
function submitForm(){
	var parm="?action=saveAndsubmit&"+Form.serialize('mainForm');
	new AjaxEngine('baleLoanController.spr',
			 {method:"post", parameters: parm,onComplete: callBackHandle});
}
var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");//{"infolion-json":{"message":"操作成功","type":"info","coustom":{"ok":"保存成功"}}}
	top.ExtModalWindowUtil.alert('提示',customField.ok);
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();	
}
function closeForm(){
	Ext.Msg.show({
   		title:'提示',
   		msg: '是否退出并关闭窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
			}
   		},
   		animEl: 'elId',
   		icon: Ext.MessageBox.QUESTION
	});
}
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
});

</script>
</html>
<fiscxd:dictionary divId="div_currency" fieldName="currency"  dictionaryName="BM_CURRENCY" width="153" selectedValue="${tBaleLoan.currency}" needDisplayCode="true"></fiscxd:dictionary>
