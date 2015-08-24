/*
 * @(#)TSysDeptJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2008-12-1
 *  描　述：创建
 */

package com.infolion.platform.console.org.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.workflow.domain.ComboBoxFormat;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.DateUtils;

@Repository
public class SysDeptJdbcDao extends BaseDao {
	public List<SysDept> queryDeptByParentId(String in_strParentId){
		final List<SysDept> deptList = new ArrayList();
		String strSql ="select a.*, " +
		       "(select b.dept_name from t_sys_dept b where b.dept_id = a.pdept_id) pdeptname, " +
		       "(select count(b.dept_id) from t_sys_dept b where b.pdept_id = a.dept_id and b.deleted = '1') childcount " +
		  "from t_sys_dept a " +
		 "where a.deleted = '1' and a.pdept_id =? order by a.display_no";
		
		getJdbcTemplate().query(strSql, new Object[]{in_strParentId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysDept tSysDept = new SysDept();
				tSysDept.setDeptid(rs.getString("dept_id"));
				tSysDept.setDeptname(rs.getString("dept_name"));
				tSysDept.setDeptcode(rs.getString("dept_code"));
				tSysDept.setPdeptid(rs.getString("pdept_id"));
				tSysDept.setPdeptname(rs.getString("pdeptname"));
				tSysDept.setYwbmcode(rs.getString("ywbm_code"));
				tSysDept.setLrzxcode(rs.getString("lrzx_code"));
				tSysDept.setCbzxcode(rs.getString("cbzx_code"));
				tSysDept.setXszzcode(rs.getString("xszz_code"));
				tSysDept.setCgzzcode(rs.getString("cgzz_code"));
				tSysDept.setYszzcode(rs.getString("yszz_code"));
				tSysDept.setCreator(rs.getString("creator"));
				tSysDept.setCreatetime(rs.getString("create_time"));
				tSysDept.setDeleted(rs.getString("deleted"));
				tSysDept.setChildcount(rs.getInt("childcount"));
				tSysDept.setCompanyCode(rs.getString("company_code"));
				deptList.add(tSysDept);
			}});
		return deptList;
	}
	
	public SysDept queryDeptById(String in_strDeptId){
		final SysDept tSysDept = new SysDept();
		String strSql = "select a.*, " +
					"(select b.dept_name from t_sys_dept b where b.dept_id = a.pdept_id) pdeptname, " +
					"(select count(b.dept_id) from t_sys_dept b where b.pdept_id = a.dept_id and b.deleted = '1') childcount "+
				  "from t_sys_dept a " +
				 "where a.deleted = '1' " +
				   "and a.dept_id = ?";
		getJdbcTemplate().query(strSql, new Object[]{in_strDeptId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				tSysDept.setDeptid(rs.getString("dept_id"));
				tSysDept.setDeptname(rs.getString("dept_name"));
				tSysDept.setDeptcode(rs.getString("dept_code"));
				tSysDept.setPdeptid(rs.getString("pdept_id"));
				tSysDept.setPdeptname(rs.getString("pdeptname"));
				tSysDept.setYwbmcode(rs.getString("ywbm_code"));
				tSysDept.setLrzxcode(rs.getString("lrzx_code"));
				tSysDept.setCbzxcode(rs.getString("cbzx_code"));
				tSysDept.setXszzcode(rs.getString("xszz_code"));
				tSysDept.setCgzzcode(rs.getString("cgzz_code"));
				tSysDept.setYszzcode(rs.getString("yszz_code"));
				tSysDept.setIsFuncDept(rs.getString("is_func_dept"));
				tSysDept.setDeptShortCode(rs.getString("dept_short_code"));
				tSysDept.setCreator(rs.getString("creator"));
				tSysDept.setCreatetime(rs.getString("create_time"));
				tSysDept.setDeleted(rs.getString("deleted"));
				tSysDept.setChildcount(rs.getInt("childcount"));
				tSysDept.setCompanyCode(rs.getString("company_code"));
			}});
		return tSysDept;
	}

	public void addDept(SysDept sysDept){
		String strSql = "insert into t_sys_dept " +
			  "(dept_id, " +
			   "dept_name, " +
			   "dept_code, " +
			   "pdept_id, " +
			   "ywbm_code, " +
			   "lrzx_code, " +
			   "cbzx_code, " +
			   "xszz_code, " +
			   "cgzz_code, " +
			   "yszz_code, " +
			   "creator, " +
			   "create_time, " +
			   "deleted," +
			   "is_func_dept," +
			   "dept_short_code," +
			   "company_code) " +
			"values " +
			 "(?,?,?,?,?,?,?,?,?,?,?,?,'1',?,?,?)";
		Object[] params = new Object[]{
				sysDept.getDeptid(),
				sysDept.getDeptname(),
				sysDept.getDeptcode(),
				sysDept.getPdeptid(),
				sysDept.getYwbmcode(),
				sysDept.getLrzxcode(),
				sysDept.getCbzxcode(),
				sysDept.getXszzcode(),
				sysDept.getCgzzcode(),
				sysDept.getYszzcode(),
				sysDept.getCreator(),
				DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),
				sysDept.getIsFuncDept(),
				sysDept.getDeptShortCode(),
				sysDept.getCompanyCode()
		};
		getJdbcTemplate().update(strSql,params);
	}
	
	public void updateDept(SysDept sysDept){
		String strSql ="update t_sys_dept set dept_name = ?, " +
					"dept_code = ?, " +
					"ywbm_code = ?, " +
					"lrzx_code = ?, " +
					"cbzx_code = ?, " +
					"xszz_code = ?, " +
					"cgzz_code = ?, " +
					"yszz_code = ?," +
					"is_func_dept = ?," +
					"dept_short_code = ?," +
					"company_code = ? " +
					"where dept_id = ?";
		Object[] params = new Object[]{
				sysDept.getDeptname(),
				sysDept.getDeptcode(),
				sysDept.getYwbmcode(),
				sysDept.getLrzxcode(),
				sysDept.getCbzxcode(),
				sysDept.getXszzcode(),
				sysDept.getCgzzcode(),
				sysDept.getYszzcode(),
				sysDept.getIsFuncDept(),
				sysDept.getDeptShortCode(),
				sysDept.getCompanyCode(),
				sysDept.getDeptid(),
		};
		getJdbcTemplate().update(strSql,params);
	}
	
	public void deleteDept(String in_strDeptId){
		String strSql = "update t_sys_dept set deleted = '0' where dept_id = ?";
		Object[] params = new Object[]{in_strDeptId};
		getJdbcTemplate().update(strSql,params);
	}
	
	/**
	 * 得到公司代码
	 * @param strDeptId
	 * @return
	 */
	public String getCompanyCode(String strDeptId){
		final SysDept tSysDept = new SysDept();
		
		String strSql ="select dept_code " +
				  " from (select a.pdept_id,a.dept_name, a.company_code,a.dept_code, level record_order " +
				          " from t_sys_dept a " +
				        " connect by a.dept_id = prior a.pdept_id " +
				         " start with a.dept_id = ? ) a " +
				 " where a.pdept_id = 'usercompany' " +
				   " and rownum = 1 " +
				 " order by a.record_order";
		
		getJdbcTemplate().query(strSql, new Object[]{strDeptId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				tSysDept.setDeptcode(rs.getString("dept_code"));
			}});
		
		return tSysDept.getDeptcode();
	}

	/**
	 * 获得部门code
	 * @param deptid
	 * @return
	 */
	public String getDeptCodeById(String deptid)
	{
		String sql = "select DEPT_CODE deptcode from T_SYS_DEPT where DEPT_ID = '" 
				+ deptid + "'";
		
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		return list.get(0).get("deptcode");
	}
	
	public String getCompanyCodeByUserName(String username)
	{
		String sql = "select DEPT_ID deptid from T_SYS_USER where USER_NAME = '" 
			+ username + "'";
	
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		String deptid = list.get(0).get("deptid");
		 
		return getCompanyCode(deptid);
	}
	
	public String getDeptIdByUserName(String username)
	{
		String sql = "select DEPT_ID deptid from T_SYS_USER where USER_NAME = '" 
			+ username + "'";
	
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		return list.get(0).get("deptid");
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-08-18
	 * 根据用户ID获取公司代码
	 * @param userId 用户编号
	 * @return 公司代码
	 */
	public String getCompanyCodeByUserId(String userId){
		String sql = "select DEPT_ID deptid from T_SYS_USER where USER_ID = '" + userId + "'";
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		String deptId = list.get(0).get("deptid");
		return this.getCompanyCode(deptId);
	}
	/**
	 * @创建作者：钟志华
	 * @创建日期：2010-11-1
	 * 根据业务范围取得部门ID 
	 * @param deptCode 业务范围
	 * @return
	 */
	public List<String> getDeptIdByDeptCode(String deptCode){
		String sql = "select DEPT_ID deptid from t_Sys_Dept where DEPT_CODE = '" + deptCode + "'";
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		List<String> li =new ArrayList<String>();
		if(null != list && list.size()>0){
			for(Map<String,String> map: list){
				li.add(map.get("deptid"));
			}
		}
		return li;
	}
}
