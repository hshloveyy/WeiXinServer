<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年07月06日 11点02分32秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象发票校验行项目(InvoiceVerifyItem)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发票校验行项目查看页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/tradeMonitoring/invoiceVerifyItemView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/tradeMonitoring/invoiceVerifyItemViewGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" >${vt.property.buzei}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.buzei" name="InvoiceVerifyItem.buzei" value="${invoiceVerifyItem.buzei}" <fisc:authentication sourceName="InvoiceVerifyItem.buzei" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.matnr}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.matnr" name="InvoiceVerifyItem.matnr" value="${invoiceVerifyItem.matnr}" <fisc:authentication sourceName="InvoiceVerifyItem.matnr" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.menge}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.menge" name="InvoiceVerifyItem.menge" value="${invoiceVerifyItem.menge}" <fisc:authentication sourceName="InvoiceVerifyItem.menge" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.bstme}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.bstme" name="InvoiceVerifyItem.bstme" value="${invoiceVerifyItem.bstme}" <fisc:authentication sourceName="InvoiceVerifyItem.bstme" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.shuilv}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.shuilv" name="InvoiceVerifyItem.shuilv" value="${invoiceVerifyItem.shuilv}" <fisc:authentication sourceName="InvoiceVerifyItem.shuilv" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.taxin}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.taxin" name="InvoiceVerifyItem.taxin" value="${invoiceVerifyItem.taxin}" <fisc:authentication sourceName="InvoiceVerifyItem.taxin" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.tax}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.tax" name="InvoiceVerifyItem.tax" value="${invoiceVerifyItem.tax}" <fisc:authentication sourceName="InvoiceVerifyItem.tax" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.taxout}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.taxout" name="InvoiceVerifyItem.taxout" value="${invoiceVerifyItem.taxout}" <fisc:authentication sourceName="InvoiceVerifyItem.taxout" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.maincode}：</td>
		<td width="30%" >
		    <input type="text" class="inputText" id="InvoiceVerifyItem.maincode" name="InvoiceVerifyItem.maincode" value="${invoiceVerifyItem.invoiceVerify.maincode}" <fisc:authentication sourceName="InvoiceVerifyItem.maincode" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.lifnr}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.lifnr" name="InvoiceVerifyItem.lifnr" value="${invoiceVerifyItem.lifnr}" <fisc:authentication sourceName="InvoiceVerifyItem.lifnr" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.yfkr}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.yfkr" name="InvoiceVerifyItem.yfkr" value="${invoiceVerifyItem.yfkr}" <fisc:authentication sourceName="InvoiceVerifyItem.yfkr" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.ydhr}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.ydhr" name="InvoiceVerifyItem.ydhr" value="${invoiceVerifyItem.ydhr}" <fisc:authentication sourceName="InvoiceVerifyItem.ydhr" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.realrecivday}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.realrecivday" name="InvoiceVerifyItem.realrecivday" value="${invoiceVerifyItem.realrecivday}" <fisc:authentication sourceName="InvoiceVerifyItem.realrecivday" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.realpayday}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="InvoiceVerifyItem.realpayday" name="InvoiceVerifyItem.realpayday" value="${invoiceVerifyItem.realpayday}" <fisc:authentication sourceName="InvoiceVerifyItem.realpayday" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
<input type="hidden" id="InvoiceVerifyItem.code" name="InvoiceVerifyItem.code" value="">
</table>
</form>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">

//页面文本
var Txt={
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 取消
	cancel:'${vt.sCancel}'
};

var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					{id:'_cancel',text:'${vt.sCancel}',handler:_cancel,iconCls:'icon-undo'},'-',
					' '
				   ],
			renderTo:'div_toolbar'
});
/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[{
					region:'north',
					layout:'fit',
					height:26,
					border:false,
					contentEl:'div_toolbar'
	   		   },{
					region:'center',
					border:false,
					buttonAlign:'center',
					items:[{
							layout:'fit',
							region:'center',
							height:690.0,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
