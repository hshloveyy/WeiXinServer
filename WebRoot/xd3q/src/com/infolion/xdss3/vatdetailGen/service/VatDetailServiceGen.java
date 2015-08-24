/*
 * @(#)VatDetailServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年12月12日 16点14分47秒
 *  描　述：创建
 */
package com.infolion.xdss3.vatdetailGen.service;

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
import com.infolion.xdss3.vatdetail.domain.VatDetail;
import com.infolion.xdss3.vatdetail.service.VatDetailService;
import com.infolion.xdss3.vatdetail.dao.VatDetailHibernateDao;

/**
 * <pre>
 * 期初已到税票未进仓(税额)(VatDetail)服务类
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
public class VatDetailServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected VatDetailHibernateDao vatDetailHibernateDao;
	
	public void setVatDetailHibernateDao(VatDetailHibernateDao vatDetailHibernateDao)
	{
		this.vatDetailHibernateDao = vatDetailHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("vatDetailAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得期初已到税票未进仓(税额)实例
	 * @param id
	 * @return
	 */
	public VatDetail _getDetached(String id)
	{		
	    VatDetail vatDetail = new VatDetail();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  vatDetail = vatDetailHibernateDao.getDetached(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(vatDetail);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    vatDetail.setOperationType(operationType);
	    
		return vatDetail;	
	}
	
	/**
	 * 根据主键ID,取得期初已到税票未进仓(税额)实例
	 * @param id
	 * @return
	 */
	public VatDetail _get(String id)
	{		
	    VatDetail vatDetail = new VatDetail();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  vatDetail = vatDetailHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(vatDetail);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    vatDetail.setOperationType(operationType);
	    
		return vatDetail;	
	}
	
	/**
	 * 根据主键ID,取得期初已到税票未进仓(税额)实例
	 * @param id
	 * @return
	 */
	public VatDetail _getForEdit(String id)
	{		
	    VatDetail vatDetail = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = vatDetail.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return vatDetail;	
	}
	
	/**
	 * 根据主键ID,取得期初已到税票未进仓(税额)实例副本
	 * @param id
	 * @return
	 */
	public VatDetail _getEntityCopy(String id)
	{		
	    VatDetail vatDetail = new VatDetail();
		VatDetail vatDetailOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(vatDetail, vatDetailOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		//vatDetail.setTid(null); 
		return vatDetail;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param vatDetail
	 */
	public void _delete(VatDetail vatDetail)
	{
		if (null != advanceService)
			advanceService.preDelete(vatDetail);
	
 		LockService.isBoInstanceLocked(vatDetail,VatDetail.class);
		vatDetailHibernateDao.remove(vatDetail);

		if (null != advanceService)
			advanceService.postDelete(vatDetail);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param vatDetailId
	 */
	public void _delete(String vatDetailId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(vatDetailId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("tid"));
		VatDetail vatDetail = this.vatDetailHibernateDao.load(vatDetailId);
		_delete(vatDetail);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<VatDetail> vatDetails
	 */
	public void _deletes(Set<VatDetail> vatDetails,BusinessObject businessObject)
	{
		if (null == vatDetails || vatDetails.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<VatDetail> it = vatDetails.iterator();
		while (it.hasNext())
		{
			VatDetail vatDetail = (VatDetail) it.next();
			_delete(vatDetail);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param vatDetailIds
	 */
	public void _deletes(String vatDetailIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(vatDetailIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("tid"));
		String[] ids = StringUtils.splitString(vatDetailIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param vatDetail
	 */
	public void _submitProcess(VatDetail vatDetail
	,BusinessObject businessObject)
	{
		String id = vatDetail.getTid();
        /**
		if (StringUtils.isNullBlank(id))
		{
			_save(vatDetail);
		}
		else
		{
			_update(vatDetail
			, businessObject);
		}**/
		
		String taskId = vatDetail.getWorkflowTaskId();
		id = vatDetail.getTid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(vatDetail, id);
		else
			WorkflowService.signalProcessInstance(vatDetail, id, null);
	}

	/**
	 * 保存或更新期初已到税票未进仓(税额)
	 * 保存  
	 *  
	 * @param vatDetail
	 */
	public void _update(VatDetail vatDetail
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(vatDetail);
		vatDetailHibernateDao.saveOrUpdate(vatDetail);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(vatDetail);
	}
	
	/**
	 * 保存  
	 *   
	 * @param vatDetail
	 */
	public void _save(VatDetail vatDetail)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(vatDetail);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		vatDetail.setTid(null);
                                		vatDetailHibernateDao.saveOrUpdate(vatDetail);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(vatDetail);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param vatDetail
	 */
	public void _saveOrUpdate(VatDetail vatDetail
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(vatDetail.getTid()))
		{	
			_save(vatDetail);
		}
		else
		{
			_update(vatDetail
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