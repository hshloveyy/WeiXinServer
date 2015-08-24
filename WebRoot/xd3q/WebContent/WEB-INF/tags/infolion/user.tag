<%@ tag pageEncoding="utf-8"%>
<%@ tag	import="com.infolion.platform.basicApp.authManage.UserContextHolder"%>
<%@ tag	import="com.infolion.platform.dpframework.core.cache.SysCachePoolUtils"%>
<%@ tag	import="org.apache.commons.lang.StringUtils"%>
<%@ attribute name="boName" required="true" rtexprvalue="true"	description="业务对象名"%>
<%@ attribute name="boProperty" required="true" rtexprvalue="true"	description="业务对象属性"%>
<%@ attribute name="userId" required="false" description="需要转换的用户id"%>
<%@ attribute name="isLoginUser" required="false" description="是否显示当前登录用户"%>
<%
	String realNameText = null;
	//如果是取得当前登录用户
	if("true".equals(isLoginUser)){
		realNameText = UserContextHolder.getLocalUserContext().getUserContext().getUser().getPersonnel().getRealName();
		userId =  UserContextHolder.getLocalUserContext().getUserContext().getUser().getUserId();
	}
	else{
		realNameText = SysCachePoolUtils.getDictDataValue("YUSER", userId);
	}

	String inputName = "" ;
	
	if(!StringUtils.isBlank(boName))
	{
		inputName = boName + "." + boProperty;
	}
	else
	{
		inputName = boProperty;
	}
	
	jspContext.setAttribute("inputName", inputName);	
	jspContext.setAttribute("realName", realNameText);	
%>

<input type="hidden" id="${inputName}" name="${inputName}" value="${userId}">
<input type="text" class="inputText" id="${inputName}_text" name="${inputName}_text" value="${realName}" readOnly="true">