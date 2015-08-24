package com.infolion.XDSS.budget.maindata.BudgetOrgTemp.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;

@Repository
public class BudgetOrgTempJdbcDao extends BaseJdbcDao {
	private Log log = LogFactory.getLog(this.getClass());
	
	public boolean isHaveBudClass(String strBudClassId,String strBudOrgId){
		String strSql = "select count(*) from ybudorgtem " +
 "where budorgid = '" + strBudOrgId + "' " +
		    "and budclassid in " +
		        "(select budclassid " +
		           "from ybudclass " +
		          "where budupclassid in " +
		                "(select budupclassid " +
		                   "from ybudclass " +
		                  "where budclassid = '" + strBudClassId + "'))";
		
		int strCount =  this.getJdbcTemplate().queryForInt(strSql);
		
		if (strCount > 0)
			return true;
		else
			return false;
	}
}
