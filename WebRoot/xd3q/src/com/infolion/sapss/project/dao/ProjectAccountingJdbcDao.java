package com.infolion.sapss.project.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.project.domain.TProjectAccounting;
/**
 * 
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 陈喜平
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class ProjectAccountingJdbcDao extends BaseDao{
	/**
	 * 保存或更新项目核算记录
	 * @param pa
	 */
	public int saveOrUpdateProjectAccounting(final List<TProjectAccounting> list) {
		if(isExistProjectAccouting(list)){
			return updateProjectAccouting(list);
		}else{
			return saveProjectAccounting(list);
		}
	}
	/**
	 * 保存记录
	 * @param list
	 * @return
	 */
	private int saveProjectAccounting(final List<TProjectAccounting> list){
		String strSql = "insert into T_PROJECT_ACCOUNTING "+
						"(id, " +
						"project_id, " +
						"accounting_item_id, " +
						"accounting_item, " +
						"currency, " +
						"accounting_value, " +
						"creator_time, " +
						"creator) " +
						"values " +
						"(?,?,?,?,?,?,?,?)";
		int rs[] = this.getJdbcTemplate().batchUpdate(strSql, new BatchPreparedStatementSetter(){
			public int getBatchSize() {
				return list.size();
			}
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				TProjectAccounting ta = list.get(i);
				ps.setString(1, CodeGenerator.getUUID());
				ps.setString(2, ta.getProjectId());
				ps.setInt(3, ta.getAccountingItemId());
				ps.setString(4, ta.getAccountingItem());
				ps.setString(5, ta.getCurrency());
				ps.setString(6, ta.getAccountingValue());
				ps.setString(7, DateUtils.getCurrTime(DateUtils.DB_STORE_DATE));
				ps.setString(8, UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserId());
			}
		});
		return rs.length;
				
	}
	/**
	 * 更新记录
	 * @return
	 */
	private int updateProjectAccouting(final List<TProjectAccounting> list) {
			StringBuffer sb = new StringBuffer();
			sb.append("update T_PROJECT_ACCOUNTING set ");
			sb.append("accounting_value=? ");
			sb.append("where accounting_item_id=? and project_id=? and currency=? and trim(contract_sales_id) is null and trim(contract_group_id) is null ");
			int rs[] = this.getJdbcTemplate().batchUpdate(sb.toString(), new BatchPreparedStatementSetter(){
				public int getBatchSize() {
					return list.size();
				}
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					TProjectAccounting ta = list.get(i);
					ps.setString(1, ta.getAccountingValue());
					ps.setInt(2, ta.getAccountingItemId());
					ps.setString(3, ta.getProjectId());
					ps.setString(4, ta.getCurrency());
				}
			});
			return rs.length;
		}
	/**
	 * 判断是否有记录存在
	 * @param list
	 * @return
	 */
	private boolean isExistProjectAccouting(List<TProjectAccounting> list){
		String sql = "select count(*) from T_PROJECT_ACCOUNTING where project_id=? and trim(contract_sales_id) is null and trim(contract_group_id) is null";
		TProjectAccounting ta = list.get(0);
		int i=this.getJdbcTemplate().queryForInt(sql,new Object[]{ta.getProjectId()});
		return i!=0;
	}
	/**
	 * 获取
	 * @param projectId
	 * @return
	 */
	public Map<String,TProjectAccounting> getProjectAccounting(String projectId){
		String sql = "Select * from t_project_accounting where project_id=? and trim(contract_sales_id) is null and trim(contract_group_id) is null order by accounting_item_id";
		final Map<String,TProjectAccounting> map = new HashMap<String,TProjectAccounting>();
		this.getJdbcTemplate().query(sql, new Object[]{projectId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				TProjectAccounting ta = new TProjectAccounting();
				ta.setAccountingItem(rs.getString("accounting_item"));
				ta.setAccountingItemId(rs.getInt("accounting_item_id"));
				ta.setAccountingValue(rs.getString("accounting_value"));
				ta.setCreator(rs.getString("creator"));
				ta.setCreatorTime(rs.getString("creator_time"));
				ta.setCurrency(rs.getString("currency"));
				ta.setProjectId(rs.getString("project_id"));
				ta.setId(rs.getString("id"));
				map.put(rs.getRow()+"", ta);
			}});
		return map;
	}
	
	/**
	 * 取得立项核算项的值
	 * @param projectId
	 * @param currency
	 * @param item
	 * @return
	 */
	public String getContractGroupAccountValue(String contractGroupId, String currency,	String item) {
		String sql = "select accounting_value from t_project_accounting where contract_group_id=? and currency=? and accounting_item=?";
		List<Map<String,Object>> list =  this.getJdbcTemplate().queryForList(sql,new Object[]{contractGroupId,currency,item});
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			return (String)map.get("accounting_value");
		}else
			return "";
	
	}
	
	public Map<String,TProjectAccounting> getContractGroupAccounting(String contractGroupId,String item){
		String sql = "Select * from t_project_accounting where contract_group_id=? and accounting_item_id=? order by accounting_item_id";
		final Map<String,TProjectAccounting> map = new HashMap<String,TProjectAccounting>();
		this.getJdbcTemplate().query(sql, new Object[]{contractGroupId,item},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				TProjectAccounting ta = new TProjectAccounting();
				ta.setAccountingItem(rs.getString("accounting_item"));
				ta.setAccountingItemId(rs.getInt("accounting_item_id"));
				ta.setAccountingValue(rs.getString("accounting_value"));
				ta.setCreator(rs.getString("creator"));
				ta.setCreatorTime(rs.getString("creator_time"));
				ta.setCurrency(rs.getString("currency"));
				ta.setProjectId(rs.getString("project_id"));
				ta.setContractGroupId(rs.getString("contract_group_id"));
				ta.setContractSalesId(rs.getString("contract_sales_id"));
				ta.setId(rs.getString("id"));
				map.put(rs.getRow()+"", ta);
			}});
		return map;
	}
	/**
	 * 获取
	 * @param contractGroupId
	 * @return
	 */
	public Map<String,TProjectAccounting> getContractGroupAccounting(String contractGroupId){
		
		//去掉行为25是（合同预计利润率）是文本不能求和,税率40,45取平均值 itemid<>4 排除了单位
		//合同预计利润率）自动计算=预计利润总额/数量（25可以计算）
		String sql = "Select max(pa.id) as id,max(pa.project_id) as project_id, pa.accounting_item_id as accounting_item_id," +
				"max(pa.accounting_item) as accounting_item,max(pa.creator_time) as creator_time,max(pa.creator) as creator," +
				"max(pa.contract_sales_id) as contract_sales_id,max(pa.contract_group_id) as contract_group_id,pa.currency  ," +
				"case when pa.accounting_item_id='40' then to_char(avg(pa.accounting_value) ) when pa.accounting_item_id='45' then to_char(avg(pa.accounting_value) ) else to_char(sum(pa.accounting_value) ) end as accounting_value " +
				"from t_project_accounting pa ,t_contract_sales_info cs where   pa.contract_sales_id=cs.contract_sales_id   and cs.is_available=1 and cs.order_state in (3,5,6,7,8,9,10) and pa.contract_group_id=?  and pa.accounting_item_id not in ('4','58') " +
				" group by pa.accounting_item_id,pa.currency  order by accounting_item_id";
		
		final Map<String,TProjectAccounting> map = new HashMap<String,TProjectAccounting>();
		this.getJdbcTemplate().query(sql, new Object[]{contractGroupId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				TProjectAccounting ta = new TProjectAccounting();
				ta.setAccountingItem(rs.getString("accounting_item"));
				ta.setAccountingItemId(rs.getInt("accounting_item_id"));
				ta.setAccountingValue(rs.getString("accounting_value"));
				ta.setCreator(rs.getString("creator"));
				ta.setCreatorTime(rs.getString("creator_time"));
				ta.setCurrency(rs.getString("currency"));
				ta.setProjectId(rs.getString("project_id"));
				ta.setContractGroupId(rs.getString("contract_group_id"));
				ta.setContractSalesId(rs.getString("contract_sales_id"));
				ta.setId(rs.getString("id"));
				map.put(rs.getRow()+"", ta);
			}});
		return map;
	}
	/**
	 * 更新立项信息为有效状态
	 */
	public void updateProjectToPass(String projectId){
		String sql = "update t_project_info set project_state=? , approved_time=? where project_Id=?";
		this.getJdbcTemplate().update(sql, new Object[]{"3","20090305120000",projectId});
		
	}
}
