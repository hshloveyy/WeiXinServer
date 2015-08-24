<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>出口核销单申领表</title>
</head>
<body>
<form name="detailForm">
<table width="800" border="0" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td width="800" height="100%" valign="top"><table width="800" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td width="189">
        <div align="right">出口货物通知单号:</div></td>
        <td>
        	<input type="text" name="exportApplyNo" value="${verification.exportApplyNo}" readonly="readonly"/>
        	<input type="hidden" name="exportApplyId" value="${verification.exportApplyId}" readonly="readonly"/>
        	<input type="hidden" name="receiptDeptIdTemp" value="${verification.receiptDeptId}" readonly="readonly"/>
        	<input type="hidden" name="receiptDeptNameTemp" value="${verification.receiptDeptName}" readonly="readonly"/>
        	<input type="button" value="..." onclick="selectExportApplyInfo()" />
        </td>
        <td align="right">核销单号：</td>
        <td><input type="text" name="writeNo" value="${verification.writeNo}" /></td>
      </tr>
      <tr>
        <td><div align="right">部门:</div></td>
        <td><div id="dept"></div></td>
        <td><div align="right">领单人:</div></td>
        <td><input type="text" name="receiptMan" value="${verification.receiptMan}" /></td>
       
        
      </tr>
      <tr>
        <td><div align="right">领单日期 :</div></td>
        <td><input type="text" name="receiptDate" value="${verification.receiptDate}" /></td>
        <td><div align="right">回单日期:</div></td>
        <td><input type="text" name="backDate" value="${verification.backDate}" /></td>
        
      </tr>
      
      <tr>
        <td><div align="right">发票号:</div></td>
        <td colspan="3"><input type="text" name="taxNo" value="${verification.taxNo}"/></td>
      </tr>
      <tr>
        <td><div align="right">备注:</div></td>
        <td colspan="3">
        	<textarea rows="3" cols="60" name="mark">${verification.mark}</textarea>
        </td>
     </tr>
      <tr>
        <td colspan="4" align="center">
        	<input type="hidden" name="verificationReceiptId" value="${verification.verificationReceiptId }"/>
        	<input type="button" name="Submit" value="保存" onclick="saveForm()"/>
          	<input type="button" name="Submit3" value="关闭" onclick="closeForm()"/>
        </td>
     </tr>
      
    </table></td>
  </tr>
</table>
</form>
</body>
<fiscxd:dept divId="dept" rootTitle="部门信息" width="155" isUseDiv="true"></fiscxd:dept>
<script type="text/javascript">
function selectExportApplyInfo(){
	top.ExtModalWindowUtil.show('查询出口货物通知单信息',
	'exportIncomeController.spr?action=selectExportApplyInfo',
	'',
	selectExportApplyInfoCallback,
	{width:900,height:450});
}
var selectExportApplyInfoCallback = function(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('exportApplyNo').value=returnvalue.NOTICE_NO;
	$('exportApplyId').value=returnvalue.EXPORT_APPLY_ID;
	$('writeNo').value=returnvalue.WRITE_NO;
}

function saveForm(){
	var parm="?action=updateVerification&"+Form.serialize('detailForm');
	    parm = parm + "&receiptDeptId=" + selectId_dept;
	    parm = parm + "&receiptDeptName=" + selectText_dept;
	new AjaxEngine('verificationReceiptController.spr',
			 {method:"post", parameters: parm,onComplete: callBackHandle});
}
var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
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

	var receiptDate = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'receiptDate'
   	});
	var backDate = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'backDate'
   	});

   	
});
selectId_dept = '${verification.receiptDeptId}'
dict_dept.setValue('${verification.receiptDeptName}');
</script>

</html>
