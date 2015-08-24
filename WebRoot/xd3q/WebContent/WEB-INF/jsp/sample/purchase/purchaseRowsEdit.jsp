<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年01月09日 11点03分54秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象采购订单行项目信息(SAP)(PurchaseRows)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchaseRowsEdit.js"></script>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/sample/purchase/purchaseRowsEditGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" >${vt.property.pstyp}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.pstyp" name="PurchaseRows.pstyp" value="${purchaseRows.pstyp}" <fisc:authentication sourceName="PurchaseRows.pstyp" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.matnr}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.matnr" name="PurchaseRows.matnr" value="${purchaseRows.matnr}" <fisc:authentication sourceName="PurchaseRows.matnr" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.txzo1}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.txzo1" name="PurchaseRows.txzo1" value="${purchaseRows.txzo1}" <fisc:authentication sourceName="PurchaseRows.txzo1" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.meins}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.meins" name="PurchaseRows.meins" value="${purchaseRows.meins}" <fisc:authentication sourceName="PurchaseRows.meins" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.menge}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.menge" name="PurchaseRows.menge" value="${purchaseRows.menge}" <fisc:authentication sourceName="PurchaseRows.menge" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.netpr}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.netpr" name="PurchaseRows.netpr" value="${purchaseRows.netpr}" <fisc:authentication sourceName="PurchaseRows.netpr" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.bprme}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.bprme" name="PurchaseRows.bprme" value="${purchaseRows.bprme}" <fisc:authentication sourceName="PurchaseRows.bprme" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.peinh}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.peinh" name="PurchaseRows.peinh" value="${purchaseRows.peinh}" <fisc:authentication sourceName="PurchaseRows.peinh" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.werks}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.werks" name="PurchaseRows.werks" value="${purchaseRows.werks}" <fisc:authentication sourceName="PurchaseRows.werks" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.werksName}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.werksName" name="PurchaseRows.werksName" value="${purchaseRows.werksName}" <fisc:authentication sourceName="PurchaseRows.werksName" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.eindt}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.eindt" name="PurchaseRows.eindt" value="${purchaseRows.eindt}" <fisc:authentication sourceName="PurchaseRows.eindt" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.webre}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.webre" name="PurchaseRows.webre" value="${purchaseRows.webre}" <fisc:authentication sourceName="PurchaseRows.webre" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.mwskz}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.mwskz" name="PurchaseRows.mwskz" value="${purchaseRows.mwskz}" <fisc:authentication sourceName="PurchaseRows.mwskz" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.mwskzName}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.mwskzName" name="PurchaseRows.mwskzName" value="${purchaseRows.mwskzName}" <fisc:authentication sourceName="PurchaseRows.mwskzName" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.factorylocal}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.factorylocal" name="PurchaseRows.factorylocal" value="${purchaseRows.factorylocal}" <fisc:authentication sourceName="PurchaseRows.factorylocal" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.totalValue}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.totalValue" name="PurchaseRows.totalValue" value="${purchaseRows.totalValue}" <fisc:authentication sourceName="PurchaseRows.totalValue" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.price}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.price" name="PurchaseRows.price" value="${purchaseRows.price}" <fisc:authentication sourceName="PurchaseRows.price" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.creator}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.creator" name="PurchaseRows.creator" value="${purchaseRows.creator}" <fisc:authentication sourceName="PurchaseRows.creator" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.createTime}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.createTime" name="PurchaseRows.createTime" value="${purchaseRows.createTime}" <fisc:authentication sourceName="PurchaseRows.createTime" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.lastmodifyer}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.lastmodifyer" name="PurchaseRows.lastmodifyer" value="${purchaseRows.lastmodifyer}" <fisc:authentication sourceName="PurchaseRows.lastmodifyer" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.lastmodifyTime}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.lastmodifyTime" name="PurchaseRows.lastmodifyTime" value="${purchaseRows.lastmodifyTime}" <fisc:authentication sourceName="PurchaseRows.lastmodifyTime" taskId=""/>>
		</td>
		<td width="20%" align="right" >${vt.property.purchaseinfoId}：</td>
		<td width="30%" >
		    <input type="text" class="inputText" id="PurchaseRows.purchaseinfoId" name="PurchaseRows.purchaseinfoId" value="${purchaseRows.purchaseInfo.purchaseinfoId}" <fisc:authentication sourceName="PurchaseRows.purchaseinfoId" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.sapRowNo}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="PurchaseRows.sapRowNo" name="PurchaseRows.sapRowNo" value="${purchaseRows.sapRowNo}" <fisc:authentication sourceName="PurchaseRows.sapRowNo" taskId=""/>>
		</td>
	      <td></td><td></td>
		</tr>
<input type="hidden" id="PurchaseRows.lpein" name="PurchaseRows.lpein" value="">
<input type="hidden" id="PurchaseRows.convnum2" name="PurchaseRows.convnum2" value="">
<input type="hidden" id="PurchaseRows.convnum1" name="PurchaseRows.convnum1" value="">
<input type="hidden" id="PurchaseRows.purchaserowsId" name="PurchaseRows.purchaserowsId" value="">
</table>
</form>
</div>

<div id="div_east" class="x-hide-display"></div>
<div id="div_west" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript">
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

/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[{
			region:'center',
			border:false,
			buttonAlign:'center',
			items:[{
					layout:'fit',
					region:'center',
					height:1200,
					border:false,
					bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_center'
				}],
			buttons:[{
				        	text:Txt.ok,
				        	name:'btn_save',
				        	minWidth: 40,
				        	handler:_save
				     },
				     {
				        	text:Txt.cancel,
				        	minWidth: 40,
				        	name:'btn_cancel',
				        	handler:_cancel
				}]
		}]
	});
//});
</script>
