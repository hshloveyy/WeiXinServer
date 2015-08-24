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
		   divId="UnclearSupplier" 
		   pageSize="10" 
		   tableName="YVENDORTITLE"
		   handlerClass="com.infolion.xdss3.unclearQuery.domain.UnclearSupplierQueryGrid"
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
					<!--  
					<input type="hidden" id="bukrs.fieldName" name="bukrs.fieldName" value="bukrs"> 
					<input type="hidden" id="bukrs.dataType" name="bukrs.dataType" value="S">  
					<input type="hidden" id="bukrs.option" name="bukrs.option" value="like">
					-->
					<fisc:searchHelp divId="div_bukrs_sh" searchHelpName="YHXD3QCOMPANY" boName="" boProperty="" searchType="field" hiddenName="bukrs.fieldValue" valueField="COMPANYID" displayField="COMANYNAME"></fisc:searchHelp>
				</td>
				<td width="20%" align="left"></td>
	
				<td width="15%" align="right">业务范围：</td>
				
				<td >
					<div id="div_gsber_sh"></div>	
					<!-- 		
					<input type="hidden" id="GSBER.fieldname" name="GSBER.fieldName" value="GSBER"> 	
					<input type="hidden" id="GSBER.dataType" name="GSBER.dataType" value="S">  
					<input type="hidden" id="GSBER.option" name="GSBER.option" value="like">
					-->
					<fisc:searchHelp divId="div_gsber_sh" searchHelpName="YHXD3QDEPT" boName="" boProperty="" searchType="field" hiddenName="GSBER.fieldValue" valueField="DEPTID" displayField="DEPTNAME"></fisc:searchHelp>
				</td>
				<td align="left"></td>
				<td width="20%" align="left"></td>
			</tr>
			<tr>
				<td width="15%" align="right">供应商：</td>
				<td>
					<div id="div_supplier_sh"></div>
					
					<fisc:searchHelp divId="div_supplier_sh" searchHelpName="YHGETLIFNR" boName="" boProperty="" searchType="field" hiddenName="lifnr.fieldValue" valueField="LIFNR" displayField="NAME1"></fisc:searchHelp>
				</td>
				<td width="20%" align="left"></td>
	
				<td width="15%" align="right">币别：</td>
				<td >
					<div id="div_currencytext_sh"></div>
					<!--
					<input type="hidden" id="waers.fieldname" name="waers.fieldName" value="waers"> 	
					<input type="hidden" id="waers.dataType" name="waers.dataType" value="S">  
					<input type="hidden" id="waers.option" name="waers.option" value="like">
					-->
					<fisc:searchHelp divId="div_currencytext_sh" searchHelpName="YHTCURT" boName="" boProperty="" searchType="field" hiddenName="waers.fieldValue" valueField="WEARS" displayField="KTEXT"></fisc:searchHelp>
				</td>
				<td  align="left"></td>
				<td width="20%" align="left"></td>
			</tr>
			<tr>
				<td width="15%" align="right">清账科目：</td>
				<td >
					<div id="div_subject_sh"></div>
					<!--
					<input type="hidden" id="SAKNR.fieldname" name="SAKNR.fieldName" value="WEARS"> 	
					<input type="hidden" id="SAKNR.dataType" name="SAKNR.dataType" value="S">  
					<input type="hidden" id="SAKNR.option" name="SAKNR.option" value="like">
					-->
					<fisc:searchHelp divId="div_subject_sh" searchHelpName="YHSKAT" boName="" boProperty="" searchType="field" hiddenName="SAKNR.fieldValue" valueField="SAKNR" displayField="TXT20"></fisc:searchHelp>
				</td>
				<td width="20%" align="left"></td>
				
				<td width="15%" align="right">过账日期：从</td>
				<td >					
					<input type="text" id="augdt_to" name="augdt_to" value="">
					<fisc:calendar applyTo="augdt_to"  divId="" fieldName="" defaultValue="" readonly="readonly"></fisc:calendar>
				</td>				
				<td align="left" width="12px" >至</td>
				<td >					
					<input type="text" id="augdt_from" name="augdt_from" value="">
					<fisc:calendar applyTo="augdt_from"  divId="" fieldName="" defaultValue="" readonly="readonly"></fisc:calendar>
				</td>
				<td width="20%" align="left"></td>
			</tr>
		</table>
	</form>
  	<div id="UnclearSupplier" ></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/unclearquery/unclearSupplierQuery.js"></script>
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
		            title: '客户清账明细',
		            height:420,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'UnclearSupplier'
				}]
			}]
    });
    viewport.doLayout();
});

function _isClearRender(value,metadata,record){
	var dmbtr = record.get("dmbtr");
	var unAmount = record.get("unAmount");
	if(value=='1'){
		return '外围已清';
	}else if(value=='2'){
		return 'SAP已清';
	}else{
		if(parseFloat(dmbtr) - parseFloat(unAmount) == 0){
			return '未清';
		}else{
			return '部分清';
		}
	}
}

function _shkzgRender(value){
	if(value=='H'){
		return '借方';
	}else{
		return '贷方';
	}
}

function _offAmountRender(value,metadata,record){
	var dmbtr = record.get("dmbtr");
	var unAmount = record.get("unAmount");
	var offAmout =parseFloat(dmbtr) - parseFloat(unAmount) ;
	return round(offAmout,2);
}

function _offbwbjeRender(value,metadata,record){
	var bwbje = record.get("bwbje");
	var unbwbje = record.get("unbwbje");
	var offbwbje =parseFloat(bwbje) - parseFloat(unbwbje) ;
	return round(offbwbje,2);
}
</script> 
