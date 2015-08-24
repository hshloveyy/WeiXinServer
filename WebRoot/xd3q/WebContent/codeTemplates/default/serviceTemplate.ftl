/*
 * @(#)${beanAttribute.beanName}.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：${beanAttribute.createTime?string("yyyy年MM月dd日 HH点mm分ss秒")}
 *  描　述：创建
 */
package ${beanAttribute.packageName}.service;

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
import ${beanAttribute.domainPackage};
import ${beanAttribute.servicePackage};
import ${beanAttribute.hibernateDaoPackage};
import ${beanAttribute.packageNameGen}.service.${beanAttribute.beanNameGen};
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
public class ${beanAttribute.beanName} extends ${beanAttribute.beanNameGen}
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
}