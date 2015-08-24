<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>收款详细页面</title>
</head>
<body>
<form name="mainForm">
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
        	<c:if test="${empty(tradeType)=='true' }">
        	<input type="hidden" name="tradeType" value="${receiptMoney.tradeType}"/>
        	</c:if>
        	<c:if test="${empty(tradeType)=='false' }">
        	<input type="hidden" name="tradeType" value="${tradeType}"/>
        	</c:if>
        	</td>
        </tr>
      <tr>
        <td width="189"><div align="right">立项号:</div></td>
        <td>
        	<input type="text" name="projectNo" id="projectNo" value="${receiptMoney.projectNo}" readonly="readonly"/>
        	<input type="button" value="..." onclick="selectProjectInfo()" />
        	<input type="hidden" value="${receiptMoney.projectId}" id="projectId" name="projectId"/>
        	</td>
        </tr>
      <tr>
        <td><div align="right">收款时间:</div></td>
        <td><input type="text" name="receiptDate" value="${receiptMoney.receiptDate}" readonly="readonly"/></td>
        </tr>
      <tr>
        <td><div align="right">实际收款时间:</div></td>
        <td><input type="text" name="realReceiptDate" value="${receiptMoney.realReceiptDate}" readonly="readonly"/></td>
        </tr>
      <tr>
        <td><div align="right">应收金额:</div></td>
        <td><input type="text" name="receiptTotal" value="${receiptMoney.receiptTotal}"/></td>
        </tr>
      <tr>
        <td><div align="right">已收款金额:</div></td>
        <td><input type="text" name="realReceiptTotal" value="${receiptMoney.realReceiptTotal}" /></td>
        </tr>
      <tr>
        <td><div align="right">应收余额:</div></td>
        <td><input type="text" name="receiptBalance" value="${receiptMoney.receiptBalance}" /></td>
        </tr>
      <tr>
        <td><div align="right">销售合同状态:</div></td>
        <td><input type="text" name="saleContractStaus" id="saleContractStaus" value="${receiptMoney.saleContractStaus}" readonly="readonly"/></td>
        </tr>
      <tr>
        <td><div align="right">对内收款金额（含保证金）:</div></td>
        <td><input type="text" name="innerReceiptTotal" value="${receiptMoney.innerReceiptTotal}" /></td>
        </tr>
      <tr>
        <td><div align="right">对内收款余额:</div></td>
        <td><input type="text" name="innerReceiptBalance" value="${receiptMoney.innerReceiptBalance}" /></td>
        </tr>
      <tr>
        <td><div align="right">对内收款关税:</div></td>
        <td><input type="text" name="innerReceiptDuty" value="${receiptMoney.innerReceiptDuty}" /></td>
        </tr>
      <tr>
        <td><div align="right">对内收款费用:</div></td>
        <td><input type="text" name="innerReceiptFee" value="${receiptMoney.innerReceiptFee}" /></td>
        </tr>
      <tr>
        <td><div align="right">用途:</div></td>
        <td><textarea rows="3" cols="50" name="use">${receiptMoney.use}</textarea> </td>
        </tr>
      <tr>
        <td><div align="right">备注:</div></td>
        <td><textarea rows="3" cols="50" name="remark">${receiptMoney.remark}</textarea> </td>
        </tr>
      <tr>
        <td colspan="2" align="center">
        	<input type="hidden" name="infoId" value="${receiptMoney.infoId}"/>
        	<c:if test="${!save=='true'}">
        	<input type="button" name="Submit" value="保存" onclick="saveForm()"/>
        	</c:if>
        	<c:if test="${!close=='true'}">
          	<input type="button" name="Submit3" value="关闭" onclick="closeForm()"/>
          	</c:if>
          	<c:if test="${!submit=='true'}">
          	<input type="button" name="Submit3" value="提交" onclick="submitForm()"/>
          	</c:if>
          	<input type="hidden" name="creator" value="${receiptMoney.creator}"/>
          	<input type="hidden" name="createTime" value="${receiptMoney.createTime}"/>
          	<input type="hidden" name="isAvailable" value="${receiptMoney.isAvailable}"/>
          	<input type="hidden" name="deptId" value="${receiptMoney.deptId}"/>
          	<input type="hidden" name="examineState" value="${receiptMoney.examineState}"/>
          	<input type="hidden" name="applyTime" value="${receiptMoney.applyTime}"/>
          	<input type="hidden" name="approvedTime" value="${receiptMoney.approvedTime}"/>
        </td>
        </tr>
      
    </table></td>
  </tr>
</table>	
</form>
</body>
<script type="text/javascript">
function selectExportApplyInfo(){
	top.ExtModalWindowUtil.show('查询合同信息',
	'exportIncomeController.spr?action=selectSalesInfo',
	'',
	selectExportApplyInfoCallback,
	{width:900,height:500});
}
var selectExportApplyInfoCallback = function(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('contractId').value=returnvalue.CONTRACT_SALES_ID;
	$('contractNo').value=returnvalue.CONTRACT_NO;
	$('saleContractStaus').value=returnvalue.ORDER_STATE_D_ORDER_STATE;
	$('projectNo').value=returnvalue.PROJECT_NO;
	$('projectId').value=returnvalue.PROJECT_ID;
}
function selectProjectInfo(){
	top.ExtModalWindowUtil.show('查询立项信息',
	'projectController.spr?action=selectProjrctInfo',
	'',
	selectProjectInfoCallBack,
	{width:800,height:400});
}
function selectProjectInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('projectId').value = returnvalue.PROJECT_ID + "";
	$('projectNo').value = returnvalue.PROJECT_NO + "";
}
function saveForm(){
	if($('contractNo').value==''){
		top.ExtModalWindowUtil.alert('提示','请选择合同号');
		return;
	}
	var parm="?action=save&" + Form.serialize("mainForm");
	new AjaxEngine('receiptMoneyController.spr',
			 {method:"post", parameters: parm,onComplete: callBackHandle});
}
function submitForm(){
    if($('contractNo').value==''){
		top.ExtModalWindowUtil.alert('提示','请选择合同号');
		return;
	}
	var parm="?action=submitAndSaveInfo&"+Form.serialize('mainForm');
	new AjaxEngine('receiptMoneyController.spr',
			 {method:"post", parameters: parm,onComplete: callBackHandle});

}

var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var sReturnMessage=customField.returnMessage;
	
	top.ExtModalWindowUtil.alert('提示',sReturnMessage);

	if (sReturnMessage.indexOf("提交成功")>-1)
	{
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	}
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
		applyTo:'receiptDate'
   	});
	var time1 = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'realReceiptDate'
   	});
   	
});
</script>
</html>
