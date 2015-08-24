<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年04月21日 15点06分43秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:   chartPageTemplate
  - <功能>项目服务（测试）(TestProjectService)图表页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.chartPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/TEMP/Temp/testProjectServiceManage.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_south" class="chart_select"> 
<form id="mainForm" name="mainForm">
<table width="210" border="0" cellpadding="0" cellspacing="0">


</table>
</form>
</div>

<div id="div_chart_container" class="chart">
	<div id="TestProjectServiceChart_div"></div>
</div>

<div id="TestProjectServiceChartDetail_div" style="width: 100%"></div>
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
					title:'${vt.sCondSelect}',
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
						id:'TestProjectServiceChart_div_ext'
					},{
						title:'${vt.chartDetail}',
						contentEl:"TestProjectServiceChartDetail_div"
					}]
				}]
			}]
		});
		Ext.getDom('TestProjectServiceChart_div_ext').align='center';
	});
	
	var toolbars = new Ext.Toolbar({
		items:[
				'-',
				{id:'_search',text:'${vt.mQuery}',handler:_queryChart,iconCls:'icon-cls'},'-',
				{id:'_cls',text:'${vt.sClean}',handler:_resetForm,iconCls:'icon-cls'},'-'
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
		//在配置中描述在标题（加在图表标题在后面）
		var configTitle = " ";
		chartTitle = chartTitle + configTitle;
		//divid + "_reload_chart"为函数名
		TestProjectServiceChart_div_reload_chart({queryParameters:queryParam,title:chartTitle});
	}
	//清空操作
	function _resetForm()
	{
		document.all.mainForm.reset();
	}
</script>
<fisc:chart divId="TestProjectServiceChart_div" divDetailId="TestProjectServiceChartDetail_div" width="400" height="300" boName="TestProjectService" defaultCondition=""></fisc:chart>