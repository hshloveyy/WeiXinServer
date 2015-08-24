<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>贴现页面</title>
</head>
<body>
<script type="text/javascript">
	var taskType = '${taskType}';
</script>
<div id="div_main" class="x-hide-display">
<form name="mainForm">
<table width="585" border="0" cellpadding="0" cellspacing="0" class="datatable">
  <!--DWLayoutTable-->
  <tr>
    <td width="585" height="100%" valign="top"><table width="585" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF">
      <tr>
        <td><div align="right">贴现编号:</div></td>
        <td>
        	<input type="text" name="discountNo" value="${tDiscount.discountNo}" readonly="readonly"/>
        </td>
      </tr>      
      <tr>
        <td width="30%"><div align="right"><font color="red">*</font>销售合同号:</div></td>
        <td width="70%">
        	<input type="text" name="contractNo" value="${tContractSalesInfo.contractNo}" readonly="readonly"/>
        	<a href="#" onclick="viewContractSalesForm()">查看</a>
        </td>
        </tr>
      <tr>
        <td><div align="right">项目号:</div></td>
        <td>
        	<input type="text" name="projectNo" value="${tProjectInfo.projectNo}" readonly="readonly"/>
        	<a href="#" onclick="viewProjectForm()">查看</a>        	
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
        <td><input type="text" name="applyTime" value="${tDiscount.applyTime}" readonly="readonly"/></td>
     </tr>
     <tr>
        <td><div align="right">银行:</div></td>
        <td><input type="text" name="bank" value="${tDiscount.bank}" readonly="readonly" /></td>
      </tr>
     <tr>
        <td><div align="right">币别:</div></td>
        	
        <td><div id="div_currency"></div></td>
     </tr>     
     <tr>
        <td><div align="right">申请金额:</div></td>
        <td><input type="text" name="applyMoney" value="${tDiscount.applyMoney}" readonly="readonly" /></td>
     </tr>
     <tr>
        <td><div align="right">实际金额:</div></td>
        <td><input type="text" name="realMoney" value="${tDiscount.realMoney}" readonly="readonly" /></td>
     </tr> 
      <tr height="25">  
      </tr>
      <tr>
        <td colspan="2" align="center">          	
          	<input type="hidden" name="discountId" value="${tDiscount.discountId}"/>
          	<input type="hidden" name="contractSalesId" value="${tDiscount.contractSalesId}"/>          	
          	<input type="hidden" name="cmd" value="${tDiscount.cmd}"/>
          	<input type="hidden" name="deptId" value="${tDiscount.deptId}"/>
          	<input type="hidden" name="examineState" value="${tDiscount.examineState}"/>
          	<input type="hidden" name="approvedTime" value="${tDiscount.approvedTime}"/>
          	<input type="hidden" name="isAvailable" value="${tDiscount.isAvailable}"/>
          	<input type="hidden" name="creatorTime" value="${tDiscount.creatorTime}"/>
          	<input type="hidden" name="creator" value="${tDiscount.creator}"/>          	          	          	          	          	
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
            {contentEl:'div_main', title: '贴现信息'},
            {contentEl:'div_history', title: '审批历史记录'}
        ]
    }); 
       	
});
	function viewContractSalesForm(){
			top.ExtModalWindowUtil.show('查看销售合同','contractController.spr?action=archSalesInfoView&businessRecordId=${tDiscount.contractSalesId}','','',{width:800,height:500});
    }
	function viewProjectForm(){
			top.ExtModalWindowUtil.show('查看立项申请','projectController.spr?action=modify&from=view&projectId=${tProjectInfo.projectId}','','',{width:800,height:500});
    }
</script>
</html>
<fiscxd:workflow-taskHistory divId="div_history" businessRecordId="${tDiscount.discountId}" width="750"></fiscxd:workflow-taskHistory>
<fiscxd:dictionary divId="div_currency" fieldName="currency"  dictionaryName="BM_CURRENCY" width="153" selectedValue="${tDiscount.currency}" needDisplayCode="true" disable="true"></fiscxd:dictionary>
