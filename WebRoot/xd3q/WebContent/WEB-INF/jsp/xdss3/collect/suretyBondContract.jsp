<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>合同保证金信息汇总表</title>
</head>
<body>
	<fisc:grid 
		   divId="Collect" 
		   pageSize="10" 
		   tableName="ycollect "
		   handlerClass="com.infolion.xdss3.collect.domain.CollectSuretyBondContractGrid"
 		   whereSql="" 
 		   needCheckBox="true"
 		   needAutoLoad="false" height="240">
	</fisc:grid>
	<div id="toolBar" style="overflow: hidden ;width: 100%"></div>
	<form id="mainForm" name="mainForm">
		<table width="100%" border="0" cellpadding="4" cellspacing="1">
			<tr>
				<td width="" align="right">部门：</td>
				<td width="" colspan="3">
					<div id="div_dept_sh"></div>
					<fisc:searchHelp divId="div_dept_sh" searchType="field" searchHelpName="YHORGANIZATION" hiddenName="dept_id" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME" boName="" boProperty="" value=""></fisc:searchHelp>
				</td>
				<td></td>
				<td width="" align="right">立项号：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="project_no" name="project_no" value="">
				</td>
				<td></td>
				<td width="" align="right">立项状态：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="t_projectstateMultiselect" name="t_projectstateMultiselect" value="" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td width="" align="right">合同号：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="contract_no" name="contract_no" value="">
				</td>
				<td></td>
				<td width="" align="right">合同审批通过时间：</td>
				<td width="92px" >
					<input type="text" id="con_apply_date1" name="con_apply_date1" value=""> 
					<fisc:calendar applyTo="con_apply_date1"  divId="" fieldName="" defaultValue="${begDate }" readonly="false" width="91"></fisc:calendar>
				</td>
				<td align="left">至</td>
				<td>
					<input type="text" id="con_apply_date2" name="con_apply_date2" value="">
					<fisc:calendar applyTo="con_apply_date2"  divId="" fieldName="" defaultValue="${sdate }" readonly="false" width="91"></fisc:calendar>
				</td>
				<td></td>
				<td width="" align="right">立项审批通过时间：</td>
				<td width="92px" >
					<input type="text" id="pro_approval_date1" name="pro_approval_date1" value=""> 
					<fisc:calendar applyTo="pro_approval_date1"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
				</td>
				<td align="left">至</td>
				<td>
					<input type="text" id="pro_approval_date2" name="pro_approval_date2" value="">
					<fisc:calendar applyTo="pro_approval_date2"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
				</td>
				<td></td>
			</tr>
			<tr>
			<td width="" align="right">业务类型：</td>
			<td  width="" colspan="3">
				<input type="text" class="inputText" id="t_typeMultiselect" name="t_typeMultiselect" value="" />
			</td>
			<td></td>
			</tr>
		</table>
	</form>
  	<div id="Collect"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/collect/suretyBondContract.js"></script>
<script type="text/javascript" defer="defer">
var context = '<%= request.getContextPath() %>';
Ext.onReady(function(){
 	// 工具栏
	var toolbars = new Ext.Toolbar({
		items:['-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_clear' ,text:'清空',handler:_clear ,iconCls:'icon-clean'},'-',
				{id:'_exp' ,text:'导出',handler:_expExcel ,iconCls:'icon-grid'} ,'-'],
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
		            title:'合同保证金信息汇总表',
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'Collect'
				}]
			}]
    });
    viewport.doLayout();
});
function _manager(value,metadata,record){
	var hrefStr = '<a href="#" style="color:green;" onclick="_commonMethodOperation(\'1\',\'收款查看\',Collect_grid,\'xdss3/collect/collectController.spr?action=_view&collectid='+value+'\',\'_viewCollectpCallBack\',\'_view\',\'false\')">查看</a>    ';
		hrefStr +='<a href="#" style="color: green;" onclick="_viewProcessState(\''+value+'\',\'xdss3/collect/collectController.spr?action=_viewProcessState\')">查看流程状态</a>';
	return hrefStr;
}

function _viewoldcollectinfo(value,metadata,record){
	//var hrefStr = '<a href="#" style="color:green;" onclick="reload_Collect_grid2(\'&viewoldcollectinfo=true&collectno='+value+'\')">'+value+'</a>';
	var hrefStr = '<a href="#" style="color:green;" onclick="_commonMethodOperation(\'1\',\'保证金明细\',Collect_grid,\'xdss3/collect/collectController.spr?action=_suretyBondSubitem&collectno='+value+'\',\'\',\'_view\',\'false\')">'+value+'</a>    ';
	
	return hrefStr;
}
	
function _dateManager(value){
	if(value && value.length>0 && value.length<=8){
		return value.substring(0,4)+'-'+value.substring(4,6)+'-'+value.substring(6,8);
	}else if(value && value.length>8&&value.length<15){
		return value.substring(0,4)+'-'+value.substring(4,6)+'-'+value.substring(6,8)+' '+value.substring(8,10)+':'+value.substring(10,12)+':'+value.substring(12,14);
	}
	return value;
}
</script> 
