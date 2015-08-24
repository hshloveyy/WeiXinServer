/*
 * @(#)ProviderCreditConfService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点07分42秒
 *  描　述：创建
 */
package com.infolion.xdss3.ceditValueControl.service;

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
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditConf;
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditConf;
import com.infolion.xdss3.ceditValueControl.service.ProviderCreditConfService;
import com.infolion.xdss3.ceditValueControl.dao.CreditConfJdbcDao;
import com.infolion.xdss3.ceditValueControl.dao.ProviderCreditConfHibernateDao;
import com.infolion.xdss3.ceditValueControlGen.service.ProviderCreditConfServiceGen;

import com.infolion.xdss3.ceditValueControl.dao.ProviderCreditConfHibernateDao;
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditProj;
import com.infolion.xdss3.ceditValueControl.service.ProviderCreditProjService;

/**
 * <pre>
 * 供应商信用额度配置(ProviderCreditConf)服务类
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
public class ProviderCreditConfService extends ProviderCreditConfServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private ProviderCreditConfHibernateDao providerCreditConfHibernateDao;
	public void setProviderCreditConfHibernateDao(ProviderCreditConfHibernateDao providerCreditConfHibernateDao)
	{
		this.providerCreditConfHibernateDao = providerCreditConfHibernateDao;
	}
	   
    @Autowired
    private CreditConfJdbcDao creditConfJdbcDao;    
    public void setCreditConfJdbcDao(CreditConfJdbcDao creditConfJdbcDao) {
        this.creditConfJdbcDao = creditConfJdbcDao;
    }
    
	/**
	 * 更新供应商代垫额度， 插入供应商代垫变更记录
	 * @param providerid
	 * @param prepayment
	 * @return 
	 */
	public int updatePrepayment(String providerid,String projectid, double prepayment, String remark )
	{
		return this.providerCreditConfHibernateDao.updatePrepayment(providerid, projectid,prepayment, remark);
	}
	
	   
    public List<ProviderCreditConf> checkExists(String projectno) {
        return this.creditConfJdbcDao.prov_checkExists(projectno);
    }
}