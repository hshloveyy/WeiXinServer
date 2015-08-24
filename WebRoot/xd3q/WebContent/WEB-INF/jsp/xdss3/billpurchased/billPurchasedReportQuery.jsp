<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>三期出口押汇综合查询</title>
</head>
<body>
<fisc:grid divId="div_southForm" 
		   pageSize="10000" 
		   editable="false" 
		   tableName="ybillpurchased" 
		   handlerClass="com.infolion.xdss3.billpurchased.web.BillPurchasedReportGrid"
 		   whereSql="" 
 		   needCheckBox="true"
 		   needAutoLoad="false"
 		   height="240"></fisc:grid>
 		   
		<!-- 	 YBILLPURCHASED.LASTMODIFYTIME desc  -->
		<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr style= "Display:none">
		<td width="10%" ></td>
		<td width="23%" colspan="3"></td>
		
		<td width="10%" ></td>
		<td width="23%" colspan="3"></td>
		
		<td width="10%" ></td>
		<td width="23%" colspan="3"></td>
	</tr>
	<tr>
		<td width="10%" align="right">公司：</td>
		<td  width="23%" colspan="3">
			<div id="div_bukrs_sh"></div>
			<fisc:searchHelp divId="div_bukrs_sh" searchHelpName="YHXD3QCOMPANY" boName="" boProperty="" searchType="field" hiddenName="bukrs" valueField="COMPANYID" displayField="COMANYNAME"></fisc:searchHelp>
		</td>
		<td width="10%" align="right">部门：</td>
		<td  width="23%" colspan="3">
			<div id="div_dept_sh"></div>
			<fisc:searchHelp divId="div_dept_sh" searchType="field" searchHelpName="YHORGANIZATION" hiddenName="dept_id" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME" boName="" boProperty="" value=""></fisc:searchHelp>
		</td>
		<td width="10%" align="right">客户：</td>
		<td  width="23%" colspan="3">
			<div id="div_customer_sh"></div>
			<fisc:searchHelp divId="div_customer_sh" searchHelpName="YHGETKUNNR" boName="" boProperty="" searchType="field" hiddenName="customer" valueField="KUNNR" displayField="NAME1"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="10%" align="right">日期：</td>
		<td >
			<input type="text" id="issuing_date" name="issuing_date" value="" width="100">
				<fisc:calendar readonly="false" applyTo="issuing_date"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td align="center">&nbsp;至&nbsp;</td>
		<td>
			<input type="text" id="issuing_dateEnd" name="issuing_dateEnd" value="" width="100">
				<fisc:calendar readonly="false" applyTo="issuing_dateEnd"  divId="" fieldName=""  width="100"></fisc:calendar>
		</td>
		<td width="10%" align="right">立项号：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="project_no" name="project_no" value="" >
		</td>
		<td width="10%" align="right">外部纸质合同号：</td>
		<td  width="23%" colspan="3">
			<input type="text" class="inputText" id="old_contract_no" name="old_contract_no" value="" >
		</td>
	</tr>
	<tr>
		<td align="center" colspan="4" height="10">
		</td>
	</tr>
</table>
</form>
</div>
<div id="div_southForm"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/billpurchased/billPurchasedReportQuery.js"></script>
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
		            title:'出口押汇报表查询',
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
});


function _manager(value,metadata,record){
	var hrefStr = '';
	hrefStr = '<a href="#" style="color: green;" onclick="_commonMethodOperation(\'1\',\'出口押汇查看\',div_southForm_grid,\'xdss3/billpurchased/billPurchasedController.spr?action=_view&billpurid='+value+'\',\'\',\'_view\',\'false\')">查看</a>  ';
	hrefStr += '<a href="#" style="color: green;" onclick="_viewProcessState(\''+value+'\',\'xdss3/billpurchased/billPurchasedController.spr?action=_viewProcessState\')">查看流程</a>  ';
	return hrefStr;
}

</script>
