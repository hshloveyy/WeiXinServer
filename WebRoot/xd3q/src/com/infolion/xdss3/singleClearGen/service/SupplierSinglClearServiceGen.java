/*
 * @(#)SupplierSinglClearServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月09日 10点02分15秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearGen.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.service.FundFlowService;
import com.infolion.xdss3.financeSquare.service.SettleSubjectService;
import com.infolion.xdss3.singleClear.dao.SupplierSinglClearHibernateDao;
import com.infolion.xdss3.singleClear.domain.SupplierSinglClear;
import com.infolion.xdss3.singleClear.domain.UnClearPayment;
import com.infolion.xdss3.singleClear.domain.UnClearSupplieBill;
import com.infolion.xdss3.singleClear.service.UnClearPaymentService;
import com.infolion.xdss3.singleClear.service.UnClearSupplieBillService;

/**
 * <pre>
 * 供应商单清帐(SupplierSinglClear)服务类
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
public class SupplierSinglClearServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected SupplierSinglClearHibernateDao supplierSinglClearHibernateDao;

	public void setSupplierSinglClearHibernateDao(SupplierSinglClearHibernateDao supplierSinglClearHibernateDao)
	{
		this.supplierSinglClearHibernateDao = supplierSinglClearHibernateDao;
	}

	/**
	 * 注入扩展服务类
	 */
	@Autowired(required = false)
	public void setAdvanceService(@Qualifier("supplierSinglClearAdvanceService") AdvanceService advanceService)
	{
		super.setAdvanceService(advanceService);
	}

	@Autowired
	protected UnClearSupplieBillService unClearSupplieBillService;

	public void setUnClearSupplieBillService(UnClearSupplieBillService unClearSupplieBillService)
	{
		this.unClearSupplieBillService = unClearSupplieBillService;
	}

	@Autowired
	protected UnClearPaymentService unClearPaymentService;

	public void setUnClearPaymentService(UnClearPaymentService unClearPaymentService)
	{
		this.unClearPaymentService = unClearPaymentService;
	}

	@Autowired
	protected FundFlowService fundFlowService;

	public void setFundFlowService(FundFlowService fundFlowService)
	{
		this.fundFlowService = fundFlowService;
	}

	@Autowired
	protected SettleSubjectService settleSubjectService;

	public void setSettleSubjectService(SettleSubjectService settleSubjectService)
	{
		this.settleSubjectService = settleSubjectService;
	}

	/**
	 * 根据主键ID,取得供应商单清帐实例
	 * 
	 * @param id
	 * @return
	 */
	public SupplierSinglClear _getDetached(String id)
	{
		SupplierSinglClear supplierSinglClear = new SupplierSinglClear();
		if (StringUtils.isNotBlank(id))
		{
			supplierSinglClear = supplierSinglClearHibernateDao.getDetached(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(supplierSinglClear);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		supplierSinglClear.setOperationType(operationType);

		return supplierSinglClear;
	}

	/**
	 * 根据主键ID,取得供应商单清帐实例
	 * 
	 * @param id
	 * @return
	 */
	public SupplierSinglClear _get(String id)
	{
		SupplierSinglClear supplierSinglClear = new SupplierSinglClear();
		if (StringUtils.isNotBlank(id))
		{
			supplierSinglClear = supplierSinglClearHibernateDao.get(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(supplierSinglClear);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		supplierSinglClear.setOperationType(operationType);

		return supplierSinglClear;
	}

	/**
	 * 根据主键ID,取得供应商单清帐实例
	 * 
	 * @param id
	 * @return
	 */
	public SupplierSinglClear _getForEdit(String id)
	{
		SupplierSinglClear supplierSinglClear = _get(id);
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = supplierSinglClear.getOperationType();
		if (OperationType.READONLY.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
		}

		return supplierSinglClear;
	}

	/**
	 * 根据主键ID,取得供应商单清帐实例副本
	 * 
	 * @param id
	 * @return
	 */
	public SupplierSinglClear _getEntityCopy(String id)
	{
		SupplierSinglClear supplierSinglClear = new SupplierSinglClear();
		SupplierSinglClear supplierSinglClearOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(supplierSinglClear, supplierSinglClearOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		// supplierSinglClear.setSuppliersclearid(null);
		supplierSinglClear.setProcessstate(" ");
		return supplierSinglClear;
	}

	/**
	 * 删除
	 * 
	 * @param supplierSinglClear
	 */
	public void _delete(SupplierSinglClear supplierSinglClear)
	{
		if (null != advanceService)
			advanceService.preDelete(supplierSinglClear);

		// 流程状态
		String processState = supplierSinglClear.getProcessstate();
		if (!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
		LockService.isBoInstanceLocked(supplierSinglClear, SupplierSinglClear.class);
		supplierSinglClearHibernateDao.remove(supplierSinglClear);

		if (null != advanceService)
			advanceService.postDelete(supplierSinglClear);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param supplierSinglClearId
	 */
	public void _delete(String supplierSinglClearId, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplierSinglClearId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("suppliersclearid"));
		SupplierSinglClear supplierSinglClear = this.supplierSinglClearHibernateDao.load(supplierSinglClearId);
		_delete(supplierSinglClear);
	}

	/**
	 * 删除
	 * 
	 * @param Set
	 *            <SupplierSinglClear> supplierSinglClears
	 */
	public void _deletes(Set<SupplierSinglClear> supplierSinglClears, BusinessObject businessObject)
	{
		if (null == supplierSinglClears || supplierSinglClears.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<SupplierSinglClear> it = supplierSinglClears.iterator();
		while (it.hasNext())
		{
			SupplierSinglClear supplierSinglClear = (SupplierSinglClear) it.next();
			_delete(supplierSinglClear);
		}
	}

	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param supplierSinglClearIds
	 */
	public void _deletes(String supplierSinglClearIds, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplierSinglClearIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("suppliersclearid"));
		String[] ids = StringUtils.splitString(supplierSinglClearIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i], businessObject);
		}
	}

	/**
	 * 提交工作流
	 * 
	 * @param supplierSinglClear
	 */
	public void _submitProcess(SupplierSinglClear supplierSinglClear, Set<UnClearSupplieBill> deletedUnClearSupplieBillSet, Set<UnClearPayment> deletedUnClearPaymentSet, BusinessObject businessObject)
	{
		String id = supplierSinglClear.getSuppliersclearid();
		/**
		 * if (StringUtils.isNullBlank(id)) { _save(supplierSinglClear); } else
		 * { _update(supplierSinglClear ,deletedUnClearSupplieBillSet
		 * ,deletedUnClearPaymentSet , businessObject); }
		 **/

		String taskId = supplierSinglClear.getWorkflowTaskId();
		id = supplierSinglClear.getSuppliersclearid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(supplierSinglClear, id);
		else
			WorkflowService.signalProcessInstance(supplierSinglClear, id, null);
	}

	/**
	 * 保存或更新供应商单清帐 保存
	 * 
	 * @param supplierSinglClear
	 */
	public void _update(SupplierSinglClear supplierSinglClear, Set<UnClearSupplieBill> deletedUnClearSupplieBillSet, Set<UnClearPayment> deletedUnClearPaymentSet, BusinessObject businessObject)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplierSinglClear);
		supplierSinglClearHibernateDao.saveOrUpdate(supplierSinglClear);
		// 删除关联子业务对象数据
		if (deletedUnClearSupplieBillSet != null && deletedUnClearSupplieBillSet.size() > 0)
		{
			unClearSupplieBillService._deletes(deletedUnClearSupplieBillSet, businessObject);
		}
		// 删除关联子业务对象数据
		if (deletedUnClearPaymentSet != null && deletedUnClearPaymentSet.size() > 0)
		{
			unClearPaymentService._deletes(deletedUnClearPaymentSet, businessObject);
		} // 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplierSinglClear);
	}

	/**
	 * 保存
	 * 
	 * @param supplierSinglClear
	 */
	public void _save(SupplierSinglClear supplierSinglClear)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(supplierSinglClear);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		supplierSinglClear.setSuppliersclearid(null);

		Set<UnClearSupplieBill> unClearSupplieBillSet = supplierSinglClear.getUnClearSupplieBill();
		Set<UnClearSupplieBill> newUnClearSupplieBillSet = null;
		if (null != unClearSupplieBillSet)
		{
			newUnClearSupplieBillSet = new HashSet();
			Iterator<UnClearSupplieBill> itUnClearSupplieBill = unClearSupplieBillSet.iterator();
			while (itUnClearSupplieBill.hasNext())
			{
				UnClearSupplieBill unClearSupplieBill = (UnClearSupplieBill) itUnClearSupplieBill.next();
				unClearSupplieBill.setUnclearsbillid(null);
				newUnClearSupplieBillSet.add(unClearSupplieBill);
			}
		}
		supplierSinglClear.setUnClearSupplieBill(newUnClearSupplieBillSet);

		Set<UnClearPayment> unClearPaymentSet = supplierSinglClear.getUnClearPayment();
		Set<UnClearPayment> newUnClearPaymentSet = null;
		if (null != unClearPaymentSet)
		{
			newUnClearPaymentSet = new HashSet();
			Iterator<UnClearPayment> itUnClearPayment = unClearPaymentSet.iterator();
			while (itUnClearPayment.hasNext())
			{
				UnClearPayment unClearPayment = (UnClearPayment) itUnClearPayment.next();
				unClearPayment.setUnclearpaymentid(null);
				newUnClearPaymentSet.add(unClearPayment);
			}
		}
		supplierSinglClear.setUnClearPayment(newUnClearPaymentSet);

		FundFlow fundFlow = supplierSinglClear.getFundFlow();
		if (null != fundFlow)
		{
			fundFlow.setFundflowid(null);
		}
		supplierSinglClear.setFundFlow(fundFlow);

		SettleSubject settleSubject = supplierSinglClear.getSettleSubject();
		if (null != settleSubject)
		{
			settleSubject.setSettlesubjectid(null);
		}
		supplierSinglClear.setSettleSubject(settleSubject);
		supplierSinglClearHibernateDao.saveOrUpdate(supplierSinglClear);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(supplierSinglClear);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param supplierSinglClear
	 */
	public void _saveOrUpdate(SupplierSinglClear supplierSinglClear, Set<UnClearSupplieBill> deletedUnClearSupplieBillSet, Set<UnClearPayment> deletedUnClearPaymentSet, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(supplierSinglClear.getSuppliersclearid()))
		{
			_save(supplierSinglClear);
		}
		else
		{
			_update(supplierSinglClear, deletedUnClearSupplieBillSet, deletedUnClearPaymentSet, businessObject);
		}
	}

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