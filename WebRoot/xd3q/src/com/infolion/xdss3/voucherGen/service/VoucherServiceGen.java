/*
 * @(#)VoucherServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月27日 07点47分01秒
 *  描　述：创建
 */
package com.infolion.xdss3.voucherGen.service;

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
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
          
import com.infolion.xdss3.voucheritem.domain.VoucherItem;
import com.infolion.xdss3.voucheritem.service.VoucherItemService;

/**
 * <pre>
 * 凭证预览(Voucher)服务类
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
public class VoucherServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected VoucherHibernateDao voucherHibernateDao;
	
	public void setVoucherHibernateDao(VoucherHibernateDao voucherHibernateDao)
	{
		this.voucherHibernateDao = voucherHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("voucherAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
          

	@Autowired
	protected VoucherItemService voucherItemService;
	
	public void setVoucherItemService(VoucherItemService voucherItemService)
	{
		this.voucherItemService = voucherItemService;
	}

          

	   
	/**
	 * 根据主键ID,取得凭证预览实例
	 * @param id
	 * @return
	 */
	public Voucher _getDetached(String id)
	{		
	    Voucher voucher = new Voucher();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  voucher = voucherHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(voucher);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    voucher.setOperationType(operationType);
	    
		return voucher;	
	}
	
	/**
	 * 根据主键ID,取得凭证预览实例
	 * @param id
	 * @return
	 */
	public Voucher _get(String id)
	{		
	    Voucher voucher = new Voucher();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  voucher = voucherHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(voucher);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    voucher.setOperationType(operationType);
	    
		return voucher;	
	}
	
	/**
	 * 根据主键ID,取得凭证预览实例
	 * @param id
	 * @return
	 */
	public Voucher _getForEdit(String id)
	{		
	    Voucher voucher = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = voucher.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return voucher;	
	}
	
	/**
	 * 根据主键ID,取得凭证预览实例副本
	 * @param id
	 * @return
	 */
	public Voucher _getEntityCopy(String id)
	{		
	    Voucher voucher = new Voucher();
		Voucher voucherOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(voucher, voucherOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//voucher.setVoucherid(null); 
		voucher.setProcessstate(" ");
		return voucher;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param voucher
	 */
	public void _delete(Voucher voucher)
	{
		if (null != advanceService)
			advanceService.preDelete(voucher);
	
		//流程状态
		String processState =voucher.getProcessstate();
		if(!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
 		LockService.isBoInstanceLocked(voucher,Voucher.class);
		voucherHibernateDao.remove(voucher);

		if (null != advanceService)
			advanceService.postDelete(voucher);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param voucherId
	 */
	public void _delete(String voucherId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(voucherId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("voucherid"));
		Voucher voucher = this.voucherHibernateDao.load(voucherId);
		_delete(voucher);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<Voucher> vouchers
	 */
	public void _deletes(Set<Voucher> vouchers,BusinessObject businessObject)
	{
		if (null == vouchers || vouchers.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<Voucher> it = vouchers.iterator();
		while (it.hasNext())
		{
			Voucher voucher = (Voucher) it.next();
			_delete(voucher);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param voucherIds
	 */
	public void _deletes(String voucherIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(voucherIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("voucherid"));
		String[] ids = StringUtils.splitString(voucherIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param voucher
	 */
	public void _submitProcess(Voucher voucher
,Set<VoucherItem> deletedVoucherItemSet	,BusinessObject businessObject)
	{
		String id = voucher.getVoucherid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(voucher);
		}
		else
		{
			_update(voucher
,deletedVoucherItemSet			, businessObject);
		}**/
		
		String taskId = voucher.getWorkflowTaskId();
		id = voucher.getVoucherid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(voucher, id);
		else
			WorkflowService.signalProcessInstance(voucher, id, null);
	}

	/**
	 * 保存或更新凭证预览
	 * 保存  
	 *  
	 * @param voucher
	 */
	public void _update(Voucher voucher
,Set<VoucherItem> deletedVoucherItemSet,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(voucher);
		voucherHibernateDao.saveOrUpdate(voucher);
// 删除关联子业务对象数据
if(deletedVoucherItemSet!=null && deletedVoucherItemSet.size()>0)
{
voucherItemService._deletes(deletedVoucherItemSet,businessObject);
}		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(voucher);
	}
	
	/**
	 * 保存  
	 *   
	 * @param voucher
	 */
	public void _save(Voucher voucher)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(voucher);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		voucher.setVoucherid(null);
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
		Set<VoucherItem> voucherItemSet = voucher.getVoucherItem();
		Set<VoucherItem> newVoucherItemSet = null;
		if (null != voucherItemSet)
		{
			newVoucherItemSet = new HashSet();
			Iterator<VoucherItem> itVoucherItem = voucherItemSet.iterator();
			while (itVoucherItem.hasNext())
			{
				VoucherItem voucherItem = (VoucherItem) itVoucherItem.next();
				voucherItem.setVoucheritemid(null);
				newVoucherItemSet.add(voucherItem);
			}
		}
		voucher.setVoucherItem(newVoucherItemSet);
		voucherHibernateDao.saveOrUpdate(voucher);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(voucher);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param voucher
	 */
	public void _saveOrUpdate(Voucher voucher
,Set<VoucherItem> deletedVoucherItemSet,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(voucher.getVoucherid()))
		{	
			_save(voucher);
		}
		else
		{
			_update(voucher
,deletedVoucherItemSet
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