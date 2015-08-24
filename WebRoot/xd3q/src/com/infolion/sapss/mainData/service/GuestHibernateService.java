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
import com.infolion.sapss.mainData.dao.GuestHibernateDao;
import com.infolion.sapss.mainData.domain.TGuest;
import com.infolion.sapss.workflow.ProcessCallBack;
@Service
public class GuestHibernateService extends BaseHibernateService implements
ProcessCallBack{
	
	@Autowired
	private GuestHibernateDao guestHibernateDao;
	
	public void setGuestHibernateDao(
			GuestHibernateDao guestHibernateDao) {
		this.guestHibernateDao = guestHibernateDao;
	}

	/**
	 * 保存或更新
	 * @param info
	 */
	public void saveOrUpdate(TGuest info) {
		this.guestHibernateDao.saveOrUpdate(info);
		
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(String id)
	{
		TGuest tGuest = this.guestHibernateDao.get(id);
		tGuest.setIsAvailable("0");
		this.guestHibernateDao.update(tGuest);
	}
	/**
	 * 查找
	 * @param id
	 */
	public TGuest find(String id) {
		return this.guestHibernateDao.get(id);
		
	}
	
	/**
	 * 提交流程
	 * 
	 * @param taskId
	 * @param tBaleLoan
	 */
	public void submit(String taskId, TGuest tGuest)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "客户编号："
				+ tGuest.getGuestCode();
		tGuest.setWorkflowBusinessNote(workflowBusinessNote);
		
		tGuest.setWorkflowProcessName("customer_data_v1");//流程相同
		Map<String,String> map = new HashMap<String,String>();
		map.put("GUEST_ID", tGuest.getGuestId());
		tGuest.setWorkflowUserDefineProcessVariable(map);
		if (StringUtils.isEmpty(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(tGuest, tGuest.getGuestId());
		}
		else
		{
			WorkflowService.signalProcessInstance(tGuest, tGuest.getGuestId(), null);
		}
	}
	public String verifyFilds(String taskId, TGuest tGuest)
	{
		String rtn = "";
		return rtn;
	}
	public void saveAndSubmit(String taskId, TGuest tGuest)
	{
		this.saveOrUpdate(tGuest);
		this.submit(taskId, tGuest);
	}
	/**
	 * 流程审批状态更新
	 */
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo)
	{
		TGuest tGuest = this.guestHibernateDao.get(businessRecordId);
		if (examineResult.equals(this.EXAMINE_SUCCESSFUL))
		{
			tGuest.setExamineState("3");

		}
		else if (examineResult.equals(this.EXAMINE_FAILE))
		{
			tGuest.setExamineState("4");
		}
		tGuest.setApprovedTime(DateUtils.getCurrTime(2));
		this.saveOrUpdate(tGuest);
	}
}
