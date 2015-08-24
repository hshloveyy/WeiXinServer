/*
 * @(#)ContractHibernateService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2009-2-11
 *  描　述：创建
 */

package com.infolion.sapss.contract.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.sapss.contract.dao.ContractBomHibernateDao;
import com.infolion.sapss.contract.dao.ContractGroupHibernateDao;
import com.infolion.sapss.contract.dao.ContractPuMaterielCaseHibernateDao;
import com.infolion.sapss.contract.dao.ContractPurchaseInfoHibernateDao;
import com.infolion.sapss.contract.dao.ContractPurchaseMaterielHibernateDao;
import com.infolion.sapss.contract.dao.ContractSalesInfoHibernateDao;
import com.infolion.sapss.contract.dao.ContractSalesMaterielHibernateDao;
import com.infolion.sapss.contract.dao.ContractSeMaterielCaseHibernateDao;
import com.infolion.sapss.contract.dao.ContractAgentMaterielHibernateDao;
import com.infolion.sapss.contract.dao.ContractAgentMtCaseHibernateDao;
import com.infolion.sapss.contract.domain.TContractBom;
import com.infolion.sapss.contract.domain.TContractGroup;
import com.infolion.sapss.contract.domain.TContractPuMaterielCase;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractPurchaseMateriel;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.domain.TContractSalesMateriel;
import com.infolion.sapss.contract.domain.TContractSeMaterielCase;
import com.infolion.sapss.contract.domain.TContractAgentMateriel;
import com.infolion.sapss.contract.domain.TContractAgentMtCase;

@Service
public class ContractHibernateService extends BaseHibernateService {
	@Autowired
	ContractPurchaseInfoHibernateDao contractPurchaseInfoHibernateDao;
	
	@Autowired
	ContractPurchaseMaterielHibernateDao contractPurchaseMaterielHibernateDao;
	
	@Autowired
	ContractBomHibernateDao contractBomHibernateDao;
	
	@Autowired
	ContractPuMaterielCaseHibernateDao contractPuMaterielCaseHibernateDao;
	
	@Autowired
	ContractSalesInfoHibernateDao contractSalesInfoHibernateDao;
	
	@Autowired
	ContractSalesMaterielHibernateDao contractSalesMaterielHibernateDao;
	
	@Autowired
	ContractSeMaterielCaseHibernateDao contractSeMaterielCaseHibernateDao;
	
	@Autowired
	ContractAgentMaterielHibernateDao contractAgentMaterielHibernateDao;
	
	@Autowired
	ContractAgentMtCaseHibernateDao contractAgentMtCaseHibernateDao;
	
	@Autowired
	ContractGroupHibernateDao contractGroupHibernateDao;	
	
	public void setContractPurchaseInfoHibernateDao(
			ContractPurchaseInfoHibernateDao contractPurchaseInfoHibernateDao) {
		this.contractPurchaseInfoHibernateDao = contractPurchaseInfoHibernateDao;
	}
	
	public void setContractPurchaseMaterielHibernateDao(
			ContractPurchaseMaterielHibernateDao contractPurchaseMaterielHibernateDao) {
		this.contractPurchaseMaterielHibernateDao = contractPurchaseMaterielHibernateDao;
	}
	
	public void setContractBomHibernateDao(
			ContractBomHibernateDao contractBomHibernateDao) {
		this.contractBomHibernateDao = contractBomHibernateDao;
	}
	
	public void setContractPuMaterielCaseHibernateDao(
			ContractPuMaterielCaseHibernateDao contractPuMaterielCaseHibernateDao) {
		this.contractPuMaterielCaseHibernateDao = contractPuMaterielCaseHibernateDao;
	}
	
	public void setContractSalesInfoHibernateDao(
			ContractSalesInfoHibernateDao contractSalesInfoHibernateDao) {
		this.contractSalesInfoHibernateDao = contractSalesInfoHibernateDao;
	}
	
	public void setContractSalesMaterielHibernateDao(
			ContractSalesMaterielHibernateDao contractSalesMaterielHibernateDao) {
		this.contractSalesMaterielHibernateDao = contractSalesMaterielHibernateDao;
	}

	public void setContractSeMaterielCaseHibernateDao(
			ContractSeMaterielCaseHibernateDao contractSeMaterielCaseHibernateDao) {
		this.contractSeMaterielCaseHibernateDao = contractSeMaterielCaseHibernateDao;
	}
	
	public void setContractAgentMaterielHibernateDao(
			ContractAgentMaterielHibernateDao contractAgentMaterielHibernateDao) {
		this.contractAgentMaterielHibernateDao = contractAgentMaterielHibernateDao;
	}
	
	public void setContractAgentMtCaseHibernateDao(
			ContractAgentMtCaseHibernateDao contractAgentMtCaseHibernateDao) {
		this.contractAgentMtCaseHibernateDao = contractAgentMtCaseHibernateDao;
	}

	
	public void setContractGroupHibernateDao(
			ContractGroupHibernateDao contractGroupHibernateDao) {
		this.contractGroupHibernateDao = contractGroupHibernateDao;
	}

	@Transactional(readOnly = true)
	public void saveContractPurchaseInfo(TContractPurchaseInfo tContractPurchaseInfo){
		this.contractPurchaseInfoHibernateDao.saveOrUpdate(tContractPurchaseInfo);
	}
	
	public TContractPurchaseInfo queryContractPurchaseInfoById(String strContractId){
		return this.contractPurchaseInfoHibernateDao.get(strContractId);
	}
	
	public TContractSalesInfo queryContractSalesInfoById(String strContractId){
		return this.contractSalesInfoHibernateDao.get(strContractId);
	}
	public TContractPurchaseInfo loadTContractPurchaseInfo(String pid)
	{
		return this.contractPurchaseInfoHibernateDao.load(pid);
	}
	@Transactional(readOnly = true)
	public void saveContractPurchaseMaterielInfo(TContractPurchaseMateriel tContractPurchaseMateriel){
		contractPurchaseMaterielHibernateDao.saveOrUpdate(tContractPurchaseMateriel);
	}
	
	@Transactional(readOnly = true)
	public void saveBomMaterielInfo(TContractBom tContractBom){
		contractBomHibernateDao.saveOrUpdate(tContractBom);
	}

	@Transactional(readOnly = true)
	public void saveContractPuMaterielCaseInfo(TContractPuMaterielCase tContractPuMaterielCase){
		contractPuMaterielCaseHibernateDao.saveOrUpdate(tContractPuMaterielCase);
	}
	
	@Transactional(readOnly = true)
	public void saveContractSalesInfo(TContractSalesInfo tContractSalesInfo){
		this.contractSalesInfoHibernateDao.saveOrUpdate(tContractSalesInfo);
	}

	@Transactional(readOnly = true)
	public void saveContractSalesMateriel(TContractSalesMateriel tContractSalesMateriel){
		this.contractSalesMaterielHibernateDao.saveOrUpdate(tContractSalesMateriel);
	}
	
	@Transactional(readOnly = true)
	public void saveContractSeMaterielCase(TContractSeMaterielCase tContractSeMaterielCase){
		this.contractSeMaterielCaseHibernateDao.saveOrUpdate(tContractSeMaterielCase);
	}
	
	@Transactional(readOnly = true)
	public void saveContractAgentMaterier(TContractAgentMateriel tContractAgentMateriel){
		this.contractAgentMaterielHibernateDao.saveOrUpdate(tContractAgentMateriel);
	}
	
	@Transactional(readOnly = true)
	public void saveContractAgentMtCase(TContractAgentMtCase tContractAgentMtCase){
		this.contractAgentMtCaseHibernateDao.saveOrUpdate(tContractAgentMtCase);
	}	
	
	@Transactional(readOnly = true)
	public void deleteAgentMateriel(String IdList){
		String[] toIdList = IdList.split("\\|");
		for (int i=0;i<toIdList.length;i++){
			this.contractAgentMaterielHibernateDao.remove(this.contractAgentMaterielHibernateDao.get(toIdList[i]));
		}
	}
	
	@Transactional(readOnly = true)
	public void saveTContractAgentMtCase(TContractAgentMtCase tContractAgentMtCase){
		this.contractAgentMtCaseHibernateDao.saveOrUpdate(tContractAgentMtCase);
	}
	
	@Transactional(readOnly = true)
	public void deleteAgentMtCase(String IdList){
		String[] toIdList = IdList.split("\\|");
		for (int i=0;i<toIdList.length;i++){
			this.contractAgentMtCaseHibernateDao.remove(this.contractAgentMtCaseHibernateDao.get(toIdList[i]));
		}
	}
	
	/**
	 * 查询采购合同实体数据
	 * @param strContractPurchaseId
	 * @return
	 */
	public TContractPurchaseInfo queryPurchase(String strContractPurchaseId){
		TContractPurchaseInfo tContractPurchaseInfo = new TContractPurchaseInfo();
		tContractPurchaseInfo = this.contractPurchaseInfoHibernateDao.get(strContractPurchaseId);
		
		List<TContractPurchaseMateriel> tContractPurchaseMaterielList = new ArrayList();
		
		tContractPurchaseMaterielList = 
			contractPurchaseMaterielHibernateDao.find("from TContractPurchaseMateriel a where a.contractPurchaseId ='" + strContractPurchaseId + "' order by createTime desc");
		
		for (int i=0;i< tContractPurchaseMaterielList.size();i++){

			List<TContractPuMaterielCase> tContractPuMaterielCaseList = new ArrayList();
			
			tContractPuMaterielCaseList = 
				contractPuMaterielCaseHibernateDao.find("from TContractPuMaterielCase a where a.purchaseRowsId ='" + tContractPurchaseMaterielList.get(i).getPurchaseRowsId() + "'");
			
			List<TContractBom> tContractBomList  = new ArrayList();
			
			tContractBomList = contractBomHibernateDao.find("from TContractBom a where a.purchaseRowsId = '" + tContractPurchaseMaterielList.get(i).getPurchaseRowsId() + "'");
			
			tContractPurchaseMaterielList.get(i).setContractPuMaterielCases(tContractPuMaterielCaseList);
			
			tContractPurchaseMaterielList.get(i).setContractBoms(tContractBomList);
		}
		
		tContractPurchaseInfo.setContractPurchaseMateriels(tContractPurchaseMaterielList);
		
		return tContractPurchaseInfo;
	}
	
	public void saveContractPurchaseInfoAll(TContractPurchaseInfo info){
		contractPurchaseInfoHibernateDao.saveOrUpdate(info);
		List<TContractPurchaseMateriel> list = info.getContractPurchaseMateriels();
		for(Iterator<TContractPurchaseMateriel> it = list.iterator();it.hasNext();){
			TContractPurchaseMateriel m = it.next();
			m.setContractPurchaseId(info.getContractPurchaseId());
			m.setPurchaseRowsId(CodeGenerator.getUUID());
			m.setCreateTime(info.getCreateTime());
			m.setCreator(info.getCreator());
			m.setEkpoMenge(0d);
			contractPurchaseMaterielHibernateDao.saveOrUpdate(m);
			List<TContractBom> bomList = m.getContractBoms();
			for(Iterator<TContractBom> it1 = bomList.iterator();it1.hasNext();){
				TContractBom bom = it1.next();
				bom.setBomId(CodeGenerator.getUUID());
				bom.setCreateTime(info.getCreateTime());
				bom.setCreator(info.getCreator());
				bom.setEntryQuantity("0");
				contractBomHibernateDao.saveOrUpdate(bom);
			}
		}
	}
	
	public void saveContractSalesAll(TContractSalesInfo info){
		contractSalesInfoHibernateDao.saveOrUpdate(info);
		List<TContractSalesMateriel> list = info.getContractSalesMateriels();
		for(Iterator<TContractSalesMateriel> it = list.iterator();it.hasNext();){
			TContractSalesMateriel m =it.next();
			m.setContractSalesId(info.getContractSalesId());
			m.setSalesRowsId(CodeGenerator.getUUID());
			m.setCreateTime(info.getCreateTime());
			m.setCreator(info.getCreator());
			m.setVbapZmeng(new BigDecimal(0));
			contractSalesMaterielHibernateDao.saveOrUpdate(m);
		}
		
	}
	
		
	/**
	 * 查询采购合同实体数据
	 * @param strContractPurchaseId
	 * @return
	 */
	public TContractSalesInfo querySales(String strContractSalesId){
		TContractSalesInfo tContractSalesInfo = new TContractSalesInfo();
		tContractSalesInfo = this.contractSalesInfoHibernateDao.get(strContractSalesId);
		
		List<TContractSalesMateriel> tContractSalesMaterielList = new ArrayList();
		
		tContractSalesMaterielList = 
			contractSalesMaterielHibernateDao.find("from TContractSalesMateriel a where a.contractSalesId ='" + strContractSalesId + "' order by createTime desc");
		for (int i=0;i< tContractSalesMaterielList.size();i++){

			List<TContractSeMaterielCase> tContractSeMaterielCaseList = new ArrayList();
			
			tContractSeMaterielCaseList = 
				contractSeMaterielCaseHibernateDao.find("from TContractSeMaterielCase a where a.salesRowsId ='" + tContractSalesMaterielList.get(i).getSalesRowsId() + "'");				
			tContractSalesMaterielList.get(i).setContractSeMaterielCases(tContractSeMaterielCaseList);
		}
		
		tContractSalesInfo.setContractSalesMateriels(tContractSalesMaterielList);
		
		return tContractSalesInfo;
	}	
	
	/**
	 * 查询采购合同实体数据
	 * @param strContractPurchaseId
	 * @return
	 */
	public TContractPurchaseInfo getContractPurchaseInfo(String strContractPurchaseId){
		return  this.contractPurchaseInfoHibernateDao.get(strContractPurchaseId);	
	}
	
	/**
	 * 查询销售合同实体数据
	 * @param strContractSalesId
	 * @return
	 */
	public TContractSalesInfo getContractSalesInfo(String strContractSalesId){
		return this.contractSalesInfoHibernateDao.get(strContractSalesId);		
	}	
	
	/**
	 * 查询销售合同行数据实体数据
	 * @param strContractSalesId
	 * @return
	 */
	public TContractSalesMateriel getTContractSalesMateriel(String strContractSalesMaterielId){
		return this.contractSalesMaterielHibernateDao.get(strContractSalesMaterielId);	
	}	
	
	public TContractPurchaseMateriel getTContractPurcharseMateriel(String strContractPurcharseMaterielId){
		return this.contractPurchaseMaterielHibernateDao.get(strContractPurcharseMaterielId);
	}
	
	/**
	 * 查询合同组实体数据
	 * @param strContractPurchaseId
	 * @return
	 */
	public TContractGroup getContractGroup(String contractGroupId){
		TContractGroup tContractGroup = new TContractGroup();
		tContractGroup = this.contractGroupHibernateDao.get(contractGroupId);		
		return tContractGroup;
	}
	
	/**
	 * 
	 */
	public void updateContractPurcharseSapOrderNo(TContractPurchaseInfo info){
		this.contractPurchaseInfoHibernateDao.update(info);
	}

	public void updateContractSalesSapOrderNo(TContractSalesInfo info) {
		contractSalesInfoHibernateDao.saveOrUpdate(info);
		// TODO Auto-generated method stub
		
	}
	
}
