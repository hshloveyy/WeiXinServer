<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>收汇详细页面</title>
</head>
<body>
<form name="detailForm">
<table width="585" border="0" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td width="585" height="100%" valign="top"><table width="585" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td width="189"><div align="right">项目号:</div></td>
        <td>
        	<input type="text" name="projectNo" value="${exportIncomeInfo.projectNo}" readonly="readonly"/>
        	<input type="button" value="..." onclick="selectProjectInfo()" />
         	<input type=hidden name="projectId" value="${exportIncomeInfo.projectId}"/>
        </td>
        </tr>
      <tr>
        <td><div align="right">合同号:</div></td>
        <td>
        	<input type="text" name="contractNo" value="${exportIncomeInfo.contractNo}" readonly="readonly"/>
        	<input type="button" value="..." onclick="selectSalesInfo()" />
        </td>
        </tr>
      <tr>
        <td><div align="right">发票号<font color="red">▲</font></div></td>
        <td><input type="text" name="invNo" value="${exportIncomeInfo.invNo}" /></td>
        </tr>
      <tr>
      <tr>
        <td><div align="right">出口货物通知单号<font color="red">▲</font></div></td>
        <td>
        	<input type="hidden" name="exportApplyId" value="${exportIncomeInfo.exportApplyId}" />
        	<input type="hidden" name="exportBillExamId" value="${exportIncomeInfo.exportBillExamId}" />
        	<input type="hidden" name="id" value="${id}" />
        	<input type="text" name="exportApplyNo" value="${exportIncomeInfo.exportApplyNo}" readonly="readonly"/>
        	<input type="button" value="..." onclick="selectExportBillExamWin()" />
        	<a href="#" onclick="viewExportExamForm()">查看</a>
        </td>	
        </tr>
       <tr>
      
        <td><div align="right">核销单号:</div></td>
        <td><input type="text" name="writeNo" value="${exportIncomeInfo.writeNo}" /></td>
        </tr>
      <tr>
      
        <td><div align="right">实际收汇金额:</div></td>
        <td><input type="text" name="total" value="${exportIncomeInfo.total}" /></td>
        </tr>
      <tr>
        <td><div align="right">实际收汇日:</div></td>
        <td><input type="text" name="acceptDate" value="${exportIncomeInfo.acceptDate}" readonly="readonly"/></td>
        </tr>
      <tr>
        <td><div align="right">押汇利率:</div></td>
        <td><input type="text" name="negotialRate" value="${exportIncomeInfo.negotialRate}" /></td>
        </tr>
      <tr>
        <td><div align="right">押汇收款金额:</div></td>
        <td><input type="text" name="negotialIncome" value="${exportIncomeInfo.negotialIncome}" /></td>
        </tr>
      <tr>
        <td><div align="right">押汇收款日:</div></td>
        <td><input type="text" name="negotiatDate" value="${exportIncomeInfo.negotiatDate}" readonly="readonly"/></td>
        </tr>
      <tr>
        <td><div align="right">收汇币别:</div></td>
        <td><input type="text" name="currency" value="${exportIncomeInfo.currency}" /></td>
        </tr>
      <tr>
        <td><div align="right">收汇银行:</div></td>
        <td>
            <select name="payBank" id="payBank">
               <option value="">请选择</option>
               <option value="光大">光大</option>
               <option value="中行">中行</option>
               <option value="交行">交行</option>
               <option value="工行">工行</option>
               <option value="农行">农行</option>
               <option value="招行">招行</option>
               <option value="东亚银行">东亚银行</option>
               <option value="建设">建设</option>
            </select>
            <script> 
                document.getElementById('payBank').value='${exportIncomeInfo.payBank}';
            </script>
        </td>
        </tr>
      <tr>
        <td><div align="right">折人民币:</div></td>
        <td><input type="text" name="cnyTotal" value="${exportIncomeInfo.cnyTotal}" /></td>
        </tr>
      <tr>
        <td><div align="right">用途:</div></td>
        <td><input type="text" name="cmd" value="${exportIncomeInfo.cmd}" size="50"/></td>
        </tr>
      <tr>
        <td colspan="2" align="center">
        	<input type="button" name="Submit" value="保存" onclick="saveForm()"/>
          	<input type="button" name="Submit3" value="关闭" onclick="closeForm()"/></td>
          	
          	<input type="hidden" name="exportIncomeInfoId" value="${exportIncomeInfo.exportIncomeInfoId}"/>
        </tr>
      
    </table>
    </td>
  </tr>
</table>	
</form>
</body>
<script type="text/javascript">
function selectExportBillExamWin(){
	top.ExtModalWindowUtil.show('查询所属登陆员工部门的出口出单审单信息',
	'exportQueryController.spr?action=toFindExportBillExam&tradeType=' + '${tradeType}' +'&examineState=3&deptid=',
	'',
	selectExportBillExamCallBack,
	{width:900,height:450});
}
function Nvl(str) 
{
	if (str=="null"||str==null)	return "";
 	else return str;
 }
function selectExportBillExamCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	Ext.getDom('exportBillExamId').value=Nvl(returnvalue.EXPORT_BILL_EXAM_ID);
	Ext.getDom('exportApplyNo').value=Nvl(returnvalue.EXPORT_APPLY_NO);
	Ext.getDom('invNo').value=Nvl(returnvalue.INV_NO);
	Ext.getDom('currency').value=Nvl(returnvalue.CURRENCY);
	Ext.getDom('writeNo').value=Nvl(returnvalue.WRITE_NO);
	$('contractNo').value=returnvalue.CONTRACT_NO;
	$('projectNo').value=returnvalue.CONTRACT_NO.substring(0,6);
}
function viewExportExamForm(){
var exportExamId = $('exportBillExamId').value;
if(exportExamId==''||exportExamId==null) return;
top.ExtModalWindowUtil.show('出口审单信息','exportController.spr?action=updateExportBillExamView&tradeType=2&isShow=1&exportBillExamId='+exportExamId,'','',{width:900,height:500});
}
function selectProjectInfo(){
	top.ExtModalWindowUtil.show('查询立项信息',
	'contractController.spr?action=selectProjrctInfo',
	'',
	selectProjectInfoCallback,
	{width:560,height:400});
}
var selectProjectInfoCallback=function(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('projectNo').value = returnvalue.PROJECT_NO;
	$('projectId').value = returnvalue.PROJECT_ID;
}

function selectSalesInfo(){
	top.ExtModalWindowUtil.show('查询销售合同信息',
	'exportIncomeController.spr?action=selectSalesInfo',
	'',
	selectSalesInfoCallback,
	{width:900,height:450});
}
var selectSalesInfoCallback=function(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('contractNo').value = returnvalue.CONTRACT_NO;
}

function selectExportApplyInfo(){
	top.ExtModalWindowUtil.show('查询出口货物通知单信息',
	'exportIncomeController.spr?action=selectExportApplyInfo',
	'',
	selectExportApplyInfoCallback,
	{width:900,height:450});
}
var selectExportApplyInfoCallback = function(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('exportApplyId').value=returnvalue.EXPORT_APPLY_ID;
	$('exportApplyNo').value=returnvalue.NOTICE_NO;
	$('writeNo').value=returnvalue.WRITE_NO;
}
function saveForm(){
    if(Ext.getDom('exportBillExamId').value==''){
	   top.ExtModalWindowUtil.alert('提示','出口货物通知单号必须填写！');
	   return;
	}
	var parm="?action=save&"+Form.serialize('detailForm');
	new AjaxEngine('exportIncomeController.spr',
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
		applyTo:'acceptDate'
   	});
	var time1 = new Ext.form.DateField({
		format:'Y-m-d',
		width:160,
		applyTo:'negotiatDate'
   	});
   	
});

</script>
</html>
