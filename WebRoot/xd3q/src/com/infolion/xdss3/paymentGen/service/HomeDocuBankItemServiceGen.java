/*
 * @(#)HomeDocuBankItemServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点58分42秒
 *  描　述：创建
 */
package com.infolion.xdss3.paymentGen.service;

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
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.xdss3.payment.domain.HomeDocuBankItem;
import com.infolion.xdss3.payment.service.HomeDocuBankItemService;
import com.infolion.xdss3.payment.dao.HomeDocuBankItemHibernateDao;

/**
 * <pre>
 * 押汇/海外代付银行(HomeDocuBankItem)服务类
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
public class HomeDocuBankItemServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomeDocuBankItemHibernateDao homeDocuBankItemHibernateDao;
	
	public void setHomeDocuBankItemHibernateDao(HomeDocuBankItemHibernateDao homeDocuBankItemHibernateDao)
	{
		this.homeDocuBankItemHibernateDao = homeDocuBankItemHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("homeDocuBankItemAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得押汇/海外代付银行实例
	 * @param id
	 * @return
	 */
	public HomeDocuBankItem _getDetached(String id)
	{		
	    HomeDocuBankItem homeDocuBankItem = new HomeDocuBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeDocuBankItem = homeDocuBankItemHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeDocuBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeDocuBankItem.setOperationType(operationType);
	    
		return homeDocuBankItem;	
	}
	
	/**
	 * 根据主键ID,取得押汇/海外代付银行实例
	 * @param id
	 * @return
	 */
	public HomeDocuBankItem _get(String id)
	{		
	    HomeDocuBankItem homeDocuBankItem = new HomeDocuBankItem();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  homeDocuBankItem = homeDocuBankItemHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(homeDocuBankItem);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    homeDocuBankItem.setOperationType(operationType);
	    
		return homeDocuBankItem;	
	}
	
	/**
	 * 根据主键ID,取得押汇/海外代付银行实例
	 * @param id
	 * @return
	 */
	public HomeDocuBankItem _getForEdit(String id)
	{		
	    HomeDocuBankItem homeDocuBankItem = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = homeDocuBankItem.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return homeDocuBankItem;	
	}
	
	/**
	 * 根据主键ID,取得押汇/海外代付银行实例副本
	 * @param id
	 * @return
	 */
	public HomeDocuBankItem _getEntityCopy(String id)
	{		
	    HomeDocuBankItem homeDocuBankItem = new HomeDocuBankItem();
		HomeDocuBankItem homeDocuBankItemOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homeDocuBankItem, homeDocuBankItemOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//homeDocuBankItem.setDocuaryitemid(null); 
		return homeDocuBankItem;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param homeDocuBankItem
	 */
	public void _delete(HomeDocuBankItem homeDocuBankItem)
	{
		if (null != advanceService)
			advanceService.preDelete(homeDocuBankItem);
	
 		LockService.isBoInstanceLocked(homeDocuBankItem,HomeDocuBankItem.class);
		homeDocuBankItemHibernateDao.remove(homeDocuBankItem);

		if (null != advanceService)
			advanceService.postDelete(homeDocuBankItem);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homeDocuBankItemId
	 */
	public void _delete(String homeDocuBankItemId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeDocuBankItemId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("docuaryitemid"));
		HomeDocuBankItem homeDocuBankItem = this.homeDocuBankItemHibernateDao.load(homeDocuBankItemId);
		_delete(homeDocuBankItem);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<HomeDocuBankItem> homeDocuBankItems
	 */
	public void _deletes(Set<HomeDocuBankItem> homeDocuBankItems,BusinessObject businessObject)
	{
		if (null == homeDocuBankItems || homeDocuBankItems.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomeDocuBankItem> it = homeDocuBankItems.iterator();
		while (it.hasNext())
		{
			HomeDocuBankItem homeDocuBankItem = (HomeDocuBankItem) it.next();
			_delete(homeDocuBankItem);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homeDocuBankItemIds
	 */
	public void _deletes(String homeDocuBankItemIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homeDocuBankItemIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("docuaryitemid"));
		String[] ids = StringUtils.splitString(homeDocuBankItemIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param homeDocuBankItem
	 */
	public void _submitProcess(HomeDocuBankItem homeDocuBankItem
	,BusinessObject businessObject)
	{
		String id = homeDocuBankItem.getDocuaryitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(homeDocuBankItem);
		}
		else
		{
			_update(homeDocuBankItem
			, businessObject);
		}**/
		
		String taskId = homeDocuBankItem.getWorkflowTaskId();
		id = homeDocuBankItem.getDocuaryitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homeDocuBankItem, id);
		else
			WorkflowService.signalProcessInstance(homeDocuBankItem, id, null);
	}

	/**
	 * 保存或更新押汇/海外代付银行
	 * 保存  
	 *  
	 * @param homeDocuBankItem
	 */
	public void _update(HomeDocuBankItem homeDocuBankItem
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeDocuBankItem);
		homeDocuBankItemHibernateDao.saveOrUpdate(homeDocuBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeDocuBankItem);
	}
	
	/**
	 * 保存  
	 *   
	 * @param homeDocuBankItem
	 */
	public void _save(HomeDocuBankItem homeDocuBankItem)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homeDocuBankItem);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homeDocuBankItem.setDocuaryitemid(null);
                    		homeDocuBankItemHibernateDao.saveOrUpdate(homeDocuBankItem);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homeDocuBankItem);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param homeDocuBankItem
	 */
	public void _saveOrUpdate(HomeDocuBankItem homeDocuBankItem
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(homeDocuBankItem.getDocuaryitemid()))
		{	
			_save(homeDocuBankItem);
		}
		else
		{
			_update(homeDocuBankItem
, businessObject);
}	}
	
	
	/**
	 * 查询  
	 *   
	 * @param queryCondition
	 * @return
	 */
	public List _query(String queryCondition)
	{
		return null;
	}
}