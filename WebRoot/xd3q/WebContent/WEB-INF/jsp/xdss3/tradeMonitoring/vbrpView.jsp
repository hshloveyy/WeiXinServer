<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年06月07日 15点00分39秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象开票明细(Vbrp)查看页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>开票明细查看页面</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/tradeMonitoring/vbrpView.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/tradeMonitoring/vbrpViewGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" >${vt.property.posnr}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.posnr" name="Vbrp.posnr" value="${vbrp.posnr}" <fisc:authentication sourceName="Vbrp.posnr" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.matnr}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.matnr" name="Vbrp.matnr" value="${vbrp.matnr}" <fisc:authentication sourceName="Vbrp.matnr" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.bstkd}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.bstkd" name="Vbrp.bstkd" value="${vbrp.bstkd}" <fisc:authentication sourceName="Vbrp.bstkd" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.arktx}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.arktx" name="Vbrp.arktx" value="${vbrp.arktx}" <fisc:authentication sourceName="Vbrp.arktx" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.vrkme}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.vrkme" name="Vbrp.vrkme" value="${vbrp.vrkme}" <fisc:authentication sourceName="Vbrp.vrkme" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.fkimg}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.fkimg" name="Vbrp.fkimg" value="${vbrp.fkimg}" <fisc:authentication sourceName="Vbrp.fkimg" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.tax}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.tax" name="Vbrp.tax" value="${vbrp.tax}" <fisc:authentication sourceName="Vbrp.tax" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.taxin}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.taxin" name="Vbrp.taxin" value="${vbrp.taxin}" <fisc:authentication sourceName="Vbrp.taxin" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.yskr}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.yskr" name="Vbrp.yskr" value="${vbrp.yskr}" <fisc:authentication sourceName="Vbrp.yskr" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.taxout}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.taxout" name="Vbrp.taxout" value="${vbrp.taxout}" <fisc:authentication sourceName="Vbrp.taxout" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.realcollectday}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.realcollectday" name="Vbrp.realcollectday" value="${vbrp.realcollectday}" <fisc:authentication sourceName="Vbrp.realcollectday" taskId="${workflowTaskId}"/>>
		</td>
		<td width="20%" align="right" >${vt.property.shuilv}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.shuilv" name="Vbrp.shuilv" value="${vbrp.shuilv}" <fisc:authentication sourceName="Vbrp.shuilv" taskId="${workflowTaskId}"/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.aubel}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="Vbrp.aubel" name="Vbrp.aubel" value="${vbrp.aubel}" <fisc:authentication sourceName="Vbrp.aubel" taskId="${workflowTaskId}"/>>
		</td>
	      <td></td><td></td>
		</tr>
<input type="hidden" id="Vbrp.vbrpid" name="Vbrp.vbrpid" value="">
<input type="hidden" id="Vbrp.vbeln" name="Vbrp.vbeln" value="">
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

Ext.onReady(function(){
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
							height:732.5,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
});
</script>
