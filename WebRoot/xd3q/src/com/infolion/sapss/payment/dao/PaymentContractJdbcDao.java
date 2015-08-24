/*
 * @(#)PaymentContractJdbcDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：黄登辉
 *  时　间：Apr 27, 2009
 *  描　述：创建
 */

package com.infolion.sapss.payment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.payment.domain.TPaymentInnerInfo;

/**
 * @author 黄登辉
 *
 */
@Repository
public class PaymentContractJdbcDao extends BaseDao{
	/**
	 * 删除内贸付款合同关联信息
	 * @param paymentId
	 */
	public int delete(String paymentContractId) {
		String sql = "update t_payment_contract set is_available='0' where payment_contract_id=?";
		return this.getJdbcTemplate().update(sql, new Object[]{paymentContractId});
	}

	/**
	 * 根据内贸付款ID删除内贸付款合同关联信息
	 * @param paymentId
	 */
	public void deleteByPaymentId(String paymentId) {
		deleteByPaymentId(paymentId,true);
	}

	/**
	 * 根据内贸付款ID删除内贸付款合同关联信息
	 * @param paymentId
	 * @param isReally
	 */
	public void deleteByPaymentId(String paymentId,boolean isReally) {
		String sql = "";
		if(isReally==true){
			sql = "delete from t_payment_contract where payment_id='"+paymentId+"'";
			getJdbcTemplate().execute(sql);
		}else{
			sql = "update t_payment_contract set is_available = 0 where payment_id='"+paymentId+"'";
			getJdbcTemplate().update(sql);
		}
	}
	/**
	 * 更新业务记录状态
	 * @param originalBizId
	 * @param string
	 */
	public void updateStatus(String paymentId, String statusCode) {
		String sql="update t_payment_inner_info t set t.EXAMINE_STATE=? where t.PAYMENT_ID=?";
		this.getJdbcTemplate().update(sql, new Object[]{statusCode,paymentId});
	}
	
	public String queryContractNoByPayMentId(String paymentId){
		String sql = "select p.ekko_Unsez,s.vbkd_Ihrez from t_payment_contract t " +
				"left outer join t_contract_purchase_info p on p.contract_purchase_id=t.contract_purchase_id " +
				"left outer join t_contract_sales_info s on s.contract_sales_id=t.contract_purchase_id where t.payment_id='"+paymentId+"'";
		List list = this.getJdbcTemplate().queryForList(sql);
		String result = "";
		for(Iterator<Map> it = list.iterator();it.hasNext();){
			Map  map = it.next();
			Object ekko_Unsez = map.get("ekko_Unsez");
			Object vbkd_Ihrez = map.get("vbkd_Ihrez");
			ekko_Unsez=((ekko_Unsez==null)?"":ekko_Unsez);
			vbkd_Ihrez=((vbkd_Ihrez==null)?"":vbkd_Ihrez);
			result+= ekko_Unsez+""+vbkd_Ihrez+"<br>";
		}
		if(result.lastIndexOf("<br>")>0)
			return result.substring(0, result.lastIndexOf("<br>"));
		return null;
	}
	
	public String checkContractsPayValue(String[] contractIds,TPaymentInnerInfo info){
		String result = "";
		if(contractIds==null) return result;
		for(int i=0;i<contractIds.length;i++){
			String[] strs = contractIds[i].split("/");
			String contractId = strs[0];
			String payValue = strs[1];
			result+=checkContractPayValue(contractId, Double.parseDouble(payValue), info.getCurrency());
		}
		return result;
	}
	public String checkContractPayValue(String contractId,Double payValue,String currency){
		String sql = " select sum(d.payValue) from (select t.pay_value*c.rate as payValue from t_payment_contract t " +
				"left outer join t_payment_inner_info i on t.payment_id=i.payment_id " +
				"left outer join bm_currency c on c.id=i.currency where  i.pay_type<>2 " +
				"AND i.examine_state in('2','3') and t.contract_purchase_id=? ) d ";
		Double contractPayTotal = (Double)getJdbcTemplate().queryForObject(sql, new Object[]{contractId}, Double.class);
		if(contractPayTotal==null) contractPayTotal = 0d;
		Map map = getPurcharseContractInfo(contractId);
		Double contractSum = (Double)map.get("sum");
		if(contractSum-contractPayTotal-payValue*getCurrencyRate(currency)<0)
			return "合同编码："+map.get("contractNo")+"项下付款累计金额超过合同货款金额";
		return "";
	}
	
	public Map getPurcharseContractInfo(String contractId){
		final Map map =new HashMap<String, Object>();
		String contractSQL = "select p.contract_no, p.total_amount*c.rate as totalAmount from t_contract_purchase_info p " +
				"left outer join bm_currency c on p.ekko_waers=c.id where p.contract_purchase_id=?";
		getJdbcTemplate().query(contractSQL,new Object[]{contractId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				map.put("sum", rs.getDouble("totalAmount"));
				map.put("contractNo", rs.getString("contract_no"));
				
			}
		});
		return map;
	}
	
	public String checkProjectPayValue(TPaymentInnerInfo info){
		Double projectSum = getProjectSum(info.getProjectId());
		Double projectPaySum = getProjectPaySum(info.getProjectId());
		Double currenPayValue = info.getPayValue()*getCurrencyRate(info.getCurrency());
		if(projectSum-projectPaySum-currenPayValue<0)
			return "付款金额累计超过立项总金额";
		return "";
	}
	
	public Double getProjectPaySum(String projectID){
	    Double projectPayTotal = 0d ;
	    final List<Double> list = new ArrayList<Double>();
		String contractSQL = "select i.pay_value,c.rate from t_payment_inner_info i left outer join bm_currency c on c.id=i.currency where  i.pay_type<>2 AND i.examine_state in('2','3') and i.project_id=?";
		getJdbcTemplate().query(contractSQL,new Object[]{projectID}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				list.add(rs.getDouble("pay_value")*rs.getDouble("rate"));
			}
		});
		for(Double d : list){
			projectPayTotal+=d;
		}
		return projectPayTotal;
	}
	
	public Double getProjectSum(String projectID){
		final Map<String,Object> map = new HashMap<String,Object>();
		String projectSQL = "select p.sum,p.currency from t_project_info p "+
                            "where p.project_id=?";
		this.getJdbcTemplate().query(projectSQL, new Object[]{projectID},
				new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				map.put("sum", rs.getDouble("sum"));
				String currency = rs.getString("currency");
				if(!"".equals(currency)&&currency!=null) map.put("currency", currency);
				else{
					 map.put("currency", "CNY");
				}
			}
        });
		Double projectTotal = (Double)map.get("sum")*getCurrencyRate((String)map.get("currency"))*10000;
		return projectTotal;
	}
	
	/**
	 * 获取汇率
	 * @param currency
	 * @return
	 */
	public Double getCurrencyRate(String currency){
		final List<Double> list = new ArrayList<Double>();
		getJdbcTemplate().query("SELECT RATE FROM BM_CURRENCY WHERE ID=?", new Object[]{currency},
				new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				list.add(rs.getDouble("rate"));
            }
        });
		return list.get(0);
	}
	
	public String  getTradeTypeByProjectId(String projectId){
		String sql = "select p.trade_type from t_project_info p where p.project_id='"+projectId+"'";
		int tradeType =  getJdbcTemplate().queryForInt(sql);
		switch(tradeType){
			case 1: return "自营进口*货款";
			case 2: return "自营出口*货款";
			case 3: return "自营进口货款";
			case 4: return "自营出口货款";
			case 5: return "代理出口货款";
			case 6: return "代理进口货款";
			case 7: return "内贸货款";
			case 8: return "进料加工货款";
			case 9: return "自营进口敞口货款";
			case 10: return "内贸敞口货款";
		}
	    return "内贸";
	}
	
	public String queryParticularId(String paymentId){
		final List<String> list = new ArrayList<String>();
		getJdbcTemplate().query("select particular_id from t_particular_workflow where original_biz_id=?", new Object[]{paymentId},
				new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				list.add(rs.getString("particular_id"));
            }
        });
		if(list.isEmpty()) return "";
		return list.get(0);
	}
	
	public Map<String,String>queryBankAndAccount(String recBank){
		final Map<String,String> map = new HashMap<String, String>();
		map.put("openAccountBank", "");
		map.put("openAccountNo", "");
		getJdbcTemplate().query("select * from (select open_Account_Bank,open_Account_No from t_payment_inner_info where rec_Bank=? order by  creator_time desc) where rownum<? ", new Object[]{recBank,"2"},
				new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				map.put("openAccountBank",rs.getString("open_Account_Bank"));
				map.put("openAccountNo",rs.getString("open_Account_No"));
        }
        });
		return map;
	}
	
	public void dealOutToExcel(String sql ,final ExcelObject excel){
		//final IntObject i= new IntObject();
		
		getJdbcTemplate().query(sql,new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[7];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("dept_name");
				values[2] = rs.getString("creator_name");
				values[3] = rs.getString("pay_method");
				values[4] = rs.getString("currency");
				values[5] = rs.getString("pay_value");
				values[6] = rs.getString("approved_time");
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
	public void dealOutToExcel1(String sql ,String strAuthSql ,final ExcelObject excel){
		
		getJdbcTemplate().query(sql+strAuthSql.replace("t.", " a."),new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] values = new String[22];
				values[0] = String.valueOf(rs.getRow());
				values[1] = rs.getString("paymentno");
				values[2] = rs.getString("dept_id_text");
				values[3] = rs.getString("supplier_text");
				values[4] = rs.getString("collectbankid");
				values[5] = rs.getString("currency");
				values[6] = rs.getString("paydate");
				values[7] = rs.getString("documentarypaydt");
				values[8] = rs.getString("factamount");
				values[9] = rs.getString("ddtext");
				values[10] = rs.getString("arrivegoodsdate");
				values[11] = rs.getString("old_contract_no");
				values[12] = rs.getString("ymat_group");
				values[13] = rs.getString("title");
				values[14] = rs.getString("paymentTypeText");
				values[15] = rs.getString("businessstateText");
				values[16] = rs.getString("currency2");
				values[17] = rs.getString("documentarylimit");
				values[18] = rs.getString("documentarydate");
				values[19] = rs.getString("documentaryrate");
				values[20] = rs.getString("doctaryinterest");
				values[21] = rs.getString("redocaryrate");
				
				try{
					excel.addRow(values);
				}
				catch (Exception e) {
                    e.printStackTrace();
				}
			}});
	}
	
	
	/**
     * 取得押汇与还押汇金额（期间内）
     * @param billpurid
     * @param begDate
     * @param endDate 为空时，则是查询（期间内）前段时间金额
     * @param flag
     * @return
     */
    public Map<String,String> getAmount(String paymentid, String begDate, String endDate, String flag) {
        final Map<String,String> map = new HashMap<String, String>();
        map.put("AMOUNT", "0");
        map.put("AMOUNT2", "0");
        String sql =   " SELECT NVL(SUM(AMOUNT),0) as AMOUNT, NVL(SUM(AMOUNT2),0) as AMOUNT2 " + 
                                "   FROM YVOUCHER V, YVOUCHERITEM VI, YPAYMENT P" + 
                                "  WHERE V.VOUCHERID = VI.VOUCHERID" + 
                                "    AND P.PAYMENTID = V.BUSINESSID" + 
                                "    AND trim(v.voucherno) IS NOT NULL " + 
                                "    AND P.PAYMENTID = '"+paymentid+"'" + 
                                "    AND TRIM(to_char(TRIM(VI.ROWNUMBER), '000')) = '001'" + 
                                "    AND v.voucherclass <> '5'" + 
                                "    AND v.voucherclass <> '7'" + 
                                "    AND VI.SUBJECTDESCRIBE NOT LIKE '%中转科目%'";
        if ("押汇".equals(flag)) {
            sql +=  "    AND (VI.CHECKCODE = '50' or VI.CHECKCODE = '01' )";
            sql +=  "    AND V.voucherclass = '2'";
            sql +=  "    AND VI.cashflowitem <> '351'";
        } else { //还押汇
            sql +=  "    AND v.voucherclass <> '1' AND ( (VI.CHECKCODE = '40') or (v.voucherclass='2' AND vi.cashflowitem='351' AND vi.checkcode='50') ) ";
        }

        if (StringUtils.isNotBlank(begDate) && StringUtils.isNotBlank(endDate)) {
            sql += "   AND V.CHECKDATE BETWEEN '"+begDate+"' AND '"+endDate+"' " ;
        } else if (StringUtils.isNullBlank(endDate)) {
            sql += "   AND V.CHECKDATE < '"+begDate+"' " ;
        }
        
        getJdbcTemplate().query(sql, new Object[]{},
                new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                map.put("AMOUNT",rs.getString("AMOUNT"));
                map.put("AMOUNT2",rs.getString("AMOUNT2"));
        }
        });
        
        return map; 
        
    }
    
    /**
     * 取得押汇与还押汇金额（期间内）
     * @param billpurid
     * @param begDate
     * @param endDate 为空时，则是查询（期间内）前段时间金额
     * @param flag
     * @return
     */
    public Map<String,String> getAmountIsOverRePay(String paymentid, String begDate, String endDate, String flag) {
        final Map<String,String> map = new HashMap<String, String>();
        map.put("AMOUNT", "0");
        map.put("AMOUNT2", "0");
        String sql =   " SELECT NVL(SUM(P.APPLYAMOUNT), 0) AS AMOUNT," +
        		"       NVL(SUM(P.APPLYAMOUNT * P.CLOSEEXCHANGERAT), 0) AS AMOUNT2" +
        		" FROM YPAYMENT P" +
        		" WHERE 1=1 " +
        		"    AND P.PAYMENTID = '"+paymentid+"'";
        if ("押汇".equals(flag)) {
            if (StringUtils.isNullBlank(endDate)) { // 期初押汇
                sql += "   AND P.DOCUMENTARYDATE < '"+begDate+"' " ;
            } else { // 期间押汇
                sql += "   AND P.DOCUMENTARYDATE BETWEEN '"+begDate+"' AND '"+endDate+"' " ;
            }
        } else { //还押汇
            sql +=
            "   AND EXISTS" +
            " (SELECT 'x'" +
            "          FROM YVOUCHER V, YVOUCHERITEM VI" +
            "         WHERE V.VOUCHERID = VI.VOUCHERID" +
            "           AND V.BUSINESSID = P.PAYMENTID";
            if (StringUtils.isNullBlank(endDate)) { //期初还押汇
                sql += "           AND V.CHECKDATE < '"+begDate+"'  " ;
            } else { //期间还押汇
                sql += "           AND V.CHECKDATE BETWEEN '"+begDate+"' AND '"+endDate+"' ";
            }
            sql += ")";
        }
        
        getJdbcTemplate().query(sql, new Object[]{},
                new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                map.put("AMOUNT",rs.getString("AMOUNT"));
                map.put("AMOUNT2",rs.getString("AMOUNT2"));
            }
        });
        
        return map; 
        
    }

}
