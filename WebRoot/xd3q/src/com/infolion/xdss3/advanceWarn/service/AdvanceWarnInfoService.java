/*
 * @(#)AdvanceWarnInfoService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点17分59秒
 *  描　述：创建
 */
package com.infolion.xdss3.advanceWarn.service;

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
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnInfo;
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnObject;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnInfoService;
import com.infolion.xdss3.advanceWarn.dao.AdvanceWarnInfoHibernateDao;
import com.infolion.xdss3.advanceWarn.dao.AdvanceWarnObjectHibernateDao;
import com.infolion.xdss3.advanceWarnGen.service.AdvanceWarnInfoServiceGen;

/**
 * <pre>
 * 业务预警信息(AdvanceWarnInfo)服务类
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
public class AdvanceWarnInfoService extends AdvanceWarnInfoServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private AdvanceWarnObjectHibernateDao advanceWarnObjectHibernateDao;
	
	/**
	 * @param advanceWarnObjectHibernateDao the advanceWarnObjectHibernateDao to set
	 */
	public void setAdvanceWarnObjectHibernateDao(
			AdvanceWarnObjectHibernateDao advanceWarnObjectHibernateDao) {
		this.advanceWarnObjectHibernateDao = advanceWarnObjectHibernateDao;
	}

	/**
	 * 处理预警信息
	 * @param warnInfoIds 选中业务预警信息ID
	 * @param againWarnDay 再次提醒天数
	 */
	public void modifyWarnState(String[] warnInfoIds, String[] warnids, String[] primaryKeys, String againWarnDay )
	{
		for(int i=0; i< warnInfoIds.length; i++ )
		{
			try
			{
				this.advanceWarnInfoHibernateDao.modifyWarnInfoState(warnInfoIds[i], againWarnDay);
				
				//发起人清除方式，关闭所有相关预警信息
				if( isNeedClearAll(warnids[i]) )
				{
					this.advanceWarnInfoHibernateDao.closeWarnInfoByWarnId(warnids[i], primaryKeys[i]);
				}			
			}
			catch(Exception ex)
			{
				throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataNotInDB, warnInfoIds[i]);
			}
		}
	}
	
	/**
	 * 判断预警信息清除方式
	 * @param warnid
	 * @return
	 */
	public boolean isNeedClearAll(String warnid)
	{
		String clearType = this.advanceWarnObjectHibernateDao.getClearType(warnid);
		
		// 1: 发起人清除
		// 2: 指定清除人清除
		return clearType.equals("1") ? true : false;
	}
	
	
}