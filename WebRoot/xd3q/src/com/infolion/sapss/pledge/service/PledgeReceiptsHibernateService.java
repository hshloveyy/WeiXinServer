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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.sapss.bapi.ShipReceiptUtils;
import com.infolion.sapss.common.NumberUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractGroupHibernateDao;
import com.infolion.sapss.contract.dao.ContractPurchaseInfoHibernateDao;
import com.infolion.sapss.contract.dao.ContractPurchaseMaterielHibernateDao;
import com.infolion.sapss.contract.domain.TContractGroup;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractPurchaseMateriel;
import com.infolion.sapss.mainData.domain.TMaterial;
import com.infolion.sapss.pledge.dao.PledgeReceiptsHibernateDao;
import com.infolion.sapss.pledge.dao.PledgeReceiptsItemHibernateDao;
import com.infolion.sapss.pledge.dao.PledgeReceiptsJdbcDao;
import com.infolion.sapss.pledge.domain.PledgeReceiptsInfo;
import com.infolion.sapss.pledge.domain.PledgeReceiptsItem;
import com.infolion.sapss.project.dao.ProjectInfoHibernate;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.receipts.domain.TReceiptInfo;
import com.infolion.sapss.receipts.dao.ReceiptsJdbcDao;
import com.infolion.sapss.receipts.dao.ReceiptsHibernateDao;
import com.infolion.sapss.receipts.domain.TReceiptMaterial;
import com.infolion.sapss.receipts.dao.ReceiptsMaterialHibernateDao;
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
public class PledgeReceiptsHibernateService extends BaseHibernateService
	implements ProcessCallBack
{
	@Autowired
	PledgeReceiptsHibernateDao pledgeReceiptsHibernateDao;
	@Autowired
	ProjectInfoHibernate projectInfoHibernate;
	public void setPledgeReceiptsHibernate(PledgeReceiptsHibernateDao pledgeReceiptsHibernateDao) {
		this.pledgeReceiptsHibernateDao = pledgeReceiptsHibernateDao;
	}
	@Autowired
	PledgeReceiptsItemHibernateDao pledgeReceiptsItemHibernateDao;
	@Autowired
	PledgeReceiptsJdbcDao pledgeReceiptsJdbcDao;

	/**
	 * 保存进仓单主信息
	 * @param receiptInfo
	 * @return
	 */
	public String newPledgeReceiptsInfo(PledgeReceiptsInfo pledgeReceiptsInfo) {
		String pledgeReceiptsInfoId = CodeGenerator.getUUID();
		pledgeReceiptsInfo.setPledgeReceiptsInfoId(pledgeReceiptsInfoId);		
		this.pledgeReceiptsHibernateDao.save(pledgeReceiptsInfo);
		return pledgeReceiptsInfoId;
	}

	/**
	 * 流程回调更新进仓单状态
	 */
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo)
	{
		PledgeReceiptsInfo pledgeReceiptsInfo = this.getPledgeReceiptsInfo(businessRecordId);
		String examineState = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
		{
			examineState = "3";
			if(StringUtils.isNullBlank(pledgeReceiptsInfo.getPledgeReceiptsInfoNo())){
				pledgeReceiptsInfo.setPledgeReceiptsInfoNo(pledgeReceiptsJdbcDao.getReceiptNo(pledgeReceiptsJdbcDao.queryDeptCode(businessRecordId).substring(0, 2)));
			}
		}
		else
		{
			examineState = "4";
		}
		
		pledgeReceiptsInfo.setExamineState(examineState);
		pledgeReceiptsInfo.setApprovedTime(DateUtils
				.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.savePledgeReceipt(pledgeReceiptsInfo);
		
		//System.out.println("examineState" + examineState);
	}
	
	public void savePledgeReceipt(PledgeReceiptsInfo pledgeReceiptsInfo)
	{
		pledgeReceiptsHibernateDao.saveOrUpdate(pledgeReceiptsInfo);
	}
	
	
	public PledgeReceiptsInfo getPledgeReceiptsInfo(String id) {
		return this.pledgeReceiptsHibernateDao.get(id);
	}

	public void saveOrUpdate(PledgeReceiptsInfo pledgeReceiptsInfo) {
		
		this.pledgeReceiptsHibernateDao.saveOrUpdate(pledgeReceiptsInfo);
	}

	
	/**
	 * 保存并提交进货单
	 * 
	 * @param taskId
	 * @param tReceiptInfo
	 */
	public void submitAndSaveReceiptInfo(String taskId,
			PledgeReceiptsInfo pledgeReceiptsInfo)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		this.saveOrUpdate(pledgeReceiptsInfo);

		String workflowBusinessNote = ("质押物进仓|")+userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "序号："+ pledgeReceiptsInfo.getPledgeReceiptsInfoNo()+
		                       "|立项号："+pledgeReceiptsInfo.getProjectNo();
		pledgeReceiptsInfo.setWorkflowBusinessNote(workflowBusinessNote);

		this.submitReceiptInfo(taskId, pledgeReceiptsInfo);
	}	

	/**
	 * 提交进货单
	 * 
	 * @param taskId
	 * @param tReceiptInfo
	 */
	public void submitReceiptInfo(String taskId, PledgeReceiptsInfo pledgeReceiptsInfo)
	{
		pledgeReceiptsInfo.setWorkflowProcessName("pledge_receipt");
		if (null == taskId || "".equals(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(pledgeReceiptsInfo,
					pledgeReceiptsInfo.getPledgeReceiptsInfoId());
		}
		else
		{
			WorkflowService.signalProcessInstance(pledgeReceiptsInfo, pledgeReceiptsInfo
					.getPledgeReceiptsInfoId(), null);
		}
	}
	
	
}
