<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SAP库存综合查询</title>
</head>
<body>
<fisc:grid divId="div_southForm" 
		   editable="false" 
		   tableName="yprofitloss" 
		   handlerClass="com.infolion.xdss3.profitLoss.web.ProfitLossMatnrGrid"
 		   whereSql="" 
 		   needCheckBox="true"
 		   needAutoLoad="false"
 		   height="240"></fisc:grid>
 		   
		<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="" align="right">查询日期：</td>
		<td width="" colspan="3">
			<input type="text" id="datevalue.fieldValue" name="datevalue.fieldValue" value="">
				<input type="hidden" id="datevalue.fieldName" name="datevalue.fieldName" value="YPROFITLOSS.DATEVALUE"> 
			<fisc:calendar applyTo="datevalue.fieldValue"  divId="" fieldName="" ></fisc:calendar>
		</td>
		<td></td>
		
		<td width="" align="right">公司：</td>
		<td width="" colspan="3">
			<div id="div_companyid_sh"></div>
			<input type="hidden" id="companyid.fieldName" name="companyid.fieldName" value="YPROFITLOSS.COMPANYID"> 
			<fisc:searchHelp divId="div_companyid_sh" boName="ProfitLoss" boProperty="companyid" searchType="field" hiddenName="companyid.fieldValue" valueField="COMPANYID" displayField="COMANYNAME"></fisc:searchHelp>
		</td>
		<td></td>

		<td width="" align="right">部门：</td>
		<td width="" colspan="3">
			<div id="div_dept_sh"></div>
			<fisc:searchHelp divId="div_dept_sh" searchType="field" searchHelpName="YHORGANIZATION" hiddenName="dept_id" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME" boName="" boProperty="" value=""></fisc:searchHelp>
		</td>
		<td></td>
	</tr>
	<tr>
		<td width="" align="right">业务类型：</td>
		<td width="" colspan="3">
			<select id="type" name="type">
				<option value=""	>请选择</option>
				<option value="外贸合作进口业务"	>外贸合作进口业务</option>
				<option value="外贸合作出口业务"	>外贸合作出口业务</option>
				<option value="外贸自营进口业务"	>外贸自营进口业务</option>
				<option value="外贸自营出口业务"	>外贸自营出口业务</option>
				<option value="内贸业务"	>内贸业务</option>
				<option value="进料加工业务"	>进料加工业务</option>
				<option value="外贸自营进口业务_敞口"	>外贸自营进口业务_敞口</option>
				<option value="内贸业务_敞口"	>内贸业务_敞口</option>
			</select>
		</td>
		<td></td>
		
		<td width="" align="right">立项号：</td>
		<td width="" colspan="3">
			<input type="text" class="inputText" id="projectno" name="projectno" value="" >
		</td>
		<td></td>
		
		<td width="" align="right">采购合同号：</td>
		<td width="" colspan="3">
			<input type="text" class="inputText" id="contract_no" name="contract_no" value="">
		</td>
		<td></td>
	</tr>
		
	<tr>
		
		<td width="" align="right">外部纸质合同号：</td>
		<td  width="" colspan="3">
			<input type="text" class="inputText" id="ekko_ihrez" name="ekko_ihrez" value="" >
		</td>
		<td></td>
		
		<td width="" align="right">立项状态：</td>
		<td width="" colspan="3">
			<select id="project_state" name="project_state" width="100%">
				<option value=""	>请选择</option>
				<option value="1">立项</option>
				<option value="2">审批中</option>
				<option value="3">生效</option>
				<option value="4">结项</option>
				<option value="5">归档</option>
				<option value="6">变更</option>
				<option value="7">作废</option>
				<option value="8">变更通过</option>
				<option value="9">关闭</option>
			</select>
		</td>
		<td></td>
		
		<td width="" align="right">物料名称：</td>
		<td width="" colspan="3">
			<input type="text" class="inputText" id=ekpo_txz01 name="ekpo_txz01" value="" >
		</td>
		<td></td>

		
	</tr>
	
	<tr>
		<td width="" align="right">合同审批通过日期：</td>
		<td width="92px" >
			<input type="text" id="approved_time1" name="approved_time1" value=""> 
			<fisc:calendar applyTo="approved_time1"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
		</td>
		<td align="left">至</td>
		<td>
			<input type="text" id="approved_time2" name="approved_time2" value="">
			<fisc:calendar applyTo="approved_time2"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
		</td>
		<td></td>
		
		<td width="" align="right">立项审批通过日期：</td>
		<td width="92px" >
			<input type="text" id="maturity1" name="maturity1" value=""> 
			<fisc:calendar applyTo="maturity1"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
		</td>
		<td align="left">至</td>
		<td>
			<input type="text" id="maturity2" name="maturity2" value="">
			<fisc:calendar applyTo="maturity2"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
		</td>
		<td></td>
		
		<td width="" align="right">采购订单：</td>
		<td width="" colspan="3">
			<input type="text" class="inputText" id="orderid" name="orderid" value="">
		</td>
		<td></td>

	</tr>
	
	<tr>
		<td width="" align="right">物料组：</td>
		<td width="" colspan="3">
			<div id="div_material_group_sh"></div>
			<input type="hidden" id="material_group.fieldName" name="material_group.fieldName" value="YPROFITLOSS.MATERIAL_GROUP"> 
			<fisc:searchHelp divId="div_material_group_sh" boName="ProfitLoss" boProperty="material_group" searchType="field" hiddenName="material_group.fieldValue" valueField="MATKL" displayField="WGBEZ"></fisc:searchHelp>
		</td>
		<td></td>
			
		<td width="" align="right">供应商：</td>
		<td width="" colspan="3">
			<div id="div_supplier_sh"></div>
			<fisc:searchHelp divId="div_supplier_sh" searchType="field" searchHelpName="YHGETLIFNR" hiddenName="supplier" valueField="LIFNR" displayField="NAME1" boName="" boProperty="" value=""></fisc:searchHelp>
		</td>
		<td></td>
	
	</tr>
	
	<tr>
	</tr>
</table>
</form>
</div>
<div id="div_southForm"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/profitLoss/profitLossQueryByMatnr.js"></script>
<script type="text/javascript" defer="defer">
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

/**
 * EXT 布局
 */
Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		items:[{
			region:"center",
			items:[{
					region:"north",
					contentEl:'div_toolbar',
					border:false,
					height:26
				},{
					region:'center',
					border:false,
					contentEl:'div_center'
				},		
				{
					region:"south",
					layout:'fit',
					border:false,
					autoScroll: true,
		            title:'SAP库存综合查询',
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-',
				{id:'_exp',text:'导出',handler:_expExcel},'-'
				],
		renderTo:"div_toolbar"
	});
	
	// 设置公司默认限定条件
	div_companyid_sh_sh.defaultCondition=" companyid IN ('1002','2100','2200','2300','2400','2600','2700','2800') ";
});


function _manager(value,metadata,record){
	var hrefStr = '';
	hrefStr = '<a href="#" style="color: green;" onclick="_commonMethodOperation(\'1\',\'浮动盈亏查看\',div_southForm_grid,\'xdss3/billpurchased/billPurchasedController.spr?action=_view&billpurid='+value+'\',\'_viewBillPurchasedpCallBack\',\'_view\',\'false\')">查看</a>  ';
	hrefStr += '<a href="#" style="color: green;" onclick="_viewProcessState(\''+value+'\',\'xdss3/billpurchased/billPurchasedController.spr?action=_viewProcessState\')">查看流程</a>  ';
	return hrefStr;
}

</script>
