<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月04日 13点22分33秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象预算模版(BudgetTemplate)增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.addPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetTemplate/budgetTemplateAdd.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/XDSS/budget/maindata/BudgetTemplate/budgetTemplateAddGen.js"></script>
</head>
<body>
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.budtemname}：</td>
		<td width="30%" >
			<input type="text" class="inputText" id="BudgetTemplate.budtemname" name="BudgetTemplate.budtemname" value="${budgetTemplate.budtemname}" <fisc:authentication sourceName="BudgetTemplate.budtemname" taskId=""/>>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.budtemtype}：</td>
		<td width="30%" >
			<div id="div_budtemtype_dict"></div>
			<fisc:dictionary boName="BudgetTemplate" boProperty="budtemtype" dictionaryName="YDBUDTEMTYPE" divId="div_budtemtype_dict" isNeedAuth="false"  value="${budgetTemplate.budtemtype}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.boid}：</td>
		<td width="30%" >
			<div id="div_boid_sh"></div>
			<fisc:searchHelp divId="div_boid_sh" boName="BudgetTemplate" boProperty="boid" value="${budgetTemplate.boid}"></fisc:searchHelp>
		</td>
		<td width="20%" align="right" ><font color="red">★</font>${vt.property.budconcycle}：</td>
		<td width="30%" >
			<div id="div_budconcycle_dict"></div>
			<fisc:dictionary boName="BudgetTemplate" boProperty="budconcycle" dictionaryName="YDBUDCONCYCLE" divId="div_budconcycle_dict" isNeedAuth="false"  value="${budgetTemplate.budconcycle}"></fisc:dictionary>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.creator}：</td>
		<td width="30%" >
			<fisc:user boProperty="creator" boName="BudgetTemplate" userId="${budgetTemplate.creator}"></fisc:user>		
		</td>
		<td width="20%" align="right" >${vt.property.createtime}：</td>
		<td width="30%" >
			<input type="text" id="BudgetTemplate.createtime" name="BudgetTemplate.createtime" value="">
				<fisc:calendar applyTo="BudgetTemplate.createtime" format="Ymd" divId="" fieldName=""  defaultValue="${budgetTemplate.createtime}"></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right" >${vt.property.lastmodifyer}：</td>
		<td width="30%" >
			<fisc:user boProperty="lastmodifyer" boName="BudgetTemplate" userId="${budgetTemplate.lastmodifyer}"></fisc:user>		
		</td>
		<td width="20%" align="right" >${vt.property.lastmodifytime}：</td>
		<td width="30%" >
			<input type="text" id="BudgetTemplate.lastmodifytime" name="BudgetTemplate.lastmodifytime" value="">
				<fisc:calendar applyTo="BudgetTemplate.lastmodifytime" format="Ymd" divId="" fieldName=""  defaultValue="${budgetTemplate.lastmodifytime}"></fisc:calendar>
		</td>
	</tr>
<input type="hidden" id="BudgetTemplate.budtemid" name="BudgetTemplate.budtemid" value="">
<input type="hidden" id="BudgetTemplate.budgetSort.budsortid" name="BudgetTemplate.budgetSort.budsortid" value="${budgetTemplate.budgetSort.budsortid}">
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
