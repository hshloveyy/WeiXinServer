/*
 * @(#)${beanAttribute.beanNameGen}.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
 *  描　述：创建
 */
package ${beanAttribute.packageNameGen}.dao;

import java.util.List;
import ${beanAttribute.packageName}.domain.${beanAttribute.boName};
import com.infolion.platform.dpframework.core.dao.BaseHibernateDao;


/**
 * <pre>
 * ${beanAttribute.boDescription}(${beanAttribute.boName}),HibernateDao 操作类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class ${beanAttribute.beanNameGen}<${beanAttribute.boName}> extends BaseHibernateDao<${beanAttribute.boName}>
{
<#if bo.methods?exists && (bo.methods?size>0)>
<#list bo.methods as method>
<#--  _view -->
	<#if method.methodName=="_view">
    <#-- 如果方法有配置了参数才生成 HibernateDao相对应的方法。 -->
<#if method.conditionMethodParameters?exists && (method.conditionMethodParameters?size>0)>
	/**
	 * 根据方法参数,取得${beanAttribute.boDescription}实例
<#list method.conditionMethodParameters?sort_by("methodParId") as methodParameter>
 	 * @param ${methodParameter.parameterName}
</#list>
	 * @return ${beanAttribute.boDescription}实例
	 */
     public ${beanAttribute.boName} ${method.methodName}(
<#compress>		
<#list method.conditionMethodParameters?sort_by("methodParId") as methodParameter>
 	 String ${methodParameter.parameterName}<#if methodParameter_has_next>,</#if>
</#list>)	
     {
		List<${beanAttribute.boName}> ${beanAttribute.boName?uncap_first}s = this.getHibernateTemplate().find("from ${beanAttribute.boName} where "	
<#list method.conditionMethodParameters?sort_by("methodParId") as methodParameter>
<#if methodParameter.parameterRef?exists && methodParameter.parameterRef!="" && methodParameter.parameterRef!=" ">
 	        + " ${methodParameter.parameterRef}=? <#if methodParameter_has_next> and </#if>"
</#if>
</#list>,new String[] {
<#list method.conditionMethodParameters?sort_by("methodParId") as methodParameter>
<#if methodParameter.parameterRef?exists && methodParameter.parameterRef!="" && methodParameter.parameterRef!=" ">
 	       ${methodParameter.parameterName}<#if methodParameter_has_next>,</#if>
</#if>
</#list>});
</#compress>
		if (null!= ${beanAttribute.boName?uncap_first}s  && ${beanAttribute.boName?uncap_first}s.size() > 0)
			return ${beanAttribute.boName?uncap_first}s.get(0);
		else
			return null;
     }
</#if>
<#--  _edit -->
	<#elseif method.methodName=="_edit"> <#--  _edit -->
<#-- 如果方法有配置了参数才生成 HibernateDao相对应的方法。 -->
<#if method.conditionMethodParameters?exists && (method.conditionMethodParameters?size>0)>
	/**
	 * 根据方法参数,取得${beanAttribute.boDescription}实例
<#list method.conditionMethodParameters?sort_by("methodParId") as methodParameter>
 	 * @param ${methodParameter.parameterName}
</#list>
	 * @return ${beanAttribute.boDescription}实例
	 */
     public ${beanAttribute.boName} ${method.methodName}(
<#compress>		
<#list method.conditionMethodParameters?sort_by("methodParId") as methodParameter>
 	 String ${methodParameter.parameterName}<#if methodParameter_has_next>,</#if>
</#list>)	
     {
		List<${beanAttribute.boName}> ${beanAttribute.boName?uncap_first}s = this.getHibernateTemplate().find(
				"from ${beanAttribute.boName} where "	
<#list method.conditionMethodParameters?sort_by("methodParId") as methodParameter>
<#if methodParameter.parameterRef?exists && methodParameter.parameterRef!="" && methodParameter.parameterRef!=" ">
 	        + " ${methodParameter.parameterRef}=? <#if methodParameter_has_next> and </#if>"
</#if>
</#list>,new String[] {
<#list method.conditionMethodParameters?sort_by("methodParId") as methodParameter>
<#if methodParameter.parameterRef?exists && methodParameter.parameterRef!="" && methodParameter.parameterRef!=" ">
 	       ${methodParameter.parameterName}<#if methodParameter_has_next>,</#if>
</#if>
</#list>});
</#compress>
		if (null!= ${beanAttribute.boName?uncap_first}s  && ${beanAttribute.boName?uncap_first}s.size() > 0)
			return ${beanAttribute.boName?uncap_first}s.get(0);
		else
			return null;
     }
</#if>

	</#if> <#-- <#if modelPro.methodName=="_view"> -->
</#list>
</#if>
}