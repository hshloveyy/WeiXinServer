/*
 * @(#)SysUserJdbcDao.java
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

import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.sys.cache.SysCachePoolUtils;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
@Repository
public class SysUserJdbcDao extends BaseDao {
	/*
	 * 
	 通过部门编号查询员工信息
	 * 
	 */
	public List<SysUser> queryUserByDeptId(String in_strDeptId){
		final List<SysUser> sysUserList = new ArrayList();
		String strSql = "select b.user_id, " +
			       "b.user_name, " +
			       "b.real_name, " +
			       "b.sex, " +
			       "b.address, " +
			       "b.id_card, " +
			       "b.e_mail, " +
			       "b.position_des, " +
			       "b.cmd, " +
			       "b.creator, " +
			       "b.create_time," +
			       "b.employee_No, " +
			       "b.mobile, "+
			       "c.dept_id, " +
			       "c.dept_name, " +
			       "a.dept_user_id " +
			  "from t_sys_dept_user a, t_sys_user b, t_sys_dept c " +
			 "where a.user_id = b.user_id " +
			   "and b.deleted = '1' " +
			   "and a.dept_id = c.dept_id " +
			   "and c.deleted = '1' " +
			   "and c.dept_id = ?";
		getJdbcTemplate().query(strSql, new Object[]{in_strDeptId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				//用于判断如果是超级管理员的员工不能显示出来
				if (!rs.getString("user_id").equals("administrator")){
					SysUser sysUser = new SysUser();
					sysUser.setUserId(rs.getString("user_id"));
					sysUser.setUserName(rs.getString("user_name"));
					sysUser.setRealName(rs.getString("real_name"));
					sysUser.setSex(SysCachePoolUtils.getDictDataValue("BM_SYS_SEX", rs.getString("sex")));
					sysUser.setAddress(rs.getString("address"));
					sysUser.setIdCard(rs.getString("id_card"));
					sysUser.setMobile(rs.getString("mobile"));
					sysUser.setEMail(rs.getString("e_mail"));
					sysUser.setCreator(rs.getString("creator"));
					sysUser.setCreateTime(rs.getString("create_time"));
					sysUser.setPositionDes(rs.getString("position_des"));
					sysUser.setCmd(rs.getString("cmd"));
					sysUser.setDeptId(rs.getString("dept_id"));
					sysUser.setDeptName(rs.getString("dept_name"));
					sysUser.setDeptUserId(rs.getString("dept_user_id"));
					sysUser.setEmployeeNo(rs.getString("employee_No"));
					sysUserList.add(sysUser);
				}
			}});
		
		return sysUserList;
	}
	
	/*
	 * 
	 通过员工编号和部门编号查询员工信息
	 * 
	 */
	public SysUser queryUserById(String in_strUserId,String in_StrDeptId){
		final SysUser sysUser = new SysUser();
		
		String strSql ="select b.user_id, " +
			       "b.user_name, " +
			       "b.password, " +
			       "b.real_name, " +
			       "b.sex, " +
			       "b.address, " +
			       "b.id_card, " +
			       "b.e_mail, " +
			       "b.creator, " +
			       "b.create_time, " +
			       "b.position_des, " +
			       "b.employee_No," +
			       "b.cmd, " +
			       "b.mobile, "+
			       "c.dept_id, " +
			       "c.dept_name, " +
			       "a.dept_user_id " +
			  "from t_sys_dept_user a, t_sys_user b, t_sys_dept c " +
			 "where a.user_id = b.user_id " +
			   "and b.deleted = '1' " +
			   "and a.dept_id = c.dept_id " +
			   "and c.deleted = '1' " +
			   "and b.user_id = ? " +
			   "and c.dept_id = ?";

		getJdbcTemplate().query(strSql, new Object[]{in_strUserId,in_StrDeptId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				sysUser.setUserId(rs.getString("user_id"));
				sysUser.setUserName(rs.getString("user_name"));
				sysUser.setPassword(rs.getString("password"));
				sysUser.setRealName(rs.getString("real_name"));
				sysUser.setSex(rs.getString("sex"));
				sysUser.setAddress(rs.getString("address"));
				sysUser.setIdCard(rs.getString("id_card"));
				sysUser.setEMail(rs.getString("e_mail"));
				sysUser.setCreator(rs.getString("creator"));
				sysUser.setCreateTime(rs.getString("create_time"));
				sysUser.setDeptId(rs.getString("dept_id"));
				sysUser.setPositionDes(rs.getString("position_des"));
				sysUser.setCmd(rs.getString("cmd"));
				sysUser.setDeptName(rs.getString("dept_name"));
				sysUser.setDeptUserId(rs.getString("dept_user_id"));
				sysUser.setEmployeeNo(rs.getString("employee_No"));
				sysUser.setMobile(rs.getString("mobile"));
			}});
		return sysUser;
	}
	
	/**
	 * 通过角色编号查询该拥有该角色的员工信息
	 * @param in_strRoleId
	 */
	public List<SysUser> queryUserByRoleId(String in_strRoleId){
		final List<SysUser> sysUserList = new ArrayList();
		
		String strSql ="select b.dept_user_id, " +
					"c.user_id, " +
					"c.user_name, " +
					"c.real_name, " +
				       "c.position_des, " +
				       "c.cmd, " +
					"d.dept_name, " +
					"d.dept_id " +
					"from t_sys_user_role a, " +
					"t_sys_dept_user b, " +
					"t_sys_user c, " +
					"t_sys_dept d " +
					"where a.role_id = ? " +
					"and a.dept_user_id = b.dept_user_id " +
					"and b.user_id = c.user_id " +
					"and c.deleted = '1' " +
					"and b.dept_id = d.dept_id " +
					"and d.deleted  = '1'";
		getJdbcTemplate().query(strSql, new Object[]{in_strRoleId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysUser sysUser = new SysUser();
				sysUser.setDeptUserId(rs.getString("dept_user_id"));
				sysUser.setUserId(rs.getString("user_id"));
				sysUser.setUserName(rs.getString("user_name"));
				sysUser.setPositionDes(rs.getString("position_des"));
				sysUser.setCmd(rs.getString("cmd"));
				sysUser.setRealName(rs.getString("real_name"));
				sysUser.setDeptName(rs.getString("dept_name"));
				sysUser.setDeptName(rs.getString("dept_id"));
				sysUserList.add(sysUser);
			}});
		
		return sysUserList;
	}
	
	
	/**
	 * 通过角色编号查询该拥有该角色的员工信息
	 * @param in_strRoleId
	 */
	public List<SysUser> receiveUserStaff(String strRoleName,String strDeptId){
		final List<SysUser> sysUserList = new ArrayList();
		
		String strSql ="select b.dept_user_id, " +
				       "c.user_id, " +
				       "c.user_name, " +
				       "c.real_name, " +
				       "c.position_des, " +
				       "c.cmd, " +
				       "d.dept_name, " +
				       "d.dept_id " +
				  "from t_sys_user_role a, t_sys_dept_user b, t_sys_user c, t_sys_dept d,t_sys_role e " +
				 "where a.dept_user_id = b.dept_user_id " +
				   "and b.user_id = c.user_id " +
				   "and c.deleted = '1' " +
				   "and b.dept_id = d.dept_id " +
				   "and d.deleted = '1' " +
				   "and a.role_id = e.role_id " +
				   "and e.deleted = '1' ";
		
		if (strDeptId != null && !"".equals(strDeptId)){
			strSql = strSql + " and d.dept_id = '" +strDeptId+ "' ";
		}
		
		if (strRoleName != null && !"".equals(strRoleName)){
			strSql = strSql + " and e.role_name like '%"+strRoleName+"%'";
		}
		                       
		getJdbcTemplate().query(strSql,new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysUser sysUser = new SysUser();
				sysUser.setDeptUserId(rs.getString("dept_user_id"));
				sysUser.setUserId(rs.getString("user_id"));
				sysUser.setUserName(rs.getString("user_name"));
				sysUser.setPositionDes(rs.getString("position_des"));
				sysUser.setCmd(rs.getString("cmd"));
				sysUser.setRealName(rs.getString("real_name"));
				sysUser.setDeptName(rs.getString("dept_name"));
				sysUserList.add(sysUser);
			}});
		
		return sysUserList;
	}
	
	/*
	 * 
	 通过员工用户名判断是否已经有同名的员工存在
	 * 
	 */
	public int queryUserIsExist(String in_strUserName){

		String strSql ="select count(a.dept_user_id) " +
		  "from t_sys_dept_user a, t_sys_user b, t_sys_dept c " +
		 "where a.user_id = b.user_id " +
		   "and b.deleted = '1' " +
		   "and a.dept_id = c.dept_id " +
		   "and c.deleted = '1' " +
		   "and b.user_name = ? ";
		
		return getJdbcTemplate().queryForInt(strSql, new Object[]{in_strUserName});
	}
	
	/*
	 * 
	 通过员工编号查询员工的部门所属情况
	 * 
	 */
	
	public List<SysUser> queryUserByUserId(String in_strUserId){
		final List<SysUser> sysUserList = new ArrayList();
		String strSql = "select b.user_id, " +
		       "b.user_name, " +
		       "b.real_name, " +
		       "b.sex, " +
		       "b.address, " +
		       "b.id_card, " +
		       "b.e_mail, " +
		       "b.creator, " +
		       "b.create_time, " +
		       "b.position_des, " +
		       "b.cmd, " +
		       "c.dept_id, " +
		       "c.dept_name " +
		  "from t_sys_dept_user a, t_sys_user b, t_sys_dept c " +
		 "where a.user_id = b.user_id " +
		   "and b.deleted = '1' " +
		   "and a.dept_id = c.dept_id " +
		   "and c.deleted = '1' " +
		   "and b.user_id = ?";

		getJdbcTemplate().query(strSql, new Object[]{in_strUserId},new RowCallbackHandler(){
		public void processRow(ResultSet rs) throws SQLException{
			SysUser sysUser = new SysUser();
			sysUser.setUserId(rs.getString("user_id"));
			sysUser.setUserName(rs.getString("user_name"));
			sysUser.setRealName(rs.getString("real_name"));
			sysUser.setSex(SysCachePoolUtils.getDictDataValue("BM_SYS_SEX", rs.getString("sex")));
			sysUser.setAddress(rs.getString("address"));
			sysUser.setIdCard(rs.getString("id_card"));
			sysUser.setEMail(rs.getString("e_mail"));
			sysUser.setCreator(rs.getString("creator"));
			sysUser.setCreateTime(rs.getString("create_time"));
			sysUser.setPositionDes(rs.getString("position_des"));
			sysUser.setCmd(rs.getString("cmd"));
			sysUser.setDeptId(rs.getString("dept_id"));
			sysUser.setDeptName(rs.getString("dept_name"));
			sysUserList.add(sysUser);
		}});

		return sysUserList;
	}
	
	/**
	 * 
	 * 通过条件查询用户实体表的信息
	 */
	public List<SysUser> queryUserByCondition(SysUser sysUser){
		final List<SysUser> sysUserList = new ArrayList();
		
		String strSql ="select b.user_id, " +
			       "b.user_name, " +
			       "b.real_name, " +
			       "b.sex, " +
			       "b.address, " +
			       "b.id_card, " +
			       "b.e_mail, " +
			       "b.creator, " +
			       "b.position_des, " +
			       "b.cmd, " +
			       "b.create_time, " +
			       "'' dept_id, " +
			       "'' dept_name " +
			  "from t_sys_user b " +
			 "where b.deleted = '1' " +
			 "and b.dept_id is null ";
		if (sysUser.getUserName() != null &&
			!"".equals(sysUser.getUserName())){
			strSql = strSql + "and b.user_name like '" + sysUser.getUserName() + "%'";
		}
		
		if (sysUser.getRealName() != null &&
			!"".equals(sysUser.getRealName())){
			strSql = strSql + "and b.real_name like '" + sysUser.getRealName()+ "%'";
		}
		
		if (sysUser.getSex() != null &&
			!"".equals(sysUser.getSex())){
			strSql = strSql + "and b.sex = '" + sysUser.getSex()+ "'";
		}
		
		if (sysUser.getIdCard() != null &&
			!"".equals(sysUser.getIdCard())){
			strSql = strSql + "and b.id_card like '" + sysUser.getIdCard()+ "%'";
		}
		
		getJdbcTemplate().query(strSql, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysUser sysUser = new SysUser();
				sysUser.setUserId(rs.getString("user_id"));
				sysUser.setUserName(rs.getString("user_name"));
				sysUser.setRealName(rs.getString("real_name"));
				sysUser.setSex(SysCachePoolUtils.getDictDataValue("BM_SYS_SEX", rs.getString("sex")));
				sysUser.setAddress(rs.getString("address"));
				sysUser.setIdCard(rs.getString("id_card"));
				sysUser.setEMail(rs.getString("e_mail"));
				sysUser.setCreator(rs.getString("creator"));
				sysUser.setCreateTime(rs.getString("create_time"));
				sysUser.setDeptId(rs.getString("dept_id"));
				sysUser.setDeptName(rs.getString("dept_name"));
				sysUser.setPositionDes(rs.getString("position_des"));
				sysUser.setCmd(rs.getString("cmd"));
				sysUserList.add(sysUser);
			}});
		
		return sysUserList;
	}
	
	public void addUser(SysUser sysUser){
		//增加员工实体表
		String strSql = "insert into t_sys_user " +
				  "(user_id, " +
				   "dept_id, " +
				   "user_name, " +
				   "real_name, " +
				   "sex, " +
				   "address, " +
				   "id_card, " +
				   "password, " +
				   "e_mail, " +
				   "creator, " +
				   "create_time, " +
				   "position_des, " +
				   "cmd, " +
				   "deleted," +
				   "employee_No," +
				   "mobile)"+
				"values " +
				  "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?,'1',?,?)";

		Object[] params = new Object[]{
				sysUser.getUserId(),
				sysUser.getDeptId(),
				sysUser.getUserName(),
				sysUser.getRealName(),
				sysUser.getSex(),
				sysUser.getAddress(),
				sysUser.getIdCard(),
				sysUser.getPassword(),
				sysUser.getEMail(),
				sysUser.getCreator(),
				DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),
				sysUser.getPositionDes(),
				sysUser.getCmd(),
			    sysUser.getEmployeeNo(),
			    sysUser.getMobile()
		};
		
		getJdbcTemplate().update(strSql,params);
		
		//增加员工部门关系表
		strSql = "insert into t_sys_dept_user " +
		  "(dept_user_id, user_id, dept_id, creator, create_time) " +
		"values " +
		  "(?, ?, ?, ?, ?)";
		
		params = null;
		
		params = new Object[]{
				sysUser.getDeptUserId(),
				sysUser.getUserId(),
				sysUser.getDeptId(),
				sysUser.getCreator(),
				DateUtils.getCurrTime(DateUtils.DB_STORE_DATE)
		};
		
		getJdbcTemplate().update(strSql,params);
	}
	
	public void updateUser(SysUser sysUser){
		//修改员工实体信息
		String strSql = "update t_sys_user " +
				   "set user_name = ?, " +
				       "real_name = ?," +
				       "sex = ?, " +
				       "address   = ?, " +
				       "id_card   = ?, " +
				       "e_mail    = ?, " +
				       "password    = ?, " +
					   "position_des = ?, " +
					   "cmd = ?, " +
				       "is_init_user = '1'," +
				       "employee_No=?, " +
				       "mobile=? " +
				 "where user_id = ? " +
				   "and deleted = '1'";
		Object[] params = new Object[]{
				sysUser.getUserName(),
				sysUser.getRealName(),
				sysUser.getSex(),
				sysUser.getAddress(),
				sysUser.getIdCard(),
				sysUser.getEMail(),
				sysUser.getPassword(),
				sysUser.getPositionDes(),
				sysUser.getCmd(),
				sysUser.getEmployeeNo(),
				sysUser.getMobile(),
				sysUser.getUserId()
		};
		
		getJdbcTemplate().update(strSql,params);
	}
	
	/**
	 * 修改登陆员工的密码
	 * @param in_strNewPass
	 * @param in_strOldPass
	 */
	public int updateUserPassword(String in_strNewPass,String in_strOldPass){
		String strNewPassWord = CodeGenerator.getMD5Digest(in_strNewPass);
		String strOldPassWord = CodeGenerator.getMD5Digest(in_strOldPass);
		String strSql = "update t_sys_user " +
						"set password = ?,is_init_user='0' " +
						"where user_id = ? and password = ?";
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

		Object[] params = new Object[]{
				strNewPassWord,
			userContext.getSysUser().getUserId(),
			strOldPassWord
		};
		
		return getJdbcTemplate().update(strSql,params);
	}
	
	public void deleteUser(String in_strUserList,String in_strDeptId){
		String strDeleteSql = "delete t_sys_dept_user " +
					 "where user_id = ? " +
					   "and dept_id = ?";
		String strSelectSql = "select count(a.user_id) from t_sys_dept_user a where a.user_id = ?";
		
		String strDeleteUserSql = "update t_sys_user set deleted = '0' where user_id = ?";
		
		String[] IdList = in_strUserList.split("\\|");
		//在删除部门员工关系表时，如果用户在这张表中没有记录了，也一起删除员工实表中的员工
		for (int i = 0; i < IdList.length; i++) {
			getJdbcTemplate().update(strDeleteSql, new Object[] {IdList[i],in_strDeptId});
			
			int recordCount = getJdbcTemplate().queryForInt(strSelectSql, new Object[]{IdList[i]});
			
			if (recordCount <= 0){
				getJdbcTemplate().update(strDeleteUserSql, new Object[] {IdList[i]});
			}
		}
	}
	
	//增加员工所属的部门
	public void addDeptUser(String in_strUserList,String in_strDeptId,String in_strCreate){
		String strSql = "insert into t_sys_dept_user " +
		  "(dept_user_id, user_id, dept_id, creator, create_time) " +
		"values " +
		  "(?, ?, ?, ?, ?)";
		
		String strUpdateSql = "update t_sys_user set dept_id = ? where user_id = ?";
		
		String strSelectSql ="select count(a.user_id) from t_sys_dept_user a where a.user_id = ? and dept_id = ?";
		
		String[] IdList = in_strUserList.split("\\|");
		
		for (int i = 0; i < IdList.length; i++) {
			int recordCount = getJdbcTemplate().queryForInt(strSelectSql, new Object[]{IdList[i],in_strDeptId});
			
			if (recordCount <= 0){
				Object[] params = new Object[]{
						CodeGenerator.getUUID(),
						IdList[i],
						in_strDeptId,
						in_strCreate,
						DateUtils.getCurrTime(DateUtils.DB_STORE_DATE)
				};
				
				getJdbcTemplate().update(strSql,params); 
				
				getJdbcTemplate().update(strUpdateSql,new Object[]{in_strDeptId,IdList[i]}); 
			}
		}
	}
	
	
	public void updateMangerDept(String deptList,String userId){
		deleteAllUserManagerDept(userId);
		String strSql ="insert into T_SYS_USER_MANAGER_DEPT(USER_MGT_ID, " +
						"USER_ID, " +
						"DEPT_ID, " +
						"CREATOR, " +
						"CREATE_TIME) values(?,?,?,?,?)";
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		String[] IdList = deptList.split("\\|");
		for (int i = 0; i < IdList.length; i++) {
			Object[] params = new Object[]{
				CodeGenerator.getUUID(),
				userId,
				IdList[i],
				userContext.getSysUser().getUserId(),
				DateUtils.getCurrTime(DateUtils.DB_STORE_DATE)
			};
			
			getJdbcTemplate().update(strSql,params);
		}
	}
	
	public void deleteAllUserManagerDept(String userId){
		String strSql ="delete T_SYS_USER_MANAGER_DEPT t where t.user_id=?";
		Object[] params = new Object[]{userId};
		getJdbcTemplate().update(strSql,params);
	}
	
	
	public List<String> queryManagerDeptByUserId(String userId){
		final List<String> list = new ArrayList<String>();
	    getJdbcTemplate().query("select t.dept_id from T_SYS_USER_MANAGER_DEPT t where t.user_id=?", new Object[]{userId},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				list.add(rs.getString("dept_id"));
			}
			
		});
		return list;
	}
	
	public void updateUserNo(SysUser sysUser){
		
		String strSql = "update t_sys_user " +
				   "set employee_No = ?,  " +
				   "mobile=? " +
				 "where user_id = ? " +
				   "and deleted = '1'";
		Object[] params = new Object[]{
				sysUser.getEmployeeNo(),
				sysUser.getMobile(),
				sysUser.getUserId()
		};
		
		getJdbcTemplate().update(strSql,params);
	}
	
	public String queryEmployeeNo(String userId){
		String strSql = "select employee_no from t_sys_user where user_id='"+userId+"'";
		List list = getJdbcTemplate().queryForList(strSql);
		return (String)((Map)list.get(0)).get("employee_no");
	}
	
	/**
	 * 根据外围用户ID获得SAP用户
	 * @param userId
	 * @return
	 */
	public String getSapUserByUserId(String userId)
	{
		String sql = "select SAPUSER sapUser from T_SYS_USER where USER_ID = '" 
				+ userId + "'";
		
		List<Map<String,String>> list = this.getJdbcTemplate().queryForList(sql);
		
		if(list != null && list.size() > 0)
		{
			return list.get(0).get("sapUser");
		}
		return "";
	}
	/**
	 * 根据userName取得该用户下所有权限下的子部门
	 * @param userName
	 * @return
	 */
	public List<String> getAllDeptCodeByUserName(String userName){
		final List<String> list = new ArrayList<String>();
	    getJdbcTemplate().query(" select distinct dept_code  from t_sys_dept where dept_id not in (select pdept_id from t_sys_dept)     and dept_id in (select distinct um.dept_id    from t_sys_dept_user du, t_sys_user_manager_dept um   where du.dept_user_id = um.user_id    and du.user_id in  (select u.user_id   From t_sys_user u  where u.user_name = ?))", new Object[]{userName},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				list.add(rs.getString("dept_code"));
			}
			
		});
		return list;
	}
}
