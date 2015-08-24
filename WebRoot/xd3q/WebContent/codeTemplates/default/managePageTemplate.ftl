<%-- 
  - Author(s):java业务平台代码生成工具
  - Date: ${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
  - Copyright Notice:版权声明 福建讯盟软件有限公司, 版权所有 违者必究
  - @(#)
  - Description:  
  - <功能>主对象${beanAttribute.boDescription}(${beanAttribute.boName})管理页面
--%>
<#import "/dpFtlLib/dpPageFtlLib.ftl" as dpPageFtlLib>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${"$"}${"{"}vt.managePage${"}"}</title>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/${beanAttribute.webPath}/${beanAttribute.boInstanceName}Manage.js"></script>
<script language="javascript" type="text/javascript" defer="defer" src="<%= request.getContextPath() %>/js/${beanAttribute.webPath}/${beanAttribute.boInstanceName}ManageGen.js"></script>
</head>
<body>
<#-- 生成管理页面tag -->
<@dpPageFtlLib.generatorManagePageTag/>
<#-- 生成管理页面Boby区域代码 -->
<@dpPageFtlLib.generatorManagePageBody/>
</body>
</html>
<script type="text/javascript" defer="defer">
<#if bo.methods?exists && (bo.methods?size>0)>
<#list bo.methods as modelPro>
<#if modelPro.methodName=="_manage">
<#if modelPro.methodParameters?exists && (modelPro.methodParameters?size>0)>
//开始取得从_manage网址上带过来的参数，并传递给控制器_create方法。
<#list modelPro.methodParameters as methodParameter>
var ${methodParameter.parameterName}='${"$"}${"{"}${methodParameter.parameterName}${"}"}';
</#list>
</#if>
</#if>
</#list>
</#if>
<#-- 生成管理页面Ext布局 和系统文本定义-->
<@dpPageFtlLib.generatorManagePageExtLayOutAndSysTxt/>
</script>
