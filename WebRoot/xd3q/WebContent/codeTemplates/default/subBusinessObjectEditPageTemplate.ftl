<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: ${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>子业务对象${beanAttribute.boDescription}(${beanAttribute.boName})编辑页面
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
<div id="div_toolbar" class="x-hide-display"></div>
<div id="div_center" class="x-hide-display"> 
<form id="mainForm" name="mainForm" class="search">
<table width="100%" border="0" cellpadding="4" cellspacing="1">
<#list bo.formColumnListHeader?if_exists?sort_by("formRowNo") as column>
<#--(调试用)序号:${column_index};formRowNo:${column.formRowNo} ;rowNo: ${column.rowNo};columnNo: ${column.columnNo};${column.propertyType?if_exists}-->
<#if column.propertyType !="XX" && column.visibility=="X" && column.propertyType!="HIDDEN">
<#if column.formColumnNo==1 || column.uiType=="12">
	<tr>
</#if>
		<td width="20%" align="right" <#if column.uiType=="12">valign="top"</#if>><#if column.nullable=="X"><font color="red">★</font></#if><#if column.visibility=="X">${"$"}${"{"}vt.property.${column.propertyName}${"}"}：</#if></td>
		<td width="30%" <#if column.uiType=="12"> colspan="3" </#if>>
		<#-- UITYPE:${column.uiType} -->
		<#-- 搜索帮助 -->
		<#if column.uiType=="11">
			<div id="div_${column.propertyName}_sh"></div>
			<fisc:searchHelp divId="div_${column.propertyName}_sh" boName="${beanAttribute.boName}" boProperty="${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:searchHelp>
		<#-- 下拉列表(字典表) -->
		<#elseif column.uiType=="06">
			<div id="div_${column.propertyName}_dict"></div>
			<fisc:dictionary boName="${beanAttribute.boName}" boProperty="${column.propertyName}" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_dict" isNeedAuth="false"  value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  <#if column.property.dictGroupValue?exists && column.property.dictGroupValue!=" " && column.property.dictGroupValue!=""> groupValue="${column.property.dictGroupValue}" </#if> <#if column.property.subBoRelPro?exists && column.property.subBoRelPro!=" " && column.property.subBoRelPro!="">parentBoProperty="${column.property.subBoRelPro}"</#if>></fisc:dictionary>
		<#-- 日期 -->
		<#elseif column.uiType=="04" || column.uiType=="05">
		  <#if column.readOnly?exists && column.readOnly!="" && column.readOnly!=" ">
				 <input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  readonly="readonly">
		  <#else>
			<input type="text" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="">
			<#if column.uiType=="04">
				<fisc:calendar applyTo="${beanAttribute.boName}.${column.propertyName}" format="Ymd" divId="" fieldName=""  defaultValue="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:calendar>
			<#elseif column.uiType=="05">
				<fisc:calendar applyTo="${beanAttribute.boName}.${column.propertyName}" format="Ymd" divId="" fieldName="" showTime="true" defaultValue="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:calendar>
			</#if>
		  </#if>
		<#-- 密码框 -->
		<#elseif column.uiType=="03">
			<input type="password" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.visibility!="X"> style="visibility: hidden;" <#else> <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/></#if>>									
		<#-- 备注框 -->
		<#elseif column.uiType=="12">
			<textarea rows="4" cols="54" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}"  <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}</textarea>
		<#-- USER系统参数 -->
		<#elseif column.uiType=="14">
			<fisc:user boProperty="${column.propertyName}" boName="${beanAttribute.boName}" userId="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:user>		
		<#-- 其他UI类别 -->
		<#else>
		    <#if column.property.columnName==parentBo.primaryKeyProperty.columnName>
		    <input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${parentBo.boName?uncap_first}.${column.propertyName}${"}"}" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>
		    <#else>
			<input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>
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
<#list bo.formColumnListTab?sort_by("formRowNo") as column>
 <#--(调试用)序号:${column_index};formRowNo:${column.formRowNo}; rowNo: ${column.rowNo};columnNo: ${column.columnNo};${column.propertyType?if_exists}-->
	<#if column.propertyType !="XX" && column.visibility=="X" && column.propertyType!="HIDDEN">
	<#if column.formColumnNo==1 || column.uiType=="12">
		<tr>
	</#if>
		 <td width="20%" align="right" <#if column.uiType=="12">valign="top"</#if>><#if column.nullable=="X"><font color="red">★</font></#if><#if column.visibility=="X">${"$"}${"{"}vt.property.${column.propertyName}${"}"}：</#if></td>
		 <td width="30%" <#if column.uiType=="12"> colspan="3" </#if>>
			<#-- UITYPE:${column.uiType} -->
			<#-- 搜索帮助 -->
			<#if column.uiType=="11">
				<div id="div_${column.propertyName}_sh"></div>
				<fisc:searchHelp divId="div_${column.propertyName}_sh" boName="${beanAttribute.boName}" boProperty="${column.propertyName}"  value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:searchHelp>
			<#-- 下拉列表(字典表) -->
			<#elseif column.uiType=="06">
				<div id="div_${column.propertyName}_dict"></div>
				<fisc:dictionary boName="${beanAttribute.boName}" boProperty="${column.propertyName}" dictionaryName="${column.property.dicTableName}" divId="div_${column.propertyName}_dict" isNeedAuth="false"  value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  <#if column.property.dictGroupValue?exists && column.property.dictGroupValue!=" " && column.property.dictGroupValue!=""> groupValue="${column.property.dictGroupValue}" </#if> <#if column.property.subBoRelPro?exists && column.property.subBoRelPro!=" " && column.property.subBoRelPro!="">parentBoProperty="${column.property.subBoRelPro}"</#if>></fisc:dictionary>
			<#-- 日期 -->
			<#elseif column.uiType=="04" || column.uiType=="05">
			  <#if column.readOnly?exists && column.readOnly!="" && column.readOnly!=" ">
				 <input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"  readonly="readonly">
			  <#else>
				<input type="text" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="">
				<#if column.uiType=="04">
					<fisc:calendar applyTo="${beanAttribute.boName}.${column.propertyName}" format="Ymd" divId="" fieldName=""  defaultValue="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" ></fisc:calendar>
				<#elseif column.uiType=="05">
					<fisc:calendar applyTo="${beanAttribute.boName}.${column.propertyName}" format="Ymd" divId="" fieldName="" showTime="true" defaultValue="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" ></fisc:calendar>
				</#if>
			  </#if>
			<#-- 密码框 -->
			<#elseif column.uiType=="03">
				<input type="password" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <#if column.visibility!="X"> style="visibility: hidden;" <#else> <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/></#if>>									
			<#-- 备注框 -->
			<#elseif column.uiType=="12">
				<textarea rows="4" cols="54" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}"  <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}</textarea>
			<#-- USER系统参数 -->
			<#elseif column.uiType=="14">
				<fisc:user boProperty="${column.propertyName}" boName="${beanAttribute.boName}" userId="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}"></fisc:user>		
			<#-- 其他UI类别 -->
			<#else>
			<!--  aa : ${column.property.columnName} bb: ${parentBo.primaryKeyProperty.columnName}-->
		    <#if column.property.columnName==parentBo.primaryKeyProperty.columnName>
		    <input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${parentBo.boName?uncap_first}.${column.propertyName}${"}"}" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>
		    <#else>
			<input type="text" class="inputText" id="${beanAttribute.boName}.${column.propertyName}" name="${beanAttribute.boName}.${column.propertyName}" value="${"$"}${"{"}${beanAttribute.boInstanceName}.${column.propertyName}${"}"}" <fisc:authentication sourceName="${beanAttribute.boName}.${column.propertyName}" taskId=""/>>
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
<#-- Hidden元素 -->
<#if bo.formColumnList?exists && (bo.formColumnList?size>0)>
<#list bo.formColumnList as columnHidden>
<#if columnHidden.visibility?if_exists !="X" && (columnHidden.propertyName?upper_case!="CLIENT" || columnHidden.property.columnName?upper_case!="MANDT")>
<#if columnHidden.property.columnName==parentBo.primaryKeyProperty.columnName>
<input type="hidden" id="${beanAttribute.boName}.${columnHidden.propertyName}" name="${beanAttribute.boName}.${columnHidden.propertyName}" value="">
<#else>
<input type="hidden" id="${beanAttribute.boName}.${columnHidden.propertyName}" name="${beanAttribute.boName}.${columnHidden.propertyName}" value="">
</#if>			
</#if>
</#list>
</#if>
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
	submitSuccess:'${"$"}${"{"}vt.submitSuccess${"}"}',
	// '操作成功！'
	operateSuccess:'${"$"}${"{"}vt.operateSuccess${"}"}',
	// 确定
	ok:'${"$"}${"{"}vt.sOk${"}"}',
	// 取消
	cancel:'${"$"}${"{"}vt.sCancel${"}"}'
};

var toolbars = new Ext.Toolbar({
			items:[
					'-','->','-',
					{id:'_save',text:'${"$"}${"{"}vt.sOk${"}"}',handler:_save,iconCls:'icon-table-save'},'-',
					{id:'_cancel',text:'${"$"}${"{"}vt.sCancel${"}"}',handler:_cancel,iconCls:'icon-undo'},'-',
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
							height:${formHieght?replace(',','')},
							border:false,
							bodyStyle:"background-color:#DFE8F6",
							contentEl:'div_center'
						}]
				}]
	});
//});
</script>
