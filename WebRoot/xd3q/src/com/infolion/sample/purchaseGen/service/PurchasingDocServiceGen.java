/*
 * @(#)PurchasingDocServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2009年12月01日 19点57分05秒
 *  描　述：创建
 */
package com.infolion.sample.purchaseGen.service;

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
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.sample.purchase.dao.PurchasingDocHibernateDao;
import com.infolion.sample.purchase.domain.PurchasingDoc;
import com.infolion.sample.purchase.domain.PurchasingDocItem;
import com.infolion.sample.purchase.domain.PurchasingDocKey;
import com.infolion.sample.purchase.service.PurchasingDocItemService;
import com.infolion.sample.purchase.service.PurchasingDocService;


/**
 * <pre>
 * SAP采购凭证(PurchasingDoc)服务类
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
public class PurchasingDocServiceGen extends BaseService
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private PurchasingDocHibernateDao purchasingDocHibernateDao;

	public void setPurchasingDocHibernateDao(PurchasingDocHibernateDao purchasingDocHibernateDao)
	{
		this.purchasingDocHibernateDao = purchasingDocHibernateDao;
	}

	/**
	 * 注入扩展服务类
	 */
	@Autowired(required = false)
	public void setAdvanceService(@Qualifier("purchasingDocAdvanceService") AdvanceService advanceService)
	{
		super.setAdvanceService(advanceService);
	}

	@Autowired
	private PurchasingDocItemService purchasingDocItemService;

	public void setPurchasingDocItemService(PurchasingDocItemService purchasingDocItemService)
	{
		this.purchasingDocItemService = purchasingDocItemService;
	}

	/**
	 * 根据主键ID,取得SAP采购凭证实例
	 * 
	 * @param id
	 * @return
	 */
	public PurchasingDoc _get(PurchasingDocKey key)
	{
		PurchasingDoc purchasingDoc = new PurchasingDoc();
		if (StringUtils.isNotBlank(key.getClient()) || StringUtils.isNotBlank(key.getEbeln()))
		{
			purchasingDoc = purchasingDocHibernateDao.get(key);
		}
//		// 需要转移到service服务里，要不存在事务问题。
//		String operationType = LockService.lockBOData(purchasingDoc);
//		if (OperationType.UNVISIABLE.equals(operationType))
//		{
//			throw new LockException("记录已被锁定，您无权查看该记录！");
//		}
//		purchasingDoc.setOperationType(operationType);

		return purchasingDoc;
	}

	/**
	 * 根据主键ID,取得SAP采购凭证实例
	 * 
	 * @param id
	 * @return
	 */
	public PurchasingDoc _getForEdit(PurchasingDocKey key)
	{
		PurchasingDoc purchasingDoc = _get(key);
//		// 需要转移到service服务里，要不存在事务问题。
//		String operationType = purchasingDoc.getOperationType();
//		if (OperationType.READONLY.equals(operationType))
//		{
//			throw new LockException("记录已被锁定，您无权编辑该记录！");
//		}

		return purchasingDoc;
	}

	/**
	 * 根据主键ID,取得SAP采购凭证实例副本
	 * 
	 * @param id
	 * @return
	 */
	public PurchasingDoc _getEntityCopy(PurchasingDocKey key)
	{
		PurchasingDoc purchasingDoc = new PurchasingDoc();
		PurchasingDoc purchasingDocOld = this._get(key);
		try
		{
			BeanUtils.copyProperties(purchasingDoc, purchasingDocOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		purchasingDoc.setEbeln(null);

		return purchasingDoc;
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

	/**
	 * 删除
	 * 
	 * @param purchasingDoc
	 */
	public void _delete(PurchasingDoc purchasingDoc)
	{
		if (null != advanceService)
			advanceService.preDelete(purchasingDoc);

//		LockService.isBoInstanceLocked(purchasingDoc, PurchasingDoc.class);
		purchasingDocHibernateDao.remove(purchasingDoc);

		if (null != advanceService)
			advanceService.postDelete(purchasingDoc);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param purchasingDocId
	 */
	public void _delete(String purchasingDocId)
	{
		if (StringUtils.isNullBlank(purchasingDocId))
			throw new BusinessException("不存在需要删除的SAP采购凭证。");
		PurchasingDoc purchasingDoc = this.purchasingDocHibernateDao.load(purchasingDocId);
		_delete(purchasingDoc);
	}

	/**
	 * 删除
	 * 
	 * @param Set
	 *            <PurchasingDoc> purchasingDocs
	 */
	public void _deletes(Set<PurchasingDoc> purchasingDocs)
	{
		if (null == purchasingDocs || purchasingDocs.size() < 1)
			throw new BusinessException("不存在需要删除的SAP采购凭证。");
		Iterator<PurchasingDoc> it = purchasingDocs.iterator();
		while (it.hasNext())
		{
			PurchasingDoc purchasingDoc = (PurchasingDoc) it.next();
			_delete(purchasingDoc);
		}
	}

	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param purchasingDocIds
	 */
	public void _deletes(String purchasingDocIds)
	{
		if (StringUtils.isNullBlank(purchasingDocIds))
			throw new BusinessException("不存在需要删除的SAP采购凭证。");
		String[] ids = StringUtils.splitString(purchasingDocIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i]);
		}
	}

	/**
	 * 提交工作流
	 * 
	 * @param purchasingDoc
	 */
	public void _submitProcess(PurchasingDoc purchasingDoc, Set<PurchasingDocItem> deletedPurchasingDocItemsSet)
	{
		String id = purchasingDoc.getEbeln();
		if (StringUtils.isNullBlank(id))
		{
			_save(purchasingDoc);
		}
		else
		{
			_update(purchasingDoc, deletedPurchasingDocItemsSet);
		}
		String taskId = purchasingDoc.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(purchasingDoc, id);
		else
			WorkflowService.signalProcessInstance(purchasingDoc, id, null);
	}

	/**
	 * 保存或更新SAP采购凭证 保存
	 * 
	 * @param purchasingDoc
	 */
	public void _update(PurchasingDoc purchasingDoc, Set<PurchasingDocItem> deletedPurchasingDocItemSet)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(purchasingDoc);
		purchasingDocHibernateDao.saveOrUpdate(purchasingDoc);
		// 删除关联子业务对象数据
		if (deletedPurchasingDocItemSet != null && deletedPurchasingDocItemSet.size() > 0)
		{
			purchasingDocItemService._deletes(deletedPurchasingDocItemSet);
		} // 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(purchasingDoc);
	}

	/**
	 * 保存
	 * 
	 * @param purchasingDoc
	 */
	public void _save(PurchasingDoc purchasingDoc)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(purchasingDoc);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		purchasingDoc.setEbeln(null);

		Set<PurchasingDocItem> purchasingDocItemsSet = purchasingDoc.getPurchasingDocItems();
		Set<PurchasingDocItem> newPurchasingDocItemSet = null;
		if (null != purchasingDocItemsSet)
		{
			newPurchasingDocItemSet = new HashSet();
			Iterator<PurchasingDocItem> itPurchasingDocItem = purchasingDocItemsSet.iterator();
			while (itPurchasingDocItem.hasNext())
			{
				PurchasingDocItem purchasingDocItems = (PurchasingDocItem) itPurchasingDocItem.next();
				purchasingDocItems.setEbeln(null);
				newPurchasingDocItemSet.add(purchasingDocItems);
			}
		}
		purchasingDoc.setPurchasingDocItems(newPurchasingDocItemSet);

		purchasingDocHibernateDao.saveOrUpdate(purchasingDoc);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(purchasingDoc);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param purchasingDoc
	 */
	public void _saveOrUpdate(PurchasingDoc purchasingDoc, Set<PurchasingDocItem> deletedPurchasingDocItemSet)
	{
		if (StringUtils.isNullBlank(purchasingDoc.getEbeln()))
		{
			_save(purchasingDoc);
		}
		else
		{
			_update(purchasingDoc, deletedPurchasingDocItemSet);
		}
	}

}