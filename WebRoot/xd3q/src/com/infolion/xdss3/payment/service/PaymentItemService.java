/*
 * @(#)PaymentItemService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年06月23日 20点09分20秒
 *  描　述：创建
 */
package com.infolion.xdss3.payment.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.payment.dao.PaymentItemJdbcDao;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.paymentGen.service.ImportPaymentItemServiceGen;

/**
 * <pre>
 * 付款金额分配表(PaymentItem)服务类
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
public class PaymentItemService extends ImportPaymentItemServiceGen
{

	@Autowired
	private PaymentItemJdbcDao paymentItemJdbcDao;

	/**
	 * @param paymentItemJdbcDao
	 *            the paymentItemJdbcDao to set
	 */
	public void setPaymentItemJdbcDao(PaymentItemJdbcDao paymentItemJdbcDao)
	{
		this.paymentItemJdbcDao = paymentItemJdbcDao;
	}

	/**
	 * 取得付款下合同
	 * 
	 * @param paymentId
	 * @return
	 */
	public String queryContractNoByPayMentId(String paymentId)
	{
		return this.paymentItemJdbcDao.queryContractNoByPayMentId(paymentId);
	}

	/**
	 * 取得付款下到单号
	 * 
	 * @param paymentId
	 * @return
	 */
	public String queryPickListNoByPayMentId(String paymentId)
	{
		return this.paymentItemJdbcDao.queryPickListNoByPayMentId(paymentId);
	}

	/**
	 * 取得到单下信用证号
	 * 
	 * @param paymentId
	 * @return
	 */
	public String queryLcNoByPickListNo(String paymentId)
	{
		return this.paymentItemJdbcDao.queryLcNoByPickListNo(paymentId);
	}
	
	public String querySupplier(String supplierNo){
		return this.paymentItemJdbcDao.querySupplier(supplierNo);
	}

	/**
	 * 根据供应商，取得未清付款行项信息。
	 * 
	 * @param project_no
	 * @param contract_no
	 * @return
	 */
	public List<ImportPaymentItem> getUnclearPaymentItemList(String supplier, String businessstates)
	{
		return this.paymentItemJdbcDao.getUnclearPaymentItemList(supplier, businessstates);
	}

	/**
	 * 根据paymentItemIds，取得未清付款行项信息。
	 * 
	 * @param paymentItemIds
	 * @return
	 */
	public List<ImportPaymentItem> getUnclearPaymentItemList(String paymentItemIds)
	{
		return this.paymentItemJdbcDao.getUnclearPaymentItemList(paymentItemIds);
	}

	/**
	 * 根据paymentId，取得未清付款行项信息。
	 * 
	 * @param paymentItemIds
	 * @return
	 */
	public List<ImportPaymentItem> getUnclearPaymentItemListByPaymentId(String paymentId)
	{
		return this.paymentItemJdbcDao.getUnclearPaymentItemListByPaymentId(paymentId);
	}

	/**
	 * 根据合同号取得原合同号(纸质合同号)
	 * 
	 * @param Contract_No
	 * @return
	 */
	public String getOldContractNo(String Contract_No)
	{
		return this.paymentItemJdbcDao.getOldContractNo(Contract_No);
	}

	/**
	 * 更新付款分配表 票清款结清标识
	 * 
	 * @param paymentItemId
	 * @return
	 */
	public void updateSettlePaymentItem(String paymentItemId)
	{
		this.importPaymentItemHibernateDao.updateSettlePaymentItem(paymentItemId);
	}

	/**
	 * 根据业务状态、付款分配行项ID，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。
	 * 
	 * @param paymentItemId
	 * @param businessstates
	 *            eg: '1','2'
	 * @return
	 */
	public BigDecimal getSumClearAmount(String paymentItemId, String businessstates)
	{
		return this.paymentItemJdbcDao.getSumClearAmount(paymentItemId, businessstates);
	}
	/**
	 * 根据业务状态、付款分配行项ID，取得款已经审批完金额(款已清金额)、在途金额(款在途金额)。去掉本次的
	 * 
	 * @param paymentItemId
	 * @param businessstates
	 *            eg: '1','2'
	 * @return
	 */
	public BigDecimal getSumClearAmount(String paymentItemId, String businessstates,String billclearid)
	{
		return this.paymentItemJdbcDao.getSumClearAmount(paymentItemId, businessstates,billclearid);
	}
	/**
	 * 更新付款分配表行项目是否已结清标志。
	 * 
	 * @param vendortitleid
	 * @param isCleared
	 */
	public void updatePaymentItemIsCleared(String paymentItemId, String isCleared)
	{
		this.paymentItemJdbcDao.updatePaymentItemIsCleared(paymentItemId, isCleared);
	}

	public ImportPaymentItem  get(String id){		
	   	  return importPaymentItemHibernateDao.get(id);	   
	}
	/**
	 * 根据付款itemid取得供应商退款纯代理金额合计
	 * @param paymentitemid
	 * @return
	 */
	public BigDecimal getRefundmentAmount(String paymentitemid){
		return paymentItemJdbcDao.getRefundmentAmount(paymentitemid);
	}
	
	/**
	 * 取得当前的本位币金额
	 * @param paymentitemid
	 * @return
	 */
	public BigDecimal getCurrAmount(String paymentid){
		return paymentItemJdbcDao.getCurrAmount(paymentid);
	}
	/**
	 * 取得当前的业务状态
	 * @param Paymentid
	 * @return
	 */
	public String getbussinessState(String paymentid){
		return paymentItemJdbcDao.getbussinessState(paymentid);
	}
	
	/**
	 * 判断是否是供应商退款纯代理
	 * @param paymentitemid 付款行项目ID
	 * @return true 为是，false为否
	 */
	public boolean isPureAgent(String paymentitemid){
		return paymentItemJdbcDao.isPureAgent(paymentitemid);
	}
}