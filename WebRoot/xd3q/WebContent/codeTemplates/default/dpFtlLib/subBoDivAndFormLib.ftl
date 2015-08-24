<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>
--%>
<#-- 生成子对象div -->
<#macro generatorSubBoDivAndForm>
<#compress> 
<#if bo.subBoTabLayOut?exists && ( bo.subBoTabLayOut?size>0)>
	<#list  bo.subBoTabLayOut?if_exists as subBo>          <#-- 遍历业务对象下所有子对象 -->
    	<#if subBo.subBoAttribute.subBoRelType=="2" || subBo.subBoAttribute.subBoRelType=="3" || subBo.subBoAttribute.subBoRelType=="4">         <#-- 业务对象子对象关系为 1对多、多对多，标签页中为表 -->
			<div id="div_${subBo.beanAttribute.boInstanceName}" ></div>
         <#elseif subBo.subBoAttribute.subBoRelType=="1">    <#-- 业务对象子对象关系为 1对1关系，标签页中为表单域 -->
			<div id="div_${subBo.beanAttribute.boInstanceName}" class="x-hide-display">
			<form id="${subBo.beanAttribute.boInstanceName}Form" name="${subBo.beanAttribute.boInstanceName}Form" class="search">
			<table width="100%" border="0" cellpadding="4" cellspacing="1">
				<#if subBo.formColumnList?exists && (subBo.formColumnList?size>0)>
				<#list subBo.formColumnList?sort_by("formRowNo") as column>
				<#--(调试用)序号:${column_index};formRowNo:${column.formRowNo}; rowNo: ${column.rowNo};columnNo: ${column.columnNo};${column.propertyType?if_exists}-->
				<#if column.propertyType !="XX" && column.visibility=="X" && column.propertyType!="HIDDEN" >
				<#if column.formColumnNo==1 || column.uiType=="12">
					<tr>
				</#if>
						<td align="right" width="15%"<#if column.uiType=="12">valign="top"</#if>><#if column.nullable=="X"><font color="red">★</font></#if><#if column.visibility=="X">${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.property.${column.propertyName}${"}"}：</#if></td>
						<td <#if column.formColumnNo==1 || column.uiType=="12">width="30%"<#else> width="40%"</#if> <#if column.uiType=="12"> colspan="3" </#if>>
						<#-- UITYPE:${column.uiType} -->
						<#-- 搜索帮助 -->
						<#if column.uiType=="11">
							<div id="div_${subBo.boName}${column.propertyName}_sh"></div>
							<fisc:searchHelp divId="div_${subBo.boName}${column.propertyName}_sh" boName="${subBo.boName}" boProperty="${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:searchHelp>
						<#-- 下拉列表(字典表) -->
						<#elseif column.uiType=="06">
							<div id="div_${subBo.boName}${column.propertyName}_dict"></div>
							<fisc:dictionary boName="${subBo.boName}" boProperty="${column.propertyName}" dictionaryName="${column.property.dicTableName}" divId="div_${subBo.boName}${column.propertyName}_dict" isNeedAuth="false" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.property.dictGroupValue?exists && column.property.dictGroupValue!=" " && column.property.dictGroupValue!=""> groupValue="${column.property.dictGroupValue}" </#if> <#if column.property.subBoRelPro?exists && column.property.subBoRelPro!=" " && column.property.subBoRelPro!="">parentBoProperty="${column.property.subBoRelPro}"</#if>></fisc:dictionary>
						<#-- 日期 -->
						<#elseif column.uiType=="04" || column.uiType=="05">
						<#if column.readOnly?exists && column.readOnly!="" && column.readOnly!=" ">
	   						<input type="text" class="inputText" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  readonly="readonly">
						<#else>
							<input type="text" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="">
							<#if column.uiType=="04">
								<fisc:calendar applyTo="${subBo.boName}.${column.propertyName}"  divId="" fieldName="" defaultValue="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:calendar>
							<#elseif column.uiType=="05">
								<fisc:calendar applyTo="${subBo.boName}.${column.propertyName}"  divId="" fieldName="" showTime="true" defaultValue="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:calendar>
							</#if>
						</#if>
						<#-- 密码框 -->
						<#elseif column.uiType=="03">
							<input class="inputText" type="password" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId=""/>>									
						<#-- 备注框 -->
						<#elseif column.uiType=="12">
							<textarea rows="4" cols="54" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}"   <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId=""/> >${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}</textarea>
						<#-- USER系统参数USER系统参数 -->
						<#elseif column.uiType=="14">
							<fisc:user boProperty="${column.propertyName}" boName="${subBo.boName}" userId="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:user>									
						<#-- 其他UI类别 -->
						<#else>
							<#if column.property.columnName?upper_case!= bo.primaryKeyProperty.columnName>
							<input type="text" class="inputText" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  <#if column.property.numberObjectId?exists && column.property.numberObjectId!="" && column.property.numberObjectId!=" "> readonly="readonly"</#if>  <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId=""/>>
							<#else>
							<input type="text" class="inputText" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.property.numberObjectId?exists && column.property.numberObjectId!="" && column.property.numberObjectId!=" "> readonly="readonly"</#if> <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId=""/>>
							</#if>
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
				</#if>
				<#-- Hidden元素 -->
<#if subBo.formColumnList ?exists && (subBo.formColumnList ?size>0)>
				<#list subBo.formColumnList as columnHidden>
					<#if columnHidden.visibility?if_exists !="X" && (columnHidden.propertyName?upper_case!="CLIENT" || columnHidden.property.columnName?upper_case!="MANDT")>
						<#if columnHidden.property.columnName?upper_case!= bo.primaryKeyProperty.columnName>
				<input type="hidden" id="${subBo.boName}.${columnHidden.propertyName}" name="${subBo.boName}.${columnHidden.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${columnHidden.propertyName}${"}"}">
				        <#else>
				<input type="hidden" id="${subBo.boName}.${columnHidden.propertyName}" name="${subBo.boName}.${columnHidden.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${columnHidden.propertyName}${"}"}">
						</#if>
					</#if>
				</#list>
</#if>				
				</table>
				</form>
				</div>                     
	            </#if>
</#list>
<#else>
<div id="div_south" class="x-hide-display"></div>
</#if>
</#compress> 
</#macro>