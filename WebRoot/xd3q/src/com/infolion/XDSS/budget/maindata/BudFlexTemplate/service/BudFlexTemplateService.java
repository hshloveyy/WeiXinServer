/*
 * @(#)BudFlexTemplateService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月03日 17点14分24秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudFlexTemplate.service;

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
import com.infolion.XDSS.budget.maindata.BudFlexTemplate.domain.BudFlexTemplate;
import com.infolion.XDSS.budget.maindata.BudFlexTemplate.service.BudFlexTemplateService;
import com.infolion.XDSS.budget.maindata.BudFlexTemplate.dao.BudFlexTemplateHibernateDao;
import com.infolion.XDSS.budget.maindata.BudFlexTemplateGen.service.BudFlexTemplateServiceGen;

/**
 * <pre>
 * 预算模版flex文件流(BudFlexTemplate)服务类
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
public class BudFlexTemplateService extends BudFlexTemplateServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
}