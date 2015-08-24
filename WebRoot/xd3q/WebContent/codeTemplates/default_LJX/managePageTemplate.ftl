<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: ${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象${beanAttribute.boDescription}(${beanAttribute.boName})管理页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${"$"}${"{"}vt.managePage${"}"}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Manage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/${beanAttribute.webPath}/${beanAttribute.boInstanceName}ManageGen.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_center" class="search"> 
<form id="mainForm" name="mainForm">
<#-- 模版变量定义区域 -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<#list fromColumn as column>
<#if column?exists && (column?size>0)>
<#if column.rowNo%2!=0>
	<tr>
</#if>
		<td width="15%" align="right">${"$"}${"{"}vt.property.${column.propertyName}${"}"}：</td>
		<td <#if column.rowNo%2!=0> width="30%" <#else> width="40%" </#if>>
		<!-- UITYPE:${column.uiType} -->
		<#-- 搜索帮助 -->
		<#if column.uiType=="11">
			<div id="div_${column.propertyName}_sh"></div>
			<input type="hidden" id="${column.propertyName}.fieldName" name="${column.propertyName}.fieldName" value="${column.property.tableName}.${column.property.columnName}"> 
			<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="${column.dataType}">  
			<input type="hidden" id="${column.propertyName}.option" name="${column.propertyName}.option" value="like">
			<fisc:searchHelp divId="div_${column.propertyName}_sh" boName="${beanAttribute.boName}" boProperty="${column.propertyName}" searchType="field" hiddenName="${column.propertyName}.fieldValue" valueField="${column.property.shSourceColumn}" displayField="${column.property.shTextColumn}"></fisc:searchHelp>
		<#-- 下拉列表(字典表) -->
		<#elseif column.uiType=="06">
			<div id="div_${column.propertyName}_dict"></div>
			<input type="hidden" id="${column.propertyName}.fieldName" name="${column.propertyName}.fieldName" value="${column.property.tableName}.${column.property.columnName}"> 
			<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="${column.dataType}">  
			<input type="hidden" id="${column.propertyName}.option" name="${column.propertyName}.option" value="like">
			<fisc:dictionary hiddenName="${column.propertyName}.fieldValue" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_dict" isNeedAuth="false" ></fisc:dictionary>
		<#-- 日期 -->
		<#elseif column.uiType=="04" || column.uiType=="05">
			<input type="text" id="${column.propertyName}.fieldValue" name="${column.propertyName}.fieldValue" value="">
			<input type="hidden" id="${column.propertyName}.fieldName" name="${column.propertyName}.fieldName" value="${column.property.tableName}.${column.property.columnName}"> 
			<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="D">  
			<input type="hidden" id="${column.propertyName}.option" name="${column.propertyName}.option" value="like">
			<#if column.uiType=="04">
				<fisc:calendar applyTo="${column.propertyName}.fieldValue"  divId="" fieldName="" ></fisc:calendar>
			<#elseif column.uiType=="05">
				<fisc:calendar applyTo="${column.propertyName}.fieldValue"  divId="" fieldName="" showTime="true"></fisc:calendar>
			</#if>
		<#-- 其他UI类别 -->
		<#else>
			<input type="text" class="inputText" id="${column.propertyName}.fieldValue" name="${column.propertyName}.fieldValue" value="" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>
			<input type="hidden" id="${column.propertyName}.fieldName" name="${column.propertyName}.fieldName" value="${column.property.tableName}.${column.property.columnName}"> 
			<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="${column.dataType}">  
			<input type="hidden" id="${column.propertyName}.option" name="${column.propertyName}.option" value="like">
		</#if>
		</td>
<#if column.rowNo%2==0>
</tr>
</#if>
</#if> 	
</#list>
<#if countRow%2!=0>
    <td></td>
    <td></td>
	</tr>
</#if>
<#list fromColumnRange as column>
<#if column?exists && (column?size>0)>
	<tr>
		<td width="15%" align="right">${"$"}${"{"}vt.property.${column.propertyName}${"}"} ${"$"}${"{"}vt.sFrom${"}"}：</td>
		<td width="30%" >
			<input type="hidden" id="${column.propertyName}.fieldName" name="${column.propertyName}.fieldName" value="${column.property.tableName}.${column.property.columnName}"> 
			<input type="hidden" id="${column.propertyName}.isRangeValue" name="${column.propertyName}.isRangeValue" value="true">
			<!-- UITYPE:${column.uiType} -->
	<#-- 日期 -->
    <#if column.uiType=="04"> 
    		<input type="text" id="${column.propertyName}_minValue" name="${column.propertyName}.minValue">
    		<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="D"> 
			<fisc:calendar applyTo="${column.propertyName}_minValue"  divId="" fieldName=""></fisc:calendar>
		</td>
		<td width="15%"  align="right">${"$"}${"{"}vt.sTo${"}"}：</td>
		<td width="40%">
			<input type="text" id="${column.propertyName}_maxValue" name="${column.propertyName}.maxValue">			
			<fisc:calendar applyTo="${column.propertyName}_maxValue"  divId="" fieldName=""></fisc:calendar>
		</td>
	<#-- 日期 时间-->
	<#elseif column.uiType=="05">
		   <input type="text" id="${column.propertyName}_minValue" name="${column.propertyName}.minValue">
    		<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="D"> 		   
		   <fisc:calendar applyTo="${column.propertyName}_minValue"  divId="" fieldName="" showTime="true"></fisc:calendar>
	   </td>
		<td width="15%"  align="right">${"$"}${"{"}vt.sTo${"}"}：</td>
		<td width="40%">
			<input type="text" id="${column.propertyName}_maxValue" name="${column.propertyName}.maxValue">
			<fisc:calendar applyTo="${column.propertyName}_maxValue"  divId="" fieldName="" showTime="true"></fisc:calendar>
		</td>
	<#-- 搜索帮助 -->
	<#elseif column.uiType=="11">
    		<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="${column.dataType}"> 
			<div id="div_${column.propertyName}_minValue_sh"></div>
			<fisc:searchHelp divId="div_${column.propertyName}_minValue_sh" boName="${beanAttribute.boName}" boProperty="${column.propertyName}" searchType="field" hiddenName="${column.propertyName}.minValue" valueField="${column.property.shSourceColumn}" displayField="${column.property.shTextColumn}"></fisc:searchHelp>
		</td>
		<td width="15%" align="right">${"$"}${"{"}vt.sTo${"}"}：</td>
		<td width="40%">
			<div id="div_${column.propertyName}_maxValue_sh"></div>
			<fisc:searchHelp divId="div_${column.propertyName}_maxValue_sh" boName="${beanAttribute.boName}" boProperty="${column.propertyName}" searchType="field" hiddenName="${column.propertyName}.maxValue" valueField="${column.property.shSourceColumn}" displayField="${column.property.shTextColumn}"></fisc:searchHelp>
		</td>
	<#-- 下拉列表(字典表) -->
	<#elseif column.uiType=="06">
			<div id="div_${column.propertyName}_dict"></div>
			<input type="hidden" id="${column.propertyName}.fieldName" name="${column.propertyName}.fieldName" value="${column.property.tableName}.${column.property.columnName}"> 
			<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="${column.dataType}">  
			<input type="hidden" id="${column.propertyName}.option" name="${column.propertyName}.option" value="like">
			<fisc:dictionary sourceName="${column.propertyName}.fieldValue" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_dict" isNeedAuth="false" ></fisc:dictionary>
	<#-- 其他UI类别 -->
	<#else>
			<input type="text" class="inputText" id="${column.propertyName}_minValue" name="${column.propertyName}.minValue" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>
		</td>
		<td width="15%" align="right">${"$"}${"{"}vt.sTo${"}"}：</td>
		<td width="40%">
			<input type="text" class="inputText" id="${column.propertyName}_maxValue" name="${column.propertyName}.maxValue" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>
		</td>
	</#if>
	</tr>
</#if>
</#list>
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
<script type="text/javascript" defer="defer">
<#if bo.methods?exists && (bo.methods?size>0)>
<#list bo.methods as modelPro>
<#if modelPro.methodName=="_manage">
<#if modelPro.methodParameters?exists && (modelPro.methodParameters?size>0)>
//开始取得从_manage网址上带过来的参数，并传递给控制器_create方法。
<#list modelPro.methodParameters as methodParameter>
var ${methodParameter.parameterName}='${"$"}${"{"}${methodParameter.parameterName}${"}"}';
</#list>
</#if>
</#if>
</#list>
</#if>

//页面文本
var Txt={
	// 采购订单
	${beanAttribute.boInstanceName}:'${"$"}${"{"}vt.boText${"}"}',
<#list gridToolbars as toolbar>
<#if toolbar?exists && (toolbar?size>0)>
<#if toolbar.methodName=="_deletes" >	
	// 您选择了【${beanAttribute.boDescription}批量删除】操作，是否确定继续该操作？
	${beanAttribute.boInstanceName}_Deletes_ConfirmOperation:'${"$"}${"{"}vt.deletes_ConfirmOperation${"}"}',
	// 请选择需要进行【${beanAttribute.boDescription}批量删除】操作的记录！
	${beanAttribute.boInstanceName}_Deletes_SelectToOperation:'${"$"}${"{"}vt.deletes_SelectToOperate${"}"}',
<#elseif toolbar.methodName=="_copyCreate">	
	// 复制创建
	${beanAttribute.boInstanceName}_CopyCreate:'${"$"}${"{"}vt.boTextCopyCreate${"}"}',
	// 复制创建
	mCopyCreate:'${"$"}${"{"}vt.mCopyCreate${"}"}',
	// 请选择需要进行【${beanAttribute.boDescription}复制创建】操作的记录！
	${beanAttribute.boInstanceName}_CopyCreate_SelectToOperation:'${"$"}${"{"}vt.copyCreate_SelectToOperate${"}"}',
	// 进行【${beanAttribute.boDescription}复制创建】操作时，只允许选择一条记录！
	${beanAttribute.boInstanceName}_CopyCreate_AllowOnlyOneItemForOperation:'${"$"}${"{"}vt.copyCreate_AllowOnlyOneItemForOperation${"}"}',
<#elseif toolbar.methodName=="_create">	
	${beanAttribute.boInstanceName}_Create:'${"$"}${"{"}vt.boTextCreate${"}"}',
</#if>
</#if>
</#list>
<#list fromColumnRange as column>
<#if column?exists && (column?size>0)>	
<#if column.uiType=="04" ||  column.uiType=="05" > 
	// ${column.property.fieldText},结束日期要大于起始日期!
	${column.propertyName}_EndDateShouldLargerStartDate:'${"$"}${"{"}vt.endDateShouldLargerStartDate.${column.propertyName}${"}"}',
<#elseif column.dataType=='N'>
    // ${column.property.fieldText},必须为数字!
	${column.propertyName}_shouldBeNumber:'${"$"}${"{"}vt.shouldBeNumber.${column.propertyName}${"}"}',
	// [${column.property.fieldText} 从]，必须小于[${column.property.fieldText} 到]！
	${column.propertyName}_fromShouldLessThanTo:'${"$"}${"{"}vt.fromShouldLessThanTo.${column.propertyName}${"}"}',
</#if>
</#if>
</#list>
	// 您选择了【${beanAttribute.boDescription}删除】操作，是否确定继续该操作？
	${beanAttribute.boInstanceName}_Delete_ConfirmOperation:'${"$"}${"{"}vt.delete_ConfirmOperation${"}"}',
	// 提示
	cue:'${"$"}${"{"}vt.sCue${"}"}',
	// 确定
	ok:'${"$"}${"{"}vt.sOk${"}"}',
	// 取消
	cancel:'${"$"}${"{"}vt.sCancel${"}"}',
	// 创建
	mCreate:'${"$"}${"{"}vt.mCreate${"}"}'
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
		            title:'${"$"}${"{"}vt.boTextDetail${"}"}',
		            height:380,
		            bodyStyle:"background-color:#DFE8F6",
					contentEl:'div_southForm'
				}]
			}]
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'${"$"}${"{"}vt.mQuery${"}"}',handler:_search,iconCls:'icon-search'},'-',
				{id:'_cls',text:'${"$"}${"{"}vt.sClean${"}"}',handler:_resetForm,iconCls:'icon-clean'},'-'
				],
		renderTo:"div_toolbar"
	});
});
</script>
<fisc:grid divId="div_southForm" boName="${beanAttribute.boName}" <#if isDefaultCondition>defaultCondition="${"$"}${"{"}sqlWhere${"}"}"</#if> editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true"></fisc:grid>
