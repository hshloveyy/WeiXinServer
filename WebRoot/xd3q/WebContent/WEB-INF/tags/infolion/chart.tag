<%@ tag pageEncoding="utf-8"%>
<%@ tag import="com.infolion.platform.dpframework.util.StringUtils"%>
<%@ tag import="java.net.URLEncoder"%>
<%@ tag import="com.infolion.platform.bo.service.BusinessObjectService"%>
<%@ tag import="com.infolion.platform.bo.domain.BusinessObject"%>
<%@ tag
	import="com.infolion.platform.dpframework.uicomponent.chart.ChartType"%>
<%@tag import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@tag import="com.infolion.platform.dpframework.language.LangConstants"%>
<script type="text/javascript" src="js/lib/swfobject.js"></script>
<%@ attribute name="divId" required="true" description="要渲染的图表的div id"%>
<%@ attribute name="divDetailId" required="false"
	description="要渲染的明细表的div id"%>
<%@ attribute name="height" required="false" description="图表高度"%>
<%@ attribute name="width" required="false" description="图表宽度"%>
<%@ attribute name="boName" required="true" description="业务对象名"%>
<%@ attribute name="orderBySql" required="false" description="排序条件语句"%>
<%@ attribute name="defaultCondition" required="false"
	description="过滤条件"%>
<%@ attribute name="title" required="false" description="图表的标题"%>
<%
	height = StringUtils.isNullBlank(height) ? "250" : height;
	width = StringUtils.isNullBlank(width) ? "350" : width;
	jspContext.setAttribute("divId", divId);
	//根据业务对象配置的图表类型判断是否是漏斗图，漏斗图用不同的swf
	BusinessObject bo = BusinessObjectService.getBusinessObject(boName);
	boolean isFunnel = false;
	ChartType chartType = ChartType.getByName(bo.getFlashChart()
			.getChartType());
	if (ChartType.STREAMLINE_FUNNEL_CHART == chartType
			|| ChartType.SECTION_FUNNEL_CHART == chartType)
	{
		isFunnel = true;
	}
%>
<script type="text/javascript" defer="defer" src="js/lib/json2.js"></script>
<script type="text/javascript" defer="defer">
	var ${divId}_isFunnel=<%=isFunnel%>;
	
	/**
	 * 外部调用此方法以更新图表
	 */		
	function ${divId}_reload_chart(config){
		if(!config) config={};
		//默认配置
		var defaultConfig={
			isFunnel:<%=isFunnel%>,
			condition:'',
			orderBySql:'',
			queryParameters:'',
			width:<%=width%>,
			height:<%=height%>,
			title:'${title}',
			xfield:''};
		config=Ext.applyIf(config,defaultConfig);
		if(config.isFunnel){
			swfobject.embedSWF('swf/Funnel.swf', '${divId}', config.width, config.height, '9.0.0','expressInstall.swf',{'dataURL':getFWURL(config)});
		}
		else{
			swfobject.embedSWF('swf/open-flash-chart-bar-clicking.swf', '${divId}', config.width, config.height, '9.0.0','expressInstall.swf',{'data-file':getOFCURL(config),'loading':'<%=LanguageService.getText(LangConstants.SYS_DATA_ONLOAD)%>...'});
		}
	}
	
	//FunnelWedgits URL参数转换
	function getFWURL(config){
		var url=getURL(config);
		url=escape(url);
		return url;
	}
	
	//OFC URL参数转换
	function getOFCURL(config){
		var url=getURL(config);
		url=escape(escape(url));
		url=url.replace(/%2526/g,"%26");
		url=url.replace(/%253D/g,"=");
		url=url.replace(/%253F/g,"?");
		url=url.replace(/%2525/g,"%25");
		url=url.replace(/%25/g,"%2525");
		return url;
	}
	
	function getURL(config){
		var url="chartQueryController.spr?action=queryChartData&boName=${boName}&condition="+
			config.condition+
			//"department='%u7535%u4FE1' and myyear='2009'"+
			"&orderSql=" + config.orderBySql+
			"&title="+config.title+
			"&xfield="+config.xfield+
			"&start=0&limit=9999"+
			"&random="+Math.random()+
			"&"+config.queryParameters;
		return url;
	}
	
	<%if(!StringUtils.isNullBlank(divDetailId)){%>
	//-------加载图表明细
	
	//图表Flash调用此方法以更新图表明细
	function ${boName}_key_on_click(para){
		var src="<%=request.getContextPath()%>"+para.onClickURL;
		for(v in para){
			if(v!="onClickURL")
				src+='&'+v+'='+para[v];
		}
		${divDetailId}_frame.src=src;
	}

	var ${divId}_div=document.getElementById("${divId}");
	${divId}_div.style.width=${width};
	${divId}_div.style.height=${height};
	
	var ${divDetailId}_frame = document.createElement("iframe");
	${divDetailId}_frame.style.width="100%";
	${divDetailId}_frame.style.height="300";
	document.getElementById("${divDetailId}").appendChild(${divDetailId}_frame);
	<%}%>
	
	${divId}_reload_chart({});
</script>