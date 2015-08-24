/*
 * @(#)CreditlogService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月17日 07点13分34秒
 *  描　述：创建
 */
package com.infolion.xdss3.creditlog.service;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import com.infolion.xdss3.creditlog.domain.Creditlog;
import com.infolion.xdss3.creditlog.service.CreditlogService;
import com.infolion.xdss3.creditlog.dao.CreditlogHibernateDao;
import com.infolion.xdss3.creditlogGen.service.CreditlogServiceGen;

/**
 * <pre>
 * 授信日志表(Creditlog)服务类
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
public class CreditlogService extends CreditlogServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	public void callSaveCustomerCreditLog(final String userid,final String customerid, final String cust_projectid){
			this.creditlogHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				CallableStatement cs = session.connection().prepareCall("{call save_customer_credit_log(?,?,?)}");    
				cs.setString(1, userid);
				cs.setString(2, customerid);
				cs.setString(3, cust_projectid);
				boolean isSuccess = cs.execute();
				return isSuccess; 
			}
		});
	}
	
	public void callSaveCustomerSendCreditLog(final String userid,final String customerid,final String cust_projectid){
			this.creditlogHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				CallableStatement cs = session.connection().prepareCall("{call save_customer_send_credit_log(?,?,?)}");    
				cs.setString(1, userid);
				cs.setString(2, customerid);
				cs.setString(3, cust_projectid);
				boolean isSuccess = cs.execute();
				return isSuccess; 
			}
		});
	}

	public void callSaveProviderCreditLog(final String userid,final String providerid, final String prov_projectid){
			this.creditlogHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				CallableStatement cs = session.connection().prepareCall("{call save_provider_credit_log(?,?,?)}");    
				cs.setString(1, userid);
				cs.setString(2, providerid);
				cs.setString(3, prov_projectid);
				boolean isSuccess = cs.execute();
				return isSuccess; 
			}
		});
	}
	
	public void clearData(){
		this.creditlogHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				PreparedStatement ps = session.connection().prepareStatement("delete from YCREDITLOG");
				boolean isSuccess = ps.execute();
				return isSuccess;
			}
		});
	}
}