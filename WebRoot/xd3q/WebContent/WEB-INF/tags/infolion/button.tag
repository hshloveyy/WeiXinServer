<%-- 
  - Author(s):林进旭
  - Date: 2009-05-13
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:按钮控件
  -
--%>

<%@ tag pageEncoding="utf-8"%>
<%@ tag import="com.infolion.platform.dpframework.uicomponent.button.ButtonTagHandler"%>
<%@ tag	import="com.infolion.platform.dpframework.util.StringUtils"%>
<%@ attribute name="divId" required="true" rtexprvalue="true" description="div id,用于button定位"%>
<%@ attribute name="name" required="false" rtexprvalue="true" description="按钮名称"%>
<%@ attribute name="resourceName" required="false" rtexprvalue="true" description="resourceName标识该按钮的功能名称，与后台的定义结合，完成权限控制功能"%>
<%@ attribute name="onclick" required="false" rtexprvalue="true" description="指定按钮单击事件句柄"%>
<%@ attribute name="text" required="true"  rtexprvalue="true" description="按钮显示的文本"%>
<%@ attribute name="hidden" required="false"  rtexprvalue="true" description="隐藏"%>
<%@ attribute name="disable" required="false"  rtexprvalue="true" description="可编辑"%>
<% 
	jspContext.setAttribute("contextPath", request.getContextPath());
	//默认的组件名字
	jspContext.setAttribute("btnObj", "btn_" + divId);
	//组件ID,Name属性值。
	if(StringUtils.isBlank(name))
	{
		jspContext.setAttribute("btnName", "btn" + divId);
	}
	else
	{
		jspContext.setAttribute("btnName", name);
	}

	if(StringUtils.isBlank(disable) || "false".equals(disable)){
		//判断是否有权限
		if(StringUtils.isBlank(resourceName))  //如果没有指定resourceName ， 默认为有权限。
		{
			jspContext.setAttribute("isDisable", "false");
		}
		else if(ButtonTagHandler.isPermissions(resourceName))
		{
			jspContext.setAttribute("isDisable", "false");
		}
		else
		{
			jspContext.setAttribute("isDisable", "true");
		}
	}else{
		jspContext.setAttribute("isDisable", disable);
	}
	jspContext.setAttribute("hidden", StringUtils.isNullBlank(hidden) ?  "false":hidden);
		
%>

<script type="text/javascript" defer="defer">
Ext.onReady(function(){
    var ${btnObj} = 
    	new Ext.Button({
			text:'${text}',
		    id:'${btnName}',
		    name:'${btnName}',
		    handler:${onclick},
		    renderTo:'${divId}',
		    hidden:${hidden},
		    disabled:${isDisable}
   		});
});
</script>