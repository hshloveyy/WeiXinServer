/*
 * @(#)CustomerRemSumService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年09月13日 09点53分56秒
 *  描　述：创建
 */
package com.infolion.xdss3.customerremainsum.service;

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
import com.infolion.xdss3.customerremainsum.domain.CustomerRemSum;
import com.infolion.xdss3.customerremainsum.service.CustomerRemSumService;
import com.infolion.xdss3.customerremainsum.dao.CustomerRemSumHibernateDao;
import com.infolion.xdss3.customerremainsumGen.service.CustomerRemSumServiceGen;

/**
 * <pre>
 * 客户项目余额(CustomerRemSum)服务类
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
public class CustomerRemSumService extends CustomerRemSumServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	/**
	 * 调用存储过程insert_customer_project_amount
	 */
	public void insertData(final String userid,final String projectid,final String customerid,final String creditType){
		this.customerRemSumHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				CallableStatement cs = session.connection().prepareCall("{call insert_customer_project_amount(?,?,?,?)}");    
				cs.setString(1, customerid);
				cs.setString(2, projectid);
				cs.setString(3, userid);
				cs.setString(4, creditType);
				boolean isSuccess = cs.execute();
				return isSuccess; 
			}
		});
	}
}