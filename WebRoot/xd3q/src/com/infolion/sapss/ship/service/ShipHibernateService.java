/*
 * @(#)SampleService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.ship.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.bapi.ShipReceiptUtils;
import com.infolion.sapss.common.WorkflowUtils;
import com.infolion.sapss.contract.dao.ContractAgentMaterielHibernateDao;
import com.infolion.sapss.contract.dao.ContractBomHibernateDao;
import com.infolion.sapss.contract.dao.ContractGroupHibernateDao;
import com.infolion.sapss.contract.dao.ContractPurchaseInfoHibernateDao;
import com.infolion.sapss.contract.dao.ContractPurchaseMaterielHibernateDao;
import com.infolion.sapss.contract.dao.ContractSalesInfoHibernateDao;
import com.infolion.sapss.contract.dao.ContractSalesMaterielHibernateDao;
import com.infolion.sapss.contract.domain.TContractAgentMateriel;
import com.infolion.sapss.contract.domain.TContractBom;
import com.infolion.sapss.contract.domain.TContractGroup;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractPurchaseMateriel;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.domain.TContractSalesMateriel;
import com.infolion.sapss.project.dao.ProjectInfoHibernate;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.receipts.domain.TReceiptInfo;
import com.infolion.sapss.ship.dao.ExportApplyHibernateDao;
import com.infolion.sapss.ship.dao.ExportApplyMaterialHibernateDao;
import com.infolion.sapss.ship.dao.ShipInfoHibernateDao;
import com.infolion.sapss.ship.dao.ShipInfoJdbcDao;
import com.infolion.sapss.ship.dao.ShipMaterialHibernateDao;
import com.infolion.sapss.ship.domain.ShipCcollect;
import com.infolion.sapss.ship.domain.TExportApplyMaterial;
import com.infolion.sapss.ship.domain.TShipInfo;
import com.infolion.sapss.ship.domain.TShipMaterial;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre>
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class ShipHibernateService extends BaseHibernateService
{
	@Autowired
	ExportApplyHibernateDao exportApplyHibernateDao;
	@Autowired
	ExportApplyMaterialHibernateDao exportApplyMaterialHibernateDao;
	@Autowired
	ContractSalesMaterielHibernateDao contractSalesMaterielHibernateDao;
	@Autowired
	ContractSalesInfoHibernateDao contractSalesInfoHibernateDao;
	@Autowired
	ContractPurchaseInfoHibernateDao contractPurchaseInfoHibernateDao;
	@Autowired
	ContractPurchaseMaterielHibernateDao contractPurchaseMaterielHibernateDao;
	@Autowired
	ContractBomHibernateDao contractBomHibernateDao;
	@Autowired
	ShipInfoHibernateDao shipInfoHibernateDao;
	@Autowired
	ShipMaterialHibernateDao shipMaterialHibernateDao;
	@Autowired
	ShipInfoJdbcDao shipInfoJdbcDao;
	@Autowired
	ContractGroupHibernateDao contractGroupHibernateDao;
	@Autowired
	ProjectInfoHibernate projectInfoHibernate;
	@Autowired
	private ContractAgentMaterielHibernateDao contractAgentMaterielHibernateDao;

	public void setExportApplyHibernateDao(ExportApplyHibernateDao exportApplyHibernateDao)
	{
		this.exportApplyHibernateDao = exportApplyHibernateDao;
	}

	public void setExportApplyMaterialHibernateDao(ExportApplyMaterialHibernateDao exportApplyMaterialHibernateDao)
	{
		this.exportApplyMaterialHibernateDao = exportApplyMaterialHibernateDao;
	}

	public void setContractSalesMaterielHibernateDao(ContractSalesMaterielHibernateDao contractSalesMaterielHibernateDao)
	{
		this.contractSalesMaterielHibernateDao = contractSalesMaterielHibernateDao;
	}

	public void setContractSalesInfoHibernateDao(ContractSalesInfoHibernateDao contractSalesInfoHibernateDao)
	{
		this.contractSalesInfoHibernateDao = contractSalesInfoHibernateDao;
	}

	public void setShipInfoHibernateDao(ShipInfoHibernateDao shipInfoHibernateDao)
	{
		this.shipInfoHibernateDao = shipInfoHibernateDao;
	}

	public void setShipMaterialHibernateDao(ShipMaterialHibernateDao shipMaterialHibernateDao)
	{
		this.shipMaterialHibernateDao = shipMaterialHibernateDao;
	}

	public void saveShipInfo(TShipInfo tShipInfo)
	{

		TShipInfo oldTshipInfo = this.shipInfoHibernateDao.getDetached(tShipInfo.getShipId());
		boolean changeIgTime = false;

		if (null != tShipInfo.getIntendGatherTime() && !tShipInfo.getIntendGatherTime().equals(oldTshipInfo.getIntendGatherTime()))
		{
			changeIgTime = true;
		}

		boolean changeMiTime = false;
		if (null != tShipInfo.getMakeInvoiceTime() && !tShipInfo.getMakeInvoiceTime().equals(oldTshipInfo.getMakeInvoiceTime()))
		{
			changeMiTime = true;
		}
		checkShipInfo(tShipInfo);
		this.shipInfoJdbcDao.updateShipMaterielDateFields(tShipInfo, changeIgTime, changeMiTime);
		this.shipInfoHibernateDao.saveOrUpdate(tShipInfo);
	}
	
	public void checkShipInfo(TShipInfo tShipInfo){
		
		List<TShipMaterial> tShipMaterialList = shipMaterialHibernateDao.find("from TShipMaterial a where a.shipId ='" + tShipInfo.getShipId() + "'");
		for (int i = 0; i < tShipMaterialList.size(); i++)
		{
			TShipMaterial tShipMaterial = (TShipMaterial) tShipMaterialList.get(i);
			//getShipQuality(String materialCode, String warehouse, String batchNo,String shipDetailId,String deptId){
			BigDecimal value = shipInfoJdbcDao.getShipQuality(tShipMaterial.getMaterialCode(), tShipInfo.getWarehouse(), tShipMaterial.getBatchNo(), tShipMaterial.getShipDetailId(), tShipInfo.getDeptId());
		    if(value.compareTo(tShipMaterial.getQuantity())<0) 
		    	throw new com.infolion.platform.dpframework.core.BusinessException("物料："+tShipMaterial.getMaterialCode()+"库存不足，请检查!");
		    	
		}
	}

	public void saveShipMaterial(TShipMaterial tShipMaterial)
	{
		this.shipMaterialHibernateDao.saveOrUpdate(tShipMaterial);
	}

	public void deleteShipMaterial(String shipDetailId)
	{
		String[] ids = shipDetailId.split(",");
		for(String id:ids){
			if(id==null) continue;
			TShipMaterial tShipMaterial = this.shipMaterialHibernateDao.get(id);
			this.shipMaterialHibernateDao.remove(tShipMaterial);
		}

	}

	public TShipInfo getShipInfo(String shipId)
	{
		return this.shipInfoHibernateDao.get(shipId);
	}

	public TShipMaterial getShipMaterial(String shipDetailId)
	{
		return this.shipMaterialHibernateDao.get(shipDetailId);
	}

	public TContractSalesMateriel getTContractSalesMateriel(String salesRowsId)
	{
		return this.contractSalesMaterielHibernateDao.get(salesRowsId);
	}

	public TShipInfo addShipInfo(String tradeType, String isProduct,String isHome)
	{
		TShipInfo tShipInfo = new TShipInfo();
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

		tShipInfo.setTradeType(tradeType);
		tShipInfo.setIsProduct(isProduct);
		tShipInfo.setIsHome(isHome);
		tShipInfo.setSerialNo(shipInfoJdbcDao.getSerialNo());//
		tShipInfo.setIsAvailable("1");
		tShipInfo.setCreator(userContext.getSysUser().getUserId());
		tShipInfo.setCreatorTime(DateUtils.getCurrDate(DateUtils.HYPHEN_DISPLAY_DATE));
		tShipInfo.setShipId(CodeGenerator.getUUID());
		tShipInfo.setDeptId(userContext.getSysDept().getDeptid());
		tShipInfo.setExamineState("1");
		tShipInfo.setCreateTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.shipInfoHibernateDao.save(tShipInfo);
		return tShipInfo;

	}

	/**
	 * 初始化出仓单
	 * 
	 * @param shipId
	 * @param exportApplyId
	 * @param contractSalesId
	 */
	public void initShipInfo(String shipId, String exportApplyId, String contractSalesId, String intendGatherTime, String makeInvoiceTime)
	{
		TShipInfo tShipInfo = this.getShipInfo(shipId);
		if (StringUtils.isNotBlank(exportApplyId))
		{
			tShipInfo.setExportApplyId(exportApplyId);
		}
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

		TContractSalesInfo tContractSalesInfo = contractSalesInfoHibernateDao.get(contractSalesId);
		//赋值
		if (tContractSalesInfo != null)
		{
			// 通过合同赋值
			tShipInfo.setSalesNo(tContractSalesInfo.getContractNo());
			tShipInfo.setContractSalesId(tContractSalesInfo.getContractSalesId());
			tShipInfo.setSapOrderNo(tContractSalesInfo.getSapOrderNo());
			tShipInfo.setContractPaperNo(tContractSalesInfo.getVbkdIhrez());
			tShipInfo.setUnitName(tContractSalesInfo.getKuwevKunnrName());
			// 获的合同组
			TContractGroup tContractGroup = contractGroupHibernateDao.get(tContractSalesInfo.getContractGroupId());
			tShipInfo.setContractGroupNo(tContractGroup.getContractGroupNo());
			// 获得立项
			TProjectInfo tProjectInfo = projectInfoHibernate.get(tContractSalesInfo.getProjectId());
			tShipInfo.setProjectNo(tProjectInfo.getProjectNo());
			tShipInfo.setProjectName(tProjectInfo.getProjectName());
		}
		this.saveShipInfo(tShipInfo);

		// 先删除存在的出仓单物料信息
		List<TShipMaterial> tShipMaterialList = new ArrayList();
		tShipMaterialList = shipMaterialHibernateDao.find("from TShipMaterial a where a.shipId ='" + shipId + "'");
		for (int i = 0; i < tShipMaterialList.size(); i++)
		{
			TShipMaterial tShipMaterial = (TShipMaterial) tShipMaterialList.get(i);
			shipMaterialHibernateDao.remove(tShipMaterial);
		}
		// 如果有出货通知单则取出货通知单的物料信息
		if (StringUtils.isNotBlank(exportApplyId))
		{
			List<TExportApplyMaterial> tExportApplyMaterialList = new ArrayList();
			tExportApplyMaterialList = exportApplyMaterialHibernateDao.find("from TExportApplyMaterial a where a.exportApplyId ='" + exportApplyId + "'");
			for (int i = 0; i < tExportApplyMaterialList.size(); i++)
			{
				TExportApplyMaterial tExportApplyMaterial = (TExportApplyMaterial) tExportApplyMaterialList.get(i);
				/*二次结算后不做该校验，数量直接标0
				BigDecimal hasQuantity = shipInfoJdbcDao.getApplyHasQuantity(tExportApplyMaterial.getExportMateId());
				if (hasQuantity.signum() == 1)
				{
					this.addMtByExportApplyMt(shipId, tExportApplyMaterial, hasQuantity, intendGatherTime, makeInvoiceTime);
				}
				*/
				this.addMtByExportApplyMt(shipId, tExportApplyMaterial, BigDecimal.ZERO, intendGatherTime, makeInvoiceTime);
			}
		}
		else if (tContractSalesInfo != null)
		{
			// 从销售合同物料取物料信息
			List<TContractSalesMateriel> tContractSalesMaterielList = new ArrayList();
			tContractSalesMaterielList = contractSalesMaterielHibernateDao.find("from TContractSalesMateriel a where a.contractSalesId ='" + contractSalesId + "'");
			for (int i = 0; i < tContractSalesMaterielList.size(); i++)
			{
				TContractSalesMateriel tContractSalesMateriel = tContractSalesMaterielList.get(i);
				/*二次结算后不做该剩余数量校验，数量直接标0
				BigDecimal hasQuantity = shipInfoJdbcDao.getSalesHasQuantity(tContractSalesMateriel.getSalesRowsId());
				if (hasQuantity.signum() == 1)
				{
					this.addMtBySalesMt(shipId, tContractSalesInfo, tContractSalesMateriel, hasQuantity, intendGatherTime, makeInvoiceTime);
				}
				*/
				this.addMtBySalesMt(shipId, tContractSalesInfo, tContractSalesMateriel, BigDecimal.ZERO, intendGatherTime, makeInvoiceTime);

			}
		}

		/** 处理清帐自己动分配 ****/
		/*
		 * tShipMaterialList =
		 * shipMaterialHibernateDao.find("from TShipMaterial a where a.shipId ='"
		 * + shipId + "'"); for (int i = 0; i < tShipMaterialList.size(); i++) {
		 * TShipMaterial tShipMaterial = (TShipMaterial)
		 * tShipMaterialList.get(i); autoAssignCharge(tShipMaterial); }
		 */
		/** 处理清帐自己动分配 ****/
	}

	/**
	 * 自动分配金额
	 * 
	 * @param tShipMaterial
	 */
	public void autoAssignCharge(TShipMaterial tShipMaterial)
	{
		BigDecimal saleTotal = tShipMaterial.getSaleTotal();
		TShipInfo tShipInfo = shipInfoHibernateDao.get(tShipMaterial.getShipId());

		ShipCcollect shipCcollect = new ShipCcollect();
		shipCcollect.setShip_detail_id(tShipMaterial.getShipDetailId());
		shipCcollect.setVoucherno("");
		shipCcollect.setProject_no("");
		shipCcollect.setContract_no("");
		shipCcollect.setCollectid("");
		shipCcollect.setCollectno("");
		shipCcollect.setCollectstate("");
		shipCcollect.setArrivegoodsdate("");
		// shipCcollect.setCollectamount(collectamount);
		shipCcollect.setCurrency("");
		// shipCcollect.setExchangerate("");
		// shipCcollect.setOffsetamount(offsetamount);
		// shipCcollect.setUnoffsetamount(unoffsetamount);
		// shipCcollect.setOnroadamount(onroadamount);
		// shipCcollect.setNowoffsetamount(nowoffsetamount);
		// shipCcollect.setClearamount(clearamount);
	}

	/**
	 * 通过出货通知单增加出仓物料信息
	 * 
	 * @param shipId
	 * @param tExportApplyMaterial
	 * @return
	 */
	public TShipMaterial addMtByExportApplyMt(String shipId, TExportApplyMaterial tExportApplyMaterial, BigDecimal hasQuantity, String intendGatherTime, String makeInvoiceTime)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		TShipMaterial tShipMaterial = new TShipMaterial();
		tShipMaterial.setShipDetailId(CodeGenerator.getUUID());
		tShipMaterial.setShipId(shipId);
		tShipMaterial.setExportMateId(tExportApplyMaterial.getExportMateId()); // 关联申请单物料ID
		tShipMaterial.setSalesRowsId(tExportApplyMaterial.getSalesRowsId());
		tShipMaterial.setMaterialCode(tExportApplyMaterial.getMaterialCode());
		tShipMaterial.setMaterialName(tExportApplyMaterial.getMaterialName());
		tShipMaterial.setMaterialUnit(tExportApplyMaterial.getMaterialUnit());
		//tShipMaterial.setPrice(tExportApplyMaterial.getPrice());
		//tShipMaterial.setEkpoPeinh(tExportApplyMaterial.getPeinh());// 条件定价单位
		// tShipMaterial.setQuantity(tExportApplyMaterial.getQuantity());
		tShipMaterial.setQuantity(hasQuantity);
		//tShipMaterial.setCurrency(tExportApplyMaterial.getCurrency());
		// tShipMaterial.setTotal(tExportApplyMaterial.getTotal());
		//tShipMaterial.setTotal(tExportApplyMaterial.getQuantity().multiply(new BigDecimal(tExportApplyMaterial.getPrice() == null ? 0 : tExportApplyMaterial.getPrice())).divide(new BigDecimal(tExportApplyMaterial.getPeinh())));

		tShipMaterial.setIsAvailabel("1");
		tShipMaterial.setCreatorTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));

		tShipMaterial.setCreator(userContext.getSysUser().getUserId());
		tShipMaterial.setSapRowNo(tExportApplyMaterial.getSapRowNo());
		tShipMaterial.setVbapKpein(tExportApplyMaterial.getPeinh());
		tShipMaterial.setSaleCurrency(tExportApplyMaterial.getCurrency());
		tShipMaterial.setSalePrice(tExportApplyMaterial.getPrice());
		tShipMaterial.setSaleTotal(tExportApplyMaterial.getQuantity().multiply(new BigDecimal(tExportApplyMaterial.getPrice() == null ? 0 : tExportApplyMaterial.getPrice())).divide(new BigDecimal(tExportApplyMaterial.getPeinh())));
		tShipMaterial.setMakeInvoiceTime(makeInvoiceTime);
		tShipMaterial.setIntendGatherTime(intendGatherTime);

		shipMaterialHibernateDao.save(tShipMaterial);
		return tShipMaterial;
	}

	/**
	 * 通过合同增加出仓物料信息
	 * 
	 * @param shipId
	 * @param tContractSalesInfo
	 * @param tContractSalesMateriel
	 * @return
	 */
	public TShipMaterial addMtBySalesMt(String shipId, TContractSalesInfo tContractSalesInfo, TContractSalesMateriel tContractSalesMateriel, BigDecimal hasQuantity, String intendGatherTime, String makeInvoiceTime)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		TShipMaterial tShipMaterial = new TShipMaterial();
		tShipMaterial.setShipDetailId(CodeGenerator.getUUID());
		tShipMaterial.setShipId(shipId);
		tShipMaterial.setSalesRowsId(tContractSalesMateriel.getSalesRowsId()); // 关联合同物料行号
		tShipMaterial.setMaterialCode(tContractSalesMateriel.getVbapMatnr()); // 物料号
		tShipMaterial.setMaterialName(tContractSalesMateriel.getVbapArktx()); // 物料名称
		tShipMaterial.setMaterialUnit(tContractSalesMateriel.getVbapVrkme()); // 单位VBAP_VRKME
		//tShipMaterial.setPrice(tContractSalesMateriel.getKonvKbetr());
		// tShipMaterial.setQuantity(tContractSalesMateriel.getVbapZmeng());
		tShipMaterial.setQuantity(hasQuantity);
		//tShipMaterial.setCurrency(tContractSalesInfo.getVbakWaerk()); // 货币VBAK_WAERK
		//tShipMaterial.setEkpoPeinh(tContractSalesMateriel.getVbapKpein());// 条件定价单位
		hasQuantity = hasQuantity == null ? new BigDecimal(0) : hasQuantity;
		//tShipMaterial.setTotal(hasQuantity.multiply(new BigDecimal(tContractSalesMateriel.getKonvKbetr() == null ? 0 : tContractSalesMateriel.getKonvKbetr())).divide(new BigDecimal(tContractSalesMateriel.getVbapKpein())));
		tShipMaterial.setIsAvailabel("1");
		tShipMaterial.setCreatorTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));

		tShipMaterial.setCreator(userContext.getSysUser().getUserId());
		tShipMaterial.setSapRowNo(tContractSalesMateriel.getSapRowNo());

		tShipMaterial.setSalePrice(tContractSalesMateriel.getKonvKbetr());
		tShipMaterial.setSaleCurrency(tContractSalesInfo.getVbakWaerk()); // 销售 货币VBAK_WAERK
		tShipMaterial.setVbapKpein(tContractSalesMateriel.getVbapKpein());// 销售 条件定价单位
		tShipMaterial.setSaleTotal(hasQuantity.multiply(new BigDecimal(tContractSalesMateriel.getKonvKbetr() == null ? 0 : tContractSalesMateriel.getKonvKbetr())).divide(new BigDecimal(tContractSalesMateriel.getVbapKpein())));

		tShipMaterial.setMakeInvoiceTime(makeInvoiceTime);
		tShipMaterial.setIntendGatherTime(intendGatherTime);

		shipMaterialHibernateDao.save(tShipMaterial);
		return tShipMaterial;
	}

	/**
	 * 代理物料
	 * 
	 * @param shipId
	 * @param tContractSalesInfo
	 * @param tContractSalesMateriel
	 * @param hasQuantity
	 * @return
	 */
	public TShipMaterial addMtBySalesMt(String shipId, TContractSalesInfo tContractSalesInfo, TContractAgentMateriel tContractSalesMateriel, BigDecimal hasQuantity)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		TShipMaterial tShipMaterial = new TShipMaterial();
		tShipMaterial.setShipDetailId(CodeGenerator.getUUID());
		tShipMaterial.setShipId(shipId);
		tShipMaterial.setSalesRowsId(tContractSalesMateriel.getSalesRowsId()); // 关联合同物料行号
		tShipMaterial.setMaterialCode(tContractSalesMateriel.getVbapMatnr()); // 物料号
		tShipMaterial.setMaterialName(tContractSalesMateriel.getVbapArktx()); // 物料名称
		tShipMaterial.setMaterialUnit(tContractSalesMateriel.getVbapVrkme()); // 单位VBAP_VRKME
		tShipMaterial.setPrice(tContractSalesMateriel.getKonvKbetr());
		// tShipMaterial.setQuantity(tContractSalesMateriel.getVbapZmeng());
		tShipMaterial.setQuantity(hasQuantity);
		tShipMaterial.setCurrency(tContractSalesInfo.getVbakWaerk()); // 货币VBAK_WAERK
		tShipMaterial.setEkpoPeinh(tContractSalesMateriel.getVbapKpein());// 条件定价单位
		hasQuantity = hasQuantity == null ? new BigDecimal(0) : hasQuantity;
		tShipMaterial.setTotal(hasQuantity.multiply(new BigDecimal(tContractSalesMateriel.getKonvKbetr() == null ? 0 : tContractSalesMateriel.getKonvKbetr())).divide(new BigDecimal(tContractSalesMateriel.getVbapKpein())));
		tShipMaterial.setIsAvailabel("1");
		tShipMaterial.setCreatorTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));

		tShipMaterial.setCreator(userContext.getSysUser().getUserId());
		shipMaterialHibernateDao.save(tShipMaterial);
		return tShipMaterial;
	}

	/**
	 * 根据参数条件新增出仓物料信息
	 * 
	 * @param shipId
	 * @param exportApplyId
	 * @param contractSalesId
	 * @param mtRowsId
	 * @return
	 */
	public TShipMaterial addMtByCon(String shipId, String exportApplyId, String contractSalesId, String mtRowsId, String hasQuantity, String tradeType, String intendGatherTime, String makeInvoiceTime)
	{
		TShipMaterial tShipMaterial = new TShipMaterial();
		BigDecimal quantity;
		if (hasQuantity == null)
			quantity = BigDecimal.valueOf(0);
		else
			quantity = BigDecimal.valueOf(Double.parseDouble(hasQuantity));
		// 如果有出货通知单则取出货通知单的物料信息
		if (StringUtils.isNotBlank(exportApplyId))
		{
			TExportApplyMaterial tExportApplyMaterial = exportApplyMaterialHibernateDao.get(mtRowsId);
			tShipMaterial = this.addMtByExportApplyMt(shipId, tExportApplyMaterial, quantity, intendGatherTime, makeInvoiceTime);
		}
		else
		{
			TContractSalesInfo tContractSalesInfo = contractSalesInfoHibernateDao.get(contractSalesId);
			TContractSalesMateriel tContractSalesMateriel = contractSalesMaterielHibernateDao.get(mtRowsId);
			//
			if (tContractSalesMateriel != null)
				tShipMaterial = this.addMtBySalesMt(shipId, tContractSalesInfo, tContractSalesMateriel, quantity, intendGatherTime, makeInvoiceTime);
			else
			{// 找代理物料
				TContractAgentMateriel agentMaterial = contractAgentMaterielHibernateDao.get(mtRowsId);
				tShipMaterial = this.addMtBySalesMt(shipId, tContractSalesInfo, agentMaterial, quantity);
			}
			// 代理物料
			// if(tradeType!=null
			// &&("5".equals(tradeType)||"6".equals(tradeType))){
			// TContractAgentMateriel agentMaterial =
			// contractAgentMaterielHibernateDao.get(mtRowsId);
			// tShipMaterial = this.addMtBySalesMt(shipId,
			// tContractSalesInfo,agentMaterial, quantity);
		}
		return tShipMaterial;
	}

	/**
	 * 保存并提交出仓单
	 * 
	 * @param taskId
	 * @param tExportApply
	 */
	public void submitAndSaveShipInfo(String taskId, TShipInfo tShipInfo)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		tShipInfo.setExamineState("2");
		tShipInfo.setApplyTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.saveShipInfo(tShipInfo);

		String workflowBusinessNote = ((StringUtils.isBlank(tShipInfo.getOldShipId()))?"":"出仓冲销|")+userContext.getSysDept().getDeptname() + "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "序号：" + tShipInfo.getSerialNo() + 
		                       "|外合同号：" + tShipInfo.getContractPaperNo();
		tShipInfo.setWorkflowBusinessNote(workflowBusinessNote);

		this.submitShipInfo(taskId, tShipInfo);
	}

	/**
	 * 提交出仓单
	 * 
	 * @param taskId
	 * @param tShipInfo
	 */
	public void submitShipInfo(String taskId, TShipInfo tShipInfo)
	{
		if (taskId != null)
		{
			TaskInstanceContext taskInstanceContext = WorkflowService.getTaskInstanceContext(taskId);
			String taskName = taskInstanceContext.getTaskName();

			if (taskName != null && taskName.indexOf("仓管员") != -1 && taskName.indexOf("*") == -1)
			{
				if (tShipInfo.getSapReturnNo() == null || "".equals(tShipInfo.getSapReturnNo()))
					throw new BusinessException("SAP交货单号,须填写");
				else
					this.shipInfoJdbcDao.updateShipInfo(tShipInfo.getShipId(), "SAP_RETURN_NO", tShipInfo.getSapReturnNo());
			}
		}
		Map parameters = new HashMap();
		//tShipInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("consignment_import_home_v1"));
		// 如果有出货通知单的走另一流程
		//if (StringUtils.isNotBlank(tShipInfo.getExportApplyId()))
		//{
			tShipInfo.setWorkflowProcessName(WorkflowUtils.chooseWorkflowName("consignment_import_home_v1"));
		//}

		BigDecimal bd = shipInfoJdbcDao.getShipInfoTotal(tShipInfo.getShipId());
		String currency = shipInfoJdbcDao.getShipCurrency(tShipInfo.getShipId());

		parameters.put("_workflow_currency", currency);
		if (!"USD".equals(currency) && !"CNY".equals(currency))
		{
			String cr = this.shipInfoJdbcDao.getCurrentRate(currency);
			BigDecimal rate = new BigDecimal(cr);
			bd = bd.multiply(rate);
		}
		parameters.put("_workflow_total_value", bd.divide(new BigDecimal(10000), 2, RoundingMode.UP));

		parameters.put("wareHouse", tShipInfo.getWarehouse());
		parameters.put("wareHouseAdd", tShipInfo.getWarehouseAdd());
		parameters.put("shipNote", tShipInfo.getShipNote());
		parameters.put("isHasInv", tShipInfo.getIsHasInv());
		parameters.put("oldShipId", tShipInfo.getOldShipId());
		tShipInfo.setWorkflowUserDefineTaskVariable(parameters);
		if (null == taskId || "".equals(taskId))
		{
			WorkflowService.createAndSignalProcessInstance(tShipInfo, tShipInfo.getShipId());
		}
		else
		{
			WorkflowService.signalProcessInstance(tShipInfo, tShipInfo.getShipId(), null);
		}
	}

	/**
	 * 提交出仓单条件检查
	 * 
	 * @param taskId
	 * @param taskName
	 * @param tExportApply
	 * @throws BusinessException
	 */
	public void checkSubmit(String taskId, String taskName, TShipInfo tShipInfo) throws BusinessException
	{

		String error = "";
		if (taskName.equals("已在SAP系统做相关出库操作"))
		{
			if (StringUtils.isBlank(tShipInfo.getWarehouse()))
			{
				error = "出仓仓库没有选择!";
			}
			if (StringUtils.isBlank(tShipInfo.getWarehouseAdd()))
			{
				error += "仓库地址没有输入!";
			}
			if (StringUtils.isBlank(tShipInfo.getShipNote()))
			{
				error += "出仓执行意见没有输入!";
			}
		}
		if (!"".equals(error))
		{
			throw new BusinessException(error);
		}

	}

	/**
	 * 流程回调更新出仓单状态
	 */
	public void updateBusinessRecord(String businessRecordId, String examineResult, String sapOrderNo)
	{
		String examineState = "";
		if (ProcessCallBack.EXAMINE_SUCCESSFUL.equals(examineResult))
		{
			examineState = "3";
		}
		else if (ProcessCallBack.NO_EXAMINE.equals(examineResult))
		{
			examineState = "5";
		}
		else
		{
			examineState = "4";
		}
		TShipInfo tShipInfo = this.getShipInfo(businessRecordId);
		tShipInfo.setExamineState(examineState);
		tShipInfo.setApprovedTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		this.saveShipInfo(tShipInfo);
	}

	public void updateSuccessFulRecord(String businessRecordId, String examineResult, String wareHouse, String wareHouseAdd, String shipOperator, String shipNote)
	{
		String examineState = "3";

		TShipInfo tShipInfo = this.getShipInfo(businessRecordId);
		tShipInfo.setExamineState(examineState);
		tShipInfo.setApprovedTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		// 审批通过填写出仓仓库,出仓人信息
		tShipInfo.setWarehouse(wareHouse);
		tShipInfo.setWarehouseAdd(wareHouseAdd);
		tShipInfo.setShipOperator(shipOperator);
		tShipInfo.setShipNote(shipNote);
		if(StringUtils.isBlank(tShipInfo.getShipNo())){
			tShipInfo.setShipNo(shipInfoJdbcDao.getShipNo(shipInfoJdbcDao.queryDeptCode(businessRecordId).substring(0, 2)));
		}
		// tShipInfo.setShipTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		if(StringUtils.isNotBlank(tShipInfo.getOldShipId())){
			tShipInfo.setBillState("1");
			TShipInfo oldShipInfo = this.getShipInfo(tShipInfo.getOldShipId());
			oldShipInfo.setBillState("1");
			this.saveShipInfo(oldShipInfo);
		}
		this.saveShipInfo(tShipInfo);
		// this.shipInfoHibernateDao.update(tShipInfo) ;
		shipInfoJdbcDao.updateSuccess(tShipInfo);
	}

	/**
	 * 初始化原材料出仓单
	 * 
	 * @param shipId
	 * @param contractPurchaseId
	 */
	public void initShipBOMInfo(String shipId, String contractPurchaseId, String intendGatherTime, String makeInvoiceTime)
	{
		TShipInfo tShipInfo = this.getShipInfo(shipId);

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

		TContractPurchaseInfo tContractPurchaseInfo = contractPurchaseInfoHibernateDao.get(contractPurchaseId);
		// 通过合同赋值
		tShipInfo.setSalesNo(tContractPurchaseInfo.getContractNo());
		//tShipInfo.setContractSalesId(tContractPurchaseInfo.getContractPurchaseId());
		tShipInfo.setContractPurchaseId(tContractPurchaseInfo.getContractPurchaseId());
		tShipInfo.setSapOrderNo(tContractPurchaseInfo.getSapOrderNo());
		tShipInfo.setContractPaperNo(tContractPurchaseInfo.getEkkoUnsez());
		tShipInfo.setUnitName(tContractPurchaseInfo.getEkkoLifnrName());

		// 获的合同组
		TContractGroup tContractGroup = contractGroupHibernateDao.get(tContractPurchaseInfo.getContractGroupId());
		tShipInfo.setContractGroupNo(tContractGroup.getContractGroupNo());
		// 获得立项
		TProjectInfo tProjectInfo = projectInfoHibernate.get(tContractPurchaseInfo.getProjectId());
		tShipInfo.setProjectNo(tProjectInfo.getProjectNo());
		tShipInfo.setProjectName(tProjectInfo.getProjectName());
		this.saveShipInfo(tShipInfo);

		// 先删除存在的出仓单物料信息
		List<TShipMaterial> tShipMaterialList = new ArrayList();
		tShipMaterialList = shipMaterialHibernateDao.find("from TShipMaterial a where a.shipId ='" + shipId + "'");
		for (int i = 0; i < tShipMaterialList.size(); i++)
		{
			TShipMaterial tShipMaterial = (TShipMaterial) tShipMaterialList.get(i);
			shipMaterialHibernateDao.remove(tShipMaterial);
		}

		// 从采购合同物料取物料信息

		List<TContractPurchaseMateriel> tContractPurchaseMaterielList = new ArrayList();
		tContractPurchaseMaterielList = contractBomHibernateDao.find("from TContractPurchaseMateriel a where a.contractPurchaseId ='" + contractPurchaseId + "'");
		for (int i = 0; i < tContractPurchaseMaterielList.size(); i++)
		{
			TContractPurchaseMateriel tContractPurchaseMateriel = tContractPurchaseMaterielList.get(i);

			List<TContractBom> tContractBomList = new ArrayList();
			tContractBomList = contractBomHibernateDao.find("from TContractBom a where a.purchaseRowsId ='" + tContractPurchaseMateriel.getPurchaseRowsId() + "'");
			for (int j = 0; j < tContractBomList.size(); j++)
			{
				TContractBom tContractBom = tContractBomList.get(j);
				/*二次结算取消校验，统一为0
				BigDecimal hasQuantity = shipInfoJdbcDao.getPurchaseHasQuantity(tContractBom.getBomId(), tShipInfo.getShipId(), "");
				if (hasQuantity.signum() == 1)
				{
					this.addMtByPurchaseMt(shipId, tContractPurchaseInfo, tContractPurchaseMateriel, tContractBom, "init", intendGatherTime, makeInvoiceTime);
				}*/
				this.addMtByPurchaseMt(shipId, tContractPurchaseInfo, tContractPurchaseMateriel, tContractBom, "init", intendGatherTime, makeInvoiceTime);
			}

		}

	}

	/**
	 * 通过合同增加出仓物料信息
	 * 
	 * @param shipId
	 * @param tContractPurchaseInfo
	 * @param tContractPurchaseMateriel
	 * @return
	 */
	public TShipMaterial addMtByPurchaseMt(String shipId, TContractPurchaseInfo tContractPurchaseInfo, TContractPurchaseMateriel tContractPurchaseMateriel, TContractBom tContractBom, String operType, String intendGatherTime, String makeInvoiceTime)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		TShipMaterial tShipMaterial = new TShipMaterial();
		tShipMaterial.setShipDetailId(CodeGenerator.getUUID());
		tShipMaterial.setShipId(shipId);
		tShipMaterial.setPurchaseRowsId(tContractPurchaseMateriel.getPurchaseRowsId()); // 关联合同物料行号
		tShipMaterial.setBomId(tContractBom.getBomId()); // 关联BOMID
		tShipMaterial.setMaterialCode(tContractBom.getMateriel()); // 物料号
		tShipMaterial.setMaterialName(tContractBom.getMaterielName()); // 物料名称
		tShipMaterial.setMaterialUnit(tContractBom.getEntryUom()); // 单位VBAP_VRKME
		tShipMaterial.setEkpoPeinh(tContractPurchaseMateriel.getEkpoPeinh()); // 条件定价单位

		// double hasQuantity=Double.valueOf(tContractBom.getEntryQuantity())
		// *tContractPurchaseMateriel.getEkpoMenge();
		String bomId = tContractBom.getBomId();
		BigDecimal hasQuantity = BigDecimal.ZERO;
		/**20120607 不做计算，统一为0
		// 是否为初始化物料信息,
		
		if (operType == "init")
			hasQuantity = shipInfoJdbcDao.getPurchaseHasQuantity(bomId, shipId, "");
		else
			hasQuantity = shipInfoJdbcDao.getPurchaseHasQuantity(bomId, "", "");

		if (hasQuantity.signum() == -1)
			hasQuantity = BigDecimal.valueOf(Double.valueOf(0));
	    **/

		tShipMaterial.setQuantity(hasQuantity);

		tShipMaterial.setCurrency(tContractPurchaseInfo.getEkkoWaers()); // 货币EKKO_WAERS
		tShipMaterial.setSaleCurrency(tContractPurchaseInfo.getEkkoWaers());
		tShipMaterial.setVbapKpein(tContractPurchaseMateriel.getEkpoPeinh());
		tShipMaterial.setPrice(Double.valueOf(tContractPurchaseMateriel.getPrice()));
		hasQuantity = hasQuantity == null ? new BigDecimal(0) : hasQuantity;
		// 单价*数量/条件定价单位
		tShipMaterial.setTotal(hasQuantity.multiply(new BigDecimal(tContractPurchaseMateriel.getPrice())).divide(new BigDecimal(tContractPurchaseMateriel.getEkpoPeinh() == null ? "1" : tContractPurchaseMateriel.getEkpoPeinh())));

		tShipMaterial.setCmd(tContractBom.getCmd()); // 备注

		tShipMaterial.setIsAvailabel("1");
		tShipMaterial.setCreatorTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));

		tShipMaterial.setCreator(userContext.getSysUser().getUserId());

		tShipMaterial.setSalePrice(Double.valueOf(tContractPurchaseMateriel.getPrice()));
		tShipMaterial.setSaleTotal(hasQuantity.multiply(new BigDecimal(tContractPurchaseMateriel.getPrice())).divide(new BigDecimal(tContractPurchaseMateriel.getEkpoPeinh() == null ? "1" : tContractPurchaseMateriel.getEkpoPeinh())));
		tShipMaterial.setIntendGatherTime(intendGatherTime);
		tShipMaterial.setMakeInvoiceTime(makeInvoiceTime);

		shipMaterialHibernateDao.save(tShipMaterial);
		return tShipMaterial;
	}

	/**
	 * 查询库存数量
	 * 
	 * @param materialCode
	 * @param wareHouse
	 * @param batchNo
	 */
	public Map queryInventoryNum(String materialCode, String warehouse, String batchNo, String shipId, String shipDetailId,String deptId,String examState)
	{
		return shipInfoJdbcDao.queryInventoryNum(materialCode, warehouse, batchNo, shipId, shipDetailId,deptId,examState);
	}
	
	/**
	 * 查询库存数量20120607
	 * 
	 * @param materialCode
	 * @param wareHouse
	 * @param batchNo
	 */
	public BigDecimal getShipQuality(String materialCode, String warehouse, String batchNo,String shipDetailId,String deptId)
	{
		return shipInfoJdbcDao.getShipQuality(materialCode, warehouse, batchNo, shipDetailId,deptId);
	}

	/**
	 * 根据参数条件新增"进料加工业务"出仓物料信息
	 * 
	 * @param shipId
	 * @param contractPurchaseId
	 * @param mtRowsId
	 * @return
	 */
	public TShipMaterial addMtByPurchase(String shipId, String contractPurchaseId, String purchaseRowsId, String bomId, String intendGatherTime, String makeInvoiceTime)
	{
		TShipMaterial tShipMaterial = new TShipMaterial();
		TContractPurchaseInfo tContractPurchaseInfo = contractPurchaseInfoHibernateDao.get(contractPurchaseId);
		TContractPurchaseMateriel tContractPurchaseMateriel = contractPurchaseMaterielHibernateDao.get(purchaseRowsId);
		TContractBom tContractBom = contractBomHibernateDao.get(bomId);
		tShipMaterial = this.addMtByPurchaseMt(shipId, tContractPurchaseInfo, tContractPurchaseMateriel, tContractBom, "", intendGatherTime, makeInvoiceTime);

		return tShipMaterial;
	}

	/**
	 * 申请不通过时,把记录更新为不通过,使得所申请数量返回仓内
	 * 
	 * @param businessRecordId
	 */
	public int updateRecordToDisavailable(String businessRecordId)
	{
		return this.shipInfoJdbcDao.updateMaterialRecordToDisavailable(businessRecordId);
	}

	public void setContractAgentMaterielHibernateDao(ContractAgentMaterielHibernateDao contractAgentMaterielHibernateDao)
	{
		this.contractAgentMaterielHibernateDao = contractAgentMaterielHibernateDao;
	}

	public Map<String, String> dealPost(String shipId, String deptCode)
	{
		TShipInfo info = shipInfoHibernateDao.get(shipId);
		if(info.getSapReturnNo()==null||"".equals(info.getSapReturnNo().trim())){
			List<TShipMaterial> materials = shipMaterialHibernateDao.find("from TShipMaterial a where a.shipId ='" + shipId + "'");
			Map<String, String> map = ShipReceiptUtils.shipBapi(info, materials, deptCode);
			if (map.get("SAP_RETURN_NO") != null && map.get("SAP_RETURN_NO").length() > 1)
				info.setSapReturnNo(map.get("SAP_RETURN_NO"));
			shipInfoHibernateDao.saveOrUpdate(info);
			return map;
		}else {
			Map<String, String> map = new HashMap<String,String>();
			map.put("SAP_RETURN_NO","");
			map.put("SAP_RETURN_MSG","过账失败!已过账,请检查");
			return map;
		}
	}
	
	public TShipInfo writeOff(String oldShipId)
	{
		if(isAreadyWriteOff(oldShipId))
			throw new com.infolion.platform.dpframework.core.BusinessException("该出仓已有冲销单据，请检查");
		TShipInfo tShipInfo = shipInfoHibernateDao.get(oldShipId);
		if(StringUtils.isNotBlank(tShipInfo.getOldShipId())) throw new com.infolion.platform.dpframework.core.BusinessException("该单据是冲销单据，不能重冲销！");
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		
		tShipInfo.setOldSapReturnNo(tShipInfo.getSapReturnNo());
		tShipInfo.setOldShipId(oldShipId);
		tShipInfo.setOldShipNo(tShipInfo.getShipNo());
		tShipInfo.setSapReturnNo("");
		tShipInfo.setCmd("冲销原出仓单号："+tShipInfo.getShipNo());
		tShipInfo.setIsHasInv("");
		tShipInfo.setBillState("1");
		tShipInfo.setSerialNo(shipInfoJdbcDao.getSerialNo());//shipInfoJdbcDao.getShipNo()
		tShipInfo.setIsAvailable("1");
		tShipInfo.setCreator(userContext.getSysUser().getUserId());
		tShipInfo.setCreatorTime(DateUtils.getCurrDate(DateUtils.HYPHEN_DISPLAY_DATE));
		tShipInfo.setShipId(CodeGenerator.getUUID());
		tShipInfo.setDeptId(userContext.getSysDept().getDeptid());
		tShipInfo.setExamineState("1");
		tShipInfo.setCreateTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
		tShipInfo.setShipNo("");
		shipInfoHibernateDao.getHibernateTemplate().evict(tShipInfo);
		this.shipInfoHibernateDao.save(tShipInfo);
		List<TShipMaterial> list = shipMaterialHibernateDao.find("from TShipMaterial a where a.shipId ='"+tShipInfo.getOldShipId()+"'");
		for(TShipMaterial m :list){
			TShipMaterial n = new TShipMaterial();
			BeanUtils.copyProperties(m, n);
			n.setShipDetailId(CodeGenerator.getUUID());
			n.setShipId(tShipInfo.getShipId());
			n.setQuantity(m.getQuantity().negate());
			n.setTotal(m.getTotal().negate());
			n.setCreatorTime(DateUtils.getCurrTime(DateUtils.HYPHEN_DISPLAY_DATE));
			n.setCreator(tShipInfo.getCreator());
			n.setSaleTotal(m.getSaleTotal().negate());
			shipMaterialHibernateDao.save(n);

		}
		return tShipInfo;
	}
	
	public boolean isAreadyWriteOff(String oldShipId){
		List<TShipInfo> list = shipInfoHibernateDao
		.find("from TShipInfo a where a.oldShipId ='"+oldShipId+"' and a.isAvailable=1 and a.examineState!=4");
		if(list.isEmpty()) return false;
		return true;
	}
	/**
	public Map<String, String> dealPostFuck(String shipId, String deptCode)
	{
		TShipInfo info = shipInfoHibernateDao.get(shipId);
		String oldSapReturnNo = info.getSapReturnNo();
			List<TShipMaterial> materials = shipMaterialHibernateDao.find("from TShipMaterial a where a.shipId ='" + shipId + "'");
			Map<String, String> map = ShipReceiptUtils.shipBapi(info, materials, deptCode);
			String newReturnNo = map.get("SAP_RETURN_NO");
			String returnMsg = map.get("SAP_RETURN_MSG");
			if (map.get("SAP_RETURN_NO") != null && map.get("SAP_RETURN_NO").length() > 1)
				info.setSapReturnNo(newReturnNo);
			else map.put("SAP_RETURN_NO", "false");
			shipInfoHibernateDao.saveOrUpdate(info);
			shipInfoJdbcDao.fuckDeal(shipId, oldSapReturnNo, newReturnNo,returnMsg);
			return map;
	}**/
}
