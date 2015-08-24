/*
 * @(#)BillClearItemPayServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月07日 17点42分28秒
 *  描　述：创建
 */
package com.infolion.xdss3.billClearGen.service;

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
import com.infolion.xdss3.billClear.domain.BillClearItemPay;
import com.infolion.xdss3.billClear.service.BillClearItemPayService;
import com.infolion.xdss3.billClear.dao.BillClearItemPayHibernateDao;

/**
 * <pre>
 * 票清付款行项目(BillClearItemPay)服务类
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
public class BillClearItemPayServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected BillClearItemPayHibernateDao billClearItemPayHibernateDao;
	
	public void setBillClearItemPayHibernateDao(BillClearItemPayHibernateDao billClearItemPayHibernateDao)
	{
		this.billClearItemPayHibernateDao = billClearItemPayHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("billClearItemPayAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得票清付款行项目实例
	 * @param id
	 * @return
	 */
	public BillClearItemPay _getDetached(String id)
	{		
	    BillClearItemPay billClearItemPay = new BillClearItemPay();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billClearItemPay = billClearItemPayHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billClearItemPay);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billClearItemPay.setOperationType(operationType);
	    
		return billClearItemPay;	
	}
	
	/**
	 * 根据主键ID,取得票清付款行项目实例
	 * @param id
	 * @return
	 */
	public BillClearItemPay _get(String id)
	{		
	    BillClearItemPay billClearItemPay = new BillClearItemPay();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  billClearItemPay = billClearItemPayHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(billClearItemPay);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    billClearItemPay.setOperationType(operationType);
	    
		return billClearItemPay;	
	}
	
	/**
	 * 根据主键ID,取得票清付款行项目实例
	 * @param id
	 * @return
	 */
	public BillClearItemPay _getForEdit(String id)
	{		
	    BillClearItemPay billClearItemPay = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = billClearItemPay.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return billClearItemPay;	
	}
	
	/**
	 * 根据主键ID,取得票清付款行项目实例副本
	 * @param id
	 * @return
	 */
	public BillClearItemPay _getEntityCopy(String id)
	{		
	    BillClearItemPay billClearItemPay = new BillClearItemPay();
		BillClearItemPay billClearItemPayOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(billClearItemPay, billClearItemPayOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//billClearItemPay.setBillclearitemid(null); 
		return billClearItemPay;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param billClearItemPay
	 */
	public void _delete(BillClearItemPay billClearItemPay)
	{
		if (null != advanceService)
			advanceService.preDelete(billClearItemPay);
	
 		LockService.isBoInstanceLocked(billClearItemPay,BillClearItemPay.class);
		billClearItemPayHibernateDao.remove(billClearItemPay);

		if (null != advanceService)
			advanceService.postDelete(billClearItemPay);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param billClearItemPayId
	 */
	public void _delete(String billClearItemPayId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billClearItemPayId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billclearitemid"));
		BillClearItemPay billClearItemPay = this.billClearItemPayHibernateDao.load(billClearItemPayId);
		_delete(billClearItemPay);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<BillClearItemPay> billClearItemPays
	 */
	public void _deletes(Set<BillClearItemPay> billClearItemPays,BusinessObject businessObject)
	{
		if (null == billClearItemPays || billClearItemPays.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<BillClearItemPay> it = billClearItemPays.iterator();
		while (it.hasNext())
		{
			BillClearItemPay billClearItemPay = (BillClearItemPay) it.next();
			_delete(billClearItemPay);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param billClearItemPayIds
	 */
	public void _deletes(String billClearItemPayIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(billClearItemPayIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("billclearitemid"));
		String[] ids = StringUtils.splitString(billClearItemPayIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param billClearItemPay
	 */
	public void _submitProcess(BillClearItemPay billClearItemPay
	,BusinessObject businessObject)
	{
		String id = billClearItemPay.getBillclearitemid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(billClearItemPay);
		}
		else
		{
			_update(billClearItemPay
			, businessObject);
		}**/
		
		String taskId = billClearItemPay.getWorkflowTaskId();
		id = billClearItemPay.getBillclearitemid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(billClearItemPay, id);
		else
			WorkflowService.signalProcessInstance(billClearItemPay, id, null);
	}

	/**
	 * 保存或更新票清付款行项目
	 * 保存  
	 *  
	 * @param billClearItemPay
	 */
	public void _update(BillClearItemPay billClearItemPay
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billClearItemPay);
		billClearItemPayHibernateDao.saveOrUpdate(billClearItemPay);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billClearItemPay);
	}
	
	/**
	 * 保存  
	 *   
	 * @param billClearItemPay
	 */
	public void _save(BillClearItemPay billClearItemPay)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(billClearItemPay);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		billClearItemPay.setBillclearitemid(null);
                                          		billClearItemPayHibernateDao.saveOrUpdate(billClearItemPay);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(billClearItemPay);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param billClearItemPay
	 */
	public void _saveOrUpdate(BillClearItemPay billClearItemPay
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(billClearItemPay.getBillclearitemid()))
		{	
			_save(billClearItemPay);
		}
		else
		{
			_update(billClearItemPay
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