
<%@tag
	import="com.infolion.platform.dpframework.language.LanguageService"%>
<%@tag import="com.infolion.platform.dpframework.language.LangConstants"%><%-- 
  - Author(s):刘俊杰
  - Date: 2009-12-14
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description: 文本标签
  - 三种使用方法：
  - 方法1：通过设置属性boName(业务对象名)和propertyName(属性名)取得文本
  - 方法2：通过LanguageService取得文本的相关属性
  - 方法3：通过msgCode(消息编码)取得文本和msgParams(消息参数)取得文本
--%>
<%@tag
	import="com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants"%>
<%@tag
	import="com.infolion.platform.dpframework.util.EasyApplicationContextUtils"%>
<%@tag
	import="com.infolion.platform.dpframework.uicomponent.systemMessage.service.SystemMessageService"%>
<%@tag import="com.infolion.platform.dpframework.util.StringUtils"%>
<%@tag pageEncoding="utf-8"%>
<%@tag
	import="com.infolion.platform.basicApp.authManage.UserContextHolder"%>
<%@tag import="com.infolion.platform.bo.domain.Property"%>
<%@tag import="com.infolion.platform.bo.service.BusinessObjectService"%>
<%@tag import="com.infolion.platform.bo.domain.BusinessObject"%>

<%@ attribute name="boName" required="false"
	description="业务对象名,如果没有设置此属性，会尝试从request的attribute中取到"%>
<%@ attribute name="propertyName" required="false" description="属性名"%>
<%@ attribute name="msgClass" required="false"
	description="消息类型，默认值为SysMsgConstants.MSG_CLASS_DP"%>

<%@ attribute name="msgCode" required="false" description="消息编码"%>
<%@ attribute name="msgParams" required="false"
	description="消息参数,参数之间用逗号隔开"%>

<%@ attribute name="key" required="false" description="所需文本的键"%>
<%
	String text = "";
	// 优先通过LanguageService取得文本
	if (StringUtils.isNotBlank(key))
	{
		text = LanguageService.getText(key);
	}
	// 通过业务对象属性取得文本
	else if (StringUtils.isNotBlank(propertyName))
	{
		String languageCode = UserContextHolder.getLocalUserContext().getUserContext().getLanguageCode();
		BusinessObject bo;
		// 如果有设置boName，从boName取得业务对象，否则从request中取得业务对象
		if (StringUtils.isNotBlank(boName))
		{
			text = BusinessObjectService.getBoPropertyText(boName, propertyName);
		}
		else
		{
			bo = (BusinessObject) request.getAttribute("businessObject");
			if (bo != null)
			{
				text = BusinessObjectService.getBoPropertyText(bo, propertyName);
			}
		}
	}
	// 消息服务取得文本
	else
	{
		SystemMessageService sysMsgService = (SystemMessageService) EasyApplicationContextUtils.getBeanByName("systemMessageService");
		if (StringUtils.isNullBlank(msgClass))
		{
			msgClass = SysMsgConstants.MSG_CLASS_DP;
		}
		Object[] params;
		if (StringUtils.isNullBlank(msgParams))
		{
			params = new String[0];
		}
		else
		{
			params = msgParams.split(",");
		}
		text = sysMsgService.getMessage(msgClass, msgCode, params);
	}
%>
<%=text%>
