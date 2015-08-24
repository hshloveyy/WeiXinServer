package com.infolion.sapss.finReport.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.StringUtils;
import com.infolion.sapss.finReport.FinReportConstans;

@Repository
public class FinReportJdbcDao extends BaseDao {

	public List<SysDept> queryDeptByParentId(String in_strParentId){
		UserContext txt = UserContextHolder.getLocalUserContext().getUserContext();		
		final List<SysDept> deptList = new ArrayList();
		String strSql ;
		if(!"-1".equals(in_strParentId))
			strSql ="select a.dept_id,a.dept_name, " +
					"(select count(b.dept_id) from t_sys_dept b where b.pdept_id = a.dept_id and b.FINREPORTDES is not null and b.dept_id in ("+txt.getGrantedDepartmentsId()+")) childcount " +
					"from t_sys_dept a " +
					"where  a.pdept_id ='"+in_strParentId+"' and a.FINREPORTDES is not null and a.dept_id in ("+txt.getGrantedDepartmentsId()+")  order by a.FINREPORTDES";
		else {
			strSql = "select a.dept_id,a.dept_name,"+
					"(select count(b.dept_id) from t_sys_dept b where b.pdept_id = a.dept_id and b.FINREPORTDES is not null and b.dept_id in ("+txt.getGrantedDepartmentsId()+")) childcount  "+
					"From t_sys_dept a where a.dept_id in ("+txt.getGrantedDepartmentsId()+") and  substr(a.finreportdes,7,8) = (select min(substr(c.finreportdes,7,8)) from t_sys_dept c where c.dept_id in ("+txt.getGrantedDepartmentsId()+"))";
		}

		getJdbcTemplate().query(strSql, new Object[]{},new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				SysDept tSysDept = new SysDept();
				tSysDept.setDeptid(rs.getString("dept_id"));
				tSysDept.setDeptname(rs.getString("dept_name"));
				tSysDept.setChildcount(rs.getInt("childcount"));
				deptList.add(tSysDept);
			}});
		return deptList;
	}
	
	public Map<String,BigDecimal> queryGenneralBudget(String year,String month,String comCodes,String busCodes){
		StringBuffer sqlBf = new StringBuffer("select ");
		for(String key : FinReportConstans.FIELD_GENNERAL.keySet()){
			sqlBf.append(" nvl(sum("+key+"),0) "+key+",");
		}
		sqlBf.deleteCharAt(sqlBf.length()-1);
		sqlBf.append(" from T_FRPBGT_GENNERAL  where year='"+year+"' and month='"+month+"'");
		if(!StringUtils.isNullBlank(comCodes)){
			sqlBf.append(" and comCode in ('"+comCodes.replaceAll(",", "','")+"')");
		}
		if(!StringUtils.isNullBlank(busCodes)){
			sqlBf.append(" and busCode in ('"+busCodes.replaceAll(",", "','")+"')");
		}
		List<Map<String,BigDecimal>> list = getJdbcTemplate().queryForList(sqlBf.toString());
		return (Map<String,BigDecimal>)list.get(0);
	}
	
	public Map<String,String> dealDeptBs(String deptId){
		Map<String,String> map = new HashMap<String, String>();
		if(StringUtils.isNullBlank(deptId)){
			map.put("isSuccess", "false");
			map.put("returnMessage", "必须选择公司或部门！");
			return map;
		}
		String sql = "select dept_code,company_code,substr(finreportdes,5,2) mack From t_sys_dept where dept_id in ('"+deptId.replaceAll(",","','")+"') and substr(finreportdes,5,2) in ('01','02') order by finReportDes ";
		List<Map<String,String>> list = getJdbcTemplate().queryForList(sql);
		//校验，不能公司和业务范围同时选择
		Set<String> mackTempSet = new HashSet<String>();
		Set<String> busCodeSet = new HashSet<String>();
		Set<String> comCodeSet = new HashSet<String>();
		for(int i = 0 ; i<list.size() ; i++){
			Map<String,String> rmap = list.get(i);
			String dept_code = rmap.get("dept_code");
			String company_code = rmap.get("company_code");
			String mack = rmap.get("mack");
			mackTempSet.add(mack);
			//公司
			if("01".equals(mack)){
				comCodeSet.add(dept_code);
			}
			//业务范围
			else if("02".equals(mack)){
				comCodeSet.add(company_code);
				busCodeSet.add(dept_code);
			}
		}
		if(mackTempSet.size()>1){
			map.put("isSuccess", "false");
			map.put("returnMessage", "查询部门项下信息不允许选择公司，请取消选择公司层信息！");
			return map;
		}
		map.put("isSuccess", "true");
		map.put("returnMessage", "");
		map.put("busCode", busCodeSet.toString().replaceAll(" ", "").replace("[", "").replace("]", ""));
		map.put("comCode", comCodeSet.toString().replaceAll(" ", "").replace("[", "").replace("]", ""));
		return map;
	}

}
