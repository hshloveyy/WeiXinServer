<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>图表演示</title>
	</head>

	<body>
		<div id="div_north" class="chart_select">
			<form id="mainForm" name="mainForm">
				<table width="210" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center" height="10">
						</td>
					</tr>
					<tr>
						<td align="right">
							年度：
						</td>
						<td>
							<input type="text" id="year.fieldValue" name="year.fieldValue"
								value="">
							<input type="hidden" id="year.fieldName" name="year.fieldName"
								value="myyear">
							<input type="hidden" id="year.dataType" name="year.dataType"
								value="S">
							<input type="hidden" id="year.option" name="year.option"
								value="like">

							<div id="div_year"></div>
						</td>
					</tr>
					<tr>
						<td align="center" height="10">
						</td>
					</tr>
					<tr>
						<td align="right">
							事业部：
						</td>
						<td>
							<input type="text" id="department.fieldValue"
								name="department.fieldValue" value="">
							<input type="hidden" id="department.fieldName"
								name="department.fieldName" value="department">
							<input type="hidden" id="department.dataType"
								name="department.dataType" value="S">
							<input type="hidden" id="department.option"
								name="department.option" value="like">
							<div id="div_department"></div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="div_chart_container" class="chart">
			<div id="TestChart_div"></div>
		</div>

		<div id="TestChartDetail_div" style="width: 100%"></div>
		<div id="div_toolbar"></div>
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
						contentEl:"div_north"
					}]
				},{
					columnWidth:1,
					split:true,
					items:[{
						contentEl:"div_chart_container",
						id:'TestChart_div_ext'
					},{
						title:"图表明细",
						contentEl:"TestChartDetail_div"
					}]
				}]
			}]
		});
		Ext.getDom('TestChart_div_ext').align='center';
		
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
		var queryParam = Form.serialize('mainForm');
		//yearTitle和departmentTitle根据查询条件在属性和属性描述构成
		var yearTitle = $("year.fieldValue").value+"年度";
		var departmentTitle =  $("department.fieldValue").value+"事业部";
		//在配置中描述在标题（加在图表标题在后面）
		var configTitle = "TEST CHART";
		//图表标题构成：
		var chartTitle = yearTitle+departmentTitle+configTitle;
		//divid + "_reload_chart"为函数名
		TestChart_div_reload_chart({queryParameters:queryParam,title:chartTitle});
	}
	//清空操作
	function _resetForm()
	{
		document.all.mainForm.reset();
	}
</script>

<fisc:chart divId="TestChart_div" divDetailId="TestChartDetail_div"
	width="400" height="300" boName="TestChart"></fisc:chart>
