<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>
--%>
<#-- 一、标准对象管理页面Form。############################################-->
<#-- 生成Manage页面Form上查询字段-->
<#macro generatorCommonObjectTypeManageForm >
<!-- commonObjectTypeManageForm.ftl -->
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<#if bo.formSearchColumn?exists && (bo.formSearchColumn?size>0)>
<#-- 普通查询字段-->
<#list bo.formSearchColumn as column>
<#if column.rowNo%2!=0>
	<tr>
</#if>
		<td width="15%" align="right">${"$"}${"{"}vt.property.${column.propertyName}${"}"}：</td>
		<td <#if column.rowNo%2!=0> width="30%" <#else> width="40%" </#if>>
		<#-- UITYPE:${column.uiType} -->
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
			<fisc:dictionary hiddenName="${column.propertyName}.fieldValue" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_dict" isNeedAuth="false"  <#if column.property.dictGroupValue?exists && column.property.dictGroupValue!=" " && column.property.dictGroupValue!=""> groupValue="${column.property.dictGroupValue}" </#if>></fisc:dictionary>
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
</#list>
</#if> 	
<#if countRow%2!=0>
    <td></td>
    <td></td>
	</tr>
</#if>
<#-- 区间查询字段-->
<#if bo.formSearchColumnRange?exists && (bo.formSearchColumnRange?size>0)>
<#list bo.formSearchColumnRange as column>
	<tr>
		<td width="15%" align="right">${"$"}${"{"}vt.property.${column.propertyName}${"}"} ${"$"}${"{"}vt.sFrom${"}"}：</td>
		<td width="30%" >
			<input type="hidden" id="${column.propertyName}.fieldName" name="${column.propertyName}.fieldName" value="${column.property.tableName}.${column.property.columnName}"> 
			<input type="hidden" id="${column.propertyName}.isRangeValue" name="${column.propertyName}.isRangeValue" value="true">
			<#-- UITYPE:${column.uiType} -->
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
	    	<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="${column.dataType}"> 
			<div id="div_${column.propertyName}_minValuedict"></div>
			<fisc:dictionary hiddenName="${column.propertyName}.minValue" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_minValuedict" isNeedAuth="false"  <#if column.property.dictGroupValue?exists && column.property.dictGroupValue!=" " && column.property.dictGroupValue!=""> groupValue="${column.property.dictGroupValue}" </#if>></fisc:dictionary>
		</td>
		<td width="15%" align="right">${"$"}${"{"}vt.sTo${"}"}：</td>
		<td width="40%">
			<div id="div_${column.propertyName}_maxValuedict"></div>
			<fisc:dictionary hiddenName="${column.propertyName}.maxValue" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_maxValuedict" isNeedAuth="false"  <#if column.property.dictGroupValue?exists && column.property.dictGroupValue!=" " && column.property.dictGroupValue!=""> groupValue="${column.property.dictGroupValue}" </#if>></fisc:dictionary>
		</td>
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
</#list>
</#if>
	<tr>
		<td align="center" colspan="4" height="10">
		</td>
	</tr>
</table>
</#macro>

<#-- 二、标准对象管理页面Ext布局和系统文本。############################################-->
<#-- 生成标准对象管理页面Ext布局和系统文本-->
<#macro generatorManagePageExtLayOutAndSysTxt>
var isCreateCopy=false;
//全局路径
var context = '<%= request.getContextPath() %>';

//页面文本
var Txt={
	// 采购订单
	${beanAttribute.boInstanceName}:'${"$"}${"{"}vt.boText${"}"}',
<#if bo.gridToolbars?exists && (bo.gridToolbars?size>0)>
<#list bo.gridToolbars as toolbar>
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
</#list>
</#if>
<#if bo.formSearchColumnRange?exists && (bo.formSearchColumnRange?size>0)>	
<#list bo.formSearchColumnRange as column>
<#if column.uiType=="04" ||  column.uiType=="05" > 
	// ${column.property.fieldText},结束日期要大于起始日期!
	${column.propertyName}_EndDateShouldLargerStartDate:'${"$"}${"{"}vt.endDateShouldLargerStartDate.${column.propertyName}${"}"}',
<#elseif column.dataType=='N'>
    // ${column.property.fieldText},必须为数字!
	${column.propertyName}_shouldBeNumber:'${"$"}${"{"}vt.shouldBeNumber.${column.propertyName}${"}"}',
	// [${column.property.fieldText} 从]，必须小于[${column.property.fieldText} 到]！
	${column.propertyName}_fromShouldLessThanTo:'${"$"}${"{"}vt.fromShouldLessThanTo.${column.propertyName}${"}"}',
</#if>
</#list>
</#if>
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
</#macro>

<#-- 三、标准对象管理页面tag生成。############################################-->
<#-- 生成标准对象管理页面tag-->
<#macro generatorManagePageTag>
<fisc:grid divId="div_southForm" boName="${beanAttribute.boName}" <#if isDefaultCondition>defaultCondition="${"$"}${"{"}sqlWhere${"}"}"</#if> editable="false" needCheckBox="true" needToolbar="true" needAutoLoad="true" <#if bo.gridOrderBySql?exists && bo.gridOrderBySql!="" && bo.gridOrderBySql!=" "> orderBySql=" ${bo.gridOrderBySql}"</#if>></fisc:grid>
</#macro>