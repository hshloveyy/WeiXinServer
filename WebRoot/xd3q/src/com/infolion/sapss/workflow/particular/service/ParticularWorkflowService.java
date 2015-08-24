/*
 * @(#)ParticularWorkflowService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-10
 *  描　述：创建
 */

package com.infolion.sapss.workflow.particular.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractJdbcDao;
import com.infolion.sapss.credit.dao.CreditEntryJdbcDao;
import com.infolion.sapss.payment.dao.PaymentContractJdbcDao;
import com.infolion.sapss.receipts.dao.ReceiptsJdbcDao;
import com.infolion.sapss.ship.dao.ShipInfoJdbcDao;
import com.infolion.sapss.workflow.particular.dao.ParticularWorkflowHibernate;
import com.infolion.sapss.workflow.particular.dao.ParticularWorkflowJdbcDao;
import com.infolion.sapss.workflow.particular.domain.TParticularWorkflow;

@Service
public class ParticularWorkflowService extends BaseHibernateService{
	@Autowired
	private ParticularWorkflowHibernate particularWorkflowHibernate;
	@Autowired
	private ContractJdbcDao contractJdbcDao;
	public void setContractJdbcDao(ContractJdbcDao contractJdbcDao) {
		this.contractJdbcDao = contractJdbcDao;
	}
	public void setParticularWorkflowHibernate(
			ParticularWorkflowHibernate particularWorkflowHibernate) {
		this.particularWorkflowHibernate = particularWorkflowHibernate;
	}
	@Autowired
	private ParticularWorkflowJdbcDao particularWorkflowJdbcDao;
	public void setParticularWorkflowJdbcDao(
			ParticularWorkflowJdbcDao particularWorkflowJdbcDao) {
		this.particularWorkflowJdbcDao = particularWorkflowJdbcDao;
	}
	@Autowired
	private PaymentContractJdbcDao paymentContractJdbcDao;
	
	public void setPaymentContractJdbcDao(
			PaymentContractJdbcDao paymentContractJdbcDao) {
		this.paymentContractJdbcDao = paymentContractJdbcDao;
	}
	@Autowired
	private ReceiptsJdbcDao receiptsJdbcDao;
	public void setReceiptsJdbcDao(ReceiptsJdbcDao receiptsJdbcDao) {
		this.receiptsJdbcDao = receiptsJdbcDao;
	}
	@Autowired
	private ShipInfoJdbcDao shipInfoJdbcDao;
	public void setShipInfoJdbcDao(ShipInfoJdbcDao shipInfoJdbcDao) {
		this.shipInfoJdbcDao = shipInfoJdbcDao;
	}
	@Autowired
	private CreditEntryJdbcDao  creditEntryJdbcDao;
	public void setCreditEntryJdbcDao(CreditEntryJdbcDao creditEntryJdbcDao) {
		this.creditEntryJdbcDao = creditEntryJdbcDao;
	}
	/**
	 * 保存
	 * @param tParticularWorkflow
	 */
	public void save(TParticularWorkflow obj){
		this.particularWorkflowHibernate.save(obj);
	}
	/**
	 * 查找
	 * @param particularId
	 * @return
	 */
	public TParticularWorkflow load(String particularId){
		return this.particularWorkflowHibernate.load(particularId);
	}
	/**
	 * 查找
	 * @param particularId
	 * @return
	 */
	public String find(String particularId){
		return this.particularWorkflowJdbcDao.find(particularId);
	}
	/**
	 * 根据原业务ID取得流转ID
	 * @param originalId
	 * @return
	 */
	public String getParticularIdByOriginalId(String originalId){
		return this.particularWorkflowJdbcDao.getParticularIdByOriginalId(originalId);
	}
	/**
	 * 首次提交审批
	 * @param obj
	 */
	public void firstSubmitParticularWorkflow(String controller,String parms,TParticularWorkflow obj) {
		obj.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("special_approve_v1"));
		obj.setWorkflowProcessUrl("particularWorkflowController.spr?action=linkSubmitParticularWorkflow&parms="+parms+"&controller="+controller);//设置待办链接的URL
		WorkflowService.createAndSignalProcessInstance(obj, obj.getParticularId());
		if(controller!=null){
			if("contractController".equals(controller)){
				if(parms!=null && parms.indexOf("Sale")!=-1)
					this.contractJdbcDao.updateSalesOrderState(obj.getOriginalBizId(), "10");
				else
					this.contractJdbcDao.updatePurchaserOrderState(obj.getOriginalBizId(), "10");
			}else if("innerTradePayController".equals(controller)){
				this.paymentContractJdbcDao.updateStatus(obj.getOriginalBizId(),"7");
			}else if("creditEntryController".equals(controller)){
				this.creditEntryJdbcDao.updateState(obj.getOriginalBizId(), "12");
			}else if("shipController".equals(controller)){
				//this.shipInfoJdbcDao.updateReceipts(obj.getOriginalBizId(), "examine_state", "7");
			}else if("receiptsController".equals(controller)){
				this.receiptsJdbcDao.updateReceipts(obj.getOriginalBizId(), "examine_state", "7");
			}
		}
	}
	/**
	 * 提交特批审批
	 * @param obj
	 */
	public void submitParticularWorkflow(TParticularWorkflow obj) {
		WorkflowService.signalProcessInstance(obj,obj.getParticularId(), null);
		
	}
	/**
	 * 取得历史记录中不同意提交的记录数
	 * @param bizId
	 * @return
	 */
	public int getNoPassTimes(String bizId){
		if(StringUtils.isBlank(bizId))
			return 0;
		return this.particularWorkflowJdbcDao.getNoPassTimes(bizId);
	}
	/**
	 * 取得最近审批结点中不同意的节点名称
	 * @param particularId
	 * @return
	 */
	public String getNearestNoPassNodeName(String bizId) {
		if(StringUtils.isBlank(bizId))
			return null;
		return this.particularWorkflowJdbcDao.getNearestNoPassNodeName(bizId);
	}

}
