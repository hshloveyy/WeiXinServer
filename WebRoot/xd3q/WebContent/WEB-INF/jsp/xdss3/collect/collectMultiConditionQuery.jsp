<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>收款多条件综合查询</title>
</head>
<body>
	<fisc:grid 
		   divId="Collect" 
		   pageSize="10" 
		   tableName="ycollect "
		   handlerClass="com.infolion.xdss3.collect.domain.CollectGrid"
 		   whereSql="" 
 		   needCheckBox="true"
 		   needAutoLoad="false" height="240">
	</fisc:grid>
	<div id="toolBar" style="overflow: hidden ;width: 100%"></div>
	<form id="mainForm" name="mainForm">
		<table width="100%" border="0" cellpadding="4" cellspacing="1">
			<tr>
				<td width="" align="right">收款单号：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="collectno" name="collectno" value="">
				</td>
				<td></td>
				<td width="" align="right">客户：</td>
				<td width="" colspan="3">
					<div id="div_customer_sh"></div>
					<fisc:searchHelp divId="div_customer_sh" searchType="field" searchHelpName="YHGETKUNNR" hiddenName="customer" valueField="KUNNR" displayField="NAME1" boName="" boProperty="" value=""></fisc:searchHelp>
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
				
				<td width="" align="right">业务状态：</td>
				<td width="" colspan="3">
					<div id="div_businessstate_dict"></div>
					<fisc:dictionary hiddenName="businessstate" dictionaryName="YDCOLLECTBUZSTATE" divId="div_businessstate_dict" isNeedAuth="false"></fisc:dictionary>
				</td>
				<td></td>
				<td width="" align="right">立项号：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="project_no" name="project_no" value="">
				</td>
				<td></td>
				<td width="" align="right">单据号码：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="draft" name="draft" value="">
				</td>
				<td></td>
			</tr>
			<tr>
				<td width="" align="right">外部纸质合同号：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="ihrez" name="ihrez" value="">
				</td>
				<td></td>
				<td width="" align="right">出单发票号：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="export_apply_no" name="export_apply_no" value="">
				</td>
				<td></td>
				<td width="" align="right">合同号：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="contract_no" name="contract_no" value="">
				</td>
				<td></td>
			</tr>
			<tr>
				<td width="" align="right">金额：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="amount1" name="amount1" value="" style="width: 91px;"> - 
					<input type="text" class="inputText" id="amount2" name="amount2" value="" style="width: 91px;">
				</td>
				<td></td>
				<td width="" align="right">币别：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="currency" name="currency" value="">
				</td>
				<td></td>
				<td width="" align="right">收款方式：</td>
				<td width="" colspan="3">
					<div id="div_collecttype_dict"></div>
					<fisc:dictionary hiddenName="collecttype" dictionaryName="YDCOLLECTTYPE" divId="div_collecttype_dict" isNeedAuth="false"></fisc:dictionary>
				</td>
				<td></td>
			</tr>
			<tr>
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
				<td width="" align="right">银行承兑汇票/国内信用证到期日：</td>
				<td width="92px" >
					<input type="text" id="draft_date1" name="draft_date1" value=""> 
					<fisc:calendar applyTo="draft_date1"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
				</td>
				<td align="left">至</td>
				<td>
					<input type="text" id="draft_date2" name="draft_date2" value="">
					<fisc:calendar applyTo="draft_date2"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
				</td>
			</tr>
			<tr>
				<td width="" align="right">凭证号：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="voucherno" name="voucherno" value="">
				</td>
				<td></td>
				<td width="" align="right">抬头文本：</td>
				<td width="" colspan="3">
					<input type="text" class="inputText" id="title_text" name="title_text" value="">
				</td>
				<td></td>
				<td width="" align="right">物料组名称：</td>
				<td width="" colspan="3">
				    <div id="div_ymatGroup"></div>
				    <fisc:dictionary width="100" hiddenName = "ymatGroup" isTextValue="true" dictionaryName="YDMATGROUP" divId="div_ymatGroup" isNeedAuth="false"   ></fisc:dictionary>
				</td>
				<td></td>
			</tr>
			<tr>
				<td width="" align="right">预计发货日：</td>
				<td width="92px" >
					<input type="text" id="sendgoodsdate" name="sendgoodsdate" value=""> 
					<fisc:calendar applyTo="sendgoodsdate"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
				</td>
				<td align="left">至</td>
				<td>
					<input type="text" id="sendgoodsdate1" name="sendgoodsdate1" value="">
					<fisc:calendar applyTo="sendgoodsdate1"  divId="" fieldName="" defaultValue="" readonly="false" width="91"></fisc:calendar>
				</td>
				<td></td>
				<td width="" align="right">预收天数：</td>
				<td width="" colspan="3">
					<input type="text" id="yushouTS" name="yushouTS" value="" size="10">-
					<input type="text" id="yushouTS1" name="yushouTS1" value="" size="10">
				</td>
				<td></td>
				<td width="" align="right">延收天数：</td>
				<td width="" colspan="3">
                    <input type="text" id="yanshouTS" name="yanshouTS" value="" size="10">-
					<input type="text" id="yanshouTS1" name="yanshouTS1" value="" size="10">				
				</td>
				<td></td>
			</tr>
			<tr>
				
				<td width="" align="right">贸易类型：</td>
				<td width="" colspan="3">
					<div id="div_tradeType_dict"></div>
					<fisc:dictionary hiddenName="tradeType" dictionaryName="YDTRADETYPE" divId="div_tradeType_dict" isNeedAuth="false"></fisc:dictionary>
				</td>
				<td colspan="10">&nbsp;</td>
			</tr>
		</table>
	</form>
  	<div id="Collect"></div>
</body>
</html>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/collect/collectMultiConditionQuery.js"></script>
<script type="text/javascript" defer="defer">
var context = '<%= request.getContextPath() %>';
Ext.onReady(function(){
 	// 工具栏
	var toolbars = new Ext.Toolbar({
		items:['-',
				{id:'_search',text:'查询',handler:_search,iconCls:'icon-search'},'-',
				{id:'_clear' ,text:'清空',handler:_clear ,iconCls:'icon-clean'} ,'-',
				{id:'_export',text:'导出',handler:_export,iconCls:'icon-clean'},'-'],
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
		            title:'收款明细',
		            height:350,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'Collect'
				}]
			}]
    });
    viewport.doLayout();
    var applyamountIndex = Collect_grid.getColumnModel().findColumnIndex('applyamount');
	Collect_grid.getColumnModel().setRenderer(applyamountIndex,function(applyamount){
		return Utils.commafy(applyamount);
	});
});
function _manager(value,metadata,record){
	var hrefStr = '<a href="#" style="color:green;" onclick="_commonMethodOperation(\'1\',\'收款查看\',Collect_grid,\'xdss3/collect/collectController.spr?action=_view&collectid='+value+'\',\'_viewCollectpCallBack\',\'_view\',\'false\')">查看</a>    ';
		hrefStr +='<a href="#" style="color: green;" onclick="_viewProcessState(\''+value+'\',\'xdss3/collect/collectController.spr?action=_viewProcessState\')">查看流程状态</a>';
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
