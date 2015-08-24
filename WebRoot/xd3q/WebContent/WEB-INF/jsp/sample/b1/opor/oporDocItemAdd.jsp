<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月26日 10点11分24秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象采购订单明细(OporDocItem)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/opor/oporDocItemAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/opor/oporDocItemAddGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" >${vt.property.itemCode}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OporDocItem.itemCode" name="OporDocItem.itemCode" value="${oporDocItem.itemCode}" <fisc:authentication sourceName="OporDocItem.itemCode" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.itemName}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OporDocItem.itemName" name="OporDocItem.itemName" value="${oporDocItem.itemName}" <fisc:authentication sourceName="OporDocItem.itemName" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.whsCode}：</td>
		<td width="30%" >
			<div id="div_whsCode_sh"></div>
			<fisc:searchHelp divId="div_whsCode_sh" boName="OporDocItem" boProperty="whsCode" value="${oporDocItem.whsCode}"></fisc:searchHelp>
		</td>
		<td width="20%" align="right" >${vt.property.vatGroup}：</td>
		<td width="30%" >
			<div id="div_vatGroup_dict"></div>
			<fisc:dictionary boName="OporDocItem" boProperty="vatGroup" dictionaryName="YDOVTG" divId="div_vatGroup_dict" isNeedAuth="false"  value="${oporDocItem.vatGroup}"   groupValue="I"  ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.unitPrice}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OporDocItem.unitPrice" name="OporDocItem.unitPrice" value="${oporDocItem.unitPrice}" <fisc:authentication sourceName="OporDocItem.unitPrice" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.quantity}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OporDocItem.quantity" name="OporDocItem.quantity" value="${oporDocItem.quantity}" <fisc:authentication sourceName="OporDocItem.quantity" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.lineTotal}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OporDocItem.lineTotal" name="OporDocItem.lineTotal" value="${oporDocItem.lineTotal}" <fisc:authentication sourceName="OporDocItem.lineTotal" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.u_qc}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="OporDocItem.u_qc" name="OporDocItem.u_qc" value="${oporDocItem.u_qc}" <fisc:authentication sourceName="OporDocItem.u_qc" taskId=""/>>
		</td>
	</tr>
<input type="hidden" id="OporDocItem.oporDocItemId" name="OporDocItem.oporDocItemId" value="">
<input type="hidden" id="OporDocItem.oporDocId" name="OporDocItem.oporDocId" value="">
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
							height:435.0,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
