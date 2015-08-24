/*
 * @(#)CollectCbillService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月07日 07点00分06秒
 *  描　述：创建
 */
package com.infolion.xdss3.collectcbill.service;

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
import java.math.BigDecimal;

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
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectcbill.service.CollectCbillService;
import com.infolion.xdss3.collectcbill.dao.CollectCbillHibernateDao;
import com.infolion.xdss3.collectcbillGen.service.CollectCbillServiceGen;

/**
 * <pre>
 * 收款清票(CollectCbill)服务类
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
public class CollectCbillService extends CollectCbillServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	public BigDecimal getSumOnroadAmount(String billno){
		String hql = "select nvl(sum(clearamount),0) as clearamount from CollectCbill " +
					 "where billno = ? and collect.businessstate<> '-1'";
		List list = this.collectCbillHibernateDao.find(hql,new Object[]{billno});
		if (list != null && list.size() > 0)
		{
			return (BigDecimal)list.get(0);
		}else{
			return BigDecimal.valueOf(0);
		}
	}
	
	public BigDecimal getSumClearedAmount(String billno){
		String hql = "select nvl(sum(clearamount),0) as clearamount from CollectCbill where billno = ? and collect.businessstate ='3'";
		List list = this.collectCbillHibernateDao.find(hql,new Object[]{billno});
		if (list != null && list.size() > 0)
		{
			return (BigDecimal)list.get(0);
		}else{
			return BigDecimal.valueOf(0);
		}
	}
	
}