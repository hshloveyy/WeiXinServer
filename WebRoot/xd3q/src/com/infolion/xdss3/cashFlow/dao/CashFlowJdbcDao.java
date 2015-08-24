/**
 * 
 */
package com.infolion.xdss3.cashFlow.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.infolion.platform.dpframework.core.dao.BaseJdbcDao;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.ageAnalysis.service.UnclearDetailedService;
import com.infolion.xdss3.cashFlow.domain.CashFlow;
import com.infolion.xdss3.collect.dao.CollectHibernateDao;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectbankitem.domain.CollectBankItem;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.masterData.dao.CustomerJdbcDao;
import com.infolion.xdss3.masterData.dao.SupplierJdbcDao;
import com.infolion.xdss3.payment.domain.HomePayBankItem;
import com.infolion.xdss3.payment.domain.ImportPayBankItem;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * @author 钟志华
 *
 */
@Repository
public class CashFlowJdbcDao extends BaseJdbcDao{
	@Autowired
	private VoucherJdbcDao voucherJdbcDao;

	public void setVoucherJdbcDao(VoucherJdbcDao voucherJdbcDao) {
		this.voucherJdbcDao = voucherJdbcDao;
	}
	@Autowired
	private CollectHibernateDao collectHibernateDao;
	
	
	public void setCollectHibernateDao(CollectHibernateDao collectHibernateDao) {
		this.collectHibernateDao = collectHibernateDao;
	}
	@Autowired
    private  CustomerJdbcDao customerJdbcDao;
    
    @Autowired
    private SupplierJdbcDao supplierJdbcDao;
    
    
	public void setCustomerJdbcDao(CustomerJdbcDao customerJdbcDao) {
		this.customerJdbcDao = customerJdbcDao;
	}
	public void setSupplierJdbcDao(SupplierJdbcDao supplierJdbcDao) {
		this.supplierJdbcDao = supplierJdbcDao;
	}
	@Autowired
	private UnclearDetailedService unclearDetailedService;
	
	public void setUnclearDetailedService(
			UnclearDetailedService unclearDetailedService) {
		this.unclearDetailedService = unclearDetailedService;
	}
	
	/**
	 * 根据凭证号取得收款单号
	 * @param voucherno
	 * @param companyCode
	 * @param fiYear
	 * @return
	 */
	public String getCollectnoByVoucherno(String voucherno, String companyCode, String fiYear){
		String bussinessid = voucherJdbcDao.getBusinessId(voucherno, companyCode, fiYear);
		return this.getCollectnoByCollectid(bussinessid);
	}
	/**
	 * 根据收款id取得收款信息
	 * @param collectid
	 *
	 * @return
	 */
	
	public Map<String,String> getProjectnoByCollectid(String collectid){
		Map<String,String> map=new HashMap<String,String>();
		String sql="SELECT a.project_no,c.collectno,c.customer FROM ycollectitem a,ycollect c WHERE a.collectid=c.collectid and  c.collectid='" + collectid +"' AND rownum = 1";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		if(null != rowList && rowList.size()>0){
			
			map.put("projectno", rowList.get(0).get("project_no").toString());
			map.put("collectno", rowList.get(0).get("collectno").toString());
			map.put("customer", rowList.get(0).get("customer").toString());
			return map;
		}
		return map;
	}
	public String getCollectnoByCollectid(String collectid){
		String sql="SELECT a.collectno FROM ycollect a WHERE a.collectid='" + collectid +"' AND rownum = 1";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		if(null != rowList && rowList.size()>0){
			return rowList.get(0).get("collectno").toString();
		}
		return "";
	}
	
	/**
	 * 退款
	 * @param refmentid
	 * @return
	 */
	public Map<String,String> getProjectnoByRefmentid(String refmentid){
		Map<String,String> map=new HashMap<String,String>();
		String sql="SELECT a.project_no,re.refundmentno,re.customer,re.supplier FROM yrefundmentitem a ,yrefundment re WHERE re.refundmentid=a.refundmentid and  re.refundmentid='" + refmentid +"' AND rownum = 1";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		if(null != rowList && rowList.size()>0){
			
			map.put("projectno", rowList.get(0).get("project_no").toString());
			map.put("refundmentno", rowList.get(0).get("refundmentno").toString());
			map.put("customer", rowList.get(0).get("customer").toString());
			map.put("supplier", rowList.get(0).get("supplier").toString());
			return map;
		}
		return map;
	}
	public String getRefmentnoByRefmentid(String refmentid){
		String sql="SELECT a.refundmentno FROM yrefundment a WHERE a.refundmentid='" + refmentid +"' AND rownum = 1";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		if(null != rowList && rowList.size()>0){
			return rowList.get(0).get("refundmentno").toString();
		}
		return "";
	}
	
	/**
	 *  根据凭证号取得付款单号
	 * @param voucherno
	 * @param companyCode
	 * @param fiYear
	 * @return
	 */
	public String getPaymentnoByVoucherno(String voucherno, String companyCode, String fiYear){
		String bussinessid = voucherJdbcDao.getBusinessId(voucherno, companyCode, fiYear);
		return getPaymentno(bussinessid);
	}
	/**
	 * 取得付款单号
	 * @param paymentid
	 * @return
	 */
	public String getPaymentno(String paymentid){
		String sql="SELECT a.paymentno FROM ypayment a WHERE a.paymentid='" + paymentid +"' ";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		if(null != rowList && rowList.size()>0){
			return rowList.get(0).get("paymentno").toString();
		}
		return "";
	}
	/**
	 * 取得项目号
	 * @param paymentid
	 * @return
	 */
	public Map<String,String> getProjectno(String paymentid){
		Map<String,String> map=new HashMap<String,String>();
		String sql="SELECT a.project_no,p.paymentno,p.supplier FROM ypaymentitem a,ypayment p WHERE p.paymentid=a.paymentid and p.paymentid='" + paymentid +"' AND rownum = 1";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		if(null != rowList && rowList.size()>0){
			
			map.put("projectno", rowList.get(0).get("project_no").toString());
			map.put("paymentno", rowList.get(0).get("paymentno").toString());
			map.put("supplier", rowList.get(0).get("supplier").toString());
			return map;
		}
		return map;
	}
	/**
	 * 取得纯代理客户号
	 * @param paymentid
	 * @return
	 */
	public String getRepresentpaycust(String paymentid){
		String sql="SELECT  a.representpaycust FROM ypayment a WHERE a.isrepresentpay = '1' AND a.paymentid='" + paymentid +"' ";
//		String representpaycust = (String)this.getJdbcTemplate().queryForObject(sql, String.class);
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		if(null != rowList && rowList.size()>0){
			return rowList.get(0).get("representpaycust").toString();
		}
		return "";
	}
	
	public String getBussinessid(String voucherno, String companyCode, String fiYear){
		return voucherJdbcDao.getBusinessId(voucherno, companyCode, fiYear);
	}
	
	public List<CashFlow> getCollectBankItem(String bukrs,String fromdate, String todate,String gsber, String rstgr,String hkont,String projectno,String customers,String type,String collectno){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT d.*,cb.*,ci.*,c.*  FROM YCOLLECT C, T_SYS_DEPT D,ycollectbankitem cb,ycollectitem ci  WHERE C.BUSINESSSTATE NOT IN ('-1', '3', '4')   AND C.CREATETIME BETWEEN '"+fromdate+"' AND '"+todate+"'   AND C.DEPT_ID = D.DEPT_ID AND c.collectid=cb.collectid  AND c.collectid=ci.collectid AND d.company_code='"+bukrs+"' ");
		if (!StringUtils.isNullBlank(gsber)){
			sb.append(" AND d.dept_code IN ("+gsber+") ");
		}
		if (!StringUtils.isNullBlank(rstgr)){
			sb.append(" AND cb.cashflowitem IN ("+rstgr+") ");		
		}
		if (!StringUtils.isNullBlank(hkont)){
			sb.append(" AND cb.colbanksubject = '"+hkont+"' ");
		}
		if (!StringUtils.isNullBlank(projectno)){
			sb.append(" AND ci.project_no IN ("+projectno+") ");	
		}
		if (!StringUtils.isNullBlank(collectno)){
			sb.append(" AND c.collectno IN ("+collectno+") ");
		}
		if (!StringUtils.isNullBlank(customers)){
			sb.append(" AND c.customer IN ("+customers+") ");
		}
		if (!StringUtils.isNullBlank(type)){
			sb.append(" AND c.collecttype IN ("+type+") ");
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		List<CashFlow> cashFlowList = new ArrayList<CashFlow>();
		for (Map map : rowList){
			CashFlow cashFlow = new CashFlow();
			cashFlow.setKunnr(map.get("customer").toString());			
			cashFlow.setAugdt(map.get("ACCOUNTDATE").toString());
			cashFlow.setBelnr("");
			cashFlow.setBname(map.get("project_no").toString());
			cashFlow.setBudat(map.get("VOUCHERDATE").toString());
			cashFlow.setBukrs(map.get("COMPANY_CODE").toString());
			
			cashFlow.setCurrency(map.get("CURRENCY").toString());
			cashFlow.setDmbtr(new BigDecimal("0"));
			cashFlow.setGsber(map.get("DEPT_CODE").toString());
			cashFlow.setHkont(map.get("COLBANKSUBJECT").toString());			
			cashFlow.setRstgr(map.get("CASHFLOWITEM").toString());
			cashFlow.setSgtxt(map.get("TEXT").toString());			
			cashFlow.setTxt40("");
			
			cashFlow.setWrbtr(new BigDecimal("0"));
			
			cashFlow.setCollectno(map.get("collectno").toString());
			cashFlow.setBussinessid(map.get("collectid").toString());
			cashFlow.setOnroadamount(map.get("COLLECTAMOUNT").toString());
			cashFlow.setOnroadamountBwb(map.get("COLLECTAMOUNT2").toString());
			if(!StringUtils.isNullBlank(cashFlow.getKunnr())){
				String customerName =customerJdbcDao.getCustomerName(cashFlow.getKunnr());
				cashFlow.setCustomerName(customerName);
			}
//			如果有立项没业务类型就去取立项上的业务类型
			if(!StringUtils.isNullBlank(cashFlow.getBname()) && StringUtils.isNullBlank(cashFlow.getBussinessType())){
				String btype2=this.getBusinessType(cashFlow.getBname());
				String btype = unclearDetailedService.convertSapByContractType(btype2);
				cashFlow.setBussinessType(btype);
			}
			cashFlowList.add(cashFlow);
		}
		return cashFlowList;
	}
	
	public List<CashFlow> getPaymentBankItem(String bukrs,String fromdate, String todate,String gsber, String rstgr,String hkont,String projectno,String supplier,String type,String paymentno){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT d.*,pb.*,pi.*,p.* FROM ypayment p,T_SYS_DEPT D,ypaybankitem pb ,ypaymentitem pi WHERE p.businessstate NOT IN ('4','5','7','8','-1') AND p.createtime BETWEEN '"+fromdate+"' AND '"+todate+"' AND p.dept_id=d.dept_id AND p.paymentid=pb.paymentid AND p.paymentid=pi.paymentid  AND d.company_code='"+bukrs+"'	 ");
		if (!StringUtils.isNullBlank(gsber)){
			sb.append(" AND d.dept_code IN ("+gsber+") ");
		}
		if (!StringUtils.isNullBlank(rstgr)){
			sb.append(" AND pb.cashflowitem IN ("+rstgr+") ");		
		}
		if (!StringUtils.isNullBlank(hkont)){
			sb.append(" AND pb.paybanksubject ='"+hkont+"' ");
		}
		if (!StringUtils.isNullBlank(projectno)){
			sb.append(" AND pi.project_no IN ("+projectno+") ");		
		}
		if (!StringUtils.isNullBlank(paymentno)){
			sb.append(" AND p.paymentno IN ("+paymentno+") ");
		}
		if (!StringUtils.isNullBlank(supplier)){
			sb.append(" AND p.supplier IN ("+supplier+") ");
		}
		if (!StringUtils.isNullBlank(type)){
			sb.append(" AND p.paymenttype IN ("+type+") ");
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		List<CashFlow> cashFlowList = new ArrayList<CashFlow>();
		for (Map map : rowList){
			CashFlow cashFlow = new CashFlow();
			cashFlow.setLifnr(map.get("supplier").toString());			
			cashFlow.setAugdt(map.get("ACCOUNTDATE").toString());
			cashFlow.setBelnr("");
			cashFlow.setBname(map.get("project_no").toString());
			cashFlow.setBudat(map.get("VOUCHERDATE").toString());
			cashFlow.setBukrs(map.get("COMPANY_CODE").toString());
			
			cashFlow.setCurrency(map.get("CURRENCY").toString());
			cashFlow.setDmbtr(new BigDecimal("0"));
			cashFlow.setGsber(map.get("DEPT_CODE").toString());
			cashFlow.setHkont(map.get("PAYBANKSUBJECT").toString());			
			cashFlow.setRstgr(map.get("CASHFLOWITEM").toString());
			cashFlow.setSgtxt(map.get("TEXT").toString());			
			cashFlow.setTxt40("");
			
			cashFlow.setWrbtr(new BigDecimal("0"));
			
			cashFlow.setPaymentno(map.get("paymentno").toString());
			cashFlow.setBussinessid(map.get("paymentid").toString());
			cashFlow.setOnroadamount(map.get("PAYAMOUNT").toString());
			cashFlow.setOnroadamountBwb(map.get("PAYAMOUNT2").toString());
//			如果有立项没业务类型就去取立项上的业务类型
			if(!StringUtils.isNullBlank(cashFlow.getBname()) && StringUtils.isNullBlank(cashFlow.getBussinessType())){
				String btype2=this.getBusinessType(cashFlow.getBname());
				String btype = unclearDetailedService.convertSapByContractType(btype2);
				cashFlow.setBussinessType(btype);
			}
			if(!StringUtils.isNullBlank(cashFlow.getLifnr())){
				String supplierName =supplierJdbcDao.getSupplierName(cashFlow.getLifnr());
				cashFlow.setSupplierName(supplierName);
				//取得纯代理客户号
				String representpaycust = this.getRepresentpaycust(cashFlow.getBussinessid());
				if(!StringUtils.isNullBlank(representpaycust)){
					String customerName =customerJdbcDao.getCustomerName(representpaycust);
					cashFlow.setCustomerName(customerName);
				}
			}
			cashFlowList.add(cashFlow);
		}
		return cashFlowList;
	}
	
	
	/**
	 * 根据id取得所有的流程节点
	 * @param bussinessid
	 * @return
	 */
	public List<String> getTaskName(String bussinessid){
		List<String> list = new ArrayList<String>();
		String sql="SELECT DISTINCT  T.TASKNAME FROM (select * from WF_TASKINS union select * From WF_TASKINS_O ) T WHERE T.BUSINESSID = '"+bussinessid+"'  OR T.PARENTBUSINESSID = '"+bussinessid+"'";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		for (Map map : rowList){
			list.add(map.get("TASKNAME").toString());
		}		
		return list;
	}
	
	public List<CashFlow> getRefmentBankItem(String bukrs,String fromdate, String todate,String gsber, String rstgr,String hkont,String projectno,String customers,String suppliers,String refundmentno){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT d.*,rb.*,ri.*,r.*  FROM yrefundment r, T_SYS_DEPT D,yrefundbankitem rb,yrefundmentitem ri  WHERE 1=1 AND r.BUSINESSSTATE NOT IN ('-1', '3', '4')   AND r.CREATETIME BETWEEN '"+fromdate+"' AND '"+todate+"'   AND r.DEPT_ID = D.DEPT_ID AND r.refundmentid=rb.refundmentid  AND r.refundmentid=ri.refundmentid AND d.company_code='"+bukrs+"' ");
		if (!StringUtils.isNullBlank(gsber)){
			sb.append(" AND d.dept_code IN ("+gsber+") ");
		}
		if (!StringUtils.isNullBlank(rstgr)){
			sb.append(" AND rb.cashflowitem IN ("+rstgr+") ");		
		}
		if (!StringUtils.isNullBlank(hkont)){
			sb.append(" AND rb.banksubject = '"+hkont+"' ");
		}
		if (!StringUtils.isNullBlank(projectno)){
			sb.append(" AND ri.project_no IN ("+projectno+") ");	
		}
		if (!StringUtils.isNullBlank(refundmentno)){
			sb.append(" AND r.refundmentno IN ("+refundmentno+") ");
		}
		if (!StringUtils.isNullBlank(customers)){
			sb.append(" AND r.customer IN ("+customers+") ");
		}
		if (!StringUtils.isNullBlank(suppliers)){
			sb.append(" AND r.supplier IN ("+suppliers+") ");
		}
		List<Map> rowList = this.getJdbcTemplate().queryForList(sb.toString());
		List<CashFlow> cashFlowList = new ArrayList<CashFlow>();
		for (Map map : rowList){
			CashFlow cashFlow = new CashFlow();
			
			cashFlow.setKunnr(map.get("customer").toString());	
						
			cashFlow.setLifnr(map.get("supplier").toString());
			
			cashFlow.setAugdt(map.get("ACCOUNTDATE").toString());
			cashFlow.setBelnr("");
			cashFlow.setBname(map.get("project_no").toString());
			cashFlow.setBudat(map.get("VOUCHERDATE").toString());
			cashFlow.setBukrs(map.get("COMPANY_CODE").toString());
			
			cashFlow.setCurrency(map.get("CURRENCY").toString());
			cashFlow.setDmbtr(new BigDecimal("0"));
			cashFlow.setGsber(map.get("DEPT_CODE").toString());
			cashFlow.setHkont(map.get("banksubject").toString());			
			cashFlow.setRstgr(map.get("CASHFLOWITEM").toString());
			cashFlow.setSgtxt(map.get("TEXT").toString());			
			cashFlow.setTxt40("");
			
			cashFlow.setWrbtr(new BigDecimal("0"));
			
			cashFlow.setCollectno(map.get("refundmentno").toString());
			cashFlow.setBussinessid(map.get("refundmentid").toString());
			cashFlow.setOnroadamount(map.get("refundamount").toString());
			cashFlow.setOnroadamountBwb(map.get("refundamount2").toString());
//			如果有立项没业务类型就去取立项上的业务类型
			if(!StringUtils.isNullBlank(cashFlow.getBname()) && StringUtils.isNullBlank(cashFlow.getBussinessType())){
				String btype2=this.getBusinessType(cashFlow.getBname());
				String btype = unclearDetailedService.convertSapByContractType(btype2);
				cashFlow.setBussinessType(btype);
			}
			if(!StringUtils.isNullBlank(cashFlow.getKunnr())){
				String customerName =customerJdbcDao.getCustomerName(cashFlow.getKunnr());
				cashFlow.setCustomerName(customerName);
			}
			if(!StringUtils.isNullBlank(cashFlow.getLifnr())){
				String supplierName =supplierJdbcDao.getSupplierName(cashFlow.getLifnr());
				cashFlow.setSupplierName(supplierName);
				//取得纯代理客户号
				String representpaycust = this.getRepresentpaycust(cashFlow.getBussinessid());
				if(!StringUtils.isNullBlank(representpaycust)){
					String customerName =customerJdbcDao.getCustomerName(representpaycust);
					cashFlow.setCustomerName(customerName);
				}
			}
			cashFlowList.add(cashFlow);
		}
		return cashFlowList;
	}
	/**
	 * 通过立项号取得业务类型
	 * @param projectno
	 * @return
	 */
	public String getBusinessType(String projectno){
		String sql="SELECT p.trade_type FROM t_project_info p WHERE p.project_no='" + projectno +"' AND rownum = 1";
		List<Map> rowList = this.getJdbcTemplate().queryForList(sql);
		if(null != rowList && rowList.size()>0){
			return rowList.get(0).get("trade_type").toString();
		}
		return "";
	}
	 // 批量插入,  
	 public void saveOrUpdateAll(final List<CashFlow> list) {  
	         this.getJdbcTemplate().batchUpdate(  
	                "INSERT INTO YCASHFLOW  (BUKRS,   BNAME,   GSBER,   KUNNR,   CUSTOMERNAME,   COLLECTNO,   PAYMENTNO,   REFMENTNO,"+
	                "LIFNR,   SUPPLIERNAME,   BELNR,   BUDAT,   AUGDT,   CURRENCY,   DMBTR,   WRBTR,   "+
	                "ONROADAMOUNT,   ONROADAMOUNTBWB,   SGTXT,   RSTGR,   TXT40,   HKONT,   SHKZG,   SUBJECTCODE,"+
	                "BUSSINESSTYPE,   BUSSINESSID,   GJAHR,   HKONT2,   BSART,   VBELTYPE,   BUZEI,   USERNAME)"+
	                "VALUES  (?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,"+
	                		" ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,"+
	                		" ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?,"+
	                		" ?,   ?,   ?,   ?,   ?,   ?,   ?,   ?)",  
	                new BatchPreparedStatementSetter() { 	                    
	                     public void setValues(PreparedStatement ps, int i)  
	                             throws SQLException {  
	                         ps.setString(1, list.get(i).getBukrs());  
	                         ps.setString(2, list.get(i).getBname());  
	                         ps.setString(3, list.get(i).getGsber());  
	                         ps.setString(4, list.get(i).getKunnr());  
	                         ps.setString(5, list.get(i).getCustomerName());  
	                         ps.setString(6, list.get(i).getCollectno());  
	                         ps.setString(7, list.get(i).getPaymentno());  
	                         ps.setString(8, list.get(i).getRefmentno());  
	                         ps.setString(9, list.get(i).getLifnr());  
	                         ps.setString(10, list.get(i).getSupplierName());  
	                         ps.setString(11, list.get(i).getBelnr());  
	                         ps.setString(12, list.get(i).getBudat());  
	                         ps.setString(13, list.get(i).getAugdt());  
	                         ps.setString(14, list.get(i).getCurrency());  
	                         ps.setString(15, list.get(i).getDmbtr().toString());  
	                         ps.setString(16, list.get(i).getWrbtr().toString());  
	                         ps.setString(17, list.get(i).getOnroadamount());  
	                         ps.setString(18, list.get(i).getOnroadamountBwb());  
	                         ps.setString(19, list.get(i).getSgtxt());  
	                         ps.setString(20, list.get(i).getRstgr());  
	                         ps.setString(21, list.get(i).getTxt40());  
	                         ps.setString(22, list.get(i).getHkont());  
	                         ps.setString(23, list.get(i).getShkzg());  
	                         ps.setString(24, list.get(i).getSubjectCode());  
	                         ps.setString(25, list.get(i).getBussinessType());  
	                         ps.setString(26, list.get(i).getBussinessid());  
	                         ps.setString(27, list.get(i).getGjahr());  
	                         ps.setString(28, list.get(i).getHkont2());  
	                         ps.setString(29, list.get(i).getBSART());  
	                         ps.setString(30, list.get(i).getVBELTYPE());  
	                         ps.setString(31, list.get(i).getBuzei());  
	                         ps.setString(32, list.get(i).getUsername());  
	                     }  
	                     public int getBatchSize() {  
	                         return list.size();  
	                    }  
	                 });  
	         
	  }
	 
	 public void delCashFlow(String username){
		 String sql="delete from yCashFlow where username='"+ username +"'";
			this.getJdbcTemplate().execute(sql);
	 }
	 
	 public List<CashFlow> getCashFlow(String username,String start,String pageSize){
		 String strSql="SELECT * FROM ( SELECT ROWNUM AS rnum,ca.* FROM YCASHFLOW ca WHERE USERNAME = '"+username+"') t WHERE t.rnum> '"+start+"' AND t.rnum <= '"+pageSize+"'";
		  List<CashFlow> cashFlows=new ArrayList<CashFlow>();
		 List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
			for (Map map : rowList)
			{
				CashFlow cashFlow = new CashFlow();
				ExBeanUtils.setBeanValueFromMap(cashFlow, map);
				
				cashFlows.add(cashFlow);			
			}
			return cashFlows;
	 }
	 
	public String getCount(String username){
		String count="0";
		String sql = " SELECT COUNT(1) as count FROM YCASHFLOW ca WHERE USERNAME = '"+username+"'";
		BigDecimal list = (BigDecimal)this.getJdbcTemplate().queryForObject(sql, BigDecimal.class);
		if(list != null )
		{
			return list.toString();
		}else{
			return count;
		}
	}
	/**
	 * 分页导出，取视图
	 * @param username
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<CashFlow> getCashFlowByExport(String username,String start,String pageSize){
		Map<String,String> saknrMap = this.getSaknr();	
		Map<String,String> kunnrMap = this.getKunnr();
		Map<String,String> lifnrMap = this.getLifnr();	
		Map<String,String> projectMap = this.getProject();	
		String strSql="";
		if(null != start && null != pageSize){
			 strSql="SELECT * FROM ( SELECT ROWNUM AS rnum,ca.* FROM vCASHFLOW ca WHERE USERNAME = '"+username+"') t WHERE t.rnum> '"+start+"' AND t.rnum <= '"+pageSize+"'";
		}else{
			 strSql=" SELECT ca.* FROM vCASHFLOW ca WHERE USERNAME = '"+username+"'";
		}
		List<CashFlow> cashFlows=new ArrayList<CashFlow>();
		 List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
			for (Map map : rowList)
			{
				CashFlow cashFlow = new CashFlow();
				ExBeanUtils.setBeanValueFromMap(cashFlow, map);
				
//				if(null !=map.get("CUSTOMERNAME5") && !StringUtils.isNullBlank(map.get("CUSTOMERNAME5").toString())){
//					cashFlow.setCustomerName(map.get("CUSTOMERNAME5").toString());
//				}else if( null !=map.get("SUPPLIERNAME5")&& !StringUtils.isNullBlank(map.get("SUPPLIERNAME5").toString())) {
//					
//					cashFlow.setSupplierName(map.get("SUPPLIERNAME5").toString());
//				}
				
				String shkzg=cashFlow.getShkzg();
				if(null != map.get("CUSTOMER5") && !StringUtils.isNullBlank(map.get("CUSTOMER5").toString())){
					cashFlow.setKunnr(map.get("CUSTOMER5").toString());
					cashFlow.setCustomerName(kunnrMap.get(cashFlow.getKunnr()));
					
//					if("H".equals(shkzg)){
//						cashFlow.setDmbtr(new BigDecimal("0").subtract(cashFlow.getDmbtr()));
//						cashFlow.setWrbtr(new BigDecimal("0").subtract(cashFlow.getWrbtr()));
//					}
				}
				if(null != map.get("SUPPLIER5") && !StringUtils.isNullBlank(map.get("SUPPLIER5").toString())){
					cashFlow.setLifnr(map.get("SUPPLIER5").toString());
					cashFlow.setSupplierName(lifnrMap.get(cashFlow.getLifnr()));
//					if("S".equals(shkzg)){
//						cashFlow.setDmbtr(new BigDecimal("0").subtract(cashFlow.getDmbtr()));
//						cashFlow.setWrbtr(new BigDecimal("0").subtract(cashFlow.getWrbtr()));
//					}
				}
				
				if("H".equals(shkzg)){
					cashFlow.setDmbtr(new BigDecimal("0").subtract(cashFlow.getDmbtr()));
					cashFlow.setWrbtr(new BigDecimal("0").subtract(cashFlow.getWrbtr()));
				}
//				日币乘以100
//				if("JPY".equals(cashFlow.getCurrency())){
//					cashFlow.setWrbtr(cashFlow.getWrbtr().multiply(new BigDecimal("100")));
//				}
				if(null != map.get("PROJECT_NO5") && !StringUtils.isNullBlank(map.get("PROJECT_NO5").toString())){
					cashFlow.setBname(map.get("PROJECT_NO5").toString());
					String type=projectMap.get(cashFlow.getBname());					
					String btype = unclearDetailedService.convertSapByContractType(type);
					cashFlow.setBussinessType(btype);
				}else{
				
					if(null != map.get("TRADE_TYPE") && !StringUtils.isNullBlank(map.get("TRADE_TYPE").toString())){
						String btype = unclearDetailedService.convertSapByContractType(map.get("TRADE_TYPE").toString());
						cashFlow.setBussinessType(btype);
					}else{
						if(!StringUtils.isNullBlank(cashFlow.getVBELTYPE())){					
							cashFlow.setBussinessType(cashFlow.getVBELTYPE());
						}else{
							cashFlow.setBussinessType(cashFlow.getBSART());
						}
						String btype = unclearDetailedService.convertSapByContractType(cashFlow.getBussinessType());
						cashFlow.setBussinessType(btype);
					}
				}
				if(null != map.get("COLLECTNO2") && !StringUtils.isNullBlank(map.get("COLLECTNO2").toString())){
					cashFlow.setCollectno(map.get("COLLECTNO2").toString());
				}
				
				if(null != map.get("PAYMENTNO3") && !StringUtils.isNullBlank(map.get("PAYMENTNO3").toString())){
					cashFlow.setPaymentno(map.get("PAYMENTNO3").toString());
				}
				
				if(null != map.get("REFUNDMENTNO4") && !StringUtils.isNullBlank(map.get("REFUNDMENTNO4").toString())){
					cashFlow.setRefmentno(map.get("REFUNDMENTNO4").toString());
				}
				
				String hkont2 = cashFlow.getHkont2();
				if(!StringUtils.isNullBlank(hkont2)){
//					hkont2以逗号分隔的字符串
					String[] hk = hkont2.split(",");
					String sname2="";
					for(String subjectco :hk){
						String sname = saknrMap.get(subjectco);	
						sname2 = sname2 + subjectco + "(" + sname + ")";
					}						
					cashFlow.setSubjectCode(sname2);
				}
//				去掉rownumer的数据
//				if(StringUtils.isNullBlank(cashFlow.getCustomerName())){
//					cashFlow.setKunnr("");
//				}
//				if(StringUtils.isNullBlank(cashFlow.getSupplierName())){
//					cashFlow.setLifnr("");
//				}
//				if(StringUtils.isNullBlank(cashFlow.getBussinessType())){
//					cashFlow.setBname("");
//				}
				cashFlows.add(cashFlow);			
			}
			return cashFlows;
	}
	
	public  Map<String,String> getSaknr(){
		String strSql="select ys.saknr,ys.txt20 from YSKAT ys GROUP BY ys.saknr,ys.txt20";		
		 List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		 Map<String,String> map2=new HashMap<String,String>();
		 for (Map map : rowList){
			 map2.put(map.get("SAKNR").toString(), map.get("TXT20").toString());
		 }
		 return map2;
	}
	
	public  Map<String,String> getKunnr(){
		String strSql="SELECT DISTINCT k.kunnr,k.name1 FROM YGETKUNNR k WHERE k.bukrs IN ('2100','2200','2400','2700','2800','2600')";
		 
		 List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		 Map<String,String> map2=new HashMap<String,String>();
		 for (Map map : rowList){
			 map2.put(map.get("kunnr").toString(), map.get("name1").toString());
		 }
		 return map2;
	}
	
	public  Map<String,String> getLifnr(){
		String strSql="SELECT DISTINCT l.lifnr,l.name1 FROM  YGETLIFNR l  WHERE l.bukrs IN ('2100','2200','2400','2700','2800','2600')";		
		 List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		 Map<String,String> map2=new HashMap<String,String>();
		 for (Map map : rowList){
			 map2.put(map.get("lifnr").toString(), map.get("name1").toString());
		 }
		 return map2;
	}
	
	public  Map<String,String> getProject(){
		String strSql="SELECT tp.project_no,tp.trade_type FROM  t_project_info tp";		
		 List<Map> rowList = this.getJdbcTemplate().queryForList(strSql);
		 Map<String,String> map2=new HashMap<String,String>();
		 for (Map map : rowList){
			 map2.put(map.get("project_no").toString(), map.get("trade_type").toString());
		 }
		 return map2;
	}
	
}
