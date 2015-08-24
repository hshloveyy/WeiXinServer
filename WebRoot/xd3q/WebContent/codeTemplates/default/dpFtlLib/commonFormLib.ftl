<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>
--%>
<#import "/dpFtlLib/subBoDivAndFormLib.ftl" as subBoDivAndFormLib>

<#-- 一、生成主对象页面Form表单区域############################################-->
<#macro generatorMainBoForm page>
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<#if bo.formColumnListHeader?exists && (bo.formColumnListHeader?size>0)>
<#list bo.formColumnListHeader?sort_by("formRowNo") as column>
<#-- column.visibility=="X" ${bo.formColumnListHeader?size}(调试用)序号:${column_index};formRowNo:${column.formRowNo} ;rowNo: ${column.rowNo};columnNo: ${column.columnNo};${column.propertyType?if_exists} -->
<#if column.propertyType !="XX" && column.visibility=="X" && column.propertyType!="HIDDEN">
<#if column.formColumnNo==1 || column.uiType=="12">
	<tr>
</#if>
<#-- column.property.numberObjectId column.visibility=="X" ${bo.formColumnListHeader?size}(调试用)序号:${column_index};formRowNo:${column.formRowNo} ;rowNo: ${column.rowNo};columnNo: ${column.columnNo};${column.propertyType?if_exists} -->
		<td align="right"  width="15%" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" nodeId="${"$"}${"{"}workflowNodeDefId${"}"}"/> <#if column.uiType=="12">valign="top"</#if>><#if column.nullable=="X" && (column.property.numberObjectId=="" || column.property.numberObjectId==" ")><font color="red">★</font></#if><#if column.visibility=="X">${"$"}${"{"}vt.property.${column.propertyName}${"}"}：</#if></td>
		<td  <#if column.formColumnNo==1 || column.uiType=="12">width="30%"<#else> width="40%"</#if> <#if column.uiType=="12"> colspan="3" </#if>>
		<#-- 搜索帮助 -->
		<#if column.uiType=="11">
			<div id="div_${column.propertyName}_sh"></div>
			<fisc:searchHelp divId="div_${column.propertyName}_sh" boName="${beanAttribute.boName}" boProperty="${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:searchHelp>
		<#-- 下拉列表(字典表) -->
		<#elseif column.uiType=="06">
			<div id="div_${column.propertyName}_dict"></div>
			<fisc:dictionary boName="${beanAttribute.boName}" boProperty="${column.propertyName}" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_dict" isNeedAuth="false"  value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.property.dictGroupValue?exists && column.property.dictGroupValue!=" " && column.property.dictGroupValue!=""> groupValue="${column.property.dictGroupValue}" </#if> <#if column.property.subBoRelPro?exists && column.property.subBoRelPro!=" " && column.property.subBoRelPro!="">parentBoProperty="${column.property.subBoRelPro}"</#if>></fisc:dictionary>
		<#-- 日期 -->
		<#elseif column.uiType=="04" || column.uiType=="05">
		<#if column.readOnly?exists && column.readOnly!="" && column.readOnly!=" ">
		    <input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  readonly="readonly" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" nodeId="${"$"}${"{"}workflowNodeDefId${"}"}"/>>
		<#else>
			<input type="text" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="">
			<#if column.uiType=="04">
				<fisc:calendar applyTo="${beanAttribute.boName}.${column.propertyName}"  divId="" fieldName="" defaultValue="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:calendar>
			<#elseif column.uiType=="05">
				<fisc:calendar applyTo="${beanAttribute.boName}.${column.propertyName}"  divId="" fieldName="" showTime="true" defaultValue="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:calendar>
			</#if>
		</#if>
		<#-- 密码框 -->
		<#elseif column.uiType=="03">
			<input class="inputText" type="password" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.visibility!="X"> style="visibility: hidden;" <#else><fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" nodeId="${"$"}${"{"}workflowNodeDefId${"}"}"/></#if>>									
		<#-- 备注框 -->
		<#elseif column.uiType=="12">
			<textarea rows="4" cols="54" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" nodeId="${"$"}${"{"}workflowNodeDefId${"}"}"/>>${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}</textarea>
		<#-- USER系统参数 -->
		<#elseif column.uiType=="14">
			<fisc:user boProperty="${column.propertyName}" boName="${beanAttribute.boName}" userId="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:user>
		<#-- 其他UI类别 -->
		<#else>
			<input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.visibility!="X"> style="visibility: hidden;" <#else><fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" nodeId="${"$"}${"{"}workflowNodeDefId${"}"}"/></#if> <#if column.property.numberObjectId?exists && column.property.numberObjectId!="" && column.property.numberObjectId!=" "> readonly="readonly"</#if>>
		</#if>
		</td>
<#if column.formColumnNo ==2 || column.uiType=="12">
	</tr>
</#if>
<#elseif column.propertyType=="XX">
<#if column.formColumnNo == 1>
	<tr>
</#if>
        <td></td>
        <td></td>
<#if column.formColumnNo == 2>
	</tr>
</#if>
</#if>
</#list>
</#if>

<#-- Hidden元素  columnHidden.visibility?if_exists !="X"-->
<#if bo.formColumnList?exists && (bo.formColumnList?size>0)>
<#list bo.formColumnList as columnHidden>
<#if columnHidden.uiType?if_exists =="13" || columnHidden.visibility?if_exists !="X">
	<input type="hidden" id="${beanAttribute.boName}.${columnHidden.propertyName}" name="${beanAttribute.boName}.${columnHidden.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${columnHidden.propertyName}${"}"}">
</#if>
</#list>
</#if>
<#if page="addPage">
	<input type="hidden" id="calActivityId" name="calActivityId" value="${"$"}${"{"}calActivityId${"}"}">
</#if>
</table>
</#macro>

<#-- 二、生成业务对象tabPanel, Form表单区域############################################-->
<#macro generatorBoTabPanelForm>
<#-- 生成业务对象标签页div ，如果formColumn有设置的抬头为false的表单元素 -->
<#if bo.formColumnListTab?exists && (bo.formColumnListTab?size>0)>
<div id="div_${beanAttribute.boInstanceName}" class="x-hide-display"> 
<form id="${beanAttribute.boInstanceName}Form" name="${beanAttribute.boInstanceName}Form"  class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
	<#list bo.formColumnListTab?sort_by("formRowNo") as column>
	 <#--(调试用${bo.formColumnListTab?size})序号:${column_index};formRowNo:${column.formRowNo}; rowNo: ${column.rowNo};columnNo: ${column.columnNo};${column.propertyType?if_exists}-->
		<#if column.propertyType !="XX" && column.visibility=="X" && column.propertyType!="HIDDEN">
		<#if column.formColumnNo==1 || column.uiType=="12">
			<tr>
		</#if>
				<td align="right"  width="15%" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" nodeId="${"$"}${"{"}workflowNodeDefId${"}"}"/> <#if column.uiType=="12">valign="top"</#if>><#if column.nullable=="X"><font color="red">★</font></#if><#if column.visibility=="X">${"$"}${"{"}vt.property.${column.propertyName}${"}"}：</#if></td>
				<td  <#if column.formColumnNo==1 || column.uiType=="12">width="30%"<#else> width="40%"</#if> <#if column.uiType=="12"> colspan="3" </#if>>
				<#-- UITYPE:${column.uiType} -->
				<#-- 搜索帮助 -->
				<#if column.uiType=="11">
					<div id="div_${column.propertyName}_sh"></div>
					<fisc:searchHelp divId="div_${column.propertyName}_sh" boName="${beanAttribute.boName}" boProperty="${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:searchHelp>
				<#-- 下拉列表(字典表) -->
				<#elseif column.uiType=="06">
					<div id="div_${column.propertyName}_dict"></div>
					<fisc:dictionary boName="${beanAttribute.boName}" boProperty="${column.propertyName}" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_dict" isNeedAuth="false" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.property.dictGroupValue?exists && column.property.dictGroupValue!=" " && column.property.dictGroupValue!=""> groupValue="${column.property.dictGroupValue}" </#if> <#if column.property.subBoRelPro?exists && column.property.subBoRelPro!=" " && column.property.subBoRelPro!="">parentBoProperty="${column.property.subBoRelPro}"</#if>></fisc:dictionary>
				<#-- 日期 -->
				<#elseif column.uiType=="04" || column.uiType=="05">
				<#if column.readOnly?exists && column.readOnly!="" && column.readOnly!=" ">
				    <input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  readonly="readonly" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" nodeId="${"$"}${"{"}workflowNodeDefId${"}"}"/>>
				<#else>
					<input type="text" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="">
					<#if column.uiType=="04">
						<fisc:calendar applyTo="${beanAttribute.boName}.${column.propertyName}"  divId="" fieldName=""  defaultValue="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:calendar>
					<#elseif column.uiType=="05">
						<fisc:calendar applyTo="${beanAttribute.boName}.${column.propertyName}"  divId="" fieldName="" showTime="true"  defaultValue="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:calendar>
					</#if>
				</#if>
				<#-- 密码框 -->
				<#elseif column.uiType=="03">
					<input class="inputText" type="password" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" nodeId="${"$"}${"{"}workflowNodeDefId${"}"}"/>>									
				<#-- 备注框 -->
				<#elseif column.uiType=="12">
					<textarea rows="4" cols="54" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}"  <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" nodeId="${"$"}${"{"}workflowNodeDefId${"}"}"/>>${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}</textarea>
				<#-- USER系统参数 -->
				<#elseif column.uiType=="14">
					<fisc:user boProperty="${column.propertyName}" boName="${beanAttribute.boName}" userId="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:user>				
					<#-- 其他UI类别 -->
				<#else>
					<input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  <#if column.property.numberObjectId?exists && column.property.numberObjectId!="" && column.property.numberObjectId!=" "> readonly="readonly"</#if> <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" nodeId="${"$"}${"{"}workflowNodeDefId${"}"}"/>>
				</#if>
				</td>
			<#if column.formColumnNo==2 || column.uiType=="12">
				</tr>
			</#if>
		<#elseif column.propertyType=="XX">
			<#if column.formColumnNo==1>
				<tr>
			</#if>
			      <td></td><td></td>
			<#if column.formColumnNo==2>
				</tr>
			</#if>
		</#if>
	</#list>
	</table>
	</form>
	</div> 
</#if>
</#macro>


<#-- 三、生成子业务对象DIV以及一比一 Form表单区域############################################-->
<#macro generatorSubBoDivAndForm>
<@subBoDivAndFormLib.generatorSubBoDivAndForm/>
</#macro>

<#-- 四、生成子业务对象Tag标签-->
<#macro generatorSubBoTag>
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
	<#list bo.subBusinessObject as subBo>          <#-- 遍历业务对象下所有子对象 "${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}"-->
		<#if subBo.boName!="Attachement">   
		    <#if subBo.subBoAttribute.subBoRelType=="2">         <#-- 业务对象子对象关系为 1对多 -->
<fisc:grid divId="div_${subBo.beanAttribute.boInstanceName}" boName="${subBo.beanAttribute.boName}" needCheckBox="true" editable="true" defaultCondition=" ${subBo.tableName}.${bo.primaryKeyProperty.columnName}='${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}'" needAutoLoad="true" height="285" nameSapceSupport="true" <#if subBo.gridOrderBySql?exists && subBo.gridOrderBySql!="" && subBo.gridOrderBySql!=" "> orderBySql=" ${subBo.gridOrderBySql}"</#if>></fisc:grid>      
		     <#elseif subBo.subBoAttribute.subBoRelType=="3" || subBo.subBoAttribute.subBoRelType=="4">
<fisc:grid divId="div_${subBo.beanAttribute.boInstanceName}" boName="${subBo.beanAttribute.boName}" needCheckBox="true" editable="false" defaultCondition=" ${subBo.tableName}.${bo.primaryKeyProperty.columnName}='${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}'" needAutoLoad="true" height="285" nameSapceSupport="true" <#if subBo.gridOrderBySql?exists && subBo.gridOrderBySql!="" && subBo.gridOrderBySql!=" "> orderBySql=" ${subBo.gridOrderBySql}"</#if>></fisc:grid>        
		     </#if>
		<#else>
<fisc:attachement businessId="${"$"}${"{"}${"businessId"}${"}"}" allowDelete="true" divId="div_attachement" boName="${beanAttribute.boName}" gridPageSize="10" gridHeight="285" <#if subBo.gridOrderBySql?exists && subBo.gridOrderBySql!="" && subBo.gridOrderBySql!=" "> orderBySql=" ${subBo.gridOrderBySql}"</#if>></fisc:attachement>
		</#if>
	</#list>
<#else>
</#if>
</#macro>
