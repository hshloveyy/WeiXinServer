package com.infolion.xdss3.singleClear.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.billclearcollect.domain.BillClearCollect;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.ImportPayment;

import com.infolion.xdss3.singleClear.domain.ClearVoucherItem;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.IParameterVoucher;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.domain.SupplierSinglClear;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * * <pre>
 * 供应商全面清帐(SupplierClearAccountService)服务类接口
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public interface ISupplierClearAccount extends IIsClearAccount{

	/**
	 * 第一步，有结算科目或纯资金凭证凭证要先出，不管能不能清账
	 * 第二步	 * 判断是否可以清账
	 * 第三步，外币要出汇损（2600公司本币是港币(HKD)，其他公司本币是人民币(CNY)）
	 * 第四步，更新isclear状态
	 * @param id 主表的id
	 * @param type 类型 1为单清账，2为票清付款 ，3为付款清票，4为退款
	 * @return
	 */
	public boolean isClearAccount(String id,String type);;
	
	

	
	/**
	 * 更新isclear的状态
	 */
	public void updateIsClear(String businessid);
	/**
	 * 同步ycustomertitle时要判断付款是否被清账，更新付款行项目的isclear
	 */
	public void updateIsClear(IInfoVoucher infoVoucher);
	
	/**
	 * 
	 * 检查单清账的数据正确性()
	 *  * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。
	 * 3。保证清账的单借贷方相等
	 *4.(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*@param marginAmount (差额)结算科目金额总计+纯资金往来金额总计的和
	 * @param supplierSinglClear（重新取通过ID去数据库取得该对象）
	 * @return false 数据错误，true 数据正确
	 */
	public IInfo checkClearData(SupplierSinglClear supplierSinglClear,BigDecimal marginAmount,boolean isSave);
	/**
	 * 检查付款清账的数据正确性,
	 * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。
	 * 3。保证清账的单借贷方相等
	 * 
	 * 
	 *  @param payment（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(HomePayment payment,BigDecimal marginAmount,boolean isSave);
	public IInfo checkClearData(ImportPayment payment,BigDecimal marginAmount,boolean isSave);
	/**
	 * 检查票清付款清账的数据正确性
	 *  * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。
	 * 3。保证清账的单借贷方相等
	 * *4.(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*@param marginAmount (差额)结算科目金额总计+纯资金往来金额总计的和
	 *  @param billClearPayment（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(BillClearPayment billClearPayment,BigDecimal marginAmount,boolean isSave);
	/**
	 * 检查退款清账的数据正确性
	 *  * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。
	 * 3。保证清账的单借贷方相等
	 *  @param supplierRefundment（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(SupplierRefundment supplierRefundment,BigDecimal marginAmount,boolean isSave);
	/**
	 *  判断是否能全清或部分清（只有两种情况）
	 *  
	 * 1.遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清付款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消付款 = 本次已抵消付款
	*	C. 借方的清账总金额 = 本次已抵消付款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消付款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param supplierSinglClear
	 * @return   true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(SupplierSinglClear supplierSinglClear,BigDecimal marginAmount);
	/**
	 *  判断是否能全清或部分清（只有两种情况）
	 *  
	 * 遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清付款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消付款 = 本次已抵消付款
	*	C. 借方的清账总金额 = 本次已抵消付款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消付款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param payment
	 * @return   true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(HomePayment payment,BigDecimal marginAmount);
	
	public IInfoVoucher isClearAccount(ImportPayment payment,BigDecimal marginAmount);
	/**
	 *  判断是否能全清或部分清（只有两种情况）
	 *  
	 * 1.遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	 * 2.A．贷方（票方），每一行的 未清付款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消付款 = 本次已抵消付款
	*	C. 借方的清账总金额 = 本次已抵消付款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消付款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param billClearPayment
	 * @return   true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(BillClearPayment billClearPayment,BigDecimal marginAmount);
	/**
	 *  判断是否能全清或部分清（只有两种情况）
	 *  
	 * 遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清付款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消付款 = 本次已抵消付款
	*	C. 借方的清账总金额 = 本次已抵消付款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消付款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消付款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param supplierRefundment
	 * @return   true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(SupplierRefundment supplierRefundment,BigDecimal marginAmount);
	
	/**
	 * 设置参数
	 * @param HomePayment
	 * @return
	 */
	public ParameterVoucherObject setParameter(HomePayment payment);
	/**
	 * 设置参数
	 * @param ImportPayment
	 * @return
	 */
	public ParameterVoucherObject setParameter(ImportPayment payment);
	/**
	 * 设置参数
	 * @param billClearPayment
	 * @return
	 */
	public ParameterVoucherObject setParameter(BillClearPayment billClearPayment);
	/**
	 * 设置参数
	 * @param supplierRefundment
	 * @return
	 */
	public ParameterVoucherObject setParameter(SupplierRefundment supplierRefundment);
	/**
	 * 设置参数
	 * @param supplierSinglClear
	 * @return
	 */
	public ParameterVoucherObject setParameter(SupplierSinglClear supplierSinglClear);
	
	/**
	 * 取得供应商清账凭证行项目
	 * @param List<IKeyValue> clearIdList , key 保存id,value 保存类型
	 * @return
	 */
	public Set<ClearVoucherItem> getClearVoucherItemBySupplier(List<IKeyValue> clearIdList);
	
	
	/**
	 * 取得供应商部分清的单据ID信息
	 * @param infoVoucherObject
	 * @param infoVoucher
	 * @param clearid
	 * @param type2
	 * @return
	 */
	public List<IKeyValue> getPartClearBySupplier(IParameterVoucher infoVoucherObject,IInfoVoucher infoVoucher,String clearid,String type2);
	
	/**
	 * 取得清账凭证行项目
	 * @param clearIdList 所有的清账单据ID，key为ID，value为类型,参照ClearConstant
	 * @return
	 */
	public Set<VoucherItem> getClearVoucherItemBySupplier2(List<IKeyValue> clearIdList,Voucher voucher,String voucherid);
	/**
	 * 保存清账凭证
	 * @param parameterVoucher 参数对象
	 * @param infoVoucher  返回的信息对象
	 * @param clearid 当前的清账单据ID
	 * @param type2 类型，参照ClearConstant
	 * @return
	 */
	public Voucher saveClearVoucherBySupplier(ParameterVoucherObject parameterVoucher,IInfoVoucher infoVoucher,String clearid,String type2,boolean isp);
	
	
	
}
