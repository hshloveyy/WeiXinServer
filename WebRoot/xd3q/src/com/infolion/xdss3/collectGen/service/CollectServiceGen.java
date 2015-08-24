/*
 * @(#)CollectServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年08月10日 02点20分22秒
 *  描　述：创建
 */
package com.infolion.xdss3.collectGen.service;

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
import com.infolion.xdss3.collect.dao.CollectHibernateDao;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectbankitem.domain.CollectBankItem;
import com.infolion.xdss3.collectbankitem.service.CollectBankItemService;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectcbill.service.CollectCbillService;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.collectrelated.domain.CollectRelated;
import com.infolion.xdss3.collectrelated.service.CollectRelatedService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.financeSquare.service.FundFlowService;
import com.infolion.xdss3.financeSquare.service.SettleSubjectService;

/**
 * <pre>
 * 收款(Collect)服务类
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
public class CollectServiceGen extends BaseService
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	protected CollectHibernateDao collectHibernateDao;

	public void setCollectHibernateDao(CollectHibernateDao collectHibernateDao)
	{
		this.collectHibernateDao = collectHibernateDao;
	}

	/**
	 * 注入扩展服务类
	 */
	@Autowired(required = false)
	public void setAdvanceService(@Qualifier("collectAdvanceService") AdvanceService advanceService)
	{
		super.setAdvanceService(advanceService);
	}

	@Autowired
	protected CollectItemService collectItemService;

	public void setCollectItemService(CollectItemService collectItemService)
	{
		this.collectItemService = collectItemService;
	}

	@Autowired
	protected CollectCbillService collectCbillService;

	public void setCollectCbillService(CollectCbillService collectCbillService)
	{
		this.collectCbillService = collectCbillService;
	}

	@Autowired
	protected CollectRelatedService collectRelatedService;

	public void setCollectRelatedService(CollectRelatedService collectRelatedService)
	{
		this.collectRelatedService = collectRelatedService;
	}

	@Autowired
	protected SettleSubjectService settleSubjectService;

	public void setSettleSubjectService(SettleSubjectService settleSubjectService)
	{
		this.settleSubjectService = settleSubjectService;
	}

	@Autowired
	protected FundFlowService fundFlowService;

	public void setFundFlowService(FundFlowService fundFlowService)
	{
		this.fundFlowService = fundFlowService;
	}

	@Autowired
	protected CollectBankItemService collectBankItemService;

	public void setCollectBankItemService(CollectBankItemService collectBankItemService)
	{
		this.collectBankItemService = collectBankItemService;
	}

	/**
	 * 根据主键ID,取得收款实例
	 * 
	 * @param id
	 * @return
	 */
	public Collect _getDetached(String id)
	{
		Collect collect = new Collect();
		if (StringUtils.isNotBlank(id))
		{
			collect = collectHibernateDao.getDetached(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(collect);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		collect.setOperationType(operationType);

		return collect;
	}

	/**
	 * 根据主键ID,取得收款实例
	 * 
	 * @param id
	 * @return
	 */
	public Collect _get(String id)
	{
		Collect collect = new Collect();
		if (StringUtils.isNotBlank(id))
		{
			collect = collectHibernateDao.get(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(collect);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		collect.setOperationType(operationType);

		return collect;
	}

	/**
	 * 根据主键ID,取得收款实例
	 * 
	 * @param id
	 * @return
	 */
	public Collect _getForEdit(String id)
	{
		Collect collect = _get(id);
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = collect.getOperationType();
		if (OperationType.READONLY.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
		}

		return collect;
	}

	/**
	 * 根据主键ID,取得收款实例副本
	 * 
	 * @param id
	 * @return
	 */
	public Collect _getEntityCopy(String id)
	{
		Collect collect = new Collect();
		Collect collectOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(collect, collectOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		collect.setCollectno(null);
		// collect.setCollectid(null);
		collect.setProcessstate(" ");
		return collect;
	}

	/**
	 * 删除
	 * 
	 * @param collect
	 */
	public void _delete(Collect collect)
	{
		if (null != advanceService)
			advanceService.preDelete(collect);

		// 流程状态
		String processState = collect.getProcessstate();
		if (!StringUtils.isNullBlank(processState))
		{
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.CannotDeleteApproveData);
		}
		LockService.isBoInstanceLocked(collect, Collect.class);
		collectHibernateDao.remove(collect);

		if (null != advanceService)
			advanceService.postDelete(collect);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param collectId
	 */
	public void _delete(String collectId, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(collectId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("collectid"));
		Collect collect = this.collectHibernateDao.load(collectId);
		_delete(collect);
	}

	/**
	 * 删除
	 * 
	 * @param Set
	 *            <Collect> collects
	 */
	public void _deletes(Set<Collect> collects, BusinessObject businessObject)
	{
		if (null == collects || collects.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<Collect> it = collects.iterator();
		while (it.hasNext())
		{
			Collect collect = (Collect) it.next();
			_delete(collect);
		}
	}

	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param collectIds
	 */
	public void _deletes(String collectIds, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(collectIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("collectid"));
		String[] ids = StringUtils.splitString(collectIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i], businessObject);
		}
	}

	/**
	 * 提交工作流
	 * 
	 * @param collect
	 */
	public void _submitProcess(Collect collect, Set<CollectItem> deletedCollectitemSet, Set<CollectCbill> deletedCollectcbillSet, Set<CollectRelated> deletedCollectrelatedSet, Set<CollectBankItem> deletedCollectbankitemSet, BusinessObject businessObject)
	{
		String id = collect.getCollectid();
		/**
		 * if (StringUtils.isNullBlank(id)) { _save(collect); } else {
		 * _update(collect ,deletedCollectitemSet ,deletedCollectcbillSet
		 * ,deletedCollectrelatedSet ,deletedCollectbankitemSet ,
		 * businessObject); }
		 **/

		String taskId = collect.getWorkflowTaskId();
		id = collect.getCollectid();

		// TODO LJX 20100901 DEBUG
		log.debug("提交工作流_submitProcess:" + collect.getCollectbankitem() != null ? collect.getCollectbankitem().size() : "0");

		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(collect, id);
		else
			WorkflowService.signalProcessInstance(collect, id, null);
	}

	/**
	 * 保存或更新收款 保存
	 * 
	 * @param collect
	 */
	public void _update(Collect collect, Set<CollectItem> deletedCollectItemSet, Set<CollectCbill> deletedCollectCbillSet, Set<CollectRelated> deletedCollectRelatedSet, Set<CollectBankItem> deletedCollectBankItemSet, BusinessObject businessObject)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(collect);
		collectHibernateDao.saveOrUpdate(collect);

		if (collect.getSettleSubject() == null)
		{
			collectHibernateDao.deleteById("YSETTLESUBJECT", "COLLECTID", collect.getCollectid());
		}
		if (collect.getFundFlow() == null)
		{
			collectHibernateDao.deleteById("YFUNDFLOW", "COLLECTID", collect.getCollectid());
		}

		// 删除关联子业务对象数据
		if (deletedCollectItemSet != null && deletedCollectItemSet.size() > 0)
		{
			collectItemService._deletes(deletedCollectItemSet, businessObject);
		}
		// 删除关联子业务对象数据
		if (deletedCollectCbillSet != null && deletedCollectCbillSet.size() > 0)
		{
			collectCbillService._deletes(deletedCollectCbillSet, businessObject);
		}
		// 删除关联子业务对象数据
		if (deletedCollectRelatedSet != null && deletedCollectRelatedSet.size() > 0)
		{
			collectRelatedService._deletes(deletedCollectRelatedSet, businessObject);
		}
		// 删除关联子业务对象数据
		if (deletedCollectBankItemSet != null && deletedCollectBankItemSet.size() > 0)
		{
			collectBankItemService._deletes(deletedCollectBankItemSet, businessObject);
		} // 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(collect);
	}

	/**
	 * 保存
	 * 
	 * @param collect
	 */
	public void _save(Collect collect)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(collect);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		collect.setCollectid(null);

		Set<CollectItem> collectitemSet = collect.getCollectitem();
		Set<CollectItem> newCollectItemSet = null;
		if (null != collectitemSet)
		{
			newCollectItemSet = new HashSet();
			Iterator<CollectItem> itCollectItem = collectitemSet.iterator();
			while (itCollectItem.hasNext())
			{
				CollectItem collectitem = (CollectItem) itCollectItem.next();
				collectitem.setCollectitemid(null);
				newCollectItemSet.add(collectitem);
			}
		}
		collect.setCollectitem(newCollectItemSet);

		Set<CollectCbill> collectcbillSet = collect.getCollectcbill();
		Set<CollectCbill> newCollectCbillSet = null;
		if (null != collectcbillSet)
		{
			newCollectCbillSet = new HashSet();
			Iterator<CollectCbill> itCollectCbill = collectcbillSet.iterator();
			while (itCollectCbill.hasNext())
			{
				CollectCbill collectcbill = (CollectCbill) itCollectCbill.next();
				collectcbill.setCollectcbillid(null);
				newCollectCbillSet.add(collectcbill);
			}
		}
		collect.setCollectcbill(newCollectCbillSet);

		Set<CollectRelated> collectrelatedSet = collect.getCollectrelated();
		Set<CollectRelated> newCollectRelatedSet = null;
		if (null != collectrelatedSet)
		{
			newCollectRelatedSet = new HashSet();
			Iterator<CollectRelated> itCollectRelated = collectrelatedSet.iterator();
			while (itCollectRelated.hasNext())
			{
				CollectRelated collectrelated = (CollectRelated) itCollectRelated.next();
				collectrelated.setCollectrelatedid(null);
				newCollectRelatedSet.add(collectrelated);
			}
		}
		collect.setCollectrelated(newCollectRelatedSet);

		SettleSubject settleSubject = collect.getSettleSubject();
		if (null != settleSubject)
		{
			settleSubject.setSettlesubjectid(null);
		}
		collect.setSettleSubject(settleSubject);

		FundFlow fundFlow = collect.getFundFlow();
		if (null != fundFlow)
		{
			fundFlow.setFundflowid(null);
		}
		collect.setFundFlow(fundFlow);

		Set<CollectBankItem> collectbankitemSet = collect.getCollectbankitem();
		Set<CollectBankItem> newCollectBankItemSet = null;
		if (null != collectbankitemSet)
		{
			newCollectBankItemSet = new HashSet();
			Iterator<CollectBankItem> itCollectBankItem = collectbankitemSet.iterator();
			while (itCollectBankItem.hasNext())
			{
				CollectBankItem collectbankitem = (CollectBankItem) itCollectBankItem.next();
				collectbankitem.setColbankitemid(null);
				// TODO LJX 20100901 DEBUG
				log.debug("collectbankitem.getColbankitemid:" + collectbankitem.getColbankitemid());
				log.debug("collectbankitem.getColbanksubject:" + collectbankitem.getColbanksubject());
				log.debug("collectbankitem.getColbanksubject:" + collectbankitem.getColbanksubject());
				log.debug("collectbankitem.getCollectbankacc:" + collectbankitem.getCollectbankacc());
				log.debug("collectbankitem.getCollectbankid:" + collectbankitem.getCollectbankid());
				log.debug("collectbankitem.getClient:" + collectbankitem.getClient());
				log.debug("collectbankitem.getCashflowitem:" + collectbankitem.getCashflowitem());

				newCollectBankItemSet.add(collectbankitem);
			}
		}
		collect.setCollectbankitem(newCollectBankItemSet);
		collectHibernateDao.saveOrUpdate(collect);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(collect);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param collect
	 */
	public void _saveOrUpdate(Collect collect, Set<CollectItem> deletedCollectItemSet, Set<CollectCbill> deletedCollectCbillSet, Set<CollectRelated> deletedCollectRelatedSet, Set<CollectBankItem> deletedCollectBankItemSet, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(collect.getCollectno()))
		{
			String collectno = NumberService.getNextObjectNumber("CollectNo", collect);
			collect.setCollectno(collectno);
		}
		if (StringUtils.isNullBlank(collect.getCollectid()))
		{
			_save(collect);
		}
		else
		{
			_update(collect, deletedCollectItemSet, deletedCollectCbillSet, deletedCollectRelatedSet, deletedCollectBankItemSet, businessObject);
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