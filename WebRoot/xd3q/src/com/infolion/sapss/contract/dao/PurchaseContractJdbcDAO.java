/*
 * @(#)PurchaseContractJdbcDAO.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-3
 *  描　述：创建
 */

package com.infolion.sapss.contract.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.sapss.common.dto.CommonChangeDto;
import com.infolion.sapss.contract.domain.TContractPurchaseMateriel;
@Repository
public class PurchaseContractJdbcDAO extends BaseDao{
	/**
	 * 每物料行关联的其他费用
	 * @param materialRowId
	 * @return
	 * 2009-03-31  CXP 不*汇率*nvl(t.rate,0)
	 */
	public String sumOtherFee(String materialRowId) {
		String sql = "select sum(rowSum) total from (select nvl(t.KONV_KBETR,0) rowSum  " +
				"from t_contract_pu_materiel_case t where  t.purchase_rows_id =  ?)";
		
//		List list= this.getJdbcTemplate().queryForList(sql,new Object[]{materialRowId});
//		if(list!=null && list.size()>0){
//			BigDecimal bd =  (BigDecimal)((Map)list.get(0)).get("total");
//			return bd==null?"0":bd.toString();
//		}
//		else
		//其它费用不加入总金额
			return "0";
	}
	/**
	 * 查找采购ID关联的物料信息
	 * @param purchaseId
	 * @return
	 */
	public List<TContractPurchaseMateriel> findMaterial(String purchaseId) {
		String sql = "select * from t_contract_purchase_materiel t where t.contract_purchase_id=?";
		final List list = new ArrayList();
		this.getJdbcTemplate().query(sql, new Object[]{purchaseId},new RowCallbackHandler(){
				//nvl(a.ekpo_menge,0) * nvl(a.ekpo_netpr,0) * nvl(a.ekpo_peinh,0)
			public void processRow(ResultSet rs) throws SQLException {
				 TContractPurchaseMateriel pm = new TContractPurchaseMateriel();
				 pm.setPurchaseRowsId(rs.getString("purchase_rows_id"));
				 pm.setEkpoMenge(rs.getDouble("ekpo_menge"));
				 pm.setEkpoNetpr(rs.getDouble("ekpo_netpr"));
				 pm.setEkpoPeinh(rs.getString("ekpo_peinh"));
				 pm.setEkpoMwskz(rs.getString("ekpo_mwskz"));
				 list.add(pm);
			}
		});
		return list;
	}
	/**
	 * 保存SAP订单号
	 * @param sapOrderNo
	 * @param contractPurchaseId
	 */
	public int saveSAPOrderNo(String sapOrderNo, String contractPurchaseId) {
		String sql = "update t_contract_purchase_info set sap_order_no=? where contract_purchase_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{sapOrderNo,contractPurchaseId});
	}
	
	public String queryContractIdByNo(String contractNo){
		String sql = "select t.contract_purchase_id  from t_contract_purchase_info t where t.contract_no = ?";
		List rs =  this.getJdbcTemplate().queryForList(sql, new Object[]{contractNo});
		if(rs!=null && rs.size()>0){
			Object obj= ((Map)rs.get(0)).get("contract_purchase_id");
			return (String)obj;
		}
		return null;
	}
	
	public List<CommonChangeDto> queryChangeDtos(String contractId){
		final List<CommonChangeDto> list = new ArrayList<CommonChangeDto>();
		String sql = "select CHANGE_ID, CHANGE_DESC,CHANGE_TIME,CHANGE_NO from T_PURCHASE_CHANGE where CONTRACT_PURCHASE_ID=? and is_available='1' order by create_time";
		getJdbcTemplate().query(sql,new Object[]{contractId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				CommonChangeDto dto = new CommonChangeDto();
				dto.setChangeId(rs.getString("CHANGE_ID"));
			    dto.setChangeDesc(rs.getString("change_desc"));
			    dto.setChangeTime(rs.getString("change_time"));
			    dto.setChangeNo(rs.getString("change_no"));
			    dto.setTasklist(queryTaskHistList(dto.getChangeId()));
				list.add(dto);
		}});
		return list;
	}
	
	private List<TaskHisDto> queryTaskHistList(String businessRecorderId){
		final List<TaskHisDto> list = new ArrayList<TaskHisDto>();
		String strSql = "select TASK_NAME,EXAMINE_DEPT_NAME,EXAMINE_PERSON,EXAMINE_RESULT,EXAMINE,TASK_CREATE_TIME,TASK_END_TIME" +
                        " from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) where business_record_id=? order by TASK_CREATE_TIME asc";
		
		getJdbcTemplate().query(strSql,new Object[]{businessRecorderId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				TaskHisDto dto = new TaskHisDto();
			    dto.setTaskName(rs.getString("TASK_NAME"));
			    dto.setExamine(rs.getString("EXAMINE"));
			    dto.setExamineDeptName(rs.getString("EXAMINE_DEPT_NAME"));
			    dto.setExaminePerson(rs.getString("EXAMINE_PERSON"));
			    dto.setExamineResult(rs.getString("EXAMINE_RESULT"));
			    dto.setTaskCreateTime(rs.getString("TASK_CREATE_TIME"));
			    dto.setTaskEndTime(rs.getString("TASK_END_TIME"));
				list.add(dto);
			}});

		return list;
	}

}
