/*
 * @(#)ImportPaymentService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月27日 11点58分49秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.sapss.receipts.domain.TReceiptInfo;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.billPayReBankItem.domain.BillPayReBankItem;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.payment.dao.PaymentCurrencyJdbcDao;
import com.infolion.xdss3.payment.dao.PaymentItemJdbcDao;
import com.infolion.xdss3.payment.dao.PaymentJdbcDao;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.HomePaymentCbill;
import com.infolion.xdss3.payment.domain.HomePaymentItem;
import com.infolion.xdss3.payment.domain.ImportDocuBankItem;
import com.infolion.xdss3.payment.domain.ImportPayBankItem;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentCbill;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.domain.ImportRelatPayment;
import com.infolion.xdss3.payment.domain.PlickListInfo;
import com.infolion.xdss3.paymentGen.service.ImportPaymentServiceGen;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;

/**
 * <pre>
 * 进口付款(ImportPayment)服务类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class ImportPaymentService extends ImportPaymentServiceGen {
	private Log log = LogFactory.getFactory().getLog(this.getClass());

	@Autowired
	private ReassignJdbcDao reassignJdbcDao;

	@Autowired
	private PaymentCurrencyJdbcDao paymentCurrencyJdbcDao;
	
	@Autowired
	private PaymentJdbcDao paymentJdbcDao;
	
	public void setPaymentJdbcDao(PaymentJdbcDao paymentJdbcDao) {
		this.paymentJdbcDao = paymentJdbcDao;
	}

	public PaymentCurrencyJdbcDao getPaymentCurrencyJdbcDao() {
		return paymentCurrencyJdbcDao;
	}

	public void setPaymentCurrencyJdbcDao(
			PaymentCurrencyJdbcDao paymentCurrencyJdbcDao) {
		this.paymentCurrencyJdbcDao = paymentCurrencyJdbcDao;
	}

	/**
	 * @param reassignJdbcDao
	 *            the reassignJdbcDao to set
	 */
	public void setReassignJdbcDao(ReassignJdbcDao reassignJdbcDao) {
		this.reassignJdbcDao = reassignJdbcDao;
	}

	@Autowired
	private PaymentItemJdbcDao paymentItemJdbcDao;
	
	public void setPaymentItemJdbcDao(PaymentItemJdbcDao paymentItemJdbcDao) {
		this.paymentItemJdbcDao = paymentItemJdbcDao;
	}

	@Autowired
	private VendorTitleJdbcDao vendorTitleJdbcDao;
	
	public void setVendorTitleJdbcDao(VendorTitleJdbcDao vendorTitleJdbcDao) {
		this.vendorTitleJdbcDao = vendorTitleJdbcDao;
	}
	
	// RFC函数调用正确返回（符合正常业务逻辑）
	public final static String RFC_FUNCTION_CALL_RESULT_PASS = "rfc_call_pass";
	// RFC函数调用错误返回（不符合需要的业务逻辑）
	public final static String RFC_FUNCTION_CALL_RESULT_NOPASS = "rfc_call_nopass";

	/**
	 * 调用RFC，此函数为测试函数，实际函数调用后可以对返回值进行处理
	 * 
	 * @param businessRecordId
	 * @return
	 */
	public String callRfcFunction(String businessRecordId) {
		System.out.println("--------------此处调用RFC函数--------------业务记录ID：--"
				+ businessRecordId);
		// RFC返回值
		// 在RFC中，E表示错误，A表示警告
		String rfcReturnValue = "E";
		if ("E".equals(rfcReturnValue) || "A".equals(rfcReturnValue))
			return RFC_FUNCTION_CALL_RESULT_NOPASS;
		return RFC_FUNCTION_CALL_RESULT_PASS;
	}

	/**
	 * 调用存储过程UPDATE_VENDORTITLE
	 */
	public void updateVendorTitle(final String supplier){
		this.importPaymentHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				CallableStatement cs = session.connection().prepareCall("{call UPDATE_VENDORTITLE(?)}");    
				cs.setString(1, supplier);
				boolean isSuccess = cs.execute();
				return isSuccess; 
			}
		});
	}
	
	/**
	 * 调用存储过程UPDATE_CUSTOMERTITLE2
	 */
	public void updateVendorTitle2(final String supplier,final String paymentid){
		this.importPaymentHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				CallableStatement cs = session.connection().prepareCall("{call UPDATE_VENDORTITLE2(?,?)}");    
				cs.setString(1, supplier);
				cs.setString(2, paymentid);
				boolean isSuccess = cs.execute();
				return isSuccess; 
			}
		});
	}
	
	/**
	 * 根据ID获取对象
	 * 
	 * @param paymentid
	 * @return
	 */
	public ImportPayment getImportPaymentById(String paymentid) {
		return this.importPaymentHibernateDao.get(paymentid);
	}

	public PlickListInfo getPlickListInfoByno(String plickListInfoId){
		List<PlickListInfo> plickListInfoLst = this.importPaymentHibernateDao.getHibernateTemplate().find("from PlickListInfo where pickListID='" + plickListInfoId + "'");
		if(plickListInfoLst != null && plickListInfoLst.size() > 0)
			return plickListInfoLst.get(0);
		else
			return null;
	}
	
	public ImportPayment getSupplierByID(String id){
		List<ImportPayment> importPaymentLst = this.importPaymentHibernateDao.getHibernateTemplate().find("from ImportPayment where supplier='" + id + "'" + " order by createtime desc");
		if(importPaymentLst != null && importPaymentLst.size() > 0)
			return importPaymentLst.get(0);
		else
			return null;
	}
	
	/**
	 * 
	 * 保存
	 * 
	 * @param importPayment
	 */
	public void _saveOrUpdate(ImportPayment importPayment,
			Set<ImportPaymentItem> deletedImportPaymentItemSet,
			Set<ImportPaymentCbill> deletedImportPaymentCbillSet,
			Set<ImportPayBankItem> deletedImportPayBankItemSet,
			Set<ImportDocuBankItem> deletedImportDocuBankItemSet,
			Set<BillPayReBankItem> deletedBillPayReBankItemSet,
			Set<ImportRelatPayment> deletedImportRelatPaymentSet,
			BusinessObject businessObject) {
		if (StringUtils.isNullBlank(importPayment.getPaymentno())) {
			String paymentno = NumberService.getNextObjectNumber(
					"importpaymentno", importPayment);
			importPayment.setPaymentno(paymentno);
		}
		
		//判断CURRENCY为"CNY"直接将APPLYAMOUNT的值存到这个字段，为其他币别时APPLYAMOUNT*CLOSEEXCHANGERAT的值存入。
		List<String> currencyCodes = this.getPaymentCurrencyJdbcDao().getAllCurrencyCode();
		if(!"CNY".equals(importPayment.getCurrency())){
			BigDecimal applyAmount = importPayment.getApplyamount();
			BigDecimal closeExchangeGreat = importPayment.getCloseexchangerat();
			BigDecimal result = applyAmount.multiply(closeExchangeGreat);
			importPayment.setConvertamount(result);
		}else{
			importPayment.setConvertamount(importPayment.getApplyamount());
		}
		
		if (StringUtils.isNullBlank(importPayment.getPaymentid())) {
			_save(importPayment);

			/**
			 * 重分配的保存
			 */
			if (importPayment.getBusinessstate().equals(
					BusinessState.REASSIGNED)) {
				/**
				 * 插入关联单据表
				 */
				ImportPayment ip = this.importPaymentHibernateDao.get(importPayment.getRepaymentid());
				this.reassignJdbcDao.insertPaymentRelateForReassign(this.reassignJdbcDao.getReassignidByBoId(importPayment.getRepaymentid()), importPayment.getPaymentid(), importPayment.getRepaymentid(), ip.getPaymentno(),
						importPayment.getFactamount());

			}
		} else {
			_update(importPayment, deletedImportPaymentItemSet,
					deletedImportPaymentCbillSet, deletedImportPayBankItemSet,
					deletedImportDocuBankItemSet,deletedBillPayReBankItemSet, deletedImportRelatPaymentSet,
					businessObject);
		}
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-27
	 * 根据部门ID去取得押汇业务范围
	 */
	public String getRedocarybcByDeptID(String deptId){
		List<Map<String,String>> list = this.paymentJdbcDao.getOrganizationById(deptId);
		return list.get(0).get("COMPANYCODE");
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-08
	 * 删除付款银行、押汇/海外代付银行、结算科目和纯资金
	 */
	public void deleteDocuInfo(String paymentId, String bankIndex){
		this.paymentJdbcDao.deleteDocuInfo(paymentId, bankIndex);
	}
	
	public void updateImportPayment(ImportPayment importPayment){
		this.importPaymentHibernateDao.update(importPayment);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-12
	 * 根据供应商编号获取供应商名字
	 */
	public String getSupplierNameByNo(String lifnr){
		List<Map<String,String>> list = this.paymentJdbcDao.getSupplierByNo(lifnr);
		return list.get(0).get("NAME1");
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-12
	 * 根据数据字典与值去查找该值对应的文本
	 */
	public String getDomainText(String domName, String domValue){
		List<Map<String,String>> list = this.paymentJdbcDao.getDomainText(domName, domValue);
		return list.get(0).get("DDTEXT");
	}
	
	/**
	 * 判断付款下的款和票是否可请
	 * @param paymentId
	 * @return
	 */
	public void settlePayment( String paymentId )
	{
		ImportPayment importPayment = this.importPaymentHibernateDao.get(paymentId);
		//判断款
		Set importPaymentItems = importPayment.getImportPaymentItem();
		
		for (Iterator<ImportPaymentItem> iter = importPaymentItems.iterator(); iter.hasNext();){
			ImportPaymentItem importPaymentItem = (ImportPaymentItem) iter.next();
			
			if((importPaymentItem.getPrepayamount().compareTo(BigDecimal.valueOf(0))==0)){
				//this.paymentItemJdbcDao.updatePaymentItemIsCleared(importPaymentItem.getPaymentitemid(), "1");
			}else{
				//this.paymentItemJdbcDao.updatePaymentItemIsCleared(importPaymentItem.getPaymentitemid(), "0");
			}
		}
		//判断票
		Set importPaymentCbills = importPayment.getImportPaymentCbill();
		for (Iterator<ImportPaymentCbill> iter = importPaymentCbills.iterator(); iter.hasNext();)
		{
			ImportPaymentCbill importPaymentCbill = (ImportPaymentCbill)iter.next();
			String billno = importPaymentCbill.getBillno();
			
			VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByInvoice(billno);
			//已审批完的金额
			BigDecimal clearedAmount = this.vendorTitleJdbcDao.getSumClearAmount(billno, "'3'");
			//发票金额
			BigDecimal billAmount = vendorTitle.getDmbtr();
			if((clearedAmount.compareTo(billAmount)==0)
					||((clearedAmount.add(importPaymentCbill.getClearamount2())).compareTo(billAmount)==0))
			{
				this.vendorTitleJdbcDao.updateVendorTitleIsClearedByInvoice(billno, "1");
			}
		}
		
		//更新付款单的业务状态 为审批完成
//		this.paymentJdbcDao.updatePayment(paymentId, "3");
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-25
	 * 根据付款单号取得付款信息
	 */
	public ImportPayment getPaymentByNo(String paymentNo){
		return this.importPaymentHibernateDao.getPaymentByNo(paymentNo);
	}
	
	/**
     * 取得(内贸、进口)付款浏览查询SQL语句
     * 2011-12-07 修改不取海外代付
     * @param map
     * @return
     */
    public String getPaymentReportQuerySql(Map<String, String> map)
    {
        String issuing_date = map.get("issuing_date");
        String issuing_dateEnd = map.get("issuing_dateEnd");
        String bukrs = map.get("bukrs");
        
        String project_no = map.get("project_no");
        String old_contract_no = map.get("old_contract_no");
        String customer = map.get("customer");
        String supplier = map.get("supplier");
        
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT PI.PROJECT_NO, "); 
        sb.append("       P.PAYMENTID, "); 
        sb.append("       TP.PROJECT_NAME, ");
        sb.append("       TC.SAP_ORDER_NO, ");
        sb.append("       PI.OLD_CONTRACT_NO, ");  //纸质合同号

        sb.append("      CASE                            ");
        sb.append("        WHEN TC.TRADE_TYPE = '1' THEN ");
        sb.append("         '外贸自营进口*业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '2' THEN ");
        sb.append("         '外贸自营出口*业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '3' THEN ");
        sb.append("         '外贸自营进口业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '4' THEN ");
        sb.append("         '外贸自营出口业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '5' THEN ");
        sb.append("         '外贸代理出口业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '6' THEN ");
        sb.append("         '外贸代理进口业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '7' THEN ");
        sb.append("         '内贸业务'                   ");
        sb.append("        WHEN TC.TRADE_TYPE = '8' THEN ");
        sb.append("         '进料加工业务'               ");
        sb.append("        WHEN TC.TRADE_TYPE = '9' THEN ");
        sb.append("         '自营进口敞口业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '10' THEN");
        sb.append("         '内贸敞口'                   ");
        sb.append("      END AS VBAK_AUART,              ");//业务类型
        sb.append("       tc.EKKO_LIFNR, ");
        sb.append("       tc.EKKO_LIFNR_NAME, "); // 供应商名称
        sb.append("       '' as VBKD_BZIRK, "); //TC.VBKD_BZIRK

        sb.append("       CASE");
        sb.append("         WHEN P.ISOVERREPAY = '1' THEN");  //　是否为海外代付
        sb.append("          '是'");
        sb.append("         ELSE");
        sb.append("          '否'");
        sb.append("       END AS ISOVERREPAY, ");
        sb.append("       CASE");
        sb.append("         WHEN P.ISREPRESENTPAY = '1' THEN");
        sb.append("          '是'");
        sb.append("         ELSE");
        sb.append("          '否'");
        sb.append("       END AS ISREPRESENTPAY, ");
        sb.append("       P.REPRESENTPAYCUST, ");
        sb.append("       YG.NAME1, ");
        sb.append("       P.PAYMENTNO, ");
        sb.append("       DB.DOCUARYBANKACCO, ");

        sb.append("       DB.DOCUARYBANKSUBJ, ");
        sb.append("       P.DOCUMENTARYRATE, ");
        sb.append("       P.CURRENCY2, ");
        sb.append("       '' AS BEGBALANCE2, ");
        sb.append("       '' AS BEGBALANCE, ");

        sb.append("       P.DOCUMENTARYDATE, ");
        sb.append("       '' AS APPLYAMOUNT2, ");
        sb.append("       P.APPLYAMOUNT, ");
        sb.append("       P.DOCUMENTARYPAYDT, ");
        sb.append("       '' AS REDOCARYAMOUNT2, ");

        sb.append("       P.REDOCARYAMOUNT, ");
        sb.append("       '' AS ENDBALANCE2, ");
        sb.append("       '' AS ENDBALANCE, ");
        sb.append("       p.dept_id, ");
        sb.append("       DP.Dept_Name ");
        
        sb.append("  FROM YPAYMENT P");
        sb.append("  LEFT JOIN YPAYMENTITEM PI");
        sb.append("    ON (PI.PAYMENTID = P.PAYMENTID)");
        sb.append("  LEFT JOIN YDOUCARYBANKITEM DB");
        sb.append("    ON (DB.PAYMENTID = P.PAYMENTID)");
        sb.append("  LEFT JOIN YGETKUNNR YG");
        sb.append("    ON (YG.KUNNR = P.REPRESENTPAYCUST AND YG.bukrs='"+bukrs+"' )");
        sb.append("  LEFT JOIN T_PROJECT_INFO TP");
        sb.append("    ON (PI.PROJECT_NO = TP.PROJECT_NO)");
        sb.append("  LEFT JOIN T_CONTRACT_PURCHASE_INFO TC");
        sb.append("    ON (TC.CONTRACT_NO = PI.CONTRACT_NO) ");
        sb.append("  LEFT JOIN T_SYS_DEPT DP");
        sb.append("    ON (DP.Dept_Id = P.DEPT_ID) ");
        sb.append(" WHERE P.PAY_TYPE = '2'");
        sb.append("      AND P.isoverrepay <> '1' ");
        
        // 供应商
        if (StringUtils.isNotBlank(supplier)){
            sb.append(" and tc.EKKO_LIFNR like '%" + supplier + "%'");
        }
        // 客户
        if (StringUtils.isNotBlank(customer)){
            //sb.append(" and a.supplier like '%" + map.get("supplier") + "%'");
        }
        // 立项
        if (StringUtils.isNotBlank(project_no)){
            sb.append(" and pi.project_no like '%" + project_no + "%'");
        }
        // 合同号
        if (StringUtils.isNotBlank(old_contract_no)){
            sb.append(" and pi.old_contract_no like '%" + old_contract_no + "%'");
        }
        String deptId = map.get("dept_id");
        // 部门
        if (StringUtils.isNotBlank(deptId)){
            sb.append(" and p.dept_id like '%" + deptId + "%'");
        }
        
        // 公司
        if (StringUtils.isNotBlank(bukrs)){
            sb.append("   AND exists (select 'x' from YVOUCHER V, YVOUCHERITEM VI where  P.PAYMENTID = V.BUSINESSID AND V.VOUCHERID = VI.VOUCHERID");
            sb.append(" AND trim(v.voucherno) IS NOT NULL ");
            sb.append(" and v.companycode like '%" + bukrs + "%' )");
        }
        // 日期
        if (StringUtils.isNotBlank(issuing_date) || StringUtils.isNotBlank(issuing_dateEnd)) {
            sb.append("   AND not exists (select 'x' from YVOUCHER V, YVOUCHERITEM VI where  P.PAYMENTID = V.BUSINESSID AND V.VOUCHERID = VI.VOUCHERID AND V.BSTAT <> 'A' ");
            if (StringUtils.isNotBlank(issuing_date) && StringUtils.isNotBlank(issuing_dateEnd)) {
                sb.append("   AND  ((V.CHECKDATE < '"+issuing_date+"' AND ( (V.VOUCHERCLASS = '3') or (v.voucherclass='2' AND vi.cashflowitem='351' )  ) ) OR (V.CHECKDATE > '"+issuing_dateEnd+"' AND V.VOUCHERCLASS = '2' AND vi.cashflowitem <> '351' AND TRIM(TO_CHAR(TRIM(vi.ROWNUMBER),'000')) = TRIM(TO_CHAR(TRIM('1'),'000')) )) ");
            } else if (StringUtils.isNotBlank(issuing_date)) {
                sb.append("   AND  ((V.CHECKDATE < '"+issuing_date+"' AND ( (V.VOUCHERCLASS = '3') or (v.voucherclass='2' AND vi.cashflowitem='351' )  )) OR (V.CHECKDATE > '"+issuing_date        +"' AND V.VOUCHERCLASS = '2' AND vi.cashflowitem <> '351' AND TRIM(TO_CHAR(TRIM(vi.ROWNUMBER),'000')) = TRIM(TO_CHAR(TRIM('1'),'000')) )) ");
            }
            sb.append(" )");
        }
        return sb.toString();
    }
    
    /**
     * 取海外代付 付款浏览查询SQL语句
     */
    public String getPaymentIsOverRePayReportQuerySql(Map<String, String> map)
    {
        String issuing_date = map.get("issuing_date");
        String issuing_dateEnd = map.get("issuing_dateEnd");
        String bukrs = map.get("bukrs");
        
        String project_no = map.get("project_no");
        String old_contract_no = map.get("old_contract_no");
        String customer = map.get("customer");
        String supplier = map.get("supplier");
        
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT PI.PROJECT_NO, "); 
        sb.append("       P.PAYMENTID, "); 
        sb.append("       TP.PROJECT_NAME, ");
        sb.append("       '' AS VBAP_ARKTX, ");
        sb.append("       TC.SAP_ORDER_NO, ");
        sb.append("       PI.OLD_CONTRACT_NO, ");  //纸质合同号
        
        sb.append("      CASE                            ");
        sb.append("        WHEN TC.TRADE_TYPE = '1' THEN ");
        sb.append("         '外贸自营进口*业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '2' THEN ");
        sb.append("         '外贸自营出口*业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '3' THEN ");
        sb.append("         '外贸自营进口业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '4' THEN ");
        sb.append("         '外贸自营出口业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '5' THEN ");
        sb.append("         '外贸代理出口业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '6' THEN ");
        sb.append("         '外贸代理进口业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '7' THEN ");
        sb.append("         '内贸业务'                   ");
        sb.append("        WHEN TC.TRADE_TYPE = '8' THEN ");
        sb.append("         '进料加工业务'               ");
        sb.append("        WHEN TC.TRADE_TYPE = '9' THEN ");
        sb.append("         '自营进口敞口业务'           ");
        sb.append("        WHEN TC.TRADE_TYPE = '10' THEN");
        sb.append("         '内贸敞口'                   ");
        sb.append("      END AS VBAK_AUART,              ");//业务类型
        sb.append("       tc.EKKO_LIFNR, ");
        sb.append("       tc.EKKO_LIFNR_NAME, "); // 供应商名称
        sb.append("       '' as VBKD_BZIRK, "); //TC.VBKD_BZIRK
        
        sb.append("       CASE");
        sb.append("         WHEN P.ISOVERREPAY = '1' THEN");  //　是否为海外代付
        sb.append("          '是'");
        sb.append("         ELSE");
        sb.append("          '否'");
        sb.append("       END AS ISOVERREPAY, ");
        sb.append("       CASE");
        sb.append("         WHEN P.ISREPRESENTPAY = '1' THEN");
        sb.append("          '是'");
        sb.append("         ELSE");
        sb.append("          '否'");
        sb.append("       END AS ISREPRESENTPAY, ");
        sb.append("       P.REPRESENTPAYCUST, ");
        sb.append("       YG.NAME1, ");
        sb.append("       P.PAYMENTNO, ");
        sb.append("       DB.DOCUARYBANKACCO, ");
        
        sb.append("       DB.DOCUARYBANKSUBJ, ");
        sb.append("       P.DOCUMENTARYRATE, ");
        sb.append("       P.CURRENCY2, ");
        sb.append("       '' AS BEGBALANCE2, ");
        sb.append("       '' AS BEGBALANCE, ");
        
        sb.append("       P.DOCUMENTARYDATE, ");
        sb.append("       '' AS APPLYAMOUNT2, ");
        sb.append("       P.APPLYAMOUNT, ");
        sb.append("       P.DOCUMENTARYPAYDT, ");
        sb.append("       '' AS REDOCARYAMOUNT2, ");
        
        sb.append("       P.REDOCARYAMOUNT, ");
        sb.append("       '' AS ENDBALANCE2, ");
        sb.append("       '' AS ENDBALANCE, ");
        sb.append("       p.dept_id, ");
        sb.append("       DP.Dept_Name ");
        
        sb.append("  FROM YPAYMENT P");
        sb.append("  LEFT JOIN YPAYMENTITEM PI");
        sb.append("    ON (PI.PAYMENTID = P.PAYMENTID)");
        sb.append("  LEFT JOIN YDOUCARYBANKITEM DB");
        sb.append("    ON (DB.PAYMENTID = P.PAYMENTID)");
        sb.append("  LEFT JOIN YGETKUNNR YG");
        sb.append("    ON (YG.KUNNR = P.REPRESENTPAYCUST AND YG.bukrs='"+bukrs+"' )");
        sb.append("  LEFT JOIN T_PROJECT_INFO TP");
        sb.append("    ON (PI.PROJECT_NO = TP.PROJECT_NO)");
        sb.append("  LEFT JOIN T_CONTRACT_PURCHASE_INFO TC");
        sb.append("    ON (TC.CONTRACT_NO = PI.CONTRACT_NO) ");
        sb.append("  LEFT JOIN T_SYS_DEPT DP");
        sb.append("    ON (DP.Dept_Id = P.DEPT_ID) ");
        sb.append(" WHERE P.PAY_TYPE = '2'");
        sb.append("      AND P.isoverrepay = '1' ");
        
        // 供应商
        if (StringUtils.isNotBlank(supplier)){
            sb.append(" and tc.EKKO_LIFNR like '%" + supplier + "%'");
        }
        // 立项
        if (StringUtils.isNotBlank(project_no)){
            sb.append(" and pi.project_no like '%" + project_no + "%'");
        }
        // 合同号
        if (StringUtils.isNotBlank(old_contract_no)){
            sb.append(" and pi.old_contract_no like '%" + old_contract_no + "%'");
        }
        String deptId = map.get("dept_id");
        // 部门
        if (StringUtils.isNotBlank(deptId)){
            sb.append(" and p.dept_id like '%" + deptId + "%'");
        }
        
        // 日期－还押汇时间<时间段起始、押汇时间>时间段结束，即不落在时间段内不计算。
        if (StringUtils.isNotBlank(issuing_date) || StringUtils.isNotBlank(issuing_dateEnd)) {
            sb.append("   AND not exists (select 'x' from YVOUCHER V, YVOUCHERITEM VI where  P.PAYMENTID = V.BUSINESSID AND V.VOUCHERID = VI.VOUCHERID AND V.BSTAT <> 'A' ");
            if (StringUtils.isNotBlank(issuing_date) && StringUtils.isNotBlank(issuing_dateEnd)) {
//                sb.append("   AND  ((V.CHECKDATE < '"+issuing_date+"' AND ( (V.VOUCHERCLASS = '3') or (v.voucherclass='2' AND vi.cashflowitem='351' )  ) ) OR (P.documentarydate > '"+issuing_dateEnd+"'  )) ");
                sb.append("   AND  ((V.CHECKDATE < '"+issuing_date+"'  ) OR (P.documentarydate > '"+issuing_dateEnd+"'  )) ");
            } else if (StringUtils.isNotBlank(issuing_date)) {
//                sb.append("   AND  ((V.CHECKDATE < '"+issuing_date+"' AND ( (V.VOUCHERCLASS = '3') or (v.voucherclass='2' AND vi.cashflowitem='351' )  )) OR (P.documentarydate > '"+issuing_date        +"' )) ");
                sb.append("   AND  ((V.CHECKDATE < '"+issuing_date+"' ) OR (P.documentarydate > '"+issuing_date        +"' )) ");
            }
            sb.append(" )");
        }
        return sb.toString();
    }
    
    public boolean isValidCustCreditPro(String projectNo){
    	return paymentJdbcDao.isValidCustCreditPro(projectNo);
    }
    
    public ImportPayment getPaymentByPaymentId(String paymentId) throws Exception{
    	return (ImportPayment)paymentJdbcDao.getPaymentByPaymentId(paymentId, ImportPayment.class);
    }
    
    public void updateDate(ImportPayment info ,String oldStr,String newStr,String userId) throws Exception{
    	paymentJdbcDao.updateDate(info ,oldStr,newStr,userId);
	}
}