/*
 * @(#)SampleService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.pledge.service;

import java.math.BigDecimal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.pledge.dao.PledgeShipHibernateDao;
import com.infolion.sapss.pledge.dao.PledgeShipItemHibernateDao;
import com.infolion.sapss.pledge.dao.PledgeShipJdbcDao;
import com.infolion.sapss.pledge.domain.PledgeReceiptsInfo;
import com.infolion.sapss.pledge.domain.PledgeShipsInfo;
import com.infolion.sapss.pledge.domain.PledgeShipsItem;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 王懿璞
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service     
public class PledgeShipHibernateService extends BaseHibernateService
	implements ProcessCallBack
{
	@Autowired
	PledgeShipHibernateDao pledgeShipHibernateDao;
	@Autowired
	PledgeShipJdbcDao pledgeShipJdbcDao;
	@Autowired
	PledgeShipItemHibernateDao pledgeShipItemHibernateDao;

	public void setPledgeShipHibernate(PledgeShipHibernateDao pledgeShipHibernateDao) {
		this.pledgeShipHibernateDao = pledgeShipHibernateDao;
	}
	public void setPledgeShipJdbcDao(PledgeShipJdbcDao pledgeShipJdbcDao) {
		this.pledgeShipJdbcDao = pledgeShipJdbcDao;
	}
	public void setPledgeShipItemHibernateDao(
			PledgeShipItemHibernateDao pledgeShipItemHibernateDao) {
		this.pledgeShipItemHibernateDao = pledgeShipItemHibernateDao;
	}
	/**
	 * 保存出仓单主信息
	 * @param receiptInfo
	 * @return
	 */
	public String newPledgeShipsInfo(PledgeShipsInfo pledgeShipsInfo) {
		String pledgeShipsInfoId = CodeGenerator.getUUID();
		pledgeShipsInfo.setPledgeShipsInfoId(pledgeShipsInfoId);		
		this.pledgeShipHibernateDao.save(pledgeShipsInfo);
		return pledgeShipsInfoId;
	}

	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		PledgeShipsInfo pledgeShipsInfo = this.getPledgeShipsInfo(businessRecordId);
		String examineState = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
		{
			examineState = "3";
			if(StringUtils.isNullBlank(pledgeShipsInfo.getPledgeShipsInfoNo())){
				pledgeShipsInfo.setPledgeShipsInfoNo(pledgeShipJdbcDao.getShipNo(pledgeShipJdbcDao.queryDeptCode(businessRecordId).substring(0, 2)));
			}
		}
		else
		{
			examineState = "4";
		}
		
		pledgeShipsInfo.setExamineState(examineState);
		pledgeShipsInfo.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.saveOrUpdate(pledgeShipsInfo);
		
	}
    public void saveOrUpdate(PledgeShipsInfo pledgeShipsInfo) {		
		this.pledgeShipHibernateDao.saveOrUpdate(pledgeShipsInfo);
	}
	
	public PledgeShipsItem getShipMaterial(String shipDetailId)
	{
		return this.pledgeShipItemHibernateDao.get(shipDetailId);
	}
	public PledgeShipsInfo getPledgeShipsInfo(String id) {
		return this.pledgeShipHibernateDao.get(id);
	}

	/**
	 * 保存并提交出货单
	 * 
	 * @param taskId
	 * @param tReceiptInfo
	 */
	public void submitAndSaveShipInfo(String taskId,
			PledgeShipsInfo pledgeShipsInfo)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		this.saveOrUpdate(pledgeShipsInfo);

		String workflowBusinessNote = ("质押物出仓|")+userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "序号："+ pledgeShipsInfo.getPledgeShipsInfoNo()+
		                       "|立项号："+pledgeShipsInfo.getProjectNo();
		pledgeShipsInfo.setWorkflowBusinessNote(workflowBusinessNote);

		this.submitShipInfo(taskId, pledgeShipsInfo);
	}	

	/**
	 * 提交进货单
	 * 
	 * @param taskId
	 * @param tReceiptInfo
	 */
	public void submitShipInfo(String taskId, PledgeShipsInfo pledgeShipsInfo)
	{
		pledgeShipsInfo.setWorkflowProcessName("pledge_export");
		if (null == taskId || "".equals(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(pledgeShipsInfo,
					pledgeShipsInfo.getPledgeShipsInfoId());
		}
		else
		{
			WorkflowService.signalProcessInstance(pledgeShipsInfo,pledgeShipsInfo
					.getPledgeShipsInfoId(), null);
		}
	}
	/**
	 * 查询库存数量20120607
	 * 
	 * @param materialCode
	 * @param wareHouse
	 * @param batchNo
	 */
	public BigDecimal getShipQuality(String materialCode, String warehouse, String batchNo,String pledgeShipsItemId,String deptId)
	{
		return pledgeShipJdbcDao.getShipQuality(materialCode, warehouse, batchNo, pledgeShipsItemId,deptId);
	}
	
	/**
	 * 查询库存数量
	 * 
	 * @param materialCode
	 * @param wareHouse
	 * @param batchNo
	 */
	public Map queryInventoryNum(String materialCode, String warehouse, String batchNo, String shipId, String shipDetailId,String deptId)
	{
		return pledgeShipJdbcDao.queryInventoryNum(materialCode, warehouse, batchNo, shipId, shipDetailId,deptId);
	}
	
	public void saveShipInfo(PledgeShipsInfo pledgeShipsInfo)
	{
		this.pledgeShipHibernateDao.saveOrUpdate(pledgeShipsInfo);
	}
	
}
