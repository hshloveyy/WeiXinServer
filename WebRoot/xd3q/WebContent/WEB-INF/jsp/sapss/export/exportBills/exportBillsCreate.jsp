<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出口押汇单</title>
<style type="text/css">
.add{
	background-image:url(<%=request.getContextPath()%>/images/fam/add.gif) !important;
}
.delete{
	background-image:url(<%=request.getContextPath()%>/images/fam/delete.gif) !important;
}
.update{
	background-image:url(<%=request.getContextPath()%>/images/fam/refresh.gif) !important;
}
.find{
	background-image:url(<%=request.getContextPath()%>/images/fam/find.png) !important;
}
</style>
<script type="text/javascript">

var strOperType='';

//贸易类型
var tradeType ='${tradeType}';

//贸易名称
var strshipTitle ='';

var Enabled='false';
if ('${submit}'!='true')Enabled='submit';
if ('${save}'!='true')Enabled='save';

//取贸易名称
strshipTitle = Utils.getTradeTypeTitle(tradeType);

function selectExportBillExamWin(){
	if (Enabled=='false')return;	//Wang Yipu 2009.4.9
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
	Ext.getDom('billType').value=Nvl(returnvalue.BILL_TYPE);
	Ext.getDom('billDate').value=Nvl(returnvalue.BILL_DATE);
	Ext.getDom('contractNo').value=Nvl(returnvalue.CONTRACT_NO);
	Ext.getDom('lcdpdaNo').value=Nvl(returnvalue.LCDPDA_NO);
	Ext.getDom('writeNo').value=Nvl(returnvalue.WRITE_NO);
	Ext.getDom('examRecord').value=Nvl(returnvalue.EXAM_RECORD);
	Ext.getDom('examDate').value=Nvl(returnvalue.EXAM_DATE);
	Ext.getDom('billShipDate').value=Nvl(returnvalue.BILL_SHIP_DATE);
	Ext.getDom('shouldIncomeDate').value=Nvl(returnvalue.SHOULD_INCOME_DATE);
	Ext.getDom('examBank').value=Nvl(returnvalue.BANK);
	Ext.getDom('isNegotiat').value=Nvl(returnvalue.IS_NEGOTIAT);
	Ext.getDom('total').value=Nvl(returnvalue.TOTAL);
	dict_div_currency.setComboValue(Nvl(returnvalue.CURRENCY));
}

/*
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	var sReturnMessage=customField.returnMessage;
	
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);

	if (strOperType == '3' || ('${close}'=='false' && (sReturnMessage.indexOf("保存成功")>-1 || sReturnMessage.indexOf("提交成功")>-1)))
	{
		top.ExtModalWindowUtil.markUpdated();
	    top.ExtModalWindowUtil.close();
	}	
}
*/

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';
	
	if('${save}'=='true'){Ext.fly("Submit1").hide();}
	if('${submit}'=='true'){Ext.fly("Submit2").hide();}
	if('${close}'=='true'){Ext.fly("Submit3").hide();}

	var taskType = '${taskType}';
	if (taskType=="1")
		Ext.getDom('realMoney').readOnly = false;
	
	var tabs = new Ext.TabPanel({
		id:'mainTabPanel',
        renderTo: document.body,
        autoWidth:true,
        activeTab: 0,
        loadMask:true,
        frame:true,
        defaults:{autoHeight: true},
        items:[
            {contentEl:'div_main', title: '出口押汇'},
            {contentEl:'div_history', title: '审批历史记录'}
        ]
    });

});//end of Ext.onReady   

//提示窗口
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

//清空窗口
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

</script>
</head>
<body>
<div id="div_main" class="x-hide-display">
<form id="mainForm" action="" name="mainForm">
      	<input type="hidden" id = "tradeType" name = "tradeType" value="${tradeType}"/>
      	<input type="hidden" name="isAvailable" value="${main.isAvailable }"/> 
      	<input type="hidden" name="exportBillId" value="${main.exportBillId }"/> 
		<input type="hidden" name="creatorTime" value="${main.creatorTime }"/> 
		<input type="hidden" name="creator" value="${main.creator }"/> 
		<input type="hidden" name="deptId" value="${main.deptId }"/>
<table width="100%" border="1" cellpadding="3" cellspacing="1"
	bordercolor="#6699FF" class="datatable">
	<tr>
		<td width="11%" align="right"><nobr>出口出单审单ID：<font color="red">▲</font></nobr></td>
		<td width="39%" align="left"><input type="text"
			id="exportBillExamId" name="exportBillExamId"
			value="${main.exportBillExamId}" readonly="readonly" /> <input
			type="button" value="..." onclick="selectExportBillExamWin()"></input>
			<a href="#" onclick="viewExportExamForm()">查看</a>
		</td>
		<td width="11%" align="right">出口货物通知单号：</td>
		<td width="39%"><input type="text" id="exportApplyNo" name="exportApplyNo" value="${tExportBillExam.exportApplyNo}" readonly="readonly"></td>
	</tr>
	<tr>
		<td width="11%" align="right">发票号（INV NO.）：<font color="red"></font></td>
		<td width="22%" align="left">
		<input type="text" id="invNo" name="invNo" value="${tExportBillExam.invNo}" readonly="readonly"></td>
		<td width="11%" align="right">单据类型（LC,DP,DA,TT）：</td>
		<td width="11%"><input type="text" id="billType" name="billType" value="${tExportBillExam.billType}" readonly="readonly"></td>
	</tr>
	<tr>
		<td width="11%" align="right">接单日期：<font color="red"></font></td>
		<td width="22%" align="left">
		<input type="text" id="billDate" name="billDate" value="${tExportBillExam.billDate}" readonly="readonly"></td>
		<td width="11%" align="right">合同号（S/C NO.）：</td>
		<td width="11%"><input type="text" id="contractNo" name="contractNo" value="${tExportBillExam.contractNo}" readonly="readonly"></td>
	</tr>
	<tr>
		<td width="11%" align="right">L/C,D/P,DA NO.：<font color="red"></font></td>
		<td width="22%" align="left">
		<input type="text" id="lcdpdaNo" name="lcdpdaNo" value="${tExportBillExam.lcdpdaNo}" readonly="readonly"></td>
		<td width="11%" align="right">核销单号：</td>
		<td width="11%"><input type="text" id="writeNo" name="writeNo" value="${tExportBillExam.writeNo}" readonly="readonly"></td>
	</tr>
	<tr>
		<td width="11%" align="right">审单记录：<font color="red"></font></td>
		<td width="22%" align="left">
		<input type="text" id="examRecord" name="examRecord" value="${tExportBillExam.examRecord}" readonly="readonly"></td>
		<td width="11%" align="right">出单日期：</td>
		<td width="11%"><input type="text" id="examDate" name="examDate" value="${tExportBillExam.examDate}" readonly="readonly"></td>
	</tr>
	<tr>
		<td width="11%" align="right">提单装船日：<font color="red"></font></td>
		<td width="22%" align="left">
		<input type="text" id="billShipDate" name="billShipDate" value="${tExportBillExam.billShipDate}" readonly="readonly"></td>
		<td width="11%" align="right">应收汇日：</td>
		<td width="11%"><input type="text" id="shouldIncomeDate" name="shouldIncomeDate" value="${tExportBillExam.shouldIncomeDate}" readonly="readonly"></td>
	</tr>
	<tr>
		<td width="11%" align="right">议付银行：<font color="red"></font></td>
		<td width="22%" align="left">
		<input type="text" id="examBank" name="examBank" value="${tExportBillExam.bank}" readonly="readonly"></td>
		<td width="11%" align="right">是否押汇：</td>
		<td width="11%"><input type="text" id="isNegotiat" name="isNegotiat" value="${tExportBillExam.isNegotiat}" readonly="readonly"></td>
	</tr>
	<tr>
		<td width="11%" align="right">金额：<font color="red"></font></td>
		<td width="22%" align="left">
		<input type="text" id="total" name="total" value="${tExportBillExam.total}" readonly="readonly"></td>
		<td width="11%" align="right">出口押汇单号：</td>
		<td width="11%"><input id="exportBillNo" name="exportBillNo" type="text" value="${main.exportBillNo}" readonly="readonly"></td>
	</tr>
	<tr>
		<td align="right">银行：</td>
		<td align="left"><input id="bank" name="bank" type="text" value="${main.bank}"></td>
		<td align="right">币别：</td>
		<td align="left"><div id="div_currency"></div></td>
	</tr>
	<tr>
		<td align="right">申请押汇金额：<font color="red">▲</font></td>
		<td align="left"><input id="applyMoney" name="applyMoney" type="text" value="${main.applyMoney}"></td>
		<td align="right">实际押汇金额：</td>
		<td align="left"><input id="realMoney" name="realMoney" type="text" value="${main.realMoney}" readonly="readonly"></td>
	</tr>
	<tr>
		<td align="right">备注：</td>
		<td align="left" colspan="3"><textarea name="cmd" rows="3"
			cols="80" style="height: 111px">${main.cmd }</textarea></td>
	</tr>
	<tr>
	<td align="center" colspan="4">
        	<input type="button" name="Submit1" value="保存" onclick="saveForm()"/>
			<input type="button" name="Submit2" value="提交" onclick="submitForm()"/>        	
          	<input type="button" name="Submit3" value="关闭" onclick="closeForm()"/>
	</td>
	</tr>
</table>
</form>
</div>
<div id="div_center"></div>
<div id="div_history" class="x-hide-display"></div>
<script type="text/javascript">
function saveForm(){
    if (checkValues()==false)return;
 	var param1 = Form.serialize('mainForm');
	var param = "action=updateExportBills&" + param1;
	new AjaxEngine('exportController.spr', 
	   {method:"post", parameters: param, onComplete: callBackHandle});
}
function submitForm(){
	if (checkValues()==false)return;
	var param1 = Form.serialize('mainForm');
	var param ="action=submitAndSaveExportBills&" + param1;
	new AjaxEngine('exportController.spr', 
		   {method:"post", parameters: param, onComplete: callBackHandle});
}
function checkValues()
{	
    if (Ext.getDom('exportBillExamId').value=="")
    {
    	Ext.MessageBox.alert('提示', '出口出单审单ID必填！');
    	return false;
    }
    if (Ext.getDom('applyMoney').value=="")
    {
    	Ext.MessageBox.alert('提示', '申请押汇金额必填！');
    	return false;
    }            	
    return true;	
}
var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");//{"infolion-json":{"message":"操作成功","type":"info","coustom":{"ok":"保存成功"}}}
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();	
}

//关闭窗体
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
function viewExportExamForm(){
var exportExamId = $('exportBillExamId').value;
if(exportExamId==''||exportExamId==null) return;
top.ExtModalWindowUtil.show('出口审单信息','exportController.spr?action=updateExportBillExamView&tradeType=2&isShow=1&exportBillExamId='+exportExamId,'','',{width:900,height:500});
}
</script>
</body>
</html>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${main.exportBillId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:dictionary divId="div_currency" fieldName="currency"  dictionaryName="BM_CURRENCY" width="153" selectedValue="${main.currency}" needDisplayCode="true"></fiscxd:dictionary>

