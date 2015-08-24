<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保证金退回</title>
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


//贸易名称
var strshipTitle ='';

var Enabled='false';
if ('${submit}'!='true')Enabled='submit';
if ('${save}'!='true')Enabled='save';




function Nvl(str) 
{
	if (str=="null"||str==null)	return "";
 	else return str;
 }

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';
	
	if('${save}'=='true'){Ext.fly("Submit1").hide();}
	if('${submit}'=='true'){Ext.fly("Submit2").hide();}
	if('${close}'=='true'){Ext.fly("Submit3").hide();}

	var taskType = '${taskType}';

	
	var tabs = new Ext.TabPanel({
		id:'mainTabPanel',
        renderTo: document.body,
        autoWidth:true,
        activeTab: 0,
        loadMask:true,
        frame:true,
        defaults:{autoHeight: true},
        items:[
            {contentEl:'div_main', title: '保证金退回'},
            {contentEl:'div_history', title: '审批历史记录'}
        ]
    });
    var applyDate = new Ext.form.DateField({
   		format:'Y-m-d',
		name:"applyDate",
		id:"applyDate",
		width: 155,
		readOnly:false,
		applyTo:'applyDate'
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
      	<input type="hidden" name="depositRetreatId" value="${main.depositRetreatId }"/> 
      	<input type="hidden" name="applyTime" value="${main.applyTime }"/> 
		<input type="hidden" name="applyedTime" value="${main.applyedTime }"/> 
		<input type="hidden" name="examState" value="${main.examState }"/> 
		<input type="hidden" name="creator" value="${main.creator }"/>
		<input type="hidden" name="deptId" value="${main.deptId }"/>
		<input type="hidden" name="createrTime" value="${main.createrTime }"/>
		<input type="hidden" name="isValid" value="${main.isValid }"/>
<table width="100%" border="1" cellpadding="3" cellspacing="1"
	bordercolor="#6699FF" class="datatable">
	<tr>
		<td align="right">申请人：<font color="red">▲</font></td>
		<td align="left">
		    <c:if test="${empty(main.depositRetreatId)}">
		    <input type="text"	id="applyer" name="applyer"	value="${realName}" readonly="readonly"/>
		    </c:if>
		    <c:if test="${!empty(main.depositRetreatId)}">
		    <input type="text"	id="applyer" name="applyer"	value="${main.applyer}" readonly="readonly"/>
		    </c:if>
		    
		</td>
		<td></td>
    </tr>
    <tr>
		<td align="right">申请时间：<font color="red">▲</font></td>
		<td align="left"><input type="text"	id="applyDate" name="applyDate"	value="${main.applyDate}"/></td>
		<td></td>
    </tr>
    <tr>
		<td align="right">申请金额：<font color="red">▲</font></td>
		<td align="left"><input type="text"	id="applySun" name="applySun"	value="${main.applySun}"/></td>
		<td></td>
    </tr>
    <tr>
		<td align="right">申请期货账户：<font color="red">▲</font></td>
		<td align="left"><div id="div_applyAccount"></div></td>
		<td></td>
    </tr>
    <tr>
		<td align="right">收款账户：<font color="red">▲</font></td>
		<td align="left"><div id="div_receiptAccount"></div></td>
		<td></td>
    </tr>
	<tr>
		<td align="right">备注：</td>
		<td align="left" colspan="2"><textarea name="cmd" rows="3" cols="70">${main.cmd }</textarea></td>
	</tr>
	<tr>
	<td align="center" colspan="3">
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
	var param = "action=updateDepositRetreat&" + param1;
	new AjaxEngine('depositRetreatController.spr', 
	   {method:"post", parameters: param, onComplete: callBackHandle});
}
function submitForm(){
	if (checkValues()==false)return;
	var param1 = Form.serialize('mainForm');
	var param ="action=submitAndSaveDepositRetreat&" + param1;
	new AjaxEngine('depositRetreatController.spr', 
		   {method:"post", parameters: param, onComplete: callBackHandle});
}
function checkValues()
{	
   /* if (Ext.getDom('exportBillExamId').value=="")
    {
    	Ext.MessageBox.alert('提示', '出口出单审单ID必填！');
    	return false;
    }
    if (Ext.getDom('applyMoney').value=="")
    {
    	Ext.MessageBox.alert('提示', '申请押汇金额必填！');
    	return false;
    } */          	
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
</script>
</body>
</html>
<fiscxd:dictionary divId="div_receiptAccount" fieldName="receiptAccount" dictionaryName="BM_DEPOSIT_RECEIPT_ACCOUNT" width="250" selectedValue="${main.receiptAccount}"></fiscxd:dictionary>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${main.depositRetreatId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:dictionary divId="div_applyAccount" fieldName="applyAccount" dictionaryName="BM_DEPOSIT_ACCOUNT" width="250" selectedValue="${main.applyAccount}"></fiscxd:dictionary>


