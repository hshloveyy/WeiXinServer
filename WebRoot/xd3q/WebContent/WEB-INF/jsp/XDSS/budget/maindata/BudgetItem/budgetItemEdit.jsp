<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月16日 11点36分09秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象预算项(BudgetItem)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetItem/budgetItemEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetItem/budgetItemEditGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.buditemname}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BudgetItem.buditemname" name="BudgetItem.buditemname" value="${budgetItem.buditemname}" <fisc:authentication sourceName="BudgetItem.buditemname" taskId=""/>>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.budguidetype}：</td>
		<td width="30%" >
			<div id="div_budguidetype_dict"></div>
			<fisc:dictionary boName="BudgetItem" boProperty="budguidetype" dictionaryName="YDBUDGUIDETYPE" divId="div_budguidetype_dict" isNeedAuth="false"  value="${budgetItem.budguidetype}" ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.budfareguide}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BudgetItem.budfareguide" name="BudgetItem.budfareguide" value="${budgetItem.budfareguide}" <fisc:authentication sourceName="BudgetItem.budfareguide" taskId=""/>>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.buditemtype}：</td>
		<td width="30%" >
			<div id="div_buditemtype_dict"></div>
			<fisc:dictionary boName="BudgetItem" boProperty="buditemtype" dictionaryName="YDBUDITEMTYPE" divId="div_buditemtype_dict" isNeedAuth="false"  value="${budgetItem.buditemtype}" ></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" valign="top">${vt.property.weadesc}：</td>
		<td width="30%"  colspan="3" >
			<textarea rows="4" cols="54" id="BudgetItem.weadesc" name="BudgetItem.weadesc"  <fisc:authentication sourceName="BudgetItem.weadesc" taskId=""/>>${budgetItem.weadesc}</textarea>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.budupitemid}：</td>
		<td width="30%" >
			<div id="div_budupitemid_sh"></div>
			<fisc:searchHelp divId="div_budupitemid_sh" boName="BudgetItem" boProperty="budupitemid" value="${budgetItem.budupitemid}"></fisc:searchHelp>
		</td>
		<td width="20%" align="right" >${vt.property.yearitemid}：</td>
		<td width="30%" >
			<div id="div_yearitemid_sh"></div>
			<fisc:searchHelp divId="div_yearitemid_sh" boName="BudgetItem" boProperty="yearitemid" value="${budgetItem.yearitemid}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.creator}：</td>
		<td width="30%" >
			<fisc:user boProperty="creator" boName="BudgetItem" userId="${budgetItem.creator}"></fisc:user>		
		</td>
		<td width="20%" align="right" >${vt.property.createtime}：</td>
		<td width="30%" >
			<input type="text" id="BudgetItem.createtime" name="BudgetItem.createtime" value="">
				<fisc:calendar applyTo="BudgetItem.createtime" format="Ymd" divId="" fieldName=""  defaultValue="${budgetItem.createtime}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.lastmodifyer}：</td>
		<td width="30%" >
			<fisc:user boProperty="lastmodifyer" boName="BudgetItem" userId="${budgetItem.lastmodifyer}"></fisc:user>		
		</td>
		<td width="20%" align="right" >${vt.property.lastmodifytime}：</td>
		<td width="30%" >
			<input type="text" id="BudgetItem.lastmodifytime" name="BudgetItem.lastmodifytime" value="">
				<fisc:calendar applyTo="BudgetItem.lastmodifytime" format="Ymd" divId="" fieldName=""  defaultValue="${budgetItem.lastmodifytime}"></fisc:calendar>
		</td>
	</tr>
<input type="hidden" id="BudgetItem.buditemid" name="BudgetItem.buditemid" value="">
<input type="hidden" id="BudgetItem.budgroupid" name="BudgetItem.budgroupid" value="">
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
							height:605.0,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
