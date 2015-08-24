﻿<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: ${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象${beanAttribute.boDescription}(${beanAttribute.boName})编辑页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${"$"}${"{"}vt.editPage${"}"}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Edit.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/${beanAttribute.webPath}/${beanAttribute.boInstanceName}EditGen.js"></script>
</head>
<body>
<#-- 开始生成Tag-->
<#compress> 
<#if subBoList?exists && (subBoList?size>0)>
	<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象 "${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}"-->
		<#if subBo.boName!="Attachement">   
		    <#if subBo.subBoAttribute.subBoRelType=="2">         <#-- 业务对象子对象关系为 1对多 -->
<fisc:grid divId="div_${subBo.beanAttribute.boInstanceName}" boName="${subBo.beanAttribute.boName}" needCheckBox="true" editable="true" defaultCondition=" ${subBo.tableName}.${bo.primaryKeyProperty.columnName}='${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>      
		     <#elseif subBo.subBoAttribute.subBoRelType=="3" || subBo.subBoAttribute.subBoRelType=="4">
<fisc:grid divId="div_${subBo.beanAttribute.boInstanceName}" boName="${subBo.beanAttribute.boName}" needCheckBox="true" editable="false" defaultCondition=" ${subBo.tableName}.${bo.primaryKeyProperty.columnName}='${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>        
		     </#if>
		<#else>
<fisc:attachement businessId="${"$"}${"{"}${"businessId"}${"}"}" allowDelete="true" divId="div_attachement" boName="${beanAttribute.boName}" gridPageSize="10" gridHeight="285"></fisc:attachement>
		</#if>
	</#list>
<#else>
</#if>
<#if isHaveRelationship>
<div id="div_east" class="x-hide-display"></div>
<fisc:relationship divId="div_east" boName="${beanAttribute.boName}"></fisc:relationship>
</#if>
<fisc:workflow-taskHistory divId="div_top_south" width="" businessRecordId="${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}"></fisc:workflow-taskHistory>
<div id="div_top_north" class="search">
<#if isHaveProcess>
<fisc:workflow-operationBar taskInstanceId="${"$"}${"{"}workflowTaskId${"}"}"></fisc:workflow-operationBar>
</#if>
</div>
</#compress> 
<#-- 结束生成Tag-->

<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<#if bo.formColumnListHeader?exists && (bo.formColumnListHeader?size>0)>
<#list bo.formColumnListHeader?if_exists?sort_by("formRowNo") as column>
<#--(调试用)序号:${column_index};formRowNo:${column.formRowNo} ;rowNo: ${column.rowNo};columnNo: ${column.columnNo};${column.propertyType?if_exists}-->
<#if column.propertyType !="XX" && column.visibility=="X" && column.propertyType!="HIDDEN">
<#if column.formColumnNo==1 || column.uiType=="12">
	<tr>
</#if>
		<td align="right" width="15%" <#if column.uiType=="12">valign="top"</#if>><#if column.nullable=="X"  && (column.property.numberObjectId=="" || column.property.numberObjectId==" ")><font color="red">★</font></#if><#if column.visibility=="X">${"$"}${"{"}vt.property.${column.propertyName}${"}"}：</#if></td>
		<td <#if column.formColumnNo==1 || column.uiType=="12">width="30%"<#else> width="40%"</#if><#if column.uiType=="12"> colspan="3" </#if>>
		<#-- UITYPE:${column.uiType} -->
		<#-- 搜索帮助 -->
		<#if column.uiType=="11">
			<div id="div_${column.propertyName}_sh"></div>
			<fisc:searchHelp divId="div_${column.propertyName}_sh" boName="${beanAttribute.boName}" boProperty="${column.propertyName}"  value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:searchHelp>
		<#-- 下拉列表(字典表) -->
		<#elseif column.uiType=="06">
			<div id="div_${column.propertyName}_dict"></div>
			<fisc:dictionary boName="${beanAttribute.boName}" boProperty="${column.propertyName}" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_dict" isNeedAuth="false"  value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.property.subBoRelPro?exists && column.property.subBoRelPro!=" " && column.property.subBoRelPro!="">parentBoProperty="${column.property.subBoRelPro}"</#if>></fisc:dictionary>
		<#-- 日期 -->
		<#elseif column.uiType=="04" || column.uiType=="05">
		<#if column.readOnly?exists && column.readOnly!="" && column.readOnly!=" ">
		    <input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  readonly="readonly" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>
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
			<input type="password" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>	
		<#-- 备注框 -->
		<#elseif column.uiType=="12">
			<textarea rows="4" cols="54" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}"  <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}</textarea>
		<#-- USER系统参数  -->
		<#elseif column.uiType=="14">
			<fisc:user boProperty="${column.propertyName}" boName="${beanAttribute.boName}" userId="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:user>
		<#-- 其他UI类别 -->
		<#else>
			<input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  <#if column.property.numberObjectId?exists && column.property.numberObjectId!="" && column.property.numberObjectId!=" "> readonly="readonly"</#if> <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>
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

<#-- Hidden元素 -->
<#if bo.formColumnList?exists && (bo.formColumnList?size>0)>
<#list bo.formColumnList as columnHidden>
<#if columnHidden.uiType?if_exists =="13" || columnHidden.visibility?if_exists !="X">
	<input type="hidden" id="${beanAttribute.boName}.${columnHidden.propertyName}" name="${beanAttribute.boName}.${columnHidden.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${columnHidden.propertyName}${"}"}">
</#if>
</#list>
</#if>
</table>
</form>
<!-- 生成子对象分组布局-->
<#if bo.subBoGroupLayOut?exists && (bo.subBoGroupLayOut?size>0)>
	<#list bo.subBoGroupLayOut?if_exists as subBo>          <#-- 遍历业务对象下所有子对象 -->
      <#if subBo.subBoAttribute.subBoLayoutType=="2"> 
         <#if subBo.subBoAttribute.subBoRelType=="1">  <#-- 1比1 -->
                  	<form id="${subBo.beanAttribute.boInstanceName}Form" name="${subBo.beanAttribute.boInstanceName}Form" class="search">
         			     <div style="background-color:#DFE8F6;width:100%">
				         <BR>
				         </div>
         			    <fieldset ><legend>${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boText${"}"}</legend>
						<table width="100%" border="0" cellpadding="4" cellspacing="1">
							<#if subBo.formColumnList?exists && (subBo.formColumnList?size>0)>
							<#list subBo.formColumnList?sort_by("formRowNo") as column>
							<#--(调试用)序号:${column_index};formRowNo:${column.formRowNo}; rowNo: ${column.rowNo};columnNo: ${column.columnNo};${column.propertyType?if_exists}-->
							<#if column.propertyType !="XX" && column.visibility=="X" && column.propertyType!="HIDDEN" >
							<#if column.formColumnNo==1 || column.uiType=="12">
								<tr>
							</#if>
									<td width="15%" align="right" <#if column.uiType=="12">valign="top"</#if>><#if column.nullable=="X"><font color="red">★</font></#if><#if column.visibility=="X">${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.property.${column.propertyName}${"}"}：</#if></td>
									<td <#if column.formColumnNo==1 || column.uiType=="12">width="30%"<#else> width="40%"</#if><#if column.uiType=="12"> colspan="3" </#if>>
									<#-- UITYPE:${column.uiType} -->
									<#-- 搜索帮助 -->
									<#if column.uiType=="11">
										<div id="div_${subBo.boName}${column.propertyName}_sh"></div>
										<fisc:searchHelp divId="div_${subBo.boName}${column.propertyName}_sh" boName="${subBo.boName}" boProperty="${column.propertyName}"  value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:searchHelp>
									<#-- 下拉列表(字典表) -->
									<#elseif column.uiType=="06">
										<div id="div_${subBo.boName}${column.propertyName}_dict"></div>
										<fisc:dictionary boName="${subBo.boName}"  boProperty="${column.propertyName}"  dictionaryName="${column.property.dicTableName}" divId="div_${subBo.boName}${column.propertyName}_dict" isNeedAuth="false" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.property.subBoRelPro?exists && column.property.subBoRelPro!=" " && column.property.subBoRelPro!="">parentBoProperty="${column.property.subBoRelPro}"</#if>></fisc:dictionary>
									<#-- 日期 -->
									<#elseif column.uiType=="04" || column.uiType=="05">
									 <#if column.readOnly?exists && column.readOnly!="" && column.readOnly!=" ">
		    							<input type="text" class="inputText" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  readonly="readonly"  <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>
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
										<input type="password" class="inputText" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>
									<#-- 备注框 -->
									<#elseif column.uiType=="12">
										<textarea rows="4" cols="54" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}</textarea>
									<#-- USER系统参数  -->
									<#elseif column.uiType=="14">
										<fisc:user boProperty="${column.propertyName}" boName="${subBo.boName}" userId="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:user>									
										<#-- 其他UI类别 -->
									<#else>
										<#if column.property.columnName?upper_case!= bo.primaryKeyProperty.columnName>
										<input type="text" class="inputText" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  <#if column.property.numberObjectId?exists && column.property.numberObjectId!="" && column.property.numberObjectId!=" "> readonly="readonly"  <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/></#if>>
										<#else>
										<input type="text" class="inputText" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.property.numberObjectId?exists && column.property.numberObjectId!="" && column.property.numberObjectId!=" "> readonly="readonly"  <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/></#if>>
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
							</table>
							<#-- Hidden元素 -->
							<#list subBo.formColumnList as columnHidden>
								<#if columnHidden.visibility?if_exists !="X" && (columnHidden.propertyName?upper_case!="CLIENT" || columnHidden.property.columnName?upper_case!="MANDT")>
									<#if columnHidden.property.columnName?upper_case!= bo.primaryKeyProperty.columnName>
							<input type="hidden" id="${subBo.boName}.${columnHidden.propertyName}" name="${subBo.boName}.${columnHidden.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${columnHidden.propertyName}${"}"}">
							        <#else>
							<input type="hidden" id="${subBo.boName}.${columnHidden.propertyName}" name="${subBo.boName}.${columnHidden.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${columnHidden.propertyName}${"}"}">
									</#if>
								</#if>
							</#list>
							<BR>
		</fieldset>
				<#if !subBo_has_next><div style="background-color:#DFE8F6;width:100%"><BR></div></#if>
				</form>
		<#else>  <#-- 其他关联关系，1对多等-->
		 <form id="${subBo.beanAttribute.boInstanceName}Form" name="${subBo.beanAttribute.boInstanceName}Form" class="search">
		 <div style="background-color:#DFE8F6;width:100%">
         <BR>
         </div>
		 <fieldset><legend>${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boText${"}"}</legend>
			<BR>
				<div id="div_${subBo.beanAttribute.boInstanceName}" ></div>
			<BR>
		</fieldset>
		<#if !subBo_has_next><div style="background-color:#DFE8F6;width:100%"><BR></div></#if>
		</form>
       </#if>
     </#if>
    </#list>
</#if>
</div>

<#compress> 
<#-- 生成子对象div -->
<#if bo.subBoTabLayOut?exists && (bo.subBoTabLayOut?size>0)>
	<#list bo.subBoTabLayOut?if_exists as subBo>          <#-- 遍历业务对象下所有子对象 -->
		   <#if subBo.subBoAttribute.subBoRelType=="2" || subBo.subBoAttribute.subBoRelType=="3" || subBo.subBoAttribute.subBoRelType=="4">         <#-- 业务对象子对象关系为 1对多、多对多，标签页中为表 -->
							<div id="div_${subBo.beanAttribute.boInstanceName}"></div>
           <#else>    <#-- 业务对象子对象关系为 1对1关系，标签页中为表单域 -->
							<div id="div_${subBo.beanAttribute.boInstanceName}" class="x-hide-display" >
							<form id="${subBo.beanAttribute.boInstanceName}Form" name="${subBo.beanAttribute.boInstanceName}Form" class="search">
							<table width="100%" border="0" cellpadding="4" cellspacing="1">
							<#if subBo.formColumnList?exists && (subBo.formColumnList?size>0)>
							<#list subBo.formColumnList?sort_by("formRowNo") as column>
							<#--(调试用)序号:${column_index};formRowNo:${column.formRowNo}; rowNo: ${column.rowNo};columnNo: ${column.columnNo};${column.propertyType?if_exists}-->
							<#if column.propertyType !="XX" && column.visibility=="X" && column.propertyType!="HIDDEN" >
							<#if column.formColumnNo==1 || column.uiType=="12">
								<tr>
							</#if>
									<td width="15%" align="right" <#if column.uiType=="12">valign="top"</#if>><#if column.nullable=="X"><font color="red">★</font></#if><#if column.visibility=="X">${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.property.${column.propertyName}${"}"}：</#if></td>
									<td <#if column.formColumnNo==1 || column.uiType=="12">width="30%"<#else> width="40%"</#if><#if column.uiType=="12"> colspan="3" </#if>>
									<#-- UITYPE:${column.uiType} -->
									<#-- 搜索帮助 -->
									<#if column.uiType=="11">
										<div id="div_${subBo.boName}${column.propertyName}_sh"></div>
										<fisc:searchHelp divId="div_${subBo.boName}${column.propertyName}_sh" boName="${subBo.boName}" boProperty="${column.propertyName}"  value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:searchHelp>
									<#-- 下拉列表(字典表) -->
									<#elseif column.uiType=="06">
										<div id="div_${subBo.boName}${column.propertyName}_dict"></div>
										<fisc:dictionary boName="${subBo.boName}"  boProperty="${column.propertyName}"  dictionaryName="${column.property.dicTableName}" divId="div_${subBo.boName}${column.propertyName}_dict" isNeedAuth="false" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.property.subBoRelPro?exists && column.property.subBoRelPro!=" " && column.property.subBoRelPro!="">parentBoProperty="${column.property.subBoRelPro}"</#if>></fisc:dictionary>
									<#-- 日期 -->
									<#elseif column.uiType=="04" || column.uiType=="05">
									 <#if column.readOnly?exists && column.readOnly!="" && column.readOnly!=" ">
		    							<input type="text" class="inputText" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  readonly="readonly"  <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>
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
										<input type="password" class="inputText" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>
									<#-- 备注框 -->
									<#elseif column.uiType=="12">
										<textarea rows="4" cols="54" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}</textarea>
									<#-- USER系统参数  -->
									<#elseif column.uiType=="14">
										<fisc:user boProperty="${column.propertyName}" boName="${subBo.boName}" userId="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:user>									
										<#-- 其他UI类别 -->
									<#else>
										<#if column.property.columnName?upper_case!= bo.primaryKeyProperty.columnName>
										<input type="text" class="inputText" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  <#if column.property.numberObjectId?exists && column.property.numberObjectId!="" && column.property.numberObjectId!=" "> readonly="readonly"  <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/></#if>>
										<#else>
										<input type="text" class="inputText" id="${subBo.boName}.${column.propertyName}" name="${subBo.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.property.numberObjectId?exists && column.property.numberObjectId!="" && column.property.numberObjectId!=" "> readonly="readonly"  <fisc:authentication sourceName="${subBo.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/></#if>>
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
							</table>
							<#-- Hidden元素 -->
							<#list subBo.formColumnList as columnHidden>
								<#if columnHidden.visibility?if_exists !="X" && (columnHidden.propertyName?upper_case!="CLIENT" || columnHidden.property.columnName?upper_case!="MANDT")>
									<#if columnHidden.property.columnName?upper_case!= bo.primaryKeyProperty.columnName>
							<input type="hidden" id="${subBo.boName}.${columnHidden.propertyName}" name="${subBo.boName}.${columnHidden.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${columnHidden.propertyName}${"}"}">
							        <#else>
							<input type="hidden" id="${subBo.boName}.${columnHidden.propertyName}" name="${subBo.boName}.${columnHidden.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${columnHidden.propertyName}${"}"}">
									</#if>
								</#if>
							</#list>
							</form>
							</div>                     
		 </#if>
	</#list>
<#else>
<div id="div_south" class="x-hide-display"></div>
</#if>
</#compress> 

<#-- 生成业务对象标签页div ，如果formColumn有设置的抬头为false的表单元素 -->
<#if bo.formColumnListTab?exists && (bo.formColumnListTab?size>0)>
<div id="div_${beanAttribute.boInstanceName}" class="x-hide-display"> 
<form id="${beanAttribute.boInstanceName}Form" name="${beanAttribute.boInstanceName}Form" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<#if bo.formColumnListTab?exists && (bo.formColumnListTab?size>0)>
	<#list bo.formColumnListTab?sort_by("formRowNo") as column>
	 <#-- (调试用)序号:${column_index};formRowNo:${column.formRowNo}; rowNo: ${column.rowNo};columnNo: ${column.columnNo};${column.propertyType?if_exists}-->
		<#if column.propertyType !="XX" && column.visibility=="X" && column.propertyType!="HIDDEN">
		<#if column.formColumnNo==1 || column.uiType=="12">
			<tr>
		</#if>
				<td align="right" width="15%" <#if column.uiType=="12">valign="top"</#if>><#if column.nullable=="X"><font color="red">★</font></#if><#if column.visibility=="X">${"$"}${"{"}vt.property.${column.propertyName}${"}"}：</#if></td>
				<td <#if column.formColumnNo==1 || column.uiType=="12">width="30%"<#else> width="40%"</#if><#if column.uiType=="12"> colspan="3" </#if>>
				<#--  UITYPE:${column.uiType} -->
				<#-- 搜索帮助 -->
				<#if column.uiType=="11">
					<div id="div_${column.propertyName}_sh"></div>
					<fisc:searchHelp divId="div_${column.propertyName}_sh" boName="${beanAttribute.boName}" boProperty="${column.propertyName}"  value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:searchHelp>
				<#-- 下拉列表(字典表) -->
				<#elseif column.uiType=="06">
					<div id="div_${column.propertyName}_dict"></div>
					<fisc:dictionary boName="${beanAttribute.boName}" boProperty="${column.propertyName}" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_dict" isNeedAuth="false"  value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.property.subBoRelPro?exists && column.property.subBoRelPro!=" " && column.property.subBoRelPro!="">parentBoProperty="${column.property.subBoRelPro}"</#if>></fisc:dictionary>
				<#-- 日期 -->
				<#elseif column.uiType=="04" || column.uiType=="05">
				 <#if column.readOnly?exists && column.readOnly!="" && column.readOnly!=" ">
		    		<input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  readonly="readonly" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>
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
					<input type="password" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>
				<#-- 备注框 -->
				<#elseif column.uiType=="12">
					<textarea rows="4" cols="54" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}"  <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}</textarea>	
				<#-- USER系统参数  -->
				<#elseif column.uiType=="14">
					<fisc:user boProperty="${column.propertyName}" boName="${beanAttribute.boName}" userId="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:user>
				<#-- 其他UI类别 -->
				<#else>
					<input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  <#if column.property.numberObjectId?exists &&column.property.numberObjectId!="" && column.property.numberObjectId!=" "> readonly="readonly"</#if> <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId="${"$"}${"{"}workflowTaskId${"}"}"/>>
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
	</table>
	</form>
	</div> 
</#if>
<div id="div_top_south" class="x-hide-display"></div>
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//是否已经提交流程
<#if isHaveProcess>
<#if bo.properties?exists && (bo.properties?size>0)>
<#list bo.properties as property>
<#if property.columnName=='PROCESSSTATE'>
var isSubmited = '${'$'}${'{'}${beanAttribute.boInstanceName}.${property.propertyName}${'}'}';
<#break>
</#if>
</#list>
</#if>
<#else>
var isSubmited = '';
</#if>
//当前对象主键ID
var ${bo.primaryKeyProperty.propertyName} = '${"$"}${"{"}${bo.beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}';	

//页面文本
var Txt={
	//${bo.boText}
	${beanAttribute.boInstanceName}:'${"$"}${"{"}vt.boText${"}"}',
<#if subBoList?exists && (subBoList?size>0)>
	<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象 -->
	<#if subBo.boName!="Attachement"> 
	//${subBo.boText}
	${subBo.beanAttribute.boInstanceName}:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boText${"}"}',
	<#if subBo.gridToolbars?exists && (subBo.gridToolbars?size>0)>  
	<#list subBo.gridToolbars as toolbar>
    <#if toolbar.methodName=="_create">
	//boText创建
	${subBo.beanAttribute.boInstanceName}_Create:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boTextCreate${"}"}',
	<#elseif toolbar.methodName=="_edit">
	${subBo.beanAttribute.boInstanceName}_Edit:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boTextEdit${"}"}',
	${subBo.beanAttribute.boInstanceName}_edit_AllowOnlyOneItemForOperation:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.edit_AllowOnlyOneItemForOperation${"}"}',
	<#elseif toolbar.methodName=="_view">
	${subBo.beanAttribute.boInstanceName}_View:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boTextView${"}"}',
	<#elseif toolbar.methodName=="_copyCreate">
	//boText复制创建
	${subBo.beanAttribute.boInstanceName}_CopyCreate:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boTextCopyCreate${"}"}',
	// 进行【${subBo.boText}复制创建】操作时，只允许选择一条记录！
	${subBo.beanAttribute.boInstanceName}_CopyCreate_AllowOnlyOneItemForOperation:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.copyCreate_AllowOnlyOneItemForOperation${"}"}',
	// 请选择需要进行【${subBo.boText}复制创建】操作的记录！
	${subBo.beanAttribute.boInstanceName}_CopyCreate_SelectToOperation:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.copyCreate_SelectToOperate${"}"}',
	<#elseif toolbar.methodName=="_deletes">
	// 请选择需要进行【${subBo.boText}批量删除】操作的记录！
	${subBo.beanAttribute.boInstanceName}_Deletes_SelectToOperation:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.deletes_SelectToOperate${"}"}',
	// 您选择了【${subBo.boText}批量删除】操作，是否确定继续该操作？
	${subBo.beanAttribute.boInstanceName}_Deletes_ConfirmOperation:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.deletes_ConfirmOperation${"}"}',
	<#elseif toolbar.methodName=="_delete">
	// 您选择了【${beanAttribute.boDescription}删除】操作，是否确定继续该操作？
	${subBo.beanAttribute.boInstanceName}_Delete_ConfirmOperation:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.delete_ConfirmOperation${"}"}',
	</#if>
	</#list>
	</#if>
	</#if>
	</#list>
</#if>
	//boText复制创建
	${beanAttribute.boInstanceName}_CopyCreate:'${"$"}${"{"}vt.boTextCopyCreate${"}"}',
	//boText创建
	${beanAttribute.boInstanceName}_Create:'${"$"}${"{"}vt.boTextCreate${"}"}',
	// 复制创建
	mCopyCreate:'${"$"}${"{"}vt.mCopyCreate${"}"}',
	// 创建
	mCreate:'${"$"}${"{"}vt.mCreate${"}"}',
	// 编辑
	mEdit:'${"$"}${"{"}vt.mEdit${"}"}',
	// 查看
	mView:'${"$"}${"{"}vt.mView${"}"}',
	// '您选择了【采购订单删除】操作，是否确定继续该操作？'
	${beanAttribute.boInstanceName}_Delete_ConfirmOperation:'${"$"}${"{"}vt.delete_ConfirmOperation${"}"}',
	// '提交成功！'
	submitSuccess:'${"$"}${"{"}vt.submitSuccess${"}"}',
	// '操作成功！'
	operateSuccess:'${"$"}${"{"}vt.operateSuccess${"}"}',
	// 提示
	cue:'${"$"}${"{"}vt.sCue${"}"}',
	// 确定
	ok:'${"$"}${"{"}vt.sOk${"}"}',
	// 取消
	cancel:'${"$"}${"{"}vt.sCancel${"}"}'
};

/**
 * EXT 布局
 */
//Ext.onReady(function(){
	var viewport = new Ext.Viewport({
		layout:"border",
		border:false,
		items:[{
					region:'center',
					layout:'border',
					border:false,
					items:[{
							region:'north',
							layout:'fit',
							height:26,
							border:false,
							contentEl:'div_toolbar'
						},{
							region:'center',
							layout:'anchor',
				            height:600,
				            border:false,
				            autoScroll:true,
				            items:[
<#if isHaveProcess>				            
				               {
				            	id:'currentWorkflowTask',
					            title: '${"$"}${"{"}vt.processApproveInfo${"}"}',
					            border:false,
					            layout:'fit',
<#if isHaveRelationship>
			            		anchor: '-20',
</#if>
					            contentEl:'div_top_north'
					          },
</#if>					          
				              {
				            		title:'${"$"}${"{"}vt.boTextInfo${"}"}',
				            		layout:'fit',
				            		autoScroll: true,
				            		border:false,
				            		//height:${formHieght},
<#if isHaveRelationship>
			            			anchor: '-20',
</#if>
				            		contentEl:'div_center'
						}
				<#if bo.subBoTabLayOut?exists && (bo.subBoTabLayOut?size>0)>
						,{
									xtype:'tabpanel',
					            	height:310,
					            	id:'tabs',
					            	name:'tabs',
					            	defaults:{bodyStyle:"background-color:#DFE8F6"},
<#if isHaveRelationship>
			            			anchor: '-20',
</#if>
					            	autoScroll: true,
									activeTab:0,
						           items:[
				<#if bo.formColumnListTab?exists && (bo.formColumnListTab?size>0)>
									    {
							            		title:'${"$"}${"{"}vt.boText${"}"}',
							            		layout:'fit',
							            		autoWidth:true,
							            		contentEl:'div_${beanAttribute.boInstanceName}'
							             },
				</#if>
			    <#list bo.subBoTabLayOut as subBo>          <#-- 遍历业务对象下所有子对象 -->
						                {
						            		title:'${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boText${"}"}',
						            		layout:'fit',
						            		autoWidth:true,
						            		contentEl:'div_${subBo.beanAttribute.boInstanceName}'
						            	}<#if subBo_has_next>,</#if>
				</#list>
						    ]}
						   ]}
<#if isHaveProcess>	 
						,{
							id:'historyWorkflowTask',
							region:'south',
							title:'${"$"}${"{"}vt.processTrackInfo${"}"}',
		            		layout:'anchor',
<#if isHaveRelationship>
			            	anchor: '-20',
</#if>
			            	collapsible: true,
			            	collapsed:true,
			            	border:false,
			            	autoScroll: true,
							height:200,
							contentEl:'div_top_south'
						}			
</#if>]
			<#else>
				<#if bo.formColumnListTab?exists  && (bo.formColumnListTab?size>0)>
							,{
									xtype:'tabpanel',
					            	height:310,
					            	id:'tabs',
					            	name:'tabs',
					            	defaults:{bodyStyle:"background-color:#DFE8F6"},
<#if isHaveRelationship>
			            			anchor: '-20',
</#if>
					            	autoScroll: true,
									activeTab:0,
						            items:[
									     {
							            		title: '${"$"}${"{"}vt.boText${"}"}',
							            		layout:'fit',
							            		autoWidth:true,
							            		contentEl:'div_${beanAttribute.boInstanceName}'
							             }]
							    }
				</#if>
<#if isHaveProcess>	
								,{
								id:'historyWorkflowTask',
								region:'south',
								title:'${"$"}${"{"}vt.processTrackInfo${"}"}',
								border:false,
			            		layout:'anchor',
<#if isHaveRelationship>
			            		anchor: '-20',
</#if>
				            	collapsible: true,
				            	collapsed:true,
				            	autoScroll: true,
								height:200,
								contentEl:'div_top_south'
							   }
</#if>
							   ]
				   }]
			</#if>
				}
<#if isHaveRelationship>
				,{
					region:'east',
					width:200,
					contentEl:'div_east',
					collapsible: true,
					collapsed:true,
					autoScroll: true,
					title:'${"$"}${"{"}vt.sRelOperation${"}"}'
				}]
<#else>
                 ]
</#if>
	});

	var toolbars = new Ext.Toolbar({
			items:[
					'-',
					<#compress>
					<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
					<#list bo.formToolbars?if_exists?sort_by("orderNo") as formToolbar>          <#-- 遍历业务对象下所有方法 -->
					<#if formToolbar.method.methodName=="_create">
					{id:'${formToolbar.method.methodName}',text:'${"$"}${"{"}vt.mCreate${"}"}',handler:${formToolbar.method.methodName}${beanAttribute.boName},iconCls:'${formToolbar.method.icon}'},'-',
					<#elseif formToolbar.method.methodName=="_copyCreate">
					{id:'${formToolbar.method.methodName}',text:'${"$"}${"{"}vt.mCopyCreate${"}"}',handler:${formToolbar.method.methodName}${beanAttribute.boName},iconCls:'${formToolbar.method.icon}'},'-',
					<#elseif formToolbar.method.methodName=="_delete">
					{id:'${formToolbar.method.methodName}',text:'${"$"}${"{"}vt.mDelete${"}"}',handler:${formToolbar.method.methodName}${beanAttribute.boName},iconCls:'${formToolbar.method.icon}'},'-',			
					<#elseif formToolbar.method.methodType=="6">
					{id:'${formToolbar.method.methodName}',xtype:'printbutton',text:'${"$"}${"{"}vt.sPrint${"}"}',boName:'${beanAttribute.boName}',methodName:'${formToolbar.method.methodName}',className:'${bo.beanAttribute.packageName}.domain.${bo.beanAttribute.boName}',businessId:'${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}'},'-',
					</#if>
					</#list>
					</#if>
					'->','-',
					<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
					<#list bo.formToolbars?if_exists?sort_by("orderNo") as formToolbar>          <#-- 遍历业务对象下所有方法 -->
					<#if formToolbar.method.methodName=="_saveOrUpdate">
					{id:'${formToolbar.method.methodName}',text:'${"$"}${"{"}vt.mSaveOrUpdate${"}"}',handler:${formToolbar.method.methodName}${beanAttribute.boName},iconCls:'${formToolbar.method.icon}'},'-',
					<#elseif formToolbar.method.methodName=="_cancel">
					{id:'${formToolbar.method.methodName}',text:'${"$"}${"{"}vt.mCancel${"}"}',handler:${formToolbar.method.methodName}${beanAttribute.boName},iconCls:'${formToolbar.method.icon}'},'-',
					<#elseif formToolbar.method.methodName=="_submitProcess"  && isHaveProcess>
<%
Object haveBindWorkFlow = request.getAttribute("haveBindWorkFlow");
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
					{id:'${formToolbar.method.methodName}',text:'${"$"}${"{"}vt.mSubmitProcess${"}"}',handler:${formToolbar.method.methodName}${beanAttribute.boName},iconCls:'${formToolbar.method.icon}'},'-',				
<%}%>					
					</#if>
					</#list>
					</#if>
					<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
					<#list bo.formToolbars?if_exists?sort_by("orderNo") as formToolbar>          <#-- formToolbar.method.isDefault!="X" && 遍历业务对象下所有方法 -->
					<#if formToolbar.method.methodType!="6" && formToolbar.method.methodName!="_create" && formToolbar.method.methodName!="_copyCreate" && formToolbar.method.methodName!="_delete" && formToolbar.method.methodName!="_saveOrUpdate" && formToolbar.method.methodName!="_cancel" && formToolbar.method.methodName!="_submitProcess">
					{id:'${formToolbar.method.methodName}',text:'${formToolbar.method.methodDesc}',handler:${formToolbar.method.methodName}${beanAttribute.boName},iconCls:'${formToolbar.method.icon}'},'-',
					</#if>
					</#list>
					</#if>
					' '
					],</#compress>
			renderTo:'div_toolbar'
	});

<#if isHaveProcess>
	var isTask = '${"$"}${"{"}workflowTaskId${"}"}';
	if(!isTask || isTask==''){
		Ext.getCmp('currentWorkflowTask').hide();
		//如果已经提交，不允许再提交
		if(isSubmited && isSubmited!='' && isSubmited!=' '){
<#compress>
<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
<#list bo.formToolbars?if_exists as formToolbar>          <#-- 遍历业务对象下所有方法 -->
<#if formToolbar.method.methodName=="_delete"> 	
		Ext.getCmp('_delete').disable();
<#elseif formToolbar.method.methodName=="_submitProcess" && isHaveProcess> 
<%
if(Boolean.parseBoolean(haveBindWorkFlow.toString())){%>
		Ext.getCmp('_submitProcess').disable();
<%}%>	
<#elseif formToolbar.method.methodName=="_saveOrUpdate"> 
		Ext.getCmp('_saveOrUpdate').disable();
<#elseif formToolbar.method.methodName=="_create"> 	
		Ext.getCmp('_create').disable();
<#elseif formToolbar.method.methodName=="_copyCreate"> 
		Ext.getCmp('_copyCreate').disable();
</#if>
</#list>
</#if>
</#compress>
		}
		else{
			Ext.getCmp('historyWorkflowTask').hide();
		}
		viewport.doLayout();
	}else{
<#compress>
<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
<#list bo.formToolbars?if_exists as formToolbar>          <#-- 遍历业务对象下所有方法 -->
<#if formToolbar.method.methodName=="_create"> 	
		Ext.getCmp('_create').disable();
<#elseif formToolbar.method.methodName=="_copyCreate"> 
		Ext.getCmp('_copyCreate').disable();
<#elseif formToolbar.method.methodName=="_delete"> 
		Ext.getCmp('_delete').disable();
<#elseif formToolbar.method.methodName=="_saveOrUpdate"> 	
		Ext.getCmp('_saveOrUpdate').disable();
<#elseif formToolbar.method.methodName=="_submitProcess"> 
<#elseif formToolbar.method.methodName=="_cancel"> 
</#if>
</#list>
</#if>
</#compress>
	}
</#if>
//});
<#if (bo.tabsSize>0) >
Ext.onReady(function(){
    var tabsSize = ${bo.tabsSize};
    if(tabsSize!=0)
    {
	  	var tabs = Ext.getCmp("tabs");
		for (i=0; i < ${bo.tabsSize} ; i++){
		   tabs.setActiveTab(${bo.tabsSize}-1-i);
		}
	}
 });
</#if>	
</script>