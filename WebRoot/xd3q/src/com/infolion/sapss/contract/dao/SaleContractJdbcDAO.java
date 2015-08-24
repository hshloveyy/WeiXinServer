/*
 * @(#)SaleContractJdbcDAO.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-3-4
 *  描　述：创建
 */

package com.infolion.sapss.contract.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.console.workflow.dto.TaskHisDto;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.dto.CommonChangeDto;
import com.infolion.sapss.contract.domain.TContractAgentMateriel;
import com.infolion.sapss.contract.domain.TContractSalesMateriel;
@Repository
public class SaleContractJdbcDAO  extends BaseDao{
	/**
	 * 查找物料信息 
	 * @param saleId
	 * @return
	 */
	public List<TContractSalesMateriel> findMaterial(String saleId) {
		String sql = "select * from T_CONTRACT_SALES_MATERIEL t where t.contract_sales_id=?";
		final List list = new ArrayList();
		this.getJdbcTemplate().query(sql, new Object[]{saleId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				TContractSalesMateriel sm = new TContractSalesMateriel();
		     	sm.setVbapZmeng(BigDecimal.valueOf(Double.valueOf(
		     			rs.getString("VBAP_ZMENG")==null || rs.getString("VBAP_ZMENG").trim().equals("")?"0":rs.getString("VBAP_ZMENG"))));
		     	sm.setRowTaxes(rs.getString("ROW_TAXES"));
		     	sm.setRowNet(rs.getString("ROW_NET"));
		     	sm.setKonvKbetr(rs.getDouble("KONV_KBETR"));
		     	sm.setVbapKpein(rs.getString("VBAP_KPEIN"));
		     	sm.setRowTaxesRale(rs.getString("ROW_TAXES_RALE"));
				sm.setSalesRowsId(rs.getString("sales_rows_id"));
				list.add(sm);
			}
		});
		return list;
	}
	/**
	 * 计算其他费用
	 * @param salesRowsId
	 * @return
	 * 2009-03-31  CXP 不*汇率*nvl(t.rate,0)
	 */
	public String sumOtherFee(String salesRowsId) {
		String sql = "select sum(rowSum) total from (select nvl(t.KONV_KBETR,0) rowSum  " +
		"from T_CONTRACT_SE_MATERIEL_CASE t where  t.sales_rows_id =  ?)";

		List list= this.getJdbcTemplate().queryForList(sql,new Object[]{salesRowsId});
		if(list!=null && list.size()>0){
			BigDecimal bd =  (BigDecimal)((Map)list.get(0)).get("total");
			return bd==null?"0":bd.toString();
		}
		else
			return "0";
	}
	/**
	 * 查找代理物料信息
	 * @param saleId
	 * @return
	 */
	public List<TContractAgentMateriel> findAgentMaterial(String saleId) {
		String sql = "select * from T_CONTRACT_AGENT_MATERIEL t where t.contract_sales_id=?";
		final List list = new ArrayList();
		this.getJdbcTemplate().query(sql, new Object[]{saleId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				TContractAgentMateriel sm = new TContractAgentMateriel();
		     	sm.setVbapZmeng(BigDecimal.valueOf(Double.valueOf(
		     			rs.getString("VBAP_ZMENG")==null || rs.getString("VBAP_ZMENG").trim().equals("")?"0":rs.getString("VBAP_ZMENG"))));
		     	sm.setRowTaxes(rs.getString("ROW_TAXES"));
		     	sm.setRowNet(rs.getString("ROW_NET"));
		     	sm.setVbapKpein(rs.getString("VBAP_KPEIN"));
		     	sm.setKonvKbetr(rs.getDouble("KONV_KBETR"));
				sm.setSalesRowsId(rs.getString("sales_rows_id"));
				sm.setAgentCurrency(rs.getString("agent_currency"));
				list.add(sm);
			}
		});
		return list;
	}
	/**
	 * 计算代理物料其他费用
	 * @param salesRowsId
	 * @return
	 * 2009-03-31  CXP 不*汇率*nvl(t.rate,0)
	 */
	public String sumAgentOtherFee(String salesRowsId) {
		String sql = "select sum(rowSum) total from (select nvl(t.KONV_KBETR,0) rowSum  " +
		"from T_CONTRACT_AGENT_MT_CASE t where  t.sales_rows_id =  ?)";

		List list= this.getJdbcTemplate().queryForList(sql,new Object[]{salesRowsId});
		if(list!=null && list.size()>0){
			BigDecimal bd =  (BigDecimal)((Map)list.get(0)).get("total");
			return bd==null?"0":bd.toString();
		}
		else
			return "0";
	}
	/**
	 * 保存SAP订单号
	 * @param sapOrderNo
	 * @param contractId
	 */
	public int saveSAPOrderNo(String sapOrderNo, String contractId) {
		String sql="update t_contract_sales_info set sap_order_no=? where contract_sales_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{sapOrderNo,contractId});
	}
	/**
	 * 取得业务类型
	 * @param saleId
	 * @return
	 */
	public String getSaleTradeType(String saleId){
		String sql="select trade_type from t_contract_sales_info where contract_sales_id=?";
		List list = this.getJdbcTemplate().queryForList(sql, new Object[]{saleId});
		return (String)((Map)list.get(0)).get("trade_type");
	}
	
	public Double getCurrencyRate(String currency){
		if(currency==null||"".equals(currency)) return 1d;
		final List<Double> list = new ArrayList<Double>();
		getJdbcTemplate().query("SELECT RATE FROM BM_CURRENCY WHERE ID=?", new Object[]{currency},
				new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				list.add(rs.getDouble("rate"));
            }
        });
		return list.get(0);
	}
	/**
	 * 
	 * @param businessRecordId
	 * @param workflowExamine
	 */
	public void saveToWorkflow(String businessRecordId, String workflowExamine,String taskName) {
		UserContext context = UserContextHolder.getLocalUserContext().getUserContext();
		String sql1 = "select * from (SELECT * fROM t_sys_wf_task_history union SELECT * FROM t_sys_wf_task_history_O) t where t.business_record_id=?";
		List list = this.getJdbcTemplate().queryForList(sql1,new Object[]{businessRecordId});
		Map map = new HashMap();
		if(list!=null && list.size()>0)
			map = (Map)list.get(0);
		
		String sql2="insert into t_sys_wf_task_history t ("
			+"TASK_HIS_ID,PROCESS_ID,TASK_ID,BUSINESS_RECORD_ID,"
			+"TASK_NAME,TASK_CREATE_TIME,TASK_END_TIME,EXAMINE_PERSON,"
			+"EXAMINE_DEPT_NAME,EXAMINE,EXAMINE_RESULT,CREATOR,CREATE_TIME)"
			+"values "
			+"(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(sql2, new Object[]{
				CodeGenerator.getUUID(),map.get("PROCESS_ID"),"1",businessRecordId,
				taskName,DateUtils.getCurrTime(2),DateUtils.getCurrTime(2),context.getSysUser().getRealName(),
				context.getSysDept().getDeptname(),workflowExamine,"同意",
				context.getSysUser().getUserName(),DateUtils.getTimeStr(new Date(),1)});
		
		this.getJdbcTemplate().update("update t_contract_sales_info t set t.vbak_auart_name='1' where t.contract_sales_id=?",new Object[]{businessRecordId});
	}
	
	
	public String queryContractIdByNo(String contractNo){
		String sql = "select contract_sales_id from t_contract_sales_info where contract_no=?";
		List rs =  this.getJdbcTemplate().queryForList(sql, new Object[]{contractNo});
		if(rs!=null && rs.size()>0){
			Object obj= ((Map)rs.get(0)).get("contract_sales_id");
			return (String)obj;
		}
		return null;
	}
	
	public String querySapOrderNoByNo(String contractNo){
		String sql = "select sap_order_no from t_contract_sales_info where contract_no=?";
		List rs =  this.getJdbcTemplate().queryForList(sql, new Object[]{contractNo});
		if(rs!=null && rs.size()>0){
			Object obj= ((Map)rs.get(0)).get("sap_order_no");
			return (String)obj;
		}
		return null;
	}
	
	public List<CommonChangeDto> queryChangeDtos(String contractId){
		final List<CommonChangeDto> list = new ArrayList<CommonChangeDto>();
		String sql = "select CHANGE_ID, CHANGE_DESC,CHANGE_TIME,CHANGE_NO from t_sales_change where CONTRACT_SALES_ID=? and is_available='1' order by create_time";
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
