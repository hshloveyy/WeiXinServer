<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月08日 10点16分21秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象模版预算项(BudgetTemplateItem)编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.editPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetTemplateItem/budgetTemplateItemEdit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetTemplateItem/budgetTemplateItemEditGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.buditemid}：</td>
		<td width="30%" >
			<div id="div_buditemid_sh"></div>
			<fisc:searchHelp divId="div_buditemid_sh" boName="BudgetTemplateItem" boProperty="buditemid" value="${budgetTemplateItem.buditemid}"></fisc:searchHelp>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.seq}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BudgetTemplateItem.seq" name="BudgetTemplateItem.seq" value="${budgetTemplateItem.seq}" <fisc:authentication sourceName="BudgetTemplateItem.seq" taskId=""/>>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.budconcycle}：</td>
		<td width="30%" >
			<div id="div_budconcycle_dict"></div>
			<fisc:dictionary boName="BudgetTemplateItem" boProperty="budconcycle" dictionaryName="YDBUDCONCYCLE" divId="div_budconcycle_dict" isNeedAuth="false"  value="${budgetTemplateItem.budconcycle}"></fisc:dictionary>
		</td>
		<td width="20%" align="right" >${vt.property.subject}：</td>
		<td width="30%" >
			<div id="div_subject_sh"></div>
			<fisc:searchHelp divId="div_subject_sh" boName="BudgetTemplateItem" boProperty="subject" value="${budgetTemplateItem.subject}"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.status}：</td>
		<td width="30%" >
			<div id="div_status_dict"></div>
			<fisc:dictionary boName="BudgetTemplateItem" boProperty="status" dictionaryName="YDBUDSTATUS" divId="div_status_dict" isNeedAuth="false"  value="${budgetTemplateItem.status}"></fisc:dictionary>
		</td>
	      <td></td><td></td>
		</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.creator}：</td>
		<td width="30%" >
			<fisc:user boProperty="creator" boName="BudgetTemplateItem" userId="${budgetTemplateItem.creator}"></fisc:user>		
		</td>
		<td width="20%" align="right" >${vt.property.createtime}：</td>
		<td width="30%" >
			<input type="text" id="BudgetTemplateItem.createtime" name="BudgetTemplateItem.createtime" value="">
				<fisc:calendar applyTo="BudgetTemplateItem.createtime" format="Ymd" divId="" fieldName=""  defaultValue="${budgetTemplateItem.createtime}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.lastmodifyer}：</td>
		<td width="30%" >
			<fisc:user boProperty="lastmodifyer" boName="BudgetTemplateItem" userId="${budgetTemplateItem.lastmodifyer}"></fisc:user>		
		</td>
		<td width="20%" align="right" >${vt.property.lastmodifytime}：</td>
		<td width="30%" >
			<input type="text" id="BudgetTemplateItem.lastmodifytime" name="BudgetTemplateItem.lastmodifytime" value="">
				<fisc:calendar applyTo="BudgetTemplateItem.lastmodifytime" format="Ymd" divId="" fieldName=""  defaultValue="${budgetTemplateItem.lastmodifytime}"></fisc:calendar>
		</td>
	</tr>
<input type="hidden" id="BudgetTemplateItem.budtemitemid" name="BudgetTemplateItem.budtemitemid" value="">
<input type="hidden" id="BudgetTemplateItem.budtemid" name="BudgetTemplateItem.budtemid" value="">
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
							height:562.5,
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
