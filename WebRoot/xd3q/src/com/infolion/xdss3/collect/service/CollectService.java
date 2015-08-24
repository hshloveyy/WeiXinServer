/*
 * @(#)CollectService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月27日 07点45分08秒
 *  描　述：创建
 */
package com.infolion.xdss3.collect.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;
import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.contract.dao.SaleContractJdbcDAO;
import com.infolion.sapss.export.dao.ExportJdbcDao;
import com.infolion.sapss.ship.domain.TShipInfo;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.bankInfo.service.BankInfoService;
import com.infolion.xdss3.collect.dao.CollectJdbcDao;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collectGen.service.CollectServiceGen;
import com.infolion.xdss3.collectbankitem.domain.CollectBankItem;
import com.infolion.xdss3.collectcbill.dao.CollectCbillJdbcDao;
import com.infolion.xdss3.collectcbill.domain.CollectCbill;
import com.infolion.xdss3.collectitem.dao.CollectItemJdbcDao;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.collectitem.service.CollectItemService;
import com.infolion.xdss3.collectrelated.domain.CollectRelated;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.dao.CustomerTitleJdbcDao;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.masterData.domain.CustomerTitle;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.unnameCollect.domain.UnnamerCollect;
import com.infolion.xdss3.unnameCollect.service.UnnamerCollectService;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;
import com.infolion.xdss3.voucheritem.service.VoucherItemService;

/**
 * <pre>
 * 收款信息表(Collect)服务类
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
public class CollectService extends CollectServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	protected UnnamerCollectService unnamerCollectService;
	
	public void setUnnamerCollectService(UnnamerCollectService unnamerCollectService) {
		this.unnamerCollectService = unnamerCollectService;
	}
	
	@Autowired
	protected VoucherService voucherService;
	
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}
	
	@Autowired
	protected BankInfoService bankInfoService;
	public void setBankInfoService(BankInfoService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}

	@Autowired
	protected CollectItemService collectItemService;
	public void setCollectItemService(CollectItemService collectItemService) {
		this.collectItemService = collectItemService;
	}
	
	@Autowired
	protected VoucherItemService voucherItemService;
	public void setVoucherItemService(VoucherItemService voucherItemService) {
		this.voucherItemService = voucherItemService;
	}
	
	@Autowired
	protected VoucherItemJdbcDao voucherItemJdbcDao;
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}
	
	@Autowired
	private CollectCbillJdbcDao collectCbillJdbcDao;
	
	public void setCollectCbillJdbcDao(CollectCbillJdbcDao collectCbillJdbcDao) {
		this.collectCbillJdbcDao = collectCbillJdbcDao;
	}
	
	@Autowired
	private SysDeptJdbcDao sysDeptJdbcDao;

	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
	}
	
	@Autowired
	private CollectJdbcDao collectJdbcDao;
	
	public void setCollectJdbcDao(CollectJdbcDao collectJdbcDao) {
		this.collectJdbcDao = collectJdbcDao;
	}

	@Autowired
	private CollectItemJdbcDao collectItemJdbcDao;
	
	public void setCollectItemJdbcDao(CollectItemJdbcDao collectItemJdbcDao) {
		this.collectItemJdbcDao = collectItemJdbcDao;
	}
	
	@Autowired
	private CustomerTitleJdbcDao customerTitleJdbcDao;

	public void setCustomerTitleJdbcDao(CustomerTitleJdbcDao customerTitleJdbcDao)
	{
		this.customerTitleJdbcDao = customerTitleJdbcDao;
	}
	
	@Autowired
	private VoucherJdbcDao voucherJdbcDao;
	
	public void setVoucherJdbcDao(VoucherJdbcDao voucherJdbcDao) {
		this.voucherJdbcDao = voucherJdbcDao;
	}
	
	@Autowired
	private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;
	
	/**
	 * @param customerRefundItemJdbcDao the customerRefundItemJdbcDao to set
	 */
	public void setCustomerRefundItemJdbcDao(
			CustomerRefundItemJdbcDao customerRefundItemJdbcDao) {
		this.customerRefundItemJdbcDao = customerRefundItemJdbcDao;
	}
	
	@Autowired
	private VendorTitleJdbcDao vendorTitleJdbcDao;

	public void setVendorTitleJdbcDao(VendorTitleJdbcDao vendorTitleJdbcDao)
	{
		this.vendorTitleJdbcDao = vendorTitleJdbcDao;
	}
	
	@Autowired
	private ReassignJdbcDao reassignJdbcDao;

	/**
	 * @param reassignJdbcDao
	 *            the reassignJdbcDao to set
	 */
	public void setReassignJdbcDao(ReassignJdbcDao reassignJdbcDao) {
		this.reassignJdbcDao = reassignJdbcDao;
	}
	
	@Autowired
	private ExportJdbcDao exportJdbcDao;
	
	public void setExportJdbcDao(ExportJdbcDao exportJdbcDao) {
		this.exportJdbcDao = exportJdbcDao;
	}
	
	@Autowired
	private SaleContractJdbcDAO saleContractJdbcDAO;
	
	public void setSaleContractJdbcDAO(SaleContractJdbcDAO saleContractJdbcDAO) {
		this.saleContractJdbcDAO = saleContractJdbcDAO;
	}
	
	public String getUserCompany(){
//		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
//		return this.sysDeptJdbcDao.getCompanyCodeByUserName(userContext.getUser().getUserName());
		return com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysDept().getCompanyCode();
	}
	
	public String getUserDept(){
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		return this.sysDeptJdbcDao.getDeptIdByUserName(userContext.getUser().getUserName());
	}
	
	public String getDeptCodeById(String deptId){
        return this.sysDeptJdbcDao.getDeptCodeById(deptId);
	}
	@Autowired
	protected VoucherHibernateDao voucherHibernateDao;

	public void setVoucherHibernateDao(VoucherHibernateDao voucherHibernateDao)
	{
		this.voucherHibernateDao = voucherHibernateDao;
	}
	/**
	 * 调用存储过程UPDATE_CUSTOMERTITLE
	 */
	public void updateCustomerTitle(final String customer){
		this.collectHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				CallableStatement cs = session.connection().prepareCall("{call UPDATE_CUSTOMERTITLE(?)}");    
				cs.setString(1, customer);
				boolean isSuccess = cs.execute();
				return isSuccess; 
			}
		});
	}
	
	/**
	 * 调用存储过程UPDATE_CUSTOMERTITLE
	 */
	public void updateCustomerTitle2(final String customer,final String collectid){
		this.collectHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				CallableStatement cs = session.connection().prepareCall("{call UPDATE_CUSTOMERTITLE2(?,?)}");    
				cs.setString(1, customer);
				cs.setString(2, collectid);
				boolean isSuccess = cs.execute();
				return isSuccess; 
			}
		});
	}
	
	/**
	 * 生成未明户收款及清帐凭证(有外币中转)
	 * @param importPayment
	 * @return
	 */
	public void genDiffUnnamerVoucher(Collect collect){
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());
		
		UnnamerCollect unnamerCollect =  this.unnamerCollectService._get(collect.getUnnamercollectid());
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "7");
		Voucher voucher = this.getVoucher(collect, "7");
		voucher.setCurrency(collect.getActcurrency());
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		//******************未名户行项目凭证行项目**********************************//
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//客户记账码
		voucherItem.setCheckcode("21");
		//特殊G/L标识
		voucherItem.setGlflag("");
		
		String Subjectdescribe = "";
		String subject = "";
		//科目
		if(collect.getCurrency().equals("CNY")){
			voucherItem.setSubject("0040000244");
			Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId("0040000244",bukrs);
			subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers("0040000244",bukrs);
		}else{
			voucherItem.setSubject("0050000208");
			Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId("0050000208",bukrs);
			subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers("0050000208",bukrs);
		}
		voucherItem.setSubjectdescribe(Subjectdescribe);
		subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
		//统驭项目
		voucherItem.setControlaccount(subject);
		//统驭科目说明
		voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
		
		//货币
		voucherItem.setCurrency(collect.getCurrency());
		//货币金额
		voucherItem.setAmount(unnamerCollect.getApplyamount());
		//本位币金额
		Set collectbankitems = collect.getCollectbankitem();
		for (Iterator<CollectBankItem> iter = collectbankitems.iterator(); iter.hasNext();)
		{
			CollectBankItem collectBankItem = (CollectBankItem) iter.next();
			voucherItem.setAmount2(collectBankItem.getCollectamount2());
		}

		//部门
		voucherItem.setDepid("2199");
		//文本
		voucherItem.setText(collect.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//借/贷标识符
		voucherItem.setDebitcredit("S");
		//行号
		voucherItem.setRownumber("2");
		
		voucherItemList.add(voucherItem);
		//******************未名户行项目凭证行项目**********************************//
		
		//************************应收中转科目**********************************//
		voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//客户记账码
		voucherItem.setCheckcode("50");
		//特殊G/L标识
		voucherItem.setGlflag("");
		String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs);
		//科目
		voucherItem.setSubject("1122999999");
		//科目说明
		voucherItem.setSubjectdescribe(subjectdescribe);
		//货币
		voucherItem.setCurrency(collect.getActcurrency());
		//货币金额
		voucherItem.setAmount(unnamerCollect.getApplyamount());
		//本位币金额
		for (Iterator<CollectBankItem> iter = collectbankitems.iterator(); iter.hasNext();)
		{
			CollectBankItem collectBankItem = (CollectBankItem) iter.next();
			voucherItem.setAmount2(collectBankItem.getCollectamount2());
		}
		//部门
		voucherItem.setDepid(deptid);
		//文本
		voucherItem.setText(collect.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//统驭项目
		voucherItem.setControlaccount("1122999999");
		//统驭科目说明
		voucherItem.setCaremark(subjectdescribe);
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//借/贷标识符
		voucherItem.setDebitcredit("H");
		//行号
		voucherItem.setRownumber("1");
		
		voucherItemList.add(voucherItem);
		
		voucher.setVoucherItem(voucherItemList);		
		
		List<Voucher> li = this.voucherJdbcDao.getVoucherList(collect.getCollectid(), "01","7");
		boolean f=false;
		if(li!=null && li.size()!=0){				
			for(Voucher vo : li){
				if(!StringUtils.isNullBlank(vo.getVoucherno())){
					voucher=vo;
					f=true;
					break;
				}
			} 
		}
//		如果已经生成凭证就不再保存
		if(!f){
			this.voucherService._save(voucher);
		}
		//************************应收中转科目**********************************//
		BigDecimal allAmount = new BigDecimal("0");
		BigDecimal allChangeAmount = new BigDecimal("0");
		
		Voucher voucher2 = this.getVoucher(collect, "7");
		Set<VoucherItem> voucherItemList2 = new HashSet<VoucherItem>();
		int rowNo = 1;
		//******************客户行项目凭证行项目**********************************//
		Iterator<CollectItem> CollectItemit = collect.getCollectitem() .iterator();		
		while(CollectItemit.hasNext()){
			CollectItem collectItem = CollectItemit.next();
			rowNo = rowNo + 1;
			voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher2);
			//客户记账码
			voucherItem.setCheckcode("11");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(collect.getCustomer());
			//科目说明
			voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs));
			//货币
			voucherItem.setCurrency(collect.getBillcurrency());
			//货币金额
			voucherItem.setAmount(collectItem.getAssignamount());
			allChangeAmount = allChangeAmount.add(collectItem.getAssignamount());
			//本位币金额
			voucherItem.setAmount2(collectItem.getAssignamount2());
			allAmount = allAmount.add(collectItem.getAssignamount2());
			//部门
			voucherItem.setDepid(deptid);
			//文本
			voucherItem.setText(collect.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			
			subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			//销售订单
			voucherItem.setSalesorder("");
			//销售订单行项目号
			voucherItem.setOrderrowno("");
			//利润中心
			voucherItem.setProfitcenter("");
			//成本中心
			voucherItem.setCostcenter("");
			//借/贷标识符
			voucherItem.setDebitcredit("H");
			//行号
			voucherItem.setRownumber(String.valueOf(rowNo));
			//收款行项目信息
			voucherItem.setBusinessitemid(collectItem.getCollectitemid());
			
			voucherItemList2.add(voucherItem);
		}
		//******************客户行项目凭证行项目**********************************//
		
		//************************应收中转科目**********************************//
		voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher2);
		//客户记账码
		voucherItem.setCheckcode("40");
		//特殊G/L标识
		voucherItem.setGlflag("");
		//科目
		voucherItem.setSubject("1122999999");
		//科目说明
		voucherItem.setSubjectdescribe(subjectdescribe);
		//货币
		voucherItem.setCurrency(collect.getBillcurrency());
		//货币金额
		voucherItem.setAmount(collect.getConvertamount());
		//本位币金额
		for (Iterator<CollectBankItem> iter = collectbankitems.iterator(); iter.hasNext();)
		{
			CollectBankItem collectBankItem = (CollectBankItem) iter.next();
			voucherItem.setAmount2(collectBankItem.getCollectamount2());
		}
		//部门
		voucherItem.setDepid(deptid);
		//文本
		voucherItem.setText(collect.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//统驭项目
		voucherItem.setControlaccount("1122999999");
		//统驭科目说明
		voucherItem.setCaremark(subjectdescribe);
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//借/贷标识符
		voucherItem.setDebitcredit("S");
		//行号
		voucherItem.setRownumber("1");
		
		voucherItemList2.add(voucherItem);
		//************************应收中转科目**********************************//
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(collect);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			setvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(collect);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		voucher2.setVoucherItem(voucherItemList2);		
		this.voucherService._save(voucher2);
		
		//******************未名户清帐凭证*******************************//
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "8");
		Voucher voucher3 = this.getClearVoucher(collect, "8");
		voucher3.setKonto("");
		voucher3.setGsber("2199");
		voucher3.setFlag("P");
		voucher3.setAgkoa("K");
		voucher3.setCurrency(unnamerCollect.getCurrency());
		if(collect.getCurrency().equals("CNY")){
			voucher3.setAgkon("0040000244");
		}else{
			voucher3.setAgkon("0050000208");
		}
		
		//凭证行项目
		Set<VoucherItem> voucherItemList3 = new HashSet<VoucherItem>();
		rowNo = 0;
		
		BigDecimal collectAmount = new BigDecimal("0");
		BigDecimal claimAmount = new BigDecimal("0");
		
		//取得未名户收款的凭证
		List<VoucherItem>  voucheritemList = this.voucherItemJdbcDao.getVoucherItemList(unnamerCollect.getUnnamercollectid(),"07");
		for (Iterator<VoucherItem> iter = voucheritemList.iterator(); iter.hasNext();)
		{
			voucherItem = (VoucherItem) iter.next();
			if(voucherItem.getCheckcode().equals("31")){
				collectAmount = collectAmount.add(voucherItem.getAmount2());
			}
		}
		
		collectbankitems = collect.getCollectbankitem();
		for (Iterator<CollectBankItem> iter = collectbankitems.iterator(); iter.hasNext();)
		{
			CollectBankItem collectBankItem = (CollectBankItem) iter.next();
			claimAmount = collectBankItem.getCollectamount2();
		}
		
		if(collectAmount.compareTo(claimAmount)!=0){
			rowNo = rowNo + 1;
		    voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher3);
			if(collectAmount.compareTo(claimAmount)==1){
				voucherItem.setCheckcode("25");
				voucherItem.setDebitcredit("S");
			}else{
				voucherItem.setCheckcode("38");
				voucherItem.setDebitcredit("H");
			}
			//科目
			if(collect.getCurrency().equals("CNY")){
				voucherItem.setSubject("0040000244");
				Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId("0040000244",bukrs);
				subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers("0040000244",bukrs);
			}else{
				voucherItem.setSubject("0050000208");
				Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId("0050000208",bukrs);
				subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers("0050000208",bukrs);
			}
			voucherItem.setSubjectdescribe(Subjectdescribe);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			
			voucherItem.setCurrency(collect.getCurrency());
			voucherItem.setAmount(new BigDecimal("0"));
			voucherItem.setAmount2(collectAmount.subtract(claimAmount).abs());
			voucherItem.setDepid("2199");
			voucherItem.setRownumber(String.valueOf(rowNo));
			voucherItem.setText("清帐凭证");
			voucherItemList3.add(voucherItem);
			
			rowNo = rowNo + 1;
			voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher3);
			
			String salesorder = "";
			Iterator<CollectItem> CollectItemit1 = collect.getCollectitem().iterator();		
			while(CollectItemit1.hasNext()){
				CollectItem collectItem = CollectItemit1.next();
				if(!StringUtils.isNullBlank(collectItem.getContract_no())){
					salesorder = this.collectCbillJdbcDao.getSapOrderNo(collectItem.getContract_no());
					break;
				}
			}
			
			if(collectAmount.compareTo(claimAmount)==1){
				voucherItem.setCheckcode("50");
				voucherItem.setDebitcredit("H");
				voucherItem.setSalesorder(salesorder);
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050302",bukrs);
				//科目
				voucherItem.setSubject("6603050302");
				//科目说明
				voucherItem.setSubjectdescribe(subjectname);
				//统驭项目
				voucherItem.setControlaccount("6603050302");
				//统驭科目说明
				voucherItem.setCaremark(subjectname);
			}else{
				voucherItem.setCheckcode("40");
				voucherItem.setDebitcredit("S");
				voucherItem.setSalesorder(salesorder);
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050301",bukrs);
				//科目
				voucherItem.setSubject("6603050301");
				//科目说明
				voucherItem.setSubjectdescribe(subjectname);
				//统驭项目
				voucherItem.setControlaccount("6603050301");
				//统驭科目说明
				voucherItem.setCaremark(subjectname);
			}
			voucherItem.setCurrency(collect.getCurrency());
			voucherItem.setAmount(new BigDecimal("0"));
			voucherItem.setAmount2(collectAmount.subtract(claimAmount).abs());
			voucherItem.setDepid("2199");
			voucherItem.setRownumber(String.valueOf(rowNo));
			voucherItem.setText("清帐凭证");
			voucherItemList3.add(voucherItem);
		}
		
		Set voucheritems = voucher.getVoucherItem();
		for (Iterator<VoucherItem> iter = voucheritems.iterator(); iter.hasNext();)
		{
			voucherItem = (VoucherItem) iter.next();
			if(voucherItem.getCheckcode().equals("21")){
				VoucherItem _voucherItem = new VoucherItem();
				_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
				_voucherItem.setRownumber(voucherItem.getRownumber());
				_voucherItem.setVoucherno(voucherItem.getVoucher().getVoucherno());
				_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
				_voucherItem.setFlag("P");
				_voucherItem.setVoucher(voucher3);
				_voucherItem.setAmount(BigDecimal.valueOf(0));
				_voucherItem.setAmount2(BigDecimal.valueOf(0));
				
				voucherItemList3.add(_voucherItem);
			}
		}
		
		for (Iterator<VoucherItem> iter = voucheritemList.iterator(); iter.hasNext();)
		{
			voucherItem = (VoucherItem) iter.next();
			if(voucherItem.getCheckcode().equals("31")){
				VoucherItem _voucherItem = new VoucherItem();
				_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
				_voucherItem.setRownumber(voucherItem.getRownumber());
				_voucherItem.setVoucherno(voucherItem.getVoucher().getVoucherno());
				_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
				_voucherItem.setFlag("P");
				_voucherItem.setVoucher(voucher3);
				_voucherItem.setAmount(BigDecimal.valueOf(0));
				_voucherItem.setAmount2(BigDecimal.valueOf(0));
				
				voucherItemList3.add(_voucherItem);
			}
		}
		
		voucher3.setVoucherItem(voucherItemList3);		
		this.voucherService._save(voucher3);
		
	}
	
	/**
	 * 生成未明户收款及清帐凭证
	 * @param importPayment
	 * @return
	 */
	public void genUnnamerVoucher(Collect collect){
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());
		
		UnnamerCollect unnamerCollect =  this.unnamerCollectService._get(collect.getUnnamercollectid());
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "7");
		Voucher voucher = this.getVoucher(collect, "7");
		
		BigDecimal allAmount = new BigDecimal("0");
		BigDecimal allChangeAmount = new BigDecimal("0");
		
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		int rowNo = 0;
		//******************客户行项目凭证行项目**********************************//
		Iterator<CollectItem> CollectItemit = collect.getCollectitem() .iterator();		
		while(CollectItemit.hasNext()){
			rowNo = rowNo + 1;
			CollectItem collectItem = CollectItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("11");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(collect.getCustomer());
			//科目说明
			voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs));
			//货币
			voucherItem.setCurrency(collect.getBillcurrency());
			//货币金额
			voucherItem.setAmount(collectItem.getAssignamount());
			allChangeAmount = allChangeAmount.add(collectItem.getAssignamount());
			//本位币金额
			voucherItem.setAmount2(collectItem.getAssignamount2());
			allAmount = allAmount.add(collectItem.getAssignamount2());
			//部门
			voucherItem.setDepid(deptid);
			//文本
			voucherItem.setText(collect.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			//销售订单
			voucherItem.setSalesorder("");
			//销售订单行项目号
			voucherItem.setOrderrowno("");
			//利润中心
			voucherItem.setProfitcenter("");
			//成本中心
			voucherItem.setCostcenter("");
			//借/贷标识符
			voucherItem.setDebitcredit("H");
			//行号
			voucherItem.setRownumber(String.valueOf(rowNo));
			//收款行项目信息
			voucherItem.setBusinessitemid(collectItem.getCollectitemid());
			
			voucherItemList.add(voucherItem);
		}
		//******************客户行项目凭证行项目**********************************//
		
		//******************未名户行项目凭证行项目**********************************//
		rowNo = rowNo + 1;
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//客户记账码
		voucherItem.setCheckcode("21");
		//特殊G/L标识
		voucherItem.setGlflag("");
		String Subjectdescribe = "";
		String subject = "";
		//科目
		if(collect.getCurrency().equals("CNY")){
			voucherItem.setSubject("0040000244");
			Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId("0040000244",bukrs);
			subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers("0040000244",bukrs);
		}else{
			voucherItem.setSubject("0050000208");
			Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId("0050000208",bukrs);
			subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers("0050000208",bukrs);
		}
		voucherItem.setSubjectdescribe(Subjectdescribe);
		subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
		//统驭项目
		voucherItem.setControlaccount(subject);
		//统驭科目说明
		voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
		//货币
		voucherItem.setCurrency(collect.getCurrency());
		//货币金额
		voucherItem.setAmount(unnamerCollect.getApplyamount());
		allChangeAmount = allChangeAmount.add(unnamerCollect.getApplyamount());
		//本位币金额
		Set collectbankitems = collect.getCollectbankitem();
		for (Iterator<CollectBankItem> iter = collectbankitems.iterator(); iter.hasNext();)
		{
			CollectBankItem collectBankItem = (CollectBankItem) iter.next();
			voucherItem.setAmount2(collectBankItem.getCollectamount2());
			allAmount = allAmount.add(collectBankItem.getCollectamount2());
		}

		//部门
		voucherItem.setDepid("2199");
		//文本
		voucherItem.setText(collect.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//借/贷标识符
		voucherItem.setDebitcredit("S");
		//行号
		voucherItem.setRownumber(String.valueOf(rowNo));
		
		voucherItemList.add(voucherItem);
		//******************未名户行项目凭证行项目**********************************//
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(collect);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			setvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(collect);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		voucher.setVoucherItem(voucherItemList);		
		List<Voucher> li = this.voucherJdbcDao.getVoucherList(collect.getCollectid(), "01","7");
		boolean f=false;
		if(li!=null && li.size()!=0){				
			for(Voucher vo : li){
				if(!StringUtils.isNullBlank(vo.getVoucherno())){
					voucher=vo;
					f=true;
					break;
				}
			} 
		}
//		如果已经生成凭证就不再保存
		if(!f){
			this.voucherService._save(voucher);
		}
		
//		//******************汇兑损益科目号行项目凭证行项目*******************************//
//		if(!collect.getBillcurrency().equals(collect.getCurrency())){
//			if (collect.getSettleSubject() != null){
//				if (collect.getSettleSubject().getDebtcredit1() != null && 
//					"S".equals(collect.getSettleSubject().getDebtcredit1())){
//					allAmount = allAmount.add(collect.getSettleSubject().getStandardamount1());
//				}
//		
//				if (collect.getSettleSubject().getDebtcredit1() != null &&
//					"H".equals(collect.getSettleSubject().getDebtcredit1())){
//					allAmount = allAmount.subtract(collect.getSettleSubject().getStandardamount1());
//				}
//				
//				if (collect.getSettleSubject().getDebtcredit2() != null &&
//					"S".equals(collect.getSettleSubject().getDebtcredit2())){
//					allAmount = allAmount.add(collect.getSettleSubject().getStandardamount2());
//				}
//		
//				if (collect.getSettleSubject().getDebtcredit2() != null &&
//					"H".equals(collect.getSettleSubject().getDebtcredit2())){
//					allAmount = allAmount.subtract(collect.getSettleSubject().getStandardamount2());
//				}
//				
//				if (collect.getSettleSubject().getDebtcredit3() != null &&
//					"S".equals(collect.getSettleSubject().getDebtcredit3())){
//					allAmount = allAmount.add(collect.getSettleSubject().getStandardamount3());
//				}
//		
//				if (collect.getSettleSubject().getDebtcredit3() != null &&
//					"H".equals(collect.getSettleSubject().getDebtcredit3())){
//					allAmount = allAmount.subtract(collect.getSettleSubject().getStandardamount3());
//				}
//				
//				if (collect.getSettleSubject().getDebtcredit4() != null && 
//					"S".equals(collect.getSettleSubject().getDebtcredit4())){
//					allAmount = allAmount.add(collect.getSettleSubject().getStandardamount4());
//				}
//		
//				if (collect.getSettleSubject().getDebtcredit4() != null && 
//					"H".equals(collect.getSettleSubject().getDebtcredit4())){
//					allAmount = allAmount.subtract(collect.getSettleSubject().getStandardamount4());
//				}
//			}
//			
//			if (collect.getFundFlow() != null){
//				if (collect.getFundFlow().getDebtcredit1() != null  &&
//					"S".equals(collect.getFundFlow().getDebtcredit1())){
//					allAmount = allAmount.add(collect.getFundFlow().getStandardamount1());
//				}
//		
//				if (collect.getFundFlow().getDebtcredit1() != null  &&
//					"H".equals(collect.getFundFlow().getDebtcredit1())){
//					allAmount = allAmount.subtract(collect.getFundFlow().getStandardamount1());
//				}
//				
//				if (collect.getFundFlow().getDebtcredit2() != null  &&
//					"S".equals(collect.getFundFlow().getDebtcredit2())){
//					allAmount = allAmount.add(collect.getFundFlow().getStandardamount2());
//				}
//		
//				if ("H".equals(collect.getFundFlow().getDebtcredit2())){
//					allAmount = allAmount.subtract(collect.getFundFlow().getStandardamount2());
//				}
//				
//				if (collect.getFundFlow().getDebtcredit3() != null  &&
//					"S".equals(collect.getFundFlow().getDebtcredit3())){
//					allAmount = allAmount.add(collect.getFundFlow().getStandardamount3());
//				}
//		
//				if (collect.getFundFlow().getDebtcredit3() != null  &&
//					"H".equals(collect.getFundFlow().getDebtcredit3())){
//					allAmount = allAmount.subtract(collect.getFundFlow().getStandardamount3());
//				}
//			}
//			//******************汇兑损益科目号行项目凭证行项目*******************************//
//			if (allAmount.signum() == 1){
//				rowNo = rowNo + 1;
//				VoucherItem profLossVoucherItem = new VoucherItem();
//				profLossVoucherItem.setVoucher(voucher);
//				
//				profLossVoucherItem.setCheckcode("50");
//				//特殊G/L标识
//				profLossVoucherItem.setGlflag("");
//				//科目
//				profLossVoucherItem.setSubject("6603050302");
//				//科目说明
//				profLossVoucherItem.setSubjectdescribe("收益科目");
//				//货币
//				profLossVoucherItem.setCurrency(collect.getCurrency());
//				//货币金额
//				profLossVoucherItem.setAmount(new BigDecimal("0"));
//				//本位币金额
//				profLossVoucherItem.setAmount2(allAmount.abs());
//				//部门
//				profLossVoucherItem.setDepid(deptid);
//				//文本
//				profLossVoucherItem.setText(collect.getText());
//				//现金流项目
//				profLossVoucherItem.setCashflowitem("");
//				//反记账标识
//				profLossVoucherItem.setUncheckflag("");
//				//统驭项目
//				profLossVoucherItem.setControlaccount("");
//				//统驭科目说明
//				profLossVoucherItem.setCaremark("");
//				//销售订单
//				profLossVoucherItem.setSalesorder("");
//				//销售订单行项目号
//				profLossVoucherItem.setOrderrowno("");
//				//利润中心
//				profLossVoucherItem.setProfitcenter("");
//				//成本中心
//				profLossVoucherItem.setCostcenter("");
//				//借/贷标识符
//				profLossVoucherItem.setDebitcredit("S");
//				//行号
//				profLossVoucherItem.setRownumber(String.valueOf(rowNo));
//				
//				voucherItemList.add(profLossVoucherItem);
//			}
//			if (allAmount.signum() == -1){
//				rowNo = rowNo + 1;
//				VoucherItem profLossVoucherItem = new VoucherItem();
//				profLossVoucherItem.setVoucher(voucher);
//				
//				profLossVoucherItem.setCheckcode("40");
//				
//				//特殊G/L标识
//				profLossVoucherItem.setGlflag("");
//				//科目
//				profLossVoucherItem.setSubject("6603050301");
//				//科目说明
//				profLossVoucherItem.setSubjectdescribe("损失科目");
//				//货币
//				profLossVoucherItem.setCurrency(collect.getCurrency());
//				//货币金额
//				profLossVoucherItem.setAmount(new BigDecimal("0"));
//				//本位币金额
//				profLossVoucherItem.setAmount2(allAmount.abs());
//				//部门
//				profLossVoucherItem.setDepid(deptid);
//				//文本
//				profLossVoucherItem.setText(collect.getText());
//				//现金流项目
//				profLossVoucherItem.setCashflowitem("");
//				//反记账标识
//				profLossVoucherItem.setUncheckflag("");
//				//统驭项目
//				profLossVoucherItem.setControlaccount("");
//				//统驭科目说明
//				profLossVoucherItem.setCaremark("");
//				//销售订单
//				profLossVoucherItem.setSalesorder("");
//				//销售订单行项目号
//				profLossVoucherItem.setOrderrowno("");
//				//利润中心
//				profLossVoucherItem.setProfitcenter("");
//				//成本中心
//				profLossVoucherItem.setCostcenter("");
//				//借/贷标识符
//				profLossVoucherItem.setDebitcredit("S");
//				//行号
//				profLossVoucherItem.setRownumber(String.valueOf(rowNo));
//				
//				voucherItemList.add(profLossVoucherItem);
//			}
//		}
//		
//		//******************汇兑损益科目号行项目凭证行项目*******************************//
		
		//******************未名户清帐凭证*******************************//
		
		
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "8");
		Voucher voucher2 = this.getClearVoucher(collect, "8");
		voucher2.setGsber("2199");
		voucher2.setFlag("P");
		voucher2.setAgkoa("K");
		voucher2.setCurrency(unnamerCollect.getCurrency());
		if(collect.getCurrency().equals("CNY")){
			voucher2.setAgkon("0040000244");
		}else{
			voucher2.setAgkon("0050000208");
		}
		
		//凭证行项目
		Set<VoucherItem> voucherItemList2 = new HashSet<VoucherItem>();
		rowNo = 0;
		BigDecimal amount2 = new BigDecimal("0");
	
		Set voucheritems = voucher.getVoucherItem();		
		
		for (Iterator<VoucherItem> iter = voucheritems.iterator(); iter.hasNext();)
		{
			voucherItem = (VoucherItem) iter.next();
			if(voucherItem.getCheckcode().equals("21")){
				VoucherItem _voucherItem = new VoucherItem();
				_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
				_voucherItem.setRownumber(voucherItem.getRownumber());
				_voucherItem.setVoucherno(voucherItem.getVoucher().getVoucherno());
				_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
				_voucherItem.setFlag("P");
				_voucherItem.setVoucher(voucher2);
				_voucherItem.setAmount(BigDecimal.valueOf(0));
				_voucherItem.setAmount2(BigDecimal.valueOf(0));
				amount2 = amount2.add(voucherItem.getAmount2());
				voucherItemList2.add(_voucherItem);
			}
		}
		BigDecimal unnameAmount = new BigDecimal("0");
		//取得未名户收款的凭证
		List<VoucherItem>  voucheritemList = this.voucherItemJdbcDao.getVoucherItemList(unnamerCollect.getUnnamercollectid(),"07");
		for (Iterator<VoucherItem> iter = voucheritemList.iterator(); iter.hasNext();)
		{
			voucherItem = (VoucherItem) iter.next();
			if(voucherItem.getCheckcode().equals("31")){
				VoucherItem _voucherItem = new VoucherItem();
				_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
				_voucherItem.setRownumber(voucherItem.getRownumber());
				_voucherItem.setVoucherno(voucherItem.getVoucher().getVoucherno());
				_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
				_voucherItem.setFlag("P");
				_voucherItem.setVoucher(voucher2);
				_voucherItem.setAmount(BigDecimal.valueOf(0));
				_voucherItem.setAmount2(BigDecimal.valueOf(0));
				unnameAmount = unnameAmount.add(voucherItem.getAmount2());
				voucherItemList2.add(_voucherItem);
			}
		}
		
		BigDecimal collectAmount = unnamerCollect.getApplyamount2();
		BigDecimal claimAmount = new BigDecimal("0");
		
		collectbankitems = collect.getCollectbankitem();
		for (Iterator<CollectBankItem> iter = collectbankitems.iterator(); iter.hasNext();)
		{
			CollectBankItem collectBankItem = (CollectBankItem) iter.next();
			claimAmount = collectBankItem.getCollectamount2();
		}
		
		if(amount2.compareTo(unnameAmount)!=0){
			rowNo = rowNo + 1;
		    voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher2);
			if(amount2.compareTo(unnameAmount)==1){
				voucherItem.setCheckcode("38");
				voucherItem.setDebitcredit("H");
			}else{
				voucherItem.setCheckcode("25");
				voucherItem.setDebitcredit("S");
			}
			
			//科目
			if(collect.getCurrency().equals("CNY")){
				voucherItem.setSubject("0040000244");
				Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId("0040000244",bukrs);
				subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers("0040000244",bukrs);
			}else{
				voucherItem.setSubject("0050000208");
				Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId("0050000208",bukrs);
				subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers("0050000208",bukrs);
			}
			voucherItem.setSubjectdescribe(Subjectdescribe);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			
			voucherItem.setCurrency(collect.getCurrency());
			voucherItem.setAmount(new BigDecimal("0"));
			voucherItem.setAmount2(amount2.subtract(unnameAmount).abs());
			voucherItem.setDepid("2199");
			voucherItem.setText("清帐凭证");
			voucherItemList2.add(voucherItem);
			
			rowNo = rowNo + 1;
			voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher2);
			
			String salesorder = "";
			Iterator<CollectItem> CollectItemit1 = collect.getCollectitem().iterator();		
			while(CollectItemit1.hasNext()){
				CollectItem collectItem = CollectItemit1.next();
				if(!StringUtils.isNullBlank(collectItem.getContract_no())){
					salesorder = this.collectCbillJdbcDao.getSapOrderNo(collectItem.getContract_no());
					break;
				}
			}
			
			if(amount2.compareTo(unnameAmount)==1){
				voucherItem.setCheckcode("40");
				voucherItem.setDebitcredit("S");
				voucherItem.setSalesorder(salesorder);
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050301",bukrs);
				//科目
				voucherItem.setSubject("6603050301");
				//科目说明
				voucherItem.setSubjectdescribe(subjectname);
				//统驭项目
				voucherItem.setControlaccount("6603050301");
				//统驭科目说明
				voucherItem.setCaremark(subjectname);
			}else{
				voucherItem.setCheckcode("50");
				voucherItem.setDebitcredit("H");
				voucherItem.setSalesorder(salesorder);
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050302",bukrs);
				//科目
				voucherItem.setSubject("6603050302");
				//科目说明
				voucherItem.setSubjectdescribe(subjectname);
				//统驭项目
				voucherItem.setControlaccount("6603050302");
				//统驭科目说明
				voucherItem.setCaremark(subjectname);
			}
			voucherItem.setCurrency(collect.getCurrency());
			voucherItem.setAmount(new BigDecimal("0"));
			voucherItem.setAmount2(amount2.subtract(unnameAmount).abs());
			voucherItem.setDepid("2199");
			voucherItem.setText("清帐凭证");
			voucherItemList2.add(voucherItem);
		}
		
		
		
		
		voucher2.setVoucherItem(voucherItemList2);		
		this.voucherService._save(voucher2);
	}

	/**
	 * 生成保证金转货款调整金额凭证
	 * @param importPayment
	 * @return
	 */
	public void genDepositVoucher(Collect collect){
		SettleSubject settleSubject = collect.getSettleSubject();
		
		FundFlow flowPay = collect.getFundFlow();
		Boolean needAdjust = false;
		if (null != settleSubject){
			if (settleSubject.getAmount1().abs().compareTo(new BigDecimal(0)) == 1
				||settleSubject.getAmount2().abs().compareTo(new BigDecimal(0)) == 1
				||settleSubject.getAmount3().abs().compareTo(new BigDecimal(0)) == 1
				||settleSubject.getAmount4().abs().compareTo(new BigDecimal(0)) == 1){
				
				needAdjust = true;
			}
		}
		
		if (null != flowPay){
			if (flowPay.getAmount1().abs().compareTo(new BigDecimal(0)) == 1
				||flowPay.getAmount2().abs().compareTo(new BigDecimal(0)) == 1
				||flowPay.getAmount3().abs().compareTo(new BigDecimal(0)) == 1){
				
				needAdjust = true;
			}
		}
		Voucher voucher = new Voucher();
		if(needAdjust){
			String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
			//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "11");
			voucher = getVoucher(collect,"11");
			Set<VoucherItem> voucherItems = new HashSet<VoucherItem>();
			
			int rowNo = 0;
			BigDecimal amount = new BigDecimal("0");
			BigDecimal amount2 = new BigDecimal("0");
			
			//******************结算科目行项目凭证行项目*******************************//
			Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
			
			SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(collect);

			Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
			while(Settlesubjectit.hasNext()){
				rowNo = rowNo + 1;
				VoucherItem setvoucherItem =Settlesubjectit.next();
				setvoucherItem.setRownumber(String.valueOf(rowNo));
				setvoucherItem.setVoucher(voucher);
				if (setvoucherItem.getDebitcredit().equals("S")){
					amount = amount.add(setvoucherItem.getAmount());
					amount2 = amount2.add(setvoucherItem.getAmount2());
				}
				
				if (setvoucherItem.getDebitcredit().equals("H")){
					amount = amount.subtract(setvoucherItem.getAmount());
					amount2 = amount2.subtract(setvoucherItem.getAmount2());
				}
				voucherItems.add(setvoucherItem);
			}
			//******************结算科目行项目凭证行项目*******************************//
			
			//******************特殊总帐行项目凭证行项目*******************************//
			//结算科目凭证行项目
			Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
			
			FundFlowVoucherItemList = this.getFundFlowVoucherItem(collect);
			
			Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
			while(FundFlowit.hasNext()){
				rowNo = rowNo + 1;
				VoucherItem funvoucherItem =FundFlowit.next();
				funvoucherItem.setRownumber(String.valueOf(rowNo));
				funvoucherItem.setVoucher(voucher);
				if (funvoucherItem.getDebitcredit().equals("S")){
					amount = amount.add(funvoucherItem.getAmount());
					amount2 = amount2.add(funvoucherItem.getAmount2());
				}
				
				if (funvoucherItem.getDebitcredit().equals("H")){
					amount = amount.subtract(funvoucherItem.getAmount());
					amount2 = amount2.subtract(funvoucherItem.getAmount2());
				}
				voucherItems.add(funvoucherItem);
			}
			//******************特殊总帐行项目凭证行项目*******************************//
			
			if(amount.abs().compareTo(new BigDecimal(0)) != 0){
				VoucherItem voucherItem = new VoucherItem();
				voucherItem.setVoucher(voucher);
				
				if(amount.abs().compareTo(new BigDecimal(0)) == 1){
					//客户记账码
					voucherItem.setCheckcode("01");
					//借/贷标识符
					voucherItem.setDebitcredit("S");
				}else{
					//客户记账码
					voucherItem.setCheckcode("11");
					//借/贷标识符
					voucherItem.setDebitcredit("H");
				}

				//特殊G/L标识
				voucherItem.setGlflag("");
				//科目
				voucherItem.setSubject(collect.getCustomer());
				String bukrs = getCompanyIDByDeptID(collect.getDept_id());
				String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs);
				voucherItem.setSubjectdescribe(Subjectdescribe);
				//货币
				voucherItem.setCurrency(collect.getCurrency());
				//货币金额
				voucherItem.setAmount(amount.abs());
				//本位币金额
				voucherItem.setAmount2(amount2.abs());
				//部门
				voucherItem.setDepid(deptid);
				//文本
				voucherItem.setText(collect.getText());
				//现金流项目
				voucherItem.setCashflowitem("");
				//反记账标识
				voucherItem.setUncheckflag("");
				String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
				subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
				//统驭项目
				voucherItem.setControlaccount(subject);
				//统驭科目说明
				voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
				//销售订单
				voucherItem.setSalesorder("");
				//销售订单行项目号
				voucherItem.setOrderrowno("");
				//利润中心
				voucherItem.setProfitcenter("");
				//成本中心
				voucherItem.setCostcenter("");
				//行号
				voucherItem.setRownumber("1");
				
				voucherItems.add(voucherItem);
				voucher.setVoucherItem(voucherItems);
				//统一保存
				this.voucherService._save(voucher);
			}
		}
		
		//******************生成保证金转货款清帐凭证***********************//
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "12");
		Voucher clearVoucher = new Voucher();
		
		//凭证抬头
		Set<VoucherItem> voucherItems = new HashSet<VoucherItem>();
		if(this.ifGenDepositClearVoucher(collect)){
			clearVoucher = getClearVoucher(collect,"12");
			
			if(needAdjust){
				//******************调整***********************//
				VoucherItem voucherItem = new VoucherItem();
				voucherItem.setVoucher(clearVoucher);
				
				voucherItem.setBusvoucherid(voucher.getVoucherid());
				voucherItem.setRownumber("1");
				voucherItem.setFiyear(voucher.getFiyear());
				voucherItem.setFlag("R");
				voucherItem.setAmount(BigDecimal.valueOf(0));
				voucherItem.setAmount2(BigDecimal.valueOf(0));
				voucherItems.add(voucherItem);
			}
			
			Voucher collectVoucher = this.voucherService.getVoucherByBusinessId(collect.getOldcollectid(), "1");
			
			Set colvoucheritems = collectVoucher.getVoucherItem();
			for (Iterator<VoucherItem> iter = colvoucheritems.iterator(); iter.hasNext();)
			{
				VoucherItem colvoucherItem = (VoucherItem) iter.next();
				if(colvoucherItem.getBusinessitemid().equals(collect.getOldcollectitemid())){
					VoucherItem _voucherItem = new VoucherItem();
					_voucherItem.setBusvoucherid(colvoucherItem.getVoucher().getVoucherid());
					_voucherItem.setRownumber(colvoucherItem.getRownumber());
					_voucherItem.setVoucherno(colvoucherItem.getVoucher().getVoucherno());
					_voucherItem.setFiyear(colvoucherItem.getVoucher().getFiyear());
					_voucherItem.setFlag("R");
					_voucherItem.setVoucher(clearVoucher);
					_voucherItem.setAmount(BigDecimal.valueOf(0));
					_voucherItem.setAmount2(BigDecimal.valueOf(0));
					
					voucherItems.add(_voucherItem);
					break;
				}
			}
			
			//******************票和损益***********************//
			Collect _collect = this._get(collect.getOldcollectid());
			
			BigDecimal allChangeAmount = _collect.getApplyamount();
			//得到分配项的本位币之和
			BigDecimal itemValue = new BigDecimal("0");
			//得到票的本位币之和
			BigDecimal billValue = new BigDecimal("0");
			//分配项之和和票之和的差
			BigDecimal subtractVlaue = new BigDecimal("0");
			
			Set<VoucherItem> SettlesubjectVoucherItemList1 = new HashSet<VoucherItem>();
			
			SettlesubjectVoucherItemList1 = this.getSettSubjVoucherItem(collect);
			
			Iterator<VoucherItem> Settlesubjectit1 = SettlesubjectVoucherItemList1.iterator();		
			while(Settlesubjectit1.hasNext()){
				VoucherItem setvoucherItem =Settlesubjectit1.next();
				
				if (setvoucherItem.getDebitcredit().equals("S")){
					allChangeAmount = allChangeAmount.add(setvoucherItem.getAmount());
					itemValue = itemValue.add(setvoucherItem.getAmount2());
				}
				
				if (setvoucherItem.getDebitcredit().equals("H")){
					allChangeAmount = allChangeAmount.subtract(setvoucherItem.getAmount());
					itemValue = itemValue.subtract(setvoucherItem.getAmount2());
				}
			}
			
			Set<VoucherItem> FundFlowVoucherItemList1 = new HashSet<VoucherItem>();
			
			FundFlowVoucherItemList1 = this.getFundFlowVoucherItem(collect);
			
			Iterator<VoucherItem> FundFlowit1 = FundFlowVoucherItemList1.iterator();		
			while(FundFlowit1.hasNext()){
				VoucherItem funvoucherItem =FundFlowit1.next();
				
				if (funvoucherItem.getDebitcredit().equals("S")){
					allChangeAmount = allChangeAmount.add(funvoucherItem.getAmount());
					itemValue = itemValue.add(funvoucherItem.getAmount2());
				}
				
				if (funvoucherItem.getDebitcredit().equals("H")){
					allChangeAmount = allChangeAmount.subtract(funvoucherItem.getAmount());
					itemValue = itemValue.subtract(funvoucherItem.getAmount2());
				}
			}
			
			Set collectitems = _collect.getCollectitem();
			for (Iterator<CollectItem> iter = collectitems.iterator(); iter.hasNext();)
			{
				CollectItem collectItem = (CollectItem) iter.next();
				itemValue.add(collectItem.getAssignamount2());
			}
			
			Set collectcbills = _collect.getCollectcbill();
			for (Iterator<CollectCbill> iter = collectcbills.iterator(); iter.hasNext();)
			{
				CollectCbill collectCbill = (CollectCbill) iter.next();
				CustomerTitle customerTitle = this.customerTitleJdbcDao.getByInvoice(collectCbill.getBillno());
				
				VoucherItem _voucherItem = new VoucherItem();
				_voucherItem.setVoucher(clearVoucher);
				_voucherItem.setRownumber(customerTitle.getBuzei());
				_voucherItem.setVoucherno(customerTitle.getBelnr());
				_voucherItem.setFiyear(customerTitle.getGjahr());
				_voucherItem.setFlag("R");
				_voucherItem.setAmount(BigDecimal.valueOf(0));
				_voucherItem.setAmount2(BigDecimal.valueOf(0));
				voucherItems.add(_voucherItem);
				
				billValue = billValue.add(customerTitle.getBwbje());
			}
			
			List collectList = this.collectHibernateDao.getCollectListByOldcollectid(collect.getOldcollectid());
			
			for(int i=0;i<collectList.size();i++){
				Collect collect1 = (Collect)collectList.get(i);
				collectitems = collect1.getCollectitem();
				for (Iterator<CollectItem> iter = collectitems.iterator(); iter.hasNext();)
				{
					CollectItem collectItem = (CollectItem) iter.next();
					itemValue.add(collectItem.getAssignamount2());
				}
				
				collectcbills = collect1.getCollectcbill();
				for (Iterator<CollectCbill> iter = collectcbills.iterator(); iter.hasNext();)
				{
					CollectCbill collectCbill = (CollectCbill) iter.next();
					CustomerTitle customerTitle = this.customerTitleJdbcDao.getByInvoice(collectCbill.getBillno());
					
					VoucherItem _voucherItem = new VoucherItem();
					_voucherItem.setVoucher(clearVoucher);
					_voucherItem.setRownumber(customerTitle.getBuzei());
					_voucherItem.setVoucherno(customerTitle.getBelnr());
					_voucherItem.setFiyear(customerTitle.getGjahr());
					_voucherItem.setFlag("R");
					_voucherItem.setAmount(BigDecimal.valueOf(0));
					_voucherItem.setAmount2(BigDecimal.valueOf(0));
					voucherItems.add(_voucherItem);
					
					billValue = billValue.add(customerTitle.getBwbje());
				}
			}
			
			if (!collect.getCurrency().equals("CNY")){
				subtractVlaue = billValue.subtract(itemValue);
				
				if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
					VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(
							collect,subtractVlaue,"1");
					supplierVoucherItem.setVoucher(clearVoucher);
					voucherItems.add(supplierVoucherItem);
					
					VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(
							collect,subtractVlaue,"2");
					profOrLossVoucherItem.setVoucher(clearVoucher);
					voucherItems.add(profOrLossVoucherItem);
				}
			}
			
			clearVoucher.setVoucherItem(voucherItems);
			//统一保存
			this.voucherService._save(clearVoucher);
		}
	}
	/**
	 * 生成保证金转货款调整金额凭证
	 * @param importPayment
	 * @return
	 */
	public void genDepositClearVoucher(Collect collect){
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "12");
		Voucher voucher = new Voucher();
		
		//凭证抬头
		Set<VoucherItem> voucherItems = new HashSet<VoucherItem>();
		if(this.ifGenDepositClearVoucher(collect)){
			voucher = getClearVoucher(collect,"12");
			
			Collect _collect = this._get(collect.getOldcollectid());
			
			BigDecimal allChangeAmount = _collect.getApplyamount();
			//得到分配项的本位币之和
			BigDecimal itemValue = new BigDecimal("0");
			//得到票的本位币之和
			BigDecimal billValue = new BigDecimal("0");
			//分配项之和和票之和的差
			BigDecimal subtractVlaue = new BigDecimal("0");
			
			Set<VoucherItem> SettlesubjectVoucherItemList1 = new HashSet<VoucherItem>();
			
			SettlesubjectVoucherItemList1 = this.getSettSubjVoucherItem(collect);
			
			Iterator<VoucherItem> Settlesubjectit1 = SettlesubjectVoucherItemList1.iterator();		
			while(Settlesubjectit1.hasNext()){
				VoucherItem setvoucherItem =Settlesubjectit1.next();
				
				if (setvoucherItem.getDebitcredit().equals("S")){
					allChangeAmount = allChangeAmount.add(setvoucherItem.getAmount());
					itemValue = itemValue.add(setvoucherItem.getAmount2());
				}
				
				if (setvoucherItem.getDebitcredit().equals("H")){
					allChangeAmount = allChangeAmount.subtract(setvoucherItem.getAmount());
					itemValue = itemValue.subtract(setvoucherItem.getAmount2());
				}
			}
			
			Set<VoucherItem> FundFlowVoucherItemList1 = new HashSet<VoucherItem>();
			
			FundFlowVoucherItemList1 = this.getFundFlowVoucherItem(collect);
			
			Iterator<VoucherItem> FundFlowit1 = FundFlowVoucherItemList1.iterator();		
			while(FundFlowit1.hasNext()){
				VoucherItem funvoucherItem =FundFlowit1.next();
				
				if (funvoucherItem.getDebitcredit().equals("S")){
					allChangeAmount = allChangeAmount.add(funvoucherItem.getAmount());
					itemValue = itemValue.add(funvoucherItem.getAmount2());
				}
				
				if (funvoucherItem.getDebitcredit().equals("H")){
					allChangeAmount = allChangeAmount.subtract(funvoucherItem.getAmount());
					itemValue = itemValue.subtract(funvoucherItem.getAmount2());
				}
			}
			
			int rowNo = 1;
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("11");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(collect.getCustomer());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			voucherItem.setCurrency(collect.getBillcurrency());
			//货币金额
			voucherItem.setAmount(allChangeAmount);
			//货币金额
			voucherItem.setAmount2(itemValue);
			//部门
			voucherItem.setDepid(deptid);
			//文本
			voucherItem.setText(collect.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			//销售订单
			voucherItem.setSalesorder("");
			//销售订单行项目号
			voucherItem.setOrderrowno("");
			//利润中心
			voucherItem.setProfitcenter("");
			//成本中心
			voucherItem.setCostcenter("");
			//借/贷标识符
			voucherItem.setDebitcredit("H");
			//行号
			voucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItems.add(voucherItem);
			
			Set collectitems = _collect.getCollectitem();
			for (Iterator<CollectItem> iter = collectitems.iterator(); iter.hasNext();)
			{
				CollectItem collectItem = (CollectItem) iter.next();
				itemValue.add(collectItem.getAssignamount2());
			}
			
			Set collectcbills = _collect.getCollectcbill();
			for (Iterator<CollectCbill> iter = collectcbills.iterator(); iter.hasNext();)
			{
				CollectCbill collectCbill = (CollectCbill) iter.next();
				CustomerTitle customerTitle = this.customerTitleJdbcDao.getByInvoice(collectCbill.getBillno());
				
				VoucherItem _voucherItem = new VoucherItem();
				_voucherItem.setVoucher(voucher);
				_voucherItem.setRownumber(customerTitle.getBuzei());
				_voucherItem.setVoucherno(customerTitle.getBelnr());
				_voucherItem.setFiyear(customerTitle.getGjahr());
				_voucherItem.setFlag("R");
				_voucherItem.setAmount(BigDecimal.valueOf(0));
				_voucherItem.setAmount2(BigDecimal.valueOf(0));
				voucherItems.add(_voucherItem);
				
				billValue = billValue.add(customerTitle.getBwbje());
			}
			
			List collectList = this.collectHibernateDao.getCollectListByOldcollectid(collect.getOldcollectid());
			
			for(int i=0;i<collectList.size();i++){
				Collect collect1 = (Collect)collectList.get(i);
				collectitems = collect1.getCollectitem();
				for (Iterator<CollectItem> iter = collectitems.iterator(); iter.hasNext();)
				{
					CollectItem collectItem = (CollectItem) iter.next();
					itemValue.add(collectItem.getAssignamount2());
				}
				
				collectcbills = collect1.getCollectcbill();
				for (Iterator<CollectCbill> iter = collectcbills.iterator(); iter.hasNext();)
				{
					CollectCbill collectCbill = (CollectCbill) iter.next();
					CustomerTitle customerTitle = this.customerTitleJdbcDao.getByInvoice(collectCbill.getBillno());
					
					VoucherItem _voucherItem = new VoucherItem();
					_voucherItem.setVoucher(voucher);
					_voucherItem.setRownumber(customerTitle.getBuzei());
					_voucherItem.setVoucherno(customerTitle.getBelnr());
					_voucherItem.setFiyear(customerTitle.getGjahr());
					_voucherItem.setFlag("R");
					_voucherItem.setAmount(BigDecimal.valueOf(0));
					_voucherItem.setAmount2(BigDecimal.valueOf(0));
					voucherItems.add(_voucherItem);
					
					billValue = billValue.add(customerTitle.getBwbje());
				}
			}
			
			if (!collect.getCurrency().equals("CNY")){
				subtractVlaue = billValue.subtract(itemValue);
				
				if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
					VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(
							collect,subtractVlaue,"1");
					supplierVoucherItem.setVoucher(voucher);
					voucherItems.add(supplierVoucherItem);
					
					VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(
							collect,subtractVlaue,"2");
					profOrLossVoucherItem.setVoucher(voucher);
					voucherItems.add(profOrLossVoucherItem);
				}
			}
			
			voucher.setVoucherItem(voucherItems);
			//统一保存
			this.voucherService._save(voucher);
		}
	}
	
	//汇兑损益致凭证
	public void genProfOrLossVoucher(Collect collect){
		if (collect.getCollectcbill() != null && collect.getCollectcbill().size()>0){
			Voucher voucher = this.getVoucher(collect, "1");
			Set voucherItems = new HashSet();
			
			BigDecimal billValue = new BigDecimal("0");
			BigDecimal itemValue = new BigDecimal("0");
			BigDecimal subtractVlaue = new BigDecimal("0");
			
			//票的金额（发票的汇率*本位币）、款的金额（收款的汇率*本位币）
			String contract_no = "";
			String project_no = "";
			String taxcode = "";
			Iterator<CollectCbill> collectCbillIteraor2 = collect.getCollectcbill().iterator();
			if(collectCbillIteraor2.hasNext()){
				CollectCbill collectCbill = collectCbillIteraor2.next();
				
				if(StringUtils.isNullBlank(project_no)){
					if(StringUtils.isNullBlank(contract_no)){
						contract_no = collectCbill.getContract_no();
						taxcode = collectCbill.getContract_no();
					}else{
						if (!contract_no.equals(collectCbill.getContract_no())){
							taxcode = collectCbill.getProject_no();
							project_no = collectCbill.getProject_no();
						}
					}
				}
				
				contract_no = collectCbill.getContract_no();
			}
			
			Iterator<CollectCbill> collectCbillIteraor = collect.getCollectcbill().iterator();
			if(collectCbillIteraor.hasNext()){
				CollectCbill collectCbill = collectCbillIteraor.next();
				
				CustomerTitle customerTitle = this.customerTitleJdbcDao.getByInvoice(collectCbill.getBillno());
				billValue = billValue.add(customerTitle.getBwbje().divide(customerTitle.getDmbtr(),5,BigDecimal.ROUND_HALF_EVEN).multiply(collectCbill.getClearamount()));
				itemValue = itemValue.add(collectCbill.getClearamount().multiply(collect.getCollectrate()));
			}
		
			subtractVlaue = billValue.subtract(itemValue);
			if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
				VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(
						collect,subtractVlaue,"1");
				supplierVoucherItem.setVoucher(voucher);
				supplierVoucherItem.setRownumber("1");
				voucherItems.add(supplierVoucherItem);
				
				VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(
						collect,subtractVlaue,"2");
				profOrLossVoucherItem.setVoucher(voucher);
				profOrLossVoucherItem.setRownumber("2");
				
				profOrLossVoucherItem.setTaxcode(taxcode);
				voucherItems.add(profOrLossVoucherItem);
			}
			
			voucher.setVoucherItem(voucherItems);
			this.voucherService._save(voucher);	
		}
	}
	
	//收款凭证
	public void genCnyVoucher(Collect collect){
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "1");
		Voucher voucher = this.getVoucher(collect, "1");
		
		Set voucherItems = new HashSet();
		int i = 1;
		
		Set collectbankitems = collect.getCollectbankitem();
		for (Iterator<CollectBankItem> iter = collectbankitems.iterator(); iter.hasNext();)
		{
			CollectBankItem collectBankItem = (CollectBankItem) iter.next();
			VoucherItem voucherItem = new VoucherItem();
			
			voucherItem.setRownumber(Integer.toString(i));
			i++;
			voucherItem.setVoucher(voucher);
			voucherItem.setCheckcode("40");
			String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(collectBankItem.getColbanksubject(),bukrs);
			voucherItem.setSubject(collectBankItem.getColbanksubject());
			voucherItem.setSubjectdescribe(subjectdesc);
			//统驭项目
			voucherItem.setControlaccount(collectBankItem.getColbanksubject());
			//统驭科目说明
			voucherItem.setCaremark(subjectdesc);
			voucherItem.setCurrency(collect.getCurrency());
			voucherItem.setAmount(collectBankItem.getCollectamount());
			voucherItem.setAmount2(collectBankItem.getCollectamount2());
			voucherItem.setDepid(deptid);
			voucherItem.setText(collect.getText());
			voucherItem.setCashflowitem(collectBankItem.getCashflowitem());
			voucherItem.setDebitcredit("S");
			voucherItems.add(voucherItem);
		}
		
		Set collectitems = collect.getCollectitem();
		for (Iterator<CollectItem> iter = collectitems.iterator(); iter.hasNext();)
		{
			CollectItem collectItem = (CollectItem) iter.next();
			VoucherItem voucherItem = new VoucherItem();
			
			voucherItem.setRownumber(Integer.toString(i));
			i++;
			voucherItem.setVoucher(voucher);
			voucherItem.setCheckcode("11");
			voucherItem.setSubject(collect.getCustomer());
			voucherItem.setSubjectdescribe(this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs));
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			voucherItem.setCurrency(collect.getCurrency());
			voucherItem.setAmount(collectItem.getAssignamount());
			voucherItem.setAmount2(collectItem.getAssignamount2());
			voucherItem.setDepid(deptid);
			voucherItem.setText(collect.getText());
			voucherItem.setDebitcredit("H");
			voucherItem.setBusinessitemid(collectItem.getCollectitemid());
			
			//判断该收款项全部都是预收款 带出SAP订单号
			if (!StringUtils.isNullBlank(collectItem.getContract_no())
					&&collectItem.getAssignamount().compareTo(collectItem.getPrebillamount())==0){
				voucherItem.setSalesorder(this.saleContractJdbcDAO.querySapOrderNoByNo(collectItem.getContract_no()));
			}
			voucherItems.add(voucherItem);
		}
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(collect);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(i));
			i++;
			setvoucherItem.setVoucher(voucher);
			voucherItems.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(collect);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			VoucherItem funvoucherItem =FundFlowit.next();
			funvoucherItem.setRownumber(String.valueOf(i));
			i++;
			funvoucherItem.setVoucher(voucher);
			voucherItems.add(funvoucherItem);
		}
		
		voucher.setVoucherItem(voucherItems);
		this.voucherService._save(voucher);
	}
	
	//外币 币别一致凭证
	public void genSameUnCnyVoucher(Collect collect){
		List<String> retList = new ArrayList<String>();
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "1");
		
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(collect, "1");
		
		int rowNo = 0;
		//******************客户行项目凭证行项目**********************************//
		Iterator<CollectItem> CollectItemit = collect.getCollectitem() .iterator();		
		while(CollectItemit.hasNext()){
			rowNo = rowNo + 1;
			CollectItem collectItem = CollectItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("11");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(collect.getCustomer());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			voucherItem.setCurrency(collect.getBillcurrency());
			//货币金额
			voucherItem.setAmount(collectItem.getAssignamount());
			//本位币金额
			voucherItem.setAmount2(collectItem.getAssignamount2());
			//部门
			voucherItem.setDepid(deptid);
			//文本
			voucherItem.setText(collect.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			//销售订单
			voucherItem.setSalesorder("");
			//销售订单行项目号
			voucherItem.setOrderrowno("");
			//利润中心
			voucherItem.setProfitcenter("");
			//成本中心
			voucherItem.setCostcenter("");
			//借/贷标识符
			voucherItem.setDebitcredit("H");
			//行号
			voucherItem.setRownumber(String.valueOf(rowNo));
			//收款行项目信息
			voucherItem.setBusinessitemid(collectItem.getCollectitemid());
			//判断该收款项全部都是预收款 带出SAP订单号
			if (!StringUtils.isNullBlank(collectItem.getContract_no())
					&&collectItem.getAssignamount().compareTo(collectItem.getPrebillamount())==0){
				voucherItem.setSalesorder(this.saleContractJdbcDAO.querySapOrderNoByNo(collectItem.getContract_no()));
			}
			voucherItemList.add(voucherItem);
		}
		//******************客户行项目凭证行项目**********************************//
		
		//******************收款银行行项目凭证行项目*******************************//
		Iterator<CollectBankItem> bankItemit = collect.getCollectbankitem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			CollectBankItem collectBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("40");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(collectBankItem.getColbanksubject(),bukrs);
			//科目
			BankVoucherItem.setSubject(collectBankItem.getColbanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(subjectdesc);
			//货币
			BankVoucherItem.setCurrency(collect.getBillcurrency());
			//货币金额
			BankVoucherItem.setAmount(collectBankItem.getCollectamount());
			//本位币金额
			BankVoucherItem.setAmount2(collectBankItem.getCollectamount2());
			//部门
			BankVoucherItem.setDepid(deptid);
			//文本
			BankVoucherItem.setText(collect.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(collectBankItem.getCashflowitem());
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//统驭项目
			BankVoucherItem.setControlaccount(collectBankItem.getColbanksubject());
			//统驭科目说明
			BankVoucherItem.setCaremark(subjectdesc);
			//销售订单
			BankVoucherItem.setSalesorder("");
			//销售订单行项目号
			BankVoucherItem.setOrderrowno("");
			//利润中心
			BankVoucherItem.setProfitcenter("");
			//成本中心
			BankVoucherItem.setCostcenter("");
			//借/贷标识符
			BankVoucherItem.setDebitcredit("S");
			//行号
			BankVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList.add(BankVoucherItem);
		}
		//******************收款银行行项目凭证行项目*******************************//
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(collect);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			setvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(collect);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		voucher.setVoucherItem(voucherItemList);		
		this.voucherService._save(voucher);
	}
	
	//外币 币别不一致凭证
	public void genDiffUnCnyVoucher(Collect collect){
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "1");
		
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());

		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(collect, "1");
		//实际收款币别
		voucher.setCurrency(collect.getActcurrency());
		
		int rowNo = 1;
		
		//******************收款银行行项目凭证行项目*******************************//
		BigDecimal bankAmount2 = new BigDecimal("0");
		BigDecimal bankAmount = new BigDecimal("0");
		Iterator<CollectBankItem> bankItemit = collect.getCollectbankitem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			CollectBankItem collectBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("40");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(collectBankItem.getColbanksubject(),bukrs);
			//科目
			BankVoucherItem.setSubject(collectBankItem.getColbanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(subjectdesc);
			//货币
			BankVoucherItem.setCurrency(collect.getActcurrency());
			//货币金额
			BankVoucherItem.setAmount(collectBankItem.getCollectamount());
			bankAmount = bankAmount.add(collectBankItem.getCollectamount());
			//本位币金额
			BankVoucherItem.setAmount2(collectBankItem.getCollectamount2());
			bankAmount2 = bankAmount2.add(collectBankItem.getCollectamount2());
			//部门
			BankVoucherItem.setDepid(deptid);
			//文本
			BankVoucherItem.setText(collect.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(collectBankItem.getCashflowitem());
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//统驭项目
			BankVoucherItem.setControlaccount(collectBankItem.getColbanksubject());
			//统驭科目说明
			BankVoucherItem.setCaremark(subjectdesc);
			//销售订单
			BankVoucherItem.setSalesorder("");
			//销售订单行项目号
			BankVoucherItem.setOrderrowno("");
			//利润中心
			BankVoucherItem.setProfitcenter("");
			//成本中心
			BankVoucherItem.setCostcenter("");
			//借/贷标识符
			BankVoucherItem.setDebitcredit("S");
			//行号
			BankVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList.add(BankVoucherItem);
		}
		//******************收款银行行项目凭证行项目*******************************//
		//************************应收中转科目**********************************//
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//客户记账码
		voucherItem.setCheckcode("50");
		//特殊G/L标识
		voucherItem.setGlflag("");
		String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs);
		//科目
		voucherItem.setSubject("1122999999");
		//科目说明
		voucherItem.setSubjectdescribe(subjectdescribe);
		//货币
		voucherItem.setCurrency(collect.getActcurrency());
		//货币金额
		voucherItem.setAmount(bankAmount);
		//本位币金额
		voucherItem.setAmount2(bankAmount2);
		//部门
		voucherItem.setDepid(deptid);
		//文本
		voucherItem.setText(collect.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//统驭项目
		voucherItem.setControlaccount("1122999999");
		//统驭科目说明
		voucherItem.setCaremark(subjectdescribe);
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//借/贷标识符
		voucherItem.setDebitcredit("H");
		//行号
		voucherItem.setRownumber("1");
		
		voucherItemList.add(voucherItem);
		//************************应收中转科目**********************************//
		voucher.setVoucherItem(voucherItemList);		
		this.voucherService._save(voucher);
		
		//如果有中转需要取票的币别
		String strCurrency = "";
		if (collect.getCollectcbill() != null){
			Iterator<CollectCbill> collectCbillIteraor = collect.getCollectcbill().iterator();
			if(collectCbillIteraor.hasNext()){
				CollectCbill collectCbill = collectCbillIteraor.next();
				strCurrency = collectCbill.getCurrency();
			}else{
				strCurrency = collect.getBillcurrency();
			}
		}else{
			strCurrency = collect.getBillcurrency();
		}
		//凭证抬头
		Voucher voucher2 = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList2 = new HashSet<VoucherItem>();
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "1");
		voucher2 = this.getVoucher(collect, "1");
		voucher2.setCurrency(strCurrency);
		
		rowNo = 0;
		//******************客户行项目凭证行项目**********************************//
		BigDecimal allAmount = new BigDecimal("0");
		BigDecimal allChangeAmount = new BigDecimal("0");
		//******************客户行项目凭证行项目**********************************//
		Iterator<CollectItem> CollectItemit = collect.getCollectitem() .iterator();		
		while(CollectItemit.hasNext()){
			rowNo = rowNo + 1;
			CollectItem collectItem = CollectItemit.next();
			
			VoucherItem collectItemvoucherItem = new VoucherItem();
			collectItemvoucherItem.setVoucher(voucher2);
			//客户记账码
			collectItemvoucherItem.setCheckcode("11");
			//特殊G/L标识
			collectItemvoucherItem.setGlflag("");
			//科目
			collectItemvoucherItem.setSubject(collect.getCustomer());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs);
			collectItemvoucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			collectItemvoucherItem.setCurrency(strCurrency);
			//货币金额
			collectItemvoucherItem.setAmount(collectItem.getAssignamount());
			allChangeAmount = allChangeAmount.subtract(collectItem.getAssignamount());
			//本位币金额
			collectItemvoucherItem.setAmount2(collectItem.getAssignamount2());
			allAmount = allAmount.subtract(collectItem.getAssignamount2());
			//部门
			collectItemvoucherItem.setDepid(deptid);
			//文本
			collectItemvoucherItem.setText(collect.getText());
			//现金流项目
			collectItemvoucherItem.setCashflowitem("");
			//反记账标识
			collectItemvoucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			collectItemvoucherItem.setControlaccount(subject);
			//统驭科目说明
			collectItemvoucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			//销售订单
			collectItemvoucherItem.setSalesorder("");
			//销售订单行项目号
			collectItemvoucherItem.setOrderrowno("");
			//利润中心
			collectItemvoucherItem.setProfitcenter("");
			//成本中心
			collectItemvoucherItem.setCostcenter("");
			//借/贷标识符
			collectItemvoucherItem.setDebitcredit("H");
			//行号
			collectItemvoucherItem.setRownumber(String.valueOf(rowNo));
			//收款行项目信息
			collectItemvoucherItem.setBusinessitemid(collectItem.getCollectitemid());
			//判断该收款项全部都是预收款 带出SAP订单号
			if (!StringUtils.isNullBlank(collectItem.getContract_no())
					&&collectItem.getAssignamount().compareTo(collectItem.getPrebillamount())==0){
				collectItemvoucherItem.setSalesorder(this.saleContractJdbcDAO.querySapOrderNoByNo(collectItem.getContract_no()));
			}
			voucherItemList2.add(collectItemvoucherItem);
		}
		//******************客户行项目凭证行项目**********************************//
		
		//************************应收中转科目**********************************//
		Set<VoucherItem> SettlesubjectVoucherItemList1 = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList1 = this.getSettSubjVoucherItem(collect);
		
		Iterator<VoucherItem> Settlesubjectit1 = SettlesubjectVoucherItemList1.iterator();		
		while(Settlesubjectit1.hasNext()){
			VoucherItem setvoucherItem =Settlesubjectit1.next();
			
			if (setvoucherItem.getDebitcredit().equals("S")){
				allChangeAmount = allChangeAmount.add(setvoucherItem.getAmount());
			}
			
			if (setvoucherItem.getDebitcredit().equals("H")){
				allChangeAmount = allChangeAmount.subtract(setvoucherItem.getAmount());
			}
		}
		
		Set<VoucherItem> FundFlowVoucherItemList1 = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList1 = this.getFundFlowVoucherItem(collect);
		
		Iterator<VoucherItem> FundFlowit1 = FundFlowVoucherItemList1.iterator();		
		while(FundFlowit1.hasNext()){
			VoucherItem funvoucherItem =FundFlowit1.next();
			
			if (funvoucherItem.getDebitcredit().equals("S")){
				allChangeAmount = allChangeAmount.add(funvoucherItem.getAmount());
			}
			
			if (funvoucherItem.getDebitcredit().equals("H")){
				allChangeAmount = allChangeAmount.subtract(funvoucherItem.getAmount());
			}
		}
		
		rowNo = rowNo + 1;
		VoucherItem voucherItem2 = new VoucherItem();
		voucherItem2.setVoucher(voucher2);
		//客户记账码
		voucherItem2.setCheckcode("40");
		//特殊G/L标识
		voucherItem2.setGlflag("");
		String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs);
		//科目
		voucherItem2.setSubject("1122999999");
		//科目说明
		voucherItem2.setSubjectdescribe(subjectdesc);
		//货币
		voucherItem2.setCurrency(strCurrency);
		//货币金额
		voucherItem2.setAmount(allChangeAmount.abs());
		//本位币金额
		voucherItem2.setAmount2(bankAmount2.abs());
		//部门
		voucherItem2.setDepid(deptid);
		//文本
		voucherItem2.setText(collect.getText());
		//现金流项目
		voucherItem2.setCashflowitem("");
		//反记账标识
		voucherItem2.setUncheckflag("");
		//统驭项目
		voucherItem2.setControlaccount("1122999999");
		//统驭科目说明
		voucherItem2.setCaremark(subjectdesc);
		//销售订单
		voucherItem2.setSalesorder("");
		//销售订单行项目号
		voucherItem2.setOrderrowno("");
		//利润中心
		voucherItem2.setProfitcenter("");
		//成本中心
		voucherItem2.setCostcenter("");
		//借/贷标识符
		voucherItem2.setDebitcredit("S");
		//行号
		voucherItem2.setRownumber(String.valueOf(rowNo));
		
		voucherItemList2.add(voucherItem2);
		//************************应收中转科目**********************************//
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(collect);

		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			setvoucherItem.setCurrency(strCurrency);
			setvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		//结算科目凭证行项目
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(collect);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setCurrency(strCurrency);
			funvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		voucher2.setVoucherItem(voucherItemList2);		
		this.voucherService._save(voucher2);
		
		//******************汇兑损益科目号行项目凭证行项目*******************************//
//		if (collect.getSettleSubject() != null){
//			if (collect.getSettleSubject().getDebtcredit1() != null && 
//				"S".equals(collect.getSettleSubject().getDebtcredit1())){
//				allAmount = allAmount.add(collect.getSettleSubject().getStandardamount1());
//			}
//	
//			if (collect.getSettleSubject().getDebtcredit1() != null &&
//				"H".equals(collect.getSettleSubject().getDebtcredit1())){
//				allAmount = allAmount.subtract(collect.getSettleSubject().getStandardamount1());
//			}
//			
//			if (collect.getSettleSubject().getDebtcredit2() != null &&
//				"S".equals(collect.getSettleSubject().getDebtcredit2())){
//				allAmount = allAmount.add(collect.getSettleSubject().getStandardamount2());
//			}
//	
//			if (collect.getSettleSubject().getDebtcredit2() != null &&
//				"H".equals(collect.getSettleSubject().getDebtcredit2())){
//				allAmount = allAmount.subtract(collect.getSettleSubject().getStandardamount2());
//			}
//			
//			if (collect.getSettleSubject().getDebtcredit3() != null &&
//				"S".equals(collect.getSettleSubject().getDebtcredit3())){
//				allAmount = allAmount.add(collect.getSettleSubject().getStandardamount3());
//			}
//	
//			if (collect.getSettleSubject().getDebtcredit3() != null &&
//				"H".equals(collect.getSettleSubject().getDebtcredit3())){
//				allAmount = allAmount.subtract(collect.getSettleSubject().getStandardamount3());
//			}
//			
//			if (collect.getSettleSubject().getDebtcredit4() != null && 
//				"S".equals(collect.getSettleSubject().getDebtcredit4())){
//				allAmount = allAmount.add(collect.getSettleSubject().getStandardamount4());
//			}
//	
//			if (collect.getSettleSubject().getDebtcredit4() != null && 
//				"H".equals(collect.getSettleSubject().getDebtcredit4())){
//				allAmount = allAmount.subtract(collect.getSettleSubject().getStandardamount4());
//			}
//		}
//		
//		if (collect.getFundFlow() != null){
//			if (collect.getFundFlow().getDebtcredit1() != null  &&
//				"S".equals(collect.getFundFlow().getDebtcredit1())){
//				allAmount = allAmount.add(collect.getFundFlow().getStandardamount1());
//			}
//	
//			if (collect.getFundFlow().getDebtcredit1() != null  &&
//				"H".equals(collect.getFundFlow().getDebtcredit1())){
//				allAmount = allAmount.subtract(collect.getFundFlow().getStandardamount1());
//			}
//			
//			if (collect.getFundFlow().getDebtcredit2() != null  &&
//				"S".equals(collect.getFundFlow().getDebtcredit2())){
//				allAmount = allAmount.add(collect.getFundFlow().getStandardamount2());
//			}
//	
//			if ("H".equals(collect.getFundFlow().getDebtcredit2())){
//				allAmount = allAmount.subtract(collect.getFundFlow().getStandardamount2());
//			}
//			
//			if (collect.getFundFlow().getDebtcredit3() != null  &&
//				"S".equals(collect.getFundFlow().getDebtcredit3())){
//				allAmount = allAmount.add(collect.getFundFlow().getStandardamount3());
//			}
//	
//			if (collect.getFundFlow().getDebtcredit3() != null  &&
//				"H".equals(collect.getFundFlow().getDebtcredit3())){
//				allAmount = allAmount.subtract(collect.getFundFlow().getStandardamount3());
//			}
//		}
//		//******************汇兑损益科目号行项目凭证行项目*******************************//
//		BigDecimal subtractValue = allAmount.add(bankAmount2);
//		if (subtractValue.signum() == 1){
//			rowNo = rowNo + 1;
//			VoucherItem profLossVoucherItem = new VoucherItem();
//			profLossVoucherItem.setVoucher(voucher2);
//			
//			profLossVoucherItem.setCheckcode("50");
//			//特殊G/L标识
//			profLossVoucherItem.setGlflag("");
//			//科目
//			profLossVoucherItem.setSubject("6603050302");
//			//科目说明
//			profLossVoucherItem.setSubjectdescribe("收益科目");
//			//货币
//			profLossVoucherItem.setCurrency(strCurrency);
//			//货币金额
//			profLossVoucherItem.setAmount(new BigDecimal("0"));
//			//本位币金额
//			profLossVoucherItem.setAmount2(subtractValue.abs());
//			//部门
//			profLossVoucherItem.setDepid(deptid);
//			//文本
//			profLossVoucherItem.setText(collect.getText());
//			//现金流项目
//			profLossVoucherItem.setCashflowitem("");
//			//反记账标识
//			profLossVoucherItem.setUncheckflag("");
//			//统驭项目
//			profLossVoucherItem.setControlaccount("");
//			//统驭科目说明
//			profLossVoucherItem.setCaremark("");
//			//销售订单
//			profLossVoucherItem.setSalesorder("");
//			//销售订单行项目号
//			profLossVoucherItem.setOrderrowno("");
//			//利润中心
//			profLossVoucherItem.setProfitcenter("");
//			//成本中心
//			profLossVoucherItem.setCostcenter("");
//			//借/贷标识符
//			profLossVoucherItem.setDebitcredit("S");
//			//行号
//			profLossVoucherItem.setRownumber(String.valueOf(rowNo));
//			
//			voucherItemList2.add(profLossVoucherItem);
//		}
//		if (subtractValue.signum() == -1){
//			rowNo = rowNo + 1;
//			VoucherItem profLossVoucherItem = new VoucherItem();
//			profLossVoucherItem.setVoucher(voucher2);
//			
//			profLossVoucherItem.setCheckcode("40");
//			
//			//特殊G/L标识
//			profLossVoucherItem.setGlflag("");
//			//科目
//			profLossVoucherItem.setSubject("6603050301");
//			//科目说明
//			profLossVoucherItem.setSubjectdescribe("损失科目");
//			//货币
//			profLossVoucherItem.setCurrency(strCurrency);
//			//货币金额
//			profLossVoucherItem.setAmount(new BigDecimal("0"));
//			//本位币金额
//			profLossVoucherItem.setAmount2(subtractValue.abs());
//			//部门
//			profLossVoucherItem.setDepid(deptid);
//			//文本
//			profLossVoucherItem.setText(collect.getText());
//			//现金流项目
//			profLossVoucherItem.setCashflowitem("");
//			//反记账标识
//			profLossVoucherItem.setUncheckflag("");
//			//统驭项目
//			profLossVoucherItem.setControlaccount("");
//			//统驭科目说明
//			profLossVoucherItem.setCaremark("");
//			//销售订单
//			profLossVoucherItem.setSalesorder("");
//			//销售订单行项目号
//			profLossVoucherItem.setOrderrowno("");
//			//利润中心
//			profLossVoucherItem.setProfitcenter("");
//			//成本中心
//			profLossVoucherItem.setCostcenter("");
//			//借/贷标识符
//			profLossVoucherItem.setDebitcredit("S");
//			//行号
//			profLossVoucherItem.setRownumber(String.valueOf(rowNo));
//			
//			voucherItemList2.add(profLossVoucherItem);
//		}
		//******************汇兑损益科目号行项目凭证行项目*******************************//
	}
	
	/**
	 * 纯代理收款
	 * @param collect
	 * @param strClass
	 * @return
	 */
	public void representCollectVoucher(Collect collect){
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "1");
		
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());

		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		voucher = this.getVoucher(collect, "1");
		//实际收款币别
		voucher.setCurrency(collect.getActcurrency());
		
		int rowNo = 1;
		
		//******************收款银行行项目凭证行项目*******************************//
		BigDecimal bankAmount2 = new BigDecimal("0");
		BigDecimal bankAmount = new BigDecimal("0");
		Iterator<CollectBankItem> bankItemit = collect.getCollectbankitem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			CollectBankItem collectBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("40");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(collectBankItem.getColbanksubject(),bukrs);
			//科目
			BankVoucherItem.setSubject(collectBankItem.getColbanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(subjectdesc);
			//货币
			BankVoucherItem.setCurrency(collect.getActcurrency());
			//货币金额
			BankVoucherItem.setAmount(collectBankItem.getCollectamount());
			bankAmount = bankAmount.add(collectBankItem.getCollectamount());
			//本位币金额
			BankVoucherItem.setAmount2(collectBankItem.getCollectamount2());
			bankAmount2 = bankAmount2.add(collectBankItem.getCollectamount2());
			//部门
			BankVoucherItem.setDepid(deptid);
			//文本
			BankVoucherItem.setText(collect.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(collectBankItem.getCashflowitem());
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//统驭项目
			BankVoucherItem.setControlaccount(collectBankItem.getColbanksubject());
			//统驭科目说明
			BankVoucherItem.setCaremark(subjectdesc);
			//销售订单
			BankVoucherItem.setSalesorder("");
			//销售订单行项目号
			BankVoucherItem.setOrderrowno("");
			//利润中心
			BankVoucherItem.setProfitcenter("");
			//成本中心
			BankVoucherItem.setCostcenter("");
			//借/贷标识符
			BankVoucherItem.setDebitcredit("S");
			//行号
			BankVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList.add(BankVoucherItem);
		}
		//******************收款银行行项目凭证行项目*******************************//
		//************************应收中转科目**********************************//
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//客户记账码
		voucherItem.setCheckcode("50");
		//特殊G/L标识
		voucherItem.setGlflag("");
		String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs);
		//科目
		voucherItem.setSubject("1122999999");
		//科目说明
		voucherItem.setSubjectdescribe(subjectdescribe);
		//货币
		voucherItem.setCurrency(collect.getActcurrency());
		//货币金额
		voucherItem.setAmount(bankAmount);
		//本位币金额
		voucherItem.setAmount2(bankAmount2);
		//部门
		voucherItem.setDepid(deptid);
		//文本
		voucherItem.setText(collect.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//统驭项目
		voucherItem.setControlaccount("1122999999");
		//统驭科目说明
		voucherItem.setCaremark(subjectdescribe);
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//借/贷标识符
		voucherItem.setDebitcredit("H");
		//行号
		voucherItem.setRownumber("1");
		
		voucherItemList.add(voucherItem);
		//************************应收中转科目**********************************//
		voucher.setVoucherItem(voucherItemList);		
		this.voucherService._save(voucher);
		
		//如果有中转需要取票的币别
		String strCurrency = "";
		if (collect.getCollectcbill() != null){
			Iterator<CollectCbill> collectCbillIteraor = collect.getCollectcbill().iterator();
			if(collectCbillIteraor.hasNext()){
				CollectCbill collectCbill = collectCbillIteraor.next();
				strCurrency = collectCbill.getCurrency();
			}else{
				strCurrency = collect.getBillcurrency();
			}
		}else{
			strCurrency = collect.getBillcurrency();
		}
		//凭证抬头
		Voucher voucher2 = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList2 = new HashSet<VoucherItem>();
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "1");
		voucher2 = this.getVoucher(collect, "1");
		voucher2.setCurrency("CNY");
		
		rowNo = 0;
		//******************客户行项目凭证行项目**********************************//
		BigDecimal allAmount = new BigDecimal("0");
		BigDecimal allChangeAmount = new BigDecimal("0");
		//******************客户行项目凭证行项目**********************************//
		Iterator<CollectItem> CollectItemit = collect.getCollectitem() .iterator();		
		while(CollectItemit.hasNext()){
			rowNo = rowNo + 1;
			CollectItem collectItem = CollectItemit.next();
			
			VoucherItem collectItemvoucherItem = new VoucherItem();
			collectItemvoucherItem.setVoucher(voucher2);
			//客户记账码
			collectItemvoucherItem.setCheckcode("11");
			//特殊G/L标识
			collectItemvoucherItem.setGlflag("");
			//科目
			collectItemvoucherItem.setSubject(collect.getCustomer());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs);
			collectItemvoucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			collectItemvoucherItem.setCurrency("CNY");
			//货币金额
			collectItemvoucherItem.setAmount(collectItem.getAssignamount2());
			allChangeAmount = allChangeAmount.subtract(collectItem.getAssignamount2());
			//本位币金额
			collectItemvoucherItem.setAmount2(collectItem.getAssignamount2());
			allAmount = allAmount.subtract(collectItem.getAssignamount2());
			//部门
			collectItemvoucherItem.setDepid(deptid);
			//文本
			collectItemvoucherItem.setText(collect.getText());
			//现金流项目
			collectItemvoucherItem.setCashflowitem("");
			//反记账标识
			collectItemvoucherItem.setUncheckflag("");
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			collectItemvoucherItem.setControlaccount(subject);
			//统驭科目说明
			collectItemvoucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			//销售订单
			collectItemvoucherItem.setSalesorder("");
			//销售订单行项目号
			collectItemvoucherItem.setOrderrowno("");
			//利润中心
			collectItemvoucherItem.setProfitcenter("");
			//成本中心
			collectItemvoucherItem.setCostcenter("");
			//借/贷标识符
			collectItemvoucherItem.setDebitcredit("H");
			//行号
			collectItemvoucherItem.setRownumber(String.valueOf(rowNo));
			//收款行项目信息
			collectItemvoucherItem.setBusinessitemid(collectItem.getCollectitemid());
			//判断该收款项全部都是预收款 带出SAP订单号
			if (!StringUtils.isNullBlank(collectItem.getContract_no())
					&&collectItem.getAssignamount().compareTo(collectItem.getPrebillamount())==0){
				collectItemvoucherItem.setSalesorder(this.saleContractJdbcDAO.querySapOrderNoByNo(collectItem.getContract_no()));
			}
			voucherItemList2.add(collectItemvoucherItem);
		}
		//******************客户行项目凭证行项目**********************************//
		
		//************************应收中转科目**********************************//
		Set<VoucherItem> SettlesubjectVoucherItemList1 = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList1 = this.getSettSubjVoucherItem(collect);
		
		Iterator<VoucherItem> Settlesubjectit1 = SettlesubjectVoucherItemList1.iterator();		
		while(Settlesubjectit1.hasNext()){
			VoucherItem setvoucherItem =Settlesubjectit1.next();
			
			if (setvoucherItem.getDebitcredit().equals("S")){
				allChangeAmount = allChangeAmount.add(setvoucherItem.getAmount());
			}
			
			if (setvoucherItem.getDebitcredit().equals("H")){
				allChangeAmount = allChangeAmount.subtract(setvoucherItem.getAmount());
			}
		}
		
		Set<VoucherItem> FundFlowVoucherItemList1 = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList1 = this.getFundFlowVoucherItem(collect);
		
		Iterator<VoucherItem> FundFlowit1 = FundFlowVoucherItemList1.iterator();		
		while(FundFlowit1.hasNext()){
			VoucherItem funvoucherItem =FundFlowit1.next();
			
			if (funvoucherItem.getDebitcredit().equals("S")){
				allChangeAmount = allChangeAmount.add(funvoucherItem.getAmount());
			}
			
			if (funvoucherItem.getDebitcredit().equals("H")){
				allChangeAmount = allChangeAmount.subtract(funvoucherItem.getAmount());
			}
		}
		
		rowNo = rowNo + 1;
		VoucherItem voucherItem2 = new VoucherItem();
		voucherItem2.setVoucher(voucher2);
		//客户记账码
		voucherItem2.setCheckcode("40");
		//特殊G/L标识
		voucherItem2.setGlflag("");
		String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById("1122999999",bukrs);
		//科目
		voucherItem2.setSubject("1122999999");
		//科目说明
		voucherItem2.setSubjectdescribe(subjectdesc);
		//货币
		voucherItem2.setCurrency("CNY");
		//货币金额
		voucherItem2.setAmount(bankAmount2.abs());
		//本位币金额
		voucherItem2.setAmount2(bankAmount2.abs());
		//部门
		voucherItem2.setDepid(deptid);
		//文本
		voucherItem2.setText(collect.getText());
		//现金流项目
		voucherItem2.setCashflowitem("");
		//反记账标识
		voucherItem2.setUncheckflag("");
		//统驭项目
		voucherItem2.setControlaccount("1122999999");
		//统驭科目说明
		voucherItem2.setCaremark(subjectdesc);
		//销售订单
		voucherItem2.setSalesorder("");
		//销售订单行项目号
		voucherItem2.setOrderrowno("");
		//利润中心
		voucherItem2.setProfitcenter("");
		//成本中心
		voucherItem2.setCostcenter("");
		//借/贷标识符
		voucherItem2.setDebitcredit("S");
		//行号
		voucherItem2.setRownumber(String.valueOf(rowNo));
		
		voucherItemList2.add(voucherItem2);
		//************************应收中转科目**********************************//
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(collect);

		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			setvoucherItem.setCurrency("CNY");
			setvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		//结算科目凭证行项目
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(collect);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setCurrency("CNY");
			funvoucherItem.setVoucher(voucher2);
			
			voucherItemList2.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		voucher2.setVoucherItem(voucherItemList2);		
		this.voucherService._save(voucher2);
	}
	
	/**
	 * 得到清帐凭证的抬头信息
	 * @param importPayment
	 * @param strClass
	 * @return
	 */
	public Voucher getClearVoucher(Collect collect,String strClass){
		Voucher voucher = new Voucher();
		
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		
		//业务编号
		voucher.setBusinessid(collect.getCollectid());
		//业务类型
		voucher.setBusinesstype("01");
		//凭证类型
		voucher.setVouchertype("DR");
		//清帐凭证状态
		voucher.setBstat("A");
		//凭证抬头文本
		voucher.setVouchertext("清帐凭证");
		//过账日期
		voucher.setCheckdate(collect.getAccountdate().replace("-", ""));
		//公司代码
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码
		//货币
		Iterator<CollectCbill> collectCbillIterator = collect.getCollectcbill().iterator();
		if(collectCbillIterator.hasNext()){
			CollectCbill collectCbill = collectCbillIterator.next();
			voucher.setCurrency(collectCbill.getCurrency());
		}else{
			voucher.setCurrency(collect.getActcurrency());
		}
		//凭证日期
		voucher.setVoucherdate(collect.getVoucherdate().replace("-", ""));
		//会计年度
		voucher.setFiyear(collect.getAccountdate().substring(0, 4));
		//会计期间
		voucher.setFiperiod(collect.getAccountdate().substring(5, 7));
		//输入日期
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));
		//输入者
		voucher.setImporter(userContext.getUser().getUserName());
		//预制者
		voucher.setPreparer(userContext.getUser().getUserName());
		//银行科目号
		Iterator<CollectBankItem> collectbankItemit = collect.getCollectbankitem().iterator();
		if(collectbankItemit.hasNext()){
			CollectBankItem collectBankItem = collectbankItemit.next();
			voucher.setKonto(collectBankItem.getColbanksubject());
		}
		//计息日
		voucher.setValut(dateFormat.format(new Date()));
		//客户编号
		voucher.setAgkon(collect.getCustomer());
		//客户或客户标识
		voucher.setAgkoa("D");
		//业务范围
		voucher.setGsber(this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id()));
		//收款清帐标识
		voucher.setFlag("R");
		//凭证分类
		voucher.setVoucherclass(strClass);
		//汇率
		voucher.setExchangerate(new BigDecimal("1"));
		
		return voucher;
	}
	
	//生成清帐凭证
	public void genClearVoucher(Collect collect){
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "10");
		//判断是否已经生成清账凭证，	如果有，不再生成	
		List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(collect.getCollectid(),"A");
		if(li!=null && li.size()!=0){				
			for(Voucher vo : li){
				if(!StringUtils.isNullBlank(vo.getVoucherno())){
					return;
				}
			} 
		}
		
		Voucher voucher = new Voucher();
		
		//得到票的本位币之和
		BigDecimal billValue = new BigDecimal("0");
		//得到分配项的本位币之和
		BigDecimal itemValue = new BigDecimal("0");
		//分配项之和和票之和的差
		BigDecimal subtractVlaue = new BigDecimal("0");
		
		//凭证抬头
		Set<VoucherItem> voucherItems = new HashSet<VoucherItem>();
		if(this.ifGenClearVoucher(collect)){
			voucher = getClearVoucher(collect,"10");
			//外币中转 清空清帐银行科目号
			if(!collect.getBillcurrency().equals(collect.getCurrency())){
				voucher.setKonto("");
			}
			
			List voucherItemList = this.voucherItemJdbcDao.getVoucherItemList(collect.getCollectid());
			for (int i = 0;i<voucherItemList.size();i++){
				VoucherItem voucherItem = (VoucherItem) voucherItemList.get(i);
				
				VoucherItem _voucherItem = new VoucherItem();
				_voucherItem.setVoucher(voucher);
				_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
				_voucherItem.setRownumber(voucherItem.getRownumber());
				_voucherItem.setAmount(BigDecimal.valueOf(0));
				_voucherItem.setAmount2(BigDecimal.valueOf(0));
				_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
				_voucherItem.setBusinessitemid(voucherItem.getBusinessitemid());
				_voucherItem.setFlag("R");
				voucherItems.add(_voucherItem);
				itemValue = itemValue.add(voucherItem.getAmount2());
			}
			
			Set collectcbills = collect.getCollectcbill();
			for (Iterator<CollectCbill> iter = collectcbills.iterator(); iter.hasNext();)
			{
				CollectCbill collectCbill = (CollectCbill) iter.next();
				CustomerTitle customerTitle = this.customerTitleJdbcDao.getByInvoice(collectCbill.getBillno());
				
				VoucherItem _voucherItem = new VoucherItem();
				_voucherItem.setVoucher(voucher);
				_voucherItem.setRownumber(customerTitle.getBuzei());
				_voucherItem.setVoucherno(customerTitle.getBelnr());
				_voucherItem.setFiyear(customerTitle.getGjahr());
				_voucherItem.setFlag("R");
				_voucherItem.setAmount(BigDecimal.valueOf(0));
				_voucherItem.setAmount2(BigDecimal.valueOf(0));
				voucherItems.add(_voucherItem);
				
				billValue = billValue.add(customerTitle.getBwbje());
			}
			
			if (!collect.getCurrency().equals("CNY")){
				subtractVlaue = billValue.subtract(itemValue);
				
				if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
					VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(
							collect,subtractVlaue,"1");
					supplierVoucherItem.setVoucher(voucher);
					voucherItems.add(supplierVoucherItem);
					
					VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(
							collect,subtractVlaue,"2");
					profOrLossVoucherItem.setVoucher(voucher);
					voucherItems.add(profOrLossVoucherItem);
				}
			}
			
			voucher.setVoucherItem(voucherItems);
			//统一保存
			this.voucherService._save(voucher);
		}else{
			List contractList = this.ifGenClearVoucherContract(collect);
			if(contractList.size()>0){
				voucher = getClearVoucher(collect,"10");
				
				String contractnos = "";
				for (int i = 0;i<contractList.size();i++){
					String contractno = (String)contractList.get(i);
					contractnos += "'" +contractno + "',";
				}
				contractnos = contractnos.substring(0, contractnos.length() - 1);
				List voucherItemList = this.voucherItemJdbcDao.getVoucherItemListByContract(contractnos);
				
				for (int i = 0;i<voucherItemList.size();i++){
					VoucherItem voucherItem = (VoucherItem) voucherItemList.get(i);
					
					VoucherItem _voucherItem = new VoucherItem();
					_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
					_voucherItem.setRownumber(voucherItem.getRownumber());
					_voucherItem.setVoucherno(voucherItem.getVoucher().getVoucherno());
					_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
					_voucherItem.setFlag("R");
					_voucherItem.setVoucher(voucher);
					_voucherItem.setAmount(BigDecimal.valueOf(0));
					_voucherItem.setAmount2(BigDecimal.valueOf(0));
					_voucherItem.setBusinessitemid(voucherItem.getBusinessitemid());
					voucherItems.add(_voucherItem);
					itemValue = itemValue.add(voucherItem.getAmount2());
				}
				
				List<CustomerTitle> customerTitleList = this.customerTitleJdbcDao.getByContract(contractnos);
				for (int i = 0;i<customerTitleList.size();i++){
					CustomerTitle customerTitle = (CustomerTitle)customerTitleList.get(i);
					
					VoucherItem _voucherItem = new VoucherItem();
					_voucherItem.setVoucher(voucher);
					_voucherItem.setRownumber(customerTitle.getBuzei());
					_voucherItem.setVoucherno(customerTitle.getBelnr());
					_voucherItem.setFiyear(customerTitle.getGjahr());
					_voucherItem.setFlag("R");
					_voucherItem.setAmount(BigDecimal.valueOf(0));
					_voucherItem.setAmount2(BigDecimal.valueOf(0));
					voucherItems.add(_voucherItem);
					itemValue = itemValue.subtract(customerTitle.getBwbje());
				}
				
				if (!collect.getCurrency().equals("CNY")){
					subtractVlaue = billValue.subtract(itemValue);
					
					if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
						VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(
								collect,subtractVlaue,"1");
						supplierVoucherItem.setVoucher(voucher);
						voucherItems.add(supplierVoucherItem);
						
						VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(
								collect,subtractVlaue,"2");
						profOrLossVoucherItem.setVoucher(voucher);
						voucherItems.add(profOrLossVoucherItem);
					}
				}
				
				voucher.setVoucherItem(voucherItems);
				//统一保存
				this.voucherService._save(voucher);
			}else{
				List projectList = this.ifGenClearVoucherProject(collect);
				
				if(projectList.size()>0){
					voucher = getClearVoucher(collect,"10");
					
					String projectnos = "";
					for (int i = 0;i<projectList.size();i++){
						String projectno = (String)projectList.get(i);
						projectnos += "'" +projectno + "',";
					}
					projectnos = projectnos.substring(0, projectnos.length() - 1);
					List voucherItemList = this.voucherItemJdbcDao.getVoucherItemListByProject(projectnos);
					
					for (int i = 0;i<voucherItemList.size();i++){
						VoucherItem voucherItem = (VoucherItem) voucherItemList.get(i);
						
						VoucherItem _voucherItem = new VoucherItem();
						_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
						_voucherItem.setRownumber(voucherItem.getRownumber());
						_voucherItem.setVoucherno(voucherItem.getVoucher().getVoucherno());
						_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
						_voucherItem.setFlag("R");
						_voucherItem.setVoucher(voucher);
						_voucherItem.setAmount(BigDecimal.valueOf(0));
						_voucherItem.setAmount2(BigDecimal.valueOf(0));
						_voucherItem.setBusinessitemid(voucherItem.getBusinessitemid());
						voucherItems.add(_voucherItem);
						itemValue = itemValue.add(voucherItem.getAmount2());
					}
					
					//TODO 取立项下的票
					List<CustomerTitle> customerTitleList = this.customerTitleJdbcDao.getByProject(projectnos);
					for (int i = 0;i<customerTitleList.size();i++){
						CustomerTitle customerTitle = (CustomerTitle)customerTitleList.get(i);
						
						VoucherItem _voucherItem = new VoucherItem();
						_voucherItem.setVoucher(voucher);
						_voucherItem.setRownumber(customerTitle.getBuzei());
						_voucherItem.setVoucherno(customerTitle.getBelnr());
						_voucherItem.setFiyear(customerTitle.getGjahr());
						_voucherItem.setFlag("R");
						_voucherItem.setAmount(BigDecimal.valueOf(0));
						_voucherItem.setAmount2(BigDecimal.valueOf(0));
						voucherItems.add(_voucherItem);
						itemValue = itemValue.subtract(customerTitle.getBwbje());
					}
					
					if (!collect.getCurrency().equals("CNY")){
						subtractVlaue = billValue.subtract(itemValue);
						
						if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
							VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(
									collect,subtractVlaue,"1");
							supplierVoucherItem.setVoucher(voucher);
							voucherItems.add(supplierVoucherItem);
							
							VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(
									collect,subtractVlaue,"2");
							profOrLossVoucherItem.setVoucher(voucher);
							voucherItems.add(profOrLossVoucherItem);
						}
					}
					
					voucher.setVoucherItem(voucherItems);
					//统一保存
					this.voucherService._save(voucher);
				}else{
					if(this.ifGenClearVoucherCustomer(collect)){
						voucher = getClearVoucher(collect,"10");
						
						List voucherItemList = this.voucherItemJdbcDao.getVoucherItemListByCustomer(collect.getDept_id(), collect.getCustomer());
						for (int i = 0;i<voucherItemList.size();i++){
							VoucherItem voucherItem = (VoucherItem) voucherItemList.get(i);
							
							VoucherItem _voucherItem = new VoucherItem();
							_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
							_voucherItem.setRownumber(voucherItem.getRownumber());
							_voucherItem.setVoucherno(voucherItem.getVoucher().getVoucherno());
							_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
							_voucherItem.setFlag("R");
							_voucherItem.setVoucher(voucher);
							_voucherItem.setAmount(BigDecimal.valueOf(0));
							_voucherItem.setAmount2(BigDecimal.valueOf(0));
							_voucherItem.setBusinessitemid(voucherItem.getBusinessitemid());
							voucherItems.add(_voucherItem);
							itemValue = itemValue.add(voucherItem.getAmount2());
						}
						
						//TODO 取客户下的票
						if (!collect.getCurrency().equals("CNY")){
							subtractVlaue = billValue.subtract(itemValue);
							
							if (new BigDecimal("0").compareTo(subtractVlaue) != 0){
								VoucherItem supplierVoucherItem = getProfitAndLossVoucherItem(
										collect,subtractVlaue,"1");
								supplierVoucherItem.setVoucher(voucher);
								voucherItems.add(supplierVoucherItem);
								
								VoucherItem profOrLossVoucherItem = getProfitAndLossVoucherItem(
										collect,subtractVlaue,"2");
								profOrLossVoucherItem.setVoucher(voucher);
								voucherItems.add(profOrLossVoucherItem);
							}
						}
						
						voucher.setVoucherItem(voucherItems);
						//统一保存
						this.voucherService._save(voucher);
					}else{
						if (!collect.getCurrency().equals("CNY")) {
							//没有清帐凭证，则生成汇兑损益凭证
							//this.genProfOrLossVoucher(collect);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 判断是否生成保证金清帐凭证
	 * 
	 * @param collect
	 * @return true 本次可清
	 */
	public Boolean ifGenDepositClearVoucher(Collect collect){
		//1、判断本次是否可清
		BigDecimal assignamount = collect.getApplyamount();
		//上次收款的剩余保证金
		BigDecimal remainsuretybond = this._get(collect.getOldcollectid()).getRemainsuretybond();
		
		Map collectcbillMap = this.collectJdbcDao.getCollectCbillAmount("", collect.getCollectid(), "", "", "", "");
		//未收款合计
		BigDecimal sumunreceivedamount = (BigDecimal) collectcbillMap.get("unreceivedamount");
		//清帐金额合计
		BigDecimal sumclearamount = (BigDecimal) collectcbillMap.get("clearamount");
		
		//本次的分配金额 = 上次收款的剩余保证金 未收款与清帐金额合计相等 预收款保证金为零 则可清帐
		if((assignamount.compareTo(remainsuretybond)==0)&&(sumclearamount.compareTo(sumunreceivedamount)==0) && (sumclearamount.compareTo(new BigDecimal("0"))>0))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否生成清帐凭证
	 * 
	 * @param collect
	 * @return true 本次可清
	 */
	public Boolean ifGenClearVoucher(Collect collect){
		//1、判断本次是否可清
		Map collectitemMap = this.collectJdbcDao.getCollectItemAmount("", "", collect.getCollectid(), "", "", "", "");
		//分配金额合计
		BigDecimal sumassignamount = (BigDecimal) collectitemMap.get("assignamount");
		//预收款金额合计
		BigDecimal sumprebillamount = (BigDecimal) collectitemMap.get("prebillamount");
		//保证金金额合计
		BigDecimal sumsuretybond = (BigDecimal) collectitemMap.get("suretybond");
		
		Map collectcbillMap = this.collectJdbcDao.getCollectCbillAmount("", collect.getCollectid(), "", "", "", "");
		//应收款合计
		BigDecimal sumreceivableamount = (BigDecimal) collectcbillMap.get("receivableamount");
		//清帐金额合计
		BigDecimal sumclearamount = (BigDecimal) collectcbillMap.get("clearamount");
		
		//如果分配金额与清帐金额合计相等 应收款与清帐金额合计相等 预收款保证金为零 则可清帐
		if((sumassignamount.compareTo(sumclearamount)==0)&&(sumclearamount.compareTo(sumreceivableamount)==0)
				&&(sumprebillamount.compareTo(BigDecimal.valueOf(0))==0)&&(sumsuretybond.compareTo(BigDecimal.valueOf(0))==0))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否生成清帐凭证
	 * 
	 * @param collect
	 * @return flag=1 本次可清
	 * @return contractList 合同下可清
	 * @return projectList 立项下可清
	 * @return customer 客户下可清
	 */
	public List ifGenClearVoucherContract(Collect collect){
		//2、判断合同下是否可清
		List contractList = new ArrayList();
		Set collectitems = collect.getCollectitem();
		Set collectcbills = collect.getCollectcbill();
		
		for (Iterator<CollectItem> iter = collectitems.iterator(); iter.hasNext();)
		{
			CollectItem collectItem = (CollectItem) iter.next();
			
			if (!StringUtils.isNullBlank(collectItem.getContract_no())){
				//1、判断本次是否可清
				Map collectitemMap1 = this.collectJdbcDao.getCollectItemAmount("", "", collect.getCollectid(), "", collectItem.getContract_no(), "", "");
				//分配金额合计
				BigDecimal sumassignamount1 = (BigDecimal) collectitemMap1.get("assignamount");
				//预收款金额合计
				BigDecimal sumprebillamount = (BigDecimal) collectitemMap1.get("prebillamount");
				//保证金金额合计
				BigDecimal sumsuretybond = (BigDecimal) collectitemMap1.get("suretybond");
				
				if(sumprebillamount.compareTo(BigDecimal.valueOf(0))==1||sumsuretybond.compareTo(BigDecimal.valueOf(0))==1){
					continue;
				}
				//该合同下已清款的金额合计
				Map collectitemMap = this.collectJdbcDao.getCollectItemAmount("", "", "", "", collectItem.getContract_no(), "", "1");
				BigDecimal sumassignamount2 = (BigDecimal) collectitemMap.get("assignamount");
				
				//本次合同下清票金额
				Map collectcbillMap = this.collectJdbcDao.getCollectCbillAmount("", collect.getCollectid(), "", collectItem.getContract_no(), "", "");
				BigDecimal sumclearamount = (BigDecimal) collectcbillMap.get("clearamount");
				//BigDecimal sumunreceivedamount = (BigDecimal) collectcbillMap.get("unreceivedamount");

				//该合同下已清的票金额合计
				BigDecimal billclearamount = this.customerTitleJdbcDao.getSumBillAmount(collectItem.getContract_no(), "", "", "1");
				
				BigDecimal sumassignamount = sumassignamount1.add(sumassignamount2);
				//款票金额相等  清票金额和票相等
				if(sumassignamount.compareTo(billclearamount)==0&&sumassignamount.compareTo(BigDecimal.valueOf(0))==1)
				{
					contractList.add(collectItem.getContract_no());
				}
			}
		}
		return contractList;
	}
	
	/**
	 * 判断是否生成清帐凭证
	 * 
	 * @param collect
	 * @return flag=1 本次可清
	 * @return contractList 合同下可清
	 * @return projectList 立项下可清
	 * @return customer 客户下可清
	 */
	public List ifGenClearVoucherProject(Collect collect){
		List projectList = new ArrayList();
		//1、判断本次是否可清
		Set collectitems = collect.getCollectitem();
		Set collectcbills = collect.getCollectcbill();
		//3、判断立项下是否可清
		for (Iterator<CollectItem> iter = collectitems.iterator(); iter.hasNext();)
		{
			CollectItem collectItem = (CollectItem) iter.next();
			if (!StringUtils.isNullBlank(collectItem.getProject_no())){
				//1、判断本次是否可清
				Map collectitemMap1 = this.collectJdbcDao.getCollectItemAmount("", "", collect.getCollectid(), "", collectItem.getContract_no(), "", "");
				//分配金额合计
				BigDecimal sumassignamount1 = (BigDecimal) collectitemMap1.get("assignamount");
				//预收款金额合计
				BigDecimal sumprebillamount = (BigDecimal) collectitemMap1.get("prebillamount");
				//保证金金额合计
				BigDecimal sumsuretybond = (BigDecimal) collectitemMap1.get("suretybond");
				
				if(sumprebillamount.compareTo(BigDecimal.valueOf(0))==1||sumsuretybond.compareTo(BigDecimal.valueOf(0))==1){
					continue;
				}
				
				//该立项下已清的金额合计
				Map collectitemMap = this.collectJdbcDao.getCollectItemAmount("", "", "", "", "", collectItem.getProject_no(), "1");
				BigDecimal sumassignamount2 = (BigDecimal) collectitemMap.get("assignamount");
				//该立项下已清的票金额合计
				BigDecimal billclearamount = this.customerTitleJdbcDao.getSumBillAmount("", collectItem.getProject_no(), "", "1");
				//BigDecimal billclearamount = this.customerTitleJdbcDao.getSumClearAmount("", "", "", collectItem.getProject_no(), "'3'");

				//合同下 已结清的款+本次收款 等于 合同下 已结清的票+本次清票 则可清
				if((sumassignamount1.add(sumassignamount2)).compareTo(billclearamount)==0)
				{
					projectList.add(collectItem.getProject_no());
				}
			}
		}
		return projectList;
	}
	
	/**
	 * 判断是否生成清帐凭证
	 * 
	 * @param collect
	 * @return flag=1 本次可清
	 * @return contractList 合同下可清
	 * @return projectList 立项下可清
	 * @return customer 客户下可清
	 */
	public Boolean ifGenClearVoucherCustomer(Collect collect){
		String customer = "";
		//4、判断客户下是否可清
		//本次清票金额
		Map collectcbillMap = this.collectJdbcDao.getCollectCbillAmount("", collect.getCollectid(), "", "", "", "");
		BigDecimal sumunreceivedamount = (BigDecimal) collectcbillMap.get("unreceivedamount");
		BigDecimal sumclearamount = (BigDecimal) collectcbillMap.get("clearamount");
		
		if(sumclearamount.compareTo(new BigDecimal("0"))==0) return false;
		if(sumunreceivedamount.compareTo(sumclearamount)==0){
			//该客户下已清的金额合计
			Map collectitemMap = this.collectJdbcDao.getCollectItemAmount(collect.getDept_id(),collect.getCustomer(), "", "", "", "", "1");
			BigDecimal sumcusassignamount = (BigDecimal) collectitemMap.get("assignamount");
			//本次收款金额
			collectitemMap = this.collectJdbcDao.getCollectItemAmount("", "", collect.getCollectid(), "", "", "", "0");
			BigDecimal sumassignamount = (BigDecimal) collectitemMap.get("assignamount");
			
			//客户下所有的清票金额合计
			BigDecimal billclearamount = this.customerTitleJdbcDao.getSumClearAmount(collect.getDept_id(), collect.getCustomer(), "", "", "'3'");
	
			
			//客户下 已结清的款+本次收款 等于 客户下 已结清的票+本次清票 则可清
			if((sumcusassignamount.add(sumassignamount)).compareTo(billclearamount.add(sumclearamount))==0)
			{
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 应收票据模拟凭证
	 * @param importPayment
	 * @return
	 */
	public void genBillVoucher(Collect collect){
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "1");
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());
		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(collect, "1");
		voucher.setReceive("X");
		voucher.setFlag("W");
		
		int rowNo = 0;
		//******************客户行项目凭证行项目**********************************//
		Iterator<CollectItem> CollectItemit = collect.getCollectitem() .iterator();		
		while(CollectItemit.hasNext()){
			rowNo = rowNo + 1;
			CollectItem collectItem = CollectItemit.next();
			
			VoucherItem voucherItem = new VoucherItem();
			voucherItem.setVoucher(voucher);
			//客户记账码
			voucherItem.setCheckcode("11");
			//特殊G/L标识
			voucherItem.setGlflag("");
			//科目
			voucherItem.setSubject(collect.getCustomer());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//货币
			voucherItem.setCurrency("CNY");
			//货币金额
			voucherItem.setAmount(collectItem.getAssignamount());
			//本位币金额
			voucherItem.setAmount2(collectItem.getAssignamount2());
			//部门
			voucherItem.setDepid(deptid);
			voucherItem.setGsber(deptid);
			//文本
			voucherItem.setText(collect.getText());
			//现金流项目
			voucherItem.setCashflowitem("");
			//反记账标识
			voucherItem.setUncheckflag("");
			//是否票据标志
			voucherItem.setFlag("W");
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
			//销售订单
			voucherItem.setSalesorder("");
			//销售订单行项目号
			voucherItem.setOrderrowno("");
			//利润中心
			voucherItem.setProfitcenter("");
			//成本中心
			voucherItem.setCostcenter("");
			//借/贷标识符
			voucherItem.setDebitcredit("H");
			//行号
			voucherItem.setRownumber(String.valueOf(rowNo));
			//到期日
			voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
			//收款行项目信息
			voucherItem.setBusinessitemid(collectItem.getCollectitemid());
			
			voucherItemList.add(voucherItem);
		}
		//******************客户行项目凭证行项目**********************************//
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(collect);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			//是否票据标志
			setvoucherItem.setFlag("W");
			setvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(collect);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			//是否票据标志
			funvoucherItem.setFlag("W");
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		rowNo = rowNo + 1;
		
		BigDecimal amount = BigDecimal.valueOf(0);
		Iterator<VoucherItem> VoucherItemit = voucherItemList.iterator();		
		while(VoucherItemit.hasNext()){
			VoucherItem voucherItem = VoucherItemit.next();
			if(voucherItem.getDebitcredit()!=null){
				if (voucherItem.getDebitcredit().equals("H")){
					amount = amount.add(voucherItem.getAmount());
				}else{
					amount = amount.subtract(voucherItem.getAmount());
				}
			}
		}
		
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//客户记账码
		voucherItem.setCheckcode("09");
		//特殊G/L标识
		voucherItem.setGlflag("W");
		String Subjectdescribe = "";
		String subject = "";
		//国内信用证
		if(collect.getCollecttype().equals("11")){
			//科目
			voucherItem.setSubject(collect.getTicketbankid());
			Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getTicketbankid(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getTicketbankid(),bukrs);
			//科目说明
			voucherItem.setSubjectdescribe(Subjectdescribe);
		}//银行/商业承兑汇票
		else if(collect.getCollecttype().equals("12")){
			//科目
			voucherItem.setSubject(collect.getCustomer());
			Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
			//科目说明
			voucherItem.setSubjectdescribe(Subjectdescribe);
		}
		subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
		String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
		//统驭项目
		voucherItem.setControlaccount(subject);
		//统驭科目说明
		voucherItem.setCaremark(subjectname);
		
		//货币
		voucherItem.setCurrency("CNY");
		if(amount.compareTo(new BigDecimal(0))==-1){
			//货币金额
			voucherItem.setAmount(amount.abs());
			//本位币金额
			voucherItem.setAmount2(amount.abs());
			//借/贷标识符
			voucherItem.setDebitcredit("H");
		}else{
			//货币金额
			voucherItem.setAmount(amount);
			//本位币金额
			voucherItem.setAmount2(amount);
			//借/贷标识符
			voucherItem.setDebitcredit("S");
		}
		//部门
		voucherItem.setDepid(deptid);
		voucherItem.setGsber(deptid);
		//文本
		voucherItem.setText(collect.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//行号
		voucherItem.setRownumber(String.valueOf(rowNo));
		//是否票据标志
		voucherItem.setFlag("W");
		//票据日期
		voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
		//票据业务范围
		voucherItem.setGsber(collect.getBillbc());
		
		voucherItemList.add(voucherItem);
		
		voucher.setVoucherItem(voucherItemList);		
		this.voucherService._save(voucher);
		
	}
	
	/**
	 * 应收票据收款模拟凭证
	 * @param importPayment
	 * @return
	 */
	public void genBillCollectVoucher(Collect collect){
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "5");
		
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());

		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(collect, "5");
		voucher.setReceive("X");
		voucher.setFlag("W");
		
		int rowNo = 1;
		//******************收款银行行项目凭证行项目*******************************//
		Iterator<CollectBankItem> bankItemit = collect.getCollectbankitem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			CollectBankItem collectBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("40");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(collectBankItem.getColbanksubject(),bukrs);
			//科目
			BankVoucherItem.setSubject(collectBankItem.getColbanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(subjectdesc);
			//货币
			BankVoucherItem.setCurrency("CNY");
			//货币金额
			BankVoucherItem.setAmount(collectBankItem.getCollectamount());
			//本位币金额
			BankVoucherItem.setAmount2(collectBankItem.getCollectamount2());
			//部门
			BankVoucherItem.setDepid(deptid);
			//业务范围
			BankVoucherItem.setGsber(deptid);
			//文本
			BankVoucherItem.setText(collect.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(collectBankItem.getCashflowitem());
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//统驭项目
			BankVoucherItem.setControlaccount(collectBankItem.getColbanksubject());
			//统驭科目说明
			BankVoucherItem.setCaremark(subjectdesc);
			//销售订单
			BankVoucherItem.setSalesorder("");
			//销售订单行项目号
			BankVoucherItem.setOrderrowno("");
			//利润中心
			BankVoucherItem.setProfitcenter("");
			//成本中心
			BankVoucherItem.setCostcenter("");
			//借/贷标识符
			BankVoucherItem.setDebitcredit("S");
			//是否票据标志
			BankVoucherItem.setFlag("W");
			//行号
			BankVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList.add(BankVoucherItem);
		}
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(collect);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			//是否票据标志
			setvoucherItem.setFlag("W");
			setvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(collect);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			//是否票据标志
			funvoucherItem.setFlag("W");
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		
		BigDecimal amount = BigDecimal.valueOf(0);
		Iterator<VoucherItem> VoucherItemit = voucherItemList.iterator();		
		while(VoucherItemit.hasNext()){
			VoucherItem voucherItem = VoucherItemit.next();
			if (voucherItem.getDebitcredit().equals("H")){
				amount = amount.add(voucherItem.getAmount());
			}else{
				amount = amount.subtract(voucherItem.getAmount());
			}
		}
		
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//行号
		voucherItem.setRownumber("1");
		//客户记账码
		voucherItem.setCheckcode("19");
		//特殊G/L标识
		voucherItem.setGlflag("W");
		//科目
		voucherItem.setSubject(collect.getCustomer());
		//科目说明
		String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs);
		voucherItem.setSubjectdescribe(Subjectdescribe);
		//货币
		voucherItem.setCurrency("CNY");
		if(amount.compareTo(new BigDecimal(0))==-1){
			//货币金额
			voucherItem.setAmount(amount.abs());
			//本位币金额
			voucherItem.setAmount2(amount.abs());
			//借/贷标识符
			voucherItem.setDebitcredit("H");
		}else{
			//货币金额
			voucherItem.setAmount(amount);
			//本位币金额
			voucherItem.setAmount2(amount);
			//借/贷标识符
			voucherItem.setDebitcredit("S");
		}
		//部门
		voucherItem.setDepid(deptid);
		voucherItem.setGsber(deptid);
		//文本
		voucherItem.setText(collect.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//是否票据标志
		voucherItem.setFlag("W");
		String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
		subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
		//统驭项目
		voucherItem.setControlaccount(subject);
		//统驭科目说明
		voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//到期日
		voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
		
		voucherItemList.add(voucherItem);
		
		voucher.setVoucherItem(voucherItemList);		
		this.voucherService._save(voucher);
		
	}
	
	public void genDiscountClearVoucher(Collect collect){
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "10");
		//判断是否已经生成清账凭证，	如果有，不再生成	
		List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(collect.getCollectid(),"A");
		if(li!=null && li.size()!=0){				
			for(Voucher vo : li){
				if(!StringUtils.isNullBlank(vo.getVoucherno())){
					return;
				}
			} 
		}
		
		Voucher voucher = new Voucher();
		
		//凭证抬头
		Set<VoucherItem> voucherItems = new HashSet<VoucherItem>();
		
		voucher = getClearVoucher(collect,"10");
		//国内信用证
		if(collect.getCollecttype().equals("11")){
			//客户编号取票据银行zhongzh
			voucher.setAgkon(collect.getTicketbankid());
		}
		voucher.setAgums("W");
		//外币中转 清空清帐银行科目号
		if(!collect.getBillcurrency().equals(collect.getCurrency())){
			voucher.setKonto("");
		}
		
		List voucherItemList = this.voucherItemJdbcDao.getVoucherItemList2(collect.getCollectid());
		for (int i = 0;i<voucherItemList.size();i++){
			VoucherItem voucherItem = (VoucherItem) voucherItemList.get(i);
			
			VoucherItem _voucherItem = new VoucherItem();
			_voucherItem.setVoucher(voucher);
			_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
			_voucherItem.setVoucherno(voucherItem.getVoucher().getVoucherno());
			_voucherItem.setRownumber(voucherItem.getRownumber());
			_voucherItem.setAmount(BigDecimal.valueOf(0));
			_voucherItem.setAmount2(BigDecimal.valueOf(0));
			_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
			_voucherItem.setBusinessitemid(voucherItem.getBusinessitemid());
			_voucherItem.setFlag("R");
			voucherItems.add(_voucherItem);
		}
		
//		//取出收票据凭证  通过单据号码找以前的收票据单
//		Collect _collect = this.collectJdbcDao.getCollectBydraft(collect.getDraft(), collect.getCollectid());
//		Voucher _voucher = this.voucherHibernateDao.getVoucherByBusinessId(_collect.getCollectid());
//		
//		Set<VoucherItem> _voucherItems = _voucher.getVoucherItem();
//		
//		Iterator<VoucherItem> voucherItemit = _voucherItems.iterator();
//		while(voucherItemit.hasNext()){
//			VoucherItem voucherItem = voucherItemit.next();
//			
//			if("W".equals(voucherItem.getGlflag())){
//				VoucherItem _voucherItem = new VoucherItem();
//				_voucherItem.setVoucher(voucher);
//				_voucherItem.setBusvoucherid(voucherItem.getVoucher().getVoucherid());
//				_voucherItem.setRownumber(voucherItem.getRownumber());
//				_voucherItem.setAmount(BigDecimal.valueOf(0));
//				_voucherItem.setAmount2(BigDecimal.valueOf(0));
//				_voucherItem.setFiyear(voucherItem.getVoucher().getFiyear());
//				_voucherItem.setBusinessitemid(voucherItem.getBusinessitemid());
//				_voucherItem.setFlag("R");
//				voucherItems.add(_voucherItem);
//			}
//		}
		
		voucher.setVoucherItem(voucherItems);
		//统一保存
		this.voucherService._save(voucher);
	}
	
	
	/**
	 * 贴现 银行承兑汇票
	 * @param importPayment
	 * @return
	 */
	public void genDiscountVoucher(Collect collect){
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "5");
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());
		// 获取原单据的信息
//		Collect oldCollect = this.collectJdbcDao.getCollectBydraft(collect.getDraft(), collect.getCollectid());

		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(collect, "5");
		voucher.setReceive("X");
		voucher.setFlag("W");

		BigDecimal amount = new BigDecimal("0");
		BigDecimal amount2 = new BigDecimal("0");
		int rowNo = 0;
		
		//******************收款银行行项目凭证行项目*******************************//
		Iterator<CollectBankItem> bankItemit = collect.getCollectbankitem().iterator();		
		while(bankItemit.hasNext()){
			CollectBankItem collectBankItem = bankItemit.next();
			
			if(collectBankItem.getFlag().equals("1")) continue;
			
			amount = amount.add(collectBankItem.getCollectamount());
			amount2 = amount2.add(collectBankItem.getCollectamount2());
			
			rowNo = rowNo + 1;
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("40");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(collectBankItem.getColbanksubject(),bukrs);
			//科目
			BankVoucherItem.setSubject(collectBankItem.getColbanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(subjectdesc);
			//货币
			BankVoucherItem.setCurrency("CNY");
			//货币金额
			BankVoucherItem.setAmount(collectBankItem.getCollectamount());
			//本位币金额
			BankVoucherItem.setAmount2(collectBankItem.getCollectamount2());
			//部门
			BankVoucherItem.setDepid(deptid);
			//业务范围
			BankVoucherItem.setGsber(collect.getBillbc());
//			BankVoucherItem.setGsber(oldCollect.getBillbc());
			
			//文本
			BankVoucherItem.setText(collect.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(collectBankItem.getCashflowitem());
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//统驭项目
			BankVoucherItem.setControlaccount(collectBankItem.getColbanksubject());
			//统驭科目说明
			BankVoucherItem.setCaremark(subjectdesc);
			//销售订单
			BankVoucherItem.setSalesorder("");
			//销售订单行项目号
			BankVoucherItem.setOrderrowno("");
			//利润中心
			BankVoucherItem.setProfitcenter("");
			//成本中心
			BankVoucherItem.setCostcenter("");
			//借/贷标识符
			BankVoucherItem.setDebitcredit("S");
			//是否票据标志
			BankVoucherItem.setFlag("W");
			//到期日
			BankVoucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
			//行号
			BankVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList.add(BankVoucherItem);
		}
		//******************收款银行行项目凭证行项目*******************************//
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(collect);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			
			if(setvoucherItem.getDebitcredit().equals("S")){
				amount = amount.add(setvoucherItem.getAmount());
				amount2 = amount2.add(setvoucherItem.getAmount2());
			}else{
				amount = amount.subtract(setvoucherItem.getAmount());
				amount2 = amount2.subtract(setvoucherItem.getAmount2());
			}
			
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			//是否票据标志
			setvoucherItem.setFlag("W");
			setvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(collect);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			
			if(funvoucherItem.getDebitcredit().equals("S")){
				amount = amount.add(funvoucherItem.getAmount());
				amount2 = amount2.add(funvoucherItem.getAmount2());
			}else{
				amount = amount.subtract(funvoucherItem.getAmount());
				amount2 = amount2.subtract(funvoucherItem.getAmount2());
			}
			//是否票据标志
			funvoucherItem.setFlag("W");
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		rowNo = rowNo + 1;
		
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//客户记账码
		voucherItem.setCheckcode("19");
		//特殊G/L标识
		voucherItem.setGlflag("W");
		//货币
		voucherItem.setCurrency("CNY");
		
		//货币金额
		voucherItem.setAmount(amount);
		//本位币金额
		voucherItem.setAmount2(amount2);
		//部门
		voucherItem.setDepid(deptid);
		//业务范围
		voucherItem.setGsber(collect.getBillbc());
//		voucherItem.setGsber(oldCollect.getBillbc());
		//文本
		voucherItem.setText(collect.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//是否票据标志
		voucherItem.setFlag("W");
		//国内信用证
		if(collect.getCollecttype().equals("11")){
			String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getTicketbankid(),bukrs);
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getTicketbankid(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
			//科目
			voucherItem.setSubject(collect.getTicketbankid());
			//科目说明
			voucherItem.setSubjectdescribe(Subjectdescribe);

			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(subjectname);
		}//银行/商业承兑汇票
		else if(collect.getCollecttype().equals("12")){
			String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs);
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
			//科目
			voucherItem.setSubject(collect.getCustomer());
			//科目说明
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(subjectname);
		}
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//借/贷标识符
		voucherItem.setDebitcredit("H");
		//到期日
		voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
		//行号
		voucherItem.setRownumber(String.valueOf(rowNo));
		
		voucherItemList.add(voucherItem);
		
		voucher.setVoucherItem(voucherItemList);		
		this.voucherService._save(voucher);
	}
	
	/**
	 * 议付 国内信用证
	 * @param importPayment
	 * @return
	 */
	public void genNegotiationVoucher(Collect collect){
		
	}
	
	/**
	 * 应收票据清帐模拟凭证
	 * @param importPayment
	 * @return
	 */
	public Voucher genBillClearVoucher(Collect collect){
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "5");
		String deptid = this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id());
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());

		//凭证抬头
		Voucher voucher = new Voucher();
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		
		voucher = this.getVoucher(collect, "5");
		voucher.setReceive("X");
		voucher.setFlag("W");

		BigDecimal amount = new BigDecimal("0");
		BigDecimal amount2 = new BigDecimal("0");
		int rowNo = 0;
		
		rowNo = rowNo + 1;
		
		VoucherItem voucherItem = new VoucherItem();
		voucherItem.setVoucher(voucher);
		//客户记账码
		voucherItem.setCheckcode("19");
		//特殊G/L标识
		voucherItem.setGlflag("W");
		//货币
		voucherItem.setCurrency("CNY");
		
		//取得票据的凭证
		List<VoucherItem>  voucheritemList2 = this.voucherItemJdbcDao.getVoucherItemList(collect.getCollectid(),"01");
		for (Iterator<VoucherItem> iter = voucheritemList2.iterator(); iter.hasNext();)
		{
			VoucherItem _voucherItem = (VoucherItem) iter.next();
			if(_voucherItem.getCheckcode().equals("09")){
				//货币金额
				voucherItem.setAmount(_voucherItem.getAmount());
				//本位币金额
				voucherItem.setAmount2(_voucherItem.getAmount2());
			}
		}
		//部门
		voucherItem.setDepid(deptid);
		//业务范围
		voucherItem.setGsber(collect.getBillbc());
		//文本
		voucherItem.setText(collect.getText());
		//现金流项目
		voucherItem.setCashflowitem("");
		//反记账标识
		voucherItem.setUncheckflag("");
		//是否票据标志
		voucherItem.setFlag("W");
		//国内信用证
		if(collect.getCollecttype().equals("11")){
			String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getTicketbankid(),bukrs);
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getTicketbankid(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
			//科目
			voucherItem.setSubject(collect.getTicketbankid());
			//科目说明
			voucherItem.setSubjectdescribe(Subjectdescribe);

			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(subjectname);
		}//银行/商业承兑汇票
		else if(collect.getCollecttype().equals("12")){
			String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs);
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs);
			//科目
			voucherItem.setSubject(collect.getCustomer());
			//科目说明
			voucherItem.setSubjectdescribe(Subjectdescribe);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(subjectname);
		}
		//销售订单
		voucherItem.setSalesorder("");
		//销售订单行项目号
		voucherItem.setOrderrowno("");
		//利润中心
		voucherItem.setProfitcenter("");
		//成本中心
		voucherItem.setCostcenter("");
		//借/贷标识符
		voucherItem.setDebitcredit("H");
		//到期日
		voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
		//行号
		voucherItem.setRownumber(String.valueOf(rowNo));
		
		voucherItemList.add(voucherItem);
		
		//******************客户行项目凭证行项目**********************************//
		
		//******************收款银行行项目凭证行项目*******************************//
		Iterator<CollectBankItem> bankItemit = collect.getCollectbankitem().iterator();		
		while(bankItemit.hasNext()){
			rowNo = rowNo + 1;
			CollectBankItem collectBankItem = bankItemit.next();
			
			VoucherItem BankVoucherItem = new VoucherItem();
			BankVoucherItem.setVoucher(voucher);
			//客户记账码
			BankVoucherItem.setCheckcode("40");
			//特殊G/L标识
			BankVoucherItem.setGlflag("");
			String subjectdesc = this.customerRefundItemJdbcDao.getSubjectNameById(collectBankItem.getColbanksubject(),bukrs);
			//科目
			BankVoucherItem.setSubject(collectBankItem.getColbanksubject());
			//科目说明
			BankVoucherItem.setSubjectdescribe(subjectdesc);
			//货币
			BankVoucherItem.setCurrency("CNY");
			//货币金额
			BankVoucherItem.setAmount(collectBankItem.getCollectamount());
			//本位币金额
			BankVoucherItem.setAmount2(collectBankItem.getCollectamount2());
			//部门
			BankVoucherItem.setDepid(deptid);
			//业务范围
			BankVoucherItem.setGsber(collect.getBillbc());
			//文本
			BankVoucherItem.setText(collect.getText());
			//现金流项目
			BankVoucherItem.setCashflowitem(collectBankItem.getCashflowitem());
			//反记账标识
			BankVoucherItem.setUncheckflag("");
			//统驭项目
			BankVoucherItem.setControlaccount(collectBankItem.getColbanksubject());
			//统驭科目说明
			BankVoucherItem.setCaremark(subjectdesc);
			//销售订单
			BankVoucherItem.setSalesorder("");
			//销售订单行项目号
			BankVoucherItem.setOrderrowno("");
			//利润中心
			BankVoucherItem.setProfitcenter("");
			//成本中心
			BankVoucherItem.setCostcenter("");
			//借/贷标识符
			BankVoucherItem.setDebitcredit("S");
			//是否票据标志
			BankVoucherItem.setFlag("W");
			//到期日
			BankVoucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
			//行号
			BankVoucherItem.setRownumber(String.valueOf(rowNo));
			
			voucherItemList.add(BankVoucherItem);
		}
		//******************收款银行行项目凭证行项目*******************************//
		
		//******************结算科目行项目凭证行项目*******************************//
		Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();
		
		SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(collect);
		
		Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();		
		while(Settlesubjectit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem setvoucherItem =Settlesubjectit.next();
			setvoucherItem.setRownumber(String.valueOf(rowNo));
			//是否票据标志
			setvoucherItem.setFlag("W");
			setvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(setvoucherItem);
		}
		//******************结算科目行项目凭证行项目*******************************//
		
		//******************特殊总帐行项目凭证行项目*******************************//
		Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();
		
		FundFlowVoucherItemList = this.getFundFlowVoucherItem(collect);
		
		Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();		
		while(FundFlowit.hasNext()){
			rowNo = rowNo + 1;
			VoucherItem funvoucherItem =FundFlowit.next();
			//是否票据标志
			funvoucherItem.setFlag("W");
			funvoucherItem.setRownumber(String.valueOf(rowNo));
			funvoucherItem.setVoucher(voucher);
			
			voucherItemList.add(funvoucherItem);
		}
		//******************特殊总帐行项目凭证行项目*******************************//
		
		voucher.setVoucherItem(voucherItemList);		
		this.voucherService._save(voucher);
		
		//this.voucherJdbcDao.deleteVoucherByBusinessidAndClass(collect.getCollectid(), "6");
//		Voucher clearVoucher = new Voucher();
//		clearVoucher = getBillClearVoucher(collect,"6",voucher,"N");
//		
//		this.voucherService._save(clearVoucher);
		return voucher;
	}
	
	/**
	 * 得到应收票据的清帐凭证
	 * @param homePayment
	 * @param strClass
	 * @param payVoucher
	 * @param strIsSHow
	 * @return
	 */
	public Voucher getBillClearVoucher(Collect collect,
			String strClass,
			Voucher payVoucher,
			String strIsSHow){
		Voucher voucher = new Voucher();
		//凭证抬头
		voucher = getClearVoucher(collect,strClass);
		// 邱杰烜 2011-06-01 将票据的清账凭证的“供应商或客户编号”更改为收款的票据银行
		voucher.setAgkon(collect.getTicketbankid());
		voucher.setAgums("W");
		//凭证行项目
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		payVoucher = this.voucherJdbcDao.getVoucher(payVoucher.getBusinessid(), payVoucher.getBusinesstype(), payVoucher.getVoucherclass());
		Set<VoucherItem> payVoucherItem = payVoucher.getVoucherItem();
		Iterator<VoucherItem> payVoucherItemit = payVoucherItem.iterator();
		while(payVoucherItemit.hasNext()){
			VoucherItem payItem = payVoucherItemit.next();
			
			if (payItem.getCheckcode().equals("19") &&
					payItem.getGlflag().equals("W")){
				
				//changed by chenfei at 2011
				VoucherItem voucherItem = getClearVoucherItem(payItem.getVoucherno(),
						payItem.getRownumber(),
						payItem.getVoucher().getFiyear(),payItem.getVoucher().getVoucherid());

				voucherItem.setVoucher(voucher);
				voucherItemList.add(voucherItem);
			}
		}
		
		List<ClearVoucherItemStruct> clearVoucherItemStructList = 
			this.vendorTitleJdbcDao.getBillClearItemInfo(collect.getCollectid(),"09","W","1");
		
		for (int j = 0;j<clearVoucherItemStructList.size();j++){
			ClearVoucherItemStruct clearVoucherItemStruct = 
				(ClearVoucherItemStruct) clearVoucherItemStructList.get(j);
			
			VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
					clearVoucherItemStruct.getRowno(),
					clearVoucherItemStruct.getYear()," ");
			
			voucherItem.setVoucher(voucher);
			voucherItemList.add(voucherItem);
		}
		
		voucher.setVoucherItem(voucherItemList);
		this.voucherService._save(voucher);
		return voucher;
	}
	
	/**
	 * 处理清帐凭证行项目
	 * @param strVoucherNo
	 * @param strRowNo
	 * @param strYear
	 * @return
	 */
	public VoucherItem getClearVoucherItem(String strVoucherNo,
			String strRowNo,
			String strYear,
			String strBusVoucherId){
		VoucherItem voucherItem = new VoucherItem();
		
		voucherItem.setVoucherno(strVoucherNo);
		voucherItem.setRownumber(strRowNo);
		voucherItem.setFiyear(strYear);
		voucherItem.setFlag("R");
		voucherItem.setAmount(new BigDecimal("0"));
		voucherItem.setAmount2(new BigDecimal("0"));
		voucherItem.setBusvoucherid(strBusVoucherId);
		
		return voucherItem;
	}
	
	/**
	 * 得到结算科目的凭证行项目
	 * @param importSettSubj
	 * @return
	 */
	public Set<VoucherItem> getSettSubjVoucherItem(Collect collect){
		SettleSubject settleSubject = collect.getSettleSubject();
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		BigDecimal amount = new BigDecimal(0);
		BigDecimal standardamount = new BigDecimal(0);
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());
		if (null != settleSubject)
		{
			//2011-5-17 huanghu
			if(!StringUtils.isNullBlank(settleSubject.getFlag())&&(Integer.valueOf(settleSubject.getFlag())<1)){
				amount = settleSubject.getAmount1();
				standardamount = settleSubject.getStandardamount1();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					// 开始添加凭证行项目
					VoucherItem voucherItem = new VoucherItem();
					
					//客户记账码
					if ("S".equals(settleSubject.getDebtcredit1()))
					{
						voucherItem.setCheckcode("40");
						//借/贷标识符
						voucherItem.setDebitcredit(settleSubject.getDebtcredit1());
					}
					
					//客户记账码
					if ("H".equals(settleSubject.getDebtcredit1()))
					{
						voucherItem.setCheckcode("50");
						//借/贷标识符
						voucherItem.setDebitcredit(settleSubject.getDebtcredit1());
					}
					//特殊G/L标识
					voucherItem.setGlflag("");
					String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(settleSubject.getSettlesubject1(),bukrs);
					//科目
					voucherItem.setSubject(settleSubject.getSettlesubject1());
					//科目说明
					voucherItem.setSubjectdescribe(subjectname);
					//统驭项目
					voucherItem.setControlaccount(settleSubject.getSettlesubject1());
					//统驭科目说明
					voucherItem.setCaremark(subjectname);
					//货币
					voucherItem.setCurrency(settleSubject.getCollect().getBillcurrency());
					//货币金额
					voucherItem.setAmount(settleSubject.getAmount1());
					//本位币金额
					voucherItem.setAmount2(settleSubject.getStandardamount1());
					//到期日
					voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
					//部门
					voucherItem.setDepid(settleSubject.getDepid1());
					//业务范围
					voucherItem.setGsber(settleSubject.getDepid1());
					//文本
					voucherItem.setText(settleSubject.getCollect().getText());
					//反记账标识
					if(settleSubject.getAntiaccount1().equals("Y")){
						voucherItem.setUncheckflag("X");
					}
					//销售订单
					voucherItem.setSalesorder(settleSubject.getOrderno1());
					//销售订单行项目号
					voucherItem.setOrderrowno(settleSubject.getRowno1());
					//成本中心
					voucherItem.setCostcenter(settleSubject.getCostcenter1());
	
					voucherItemList.add(voucherItem);
				}
			}
			
			if(!StringUtils.isNullBlank(settleSubject.getFlag())&&(Integer.valueOf(settleSubject.getFlag())<2)){
				amount = settleSubject.getAmount2();
				standardamount = settleSubject.getStandardamount2();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					// 开始添加凭证行项目
					VoucherItem voucherItem = new VoucherItem();
					
					//客户记账码
					if ("S".equals(settleSubject.getDebtcredit2()))
					{
						voucherItem.setCheckcode("40");
						//借/贷标识符
						voucherItem.setDebitcredit(settleSubject.getDebtcredit2());
					}
					
					//客户记账码
					if ("H".equals(settleSubject.getDebtcredit2()))
					{
						voucherItem.setCheckcode("50");
						//借/贷标识符
						voucherItem.setDebitcredit(settleSubject.getDebtcredit2());
					}
					//特殊G/L标识
					voucherItem.setGlflag("");
					String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(settleSubject.getSettlesubject2(),bukrs);
					//科目
					voucherItem.setSubject(settleSubject.getSettlesubject2());
					//科目说明
					voucherItem.setSubjectdescribe(subjectname);
					//统驭项目
					voucherItem.setControlaccount(settleSubject.getSettlesubject2());
					//统驭科目说明
					voucherItem.setCaremark(subjectname);
					//货币
					voucherItem.setCurrency(settleSubject.getCollect().getBillcurrency());
					//货币金额
					voucherItem.setAmount(settleSubject.getAmount2());
					//本位币金额
					voucherItem.setAmount2(settleSubject.getStandardamount2());
					//到期日
					voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
					//部门
					voucherItem.setDepid(settleSubject.getDepid2());
					//业务范围
					voucherItem.setGsber(settleSubject.getDepid2());
					//文本
					voucherItem.setText(settleSubject.getCollect().getText());
					//反记账标识
					if(settleSubject.getAntiaccount2().equals("Y")){
						voucherItem.setUncheckflag("X");
					}
					//销售订单
					voucherItem.setSalesorder(settleSubject.getOrderno2());
					//销售订单行项目号
					voucherItem.setOrderrowno(settleSubject.getRowno2());
					//成本中心
					voucherItem.setCostcenter(settleSubject.getCostcenter2());
	
					voucherItemList.add(voucherItem);
				}
			}
			
			if(!StringUtils.isNullBlank(settleSubject.getFlag())&&(Integer.valueOf(settleSubject.getFlag())<3)){
				amount = settleSubject.getAmount3();
				standardamount = settleSubject.getStandardamount3();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					// 开始添加凭证行项目
					VoucherItem voucherItem = new VoucherItem();
					
					//客户记账码
					if ("S".equals(settleSubject.getDebtcredit3()))
					{
						voucherItem.setCheckcode("40");
						//借/贷标识符
						voucherItem.setDebitcredit(settleSubject.getDebtcredit3());
					}
					
					//客户记账码
					if ("H".equals(settleSubject.getDebtcredit3()))
					{
						voucherItem.setCheckcode("50");
						//借/贷标识符
						voucherItem.setDebitcredit(settleSubject.getDebtcredit3());
					}
					//特殊G/L标识
					voucherItem.setGlflag("");
					String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(settleSubject.getSettlesubject3(),bukrs);
					//科目
					voucherItem.setSubject(settleSubject.getSettlesubject3());
					//科目说明
					voucherItem.setSubjectdescribe(subjectname);
					//统驭项目
					voucherItem.setControlaccount(settleSubject.getSettlesubject3());
					//统驭科目说明
					voucherItem.setCaremark(subjectname);
					//货币
					voucherItem.setCurrency(settleSubject.getCollect().getBillcurrency());
					//货币金额
					voucherItem.setAmount(settleSubject.getAmount3());
					//本位币金额
					voucherItem.setAmount2(settleSubject.getStandardamount3());
					//到期日
					voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
					//部门
					voucherItem.setDepid(settleSubject.getDepid3());
					//业务范围
					voucherItem.setGsber(settleSubject.getDepid3());
					//文本
					voucherItem.setText(settleSubject.getCollect().getText());
					//反记账标识
					if(settleSubject.getAntiaccount3().equals("Y")){
						voucherItem.setUncheckflag("X");
					}
					//销售订单
					voucherItem.setSalesorder(settleSubject.getOrderno3());
					//销售订单行项目号
					voucherItem.setOrderrowno(settleSubject.getRowno3());
					//成本中心
					voucherItem.setCostcenter(settleSubject.getCostcenter3());
	
					voucherItemList.add(voucherItem);
				}
			}
			
			if(!StringUtils.isNullBlank(settleSubject.getFlag())&&(Integer.valueOf(settleSubject.getFlag())<4)){
				amount = settleSubject.getAmount4();
				standardamount = settleSubject.getStandardamount4();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					// 开始添加凭证行项目
					VoucherItem voucherItem = new VoucherItem();
					
					//客户记账码
					if ("S".equals(settleSubject.getDebtcredit4()))
					{
						voucherItem.setCheckcode("40");
						//借/贷标识符
						voucherItem.setDebitcredit(settleSubject.getDebtcredit4());
					}
					
					//客户记账码
					if ("H".equals(settleSubject.getDebtcredit4()))
					{
						voucherItem.setCheckcode("50");
						//借/贷标识符
						voucherItem.setDebitcredit(settleSubject.getDebtcredit4());
					}
					//特殊G/L标识
					voucherItem.setGlflag("");
					String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(settleSubject.getSettlesubject4(),bukrs);
					//科目
					voucherItem.setSubject(settleSubject.getSettlesubject4());
					//科目说明
					voucherItem.setSubjectdescribe(subjectname);
					//统驭项目
					voucherItem.setControlaccount(settleSubject.getSettlesubject4());
					//统驭科目说明
					voucherItem.setCaremark(subjectname);
					//货币
					voucherItem.setCurrency(settleSubject.getCollect().getBillcurrency());
					//货币金额
					voucherItem.setAmount(settleSubject.getAmount4());
					//本位币金额
					voucherItem.setAmount2(settleSubject.getStandardamount4());
					//到期日
					voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
					//部门
					voucherItem.setDepid(settleSubject.getDepid4());
					//业务范围
					voucherItem.setGsber(settleSubject.getDepid4());
					//文本
					voucherItem.setText(settleSubject.getCollect().getText());
					//反记账标识
					if(settleSubject.getAntiaccount4().equals("Y")){
						voucherItem.setUncheckflag("X");
					}
					//销售订单
					voucherItem.setSalesorder(settleSubject.getOrderno4());
					//销售订单行项目号
					voucherItem.setOrderrowno(settleSubject.getRowno4());
					//利润中心
					voucherItem.setProfitcenter(settleSubject.getProfitcenter());
	
					voucherItemList.add(voucherItem);
				}
			}
		}
		return voucherItemList;
	}
	
	/**
	 * 纯资金往来
	 * @param flowPay
	 * @return
	 */
	public Set<VoucherItem> getFundFlowVoucherItem(Collect collect){
		FundFlow flowPay = collect.getFundFlow();
		Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());
		BigDecimal amount = new BigDecimal(0);
		BigDecimal standardamount = new BigDecimal(0);
		if (null != flowPay)
		{
			if(!StringUtils.isNullBlank(flowPay.getFlag())&&(Integer.valueOf(flowPay.getFlag())<1)){
				amount = flowPay.getAmount1();
				standardamount = flowPay.getStandardamount1();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					VoucherItem voucherItem = new VoucherItem();
					
					//客户记账码
					if ("S".equals(flowPay.getDebtcredit1()))
					{
						if(StringUtils.isNullBlank(flowPay.getSpecialaccount1())){
							voucherItem.setCheckcode("01");
						}else{
							voucherItem.setCheckcode("09");
						}
                        if ("D".equals(flowPay.getSpecialaccount1())) {
                            voucherItem.setCheckcode("29");
                        }
						//借/贷标识符
						voucherItem.setDebitcredit(flowPay.getDebtcredit1());
					}
					
					//客户记账码
					if ("H".equals(flowPay.getDebtcredit1()))
					{
						if(StringUtils.isNullBlank(flowPay.getSpecialaccount1())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItem.setCheckcode("19");
						}
						if ("D".equals(flowPay.getSpecialaccount1())) {
						    voucherItem.setCheckcode("39");
						}
						//借/贷标识符
						voucherItem.setDebitcredit(flowPay.getDebtcredit1());
					}
					//客户记账码
					//voucherItem.setCheckcode("29");
					//借/贷标识符
					//voucherItem.setDebitcredit(flowPay.getDebtcredit1());
					//特殊G/L标识
					voucherItem.setGlflag(flowPay.getSpecialaccount1());
					//科目
					voucherItem.setSubject(flowPay.getCustomer1());
					//科目说明
					String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(
							flowPay.getCustomer1()
							,bukrs);
	                if ("D".equals(flowPay.getSpecialaccount1())) {
	                    //科目
	                    voucherItem.setSubject(flowPay.getSupplier1());
	                    //科目说明
	                    Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(flowPay.getSupplier1(), bukrs);
	                }
					voucherItem.setSubjectdescribe(Subjectdescribe);
					//货币
					voucherItem.setCurrency(flowPay.getCollect().getBillcurrency());
					//货币金额
					voucherItem.setAmount(flowPay.getAmount1());
					//本位币金额
					voucherItem.setAmount2(flowPay.getStandardamount1());
					//到期日
					voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
					//部门
					voucherItem.setDepid(flowPay.getDepid1());
					//业务范围
					voucherItem.setGsber(flowPay.getDepid1());
					String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(flowPay.getCustomer1(),bukrs);
                    if ("D".equals(flowPay.getSpecialaccount1())) {
                        subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(flowPay.getSupplier1(), bukrs);
                    }
					subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
					//统驭项目
					voucherItem.setControlaccount(subject);
					//统驭科目说明
					voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
					//文本
					voucherItem.setText(flowPay.getCollect().getText());
					//反记账标识
					if(flowPay.getAntiaccount1().equals("Y")){
						voucherItem.setUncheckflag("X");
					}
					voucherItemList.add(voucherItem);
				}
			}
			
			if(!StringUtils.isNullBlank(flowPay.getFlag())&&(Integer.valueOf(flowPay.getFlag())<2)){
				amount = flowPay.getAmount2();
				standardamount = flowPay.getStandardamount2();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					VoucherItem voucherItem = new VoucherItem();
					
					//客户记账码
					if ("S".equals(flowPay.getDebtcredit2()))
					{
						if(StringUtils.isNullBlank(flowPay.getSpecialaccount2())){
							voucherItem.setCheckcode("01");
						}else{
							voucherItem.setCheckcode("09");
						}
                        if ("D".equals(flowPay.getSpecialaccount2())) {
                            voucherItem.setCheckcode("29");
                        }
						//借/贷标识符
						voucherItem.setDebitcredit(flowPay.getDebtcredit2());
					}
					
					//客户记账码
					if ("H".equals(flowPay.getDebtcredit2()))
					{
						if(StringUtils.isNullBlank(flowPay.getSpecialaccount2())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItem.setCheckcode("19");
						}
                        if ("D".equals(flowPay.getSpecialaccount2())) {
                            voucherItem.setCheckcode("39");
                        }
						//借/贷标识符
						voucherItem.setDebitcredit(flowPay.getDebtcredit2());
					}
					//客户记账码
					//voucherItem.setCheckcode("29");
					//借/贷标识符
					//voucherItem.setDebitcredit(flowPay.getDebtcredit2());
					//特殊G/L标识
					voucherItem.setGlflag(flowPay.getSpecialaccount2());
					//科目
					voucherItem.setSubject(flowPay.getCustomer2());
					//科目说明
					String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(
							flowPay.getCustomer2()
							,bukrs);
	                if ("D".equals(flowPay.getSpecialaccount2())) {
	                    //科目
	                    voucherItem.setSubject(flowPay.getSupplier2());
	                    //科目说明
	                    Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(flowPay.getSupplier2(), bukrs);
	                }
					voucherItem.setSubjectdescribe(Subjectdescribe);
					//货币
					voucherItem.setCurrency(flowPay.getCollect().getBillcurrency());
					//货币金额
					voucherItem.setAmount(flowPay.getAmount2());
					//本位币金额
					voucherItem.setAmount2(flowPay.getStandardamount2());
					//到期日
					voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
					//部门
					voucherItem.setDepid(flowPay.getDepid2());
					String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(flowPay.getCustomer2(),bukrs);
                    if ("D".equals(flowPay.getSpecialaccount2())) {
                        subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(flowPay.getSupplier2(), bukrs);
                    }
					subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
					//统驭项目
					voucherItem.setControlaccount(subject);
					//统驭科目说明
					voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
					//业务范围
					voucherItem.setGsber(flowPay.getDepid2());
					//文本
					voucherItem.setText(flowPay.getCollect().getText());
					//反记账标识
					if(flowPay.getAntiaccount2().equals("Y")){
						voucherItem.setUncheckflag("X");
					}
					
					voucherItemList.add(voucherItem);
				}
			}
			
			if(!StringUtils.isNullBlank(flowPay.getFlag())&&(Integer.valueOf(flowPay.getFlag())<3)){
				amount = flowPay.getAmount3();
				standardamount = flowPay.getStandardamount3();
				if (amount.abs().compareTo(new BigDecimal(0)) == 1||standardamount.abs().compareTo(new BigDecimal(0)) == 1)
				{
					VoucherItem voucherItem = new VoucherItem();
					
					//客户记账码
					if ("S".equals(flowPay.getDebtcredit3()))
					{
						if(StringUtils.isNullBlank(flowPay.getSpecialaccount3())){
							voucherItem.setCheckcode("01");
						}else{
							voucherItem.setCheckcode("09");
						}
                        if ("D".equals(flowPay.getSpecialaccount3())) {
                            voucherItem.setCheckcode("29");
                        }
						//借/贷标识符
						voucherItem.setDebitcredit(flowPay.getDebtcredit3());
					}
					
					//客户记账码
					if ("H".equals(flowPay.getDebtcredit3()))
					{
						if(StringUtils.isNullBlank(flowPay.getSpecialaccount3())){
							voucherItem.setCheckcode("11");
						}else{
							voucherItem.setCheckcode("19");
						}
                        if ("D".equals(flowPay.getSpecialaccount3())) {
                            voucherItem.setCheckcode("39");
                        }
						//借/贷标识符
						voucherItem.setDebitcredit(flowPay.getDebtcredit3());
					}
					//客户记账码
					//voucherItem.setCheckcode("29");
					//借/贷标识符
					//voucherItem.setDebitcredit(flowPay.getDebtcredit3());
					//特殊G/L标识
					voucherItem.setGlflag(flowPay.getSpecialaccount3());
					//科目
					voucherItem.setSubject(flowPay.getCustomer3());
					//科目说明
					String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(
							flowPay.getCustomer3()
							,bukrs);
	                if ("D".equals(flowPay.getSpecialaccount3())) {
	                    //科目
	                    voucherItem.setSubject(flowPay.getSupplier3());
	                    //科目说明
	                    Subjectdescribe = this.customerRefundItemJdbcDao.getSupplierDescByCustomerId(flowPay.getSupplier3(), bukrs);
	                }
					voucherItem.setSubjectdescribe(Subjectdescribe);
					//货币
					voucherItem.setCurrency(flowPay.getCollect().getBillcurrency());
					//货币金额
					voucherItem.setAmount(flowPay.getAmount3());
					//本位币金额
					voucherItem.setAmount2(flowPay.getStandardamount3());
					//到期日
					voucherItem.setZfbdt(collect.getDraftdate().replace("-", ""));
					//部门
					voucherItem.setDepid(flowPay.getDepid3());
					String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(flowPay.getCustomer3(),bukrs);
                    if ("D".equals(flowPay.getSpecialaccount3())) {
                        subject = this.customerRefundItemJdbcDao.getSubjectBySuppliers(flowPay.getSupplier3(), bukrs);
                    }
					subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
					//统驭项目
					voucherItem.setControlaccount(subject);
					//统驭科目说明
					voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
					//业务范围
					voucherItem.setGsber(flowPay.getDepid3());
					//文本
					voucherItem.setText(flowPay.getCollect().getText());
					//反记账标识
					if(flowPay.getAntiaccount3().equals("Y")){
						voucherItem.setUncheckflag("X");
					}
					
					voucherItemList.add(voucherItem);
				}
			}
		}
		
		return voucherItemList;
	}
	
	/**
	 * 得到收款的凭证抬头
	 * @param importPayment
	 * @return
	 */
	public Voucher getVoucher(Collect collect,String strClass){
		Voucher voucher = new Voucher();
		
		//业务编号
		voucher.setBusinessid(collect.getCollectid());
		//业务类型
		voucher.setBusinesstype("01");
		//过账日期
		voucher.setCheckdate(collect.getAccountdate().replace("-", ""));
		//公司代码
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());
		voucher.setCompanycode(bukrs); //公司代码
		//货币
		voucher.setCurrency(collect.getBillcurrency());
		//汇率
		voucher.setExchangerate(collect.getCollectrate());
		//会计期间
		voucher.setFiperiod(collect.getAccountdate().substring(5, 7));
		//会计年度
		voucher.setFiyear(collect.getAccountdate().substring(0, 4));
		//输入日期
		SimpleDateFormat  dateFormat=new SimpleDateFormat ("yyyyMMdd");
		voucher.setImportdate(dateFormat.format(new Date()));
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		//输入者
		voucher.setImporter(userContext.getUser().getUserName());
		//预制者
		voucher.setPreparer(userContext.getUser().getUserName());
		//凭证日期
		voucher.setVoucherdate(collect.getVoucherdate().replace("-", ""));
		//凭证抬头文本
		voucher.setVouchertext(collect.getText());
		//凭证类型
		voucher.setVouchertype("SA");
		//凭证分类
		voucher.setVoucherclass(strClass);
		
		return voucher;
	}
	
	/**
	 * 得到有汇损益的行项目信息
	 * @param importPayment
	 * @param subtractVlaue
	 * @param strType
	 * @return
	 */
	public VoucherItem getProfitAndLossVoucherItem(Collect collect,
			BigDecimal subtractVlaue,
			String strType){
		String bukrs = getCompanyIDByDeptID(collect.getDept_id());

		VoucherItem voucherItem = new VoucherItem();
		
		if (strType.equals("1")){
			//科目
			voucherItem.setSubject(collect.getCustomer());
			//科目说明
			String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(collect.getCustomer(),bukrs);
			voucherItem.setSubjectdescribe(Subjectdescribe);
			
			if (subtractVlaue.signum() == 1){
				//记帐码
				voucherItem.setCheckcode("15");
				voucherItem.setDebitcredit("H");
			}else{
				//记帐码
				voucherItem.setCheckcode("08");
				voucherItem.setDebitcredit("S");
			}
			
			String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(collect.getCustomer(),bukrs);
			subject = this.customerRefundItemJdbcDao.getSkont(voucherItem,subject);
			//统驭项目
			voucherItem.setControlaccount(subject);
			//统驭科目说明
			voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject,bukrs));
		}
		
		if (strType.equals("2")){
			String salesorder = "";
			Iterator<CollectItem> CollectItemit1 = collect.getCollectitem().iterator();		
			while(CollectItemit1.hasNext()){
				CollectItem collectItem = CollectItemit1.next();
				if(!StringUtils.isNullBlank(collectItem.getContract_no())){
					salesorder = this.collectCbillJdbcDao.getSapOrderNo(collectItem.getContract_no());
					break;
				}
			}
			
			if (subtractVlaue.signum() == 1){
				//记帐码
				voucherItem.setCheckcode("40");
				voucherItem.setDebitcredit("S");
				voucherItem.setSalesorder(salesorder);
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050301",bukrs);
				//科目
				voucherItem.setSubject("6603050301");
				//科目说明
				voucherItem.setSubjectdescribe(subjectname);
				//统驭项目
				voucherItem.setControlaccount("6603050301");
				//统驭科目说明
				voucherItem.setCaremark(subjectname);
			}else{
				//记帐码
				voucherItem.setCheckcode("50");
				voucherItem.setDebitcredit("H");
				voucherItem.setSalesorder(salesorder);
				String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050302",bukrs);
				//科目
				voucherItem.setSubject("6603050302");
				//科目说明
				voucherItem.setSubjectdescribe(subjectname);
				//统驭项目
				voucherItem.setControlaccount("6603050302");
				//统驭科目说明
				voucherItem.setCaremark(subjectname);
			}
		}
		
		//货币
		voucherItem.setCurrency(collect.getBillcurrency());
		//货币金额
		voucherItem.setAmount(new BigDecimal("0"));
		//本位币金额
		voucherItem.setAmount2(subtractVlaue.abs());
		//部门
		voucherItem.setDepid(this.sysDeptJdbcDao.getDeptCodeById(collect.getDept_id()));
		//文本
		voucherItem.setText(collect.getText());
		
		return voucherItem;
	}
	
	
	/**
	 * 根据部门ID获得公司ID
	 */
	public String getCompanyIDByDeptID(String deptID)
	{
		return this.sysDeptJdbcDao.getCompanyCode(deptID);
	}
	
	/**z
	 * 根据ID获取对象
	 * @param collectId
	 * @return
	 */
	public Collect getCollectById( String collectId )
	{
//		return this.collectHibernateDao.get(collectId);
		return collectJdbcDao.getCollectById(collectId);
	}
	
	/**
	 * 
	 * 保存
	 * 
	 * @param collect
	 */
	public void _saveOrUpdate(Collect collect,
			Set<CollectItem> deletedCollectItemSet,
			Set<CollectCbill> deletedCollectCbillSet,
			Set<CollectRelated> deletedCollectRelatedSet,
			Set<CollectBankItem> deletedCollectBankItemSet,
			BusinessObject businessObject) 
	{
		if (StringUtils.isNullBlank(collect.getCollectno())) {
			String collectno = NumberService.getNextObjectNumber("CollectNo",
					collect);
			collect.setCollectno(collectno);
		}
		if (StringUtils.isNullBlank(collect.getCollectid())) {
			_save(collect);
			
			/**
			 * 重分配的保存 
			 */
			if(collect.getBusinessstate().equals(BusinessState.REASSIGNED))
			{				
				/**
				 * 插入关联单据表
				 */
				this.reassignJdbcDao.insertCollectRelateForReassign(this.reassignJdbcDao.getReassignidByBoId(collect.getOldcollectid()), 
						collect.getCollectid(), collect.getOldcollectno(), collect.getActamount());
			}
		} else {
			_update(collect, deletedCollectItemSet, deletedCollectCbillSet,
					deletedCollectRelatedSet, deletedCollectBankItemSet,
					businessObject);
		}
	}
	
	
	/**
	 * 判断收款下的款和票是否可请
	 * @param collectId
	 * @return
	 */
	public void settleCollect( String collectId )
	{
//		Collect collect = this.collectHibernateDao.get(collectId);
		Collect collect = collectJdbcDao.getCollectById(collectId);
		//判断款
		Set collectitems = collect.getCollectitem();
		for (Iterator<CollectItem> iter = collectitems.iterator(); iter.hasNext();)
		{
			CollectItem collectItem = (CollectItem) iter.next();
			//更新保证金金额
			if(collect.getIncsuretybond().equals("Y")){
				collectItem.setActsuretybond(collectItem.getSuretybond());
				this.collectItemService._update(collectItem, null);
			}
			if((collectItem.getPrebillamount().compareTo(BigDecimal.valueOf(0))==0)
				&&(collectItem.getSuretybond().compareTo(BigDecimal.valueOf(0))==0))
			{
				//this.collectItemJdbcDao.updateCollectItemIsCleared(collectItem.getCollectitemid(), "1");
			}else{
				//this.collectItemJdbcDao.updateCollectItemIsCleared(collectItem.getCollectitemid(), "0");
			}
				
		}
		//判断票
		Set collectcbills = collect.getCollectcbill();
		for (Iterator<CollectCbill> iter = collectcbills.iterator(); iter.hasNext();)
		{
			CollectCbill collectCbill = (CollectCbill) iter.next();
			String billno = collectCbill.getBillno();
			
			CustomerTitle customerTitle = this.customerTitleJdbcDao.getByInvoice(billno);
			//已审批完的金额
			BigDecimal clearedAmount = this.customerTitleJdbcDao.getSumClearAmount(billno, "'3'");
			//发票金额
			BigDecimal billAmount = customerTitle.getDmbtr();
			if((clearedAmount.compareTo(billAmount)==0)
					||((clearedAmount.add(collectCbill.getClearamount())).compareTo(billAmount)==0))
			{
				this.customerTitleJdbcDao.updateCustomerTitleIsClearedByInvoice(billno, "1");
			}
		}
		//判断是否有出单发票号
		if(!StringUtils.isNullBlank(collect.getExport_apply_no())){
			this.exportJdbcDao.updateExportBillExamOfCollect(collect.getExport_apply_no(), collect.getCollectid(), collect.getCollectno());
		}
		
		//更新收款单的业务状态 为审批完成
		this.collectJdbcDao.updateCollect(collectId, "3");
		
		//保证金转货款金额处理
		if(!StringUtils.isNullBlank(collect.getOldcollectid())&&!StringUtils.isNullBlank(collect.getOldcollectitemid())){
			BigDecimal applyValue = collect.getApplyamount();
//			Collect oldCollect = this.collectHibernateDao.get(collect.getOldcollectid());
//			Collect oldCollect = collectJdbcDao.getCollectById(collect.getOldcollectid());
//			oldCollect.setRemainsuretybond(oldCollect.getRemainsuretybond().subtract(applyValue));
			CollectItem oldCollectItem = this.collectItemService._get(collect.getOldcollectitemid());
			BigDecimal actsuretybond = oldCollectItem.getActsuretybond().subtract(applyValue);
			oldCollectItem.setActsuretybond(actsuretybond);
			if(actsuretybond.compareTo(BigDecimal.ZERO)<=0)
				oldCollectItem.setSuretybondclear("1");	
			Collect oldCollect = oldCollectItem.getCollect();
			oldCollect.setRemainsuretybond(oldCollect.getRemainsuretybond().subtract(applyValue));
			this.collectHibernateDao.saveOrUpdate(oldCollect);
			this.collectHibernateDao.flush();
			this.collectItemService._saveOrUpdate(oldCollectItem, null);
		}
	}

	/**
	 * 保证金转货款，保存金额校验判断
	 * @param collect
	 * @return
	 */
	public String checkCollectDeposit(Collect collect){
		if(!StringUtils.isNullBlank(collect.getOldcollectitemid())){
			if(collect.getApplyamount().compareTo(collect.getConvertamount())!=0){
				return "申请收款金额和保证金转货款金额不相等！";
			}
			if(collect.getCollectitem().isEmpty())
				return "未选择分配行项目！";
			BigDecimal sum = BigDecimal.ZERO;
			for(Iterator<CollectItem> it = collect.getCollectitem().iterator();it.hasNext();){
				sum.add(it.next().getAssignamount());
			}
			if(sum.compareTo(collect.getApplyamount())!=0){
				return "保证金转货款金额和分配金额合计不相等！";
			}
			BigDecimal availableDeposit = collectJdbcDao.queryAvailableDepositValue(collect.getOldcollectitemid());
			if(collect.getApplyamount().compareTo(availableDeposit)>0){
				return "保证金转货款金额大于可转保证金金额！";
			}
		}
		return null;
	}
	
	public BigDecimal queryAvailableDepositValue(String oldcollectitemid){
		BigDecimal availableDeposit = collectJdbcDao.queryAvailableDepositValue(oldcollectitemid);
		return availableDeposit;
	}
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-17 
	 * 根据收款金额分配的行项目来判断该项目是否已经授信，若已经授信则反回授信类型
	 * @参数列表：
	 * projectId 项目ID
	 * @返回结果：
	 * 0 - 无授信
	 * 1 - 客户放货		(对应busType=1, creType=1)
	 * 2 - 客户代垫		(对应busType=1, creType=2)
	 * 3 - 客户放货+代垫	(对应busType=1, creType=3)
	 * 4 - 供应商授信	(对应busType=2, creType=1)
	 */
	public String checkProjCreditType(String projectNos){
		StringBuffer sb = new StringBuffer();
		String[] projNos = projectNos.split(",");
		for(int i=0; i<projNos.length; i++){
			List<Map<String,String>> projList = this.collectJdbcDao.getProjectCreditById(projNos[i]);
			if(null != projList && projList.size() > 0){
//				boolean flag1 = false;
//				boolean flag2 = false;
//				/*
//				 * 遍历项目列表(若为供应商则直接返回4,否则还要判断列表里是否有两条客户授信)
//				 */
//				for(int j=0; j<projList.size(); j++){
//					String busType = projList.get(j).get("BUSTYPE");
//					String creType = projList.get(j).get("CREDITTYPE");
//					if("1".equals(busType)){		// 若为客户授信
//						if("1".equals(creType)){			// 放货(先作标记)
//							flag1 = true;
//						}else if("2".equals(creType)){		// 代垫(先作标记)
//							flag2 = true;
//						}else{								// 放贷+代垫
//							flag1 = true;
//							flag2 = true;
//						}
//					}else{							// 否则供应商授信//						sb.append("4,");
//						break;
//					}	
//				}
//				/*
//				 * 到这一步说明结果可能是"放货"或"代垫"或"放货加代垫"
//				 */
//				if(flag1 && flag2){
//					sb.append("3,");	// 客户放贷+代垫
//					break;
//				}else if(flag1){	
//					sb.append("1,");	// 客户放货
//				}else if(flag2){
//					sb.append("2,");	// 客户代垫
//				}
				
				/*
				 * 遍历项目列表()
				 */
				for(int j=0; j<projList.size(); j++){
					String busType = projList.get(j).get("BUSTYPE");
					String creType = projList.get(j).get("CREDITTYPE");
					if("1".equals(busType)){		// 若为客户授信
						sb.append("客户");
						if("1".equals(creType)){			// 放货
							sb.append("放货 ");
						}else if("2".equals(creType)){		// 代垫(
							sb.append("代垫 ");
						}
					}else{							// 否则供应商授信
						sb.append("供应商授信 ");						
					}	
				}
				sb.append(",");
			}else{
				sb.append("无,");	
//				sb.append("0,");		// 无授信
			}
		}
//		return sb.deleteCharAt(projNos.length*2-1).toString();
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-09-21
	 * 判断"出单发票号"是否已经被关联
	 */
	public String getCollectnoByExportNo(String exportApplyNo){
		List<Map<String,String>> list = this.collectJdbcDao.getExportApplyNo(exportApplyNo);
		if(null == list || list.size()<=0){
			return "";
		}else{
			return list.get(0).get("COLLECTNO");
		}
	}
	
	public String getCountryByCustomer( Collect collect){
		return this.customerRefundItemJdbcDao.getCountryByCustomer(collect.getCustomer(), getCompanyIDByDeptID(collect.getDept_id()));
	}
	
	/**
	 * @创建作者：yanghancai
	 * @创建日期：2010-09-26
	 * 增加预确认功能
	 */	
	public void prepConfirmCollect(String collectid, String businessstate){
		//更新收款单的业务状态 为2
		this.collectJdbcDao.updateCollect(collectid,"2");
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-08
	 * 删除结算科目和纯资金
	 */
	public void deleteSettleFund(String collectId){
		this.collectJdbcDao.deleteSettleFund(collectId);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-11
	 * 根据银行帐号获取银行信息
	 */
	public String getBankInfoByAccount(String bankAccount){
		List<Map<String,String>> list = this.collectJdbcDao.getBankInfoByAccount(bankAccount);
		return list.get(0).get("BANK_NAME");
	}
	
	public String getBankIsPvaByAccount(String bankAccount){
		List<Map<String,String>> list = this.collectJdbcDao.getBankInfoByAccount(bankAccount);
		if(!list.isEmpty())
			return list.get(0).get("ISPVA");
		else
			return "0";
	}
	
	public Collect getCollectByNo(String collectNo){
		return this.collectHibernateDao.getCollectByNo(collectNo);
	}
	
	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-11
	 * 根据业务状态，取得发票已经审批完金额、在途金额。
	 */
	public BigDecimal getSumClearAmount(String invoice, String billClear, String singleClear, String collectState){
		return this.collectJdbcDao.getSumClearAmount(invoice, billClear, singleClear, collectState);
	}
	
	/**
	 * 调用存储过程UPDATE_COLLECTDEPOSIT
	 */
	public void updateCollectDeposit(){
		this.collectHibernateDao.getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				CallableStatement cs = session.connection().prepareCall("{call UPDATE_COLLECTDEPOSIT}");    
				
				boolean isSuccess = cs.execute();
				return isSuccess; 
			}
		});
	}
	/**
	 * 根据collectitemid取得在批的退款号
	 * @param collectItemid
	 * @param businessstate
	 * @return
	 */
	public String getRefundByCollectitemid(String collectItemid){
		return this.collectJdbcDao.getRefundByCollectitemid(collectItemid, BusinessState.ALL_COLLECT_ONROAD);
	}

    /**
     * @创建作者：邱杰烜
     * @创建日期：2011-05-18 
     * 更新收款子对象（收款银行、结算科目、纯资金往来）的Flag字段
     */
    public void updateCollectSubObjFlag(String businessId){
        this.collectJdbcDao.updateCollectSubObjFlag(businessId);
    }
    
    /**
     * @创建作者：邱杰烜
     * @创建日期：2011-05-30
     * 更新已生成并提交的贴现/议付凭证的“参考码”字段为1
     */
    public void updateDisNegVoucherState(String businessId){
        this.collectJdbcDao.updateDisNegVoucherState(businessId);
    }
    
	public void updateDate(Collect info ,String oldStr,String newStr,String userId){
		collectJdbcDao.updateDate(info ,oldStr,newStr,userId);
	}
	
	public Collect getCollectByCollectId(String collectId){
		return collectJdbcDao.getCollectByCollectId(collectId);
	}
	/* (non-Javadoc)
	 * @see com.infolion.xdss3.collectGen.service.CollectServiceGen#_get(java.lang.String)
	 */
	@Override
	public Collect _get(String id) {
		return collectJdbcDao.getCollectById(id);
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.collectGen.service.CollectServiceGen#_getDetached(java.lang.String)
	 */
	@Override
	public Collect _getDetached(String id) {
		return collectJdbcDao.getCollectById(id);
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.collectGen.service.CollectServiceGen#_getForEdit(java.lang.String)
	 */
	@Override
	public Collect _getForEdit(String id) {
		return collectJdbcDao.getCollectById(id);
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.collectGen.service.CollectServiceGen#_getEntityCopy(java.lang.String)
	 */
	@Override
	public Collect _getEntityCopy(String id) {
		Collect collect = new Collect();
		Collect collectOld = collectJdbcDao.getCollectById(id);
		try
		{
			BeanUtils.copyProperties(collect, collectOld);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		collect.setCollectno(null);
		// collect.setCollectid(null);
		collect.setProcessstate(" ");
		return collect;
	}
	
	public void dealOutToExcel(String sql,String strAuthSql ,ExcelObject excel){
		collectJdbcDao.dealOutToExcel(sql,strAuthSql ,excel);
	}
	
	public void dealOutToExcelSurety(String sql,String strAuthSql ,ExcelObject excel){
		collectJdbcDao.dealOutToExcelSurety(sql,strAuthSql ,excel);
	}
	
	public Collect getCollectBydraft(String draft, String collectid){
		if(StringUtils.isNullBlank(collectid)){
			return this.collectJdbcDao.getCustomerBydraft(draft);
		}else{
			return this.collectJdbcDao.getCollectBydraft(draft, collectid);
		}
	}
	 public String getTradeTypeByProjectno(String projectno){
		 return this.collectJdbcDao.getTradeTypeByProjectno(projectno);
	 }
}