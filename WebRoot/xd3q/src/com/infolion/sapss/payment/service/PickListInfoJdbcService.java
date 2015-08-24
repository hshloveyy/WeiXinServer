/*
 * @(#)PickListInfoJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Apr 27, 2009
 *  描　述：创建
 */

package com.infolion.sapss.payment.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.payment.dao.PickListInfoJdbcDao;

@Service
public class PickListInfoJdbcService extends BaseJdbcService
{
	@Autowired
	PickListInfoJdbcDao pickListInfoJdbcDao;

	public void setPickListInfoJdbcDao(PickListInfoJdbcDao pickListInfoJdbcDao)
	{
		this.pickListInfoJdbcDao = pickListInfoJdbcDao;
	}

	/**
	 * 获取MAP的对象名与值
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> getFilter(Map map)
	{
		Map filter = new HashMap();
		if (map != null && !map.isEmpty())
		{
			Iterator entries = map.entrySet().iterator();
			while (entries.hasNext())
			{
				Map.Entry entry = (java.util.Map.Entry) entries.next();
				String key = entry.getKey().toString();
				String[] keyValue = (String[]) entry.getValue();
				filter.put(key, keyValue[0]);
			}
		}
		return filter;
	}

	/**
	 * 组装查询到单查询条件
	 * 
	 * @param filter
	 * @return
	 */
	public String getQueryPickListInfoSql(Map<String, String> filter)
	{
		boolean mutilDeptFlag=false;
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String sql = "select t.*,t.EXAMINE_STATE EXAMINE_STATE_D_EXAMINE_STATE,d.dept_name,"
				+ "users.real_name,type.title type_name"
				+ " from t_pick_list_info t "
				+ "left outer join t_sys_dept d on t.dept_id = d.dept_id "
				+ "left outer join t_sys_user users on  t.creator = users.user_id "
				+ "left outer join bm_pick_list_type type on t.pick_list_type = type.id "
				+ " where t.is_available = 1";
		if (filter != null && !filter.isEmpty())
		{
			// 部门名称
			String deptName = filter.get("dict_dept");
			if (deptName!=null && !"".equals(deptName) && !"请选择...".equals(deptName)){
				String temp = deptName;
				if(temp.indexOf(",")!=-1){
					mutilDeptFlag = true;
					String[] temps = temp.split(",");
					temp="";
					for (int i = 0; i < temps.length; i++) {
						temp=temp+"'"+temps[i]+"'";
						if(i!=temps.length-1)
							temp=temp+",";
					}
					sql=sql+" and d.dept_name in ("+temp+")";
				}else
					sql=sql+" and d.dept_name='"+deptName+"'";

			}
			// 业务类型
			String tradeType = filter.get("tradeType");
			if (StringUtils.isNotBlank(tradeType))
			{
				sql += " and t.trade_type = '" + tradeType + "'";
			}
			String pickListType = filter.get("pickListType");
			if (StringUtils.isNotBlank(pickListType))
			{
				sql += " and t.pick_list_type = '" + pickListType + "'";
			}			
			// 单号
			String pickListNo = filter.get("pickListNo");
			if (StringUtils.isNotBlank(pickListNo))
			{
				sql += " and t.pick_list_no like '%" + pickListNo + "%'";
			}
			// 开证行(L/C)
			String issuingBank = filter.get("issuingBank");
			if (StringUtils.isNotBlank(issuingBank))
			{
				sql += " and t.issuing_bank = '" + issuingBank + "'";
			}
			// 
			String examineState = filter.get("examineState");
			if (StringUtils.isNotBlank(examineState))
			{
				sql += " and t.examine_state = '" + examineState + "'";
			}
			// L/C NO.(L/C)
			String lcNo = filter.get("lcNo");
			if (StringUtils.isNotBlank(lcNo))
			{
				sql += " and t.lc_no like '%" + lcNo + "%'";
			}
			// 代收行(D/P D/A )
			String collectionBank = filter.get("collectionBank");
			if (StringUtils.isNotBlank(collectionBank))
			{
				sql += " and t.collection_bank like '%" + collectionBank + "%'";
			}
			// 合同号
			String contractNo = filter.get("contractNo");
			if (StringUtils.isNotBlank(contractNo))
			{
				sql += " and t.contract_no like '%" + contractNo + "%'";
			}
			// SAP订单号
			String sapOrderNo = filter.get("sapOrderNo");
			if (StringUtils.isNotBlank(sapOrderNo))
			{
				sql += " and t.sap_order_no like '%" + sapOrderNo + "%'";
			}
			// D/P D/A NO.(D/P D/A )
			String dpDaNo = filter.get("dpDaDo");
			if (StringUtils.isNotBlank(dpDaNo))
			{
				sql += " and t.dp_da_no = '" + dpDaNo + "'";
			}
			// 开证日期(L/C)
			String issuingDate = filter.get("issuingDate");
			if (StringUtils.isNotBlank(issuingDate))
			{
				sql += " and t.issuing_date = '" + issuingDate + "'";
			}
			// 装运港
			String shipmentPort = filter.get("shipmentPort");
			if (StringUtils.isNotBlank(shipmentPort))
			{
				sql += " and t.shipment_port like '%" + shipmentPort + "%'";
			}
			// 目的港
			String destinationPort = filter.get("destinationPort");
			if (StringUtils.isNotBlank(destinationPort))
			{
				sql += " and t.destination_port like '%" + destinationPort
						+ "%'";
			}
			// 到单日
			String pickListRecDate = filter.get("pickListRecDate");
			if (StringUtils.isNotBlank(pickListRecDate))
			{
				sql += " and t.pick_list_rec_date = '" + pickListRecDate + "'";
			}
			// 提交时间
			String applyTime = filter.get("applyTime");
			if (StringUtils.isNotBlank(applyTime))
			{
				sql += " and substr(t.apply_time,1,10) = '" + applyTime + "'";
			}
			// 审核通过时间
			String approvedTime = filter.get("approvedTime");
			if (StringUtils.isNotBlank(approvedTime))
			{
				sql += " and substr(t.approved_time,1,10) = '" + approvedTime
						+ "'";
			}
		}
		//部门条件判断
		if (!mutilDeptFlag) {
			if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
				sql += " and t.dept_id in ( "+ userContext.getGrantedDepartmentsId() + ")";
			} else {
				sql += " and t.dept_id = '"	+ userContext.getSysDept().getDeptid() + "'";
			}
		}
		sql += " order by t.creator_time desc";
		return sql;
	}

	/**
	 * 取得 采购订单查询的SQL语句
	 * 
	 * @param map
	 * @return
	 */
	public String getPurchaseQuerySql(Map<String, String> map)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String contractGroupNo = map.get("contractGroupNo");
		String contractGroupName = map.get("contractGroupName");
		String contractNo = map.get("contractNo");
		String contractName = map.get("contractName");
		String deptId = map.get("deptId");
		String orderState = map.get("orderState");
		String tradeType = map.get("tradeType");
		String sql = "select c.project_no, c.project_name,b.contract_group_no,b.contract_group_name,a.contract_purchase_id,EKKO_ZTERM EKKO_ZTERM_D_PAYMENT_TYPE,"
				+ "a.contract_no,a.contract_name,a.sap_order_no,a.ekko_inco1,a.ekko_waers,c.project_id,a.EKKO_LIFNR_NAME,a.ymat_group"
				+ "  from t_contract_Purchase_info a, t_contract_group b, t_project_info c"
				+ " where a.contract_group_id = b.contract_group_id and a.project_id = c.project_id";

		if (StringUtils.isNotBlank(contractGroupNo))
		{
			sql = sql + " and b.contract_group_no like '%" + contractGroupNo
					+ "%'";
		}
		if (StringUtils.isNotBlank(contractGroupName))
		{
			sql = sql + " and b.contract_group_name like '%"
					+ contractGroupName + "%'";
		}
		if (StringUtils.isNotBlank(contractNo))
		{
			sql = sql + " and a.contract_no like '%" + contractNo + "%'";
		}
		if (StringUtils.isNotBlank(contractName))
		{
			sql = sql + " and a.contract_name like '%" + contractName + "%'";
		}
		if (StringUtils.isNotBlank(orderState))
		{   
			sql = sql + " and a.order_state in (" + orderState + ")";
		}
		if (StringUtils.isNotBlank(tradeType))
		{
			sql = sql + " and a.trade_type = " + tradeType;
		}
		if(!"1".equals(userContext.getSysDept().getIsFuncDept()))
			sql = sql + " and a.dept_id = '" + userContext.getSysDept().getDeptid() + "'";
		return sql;
	}

	public String getLcNoQuerySql(Map<String, String> map)
	{
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String creditNo = map.get("creditNo");
		String creditBank = map.get("creditBank");
		String contractNo = map.get("contractNo");
		String contractName = map.get("contractName");
		String department = map.get("department");
		String tradeType = map.get("tradeType");
		String sql = "select t2.write_off_single_no,t1.contract_purchase_id,t1.credit_create_id,t1.credit_id,t2.trade_type,t2.credit_no,t2.goods," +
				"t2.create_bank,t2.create_date,t3.contract_no,t3.contract_name,t3.sap_order_no,t3.ekko_waers,t3.EKKO_LIFNR_NAME,t2.AVAIL_DATE,t3.ymat_group"
				+ " from t_credit_create t1 inner join t_credit_info t2 on t1.credit_id = t2.credit_id"
				+ " inner join t_contract_purchase_info t3 on t1.contract_purchase_id = t3.contract_purchase_id where t2.credit_state in('5' ,'11')";

		if (StringUtils.isNotBlank(creditNo))
		{
			sql = sql + " and t2.credit_no like '%" + creditNo
					+ "%'";
		}
		if (StringUtils.isNotBlank(creditBank))
		{
			sql = sql + " and t2.create_bank like '%"
					+ creditBank + "%'";
		}
		if (StringUtils.isNotBlank(contractNo))
		{
			sql = sql + " and t3.contract_no like '%" + contractNo + "%'";
		}
		if (StringUtils.isNotBlank(contractName))
		{
			sql = sql + " and t3.contract_name like '%" + contractName + "%'";
		}
		if (StringUtils.isNotBlank(tradeType))
		{
			sql = sql + " and t2.trade_type = " + tradeType;
		}
		if(StringUtils.isNotBlank(department)){
			String temp ="";
			String[] str = department.split(",");
			if(str.length>1){
				for (int i = 0; i < str.length; i++) {
					temp=temp+"'"+str[i];
					if(i!=str.length-1)
						temp = temp+"',";
					else
						temp = temp+"'";
				}
				sql = sql + " and t2.dept_id in (" + temp+")";
			}else
				sql = sql + " and t2.dept_id = '" + department+"'";
		}else
			sql = sql + " and t2.dept_id in (" + userContext.getGrantedDepartmentsId()+")";
		return sql;
	}

	/**
	 * 取的采购合同的物料
	 * 
	 * @param filter
	 * @return
	 */
	public String getQueryPurchaseMaterielSql(Map<String, String> filter)
	{
		String sql = "";
		String tradeType = filter.get("tradeType");
		sql = "select t.* from t_contract_Purchase_materiel t where 1 = 1";
		String contractPurchaseId = filter.get("contractPurchaseId");
		if (StringUtils.isNotBlank(contractPurchaseId))
		{
			sql += " and t.contract_Purchase_id = '" + contractPurchaseId + "'";
		}
		String materialCode = filter.get("materialCode");
		if (StringUtils.isNotBlank(materialCode))
		{
			sql += " and t.EKPO_MATNR like '%" + materialCode + "%'";
		}
		String materialName = filter.get("materialName");
		if (StringUtils.isNotBlank(materialName))
		{
			sql += " and t.EKPO_TXZ01 like '%" + materialName + "%'";
		}
		return sql;
	}

	public void updateMateriel(String purchaseRowsId, String colName,
			String colValue)
	{
		pickListInfoJdbcDao.updateMateriel(purchaseRowsId, colName, colValue);
	}
	
	public boolean isExistBillNo(String pickListId,String pickListNo){
		return pickListInfoJdbcDao.isExistBillNo(pickListId, pickListNo);
	}

}
