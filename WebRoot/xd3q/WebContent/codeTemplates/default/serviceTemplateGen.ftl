/*
 * @(#)${beanAttribute.beanNameGen}.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
 *  描　述：创建
 */
package ${beanAttribute.packageNameGen}.service;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.bo.domain.BusinessObject;
import ${beanAttribute.domainPackage};
import ${beanAttribute.servicePackage};
import ${beanAttribute.hibernateDaoPackage};
<#-- 导入引用到的业务对象子对象domain包路径  subBo.beanAttribute.domainPackage!="" && subBo.boName!="Attachement"&&-->
<#if subBoList?exists>
<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象&& subBo.beanAttribute.packageName!="${entityAttribute.packageName}" -->
<#if subBo.parentBusinessObjectName!="" >
import ${subBo.beanAttribute.domainPackage};
import ${subBo.beanAttribute.servicePackage};
<#if subBo.boName=="Attachement" >
import ${subBo.beanAttribute.jdbcDaoPackage};
</#if>
</#if>
</#list>
</#if>

/**
 * <pre>
 * ${beanAttribute.boDescription}(${beanAttribute.boName})服务类
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
@Service
public class ${beanAttribute.beanNameGen} extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected ${beanAttribute.boName}HibernateDao ${beanAttribute.boInstanceName}HibernateDao;
	
	public void set${beanAttribute.boName}HibernateDao(${beanAttribute.boName}HibernateDao ${beanAttribute.boInstanceName}HibernateDao)
	{
		this.${beanAttribute.boInstanceName}HibernateDao = ${beanAttribute.boInstanceName}HibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("${beanAttribute.boInstanceName}AdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
<#if subBoList?exists>
<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象&& subBo.beanAttribute.packageName!="${entityAttribute.packageName}" -->
<#if subBo.parentBusinessObjectName!="" && subBo.boName!="Attachement" >

	@Autowired
	protected ${subBo.boName}Service ${subBo.beanAttribute.boInstanceName}Service;
	
	public void set${subBo.boName}Service(${subBo.boName}Service ${subBo.beanAttribute.boInstanceName}Service)
	{
		this.${subBo.beanAttribute.boInstanceName}Service = ${subBo.beanAttribute.boInstanceName}Service;
	}
</#if>
</#list>

<#list subBoList as subBo>          <#-- 遍历业务对象下所有子对象&& subBo.beanAttribute.packageName!="${entityAttribute.packageName}" -->
<#if subBo.parentBusinessObjectName!="" && subBo.boName=="Attachement" >

	@Autowired
	protected ${subBo.boName}Service attachementService;
	
	public void set${subBo.boName}Service(${subBo.boName}Service attachementService)
	{
		this.attachementService = attachementService;
	}

	@Autowired
	protected ${subBo.boName}JdbcDao attachementJdbcDao;
	
	public void set${subBo.boName}JdbcDao(${subBo.boName}JdbcDao attachementJdbcDao)
	{
		this.attachementJdbcDao = attachementJdbcDao;
	}
	<#break>
</#if>
</#list>

</#if>
	   
	/**
	 * 根据主键ID,取得${beanAttribute.boDescription}实例
	 * @param id
	 * @return
	 */
	public ${beanAttribute.boName} _getDetached(String id)
	{		
	    ${beanAttribute.boName} ${beanAttribute.boInstanceName} = new ${beanAttribute.boName}();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  ${beanAttribute.boInstanceName} = ${beanAttribute.boInstanceName}HibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(${beanAttribute.boInstanceName});
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    ${beanAttribute.boInstanceName}.setOperationType(operationType);
	    
		return ${beanAttribute.boInstanceName};	
	}
	
	/**
	 * 根据主键ID,取得${beanAttribute.boDescription}实例
	 * @param id
	 * @return
	 */
	public ${beanAttribute.boName} _get(String id)
	{		
	    ${beanAttribute.boName} ${beanAttribute.boInstanceName} = new ${beanAttribute.boName}();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  ${beanAttribute.boInstanceName} = ${beanAttribute.boInstanceName}HibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(${beanAttribute.boInstanceName});
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    ${beanAttribute.boInstanceName}.setOperationType(operationType);
	    
		return ${beanAttribute.boInstanceName};	
	}
	
	/**
	 * 根据主键ID,取得${beanAttribute.boDescription}实例
	 * @param id
	 * @return
	 */
	public ${beanAttribute.boName} _getForEdit(String id)
	{		
	    ${beanAttribute.boName} ${beanAttribute.boInstanceName} = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = ${beanAttribute.boInstanceName}.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return ${beanAttribute.boInstanceName};	
	}
	
	/**
	 * 根据主键ID,取得${beanAttribute.boDescription}实例副本
	 * @param id
	 * @return
	 */
	public ${beanAttribute.boName} _getEntityCopy(String id)
	{		
	    ${beanAttribute.boName} ${beanAttribute.boInstanceName} = new ${beanAttribute.boName}();
		${beanAttribute.boName} ${beanAttribute.boInstanceName}Old = this._get(id);
		try
		{
			BeanUtils.copyProperties(${beanAttribute.boInstanceName}, ${beanAttribute.boInstanceName}Old);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
<#-- 号码对象处理，copyCreate状态下清楚号码 -->  
<#list bo.properties?if_exists as property><#-- 遍历业务对象下所有属性 -->
<#if property.numberObjectId?exists==false  || (property.numberObjectId?exists && property.numberObjectId!="" && property.numberObjectId!=" ")>
		${beanAttribute.boInstanceName}.set${property.propertyName?cap_first}(null); 
</#if>
</#list>
		//${beanAttribute.boInstanceName}.set${bo.primaryKeyProperty.propertyName?cap_first}(null); 
<#if isHaveProcess>
		${beanAttribute.boInstanceName}.set${bo.processStateFieldName?cap_first}(" ");
</#if>
		return ${beanAttribute.boInstanceName};	
	}
	<#list methodList as modelPro>
    <#if modelPro.methodName=="_view">
<#-- 如果方法有配置了参数才生成 service服务层方法。 -->
<#if modelPro.conditionMethodParameters?exists && (modelPro.conditionMethodParameters?size>0)>
	/**
	 * 根据方法参数,取得${beanAttribute.boDescription}实例
	 * @param id
	 * @return ${beanAttribute.boDescription}实例
	 */
     public ${beanAttribute.boName} ${modelPro.methodName}(
<#compress>		
<#list modelPro.conditionMethodParameters?sort_by("methodParId") as methodParameter>
 	 String ${methodParameter.parameterName}<#if methodParameter_has_next>,</#if>
</#list>)
</#compress>	
     {
       ${beanAttribute.boName} ${beanAttribute.boInstanceName} = ${beanAttribute.boInstanceName}HibernateDao. ${modelPro.methodName}(
<#compress>		
<#list modelPro.conditionMethodParameters?sort_by("methodParId") as methodParameter>
 	   ${methodParameter.parameterName}<#if methodParameter_has_next>,</#if>
</#list>);
</#compress>
	   if(${beanAttribute.boInstanceName}!=null)
	   {	
		   //以下代码需要放在service服务同一方法，要不存在事务问题。
		   String operationType = LockService.lockBOData(${beanAttribute.boInstanceName});
		   if(OperationType.UNVISIABLE.equals(operationType))
		   {
			  throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		   }
		   ${beanAttribute.boInstanceName}.setOperationType(operationType);
	   }
	   
       return  ${beanAttribute.boInstanceName} ;
     }
</#if>
	<#elseif modelPro.methodName=="_edit">
<#-- 如果方法有配置了参数才生成 service服务层方法。 -->
<#if modelPro.conditionMethodParameters?exists && (modelPro.conditionMethodParameters?size>0)>
	/**
	 * 根据方法参数,取得${beanAttribute.boDescription}实例
	 * @param id
	 * @return ${beanAttribute.boDescription}实例
	 */
     public ${beanAttribute.boName} ${modelPro.methodName}(
<#compress>		
<#list modelPro.conditionMethodParameters?sort_by("methodParId") as methodParameter>
 	 String ${methodParameter.parameterName}<#if methodParameter_has_next>,</#if>
</#list>)
</#compress>	
     {
       ${beanAttribute.boName} ${beanAttribute.boInstanceName} = ${beanAttribute.boInstanceName}HibernateDao.${modelPro.methodName}(
<#compress>		
<#list modelPro.conditionMethodParameters?sort_by("methodParId") as methodParameter>
 	   ${methodParameter.parameterName}<#if methodParameter_has_next>,</#if>
</#list>);
</#compress>	
	   if(${beanAttribute.boInstanceName}!=null)
	   {
		   //以下代码需要放在service服务同一方法，要不存在事务问题。
		   String operationType = LockService.lockBOData(${beanAttribute.boInstanceName});
		   if(OperationType.UNVISIABLE.equals(operationType))
		   {
			  throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		   }
		   if(OperationType.READONLY.equals(operationType))
	   	   {
			  throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	   	   }
		   
		   ${beanAttribute.boInstanceName}.setOperationType(operationType);
	   }
	   
       return  ${beanAttribute.boInstanceName} ;
     }
</#if>
	<#elseif modelPro.methodName=="_saveOrUpdate">

	/**
	 * 保存或更新${beanAttribute.boDescription}
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo}
	 * @param ${beanAttribute.boInstanceName}
	 */
	public void _update(${beanAttribute.boName} ${beanAttribute.boInstanceName}
<#compress>	
<#list bo.properties as property><#-- 遍历业务对象下所有子对象        ,${property.propertyName?cap_first} deleted${property.propertyName?cap_first}-->
<#if subBoList?exists && (subBoList?size>0)>
  <#list subBoList?sort_by("boName") as subBo>     <#-- 遍历业务对象下所有子对象 -->
   <#if property.propertyType=="业务对象" && property.subBoName!="Attachement" && property.subBoName==subBo.boName>
      <#if (property.subBoRelType=="2" || property.subBoRelType=="3") && subBo.parentBusinessObjectName!="">
      ,Set<${property.subBoName}> deleted${property.subBoName}Set
    	</#if>
    </#if>
   </#list>
</#if>
</#list>
</#compress>,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(${beanAttribute.boInstanceName});
		${beanAttribute.boInstanceName}HibernateDao.saveOrUpdate(${beanAttribute.boInstanceName});
<#compress>	
<#list bo.properties as property>          <#-- 遍历业务对象下所有子对象 -->
  <#list subBoList?if_exists as subBo>     <#-- 遍历业务对象下所有子对象 -->
   <#if property.propertyType=="业务对象" && property.subBoName!="Attachement" && property.subBoName==subBo.boName>
      <#if (property.subBoRelType=="2" || property.subBoRelType=="3") && subBo.parentBusinessObjectName!="">
        // 删除关联子业务对象数据
        if(deleted${property.subBoName}Set!=null && deleted${property.subBoName}Set.size()>0)
        {
        	${property.subBoName?uncap_first}Service._deletes(deleted${property.subBoName}Set,businessObject);
        }
    	</#if>
    </#if>
   </#list>
</#list>
</#compress>	
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(${beanAttribute.boInstanceName});
	}
	
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param ${beanAttribute.boInstanceName}
	 */
	public void _save(${beanAttribute.boName} ${beanAttribute.boInstanceName})
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(${beanAttribute.boInstanceName});
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		${beanAttribute.boInstanceName}.set${bo.primaryKeyProperty.propertyName?cap_first}(null);
<#list bo.properties as property>     
  <#list subBoList?if_exists as subBo>     <#-- 遍历业务对象下所有子对象 -->
    <#if property.propertyType=="业务对象" && property.subBoName!="Attachement" && property.subBoName==subBo.boName && subBo.parentBusinessObjectName!="">
       <#if property.subBoRelType=="1">
         ${property.subBoName} ${property.propertyName} = ${beanAttribute.boInstanceName}.get${property.propertyName?cap_first}();
         if (null != ${property.propertyName})
		 {
		    ${property.propertyName}.set${subBo.primaryKeyProperty.propertyName?cap_first}(null);
		 }
		 ${beanAttribute.boInstanceName}.set${property.propertyName?cap_first}(${property.propertyName});
        <#elseif property.subBoRelType=="2" || property.subBoRelType=="3"> 
		Set<${property.subBoName}> ${property.propertyName}Set = ${beanAttribute.boInstanceName}.get${property.propertyName?cap_first}();
		Set<${property.subBoName}> new${property.subBoName}Set = null;
		if (null != ${property.propertyName}Set)
		{
			new${property.subBoName}Set = new HashSet();
			Iterator<${property.subBoName}> it${property.subBoName} = ${property.propertyName}Set.iterator();
			while (it${property.subBoName}.hasNext())
			{
				${property.subBoName} ${property.propertyName} = (${property.subBoName}) it${property.subBoName}.next();
				${property.propertyName}.set${subBo.primaryKeyProperty.propertyName?cap_first}(null);
				new${property.subBoName}Set.add(${property.propertyName});
			}
		}
		${beanAttribute.boInstanceName}.set${property.propertyName?cap_first}(new${property.subBoName}Set);
    	</#if>
    </#if>
  </#list> 
</#list>
		${beanAttribute.boInstanceName}HibernateDao.saveOrUpdate(${beanAttribute.boInstanceName});
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(${beanAttribute.boInstanceName});
	}
	
	/**
	 *
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param ${beanAttribute.boInstanceName}
	 */
	public void _saveOrUpdate(${beanAttribute.boName} ${beanAttribute.boInstanceName}
<#compress>		
<#list bo.properties as property><#-- 遍历业务对象下所有子对象        ,${property.propertyName?cap_first} deleted${property.propertyName?cap_first}-->
 <#if subBoList?exists && (subBoList?size>0)>
  <#list subBoList?sort_by("boName") as subBo>     <#-- 遍历业务对象下所有子对象 -->
   <#if property.propertyType=="业务对象" && property.subBoName==subBo.boName>
      <#if (property.subBoRelType=="2"  || property.subBoRelType=="3") && subBo.parentBusinessObjectName!="" && property.subBoName!="Attachement">
      ,Set<${property.subBoName}> deleted${property.subBoName}Set
      <#elseif property.subBoRelType=="2" && subBo.parentBusinessObjectName!="" && property.subBoName=="Attachement">
      <#--,Set<${property.subBoName}> ${property.propertyName}s-->
      </#if>
    </#if>
   </#list>
 </#if>
</#list>
  <#--多附件支持，多个附件时候，后台绑定为同一个 -->
  <#list bo.properties as property>
	 <#if property.subBoRelType=="2" && property.subBoName=="Attachement">
	    //取得业务附件，业务ID
		 ,Set<${property.subBoName}> ${property.propertyName}s
	 <#break>
	 </#if>
  </#list>
</#compress>,BusinessObject businessObject		
	)
	{
       <#-- 号码对象 -->
<#compress>  
<#list bo.properties?if_exists as property><#-- 遍历业务对象下所有属性 -->
<#if property.numberObjectId?exists && property.numberObjectId!="" && property.numberObjectId!=" ">
        if (StringUtils.isNullBlank(${beanAttribute.boInstanceName}.get${property.propertyName?cap_first}()))
        {
       		 String ${property.propertyName} = NumberService.getNextObjectNumber("${property.numberObjectName}", ${beanAttribute.boInstanceName});
			 ${beanAttribute.boInstanceName}.set${property.propertyName?cap_first}(${property.propertyName}); 
		}  
</#if>
</#list>
</#compress>
		if (StringUtils.isNullBlank(${beanAttribute.boInstanceName}.get${bo.primaryKeyProperty.propertyName?cap_first}()))
		{	
			_save(${beanAttribute.boInstanceName});
		}
		else
		{
			_update(${beanAttribute.boInstanceName}
<#compress>				
<#list bo.properties as property><#-- 遍历业务对象下所有子对象        ,${property.propertyName?cap_first} deleted${property.propertyName?cap_first}-->
 <#if subBoList?exists && (subBoList?size>0)>
  <#list subBoList?sort_by("boName") as subBo>     <#-- 遍历业务对象下所有子对象 -->
   <#if property.propertyType=="业务对象" && property.subBoName!="Attachement" && property.subBoName==subBo.boName>
      <#if (property.subBoRelType=="2" || property.subBoRelType=="3") && subBo.parentBusinessObjectName!="">
      ,deleted${property.subBoName}Set
    	</#if>
    </#if>
   </#list>
</#if>
</#list>, businessObject);
       }
<#list bo.properties as property><#-- 遍历业务对象下所有子对象        ,${property.propertyName?cap_first} deleted${property.propertyName?cap_first}-->
      <#if property.propertyType=="业务对象" && property.subBoRelType=="2" && property.subBoName=="Attachement">
        //保存附件业务ID 
       	String ${bo.primaryKeyProperty.propertyName} = ${beanAttribute.boInstanceName}.get${bo.primaryKeyProperty.propertyName?cap_first}();
	    attachementJdbcDao.update(${property.propertyName}s,${bo.primaryKeyProperty.propertyName});
	    <#break>
      </#if>
</#list>
</#compress>	
	}
	
	<#elseif modelPro.methodName=="_save" ||modelPro.methodName=="_saveOrUpdate" >
	<#elseif modelPro.methodName=="_delete">
	
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param ${beanAttribute.boInstanceName}
	 */
	public void _delete(${beanAttribute.boName} ${beanAttribute.boInstanceName})
	{
		if (null != advanceService)
			advanceService.preDelete(${beanAttribute.boInstanceName});
	
<#if isHaveProcess>
		//流程状态
		String processState =${beanAttribute.boInstanceName}.get${bo.processStateFieldName?cap_first}();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
</#if>		
 		LockService.isBoInstanceLocked(${beanAttribute.boInstanceName},${bo.boName}.class);
		${beanAttribute.boInstanceName}HibernateDao.remove(${beanAttribute.boInstanceName});
<#compress>	
<#list bo.properties as property><#-- 遍历业务对象下所有子对象        ,${property.propertyName?cap_first} deleted${property.propertyName?cap_first}-->
      <#if property.propertyType=="业务对象" && property.subBoRelType=="2" && property.subBoName=="Attachement">
        //删除业务附件
       	String ${bo.primaryKeyProperty.propertyName} = ${beanAttribute.boInstanceName}.get${bo.primaryKeyProperty.propertyName?cap_first}();
	    attachementService.deleteByBusinessId(${bo.primaryKeyProperty.propertyName});
	    <#break>
      </#if>
</#list>
</#compress>	

		if (null != advanceService)
			advanceService.postDelete(${beanAttribute.boInstanceName});
	}

	/**
	 * 根据主键删除
	 * 
	 * @param ${beanAttribute.boInstanceName}Id
	 */
	public void _delete(String ${beanAttribute.boInstanceName}Id,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(${beanAttribute.boInstanceName}Id))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("${bo.primaryKeyProperty.propertyName}"));
		${beanAttribute.boName} ${beanAttribute.boInstanceName} = this.${beanAttribute.boInstanceName}HibernateDao.load(${beanAttribute.boInstanceName}Id);
		_delete(${beanAttribute.boInstanceName});
	}
	<#elseif modelPro.methodName=="_deletes">
	<#-- 批量删除 -->
	
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param Set<${beanAttribute.boName}> ${beanAttribute.boInstanceName}s
	 */
	public void _deletes(Set<${beanAttribute.boName}> ${beanAttribute.boInstanceName}s,BusinessObject businessObject)
	{
		if (null == ${beanAttribute.boInstanceName}s || ${beanAttribute.boInstanceName}s.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<${beanAttribute.boName}> it = ${beanAttribute.boInstanceName}s.iterator();
		while (it.hasNext())
		{
			${beanAttribute.boName} ${beanAttribute.boInstanceName} = (${beanAttribute.boName}) it.next();
			_delete(${beanAttribute.boInstanceName});
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param ${beanAttribute.boInstanceName}Ids
	 */
	public void _deletes(String ${beanAttribute.boInstanceName}Ids,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(${beanAttribute.boInstanceName}Ids))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("${bo.primaryKeyProperty.propertyName}"));
		String[] ids = StringUtils.splitString(${beanAttribute.boInstanceName}Ids);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	<#elseif modelPro.methodName=="_query">
	
	/**
	 * ${modelPro.methodDesc}  
	 * ${modelPro.memo} 
	 * @param queryCondition
	 * @return
	 */
	public List _query(String queryCondition)
	{
		return null;
	}
	<#elseif modelPro.methodName=="_submitProcess">
	<#-- 提交工作流 -->
	/**
	 * 提交工作流
	 * 
	 * @param ${beanAttribute.boInstanceName}
	 */
	public void _submitProcess(${beanAttribute.boName} ${beanAttribute.boInstanceName}
<#compress>		
<#list bo.properties as property>          <#-- 遍历业务对象下所有子对象 -->
<#if subBoList?exists && (subBoList?size>0)>
  <#list subBoList?sort_by("boName") as subBo>     <#-- 遍历业务对象下所有子对象 -->
   <#if property.propertyType=="业务对象" && property.subBoName!="Attachement" && property.subBoName==subBo.boName && subBo.parentBusinessObjectName!="">
      <#if property.subBoRelType=="2" || property.subBoRelType=="3">
      ,Set<${property.subBoName}> deleted${property.propertyName?cap_first}Set
      </#if>
    </#if>
   </#list>
</#if>
</#list>
</#compress>	
	,BusinessObject businessObject)
	{
		String id = ${beanAttribute.boInstanceName}.get${bo.primaryKeyProperty.propertyName?cap_first}();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(${beanAttribute.boInstanceName});
		}
		else
		{
			_update(${beanAttribute.boInstanceName}
<#compress>			
<#list bo.properties as property>          <#-- 遍历业务对象下所有子对象 -->
<#if subBoList?exists && (subBoList?size>0)>
  <#list subBoList?sort_by("boName") as subBo>     <#-- 遍历业务对象下所有子对象 -->
   <#if property.propertyType=="业务对象" && property.subBoName!="Attachement" && property.subBoName==subBo.boName && subBo.parentBusinessObjectName!="">
      <#if property.subBoRelType=="2" || property.subBoRelType=="3">
      ,deleted${property.propertyName?cap_first}Set
    	</#if>
    </#if>
   </#list>
</#if>
</#list>
</#compress>
			, businessObject);
		}**/
		
		String taskId = ${beanAttribute.boInstanceName}.getWorkflowTaskId();
		id = ${beanAttribute.boInstanceName}.get${bo.primaryKeyProperty.propertyName?cap_first}();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(${beanAttribute.boInstanceName}, id);
		else
			WorkflowService.signalProcessInstance(${beanAttribute.boInstanceName}, id, null);
	}
    </#if>
    </#list>
}