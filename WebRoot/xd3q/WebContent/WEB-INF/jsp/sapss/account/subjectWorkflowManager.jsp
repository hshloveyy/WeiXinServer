<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程管理页面</title>
<link href="<%=request.getContextPath()%>/css/public.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
Ext.onReady(function(){
	var mainForm = new Ext.form.FormPanel({
		renderTo:document.body,
		layout:'form',
		autoHeight:true,
		items:[{
			contentEl:'workflowAction',
			title:'流程办理',
			miniHeight:130,
			autoHeight:true,
			collapsed:false,
			collapsible: true
		},{
			html:'<iframe src="${iframeSrc}" width="900" style="height:400" id="content" ></iframe>',
			minHeight:100,
			autoHeight:true,
			title:'审批内容',
			collapsible: true
		},{
			contentEl:'workflowHistory',
			title:'流程跟踪',
			autoHeight:true,
			collapsed:true,
			collapsible: true
		},{
			contentEl:'action',
			collapsed:false,
			collapsible:false,
			height:30
		}]
	});
})
function submitMainForm(){
	var mask =Ext.getDom('workflowExamine').value;
	mask = Utils.replaceSpecialCharacter(mask);
	var workflowInfo = '&workflowLeaveTransitionName='+Ext.getDom('workflowLeaveTransitionName').value;
	workflowInfo = workflowInfo+'&workflowExamine='+mask;
	workflowInfo = workflowInfo + '&workflowCurrentTaskName='+Ext.getDom('workflowCurrentTaskName').value;
	var para1 = ${iframeForms};//
	var para = '?action=${action}&workflowTaskId=${taskId}'+workflowInfo+'&'+para1;
	if(mask!=null && mask.trim()!=''){
		Ext.Msg.show({
	   		title:'提示',
	   		msg: '您选择的下一步操作是:</br><font color="red">'+Ext.getDom('workflowLeaveTransitionName').value+'</font>,是否继续?',
	   		buttons: {yes:'确定', no:'取消'},
	   		fn: function(buttonId,text){
	 			if(buttonId=='yes'){
					new AjaxEngine('${submitUrl}',
					 {method:"post", parameters: para,onComplete: customCallBackHandle});
				}
	   		},
	   		animEl: 'elId',
	   		icon: Ext.MessageBox.QUESTION
		});
		
		
		
		}else
			showMsg('意见不能为空');
}
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	showMsg(customField.returnMessage);
	setTimeout(function(){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	},1000);	
}

function closeForm(){
	Ext.Msg.show({
   		title:'提示',
   		closable:false,
   		msg: '是否退出并关闭窗口',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
 			if(buttonId=='yes'){
 				_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
				top.ExtModalWindowUtil.markUpdated();
				top.ExtModalWindowUtil.close();
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

</script>
</head>
<body>
<form id="frm">
<div id="workflowAction" class="x-hide-display">
<fiscxd:workflow-operationBar taskInstanceId="${taskId}"></fiscxd:workflow-operationBar>
</div>

<div id="workflowHistory" style="position: relative" class="x-hide-display"></div>
<div id="action">
<table width="755" border="0" cellspacing="1" cellpadding="1">
	<tr>
		<td align="right" width="45%">
			<input type="button" onClick="submitMainForm()" value="提交审批"/>
		</td>
		<td width="10%"></td>
		<td align="left" width="45%">
			<input type="button" onClick="closeForm()" value="退出审批"/>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>
<fiscxd:workflow-taskHistory divId="workflowHistory" businessRecordId="${businessRecordId}" width="800"></fiscxd:workflow-taskHistory>
