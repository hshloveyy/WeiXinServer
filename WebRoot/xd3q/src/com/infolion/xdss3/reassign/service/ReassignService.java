/*
 * @(#)ReassignService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月13日 09点39分00秒
 *  描　述：创建
 */
package com.infolion.xdss3.reassign.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.sapss.bapi.ConnectManager;
import com.infolion.sapss.bapi.ExtractSapData;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.Constants.cleared;
import com.infolion.xdss3.billClear.dao.BillClearPaymentHibernateDao;
import com.infolion.xdss3.billClear.domain.BillClearPayment;
import com.infolion.xdss3.billClear.service.BillClearPaymentService;
import com.infolion.xdss3.billclearcollect.dao.BillClearCollectHibernateDao;
import com.infolion.xdss3.billclearcollect.domain.BillClearCollect;
import com.infolion.xdss3.billclearcollect.service.BillClearCollectService;
import com.infolion.xdss3.collect.dao.CollectHibernateDao;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collect.service.CollectService;
import com.infolion.xdss3.collectbankitem.domain.CollectBankItem;
import com.infolion.xdss3.collectitem.domain.CollectItem;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundmentHibernateDao;
import com.infolion.xdss3.customerDrawback.domain.CustomerDbBankItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundItem;
import com.infolion.xdss3.customerDrawback.domain.CustomerRefundment;
import com.infolion.xdss3.customerDrawback.service.CustomerRefundmentService;
import com.infolion.xdss3.financeSquare.dao.FundFlowHibernateDao;
import com.infolion.xdss3.financeSquare.dao.SettleSubjectHibernateDao;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.domain.CertificateNo;
import com.infolion.xdss3.masterData.service.UnclearCustomerService;
import com.infolion.xdss3.masterData.service.VendorService;
import com.infolion.xdss3.payment.dao.HomePaymentHibernateDao;
import com.infolion.xdss3.payment.dao.ImportPaymentHibernateDao;
import com.infolion.xdss3.payment.domain.HomeDocuBankItem;
import com.infolion.xdss3.payment.domain.HomeFundFlow;
import com.infolion.xdss3.payment.domain.HomePayBankItem;
import com.infolion.xdss3.payment.domain.HomePayment;
import com.infolion.xdss3.payment.domain.HomePaymentCbill;
import com.infolion.xdss3.payment.domain.HomePaymentItem;
import com.infolion.xdss3.payment.domain.HomeSettSubj;
import com.infolion.xdss3.payment.domain.ImportDocuBankItem;
import com.infolion.xdss3.payment.domain.ImportFundFlow;
import com.infolion.xdss3.payment.domain.ImportPayBankItem;
import com.infolion.xdss3.payment.domain.ImportPayment;
import com.infolion.xdss3.payment.domain.ImportPaymentCbill;
import com.infolion.xdss3.payment.domain.ImportPaymentItem;
import com.infolion.xdss3.payment.domain.ImportSettSubj;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.dao.ReassignJdbcDao;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassignGen.service.ReassignServiceGen;
import com.infolion.xdss3.singleClear.dao.CustomerClearAccountJdbcDao;
import com.infolion.xdss3.singleClear.dao.SupplierClearAccountJdbcDao;
import com.infolion.xdss3.singleClear.domain.IInfoVoucher;
import com.infolion.xdss3.singleClear.domain.IKeyValue;
import com.infolion.xdss3.singleClear.domain.IParameterVoucher;
import com.infolion.xdss3.singleClear.domain.InfoObject;
import com.infolion.xdss3.singleClear.domain.KeyValue;
import com.infolion.xdss3.supplierDrawback.dao.SupplierRefundItemJdbcDao;
import com.infolion.xdss3.supplierDrawback.dao.SupplierRefundmentHibernateDao;
import com.infolion.xdss3.supplierDrawback.domain.SupplierDbBankItem;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundItem;
import com.infolion.xdss3.supplierDrawback.domain.SupplierRefundment;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.infolion.xdss3.singleClear.domain.ClearConstant;
import com.infolion.xdss3.singleClear.service.SupplierClearAccountService;

/**
 * <pre>
 * 重分配(Reassign)服务类
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
public class ReassignService extends ReassignServiceGen {
	private Log log = LogFactory.getFactory().getLog(this.getClass());

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
	private CustomerRefundmentService customerRefundmentService;

	/**
	 * @param customerRefundmentService
	 *            the customerRefundmentService to set
	 */
	public void setCustomerRefundmentService(
			CustomerRefundmentService customerRefundmentService) {
		this.customerRefundmentService = customerRefundmentService;
	}

	@Autowired
	private CustomerRefundmentHibernateDao customerRefundmentHibernateDao;

	/**
	 * @param customerRefundmentHibernateDao
	 *            the customerRefundmentHibernateDao to set
	 */
	public void setCustomerRefundmentHibernateDao(
			CustomerRefundmentHibernateDao customerRefundmentHibernateDao) {
		this.customerRefundmentHibernateDao = customerRefundmentHibernateDao;
	}

	@Autowired
	private SupplierRefundmentHibernateDao supplierRefundmentHibernateDao;

	/**
	 * @param supplierRefundmentHibernateDao
	 *            the supplierRefundmentHibernateDao to set
	 */
	public void setSupplierRefundmentHibernateDao(
			SupplierRefundmentHibernateDao supplierRefundmentHibernateDao) {
		this.supplierRefundmentHibernateDao = supplierRefundmentHibernateDao;
	}

	@Autowired
	protected VoucherHibernateDao voucherHibernateDao;

	/**
	 * @param voucherHibernateDao
	 *            the voucherHibernateDao to set
	 */
	public void setVoucherHibernateDao(VoucherHibernateDao voucherHibernateDao) {
		this.voucherHibernateDao = voucherHibernateDao;
	}

	@Autowired
	protected VoucherService voucherService;

	/**
	 * @param voucherService
	 *            the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@Autowired
	private BillClearPaymentHibernateDao billClearPaymentHibernateDao;

	/**
	 * @param billClearPaymentHibernateDao
	 *            the billClearPaymentHibernateDao to set
	 */
	public void setBillClearPaymentHibernateDao(
			BillClearPaymentHibernateDao billClearPaymentHibernateDao) {
		this.billClearPaymentHibernateDao = billClearPaymentHibernateDao;
	}

	@Autowired
	private BillClearCollectHibernateDao billClearCollectHibernateDao;

	/**
	 * @param billClearCollectHibernateDao
	 *            the billClearCollectHibernateDao to set
	 */
	public void setBillClearCollectHibernateDao(
			BillClearCollectHibernateDao billClearCollectHibernateDao) {
		this.billClearCollectHibernateDao = billClearCollectHibernateDao;
	}

	@Autowired
	private CollectHibernateDao collectHibernateDao;

	/**
	 * @param collectHibernateDao
	 *            the collectHibernateDao to set
	 */
	public void setCollectHibernateDao(CollectHibernateDao collectHibernateDao) {
		this.collectHibernateDao = collectHibernateDao;
	}

	@Autowired
	private CollectService collectService;

	/**
	 * @param collectService
	 *            the collectService to set
	 */
	public void setCollectService(CollectService collectService) {
		this.collectService = collectService;
	}

	@Autowired
	private HomePaymentHibernateDao homePaymentHibernateDao;

	/**
	 * @param homePaymentHibernateDao
	 *            the homePaymentHibernateDao to set
	 */
	public void setHomePaymentHibernateDao(
			HomePaymentHibernateDao homePaymentHibernateDao) {
		this.homePaymentHibernateDao = homePaymentHibernateDao;
	}

	@Autowired
	private ImportPaymentHibernateDao importPaymentHibernateDao;

	/**
	 * @param importPaymentHibernateDao
	 *            the importPaymentHibernateDao to set
	 */
	public void setImportPaymentHibernateDao(
			ImportPaymentHibernateDao importPaymentHibernateDao) {
		this.importPaymentHibernateDao = importPaymentHibernateDao;
	}

	@Autowired
	private FundFlowHibernateDao fundFlowHibernateDao;

	/**
	 * @param fundFlowHibernateDao
	 *            the fundFlowHibernateDao to set
	 */
	public void setFundFlowHibernateDao(
			FundFlowHibernateDao fundFlowHibernateDao) {
		this.fundFlowHibernateDao = fundFlowHibernateDao;
	}

	@Autowired
	private SettleSubjectHibernateDao settleSubjectHibernateDao;

	/**
	 * @param settleSubjectHibernateDao
	 *            the settleSubjectHibernateDao to set
	 */
	public void setSettleSubjectHibernateDao(
			SettleSubjectHibernateDao settleSubjectHibernateDao) {
		this.settleSubjectHibernateDao = settleSubjectHibernateDao;
	}

	@Autowired
	private BillClearPaymentService billClearPaymentService;

	/**
	 * @param billClearPaymentService
	 *            the billClearPaymentService to set
	 */
	public void setBillClearPaymentService(
			BillClearPaymentService billClearPaymentService) {
		this.billClearPaymentService = billClearPaymentService;
	}

	@Autowired
	private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;

	/**
	 * @param customerRefundItemJdbcDao
	 *            the customerRefundItemJdbcDao to set
	 */
	public void setCustomerRefundItemJdbcDao(
			CustomerRefundItemJdbcDao customerRefundItemJdbcDao) {
		this.customerRefundItemJdbcDao = customerRefundItemJdbcDao;
	}

	@Autowired
	private SupplierRefundItemJdbcDao supplierRefundItemJdbcDao;

	/**
	 * @param supplierRefundItemJdbcDao
	 *            the supplierRefundItemJdbcDao to set
	 */
	public void setSupplierRefundItemJdbcDao(
			SupplierRefundItemJdbcDao supplierRefundItemJdbcDao) {
		this.supplierRefundItemJdbcDao = supplierRefundItemJdbcDao;
	}

	@Autowired
	private UnclearCustomerService unclearCustomerService;

	/**
	 * @param unclearCustomerService
	 *            the unclearCustomerService to set
	 */
	public void setUnclearCustomerService(
			UnclearCustomerService unclearCustomerService) {
		this.unclearCustomerService = unclearCustomerService;
	}

	@Autowired
	private VendorService vendorService;

	/**
	 * @param vendorService
	 *            the vendorService to set
	 */
	public void setVendorService(VendorService vendorService) {
		vendorService = vendorService;
	}

	@Autowired
	private BillClearCollectService billClearCollectService;

	/**
	 * @param billClearCollectService
	 *            the billClearCollectService to set
	 */
	public void setBillClearCollectService(
			BillClearCollectService billClearCollectService) {
		this.billClearCollectService = billClearCollectService;
	}

	@Resource
	private CustomerClearAccountJdbcDao customerClearAccountJdbcDao;
	
	/**
	 * @param customerClearAccountJdbcDao the customerClearAccountJdbcDao to set
	 */
	public void setCustomerClearAccountJdbcDao(
			CustomerClearAccountJdbcDao customerClearAccountJdbcDao) {
		this.customerClearAccountJdbcDao = customerClearAccountJdbcDao;
	}
	
	@Autowired
	private SupplierClearAccountJdbcDao supplierClearAccountJdbcDao;
	
	/**
	 * @param supplierClearAccountJdbcDao the supplierClearAccountJdbcDao to set
	 */
	public void setSupplierClearAccountJdbcDao(
			SupplierClearAccountJdbcDao supplierClearAccountJdbcDao) {
		this.supplierClearAccountJdbcDao = supplierClearAccountJdbcDao;
	}
	
	/**
	 * 判断是否已经手动重置清帐凭证
	 * 
	 * @param reassignType
	 * @param reassignMethod
	 * @param bussinessid
	 * @return 未重置的凭证号， null已经全部清除
	 */
	public String isVoucherReseted(String reassignType, String reassignMethod,
 String businessid){
        List<CertificateNo> certificateNoList = this.reassignJdbcDao.getVoucherbyBussinessID(businessid, reassignType, reassignMethod);

        /**
         * 有凭证编号
         */
        if(certificateNoList != null && certificateNoList.size() > 0){
            /**
             * 调用RFC判断是否已经人工重置清帐凭证
             */
            ConnectManager manager = ConnectManager.manager;
            manager.getClient();
            JCO.Client client = null;
            JCO.Table inputTable = null;
            JCO.Table outputTable = null;
            try{
                IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate("ZF_REVERSAL");
                if(ftemplate != null){
                    JCO.Function function = ftemplate.getFunction();
                    client = JCO.getClient(manager.poolName);
                    inputTable = function.getTableParameterList().getTable("T_BELNR");
                    inputTable.firstRow();
                    for(int i = 0; i < certificateNoList.size(); i++){
                        inputTable.appendRow();
                        inputTable.setValue(certificateNoList.get(i).getBelnr(), "BELNR");
                        inputTable.setValue(certificateNoList.get(i).getBukrs(), "BUKRS");
                        inputTable.setValue(certificateNoList.get(i).getGjahr(), "GJAHR");
                    }

                    client.execute(function); // 执行RFC

                    outputTable = function.getTableParameterList().getTable("T_BELNR");

                    List<Map<String, String>> outPutList = ExtractSapData.getList(outputTable);

                    /**
                     * 判断是否全部重置或冲销
                     */
                    if(outPutList != null && outPutList.size() > 0){
                        String strVouchNo = ""; // 清帐凭证号
                        String strClearVouchNo = ""; // 冲销凭证号
                        String strVouchNoTemp = ""; // 清帐凭证号临时
                        String strClearVouchNoTemp = ""; // 冲销凭证号临时
                        String strVouchNo1 = ""; // 清帐凭证号:用来判断是否清账
                        String strClearVouchNo1 = ""; // 冲销凭证号:用来判断是否冲销
                        String strVoucherState = ""; // 凭证状态
                        String strOffVouchNo = ""; // 冲销凭证号
                        String strOffYear = ""; // 冲销会计年度

                        for(int j = 0; j < outPutList.size(); j++){
                            strVoucherState = outPutList.get(j).get("BSTAT");
                            if(strVoucherState.equals("A")){
                                strVouchNoTemp = outPutList.get(j).get("BELNR");
                            }
                            if(strVoucherState.equals("")){
                                strClearVouchNoTemp = outPutList.get(j).get("BELNR");
                            }
                            // strVouchNo += outPutList.get(j).get("BELNR");
                            strVoucherState = outPutList.get(j).get("BSTAT");
                            strOffVouchNo = outPutList.get(j).get("STBLG");
                            strOffYear = outPutList.get(j).get("STJAH");

                            if((reassignMethod.equals(ReassignConstant.RESET_AND_CLEAR) || 
                                reassignMethod.equals(ReassignConstant.FI_CLEAR) || 
                                reassignMethod.equals(ReassignConstant.RESET_AND_CLEAR_TO_CASH))){
                                if(strVoucherState.equals("")){ // 【冲销凭证】
                                    // 未全部冲销
                                    if(strOffVouchNo.equals("") || strOffYear.equals("")){  
                                        strClearVouchNo1 += ",";
                                        if(strClearVouchNo.equals("")){
                                            strClearVouchNo = strClearVouchNoTemp;
                                        }else{
                                            strClearVouchNo = strClearVouchNo + "," + strClearVouchNoTemp;
                                        }
                                    }
                                }else{                          // 【清帐凭证】
                                    // 未全部重置
                                    if(!strVoucherState.equals("B")){   
                                        strVouchNo1 += ", ";
                                        if(strVouchNo.equals("")){
                                            strVouchNo = strVouchNoTemp;
                                        }else{
                                            strVouchNo = strVouchNo + "," + strVouchNoTemp;
                                        }
                                    }
                                }
                            }else{
                                // 未全部重置
                                if(!strVoucherState.equals("B")){
                                    strVouchNo1 += ", ";
                                    if(strVouchNo.equals("")){
                                        strVouchNo = strVouchNoTemp;
                                    }else{
                                        strVouchNo = strVouchNo + "," + strVouchNoTemp;
                                    }
                                }
                            }
                        }
                        String strRet = "";

                        // 所有清帐凭证已重置
                        if(strVouchNo1.equals("") && strClearVouchNo1.equals("")){
                            /**
                             * 更新凭证编号状态
                             */
                            this.reassignJdbcDao.updateVoucherInfo(outPutList);
                            /**
                             * 重置清帐标志
                             */
                            this.resetClearAcountFlag(reassignType, outPutList, businessid);
                            return null;
                        }else if(strVouchNo1.equals("")){
                            strRet = "收/付款凭证" + strClearVouchNo + "未冲销，请先冲销凭证！";
                        }else if(strClearVouchNo1.equals("")){
                            strRet = "清帐凭证" + strVouchNo + "未重置，请先重置清帐凭证！";
                        }else{
                            strRet = "清帐凭证" + strVouchNo + "未重置，请先重置清帐凭证！" + "收/付款凭证" + strClearVouchNo + "未冲销，请先冲销凭证！";
                        }
                        return strRet;
                    }else{
                        return null;
                    }
                }
            }catch(Exception ex){
                log.error("----------------执行RFC - ZF_REVERSAL发生错误，错误信息为:" + ex.getMessage());
            }

        }
        return null;
    }

	/**
	 * 重置已清标志
	 * 
	 * @param reassignType
	 * @param reassignBoId
	 */
    public void resetClearFlag(String reassignType, String reassignBoId,String isclear){
        /**
         * 重分配收款单
         */
        if(reassignType.equals(ReassignConstant.COLLECT)){
            this.reassignJdbcDao.resetClearFlagForCollect(reassignBoId,isclear);
        }
        /**
         * 重分配付款单
         */
        else if(reassignType.equals(ReassignConstant.PAYMENT)){
            this.reassignJdbcDao.resetClearFlagForPayment(reassignBoId,isclear);
        }
        /**
         * 重分配票清收款
         */
        else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT)){
            this.reassignJdbcDao.resetFlagForBillCCollect(reassignBoId,isclear);
        }
        /**
         * 重分配票清付款
         */
        else if(reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)){
            this.reassignJdbcDao.resetFlagForBillCPayment(reassignBoId,isclear);
        }
        /**
         * 重分配退款单
         */
        else if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT) || reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){
            this.reassignJdbcDao.resetFlagForRefundment(reassignBoId,isclear);
        }
        /**
         * 重分配客户单清
         */
        else if(reassignType.equals(ReassignConstant.CUSTOMERSINGLECLEAR)){
            this.reassignJdbcDao.resetClearFlagForCSC(reassignBoId,isclear);
        }
        /**
         * 重分配供应商单清
         */
        else if(reassignType.equals(ReassignConstant.SUPPLIERSINGLECLEAR)){
            this.reassignJdbcDao.resetClearFlagForSSC(reassignBoId,isclear);
        }
    }
    /**
     * 删除收款清票，把收款，付款修改成预收款
     * @param reassignType
     * @param reassignBoId
     */
    public void resetDel(String reassignType, String reassignBoId){
        /**
         * 重分配收款单
         */
        if(reassignType.equals(ReassignConstant.COLLECT)){
        	this.reassignJdbcDao.resetDelForCollect(reassignBoId);
        }/**
         * 重分配付款单
         */
        else if(reassignType.equals(ReassignConstant.PAYMENT)){
        	this.reassignJdbcDao.resetDelForPayment(reassignBoId);
        }
    }
	/**
	 * 作废原有单据
	 * 
	 * @param reassignType
	 * @param reassignBoId
	 */
	public void abolishOldNo(String reassignType, String reassignBoId) {
		this.reassignJdbcDao.abolishBoNo(reassignType, reassignBoId);
	}

	/**
	 * 复制单据
	 * 
	 * @param reassignType
	 * @param reassignBoId
	 */
	public void copyCreateNewNo(String reassignType, String reassignBoId,
			String reassignId) {
		/**
		 * 判断重分配类型
		 */
		/**
		 * 重分配收款单
		 */
		if (reassignType.equals(ReassignConstant.COLLECT)) {
			this.copyCollect(reassignId, reassignBoId);
		}
		/**
		 * 重分配付款单
		 */
		else if (reassignType.equals(ReassignConstant.PAYMENT)) {
			String tradeType = getPaymentTradeType(reassignBoId);
			// 内贸付款
			if (tradeType.equals("01")) {
				this.copyHomePayment(reassignId, reassignBoId);
			}
			// 外贸付款
			else {
				this.copyImportPayment(reassignId, reassignBoId);
			}
		}
		/**
		 * 拷贝票清收款
		 */
		else if (reassignType.equals(ReassignConstant.BILLCLEARCOLLECT)) {
			this.copyBillClearCollect(reassignBoId);
		}
		/**
		 * 拷贝票清付款
		 */
		else if (reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)) {
			this.copyBillClearPayment(reassignBoId);
		}
		/**
		 * 重分配退款单
		 */
		else if (reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT)){
		    this.copyCustomerRefundment(reassignBoId);
		}else if(reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){
		    this.copySupplierRefundment(reassignBoId);
		}

	}

	/**
	 * 重分配通过后，设置结清标志
	 * 
	 * @param reassginType
	 * @param reassignBoid
	 */
	public void setIsClearFlag(String reassignType, String reassignBoId) {
		/**
		 * 判断重分配类型
		 */

		/**
		 * 收款
		 */
		if (reassignType.equals(ReassignConstant.COLLECT)) {
			String collectId = this.reassignJdbcDao.getNewNoIdByOldNoId(
					ReassignConstant.COLLECT, reassignBoId);

			// 更新结清标志
			this.collectService.settleCollect(collectId);

			// //更新customerTitle表
			// List<Map<String,Object>> invoiceList =
			// this.reassignJdbcDao.getInvoiceListByCollectId(collectId);
			// if( invoiceList != null )
			// {
			// for( Map<String,Object> invoice: invoiceList)
			// {
			// BigDecimal clearAmount =
			// this.unclearCustomerService.getSumClearAmount(invoice.get("billNo").toString(),
			// BusinessState.SUBMITPASS +"," + BusinessState.SPECIALSUBMITPASS
			// );
			//					
			// BigDecimal InvoiceAmount = new
			// BigDecimal((invoice.get("billAmount").toString()));
			//					
			// if(clearAmount.compareTo(InvoiceAmount) == 0)
			// {
			// this.unclearCustomerService.updateCustomerTitleIsClearedByInvoice(invoice.get("billNo").toString(),
			// cleared.isCleared);
			// }
			// }
			// }
		}
		/**
		 * 付款
		 */
		else if (reassignType.equals(ReassignConstant.PAYMENT)) {
			String paymentId = this.reassignJdbcDao.getNewNoIdByOldNoId(
					ReassignConstant.PAYMENT, reassignBoId);

			// 更新paymentItem表
			this.reassignJdbcDao.updatePaymentItemIsClearByPaymentId(paymentId);

			// 更新vendortitle表
			List<Map<String, Object>> invoiceList = this.reassignJdbcDao
					.getInvoiceListByPaymentId(paymentId);
			for (Map<String, Object> invoice : invoiceList) {
				BigDecimal clearAmount = this.vendorService.getSumClearAmount(
						invoice.get("billNo").toString(),
						BusinessState.SUBMITPASS + ","
								+ BusinessState.SPECIALSUBMITPASS);

				BigDecimal InvoiceAmount = new BigDecimal((invoice
						.get("billAmount").toString()));

				if (clearAmount.compareTo(InvoiceAmount) == 0) {
					this.vendorService
							.updateVendorTitleIsClearedByInvoice(invoice.get(
									"billNo").toString(), cleared.isCleared);
				}
			}
		}
		/**
		 * 票清收款
		 */
		else if (reassignType.equals(ReassignConstant.BILLCLEARCOLLECT)) {
			String billClearId = this.reassignJdbcDao.getNewNoIdByOldNoId(
					ReassignConstant.BILLCLEARCOLLECT, reassignBoId);

			// 更新结清标志
			this.billClearCollectService.settleBillClearCollect(reassignBoId);

		}
		/**
		 * 票清付款
		 */
		else if (reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)) {
			String billClearId = this.reassignJdbcDao.getNewNoIdByOldNoId(
					ReassignConstant.BILLCLEARPAYMENT, reassignBoId);

			/**
			 * 更新付款分配表
			 */
			this.billClearPaymentService
					.updatePaymentItemIsCleared(billClearId);

			/**
			 * 更新票
			 */
			this.billClearPaymentService
					.updateVendorTitleIsCleared(billClearId);

		}
		/**
		 * 退款单
		 */
		else if (reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT)) {
		    // 客户 退款
			String refundmentId = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.CUSTOMERREFUNDMENT, reassignBoId);
			this.customerRefundItemJdbcDao.updateCollectItem(refundmentId);
		}else if(reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){
		    // 供应商退款
	        String refundmentId = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.SUPPLIERREFUNDMENT, reassignBoId);
		    this.supplierRefundItemJdbcDao.updatePaymentItem(refundmentId);
		}
	}

	/**
	 * 获取重分配ID对象
	 * 
	 * @param reassignId
	 * @return
	 */
	public Reassign getReassignById(String reassignId) {
		return this.reassignHibernateDao.get(reassignId);
	}

	/**
	 * 获取付款方式
	 * 
	 * @param paymentId
	 * @return
	 */
	public String getPaymentTradeType(String paymentId) {
		return this.reassignJdbcDao.getPaymentTradeType(paymentId);
	}

	/**
	 * 获取退款类型
	 * 
	 * @param refundmentId
	 * @return
	 */
	public String getRefundmentType(String refundmentId) {
		return this.reassignJdbcDao.getRefundmentType(refundmentId);
	}

	/**
	 * 拷贝凭证
	 * 
	 * @param bussinessid
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public void copyVoucher(String bussinessid, String newBussinessid) {
		List<String> voucherIdList = this.reassignJdbcDao
				.getVoucherIdByClass(bussinessid);

		for (int i = 0; i < voucherIdList.size(); i++) {

			Voucher newVoucher = this.voucherHibernateDao
					.getDetached(voucherIdList.get(i));
			newVoucher.setVoucherid(null);

			newVoucher.setBusinessid(newBussinessid);
			Set<VoucherItem> voucherItemSet = newVoucher.getVoucherItem();
			Set<VoucherItem> newVoucherItemSet = null;
			newVoucher.setVoucherItem(null);
			if (null != voucherItemSet) {
				newVoucherItemSet = new HashSet<VoucherItem>();
				Iterator<VoucherItem> it = voucherItemSet.iterator();
				while (it.hasNext()) {
					VoucherItem voucherItem = it.next();
					voucherItem.setVoucheritemid(null);
					voucherItem.setVoucher(newVoucher);
					newVoucherItemSet.add(voucherItem);
				}
			}
			newVoucher.setVoucherItem(newVoucherItemSet);

			this.voucherHibernateDao.saveOrUpdate(newVoucher);
		}
	}

	/**
	 * 原单据的凭证（除了清帐凭证）拷贝至新的单据 拷贝凭证
	 * 
	 * @param bussinessid
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public void copyVoucher2(String bussinessid, String newBussinessid) {
		List<String> voucherIdList = this.reassignJdbcDao
				.getVoucherId2(bussinessid);

		for (int i = 0; i < voucherIdList.size(); i++) {

			Voucher newVoucher = this.voucherHibernateDao
					.getDetached(voucherIdList.get(i));
			newVoucher.setVoucherid(null);

			newVoucher.setBusinessid(newBussinessid);
			Set<VoucherItem> voucherItemSet = newVoucher.getVoucherItem();
			Set<VoucherItem> newVoucherItemSet = null;
			newVoucher.setVoucherItem(null);
			if (null != voucherItemSet) {
				newVoucherItemSet = new HashSet<VoucherItem>();
				Iterator<VoucherItem> it = voucherItemSet.iterator();
				while (it.hasNext()) {
					VoucherItem voucherItem = it.next();
					voucherItem.setVoucheritemid(null);
					voucherItem.setVoucher(newVoucher);
					newVoucherItemSet.add(voucherItem);
				}
			}
			newVoucher.setVoucherItem(newVoucherItemSet);

			this.voucherHibernateDao.saveOrUpdate(newVoucher);
		}
	}

	/**
	 * 设置重分配状态
	 * 
	 * @param reassignId
	 * @param state
	 *            审核通过；审核不通过
	 */
	public void updateReassignState(String reassignId, String state) {
		this.reassignJdbcDao.updateReassignState(reassignId, state);
	}

	/**
	 * 根据具体业务对象ID，获得单号
	 * 
	 * @param reassignType
	 *            ：重分配类型
	 * @param businessId
	 *            : 重分配对象No
	 * @return
	 */
	public String getBusinessNoById(String reassignType, String businessId) {
		return this.reassignJdbcDao.getBusinessNoById(reassignType, businessId);
	}

	/**
	 * yanghancai 2010-10-14 根据具体业务对象ID，获得部门ID
	 * 
	 * @param reassignType
	 *            ：重分配类型
	 * @param businessId
	 *            : 重分配对象No
	 * @return
	 */
	public String getDeptId(String reassignType, String businessId) {
		return this.reassignJdbcDao.getDeptId(reassignType, businessId);
	}

	/**
	 * yanghancai 2010-10-14 根据具体业务对象ID，获得原创建人
	 * 
	 * @param businessId
	 *            : 重分配对象No
	 * @return
	 */
	public String getOcreator(String reassignType, String businessId) {
		return this.reassignJdbcDao.getOcreator(reassignType, businessId);
	}

	/**
	 * 拷贝客户退款
	 * 
	 * @param redfundmentid
	 */
	private void copyCustomerRefundment(String redfundmentid) {
		CustomerRefundment customerRefundment = this.customerRefundmentHibernateDao
				.getDetached(redfundmentid);

		customerRefundment.setReassigneddbid(customerRefundment
				.getRefundmentid());
		customerRefundment.setRefundmentid(null);
		customerRefundment.setBusinessstate(BusinessState.REASSIGNED); // 重分配
		customerRefundment.setProcessstate("");
		customerRefundment.setCreator("");
		customerRefundment.setCreatetime("");
		customerRefundment.setLastmodifytime("");
		customerRefundment.setLastmodiyer("");
		String refundmentno = NumberService.getNextObjectNumber(
				"CustomerRefundmentNo", customerRefundment);
		customerRefundment.setRefundmentno(refundmentno);

		Set<CustomerRefundItem> customerRefundItemSet = customerRefundment
				.getCustomerRefundItem();
		Set<CustomerRefundItem> newCustomerRefundItemSet = null;
		customerRefundment.setCustomerRefundItem(null);
		if (customerRefundItemSet != null) {
			newCustomerRefundItemSet = new HashSet<CustomerRefundItem>();
			Iterator<CustomerRefundItem> it = customerRefundItemSet.iterator();
			while (it.hasNext()) {
				CustomerRefundItem customerRefundItem = it.next();
				customerRefundItem.setRefundmentitemid(null);
				customerRefundItem.setCustomerRefundment(customerRefundment);
				newCustomerRefundItemSet.add(customerRefundItem);
			}
		}
		customerRefundment.setCustomerRefundItem(newCustomerRefundItemSet);

		Set<CustomerDbBankItem> customerRefundDbBankSet = customerRefundment
				.getCustomerDbBankItem();
		Set<CustomerDbBankItem> newCustomerRefundDbBankSet = null;
		customerRefundment.setCustomerDbBankItem(null);
		if (customerRefundDbBankSet != null) {
			newCustomerRefundDbBankSet = new HashSet<CustomerDbBankItem>();
			Iterator<CustomerDbBankItem> it = customerRefundDbBankSet
					.iterator();
			while (it.hasNext()) {
				CustomerDbBankItem customerDbBankItem = it.next();
				customerDbBankItem.setRefundbankitemid(null);
				customerDbBankItem.setCustomerRefundment(customerRefundment);
				newCustomerRefundDbBankSet.add(customerDbBankItem);
			}
		}
		customerRefundment.setCustomerDbBankItem(newCustomerRefundDbBankSet);

		this.customerRefundmentHibernateDao.save(customerRefundment);
	}

	/**
	 * 拷贝供应商退款
	 * 
	 * @param refundmentid
	 */
	private void copySupplierRefundment(String refundmentid) {
		SupplierRefundment supplierRefundment = this.supplierRefundmentHibernateDao
				.getDetached(refundmentid);
		supplierRefundment.setReassigneddbid(supplierRefundment
				.getRefundmentid());
		supplierRefundment.setRefundmentid(null);
		supplierRefundment.setBusinessstate(BusinessState.REASSIGNED); // 重分配
		supplierRefundment.setProcessstate("");
		supplierRefundment.setCreator("");
		supplierRefundment.setCreatetime("");
		supplierRefundment.setLastmodifytime("");
		supplierRefundment.setLastmodiyer("");
		String refundmentno = NumberService.getNextObjectNumber(
				"SupplierRefundmentNo", supplierRefundment);
		supplierRefundment.setRefundmentno(refundmentno);

		Set<SupplierRefundItem> supplierRefundItemSet = supplierRefundment
				.getSupplierRefundItem();
		Set<SupplierRefundItem> newSupplierRefundItemSet = null;
		supplierRefundment.setSupplierRefundItem(null);
		if (supplierRefundItemSet != null) {
			newSupplierRefundItemSet = new HashSet<SupplierRefundItem>();
			Iterator<SupplierRefundItem> it = supplierRefundItemSet.iterator();
			while (it.hasNext()) {
				SupplierRefundItem supplierRefundItem = it.next();
				supplierRefundItem.setRefundmentitemid(null);
				supplierRefundItem.setSupplierRefundment(supplierRefundment);
				newSupplierRefundItemSet.add(supplierRefundItem);
			}
		}
		supplierRefundment.setSupplierRefundItem(newSupplierRefundItemSet);

		Set<SupplierDbBankItem> supplierRefundDbBankSet = supplierRefundment
				.getSupplierDbBankItem();
		Set<SupplierDbBankItem> newSupplierRefundDbBankSet = null;
		supplierRefundment.setSupplierDbBankItem(null);
		if (supplierRefundDbBankSet != null) {
			newSupplierRefundDbBankSet = new HashSet<SupplierDbBankItem>();
			Iterator<SupplierDbBankItem> it = supplierRefundDbBankSet
					.iterator();
			while (it.hasNext()) {
				SupplierDbBankItem supplierDbBankItem = it.next();
				supplierDbBankItem.setRefundbankitemid(null);
				supplierDbBankItem.setSupplierRefundment(supplierRefundment);
				newSupplierRefundDbBankSet.add(supplierDbBankItem);
			}
		}
		supplierRefundment.setSupplierDbBankItem(newSupplierRefundDbBankSet);

		this.supplierRefundmentHibernateDao.save(supplierRefundment);
	}

	/**
	 * 拷贝内贸付款
	 * 
	 * @param paymentId
	 */
	private void copyHomePayment(String reassignId, String paymentId) {
		HomePayment homePayment = this.homePaymentHibernateDao
				.getDetached(paymentId);
		String oldPaymentNo = homePayment.getPaymentno();
		homePayment.setBusinessstate(BusinessState.REASSIGNED);
		homePayment.setCreatetime("");
		homePayment.setCreator("");
		homePayment.setLastmodifyer("");
		homePayment.setLastmodifytime("");
		homePayment.setProcessstate("");
		homePayment.setRepaymentid(homePayment.getPaymentid());
		homePayment.setPaymentid(null);
		String paymentno = NumberService.getNextObjectNumber("homepaymentno",
				homePayment);
		homePayment.setPaymentno(paymentno);

		Set<HomePaymentItem> homePaymentItemSet = homePayment
				.getHomePaymentItem();
		Set<HomePaymentItem> newHomePaymentItemSet = null;
		if (homePaymentItemSet != null) {
			newHomePaymentItemSet = new HashSet<HomePaymentItem>();
			Iterator<HomePaymentItem> it = homePaymentItemSet.iterator();
			while (it.hasNext()) {
				HomePaymentItem homePaymentItem = it.next();
				homePaymentItem.setPaymentitemid(null);
				homePaymentItem.setHomePayment(homePayment);
				newHomePaymentItemSet.add(homePaymentItem);
			}
		}
		homePayment.setHomePaymentItem(newHomePaymentItemSet);

		Set<HomeDocuBankItem> homeDocuBankItemSet = homePayment
				.getHomeDocuBankItem();
		Set<HomeDocuBankItem> newHomeDocuBankItemSet = null;
		if (homeDocuBankItemSet != null) {
			newHomeDocuBankItemSet = new HashSet<HomeDocuBankItem>();
			Iterator<HomeDocuBankItem> it = homeDocuBankItemSet.iterator();
			while (it.hasNext()) {
				HomeDocuBankItem homeDocuBankItem = it.next();
				homeDocuBankItem.setDocuaryitemid(null);
				homeDocuBankItem.setHomePayment(homePayment);
				newHomeDocuBankItemSet.add(homeDocuBankItem);
			}
		}
		homePayment.setHomeDocuBankItem(newHomeDocuBankItemSet);

		Set<HomePayBankItem> homePayBankItemSet = homePayment
				.getHomePayBankItem();
		Set<HomePayBankItem> newHomePayBankItemSet = null;
		if (homePayBankItemSet != null) {
			newHomePayBankItemSet = new HashSet<HomePayBankItem>();
			Iterator<HomePayBankItem> it = homePayBankItemSet.iterator();
			while (it.hasNext()) {
				HomePayBankItem homePayBankItem = it.next();
				homePayBankItem.setPaybankitemid(null);
				homePayBankItem.setHomePayment(homePayment);
				newHomePayBankItemSet.add(homePayBankItem);
			}
		}
		homePayment.setHomePayBankItem(newHomePayBankItemSet);

		Set<HomePaymentCbill> homePaymentCbillSet = homePayment
				.getHomePaymentCbill();
		Set<HomePaymentCbill> newHomePaymentCbillSet = null;
		if (homePaymentCbillSet != null) {
			newHomePaymentCbillSet = new HashSet<HomePaymentCbill>();
			Iterator<HomePaymentCbill> it = homePaymentCbillSet.iterator();
			while (it.hasNext()) {
				HomePaymentCbill homePaymentCbill = it.next();
				homePaymentCbill.setPaymentcbillid(null);
				homePaymentCbill.setHomePayment(homePayment);
				newHomePaymentCbillSet.add(homePaymentCbill);
			}
		}
		homePayment.setHomePaymentCbill(newHomePaymentCbillSet);

		HomeFundFlow homeFundFlow = homePayment.getHomeFundFlow();
		HomeFundFlow newHomeFundFlow = null;
		if (homeFundFlow != null) {
			newHomeFundFlow = homeFundFlow;
			newHomeFundFlow.setFundflowid(null);
			newHomeFundFlow.setHomePayment(homePayment);
		}
		homePayment.setHomeFundFlow(newHomeFundFlow);

		HomeSettSubj homeSettSubj = homePayment.getHomeSettSubj();
		HomeSettSubj newHomeSettSubj = null;
		if (homeSettSubj != null) {
			newHomeSettSubj = homeSettSubj;
			newHomeSettSubj.setSettlesubjectid(null);
			newHomeSettSubj.setHomePayment(homePayment);
		}
		homePayment.setHomeSettSubj(newHomeSettSubj);
		homePayment.setHomePaymentCbill(null);

		// 更新新生成单据状态businesstate=3,processstate='作废'.
		homePayment.setBusinessstate("4");
		homePayment.setProcessstate("结束");

		this.homePaymentHibernateDao.save(homePayment);

		// yanghancai 2010-10-26
		// 原单据的凭证（除了清帐凭证）拷贝至新的单据
		this.copyVoucher2(paymentId, homePayment.getPaymentid());

		// 关联单据表
		this.reassignJdbcDao.insertPaymentRelateForReassign(reassignId,
				homePayment.getPaymentid(), homePayment.getRepaymentid(),
				oldPaymentNo, homePayment.getFactamount());

	}

	/**
	 * 拷贝进口付款
	 * 
	 * @param paymentId
	 */
	private void copyImportPayment(String reassignId, String paymentId) {
		ImportPayment importPayment = this.importPaymentHibernateDao
				.getDetached(paymentId);
		String oldPaymentNo = importPayment.getPaymentno();
		importPayment.setBusinessstate(BusinessState.REASSIGNED);
		importPayment.setCreatetime("");
		importPayment.setCreator("");
		importPayment.setLastmodifyer("");
		importPayment.setLastmodifytime("");
		importPayment.setProcessstate("");
		importPayment.setRepaymentid(importPayment.getPaymentid());
		importPayment.setPaymentid(null);
		String paymentno = NumberService.getNextObjectNumber("importpaymentno",
				importPayment);
		importPayment.setPaymentno(paymentno);

		Set<ImportPaymentItem> importPaymentItemSet = importPayment
				.getImportPaymentItem();
		Set<ImportPaymentItem> newImportPaymentItemSet = null;
		if (importPaymentItemSet != null) {
			newImportPaymentItemSet = new HashSet<ImportPaymentItem>();
			Iterator<ImportPaymentItem> it = importPaymentItemSet.iterator();
			while (it.hasNext()) {
				ImportPaymentItem importPaymentItem = it.next();
				importPaymentItem.setPaymentitemid(null);
				importPaymentItem.setImportPayment(importPayment);
				newImportPaymentItemSet.add(importPaymentItem);
			}
		}
		importPayment.setImportPaymentItem(newImportPaymentItemSet);

		Set<ImportDocuBankItem> importDocuBankItemSet = importPayment
				.getImportDocuBankItem();
		Set<ImportDocuBankItem> newImportDocuBankItemSet = null;
		if (importDocuBankItemSet != null) {
			newImportDocuBankItemSet = new HashSet<ImportDocuBankItem>();
			Iterator<ImportDocuBankItem> it = importDocuBankItemSet.iterator();
			while (it.hasNext()) {
				ImportDocuBankItem importDocuBankItem = it.next();
				importDocuBankItem.setDocuaryitemid(null);
				importDocuBankItem.setImportPayment(importPayment);
				newImportDocuBankItemSet.add(importDocuBankItem);
			}
		}
		importPayment.setImportDocuBankItem(newImportDocuBankItemSet);

		Set<ImportPayBankItem> importPayBankItemSet = importPayment
				.getImportPayBankItem();
		Set<ImportPayBankItem> newImportPayBankItemSet = null;
		if (importPayBankItemSet != null) {
			newImportPayBankItemSet = new HashSet<ImportPayBankItem>();
			Iterator<ImportPayBankItem> it = importPayBankItemSet.iterator();
			while (it.hasNext()) {
				ImportPayBankItem importPayBankItem = it.next();
				importPayBankItem.setPaybankitemid(null);
				importPayBankItem.setImportPayment(importPayment);
				newImportPayBankItemSet.add(importPayBankItem);
			}
		}
		importPayment.setImportPayBankItem(newImportPayBankItemSet);

		Set<ImportPaymentCbill> importPaymentCbillSet = importPayment
				.getImportPaymentCbill();
		Set<ImportPaymentCbill> newImportPaymentCbillSet = null;
		if (importPaymentCbillSet != null) {
			newImportPaymentCbillSet = new HashSet<ImportPaymentCbill>();
			Iterator<ImportPaymentCbill> it = importPaymentCbillSet.iterator();
			while (it.hasNext()) {
				ImportPaymentCbill importPaymentCbill = it.next();
				importPaymentCbill.setPaymentcbillid(null);
				importPaymentCbill.setImportPayment(importPayment);
				newImportPaymentCbillSet.add(importPaymentCbill);
			}
		}
		importPayment.setImportPaymentCbill(newImportPaymentCbillSet);

		ImportFundFlow importFundFlow = importPayment.getImportFundFlow();
		ImportFundFlow newImportFundFlow = null;
		if (importFundFlow != null) {
			newImportFundFlow = importFundFlow;
			newImportFundFlow.setFundflowid(null);
			newImportFundFlow.setImportPayment(importPayment);
		}
		importPayment.setImportFundFlow(newImportFundFlow);

		ImportSettSubj importSettSubj = importPayment.getImportSettSubj();
		ImportSettSubj newImportSettSubj = null;
		if (importSettSubj != null) {
			newImportSettSubj = importSettSubj;
			newImportSettSubj.setSettlesubjectid(null);
			newImportSettSubj.setImportPayment(importPayment);
		}
		importPayment.setImportSettSubj(newImportSettSubj);
		importPayment.setImportPaymentCbill(null);

		// 更新新生成单据状态businesstate=3,processstate='作废'.
		importPayment.setBusinessstate("4");
		importPayment.setProcessstate("结束");

		this.importPaymentHibernateDao.save(importPayment);

		// yanghancai 2010-10-26
		// 原单据的凭证（除了清帐凭证）拷贝至新的单据
		this.copyVoucher2(paymentId, importPayment.getPaymentid());

		// 关联单据表
		this.reassignJdbcDao.insertPaymentRelateForReassign(reassignId,
				importPayment.getPaymentid(), importPayment.getRepaymentid(),
				oldPaymentNo, importPayment.getFactamount());
	}

	/**
	 * 拷贝收款
	 * 
	 * @param collectId
	 */

	private void copyCollect(String reassignId, String collectId) {
//		Collect collect = this.collectHibernateDao.getDetached(collectId);
		Collect collect =this.collectService.getCollectById(collectId);
		collect.setBusinessstate(BusinessState.REASSIGNED);
		collect.setCreatetime("");
		collect.setCreator("");
		collect.setLastmodifyer("");
		collect.setLastmodifytime("");
		collect.setProcessstate("");
		collect.setOldcollectid(collect.getCollectid());
		collect.setOldcollectno(collect.getCollectno());
		collect.setCollectid(null);
		String collectno = NumberService.getNextObjectNumber("CollectNo",
				collect);
		collect.setCollectno(collectno);

		Set<CollectItem> collectitemSet = collect.getCollectitem();
		Set<CollectItem> newCollectitemSet = null;
		if (collectitemSet != null) {
			newCollectitemSet = new HashSet<CollectItem>();
			Iterator<CollectItem> it = collectitemSet.iterator();
			while (it.hasNext()) {
				CollectItem collectItem = it.next();
				collectItem.setCollectitemid(null);
				collectItem.setCollect(collect);
				collectItem.setPrebillamount(collectItem.getAssignamount()); // yanghancai
																				// 2010-10-25
				newCollectitemSet.add(collectItem);
			}
		}
		collect.setCollectitem(newCollectitemSet);

		Set<CollectBankItem> collectbankItemSet = collect.getCollectbankitem();
		Set<CollectBankItem> newCollectbankitemSet = null;
		if (collectbankItemSet != null) {
			newCollectbankitemSet = new HashSet<CollectBankItem>();
			Iterator<CollectBankItem> it = collectbankItemSet.iterator();
			while (it.hasNext()) {
				CollectBankItem collectBankItem = it.next();
				// collectBankItem.setCollectbankid(null);
				collectBankItem.setColbankitemid(null);
				collectBankItem.setCollect(collect);
				newCollectbankitemSet.add(collectBankItem);
			}
		}
		collect.setCollectbankitem(newCollectbankitemSet);
		// this.collectHibernateDao.save(collect);

		/**
		 * 拷贝存资金往来
		 */
		FundFlow fundFlow = collect.getFundFlow();
		FundFlow newFundFlow = null;
		if (fundFlow != null) {
			newFundFlow = fundFlow;
			newFundFlow.setFundflowid(null);
			newFundFlow.setCollect(collect);
		}
		collect.setFundFlow(newFundFlow);

		/**
		 * 拷贝结算科目
		 */
		SettleSubject settleSubject = collect.getSettleSubject();
		SettleSubject newSettleSubject = null;
		if (settleSubject != null) {
			newSettleSubject = settleSubject;
			newSettleSubject.setSettlesubjectid(null);
			newSettleSubject.setCollect(collect);
		}
		collect.setSettleSubject(newSettleSubject);
		collect.setCollectcbill(null);

		// 更新新生成单据状态businesstate=3,processstate='结束'.
		collect.setBusinessstate("3");
		collect.setProcessstate("结束");

		this.collectHibernateDao.save(collect);
		// yanghancai 2010-10-26
		// 原单据的凭证（除了清帐凭证）拷贝至新的单据
		this.copyVoucher2(collectId, collect.getCollectid());

		this.reassignJdbcDao
				.insertCollectRelateForReassign(reassignId, collect
						.getCollectid(), collect.getOldcollectno(), collect
						.getActamount());
	}

	/**
	 * 拷贝票清付款
	 * 
	 * @param billClearId
	 */
	private void copyBillClearPayment(String billClearId) {
		BillClearPayment billClearPayment = this.billClearPaymentHibernateDao
				.getDetached(billClearId);
		billClearPayment.setBusinessstate(BusinessState.REASSIGNED);
		billClearPayment.setCreatetime("");
		billClearPayment.setCreator("");
		billClearPayment.setLastmodifyer("");
		billClearPayment.setLastmodifytime("");
		billClearPayment.setProcessstate("");
		billClearPayment.setOldbillclearid(billClearPayment.getBillclearid());
		billClearPayment.setOldbillclearno(billClearPayment.getBillclearno());
		billClearPayment.setBillclearid(null);

		String billclearno = NumberService.getNextObjectNumber(
				"billclearnoPayment", billClearPayment);
		billClearPayment.setBillclearno(billclearno);

		billClearPayment.setBillClearItemPay(null);
		billClearPayment.setBillInPayment(null);
		billClearPayment.setFundFlow(null);
		billClearPayment.setSettleSubject(null);

		// Set<BillClearItemPay> billClearItemPaySet =
		// billClearPayment.getBillClearItemPay();
		// Set<BillClearItemPay> newBillClearItemPaySet = null;
		// if( billClearItemPaySet != null )
		// {
		// newBillClearItemPaySet = new HashSet<BillClearItemPay>();
		// Iterator<BillClearItemPay> it = billClearItemPaySet.iterator();
		// while(it.hasNext())
		// {
		// BillClearItemPay billClearItemPay = it.next();
		// billClearItemPay.setBillclearitemid(null);
		// billClearItemPay.setBillClearPayment(billClearPayment);
		// newBillClearItemPaySet.add(billClearItemPay);
		// }
		// }
		// billClearPayment.setBillClearItemPay(newBillClearItemPaySet);
		//		
		//		
		// Set<BillInPayment> billInPaymentSet =
		// billClearPayment.getBillInPayment();
		// Set<BillInPayment> newBillInPaymentSet = null;
		// if( billInPaymentSet != null )
		// {
		// newBillInPaymentSet = new HashSet<BillInPayment>();
		// Iterator<BillInPayment> it = billInPaymentSet.iterator();
		// BillInPayment billInPayment = it.next();
		// billInPayment.setBillnpaymentid(null);
		// billInPayment.setBillClearPayment(billClearPayment);
		// newBillInPaymentSet.add(billInPayment);
		// }
		// billClearPayment.setBillInPayment(newBillInPaymentSet);
		//		
		// FundFlow fundFlow = billClearPayment.getFundFlow();
		// FundFlow newFundFlow = null;
		// if( fundFlow != null )
		// {
		// newFundFlow = fundFlow;
		// newFundFlow.setFundflowid(null);
		// newFundFlow.setBillClearPayment(billClearPayment);
		// }
		// billClearPayment.setFundFlow(newFundFlow);
		//		
		// SettleSubject settleSubject = billClearPayment.getSettleSubject();
		// SettleSubject newSettleSubject = null;
		// if( settleSubject != null )
		// {
		// newSettleSubject = settleSubject;
		// newSettleSubject.setSettlesubjectid(null);
		// newSettleSubject.setBillClearPayment(billClearPayment);
		// }
		// billClearPayment.setSettleSubject(newSettleSubject);

		this.billClearPaymentHibernateDao.save(billClearPayment);

	}

	/**
	 * 拷贝票清收款
	 * 
	 * @param billClearId
	 */
	private void copyBillClearCollect(String billClearId) {
		BillClearCollect billClearCollect = this.billClearCollectHibernateDao
				.getDetached(billClearId);

		billClearCollect.setBusinessstate(BusinessState.REASSIGNED);
		billClearCollect.setCreatetime("");
		billClearCollect.setCreator("");
		billClearCollect.setLastmodifyer("");
		billClearCollect.setLastmodifytime("");
		billClearCollect.setProcessstate("");
		billClearCollect.setOldbillclearid(billClearCollect.getBillclearid());
		billClearCollect.setOldbillclearno(billClearCollect.getBillclearno());
		billClearCollect.setBillclearid(null);

		String billclearno = NumberService.getNextObjectNumber(
				"billclearnoCollect", billClearCollect);
		billClearCollect.setBillclearno(billclearno);

		billClearCollect.setBillclearitem(null);
		billClearCollect.setBillincollect(null);
		billClearCollect.setFundFlowBcc(null);
		billClearCollect.setSettleSubjectBcc(null);

		// Set<BillClearItem> billClearItemSet =
		// billClearCollect.getBillclearitem();
		// Set<BillClearItem> newBillClearItemSet = null;
		// if( billClearItemSet != null )
		// {
		// newBillClearItemSet = new HashSet<BillClearItem>();
		// Iterator<BillClearItem> it = billClearItemSet.iterator();
		// BillClearItem billClearItem = it.next();
		// billClearItem.setBillclearitemid(null);
		// billClearItem.setBillClearCollect(billClearCollect);
		// newBillClearItemSet.add(billClearItem);
		// }
		// billClearCollect.setBillclearitem(newBillClearItemSet);
		//		
		// Set<BillInCollect> billInCollectSet =
		// billClearCollect.getBillincollect();
		// Set<BillInCollect> newBillInCollectSet = null;
		// if( billInCollectSet != null )
		// {
		// newBillInCollectSet = new HashSet<BillInCollect>();
		// Iterator<BillInCollect> it = billInCollectSet.iterator();
		// BillInCollect billInCollect = it.next();
		// billInCollect.setBillincollectid(null);
		// billInCollect.setBillClearCollect(billClearCollect);
		// newBillInCollectSet.add(billInCollect);
		// }
		// billClearCollect.setBillincollect(newBillInCollectSet);
		//		
		// FundFlowBcc fundFlowBcc = billClearCollect.getFundFlowBcc();
		// FundFlowBcc newFundFlowBcc = null;
		// if( fundFlowBcc != null )
		// {
		// newFundFlowBcc = fundFlowBcc;
		// newFundFlowBcc.setFundflowid(null);
		// newFundFlowBcc.setBillClearCollect(billClearCollect);
		// }
		// billClearCollect.setFundFlowBcc(newFundFlowBcc);
		//		
		// SettleSubjectBcc settleSubjectBcc =
		// billClearCollect.getSettleSubjectBcc();
		// SettleSubjectBcc newSettleSubjectBcc = null;
		// if( settleSubjectBcc != null )
		// {
		// newSettleSubjectBcc = settleSubjectBcc;
		// newSettleSubjectBcc.setSettlesubjectid(null);
		// newSettleSubjectBcc.setBillClearCollect(billClearCollect);
		// }
		// billClearCollect.setSettleSubjectBcc(newSettleSubjectBcc);

		this.billClearCollectHibernateDao.save(billClearCollect);

	}

	/**
	 * 重置清帐标志
	 * 
	 * @param reassignType
	 * @param rfcRetVoucherList
	 */
	private void resetClearAcountFlag(String reassignType,
 List<Map<String, String>> rfcRetVoucherList, String businessId){
        String strVouchNo = ""; // 清帐凭证号
        String strBukrs = ""; // 公司代码
        String strFiYear = ""; // 会计年度
        String strVoucherState = ""; // 凭证状态

        /**
         * 获得清帐凭证列表
         */
        List<Map<String, String>> clearAcountList = new ArrayList<Map<String, String>>();
        for(Map<String, String> map : rfcRetVoucherList){
            strVoucherState = map.get("BSTAT");
            if(strVoucherState.equals("B")){
                clearAcountList.add(map);
            }
        }

        /**
         * 遍历清帐凭证
         */
        for(Map<String, String> map : clearAcountList){
            strVouchNo = map.get("BELNR"); // 会计凭证
            strBukrs = map.get("BUKRS"); // 公司代码
            strFiYear = map.get("GJAHR"); // 会计年度

            /**
             * 找出清帐凭证相关的行项目ItemID(收款，付款分配表id)
             */
            List<Map<String, String>> itemIdList = this.reassignJdbcDao.getItemIdListByVoucherNo(strVouchNo, strBukrs, strFiYear);

            if(itemIdList == null){
                return;
            }

            // 重分配收款
            if(reassignType.equals(ReassignConstant.COLLECT)){
                for(Map<String, String> itemIdMap : itemIdList){
                    String itemId = itemIdMap.get("itemId");

                    // businessid 没有值
                    if(itemId == null || itemId.trim().equals("")){
                        List<Map<String, Object>> invoiceList = this.reassignJdbcDao.getInvoiceListByVouherNo("YCUSTOMERTITLE", strVouchNo);
                        for(Map<String, Object> invoiceInfo : invoiceList){
                            // 发票金额
                            BigDecimal billAmount = new BigDecimal(invoiceInfo.get("billAmount").toString());

                            // 此发票相关清帐金额(不包含本次金额)
                            BigDecimal clearAmount = this.reassignJdbcDao.getSumClearAmountForCollect(invoiceInfo.get("invoice").toString(),
                                    businessId);

                            // 发票金额和清帐总金额相等： 设置为结清
                            if(billAmount.compareTo(clearAmount) == 0){
                                this.unclearCustomerService.updateCustomerTitleIsClearedByInvoice(invoiceInfo.get("invoice").toString(),
                                        cleared.isCleared);
                            }else{
                                this.unclearCustomerService.updateCustomerTitleIsClearedByInvoice(invoiceInfo.get("invoice").toString(),
                                        cleared.notCleared);
                            }
                        }
                    }else{
                        // 将YCOLLECTITEM中ISCLEAR设为未结清
                        this.reassignJdbcDao.setCollectItemIsCleared(itemId, cleared.notCleared);

                    }
                }
            }
            // 重分配付款
            else if(reassignType.equals(ReassignConstant.PAYMENT)){
                for(Map<String, String> itemIdMap : itemIdList){
                    String itemId = itemIdMap.get("itemId");

                    // businessid 没有值
                    if(itemId == null || itemId.trim().equals("")){
                        List<Map<String, Object>> invoiceList = this.reassignJdbcDao.getInvoiceListByVouherNo("YVENDORTITLE", strVouchNo);
                        for(Map<String, Object> invoiceInfo : invoiceList){
                            // 发票金额
                            BigDecimal billAmount = new BigDecimal(invoiceInfo.get("billAmount").toString());

                            // 此发票相关清帐金额(不包含本次金额)
                            BigDecimal clearAmount = this.reassignJdbcDao.getSumClearAmountForPayment(invoiceInfo.get("invoice").toString(),
                                    businessId);

                            // 发票金额和清帐总金额相等： 设置为结清
                            if(billAmount.compareTo(clearAmount) == 0){
                                this.vendorService.updateVendorTitleIsClearedByInvoice(invoiceInfo.get("invoice").toString(), cleared.isCleared);
                            }else{
                                this.vendorService.updateVendorTitleIsClearedByInvoice(invoiceInfo.get("invoice").toString(), cleared.notCleared);
                            }

                        }
                    }else{
                        // 将YPAYMENTITEM中BILLISCLEAR设为未结清
                        this.reassignJdbcDao.setPaymentItemIsCleared(itemId, cleared.notCleared);
                    }
                }
            }
            // 重分配票清收款
            else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT)){
                for(Map<String, String> itemIdMap : itemIdList){
                    String itemId = itemIdMap.get("itemId");

                    // businessid 没有值
                    if(itemId == null || itemId.trim().equals("")){
                        List<Map<String, Object>> invoiceList = this.reassignJdbcDao.getInvoiceListByVouherNo("YCUSTOMERTITLE", strVouchNo);
                        for(Map<String, Object> invoiceInfo : invoiceList){
                            // 发票金额
                            BigDecimal billAmount = new BigDecimal(invoiceInfo.get("billAmount").toString());

                            // 此发票相关清帐金额(不包含本次金额)
                            BigDecimal clearAmount = this.reassignJdbcDao.getSumBillClearAmountForBillClearCollect(invoiceInfo.get("invoice")
                                    .toString(), businessId);

                            // 发票金额和清帐总金额相等： 设置为结清
                            if(billAmount.compareTo(clearAmount) == 0){
                                this.unclearCustomerService.updateCustomerTitleIsClearedByInvoice(invoiceInfo.get("invoice").toString(),
                                        cleared.isCleared);
                            }else{
                                this.unclearCustomerService.updateCustomerTitleIsClearedByInvoice(invoiceInfo.get("invoice").toString(),
                                        cleared.notCleared);
                            }
                        }
                    }else{
                        // 货款金额
                        BigDecimal goodAmound = this.reassignJdbcDao.getGoodSAmountForCollect(itemId);

                        // 收款分配行项目关联清帐金额
                        BigDecimal clearAmount = this.reassignJdbcDao.getSumClearAmountForBillClearCollect(itemId, businessId);

                        if(goodAmound.compareTo(clearAmount) == 0){
                            this.reassignJdbcDao.setCollectItemIsCleared(itemId, cleared.isCleared);
                        }else{
                            this.reassignJdbcDao.setCollectItemIsCleared(itemId, cleared.notCleared);
                        }
                    }
                }
            }
            // 重分配票清付款
            else if(reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)){
                for(Map<String, String> itemIdMap : itemIdList){
                    String itemId = itemIdMap.get("itemId");

                    // businessid 没有值
                    if(itemId == null || itemId.trim().equals("")){
                        List<Map<String, Object>> invoiceList = this.reassignJdbcDao.getInvoiceListByVouherNo("YVENDORTITLE", strVouchNo);
                        for(Map<String, Object> invoiceInfo : invoiceList){
                            // 发票金额
                            BigDecimal billAmount = new BigDecimal(invoiceInfo.get("billAmount").toString());

                            // 此发票相关清帐金额(不包含本次金额)
                            BigDecimal clearAmount = this.reassignJdbcDao.getSumBillClearAmountForBillClearPayment(invoiceInfo.get("invoice")
                                    .toString(), businessId);

                            // 发票金额和清帐总金额相等： 设置为结清
                            if(billAmount.compareTo(clearAmount) == 0){
                                this.vendorService.updateVendorTitleIsClearedByInvoice(invoiceInfo.get("invoice").toString(), cleared.isCleared);
                            }else{
                                this.vendorService.updateVendorTitleIsClearedByInvoice(invoiceInfo.get("invoice").toString(), cleared.notCleared);
                            }

                        }
                    }else{
                        // 货款金额
                        BigDecimal goodAmound = this.reassignJdbcDao.getGoodSAmountForPayment(itemId);

                        // 付款分配行项目关联清帐金额
                        BigDecimal clearAmount = this.reassignJdbcDao.getSumClearAmountForBillClearPayment(itemId, businessId);

                        if(goodAmound.compareTo(clearAmount) == 0){
                            this.reassignJdbcDao.setPaymentItemIsCleared(itemId, cleared.isCleared);
                        }else{
                            this.reassignJdbcDao.setPaymentItemIsCleared(itemId, cleared.notCleared);
                        }

                    }
                }
            }
            // 重分配退款
            else if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT)){
                // 客户退款
                for(Map<String, String> itemIdMap : itemIdList){
                    String itemId = itemIdMap.get("itemId");

                    // businessid 没有值
                    if(itemId == null || itemId.trim().equals("")){
                        List<Map<String, Object>> invoiceList = this.reassignJdbcDao.getInvoiceListByVouherNo("YCUSTOMERTITLE", strVouchNo);
                        for(Map<String, Object> invoiceInfo : invoiceList){
                            // 发票金额
                            BigDecimal billAmount = new BigDecimal(invoiceInfo.get("billAmount").toString());

                            // 此发票相关清帐金额(不包含本次金额)
                            BigDecimal clearAmount = this.reassignJdbcDao.getSumBillClearAmountForBillClearCollect(invoiceInfo.get("invoice")
                                    .toString(), businessId);

                            // 发票金额和清帐总金额相等： 设置为结清
                            if(billAmount.compareTo(clearAmount) == 0){
                                this.unclearCustomerService.updateCustomerTitleIsClearedByInvoice(invoiceInfo.get("invoice").toString(),
                                        cleared.isCleared);
                            }else{
                                this.unclearCustomerService.updateCustomerTitleIsClearedByInvoice(invoiceInfo.get("invoice").toString(),
                                        cleared.notCleared);
                            }
                        }
                    }else{
                        // 货款金额
                        BigDecimal goodAmound = this.reassignJdbcDao.getGoodSAmountForCollect(itemId);

                        // 收款分配行项目关联清帐金额（不包含本次金额）
                        BigDecimal clearAmount = this.reassignJdbcDao.getSumClearAmountForBillClearCollect(itemId, businessId);

                        if(goodAmound.compareTo(clearAmount) == 0){
                            this.reassignJdbcDao.setCollectItemIsCleared(itemId, cleared.isCleared);
                        }else{
                            this.reassignJdbcDao.setCollectItemIsCleared(itemId, cleared.notCleared);
                        }
                    }
                }
            }else if(reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){
                // 供应商退款
                for(Map<String, String> itemIdMap : itemIdList){
                    String itemId = itemIdMap.get("itemId");

                    // businessid 没有值
                    if(itemId == null || itemId.trim().equals("")){
                        List<Map<String, Object>> invoiceList = this.reassignJdbcDao.getInvoiceListByVouherNo("YVENDORTITLE", strVouchNo);
                        for(Map<String, Object> invoiceInfo : invoiceList){
                            // 发票金额
                            BigDecimal billAmount = new BigDecimal(invoiceInfo.get("billAmount").toString());

                            // 此发票相关清帐金额(不包含本次金额)
                            BigDecimal clearAmount = this.reassignJdbcDao.getSumBillClearAmountForBillClearPayment(invoiceInfo.get("invoice")
                                    .toString(), businessId);

                            // 发票金额和清帐总金额相等： 设置为结清
                            if(billAmount.compareTo(clearAmount) == 0){
                                this.vendorService.updateVendorTitleIsClearedByInvoice(invoiceInfo.get("invoice").toString(), cleared.isCleared);
                            }else{
                                this.vendorService.updateVendorTitleIsClearedByInvoice(invoiceInfo.get("invoice").toString(), cleared.notCleared);
                            }

                        }
                    }else{
                        // 货款金额
                        BigDecimal goodAmound = this.reassignJdbcDao.getGoodSAmountForPayment(itemId);

                        // 付款分配行项目关联清帐金额（不包含本次金额）
                        BigDecimal clearAmount = this.reassignJdbcDao.getSumClearAmountForBillClearPayment(itemId, businessId);

                        if(goodAmound.compareTo(clearAmount) == 0){
                            this.reassignJdbcDao.setPaymentItemIsCleared(itemId, cleared.isCleared);
                        }else{
                            this.reassignJdbcDao.setPaymentItemIsCleared(itemId, cleared.notCleared);
                        }
                    }
                }
            }
        }
    }

	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-21 
	 * 根据付款ID判断该单号是内贸还是进口 1：内贸付款 2：进口付款
	 */
	public int judgePaymentTradeType(String paymentId) {
		List<Map<String, String>> list = this.reassignJdbcDao
				.getPaymentInfo(paymentId);
		if (list != null && list.size() > 0) {
			String tradeType = list.get(0).get("TRADE_TYPE");
			if (tradeType.equals("01")) {
				return 1; // 1：内贸付款
			} else {
				return 2; // 2：进口付款
			}
		}
		return 0;
	}

	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-22 
	 * 根据退款ID判断该单号是客户还是供应商 1：客户退款 2：供应商退款
	 */
	public int judgeRefundmentType(String refundId) {
		List<Map<String, String>> list = this.reassignJdbcDao
				.getRefundInfo(refundId);
		if (list != null && list.size() > 0) {
			String customer = list.get(0).get("CUSTOMER");
			if (null == customer || "".equals(customer.trim())) {
				return 2; // 2：供应商退款
			} else {
				return 1; // 1：客户退款
			}
		}
		return 0;
	}

	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-22 
	 * 流程结束时，根据重分配信息自动去更新对应的表审批状态（"客户单清"、"供应商单清"不会进来）
	 */
    public void finishProcess(Reassign reassign){
        String reassignType = reassign.getReassigntype();
        if(reassignType.equals(ReassignConstant.COLLECT)){                  //【收款重分配】
            String collectId = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.COLLECT, reassign.getReassignboid());
            String busId = "COLLECTID='" + collectId + "'";
            this.reassignJdbcDao.updateProcess("YCOLLECT", busId, "流程结束", "3");
        }else if(reassignType.equals(ReassignConstant.PAYMENT)){            //【付款重分配】
            String paymentId = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.PAYMENT, reassign.getReassignboid());
            String busId = "PAYMENTID='" + paymentId + "'";
            this.reassignJdbcDao.updateProcess("YPAYMENT", busId, "流程结束", "4");
        }else if(reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)){   //【票清付款重分配】
            String billclearId = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.BILLCLEARPAYMENT, reassign.getReassignboid());
            String busId = "BILLCLEARID='" + billclearId + "'";
            this.reassignJdbcDao.updateProcess("YBILLCLEAR", busId, "流程结束", "3");
        }else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT)){   //【票清收款重分配】
            String billclearId = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.BILLCLEARCOLLECT, reassign.getReassignboid());
            String busId = "BILLCLEARID='" + billclearId + "'";
            this.reassignJdbcDao.updateProcess("YBILLCLEAR", busId, "流程结束", "3");
        }else if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT)){ //【客户退款重分配】
            String refundId = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.CUSTOMERREFUNDMENT, reassign.getReassignboid());
            String busId = "REFUNDMENTID='" + refundId + "'";
            this.reassignJdbcDao.updateProcess("YREFUNDMENT", busId, "流程结束", "3");
        }else if(reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){ //【供应商退款重分配】
            String refundId = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.SUPPLIERREFUNDMENT, reassign.getReassignboid());
            String busId = "REFUNDMENTID='" + refundId + "'";
            this.reassignJdbcDao.updateProcess("YREFUNDMENT", busId, "流程结束", "3");
        }
    }

	/**
	 * @创建作者：邱杰烜
	 * @创建日期：2010-10-22 
	 * 流程结束时，根据重分配信息自动去更新对应的表审批状态（"客户单清"、"供应商单清"不会进来）
	 */
    public void rejectProcess(Reassign reassign){
        String reassignType = reassign.getReassigntype();
        if(reassignType.equals(ReassignConstant.COLLECT)){                  //【收款重分配】
            String id = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.COLLECT, reassign.getReassignboid());
            String busId = "COLLECTID='" + id + "'";
            this.reassignJdbcDao.updateProcess("YCOLLECT", busId, "审批不通过", "4");
        }else if(reassignType.equals(ReassignConstant.PAYMENT)){            //【付款重分配】
            String id = this.reassignJdbcDao.getNewNoIdByOldNoId(ReassignConstant.PAYMENT, reassign.getReassignboid());
            String busId = "PAYMENTID='" + id + "'";
            this.reassignJdbcDao.updateProcess("YPAYMENT", busId, "审批不通过", "5");
        }else if(reassignType.equals(ReassignConstant.BILLCLEARPAYMENT)){   //【票清付款重分配】

        }else if(reassignType.equals(ReassignConstant.BILLCLEARCOLLECT)){   //【票清收款重分配】

        }else if(reassignType.equals(ReassignConstant.CUSTOMERREFUNDMENT)){ //【客户退款重分配】
        
        }else if(reassignType.equals(ReassignConstant.SUPPLIERREFUNDMENT)){ //【供应商退款重分配】
            
        }
    }
    
    /**
     * @创建作者：邱杰烜
     * @创建日期：2010-12-14
     * 根据当前资金部人员取得所属的部门
     * @param username 当前办理人的username
     */
    public String getDeptcodeByUsername(String username){
        return this.reassignJdbcDao.getDeptcodeByUsername(username);
    }
    /**
     * 废除已生成的旧的凭证号
     * @param reassignType
     * @param reassignMethod
     * @param businessid
     */
    public void abolishOldVoucherNo(String reassignType, String reassignMethod,String businessid){
    	boolean flag =true;
    	//重置（到业务部门重新分配）,重置（财务部直接解除分配关系）,不用废除,
    	if(ReassignConstant.RESET_TO_BS.equals(reassignMethod) || ReassignConstant.RESET_TO_FI.equals(reassignMethod)){
    		flag=false;
    	}
    	if(flag){
	    	 List<CertificateNo> certificateNoList = this.reassignJdbcDao.getVoucherbyBussinessID2(businessid, reassignType, reassignMethod);
	
	         /**
	          * 有凭证编号
	          */
	         if(certificateNoList != null && certificateNoList.size() > 0){
	        	for(CertificateNo certificateNo :certificateNoList){
	        		voucherService.updateVoucherState(certificateNo.getBelnr(), certificateNo.getBukrs(), certificateNo.getGjahr());
	        	}
	         }
    	}
    }
    
    /**
	 * 
	 * 取得客户部分清的单据ID信息,flag=1为当前部分清，（当前未清金额不等于清账金额）否则 有部分全部的数据（当前金额不等于清账金额）
	 * @return Map<String,List<IKeyValue>> key clear 为部分清清账单据 reassign为重分配当前部分清的单据
	 */	 
	public Map<String,List<IKeyValue>> getPartClearByCustomer(String clearid,String type2,InfoObject infoObject,String flag){
		Map<String,List<IKeyValue>> map =new HashMap<String,List<IKeyValue>>();
//		保存清账的ID号
		List<IKeyValue> clearIdList = new ArrayList<IKeyValue>();
		KeyValue keyValue2 = new KeyValue(); 
		keyValue2.setKey(clearid);
		keyValue2.setValue(type2);
		clearIdList.add(keyValue2);
		List<IKeyValue> reassignList  = new ArrayList<IKeyValue>();
		reassignList.add(keyValue2);		
		infoObject.setRight(true);
		
		List<IKeyValue> partIdsList = new ArrayList<IKeyValue>();
		if("1".equals(flag)){
			partIdsList = customerClearAccountJdbcDao.getPartIdsByCustomerClear(clearid);			
		}else{
			partIdsList = reassignJdbcDao.getPartIdsByCustomerClear(clearid);
		}
//		保存已经找过的票或款id
		List<String> titleIdList = new ArrayList<String>();
		List<String> collectitemOrBillnoList = new ArrayList<String>();
		for(IKeyValue keyValue : partIdsList){
//		value保存titleid,key保存billno或collectitemid
			String titleid = keyValue.getKey();	
			String collectitemidOrBillno = keyValue.getValue();			
			titleIdList.add(titleid);
			collectitemOrBillnoList.add(collectitemidOrBillno);
			getCustomerClearPart(titleid,collectitemidOrBillno,clearid,clearIdList,titleIdList,collectitemOrBillnoList,reassignList,infoObject,flag);

		}
//		判断是否正确
		if(infoObject.isRight()){
			map.put("clear", clearIdList);
			map.put("reassign", reassignList);
			return map;
		}else{
			return map;
		}		
	}
	
    /**
	 * 取得客户除本身外当前部分清的数据（当前部分清指未清金额等于不清账金额）有2条或2条以上记录的数据
	 */      
	public void getCustomerClearPart(String titleid,String collectitemidOrBillno,String clearid,List<IKeyValue> clearIdList,List<String> titleIdList,List<String> collectitemOrBillnoList,List<IKeyValue> reassignList,InfoObject infoObject,String flag){
//		如果有错误不用在做
		if(!infoObject.isRight()){
			return ;
		}
//		取得除本次以外的款方有部分清的，客户清账ID 
		List<IKeyValue> clearList = customerClearAccountJdbcDao.getPartIdsByCustomerClear(titleid, clearid,collectitemidOrBillno);
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
			List<IKeyValue> kvList = new ArrayList<IKeyValue>();
			if("1".equals(flag)){
				kvList = customerClearAccountJdbcDao.getPartIdsByCustomerClear(clid);				
			}else{
				kvList = reassignJdbcDao.getPartIdsByCustomerClear(clid);
			}
			for(IKeyValue keyValue :kvList){
				String titleid2 = keyValue.getKey();
				String collectitemidOrBillno2 = keyValue.getValue();				
//				如果有在titleIdList.CollectitemOrBillnoList说明有跟以前的有找过了，不用再找
				if(titleIdList.contains(titleid2) && collectitemOrBillnoList.contains(collectitemidOrBillno2)){
					continue;
				}else{
					titleIdList.add(titleid2);
					collectitemOrBillnoList.add(collectitemidOrBillno2);
					if(!this.isSame(clid, reassignList)){
						KeyValue keyValue3 = new KeyValue(); 
						keyValue3.setKey(clid);
						keyValue3.setValue(type2);
	//					增加重分配的单号
						reassignList.add(keyValue3);
					}
				}
				getCustomerClearPart(titleid2,collectitemidOrBillno2,keyValue.getValue2(),clearIdList,titleIdList,collectitemOrBillnoList,reassignList,infoObject,flag);
			}
		}		
	}
	
	  /**
	 * 
	 * 取得供应商部分清的单据ID信息,flag=1为当前部分清，（当前未清金额不等于清账金额）否则 有部分全部的数据（当前金额不等于清账金额）
	 * @return Map<String,List<IKeyValue>> key clear 为部分清清账单据 reassign为重分配当前部分清的单据
	 */	 
	public Map<String,List<IKeyValue>> getPartClearBySupplier(String clearid,String type2,InfoObject infoObject,String flag){
		Map<String,List<IKeyValue>> map =new HashMap<String,List<IKeyValue>>();
//		保存清账的ID号
		List<IKeyValue> clearIdList = new ArrayList<IKeyValue>();
		KeyValue keyValue2 = new KeyValue(); 
		keyValue2.setKey(clearid);
		keyValue2.setValue(type2);
		clearIdList.add(keyValue2);
		List<IKeyValue> reassignList  = new ArrayList<IKeyValue>();
		reassignList.add(keyValue2);		
		infoObject.setRight(true);
		
		List<IKeyValue> partIdsList = new ArrayList<IKeyValue>();
		if("1".equals(flag)){
			partIdsList = supplierClearAccountJdbcDao.getPartIdsBySupplierClear(clearid);			
		}else{
			partIdsList = reassignJdbcDao.getPartIdsBySupplierClear(clearid);
		}
//		保存已经找过的票或款id
		List<String> titleIdList = new ArrayList<String>();
		List<String> itemOrBillnoList = new ArrayList<String>();
		for(IKeyValue keyValue : partIdsList){
//		value保存titleid,key保存billno或collectitemid
			String titleid = keyValue.getKey();	
			String itemidOrBillno = keyValue.getValue();			
			titleIdList.add(titleid);
			itemOrBillnoList.add(itemidOrBillno);
			getSupplierClearPart(titleid,itemidOrBillno,clearid,clearIdList,titleIdList,itemOrBillnoList,reassignList,infoObject,flag);

		}
//		判断是否正确
		if(infoObject.isRight()){
			map.put("clear", clearIdList);
			map.put("reassign", reassignList);
			return map;
		}else{
			return map;
		}		
	}
	
    /**
	 * 取得供应商除本身外当前部分清的数据（当前部分清指未清金额等于不清账金额）有2条或2条以上记录的数据
	 */      
	public void getSupplierClearPart(String titleid,String itemidOrBillno,String clearid,List<IKeyValue> clearIdList,List<String> titleIdList,List<String> itemOrBillnoList,List<IKeyValue> reassignList,InfoObject infoObject,String flag){
//		如果有错误不用在做
		if(!infoObject.isRight()){
			return ;
		}
//		取得除本次以外的款方有部分清的，客户清账ID 
		List<IKeyValue> clearList = supplierClearAccountJdbcDao.getPartIdsBySupplierClear(titleid, clearid,itemidOrBillno);
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
			List<IKeyValue> kvList = new ArrayList<IKeyValue>();
			if("1".equals(flag)){
				kvList = supplierClearAccountJdbcDao.getPartIdsBySupplierClear(clid);				
			}else{
				kvList = reassignJdbcDao.getPartIdsBySupplierClear(clid);
			}
			for(IKeyValue keyValue :kvList){
				String titleid2 = keyValue.getKey();
				String collectitemidOrBillno2 = keyValue.getValue();				
//				如果有在titleIdList.CollectitemOrBillnoList说明有跟以前的有找过了，不用再找
				if(titleIdList.contains(titleid2) && itemOrBillnoList.contains(collectitemidOrBillno2)){
					continue;
				}else{
					titleIdList.add(titleid2);
					itemOrBillnoList.add(collectitemidOrBillno2);
					if(!this.isSame(clid, reassignList)){
						KeyValue keyValue3 = new KeyValue(); 
						keyValue3.setKey(clid);
						keyValue3.setValue(type2);
	//					增加重分配的单号
						reassignList.add(keyValue3);
					}
				}
				getSupplierClearPart(titleid2,collectitemidOrBillno2,keyValue.getValue2(),clearIdList,titleIdList,itemOrBillnoList,reassignList,infoObject,flag);
			}
		}		
	}
	
	/***
	 * 
	 * @param clid
	 * @param list
	 * @return 相同的返回true
	 */
	public boolean isSame(String clid,List<IKeyValue> list){
		for(IKeyValue kv : list){
			if(clid.equals(kv.getKey())){
				return true;
			}
		}
		return false;
	}
	
	public String convertType(String type){
		
		if(ReassignConstant.COLLECT.equals(type)){
			return ClearConstant.COLLECT_TYPE ;
		}else if(ReassignConstant.PAYMENT.equals(type)){
			return ClearConstant.PAYMENT_TYPE;		
		}else if(ReassignConstant.BILLCLEARCOLLECT.equals(type)){
			return ClearConstant.BILL_TYPE;
		}else if(ReassignConstant.BILLCLEARPAYMENT.equals(type)){
			return ClearConstant.BILL_TYPE_P;
		}else if(ReassignConstant.CUSTOMERREFUNDMENT.equals(type)){
			return ClearConstant.REFUNDMENT_TYPE;
		}else if(ReassignConstant.SUPPLIERREFUNDMENT.equals(type)){
			return ClearConstant.REFUNDMENT_TYPE_L;
		}else if(ReassignConstant.CUSTOMERSINGLECLEAR.equals(type)){
			return ClearConstant.SINGLE_TYPE;
		}else if(ReassignConstant.SUPPLIERSINGLECLEAR.equals(type)){
			return ClearConstant.SINGLE_TYPE_L;
		}else{
			return null;
		}		
	}
	public String convertType2(String type){
		
		if( ClearConstant.COLLECT_TYPE.equals(type)){
			return ReassignConstant.COLLECT ;
		}else if(ClearConstant.PAYMENT_TYPE.equals(type)){
			return ReassignConstant.PAYMENT;		
		}else if(ClearConstant.BILL_TYPE.equals(type)){
			return ReassignConstant.BILLCLEARCOLLECT;
		}else if(ClearConstant.BILL_TYPE_P.equals(type)){
			return ReassignConstant.BILLCLEARPAYMENT;
		}else if(ClearConstant.REFUNDMENT_TYPE.equals(type)){
			return ReassignConstant.CUSTOMERREFUNDMENT;
		}else if(ClearConstant.REFUNDMENT_TYPE_L.equals(type)){
			return ReassignConstant.SUPPLIERREFUNDMENT;
		}else if(ClearConstant.SINGLE_TYPE.equals(type)){
			return ReassignConstant.CUSTOMERSINGLECLEAR;
		}else if(ClearConstant.SINGLE_TYPE_L.equals(type)){
			return ReassignConstant.SUPPLIERSINGLECLEAR;
		}else{
			return null;
		}		
	}
	
	public List<CertificateNo> getClearVoucher(String bussinessid){
		return this.reassignJdbcDao.getClearVoucher(bussinessid);
	}
}