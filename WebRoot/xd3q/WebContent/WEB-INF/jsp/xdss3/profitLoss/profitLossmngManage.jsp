<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年05月21日 11点46分24秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象存货浮动盈亏调查表(ProfitLossmng)管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.managePage}</title>
</head>
<body>
		<fisc:grid divId="div_southForm" boName="ProfitLossmng"
			editable="true" needCheckBox="false" needToolbar="true"
			needAutoLoad="false" needAuthentication="true" pageSize="10"  
			orderBySql="YPROFITLOSS.Datevalue, YPROFITLOSS.Deptid, YPROFITLOSS.type desc,  YPROFITLOSS.project_no, YPROFITLOSS.contractno, YPROFITLOSS.profitlossid"></fisc:grid>
		<!-- dpPageFtlLib.ftl -->
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="15%" align="right">${vt.property.companyid}：</td>
		<td  width="30%" >
			<div id="div_companyid_sh"></div>
			<input type="hidden" id="companyid.fieldName" name="companyid.fieldName" value="YPROFITLOSS.COMPANYID"> 
			<input type="hidden" id="companyid.dataType" name="companyid.dataType" value="S">  
			<input type="hidden" id="companyid.option" name="companyid.option" value="like">
			<fisc:searchHelp divId="div_companyid_sh" boName="ProfitLoss" boProperty="companyid" searchType="field" hiddenName="companyid.fieldValue" valueField="COMPANYID" displayField="COMANYNAME"></fisc:searchHelp>
		</td>
		
		<td width="15%" align="right">${vt.property.deptid}：</td>
		<td  width="30%" >
			<div id="div_deptid_sh"></div>
			<input type="hidden" id="deptid.fieldName" name="deptid.fieldName" value="YPROFITLOSS.DEPTID"> 
			<input type="hidden" id="deptid.dataType" name="deptid.dataType" value="S">  
			<input type="hidden" id="deptid.option" name="deptid.option" value="like">
			<fisc:searchHelp divId="div_deptid_sh" boName="ProfitLoss" boProperty="deptid" searchType="field" hiddenName="deptid.fieldValue" valueField="ORGANIZATIONID" displayField="ORGANIZATIONNAME"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">${vt.property.datevalue}：</td>
		<td  width="40%" >
			<input type="text" id="datevalue.fieldValue" name="datevalue.fieldValue" value="">
			<input type="hidden" id="datevalue.fieldName" name="datevalue.fieldName" value="YPROFITLOSS.DATEVALUE"> 
			<input type="hidden" id="datevalue.dataType" name="datevalue.dataType" value="D">  
			<input type="hidden" id="datevalue.option" name="datevalue.option" value="like">
				<fisc:calendar applyTo="datevalue.fieldValue"  divId="" fieldName=""></fisc:calendar>
		</td>
		
		<td width="15%" align="right">${vt.property.project_no}：</td>
		<td  width="30%" >
			<div id="div_project_no_sh"></div>
			<input type="hidden" id="project_no.fieldName" name="project_no.fieldName" value="YPROFITLOSS.PROJECT_NO"> 
			<input type="hidden" id="project_no.dataType" name="project_no.dataType" value="S">  
			<input type="hidden" id="project_no.option" name="project_no.option" value="like">
			<fisc:searchHelp divId="div_project_no_sh" boName="ProfitLoss" boProperty="project_no" searchType="field" hiddenName="project_no.fieldValue" valueField="PROJECT_NO" displayField="PROJECT_NO"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">${vt.property.type}：</td>
		<td  width="40%" >
			<select id="type.fieldValue" name="type.fieldValue">
				<option value=""	>请选择</option>
				<option value="外贸合作进口业务"	>外贸合作进口业务</option>
				<option value="外贸合作出口业务"	>外贸合作出口业务</option>
				<option value="外贸自营进口业务"	>外贸自营进口业务</option>
				<option value="外贸自营出口业务"	>外贸自营出口业务</option>
<%--				<option value="外贸代理出口业务"	>外贸代理出口业务</option>--%>
<%--				<option value="外贸代理进口业务"	>外贸代理进口业务</option>--%>
				<option value="内贸业务"	>内贸业务</option>
				<option value="进料加工业务"	>进料加工业务</option>
				<option value="外贸自营进口业务_敞口"	>外贸自营进口业务_敞口</option>
				<option value="内贸业务_敞口"	>内贸业务_敞口</option>
            </select>
			<input type="hidden" id="type.fieldName" name="type.fieldName" value="YPROFITLOSS.TYPE"> 
			<input type="hidden" id="type.dataType" name="type.dataType" value="S">  
			<input type="hidden" id="type.option" name="type.option" value="equal">
<%--			<input type="text" id="type.fieldValue" name="type.fieldValue" value="">--%>
<%--			<div id="div_type_dict"></div>--%>
<%--			<fisc:dictionary hiddenName="type.fieldValue" dictionaryName="YDORDERTYPE" divId="div_type_dict" isNeedAuth="false"></fisc:dictionary>--%>
		</td>
		<td width="15%" align="right">${vt.property.material_group}：</td>
		<td  width="30%" >
			<div id="div_material_group_sh"></div>
			<input type="hidden" id="material_group.fieldName" name="material_group.fieldName" value="YPROFITLOSS.MATERIAL_GROUP"> 
			<input type="hidden" id="material_group.dataType" name="material_group.dataType" value="S">  
			<input type="hidden" id="material_group.option" name="material_group.option" value="like">
			<fisc:searchHelp divId="div_material_group_sh" boName="ProfitLoss" boProperty="material_group" searchType="field" hiddenName="material_group.fieldValue" valueField="MATKL" displayField="WGBEZ"></fisc:searchHelp>
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
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/profitLoss/profitLossmngManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/profitLoss/profitLossmngManageGen.js"></script>
<script type="text/javascript" defer="defer">
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	profitLossmng:'${vt.boText}',
	// 您选择了【存货浮动盈亏调查表删除】操作，是否确定继续该操作？
	profitLossmng_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
	// 提示
	cue:'${vt.sCue}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}',
	// 创建
	mCreate:'${vt.mCreate}'
};

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
		            title:'${vt.boTextDetail}',
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'${vt.mQuery}',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'${vt.sClean}',handler:_resetForm,iconCls:'icon-clean'},'-',
				{id:'_exp',text:'导出',handler:_expExcel},'-'
				],
		renderTo:"div_toolbar"
	});
	
	var toolbar = ProfitLossmng_grid.getTopToolbar();
	toolbar.items.get('ProfitLossmngaddRow').hide();		// 隐藏"增加行"按钮
	toolbar.items.get('ProfitLossmngdeleteRow').hide();	// 隐藏"删除行"按钮
	
});

function _saveOrUpdate()
{
	var param = Form.serialize('mainForm');	
	//param= param + getAllProfitLossmngGridData();
	//param= param + getProfitLossGridData();
	var records = ProfitLossmng_grid.getStore().getModifiedRecords();
	//编号
	var modifyId = "";
	//返补备注
	var backcommentlist = "";
	//最大损失备注
	var maxlosscommentlist = "";
	//扣除预计返补后的总浮盈亏
	var remaintotalvaluelist = "";
	// 持仓费用
	var positionvaluelist = "";
    for(var i=0;i<records.size();i++)
    {
    	modifyId = modifyId + records[i].data.profitlossid + "|";
    	
    	if ( records[i].data.backcomment.trim() == '' ) {
    		backcommentlist = backcommentlist + ' ' + "|";
    	} else {
    		backcommentlist = backcommentlist + records[i].data.backcomment + "|";
    	}
    	if ( records[i].data.maxlosscomment.trim() == '' ) {
    		maxlosscommentlist = maxlosscommentlist + ' ' + "|";
    	} else {
    		maxlosscommentlist = maxlosscommentlist + records[i].data.maxlosscomment + "|";
    	}
    	//maxlosscommentlist = maxlosscommentlist + records[i].data.maxlosscomment + "|";
    	remaintotalvaluelist = remaintotalvaluelist + records[i].data.remaintotalvalue + "|";
    	positionvaluelist = positionvaluelist + records[i].data.positionvalue + "|";
    }
    param = param + '&profitlossidlist=' + modifyId + '&backcommentlist='+backcommentlist + 
    		'&maxlosscommentlist=' + maxlosscommentlist + 
    		'&remaintotalvaluelist=' + remaintotalvaluelist +
    		'&positionvaluelist=' + positionvaluelist;
    new AjaxEngine(contextPath + '/xdss3/profitLoss/profitLossmngController.spr?action=_saveOrUpdate', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
}

/**
 * 操作成功后的回调动作
 */
function customCallBackHandle(transport)
{
	_search()
}
</script>
