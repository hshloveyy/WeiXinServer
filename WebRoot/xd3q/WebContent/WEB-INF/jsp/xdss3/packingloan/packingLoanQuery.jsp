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
		   pageSize="10" 
		   editable="false" 
		   tableName="ypackingloan" 
		   handlerClass="com.infolion.xdss3.packingloan.web.PackingloanGrid"
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
	<tr>
			<td width="" align="right">部门：</td>
			<td width="" colspan="3">
				<div id="div_dept_sh"></div>
				<fisc:searchHelp divId="div_dept_sh" searchType="field" searchHelpName="YHORGANIZATION" hiddenName="dept_id" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME" boName="" boProperty="" value=""></fisc:searchHelp>
			</td>
			<td></td>
			
			<td width="" align="right">打包贷款单号：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="packing_no" name="packing_no" value="">
			</td>
			<td></td>
			
			<td width="" align="right">关联合同号：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="contractno" name="contractno" value="">
			</td>
			<td></td>
		</tr>
		
		<tr>   
			<td width="" align="right">信用证号：</td>
			<td width="" colspan="3">
				<div id="div_creditno_sh"></div>
				<fisc:searchHelp divId="div_creditno_sh"  searchType="field" searchHelpName="YHCREDITINFO" hiddenName="credit_no" valueField="CREDIT_NO" displayField="CREDIT_NO" boName="" boProperty="" value=""></fisc:searchHelp>
			</td>
			<td></td>
						
			<td width="" align="right">开证行：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="createbank" name="createbank"  >
			</td>
			<td></td>
			
			<td width="" align="right">打包贷款文本：</td>
			<td  width="" colspan="3">
				<input type="text" class="inputText" id="text" name="text" value="" >
			</td>
			<td></td>

		</tr>
		
		<tr>
			<td width="" align="right">还打包贷款文本：</td>
			<td  width="" colspan="3">
				<input type="text" class="inputText" id="retext" name="retext" value="" >
			</td>
			<td></td>
			<td width="" align="right">创建人：</td>
			<td  width="" colspan="3">
				<input type="text" class="inputText" id="creator" name="creator" value="" >
			</td>
			<td></td>
			
			<td width="" align="right">凭证号：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="voucherno" name="voucherno" value="">
			</td>
			<td></td>
		</tr>
		
		<tr>
			<td width="" align="right">打包到期日：</td>
			<td width="92px" >
				<input type="text" id="dealine1" name="dealine1" value=""> 
				<fisc:calendar applyTo="dealine1"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
			</td>
			<td align="left">至</td>
			<td>
				<input type="text" id="dealine2" name="dealine2" value="">
				<fisc:calendar applyTo="dealine2"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
			</td>
			<td></td>
			
			<td width="" align="right">申请时间：</td>
			<td width="92px" >
				<input type="text" id="apply_date1" name="apply_date1" value=""> 
				<fisc:calendar applyTo="apply_date1"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
			</td>
			<td align="left">至</td>
			<td>
				<input type="text" id="apply_date2" name="apply_date2" value="">
				<fisc:calendar applyTo="apply_date2"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
			</td>
			<td></td>
			
			<td width="" align="right"></td>
			<td width="" colspan="3">
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
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/packingloan/packingLoanQuery.js"></script>
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
		            title:'三期出口押汇综合查询',
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
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});
});


function _manager(value,metadata,record){
	var hrefStr = '';
	hrefStr = '<a href="#" style="color: green;" onclick="_commonMethodOperation(\'1\',\'打包贷款查看\',div_southForm_grid,\'xdss3/packingloan/packingLoanController.spr?action=_view&packingid='+value+'\',\'\',\'_view\',\'false\')">查看</a>  ';
	hrefStr += '<a href="#" style="color: green;" onclick="_viewProcessState(\''+value+'\',\'xdss3/packingloan/packingLoanController.spr?action=_viewProcessState\')">查看流程</a>  ';
	return hrefStr;
}

</script>
