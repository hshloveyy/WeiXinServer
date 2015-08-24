<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>供应商清账明细查询</title>
</head>
<body>

	<fisc:grid 
		   divId="UnClearSupplieBill" 
		   pageSize="10" 
		   tableName="YUNCLEARSUPPBILL"
		   handlerClass="com.infolion.xdss3.unclearQuery.domain.UnClearSupplieBillQueryGrid"
 		   whereSql="" 
 		   needCheckBox="false"
 		   height="400"
 		   needAutoLoad="false" isFixed="false">
	</fisc:grid>
	
	<div id="toolBar" style="overflow: hidden ;width: 100%"></div>
	<form id="mainForm" name="mainForm">
		<table border="0">
			<tr>
				<td width="15%" align="right">公司代码：</td>
				<td >
					<div id="div_bukrs_sh"></div>
					<fisc:searchHelp divId="div_bukrs_sh" searchHelpName="YHXD3QCOMPANY" boName="" boProperty="" searchType="field" hiddenName="BUKRS" valueField="COMPANYID" displayField="COMANYNAME"></fisc:searchHelp>
				</td>
				<td width="20%" align="left"></td>
	
				<td width="15%" align="right">业务范围：</td>
				
				<td >
					<div id="div_gsber_sh"></div>
					<fisc:searchHelp divId="div_gsber_sh" searchHelpName="YHXD3QDEPT" boName="" boProperty="" searchType="field" hiddenName="GSBER" valueField="DEPTID" displayField="DEPTNAME"></fisc:searchHelp>
				</td>
				<td align="left"></td>
				<td width="20%" align="left"></td>
			</tr>
			<tr>
				<td width="15%" align="right">供应商：</td>
				<td>
					<div id="div_supplier_sh"></div>
					
					<fisc:searchHelp divId="div_supplier_sh" searchHelpName="YHGETLIFNR" boName="" boProperty="" searchType="field" hiddenName="LIFNR" valueField="LIFNR" displayField="NAME1"></fisc:searchHelp>
				</td>
				<td width="20%" align="left"></td>
	
				<td width="15%" align="right">币别：</td>
				<td >
					<div id="div_currencytext_sh"></div>
					<fisc:searchHelp divId="div_currencytext_sh" searchHelpName="YHTCURT" boName="" boProperty="" searchType="field" hiddenName="WAERS" valueField="WEARS" displayField="KTEXT"></fisc:searchHelp>
				</td>
				<td  align="left"></td>
				<td width="20%" align="left"></td>
			</tr>
			<tr>
				<td width="15%" align="right">账务凭证号：</td>
				<td >
					<input type="text" class="inputText" id="voucherno" name="voucherno" value="">
				</td>
				<td width="20%" align="left"></td>
				
				<td width="15%" align="right">记账日期：从</td>
				<td >					
					<input type="text" id="accountdate1" name="accountdate1" value="">
					<fisc:calendar applyTo="accountdate1"  divId="" fieldName="" defaultValue="" readonly="readonly"></fisc:calendar>
				</td>				
				<td align="left" width="12px" >至</td>
				<td >					
					<input type="text" id="accountdata2" name="accountdata2" value="">
					<fisc:calendar applyTo="accountdata2"  divId="" fieldName="" defaultValue="" readonly="readonly"></fisc:calendar>
				</td>
				<td width="20%" align="left"></td>
			</tr>
		</table>
	</form>
  	<div id="UnClearSupplieBill" ></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/unclearquery/unClearSupplieBillQuery.js"></script>
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
		            title: '未清应收(借方)',
		            height:420,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'UnClearSupplieBill'
				}]
			}]
    });
    viewport.doLayout();
});




</script> 
