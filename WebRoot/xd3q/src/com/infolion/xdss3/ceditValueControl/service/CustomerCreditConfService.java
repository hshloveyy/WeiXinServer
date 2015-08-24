/*
 * @(#)CustomerCreditConfService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点08分11秒
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
import com.infolion.xdss3.ceditValueControl.service.CustomerCreditConfService;
import com.infolion.xdss3.ceditValueControl.dao.CreditConfJdbcDao;
import com.infolion.xdss3.ceditValueControl.dao.CustomerCreditConfHibernateDao;
import com.infolion.xdss3.ceditValueControlGen.service.CustomerCreditConfServiceGen;
          
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditProj;
import com.infolion.xdss3.ceditValueControl.service.CustomerCreditProjService;

/**
 * <pre>
 * 客户代垫额度和发货额度配置(CustomerCreditConf)服务类
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
public class CustomerCreditConfService extends CustomerCreditConfServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private CustomerCreditConfHibernateDao customerCreditConfHibernateDao;	
	public void setCustomerCreditConfHibernateDao( CustomerCreditConfHibernateDao customerCreditConfHibernateDao )
	{
		this.customerCreditConfHibernateDao = customerCreditConfHibernateDao;
	}
	
	@Autowired
	private CreditConfJdbcDao creditConfJdbcDao;	
    public void setCreditConfJdbcDao(CreditConfJdbcDao creditConfJdbcDao) {
        this.creditConfJdbcDao = creditConfJdbcDao;
    }

    /**
	 * 更新客户代垫额度,同时插入更新记录
	 * @param customerid: 客户id
	 * @param prepayment: 代垫额度
	 */
	public int updateCredit(String customerid, String projectid, String valueType, double prepayValue, double sendValue, String remark )
	{
		return this.customerCreditConfHibernateDao.updateCredit(customerid, projectid, valueType, prepayValue, sendValue, remark);		
	}
	
	public List<CustomerCreditConf> checkExists(String creditType, String projectno) {
	    return this.creditConfJdbcDao.cust_checkExists(creditType,projectno);
	}
	
}