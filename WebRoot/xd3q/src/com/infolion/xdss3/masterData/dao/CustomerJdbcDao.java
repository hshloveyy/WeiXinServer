/**
 * 
 */
package com.infolion.xdss3.masterData.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.util.StringUtils;

/**
 * @author zzh
 *
 */
@Repository
public class CustomerJdbcDao extends BaseJdbcDao{
	/**
	 *取得客户名称
	 * 
	 * @param user
	 * @return
	 */
	public String getCustomerName(String customer, String bukrs)
	{
		String sql = "select name1 from YGETKUNNR WHERE kunnr='" + customer + "'and Bukrs='" + bukrs + "'";
		return (String) this.getJdbcTemplate().queryForObject(sql, String.class);
	}


    /**
     * 取得付款下合同
     * 
     * @param paymentId
     * @return
     */
    public String queryContractNoByRefundmentId(String refundmentId)
    {
        StringBuffer sb = new StringBuffer();
        String strSql = "select distinct PROJECT_NO,CONTRACT_NO from yrefundmentitem where refundmentid='" + refundmentId + "'";
        List<Map> list = this.getJdbcTemplate().queryForList(strSql);

        if (null != list && list.size() > 0)
        {
            for (Map map : list)
            {
                String contract_no = (String) map.get("CONTRACT_NO");
                String project_no = (String) map.get("PROJECT_NO");
                if (StringUtils.isNullBlank(contract_no))
                {
                    if (!StringUtils.isNullBlank(project_no))
                    {
                        sb.append(project_no + "<br>");
                    }
                }
                else
                {
                    sb.append(contract_no + "<br>");
                }
            }
        }
        else
        {
            sb.append("");
        }

        return sb.toString();
    }

    
	/**
	 *取得客户名称
	 * 
	 * @param user
	 * @return
	 */
	public String getCustomerName(String customer)
	{
		String sql = "select name1 as customername from YGETKUNNR WHERE kunnr='" + customer + "'";
		List list = this.getJdbcTemplate().queryForList(sql);

		if (null != list && list.size() > 0)
			return (String) ((Map) list.get(0)).get("customername");
		else
			return "";
	}
}
