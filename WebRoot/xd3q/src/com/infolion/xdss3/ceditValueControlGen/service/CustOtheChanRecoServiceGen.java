/*
 * @(#)CustOtheChanRecoServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月28日 13点31分18秒
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
import com.infolion.xdss3.ceditValueControl.domain.CustOtheChanReco;
import com.infolion.xdss3.ceditValueControl.service.CustOtheChanRecoService;
import com.infolion.xdss3.ceditValueControl.dao.CustOtheChanRecoHibernateDao;

/**
 * <pre>
 * 客户代垫费用查询(CustOtheChanReco)服务类
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
public class CustOtheChanRecoServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CustOtheChanRecoHibernateDao custOtheChanRecoHibernateDao;
	
	public void setCustOtheChanRecoHibernateDao(CustOtheChanRecoHibernateDao custOtheChanRecoHibernateDao)
	{
		this.custOtheChanRecoHibernateDao = custOtheChanRecoHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("custOtheChanRecoAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得客户代垫费用查询实例
	 * @param id
	 * @return
	 */
	public CustOtheChanReco _getDetached(String id)
	{		
	    CustOtheChanReco custOtheChanReco = new CustOtheChanReco();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  custOtheChanReco = custOtheChanRecoHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(custOtheChanReco);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    custOtheChanReco.setOperationType(operationType);
	    
		return custOtheChanReco;	
	}
	
	/**
	 * 根据主键ID,取得客户代垫费用查询实例
	 * @param id
	 * @return
	 */
	public CustOtheChanReco _get(String id)
	{		
	    CustOtheChanReco custOtheChanReco = new CustOtheChanReco();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  custOtheChanReco = custOtheChanRecoHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(custOtheChanReco);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    custOtheChanReco.setOperationType(operationType);
	    
		return custOtheChanReco;	
	}
	
	/**
	 * 根据主键ID,取得客户代垫费用查询实例
	 * @param id
	 * @return
	 */
	public CustOtheChanReco _getForEdit(String id)
	{		
	    CustOtheChanReco custOtheChanReco = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = custOtheChanReco.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return custOtheChanReco;	
	}
	
	/**
	 * 根据主键ID,取得客户代垫费用查询实例副本
	 * @param id
	 * @return
	 */
	public CustOtheChanReco _getEntityCopy(String id)
	{		
	    CustOtheChanReco custOtheChanReco = new CustOtheChanReco();
		CustOtheChanReco custOtheChanRecoOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(custOtheChanReco, custOtheChanRecoOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//custOtheChanReco.setChangeid(null); 
		return custOtheChanReco;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param custOtheChanReco
	 */
	public void _delete(CustOtheChanReco custOtheChanReco)
	{
		if (null != advanceService)
			advanceService.preDelete(custOtheChanReco);
	
 		LockService.isBoInstanceLocked(custOtheChanReco,CustOtheChanReco.class);
		custOtheChanRecoHibernateDao.remove(custOtheChanReco);

		if (null != advanceService)
			advanceService.postDelete(custOtheChanReco);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param custOtheChanRecoId
	 */
	public void _delete(String custOtheChanRecoId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(custOtheChanRecoId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("changeid"));
		CustOtheChanReco custOtheChanReco = this.custOtheChanRecoHibernateDao.load(custOtheChanRecoId);
		_delete(custOtheChanReco);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<CustOtheChanReco> custOtheChanRecos
	 */
	public void _deletes(Set<CustOtheChanReco> custOtheChanRecos,BusinessObject businessObject)
	{
		if (null == custOtheChanRecos || custOtheChanRecos.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<CustOtheChanReco> it = custOtheChanRecos.iterator();
		while (it.hasNext())
		{
			CustOtheChanReco custOtheChanReco = (CustOtheChanReco) it.next();
			_delete(custOtheChanReco);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param custOtheChanRecoIds
	 */
	public void _deletes(String custOtheChanRecoIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(custOtheChanRecoIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("changeid"));
		String[] ids = StringUtils.splitString(custOtheChanRecoIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param custOtheChanReco
	 */
	public void _submitProcess(CustOtheChanReco custOtheChanReco
	,BusinessObject businessObject)
	{
		String id = custOtheChanReco.getChangeid();
		if (StringUtils.isNullBlank(id))
		{
			_save(custOtheChanReco);
		}
		else
		{
			_update(custOtheChanReco
			, businessObject);
		}
		String taskId = custOtheChanReco.getWorkflowTaskId();
		id = custOtheChanReco.getChangeid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(custOtheChanReco, id);
		else
			WorkflowService.signalProcessInstance(custOtheChanReco, id, null);
	}

	/**
	 * 保存或更新客户代垫费用查询
	 * 保存  
	 *  
	 * @param custOtheChanReco
	 */
	public void _update(CustOtheChanReco custOtheChanReco
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(custOtheChanReco);
		custOtheChanRecoHibernateDao.saveOrUpdate(custOtheChanReco);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(custOtheChanReco);
	}
	
	/**
	 * 保存  
	 *   
	 * @param custOtheChanReco
	 */
	public void _save(CustOtheChanReco custOtheChanReco)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(custOtheChanReco);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		custOtheChanReco.setChangeid(null);
                        		custOtheChanRecoHibernateDao.saveOrUpdate(custOtheChanReco);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(custOtheChanReco);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param custOtheChanReco
	 */
	public void _saveOrUpdate(CustOtheChanReco custOtheChanReco
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(custOtheChanReco.getChangeid()))
		{	
			_save(custOtheChanReco);
		}
		else
		{
			_update(custOtheChanReco
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