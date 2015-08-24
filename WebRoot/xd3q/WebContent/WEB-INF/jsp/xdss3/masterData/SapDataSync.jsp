<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SAP主数据同步页面</title>
<link href="<%=request.getContextPath()%>/css/public.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div id="div_center"></div>
<div id="main" class="">
<form id="mainForm" method="post">
<table width="80%" border="1" cellpadding="4" cellspacing="1" bordercolor="#6699FF" class="datatable">
	<tr>
		<td colspan="2" align="center">
			<input type="button" onclick="sysAll(this)" value="同步所有"/>
		</td>
	</tr>
    <tr>
      <td width="50%" height="20" align="right">同步供应商主数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeSupplier','供应商主数据')" value="同步"/>
      </td>
    </tr>
    <tr>
      <td width="50%" height="20" align="right">同步客户主数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeCustomer','同步客户主数据')" value="同步"/>
      </td>
    </tr>
    <tr>
      <td width="50%" height="20" align="right">同步会计科目数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeHkont','同步会计科目数据')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步现金流量数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeCashFlowItem','同步现金流量数据')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步成本中心数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeCostCenter','同步成本中心数据')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步利润中心数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizePrctr','同步利润中心数据')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步开票业务数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeVbrk','同步开票业务数据')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步发票校验数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeInvoiceVerify','同步发票校验数据')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步未清供应商数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeUnclearVendor','同步未清供应商数据')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步未清客户数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeUnclearCustomer','同步未清客户数据')" value="同步"/>
      </td>
     </tr>
      <tr>
      <td width="50%" height="20" align="right">同步未清供应商数据其他公司:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeUnclearVendorOthers','同步未清供应商数据其他公司')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步未清客户数据其他公司:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeUnclearCustomerOthers','同步未清客户数据其他公司')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步公司数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeCompany','同步公司数据')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步部门数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeDept','同步部门数据')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步物料组数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeMatGroup','同步物料组数据')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步汇率数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeTcurr','同步汇率数据')" value="同步"/>
      </td>
     </tr>
     
     <tr>
      <td width="50%" height="20" align="right">同步客户未清抬头数据状态:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_syncAllCustomerTitleState','同步客户未清抬头数据状态')" value="同步"/>
      </td>
     </tr>
     <tr>
      <td width="50%" height="20" align="right">同步供应商未清抬头数据状态:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_syncAllSupplierTitleState','同步客户未清抬头数据状态')" value="同步"/>
      </td>
     </tr>
     
     <tr>
      <td width="50%" height="20" align="right">同步凭证数据:</td>
      <td align="center">
      	<input type="button" onclick="sysByname(this,'_synchronizeBseg','同步凭证数据')" value="同步"/>
      </td>
     </tr>
</table>
</form>
</div>
</body>
<script type="text/javascript">
function sysByname(btn,methodName,name){
  btn.disabled=true;
	Ext.Msg.show({
   		title:'提示',
   		msg: '请确认，是否同步'+name,
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
					Ext.Ajax.request({
					url: 'syncMasterController.spr?action=_ayncMasterData&methodName='+methodName,
					success: function(response, options){
						var txt = Ext.util.JSON.decode(response.responseText);
						var json = txt['infolion-json'];
						if(json!=null&&json.message!=null&&json.message!='同步成功!'){
								top.Ext.Msg.show({
				  						title:'警告',
				  						closable:false,
				  						msg:json.message,
				  						buttons:{yes:'关闭'},
				  						fn:Ext.emptyFn,
				  						icon:Ext.MessageBox.WARNING
								});
								btn.disabled=false;
						}else{
							showMsg("同步成功!");
							btn.disabled=false;
						}
					}
				});
   			}
   		},
   		icon: Ext.MessageBox.QUESTION
	});
}
function sysAll(btn){
btn.disabled=false;
	Ext.Msg.show({
   		title:'提示',
   		msg: '请确认，是否同步所有的数据',
   		buttons: {yes:'确定', no:'取消'},
   		fn: function(buttonId,text){
   			if(buttonId=='yes'){
					Ext.Ajax.request({
					url: 'syncMasterController.spr?action=_syncAll',
					success: function(response, options){
						var txt = Ext.util.JSON.decode(response.responseText);
						var json = txt['infolion-json'];
						if(json!=null&&json.message!=null&&json.message!='同步成功!'){
								top.Ext.Msg.show({
				  						title:'警告',
				  						closable:false,
				  						msg:json.message,
				  						buttons:{yes:'关闭'},
				  						fn:Ext.emptyFn,
				  						icon:Ext.MessageBox.WARNING
								});
								btn.disabled=false;
						}else{
							showMsg("同步成功!");
							btn.disabled=false;
						}
					}
				});
   			}
   		},
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
</html>
