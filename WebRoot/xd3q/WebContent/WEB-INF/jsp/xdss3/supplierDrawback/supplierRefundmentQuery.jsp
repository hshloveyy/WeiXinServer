<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商退款综合查询页面</title>
</head>
<body>
<fisc:grid divId="div_southForm" 
		   pageSize="10" 
		   editable="false" 
		   tableName="YREFUNDMENT" 
		   handlerClass="com.infolion.xdss3.supplierDrawback.web.SupplierGrid"
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
			<td width="" align="right">部门：</td>
			<td width="" colspan="3">
				<div id="div_dept_sh"></div>
				<fisc:searchHelp divId="div_dept_sh" searchType="field" searchHelpName="YHORGANIZATION" hiddenName="dept_id" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME" boName="" boProperty="" value=""></fisc:searchHelp>
			</td>
			<td></td>
			
			<td width="" align="right">供应商：</td>
			<td width="" colspan="3">
				<div id="div_supplier_sh"></div>
				<fisc:searchHelp divId="div_supplier_sh" searchType="field" searchHelpName="YHGETLIFNR" hiddenName="supplier" valueField="LIFNR" displayField="NAME1" boName="" boProperty="" value=""></fisc:searchHelp>
			</td>
			<td></td>
			
			<td width="" align="right">退款单号：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="refundmentno" name="refundmentno" value="">
			</td>
			<td></td>
		</tr>
		
		<tr>
			<td width="" align="right">付款单号：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="paymentno" name="paymentno" value="">
			</td>
			<td></td>
			
			<td width="" align="right">合同编码：</td>
			<td  width="" colspan="3">
			<input type="text" class="inputText" id="contract_no" name="contract_no" value="" >
			</td>
			<td></td>
						
			<td width="" align="right">退款金额：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="amount1" name="amount1" value="" style="width: 91px;"> - 
				<input type="text" class="inputText" id="amount2" name="amount2" value="" style="width: 91px;">
			</td>
			<td></td>

		</tr>
		
		<tr>
			<td width="" align="right">抬头文本：</td>
			<td  width="" colspan="3">
				<input type="text" class="inputText" id="title_text" name="title_text" value="" >
			</td>
			<td></td>
			
			<td width="" align="right">凭证号：</td>
			<td width="" colspan="3">
				<input type="text" class="inputText" id="voucherno" name="voucherno" value="">
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
		</tr>
		
		<tr>
			<td width="" align="right">审批通过时间：</td>
			<td width="92px" >
				<input type="text" id="approval_date1" name="approval_date1" value=""> 
				<fisc:calendar applyTo="approval_date1"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
			</td>
			<td align="left">至</td>
			<td>
				<input type="text" id="approval_date2" name="approval_date2" value="">
				<fisc:calendar applyTo="approval_date2"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
			</td>
			
			<td></td>
			<td width="" align="right">立项号：</td>
			<td  width="" colspan="3">
				<input type="text" class="inputText" id="project_no" name="project_no" value="" >
			</td>
			<td></td>
		</tr>
		
	</table>
</form>
</div>
<div id="div_southForm"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/supplierDrawback/supplierRefundmentQuery.js"></script>

<script type="text/javascript" defer="defer">
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本

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
		            title:'供应商退款查询',
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
	
	var contIndex = div_southForm_grid.getColumnModel().findColumnIndex('businessstate');
    div_southForm_grid.getColumnModel().setRenderer(contIndex,function(contNo){		
		if(contNo == '2'){
			return '过资金部';
		}
		if(contNo == '3'){
			return '审批通过';
		}
		if(contNo == '-1'){
			return '作废';
		}
		if(contNo == '0'){
			return '新增';
		}
		if(contNo == '1'){
			return '审批';
		}
		if(contNo == '4'){
			return '审批不通过';
		}
		if(contNo == '-2'){
			return '重做';
		}
		
		return contNo;
		
	});
	
});


function _manager(value,metadata,record){
	var hrefStr = '';
	hrefStr = '<a href="#" style="color: green;" onclick="_commonMethodOperation(\'1\',\'供应商退款查看\',div_southForm_grid,\'xdss3/supplierDrawback/supplierRefundmentController.spr?action=_view&refundmentid='+value+'\',\'_viewSupplierRefundmentpCallBack\',\'_view\',\'false\')">退款查看</a>  ';
	hrefStr += '<a href="#" style="color: green;" onclick="_viewProcessState(\''+value+'\',\'xdss3/supplierDrawback/supplierRefundmentController.spr?action=_viewProcessState\')">查看流程</a>  ';
	return hrefStr;
}

</script>
