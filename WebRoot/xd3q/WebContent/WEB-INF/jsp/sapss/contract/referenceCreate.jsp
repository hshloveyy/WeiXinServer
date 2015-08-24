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
<form name="mainForm" id="mainForm">
<input type="hidden" name="contractGroupId" value="${contractgroupid}">
<input type="hidden" name="tradetype" value="${tradetype}">
<input type="hidden" name="projectId" value="${projectid}">
<input type="hidden" name="projectName" value="${projectname}">
<input type="hidden" name="contractGroupNo" value="${contractgroupno}">
<table align="center">
 <tr valign="middle" height="100">
    <td><input type="radio" value="sales" name="contractType" onclick="javascript:Ext.getDom('contractType').value='sales';" checked/>销售合同
        <input type="radio" value="purcharse" name="contractType" onclick="javascript:Ext.getDom('contractType').value='purcharse';"/>采购合同</td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;合同编码：<input type="text" name="contractNo"/></td>
    <td><input type="button" value="确定" onclick="referenceCreate()"/></td>
 </tr>
</table>
</form>
<script type="text/javascript">
function BuycallbackFunction(){
$('contractNo').value='';
top.ExtModalWindowUtil.markUpdated();
}

function openContractWinByAdd(lx,contractId,contractType){
	
	if (lx == '2'){
		top.ExtModalWindowUtil.show( '采购合同信息',
			'contractController.spr?action=addPurchaseContractView&contractid='+ contractId+'&contractType='+contractType,
			'',
			BuycallbackFunction,
			{width:900,height:550});
	}

	if (lx == '1'){
		top.ExtModalWindowUtil.show('销售合同信息',
		'contractController.spr?action=addSaleContractView&contractid='+ contractId+'&contractType='+contractType,
		'',
		BuycallbackFunction,
		{width:900,height:550});
	}
}

function customCallBackHandle(transport){
    
	var responseUtil1 = new AjaxResponseUtils(transport.responseText);
	var customField = responseUtil1.getCustomField("coustom");
	top.ExtModalWindowUtil.setReturnValue(customField);
	top.ExtModalWindowUtil.markUpdated();
	//top.ExtModalWindowUtil.close();
	openContractWinByAdd(customField.contractType,customField.contractId,'${tradetype}');
}
function referenceCreate(){
    var contractNo = $('contractNo').value;
    if(contractNo=='') {
       top.ExtModalWindowUtil.alert('提示','合同编码不能为空！');
       return;
    }
    
    var contractType = $('contractType').value;
    if(contractType=='') {
       top.ExtModalWindowUtil.alert('提示','请选择合同类型！');
       return;
    }
    if(contractType=='sales') {                   
		var param = Form.serialize('mainForm');
		param += "&action=addReferenceSaleContract";
		new AjaxEngine('contractController.spr', 
		   {method:"post", parameters: param, onComplete: callBackHandle});
    }
    
    else if(contractType=='purcharse'){
        var param = Form.serialize('mainForm');
		            param += "&action=addReferencePurcharseContract";
	    new AjaxEngine('contractController.spr', 
		   {method:"post", parameters: param, onComplete: callBackHandle});
    }
}

Ext.onReady(function(){	
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
});
</script>
</body>
</html>