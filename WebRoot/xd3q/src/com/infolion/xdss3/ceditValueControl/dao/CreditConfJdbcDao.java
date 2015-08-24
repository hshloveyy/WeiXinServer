/*
 * @(#)CreditConfJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2010-5-18
 *  描　述：创建
 */

package com.infolion.xdss3.ceditValueControl.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.xdss3.ceditValueControl.domain.CustomerCreditConf;
import com.infolion.xdss3.ceditValueControl.domain.ProviderCreditConf;

@Repository
public class CreditConfJdbcDao extends BaseJdbcDao
{
	/**
	 * 得到立项所对应的受信类型
	 * @param strProjectId
	 * @return
	 */
	public String getProjectCreditType(String strProjectId){
		String strResult ="";
		int iCreditType = 0;
		
		String strSql = "select get_credit_type('"+strProjectId+"') from dual";
		
		iCreditType = this.getJdbcTemplate().queryForInt(strSql);

		strResult = Integer.toString(iCreditType);
		
		return strResult;
	}
	
	/**
	 * 返回存在的记录
	 * @param creditType
	 * @param projectno
	 * @return
	 */
	public List<CustomerCreditConf> cust_checkExists(String creditType, String projectno) {
	    String sql  = 
	            " SELECT c.*  FROM YCUSTCREDCONF C" +
	    		"  LEFT OUTER JOIN YCUSTCREDPROJ T" +
	    		"    ON C.CONFIGID = T.CONFIGID" +
	    		" WHERE 1=1" +
	    		"   AND C.CREDITTYPE = '" + creditType+"'" +
	    		"   AND T.projectno = '"+projectno+"' " ;
	    return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 返回存在的记录
	 * @param creditType
	 * @param projectno
	 * @return
	 */
	public List<ProviderCreditConf> prov_checkExists(String projectno) {
	    String 
	    sql =
	        " SELECT C.* " +
	        "  FROM YPROVCREDPROJ TT, YPROVCREDCONF C" +
	        " WHERE C.CONFIGID = TT.CONFIGID " +
	        "      AND TT.PROJECTNO = '" + projectno + "'";
	    return this.getJdbcTemplate().queryForList(sql);
	}
	
}
