package com.infolion.sapss.mainData.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.mainData.dao.SupplyHibernateDao;
import com.infolion.sapss.mainData.domain.TGuest;
import com.infolion.sapss.mainData.domain.TSuppliers;
import com.infolion.sapss.workflow.ProcessCallBack;
@Service
public class SupplyHibernateService extends BaseHibernateService implements
ProcessCallBack{
	
	@Autowired
	private SupplyHibernateDao supplyHibernateDao;
	
	public void setSupplyHibernateDao(
			SupplyHibernateDao supplyHibernateDao) {
		this.supplyHibernateDao = supplyHibernateDao;
	}

	/**
	 * 保存或更新
	 * @param info
	 */
	public void saveOrUpdate(TSuppliers info) {
		this.supplyHibernateDao.saveOrUpdate(info);
		
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(String id)
	{
		TSuppliers tSuppliers = this.supplyHibernateDao.get(id);
		tSuppliers.setIsAvailable("0");
		this.supplyHibernateDao.update(tSuppliers);
	}
	/**
	 * 查找
	 * @param id
	 */
	public TSuppliers find(String id) {
		return this.supplyHibernateDao.get(id);
		
	}
	
	/**
	 * 提交流程
	 * 
	 * @param taskId
	 * @param tBaleLoan
	 */
	public void submit(String taskId, TSuppliers tSuppliers)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "供应商编号："
				+ tSuppliers.getSuppliersCode();
		tSuppliers.setWorkflowBusinessNote(workflowBusinessNote);
		tSuppliers.setWorkflowProcessName("supplier_data_v1");
		Map<String,String> map = new HashMap<String,String>();
		map.put("SUPPLIER_ID", tSuppliers.getSuppliersId());
		tSuppliers.setWorkflowUserDefineProcessVariable(map);

		if (StringUtils.isEmpty(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(tSuppliers, tSuppliers.getSuppliersId());
		}
		else
		{
			WorkflowService.signalProcessInstance(tSuppliers, tSuppliers.getSuppliersId(), null);
		}
	}
	public String verifyFilds(String taskId, TSuppliers tSuppliers)
	{
		String rtn = "";
		return rtn;
	}
	public void saveAndSubmit(String taskId, TSuppliers tSuppliers)
	{
		this.saveOrUpdate(tSuppliers);
		this.submit(taskId, tSuppliers);
	}
	/**
	 * 流程审批状态更新
	 */
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo)
	{
		TSuppliers tSuppliers = this.supplyHibernateDao.get(businessRecordId);
		if (examineResult.equals(this.EXAMINE_SUCCESSFUL))
		{
			tSuppliers.setExamineState("3");

		}
		else if (examineResult.equals(this.EXAMINE_FAILE))
		{
			tSuppliers.setExamineState("4");
		}
		tSuppliers.setApprovedTime(DateUtils.getCurrTime(2));
		this.saveOrUpdate(tSuppliers);
	}
}
