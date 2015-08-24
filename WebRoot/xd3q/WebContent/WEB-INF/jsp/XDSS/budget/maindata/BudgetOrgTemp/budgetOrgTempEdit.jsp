<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月26日 10点10分26秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象预算组织模版(BudgetOrgTemp)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetOrgTemp/budgetOrgTempEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetOrgTemp/budgetOrgTempEditGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.budclassid}：</td>
		<td width="30%" >
			<div id="div_budclassid_sh"></div>
			<fisc:searchHelp divId="div_budclassid_sh" boName="BudgetOrgTemp" boProperty="budclassid" value="${budgetOrgTemp.budclassid}" defaultCondition="ISACTIVE = 'Y'"></fisc:searchHelp>
		</td>
	      <td></td><td></td>
		</tr>
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.weastartdate}：</td>
		<td width="30%" >
			<input type="text" id="BudgetOrgTemp.weastartdate" name="BudgetOrgTemp.weastartdate" value="">
				<fisc:calendar applyTo="BudgetOrgTemp.weastartdate" format="Ymd" divId="" fieldName=""  defaultValue="${budgetOrgTemp.weastartdate}"></fisc:calendar>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.weaentdate}：</td>
		<td width="30%" >
			<input type="text" id="BudgetOrgTemp.weaentdate" name="BudgetOrgTemp.weaentdate" value="">
				<fisc:calendar applyTo="BudgetOrgTemp.weaentdate" format="Ymd" divId="" fieldName=""  defaultValue="${budgetOrgTemp.weaentdate}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.creator}：</td>
		<td width="30%" >
			<fisc:user boProperty="creator" boName="BudgetOrgTemp" userId="${budgetOrgTemp.creator}"></fisc:user>		
		</td>
		<td width="20%" align="right" >${vt.property.createtime}：</td>
		<td width="30%" >
			<input type="text" id="BudgetOrgTemp.createtime" name="BudgetOrgTemp.createtime" value="">
				<fisc:calendar applyTo="BudgetOrgTemp.createtime" format="Ymd" divId="" fieldName=""  defaultValue="${budgetOrgTemp.createtime}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.lastmodifyer}：</td>
		<td width="30%" >
			<fisc:user boProperty="lastmodifyer" boName="BudgetOrgTemp" userId="${budgetOrgTemp.lastmodifyer}"></fisc:user>		
		</td>
		<td width="20%" align="right" >${vt.property.lastmodifytime}：</td>
		<td width="30%" >
			<input type="text" id="BudgetOrgTemp.lastmodifytime" name="BudgetOrgTemp.lastmodifytime" value="">
				<fisc:calendar applyTo="BudgetOrgTemp.lastmodifytime" format="Ymd" divId="" fieldName=""  defaultValue="${budgetOrgTemp.lastmodifytime}"></fisc:calendar>
		</td>
	</tr>
<input type="hidden" id="BudgetOrgTemp.budorgtemid" name="BudgetOrgTemp.budorgtemid" value="">
<input type="hidden" id="BudgetOrgTemp.budorgid" name="BudgetOrgTemp.budorgid" value="">
</table>
</form>
</div>
<div id="div_toolbar" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//页面文本
var Txt={
	// '提交成功！'
	submitSuccess:'${vt.submitSuccess}',
	// '操作成功！'
	operateSuccess:'${vt.operateSuccess}',
	// 确定
	ok:'${vt.sOk}',
	// 取消
	cancel:'${vt.sCancel}'
};

var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					{id:'_save',text:'${vt.sOk}',handler:_save,iconCls:'icon-table-save'},'-',
					{id:'_cancel',text:'${vt.sCancel}',handler:_cancel,iconCls:'icon-undo'},'-',
					' '
				   ],
			renderTo:'div_toolbar'
});
	
/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[{
					region:'north',
					layout:'fit',
					height:26,
					border:false,
					contentEl:'div_toolbar'
	   		   },{
					region:'center',
					border:false,
					buttonAlign:'center',
					items:[{
							layout:'fit',
							region:'center',
							height:477.5,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
