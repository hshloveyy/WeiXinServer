/*
 * @(#)DeptChargeService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月11日 09点45分50秒
 *  描　述：创建
 */
package com.infolion.XDSS.sample.deptcharge.service;

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
import com.infolion.XDSS.sample.deptcharge.domain.DeptCharge;
import com.infolion.XDSS.sample.deptcharge.service.DeptChargeService;
import com.infolion.XDSS.sample.deptcharge.dao.DeptChargeHibernateDao;
import com.infolion.XDSS.sample.deptchargeGen.service.DeptChargeServiceGen;

import com.infolion.XDSS.sample.deptcharge.domain.DeptCharDetail;
import com.infolion.XDSS.sample.deptcharge.service.DeptCharDetailService;

/**
 * <pre>
 * 管理费用预算(DeptCharge)服务类
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
public class DeptChargeService extends DeptChargeServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	public DeptCharge get(String budorgid, String budclassid, String version, String ayear)
	{
		DeptCharge resultDeptCharge = null;
		DeptCharge example = new DeptCharge();
		example.setBudorgid(budorgid);
		example.setAyear(ayear);
		example.setBudclassid(budclassid);
		example.setVersion(version);
		List list = this.deptChargeHibernateDao.findByExample(example);
		if (list != null && list.size() > 0)
		{
			resultDeptCharge = (DeptCharge) list.get(0);
		}
		return resultDeptCharge;

	}
}