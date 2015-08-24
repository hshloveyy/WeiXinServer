<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>打包贷款页面</title>
</head>
<body>
<script type="text/javascript">
	var taskType = '${taskType}';
</script>
<div id="div_main" class="x-hide-display">
<form name="mainForm">
<table width="585" border="0" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td width="585" height="100%" valign="top"><table width="585" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td><div align="right">打包贷款编号:</div></td>
        <td>
        	<input type="text" name="baleLoanNo" value="${tBaleLoan.baleLoanNo}" readonly="readonly"/>
        </td>
      </tr>       
      <tr>
        <td width="30%"><div align="right">信用证号码:</div></td>
        <td width="70%">
        	<input type="text" name="creditNo" value="${tCreditInfo.creditNo}" readonly="readonly"/>
        	<a href="#" onclick="viewCreditForm()">查看</a>
        </td>
        </tr>
      <tr>
        <td><div align="right">到证日期:</div></td>
        <td>
        	<input type="text" name="creditRecDate" value="${tCreditInfo.creditRecDate}" readonly="readonly"/>
        </td>
      </tr>
      <tr>
        <td><div align="right">开证行:</div></td>
        <td>
        	<input type="text" name="createBank" value="${tCreditInfo.createBank}" readonly="readonly"/>
        </td>
      </tr>  
      <tr>
        <td><div align="right">关联合同号:</div></td>
        <td>
        	<input type="text" name="contractNo" value="${tCreditInfo.contractNo}" readonly="readonly"/>
        </td>
      </tr>           
      <tr>
        <td><div align="right">发票号:</div></td>
        <td><input type="text" name="invoice" value="${tCreditInfo.invoice}" readonly="readonly" /></td>
      </tr>
      <tr>
        <td><div align="right">申请日期:</div></td>
        <td><input type="text" name="applyTime" value="${tBaleLoan.applyTime}" readonly="readonly"/></td>
     </tr>
     <tr>
        <td><div align="right">银行:</div></td>
        <td><input type="text" name="bank" value="${tBaleLoan.bank}" readonly="readonly" /></td>
     </tr>
     <tr>
        <td><div align="right">币别:</div></td>
        	
        <td><div id="div_currency"></div></td>
     </tr>     
     <tr>
        <td><div align="right">申请金额:</div></td>
        <td><input type="text" name="applyMoney" value="${tBaleLoan.applyMoney}" readonly="readonly" /></td>
     </tr>
     <tr>
        <td><div align="right">实际金额:</div></td>
        <td><input type="text" name="realMoney" value="${tBaleLoan.realMoney}" readonly="readonly" /></td>
     </tr>     

      <tr height="25">  
      </tr>
      <tr>
        <td colspan="2" align="center">     	
          	<input type="hidden" name="baleLoanId" value="${tBaleLoan.baleLoanId}"/>
          	<input type="hidden" name="creditId" value="${tBaleLoan.creditId}"/>          	
          	<input type="hidden" name="cmd" value="${tBaleLoan.cmd}"/>
          	<input type="hidden" name="deptId" value="${tBaleLoan.deptId}"/>
          	<input type="hidden" name="examineState" value="${tBaleLoan.examineState}"/>
          	<input type="hidden" name="approvedTime" value="${tBaleLoan.approvedTime}"/>
          	<input type="hidden" name="isAvailable" value="${tBaleLoan.isAvailable}"/>
          	<input type="hidden" name="creatorTime" value="${tBaleLoan.creatorTime}"/>
          	<input type="hidden" name="creator" value="${tBaleLoan.creator}"/>    
          </td>	      	          	          	          	          	
        </tr>
      
    </table>
    </td>
  </tr>
</table>	
</form>
</div>
<div id="div_history" class="x-hide-display"></div>
</body>
<script type="text/javascript">

Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%= request.getContextPath() %>/images/fam/s.gif';
   	
    if(taskType=="1") //填写实际金额
    {
        Ext.getDom("realMoney").readOnly = false;
    }
 	var tabs = new Ext.TabPanel({
		id:'mainTabPanel',
        renderTo: document.body,
        autoWidth:true,
        activeTab: 0,
        loadMask:true,
        frame:true,
        defaults:{autoHeight: true},
        items:[
            {contentEl:'div_main', title: '打包贷款信息'},
            {contentEl:'div_history', title: '审批历史记录'}
        ]
    });  	
	
   	
});

	function viewCreditForm(){
			top.ExtModalWindowUtil.show('查看信用证到证信息','creditArriveController.spr?action=modify&creditId=${tBaleLoan.creditId}','','',{width:800,height:500});
	   }
</script>
</html>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${tBaleLoan.baleLoanId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:dictionary divId="div_currency" fieldName="currency"  dictionaryName="BM_CURRENCY" width="153" selectedValue="${tBaleLoan.currency}" needDisplayCode="true" disable="true" ></fiscxd:dictionary>
