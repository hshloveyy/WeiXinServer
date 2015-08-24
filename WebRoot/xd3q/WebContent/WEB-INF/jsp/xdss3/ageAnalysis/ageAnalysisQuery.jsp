<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>账龄分析综合查询</title>
</head>
<body>
	<fisc:grid 
		   divId="AgeAnalysis" 
		   pageSize="10000" 
		   tableName="YUNCLEARDETAILED "
		   handlerClass="com.infolion.xdss3.ageAnalysis.domain.AgeAnalysisGrid"
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
			</tr>
			
			<tr>
				<td width="15%" align="right">总账科目：</td>
				<td >
					<div id="div_subjectCode_dict"/>
				</td>
				<td width="20%" align="left"></td>
				
				<td width="15%" align="right">过账日期：</td>
				<td >
					<input type="text" id="augdt" name="augdt" value="">
					<fisc:calendar applyTo="augdt"  divId="" fieldName="" defaultValue="" readonly="readonly"></fisc:calendar>
				</td>
				<td width="20%" align="left"></td>
			</tr>
			<tr>
				<td width="15%" align="right">业务类型：</td>
				<td >
					<!-- 
					<div id="div_vbeltype_dict"/>
					 -->
					<INPUT TYPE="text" NAME="customerMultiselect" id='customerMultiselect' size='30'>
				</td>
				<td width="20%" align="left"></td>
				<td width="15%" align="right">是否逾期：</td>
				<td >
					<div id="div_isExceed_dict"/>
				</td>
				<td width="20%" align="left"></td>
				
			</tr>
			<tr>
				<td width="15%" align="right">客户：</td>
				<td>
					<input type="text" class="inputText" id="customerName" name="customerName" value="" readonly="readonly">
				</td>
				<td width="20%" align="left"><div id="div_customer_bt"/></td>
	
				<td width="15%" align="right">供应商：</td>
				<td >
					<input type="text" class="inputText" id="vendorName" name="vendorName" value="" readonly="readonly">
				</td>
				<td width="20%" align="left"><div id="div_vendor_bt"/></td>
			</tr>
			
		
			
			<!-- 隐藏域 -->
			<input type="hidden" class="inputText" id="customerNo" name="customerNo" value="">
			<input type="hidden" class="inputText" id="customerType" name="customerType" value="">
			<input type="hidden" class="inputText" id="vbeltype" name="vbeltype" value="">
		</table>
	</form>
  	<div id="AgeAnalysis" ></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/ageAnalysis/ageAnalysisQuery.js"></script>
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
		            title:'账龄分析明细',
		            height:420,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'AgeAnalysis'
				}]
			}]
    });
    viewport.doLayout();
});

function _customerNoRender(value,metadata,record){
	var customerNo = record.get("customerNo");
	var augdt = record.get("augdt");
	var subjectCode = record.get("subjectCode");
	var gsber = record.get("gsber");
	var bukrs = record.get("bukrs");
	var vbeltype = record.get("vbeltype");
	
	return '<a href="#" onclick="viewUnclearDetail(\''+customerNo+'\',\''+augdt+'\',\''+subjectCode+'\',\''+gsber+'\',\''+bukrs+'\',\''+vbeltype+'\');"><u style="border-bottom:1px;">'+value+'</u></a>';
}

function _setTotal(value,metadata,record){
	var date1_3 = record.get("date1_3");
	var date4_6 = record.get("date4_6");
	var date7_12 = record.get("date7_12");
	var year1_2 = record.get("year1_2");
	var year2_3 = record.get("year2_3");
	var year3_4 = record.get("year3_4");
	var year4_5 = record.get("year4_5");
	var year5_above = record.get("year5_above");
	var total =parseFloat(date1_3) + parseFloat(date4_6) + parseFloat(date7_12) + parseFloat(year1_2) + parseFloat(year2_3) + parseFloat(year3_4) + parseFloat(year4_5) + parseFloat(year5_above);
	return round(total,2);
}
function viewUnclearDetail(customerNo,augdt,subjectCode,gsber,bukrs,vbeltype){
//	var para = Form.serialize('mainForm');
//	para += '&no=' + customerNo;
	var subject2= subjectCodeDict.getValue();
	var para ="customerNo=" +customerNo + "&augdt="+augdt+"&subjectCode="+subjectCode+"&gsber="+gsber+ "&bukrs="+bukrs+ "&vbeltype="+vbeltype + "&subject2="+subject2;
	var requestUrl = context + '/ageAnalysisController.spr?action=unclearDetailQuery&' + para;
//	alert(requestUrl);
//	if(_commonVerification()){
		_getMainFrame().maintabs.addPanel('未清明细','',requestUrl);
//	}
}

function _dateRender(value){
	if(value && value.length>0 && value.length<=8){
		return value.substring(0,4)+'-'+value.substring(4,6)+'-'+value.substring(6,8);
	}else if(value && value.length>8&&value.length<15){
		return value.substring(0,4)+'-'+value.substring(4,6)+'-'+value.substring(6,8)+' '+value.substring(8,10)+':'+value.substring(10,12)+':'+value.substring(12,14);
	}
	return value;
}

function _isExceedRender(value){
	if(value=='Y'){
		return '是';
	}else{
		return '否';
	}
}

</script> 
