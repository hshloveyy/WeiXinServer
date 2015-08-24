<%-- 
  - Author(s):林进旭
  - Date: 2009-05-13
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:日期控件
  - 日期控件使用方法：
  - A: 指定 applyTo属性，不指定  divId (divId="")，fieldName也可以不指定(fieldName="")。
  -    次种情况下，可以使用  var date11 = Ext.getDom("date11").value; 获取当前控件说选择的日期的值。
  -    如果有指定 fieldName 那么也可以使用  var date12 = Ext.getCmp("date12").value;
  - eg:  <fisc:calendar divId="" applyTo="date11" fieldName="date12" ></fisc:calendar>
  -      
  - B：  指定 divId属性，不指定  applyTo (applyTo="")，次时fieldName必需指定。
  - eg:  <fisc:calendar divId="divdate1" applyTo="" fieldName="date1" ></fisc:calendar>
  - 可以使用 var date1 = Ext.getDom("date1").value;
  -
  -  使用当前日期控件的实例对象取值等;规则为    "calendar_" + divId 或者  "calendar_" + applyTo
  -  eg:
  - 	var date13 = calendar_divdate1.value;   var date14 = calendar_date11.value;
  - C：使用showTime属性来开启时间显示。 利用 minValue maxValue来控制可选择的日期区间。
  -    eg:<fisc:calendar divId="" applyTo="date6" fieldName="date6" minValue="2009-05-01 23:59:00" minText="日期选择不能小于 2009-05-01 23:59:00" maxValue="2009-05-20 23:59:00" maxText="日期选择不能大于 2009-05-20 23:59:00"  showTime="true"></fisc:calendar>
  - D: 使用高级属性扩展功能 extendField ：
  -    eg:<fisc:calendar divId="" applyTo="date5" fieldName="date5" extendField="allowBlank:false,emptyText:'请选择日期!',blankText:'3'," minValue="2009-05-05 23:59:00" minText="日期选择不能小于 2009-05-05 23:59:00" maxValue="2009-05-14 11:59:00" maxText="日期选择不能大于 2009-05-14 11:59:00"  showTime="true" ></fisc:calendar>
  -  
  - LJX 20090531 当加入脚本 包含时候会报错，故去掉脚本包含 
--%>
<%@ tag pageEncoding="utf-8"%>
<%@ tag import="com.infolion.platform.dpframework.uicomponent.calendar.web.CalendarTagHandler"%>
<%@ tag import="com.infolion.platform.dpframework.uicomponent.calendar.domain.Calendar"%>
<%@ tag	import="org.apache.commons.lang.StringUtils"%>
<%@ tag	import="com.infolion.platform.basicApp.authManage.domain.OperationType"%>
<%@ attribute name="format" required="false"  rtexprvalue="true" description="用以覆盖本地化的默认日期格式化字串。字串必须为符合 Date#parseDate 指定的形式(默认为 'm/d/y')。"%>
<%@ attribute name="fieldName" required="true" rtexprvalue="true" description="字段名称"%>
<%@ attribute name="applyTo" required="true" rtexprvalue="true" description="applyTo,用于日期控件定位。设定了applyTo就不要设定fieldName。如果设定了applyTO的属性(applyTo不为空)则divId将不起作用。applyTo优先"%>
<%@ attribute name="divId" required="true" rtexprvalue="true" description="div id,用于日期控件定位。关于控件定位有两方式，另一种为设定applyTO属性。如果设定了applyTO的属性(applyTo不为空)则divId将不起作用。applyTo优先"%>
<%@ attribute name="minValue" required="false" rtexprvalue="true" description="允许的最小日期。可以是一个 Javascript 日期对象或一个有效格式的字串(默认为 null),只有当 moreThanToday、lessThanToday属性全为false时候此属性才起作用"%>
<%@ attribute name="minText" required="false" rtexprvalue="true" description="当字段的日期早于 minValue 属性指定值时显示的错误文本(默认为 'The date in this field must be after {minValue}')。"%>
<%@ attribute name="maxValue" required="false" rtexprvalue="true" description="允许的最大日期。可以是一个 Javascript 日期对象或一个有效格式的字串(默认为 null),只有当 moreThanToday、lessThanToday属性全为false时候此属性才起作用"%>
<%@ attribute name="maxText" required="false" rtexprvalue="true" description="当字段的日期晚于 maxValue 属性指定值时显示的错误文本(默认为 'The date in this field must be before {maxValue}')。"%>
<%@ attribute name="moreThanToday" required="false" rtexprvalue="true" description="是否设定模式为选择的日期只能大于当天日期" %>
<%@ attribute name="lessThanToday" required="false" rtexprvalue="true" description="是否设定模式为选择的日期只能小于当天日期"%>
<%@ attribute name="showTime" required="false" rtexprvalue="true" description="是否显示时间（默认值：false）"%>
<%@ attribute name="width" required="false" rtexprvalue="true" description="控件宽度（默认值：120）"%>
<%@ attribute name="extendField" required="false" rtexprvalue="true" description="高级功能，日期控件扩展。考虑TAG并无暴露Ext.form.DateField的所有属性，如果你要使用到的属性不在TAG属性中，那么可以使用此扩展字段来进行扩展。eg: [height:99,blankText:'aaaa',]"%>
<%@ attribute name="defaultValue" required="false" rtexprvalue="true" description="值"%>
<%@ attribute name="readonly" required="false" rtexprvalue="true" description="只读（默认值：true）" %>
<%@ attribute name="disable" required="false" rtexprvalue="true" description="失效（默认值：false）" %>

<%
    boolean isReadOnly = "false".equalsIgnoreCase(readonly) ? false : true;
    boolean isDisable = "true".equalsIgnoreCase(disable) ? true : false;
    String strMinValue = StringUtils.isBlank(minValue) ? "" : minValue;
    String strMinText = StringUtils.isBlank(minText) ? "" : minText;
    
    String strMaxValue = StringUtils.isBlank(maxValue) ? "" : maxValue;
    String strMaxText = StringUtils.isBlank(maxText) ? "" : maxText;
    boolean isMoreThanToday = "true".equalsIgnoreCase(moreThanToday) ? true : false; 
    boolean isLessThanToday = "true".equalsIgnoreCase(lessThanToday) ? true : false; 
    boolean isShowTime = "true".equalsIgnoreCase(showTime) ? true : false; 
    
    String strApplyTo = StringUtils.isBlank(applyTo) ? "" : applyTo;
    String strDivId = StringUtils.isBlank(divId) ? "" : divId;
    String strExtendField = StringUtils.isBlank(extendField) ? "" : extendField;
    String strFormat="";
    
	//是否显示 时间(日期控件的使用模式是否显示时间)
	boolean isShow = false;
	if(StringUtils.isBlank(showTime))
	{
		strFormat = StringUtils.isBlank(format) ? "Y-m-d" : format;
		jspContext.setAttribute("isShowTime", "false");
	}
	else if("false".equalsIgnoreCase(showTime))
	{
		strFormat = StringUtils.isBlank(format) ? "Y-m-d" : format;
		jspContext.setAttribute("isShowTime", "false");
	}
	else if("true".equalsIgnoreCase(showTime))
	{
		strFormat = StringUtils.isBlank(format) ? "Y-m-d H:i:s" : format;
		jspContext.setAttribute("isShowTime", "true");
		isShow = true ;
	}
	
    //控件宽度
    String strWidth;
    if(isShow)
    {
   		strWidth = StringUtils.isBlank(width) ? "202" : width;
    }
    else
    {
     	strWidth = StringUtils.isBlank(width) ? "202" : width;	
    }
    
    String strDefaultValue = StringUtils.isBlank(defaultValue) ? "" : defaultValue;
     
    //根据UI上设定的tag各属性的值，产生Calendar实例。
    Calendar calendar = new Calendar(strFormat,fieldName,strMinValue, strMinText,
    								strMaxValue,  strMaxText,  isMoreThanToday,
    								isLessThanToday,isShowTime,strWidth,strDefaultValue,
    		                         isReadOnly, isDisable,strApplyTo,strDivId,strExtendField);
    
    CalendarTagHandler calendarTagHandler = new CalendarTagHandler(calendar);
    //取得Ext对像生成代码
    String strExtCode = calendarTagHandler.getExtCode();
    //Ext对像生成代码
    jspContext.setAttribute("extCode", strExtCode);
    
	jspContext.setAttribute("contextPath", request.getContextPath());
	
	String[] arr = StringUtils.isNotBlank(applyTo) ?  applyTo.split("\\."): divId.split("\\.");
	//默认的组件名字
	//System.out.println(isShowTime + ";" + showTime);
	//System.out.println(applyTo);
	//System.out.println(divId);
	//System.out.println( applyTo.split("\\.")[0]);
	//System.out.println( divId.split("\\.")[0]);
	//System.out.println(arr.length);
	//System.out.println(arr.length>1);
	String objName ="";
	if(arr.length>1)
	{
		objName=arr[0] + "_" + arr[1];
	}
	else
	{
		objName=arr[0];
	}
	
	if(StringUtils.isBlank(divId) && StringUtils.isNotBlank(applyTo))
	{
		jspContext.setAttribute("cObj", "calendar_" + objName);
	}
	else
	{
		jspContext.setAttribute("cObj", "calendar_" + objName);
	}

	//applyTo  renderTo
    //System.out.println("生成的EXT代码：" + strExtCode);
	//System.out.println(divId + ".实例名称:" + "calendar_" + objName);
	//System.out.println(divId + ".divid:" + divId);
	//System.out.println(divId + ".fieldName:" + "");
%>

<script type="text/javascript" defer="defer">
//Ext.onReady(function(){
        <%if(isShowTime==false){%>
	    var ${cObj} = new Ext.form.DateField(
  		  	        {
  		  	          ${extCode}	
				   	}
			);
		<%}else{%>
	    var ${cObj} = new Ext.form.DateTimeField(
		  	        {
		  	          ${extCode}	
			     	}
		   	);  
	    <%}%>
//});

</script>
