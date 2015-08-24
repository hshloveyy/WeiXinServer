<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>汇率管理</title>
</head>
<body>	
	<div id="toolBar" style="overflow: hidden ;width: 100%"></div>
	<form id="mainForm" name="mainForm">
		<table border="0">
			
			
			<tr>
				
				<td width="15%" align="right">日期：</td>
				<td >
					<input type="text" id="rate_date" name="rate_date" value="${exchangeRate.rate_date}">
					<fisc:calendar applyTo="rate_date"  divId="" fieldName="" defaultValue="" readonly="readonly"></fisc:calendar>
				</td>
				<td width="20%" align="left"></td>
				
				<td width="15%" align="right">买入价：</td>
				<td >
					<input type="text" id="buying_rate" name="buying_rate" value="${exchangeRate.buying_rate}">					
				</td>
				<td width="20%" align="left"></td>
			</tr>
				
			<tr>
				
				<td width="15%" align="right">卖出价：</td>
				<td >
					<input type="text" id="selling_rate" name="selling_rate" value="${exchangeRate.selling_rate}">
					
				</td>
				<td width="20%" align="left"></td>
				
				<td width="15%" align="right">中间价：</td>
				<td >
					<input type="text" id="middle_rate" name="middle_rate" value="${exchangeRate.middle_rate}">					
				</td>
				<td width="20%" align="left"></td>
			</tr>
			<tr>
				
				<td width="15%" align="right">币别：</td>
				<td >					
					
					<div id="div_currency_sh"></div>
					<fisc:searchHelp divId="div_currency_sh" searchHelpName="YHTCURT" boName="" boProperty="" searchType="field" hiddenName="currency" valueField="WEARS" displayField="KTEXT" value="${exchangeRate.currency}"></fisc:searchHelp>
				</td>
				<td width="20%" align="left"></td>
				
				<td width="15%" align="right">本位币币别：</td>
				<td >
					<div id="div_currency2_sh"></div>
					<fisc:searchHelp divId="div_currency2_sh" searchHelpName="YHTCURT" boName="" boProperty="" searchType="field" hiddenName="currency2" valueField="WEARS" displayField="KTEXT" value="${exchangeRate.currency2}"></fisc:searchHelp>
				</td>
				<td width="20%" align="left"></td>
				
			</tr>
		
		
			
			<!-- 隐藏域 -->
		
			
		</table>
	</form>
  	
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/exchangeRate/exchangeRate.js"></script>
<script type="text/javascript" defer="defer">
var context = '<%= request.getContextPath() %>';
Ext.onReady(function(){
 	// 工具栏
	var toolbars = new Ext.Toolbar({
		items:['-',
				{id:'_save',text:'保存',handler:_save,iconCls:'icon-save'},'-',
				{id:'_cancel' ,text:'取消',handler:_cancel ,iconCls:'icon-clean'} ,'-'
				],
		renderTo:"toolBar"
	});
	
	// 查询主体框
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[{
			region:"center",
			items:[{
					region:"north",
					contentEl:'toolBar',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					autoScroll:true,
					contentEl:'mainForm',
					bodyStyle:"background-color:#DFE8F6"
				}]
			}]
    });
    viewport.doLayout();
});


</script> 
