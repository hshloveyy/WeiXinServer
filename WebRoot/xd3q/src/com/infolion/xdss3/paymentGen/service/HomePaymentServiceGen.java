/*
 * @(#)HomePaymentServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月25日 09点58分37秒
 *  描　述：创建
 */
package com.infolion.xdss3.paymentGen.service;

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
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.payment.dao.HomePaymentHibernateDao;
import com.infolion.xdss3.payment.domain.HomeDocuBankItem;
import com.infolion.xdss3.payment.domain.HomeFundFlow;
import com.infolion.xdss3.payment.domain.HomePayBankItem;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.HomePaymentCbill;
import com.infolion.xdss3.payment.domain.HomePaymentItem;
import com.infolion.xdss3.payment.domain.HomePaymentRelat;
import com.infolion.xdss3.payment.domain.HomeSettSubj;
import com.infolion.xdss3.payment.service.HomeDocuBankItemService;
import com.infolion.xdss3.payment.service.HomeFundFlowService;
import com.infolion.xdss3.payment.service.HomePayBankItemService;
import com.infolion.xdss3.payment.service.HomePaymentCbillService;
import com.infolion.xdss3.payment.service.HomePaymentItemService;
import com.infolion.xdss3.payment.service.HomePaymentRelatService;
import com.infolion.xdss3.payment.service.HomeSettSubjService;

/**
 * <pre>
 * 内贸付款(HomePayment)服务类
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
public class HomePaymentServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected HomePaymentHibernateDao homePaymentHibernateDao;

	public void setHomePaymentHibernateDao(HomePaymentHibernateDao homePaymentHibernateDao)
	{
		this.homePaymentHibernateDao = homePaymentHibernateDao;
	}

	/**
	 * 注入扩展服务类
	 */
	@Autowired(required = false)
	public void setAdvanceService(@Qualifier("homePaymentAdvanceService") AdvanceService advanceService)
	{
		super.setAdvanceService(advanceService);
	}

	@Autowired
	protected HomePaymentItemService homePaymentItemService;

	public void setHomePaymentItemService(HomePaymentItemService homePaymentItemService)
	{
		this.homePaymentItemService = homePaymentItemService;
	}

	@Autowired
	protected HomePaymentCbillService homePaymentCbillService;

	public void setHomePaymentCbillService(HomePaymentCbillService homePaymentCbillService)
	{
		this.homePaymentCbillService = homePaymentCbillService;
	}

	@Autowired
	protected HomePayBankItemService homePayBankItemService;

	public void setHomePayBankItemService(HomePayBankItemService homePayBankItemService)
	{
		this.homePayBankItemService = homePayBankItemService;
	}

	@Autowired
	protected HomeDocuBankItemService homeDocuBankItemService;

	public void setHomeDocuBankItemService(HomeDocuBankItemService homeDocuBankItemService)
	{
		this.homeDocuBankItemService = homeDocuBankItemService;
	}

	@Autowired
	protected HomeSettSubjService homeSettSubjService;

	public void setHomeSettSubjService(HomeSettSubjService homeSettSubjService)
	{
		this.homeSettSubjService = homeSettSubjService;
	}

	@Autowired
	protected HomeFundFlowService homeFundFlowService;

	public void setHomeFundFlowService(HomeFundFlowService homeFundFlowService)
	{
		this.homeFundFlowService = homeFundFlowService;
	}

	@Autowired
	protected HomePaymentRelatService homePaymentRelatService;

	public void setHomePaymentRelatService(HomePaymentRelatService homePaymentRelatService)
	{
		this.homePaymentRelatService = homePaymentRelatService;
	}

	/**
	 * 根据主键ID,取得内贸付款实例
	 * 
	 * @param id
	 * @return
	 */
	public HomePayment _getDetached(String id)
	{
		HomePayment homePayment = new HomePayment();
		if (StringUtils.isNotBlank(id))
		{
			homePayment = homePaymentHibernateDao.getDetached(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(homePayment);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		homePayment.setOperationType(operationType);

		return homePayment;
	}

	/**
	 * 根据主键ID,取得内贸付款实例
	 * 
	 * @param id
	 * @return
	 */
	public HomePayment _get(String id)
	{
		HomePayment homePayment = new HomePayment();
		if (StringUtils.isNotBlank(id))
		{
			homePayment = homePaymentHibernateDao.get(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(homePayment);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		homePayment.setOperationType(operationType);

		return homePayment;
	}

	/**
	 * 根据主键ID,取得内贸付款实例
	 * 
	 * @param id
	 * @return
	 */
	public HomePayment _getForEdit(String id)
	{
		HomePayment homePayment = _get(id);
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = homePayment.getOperationType();
		if (OperationType.READONLY.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
		}

		return homePayment;
	}

	/**
	 * 根据主键ID,取得内贸付款实例副本
	 * 
	 * @param id
	 * @return
	 */
	public HomePayment _getEntityCopy(String id)
	{
		HomePayment homePayment = new HomePayment();
		HomePayment homePaymentOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(homePayment, homePaymentOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		homePayment.setPaymentno(null);
		// homePayment.setPaymentid(null);
		homePayment.setProcessstate(" ");
		return homePayment;
	}

	/**
	 * 删除
	 * 
	 * @param homePayment
	 */
	public void _delete(HomePayment homePayment)
	{
		if (null != advanceService)
			advanceService.preDelete(homePayment);

		// 流程状态
		String processState = homePayment.getProcessstate();
		if (!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
		LockService.isBoInstanceLocked(homePayment, HomePayment.class);
		homePaymentHibernateDao.remove(homePayment);

		if (null != advanceService)
			advanceService.postDelete(homePayment);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param homePaymentId
	 */
	public void _delete(String homePaymentId, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePaymentId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentid"));
		HomePayment homePayment = this.homePaymentHibernateDao.load(homePaymentId);
		_delete(homePayment);
	}

	/**
	 * 删除
	 * 
	 * @param Set
	 *            <HomePayment> homePayments
	 */
	public void _deletes(Set<HomePayment> homePayments, BusinessObject businessObject)
	{
		if (null == homePayments || homePayments.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<HomePayment> it = homePayments.iterator();
		while (it.hasNext())
		{
			HomePayment homePayment = (HomePayment) it.next();
			_delete(homePayment);
		}
	}

	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param homePaymentIds
	 */
	public void _deletes(String homePaymentIds, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePaymentIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("paymentid"));
		String[] ids = StringUtils.splitString(homePaymentIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i], businessObject);
		}
	}

	/**
	 * 提交工作流
	 * 
	 * @param homePayment
	 */
	public void _submitProcess(HomePayment homePayment, Set<HomePaymentItem> deletedHomePaymentItemSet, Set<HomePaymentCbill> deletedHomePaymentCbillSet, Set<HomePayBankItem> deletedHomePayBankItemSet, Set<HomeDocuBankItem> deletedHomeDocuBankItemSet, Set<HomePaymentRelat> deletedHomePaymentRelatSet, BusinessObject businessObject)
	{
		String id = homePayment.getPaymentid();
		if("[object Object]".equals(id)){
			throw new BusinessException("业务ID错误，请联系管理员！");
		}
		/**
		 * if (StringUtils.isNullBlank(id)) { _save(homePayment); } else {
		 * _update(homePayment ,deletedHomePaymentItemSet
		 * ,deletedHomePaymentCbillSet ,deletedHomePayBankItemSet
		 * ,deletedHomeDocuBankItemSet ,deletedHomePaymentRelatSet ,
		 * businessObject); }
		 **/

		String taskId = homePayment.getWorkflowTaskId();
		//id = homePayment.getPaymentid();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(homePayment, id);
		else
			WorkflowService.signalProcessInstance(homePayment, id, null);
	}

	/**
	 * 保存或更新内贸付款 保存
	 * 
	 * @param homePayment
	 */
	public void _update(HomePayment homePayment, Set<HomePaymentItem> deletedHomePaymentItemSet, Set<HomePaymentCbill> deletedHomePaymentCbillSet, Set<HomePayBankItem> deletedHomePayBankItemSet, Set<HomeDocuBankItem> deletedHomeDocuBankItemSet, Set<HomePaymentRelat> deletedHomePaymentRelatSet, BusinessObject businessObject)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homePayment);
		homePaymentHibernateDao.saveOrUpdate(homePayment);
		// 删除关联子业务对象数据
		if (deletedHomePaymentItemSet != null && deletedHomePaymentItemSet.size() > 0)
		{
			homePaymentItemService._deletes(deletedHomePaymentItemSet, businessObject);
		}
		// 删除关联子业务对象数据
		if (deletedHomePaymentCbillSet != null && deletedHomePaymentCbillSet.size() > 0)
		{
			homePaymentCbillService._deletes(deletedHomePaymentCbillSet, businessObject);
		}
		// 删除关联子业务对象数据
		if (deletedHomePayBankItemSet != null && deletedHomePayBankItemSet.size() > 0)
		{
			homePayBankItemService._deletes(deletedHomePayBankItemSet, businessObject);
		}
		// 删除关联子业务对象数据
		if (deletedHomeDocuBankItemSet != null && deletedHomeDocuBankItemSet.size() > 0)
		{
			homeDocuBankItemService._deletes(deletedHomeDocuBankItemSet, businessObject);
		}
		// 删除关联子业务对象数据
		if (deletedHomePaymentRelatSet != null && deletedHomePaymentRelatSet.size() > 0)
		{
			homePaymentRelatService._deletes(deletedHomePaymentRelatSet, businessObject);
		} // 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homePayment);
	}

	/**
	 * 保存
	 * 
	 * @param homePayment
	 */
	public void _save(HomePayment homePayment)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(homePayment);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		homePayment.setPaymentid(null);

		Set<HomePaymentItem> homePaymentItemSet = homePayment.getHomePaymentItem();
		Set<HomePaymentItem> newHomePaymentItemSet = null;
		if (null != homePaymentItemSet)
		{
			newHomePaymentItemSet = new HashSet();
			Iterator<HomePaymentItem> itHomePaymentItem = homePaymentItemSet.iterator();
			while (itHomePaymentItem.hasNext())
			{
				HomePaymentItem homePaymentItem = (HomePaymentItem) itHomePaymentItem.next();
				homePaymentItem.setPaymentitemid(null);
				newHomePaymentItemSet.add(homePaymentItem);
			}
		}
		homePayment.setHomePaymentItem(newHomePaymentItemSet);

		Set<HomePaymentCbill> homePaymentCbillSet = homePayment.getHomePaymentCbill();
		Set<HomePaymentCbill> newHomePaymentCbillSet = null;
		if (null != homePaymentCbillSet)
		{
			newHomePaymentCbillSet = new HashSet();
			Iterator<HomePaymentCbill> itHomePaymentCbill = homePaymentCbillSet.iterator();
			while (itHomePaymentCbill.hasNext())
			{
				HomePaymentCbill homePaymentCbill = (HomePaymentCbill) itHomePaymentCbill.next();
				homePaymentCbill.setPaymentcbillid(null);
				newHomePaymentCbillSet.add(homePaymentCbill);
			}
		}
		homePayment.setHomePaymentCbill(newHomePaymentCbillSet);

		Set<HomePayBankItem> homePayBankItemSet = homePayment.getHomePayBankItem();
		Set<HomePayBankItem> newHomePayBankItemSet = null;
		if (null != homePayBankItemSet)
		{
			newHomePayBankItemSet = new HashSet();
			Iterator<HomePayBankItem> itHomePayBankItem = homePayBankItemSet.iterator();
			while (itHomePayBankItem.hasNext())
			{
				HomePayBankItem homePayBankItem = (HomePayBankItem) itHomePayBankItem.next();
				homePayBankItem.setPaybankitemid(null);
				newHomePayBankItemSet.add(homePayBankItem);
			}
		}
		homePayment.setHomePayBankItem(newHomePayBankItemSet);

		Set<HomeDocuBankItem> homeDocuBankItemSet = homePayment.getHomeDocuBankItem();
		Set<HomeDocuBankItem> newHomeDocuBankItemSet = null;
		if (null != homeDocuBankItemSet)
		{
			newHomeDocuBankItemSet = new HashSet();
			Iterator<HomeDocuBankItem> itHomeDocuBankItem = homeDocuBankItemSet.iterator();
			while (itHomeDocuBankItem.hasNext())
			{
				HomeDocuBankItem homeDocuBankItem = (HomeDocuBankItem) itHomeDocuBankItem.next();
				homeDocuBankItem.setDocuaryitemid(null);
				newHomeDocuBankItemSet.add(homeDocuBankItem);
			}
		}
		homePayment.setHomeDocuBankItem(newHomeDocuBankItemSet);

		HomeSettSubj homeSettSubj = homePayment.getHomeSettSubj();
		if (null != homeSettSubj)
		{
			homeSettSubj.setSettlesubjectid(null);
		}
		homePayment.setHomeSettSubj(homeSettSubj);

		HomeFundFlow homeFundFlow = homePayment.getHomeFundFlow();
		if (null != homeFundFlow)
		{
			homeFundFlow.setFundflowid(null);
		}
		homePayment.setHomeFundFlow(homeFundFlow);

		Set<HomePaymentRelat> homePaymentRelatSet = homePayment.getHomePaymentRelat();
		Set<HomePaymentRelat> newHomePaymentRelatSet = null;
		if (null != homePaymentRelatSet)
		{
			newHomePaymentRelatSet = new HashSet();
			Iterator<HomePaymentRelat> itHomePaymentRelat = homePaymentRelatSet.iterator();
			while (itHomePaymentRelat.hasNext())
			{
				HomePaymentRelat homePaymentRelat = (HomePaymentRelat) itHomePaymentRelat.next();
				homePaymentRelat.setRelatedpaymentid(null);
				newHomePaymentRelatSet.add(homePaymentRelat);
			}
		}
		homePayment.setHomePaymentRelat(newHomePaymentRelatSet);
		homePaymentHibernateDao.saveOrUpdate(homePayment);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(homePayment);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param homePayment
	 */
	public void _saveOrUpdate(HomePayment homePayment, Set<HomePaymentItem> deletedHomePaymentItemSet, Set<HomePaymentCbill> deletedHomePaymentCbillSet, Set<HomePayBankItem> deletedHomePayBankItemSet, Set<HomeDocuBankItem> deletedHomeDocuBankItemSet, Set<HomePaymentRelat> deletedHomePaymentRelatSet, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(homePayment.getPaymentno()))
		{
			String paymentno = NumberService.getNextObjectNumber("importpaymentno", homePayment);
			homePayment.setPaymentno(paymentno);
		}
		if (StringUtils.isNullBlank(homePayment.getPaymentid()))
		{
			_save(homePayment);
		}
		else
		{
			_update(homePayment, deletedHomePaymentItemSet, deletedHomePaymentCbillSet, deletedHomePayBankItemSet, deletedHomeDocuBankItemSet, deletedHomePaymentRelatSet, businessObject);
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