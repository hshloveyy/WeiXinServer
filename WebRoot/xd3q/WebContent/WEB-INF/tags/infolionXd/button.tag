<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.component.processor.ReflectionHandler"%>
<%@ tag	import="com.infolion.platform.console.sys.context.UserContext"%>
<%@ tag	import="com.infolion.platform.sys.Constants"%>
<%@ attribute name="id" required="true" description="div id"%>
<%@ attribute name="onclick" required="true" description=""%>
<%@ attribute name="value" required="true" description=""%>
<%@ attribute name="cls" required="false" description="css类"%>
<%@ attribute name="style" required="false" description=""%>
<%@ attribute name="size" required="false" description=""%>
<%@ attribute name="modelName" required="true"  description="传递页面ID,用于比较资源中的parentid"%>
<%@ attribute name="pageResourcesName" required="true"  description="用于比较资源中的resourcename"%>
<%@ attribute name="other" required="false"  description="自定义后台参数"%>
<%@ attribute name="underlyingClass" required="false"  description="后台处理类"%>


<%
	String clas = cls==null||cls.trim()==""?"":"class='"+cls+"' ";
	String stl = style==null||style.trim()==""?"":"style='"+style+"' ";
	String sz = size==null||size.trim()==""?"":"size='"+size+"' ";
	boolean isDisabled = false;
	String dsb ="";
	if(underlyingClass!=null){
			ReflectionHandler
			Object obj;
			if(other!=null)
			 	obj = ReflectionHandler.process(underlyingClass,modelName,pageResourcesName);
			else
				obj = ReflectionHandler.process(underlyingClass,modelName,pageResourcesName);
			if(obj!=null)
				isDisabled = (Boolean)obj;
	}
	if(isDisabled)
		dsb =" disabled=true ";
	else
		dsb="";
%>
<input type='button' id='${id}' name='${id}' onclick='${onclick}' value='${value}' <%=dsb%> <%=clas%> <%=stl%> <%=sz%>/>
