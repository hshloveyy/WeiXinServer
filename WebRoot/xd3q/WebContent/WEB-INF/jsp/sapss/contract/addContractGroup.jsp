<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="" id="contractGroupForm" name="contractGroupForm">
<table width="100%">
<tr>
	<td align="right">项目名称：</td>
	<td>
	<input type="text" id="projectName" name="projectName" value=""></input>
	<input type="hidden" id="projectId" name="projectId" value=""></input>
	<input type="hidden" id="projectNo" name="projectNo" value=""></input>
	<input type="button" value="..." onclick="selectProjectWin()"></input>
	</td>
</tr>
<tr>
	<td align="right">合同组名称：</td>
	<td>
	<input type="text" id="contractGroupName" name="contractGroupName" value=""></input>
	</td>
	<input type="hidden" id="contractGroupNo" name="contractGroupNo" value="" readonly="readonly"></input>
</tr>
<tr>
	<td align="right">备注：</td>
	<td>
	<input type="text" id="cmd" name="cmd" value=""></input>
	<input type="hidden" id="tradeType" name="tradeType" value="${tradeType}"></input>
	</td>
</tr>
<tr>
	<td align="center" colspan="2">
	<input type="button" value="确认" onclick="addContractGroup()"></input>
	<input type="button" value="关闭" onclick="closeContractGriup()"></input>
	</td>
</tr>
</table>
</form>
</body>
</html>
<script type="text/javascript">
function closeContractGriup(){
	top.ExtModalWindowUtil.close();
}

function selectProjectInfoCallBack(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();

	document.contractGroupForm.projectName.value = returnvalue.PROJECT_NAME;
	document.contractGroupForm.projectId.value = returnvalue.PROJECT_ID;
	document.contractGroupForm.projectNo.value = returnvalue.PROJECT_NO;
	/*var requesturl = 'contractController.spr?action=getContractGroupCount&projectid=' + returnvalue.PROJECT_ID + '&projectno='+returnvalue.PROJECT_NO;

	Ext.Ajax.request({
		url:requesturl,
		success: function(response, options){
			var responseArray = Ext.util.JSON.decode(response.responseText);

			document.contractGroupForm.contractGroupNo.value = responseArray.count;
		},
		failure:function(response, options){
			top.ExtModalWindowUtil.alert('提示','与服务器交付失败请重新再试！');
		}
	});*/
}

function selectProjectWin(){
	top.ExtModalWindowUtil.show('查询立项信息',
	'contractController.spr?action=selectProjrctInfo&tradeType=${tradeType}',
	'',
	selectProjectInfoCallBack,
	{width:560,height:300});
}

function addContractGroup(){
	if($('projectId').value=='' || $('projectNo').value==''){
		top.Ext.Msg.alert('提示','请选择关联的立项');
	}else{
		var param = Form.serialize('contractGroupForm');
		param += "&action=addContractGroup";
		new AjaxEngine('contractController.spr', 
		   {method:"post", parameters: param, onComplete: callBackHandle});
	}
}

function customCallBackHandle(transport){
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	
	top.ExtModalWindowUtil.setReturnValue(customField);
	top.ExtModalWindowUtil.markUpdated();
	top.ExtModalWindowUtil.close();
}

Ext.onReady(function(){	
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
});
</script>