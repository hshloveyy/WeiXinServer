/*
 * @(#)DeptChargeServiceGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年03月11日 09点45分48秒
 *  描　述：创建
 */
package com.infolion.XDSS.sample.deptchargeGen.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.infolion.XDSS.budget.maindata.BudgetClass.domain.BudgetClass;
import com.infolion.XDSS.budget.maindata.BudgetOrganization.domain.BudgetOrganization;
import com.infolion.XDSS.budget.maindata.BudgetTemplate.domain.BudgetTemplate;
import com.infolion.XDSS.budget.maindata.BudgetTemplateItem.domain.BudgetTemplateItem;
import com.infolion.XDSS.budget.sheet.service.BudgetSheetService;
import com.infolion.XDSS.sample.deptBudgetting.domain.DeptBudgetting;
import com.infolion.XDSS.sample.deptBudgetting.service.DeptBudgettingService;
import com.infolion.XDSS.sample.deptcharge.dao.DeptChargeHibernateDao;
import com.infolion.XDSS.sample.deptcharge.domain.DeptCharDetail;
import com.infolion.XDSS.sample.deptcharge.domain.DeptCharge;
import com.infolion.XDSS.sample.deptcharge.service.DeptCharDetailService;
import com.infolion.XDSS.sample.deptchargeGen.dao.DeptChargeJdbcDao;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.domain.GridColumn;
import com.infolion.platform.bo.domain.Property;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.platform.dpframework.core.service.SheetService;
import com.infolion.platform.dpframework.outsideInterface.OutsideInterfaceUtils;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.dpframework.util.AssertUtil;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.spreadsheet.SpreadsheetException;
import com.infolion.platform.spreadsheet.Utils;
import com.infolion.platform.workflow.engine.WorkflowService;

/**
 * <pre>
 * 管理费用预算(DeptCharge)服务类
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
public class DeptChargeServiceGen extends SheetService<DeptCharge>
{
	protected Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private BudgetSheetService budgetSheetService;

	@Autowired
	protected DeptChargeHibernateDao deptChargeHibernateDao;

	@Autowired
	protected DeptChargeJdbcDao deptChargeJdbcDao;

	@Autowired
	protected DeptBudgettingService deptBudgettingService;

	public void setBudgetSheetService(BudgetSheetService budgetSheetService)
	{
		this.budgetSheetService = budgetSheetService;
	}

	public void setDeptChargeJdbcDao(DeptChargeJdbcDao deptChargeJdbcDao)
	{
		this.deptChargeJdbcDao = deptChargeJdbcDao;
	}

	public void setDeptChargeHibernateDao(DeptChargeHibernateDao deptChargeHibernateDao)
	{
		this.deptChargeHibernateDao = deptChargeHibernateDao;
	}

	/**
	 * 注入扩展服务类
	 */
	@Autowired(required = false)
	public void setAdvanceService(@Qualifier("deptChargeAdvanceService") AdvanceService advanceService)
	{
		super.setAdvanceService(advanceService);
	}

	@Autowired
	protected DeptCharDetailService deptCharDetailService;

	public void setDeptCharDetailService(DeptCharDetailService deptCharDetailService)
	{
		this.deptCharDetailService = deptCharDetailService;
	}

	/**
	 * 根据主键ID,取得管理费用预算实例
	 * 
	 * @param id
	 * @return
	 */
	public DeptCharge _get(String id)
	{
		DeptCharge deptCharge = new DeptCharge();
		if (StringUtils.isNotBlank(id))
		{
			deptCharge = deptChargeHibernateDao.get(id);
		}
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = LockService.lockBOData(deptCharge);
		if (OperationType.UNVISIABLE.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotView);
		}
		deptCharge.setOperationType(operationType);

		return deptCharge;
	}

	/**
	 * 根据主键ID,取得管理费用预算实例
	 * 
	 * @param id
	 * @return
	 */
	public DeptCharge _getForEdit(String id)
	{
		DeptCharge deptCharge = _get(id);
		// 需要转移到service服务里，要不存在事务问题。
		String operationType = deptCharge.getOperationType();
		if (OperationType.READONLY.equals(operationType))
		{
			throw new LockException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.DataLockCanNotEdit);
		}

		return deptCharge;
	}

	/**
	 * 根据主键ID,取得管理费用预算实例副本
	 * 
	 * @param id
	 * @return
	 */
	public DeptCharge _getEntityCopy(String id)
	{
		DeptCharge deptCharge = new DeptCharge();
		DeptCharge deptChargeOld = this._get(id);
		try
		{
			BeanUtils.copyProperties(deptCharge, deptChargeOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		deptCharge.setDeptchargeid(null);
		return deptCharge;
	}

	/**
	 * 删除
	 * 
	 * @param deptCharge
	 */
	public void _delete(DeptCharge deptCharge)
	{
		if (null != advanceService)
			advanceService.preDelete(deptCharge);

		LockService.isBoInstanceLocked(deptCharge, DeptCharge.class);
		deptChargeHibernateDao.remove(deptCharge);

		if (null != advanceService)
			advanceService.postDelete(deptCharge);
	}

	/**
	 * 根据主键删除
	 * 
	 * @param deptChargeId
	 */
	public void _delete(String deptChargeId, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(deptChargeId))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("deptchargeid"));
		DeptCharge deptCharge = this.deptChargeHibernateDao.load(deptChargeId);
		_delete(deptCharge);
	}

	/**
	 * 删除
	 * 
	 * @param Set
	 *            <DeptCharge> deptCharges
	 */
	public void _deletes(Set<DeptCharge> deptCharges, BusinessObject businessObject)
	{
		if (null == deptCharges || deptCharges.size() < 1)
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoText());
		Iterator<DeptCharge> it = deptCharges.iterator();
		while (it.hasNext())
		{
			DeptCharge deptCharge = (DeptCharge) it.next();
			_delete(deptCharge);
		}
	}

	/**
	 * 根据多个主键(由，分割)删除多个对象
	 * 
	 * @param deptChargeIds
	 */
	public void _deletes(String deptChargeIds, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(deptChargeIds))
			// TODO DataElementText
			throw new BusinessException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.NotBlank, businessObject.getBoPropertyText("deptchargeid"));
		String[] ids = StringUtils.splitString(deptChargeIds);
		for (int i = 0; i < ids.length; i++)
		{
			_delete(ids[i], businessObject);
		}
	}

	/**
	 * 提交工作流
	 * 
	 * @param deptCharge
	 */
	public void _submitProcess(DeptCharge deptCharge, Set<DeptCharDetail> deletedChargeDetailSet, BusinessObject businessObject)
	{
		String id = deptCharge.getDeptchargeid();
		if (StringUtils.isNullBlank(id))
		{
			_save(deptCharge);
		}
		else
		{
			_update(deptCharge, deletedChargeDetailSet, businessObject);
		}
		String taskId = deptCharge.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(deptCharge, id);
		else
			WorkflowService.signalProcessInstance(deptCharge, id, null);
	}

	/**
	 * 保存或更新管理费用预算 保存
	 * 
	 * @param deptCharge
	 */
	public void _update(DeptCharge deptCharge, Set<DeptCharDetail> deletedDeptCharDetailSet, BusinessObject businessObject)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(deptCharge);
		deptChargeHibernateDao.saveOrUpdate(deptCharge);
		// 删除关联子业务对象数据
		if (deletedDeptCharDetailSet != null && deletedDeptCharDetailSet.size() > 0)
		{
			deptCharDetailService._deletes(deletedDeptCharDetailSet, businessObject);
		} // 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(deptCharge);
	}

	/**
	 * 保存
	 * 
	 * @param deptCharge
	 */
	public void _save(DeptCharge deptCharge)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(deptCharge);
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		deptCharge.setDeptchargeid(null);

		Set<DeptCharDetail> chargeDetailSet = deptCharge.getChargeDetail();
		Set<DeptCharDetail> newDeptCharDetailSet = null;
		if (null != chargeDetailSet)
		{
			newDeptCharDetailSet = new HashSet();
			Iterator<DeptCharDetail> itDeptCharDetail = chargeDetailSet.iterator();
			while (itDeptCharDetail.hasNext())
			{
				DeptCharDetail chargeDetail = (DeptCharDetail) itDeptCharDetail.next();
				chargeDetail.setDeptchardetailid(null);
				newDeptCharDetailSet.add(chargeDetail);
			}
		}
		deptCharge.setChargeDetail(newDeptCharDetailSet);

		deptChargeHibernateDao.saveOrUpdate(deptCharge);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(deptCharge);
	}

	/**
	 * 
	 * 保存
	 * 
	 * @param deptCharge
	 */
	public void _saveOrUpdate(DeptCharge deptCharge, Set<DeptCharDetail> deletedDeptCharDetailSet, BusinessObject businessObject)
	{
		if (StringUtils.isNullBlank(deptCharge.getDeptchargeid()))
		{
			_save(deptCharge);
		}
		else
		{
			_update(deptCharge, deletedDeptCharDetailSet, businessObject);
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

	@Override
	public void _update(DeptCharge boInst)
	{
		// 用户二次开发服务，保存前执行
		if (null != advanceService)
			advanceService.preSaveOrUpdate(boInst);
		deptChargeHibernateDao.saveOrUpdate(boInst);
		// 用户二次开发服务，保存后执行
		if (null != advanceService)
			advanceService.postSaveOrUpdate(boInst);
	}

	@Override
	public List<String[]> _getVersionList(String busId)
	{
		DeptCharge deptCharge = this.deptChargeHibernateDao.get(busId);
		return this.deptChargeJdbcDao.getVersionList(deptCharge.getBudclassid(), deptCharge.getBudorgid());
	}

	@Override
	public DeptCharge _getByBudorgid(String budorgid, String busId)
	{
		QueryCondition queryCondition = getQueryCondition(busId);

		DeptCharge resultDeptCharge = null;
		// 如果不是汇总数据，查找与参考业务实例相对应的实例
		DeptCharge example = new DeptCharge();
		example.setBudorgid(budorgid);
		example.setAyear(queryCondition.ayear);
		example.setBudclassid(queryCondition.budclassid);
		example.setVersion(queryCondition.version);

		List list = this.deptChargeHibernateDao.findByExample(example);
		if (list != null && list.size() > 0)
		{
			resultDeptCharge = (DeptCharge) list.get(0);
		}
		return resultDeptCharge;
	}

	@Override
	public Object _summary(String sumBudOrgId, String[] budOrgIds, BudgetClass budgetClass, String busId)
	{
		// 汇总业务对象
		BusinessObject businessObject = BusinessObjectService.getBusinessObjectByBoId(budgetClass.getSumboid());
		Object boInst = OutsideInterfaceUtils.newBusinessObjectInstance(businessObject);

		QueryCondition queryCondition = getQueryCondition(busId);

		// 与特定业务相关的查询语句
		String whereSql = " AYEAR = '" + queryCondition.ayear + "' and VERSION = '" + queryCondition.version + "'";

		String[] busIds = this.budgetSheetService.getBusIdByBudOrgId(businessObject, budgetClass.getBudclassid(), budOrgIds, whereSql);

		if (busIds.length == 0)
		{
			throw new SpreadsheetException("指定预算组织下没有要汇总的数据");
		}

		// 设置所有预算模板对应的子对象集合
		for (BudgetTemplate budgetTemplate : budgetClass.getBudgetTemplate())
		{
			Set set = new HashSet();
			// 要汇总的子业务对象
			BusinessObject subBusinessObject = BusinessObjectService.getBusinessObjectByBoId(budgetTemplate.getBoid());
			// 取得所有需要进行SUM操作的子对象属性
			Set<Property> sumProperties = new HashSet<Property>();
			for (GridColumn gridColumn : subBusinessObject.getGridColumns())
			{
				if (StringUtils.isNotBlank(gridColumn.getIsSum()))
				{
					sumProperties.add(gridColumn.getProperty());
				}
			}
			if (sumProperties.size() == 0)
			{
				throw new SpreadsheetException("业务对象[" + subBusinessObject.getBoName() + "]没有配置任何需要汇总的属性！");
			}
			Property[] sumProArray = sumProperties.toArray(new Property[sumProperties.size()]);

			// 子对象表中关联模板预算项的字段名
			String budTempItemColumnName = subBusinessObject.getPropertyByName(BudgetSheetService.BUD_TEM_ITEM_ID).getColumnName();
			// 子对象对应的表名
			String subTableName = subBusinessObject.getTableName();
			// 子对象关联主对象的字段名（主对象的主键字段名就是子对象关联主对象的字段名）
			String foreignKeyColumnName = businessObject.getPrimaryKeyProperty().getColumnName();
			// 汇总每一个预算项
			for (BudgetTemplateItem budgetTemplateItem : budgetTemplate.getBudgetTemplateItem())
			{
				Map<String, Object> map = this.budgetSheetService.summary(foreignKeyColumnName, busIds, budgetTemplateItem.getBudtemitemid(), budTempItemColumnName, sumProArray, subTableName);
				Object obj = OutsideInterfaceUtils.newBusinessObjectInstance(subBusinessObject);
				Utils.setPros(obj, map);
				// 设置子对象实例关联模板预算项的属性值，构造Flex数据时能过此属性匹配表格中哪一行与此记录相对应
				Utils.setProperty(obj, BudgetSheetService.BUD_TEM_ITEM_ID, budgetTemplateItem.getBudtemitemid());
				// 特定业务相关的操作
				// ...

				set.add(obj);
			}
			// 存放子对象集合的属性名
			String subProName = Utils.getProNameBySubBoName(businessObject, subBusinessObject.getBoName());
			if (subProName == null)
			{
				throw new BusinessException("主业务对象[" + businessObject.getBoName() + "]找不到与预算模板[" + budgetTemplate.getBudtemname() + "]相对应的存放子对象[" + subBusinessObject.getBoName() + "]的属性");
			}
			Utils.setProperty(boInst, subProName, set);
		}

		// 特定业务相关的操作
		DeptCharge deptCharge2 = (DeptCharge) boInst;
		deptCharge2.setAyear(queryCondition.ayear);
		BudgetOrganization budOrg = (BudgetOrganization) BusinessObjectService.getBoInstance("BudgetOrganization", sumBudOrgId);
		deptCharge2.setBudorgname(budOrg.getBudorgname());
		deptCharge2.setVersion(queryCondition.version);
		deptCharge2.setBudorgid(sumBudOrgId);

		return deptCharge2;
	}

	/**
	 * 提交工作流
	 * 
	 * @param deptCharge
	 */
	public void _submitProcess(DeptCharge deptCharge, BusinessObject businessObject)
	{
		String id = deptCharge.getDeptchargeid();
		if (StringUtils.isNullBlank(id))
		{
			_save(deptCharge);
		}
		else
		{
			_update(deptCharge, null, businessObject);
		}
		String taskId = deptCharge.getWorkflowTaskId();
		if (null == taskId || "".equals(taskId))
			WorkflowService.createAndSignalProcessInstance(deptCharge, id);
		else
			WorkflowService.signalProcessInstance(deptCharge, id, null);
	}

	@Override
	public void _summarySumitProcess(String businessId, String workflowTaskId, String workflowLeaveTransitionName, String workflowExamine)
	{
		DeptBudgetting deptBudgetting = this.deptBudgettingService._get(businessId);
		deptBudgetting.setWorkflowTaskId(workflowTaskId);
		deptBudgetting.setWorkflowLeaveTransitionName(workflowLeaveTransitionName);
		deptBudgetting.setWorkflowExamine(workflowExamine);
		WorkflowService.signalProcessInstance(deptBudgetting, businessId, null);
	}

	/**
	 * 从参考对象实例ID取得查询条件
	 * 
	 * @param busId
	 * @return
	 */
	protected QueryCondition getQueryCondition(String busId)
	{
		QueryCondition queryCondition = new QueryCondition();
		// 以参考业务对象作为查询条件
		// DeptCharge deptCharge = this.deptChargeHibernateDao.get(busId);
		// if (deptCharge == null)
		// {
		// throw new BusinessException("DeptCharge找不到ID为[" + busId + "]的记录");
		// }
		// queryCondition.ayear = deptCharge.getAyear();
		// queryCondition.budclassid = deptCharge.getBudclassid();
		// queryCondition.version = deptCharge.getVersion();
		// queryCondition.budorgid = deptCharge.getBudorgid();

		// 以辅助业务对象(DeptBudgetting)作为查询条件取得busIds
		DeptBudgetting deptBudgetting = this.deptBudgettingService._get(busId);
		if (deptBudgetting == null)
		{
			throw new BusinessException("DeptBudgetting找不到ID为[" + busId + "]的记录");
		}
		queryCondition.ayear = deptBudgetting.getAyear();
		queryCondition.budclassid = deptBudgetting.getBudclassid();
		queryCondition.version = deptBudgetting.getVersion();
		queryCondition.budorgid = deptBudgetting.getBudorgid();

		return queryCondition;
	}

	// 查询条件
	protected static class QueryCondition
	{
		public String ayear;
		public String budclassid;
		public String version;
		public String budorgid;
	}

}