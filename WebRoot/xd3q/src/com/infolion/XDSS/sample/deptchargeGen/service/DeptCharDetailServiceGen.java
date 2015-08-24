/*
 * @(#)DeptCharDetailServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月11日 09点45分51秒
 *  描　述：创建
 */
package com.infolion.XDSS.sample.deptchargeGen.service;

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
import com.infolion.XDSS.sample.deptcharge.domain.DeptCharDetail;
import com.infolion.XDSS.sample.deptcharge.service.DeptCharDetailService;
import com.infolion.XDSS.sample.deptcharge.dao.DeptCharDetailHibernateDao;

/**
 * <pre>
 * 费用明细(DeptCharDetail)服务类
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
public class DeptCharDetailServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected DeptCharDetailHibernateDao deptCharDetailHibernateDao;
	
	public void setDeptCharDetailHibernateDao(DeptCharDetailHibernateDao deptCharDetailHibernateDao)
	{
		this.deptCharDetailHibernateDao = deptCharDetailHibernateDao;
	}
	
     /**
	 * 注入扩展服务类
	 */
	 @Autowired(required = false)
	 public void setAdvanceService(@Qualifier("deptCharDetailAdvanceService") AdvanceService advanceService)
	 {
	  	super.setAdvanceService(advanceService);
	 }   
	 
	   
	/**
	 * 根据主键ID,取得费用明细实例
	 * @param id
	 * @return
	 */
	public DeptCharDetail _get(String id)
	{		
	    DeptCharDetail deptCharDetail = new DeptCharDetail();
	    if(StringUtils.isNotBlank(id))
	    {
	   	  deptCharDetail = deptCharDetailHibernateDao.get(id);
	    }
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = LockService.lockBOData(deptCharDetail);
	    if(OperationType.UNVISIABLE.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
	    }
	    deptCharDetail.setOperationType(operationType);
	    
		return deptCharDetail;	
	}
	
		/**
	 * 根据主键ID,取得费用明细实例
	 * @param id
	 * @return
	 */
	public DeptCharDetail _getForEdit(String id)
	{		
	    DeptCharDetail deptCharDetail = _get(id);
	    //需要转移到service服务里，要不存在事务问题。
	    String operationType = deptCharDetail.getOperationType();
	    if(OperationType.READONLY.equals(operationType))
	    {
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
	    }

		return deptCharDetail;	
	}
	
	/**
	 * 根据主键ID,取得费用明细实例副本
	 * @param id
	 * @return
	 */
	public DeptCharDetail _getEntityCopy(String id)
	{		
	    DeptCharDetail deptCharDetail = new DeptCharDetail();
		DeptCharDetail deptCharDetailOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(deptCharDetail, deptCharDetailOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		deptCharDetail.setDeptchardetailid(null); 
		return deptCharDetail;	
	}
	
	/**
	 * 删除  
	 *   
	 * @param deptCharDetail
	 */
	public void _delete(DeptCharDetail deptCharDetail)
	{
		if (null != advanceService)
			advanceService.preDelete(deptCharDetail);
	
 		LockService.isBoInstanceLocked(deptCharDetail,DeptCharDetail.class);
		deptCharDetailHibernateDao.remove(deptCharDetail);

		if (null != advanceService)
			advanceService.postDelete(deptCharDetail);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param deptCharDetailId
	 */
	public void _delete(String deptCharDetailId,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(deptCharDetailId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("deptchardetailid"));
		DeptCharDetail deptCharDetail = this.deptCharDetailHibernateDao.load(deptCharDetailId);
		_delete(deptCharDetail);
	}
	
	/**
	 * 删除  
	 *   
	 * @param Set<DeptCharDetail> deptCharDetails
	 */
	public void _deletes(Set<DeptCharDetail> deptCharDetails,BusinessObject businessObject)
	{
		if (null == deptCharDetails || deptCharDetails.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<DeptCharDetail> it = deptCharDetails.iterator();
		while (it.hasNext())
		{
			DeptCharDetail deptCharDetail = (DeptCharDetail) it.next();
			_delete(deptCharDetail);
		}
	}
	
	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param deptCharDetailIds
	 */
	public void _deletes(String deptCharDetailIds,BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(deptCharDetailIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("deptchardetailid"));
		String[] ids = StringUtils.splitString(deptCharDetailIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i],businessObject);
		}
	}
	/**
	 * 提交工作流
	 * 
	 * @param deptCharDetail
	 */
	public void _submitProcess(DeptCharDetail deptCharDetail
	,BusinessObject businessObject)
	{
		String id = deptCharDetail.getDeptchardetailid();
		if (StringUtils.isNullBlank(id))
		{
			_save(deptCharDetail);
		}
		else
		{
			_update(deptCharDetail
			, businessObject);
		}
		String taskId = deptCharDetail.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(deptCharDetail, id);
		else
			WorkflowService.signalProcessInstance(deptCharDetail, id, null);
	}

	/**
	 * 保存或更新费用明细
	 * 保存  
	 *  
	 * @param deptCharDetail
	 */
	public void _update(DeptCharDetail deptCharDetail
,BusinessObject businessObject	
	)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(deptCharDetail);
		deptCharDetailHibernateDao.saveOrUpdate(deptCharDetail);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(deptCharDetail);
	}
	
	/**
	 * 保存  
	 *   
	 * @param deptCharDetail
	 */
	public void _save(DeptCharDetail deptCharDetail)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(deptCharDetail);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		deptCharDetail.setDeptchardetailid(null);
                                                  		deptCharDetailHibernateDao.saveOrUpdate(deptCharDetail);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(deptCharDetail);
	}
	
	/**
	 *
	 * 保存  
	 *   
	 * @param deptCharDetail
	 */
	public void _saveOrUpdate(DeptCharDetail deptCharDetail
,BusinessObject businessObject		
	)
	{
		if (StringUtils.isNullBlank(deptCharDetail.getDeptchardetailid()))
		{	
			_save(deptCharDetail);
		}
		else
		{
			_update(deptCharDetail
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