/**
 * 
 */
package com.infolion.xdss3.singleClearOther.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.Constants;

import com.infolion.xdss3.masterData.dao.CustomerJdbcDao;

import com.infolion.xdss3.masterData.dao.CustomerTitleOthersJdbcDao;

import com.infolion.xdss3.masterData.domain.CustomerTitleOthers;

import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.domain.ClearVoucherItem;

import com.infolion.xdss3.singleClear.domain.IInfo;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.IParameterVoucher;
import com.infolion.xdss3.singleClear.domain.InfoObject;
import com.infolion.xdss3.singleClear.domain.InfoVoucherObject;
import com.infolion.xdss3.singleClear.domain.KeyValue;
import com.infolion.xdss3.singleClear.domain.ParameterVoucherObject;
import com.infolion.xdss3.singleClear.service.IsClearAccount;
import com.infolion.xdss3.singleClearOther.dao.CustomSingleOtherJdbcDao;
import com.infolion.xdss3.singleClearOther.domain.CustomSingleOther;
import com.infolion.xdss3.singleClearOther.domain.UnCleaCollectOther;
import com.infolion.xdss3.singleClearOther.domain.UnCustomBillOther;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 *  * * <pre>
 * 其他公司客户全面清帐(CustomerClearAccountOtherService)服务类
 * </pre>
 * 
 * @author zzh
 *
 */

@Component
public class CustomerClearAccountOtherService  extends IsClearAccount{
	
	
	
	@Autowired
	protected CustomerJdbcDao customerJdbcDao;
	
	public void setCustomerJdbcDao(
			CustomerJdbcDao customerJdbcDao) {
		this.customerJdbcDao = customerJdbcDao;
	}
	
	
	
	
	
	@Autowired
	private SysDeptJdbcDao sysDeptJdbcDao;

	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
	}
	
	
	@Autowired
	private CustomSingleOtherService customSingleOtherService;
	
	
	/**
	 * @param customSingleOtherService the customSingleOtherService to set
	 */
	public void setCustomSingleOtherService(
			CustomSingleOtherService customSingleOtherService) {
		this.customSingleOtherService = customSingleOtherService;
	}
	
	
	@Autowired
	private VoucherItemJdbcDao voucherItemJdbcDao;

	
	public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
		this.voucherItemJdbcDao = voucherItemJdbcDao;
	}
	@Autowired
	private CustomerTitleOthersJdbcDao customerTitleOthersJdbcDao;
	
	
	/**
	 * @param customerTitleOthersJdbcDao the customerTitleOthersJdbcDao to set
	 */
	public void setCustomerTitleOthersJdbcDao(
			CustomerTitleOthersJdbcDao customerTitleOthersJdbcDao) {
		this.customerTitleOthersJdbcDao = customerTitleOthersJdbcDao;
	}

	@Autowired
	private VoucherService voucherService;

	/**
	 * @param voucherService
	 *            the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService)
	{
		this.voucherService = voucherService;
	}
	
	@Autowired
	private CustomSingleOtherJdbcDao customSingleOtherJdbcDao;

	/**
	 * @param customSingleOtherJdbcDao the customSingleOtherJdbcDao to set
	 */
	public void setCustomSingleOtherJdbcDao(
			CustomSingleOtherJdbcDao customSingleOtherJdbcDao) {
		this.customSingleOtherJdbcDao = customSingleOtherJdbcDao;
	}
	
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
	 *  @param isSave 是否保存
	 * @return false 数据错误，true 数据正确
	 */
	public IInfo checkClearData(CustomSingleOther customSingleClear,BigDecimal marginAmount,boolean isSave){
		InfoObject infoObject =new InfoObject();
		//默认为true
		infoObject.setRight(true);
		
		Set<UnCustomBillOther> unClearCustomBills = customSingleClear.getUnCustomBillOther();
		Set<UnCleaCollectOther> unClearCollects = customSingleClear.getUnCleaCollectOther();	
		List<String> ids = new ArrayList<String>();
//		判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
//		判断有几条数据为部分清
		int i=0;
//		判断有几条数据为保证金
		int k=0;
//		
		boolean isa = false;
//		票的总额
		BigDecimal billAmount = new BigDecimal("0");
//		款的总额
		BigDecimal cpAmount = new BigDecimal("0");
//		调整金
		BigDecimal ajAmount = new BigDecimal("0");
//		有在批的单据也算部分清
		boolean isonload =false;
		for (UnCustomBillOther unClearCustomBill : unClearCustomBills){
			// 1。检查是否有相同的数据，
			if(!ids.contains(unClearCustomBill.getCustomertitleid())){
				ids.add(unClearCustomBill.getCustomertitleid());
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+infoObject.CODE_0+",Customertitleid=" + unClearCustomBill.getCustomertitleid());
				infoObject.setInfo(infoObject.CODE_0);
				infoObject.setRight(false);				
				return infoObject;
			}
		

			// 开票发票在途的，发票在途金额
			BigDecimal onroadamount = this.customSingleOtherService.getSumClearAmountByBill(unClearCustomBill.getCustomertitleid(), BusinessState.ALL_COLLECT_ONROAD);
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount = onroadamount.subtract(unClearCustomBill.getClearamount());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清,在批的也算部分清
			if(unClearCustomBill.getUnreceivableamou().compareTo(unClearCustomBill.getClearamount()) !=0 || onroadamount.compareTo(new BigDecimal("0")) > 0){
				billF = true;				
				i++;
				if(onroadamount.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			billAmount = billAmount.add(unClearCustomBill.getClearamount());
			ajAmount = ajAmount.add(unClearCustomBill.getAdjustamount());
		}
		
		ids = new ArrayList<String>();
		
		for (UnCleaCollectOther unClearCollect : unClearCollects){
			// 1。检查是否有相同的数据，
			if(!ids.contains(unClearCollect.getCustomertitleid())){
				ids.add(unClearCollect.getCustomertitleid());
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+infoObject.CODE_1+",Customertitleid=" + unClearCollect.getCustomertitleid());
				infoObject.setInfo(infoObject.CODE_1);
				infoObject.setRight(false);
				return infoObject;
			}
//			在批的
			BigDecimal onroadamount1 = new BigDecimal("0");
			
			onroadamount1 = customSingleOtherService.getSumClearAmountByCollect(unClearCollect.getCustomertitleid(), BusinessState.ALL_COLLECT_ONROAD);
			
//			如果已经保存了，去掉本次的
			if(isSave){
				onroadamount1 = onroadamount1.subtract(unClearCollect.getNowclearamount());
			}
			//2.是否能保每次清账一定要至少一边全清，并且部分清只能只有一行有余额未清,在批的也算部分清
			if(unClearCollect.getUnoffsetamount().compareTo(unClearCollect.getNowclearamount()) !=0 || onroadamount1.compareTo(new BigDecimal("0")) >0){
				clearF = true;			
				i++;		
				if(onroadamount1.compareTo(new BigDecimal("0")) !=0){
					isonload=true;
				}
			}
			//检查是否有几条保证金
			if(unClearCollect.getSuretybond().compareTo(new BigDecimal("0")) !=0){
				k++;
				if(unClearCollect.getUnoffsetamount().compareTo(unClearCollect.getNowclearamount()) !=0){
					isa = true;
				}
			}
			cpAmount = cpAmount.add(unClearCollect.getNowclearamount());
		}
		//两边没有一边全清
		if(billF && clearF){
			if(isonload){
				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_2 + "(有在批的单据)");
				infoObject.setInfo(infoObject.CODE_2  + "(有在批的单据)");
				infoObject.setRight(false);
				return infoObject;
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_2);
				infoObject.setInfo(infoObject.CODE_2);
				infoObject.setRight(false);
				return infoObject;
			}
		}
//		部分清只能只有一行有余额未清
		if(i>1){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_3);
			infoObject.setInfo(infoObject.CODE_3);
			infoObject.setRight(false);
			return infoObject;
		}
//		保证金只能有一条
		if(k>1){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_4);
			infoObject.setInfo(infoObject.CODE_4);
			infoObject.setRight(false);
			return infoObject;
		}
//		有保证金只能这一条有部分清(在判断的最后面)
		if(k==1 && !isa){
			log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_5);
			infoObject.setInfo(infoObject.CODE_5);
			infoObject.setRight(false);
			return infoObject;
		}
//		清账的单借贷方相等
		if(billAmount.compareTo(cpAmount) ==0){
			infoObject.setRight(true);
			return infoObject;
		}else{
//		清账的单借贷方不相等，	(贷方的清账总金额 = 本次已抵消收款 总金额 + 调整差额的合计 (有正有负))
//		(结算科目金额总计+纯资金往来金额总计的和=调整差额的合计值)
			if(ajAmount.compareTo(new BigDecimal("0")) !=0){
				if(billAmount.compareTo(cpAmount.add(ajAmount)) ==0 && ajAmount.compareTo(marginAmount) == 0  && marginAmount.compareTo(new BigDecimal("0")) !=0){
					infoObject.setRight(true);
					return infoObject;
				}else{
					log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_7);
					infoObject.setInfo(infoObject.CODE_7);
					infoObject.setRight(false);
					return infoObject;
				}
			}else{
				log.debug("客户清账检查单清账的数据正确性---"+ infoObject.CODE_6);
				infoObject.setInfo(infoObject.CODE_6);
				infoObject.setRight(false);
				return infoObject;
			}
		}
//		return infoObject;
	}
	
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
	 * @param customSingleClear
	 * @return true 为能全清，false 为部分清
	 */
	public IInfoVoucher isClearAccount(CustomSingleOther customSingleClear){
		InfoVoucherObject infoVoucherObject =new InfoVoucherObject();
		//默认为true
		infoVoucherObject.setRight(true);
//		默认能全清
		infoVoucherObject.setClear(true);
		Set<UnCustomBillOther> unClearCustomBills = customSingleClear.getUnCustomBillOther();
		Set<UnCleaCollectOther> unClearCollects = customSingleClear.getUnCleaCollectOther();
//		部分清的ID
		List<IKeyValue> billIdsByPart= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByPart= new ArrayList<IKeyValue>();
//		本次全清的ID
		List<IKeyValue> billIdsByAllClear= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIdsByAllClear= new ArrayList<IKeyValue>();
		
//		所有的ID
		List<IKeyValue> billIds= new ArrayList<IKeyValue>(); 
		List<IKeyValue> cpIds= new ArrayList<IKeyValue>(); 
//		是否出汇损
		boolean ispl = this.isProfitAndLoss(customSingleClear.getCurrencytext(), customSingleClear.getCompanyno());
		
		//款未抵消本位币金额合计
		BigDecimal sumUnoffsetamountBwb = new BigDecimal("0");
		//未收款本位币金额合计
		BigDecimal sumUnreceivableamouBwb = new BigDecimal("0");
		
		//清帐金额本位币合计
		BigDecimal sumclearAmountBwb = new BigDecimal("0");
		//本次抵消本位币金额合计
		BigDecimal sumoffsetAmountBwb = new BigDecimal("0");
		//判断哪一边全清
		boolean billF = false;
		boolean clearF = false;
		
		//合同号列表
		List<String> contractNoList = new ArrayList<String>();
		String projectNo =" ";
		
		// 重新计算未清金额(未收款、未付款等)
		for (UnCustomBillOther unClearCustomBill : unClearCustomBills){
			// 发票总金额
			BigDecimal billamount = unClearCustomBill.getReceivableamount();
			// 发票已经审批完的，发票已清金额
			BigDecimal receivedamount = this.customSingleOtherService.getSumClearAmountByBill(unClearCustomBill.getCustomertitleid(), BusinessState.ALL_COLLECT_PAIDUP);
//			所有的清账金额
			BigDecimal allClearAmount = unClearCustomBill.getClearamount().add(receivedamount);	
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(unClearCustomBill.getCustomertitleid());
			
			
			
//			等于（全清）
			if(billamount.compareTo(allClearAmount) == 0){
				billIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(billamount.compareTo(allClearAmount) == -1){
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + unClearCustomBill.getVoucherno());
				return infoVoucherObject;
			}
//			大于(是本次部分清)
			if(billamount.compareTo(allClearAmount) == 1){
				infoVoucherObject.setClear(false);
				billF = true;							
			}
//			找部分清的数据
			if(receivedamount.compareTo(new BigDecimal("0")) !=0){
				billIdsByPart.add(keyValue);	
			}
			sumUnoffsetamountBwb = sumUnoffsetamountBwb.add(unClearCustomBill.getUnbwbje());
			if(unClearCustomBill.getReceivableamount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumclearAmountBwb = sumclearAmountBwb.add(unClearCustomBill.getBwbje());
			}else{
				sumclearAmountBwb = sumclearAmountBwb.add(unClearCustomBill.getBwbje().multiply(unClearCustomBill.getClearamount()).divide(unClearCustomBill.getReceivableamount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(unClearCustomBill.getContract_no());
			if(!StringUtils.isNullBlank(unClearCustomBill.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = unClearCustomBill.getProject_no();
			}
			//加入本次票的ID
			billIds.add(keyValue);			
		}
		
		
		
		for (UnCleaCollectOther unClearCollect : unClearCollects){
			// 款总金额
			BigDecimal goodsamount = unClearCollect.getAmount();
			// 收款单已审批完的 ，已清票款
			BigDecimal clearedPaymentAmount = this.customSingleOtherService.getSumClearAmountByCollect(unClearCollect.getCustomertitleid(), BusinessState.ALL_COLLECT_PAIDUP);
			// 所有的清账金额	
			BigDecimal allClearAmount = unClearCollect.getNowclearamount().add(clearedPaymentAmount);
			
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(unClearCollect.getCustomertitleid());
			
			
			
//			等于（全清）
			if(goodsamount.compareTo(allClearAmount) ==0){	
				cpIdsByAllClear.add(keyValue);
			}
//			小于（数据错误）
			if(goodsamount.compareTo(allClearAmount) == -1){	
				infoVoucherObject.setRight(false);
				infoVoucherObject.setInfo(infoVoucherObject.CODE_8 + unClearCollect.getVoucherno());
				return infoVoucherObject;			
			}
//			大于(是部分清)
			if(goodsamount.compareTo(allClearAmount) == 1){	
				infoVoucherObject.setClear(false);
				clearF = true;
			}
//			找部分清的数据
			if(clearedPaymentAmount.compareTo(new BigDecimal("0")) !=0){
				cpIdsByPart.add(keyValue);
			}
			sumUnreceivableamouBwb= sumUnreceivableamouBwb.add(unClearCollect.getUnbwbje());
			if(unClearCollect.getAmount().compareTo(BigDecimal.valueOf(0)) ==0){
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(unClearCollect.getBwbje());
			}else{
				sumoffsetAmountBwb = sumoffsetAmountBwb.add(unClearCollect.getBwbje().multiply(unClearCollect.getNowclearamount()).divide(unClearCollect.getAmount(),13,BigDecimal.ROUND_HALF_EVEN));
			}
			contractNoList.add(unClearCollect.getContract_no());
			if(!StringUtils.isNullBlank(unClearCollect.getProject_no()) && StringUtils.isNullBlank(projectNo)){
				projectNo = unClearCollect.getProject_no();
			}
			//加入本次款的ID
			cpIds.add(keyValue);			
		}
		infoVoucherObject.setBillIdsByPart(billIdsByPart);
		infoVoucherObject.setCpIds(cpIds);
		infoVoucherObject.setCpIdsByPart(cpIdsByPart);
		infoVoucherObject.setBillIds(billIds);
		infoVoucherObject.setBillIdsByAllClear(billIdsByAllClear);
		infoVoucherObject.setCpIdsByAllClear(cpIdsByAllClear);
//		加入本次单据的ID
		List<String> ids = new ArrayList<String>();
		ids.add(customSingleClear.getCustomsclearid());
		infoVoucherObject.setIds(ids);
//		计算汇损差额
		if(ispl){
			BigDecimal subtractVlaue2 = this.getMarginByprofitAndLoss(clearF, billF, sumUnreceivableamouBwb, sumUnoffsetamountBwb, sumoffsetAmountBwb, sumclearAmountBwb);
			infoVoucherObject.setSubtractVlaue(subtractVlaue2);
			String taxCode = this.getTaxCode(contractNoList, projectNo);
			infoVoucherObject.setTaxCode(taxCode);
		}
		
		return infoVoucherObject;
	}
	
	
	/**
	 * 更新isclear的状态
	 */
	public void updateIsClear(String businessid){
		List<VoucherItem> voucherItems = voucherItemJdbcDao.getVoucherItems(businessid);
		
		for(VoucherItem voucherItem : voucherItems){
//			如果不是清账凭证就不更新
			if(!"A".equals(voucherItem.getVoucher().getBstat()) && !"9".equals(voucherItem.getVoucher().getVoucherclass())){
				return ;
			}
			// 公司代码
			String companyCode = voucherItem.getVoucher().getCompanycode();
			// 财务凭证号
			String voucherNo =voucherItem.getVoucher().getVoucherno();
			// 会计年度
			String fiYear =voucherItem.getFiyear();
			//行项目
			String rowNumber = voucherItem.getRownumber();
			if(rowNumber.length()==2)rowNumber="0" + rowNumber;			
			if(rowNumber.length()==1)rowNumber="00" + rowNumber;
			log.debug("rowNumber:" + rowNumber);
			CustomerTitleOthers customerTitle = customerTitleOthersJdbcDao.getCustomerTitle(companyCode, voucherNo, fiYear, rowNumber);
			if(null != customerTitle){
				this.customerTitleOthersJdbcDao.updateCustomerTitleIsCleared(customerTitle.getCustomertitleid(), Constants.cleared.sapIsCleared);				
			}
			
		}
	}
	/**
	 * 更新部分清的isclear的状态
	 */
	public void updateIsClear(IInfoVoucher infoVoucher){
//		取出本次全清的数据
		for(IKeyValue keyValue:infoVoucher.getBillIdsByAllClear()){			
			String customertitleid = keyValue.getKey();			
			this.customerTitleOthersJdbcDao.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.isCleared);
		}
		for(IKeyValue keyValue:infoVoucher.getCpIdsByAllClear()){			
			String customertitleid = keyValue.getKey();			
			this.customerTitleOthersJdbcDao.updateCustomerTitleIsCleared(customertitleid, Constants.cleared.isCleared);
						
		}
	}
	
	/**
	 * 保存清账凭证
	 * @param parameterVoucher 参数对象
	 * @param infoVoucher  返回的信息对象
	 * @param clearid 当前的清账单据ID
	 * @param type2 类型，参照ClearConstant
	 * @return
	 */
	public Voucher saveClearVoucher(ParameterVoucherObject parameterVoucher,IInfoVoucher infoVoucher,String clearid,String type2,boolean isp){
//		所有的清账voucherclass都是9
		parameterVoucher.setVoucherclass("9");
		Voucher voucher = this.getClearVoucher(parameterVoucher);
		List<IKeyValue>  clearIdList = this.getPartClearByCustomer(parameterVoucher, infoVoucher, clearid, type2);
		if(null == clearIdList)return null;
		Set<VoucherItem> viSet =this.getClearVoucherItemByCustomer2(clearIdList,voucher,parameterVoucher.getVoucherid());

//		是否出汇损,并且差额不为0
		if(isp && parameterVoucher.getSubtractVlaue().compareTo(new BigDecimal("0"))!= 0){
			
			parameterVoucher.setRownumber("001");
			parameterVoucher.setStrType("1");
			VoucherItem voucherItem1 = this.getProfitAndLossVoucherItem(parameterVoucher);
			voucherItem1.setVoucher(voucher);
			viSet.add(voucherItem1);			
			parameterVoucher.setRownumber("002");
			parameterVoucher.setStrType("2");
			VoucherItem voucherItem2 = this.getProfitAndLossVoucherItem(parameterVoucher);
			voucherItem2.setVoucher(voucher);
			viSet.add(voucherItem2);
		}
		voucher.setVoucherItem(viSet);
		//判断是否已经生成清账凭证，
		Voucher clearVoucher2=null;
		List<Voucher> li=voucherHibernateDao.getVoucherByBusinessId2(parameterVoucher.getBusinessid(),"A");
		if(li!=null && li.size()!=0){				
			for(Voucher vo : li){
				if(!StringUtils.isNullBlank(vo.getVoucherno()) && "9".equals(vo.getVoucherclass())){
					clearVoucher2=vo;
					break;
				}
			} 
		}
		if(null == clearVoucher2){
			this.voucherService._saveOrUpdate(voucher, null, BusinessObjectService.getBusinessObject("Voucher"));
		}else{
			voucher = clearVoucher2;
		}
		
		return voucher;
	}
	
	/**
	 * 
	 * 取得客户部分清的单据ID信息
	 * @return
	 */	 
	public List<IKeyValue> getPartClearByCustomer(IParameterVoucher infoVoucherObject,IInfoVoucher infoVoucher,String clearid,String type2){
		
//		保存清账的ID号
		List<IKeyValue> clearIdList = new ArrayList<IKeyValue>();

		KeyValue keyValue2 = new KeyValue(); 
		keyValue2.setKey(clearid);
		keyValue2.setValue(type2);
		clearIdList.add(keyValue2);
		InfoObject infoObject = new InfoObject();
		infoObject.setRight(true);

		List<IKeyValue> partIdsList = new ArrayList<IKeyValue>();
		partIdsList.addAll(infoVoucher.getCpIdsByPart());
		partIdsList.addAll(infoVoucher.getBillIdsByPart());
//		保存已经找过的票或款id
		List<String> titleIdList = new ArrayList<String>();
		List<String> collectitemOrBillnoList = new ArrayList<String>();
		for(IKeyValue keyValue : partIdsList){
//		key保存titleid
			String titleid = keyValue.getKey();						
			titleIdList.add(titleid);			
			getCustomerClearPart(titleid,clearid,clearIdList,titleIdList,collectitemOrBillnoList,infoObject);
		}
//		判断是否正确
		if(infoObject.isRight()){
			return clearIdList;
		}else{
			return null;
		}		
	}
	
	/***
	 * 循环找到所有客户有部分清的数据
	 * @param titleid
	 * @param clearid
	 * @param clearIdList
	 */
	public void getCustomerClearPart(String titleid,String clearid,List<IKeyValue> clearIdList,List<String> titleIdList,List<String> collectitemOrBillnoList,InfoObject infoObject){
		
//		取得除本次以外的款方有部分清的，客户清账ID 
		List<IKeyValue> clearList = customSingleOtherJdbcDao.getPartIdsByCustomerClear(titleid, clearid);
		for(IKeyValue keyValue2 : clearList){
			String clid = keyValue2.getKey();			
			String type2= keyValue2.getValue();
//			如果有在clearIdList说明有跟以前的单据重复，这是不允许的
			if(this.isSame(clid, clearIdList)){
				infoObject.setRight(false);
				infoObject.setInfo(infoObject.CODE_9 + clid);
				infoObject.setCode(clid);
				log.debug(infoObject.CODE_9 + clid);
				return;
			}else{
				KeyValue keyValue3 = new KeyValue(); 
				keyValue3.setKey(clid);
				keyValue3.setValue(type2);
				clearIdList.add(keyValue3);
			}			
//			取得本次有部分清的，款行项目titleID, IKeyValue key 为titleid,value 为collectitemID,value2 为客户单清账ID
			List<IKeyValue> kvList = customSingleOtherJdbcDao.getPartIdsByCustomerClear(clid);
			for(IKeyValue keyValue :kvList){
				String titleid2 = keyValue.getKey();						
//				如果有在titleIdList.CollectitemOrBillnoList说明有跟以前的有找过了，不用再找
				if(titleIdList.contains(titleid2) ){
					continue;
				}else{
					titleIdList.add(titleid2);					
				}
				getCustomerClearPart(titleid2,keyValue.getValue2(),clearIdList,titleIdList,collectitemOrBillnoList,infoObject);
			}
		}
		
	}

	/**
	 * 取得清账凭证行项目
	 * @param clearIdList 所有的清账单据ID，key为ID，value为类型,参照ClearConstant
	 * @param voucher
	 * @param voucherid 结算科目的凭证ID
	 * @return
	 */
	public Set<VoucherItem> getClearVoucherItemByCustomer2(List<IKeyValue> clearIdList,Voucher voucher,String voucherid){
		Set<ClearVoucherItem> cviSet = this.getClearVoucherItemByCustomer(clearIdList);
		return this.getClearVoucherItems(cviSet,voucher,voucherid);		
	}
	
	/**
	 * 取得清账凭证行项目
	 * @return
	 */
	public Set<ClearVoucherItem> getClearVoucherItemByCustomer(List<IKeyValue> clearIdList){
		
		
		StringBuffer singleSb = new StringBuffer();	

		
		String singleids ="";
		String ids ="";
		for(IKeyValue keyValue:clearIdList){
			String clid = keyValue.getKey();
			String type = keyValue.getValue();
			
			if(ClearConstant.SINGLE_TYPE.equals(type)){
				if(StringUtils.isNullBlank(singleids)){
					singleids=clid;
				}else{
					singleids= singleids +  "','" + clid ;
				}
			}
			
			if(StringUtils.isNullBlank(ids)){
				ids=clid;
			}else{
				ids= ids + "','" + clid ;
			}
			
		}	
//		查找款方
		
		singleSb.append("SELECT uc.customertitleid FROM YUNCOLLECTOTHER uc WHERE uc.customsclearid IN ( '");
		
		singleSb.append(singleids + "')");
//		为了去掉重复
		Set<ClearVoucherItem> viSet = new HashSet<ClearVoucherItem>();
		if(!StringUtils.isNullBlank(singleids)){
			List<ClearVoucherItem> sList = customSingleOtherJdbcDao.getVoucherItemByTitle(singleSb.toString());
			viSet.addAll(sList);
		}

//		清空,要通过使用sbi.setLength(0);来清空StringBuffer对象中的内容效率最高。
	
		singleSb.setLength(0);	
//		查找票方
		singleSb.append("SELECT ub.customertitleid FROM YUNCUSTBILLOTHER ub WHERE ub.customsclearid IN ( '");
		singleSb.append(singleids + "')");
		
		
		if(!StringUtils.isNullBlank(singleids)){
			List<ClearVoucherItem> sList = customSingleOtherJdbcDao.getVoucherItemByTitle(singleSb.toString());
			viSet.addAll(sList);
		}
//		查找外围生成的汇损和调整金(有可能包含本次的已经生成的调整金,通过businessid来判断)
		List<ClearVoucherItem> pList =customSingleOtherJdbcDao.getProfitAndLossByPart(ids);
		viSet.addAll(pList);		
		return viSet;
	}
	
	/*  设置参数
	 * @see com.infolion.xdss3.singleClear.service.ICustomerClearAccount#setParameter(com.infolion.xdss3.singleClear.domain.CustomSingleClear)
	 */
	public ParameterVoucherObject setParameter(CustomSingleOther customSingleClear) {		
		
		ParameterVoucherObject parameterVoucher = new ParameterVoucherObject();
		parameterVoucher.setAccountdate(customSingleClear.getAccountdate());
		parameterVoucher.setBukrs(customSingleClear.getCompanyno());
		parameterVoucher.setBusinessid(customSingleClear.getCustomsclearid());
		parameterVoucher.setBusinesstype("08");
		parameterVoucher.setCheckCode("");
		parameterVoucher.setCheckCode2("");
		parameterVoucher.setCurrencytext(customSingleClear.getCurrencytext());
		String Custom_htext = customerJdbcDao.getCustomerName(customSingleClear.getCustom());
		parameterVoucher.setCustom_htext(Custom_htext);
		parameterVoucher.setCustomer(customSingleClear.getCustom());
		parameterVoucher.setDebitcredit("");
		parameterVoucher.setFundFlow(customSingleClear.getFundFlowOther());
		parameterVoucher.setGsber(customSingleClear.getDepid());
		parameterVoucher.setRownumber("");
		parameterVoucher.setSettleSubject(customSingleClear.getSettleSubjectOther());
		parameterVoucher.setStrType("");
		parameterVoucher.setSubtractVlaue(new BigDecimal("0"));
		
		Set<UnCustomBillOther> unClearCustomBills = customSingleClear.getUnCustomBillOther();	
		BigDecimal sumAdjustamount= new BigDecimal("0");
		// 计算调整金之和
		for (UnCustomBillOther unClearCustomBill : unClearCustomBills){
			sumAdjustamount = sumAdjustamount.add(unClearCustomBill.getAdjustamount());
		}
		parameterVoucher.setSumAdjustamount(sumAdjustamount);
		parameterVoucher.setSupplier("");
		parameterVoucher.setSupplier_htext("");
		//String taxCode = customSingleClearService.getTaxCode(customSingleClear);
//		区别出损汇凭证
		parameterVoucher.setTaxCode("00001");
		parameterVoucher.setText(customSingleClear.getText());
//		parameterVoucher.setVoucher();
		parameterVoucher.setVoucherclass("1");
		parameterVoucher.setVoucherdate(customSingleClear.getVoucherdate());
		parameterVoucher.setVouchertype("SA");		
		return parameterVoucher;
	}	
	
}
