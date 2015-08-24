<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年09月08日 16点36分21秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:   chartPageTemplate
  - <功能> (TestChart)图表页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 图表页面</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/test/testChartManage.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_south" class="chart_select"> 
<form id="mainForm" name="mainForm">
<table width="210" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right">年度：</td>
		<td>
		<!-- UITYPE:  -->
			<input type="text" id="myyear.fieldValue" name="myyear.fieldValue" value="">
			<input type="hidden" id="myyear.fieldName" name="myyear.fieldName" value="YDEMOCHART.MYYEAR"> 
			<input type="hidden" id="myyear.dataType" name="myyear.dataType" value="S">  
			<input type="hidden" id="myyear.option" name="myyear.option" value="like">
		</td>
	</tr>
	<tr>
		<td align="right">事业部：</td>
		<td>
		<!-- UITYPE:  -->
			<input type="text" id="department.fieldValue" name="department.fieldValue" value="">
			<input type="hidden" id="department.fieldName" name="department.fieldName" value="YDEMOCHART.DEPARTMENT"> 
			<input type="hidden" id="department.dataType" name="department.dataType" value="S">  
			<input type="hidden" id="department.option" name="department.option" value="like">
		</td>
	</tr>


</table>
</form>
</div>

<div id="div_chart_container" class="chart">
	<div id="TestChartChart_div"></div>
</div>

<div id="TestChartChartDetail_div" style="width: 100%"></div>
</body>
</html>
<script type="text/javascript">

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
					title:"条件选择",
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
						id:'TestChartChart_div_ext'
					},{
						title:"图表明细",
						contentEl:"TestChartChartDetail_div"
					}]
				}]
			}]
		});
		Ext.getDom('TestChartChart_div_ext').align='center';
		
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'查询',handler:_queryChart,iconCls:'icon-cls'},'-',
				{id:'_cls',text:'清空',handler:_resetForm,iconCls:'icon-cls'},'-'
				],
		renderTo:"div_toolbar"
	});
	
	/**
	*图表查询
	**/
	function _queryChart()
	{
	    //图表标题
	    var chartTitle ="";
		var queryParam = Form.serialize('mainForm');
		//根据查询条件在属性和属性描述构成
        var myyear = $("myyear.fieldValue").value + " 年度" ;
        chartTitle = chartTitle + myyear;
        var department = $("department.fieldValue").value + " 事业部" ;
        chartTitle = chartTitle + department;
		//在配置中描述在标题（加在图表标题在后面）
		var configTitle = "TEST CHART";
		chartTitle = chartTitle + configTitle;
		TestChartChart_div_reload_chart({queryParameters:queryParam,title:chartTitle});
	}
	//清空操作
	function _resetForm()
	{
		document.all.mainForm.reset();
	}
</script>
<fisc:chart divId="TestChartChart_div" divDetailId="TestChartChartDetail_div" width="400" height="300" boName="TestChart" defaultCondition=""></fisc:chart>