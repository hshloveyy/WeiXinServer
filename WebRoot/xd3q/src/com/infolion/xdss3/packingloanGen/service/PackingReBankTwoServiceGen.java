/*
 * @(#)PackingReBankTwoServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年06月15日 15点21分13秒
 *  描　述：创建
 */
package com.infolion.xdss3.packingloanGen.service;

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
import com.infolion.xdss3.packingloan.domain.PackingReBankTwo;
import com.infolion.xdss3.packingloan.service.PackingReBankTwoService;
import com.infolion.xdss3.packingloan.dao.PackingReBankTwoHibernateDao;

/**
 * <pre>
 * 还打包银行2(PackingReBankTwo)服务类
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
public class PackingReBankTwoServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected PackingReBankTwoHibernateDao packingReBankTwoHibernateDao;
	
	public void setPackingReBankTwoHibernateDao(PackingReBankTwoHibernateDao packingReBankTwoHibernateDao)
	{
		this.packingReBankTwoHibernateDao = packingReBankTwoHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("packingReBankTwoAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得还打包银行2实例
	 * @param id
	 * @return
	 */
	public PackingReBankTwo _getDetached(String id)
	{		
	    PackingReBankTwo packingReBankTwo = new PackingReBankTwo();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  packingReBankTwo = packingReBankTwoHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(packingReBankTwo);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    packingReBankTwo.setOperationType(operationType);
	    
		return packingReBankTwo;	
	}
	
	/**
	 * 根据主键ID,取得还打包银行2实例
	 * @param id
	 * @return
	 */
	public PackingReBankTwo _get(String id)
	{		
	    PackingReBankTwo packingReBankTwo = new PackingReBankTwo();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  packingReBankTwo = packingReBankTwoHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(packingReBankTwo);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    packingReBankTwo.setOperationType(operationType);
	    
		return packingReBankTwo;	
	}
	
	/**
	 * 根据主键ID,取得还打包银行2实例
	 * @param id
	 * @return
	 */
	public PackingReBankTwo _getForEdit(String id)
	{		
	    PackingReBankTwo packingReBankTwo = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = packingReBankTwo.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return packingReBankTwo;	
	}
	
	/**
	 * 根据主键ID,取得还打包银行2实例副本
	 * @param id
	 * @return
	 */
	public PackingReBankTwo _getEntityCopy(String id)
	{		
	    PackingReBankTwo packingReBankTwo = new PackingReBankTwo();
		PackingReBankTwo packingReBankTwoOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(packingReBankTwo, packingReBankTwoOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//packingReBankTwo.setBankitemid(null); 
		return packingReBankTwo;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param packingReBankTwo
	 */
	public void _delete(PackingReBankTwo packingReBankTwo)
	{
		if (null != advanceService)
			advanceService.preDelete(packingReBankTwo);
	
 		LockService.isBoInstanceLocked(packingReBankTwo,PackingReBankTwo.class);
		packingReBankTwoHibernateDao.remove(packingReBankTwo);

		if (null != advanceService)
			advanceService.postDelete(packingReBankTwo);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param packingReBankTwoId
	 */
	public void _delete(String packingReBankTwoId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(packingReBankTwoId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("bankitemid"));
		PackingReBankTwo packingReBankTwo = this.packingReBankTwoHibernateDao.load(packingReBankTwoId);
		_delete(packingReBankTwo);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<PackingReBankTwo> packingReBankTwos
	 */
	public void _deletes(Set<PackingReBankTwo> packingReBankTwos,BusinessObject businessObject)
	{
		if (null == packingReBankTwos || packingReBankTwos.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<PackingReBankTwo> it = packingReBankTwos.iterator();
		while (it.hasNext())
		{
			PackingReBankTwo packingReBankTwo = (PackingReBankTwo) it.next();
			_delete(packingReBankTwo);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param packingReBankTwoIds
	 */
	public void _deletes(String packingReBankTwoIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(packingReBankTwoIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("bankitemid"));
		String[] ids = StringUtils.splitString(packingReBankTwoIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param packingReBankTwo
	 */
	public void _submitProcess(PackingReBankTwo packingReBankTwo
	,BusinessObject businessObject)
	{
		String id = packingReBankTwo.getBankitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(packingReBankTwo);
		}
		else
		{
			_update(packingReBankTwo
			, businessObject);
		}**/
		
		String taskId = packingReBankTwo.getWorkflowTaskId();
		id = packingReBankTwo.getBankitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(packingReBankTwo, id);
		else
			WorkflowService.signalProcessInstance(packingReBankTwo, id, null);
	}

	/**
	 * 保存或更新还打包银行2
	 * 保存  
	 *  
	 * @param packingReBankTwo
	 */
	public void _update(PackingReBankTwo packingReBankTwo
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(packingReBankTwo);
		packingReBankTwoHibernateDao.saveOrUpdate(packingReBankTwo);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(packingReBankTwo);
	}
	
	/**
	 * 保存  
	 *   
	 * @param packingReBankTwo
	 */
	public void _save(PackingReBankTwo packingReBankTwo)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(packingReBankTwo);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		packingReBankTwo.setBankitemid(null);
                      		packingReBankTwoHibernateDao.saveOrUpdate(packingReBankTwo);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(packingReBankTwo);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param packingReBankTwo
	 */
	public void _saveOrUpdate(PackingReBankTwo packingReBankTwo
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(packingReBankTwo.getBankitemid()))
		{	
			_save(packingReBankTwo);
		}
		else
		{
			_update(packingReBankTwo
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