<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年06月13日 09点36分32秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象发票(BillClearItem)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发票查看页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/billclearitem/billClearItemView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/billclearitem/billClearItemViewGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" >${vt.property.billclearid}：</td>
		<td width="30%" >
		    <input type="text" class="inputText" id="BillClearItem.billclearid" name="BillClearItem.billclearid" value="${billClearItem.billClearCollect.billclearid}" <fisc:authentication sourceName="BillClearItem.billclearid" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.contract_no}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.contract_no" name="BillClearItem.contract_no" value="${billClearItem.contract_no}" <fisc:authentication sourceName="BillClearItem.contract_no" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.export_apply_no}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.export_apply_no" name="BillClearItem.export_apply_no" value="${billClearItem.export_apply_no}" <fisc:authentication sourceName="BillClearItem.export_apply_no" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.project_no}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.project_no" name="BillClearItem.project_no" value="${billClearItem.project_no}" <fisc:authentication sourceName="BillClearItem.project_no" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.voucherno}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.voucherno" name="BillClearItem.voucherno" value="${billClearItem.voucherno}" <fisc:authentication sourceName="BillClearItem.voucherno" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.billcheckid}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.billcheckid" name="BillClearItem.billcheckid" value="${billClearItem.billcheckid}" <fisc:authentication sourceName="BillClearItem.billcheckid" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.billno}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.billno" name="BillClearItem.billno" value="${billClearItem.billno}" <fisc:authentication sourceName="BillClearItem.billno" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.old_contract_no}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.old_contract_no" name="BillClearItem.old_contract_no" value="${billClearItem.old_contract_no}" <fisc:authentication sourceName="BillClearItem.old_contract_no" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.sap_order_no}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.sap_order_no" name="BillClearItem.sap_order_no" value="${billClearItem.sap_order_no}" <fisc:authentication sourceName="BillClearItem.sap_order_no" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.currency}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.currency" name="BillClearItem.currency" value="${billClearItem.currency}" <fisc:authentication sourceName="BillClearItem.currency" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.exchangerate}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.exchangerate" name="BillClearItem.exchangerate" value="${billClearItem.exchangerate}" <fisc:authentication sourceName="BillClearItem.exchangerate" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.receivableamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.receivableamount" name="BillClearItem.receivableamount" value="${billClearItem.receivableamount}" <fisc:authentication sourceName="BillClearItem.receivableamount" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.receivabledate}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.receivabledate" name="BillClearItem.receivabledate" value="${billClearItem.receivabledate}" <fisc:authentication sourceName="BillClearItem.receivabledate" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.receivedamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.receivedamount" name="BillClearItem.receivedamount" value="${billClearItem.receivedamount}" <fisc:authentication sourceName="BillClearItem.receivedamount" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.unreceivedamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.unreceivedamount" name="BillClearItem.unreceivedamount" value="${billClearItem.unreceivedamount}" <fisc:authentication sourceName="BillClearItem.unreceivedamount" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.onroadamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.onroadamount" name="BillClearItem.onroadamount" value="${billClearItem.onroadamount}" <fisc:authentication sourceName="BillClearItem.onroadamount" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.clearbillamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.clearbillamount" name="BillClearItem.clearbillamount" value="${billClearItem.clearbillamount}" <fisc:authentication sourceName="BillClearItem.clearbillamount" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.adjustamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillClearItem.adjustamount" name="BillClearItem.adjustamount" value="${billClearItem.adjustamount}" <fisc:authentication sourceName="BillClearItem.adjustamount" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
<input type="hidden" id="BillClearItem.billclearitemid" name="BillClearItem.billclearitemid" value="">
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
							height:860.0,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
