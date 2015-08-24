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
			minHeight:130,
			autoHeight:true,
			collapsed:false,
			collapsible: true
		},{
			html:'<iframe src='+src+' width="800" style="height:400" id="content" ></iframe>',
			minHeight:100,
			autoHeight:true,
			title:'${tradeType}审批内容',
			collapsible: true
		},{
			contentEl:'action',
			collapsed:false,
			collapsible:false,
			height:30
		}]
	});
})
function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");

	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	
	setTimeout(function(){
		_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
		top.ExtModalWindowUtil.markUpdated();
		top.ExtModalWindowUtil.close();
	},1000);	
}

function submitMainForm(){
	if (Ext.getDom('workflowExamine').value.trim() != ''){
		workflowInfo = '&workflowExamine='+Ext.getDom('workflowExamine').value.trim();
		var param = '?action=seniorSubmitSalesInfo'+workflowInfo+'&businessRecordId=${businessRecordId}&person=${person}';
		Ext.Msg.show({
	   		title:'提示',
	   		msg: '提交审批?',
	   		buttons: {yes:'确定', no:'取消'},
	   		fn: function(buttonId,text){
	 			if(buttonId=='yes'){
					new AjaxEngine('contractController.spr', 
					{method:"post", parameters: param, onComplete: callBackHandle});
				}
	   		},
	   		animEl: 'elId',
	   		icon: Ext.MessageBox.QUESTION
		});
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

</script>
</head>
<body>
<form id="frm">
<div id="workflowAction" class="x-hide-display">
	审批意见:<br>
	<textarea rows="2" cols="80" name="workflowExamine" style="width:90%;overflow-y:visible;word-break:break-all;"></textarea>
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
			<input type="button" onclick="closeForm()" value="退出审批"/>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>
