<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.infolion.platform.bo.domain.*"%>
<%@ page import="com.infolion.platform.dpframework.util.StringUtils"%>
<%@ page import="java.util.*"%>
<%
	//年度可选值
	String years = "[['2009']]";
	//部门可选值
	String departments = "[['ABROAD'],['TELECOM']]";
	String chartDivId = "div_chart";
	String detailDivId = "div_chart_detail";
	BusinessObject bo = (BusinessObject) request
			.getAttribute("businessObject");
	ArrayList<String> condition = new ArrayList();
	for (GridColumn col : bo.getGridColumns())
	{
		if (StringUtils.transformToBoolean(col.getIsCondition()))
		{
			System.out.println(col.getProperty().getColumnName() + ","
					+ col.getProperty().getColumnJavaName() + ","
					+ col.getProperty().getFieldText());
			condition.add(col.getProperty().getFieldText());
		}
	}
%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="js/util.js"></script>
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
							<div id="div_department"></div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="div_chart_container" class="chart">
			<div id="<%=chartDivId%>"></div>
		</div>

		<div id="<%=detailDivId%>" style="width: 100%"></div>
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
					title:"条件选择",
					split:true,
					contentEl:'div_north'
				},{
					columnWidth:1,
					split:true,
					items:[{
						contentEl:"div_chart_container",
						id:'<%=chartDivId%>_ext'
					},{
						title:"图表明细",
						contentEl:"<%=detailDivId%>"
					}]
				}]
			}]
		});

		Ext.getDom('<%=chartDivId%>_ext').align='center';
		var year_combobox=new Ext.form.ComboBox({
			renderTo:"div_year",
			disabled:false,
			id:'combobox_year',
			hidden:false,
			editable:true,
			readOnly:false,	
			width:158,
			allowBlank:true,
		    store: new Ext.data.SimpleStore({
		        fields: ['text'],
		        data:<%=years%>
		    }),
			valueField:'text',
			displayField:'text',
			value:'',
		    mode: 'local',
		    triggerAction: 'all',
		    emptyText:'请选择...',
		    selectOnFocus:true
		});
		year_combobox.on('collapse',_createChart);
		var department_combobox=new Ext.form.ComboBox({
			renderTo:"div_department",
			id:"combobox_department",
			disabled:false,
			hidden:false,
			editable:true,
			readOnly:false,	
			width:158,
			allowBlank:true,
		    store: new Ext.data.SimpleStore({
		        fields: ['text'],
		        data:<%=departments%>
		    }),
			valueField:'text',
			displayField:'text',
			value:'',
		    mode: 'local',
		    triggerAction: 'all',
		    emptyText:'请选择...',
		    selectOnFocus:true
		});
		department_combobox.on('collapse',_createChart);
	});

	function queryChart()
	{
		<%=chartDivId%>_reload_chart({defaultCondition : defaultCondition,queryParameters:'',title:year+" Year "+department+" Department"});
	}
	var lastSelection={year:'',department:''};
	
	function _createChart(){
		var year=Ext.getCmp('combobox_year').getValue();
		var department=Ext.getCmp('combobox_department').getValue();
		var condition="";
		if(Utils.isEmpty(department)){
			return;
		}
		if(Utils.isEmpty(year)){
			return;
		}
		if(year == lastSelection.year && department==lastSelection.department){
			return;
		}
		lastSelection.year=year;
		lastSelection.department=department;
		condition+="department="+department+"";
		condition+=",myyear="+year+"";
		//defaultCondition+=" myyear='"+year+"'";
		<%=chartDivId%>_reload_chart({condition : condition,queryParameters:'',title:year+" Year "+department+" Department"});
	}	
</script>

<fisc:chart divId="<%=chartDivId%>" divDetailId="<%=detailDivId%>"
	width="400" height="300" boName="${boName}"
	defaultCondition="department='TELECOM' and myyear='2009'"></fisc:chart>
