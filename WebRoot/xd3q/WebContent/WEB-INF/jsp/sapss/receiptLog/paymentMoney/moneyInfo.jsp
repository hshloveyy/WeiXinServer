<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>付款详细页面</title>
</head>
<body>
<form name="detailForm">
<table width="585" border="0" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td width="585" height="100%" valign="top"><table width="585" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td width="189"><div align="right">合同号:</div></td>
        <td>
        	<input type="text" name="contractNo" id="contractNo" value="${receiptMoney.contractNo}" readonly="readonly"/>
        	<input type="button" value="..." onclick="selectExportApplyInfo()" />
        	<input type="hidden" value="${receiptMoney.contractId}" id="contractId" name="contractId"/>
        </td>
        </tr>
      <tr>
        <td><div align="right">付款时间:</div></td>
        <td><input type="text" name="payDate" value="${receiptMoney.payDate}" readonly="readonly"/></td>
        </tr>
      <tr>
        <td><div align="right">实际付款时间:</div></td>
        <td><input type="text" name="realPayDate" value="${receiptMoney.realPayDate}" readonly="readonly"/></td>
        </tr>
      <tr>
        <td><div align="right">应付金额:</div></td>
        <td><input type="text" name="payTotal" id="payTotal" value="${receiptMoney.payTotal}" onblur="calcLeftMoney()"/></td>
        </tr>
      <tr>
        <td><div align="right">已付款金额:</div></td>
        <td><input type="text" name="realPayTotal" id="realPayTotal" value="${receiptMoney.realPayTotal}" onblur="calcLeftMoney()"/></td>
        </tr>
      <tr>
        <td><div align="right">应付余额:</div></td>
        <td><input type="text" name="payBalance" id="payBalance" value="${receiptMoney.payBalance}" readonly="readonly"/></td>
        </tr>
      <tr>
        <td><div align="right">采购合同状态:</div></td>
        <td>
        	<select name="purchaseContractStaus" id="purchaseContractStaus">
        		<option value="逾期未收">逾期未收</option>
        		<option value="逾期收款">逾期收款</option>
        		<option value="执行中">执行中</option>
        		<option value="完成">完成</option>
        	</select>
        </td>
        </tr>
      <tr>
        <td><div align="right">备注:</div></td>
        <td><textarea rows="3" cols="50" name="remark">${receiptMoney.remark}</textarea> </td>
        </tr>
      <tr>
        <td colspan="2" align="center">
        	<input type="hidden" name="infoId" value="${receiptMoney.infoId}"/>
        	<input type="button" name="Submit" value="保存" onclick="saveForm()"/>
          	<input type="button" name="Submit3" value="关闭" onclick="closeForm()"/></td>
        </tr>
      
    </table></td>
  </tr>
</table>	
</form>
</body>
<script type="text/javascript">
$('purchaseContractStaus').value='${receiptMoney.purchaseContractStaus}';
function calcLeftMoney(){
	var total= $('payTotal').value;
	total = total==null||total==''?0:total;
	var sub = $('realPayTotal').value;
	sub = sub==null||sub==''?0:sub;
	$('payBalance').value=total-sub;
}

function selectExportApplyInfo(){
	top.ExtModalWindowUtil.show('查询合同信息',
	'receiptsController.spr?action=selectPurchaseInfo',
	'',
	selectExportApplyInfoCallback,
	{width:900,height:500});
}
var selectExportApplyInfoCallback = function(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('contractId').value=returnvalue.CONTRACT_PURCHASE_ID;
	$('contractNo').value=returnvalue.CONTRACT_NO;
}

function saveForm(){
	/*
	if($('contractNo').value==''){
		top.ExtModalWindowUtil.alert('提示','请选择合同号');
		return;
	}
	*/
	var parm="?action=save&"+Form.serialize('detailForm');
	new AjaxEngine('paymentMoneyController.spr',
			 {method:"post", parameters: parm,onComplete: callBackHandle});
}
var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");//{"infolion-json":{"message":"操作成功","type":"info","coustom":{"ok":"保存成功"}}}
	top.ExtModalWindowUtil.alert('提示',customField.ok);
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

	var time = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'payDate'
   	});
	var time1 = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'realPayDate'
   	});
   	
});
</script>
</html>
