<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>未清明细查询</title>
</head>
<body>
	<fisc:grid 
		   divId="UnclearDetail" 
		   pageSize="10000" 
		   tableName="YUNCLEARDETAILED "
		   handlerClass="com.infolion.xdss3.ageAnalysis.domain.UnclearDetailGrid"
 		   whereSql="" 
 		   height="520"
 		   needCheckBox="true" 		  
 		   needAutoLoad="false" isFixed="false">
	</fisc:grid>
	<div id="toolBar" style="overflow: hidden ;width: 100%"></div>
	<form id="mainForm" name="mainForm">
	<!-- 隐藏域 -->
			<input type="hidden" class="inputText" id="subjectCode" name="subjectCode" value="${subjectCode }">
			<input type="hidden" class="inputText" id="augdt" name="augdt" value="${augdt }">
			<input type="hidden" class="inputText" id="subject2" name="subject2" value="${subject2 }">
			<input type="hidden" class="inputText" id="bukrs" name="bukrs" value="${ bukrs}">
			<input type="hidden" class="inputText" id="gsber" name="gsber" value="${gsber }">
			<input type="hidden" class="inputText" id="vbeltype" name="vbeltype" value="${vbeltype }">
			<input type="hidden" class="inputText" id="isExceed" name="isExceed" value="${isExceed }">
			<input type="hidden" class="inputText" id="customerNo" name="customerNo" value="${customerNo}">
			<input type="hidden" class="inputText" id="customerType" name="customerType" value="${customerType}">
	</form>
  	<div id="UnclearDetail"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ageAnalysis/unclearDetailQuery.js"></script>
<script type="text/javascript" defer="defer">
var context = '<%= request.getContextPath() %>';
Ext.onReady(function(){
 	// 工具栏
	var toolbars = new Ext.Toolbar({
		items:['-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_clear' ,text:'清空',handler:_clear ,iconCls:'icon-clean'} ,'-',
				{id:'_exp',text:'导出',handler:_expExcel},'-'],
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
		            title:'未清明细',
		            height:560,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'UnclearDetail'
				}]
			}]
    });
    viewport.doLayout();
});


function _setTotal(value,metadata,record){
	var wbje1_3_date = record.get("wbje1_3_date");
	var wbje4_6_date = record.get("wbje4_6_date");
	var wbje7_12_date = record.get("wbje7_12_date");
	var wbje1_2_year = record.get("wbje1_2_year");
	var wbje2_3_year = record.get("wbje2_3_year");
	var wbje3_4_year = record.get("wbje3_4_year");
	var wbje4_5_year = record.get("wbje4_5_year");
	var wbje5_year_above = record.get("wbje5_year_above");	
	var total =parseFloat(wbje1_3_date) + parseFloat(wbje4_6_date) + parseFloat(wbje7_12_date) + parseFloat(wbje1_2_year) + parseFloat(wbje2_3_year) + parseFloat(wbje3_4_year) + parseFloat(wbje4_5_year) + parseFloat(wbje5_year_above);
	return round(total,2);
}

function _setTotal2(value,metadata,record){
	var bwbje1_3_date = record.get("bwbje1_3_date");
	var bwbje4_6_date = record.get("bwbje4_6_date");
	var bwbje7_12_date = record.get("bwbje7_12_date");
	var bwbje1_2_year = record.get("bwbje1_2_year");
	var bwbje2_3_year = record.get("bwbje2_3_year");
	var bwbje3_4_year = record.get("bwbje3_4_year");
	var bwbje4_5_year = record.get("bwbje4_5_year");
	var bwbje5_year_above = record.get("bwbje5_year_above");	
	var total =parseFloat(bwbje1_3_date) + parseFloat(bwbje4_6_date) + parseFloat(bwbje7_12_date) + parseFloat(bwbje1_2_year) + parseFloat(bwbje2_3_year) + parseFloat(bwbje3_4_year) + parseFloat(bwbje4_5_year) + parseFloat(bwbje5_year_above);
	return round(total,2);
}
</script> 
