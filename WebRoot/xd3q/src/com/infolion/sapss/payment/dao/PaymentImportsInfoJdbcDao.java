/*
 * @(#)PaymentImportsINfoJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：Apr 27, 2009
 *  描　述：创建
 */

package com.infolion.sapss.payment.dao;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.contract.dao.PurchaseChangeJdbcDao;
import com.infolion.sapss.contract.dao.SaleChangeJdbcDao;
import com.infolion.sapss.payment.domain.TPaymentImportsInfo;
import com.infolion.sapss.project.dao.ChangeProjectJdbcDao;
import com.infolion.sapss.project.dao.ProjectInfoJdbcDao;
import com.infolion.sapss.workflow.ProcessCallBack;

@Repository
public class PaymentImportsInfoJdbcDao extends BaseDao{
	/**
	 * 删除
	 * @param paymentId
	 */
	public int delete(String paymentId) {
		String sql = "update t_payment_imports_info set is_available='0' where payment_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{paymentId});
	}
	/**
	 * 更新状态
	 * @param paymentImportsInfo
	 * @param string
	 */
	public int updateState(TPaymentImportsInfo paymentImportsInfo,	String status) {
		String sql = "update t_payment_imports_info set examine_state=?,approved_time=? where payment_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{status,DateUtils.getCurrDate(2),paymentImportsInfo.getPaymentId()});
	}
	/**
	 * 根据币别取得汇率
	 * @param currency
	 * @return
	 */
	public String getCurrentRate(String currency) {
		String sql="select rate from bm_currency where id=?";
		List rs =  this.getJdbcTemplate().queryForList(sql, new Object[]{currency});
		if(rs!=null && rs.size()>0){
			Object obj= ((Map)rs.get(0)).get("rate");
			return ((BigDecimal)obj).toString();
		}
		return "1";
	}
	/**
	 * 取得记录审批状态
	 * @param paymentId
	 * @return
	 */
	public String getExamineSate(String paymentId) {
		String sql="select examine_state from t_payment_imports_info where payment_id=?";
		List rs =  this.getJdbcTemplate().queryForList(sql, new Object[]{paymentId});
		Object obj= ((Map)rs.get(0)).get("examine_state");
		return ((String)obj).toString();
	}
	
	public int getTradeTypeByPickListId(String pickListId){
		String sql = "select p.trade_type from T_PICK_LIST_INFO t ,t_contract_purchase_info p " +
				"where t.contract_purchase_id=p.contract_purchase_id and t.pick_list_id='"+pickListId+"'";
		return getJdbcTemplate().queryForInt(sql);
	}
	/**
	 * 
	 * @param paymentId
	 * @param columnName
	 * @param columnValue
	 * @return
	 */
	public int updateColumnData(String paymentId, String columnName,String columnValue) {
		String sql="update t_payment_imports_info set "+columnName+"=? where payment_id=?";
		return this.getJdbcTemplate().update(sql,new Object[]{columnValue,paymentId});
	}
	
	public String checkPayValue(TPaymentImportsInfo info){
		return "";
	}
	public String queryParticularId(String paymentId){
		final List<String> list = new ArrayList<String>();
		getJdbcTemplate().query("select particular_id from t_particular_workflow where original_biz_id=?", new Object[]{paymentId},
				new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				list.add(rs.getString("particular_id"));
            }
        });
		if(list.isEmpty()) return "";
		return list.get(0);
	}
	
	public void dealOutToExcel(String sql ,final ExcelObject excel){
		//final IntObject i= new IntObject();
		
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[13];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("dept_name");
				values[2] = rs.getString("real_name");
				values[3] = rs.getString("currency");
				values[4] = rs.getString("pay_value");
				values[5] = rs.getString("contract_no");
				values[6] = rs.getString("credit_no");
				values[7] = rs.getString("title");
				values[8] = rs.getString("issuing_date");
				values[9] = rs.getString("issuing_bank");
				values[10] = rs.getString("pay_time");
				values[11] = rs.getString("pay_date");
				values[12] = rs.getString("approved_time");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
	@Autowired
	private PurchaseChangeJdbcDao purchaseChangeJdbcDao;
	@Autowired
	private SaleChangeJdbcDao saleChangeJdbcDao;
	@Autowired
	private ChangeProjectJdbcDao changeProjectJdbcDao;
	@Autowired
	private ProjectInfoJdbcDao projectInfoJdbcDao;
	public void updateChenduState(String pdName,String businessid){
		if("contract_purcharse_modify_v10".equals(pdName)||"contract_purcharse_modify_v3".equals(pdName)){
			String purchaseId = this.purchaseChangeJdbcDao.getContractId(businessid);
			this.purchaseChangeJdbcDao.workflowChangeUpdateState(businessid,ProcessCallBack.EXAMINE_SUCCESSFUL);
			this.purchaseChangeJdbcDao.workflowUpdateState(purchaseId, ProcessCallBack.EXAMINE_SUCCESSFUL);			
		}else if("contract_purcharse_v10".equals(pdName)||"contract_purcharse_v3".equals(pdName)){
			String strSql = "update t_contract_purchase_info a set a.order_state = '3' where a.contract_purchase_id = ?";
			getJdbcTemplate().update(strSql,new Object[]{businessid});
			
		}else if("contract_sales_modify_v10".equals(pdName)||"contract_sales_modify_v3".equals(pdName)){
			String projectId = this.saleChangeJdbcDao.getContractId(businessid);
			saleChangeJdbcDao.workflowUpdateState(projectId,ProcessCallBack.EXAMINE_SUCCESSFUL);
			saleChangeJdbcDao.workflowChangeUpdateState(businessid, ProcessCallBack.EXAMINE_SUCCESSFUL);
			
		}else if("contract_sales_v10".equals(pdName)||"contract_sales_v3".equals(pdName)){
			String strSql = "update t_contract_sales_info a set a.order_state = '3' where a.contract_sales_id = ?";
			getJdbcTemplate().update(strSql,new Object[]{businessid});
		}else if("project_modify_v10".equals(pdName)||"project_modify_v3".equals(pdName)){			
			String projectId = this.changeProjectJdbcDao.getProjectId(businessid);
			this.changeProjectJdbcDao.workflowUpdateState(businessid,ProcessCallBack.EXAMINE_SUCCESSFUL);
			this.projectInfoJdbcDao.workflowChangeUpdateState(projectId, ProcessCallBack.EXAMINE_SUCCESSFUL);			
		}else if("project_v10".equals(pdName)||"project_v3".equals(pdName)){
			String strSql = "update t_project_info set project_state='3' where project_id=?";
			getJdbcTemplate().update(strSql,new Object[]{businessid});
		}		
		
	}
	
	public int getChenduCunliang2Yi(String businessid){
		String sql="select examine_result from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) where business_record_id=? and task_name like '%财务%' order by create_time desc ";
		List rs =  this.getJdbcTemplate().queryForList(sql, new Object[]{businessid});
		Object obj= ((Map)rs.get(0)).get("examine_result");
		if("业务存量超2亿".equals(obj)) return 1;
		return 0;
	}
}
