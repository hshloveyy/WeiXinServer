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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.ReceiptShipConstants;
import com.infolion.sapss.receipts.domain.TReceiptInfo;
import com.infolion.sapss.ship.dao.ShipInfoJdbcDao;
import com.infolion.sapss.ship.dao.ShipMaterialHibernateDao;
import com.infolion.sapss.ship.domain.TShipInfo;
import com.infolion.sapss.ship.domain.TShipMaterial;

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
public class ShipJdbcService extends BaseJdbcService {

	@Autowired
	ShipInfoJdbcDao shipInfoJdbcDao;
	@Autowired
	ShipMaterialHibernateDao shipMaterialHibernateDao;

	public void setShipInfoJdbcDao(ShipInfoJdbcDao shipInfoJdbcDao) {
		this.shipInfoJdbcDao = shipInfoJdbcDao;
	}

	public void setShipMaterialHibernateDao(
			ShipMaterialHibernateDao shipMaterialHibernateDao) {
		this.shipMaterialHibernateDao = shipMaterialHibernateDao;
	}

	public void updateShipMateriel(String shipDetailId, String colName,
			String colValue) throws BusinessException {
		/****20120607
		BigDecimal has_quantity = BigDecimal.valueOf(0);

		// 判断控制输入数量
		
		if (colName.equals("QUANTITY")) {
			TShipMaterial tShipMaterial = shipMaterialHibernateDao
					.get(shipDetailId);

			String salesRowsId = tShipMaterial.getSalesRowsId();
			String exportMateId = tShipMaterial.getExportMateId();
			String bomId = tShipMaterial.getBomId();
			// 销售物料
			if (StringUtils.isNotBlank(salesRowsId)) {
				has_quantity = shipInfoJdbcDao.getSalesHasQuantity(salesRowsId);
			}
			// 出货通知单物料
			if (StringUtils.isNotBlank(exportMateId)) {
				has_quantity = shipInfoJdbcDao
						.getApplyHasQuantity(exportMateId);

			}

			// 采购单BOM物料
			if (StringUtils.isNotBlank(bomId))
				has_quantity = shipInfoJdbcDao.getPurchaseHasQuantity(bomId,
						tShipMaterial.getShipId(), shipDetailId);

			BigDecimal quantity = BigDecimal.valueOf(Double
					.parseDouble(colValue));
			has_quantity = has_quantity.subtract(quantity);

			if (!StringUtils.isNotBlank(bomId))
				has_quantity = has_quantity.add(tShipMaterial.getQuantity());

			// 如果小于0的话操作剩余数量
			*
			 * if (has_quantity.signum() == -1) return
			 * has_quantity.doubleValue();
			 *
		}
	    **/
		this.shipInfoJdbcDao
				.updateShipMateriel(shipDetailId, colName, colValue);

		//return has_quantity.doubleValue();
	}

	public String getShipNo(String code) {
		return this.shipInfoJdbcDao.getShipNo(code);
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

	public String getQueryShipSql(Map<String, String> filter) {
		String sql = "select distinct t.*,t.EXAMINE_STATE RSEXAM_STATE_D_RSEXAM_STATE ,u.real_name from t_ship_info t " +
				"left outer join t_ship_material m on t.ship_id=m.ship_id " +
				"left outer join t_sys_user u on t.creator=u.user_id  where 1=1 ";
		if(ReceiptShipConstants.ins().isShouldHide()) sql+=" and t.EXAMINE_STATE!='5'";
		
		if (filter != null && !filter.isEmpty()) {
			if ("0".equals(filter.get("isAvailable")))
				sql+=" and t.is_available='0'";
			else sql+=" and t.is_available='1'";
			
			
			String tradeType = filter.get("tradeType");
			if (StringUtils.isNotBlank(tradeType)) {
				sql += " and t.trade_type = '" + tradeType + "'";
			}
			String examState = filter.get("examState");
			if (StringUtils.isNotBlank(examState)) {
				sql += " and t.examine_state = '" + examState + "'";
			}
			String sapOrderNo = filter.get("sapOrderNo");
			if (StringUtils.isNotBlank(sapOrderNo)) {
				sql += " and t.sap_order_no = '" + sapOrderNo + "'";
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
			String sDate = filter.get("sDate");
			if (StringUtils.isNotBlank(sDate)) {
				sql += " and t.creator_time >= '" + sDate + "'";
			}
			String eDate = filter.get("eDate");
			if (StringUtils.isNotBlank(eDate)) {
				sql += " and t.creator_time <= '" + eDate + "'";
			}
			String aDate = filter.get("aDate");
			if (StringUtils.isNotBlank(aDate)) {
				sql += " and t.approved_time >= '" + aDate + "'";
			}
			String aDate1 = filter.get("aDate1");
			if (StringUtils.isNotBlank(aDate1)) {
				sql += " and t.approved_time <= '" + aDate1 + " 24:00:00'";
			}
			String unitName = filter.get("unitName");
			if (StringUtils.isNotBlank(unitName)) {
				sql += " and t.unit_Name like '%" + unitName + "%'";
			}
			String shipNo = filter.get("shipNo");
			if (StringUtils.isNotBlank(shipNo)) {
				sql += " and (t.ship_no like '%" + shipNo + "%'";
				sql += " or  t.serialNo like '%" + shipNo + "%')";
			}
			String sapReturnNo = filter.get("sapReturnNo");
			if (StringUtils.isNotBlank(sapReturnNo)) {
				sql += " and t.sap_return_no like '%" + sapReturnNo + "%'";
			}
			String paperNo = filter.get("paperNo");
			if (StringUtils.isNotBlank(paperNo)) {
				sql += " and (t.contract_paper_no like '%" + paperNo + "%')";
			}
			
			String batchNo = filter.get("batchNo");
			if (StringUtils.isNotBlank(batchNo)) {
				sql += " and m.batch_No like '%" + batchNo + "%'";
			}
			
			String wareHouse = filter.get("wareHouse");
			if (StringUtils.isNotBlank(wareHouse)) {
				sql += " and t.wareHouse = '" + wareHouse + "'";
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

		sql += " order by t.creator_time desc,t.ship_no desc";

		return sql;
	}

	/**
	 * 取得 成品采购订单查询的SQL语句
	 * 
	 * @param map
	 * @return
	 */
	public String getPurchaseQuerySql(Map<String, String> map) {
		String contractGroupNo = map.get("contractGroupNo");
		String contractGroupName = map.get("contractGroupName");
		String contractNo = map.get("contractNo");
		String contractName = map.get("contractName");
		String deptId = map.get("deptId");
		String orderState = map.get("orderState");
		String tradeType = map.get("tradeType");
		String hasQuantity = map.get("hasQuantity");
		String sql = "select DISTINCT c.project_no, c.project_name,b.contract_group_no,b.contract_group_name,a.contract_purchase_id,"
				+ "a.contract_no,a.contract_name,a.sap_order_no,a.EKKO_UNSEZ,a.EKKO_LIFNR_NAME,a.ekko_inco1"
				+ " from t_contract_Purchase_info a"
				+ " Inner Join t_contract_purchase_materiel d On a.CONTRACT_PURCHASE_ID = d.CONTRACT_PURCHASE_ID"
				+ " inner join t_contract_group b On a.contract_group_id = b.contract_group_id"
				+ " Inner Join t_project_info c On a.project_id = c.project_id"
///				+ " Left Join v_sel_purchase_ship_mt v1 On d.PURCHASE_ROWS_ID = v1.PURCHASE_ROWS_ID"
				+ " where a.EKKO_BSART = 'Z006' And a.IS_AVAILABLE = '1' And a.ORDER_STATE = '3'";
		// 仅审核通过的成品采购合同

		/*if (StringUtils.isNotBlank(hasQuantity))
			sql = sql + " and nvl(v1.has_quantity,0) > 0";
		else
			sql = sql + " and nvl(v1.has_quantity,0) <= 0";*/

		if (StringUtils.isNotBlank(contractGroupNo)) {
			sql = sql + " and b.contract_group_no like '%" + contractGroupNo
					+ "%'";
		}
		if (StringUtils.isNotBlank(contractGroupName)) {
			sql = sql + " and b.contract_group_name like '%"
					+ contractGroupName + "%'";
		}
		if (StringUtils.isNotBlank(contractNo)) {
			sql = sql + " and a.contract_no like '%" + contractNo + "%'";
		}
		if (StringUtils.isNotBlank(contractName)) {
			sql = sql + " and a.contract_name like '%" + contractName + "%'";
		}
		if (StringUtils.isNotBlank(deptId)) {
			sql = sql + " and a.dept_id like '%" + deptId + "%'";
		}
		if (StringUtils.isNotBlank(orderState))
			sql = sql + " and a.order_state in (" + orderState + ")";
		else
			sql = sql + " and a.order_state='3'";

		if (StringUtils.isNotBlank(tradeType)) {
			sql = sql + " and a.trade_type = " + tradeType;
		}
		
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sql += " and a.dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",";
		sql += "'" + userContext.getSysDept().getDeptid() + "')";
		
		return sql;
	}

	/**
	 * 查询采购订单物料
	 */
	public String getQueryPurchaseMaterielSql(Map<String, String> filter) {
		String sql = "";
		String tradeType = filter.get("tradeType");
		sql = "select t.*,t1.BOM_ID,t1.MATERIEL,t1.MATERIEL_NAME,t1.ENTRY_QUANTITY,t1.ENTRY_UOM,"
				+ "t1.CMD,t1.PLANT,t.EKPO_MENGE*t1.ENTRY_QUANTITY as QUANTITY,"
				+ "v1.HAS_QUANTITY,"
				+ "1 LINES from t_contract_Purchase_materiel t";
		sql += " Inner Join t_contract_bom t1 on t.PURCHASE_ROWS_ID=t1.PURCHASE_ROWS_ID";
		sql += " Left Join v_sel_purchase_ship_mt v1 on t1.BOM_ID=v1.BOM_ID";
		sql += " where 1 = 1";

		String contractPurchaseId = filter.get("contractPurchaseId");
		if (StringUtils.isNotBlank(contractPurchaseId)) {
			sql += " and t.contract_Purchase_id = '" + contractPurchaseId + "'";
		}
		String materialCode = filter.get("materialCode");
		if (StringUtils.isNotBlank(materialCode)) {
			sql += " and t.MATERIEL like '%" + materialCode + "%'";
		}
		String materialName = filter.get("materialName");
		if (StringUtils.isNotBlank(materialName)) {
			sql += " and t.MATERIEL_NAME like '%" + materialName + "%'";
		}
		return sql;
	}
	
	public List<TShipMaterial> queryShipMaterialList(String shipId){
		return shipInfoJdbcDao.queryShipMaterialList(shipId);
	}
	/**
	 * 
	 * @param shipId
	 * @param string
	 * @param sapReturnNo
	 */
	public void saveColumnValue(String shipId, String column, String value) {
		this.shipInfoJdbcDao.updateShipInfo(shipId,column,value);
		
	}
	public String queryDeptCode(String shipId){
		return shipInfoJdbcDao.queryDeptCode(shipId);
	}
	
	public void updateWithTransition(String shipId,String transistionName){
		if("二次结算".equals(transistionName)){
			shipInfoJdbcDao.updateShipInfo(shipId, "seConfigTime", DateUtils.getCurrTime(2));
			shipInfoJdbcDao.updateShipInfo(shipId, "EXAMINE_STATE", "5");
			shipInfoJdbcDao.updateSeReceiptQua(shipId,"1");
		}else if("确认".equals(transistionName)){
			shipInfoJdbcDao.updateSeReceiptQua(shipId,"2");
		}
		else if("退回财务".equals(transistionName)||"退回修改".equals(transistionName)){
			shipInfoJdbcDao.updateShipInfo(shipId, "seConfigTime", "");
			shipInfoJdbcDao.updateShipInfo(shipId, "EXAMINE_STATE", "2");
			shipInfoJdbcDao.updateSeReceiptQua(shipId,null);
		}
	}
	
	public List queryTaskHis(String businessid,boolean isPrint){
		return shipInfoJdbcDao.queryTaskHis(businessid,isPrint);
	}
	
	public void updateDate(TShipInfo info ,String oldStr,String newStr,String userId){
		shipInfoJdbcDao.updateDate(info ,oldStr,newStr,userId);
	}
	/**
	public List fuckDealQuery(){
		return shipInfoJdbcDao.fuckDealQuery();
	}
	public int fuckCheck(String ship_id){
		return shipInfoJdbcDao.fuckCheck(ship_id);
	}
	public int fuckDealBill(){
		return shipInfoJdbcDao.fuckDealBill();
	}**/
}
