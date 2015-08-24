/*
 * @(#)JdPurchaseService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-25
 *  描　述：创建
 */

package com.infolion.sapss.storageProtocol.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractPurchaseInfoHibernateDao;
import com.infolion.sapss.contract.dao.ContractSalesInfoHibernateDao;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.project.service.ProjectJdbcService;
import com.infolion.sapss.storageProtocol.dao.StorageProtocolCHibernate;
import com.infolion.sapss.storageProtocol.dao.StorageProtocolChangeHibernate;
import com.infolion.sapss.storageProtocol.dao.StorageProtocolHibernate;
import com.infolion.sapss.storageProtocol.dao.StorageProtocolJdbcDao;
import com.infolion.sapss.storageProtocol.domain.TStorageProtocol;
import com.infolion.sapss.storageProtocol.domain.TStorageProtocolC;
import com.infolion.sapss.storageProtocol.domain.TStorageProtocolChange;
@Service
public class StorageProtocolService extends BaseJdbcService{
	@Autowired
	private StorageProtocolJdbcDao storageProtocolJdbcDao;
	@Autowired
	private StorageProtocolHibernate storageProtocolHibernate;
	@Autowired
	private ContractPurchaseInfoHibernateDao contractPurchaseInfoHibernateDao;
	@Autowired
	private ContractSalesInfoHibernateDao contractSalesInfoHibernateDao;
	@Autowired
	private StorageProtocolCHibernate storageProtocolCHibernate;
	
	@Autowired
	private ProjectJdbcService projectJdbcService;
	
	@Autowired
	private StorageProtocolChangeHibernate storageProtocolChangeHibernate;
	
	public void setStorageProtocolJdbcDao(StorageProtocolJdbcDao storageProtocolJdbcDao) {
		this.storageProtocolJdbcDao = storageProtocolJdbcDao;
	}
	public void setStorageProtocolHibernate(StorageProtocolHibernate storageProtocolHibernate) {
		this.storageProtocolHibernate = storageProtocolHibernate;
	}
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
	}
	/**
	 * 保存
	 * @param s
	 */
	public void saveInfo(TStorageProtocol s,String[] contracts) {
		storageProtocolJdbcDao.deleteContract(s.getInfoId());
		if(contracts!=null){
			for(int i=0;i<contracts.length;i++){
				String[] strs = contracts[i].split("/");
				String id = strs[0];
				String contractType = strs[1].trim();
				TStorageProtocolC c = new TStorageProtocolC();
				if("采购合同".equals(contractType)){
					TContractPurchaseInfo info = contractPurchaseInfoHibernateDao.get(id);
					c.setContractId(id);
					c.setContractNo(info.getContractNo());
					c.setContractType("采购合同");
					c.setPaperNo(info.getEkkoUnsez());
					c.setSapOrderNo(info.getSapOrderNo());
					c.setStorageProtocolCId(CodeGenerator.getUUID());
					c.setStorageProtocolId(s.getInfoId());
				}
				else if("销售合同".equals(contractType)){
					TContractSalesInfo info = contractSalesInfoHibernateDao.get(id);
					c.setContractId(id);
					c.setContractNo(info.getContractNo());
					c.setContractType("销售合同");
					c.setPaperNo(info.getVbkdIhrez());
					c.setSapOrderNo(info.getSapOrderNo());
					c.setStorageProtocolCId(CodeGenerator.getUUID());
					c.setStorageProtocolId(s.getInfoId());
				}
				storageProtocolCHibernate.saveOrUpdate(c);
			}
		}
		this.storageProtocolHibernate.saveOrUpdate(s);
	}
	/**
	 * 查找
	 * @param scrappedId
	 */
	public TStorageProtocol findInfo(String id) {
		List<TStorageProtocol> list= this.storageProtocolHibernate.find("from TStorageProtocol t where t.infoId=?", new String[]{id});
		if(list!=null && list.size()>0)
			return list.get(0);
		return new TStorageProtocol();
	}
	/**
	 * 删除
	 * @param scrappedId
	 */
	public int delInfo(String id) {
		return this.storageProtocolJdbcDao.delInfo(id);
		
	}
	/**
	 * 提交流程审批
	 * @param taskId
	 * @param ts
	 */
	public void submit(String taskId, TStorageProtocol ts) {
		Map map = new HashMap();
		map.put("INFO_ID", ts.getInfoId());
		map.put("is_Relate_Company",String.valueOf(projectJdbcService.isRelateCompany(ts.getComNo())));
		ts.setWorkflowUserDefineTaskVariable(map);
		ts.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("storage_protocol_v1"));
		if(null==taskId||"".equals(taskId)){
			WorkflowService.createAndSignalProcessInstance(ts, ts.getInfoId());
			this.storageProtocolJdbcDao.updateState(ts,"2",true);
		}else{
			WorkflowService.signalProcessInstance(ts,ts.getInfoId(), null);
		}
		
	}
	
	public void setContractPurchaseInfoHibernateDao(
			ContractPurchaseInfoHibernateDao contractPurchaseInfoHibernateDao) {
		this.contractPurchaseInfoHibernateDao = contractPurchaseInfoHibernateDao;
	}
	public void setContractSalesInfoHibernateDao(
			ContractSalesInfoHibernateDao contractSalesInfoHibernateDao) {
		this.contractSalesInfoHibernateDao = contractSalesInfoHibernateDao;
	}
	public void setStorageProtocolCHibernate(
			StorageProtocolCHibernate storageProtocolCHibernate) {
		this.storageProtocolCHibernate = storageProtocolCHibernate;
	}
	
	public List<TStorageProtocolChange> findStorageProtocolChangeByInfoId(String infoId,String changeState){
			String hql = "from TStorageProtocolChange where infoId='"+infoId+"'";
			if(changeState!=null && !"".equals(changeState))
				hql = hql + " and changeState='"+changeState+"'";
			return this.storageProtocolChangeHibernate.find(hql);
	}
	public StorageProtocolChangeHibernate getStorageProtocolChangeHibernate() {
		return storageProtocolChangeHibernate;
	}
	public void setStorageProtocolChangeHibernate(
			StorageProtocolChangeHibernate storageProtocolChangeHibernate) {
		this.storageProtocolChangeHibernate = storageProtocolChangeHibernate;
	}
	
	public int getChangeNo(String infoId){
		return storageProtocolJdbcDao.getChangeNo(infoId);
	}
	
	public String saveChange(TStorageProtocolChange tc){
		if(tc.getChangeId()==null||"".equals(tc.getChangeId()))
		     tc.setChangeId(CodeGenerator.getUUID());
		storageProtocolChangeHibernate.saveOrUpdate(tc);
		return tc.getChangeId();
	}
	
	public int updateChange(TStorageProtocolChange tc){
		return storageProtocolJdbcDao.updateChange(tc);
	}
	
	public int deleteChange(String changeId){
		return storageProtocolJdbcDao.deleteChange(changeId);
	}
	
	public TStorageProtocolChange getChange(String changeId){
		return storageProtocolChangeHibernate.get(changeId);
	}
	
	
	public void submitChangeWorkflow(String taskId, TStorageProtocolChange change,TStorageProtocol info) {
		
		change.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("storage_protocol_modify_v1"));
			if(null==taskId||"".equals(taskId)){
				WorkflowService.createAndSignalProcessInstance(change, change.getChangeId());
				this.storageProtocolJdbcDao.submitUpdateChangeState(change.getChangeId(),"2");
				this.storageProtocolJdbcDao.submitUpdateInfoState(info.getInfoId(),"6");
			}else{
				WorkflowService.signalProcessInstance(change, change.getChangeId(), null);
			}
	}
	
	public void processChangeFailure(String businessRecordId,long processId)
	{   TStorageProtocolChange change =  this.getChange(businessRecordId);
		WorkflowService.updateProcessInstanceEndState(processId,"仓储协议变更申请未通过");
		this.storageProtocolJdbcDao.workflowChangeUpdateState(businessRecordId,"");
		this.storageProtocolJdbcDao.workflowChangeUpdatePState(change.getInfoId(),"");
	}
	
	
	public void processChangeSucessful(String businessRecordId,long processId)
	{   TStorageProtocolChange change =  this.getChange(businessRecordId);
		WorkflowService.updateProcessInstanceEndState(processId,"仓储协议变更申请通过");
		this.storageProtocolJdbcDao.workflowChangeUpdateState(businessRecordId,"pass");
		this.storageProtocolJdbcDao.workflowChangeUpdatePState(change.getInfoId(),"pass");
	}
	public void setProjectJdbcService(ProjectJdbcService projectJdbcService) {
		this.projectJdbcService = projectJdbcService;
	}
	public void fileProtocol(String idList){
		String[] toIdList = idList.split("\\|");
		for (int i = 0; i < toIdList.length; i++) {
			storageProtocolJdbcDao.fileProtocol(toIdList[i]);
		}
	}

}
