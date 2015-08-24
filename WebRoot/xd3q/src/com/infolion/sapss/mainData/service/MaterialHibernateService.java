package com.infolion.sapss.mainData.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.export.domain.TBaleLoan;
import com.infolion.sapss.mainData.dao.MaterialHibernateDao;
import com.infolion.sapss.mainData.domain.TMaterial;
import com.infolion.sapss.workflow.ProcessCallBack;
@Service
public class MaterialHibernateService extends BaseHibernateService implements
ProcessCallBack{
	@Autowired
	private MaterialHibernateDao materialHibernateDao;
	
	public void setMaterialHibernateDao(
			MaterialHibernateDao materialHibernateDao) {
		this.materialHibernateDao = materialHibernateDao;
	}

	/**
	 * 保存或更新
	 * @param info
	 */
	public void saveOrUpdate(TMaterial info) {
		this.materialHibernateDao.saveOrUpdate(info);
		
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(String id)
	{
		TMaterial tMaterial = this.materialHibernateDao.get(id);
		tMaterial.setIsAvailable("0");
		this.materialHibernateDao.update(tMaterial);
	}
	/**
	 * 查找
	 * @param id
	 */
	public TMaterial find(String id) {
		return this.materialHibernateDao.get(id);
		
	}
	
	/**
	 * 提交流程
	 * 
	 * @param taskId
	 * @param tBaleLoan
	 */
	public void submit(String taskId, TMaterial tMaterial)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "物料编号："
				+ tMaterial.getMaterialCode();
		tMaterial.setWorkflowBusinessNote(workflowBusinessNote);
		tMaterial.setWorkflowProcessName("materiel_data_v1");
		if (StringUtils.isEmpty(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(tMaterial, tMaterial
					.getMaterialId());
		}
		else
		{
			WorkflowService.signalProcessInstance(tMaterial, tMaterial
					.getMaterialId(), null);
		}
	}
	public String verifyFilds(String taskId, TMaterial tMaterial)
	{
		String rtn = "";
		return rtn;
	}
	public void saveAndSubmit(String taskId, TMaterial tMaterial)
	{
		this.saveOrUpdate(tMaterial);
		this.submit(taskId, tMaterial);
	}
	/**
	 * 流程审批状态更新
	 */
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo)
	{
		TMaterial tMaterial = this.materialHibernateDao.get(businessRecordId);
		if (examineResult.equals(this.EXAMINE_SUCCESSFUL))
		{
			tMaterial.setExamineState("3");

		}
		else if (examineResult.equals(this.EXAMINE_FAILE))
		{
			tMaterial.setExamineState("4");
		}
		tMaterial.setApprovedTime(DateUtils.getCurrTime(2));
		this.saveOrUpdate(tMaterial);
	}
}
