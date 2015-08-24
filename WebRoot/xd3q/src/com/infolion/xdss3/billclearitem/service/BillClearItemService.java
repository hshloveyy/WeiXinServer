/*
 * @(#)BillClearItemService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月11日 03点10分51秒
 *  描　述：创建
 */
package com.infolion.xdss3.billclearitem.service;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.infolion.xdss3.billclearitem.domain.BillClearItem;
import com.infolion.xdss3.billclearitem.service.BillClearItemService;
import com.infolion.xdss3.billclearitem.dao.BillClearItemHibernateDao;
import com.infolion.xdss3.billclearitemGen.service.BillClearItemServiceGen;

/**
 * <pre>
 * 票清款行项(BillClearItem)服务类
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
public class BillClearItemService extends BillClearItemServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	public BigDecimal getSumOnroadAmount(String billno, String cleartype){
		String hql = "select nvl(sum(clearbillamount),0) as clearbillamount from BillClearItem where invoice = ? and billClearCollect.cleartype = ? and billClearCollect.businessstate <=1";
		List list = this.billClearItemHibernateDao.find(hql,new Object[]{billno, cleartype});
		if (list != null && list.size() > 0)
		{
			return (BigDecimal)list.get(0);
		}else{
			return BigDecimal.valueOf(0);
		}
	}
	
	public BigDecimal getSumClearedAmount(String billno, String cleartype){
		String hql = "select nvl(sum(clearbillamount),0) as clearbillamount from BillClearItem where invoice = ? and billClearCollect.cleartype = ? and billClearCollect.businessstate =3";
		List list = this.billClearItemHibernateDao.find(hql,new Object[]{billno, cleartype});
		if (list != null && list.size() > 0)
		{
			return (BigDecimal)list.get(0);
		}else{
			return BigDecimal.valueOf(0);
		}
	}
	
	public BigDecimal getSumAdjustAmount(String billclearid){
		String hql = "select nvl(sum(adjustamount),0) as adjustamount from BillClearItem where billclearid = ?";
		List list = this.billClearItemHibernateDao.find(hql,new Object[]{billclearid});
		if (list != null && list.size() > 0)
		{
			return (BigDecimal)list.get(0);
		}else{
			return BigDecimal.valueOf(0);
		}
	}
}