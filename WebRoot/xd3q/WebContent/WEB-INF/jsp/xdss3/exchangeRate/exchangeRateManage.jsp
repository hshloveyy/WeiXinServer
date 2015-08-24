<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>汇率管理</title>
</head>
<body>
	<fisc:grid 
		   divId="exchangerate" 
		   pageSize="10000" 
		   tableName="YEXCHANGERATE"
		   handlerClass="com.infolion.xdss3.exchangeRate.domain.ExchangeRateGrid"
 		   whereSql="" 
 		   needCheckBox="true"
 		   height="400"
 		   needAutoLoad="false" isFixed="false">
	</fisc:grid>
	<div id="toolBar" style="overflow: hidden ;width: 100%"></div>
	<form id="mainForm" name="mainForm">
		<table border="0">
			
			
			<tr>
				
				<td width="15%" align="right">日期：从</td>
				<td >
					<input type="text" id="ratedateto" name="ratedateto" value="">
					<fisc:calendar applyTo="ratedateto"  divId="" fieldName="" defaultValue="" readonly="readonly"></fisc:calendar>
				</td>
				<td width="20%" align="left"></td>
				
				<td width="15%" align="right">到：</td>
				<td >
					<input type="text" id="ratedatefrom" name="ratedatefrom" value="">
					<fisc:calendar applyTo="ratedatefrom"  divId="" fieldName="" defaultValue="" readonly="readonly"></fisc:calendar>
				</td>
				<td width="20%" align="left"></td>
			</tr>
			<tr>
				
				<td width="15%" align="right">币别：</td>
				<td >					
					
					<div id="div_currency_sh"></div>
					<fisc:searchHelp divId="div_currency_sh" searchHelpName="YHTCURT" boName="" boProperty="" searchType="field" hiddenName="currency" valueField="WEARS" displayField="KTEXT"></fisc:searchHelp>
				</td>
				<td width="20%" align="left"></td>
				
				<td width="15%" align="right">本位币币别：</td>
				<td >
					<div id="div_currency2_sh"></div>
					<fisc:searchHelp divId="div_currency2_sh" searchHelpName="YHTCURT" boName="" boProperty="" searchType="field" hiddenName="currency2" valueField="WEARS" displayField="KTEXT" value="CNY"></fisc:searchHelp>
				</td>
				<td width="20%" align="left"></td>
				
			</tr>
		
		
			
			<!-- 隐藏域 -->
		
			
		</table>
	</form>
  	<div id="exchangerate" ></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/exchangeRate/exchangeRate.js"></script>
<script type="text/javascript" defer="defer">
var context = '<%= request.getContextPath() %>';
Ext.onReady(function(){
 	// 工具栏
	var toolbars = new Ext.Toolbar({
		items:['-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_clear' ,text:'清空',handler:_clear ,iconCls:'icon-clean'} ,'-'
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
				},{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:'汇率明细表',
		            height:420,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'exchangerate'
				}]
			}]
    });
    viewport.doLayout();
});
function _manager(value,metadata,record){
	var hrefStr = '<a href="#" style="color: green;" onclick="_view(\''+value+'\')">查看</a>  ';
		hrefStr +='<a href="#" style="color: green;" onclick="_del(\''+value+'\')">删除</a>';
	return hrefStr;
}

</script> 
