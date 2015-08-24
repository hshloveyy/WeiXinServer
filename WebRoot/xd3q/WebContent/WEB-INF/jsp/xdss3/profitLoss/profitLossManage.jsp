<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年06月04日 16点23分21秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象合同信息及市场单价维护(ProfitLoss)管理页面
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
		<fisc:grid divId="div_southForm" boName="ProfitLoss" editable="true"
			needCheckBox="false" needToolbar="true" needAutoLoad="false"
			needAuthentication="true" pageSize="10"  
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
				<fisc:calendar applyTo="datevalue.fieldValue"  divId="" fieldName="" ></fisc:calendar>
		</td>
		<td width="15%" align="right">${vt.property.type}：</td>
		<td  width="30%" >
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
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">批次号：</td>
		<td  width="30%" >
			<div id="div_companyid_sh"></div>
			<input type="hidden" id="BATCH_NO.fieldName" name="BATCH_NO.fieldName" value="YPROFITLOSS.BATCH_NO"> 
			<input type="hidden" id="BATCH_NO.dataType" name="BATCH_NO.dataType" value="S">  
			<input type="hidden" id="BATCH_NO.option" name="BATCH_NO.option" value="like">
			<input type="text" id="BATCH_NO.fieldValue" name="BATCH_NO.fieldValue" value="">
		</td>
		
		<td width="15%" align="right"></td>
		<td  width="30%" >
			
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
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/profitLoss/profitLossManage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/xdss3/profitLoss/profitLossManageGen.js"></script>
<script type="text/javascript" defer="defer">
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	profitLoss:'${vt.boText}',
	// 您选择了【合同信息及市场单价维护删除】操作，是否确定继续该操作？
	profitLoss_Delete_ConfirmOperation:'${vt.delete_ConfirmOperation}',
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
				//,{id:'_reloadAll',text:'重载全部（QJX）',handler:_reloadAll},'-'
				],
		renderTo:"div_toolbar"
	});
	
	var toolbar = ProfitLoss_grid.getTopToolbar();
	toolbar.items.get('ProfitLossaddRow').hide();		// 隐藏"增加行"按钮
	toolbar.items.get('ProfitLossdeleteRow').hide();	// 隐藏"删除行"按钮
});

function _reload()
{
	
	if ( ! window.confirm('请先查询是否存在记录，\n确认重新读取？') ){
		return false;
	}
	
	//var companyid = document.getElementById('ProfitLoss.companyid').value;
	var companyid = div_companyid_sh_sh.getValue();
	var datevalue = document.getElementById('datevalue.fieldValue').value;

	if (companyid == ""){
		_getMainFrame().showInfo('请输入公司！');
		return false;
	}
	if (datevalue == ""){
		_getMainFrame().showInfo('请输入日期！');
		return false;	
	}
	var param = Form.serialize('mainForm');	
	
    new AjaxEngine(contextPath + '/xdss3/profitLoss/profitLossController.spr?action=_reload', 
			   {method:"post", parameters: param, onComplete: callBackHandle,callback:customCallBackHandle});
		
}



function _saveOrUpdate()
{
	var param = Form.serialize('mainForm');	

	//param= param + getAllProfitLossGridData();
	//param= param + getProfitLossGridData();
	var records = ProfitLoss_grid.getStore().getModifiedRecords();
	var modifyId = "";
	var modifyValue = "";
	var maxlosscommentList = "";
	var positionvaluelist = "";
    for(var i=0;i<records.size();i++)
    {
    	modifyId = modifyId + records[i].data.profitlossid + "|";
    	modifyValue = modifyValue + records[i].data.marketunitprice + "|";
    	if ( records[i].data.maxlosscomment.trim() == '' ) {
    		maxlosscommentList = maxlosscommentList + ' ' + "|";
    	} else {
    		maxlosscommentList = maxlosscommentList + records[i].data.maxlosscomment + "|";
    	}
    	positionvaluelist = positionvaluelist + records[i].data.positionvalue + "|";
    	
    }
    param = param + '&profitlossidlist=' + modifyId + 
                                '&marketunitpricelist='+modifyValue +
                                '&maxlosscommentList='+maxlosscommentList +
                                '&positionvaluelist=' + positionvaluelist;

	
    new AjaxEngine(contextPath + '/xdss3/profitLoss/profitLossController.spr?action=_saveOrUpdate', 
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
