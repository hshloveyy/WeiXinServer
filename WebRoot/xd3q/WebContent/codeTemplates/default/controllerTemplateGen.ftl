/*
 * @(#)${beanAttribute.beanNameGen}.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
 *  描　述：创建
 */
package ${beanAttribute.packageNameGen}.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import java.io.IOException;
import java.util.Set;
import java.math.BigDecimal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.JsonUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import ${beanAttribute.packageName}.domain.${beanAttribute.boName};
import ${beanAttribute.packageName}.service.${beanAttribute.boName}Service;
<#-- 导入引用到的业务对象子对象domain包路径  subBo.beanAttribute.domainPackage!="" &&-->
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
<#list bo.subBusinessObject as subBo>          <#-- 遍历业务对象下所有子对象&& subBo.beanAttribute.packageName!="${entityAttribute.packageName}" -->
<#if subBo.parentBusinessObjectName!="">
import ${subBo.beanAttribute.domainPackage};
import ${subBo.beanAttribute.servicePackage};
</#if>
<#if subBo.parentBoAttributesNotWith?exists && (subBo.parentBoAttributesNotWith?size>0)>
<#list subBo.parentBoAttributesNotWith as parentBoAttributesNotWith>
//import ${parentBoAttributesNotWith.parentBusinessObject.beanAttribute.domainPackage};
</#list>
</#if>
</#list>
</#if>
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * ${beanAttribute.boDescription}(${beanAttribute.boName})控制器类
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
public class ${beanAttribute.beanNameGen} extends AbstractGenController
{
	private final String boName = "${beanAttribute.boName}";

	public String getBoName()
	{
		return this.boName;
	}
	
	@Autowired
	protected ${beanAttribute.boName}Service ${beanAttribute.boInstanceName}Service;
	
	public void set${beanAttribute.boName}Service(${beanAttribute.boName}Service ${beanAttribute.boInstanceName}Service)
	{
		this.${beanAttribute.boInstanceName}Service = ${beanAttribute.boInstanceName}Service;
	}
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
<#list bo.subBusinessObject as subBo>          <#-- 遍历业务对象下所有子对象 -->
<#if subBo.boName=="Attachement">
	@Autowired
	protected ${subBo.boName}Service ${subBo.beanAttribute.boInstanceName}Service;
	
	public void set${subBo.boName}Service(${subBo.boName}Service ${subBo.beanAttribute.boInstanceName}Service)
	{
		this.${subBo.beanAttribute.boInstanceName}Service = ${subBo.beanAttribute.boInstanceName}Service;
	}
 <#break>
</#if>
</#list>
</#if>

<#-- LJX 100312 暂时加在这里，如果对象为树类型 -->
<#if bo.boTypeId=="2">
    <#-- 取得树节点信息 -->
	/**
	 * 取得树节点信息
	 * @param request
	 * @param response
	 */
	public void _getTreeNode(HttpServletRequest request, HttpServletResponse response) throws IOException
	{ 
		${beanAttribute.boName} ${beanAttribute.boInstanceName} = new ${beanAttribute.boName}();
		String id = request.getParameter("${bo.primaryKeyProperty.propertyName}");
		${beanAttribute.boInstanceName} = this.${beanAttribute.boInstanceName}Service._getDetached(id);
		JSONObject jo = new JSONObject();
<#if bo.formColumns?exists && (bo.formColumns?size>0)>
<#list bo.formColumns as formColumnList>
<#if formColumnList.property.columnName=="CREATOR" ||  formColumnList.property.columnName=="LASTMODIFYER" ||  formColumnList.property.columnName=="LASTMODIFYOR"> 
		String ${formColumnList.property.propertyName} = ${beanAttribute.boInstanceName}.get${formColumnList.property.propertyName?cap_first}();
		String ${formColumnList.property.propertyName}_text = SysCachePoolUtils.getDictDataValue("YUSER", ${formColumnList.property.propertyName});
		jo.put("${formColumnList.property.propertyName}_text", ${formColumnList.property.propertyName}_text);
		jo.put("${formColumnList.property.propertyName}", ${formColumnList.property.propertyName});
<#else>	
		jo.put("${formColumnList.property.propertyName}",${beanAttribute.boInstanceName}.get${formColumnList.property.propertyName?cap_first}());
</#if>
</#list>
</#if>
<#-- 树类型对象。-->
<#if bo.boTypeId=="2">
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
<#list bo.subBusinessObject as subBo>  
<#if subBo.subBoAttribute.subBoRelType=="1">
<#if subBo.formColumns?exists && (subBo.formColumns?size>0)>
        ${subBo.boName} ${subBo.beanAttribute.boInstanceName}= ${beanAttribute.boInstanceName}.get${subBo.boName}();
<#list subBo.formColumns as formColumns>
<#if formColumns.property.propertyName!=bo.primaryKeyProperty.propertyName>
		jo.put("${subBo.boName}${formColumns.property.propertyName}",null==${subBo.beanAttribute.boInstanceName} ? "" : ${subBo.beanAttribute.boInstanceName}.get${formColumns.property.propertyName?cap_first}());
</#if>
</#list>
</#if>
</#if>
</#list>
</#if>
</#if>
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsontext);
	}
</#if>

<#if bo.methods?exists && (bo.methods?size>0)>
	<#list bo.methods as modelPro>
	<#-- 控制器方法 -->
    <#if modelPro.methodName=="_manage">
    
    <#-- 管理页面 -->
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{ 
<#if modelPro.methodParameters?exists && (modelPro.methodParameters?size>0)>
        StringBuilder sbSqlWhere = new StringBuilder();
<#list modelPro.methodParameters as methodParameter>
        String ${methodParameter.parameterName} = request.getParameter("${methodParameter.parameterName}");
        request.setAttribute("${methodParameter.parameterName}",${methodParameter.parameterName});
<#if bo.properties?exists && (bo.properties?size>0)>
<#list bo.properties as property>
<#if property.propertyName==methodParameter.parameterRef>
		if (${methodParameter.parameterName} != null && StringUtils.isNotBlank(${methodParameter.parameterName}))
		{
			sbSqlWhere.append(" ${property.tableName}.${property.columnName}='" + ${methodParameter.parameterName} + "'"<#if methodParameter_has_next>+ " and "</#if>);
	    } 		
</#if>	
</#list>
</#if>	
</#list>
		request.setAttribute("sqlWhere", sbSqlWhere.toString());
</#if>	
<#if bo.boTypeId=="2">
 		if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
</#if>	
		request.setAttribute("vt", getBusinessObject().getViewText());
		return new ModelAndView("${beanAttribute.webPath}/${beanAttribute.boInstanceName}Manage");
	}
	<#elseif modelPro.methodName=="_view">

	<#-- 查看页面 -->
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public ModelAndView _view(HttpServletRequest request, HttpServletResponse response)
	{
		${beanAttribute.boName} ${beanAttribute.boInstanceName} = new ${beanAttribute.boName}();
<#if bo.parentBusinessObjectName!=""><#-- 子业务对象的编辑页面-->
	    ${beanAttribute.boInstanceName} = (${beanAttribute.boName}) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ${beanAttribute.boName}.class , false , request.getMethod(), false);
<#else>
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String id = request.getParameter("${bo.primaryKeyProperty.propertyName}");
		String businessId = request.getParameter("businessId");	
<#if modelPro.conditionMethodParameters?exists && (modelPro.conditionMethodParameters?size>0)>
<#list modelPro.conditionMethodParameters as methodParameter>
        String ${methodParameter.parameterName} = request.getParameter("${methodParameter.parameterName}");
</#list>
</#if>			
		if (null == id)
			id = businessId ;

		if (StringUtils.isNullBlank(id))
		{
<#compress>						
<#if modelPro.conditionMethodParameters?exists && (modelPro.conditionMethodParameters?size>0)>			
			if(
<#list modelPro.conditionMethodParameters?sort_by("methodParId") as methodParameter>
				!StringUtils.isNullBlank(${methodParameter.parameterName}) <#if methodParameter_has_next> && </#if>
</#list>		
				)
       		 {	
           		${beanAttribute.boInstanceName} = this.${beanAttribute.boInstanceName}Service._view(
<#list modelPro.conditionMethodParameters?sort_by("methodParId") as methodParameter>
           		${methodParameter.parameterName}<#if methodParameter_has_next>,</#if>
</#list>);}
<#else>
		   ${beanAttribute.boInstanceName} = this.${beanAttribute.boInstanceName}Service._get(id);
</#if></#compress>
        }
        else
        {
           ${beanAttribute.boInstanceName} = this.${beanAttribute.boInstanceName}Service._get(id);
        }
    
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("${bo.boId}");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
</#if>	
		request.setAttribute("vt", getBusinessObject().getViewText());
		<#if bo.parentBusinessObjectName==""><#-- 子业务对象的编辑页面-->
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		</#if>
		
		request.setAttribute("${beanAttribute.boInstanceName}", ${beanAttribute.boInstanceName});  
		return new ModelAndView("${beanAttribute.webPath}/${beanAttribute.boInstanceName}View");
	}
	<#elseif modelPro.methodName=="_edit">
	
	<#-- 编辑链接进入方法 -->
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response)
	{
		${beanAttribute.boName} ${beanAttribute.boInstanceName} = new ${beanAttribute.boName}();
<#if bo.parentBusinessObjectName!=""><#-- 子业务对象的编辑页面-->
	    ${beanAttribute.boInstanceName} = (${beanAttribute.boName}) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ${beanAttribute.boName}.class , false, request.getMethod(), false);
<#else>
		String id = request.getParameter("${bo.primaryKeyProperty.propertyName}");
		String workflowTaskId = request.getParameter("workflowTaskId");
		String workflowNodeDefId = request.getParameter("workflowNodeDefId");
		String businessId = request.getParameter("businessId");	
		if (null == id)
			id = businessId ;
			
		if (StringUtils.isNullBlank(id))
		{	
<#compress>		
<#if modelPro.conditionMethodParameters?exists && (modelPro.conditionMethodParameters?size>0)>			
			if(
<#list modelPro.conditionMethodParameters?sort_by("methodParId") as methodParameter>
				!StringUtils.isNullBlank(${methodParameter.parameterName}) <#if methodParameter_has_next> && </#if>
</#list>		
				)
       		 {	
           		${beanAttribute.boInstanceName} = this.${beanAttribute.boInstanceName}Service._edit(
<#list modelPro.conditionMethodParameters?sort_by("methodParId") as methodParameter>
           		${methodParameter.parameterName}<#if methodParameter_has_next>,</#if>
</#list>);}
<#else>
		   ${beanAttribute.boInstanceName} = this.${beanAttribute.boInstanceName}Service._getForEdit(id);
</#if></#compress>	
        }
        else
        {
           ${beanAttribute.boInstanceName} = this.${beanAttribute.boInstanceName}Service._getForEdit(id);
        }
       
		boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("${bo.boId}");
		request.setAttribute("businessId", id);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);	
</#if>
		request.setAttribute("vt", getBusinessObject().getViewText());
		<#if bo.parentBusinessObjectName==""><#-- 子业务对象的编辑页面-->
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
		</#if>
		request.setAttribute("${beanAttribute.boInstanceName}", ${beanAttribute.boInstanceName});
		return new ModelAndView("${beanAttribute.webPath}/${beanAttribute.boInstanceName}Edit");
	}
	<#elseif modelPro.methodName=="_cancel">
	
	/**
	 * 取消后的解锁
	 * 
	 * @param request
	 * @param response
	 */
	public void _cancel(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		${beanAttribute.boName} ${beanAttribute.boInstanceName}  = new ${beanAttribute.boName}();
		String ${bo.primaryKeyProperty.propertyName} = request.getParameter("${bo.primaryKeyProperty.propertyName}");
		${beanAttribute.boInstanceName}.set${bo.primaryKeyProperty.propertyName?cap_first}(${bo.primaryKeyProperty.propertyName});
		LockService.unLockBOData(${beanAttribute.boInstanceName});
		this.operateSuccessfullyHiddenInfo(response);
	}
	<#elseif modelPro.methodName=="_create">
	
	<#-- 创建 -->
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public ModelAndView _create(HttpServletRequest request, HttpServletResponse response)
	{
        <#--日历使用 -->
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
	    ${beanAttribute.boName} ${beanAttribute.boInstanceName} = new ${beanAttribute.boName}();
	    String workflowTaskId = request.getParameter("workflowTaskId");
	    String workflowNodeDefId = request.getParameter("workflowNodeDefId");
<#if bo.formColumnList?exists && (bo.formColumnList?size>0)>
<#list bo.formColumnList as column>
<#if column.defaultValue?exists && column.defaultValue!="" && column.defaultValue!=" ">
    <#if column.property.dataType=="DEC" >
        BigDecimal ${column.propertyName} = new BigDecimal(${column.defaultValue});
        ${beanAttribute.boInstanceName}.set${column.propertyName?cap_first}(${column.propertyName});
    <#else >
    	${beanAttribute.boInstanceName}.set${column.propertyName?cap_first}("${column.defaultValue}");
    </#if>
</#if>
</#list>
</#if>
<#-- 开始处理方法上配置的参数，如果为业务流程，create时候必须要加入参数名称为businessId的参数，并指定映射的属性-->
<#--
eg:
		String businessId = request.getParameter("businessId");
		if (StringUtils.isNotBlank(businessId))
		{
			testProjectService.setProjectid(businessId);
		}
		业务流时候，2个业务对象关联属性传值。
-->
<#if modelPro.methodParameters?exists && (modelPro.methodParameters?size>0)>
<#list modelPro.methodParameters as methodParameter>
<#if methodParameter.parameterRef?exists && methodParameter.parameterRef!="" && methodParameter.parameterRef!=" ">
        String ${methodParameter.parameterName} = request.getParameter("${methodParameter.parameterName}");
        if(StringUtils.isNotBlank(${methodParameter.parameterName}))
        {
        	${beanAttribute.boInstanceName}.set${methodParameter.parameterRef?cap_first}(${methodParameter.parameterName});
        }
<#elseif !methodParameter.parameterRef?exists ||( methodParameter.parameterRef?exists && methodParameter.parameterRef=="" || methodParameter.parameterRef==" ")   >
	    String ${methodParameter.parameterName} = request.getParameter("${methodParameter.parameterName}");
		request.setAttribute("${methodParameter.parameterName}",${methodParameter.parameterName});
</#if>		
</#list>
</#if>
<#list methodList as managerMethod>
<#if managerMethod.methodName=="_manage">
<#if managerMethod.methodParameters?exists && (managerMethod.methodParameters?size>0)>
        //开始接收从_manage方法传递过来的参数。
<#list managerMethod.methodParameters as managerMethodParameter>
<#if managerMethodParameter.parameterRef?exists && managerMethodParameter.parameterRef!="" && managerMethodParameter.parameterRef!=" ">
        String ${managerMethodParameter.parameterName}Manager = request.getParameter("${managerMethodParameter.parameterName}");
        ${beanAttribute.boInstanceName}.set${managerMethodParameter.parameterRef?cap_first}(${managerMethodParameter.parameterName}Manager);
</#if>		
</#list>
</#if>
</#if>
</#list>
		request.setAttribute("vt", getBusinessObject().getViewText());
<#if bo.parentBusinessObjectName==""><#-- 子业务对象的编辑页面-->
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
</#if>
		request.setAttribute("${beanAttribute.boInstanceName}",${beanAttribute.boInstanceName});
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("${bo.boId}");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", workflowTaskId);	
		request.setAttribute("workflowNodeDefId", workflowNodeDefId);
	    return new ModelAndView("${beanAttribute.webPath}/${beanAttribute.boInstanceName}Add");
	}
	
	<#elseif modelPro.methodName=="_copyCreate">
	
	<#-- 复制创建 -->
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response)
	{
	    <#--日历使用 -->
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
			request.setAttribute("calActivityId", calActivityId);
<#if bo.parentBusinessObjectName!="">
		${beanAttribute.boName} ${beanAttribute.boInstanceName} = new ${beanAttribute.boName} ();
	    ${beanAttribute.boInstanceName} = (${beanAttribute.boName}) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ${beanAttribute.boName}.class , false , request.getMethod(), false);
<#else>
		String id = request.getParameter("${bo.primaryKeyProperty.propertyName}");
		${beanAttribute.boName} ${beanAttribute.boInstanceName} = this.${beanAttribute.boInstanceName}Service._getEntityCopy(id);
		request.setAttribute("isCreateCopy", "true");
</#if>	
		request.setAttribute("${beanAttribute.boInstanceName}", ${beanAttribute.boInstanceName});
		request.setAttribute("vt", getBusinessObject().getViewText());
<#if bo.parentBusinessObjectName==""><#-- 子业务对象的编辑页面-->
        if(null!=getBusinessObject().getSubBusinessObject())
        {
			// 取得子业务对象
			for (BusinessObject bo : getBusinessObject().getSubBusinessObject())
			{
				request.setAttribute(bo.getBeanAttribute().getBeanInstanceName()+"Vt", bo.getViewText());
			}
		}
</#if>
      	boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("${bo.boId}");
		request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
		request.setAttribute("workflowTaskId", "");	
		request.setAttribute("workflowNodeDefId", "");
		return new ModelAndView("${beanAttribute.webPath}/${beanAttribute.boInstanceName}Add");
	}

	<#elseif modelPro.methodName=="_update">
	
	<#-- 主对象保存，修改 -->
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public void _update(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		${beanAttribute.boName} ${beanAttribute.boInstanceName} = (${beanAttribute.boName}) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ${beanAttribute.boName}.class , true , request.getMethod(), true);
<#list bo.properties as property>          <#-- 遍历业务对象下所有子对象 -->
  <#list subBoList?if_exists as subBo>     <#-- 遍历业务对象下所有子对象 -->
  <#if property.propertyType=="业务对象" && property.subBoName==subBo.boName && property.subBoName!="Attachement">
	 <#if property.subBoRelType=="1">
	    //绑定子对象(一对一关系)
	    ${property.subBoName} ${property.propertyName} = (${property.subBoName}) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ${property.subBoName}.class , false , request.getMethod(), true);
	    ${beanAttribute.boInstanceName}.set${property.subBoName}(${property.propertyName});
	    ${property.propertyName}.set${beanAttribute.boName}(${beanAttribute.boInstanceName});
	 <#elseif (property.subBoRelType=="2" || property.subBoRelType=="3") && subBo.parentBusinessObjectName!=""> 
	    // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
	    Set<${property.subBoName}> ${property.propertyName}deleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { ${beanAttribute.boInstanceName} }, ${property.subBoName}.class, null);
		Set<${property.subBoName}> ${property.propertyName}modifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { ${beanAttribute.boInstanceName} }, ${property.subBoName}.class, null);
		${beanAttribute.boInstanceName}.set${property.subBoName}(${property.propertyName}modifyItems);
	 </#if>
  </#if>
 </#list> 
</#list>
		this.${beanAttribute.boInstanceName}Service._update(${beanAttribute.boInstanceName}
<#list bo.properties as property><#-- 遍历业务对象下所有子对象 -->
  <#list bo.subBusinessObject?if_exists as subBo>     <#-- 遍历业务对象下所有子对象 -->
   <#if property.propertyType=="业务对象" && property.subBoName==subBo.boName && property.subBoName!="Attachement">
      <#if (property.subBoRelType=="2" || property.subBoRelType=="3") && subBo.parentBusinessObjectName!="">
      ,${property.subBoName?uncap_first}deleteItems
      </#if>
    </#if>
  </#list>
</#list>,getBusinessObject());
		this.operateSuccessfully(response);
	}
	<#elseif modelPro.methodName=="_saveOrUpdate">
	
	<#-- 主对象保存 -->
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public void _saveOrUpdate(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		// 绑定主对象值
		${beanAttribute.boName} ${beanAttribute.boInstanceName} = (${beanAttribute.boName}) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ${beanAttribute.boName}.class , true , request.getMethod(), true);
<#compress>  
<#list bo.properties as property>          <#-- 遍历业务对象下所有子对象&& property.propertyName!="attachement" -->
  <#if property.propertyType=="业务对象" >
	 <#if property.subBoRelType=="1" && property.subBoName!="Attachement">
	    //绑定子对象(一对一关系)
	    ${property.subBoName} ${property.propertyName} = (${property.subBoName}) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ${property.subBoName}.class, false , request.getMethod(), true);
	    if(${property.propertyName}!=null)
	    {
		    ${beanAttribute.boInstanceName}.set${property.subBoName}(${property.propertyName});
		    ${property.propertyName}.set${beanAttribute.boName}(${beanAttribute.boInstanceName});
	    }
	 <#elseif (property.subBoRelType=="2" || property.subBoRelType=="3") && property.subBoName!="Attachement"> 
	    // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<${property.subBoName}> ${property.propertyName}modifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { ${beanAttribute.boInstanceName} }, ${property.subBoName}.class, null);
		${beanAttribute.boInstanceName}.set${property.propertyName?cap_first}(${property.propertyName}modifyItems);
		Set<${property.subBoName}> ${property.propertyName}deleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { ${beanAttribute.boInstanceName} }, ${property.subBoName}.class, null);
	 <#elseif (property.subBoRelType=="333333333") && property.subBoName!="Attachement"> 
	    // 绑定需要修改（包括新增和修改）的子对象集合(1:N:1关系)
	    <#list bo.subBusinessObject as subBo>
		    <#if subBo.boName==property.subBoName>
		Set<${property.subBoName}> ${property.propertyName}modifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { ${beanAttribute.boInstanceName} }, ${property.subBoName}.class, 
			<#if subBo.parentBoAttributesNotWith?exists && (subBo.parentBoAttributesNotWith?size>0)>
			new Class[]{
			<#list subBo.parentBoAttributesNotWith as parentBoAttributesNotWith>
			     ${parentBoAttributesNotWith.parentBusinessObjectName}.class<#if parentBoAttributesNotWith_has_next>,</#if>
			</#list>
			}
			<#else>
			null
			</#if>
			);
		${beanAttribute.boInstanceName}.set${property.propertyName?cap_first}(${property.propertyName}modifyItems);
		Set<${property.subBoName}> ${property.propertyName}deleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { ${beanAttribute.boInstanceName} }, ${property.subBoName}.class,
			<#if subBo.parentBoAttributesNotWith?exists && (subBo.parentBoAttributesNotWith?size>0)>
			new Class[]{
			<#list subBo.parentBoAttributesNotWith as parentBoAttributesNotWith>
			     ${parentBoAttributesNotWith.parentBusinessObjectName}.class<#if parentBoAttributesNotWith_has_next>,</#if>
			</#list>
			}
			<#else>
			null
			</#if>
		 );
		   </#if>
	    </#list>
	 <#elseif property.subBoRelType=="2" && property.subBoName=="Attachement"> 
	 <#--多附件支持，多个附件时候，后台绑定为同一个 -->
	 </#if>
  </#if>
</#list>
<#list bo.properties as property>
	 <#if property.subBoRelType=="2" && property.subBoName=="Attachement">
	    //取得业务附件，业务ID
	    Set<${property.subBoName}> ${property.propertyName}s = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, ${property.subBoName}.class, null);
	 <#break>
	 </#if>
</#list>

		this.${beanAttribute.boInstanceName}Service._saveOrUpdate(${beanAttribute.boInstanceName}
<#list bo.properties?if_exists as property><#-- 遍历业务对象下所有子对象 -->
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
  <#list bo.subBusinessObject?sort_by("boName") as subBo>     <#-- 遍历业务对象下所有子对象 -->
	   <#if property.propertyType=="业务对象" && property.subBoName==subBo.boName>
	      <#if (property.subBoRelType=="2"  || property.subBoRelType=="3") && subBo.parentBusinessObjectName!="" && property.subBoName!="Attachement">
	      ,${property.propertyName}deleteItems
	      <#elseif property.subBoRelType=="2" && subBo.parentBusinessObjectName!="" && property.subBoName=="Attachement">
	      <#--,${property.propertyName}s -->
	      </#if>
	    </#if>
  </#list>
</#if>
</#list>
  <#--多附件支持，多个附件时候，后台绑定为同一个 -->
  <#list bo.properties as property>
	 <#if property.subBoRelType=="2" && property.subBoName=="Attachement">
		 ,${property.propertyName}s 
	 <#break>
	 </#if>
  </#list>
</#compress>,getBusinessObject());

<#-- 日历使用。 -->
		// 如果返回参数带有calActivityId，将业务对象保存到相应的CalActivity中
		String calActivityId = request.getParameter("calActivityId");
		if (StringUtils.isNotBlank(calActivityId))
		{
			CalActivityService calActivityService = (CalActivityService) EasyApplicationContextUtils.getBeanByName("calActivityService");
			CalActivity calActivity = calActivityService._get(calActivityId);
			if (calActivity != null)
			{
				calActivity.setBoid(this.getBusinessObject().getBoId());
				calActivity.setBoname(this.getBoName());
				calActivity.setBusid(${beanAttribute.boInstanceName}.get${bo.primaryKeyProperty.propertyName?cap_first}());
				calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
			}
			this.operateClose(response);
		}
	    else
		{	
<#-- 以下为回填值到前台页面 -->
<#-- 号码对象 -->
<#compress>  
<#list bo.properties?if_exists as property><#-- 遍历业务对象下所有属性 -->
<#if property.numberObjectId?exists && property.numberObjectId!="" && property.numberObjectId!=" ">
		jo.put("${property.propertyName}", ${beanAttribute.boInstanceName}.get${property.propertyName?cap_first}());
</#if>
</#list>
</#compress>
		jo.put("${bo.primaryKeyProperty.propertyName}", ${beanAttribute.boInstanceName}.get${bo.primaryKeyProperty.propertyName?cap_first}());
<#-- 当关联关系为1比1,则回填1对1子对象端主键ID值到表单 -->
<#compress>  
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)> 
<#list bo.subBusinessObject as subBo>
	 <#if subBo.subBoAttribute.subBoRelType=="1">
	   jo.put("${subBo.primaryKeyProperty.propertyName}", ${beanAttribute.boInstanceName}.get${subBo.boName}()!=null ? ${beanAttribute.boInstanceName}.get${subBo.boName}().get${subBo.primaryKeyProperty.propertyName?cap_first}() : "");
	 </#if>
</#list> 
</#if>
</#compress>
<#-- 回填，业务对象创建人、创建时间、修改人、修改时间 -->
<#compress>  
<#list bo.properties as property> 
<#if property.columnName=="CREATOR" ||  property.columnName=="LASTMODIFYER" ||  property.columnName=="LASTMODIFYOR"> 
       String ${property.propertyName} = ${beanAttribute.boInstanceName}.get${property.propertyName?cap_first}();
       String ${property.propertyName}_text = SysCachePoolUtils.getDictDataValue("YUSER", ${property.propertyName});
	   jo.put("${property.propertyName}_text", ${property.propertyName}_text);
	   jo.put("${property.propertyName}", ${property.propertyName});
<#elseif property.columnName=="CREATETIME" || property.columnName=="LASTMODIFYTIME">
	   jo.put("${property.propertyName}", ${beanAttribute.boInstanceName}.get${property.propertyName?cap_first}());
</#if>
</#list>
<#-- 如果为树对象类型则回填 -->
<#-- LJX 100315 暂时加在这里，如果对象为树类型 -->
<#if bo.boTypeId=="2">
	   jo.put("${bo.boTreeConfig.parentProtertyName}", ${beanAttribute.boInstanceName}.get${bo.boTreeConfig.parentProtertyName?cap_first}());
	   jo.put("${bo.boTreeConfig.titleProtertyName}", ${beanAttribute.boInstanceName}.get${bo.boTreeConfig.titleProtertyName?cap_first}());
</#if>
</#compress>
		this.operateSuccessfullyWithString(response,jo.toString());
	   }
	}
	<#elseif modelPro.methodName=="_delete">
	
	<#-- 单个删除 -->
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public void _delete(HttpServletRequest request, HttpServletResponse response)
	{
		String ${bo.primaryKeyProperty.propertyName} = request.getParameter("${bo.primaryKeyProperty.propertyName}");
		${beanAttribute.boInstanceName}Service._delete(${bo.primaryKeyProperty.propertyName},getBusinessObject());
		this.operateSuccessfully(response);
	}
	<#elseif modelPro.methodName=="_deletes">
	
	<#-- 批量删除 -->
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public void _deletes(HttpServletRequest request, HttpServletResponse response)
	{
		String ${beanAttribute.boInstanceName}Ids = request.getParameter("${beanAttribute.boInstanceName}Ids");
		${beanAttribute.boInstanceName}Service._deletes(${beanAttribute.boInstanceName}Ids,getBusinessObject());
		this.operateSuccessfully(response);
	}
	<#elseif modelPro.methodName=="_query">
	
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public void _query(HttpServletRequest request, HttpServletResponse response)
	{

	}
	<#elseif modelPro.methodName=="_submitProcess">
	
	<#-- 提交工作流 -->
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	 
	public void _submitProcess(HttpServletRequest request, HttpServletResponse response)
	{
		// 绑定主对象值
		${beanAttribute.boName} ${beanAttribute.boInstanceName} = (${beanAttribute.boName}) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ${beanAttribute.boName}.class , true , request.getMethod(), true);
        //类型标识是从那个页面提交，view 表示从view页面提交流程。
        String type = request.getParameter("type");
<#compress>
<#list bo.properties as property>          <#-- 遍历业务对象下所有子对象 -->
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
  <#list bo.subBusinessObject?sort_by("boName") as subBo>     <#-- 遍历业务对象下所有子对象 -->
  <#if property.propertyType=="业务对象" && property.subBoName!="Attachement" && property.subBoName==subBo.boName && subBo.parentBusinessObjectName!="">
	 <#if property.subBoRelType=="1">
	    //绑定子对象(一对一关系)
	    ${property.subBoName} ${property.propertyName} = (${property.subBoName}) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ${property.subBoName}.class, false , request.getMethod(), true);
	    if(${property.propertyName}!=null)
	    {
		    ${beanAttribute.boInstanceName}.set${property.subBoName}(${property.propertyName});
		    ${property.propertyName}.set${beanAttribute.boName}(${beanAttribute.boInstanceName});
	    }
	 <#elseif property.subBoRelType=="2" || property.subBoRelType=="3"> 
	    // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
		Set<${property.subBoName}> ${property.propertyName}modifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), new Object[] { ${beanAttribute.boInstanceName} }, ${property.subBoName}.class, null);
		Set<${property.subBoName}> deleted${property.subBoName}Set = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(), new Object[] { ${beanAttribute.boInstanceName} }, ${property.subBoName}.class, null);
		${beanAttribute.boInstanceName}.set${property.propertyName?cap_first}(${property.propertyName}modifyItems);
	 </#if>
  </#if>
  </#list>
</#if> 
</#list>

<#list bo.properties as property>
	 <#if property.subBoRelType=="2" && property.subBoName=="Attachement">
	    //取得业务附件，业务ID
	    Set<${property.subBoName}> ${property.propertyName}s = ExBeanUtils.bindModifySubBoData(request.getParameterMap(), null, ${property.subBoName}.class, null);
	 <#break>
	 </#if>
</#list>

    if (!"view".equalsIgnoreCase(type))
	{
		this.${beanAttribute.boInstanceName}Service._saveOrUpdate(${beanAttribute.boInstanceName}
<#list bo.properties?if_exists as property><#-- 遍历业务对象下所有子对象 -->
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
  <#list bo.subBusinessObject?sort_by("boName") as subBo>     <#-- 遍历业务对象下所有子对象 -->
	   <#if property.propertyType=="业务对象" && property.subBoName==subBo.boName>
	      <#if (property.subBoRelType=="2"  || property.subBoRelType=="3") && subBo.parentBusinessObjectName!="" && property.subBoName!="Attachement">
	      ,deleted${property.subBoName}Set
	      <#elseif property.subBoRelType=="2" && subBo.parentBusinessObjectName!="" && property.subBoName=="Attachement">
	      <#--,${property.propertyName}s -->
	      </#if>
	    </#if>
  </#list>
</#if>
</#list>
  <#--多附件支持，多个附件时候，后台绑定为同一个 -->
  <#list bo.properties as property>
	 <#if property.subBoRelType=="2" && property.subBoName=="Attachement">
		 ,${property.propertyName}s 
	 <#break>
	 </#if>
  </#list>,getBusinessObject());
      }
      
		this.${beanAttribute.boInstanceName}Service._submitProcess(${beanAttribute.boInstanceName}
<#list bo.properties as property>          <#-- 遍历业务对象下所有子对象 -->
<#if bo.subBusinessObject?exists && (bo.subBusinessObject?size>0)>
  <#list bo.subBusinessObject?sort_by("boName") as subBo>     <#-- 遍历业务对象下所有子对象 -->
   <#if property.propertyType=="业务对象" && property.subBoName!="Attachement" && property.subBoName==subBo.boName && subBo.parentBusinessObjectName!="">
      <#if property.subBoRelType=="2" || property.subBoRelType=="3"> 
       ,deleted${property.subBoName}Set
    	</#if>
    </#if>
   </#list>
</#if>
</#list>
</#compress>
		,getBusinessObject());
		this.operateSuccessfully(response);
	}
	<#elseif modelPro.methodName=="_entryProcess">
	<#-- 已经集成到BaseMultiActionController -->
	<#elseif modelPro.methodName=="_deleteNode" && bo.boType=="2">
	 /**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public void ${modelPro.methodName} (HttpServletRequest request, HttpServletResponse response)
	{
       _delete(request,response);
	}
	<#elseif modelPro.isDefault!="Y">
    
	<#-- 其他方法action -->
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param request
	 * @param response
	 */
	public void ${modelPro.methodName} (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
    </#if>
    </#list>
</#if>
}