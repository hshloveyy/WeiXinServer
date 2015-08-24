package com.infolion.xdss3.singleClear.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.infolion.xdss3.billclearcollect.domain.BillClearCollect;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.singleClear.domain.ClearVoucherItem;
import com.infolion.xdss3.singleClear.domain.CustomSingleClear;
import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.IParameter;
import com.infolion.xdss3.singleClear.domain.IParameterVoucher;
import com.infolion.xdss3.singleClear.domain.InfoVoucherObject;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * * <pre>
 * 客户全面清帐(ICustomerClearAccount)服务类接口
 * </pre>
 * 
 * @author zhongzh
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public interface ICustomerClearAccount extends IIsClearAccount{

	/**
	 * 第一步，有结算科目或纯资金凭证凭证要先出，不管能不能清账
	 * 第二步	 * 判断是否可以清账
	 * 第三步，外币要出汇损（2600公司本币是港币(HKD)，其他公司本币是人民币(CNY)）
	 * 第四步，更新isclear状态
	 * @param id 主表的id
	 * @param type 类型 1为单清账，2为票清收款 ，3为收款清票，4为退款
	 * @return
	 */
	public boolean isClearAccount(String id,String type);
	
	
	
	
	
	
	
	
	/**
	 * 
	 * 检查单清账的数据正确性()
	 *  * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。（如果有保证金的话，部分清的一定要在保证金这一行。）
	 * 3。保证清账的单借贷方相等
	 *4.(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	 * @param customSingleClear（重新取通过ID去数据库取得该对象）
	 *  @param marginAmount (差额)结算科目金额总计+纯资金往来金额总计的和
	 * @return false 数据错误，true 数据正确
	 */
	public IInfo checkClearData(CustomSingleClear customSingleClear,BigDecimal marginAmount,boolean isSave);
	/**
	 * 检查收款清账的数据正确性,
	 * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。（如果有保证金的话，部分清的一定要在保证金这一行。）
	 * 3。保证清账的单借贷方相等
	 * 4.收款，如果有清票，就不能有两条以上的保证金
	 * 
	 *  @param collect（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(Collect collect,BigDecimal marginAmount,boolean isSave);
	/**
	 * 检查票清收款清账的数据正确性
	 *  * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。（如果有保证金的话，部分清的一定要在保证金这一行。）
	 * 3。保证清账的单借贷方相等
	 * *4.(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	* @param marginAmount (差额)结算科目金额总计+纯资金往来金额总计的和
	 *  @param billClearCollect（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(BillClearCollect billClearCollect,BigDecimal marginAmount,boolean isSave);
	/**
	 * 检查退款清账的数据正确性
	 *  * 1。是否有相同的数据，
	 * 2。是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清。（如果有保证金的话，部分清的一定要在保证金这一行。）
	 * 3。保证清账的单借贷方相等
	 *  @param customerRefundment（重新取通过ID去数据库取得该对象）
	 * @return
	 */
	public IInfo checkClearData(CustomerRefundment customerRefundment,BigDecimal marginAmount,boolean isSave);
	/**判断是否能全清或部分清（只有两种情况）
	 * 
	 * 1.遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清收款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消收款 = 本次已抵消收款
	*	C. 借方的清账总金额 = 本次已抵消收款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消收款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param customSingleClear
	 * @return  true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(CustomSingleClear customSingleClear,BigDecimal marginAmount);
	/**
	 * 判断是否能全清或部分清（只有两种情况）
	 * 
	 * 遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清收款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消收款 = 本次已抵消收款
	*	C. 借方的清账总金额 = 本次已抵消收款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消收款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param collect
	 * @return  true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(Collect collect,BigDecimal marginAmount);
	/**
	 * 判断是否能全清或部分清（只有两种情况）
	 * 
	 * 1.遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	 * 2.A．贷方（票方），每一行的 未清收款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消收款 = 本次已抵消收款
	*	C. 借方的清账总金额 = 本次已抵消收款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消收款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param billClearCollect
	 * @return  true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(BillClearCollect billClearCollect,BigDecimal marginAmount);
	/**
	 * 判断是否能全清或部分清（只有两种情况）
	 * 
	 * 遍历寻找本次行项目有部分清的状态，还要去查找本次凭证以前部分清（审批通过，还有退款的审批通过）的金额合计，加上本次清账的金额是否等于总的金额（如应收款金额）,如果相等才能清账
	  * 2.A．贷方（票方），每一行的 未清收款金额 = 清账金额
	*	B．借方（款方），每一行的 未抵消收款 = 本次已抵消收款
	*	C. 借方的清账总金额 = 本次已抵消收款 总金额
	*	D．如果：贷方的清账总金额 不相等于 本次已抵消收款 总金额 那么
	*	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
	*	(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
	*	（1）。ABC 同时满足可以出清账凭证, 外币还要出汇损
	*	（2）。ABD同时满足可以出清账凭证，同时出调整差额的凭证，外币还要出汇损
	 * @param customerRefundment
	 * @return  true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(CustomerRefundment customerRefundment,BigDecimal marginAmount);
	/**
	 * 设置参数
	 * @param Collect
	 * @return
	 */
	public IParameterVoucher setParameter(Collect collect);
	/**
	 * 设置参数
	 * @param BillClearCollect
	 * @return
	 */
	public IParameterVoucher setParameter(BillClearCollect billClearCollect);
	/**
	 * 设置参数
	 * @param CustomerRefundment
	 * @return
	 */
	public IParameterVoucher setParameter(CustomerRefundment customerRefundment);
	/**
	 * 设置参数
	 * @param customSingleClear
	 * @return
	 */
	public IParameterVoucher setParameter(CustomSingleClear customSingleClear);
	
	/**
	 * 更新isclear的状态
	 */
	public void updateIsClear(String businessid);
	/**
	 * 更新部分清的isclear的状态
	 */
	public void updateIsClear(IInfoVoucher infoVoucher);
	/**
	 * 同步ycustomertitle时要判断收款是否被清账，更新收款行项目的isclear
	 */
	public void updateCollectItemIsClear(String companyCode, String voucherNo, String fiYear, String rowNumber);
	
	
	/**
	 * 取得客户清账凭证行项目
	 * @param List<IKeyValue> clearIdList , key 保存id,value 保存类型
	 * @return
	 */
	public Set<ClearVoucherItem> getClearVoucherItemByCustomer(List<IKeyValue> clearIdList);
	
	/**
	 * 取得客户部分清的单据ID信息
	 * @param infoVoucherObject
	 * @param infoVoucher
	 * @param clearid
	 * @param type2
	 * @return
	 */
	public List<IKeyValue> getPartClearByCustomer(IParameterVoucher infoVoucherObject,IInfoVoucher infoVoucher,String clearid,String type2);
	
	/**
	 * 取得清账凭证行项目
	 * @param clearIdList 所有的清账单据ID，key为ID，value为类型,参照ClearConstant
	 * @return
	 */
	public Set<VoucherItem> getClearVoucherItemByCustomer2(List<IKeyValue> clearIdList,Voucher voucher,String voucherid);
	/**
	 * 保存清账凭证
	 * @param parameterVoucher 参数对象
	 * @param infoVoucher  返回的信息对象
	 * @param clearid 当前的清账单据ID
	 * @param type2 类型，参照ClearConstant
	 * @return
	 */
	public Voucher saveClearVoucherByCustomer(ParameterVoucherObject parameterVoucher,IInfoVoucher infoVoucher,String clearid,String type2,boolean isp);
	
}
