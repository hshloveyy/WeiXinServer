<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: 2009年12月25日 08点58分58秒
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:   chartPageTemplate
  - <功能>检验信息(Examine)图表页面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vt.chartPage}</title>
<script language="javascript" type="text/javascript" src="<%= request.getContextPath() %>/js/TEMP/examine/examineManage.js"></script>
</head>
<body>
<div id="div_toolbar"></div>
<div id="div_south" class="chart_select"> 
<form id="mainForm" name="mainForm">
<table width="210" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right">${vt.property.outward3}：</td>
		<td>
		<!-- UITYPE:01 -->
			<input type="text" id="outward3.fieldValue" name="outward3.fieldValue" value="">
			<input type="hidden" id="outward3.fieldName" name="outward3.fieldName" value="YEXAMINE.OUTWARD3"> 
			<input type="hidden" id="outward3.dataType" name="outward3.dataType" value="S">  
			<input type="hidden" id="outward3.option" name="outward3.option" value="like">
		</td>
	</tr>

	<tr>
		<td align="right">${vt.property.vi} ${vt.sFrom}：</td>
		<td>
			<input type="hidden" id="vi.fieldName" name="vi.fieldName" value="YEXAMINE.VI"> 
			<input type="hidden" id="vi.isRangeValue" name="vi.isRangeValue" value="true">
			<!-- UITYPE:13 -->
			<input type="text" id="vi_minValue" name="vi.minValue">
		</td>
		</tr>
		<tr>
		<td align="right">${vt.sTo}：</td>
		<td>
			<input type="text" id="vi_maxValue" name="vi.maxValue">
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.outward} ${vt.sFrom}：</td>
		<td>
			<input type="hidden" id="outward.fieldName" name="outward.fieldName" value="YEXAMINE.OUTWARD"> 
			<input type="hidden" id="outward.isRangeValue" name="outward.isRangeValue" value="true">
			<!-- UITYPE:01 -->
			<input type="text" id="outward_minValue" name="outward.minValue">
		</td>
		</tr>
		<tr>
		<td align="right">${vt.sTo}：</td>
		<td>
			<input type="text" id="outward_maxValue" name="outward.maxValue">
		</td>
	</tr>
	<tr>
		<td align="right">${vt.property.certificatedate} ${vt.sFrom}：</td>
		<td>
			<input type="hidden" id="certificatedate.fieldName" name="certificatedate.fieldName" value="YEXAMINE.CERTIFICATEDATE"> 
			<input type="hidden" id="certificatedate.isRangeValue" name="certificatedate.isRangeValue" value="true">
			<!-- UITYPE:04 -->
    		<input type="text" id="certificatedate_minValue" name="certificatedate.minValue">
    		<input type="hidden" id="certificatedate.dataType" name="certificatedate.dataType" value="D"> 
			<fisc:calendar applyTo="certificatedate_minValue" format="Ymd" divId="" fieldName=""></fisc:calendar>
		</td>
		</tr>
		<tr>
		<td align="right">${vt.sTo}：</td>
		<td>
			<input type="text" id="certificatedate_maxValue" name="certificatedate.maxValue">			
			<fisc:calendar applyTo="certificatedate_maxValue" format="Ymd" divId="" fieldName=""></fisc:calendar>
		</td>
	</tr>

</table>
</form>
</div>

<div id="div_chart_container" class="chart">
	<div id="ExamineChart_div"></div>
</div>

<div id="ExamineChartDetail_div" style="width: 100%"></div>
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
						id:'ExamineChart_div_ext'
					},{
						title:'${vt.chartDetail}',
						contentEl:"ExamineChartDetail_div"
					}]
				}]
			}]
		});
		Ext.getDom('ExamineChart_div_ext').align='center';
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
        var outward3 = $("outward3.fieldValue").value + " 短文本" ;
        chartTitle = chartTitle + outward3;
        var vi = $("vi.fieldValue").value + " 检查信息ID" ;
        if(!chartTitle){
       	 chartTitle = chartTitle + vi;
        }
        var outward = $("outward.fieldValue").value + " 备注" ;
        if(!chartTitle){
       	 chartTitle = chartTitle + outward;
        }
        var certificatedate = $("certificatedate.fieldValue").value + " 凭证日期" ;
        if(!chartTitle){
       	 chartTitle = chartTitle + certificatedate;
        }
		//在配置中描述在标题（加在图表标题在后面）
		var configTitle = " ";
		chartTitle = chartTitle + configTitle;
		//divid + "_reload_chart"为函数名
		ExamineChart_div_reload_chart({queryParameters:queryParam,title:chartTitle});
	}
	//清空操作
	function _resetForm()
	{
		document.all.mainForm.reset();
	}
</script>
<fisc:chart divId="ExamineChart_div" divDetailId="ExamineChartDetail_div" width="400" height="300" boName="Examine" defaultCondition=""></fisc:chart>