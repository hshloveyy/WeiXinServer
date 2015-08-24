/*
 * @(#)CustomSingleOtherService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2012年06月08日 11点48分54秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClearOther.service;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.util.MultyGridData;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.xdss3.BusinessState;

import com.infolion.xdss3.masterData.dao.CustomerTitleOthersJdbcDao;


import com.infolion.xdss3.masterData.domain.CustomerTitleOthers;

import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.UnClearCollect;
import com.infolion.xdss3.singleClear.domain.UnClearCustomBill;
import com.infolion.xdss3.singleClearOther.domain.CustomSingleOther;
import com.infolion.xdss3.singleClearOther.service.CustomSingleOtherService;
import com.infolion.xdss3.singleClearOther.dao.CustomSingleOtherHibernateDao;
import com.infolion.xdss3.singleClearOther.dao.CustomSingleOtherJdbcDao;
import com.infolion.xdss3.singleClearOtherGen.service.CustomSingleOtherServiceGen;
          
import com.infolion.xdss3.singleClearOther.domain.SettleSubjectOther;
import com.infolion.xdss3.singleClearOther.service.SettleSubjectOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.FundFlowOther;
import com.infolion.xdss3.singleClearOther.service.FundFlowOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.UnCleaCollectOther;
import com.infolion.xdss3.singleClearOther.service.UnCleaCollectOtherService;
          
import com.infolion.xdss3.singleClearOther.domain.UnCustomBillOther;
import com.infolion.xdss3.singleClearOther.service.UnCustomBillOtherService;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;

/**
 * <pre>
 * 其他公司客户单清帐(CustomSingleOther)服务类
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
public class CustomSingleOtherService extends CustomSingleOtherServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	public CustomerTitleOthersJdbcDao customerTitleOthersJdbcDao;
	
	
	/**
	 * @param customerTitleOthersJdbcDao the customerTitleOthersJdbcDao to set
	 */
	public void setCustomerTitleOthersJdbcDao(
			CustomerTitleOthersJdbcDao customerTitleOthersJdbcDao) {
		this.customerTitleOthersJdbcDao = customerTitleOthersJdbcDao;
	}
	
	@Autowired
	public CustomSingleOtherJdbcDao customSingleOtherJdbcDao;
	
	/**
	 * @param customSingleOtherJdbcDao the customSingleOtherJdbcDao to set
	 */
	public void setCustomSingleOtherJdbcDao(
			CustomSingleOtherJdbcDao customSingleOtherJdbcDao) {
		this.customSingleOtherJdbcDao = customSingleOtherJdbcDao;
	}

	@Autowired
	private VoucherJdbcDao voucherJdbcDao;

	/**
	 * @param voucherJdbcDao
	 *            the voucherJdbcDao to set
	 */
	public void setVoucherJdbcDao(VoucherJdbcDao voucherJdbcDao)
	{
		this.voucherJdbcDao = voucherJdbcDao;
	}

	/**
	 * 根据选择的未清客户编号，科目，货币，公司代码,业务范围，查询出未清客户下所有未清发票信息。
	 */
	public JSONObject _queryUnClear(String custom, String subject ,String currencytext ,String companyno ,String depid)
	{
		// 根据客户，取得客户下未清发票数据集合
		List<CustomerTitleOthers> customerTitleList = this.getCustomerTitleList(custom, subject,currencytext,companyno,depid, "");
		// 未清应收（借方）
		List<UnCustomBillOther> unClearCustomBillList = new ArrayList<UnCustomBillOther>();
		// 未清收款（贷方）
		List<UnCleaCollectOther> unClearCollectList = new ArrayList<UnCleaCollectOther>();

		this.getUnClearInfo(customerTitleList, unClearCustomBillList, unClearCollectList);

		MultyGridData gridJsonData = new MultyGridData();
		gridJsonData.setData(unClearCustomBillList.toArray());
		gridJsonData.setData2(unClearCollectList.toArray());
		JSONObject jsonList = JSONObject.fromObject(gridJsonData);

		return jsonList;
	}
	
	private void getUnClearInfo(List<CustomerTitleOthers> customerTitleList, List<UnCustomBillOther> unClearCustomBillList, List<UnCleaCollectOther> unClearCollectList)
	{
		// 遍历所有客户下未清发票数据集合
		for (Iterator<CustomerTitleOthers> it = customerTitleList.iterator(); it.hasNext();)
		{
			CustomerTitleOthers customerTitle = (CustomerTitleOthers) it.next();

			String shkzg = customerTitle.getShkzg();
			if (StringUtils.isNullBlank(shkzg))
			{
				continue;
			}

			// 会计年度
			String fiYear = customerTitle.getGjahr();
			// 财务凭证号
			String voucherNo = customerTitle.getBelnr();
			// 公司代码
			String companyCode = customerTitle.getBukrs();
			String rowNumber = customerTitle.getBuzei();
			
			String customertitleid = customerTitle.getCustomertitleid();

			// 未清应收（借方）
			if (shkzg.equals("S")){
				String invoice = customerTitle.getInvoice();
				
					UnCustomBillOther unClearCustomBill = new UnCustomBillOther();
					unClearCustomBill.setBillno(customerTitle.getInvoice());
					unClearCustomBill.setCurrency(customerTitle.getWaers());
					unClearCustomBill.setVoucherno(customerTitle.getBelnr());
					unClearCustomBill.setProject_no(customerTitle.getBname());
					unClearCustomBill.setContract_no(customerTitle.getIhrez());
					unClearCustomBill.setOld_contract_no(" "); // 纸质合同号码
//					unClearCustomBill.setSap_order_no(customerTitle.getVgbel()); // sap订单号,销售凭证号
					unClearCustomBill.setText(customerTitle.getBktxt());
					unClearCustomBill.setAccountdate(customerTitle.getBudat());
					unClearCustomBill.setPaymentid(" ");
					unClearCustomBill.setPaymentitemid(" ");
					unClearCustomBill.setCustomertitleid(customertitleid);
					unClearCustomBill.setPaymentno(" ");
					unClearCustomBill.setBwbje(customerTitle.getBwbje());
					// 开票发票总金额,应付款.
					BigDecimal billamount = customerTitle.getDmbtr();
					unClearCustomBill.setReceivableamount(billamount);

					// 开票发票已经审批完的，发票已收款
					BigDecimal receivedamount = getSumClearAmountByBill(customertitleid.trim(), BusinessState.ALL_COLLECT_PAIDUP);
					unClearCustomBill.setReceivedamount(receivedamount);
					// 开票发票在途的，发票在途金额
					BigDecimal onroadamount = getSumClearAmountByBill(customertitleid.trim(), BusinessState.ALL_COLLECT_ONROAD);
					unClearCustomBill.setOnroadamount(onroadamount);
					
					// 未收款=开票发票金额-发票已收款-在批
					unClearCustomBill.setUnreceivableamou(billamount.subtract(receivedamount).subtract(onroadamount));
					//未收款为0不显示
					if(unClearCustomBill.getUnreceivableamou().compareTo(BigDecimal.valueOf(0)) <= 0 && unClearCustomBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) != 0){
						continue;
					}
					unClearCustomBill.setClearamount(unClearCustomBill.getUnreceivableamou());
					unClearCustomBill.setAdjustamount(BigDecimal.valueOf(0));
					//汇率
					BigDecimal rat = new BigDecimal(1);
					if(unClearCustomBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) != 0){
						rat = customerTitle.getBwbje().divide(billamount,5,BigDecimal.ROUND_HALF_EVEN);
						unClearCustomBill.setUnbwbje(unClearCustomBill.getUnreceivableamou().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
						
					}else{
						unClearCustomBill.setUnbwbje(unClearCustomBill.getBwbje());
					}
					unClearCustomBill.setExchangerate(rat);
					
					unClearCustomBillList.add(unClearCustomBill);
				
				
			// 未清收款（贷方）
			}else if (shkzg.equals("H")){				
					
					UnCleaCollectOther unClearCollect = new UnCleaCollectOther();
					unClearCollect.setAccountdate(customerTitle.getBudat());
					unClearCollect.setContract_no(customerTitle.getIhrez());
					unClearCollect.setProject_no(customerTitle.getBname());
					unClearCollect.setCurrency(customerTitle.getWaers());
					unClearCollect.setExchangerate(new BigDecimal(1));
					unClearCollect.setOld_contract_no(" ");
					unClearCollect.setCollectno(" ");
					unClearCollect.setCollectitemid(" ");
					unClearCollect.setCollectid(" ");
					unClearCollect.setBktxt(customerTitle.getBktxt());
					unClearCollect.setVoucherno(voucherNo);
					unClearCollect.setCustomertitleid(customertitleid);
					unClearCollect.setBwbje(customerTitle.getBwbje());
					// 金额
					BigDecimal goodsAmount = customerTitle.getDmbtr();
					unClearCollect.setAmount(goodsAmount);
					
					// 已清金额
					BigDecimal offsetamount = getSumClearAmountByCollect(customertitleid.trim(), BusinessState.ALL_COLLECT_PAIDUP);
					unClearCollect.setOffsetamount(offsetamount);
					// 在批金额
					BigDecimal onroadamount = getSumClearAmountByCollect(customertitleid.trim(),BusinessState.ALL_COLLECT_ONROAD);
					unClearCollect.setOnroadamount(onroadamount);
					// 未抵消金额, = 金额 - 已清金额-在批金额
					BigDecimal unoffsetamount = goodsAmount.subtract(offsetamount).subtract(onroadamount);
					unClearCollect.setUnoffsetamount(unoffsetamount);
					//未抵消金额为0不显示
					if(unClearCollect.getUnoffsetamount().compareTo(BigDecimal.valueOf(0)) <= 0 && unClearCollect.getAmount().compareTo(BigDecimal.valueOf(0)) != 0){
						continue;
					}
					//判断sap手工调汇率，是否有清账
					if(unClearCollect.getAmount().compareTo(BigDecimal.valueOf(0)) == 0 && customSingleOtherJdbcDao.getSapCollect(customertitleid.trim())){
						continue;
					}
					// 本次抵消金额
					BigDecimal nowclearamount = new BigDecimal(0);
					unClearCollect.setNowclearamount(unClearCollect.getUnoffsetamount());
					//汇率
					BigDecimal rat = new BigDecimal(1);
					if(unClearCollect.getAmount().compareTo(BigDecimal.valueOf(0)) != 0){
						rat = customerTitle.getBwbje().divide(goodsAmount,5,BigDecimal.ROUND_HALF_EVEN);
						unClearCollect.setUnbwbje(unClearCollect.getUnoffsetamount().multiply(rat).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else{
						unClearCollect.setUnbwbje(unClearCollect.getBwbje());
					}
					unClearCollect.setExchangerate(rat);
					
					unClearCollectList.add(unClearCollect);				


			}
		}
	}
	
		/**
		 * 根据业务状态、customertitleid，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。
		 * 
		 * @param customertitleid
		 * @param businessstates
		 * @return
		 */
		public BigDecimal getSumClearAmountByCollect(String customertitleid, String businessstates)
		{
			
			return this.customSingleOtherJdbcDao.getSumClearAmountByCollect(customertitleid, businessstates);
		}	
		
		/**
		 * 根据业务状态、customertitleid，取得票已经审批完金额(款已清金额)、在途金额(款在途金额)。
		 * 
		 * @param customertitleid
		 * @param businessstates
		 * @return
		 */
		public BigDecimal getSumClearAmountByBill(String customertitleid, String businessstates)
		{
			
			return this.customSingleOtherJdbcDao.getSumClearAmountByBill(customertitleid, businessstates);
		}	
		
	/**
	 * 取得未清数据集合
	 * @param custom
	 *  客户
	 * @param subject
	 * 清帐科目
	 * @param currencytext
	 * 货币文本
	 * @param companyno
	 * 公司代码	 
	 * @param shkzg
	 * * 业务范围
	 * @param depid
	 * 借方/贷方标识
	 * @return
	 */
	public List<CustomerTitleOthers> getCustomerTitleList(String custom, String subject,String currencytext ,String companyno,String depid, String shkzg)
	{
		return this.customerTitleOthersJdbcDao.getCustomerTitleListByCustom(custom, subject,currencytext,companyno,depid, shkzg);
	}
	
	/**
	 * 取得已生成凭证的ID
	 * @param businessId
	 * @return
	 */
	public String getVoucherId(String businessId){
		return this.voucherJdbcDao.getVoucherId(businessId);
	}
	
	
	/**
	 * 
	 * 保存
	 * 
	 * @param supplierSinglClear
	 */
	public void _saveOrUpdate(CustomSingleOther customSingleOther, BusinessObject businessObject)
	{
		// 删除所有1比多子对象数据。
		this.customSingleOtherJdbcDao.deleteCustomSingleClearUnderOneToManySubObject(customSingleOther.getCustomsclearid());
		customSingleOtherHibernateDao.saveOrUpdate(customSingleOther);

	}
	
	public void deleteCustomSingleClearUnderOneToManySubObject(String customsclearid){
		// 删除所有1比多子对象数据。
		this.customSingleOtherJdbcDao.deleteCustomSingleClearUnderOneToManySubObject(customsclearid);
	}
	
	public void _saveOrUpdate(CustomSingleOther customSingleOther){
		// 对id赋空值，使之执行insert操作(适应复制创建场景)
		Set<UnCustomBillOther> unClearCustomBillSet = customSingleOther.getUnCustomBillOther();
		Set<UnCustomBillOther> newUnClearCustomBillSet = null;
		//哪一边不能全清
//		String clearFlag = checkAllClear(customSingleClear);
//		//汇率
//		BigDecimal rate = getUnoffAmoutRate(customSingleClear);
		 
		if (null != unClearCustomBillSet)
		{
			newUnClearCustomBillSet = new HashSet();
			Iterator<UnCustomBillOther> itUnClearCustomBill = unClearCustomBillSet.iterator();
			while (itUnClearCustomBill.hasNext())
			{
				UnCustomBillOther unClearCustomBill = (UnCustomBillOther) itUnClearCustomBill.next();
				unClearCustomBill.setUnclearcusbillid(null);
				newUnClearCustomBillSet.add(unClearCustomBill);
			}
		}
		customSingleOther.setUnCustomBillOther(newUnClearCustomBillSet);

		Set<UnCleaCollectOther> unClearCollectSet = customSingleOther.getUnCleaCollectOther();
		Set<UnCleaCollectOther> newUnClearCollectSet = null;
		if (null != unClearCollectSet)
		{
			newUnClearCollectSet = new HashSet();
			Iterator<UnCleaCollectOther> itUnClearCollect = unClearCollectSet.iterator();
			while (itUnClearCollect.hasNext())
			{
				UnCleaCollectOther unClearCollect = (UnCleaCollectOther) itUnClearCollect.next();
				unClearCollect.setUnclearcollectid(null);
				newUnClearCollectSet.add(unClearCollect);
			}
		}
		customSingleOther.setUnCleaCollectOther(newUnClearCollectSet);	
		//生成单号
		if (StringUtils.isNullBlank(customSingleOther.getCustomclearno())) {
			String customClearNo = NumberService.getNextObjectNumber("customSingleOther",customSingleOther);			
			customSingleOther.setCustomclearno(customClearNo);
		}
		
		customSingleOtherHibernateDao.saveOrUpdate(customSingleOther);
	}
}