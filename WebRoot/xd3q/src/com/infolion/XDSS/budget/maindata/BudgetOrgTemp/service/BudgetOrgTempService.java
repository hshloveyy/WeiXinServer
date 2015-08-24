/*
 * @(#)BudgetOrgTempService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月05日 13点59分07秒
 *  描　述：创建
 */
package com.infolion.XDSS.budget.maindata.BudgetOrgTemp.service;

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
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.domain.BudgetOrgTemp;
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.service.BudgetOrgTempService;
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.dao.BudgetOrgTempHibernateDao;
import com.infolion.XDSS.budget.maindata.BudgetOrgTemp.dao.BudgetOrgTempJdbcDao;
import com.infolion.XDSS.budget.maindata.BudgetOrgTempGen.service.BudgetOrgTempServiceGen;

/**
 * <pre>
 * 预算组织模版(BudgetOrgTemp)服务类
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
public class BudgetOrgTempService extends BudgetOrgTempServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private BudgetOrgTempJdbcDao budgetOrgTempJdbcDao;

	public boolean isHaveBudClass(String strBudClassId,String strBudOrgId){
		// String strSql = "from BudgetOrgTemp budgetorgtemp " +
		// "where budgetorgtemp.budorgid = '" + strBudClassId + "' " +
		// "and budgetorgtemp.budclassid in " +
		// "(select budgetclass.budclassid " +
		// "from BudgetClass budgetclass " +
		// "where budgetclass.budupclassid in " +
		// "(select budclassidin.budupclassid " +
		// "from BudgetClass budclassidin " +
		// "where budclassidin.budclassid = '" + strBudClassId + "'))";
		//		
		// List budgetOrgTempList = budgetOrgTempHibernateDao.find(strSql);
		//		
		// if (budgetOrgTempList != null && budgetOrgTempList.size() > 0)
		// {
		// return true;
		// }else{
		// return false;
		// }
		return budgetOrgTempJdbcDao.isHaveBudClass(strBudClassId, strBudOrgId);
	}

	public void setBudgetOrgTempJdbcDao(BudgetOrgTempJdbcDao budgetOrgTempJdbcDao)
	{
		this.budgetOrgTempJdbcDao = budgetOrgTempJdbcDao;
	}
}