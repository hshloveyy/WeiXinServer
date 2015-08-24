/*
 * @(#)ProjectJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.ship.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.JbpmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.project.dao.ProjectAccountingJdbcDao;
import com.infolion.sapss.project.dao.ProjectAccountingTypeJdbcDao;
import com.infolion.sapss.project.dao.ProjectInfoJdbcDao;
import com.infolion.sapss.project.domain.TProjectAccounting;
import com.infolion.sapss.project.domain.TProjectInfo;
import com.infolion.sapss.project.web.ProjectDetailVO;
import com.infolion.sapss.ship.dao.ExportApplyJdbcDao;
import com.infolion.sapss.ship.dao.ExportApplyMaterialHibernateDao;
import com.infolion.sapss.ship.domain.TExportApplyMaterial;
import com.infolion.sapss.workflow.ProcessCallBack;

/**
 * 
 * <pre></pre>
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
public class ShipNotifyJdbcService extends BaseJdbcService {

	@Autowired
	ExportApplyJdbcDao exportApplyJdbcDao;
	@Autowired
	ExportApplyMaterialHibernateDao exportApplyMaterialHibernateDao;

	public void setExportApplyJdbcDao(ExportApplyJdbcDao exportApplyJdbcDao) {
		this.exportApplyJdbcDao = exportApplyJdbcDao;
	}

	/**
	 * 更新出仓通知单
	 * 
	 * @param exportMateId
	 * @param colName
	 * @param colValue
	 */
	public void updateExportApplyMateriel(String exportMateId, String colName,
			String colValue) throws BusinessException {
		// 判断控制输入数量
		if (colName.equals("QUANTITY")) {
			BigDecimal has_quantity = BigDecimal.valueOf(0);
			TExportApplyMaterial tExportApplyMaterial = exportApplyMaterialHibernateDao
					.get(exportMateId);
			String salesRowsId = tExportApplyMaterial.getSalesRowsId();
			String agentMaterialId = tExportApplyMaterial.getAgentMaterialId();
			// 销售物料
			if (StringUtils.isNotBlank(salesRowsId)) {
				has_quantity = exportApplyJdbcDao
						.getSalesHasQuantity(salesRowsId);
			}
			// 代理物料
			if (StringUtils.isNotBlank(agentMaterialId)) {
				has_quantity = exportApplyJdbcDao
						.getAgentHasQuantity(agentMaterialId);
			}
			// BigDecimal quantity = BigDecimal.valueOf(Double
			// .parseDouble(colValue));
			BigDecimal quantity = new BigDecimal(colValue);
			has_quantity = has_quantity.subtract(quantity);
			has_quantity = has_quantity.add(tExportApplyMaterial.getQuantity());
			// 如果小于0的话超过剩余数量//20150625调整，允许小数调整，处理成：剩余数量加1不允许为负值
			if (has_quantity.add(BigDecimal.ONE).signum() == -1) {
				throw new BusinessException("申请数量超过合同物料数量");
			}
		}
		exportApplyJdbcDao.updateExportApplyMateriel(exportMateId, colName,
				colValue);
	}

	public void deleteMaterialByApply(String exportApplyId) {
		exportApplyJdbcDao.deleteMaterielByApply(exportApplyId);
	}

	/**
	 * 获取通知单号
	 * 
	 * @return
	 */
	public String getNoticeNo() {
		return exportApplyJdbcDao.getNoticeNo();
	}

	/**
	 * 获取MAP的对象名与值
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> getFilter(Map map) {
		Map filter = new HashMap();
		if (map != null && !map.isEmpty()) {
			Iterator entries = map.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (java.util.Map.Entry) entries.next();
				String key = entry.getKey().toString();
				String[] keyValue = (String[]) entry.getValue();
				filter.put(key, keyValue[0]);
			}
		}
		return filter;
	}

	/**
	 * 组装出仓通知单查询条件语句
	 * 
	 * @param map
	 * @return
	 */
	public String getQueryExportApplySql(Map<String, String> filter) {
		String sql = "select t.*,t.EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE,f.contract_name,f.VBKD_IHREZ,f.KUWEV_KUNNR_NAME,k.TOTAL,k.TOTAL_QUANTITY,f.vbak_waerk"
				+ " from t_export_apply t left outer join t_contract_sales_info f on t.contract_sales_id = f.contract_sales_id "
				+ " left outer join (select round(sum(m.total),2) as total,sum(m.quantity) as TOTAL_QUANTITY,m.export_apply_id from t_export_apply_material m group by m.export_apply_id) k" 
				+ " on t.export_apply_id=k.export_apply_id"
				+ " where t.is_available = 1 ";
		if (filter != null && !filter.isEmpty()) {
			String examineState = filter.get("examineState");
			if (StringUtils.isNotBlank(examineState))
				sql += " and t.examine_State = '" + examineState + "'";
			// SAP订单号
			String sapOrderNo = filter.get("sapOrderNo");
			if (StringUtils.isNotBlank(sapOrderNo))
				sql += " and t.sap_order_no = '" + sapOrderNo + "'";

			String tradeType = filter.get("tradeType");
			if (StringUtils.isNotBlank(tradeType)) {
				sql += " and t.trade_type = '" + tradeType + "'";
			}
			String projectNo = filter.get("projectNo");
			if (StringUtils.isNotBlank(projectNo)) {
				sql += " and t.project_no like '%" + projectNo + "%'";
			}
			String contractGroupNo = filter.get("contractGroupNo");
			if (StringUtils.isNotBlank(contractGroupNo)) {
				sql += " and t.contract_group_no like '%" + contractGroupNo
						+ "%'";
			}
			String noticeNo = filter.get("noticeNo");
			if (StringUtils.isNotBlank(noticeNo)) {
				sql += " and t.notice_no like '%" + noticeNo + "%'";
			}
			String contractNo = filter.get("contractNo");
			if (StringUtils.isNotBlank(contractNo)) {
				sql += " and t.sales_no like '%" + contractNo + "%'";
			}
			String contractName = filter.get("contractName");
			if (StringUtils.isNotBlank(contractName)) {
				sql += " and f.contract_name like '%" + contractName + "%'";
			}
			String writeNo = filter.get("writeNo");
			if (StringUtils.isNotBlank(writeNo)) {
				sql += " and t.WRITE_NO like '%" + writeNo + "%'";
			}
			String sGetSheetTime = filter.get("sGetSheetTime");
			if (StringUtils.isNotBlank(sGetSheetTime)) {
				sql += " and t.GET_SHEET_TIME >= '" + sGetSheetTime + "'";
			}
			String eGetSheetTime = filter.get("eGetSheetTime");
			if (StringUtils.isNotBlank(eGetSheetTime)) {
				sql += " and t.GET_SHEET_TIME <= '" + eGetSheetTime + "'";
			}
			String paperNo = filter.get("paperNo");
			if(StringUtils.isNotBlank(paperNo)){
				sql +=" and f.vbkd_ihrez like '%"+paperNo+"%'";
			}
			String lcNo = filter.get("lcNo");
			if(StringUtils.isNotBlank(lcNo)){
				sql +=" and t.lcno like '%"+lcNo+"%'";
			}
			
			String sDate = filter.get("sDate");
			if (StringUtils.isNotBlank(sDate)) {
				sql += " and t.creator_time >= '" + sDate + "'";
			}
			String eDate = filter.get("eDate");
			if (StringUtils.isNotBlank(eDate)) {
				sql += " and t.creator_time <= '" + eDate + "'";
			}
			/**新增关联出单审单查询，根据需求未关联出单的五联单，从10年开始查询，之前历史数据可以忽略***/
			String isBill = filter.get("isBill");
			if (StringUtils.isNotBlank(isBill)) {
				if("1".equals(isBill)){
					sql += " and t.export_apply_id in (select a.export_apply_id from t_Export_Bill_Exam_d a inner join T_EXPORT_BILL_EXAM b on a.export_bill_exam_id=b.export_bill_exam_id  where b.is_available=1) ";
				}
				else if("0".equals(isBill)){
					sql += " and t.export_apply_id not in (select a.export_apply_id from t_Export_Bill_Exam_d a inner join T_EXPORT_BILL_EXAM b on a.export_bill_exam_id=b.export_bill_exam_id  where b.is_available=1) ";
				}
				
			}
			// 部门的过滤F
			UserContext userContext = UserContextHolder.getLocalUserContext()
					.getUserContext();
			String deptId = filter.get("deptId");
			String deptSql = "";
			// 部门条件判断
			if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
				// 处理多选
				if (StringUtils.isNotBlank(deptId)) {
					String[] dp = deptId.split(",");
					deptId = "";
					for (int i = 0; i < dp.length; i++) {
						if (i == (dp.length - 1))
							deptId = deptId + "'" + dp[i] + "'";
						else
							deptId = deptId + "'" + dp[i] + "',";
					}
					deptSql = " and t.dept_id in (" + deptId + ")";
					sql += deptSql;
				}
				sql += " and t.dept_id in ( "
						+ userContext.getGrantedDepartmentsId() + ",";
				sql += "'" + userContext.getSysDept().getDeptid() + "')";

			} else {
				sql += " and t.dept_id = '"
						+ userContext.getSysDept().getDeptid() + "'";
			}
		}
		sql += " order by t.creator_time desc,t.notice_no desc";

		return sql;
	}
	
	
	
	public String getQueryBillApplySql(Map<String, String> filter) {
		String sql = "select t.*,c.VBKD_IHREZ,c.CONTRACT_NAME from t_bill_apply t left outer join t_contract_sales_info c on t.contract_sales_id=c.contract_sales_id"
				+ " where t.is_available = 1";
		if (filter != null && !filter.isEmpty()) {
			String examineState = filter.get("examineState");
			if (StringUtils.isNotBlank(examineState))
				sql += " and t.examine_State = '" + examineState + "'";
			// SAP订单号
			String sapOrderNo = filter.get("sapOrderNo");
			if (StringUtils.isNotBlank(sapOrderNo))
				sql += " and t.sap_order_no like '%" + sapOrderNo + "%'";

			String tradeType = filter.get("tradeType");
			if (StringUtils.isNotBlank(tradeType)) {
				sql += " and t.trade_type = '" + tradeType + "'";
			}
			String contractNo = filter.get("contractNo");
			if (StringUtils.isNotBlank(contractNo)) {
				sql += " and c.contract_no like '%" + contractNo + "%'";
			}
			String contractName = filter.get("contractName");
			if (StringUtils.isNotBlank(contractName)) {
				sql += " and c.contract_name like '%" + contractName + "%'";
			}
			String totalValue = filter.get("totalValue");
			if (StringUtils.isNotBlank(totalValue)) {
				sql += " and t.price_total >= '" + totalValue + "'";
			}
			String totalValue1 = filter.get("totalValue1");
			if (StringUtils.isNotBlank(totalValue1)) {
				sql += " and t.price_total <= '" + totalValue1 + "'";
			}
			String paperNo = filter.get("paperNo");
			if(StringUtils.isNotBlank(paperNo)){
				sql +=" and c.vbkd_ihrez like '%"+paperNo+"%'";
			}
			String billApplyNo = filter.get("billApplyNo");
			if (StringUtils.isNotBlank(billApplyNo)) {
				sql += " and t.bill_apply_no like '%" + billApplyNo + "%'";
			}
			String sapBillNo = filter.get("sapBillNo");
			if (StringUtils.isNotBlank(sapBillNo)) {
				sql += " and t.sap_bill_no like '%" + sapBillNo + "%'";
			}

			// 部门的过滤F
			UserContext userContext = UserContextHolder.getLocalUserContext()
					.getUserContext();
			String deptId = filter.get("deptId");
			String deptSql = "";
			// 部门条件判断
			if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
				// 处理多选
				if (StringUtils.isNotBlank(deptId)) {
					String[] dp = deptId.split(",");
					deptId = "";
					for (int i = 0; i < dp.length; i++) {
						if (i == (dp.length - 1))
							deptId = deptId + "'" + dp[i] + "'";
						else
							deptId = deptId + "'" + dp[i] + "',";
					}
					deptSql = " and t.dept_id in (" + deptId + ")";
					sql += deptSql;
				}
				sql += " and t.dept_id in ( "
						+ userContext.getGrantedDepartmentsId() + ",";
				sql += "'" + userContext.getSysDept().getDeptid() + "')";

			} else {
				sql += " and t.dept_id = '"
						+ userContext.getSysDept().getDeptid() + "'";
			}
		}
		sql += " order by t.creator_time desc";

		return sql;
	}
	
	public String getQueryNotifyInfoSql(Map<String, String> filter){
		String sql = "select * from v_shipnotify_info t where 1=1 ";
		if (filter != null && !filter.isEmpty()) {
			
			String noticeNo = filter.get("noticeNo");
			if (StringUtils.isNotBlank(noticeNo)) {
				sql += " and t.notice_no like '%" + noticeNo + "%'";
			}
			String projectNo = filter.get("projectNo");
			if (StringUtils.isNotBlank(projectNo)) {
				sql += " and t.project_No like '%" + projectNo + "%'";
			}
			String writeNo = filter.get("writeNo");
			if (StringUtils.isNotBlank(writeNo)) {
				sql += " and t.WRITE_NO like '%" + writeNo + "%'";
			}
			String material = filter.get("material");
			if(StringUtils.isNotBlank(material)){
				sql +=" and t.material_name like '%" + material +"%'";
			}
			String currency = filter.get("currency");
			if(StringUtils.isNotBlank(currency)){
				sql +=" and t.currency like '%" + currency +"%'";
			}
			String sGetSheetTime = filter.get("sGetSheetTime");
			if (StringUtils.isNotBlank(sGetSheetTime)) {
				sql += " and t.GET_SHEET_TIME >= '" + sGetSheetTime + "'";
			}
			String eGetSheetTime = filter.get("eGetSheetTime");
			if (StringUtils.isNotBlank(eGetSheetTime)) {
				sql += " and t.GET_SHEET_TIME <= '" + eGetSheetTime + "'";
			}
			String sDate = filter.get("sDate");
			if (StringUtils.isNotBlank(sDate)) {
				sql += " and t.creator_time >= '" + sDate + "'";
			}
			String eDate = filter.get("eDate");
			if (StringUtils.isNotBlank(eDate)) {
				sql += " and t.creator_time <= '" + eDate + "'";
			}
			// 部门的过滤
			UserContext userContext = UserContextHolder.getLocalUserContext()
			.getUserContext();
			String deptId = filter.get("deptId");
			String deptSql = "";
			// 部门条件判断
			if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
				// 处理多选
				if (StringUtils.isNotBlank(deptId)) {
					String[] dp = deptId.split(",");
					deptId = "";
					for (int i = 0; i < dp.length; i++) {
						if (i == (dp.length - 1))
							deptId = deptId + "'" + dp[i] + "'";
						else
							deptId = deptId + "'" + dp[i] + "',";
					}
					deptSql = " and t.dept_id in (" + deptId + ")";
					sql += deptSql;
				}
				sql += " and t.dept_id in ( "
					+ userContext.getGrantedDepartmentsId() + ",";
				sql += "'" + userContext.getSysDept().getDeptid() + "')";

			} else {
				sql += " and t.dept_id = '"
					+ userContext.getSysDept().getDeptid() + "'";
			}
		}

		return sql;
}

	/**
	 * 组装销售合同查询条件语句
	 * 
	 * @param filter
	 * @return
	 */
	public String getQuerySalesSql(Map<String, String> filter) {
		String sql = "select c.project_no, c.project_name,b.contract_group_no,b.contract_group_name,a.contract_sales_id,"
				+ "a.contract_no,a.contract_name,a.sap_order_no,a.VBKD_IHREZ,a.KUWEV_KUNNR_NAME,a.vbkd_inco1,a.vbkd_inco2,"
				+ "a.vbkd_zlsch vbkd_zlsch_d_pay_type,a.shipment_date,a.shipment_port,a.destination_port,a.collection_Date "
				+ "  from t_contract_sales_info a, t_contract_group b, t_project_info c"
				+ " where a.contract_group_id = b.contract_group_id and a.project_id = c.project_id";
		String contractGroupNo = filter.get("contractGroupNo");
		String deptId = getDeptId(filter);
		if (StringUtils.isNotBlank(contractGroupNo)) {
			sql = sql + " and b.contract_group_no like '%" + contractGroupNo
					+ "%'";
		}
		String contractGroupName = filter.get("contractGroupName");
		if (StringUtils.isNotBlank(contractGroupName)) {
			sql = sql + " and b.contract_group_name like '%"
					+ contractGroupName + "%'";
		}
		String contractNo = filter.get("contractNo");
		if (StringUtils.isNotBlank(contractNo)) {
			sql = sql + " and a.contract_no like '%" + contractNo + "%'";
		}
		String contractName = filter.get("contractName");
		if (StringUtils.isNotBlank(contractName)) {
			sql = sql + " and a.contract_name like '%" + contractName + "%'";
		}
		if (StringUtils.isNotBlank(deptId)) {
			sql = sql + " and a.dept_id in (" + deptId + ")";
		}
		String orderState = filter.get("orderState");
		if (StringUtils.isNotBlank(orderState)) {
			sql = sql + " and a.order_state in (" + orderState + ")";
		} else {
			sql = sql + " and a.order_state in ('3','5','7','8','9')";
		}
		String tradeType = filter.get("tradeType");
		if (StringUtils.isNotBlank(tradeType)) {
			sql = sql + " and a.trade_type = " + tradeType;
		}
		String vbkdIhrez =filter.get("vbkdIhrez");
		if(StringUtils.isNotBlank(vbkdIhrez)){
			sql = sql + " and a.vbkd_Ihrez like '%"+vbkdIhrez+"%'";
		}
		return sql;
	}

	/**
	 * 查询合同相关的L/C No
	 * 
	 * @param map
	 * @return
	 */
	public String getQueryLcNo(Map<String, String> map) {
		String sql = "select t1.credit_no,t1.country, t3.contract_no,t3.contract_name from t_credit_info t1 "
				+ "left outer join t_credit_rec t2 on t1.credit_id=t2.credit_id "
				+ "left outer join t_contract_sales_info t3 on t2.contract_sales_id=t3.contract_sales_id where "
				+ "t1.create_or_rec='2' and t1.is_available='1' and t1.credit_state in ('11','4','5','13')";
		String creditNo = map.get("creditNo");
		if (StringUtils.isNotBlank(creditNo)) {
			sql += " and t1.credit_no like '%" + creditNo + "%'";
		}
		String country = map.get("country");
		if (StringUtils.isNotBlank(country)) {
			sql += " and t1.country like '%" + country + "%'";
		}
		String contractNo = map.get("contractNo");
		if (StringUtils.isNotBlank(contractNo)) {
			sql += " and t3.contract_No like '%" + contractNo + "%'";
		}
		String contractName = map.get("contractName");
		if (StringUtils.isNotBlank(contractName)) {
			sql += " and t3.contract_Name like '%" + contractName + "%'";
		}
		String tradeType = map.get("tradeType");
		if (StringUtils.isNotBlank(tradeType)) {
			sql += " and t1.trade_Type ='" + tradeType + "'";
		}
		return sql;
	}
	public String getQueryCus(Map<String, String> map) {
		String sql = "SELECT t.ID,t.TITLE FROM t_foreign_cus t where 1=1 ";
		String title = map.get("title");
		if (StringUtils.isNotBlank(title)) {
			sql += " and t.title like '%" + title + "%'";
		}
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		String deptId = map.get("deptId");
		String deptSql = "";
		// 部门条件判断
		if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
			// 处理多选
			if (StringUtils.isNotBlank(deptId)) {
				String[] dp = deptId.split(",");
				deptId = "";
				for (int i = 0; i < dp.length; i++) {
					if (i == (dp.length - 1))
						deptId = deptId + "'" + dp[i] + "'";
					else
						deptId = deptId + "'" + dp[i] + "',";
				}
				deptSql = " and t.dept_id in (" + deptId + ")";
				sql += deptSql;
			}
			sql += " and t.dept_id in ( "
				+ userContext.getGrantedDepartmentsId() + ",";
			sql += "'" + userContext.getSysDept().getDeptid() + "')";

		} else {
			sql += " and t.dept_id = '"
				+ userContext.getSysDept().getDeptid() + "'";
		}
		return sql;
	}
	/**
	 * 得到当前用户所能查看到的部门
	 * 
	 * @param map
	 * @return 返回可以用于sql in查询的部门ID串
	 */
	private String getDeptId(Map<String, String> map) {
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		String deptId;
		if ("1".equals(userContext.getSysDept().getIsFuncDept()))
			deptId = userContext.getGrantedDepartmentsId();
		else {
			if (StringUtils.isNotBlank(map.get("deptId")))
				deptId = "'" + map.get("deptId") + "'";
			else
				deptId = "'" + userContext.getSysUser().getDeptId() + "'";
		}
		return deptId;
	}

	/**
	 * 组装销售物料查询条件语句
	 * 
	 * @param filter
	 * @return
	 */
	public String getQuerySalesMaterielSql(Map<String, String> filter) {
		String sql = "";
		String tradeType = filter.get("tradeType");
		String shipType = filter.get("shipType");
		// shipType=0表示通知单关联合同物料;
		if (shipType.equals("0")) {
			// tradeType=5表示取代理物料;
			if (tradeType.equals("5")) {
				sql = "select t.* from v_sel_agent_apply_mt t where 1 = 1 "; // where
				// has_quantity
				// >
				// 0";
			} else {
				sql = "select t.* from v_sel_sales_apply_mt t where 1 = 1 "; // where
				// has_quantity
				// >
				// 0";
			}
		}
		// shipType=1表示出仓单关联合同物料；
		else if (shipType.equals("1")) {
			sql = "select t.* from v_sel_sales_ship_mt t where 1 = 1 "; // where
			// has_quantity
			// > 0";
		}
		String contractSalesId = filter.get("contractSalesId");
		if (StringUtils.isNotBlank(contractSalesId)) {
			sql += " and t.contract_sales_id = '" + contractSalesId + "'";
		}
		String materialCode = filter.get("materialCode");
		if (StringUtils.isNotBlank(materialCode)) {
			sql += " and t.material_code like '%" + materialCode + "%'";
		}
		String materialName = filter.get("materialName");
		if (StringUtils.isNotBlank(materialName)) {
			sql += " and t.material_name like '%" + materialName + "%'";
		}
		return sql;
	}

	/**
	 * 组装出口货物通知单物料查询条件语句
	 * 
	 * @param filter
	 * @return
	 */
	public String getQueryExportApplyMaterielSql(Map<String, String> filter) {
		String sql = "select t.* from v_sel_apply_ship_mt t where 1 = 1 "; // where
		// has_quantity
		// > 0
		// ";

		String exportApplyId = filter.get("exportApplyId");

		if (StringUtils.isNotBlank(exportApplyId)) {
			sql += " and t.export_apply_id = '" + exportApplyId + "'";
		}

		String materialCode = filter.get("materialCode");
		if (StringUtils.isNotBlank(materialCode)) {
			sql += " and t.material_code like '%" + materialCode + "%'";
		}

		String materialName = filter.get("materialName");
		if (StringUtils.isNotBlank(materialName)) {
			sql += " and t.material_name like '%" + materialName + "%'";
		}
		return sql;
	}
	
	public void dealOutToExcel(String sql ,ExcelObject excel){
		exportApplyJdbcDao.dealOutToExcel(sql ,excel);
	}
	
	public void dealOutToExcelV1(String sql ,ExcelObject excel){
		exportApplyJdbcDao.dealOutToExcelV1(sql ,excel);
	}
	
}
