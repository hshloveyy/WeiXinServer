/*
 * @(#)HomePaymentService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月19日 10点44分13秒
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
import com.infolion.sapss.common.ExcelObject;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.VendorTitle;
import com.infolion.xdss3.payment.dao.PayMentCBillJdbcDao;
import com.infolion.xdss3.payment.dao.PaymentCurrencyJdbcDao;
import com.infolion.xdss3.payment.dao.PaymentItemJdbcDao;
import com.infolion.xdss3.payment.dao.PaymentJdbcDao;
import com.infolion.xdss3.payment.domain.HomeDocuBankItem;
import com.infolion.xdss3.payment.domain.HomePayBankItem;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.HomePaymentCbill;
import com.infolion.xdss3.payment.domain.HomePaymentItem;
import com.infolion.xdss3.payment.domain.HomePaymentRelat;
import com.infolion.xdss3.paymentGen.service.HomePaymentServiceGen;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;

/**
 * <pre>
 * 内贸付款(HomePayment)服务类
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
public class HomePaymentService extends HomePaymentServiceGen
{
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

	/**
	 * @param reassignJdbcDao
	 *            the reassignJdbcDao to set
	 */
	public void setReassignJdbcDao(ReassignJdbcDao reassignJdbcDao) {
		this.reassignJdbcDao = reassignJdbcDao;
	}
	
	@Autowired
	private PayMentCBillJdbcDao payMentCBillJdbcDao;
	
	public void setPayMentCBillJdbcDao(PayMentCBillJdbcDao payMentCBillJdbcDao)
	{
		this.payMentCBillJdbcDao = payMentCBillJdbcDao;
	}
	
	public PaymentCurrencyJdbcDao getPaymentCurrencyJdbcDao() {
		return paymentCurrencyJdbcDao;
	}

	public void setPaymentCurrencyJdbcDao(
			PaymentCurrencyJdbcDao paymentCurrencyJdbcDao) {
		this.paymentCurrencyJdbcDao = paymentCurrencyJdbcDao;
	}
	
	/**
	 * 根据ID获取对象
	 * @param paymentid
	 * @return
	 */
	public HomePayment getHomePaymentById( String paymentid )
	{
		return this.homePaymentHibernateDao.get(paymentid);
	}
	
	/**
	 * 调用存储过程UPDATE_CUSTOMERTITLE
	 */
	public void updateVendorTitle(final String supplier){
		this.homePaymentHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
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
		this.homePaymentHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
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
	 * 
	 * 保存
	 * 
	 * @param homePayment
	 */
	public void _saveOrUpdate(HomePayment homePayment,
			Set<HomePaymentItem> deletedHomePaymentItemSet,
			Set<HomePaymentCbill> deletedHomePaymentCbillSet,
			Set<HomePayBankItem> deletedHomePayBankItemSet,
			Set<HomeDocuBankItem> deletedHomeDocuBankItemSet,
			Set<HomePaymentRelat> deletedHomePaymentRelatSet,
			BusinessObject businessObject) {

		if (StringUtils.isNullBlank(homePayment.getPaymentno())) {
			String paymentno = NumberService.getNextObjectNumber(
					"homepaymentno", homePayment);
			homePayment.setPaymentno(paymentno);
		}

		// 判断CURRENCY为"CNY"时直接将APPLYAMOUNT的值存到这个字段，为其他币别时APPLYAMOUNT*CLOSEEXCHANGERAT的值存入。
		List<String> currencyCodes = this.getPaymentCurrencyJdbcDao()
				.getAllCurrencyCode();
		if (!"CNY".equals(homePayment.getCurrency())) {
			BigDecimal applyAmount = homePayment.getApplyamount();
			BigDecimal closeExchangeGreat = homePayment.getCloseexchangerat();
			BigDecimal result = applyAmount.multiply(closeExchangeGreat);
			homePayment.setConvertamount(result);
		} else {
			homePayment.setConvertamount(homePayment.getApplyamount());
		}

		if (StringUtils.isNullBlank(homePayment.getPaymentid())) {
			_save(homePayment);

			/**
			 * 重分配的保存
			 */
			if (homePayment.getBusinessstate().equals(BusinessState.REASSIGNED)) {
				HomePayment hp = this.homePaymentHibernateDao.get(homePayment.getRepaymentid());
				
				/**
				 * 插入关联单据表
				 */
				this.reassignJdbcDao.insertPaymentRelateForReassign(this.reassignJdbcDao.getReassignidByBoId(homePayment.getRepaymentid()), homePayment.getPaymentid(), homePayment.getRepaymentid(), hp.getPaymentno(),
								homePayment.getFactamount());

			}

		} else {
			_update(homePayment, deletedHomePaymentItemSet,
					deletedHomePaymentCbillSet, deletedHomePayBankItemSet,
					deletedHomeDocuBankItemSet, deletedHomePaymentRelatSet,
					businessObject);
		}
	}

	public HomePayment getSupplierByID(String supplierid) {
		List<HomePayment> homePaymentLst = this.homePaymentHibernateDao.getHibernateTemplate().find("from HomePayment where supplier='" + supplierid + "'");
		if(homePaymentLst != null && homePaymentLst.size() > 0)
			return homePaymentLst.get(0);
		else
			return null;
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-08
	 * 删除结算科目和纯资金
	 */
	public void deleteSettleFund(String paymentId){
		this.paymentJdbcDao.deleteSettleFund(paymentId);
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
		HomePayment homePayment = this.homePaymentHibernateDao.get(paymentId);
		//判断款
		Set homePaymentItems = homePayment.getHomePaymentItem();
		
		for (Iterator<HomePaymentItem> iter = homePaymentItems.iterator(); iter.hasNext();){
			HomePaymentItem homePaymentItem = (HomePaymentItem) iter.next();
			
			if((homePaymentItem.getPrepayamount().compareTo(BigDecimal.valueOf(0))==0)){
				//this.paymentItemJdbcDao.updatePaymentItemIsCleared(homePaymentItem.getPaymentitemid(), "1");
			}else{
				//this.paymentItemJdbcDao.updatePaymentItemIsCleared(homePaymentItem.getPaymentitemid(), "0");
			}
		}
		//判断票
		Set homePaymentCbills = homePayment.getHomePaymentCbill();
		for (Iterator<HomePaymentCbill> iter = homePaymentCbills.iterator(); iter.hasNext();)
		{
			HomePaymentCbill homePaymentCbill = (HomePaymentCbill)iter.next();
			String billno = homePaymentCbill.getBillno();
			
			VendorTitle vendorTitle = this.vendorTitleJdbcDao.getByInvoice(billno);
			//已审批完的金额
			BigDecimal clearedAmount = this.vendorTitleJdbcDao.getSumClearAmount(billno, "'3'");
			//发票金额
			BigDecimal billAmount = vendorTitle.getDmbtr();
			if((clearedAmount.compareTo(billAmount)==0)
					||((clearedAmount.add(homePaymentCbill.getClearamount2())).compareTo(billAmount)==0))
			{
				this.vendorTitleJdbcDao.updateVendorTitleIsClearedByInvoice(billno, "1");
			}
		}
		
		//更新付款单的业务状态 为审批完成
//		this.paymentJdbcDao.updatePayment(paymentId, "3");
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-14
	 * 根据供应商编号从付款主表中取出该供应商最近付款所使用的银行
	 */
	public List getBankInfoBySupplierNo(String supplier){
		return this.paymentJdbcDao.getBankInfoBySupplierNo(supplier);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-25
	 * 根据付款单号取得付款信息
	 */
	public HomePayment getPaymentByNo(String paymentNo){
		return this.homePaymentHibernateDao.getPaymentByNo(paymentNo);
	}
	
	   
    private String getDateSql(String startDate,String endDate,String column){
        StringBuffer sb = new StringBuffer();
        if (startDate != null && endDate != null)
            sb.append(" and to_date("+column+",'yyyy-mm-dd hh24:mi:ss') between to_date('" 
                    + startDate + "','yyyy-mm-dd hh24:mi:ss') and to_date('" 
                    + endDate + "','yyyy-mm-dd hh24:mi:ss')");
        else if (startDate != null)
            sb.append(" and "+column+" like '%" + startDate + "%'");
        else if (endDate != null)
            sb.append(" and to_date("+column+",'yyyy-mm-dd hh24:mi:ss') < to_date('"
                    + endDate + "','yyyy-mm-dd hh24:mi:ss')");
        else
            return "";
        return sb.toString();
            
    }

    /**
     * 得到当前用户所能查看到的部门
     * @param map
     * @return 返回可以用于sql in查询的部门ID串
     */
    private String getDeptId(Map<String,String> map){
        com.infolion.platform.console.sys.context.UserContext userContext = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext()
            .getUserContext();
        String deptId;
        
        if("1".equals(userContext.getSysDept().getIsFuncDept()))
            deptId = userContext.getGrantedDepartmentsId();
        else{
            deptId = "'"+userContext.getSysUser().getDeptId()+"'";
        }
        return deptId;
    }
    
	/**
     * 取得内贸付款浏览查询SQL语句
     * 
     * @param map
     * @return
     */
    public String getQuerySql(Map<String, String> map)
    {
        String deptId = getDeptId(map);
        String payType = map.get("payType");
        //默认字典表为内贸付款方式字典表
        String dictTable = "bm_inner_pay_method";
        String contractTable = "T_CONTRACT_PURCHASE_INFO";
        //如果是非货付款取非货付款方式字典表
        if("2".equals(payType)){
            dictTable = "bm_inner_pay_method_un";
            contractTable = "V_CONTRACT_PURCHASE_AND_SALES";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(" Select distinct d.dept_name,u.real_name as creator_name,p.currency,");
        sb.append(" m.title as pay_method,p.apply_time,p.approved_time,p.pay_real_time,p.pay_value,p.rec_bank,p.pay_method pay_method_view,");
        sb.append(" p.open_account_bank,p.open_account_no,p.EXAMINE_STATE,p.EXAMINE_STATE as");
        sb.append(" EXAMINE_STATE_D_EXAMINE_STATE,p.payment_id,p.creator,p.creator_time ");
        sb.append(" from T_PAYMENT_INNER_INFO p left outer join T_PAYMENT_CONTRACT c on p.payment_id=c.payment_id left outer join "+contractTable+" i on i.contract_purchase_id=c.contract_purchase_id ");
        sb.append(" left outer join T_SYS_DEPT d on d.dept_id=p.dept_id left outer join T_SYS_USER u on u.user_id=p.creator left outer join "+dictTable+" m on m.id=p.pay_method ");
        sb.append(" where p.is_available = '1' and (i.order_state in ('3','5','7','8','9') or i.order_state is null) and p.dept_id in ("+deptId);
        sb.append(") ");

        if(map.get("deptId")!=null&&map.get("deptId").length()>0){
            String ds =map.get("deptId");
            String[] dss = ds.split(",");
            String deptIds="";
            for (int i = 0; i < dss.length; i++) {
                if(i==(dss.length-1))
                    deptIds = deptIds+"'"+dss[i]+"'";
                else
                    deptIds = deptIds+"'"+dss[i]+"',";
            }
            sb.append(" and p.dept_id in (" + deptIds + ")");
        }
        // 申请人
        if (map.get("realName") != null)
            sb.append(" and p.Creator_Name like '%" + map.get("realName") + "%'");
        // 合同时间
        if (map.get("contractTime") != null || map.get("endContractTime") != null)
            sb.append(getDateSql(map.get("contractTime"),map.get("endContractTime"),"i.APPROVED_TIME"));
        // 合同编码
        if (map.get("contractNo") != null)
            sb.append(" and i.CONTRACT_NO like '%" + map.get("contractNo").toUpperCase()
                    + "%'");
        // 付款形式
        if (map.get("payMethod") != null)
            sb.append(" and p.PAY_METHOD in (" + map.get("payMethod")
                    + ")");
        // 付款类别
        if (payType != null)
            sb.append(" and p.PAY_TYPE = '" + map.get("payType")
                    + "'");
        // 付款用途
        if (map.get("payUse") != null)
            sb.append(" and p.PAY_USE like '%" + map.get("payUse") + "%'");
        // 申请时间
        if (map.get("applyTime") != null || map.get("endApplyTime") != null)
            sb.append(getDateSql(map.get("applyTime"),map.get("endApplyTime"),"p.APPLY_TIME"));
        // 审批通过时间
        if (map.get("approvedTime") != null || map.get("endApprovedTime") != null)
            sb.append(getDateSql(map.get("approvedTime"),map.get("endApprovedTime"),"p.APPROVED_TIME"));
        // 实际付款日
        if (map.get("payRealTime") != null || map.get("endPayRealTime") != null)
            sb.append(getDateSql(map.get("payRealTime"),map.get("endPayRealTime"),"p.PAY_REAL_TIME"));
        // 收款单位名
        if (map.get("recBank") != null)
            sb.append(" and p.REC_BANK like '%" + map.get("recBank") + "%'");
        if (map.get("payValue") != null)
            sb.append(" and p.pay_value >= '" + map.get("payValue") + "'");
        if (map.get("payValue1") != null)
            sb.append(" and p.pay_value <= '" + map.get("payValue1") + "'");
        // 财务编号
        if (map.get("financeNo") != null)
            sb.append(" and p.FINANCE_NO like '%" + map.get("financeNo") + "%'");
        if (map.get("examState") != null)
            sb.append(" and p.examine_state = '" + map.get("examState") + "'");
        sb.append(" order by p.creator_time desc");
        //System.out.println("内贸付款查询功能SQL语句:" + sb.toString());
        return sb.toString();
    }
    
    public boolean isValidCustCreditPro(String projectNo){
    	return paymentJdbcDao.isValidCustCreditPro(projectNo);
    }
    public HomePayment getPaymentByPaymentId(String paymentId) throws Exception{
    	return (HomePayment)paymentJdbcDao.getPaymentByPaymentId(paymentId, HomePayment.class);
    }
    
    public void updateDate(HomePayment info ,String oldStr,String newStr,String userId) throws Exception{
    	paymentJdbcDao.updateDate(info ,oldStr,newStr,userId);
	}
    
    public void dealOutToExcel(String sql,String strAuthSql ,ExcelObject excel){
    	paymentJdbcDao.dealOutToExcel(sql,strAuthSql ,excel);
	}
}