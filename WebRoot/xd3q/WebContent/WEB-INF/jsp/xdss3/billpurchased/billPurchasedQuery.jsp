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
		   tableName="ybillpurchased" 
		   handlerClass="com.infolion.xdss3.billpurchased.web.BillPurchasedGrid"
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
			
			<td width="" align="right">出口押汇单号：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="billpur_no" name="billpur_no" value="">
			</td>
			<td></td>
			
			<td width="" align="right">出单发票号：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="billno" name="billno" value="">
			</td>
			<td></td>
		</tr>
		
		<tr>
			<td width="" align="right">L/C No：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="lcno" name="lcno" value="">
			</td>
			<td></td>
						
			<td width="" align="right">申请押汇金额：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="amount1" name="amount1" value=""  style="width: 91px;"> - 
				<input type="text" class="inputText" id="amount2" name="amount2" value=""  style="width: 91px;">
			</td>
			<td></td>
			
			<td width="" align="right">抬头文本（押汇用途）：</td>
			<td  width="" colspan="3">
				<input type="text" class="inputText" id="text" name="text" value="" >
			</td>
			<td></td>

		</tr>
		
		<tr>
			<td width="" align="right">抬头文本（还押汇用途）：</td>
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
			<td width="" align="right">押汇到期日：</td>
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
			
			<td width="" align="right">单据类型：</td>
			<td width="" colspan="3">
			<select name="billtype">
			   <option value="">请选择</option>
			   <option value="LC">LC</option>
			   <option value="DP">DP</option>
			   <option value="DA">DA</option>
			   <option value="TT">TT</option>
			</select>
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
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/billpurchased/billPurchasedQuery.js"></script>
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
	hrefStr = '<a href="#" style="color: green;" onclick="_commonMethodOperation(\'1\',\'出口押汇查看\',div_southForm_grid,\'xdss3/billpurchased/billPurchasedController.spr?action=_view&billpurid='+value+'\',\'_viewBillPurchasedpCallBack\',\'_view\',\'false\')">查看</a>  ';
	hrefStr += '<a href="#" style="color: green;" onclick="_viewProcessState(\''+value+'\',\'xdss3/billpurchased/billPurchasedController.spr?action=_viewProcessState\')">查看流程</a>  ';
	return hrefStr;
}

</script>
