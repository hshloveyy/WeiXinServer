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
public class ContractAccountingJdbcDao extends BaseDao{
	/**
	 * 保存或更新项目核算记录
	 * @param pa
	 */
	public int saveOrUpdateContractAccounting(final List<TProjectAccounting> list) {
		if(isExistContractAccouting(list)){
			return updateContractAccouting(list);
		}else{
			return saveContractAccounting(list);
		}
	}
	/**
	 * 保存记录
	 * @param list
	 * @return
	 */
	private int saveContractAccounting(final List<TProjectAccounting> list){
		String strSql = "insert into T_PROJECT_ACCOUNTING "+
						"(id, " +
						"project_id, " +						
						"accounting_item_id, " +
						"accounting_item, " +
						"currency, " +
						"accounting_value, " +
						"creator_time, " +
						"creator,contract_sales_id, " +
						"contract_group_id) " +
						"values " +
						"(?,?,?,?,?,?,?,?,?,?)";
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
				ps.setString(9, ta.getContractSalesId());
				ps.setString(10, ta.getContractGroupId());
			}
		});
		return rs.length;
				
	}
	/**
	 * 更新记录
	 * @return
	 */
	private int updateContractAccouting(final List<TProjectAccounting> list) {
			StringBuffer sb = new StringBuffer();
			sb.append("update T_PROJECT_ACCOUNTING set ");
			sb.append("accounting_value=? ");
			sb.append("where accounting_item_id=? and contract_sales_id=? and currency=? ");
			int rs[] = this.getJdbcTemplate().batchUpdate(sb.toString(), new BatchPreparedStatementSetter(){
				public int getBatchSize() {
					return list.size();
				}
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					TProjectAccounting ta = list.get(i);
					ps.setString(1, ta.getAccountingValue());
					ps.setInt(2, ta.getAccountingItemId());
					ps.setString(3, ta.getContractSalesId());
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
	private boolean isExistContractAccouting(List<TProjectAccounting> list){
		String sql = "select count(*) from T_PROJECT_ACCOUNTING where contract_sales_id=?";
		TProjectAccounting ta = list.get(0);
		int i=this.getJdbcTemplate().queryForInt(sql,new Object[]{ta.getContractSalesId()});
		return i!=0;
	}
	/**
	 * 获取
	 * @param projectId
	 * @return
	 */
	public Map<String,TProjectAccounting> getContractAccounting(String contract_sales_id){
		String sql = "Select * from t_project_accounting where contract_sales_id=? order by accounting_item_id";
		final Map<String,TProjectAccounting> map = new HashMap<String,TProjectAccounting>();
		this.getJdbcTemplate().query(sql, new Object[]{contract_sales_id},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				TProjectAccounting ta = new TProjectAccounting();
				ta.setAccountingItem(rs.getString("accounting_item"));
				ta.setAccountingItemId(rs.getInt("accounting_item_id"));
				ta.setAccountingValue(rs.getString("accounting_value"));
				ta.setCreator(rs.getString("creator"));
				ta.setCreatorTime(rs.getString("creator_time"));
				ta.setCurrency(rs.getString("currency"));
				ta.setContractSalesId(rs.getString("contract_sales_id"));
				ta.setContractGroupId(rs.getString("contract_group_id"));
				ta.setId(rs.getString("id"));
				map.put(rs.getRow()+"", ta);
			}});
		return map;
	}	
}
