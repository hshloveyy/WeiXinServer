<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出仓未清收款单</title>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.QuickTips.init();
   	Ext.form.Field.prototype.msgTarget = 'side';
   	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/fam/s.gif';

	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			layout:'fit',
			contentEl:'div_center'
		}]
	});
   	                                       		
});
</script>
</head>
<body>
<div id="div_center"></div>
</body>
</html>
<fisc:grid divId="div_center" 
           boName="ReceiptCpayment"  
           editable="false" 
           needCheckBox="false" 
           needToolbar="false" 
           needAutoLoad="true"
           defaultCondition=" YRECEIPTCPAYMENT.RECEIPTDETAILID='${receiptDetailId}'"></fisc:grid>