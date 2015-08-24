<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>kpi</title>
</head>
<body>
	<fisc:grid 
		   divId="bankAcceptance" 
		   pageSize="10000" 
		   tableName="yvoucheritem"		  
		   handlerClass="com.infolion.xdss3.kpi.domain.BankAcceptanceGrid"
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
					<fisc:searchHelp divId="div_bukrs_sh" searchHelpName="YHXD3QCOMPANY" boName="" boProperty="" searchType="field" hiddenName="p_bukrs" valueField="COMPANYID" displayField="COMANYNAME"></fisc:searchHelp>
				</td>
				
					<td width="15%" align="right">部门：</td>
				 
				<td >
					<div id="div_gsber_sh"></div>
					<fisc:searchHelp divId="div_gsber_sh" searchHelpName="YHXD3QDEPT" boName="" boProperty="" searchType="field" hiddenName="p_gsber" valueField="DEPTID" displayField="DEPTNAME"></fisc:searchHelp>
				</td>
				
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
			</tr>
			<tr>
				<td width="15%" align="right">过账日期：</td>
				<td >
					<input type="text" class="inputText" id="p_checkdate" name="p_checkdate" value="" >
					<fisc:calendar applyTo="p_checkdate"  divId="" fieldName="" defaultValue="" readonly="readonly"></fisc:calendar>
				</td>
				
				<td width="20%" align="right"></td>
				<td width="15%" align="right">				
				</td>
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
				<td width="20%" align="left"></td>
			</tr>
		
			<tr>
				<td  align="left" colspan="2" > </td>
				
				
				<td width="15%" align="right"><div style="color:#F00">   	部门KPI 单位为：人民币（元）</div></td>				 
				<td >
				
				</td>				
				<td width="20%" align="left"></td>
				<td width="20%" align="left">	</td>
				<td width="20%" align="left"></td>
			</tr>
			
			<tr>
				<td width="15%" align="right"> SAP日平均额度占用：</td>
				<td >
					<div id="sap_pjzy" style="color:#F00"></div>					
				</td>
				
				<td width="15%" align="right">SAP部门周转率：</td>				 
				<td >
				<div id="sap_zzl" style="color:#F00"></div>	
				</td>				
				<td width="20%" align="left">SAP部门净利率:</td>
				<td width="20%" align="left"><div id="sap_jll" style="color:#F00"></div>	</td>
				<td width="20%" align="left"></td>
			</tr>
			<tr>
				<td width="15%" align="right"> 日平均额度占用：</td>
				<td >
				<div id="pjzy" style="color:#F00"></div>	
				</td>				
					<td width="15%" align="right">部门周转率：</td>				 
				<td >	
				<div id="zzl" style="color:#F00"></div>	
				</td>				
				<td width="20%" align="left">部门净利率:</td>
				<td width="20%" align="left"><div id="jll" style="color:#F00"></div>	</td>
				<td width="20%" align="left"></td>
			</tr>		
		</table>
	</form>

		<div id="bankAcceptance" ></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/kpi/kpi.js"></script>
<script type="text/javascript" defer="defer">
var context = '<%= request.getContextPath() %>';
Ext.onReady(function(){
 	// 工具栏
	var toolbars = new Ext.Toolbar({
		items:['-',
				{id:'view',text:'查看部门KPI',handler:view,iconCls:'icon-search'},'-',
				{id:'_clear' ,text:'清空',handler:_clear ,iconCls:'icon-clean'} ,'-',
				{id:'_search',text:'查看银承余额表',handler:_search,iconCls:'icon-search'},'-'
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
		            title:'银承扣去付款余额查询表',
		            height:420,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'bankAcceptance'
				}]
			}]
    });
    viewport.doLayout();
});



</script> 
