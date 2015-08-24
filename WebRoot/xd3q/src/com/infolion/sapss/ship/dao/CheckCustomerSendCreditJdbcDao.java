package com.infolion.sapss.ship.dao;


import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;

/**
 * 
 * @author user
 * 
 */

@Repository
public class CheckCustomerSendCreditJdbcDao extends BaseDao {
	private static final String SEND_SQL = "check_customer_send_credit";
	private static final String PROVIDER_SQL = "check_provider_credit";
	private static final String CUSTOMER_SQL = "check_customer_credit";
	
	/**
	 * 放货额度计算方法
	 * 
	 * @param customer_id
	 *            客户ID
	 * @param project_id
	 *            立项ID
	 * @param value
	 *            发货值（含税人民币金额）
	 * @return 返回此次发货后的额度余额。
	 */
	public String checkCustomerSendCredit(String customer_id,
			String project_id, float value) {
		CheckCustomerSendCreditStoreProcedure sproc = new CheckCustomerSendCreditStoreProcedure(
				this.getDataSource(),customer_id,project_id,value,SEND_SQL);
		Map results = sproc.execute();
		Object rt=results.get(CheckCustomerSendCreditStoreProcedure.OUT_RETURN);		
		return 	rt.toString();
	}
	/**
	 * 检查客户授信
	 * @param customer_id
	 * @param project_id
	 * @param value
	 * @return
	 */
	public String checkCustomerCredit(String customer_id,
			String project_id, float value) {
		CheckCustomerSendCreditStoreProcedure sproc = new CheckCustomerSendCreditStoreProcedure(
				this.getDataSource(),customer_id,project_id,value,CUSTOMER_SQL);
		Map results = sproc.execute();
		Object rt=results.get(CheckCustomerSendCreditStoreProcedure.OUT_RETURN);		
		return 	rt.toString();
	}
	/**
	 * 检查供应商授信
	 * @param provider_id
	 * @param project_id
	 * @param value
	 * @return
	 */
	public String checkProviderCredit(String provider_id,
			String project_id, float value) {
		CheckCustomerSendCreditStoreProcedure sproc = new CheckCustomerSendCreditStoreProcedure(
				this.getDataSource(),provider_id,project_id,value,PROVIDER_SQL);
		Map results = sproc.execute();
		Object rt=results.get(CheckCustomerSendCreditStoreProcedure.OUT_RETURN);		
		return 	rt.toString();
	}
	/**
	 * 取得供应商授信类型
	 * @param provider_id
	 * @param project_id
	 * @param currentdate
	 * @return 返回-1代表没有授信，2为自营，1为非自营
	 */
	public String getCreditTypeByProvider(String provider_id,String project_id,String currentdate){		
		String sql ="SELECT DISTINCT c.credittype  FROM YPROVCREDCONF C  LEFT OUTER JOIN YPROVCREDPROJ T ON C.CONFIGID = T.CONFIGID " +
        " WHERE C.PROVIDERID = "+provider_id+"   AND T.PROJECTID = "+project_id+"   AND C.STARTINGTIME <= "+currentdate+"    AND C.ENDTIME >= "+currentdate+" ";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		for (Map map : rowList){
			return map.get("credittype").toString();
		}
		return "-1";
	}
}
