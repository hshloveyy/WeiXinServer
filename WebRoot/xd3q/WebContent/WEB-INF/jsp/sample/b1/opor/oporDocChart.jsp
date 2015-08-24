<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2010年03月25日 19点56分06秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:   chartPageTemplate
  - <功能>采购订单(OporDoc)图表页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.chartPage}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/sample/b1/opor/oporDocManage.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_south" class="chart_select"> 
<form id="mainForm" name="mainForm">
<table width="210" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right">${vt.property.cardCode}：</td>
		<td>
		<!-- UITYPE:11 -->
			<div id="div_cardCode_sh"></div>
			<input type="hidden" id="cardCode.fieldName" name="cardCode.fieldName" value="YOPORDOC.CARDCODE"> 
			<input type="hidden" id="cardCode.dataType" name="cardCode.dataType" value="S">  
			<input type="hidden" id="cardCode.option" name="cardCode.option" value="like">
			<fisc:searchHelp divId="div_cardCode_sh" boName="OporDoc" boProperty="cardCode" searchType="field" hiddenName="cardCode.fieldValue" valueField="CARDCODE" displayField="CARDCODE"></fisc:searchHelp>
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.taxDate}：</td>
		<td>
		<!-- UITYPE:04 -->
			<input type="text" id="taxDate.fieldValue" name="taxDate.fieldValue" value="">
			<input type="hidden" id="taxDate.fieldName" name="taxDate.fieldName" value="YOPORDOC.TAXDATE"> 
			<input type="hidden" id="taxDate.dataType" name="taxDate.dataType" value="D">  
			<input type="hidden" id="taxDate.option" name="taxDate.option" value="like">
				<fisc:calendar applyTo="taxDate.fieldValue" format="Ymd" divId="" fieldName="" ></fisc:calendar>
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.docDate}：</td>
		<td>
		<!-- UITYPE:04 -->
			<input type="text" id="docDate.fieldValue" name="docDate.fieldValue" value="">
			<input type="hidden" id="docDate.fieldName" name="docDate.fieldName" value="YOPORDOC.DOCDATE"> 
			<input type="hidden" id="docDate.dataType" name="docDate.dataType" value="D">  
			<input type="hidden" id="docDate.option" name="docDate.option" value="like">
				<fisc:calendar applyTo="docDate.fieldValue" format="Ymd" divId="" fieldName="" ></fisc:calendar>
		</td>
	</tr>


</table>
</form>
</div>

<div id="div_chart_container" class="chart">
	<div id="OporDocChart_div"></div>
</div>

<div id="OporDocChartDetail_div" style="width: 100%"></div>
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
						id:'OporDocChart_div_ext'
					},{
						title:'${vt.chartDetail}',
						contentEl:"OporDocChartDetail_div"
					}]
				}]
			}]
		});
		Ext.getDom('OporDocChart_div_ext').align='center';
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
        var cardCode = $("cardCode.fieldValue").value + " 业务伙伴编码" ;
        chartTitle = chartTitle + cardCode;
        var taxDate = $("taxDate.fieldValue").value + " 单据日期" ;
        chartTitle = chartTitle + taxDate;
        var docDate = $("docDate.fieldValue").value + " 过帐日期" ;
        chartTitle = chartTitle + docDate;
		//在配置中描述在标题（加在图表标题在后面）
		var configTitle = " ";
		chartTitle = chartTitle + configTitle;
		//divid + "_reload_chart"为函数名
		OporDocChart_div_reload_chart({queryParameters:queryParam,title:chartTitle});
	}
	//清空操作
	function _resetForm()
	{
		document.all.mainForm.reset();
	}
</script>
<fisc:chart divId="OporDocChart_div" divDetailId="OporDocChartDetail_div" width="400" height="300" boName="OporDoc" defaultCondition=""></fisc:chart>