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
var ifInWorkflow=false;

Ext.onReady(function(){
	var mainForm = new Ext.form.FormPanel({
		renderTo:document.body,
		layout:'form',
		autoHeight:true,
		items:[{
			xtype:'tabpanel',
			activeTab:0,
			items:[{
				title: '${title}',
				layout:'form',
				autoHeight:true,
		  		items:[{
					autoHeight:true,
					autoWidth:true,
					html:'<iframe src="${iframeSrc}" width="900" height="480" id="iframe"/>',
					id:'iframeItem',
					autoScroll:true,
					collapsible:true,
					collapsed:false
				}]
		     },{
		           disabled:ifInWorkflow,
		           contentEl:'workflowHistory',
		           title: '特批审批历史记录'
		     }]
		},{
			contentEl:'action',
			//hidden:true,
			collapsed:false,
			collapsible:false,
			height:30
		}]
	});
});


function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	_getMainFrame().maintabs.remove(_getMainFrame().maintabs.getActiveTab());
	top.ExtModalWindowUtil.alert('提示',customField.returnMessage);
	
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
}

function submitMainForm(){
	var parms = encodeURI('${parameters}');
		Ext.Msg.show({
	   		title:'提示',
	   		msg: '是否确定提交流程审批',
	   		buttons: {yes:'确定', no:'取消'},
	   		fn: function(buttonId,text){
	 			if(buttonId=='yes'){
					new AjaxEngine('${submitUrl}', 
					{method:"post", parameters: parms, onComplete: callBackHandle});
				}
	   		},
	   		animEl: 'elId',
	   		icon: Ext.MessageBox.QUESTION
		});
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
<form id="frm" name="frm">
	<div id="workflowHistory" style="position: relative" class="x-hide-display"></div>
	<div id="action">
		<table width="755" border="0" cellspacing="1" cellpadding="1">
			<tr>
				<td align="right" width="45%">
					<input type="button" value="提交审批" onclick="submitMainForm()"/>
				</td>
				<td width="10%"></td>
				<td align="left" width="45%">
					<input type="button" value="退出审批" onclick="closeForm()"/>
				</td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript">
var from = '${submitUrl}';
if(from=='')
	$('action').innerHTML='';
</script>
</body>
</html>
<fiscxd:workflow-taskHistory divId="workflowHistory" businessRecordId="${particularId}" width="800" 
	customeSQL="select * from (select * from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) b where b.business_record_id in (select a.original_biz_id from t_particular_workflow a where a.particular_id='${particularId}')union select * from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) c where c.business_record_id='${particularId}')d order by d.task_end_time desc"></fiscxd:workflow-taskHistory>
