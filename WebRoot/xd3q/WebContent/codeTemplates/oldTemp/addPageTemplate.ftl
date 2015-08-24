<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: ${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象${beanAttribute.boDescription}(${beanAttribute.boName})增加页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${"$"}${"{"}vt.addPage${"}"}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Add.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/${beanAttribute.webPath}/${beanAttribute.boInstanceName}AddGen.js"></script>
</head>
<body>
<#-- 开始生成Tag-->
<#compress> 
<#if subBoList?exists && (subBoList?size>0)>
	<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象 "${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}"-->
		<#if subBo.boName!="Attachement">   
		   <#list bo.properties as property>
		        <#if property.subBoName==subBo.boName> 
		        	<#if property.subBoRelType=="2">         <#-- 业务对象子对象关系为 1对多 -->
<fisc:grid divId="div_${subBo.beanAttribute.boInstanceName}" boName="${subBo.beanAttribute.boName}" needCheckBox="true" editable="true" defaultCondition=" ${subBo.tableName}.${bo.primaryKeyProperty.columnName}='${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>      
		            <#elseif property.subBoRelType=="3" || property.subBoRelType=="4">
<fisc:grid divId="div_${subBo.beanAttribute.boInstanceName}" boName="${subBo.beanAttribute.boName}" needCheckBox="true" editable="false" defaultCondition=" ${subBo.tableName}.${bo.primaryKeyProperty.columnName}='${"$"}${"{"}${beanAttribute.boInstanceName}.${bo.primaryKeyProperty.propertyName}${"}"}'" needAutoLoad="true" height="285" nameSapceSupport="true"></fisc:grid>        
		            </#if>
		        </#if>
		   </#list>
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
</#compress> 
<#-- 结束生成Tag-->
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<#if bo.formColumnListHeader?exists && (bo.formColumnListHeader?size>0)>
<#list bo.formColumnListHeader?if_exists?sort_by("formRowNo") as column>
<#-- column.visibility=="X" ${bo.formColumnListHeader?size}(调试用)序号:${column_index};formRowNo:${column.formRowNo} ;rowNo: ${column.rowNo};columnNo: ${column.columnNo};${column.propertyType?if_exists} -->
<#if column.propertyType !="XX" && column.visibility=="X" && column.propertyType!="HIDDEN">
<#if column.formColumnNo==1 || column.uiType=="12">
	<tr>
</#if>
<!-- column.property.numberObjectId column.visibility=="X" ${bo.formColumnListHeader?size}(调试用)序号:${column_index};formRowNo:${column.formRowNo} ;rowNo: ${column.rowNo};columnNo: ${column.columnNo};${column.propertyType?if_exists} -->
		<td align="right"  width="15%" <#if column.uiType=="12">valign="top"</#if>><#if column.nullable=="X" && (column.property.numberObjectId=="" || column.property.numberObjectId==" ")><font color="red">★</font></#if><#if column.visibility=="X">${"$"}${"{"}vt.property.${column.propertyName}${"}"}：</#if></td>
		<td  <#if column.formColumnNo==1 || column.uiType=="12">width="30%"<#else> width="40%"</#if> <#if column.uiType=="12"> colspan="3" </#if>>
		<#-- 搜索帮助 -->
		<#if column.uiType=="11">
			<div id="div_${column.propertyName}_sh"></div>
			<fisc:searchHelp divId="div_${column.propertyName}_sh" boName="${beanAttribute.boName}" boProperty="${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:searchHelp>
		<#-- 下拉列表(字典表) -->
		<#elseif column.uiType=="06">
			<div id="div_${column.propertyName}_dict"></div>
			<fisc:dictionary boName="${beanAttribute.boName}" boProperty="${column.propertyName}" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_dict" isNeedAuth="false"  value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:dictionary>
		<#-- 日期 -->
		<#elseif column.uiType=="04" || column.uiType=="05">
		<#if column.readOnly?exists && column.readOnly!="" && column.readOnly!=" ">
		    <input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  readonly="readonly" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>
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
			<input class="inputText" type="password" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.visibility!="X"> style="visibility: hidden;" <#else><fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/></#if>>									
		<#-- 备注框 -->
		<#elseif column.uiType=="12">
			<textarea rows="4" cols="54" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}</textarea>
		<#-- USER系统参数 -->
		<#elseif column.uiType=="14">
			<fisc:user boProperty="${column.propertyName}" boName="${beanAttribute.boName}" userId="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:user>
		<#-- 其他UI类别 -->
		<#else>
			<input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.visibility!="X"> style="visibility: hidden;" <#else><fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/></#if> <#if column.property.numberObjectId?exists && column.property.numberObjectId!="" && column.property.numberObjectId!=" "> readonly="readonly"</#if>>
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
</table>
</form>
</div>

<#-- 生成子对象div -->
<#if subBoList?exists && (subBoList?size>0)>
	<#list subBoList?if_exists as subBo>          <#-- 遍历业务对象下所有子对象 -->
		<#if subBo.boName!="Attachement">   
		   <#list bo.properties as property>
		        <#if property.subBoName==subBo.boName> 
		        	<#if property.subBoRelType=="2" || property.subBoRelType=="3" || property.subBoRelType=="4">         <#-- 业务对象子对象关系为 1对多、多对多，标签页中为表 -->
						<div id="div_${subBo.beanAttribute.boInstanceName}" ></div>
                     <#else>    <#-- 业务对象子对象关系为 1对1关系，标签页中为表单域 -->
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
										<fisc:dictionary boName="${subBo.boName}" boProperty="${column.propertyName}" dictionaryName="${column.property.dicTableName}" divId="div_${subBo.boName}${column.propertyName}_dict" isNeedAuth="false" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${subBo.beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:dictionary>
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
				        </#if>
					   </#list>
					<#else>
<div id="div_attachement" ></div>
					</#if>
	</#list>
<#else>
<div id="div_south" class="x-hide-display"></div>
</#if>

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
				<td align="right"  width="15%" <#if column.uiType=="12">valign="top"</#if>><#if column.nullable=="X"><font color="red">★</font></#if><#if column.visibility=="X">${"$"}${"{"}vt.property.${column.propertyName}${"}"}：</#if></td>
				<td  <#if column.formColumnNo==1 || column.uiType=="12">width="30%"<#else> width="40%"</#if> <#if column.uiType=="12"> colspan="3" </#if>>
				<#-- UITYPE:${column.uiType} -->
				<#-- 搜索帮助 -->
				<#if column.uiType=="11">
					<div id="div_${column.propertyName}_sh"></div>
					<fisc:searchHelp divId="div_${column.propertyName}_sh" boName="${beanAttribute.boName}" boProperty="${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:searchHelp>
				<#-- 下拉列表(字典表) -->
				<#elseif column.uiType=="06">
					<div id="div_${column.propertyName}_dict"></div>
					<fisc:dictionary boName="${beanAttribute.boName}" boProperty="${column.propertyName}" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_dict" isNeedAuth="false" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:dictionary>
				<#-- 日期 -->
				<#elseif column.uiType=="04" || column.uiType=="05">
				<#if column.readOnly?exists && column.readOnly!="" && column.readOnly!=" ">
				    <input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  readonly="readonly" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>
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
					<input class="inputText" type="password" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>									
				<#-- 备注框 -->
				<#elseif column.uiType=="12">
					<textarea rows="4" cols="54" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}"  <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}</textarea>
				<#-- USER系统参数 -->
				<#elseif column.uiType=="14">
					<fisc:user boProperty="${column.propertyName}" boName="${beanAttribute.boName}" userId="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:user>				
					<#-- 其他UI类别 -->
				<#else>
					<input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  <#if column.property.numberObjectId?exists && column.property.numberObjectId!="" && column.property.numberObjectId!=" "> readonly="readonly"</#if> <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>
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
</body>
</html>
<script type="text/javascript" defer="defer">
//应用的上下文路径，作为全局变量供js使用
var contextPath = '<%= request.getContextPath() %>';
//如果是复制创建，则清空主对象ID，使之执行保存动作
var isCreateCopy = '${"$"}${"{"}isCreateCopy${"}"}';

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
							layout: 'anchor',
				            height:600,
				            border:false,
				            autoScroll: true,
				            items:[{
				            		title: '${"$"}${"{"}vt.boTextInfo${"}"}',
				            		layout:'fit',
				            		autoScroll: true,
<#if isHaveRelationship>
			            			anchor: '-20',
</#if>
				            		contentEl:'div_center'
						}
				<#if subBoList?exists && (subBoList?size>0)>
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
									    		id:'${bo.beanAttribute.boInstanceName}Tab',
							            		title: '${"$"}${"{"}vt.boText${"}"}',
							            		layout:'fit',
							            		bodyStyle:"background-color:#DFE8F6",
							            		contentEl:'div_${beanAttribute.boInstanceName}'
							             },
				</#if>
				<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象 -->
					<#--<#if subBo.boName!="Attachement"> -->   
						                {
						            		title: '${"$"}${"{"}${subBo.beanAttribute.boInstanceName}Vt.boText${"}"}',
						            		layout:'fit',
						            		id:'${subBo.beanAttribute.boInstanceName}Tab',
						            		<#if subBo.subBoAttribute.subBoRelType=="4">
						            		disabled: true,
						            		</#if>
						            		contentEl:'div_${subBo.beanAttribute.boInstanceName}'
						            	}<#if subBo_has_next>,</#if>
					<#--</#if> -->
				</#list>
				<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象 -->
					<#if subBo.boName=="Attachement___">   
							      		{
						            		title: '${"$"}${"{"}${vt.sAttachement}${"}"}',
						            		layout:'fit',
						            		id:'attachementTab',
						            		contentEl:'div_attachement'
						            	}
					</#if>
				</#list>
						]
						}]
						}]
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
						            items:[{
							            		title: '${"$"}${"{"}vt.boText${"}"}',
							            		layout:'fit',
							            		contentEl:'div_${beanAttribute.boInstanceName}'
							              }]
				 }</#if>
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
					'-','->','-',
					<#if bo.formToolbars?exists && (bo.formToolbars?size>0)>
					<#list bo.formToolbars?sort_by("orderNo") as formToolbar>          <#-- 遍历业务对象下所有方法 -->
					<#if formToolbar.method.methodName=="_saveOrUpdate"> 	
					{id:'_save',text:'${"$"}${"{"}vt.mSaveOrUpdate${"}"}',handler:_save,iconCls:'${formToolbar.method.icon}'},<#if formToolbar_has_next>'-',</#if>
					<#elseif formToolbar.method.methodName=="_submitProcess不生成"> 
					{id:'_submit',text:'${formToolbar.method.methodDesc}',handler:_submitProcess,iconCls:'${formToolbar.method.icon}'},<#if formToolbar_has_next>'-',</#if>
					<#elseif formToolbar.method.methodName=="_cancel"> 
					{id:'_cancel',text:'${"$"}${"{"}vt.mCancel${"}"}',handler:_cancel,iconCls:'${formToolbar.method.icon}'},<#if formToolbar_has_next>'-',</#if>
					</#if>
					</#list>
					</#if>
					' '],  
			renderTo:"div_toolbar"
	});
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
//});
</script>
