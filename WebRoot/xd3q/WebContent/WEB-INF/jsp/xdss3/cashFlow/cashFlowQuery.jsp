<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>现金流量项查询</title>
</head>
<body>
	<fisc:grid 
		   divId="CashFlow" 
		   pageSize="100" 
		   tableName="yCashFlow"
		   handlerClass="com.infolion.xdss3.cashFlow.domain.CashFlowGrid"
		   url="/xdss3/cashFlow/cashFlowController.spr?action=queryGrid"
 		   whereSql="" 
 		   needCheckBox="true"
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
					<fisc:searchHelp divId="div_bukrs_sh" searchHelpName="YHXD3QCOMPANY" boName="" boProperty="" searchType="field" hiddenName="bukrs" valueField="COMPANYID" displayField="COMANYNAME"></fisc:searchHelp>
				</td>
				<td width="20%" align="left"></div></td>
				<td width="20%" align="left"></td>
				<td width="15%" align="right">部门：</td>
				<!--  
				<td >
					<div id="div_gsber_sh"></div>
					<fisc:searchHelp divId="div_gsber_sh" searchHelpName="YHXD3QDEPT" boName="" boProperty="" searchType="field" hiddenName="gsber" valueField="DEPTID" displayField="DEPTNAME"></fisc:searchHelp>
				</td>
				<td width="20%" align="left"></td>
				-->
				<td >
					<input type="text" class="inputText" id="gsber" name="gsber" value="" readonly="readonly">
				</td>
				<td width="20%" align="left"><div id="div_gsber_bt"/></td>
				<td width="20%" align="left"></td>
			</tr>
			
			<tr>
				<td width="15%" align="right">总账科目：</td>
				
				<td  >
				<div id="div_subject_sh"></div>
					<fisc:searchHelp divId="div_subject_sh" searchHelpName="YHSKAT" boName="" boProperty="" searchType="field" hiddenName="subject" valueField="SAKNR" displayField="TXT20"></fisc:searchHelp>
				</td>
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
				
				
				<td width="15%" align="right">原因代码：</td>
				<td>
					<input type="text" class="inputText" id="rstgr" name="rstgr" value="">
				</td>
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
			</tr>
			
			<tr>
				<td width="15%" align="right">客户：</td>
				<td>
					<input type="text" class="inputText" id="customerName" name="customerName" value="" readonly="readonly">
				</td>
				<td width="20%" align="left"><div id="div_customer_bt"/></td>
				<td width="20%" align="left"></td>
				<td width="15%" align="right">供应商：</td>
				<td >
					<input type="text" class="inputText" id="vendorName" name="vendorName" value="" readonly="readonly">
				</td>
				<td width="20%" align="left"><div id="div_vendor_bt"/></td>
				<td width="20%" align="left"></td>
			</tr>
			
			<tr>
				
				<td width="15%" align="right">立项号：</td>
				<td>
					<input type="text" class="inputText" id="projectno" name="projectno" value="">
				</td>
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
				
				
				<td width="15%" align="right">过账日期： 从</td>
				<td >					
					<input type="text" id="augdt_to" name="augdt_to" value="">
					<fisc:calendar applyTo="augdt_to"  divId="" fieldName="" defaultValue="" readonly="readonly"></fisc:calendar>
				</td>				
				<td align="left" width="2px" >至</td>
				<td >					
					<input type="text" id="augdt_from" name="augdt_from" value="">
					<fisc:calendar applyTo="augdt_from"  divId="" fieldName="" defaultValue="" readonly="readonly"></fisc:calendar>
				</td>
				<td width="20%" align="left"></td>
			</tr>
			
			<!-- 隐藏域 -->
			<input type="hidden" class="inputText" id="customerNo" name="customerNo" value="">
			<input type="hidden" class="inputText" id="customerType" name="customerType" value="">
			
		</table>
	</form>
  	<div id="CashFlow" ></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/cashFlow/cashFlowQuery.js"></script>
<script type="text/javascript" defer="defer">
var context = '<%= request.getContextPath() %>';
Ext.onReady(function(){
 	// 工具栏
	var toolbars = new Ext.Toolbar({
		items:['-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_clear' ,text:'清空',handler:_clear ,iconCls:'icon-clean'} ,'-',
				{id:'_exp',text:'导出',handler:_expExcel3},'-'],
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
		            title:'现金流量项查询',
		            height:420,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'CashFlow'
				}]
			}]
    });
    viewport.doLayout();
});



</script> 
