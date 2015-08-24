<%-- 
  - Author(s):林进旭
  - Date: 2009-05-26
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:在线帮助控件
  - 在线帮助控件使用方法：
  - 该空间有两种不同的显示模式分别为:
  - A: closable = true 情况下，悬浮在目标元素之上时出现的提示信息显示关闭按钮。
  -    此种情况当鼠标悬浮在目标元素之上时出现的提示信息要手动进行关闭操作。
  -     eg: <fisc:onlineHelp boName="buyOrder" closable="true" width="200" showDelay="2000"></fisc:onlineHelp>
  - B: closable = false 情况下，悬浮在目标元素之上时出现的提示信息不显示关闭按钮。
  -    此种情况当鼠标悬浮在目标元素之上时出现的提示信息,而当鼠标离开目标元素时候则提示信息自动隐藏。
  -     eg: <fisc:onlineHelp boName="buyOrder" closable="false" width="200" showDelay="2000"></fisc:onlineHelp>
  - 
  - 注：在线帮助控件封装了Ext.ToolTip组件，但是并无完全暴露全部无暴露Ext.ToolTip的所有属性，
  -     如果你要使用到的属性不在TAG属性中，那么可以使用此扩展字段来进行扩展
  -     eg: <fisc:onlineHelp boName="buyOrder" closable="true" width="200" showDelay="2000" extendField="animCollapse:true"></fisc:onlineHelp>
  -
--%>

<%@ tag pageEncoding="utf-8"%>
<%@ tag import="com.infolion.platform.dpframework.uicomponent.onlineHelp.OnlineHelpTagHandler"%>
<%@ tag	import="org.apache.commons.lang.StringUtils"%>
<%@ tag	import="java.util.List"%>
<%@ tag	import="java.util.Set"%>
<%@ tag	import="java.util.Iterator"%>
<%@ tag	import="com.infolion.platform.bo.domain.DataElement"%>
<%@ tag	import="com.infolion.platform.bo.domain.DataElementText"%>
<%@ tag	import="com.infolion.platform.bo.domain.Property"%>

<%@ attribute name="boName" required="true" rtexprvalue="true" description="boName,业务对象名称"%>
<%@ attribute name="closable" required="false" rtexprvalue="true" description="是否显示关闭按钮"%>
<%@ attribute name="width" required="false" rtexprvalue="true" description="显示的ToolTip的宽度"%>
<%@ attribute name="showDelay" required="false" rtexprvalue="true" description="以毫秒表示的当鼠标进入目标元素后显示快捷提示的延迟时间(默认为 500)"%>
<%@ attribute name="extendField" required="false" rtexprvalue="true" description="高级功能，在线帮助控件扩展。考虑TAG并无暴露Ext.ToolTip的所有属性，如果你要使用到的属性不在TAG属性中，那么可以使用此扩展字段来进行扩展。eg: [height:99,blankText:'aaaa',]"%>

<%  
	jspContext.setAttribute("contextPath", request.getContextPath());
	jspContext.setAttribute("isDisable", "true");

	List<Property>  dataElementList  = OnlineHelpTagHandler.getAllDataElements(boName);
	Integer n = dataElementList.size();
	Integer i= 0 ;
	String strJsSource = "";
	StringBuilder sb = new StringBuilder();
	
	//是否显示关闭按钮
	boolean isColsable = false;
	
	if(StringUtils.isBlank(closable))
	{
		isColsable = false;
	}
	else	
	{
		if(closable.equals("true"))
		{
			isColsable = true;
		}
		else
		{
			isColsable = false;
		}
	}
	
	if(isColsable)
	{
		sb.append("autoHide: false,closable: true,draggable: true");
	}
	else
	{
		sb.append("autoHide: true,closable: false,draggable: false");
	}
	
	if(StringUtils.isBlank(width))
	{
		sb.append(",width:200");
	}
	else
	{
		sb.append(",width:" + width);
	}
	
	if(StringUtils.isNotBlank(showDelay))
	{
		sb.append(",showDelay:" + showDelay);
	}
	
	System.out.println("extendField:" + extendField);
	
	if(StringUtils.isNotBlank(extendField))
	{
		sb.append("," +extendField);
	}
	
	strJsSource = sb.toString();
	//ToolTip的targer属性
	String strTarget = "";
	//ToolTip.title
	String strTitle = "";
	//TOolTip.html //strJsSource
	String strHtml = "";
	
%>

<script type="text/javascript" defer="defer">
    
    <%for( i = 0;i < n; i ++ )
    {
    	Property property = dataElementList.get(i) ;
    	if(property.getPropertyType().equals("一般") == false)
    	{
    		continue;
    	}
    	DataElement dataElement = property.getDataElement();
    	strTarget = property.getPropertyName();
    	strTitle = property.getPropertyName() + ":";
    	Set<DataElementText> dataElementTexts = dataElement.getDataElementTexts();
    	Iterator<DataElementText> itData = dataElementTexts.iterator();
    	
    	
    	while(itData.hasNext())
    	{
    		DataElementText dataElementText = itData.next();
    		if(dataElementText.getDdLanguage().equals("1"))
    		{
    			strHtml = dataElementText.getDdText();
    		}
    	}
    		
    if(StringUtils.isNotBlank(strHtml))
    {
    %>
    try{
	    new Ext.ToolTip({
	        target: '<%=strTarget%>',
	        title: '<%=strTitle%>',
	        html: '<%=strHtml%>',
	        trackMouse:true,
	        <%=strJsSource%>
	    });
    }catch(ex){}
    <%}}%>   
</script>

