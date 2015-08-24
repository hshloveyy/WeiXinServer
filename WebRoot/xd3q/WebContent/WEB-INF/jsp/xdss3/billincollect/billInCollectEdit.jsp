<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年06月13日 09点36分32秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象未清预收款(BillInCollect)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/billincollect/billInCollectEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/billincollect/billInCollectEditGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" >${vt.property.billclearid}：</td>
		<td width="30%" >
		    <input type="text" class="inputText" id="BillInCollect.billclearid" name="BillInCollect.billclearid" value="${billInCollect.billClearCollect.billclearid}" <fisc:authentication sourceName="BillInCollect.billclearid" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.billclearitemid}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.billclearitemid" name="BillInCollect.billclearitemid" value="${billInCollect.billclearitemid}" <fisc:authentication sourceName="BillInCollect.billclearitemid" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.collectitemid}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.collectitemid" name="BillInCollect.collectitemid" value="${billInCollect.collectitemid}" <fisc:authentication sourceName="BillInCollect.collectitemid" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.voucherno}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.voucherno" name="BillInCollect.voucherno" value="${billInCollect.voucherno}" <fisc:authentication sourceName="BillInCollect.voucherno" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.project_no}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.project_no" name="BillInCollect.project_no" value="${billInCollect.project_no}" <fisc:authentication sourceName="BillInCollect.project_no" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.contract_no}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.contract_no" name="BillInCollect.contract_no" value="${billInCollect.contract_no}" <fisc:authentication sourceName="BillInCollect.contract_no" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.collectno}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.collectno" name="BillInCollect.collectno" value="${billInCollect.collectno}" <fisc:authentication sourceName="BillInCollect.collectno" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.sendgoodsdate}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.sendgoodsdate" name="BillInCollect.sendgoodsdate" value="${billInCollect.sendgoodsdate}" <fisc:authentication sourceName="BillInCollect.sendgoodsdate" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.collectamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.collectamount" name="BillInCollect.collectamount" value="${billInCollect.collectamount}" <fisc:authentication sourceName="BillInCollect.collectamount" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.currency}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.currency" name="BillInCollect.currency" value="${billInCollect.currency}" <fisc:authentication sourceName="BillInCollect.currency" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.exchangerate}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.exchangerate" name="BillInCollect.exchangerate" value="${billInCollect.exchangerate}" <fisc:authentication sourceName="BillInCollect.exchangerate" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.offsetamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.offsetamount" name="BillInCollect.offsetamount" value="${billInCollect.offsetamount}" <fisc:authentication sourceName="BillInCollect.offsetamount" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.unoffsetamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.unoffsetamount" name="BillInCollect.unoffsetamount" value="${billInCollect.unoffsetamount}" <fisc:authentication sourceName="BillInCollect.unoffsetamount" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.onroadamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.onroadamount" name="BillInCollect.onroadamount" value="${billInCollect.onroadamount}" <fisc:authentication sourceName="BillInCollect.onroadamount" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.nowclearamount}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BillInCollect.nowclearamount" name="BillInCollect.nowclearamount" value="${billInCollect.nowclearamount}" <fisc:authentication sourceName="BillInCollect.nowclearamount" taskId=""/>>
		</td>
	      <td></td><td></td>
		</tr>
<input type="hidden" id="BillInCollect.billincollectid" name="BillInCollect.billincollectid" value="">
</table>
</form>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//页面文本
var Txt={
	// '提交成功！'
	submitSuccess:'${vt.submitSuccess}',
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}'
};

var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					{id:'_save',text:'${vt.sOk}',handler:_save,iconCls:'icon-table-save'},'-',
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
							height:775.0,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
