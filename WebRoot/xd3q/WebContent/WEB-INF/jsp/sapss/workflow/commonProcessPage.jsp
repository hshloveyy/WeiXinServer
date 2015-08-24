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
var src = '${iframeSrc}';
Ext.onReady(function(){
	var mainForm = new Ext.form.FormPanel({
		renderTo:document.body,
		layout:'form',
		autoHeight:true,
		items:[{
			contentEl:'workflowAction',
			title:'流程办理',
			height:105,
			collapsed:false,
			collapsible: true
		},{
			html:'<iframe src='+src+' width="99%" style="height:450" id="content" ></iframe>',
			minHeight:100,
			autoHeight:true,
			title:'审批内容',
			autoScroll:true,
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
	
  
    var btnclose = new Ext.Button(
	    {
	     	text:'退出审批',
	     	id:'btnclose',
	     	name:'btnclose',
	     	handler:closeForm,
	     	renderTo:'div-close'
	    }
    )
    
})


function customCallBackHandle(transport){
    var para = '?action=checkSpecil&businessId=${businessRecordId}&type=1';
    new AjaxEngine('showProcessImgController.spr', 
							   {method:"post", parameters: para, onComplete: speckBackHandle});
}

function speckBackHandle(transport){
    var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	if(customField.returnMessage>0){
	  submitMainForm(customField.returnMessage);
	}else {
	top.ExtModalWindowUtil.alert('提示','提交成功');
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
	}
}

function submitMainForm(ti){
	if (Ext.getDom('workflowExamine').value.trim() != ''){
		var mask =Ext.getDom('workflowExamine').value;
		var workflowInfo = '&workflowLeaveTransitionName='+Ext.getDom('workflowLeaveTransitionName').value;
		workflowInfo = workflowInfo+'&workflowExamine='+Utils.replaceSpecialCharacter(mask);;

		var workflowCurrentTaskName = Ext.getDom('workflowCurrentTaskName').value;
		var param1 = Form.serialize(window.frames['content']['mainForm']);
		var param2 ;
		try{
			param2 = Form.serialize(window.frames['content']['headForm']);
		}catch(e){
			param2 = '';
		}
		var taskId = ${taskId};
	    if(ti>0) taskId =ti;
		var param = '&workflowTaskId='+taskId+'&workflowCurrentTaskName='+workflowCurrentTaskName+workflowInfo + '&' + param1+"&"+param2;
		if(ti==null){
		Ext.Msg.show({
	   		title:'提示',
	   		msg: '您选择的下一步操作是:</br><font color="red">'+Ext.getDom('workflowLeaveTransitionName').value+'</font>,是否继续?',
	   		buttons: {yes:'确定', no:'取消'},
	   		fn: function(buttonId,text){
	 			if(buttonId=='yes'){
					new AjaxEngine('${submitUrl}', 
					{method:"post", parameters: param, onComplete: callBackHandle});
				}
	   		},
	   		animEl: 'elId',
	   		icon: Ext.MessageBox.QUESTION
		});
	    }else {
					new AjaxEngine('${submitUrl}', 
					{method:"post", parameters: param, onComplete: callBackHandle});
		}
	}else{
		Ext.MessageBox.alert('提示', '请输入审批意见！');
	}
}
function closeForm(){
	Ext.Msg.show({
   		title:'提示',
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
function printHandle(){
   window.open('${printUrl}','_blank','location=no,resizable=yes');
}
function printShipHandle(){
window.open('${shipPrintUrl}','_blank','height=30, width=400, toolbar= no, menubar=no, scrollbars=no, resizable=no, location=no, status=no,top=100,left=300');
}
</script>
</head>
<body>
<form id="frm" name="frm">
	<div id="workflowAction" class="x-hide-display">
	<fiscxd:workflow-operationBar taskInstanceId="${taskId}"></fiscxd:workflow-operationBar>
	</div>

	<div id="workflowHistory" style="position: relative" class="x-hide-display"></div>
	<div id="action">
		<table width="755" border="0" cellspacing="1" cellpadding="1">
			<tr>
				<td align="right" width="45%">
					<input type="button" onclick="submitMainForm()" value="提交审批"/>
				</td>
				<td width="10%"></td>
				<td align="left" width="45%">
					<div id="div-close" name="div-close"></div>
				</td>
				<td align="left">
				<c:if test="${isPrintHidden=='false'}">
					<input type="button" value="打印" onclick="printHandle()">
				</c:if>
				<c:if test="${isShipPrintHidden=='false'}">
					<input type="button" value="打印" onclick="printShipHandle()">
				</c:if>
				</td>
			</tr>
		</table>
	</div>
</form>

</body>
</html>
<fiscxd:workflow-taskHistory divId="workflowHistory" businessRecordId="${businessRecordId}" width="800" url="${url}"></fiscxd:workflow-taskHistory>
