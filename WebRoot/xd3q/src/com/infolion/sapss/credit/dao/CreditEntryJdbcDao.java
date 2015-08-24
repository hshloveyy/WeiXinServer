/*
 * @(#)CreaditEntryJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-7
 *  描　述：创建
 */

package com.infolion.sapss.credit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.credit.domain.TCreditInfo;

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
@Repository
public class CreditEntryJdbcDao extends BaseDao
{

	/**
	 * 根据ID删除信用证表数据(T_CREDIT_INFO)
	 * @param creditId
	 * @return
	 */
	public int delete(String creditId){
		String sql="update T_CREDIT_INFO set is_available='0' where credit_id=?  and credit_state in('1')";
		
		return this.getJdbcTemplate().update(sql, new Object[]{creditId});
	}
	
	/**
	 * 根据ID信用证开证表数据(T_CREDIT_CREATE)
	 * @param creditId
	 * @return
	 */
	public int deleteCreditCreate(String creditId){
		String sql="delete T_CREDIT_CREATE where credit_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{creditId});
	}
	
	/**
	 * 提交流程审批后更新状态
	 * 状态:1、信用证开证 2、信用证收证 3、审批中 4、生效 5、改证 6、撤销 7、作废
	 * @param creditId
	 */
	public int submitUpdateState(String creditId){
		String sql = "update T_CREDIT_INFO set credit_state=?,apply_time=?  where CREDIT_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[]{"3", DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),creditId});
	}
	/**
	 * 
	 * @param creditId
	 * @return
	 */
	public int updateState(String creditId,String statusCode){
		String sql = "update T_CREDIT_INFO set credit_state=?,apply_time=?  where CREDIT_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[]{statusCode, DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),creditId});
	}
	
	/**
	 * 流程审批后更新信用证状态
	 * 状态:1、信用证开证 2、信用证收证 3、审批中 4、生效5、备用6、过期7、撤销8、关闭9、作废10、改证
	 * @param projectInfo
	 * @return
	 */
	public int workflowUpdateState(String businessRecordId,String examineResult,String result){
		String sql = "update T_CREDIT_INFO set credit_state=? , approved_time=? where CREDIT_ID=?";
		return this.getJdbcTemplate().update(sql, new Object[]{result,DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),businessRecordId});
	}
	/**
	 * 
	 * @param creditId
	 * @param status
	 */
	public void saveCreditStatus(String creditId, String status) {
		String sql = "update T_CREDIT_INFO set credit_state=? where CREDIT_ID=?";
		//String sql1="update t_credit_his_info set credit_state=? where credit_id=? and version_no=(select max(version_no) from t_credit_his_info where credit_id=?)";
		this.getJdbcTemplate().update(sql, new Object[]{status,creditId});
		//this.getJdbcTemplate().update(sql1, new Object[]{status,creditId,creditId});
	}
	public void dealOutToExcel(String sql ,final ExcelObject excel){
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[14];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("CONTRACT_NO");
				values[2] = rs.getString("PROJECT_NO");
				values[3] = rs.getString("project_name");
				values[4] = rs.getString("CREDIT_NO");
				values[5] = rs.getString("CREATE_BANK");
				values[6] = rs.getString("CREATE_DATE");
				values[7] = rs.getString("PAYMENT_TYPE");
				values[8] = rs.getString("BENEFIT");
				values[9] = rs.getString("CURRENCY");
				values[10] = rs.getString("AMOUNT");
				values[11] = rs.getString("valid_Date");
				values[12] = rs.getString("loading_Period");
				values[13] = rs.getString("title");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
}
