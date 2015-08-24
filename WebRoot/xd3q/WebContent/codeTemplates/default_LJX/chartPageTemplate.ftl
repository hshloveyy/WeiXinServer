<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: ${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:   chartPageTemplate
  - <功能>${beanAttribute.boDescription}(${beanAttribute.boName})图表页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${"$"}${"{"}vt.chartPage${"}"}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Manage.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_south" class="chart_select"> 
<form id="mainForm" name="mainForm">
<table width="210" border="0" cellpadding="0" cellspacing="0">
<#list fromColumn as column>
<#if column?exists && (column?size>0)>
	<tr>
		<td align="right">${"$"}${"{"}vt.property.${column.propertyName}${"}"}：</td>
		<td>
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
				<fisc:calendar applyTo="${column.propertyName}.fieldValue" format="Ymd" divId="" fieldName="" ></fisc:calendar>
			<#elseif column.uiType=="05">
				<fisc:calendar applyTo="${column.propertyName}.fieldValue" format="Ymd" divId="" fieldName="" showTime="true"></fisc:calendar>
			</#if>
		<#-- 其他UI类别 -->
		<#else>
			<input type="text" id="${column.propertyName}.fieldValue" name="${column.propertyName}.fieldValue" value="">
			<input type="hidden" id="${column.propertyName}.fieldName" name="${column.propertyName}.fieldName" value="${column.property.tableName}.${column.property.columnName}"> 
			<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="${column.dataType}">  
			<input type="hidden" id="${column.propertyName}.option" name="${column.propertyName}.option" value="like">
		</#if>
		</td>
	</tr>
</#if> 	
</#list>

<#list fromColumnRange as column>
<#if column?exists && (column?size>0)>
	<tr>
		<td align="right">${"$"}${"{"}vt.property.${column.propertyName}${"}"} ${"$"}${"{"}vt.sFrom${"}"}：</td>
		<td>
			<input type="hidden" id="${column.propertyName}.fieldName" name="${column.propertyName}.fieldName" value="${column.property.tableName}.${column.property.columnName}"> 
			<input type="hidden" id="${column.propertyName}.isRangeValue" name="${column.propertyName}.isRangeValue" value="true">
			<!-- UITYPE:${column.uiType} -->
	<#-- 日期 -->
    <#if column.uiType=="04"> 
    		<input type="text" id="${column.propertyName}_minValue" name="${column.propertyName}.minValue">
    		<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="D"> 
			<fisc:calendar applyTo="${column.propertyName}_minValue" format="Ymd" divId="" fieldName=""></fisc:calendar>
		</td>
		</tr>
		<tr>
		<td align="right">${"$"}${"{"}vt.sTo${"}"}：</td>
		<td>
			<input type="text" id="${column.propertyName}_maxValue" name="${column.propertyName}.maxValue">			
			<fisc:calendar applyTo="${column.propertyName}_maxValue" format="Ymd" divId="" fieldName=""></fisc:calendar>
		</td>
	<#-- 日期 时间-->
	<#elseif column.uiType=="05">
		   <input type="text" id="${column.propertyName}_minValue" name="${column.propertyName}.minValue">
    		<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="D"> 		   
		   <fisc:calendar applyTo="${column.propertyName}_minValue" format="Ymd" divId="" fieldName="" showTime="true"></fisc:calendar>
	   </td>
	    </tr>
		<tr>
		<td align="right">${"$"}${"{"}vt.sTo${"}"}：</td>
		<td>
			<input type="text" id="${column.propertyName}_maxValue" name="${column.propertyName}.maxValue">
			<fisc:calendar applyTo="${column.propertyName}_maxValue" format="Ymd" divId="" fieldName="" showTime="true"></fisc:calendar>
		</td>
	<#-- 搜索帮助 -->
	<#elseif column.uiType=="11">
    		<input type="hidden" id="${column.propertyName}.dataType" name="${column.propertyName}.dataType" value="${column.dataType}"> 
			<div id="div_${column.propertyName}_minValue_sh"></div>
			<fisc:searchHelp divId="div_${column.propertyName}_minValue_sh" boName="${beanAttribute.boName}" boProperty="${column.propertyName}" searchType="field" hiddenName="${column.propertyName}.minValue" valueField="${column.property.shSourceColumn}" displayField="${column.property.shTextColumn}"></fisc:searchHelp>
		</td>
		</tr>
		<tr>
		<td align="right">${"$"}${"{"}vt.sTo${"}"}：</td>
		<td>
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
			<input type="text" id="${column.propertyName}_minValue" name="${column.propertyName}.minValue">
		</td>
		</tr>
		<tr>
		<td align="right">${"$"}${"{"}vt.sTo${"}"}：</td>
		<td>
			<input type="text" id="${column.propertyName}_maxValue" name="${column.propertyName}.maxValue">
		</td>
	</#if>
	</tr>
</#if>
</#list>

</table>
</form>
</div>

<div id="div_chart_container" class="chart">
	<div id="${bo.boName}Chart_div"></div>
</div>

<div id="${bo.boName}ChartDetail_div" style="width: 100%"></div>
</body>
</html>
<script type="text/javascript" defer="defer">

/**
 * EXT 布局
 */
Ext.onReady(function(){
		var viewport = new Ext.Viewport({
			layout:"fit",
			autoScroll:true,
			items:[{
				layout:"column",
				autoScroll:true,
				split:true,
				items:[{
					width:236,					
					split:true,
					title:'${"$"}${"{"}vt.sCondSelect${"}"}',
					items:[{
						height:28,
						region:"north",
						contentEl:"div_toolbar"
					},{
						region:"south",
						contentEl:"div_south"
					}]
				},{
					columnWidth:1,
					split:true,
					items:[{
						contentEl:"div_chart_container",
						id:'${bo.boName}Chart_div_ext'
					},{
						title:'${"$"}${"{"}vt.chartDetail${"}"}',
						contentEl:"${bo.boName}ChartDetail_div"
					}]
				}]
			}]
		});
		Ext.getDom('${bo.boName}Chart_div_ext').align='center';
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'${"$"}${"{"}vt.mQuery${"}"}',handler:_queryChart,iconCls:'icon-cls'},'-',
				{id:'_cls',text:'${"$"}${"{"}vt.sClean${"}"}',handler:_resetForm,iconCls:'icon-cls'},'-'
				],
		renderTo:"div_toolbar"
	});
	
	/**
	*图表查询
	**/
	function _queryChart()
	{
	    //图表标题
	    var chartTitle ="" ;
		var queryParam = Form.serialize('mainForm');
		//根据查询条件在属性和属性描述构成
<#list fromColumn as column>
<#if column?exists && (column?size>0)>
        var ${column.propertyName} = ${'$'}("${column.propertyName}.fieldValue").value + " ${column.property.fieldText?default('')}" ;
        chartTitle = chartTitle + ${column.propertyName};
</#if>
</#list>
<#list fromColumnRange as column>
<#if column?exists && (column?size>0)>
        var ${column.propertyName} = ${'$'}("${column.propertyName}.fieldValue").value + " ${column.property.fieldText?default('')}" ;
        if(!chartTitle){
       	 chartTitle = chartTitle + ${column.propertyName};
        }
</#if>
</#list>
		//在配置中描述在标题（加在图表标题在后面）
		var configTitle = "${bo.flashChart.chartTitle}";
		chartTitle = chartTitle + configTitle;
		//divid + "_reload_chart"为函数名
		${bo.boName}Chart_div_reload_chart({queryParameters:queryParam,title:chartTitle});
	}
	//清空操作
	function _resetForm()
	{
		document.all.mainForm.reset();
	}
</script>
<fisc:chart divId="${bo.boName}Chart_div" divDetailId="${bo.boName}ChartDetail_div" width="400" height="300" boName="${bo.boName}" defaultCondition=""></fisc:chart>