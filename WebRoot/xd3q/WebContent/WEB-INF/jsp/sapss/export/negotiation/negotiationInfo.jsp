<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>议付页面</title>
</head>
<body>
<form name="mainForm">
<table width="585" border="0" cellpadding="0" cellspacing="0" class="datatable">
  <!--DWLayoutTable-->
  <tr>
    <td width="585" height="100%" valign="top"><table width="585" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td><div align="right">议付编号:</div></td>
        <td>
        	<input type="text" name="negotiationNo" value="${tNegotiation.negotiationNo}" readonly="readonly"/>
        </td>
      </tr>      
      <tr>
        <td width="30%"><div align="right"><font color="red">*</font>销售合同号:</div></td>
        <td width="70%">
        	<input type="text" name="contractNo" value="${tContractSalesInfo.contractNo}" readonly="readonly"/>
        	<input type="button" value="..." onclick="selectContractSalesInfo()" />
        </td>
        </tr>
      <tr>
        <td><div align="right">项目号:</div></td>
        <td>
        	<input type="text" name="projectNo" value="${tProjectInfo.projectNo}" readonly="readonly"/>
        </td>
      </tr>
      <tr>
        <td><div align="right">合同名称:</div></td>
        <td>
        	<input type="text" name="contractName" value="${tContractSalesInfo.contractName}" readonly="readonly"/>
        </td>
      </tr>  
      <tr>
        <td><div align="right">合同组:</div></td>
        <td>
        	<input type="text" name="contractGroupNo" value="${tContractGroup.contractGroupNo}" readonly="readonly"/>
        </td>
      </tr>           
      <tr>
        <td><div align="right">申请日期:</div></td>
        <td><input type="text" name="applyTime" value="${tNegotiation.applyTime}" readonly="readonly"/></td>
     </tr>
     <tr>
        <td><div align="right"><font color="red">*</font>银行:</div></td>
        <td><input type="text" name="bank" value="${tNegotiation.bank}" /></td>
      </tr>
     <tr>
        <td><div align="right"><font color="red">*</font>币别:</div></td>
        	
        <td><div id="div_currency"></div></td>
     </tr>     
     <tr>
        <td><div align="right"><font color="red">*</font>申请金额:</div></td>
        <td><input type="text" name="applyMoney" value="${tNegotiation.applyMoney}" /></td>
     </tr>

      <tr height="25">  
      </tr>
      <tr>
        <td colspan="2" align="center">
        	<input type="button" name="Submit1" value="保存" onclick="saveForm()"/>
			<input type="button" name="Submit2" value="提交" onclick="submitForm()"/>        	
          	<input type="button" name="Submit3" value="关闭" onclick="closeForm()"/></td>
          	
          	<input type="hidden" name="negotiationId" value="${tNegotiation.negotiationId}"/>
          	<input type="hidden" name="contractSalesId" value="${tNegotiation.contractSalesId}"/>          	
          	<input type="hidden" name="realMoney" value="${tNegotiation.realMoney}"/>
          	<input type="hidden" name="cmd" value="${tNegotiation.cmd}"/>
          	<input type="hidden" name="deptId" value="${tNegotiation.deptId}"/>
          	<input type="hidden" name="examineState" value="${tNegotiation.examineState}"/>
          	<input type="hidden" name="approvedTime" value="${tNegotiation.approvedTime}"/>
          	<input type="hidden" name="isAvailable" value="${tNegotiation.isAvailable}"/>
          	<input type="hidden" name="creatorTime" value="${tNegotiation.creatorTime}"/>
          	<input type="hidden" name="creator" value="${tNegotiation.creator}"/>          	          	          	          	          	
        </tr>
      
    </table>
    </td>
  </tr>
</table>	
</form>
</body>
<script type="text/javascript">
function selectContractSalesInfo(){
	top.ExtModalWindowUtil.show('查询销售合同信息',
	'exportIncomeController.spr?action=selectSalesInfo',
	'',
	selectSalesInfoCallback,
	{width:900,height:450});
}
var selectSalesInfoCallback=function(){
	var returnvalue = top.ExtModalWindowUtil.getReturnValue();
	$('contractSalesId').value = returnvalue.CONTRACT_SALES_ID;
	$('contractNo').value = returnvalue.CONTRACT_NO;
	$('contractName').value = returnvalue.CONTRACT_NAME;	
	$('projectNo').value = returnvalue.PROJECT_NO;
	$('contractGroupNo').value = returnvalue.CONTRACT_GROUP_NO;
}


function saveForm(){
	var parm="?action=save&"+Form.serialize('mainForm');
	new AjaxEngine('negotiationController.spr',
			 {method:"post", parameters: parm,onComplete: callBackHandle});
}
function submitForm(){
	var parm="?action=saveAndsubmit&"+Form.serialize('mainForm');
	new AjaxEngine('negotiationController.spr',
			 {method:"post", parameters: parm,onComplete: callBackHandle});
}
var customCallBackHandle=function(trans){
	var responseUtil1 = new AjaxResponseUtils(trans.responseText);
	var customField = responseUtil1.getCustomField("coustom");//{"infolion-json":{"message":"操作成功","type":"info","coustom":{"ok":"保存成功"}}}
	top.ExtModalWindowUtil.alert('提示',customField.ok);
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
   	
});

</script>
</html>
<fiscxd:dictionary divId="div_currency" fieldName="currency"  dictionaryName="BM_CURRENCY" width="153" selectedValue="${tNegotiation.currency}" needDisplayCode="true"></fiscxd:dictionary>
