/*
 * @(#)PrivOtheChanRecoServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月28日 14点27分30秒
 *  描　述：创建
 */
package com.infolion.xdss3.ceditValueControlGen.service;

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
import com.infolion.xdss3.ceditValueControl.domain.PrivOtheChanReco;
import com.infolion.xdss3.ceditValueControl.service.PrivOtheChanRecoService;
import com.infolion.xdss3.ceditValueControl.dao.PrivOtheChanRecoHibernateDao;

/**
 * <pre>
 * 供应商代垫费用查询(PrivOtheChanReco)服务类
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
public class PrivOtheChanRecoServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected PrivOtheChanRecoHibernateDao privOtheChanRecoHibernateDao;
	
	public void setPrivOtheChanRecoHibernateDao(PrivOtheChanRecoHibernateDao privOtheChanRecoHibernateDao)
	{
		this.privOtheChanRecoHibernateDao = privOtheChanRecoHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("privOtheChanRecoAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得供应商代垫费用查询实例
	 * @param id
	 * @return
	 */
	public PrivOtheChanReco _getDetached(String id)
	{		
	    PrivOtheChanReco privOtheChanReco = new PrivOtheChanReco();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  privOtheChanReco = privOtheChanRecoHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(privOtheChanReco);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    privOtheChanReco.setOperationType(operationType);
	    
		return privOtheChanReco;	
	}
	
	/**
	 * 根据主键ID,取得供应商代垫费用查询实例
	 * @param id
	 * @return
	 */
	public PrivOtheChanReco _get(String id)
	{		
	    PrivOtheChanReco privOtheChanReco = new PrivOtheChanReco();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  privOtheChanReco = privOtheChanRecoHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(privOtheChanReco);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    privOtheChanReco.setOperationType(operationType);
	    
		return privOtheChanReco;	
	}
	
	/**
	 * 根据主键ID,取得供应商代垫费用查询实例
	 * @param id
	 * @return
	 */
	public PrivOtheChanReco _getForEdit(String id)
	{		
	    PrivOtheChanReco privOtheChanReco = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = privOtheChanReco.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return privOtheChanReco;	
	}
	
	/**
	 * 根据主键ID,取得供应商代垫费用查询实例副本
	 * @param id
	 * @return
	 */
	public PrivOtheChanReco _getEntityCopy(String id)
	{		
	    PrivOtheChanReco privOtheChanReco = new PrivOtheChanReco();
		PrivOtheChanReco privOtheChanRecoOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(privOtheChanReco, privOtheChanRecoOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//privOtheChanReco.setChangeid(null); 
		return privOtheChanReco;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param privOtheChanReco
	 */
	public void _delete(PrivOtheChanReco privOtheChanReco)
	{
		if (null != advanceService)
			advanceService.preDelete(privOtheChanReco);
	
 		LockService.isBoInstanceLocked(privOtheChanReco,PrivOtheChanReco.class);
		privOtheChanRecoHibernateDao.remove(privOtheChanReco);

		if (null != advanceService)
			advanceService.postDelete(privOtheChanReco);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param privOtheChanRecoId
	 */
	public void _delete(String privOtheChanRecoId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(privOtheChanRecoId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("changeid"));
		PrivOtheChanReco privOtheChanReco = this.privOtheChanRecoHibernateDao.load(privOtheChanRecoId);
		_delete(privOtheChanReco);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<PrivOtheChanReco> privOtheChanRecos
	 */
	public void _deletes(Set<PrivOtheChanReco> privOtheChanRecos,BusinessObject businessObject)
	{
		if (null == privOtheChanRecos || privOtheChanRecos.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<PrivOtheChanReco> it = privOtheChanRecos.iterator();
		while (it.hasNext())
		{
			PrivOtheChanReco privOtheChanReco = (PrivOtheChanReco) it.next();
			_delete(privOtheChanReco);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param privOtheChanRecoIds
	 */
	public void _deletes(String privOtheChanRecoIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(privOtheChanRecoIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("changeid"));
		String[] ids = StringUtils.splitString(privOtheChanRecoIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param privOtheChanReco
	 */
	public void _submitProcess(PrivOtheChanReco privOtheChanReco
	,BusinessObject businessObject)
	{
		String id = privOtheChanReco.getChangeid();
		if (StringUtils.isNullBlank(id))
		{
			_save(privOtheChanReco);
		}
		else
		{
			_update(privOtheChanReco
			, businessObject);
		}
		String taskId = privOtheChanReco.getWorkflowTaskId();
		id = privOtheChanReco.getChangeid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(privOtheChanReco, id);
		else
			WorkflowService.signalProcessInstance(privOtheChanReco, id, null);
	}

	/**
	 * 保存或更新供应商代垫费用查询
	 * 保存  
	 *  
	 * @param privOtheChanReco
	 */
	public void _update(PrivOtheChanReco privOtheChanReco
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(privOtheChanReco);
		privOtheChanRecoHibernateDao.saveOrUpdate(privOtheChanReco);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(privOtheChanReco);
	}
	
	/**
	 * 保存  
	 *   
	 * @param privOtheChanReco
	 */
	public void _save(PrivOtheChanReco privOtheChanReco)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(privOtheChanReco);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		privOtheChanReco.setChangeid(null);
                        		privOtheChanRecoHibernateDao.saveOrUpdate(privOtheChanReco);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(privOtheChanReco);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param privOtheChanReco
	 */
	public void _saveOrUpdate(PrivOtheChanReco privOtheChanReco
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(privOtheChanReco.getChangeid()))
		{	
			_save(privOtheChanReco);
		}
		else
		{
			_update(privOtheChanReco
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