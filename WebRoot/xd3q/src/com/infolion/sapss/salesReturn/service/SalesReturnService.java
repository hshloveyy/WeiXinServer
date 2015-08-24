/*
 * @(#)ExportIncomeService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林哲文
 *  时　间：2009-6-10
 *  描　述：创建
 */

package com.infolion.sapss.salesReturn.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.infolion.sapss.contract.dao.ContractSalesMaterielHibernateDao;
import com.infolion.sapss.contract.domain.TContractSalesMateriel;
import com.infolion.sapss.contract.service.ContractHibernateService;
import com.infolion.sapss.salesReturn.dao.SalesReturnHibernateDao;
import com.infolion.sapss.salesReturn.dao.SalesReturnJdbcDao;
import com.infolion.sapss.salesReturn.dao.SalesReturnMaterialHibernateDao;
import com.infolion.sapss.salesReturn.domain.TSalesReturn;
import com.infolion.sapss.salesReturn.domain.TSalesReturnMaterial;
import com.infolion.sapss.ship.dao.ShipMaterialHibernateDao;
import com.infolion.sapss.ship.domain.TShipInfo;
import com.infolion.sapss.ship.domain.TShipMaterial;
import com.infolion.sapss.ship.service.ShipHibernateService;
import com.infolion.sapss.workflow.ProcessCallBack;
import com.infolion.sapss.common.WorkflowUtils;

@Service
public class SalesReturnService extends BaseHibernateService implements
		ProcessCallBack {
	@Autowired
	private SalesReturnHibernateDao salesReturnHibernateDao;
	@Autowired
	private SalesReturnJdbcDao salesReturnJdbcDao;
	@Autowired
	private ContractSalesMaterielHibernateDao contractSalesMaterielHibernateDao;
	@Autowired
	private ShipMaterialHibernateDao shipMaterialHibernateDao;
	@Autowired
	private SalesReturnMaterialHibernateDao salesReturnMaterialHibernateDao;

	public void setSalesReturnHibernateDao(
			SalesReturnHibernateDao salesReturnHibernateDao) {
		this.salesReturnHibernateDao = salesReturnHibernateDao;
	}

	public void setSalesReturnJdbcDao(SalesReturnJdbcDao salesReturnJdbcDao) {
		this.salesReturnJdbcDao = salesReturnJdbcDao;
	}

	public void setContractSalesMaterielHibernateDao(
			ContractSalesMaterielHibernateDao contractSalesMaterielHibernateDao) {
		this.contractSalesMaterielHibernateDao = contractSalesMaterielHibernateDao;
	}

	public void setShipMaterialHibernateDao(
			ShipMaterialHibernateDao shipMaterialHibernateDao) {
		this.shipMaterialHibernateDao = shipMaterialHibernateDao;
	}

	public void setSalesReturnMaterialHibernateDao(
			SalesReturnMaterialHibernateDao salesReturnMaterialHibernateDao) {
		this.salesReturnMaterialHibernateDao = salesReturnMaterialHibernateDao;
	}

	/**
	 * 获取销售退货编号
	 * 
	 * @return
	 */
	public String getReturnNo() {
		return this.salesReturnJdbcDao.getReturnNo();
	}

	/**
	 * 保存或更新
	 * 
	 * @param info
	 */
	public void saveOrUpdate(TSalesReturn tSalesReturn) {
		this.salesReturnHibernateDao.saveOrUpdate(tSalesReturn);

	}

	public TSalesReturn updateSalesReturnMoney(TSalesReturn tSalesReturn) {
		return this.salesReturnJdbcDao.updateSalesReturnMoney(tSalesReturn);
	}

	/**
	 * 保存
	 * 
	 * @param tBaleLoan
	 */
	public TSalesReturn save(TSalesReturn tSalesReturn) {
		UserContext context = UserContextHolder.getLocalUserContext()
				.getUserContext();
		if (StringUtils.isEmpty(tSalesReturn.getReturnId())) {
			tSalesReturn.setReturnId(CodeGenerator.getUUID());
			tSalesReturn.setReturnNo(this.getReturnNo());
			tSalesReturn.setCreator(context.getSysUser().getUserId());
			tSalesReturn.setDeptId(context.getSysDept().getDeptid());
			tSalesReturn.setCreatorTime(DateUtils.getCurrTime(2));
			tSalesReturn.setIsAvailable("1");
			tSalesReturn.setExamineState("1");
			tSalesReturn.setNetMoney("0");
			tSalesReturn.setTaxMoney("0");
			tSalesReturn.setTotalMoney("0");
			tSalesReturn.setRate("1");
		}
		tSalesReturn = this.updateSalesReturnMoney(tSalesReturn);
		this.salesReturnHibernateDao.saveOrUpdate(tSalesReturn);
		return tSalesReturn;
	}

	public String verifyFilds(String taskId, TSalesReturn tSalesReturn) {
		String taskName = "";
		String rtn = "";
		if (!StringUtils.isEmpty(taskId)) {
			TaskInstanceContext taskInstanceContext = WorkflowService
					.getTaskInstanceContext(taskId);
			taskName = taskInstanceContext.getTaskName();
		}
		if ("1".equals(tSalesReturn.getReturnType())) {
			if (taskName.equals("信息中心创建退货订单")) {
				String sapOrderNo = tSalesReturn.getSapOrderNo();
				if (StringUtils.isBlank(sapOrderNo)) {
					rtn = "请填写SAP退货订单号！";
					return rtn;
				}
				this.saveOrUpdate(tSalesReturn);
			}
			if (taskName.equals("综合一部处理退货收货") || taskName.equals("综合部处理退货收货")) {
				String sapDeliveryNo = tSalesReturn.getSapDeliveryNo();
				if (StringUtils.isBlank(sapDeliveryNo)) {
					rtn = "请填写退货SAP交货单号！";
					return rtn;
				}
				this.saveOrUpdate(tSalesReturn);
			}
			if (taskName.equals("综合一部处理开票") || taskName.equals("综合部处理开票")) {
				String sapBillingNo = tSalesReturn.getSapBillingNo();
				if (StringUtils.isBlank(sapBillingNo)) {
					rtn = "请填写退货SAP开票号！";
					return rtn;
				}
				String invoiceNo = tSalesReturn.getInvoiceNo();
				if (StringUtils.isBlank(invoiceNo)) {
					rtn = "请填写红字发票号码！";
					return rtn;
				}
				this.saveOrUpdate(tSalesReturn);
			}
		}

		return rtn;
	}

	/**
	 * 提交流程
	 * 
	 * @param taskId
	 * @param tBaleLoan
	 */
	public void submit(String taskId, TSalesReturn tSalesReturn) {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String workflowBusinessNote = userContext.getSysDept().getDeptname()
				+ "|" + userContext.getSysUser().getRealName() + "|";
		workflowBusinessNote = workflowBusinessNote + "销售退货编号："
				+ tSalesReturn.getReturnNo();
		tSalesReturn.setWorkflowBusinessNote(workflowBusinessNote);
		tSalesReturn.setWorkflowProcessName(WorkflowUtils
				.chooseWorkflowName("sales_retract_v1"));
		// tSalesReturn.setWorkflowProcessName(WorkflowUtils.getWorkflowProcessName("sales_retract_v1"));
		Map parameters = new HashMap();
		parameters.put("type", "1");
		parameters.put("totalMoney", tSalesReturn.getTotalMoney());
		parameters.put("returnType", tSalesReturn.getReturnType());
		parameters.put("currency", "CNY");
		tSalesReturn.setWorkflowUserDefineTaskVariable(parameters);
		if (StringUtils.isEmpty(taskId)) {
			WorkflowService.createAndSignalProcessInstance(tSalesReturn,
					tSalesReturn.getReturnId());
		} else {
			WorkflowService.signalProcessInstance(tSalesReturn, tSalesReturn
					.getReturnId(), null);
		}
	}

	/**
	 * 保存并提交
	 * 
	 * @param taskId
	 * @param tBaleLoan
	 */
	public void saveAndSubmit(String taskId, TSalesReturn tSalesReturn) {
		this.save(tSalesReturn);
		this.submit(taskId, tSalesReturn);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(String id) {
		TSalesReturn tSalesReturn = this.salesReturnHibernateDao.get(id);
		tSalesReturn.setIsAvailable("0");
		this.salesReturnHibernateDao.update(tSalesReturn);
	}

	/**
	 * 查找
	 * 
	 * @param id
	 */
	public TSalesReturn find(String id) {
		return this.salesReturnHibernateDao.get(id);

	}

	private Map<String, String> extractFR(HttpServletRequest req) {
		try {
			String wait = java.net.URLDecoder.decode(req.getQueryString(),
					"UTF-8");
			String ar1[] = wait.split("&");
			Map<String, String> map = new HashMap<String, String>();
			String str = "";
			String ar2[];
			for (int i = 0; i < ar1.length; i++) {
				str = ar1[i];
				ar2 = str.split("=");
				if (ar2.length == 1)
					continue;
				map.put(ar2[0], ar2[1]);
			}
			return map;
		} catch (UnsupportedEncodingException e) {
		}
		return Collections.EMPTY_MAP;
	}

	/**
	 * 组装销售退货查询条件
	 * 
	 * @param request
	 * @return
	 */
	public String querySQL(HttpServletRequest request) {
		Map<String, String> filter = extractFR(request);
		String deptId = filter.get("deptId");
		String projectNo = filter.get("projectNo");
		String contractNo = filter.get("contractNo");
		String salesSapOrderNo = filter.get("salesSapOrderNo");
		String startDate = filter.get("startDate");
		String endDate = filter.get("endDate");
		String shipNo = filter.get("shipNo");
		String sapReturnNo = filter.get("sapReturnNo");
		String returnType = filter.get("returnType");
		String sql = "select t1.*,t1.EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE,t2.contract_no,t2.contract_name,t2.sap_order_no as sales_sap_order_no,t3.project_no,t3.project_name,t4.ship_no,"
				+ "t4.sap_order_no as ship_sap_order_no,t4.sap_return_no from t_sales_return t1 left outer join t_contract_sales_info t2 on "
				+ "t1.contract_sales_id = t2.contract_sales_id left outer join t_project_info t3 on t2.project_id = t3.project_id "
				+ "left outer join t_ship_info t4 on t1.ship_id = t4.ship_id where t1.is_available = '1'";
		if (StringUtils.isNotBlank(returnType)) {
			sql += " and t1.return_type = '" + returnType + "'";
		}
		if (StringUtils.isNotBlank(projectNo)) {
			sql += " and t3.project_no like '%" + projectNo + "%'";
		}
		if (StringUtils.isNotBlank(contractNo)) {
			sql += " and t2.contract_no like '%" + contractNo + "%'";
		}
		if (StringUtils.isNotBlank(salesSapOrderNo)) {
			sql += " and t2.sap_order_no like '%" + salesSapOrderNo + "%'";
		}
		if (StringUtils.isNotBlank(contractNo)) {
			sql += " and t2.contract_no like '%" + contractNo + "%'";
		}
		if (StringUtils.isNotBlank(startDate)) {
			sql += " and to_char(t1.apply_time,'yyyy-mm-dd') >= '" + startDate
					+ "'";
		}
		if (StringUtils.isNotBlank(endDate)) {
			sql += " and to_char(t1.apply_time,'yyyy-mm-dd') <= '" + endDate
					+ "'";
		}
		if (StringUtils.isNotBlank(shipNo)) {
			sql += " and t4.ship_no like '%" + shipNo + "%'";
		}
		if (StringUtils.isNotBlank(sapReturnNo)) {
			sql += " and t4.sap_return_no like '%" + sapReturnNo + "%'";
		}
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		// 部门的过滤
		if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
			if (StringUtils.isNotBlank(deptId)) {
				String[] dp = deptId.split(",");
				deptId = "";
				for (int i = 0; i < dp.length; i++) {
					if (i == (dp.length - 1))
						deptId = deptId + "'" + dp[i] + "'";
					else
						deptId = deptId + "'" + dp[i] + "',";
				}
				sql += " and t1.dept_id in (" + deptId + ")";
			}
			sql += " and t1.dept_id in ( "
					+ userContext.getGrantedDepartmentsId() + ")";
		} else {
			sql += " and t1.dept_id = '" + userContext.getSysDept().getDeptid()
					+ "'";
		}
		sql += " order by t1.creator_time desc";
		return sql;
	}

	/**
	 * 流程审批状态更新
	 */
	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		TSalesReturn tSalesReturn = this.salesReturnHibernateDao
				.get(businessRecordId);
		if (examineResult.equals(this.EXAMINE_SUCCESSFUL)) {
			tSalesReturn.setExamineState("3");

		} else if (examineResult.equals(this.EXAMINE_FAILE)) {
			tSalesReturn.setExamineState("4");
		}
		tSalesReturn.setApprovedTime(DateUtils.getCurrTime(2));
		this.saveOrUpdate(tSalesReturn);
	}

	/**
	 * 初始化退货单
	 * 
	 * @param returnId
	 * @param contractSalesId
	 * @param shipId
	 */
	public void initSalesReturnMt(String returnId, String contractSalesId,
			String shipId) {
		TSalesReturn tSalesReturn = this.salesReturnHibernateDao.get(returnId);
		tSalesReturn.setContractSalesId(contractSalesId);
		tSalesReturn.setShipId(shipId);
		this.salesReturnHibernateDao.save(tSalesReturn);
		// 先删除存在的
		List<TSalesReturnMaterial> TSalesReturnMaterialList = new ArrayList();
		TSalesReturnMaterialList = salesReturnMaterialHibernateDao
				.find("from TSalesReturnMaterial a where a.returnId ='"
						+ returnId + "'");
		for (int i = 0; i < TSalesReturnMaterialList.size(); i++) {
			TSalesReturnMaterial tSalesReturnMaterial = TSalesReturnMaterialList
					.get(i);
			this.salesReturnMaterialHibernateDao.remove(tSalesReturnMaterial);

		}
		if (StringUtils.isNotEmpty(contractSalesId)) {
			List<TContractSalesMateriel> tContractSalesMaterielList = new ArrayList();
			tContractSalesMaterielList = contractSalesMaterielHibernateDao
					.find("from TContractSalesMateriel a where a.contractSalesId ='"
							+ contractSalesId + "'");
			for (int i = 0; i < tContractSalesMaterielList.size(); i++) {
				TContractSalesMateriel tContractSalesMateriel = tContractSalesMaterielList
						.get(i);
				this.saveMaterialByContract(returnId, tContractSalesMateriel);

			}
		}
		if (StringUtils.isNotEmpty(shipId)) {
			List<TShipMaterial> tShipMateriallList = new ArrayList();
			tShipMateriallList = shipMaterialHibernateDao
					.find("from TShipMaterial a where a.shipId ='" + shipId
							+ "'");
			for (int i = 0; i < tShipMateriallList.size(); i++) {
				TShipMaterial tShipMaterial = tShipMateriallList.get(i);
				this.saveMaterialByShip(returnId, tShipMaterial);

			}
		}
	}

	/**
	 * 通过销售合同物料增加退货物料
	 * 
	 * @param returnId
	 * @param tContractSalesMateriel
	 */
	public void saveMaterialByContract(String returnId,
			TContractSalesMateriel tContractSalesMateriel) {
		TSalesReturnMaterial tSalesReturnMaterial = new TSalesReturnMaterial();
		tSalesReturnMaterial.setReturnMaterialId(CodeGenerator.getUUID());
		tSalesReturnMaterial.setReturnId(returnId);
		tSalesReturnMaterial.setMaterialCode(tContractSalesMateriel
				.getVbapMatnr());
		tSalesReturnMaterial.setMaterialName(tContractSalesMateriel
				.getVbapArktx());
		tSalesReturnMaterial.setMaterialUnit(tContractSalesMateriel
				.getVbapVrkme());
//		tSalesReturnMaterial.setQuantity(tContractSalesMateriel.getVbapKwmeng());
		tSalesReturnMaterial.setPrice(tContractSalesMateriel.getKonvKbetr());
		tSalesReturnMaterial.setCalcUnit(tContractSalesMateriel.getVbapVrkme());
		tSalesReturnMaterial.setPriceUnit(tContractSalesMateriel.getVbapKpein());
		tSalesReturnMaterial.setFactory(tContractSalesMateriel.getVbapWerks());
		tSalesReturnMaterial.setDeliveryTime(tContractSalesMateriel.getRv45aEtdat());// ROW_TAXES_RALE
		String rate = tContractSalesMateriel.getRowTaxesRale();
		if (StringUtils.isBlank(rate)) {
			rate = "0";
		}
		tSalesReturnMaterial.setRate(Double.valueOf(rate));
		this.salesReturnMaterialHibernateDao.save(tSalesReturnMaterial);
	}

	/**
	 * 通过出仓单保存物料
	 * 
	 * @param returnId
	 * @param tShipMaterial
	 */
	public void saveMaterialByShip(String returnId, TShipMaterial tShipMaterial) {
		TSalesReturnMaterial tSalesReturnMaterial = new TSalesReturnMaterial();
		tSalesReturnMaterial.setReturnMaterialId(CodeGenerator.getUUID());
		tSalesReturnMaterial.setReturnId(returnId);
		tSalesReturnMaterial.setMaterialCode(tShipMaterial.getMaterialCode());
		tSalesReturnMaterial.setMaterialName(tShipMaterial.getMaterialName());
		tSalesReturnMaterial.setMaterialUnit(tShipMaterial.getMaterialUnit());
//		tSalesReturnMaterial.setQuantity(tShipMaterial.getQuantity());
		tSalesReturnMaterial.setPrice(tShipMaterial.getPrice());
		tSalesReturnMaterial.setCurrency(tShipMaterial.getCurrency());
//		tSalesReturnMaterial.setTotalMoney(Double.parseDouble(tShipMaterial.getQuantity().toString())* tShipMaterial.getPrice());
		tSalesReturnMaterial.setPriceUnit(tShipMaterial.getEkpoPeinh());//条件定价单位
		tSalesReturnMaterial.setBatchNo(tShipMaterial.getBatchNo());//批次号
		this.salesReturnMaterialHibernateDao.save(tSalesReturnMaterial);

	}

	/**
	 * 添加物料信息
	 * 
	 * @param returnId
	 * @param shipDetailId
	 * @param salesRowsId
	 */
	public void addMaterial(String returnId, String shipDetailId,
			String salesRowsId) {
		if (StringUtils.isNotBlank(shipDetailId)) {
			TShipMaterial tShipMaterial = this.shipMaterialHibernateDao
					.get(shipDetailId);
			this.saveMaterialByShip(returnId, tShipMaterial);
		}
		if (StringUtils.isNotBlank(salesRowsId)) {
			TContractSalesMateriel tContractSalesMateriel = this.contractSalesMaterielHibernateDao
					.get(salesRowsId);
			this.saveMaterialByContract(returnId, tContractSalesMateriel);
		}
	}

	public void delMaterial(String returnMaterialId) {
		TSalesReturnMaterial tSalesReturnMaterial = this.salesReturnMaterialHibernateDao
				.get(returnMaterialId);
		this.salesReturnMaterialHibernateDao.remove(tSalesReturnMaterial);

	}

	/**
	 * 更新物料单信息
	 * 
	 * @param tSalesReturn
	 * @param returnMaterialId
	 * @param colName
	 * @param colValue
	 * @throws BusinessException
	 */
	public void updateMaterial(TSalesReturn tSalesReturn,
			String returnMaterialId, String colName, String colValue)
			throws BusinessException {
		this.salesReturnJdbcDao.updateMaterial(tSalesReturn, returnMaterialId,
				colName, colValue);
	}

	/**
	 * 取的物料信息
	 * 
	 * @param returnMaterialId
	 * @return
	 */
	public TSalesReturnMaterial getMaterial(String returnMaterialId) {
		return this.salesReturnMaterialHibernateDao.get(returnMaterialId);
	}

	/**
	 * 取的物料的汇总金额
	 * 
	 * @param returnId
	 * @return
	 */
	public List<BigDecimal> getSalesReturnMtMoney(String returnId) {
		return this.salesReturnJdbcDao.getSalesReturnMtMoney(returnId);
	}
}
