/*
 * @(#)PackingLoanService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年05月19日 16点48分19秒
 *  描　述：创建
 */
package com.infolion.xdss3.packingloan.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.bankInfo.dao.BankInfoJdbcDao;
import com.infolion.xdss3.customerDrawback.dao.CustomerRefundItemJdbcDao;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.masterData.dao.VendorTitleJdbcDao;
import com.infolion.xdss3.packingloan.dao.PackingLoanJdbcDao;
import com.infolion.xdss3.packingloan.domain.PackingBankItem;
import com.infolion.xdss3.packingloan.domain.PackingLoan;
import com.infolion.xdss3.packingloan.domain.PackingReBankItem;
import com.infolion.xdss3.packingloan.domain.PackingReBankTwo;
import com.infolion.xdss3.packingloanGen.service.PackingLoanServiceGen;
import com.infolion.xdss3.payment.dao.PayMentCBillJdbcDao;
import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;
import com.infolion.xdss3.voucher.dao.VoucherHibernateDao;
import com.infolion.xdss3.voucher.dao.VoucherJdbcDao;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucheritem.dao.VoucherItemJdbcDao;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 打包贷款(PackingLoan)服务类
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
public class PackingLoanService extends PackingLoanServiceGen {
    private Log log = LogFactory.getFactory().getLog(this.getClass());

    @Autowired
    private PackingLoanJdbcDao packingLoanJdbcDao;

    public void setPackingLoanJdbcDao(PackingLoanJdbcDao packingLoanJdbcDao) {
        this.packingLoanJdbcDao = packingLoanJdbcDao;
    }

    @Autowired
    private SysDeptJdbcDao sysDeptJdbcDao;

    public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
        this.sysDeptJdbcDao = sysDeptJdbcDao;
    }

    @Autowired
    private CustomerRefundItemJdbcDao customerRefundItemJdbcDao;

    public void setCustomerRefundItemJdbcDao(CustomerRefundItemJdbcDao customerRefundItemJdbcDao) {
        this.customerRefundItemJdbcDao = customerRefundItemJdbcDao;
    }

    @Autowired
    private VendorTitleJdbcDao vendorTitleJdbcDao;

    public void setVendorTitleJdbcDao(VendorTitleJdbcDao vendorTitleJdbcDao) {
        this.vendorTitleJdbcDao = vendorTitleJdbcDao;
    }

    @Autowired
    private VoucherHibernateDao voucherHibernateDao;

    public void setVoucherHibernateDao(VoucherHibernateDao voucherHibernateDao) {
        this.voucherHibernateDao = voucherHibernateDao;
    }

    @Autowired
    private VoucherJdbcDao voucherJdbcDao;

    public void setVoucherJdbcDao(VoucherJdbcDao voucherJdbcDao) {
        this.voucherJdbcDao = voucherJdbcDao;
    }

    @Autowired
    protected VoucherItemJdbcDao voucherItemJdbcDao;

    public void setVoucherItemJdbcDao(VoucherItemJdbcDao voucherItemJdbcDao) {
        this.voucherItemJdbcDao = voucherItemJdbcDao;
    }

    @Autowired
    private PayMentCBillJdbcDao importPayMentCBillJdbcDao;

    public void setImportPayMentCBillJdbcDao(PayMentCBillJdbcDao importPayMentCBillJdbcDao) {
        this.importPayMentCBillJdbcDao = importPayMentCBillJdbcDao;
    }

    @Autowired
    private BankInfoJdbcDao bankInfoJdbcDao;

    public void setBankInfoJdbcDao(BankInfoJdbcDao bankInfoJdbcDao) {
        this.bankInfoJdbcDao = bankInfoJdbcDao;
    }

    /**
     * 单独更新 打包贷款 业务主对象
     * 
     * @param packingLoan
     */
    public void updatePackingLoan(PackingLoan packingLoan) {
        this.packingLoanHibernateDao.saveOrUpdate(packingLoan);
    }

    /**
     * 得到清帐凭证的抬头信息
     * 
     * @param packingLoan
     * @param strClass
     * @return
     */
    public Voucher getClearVoucher(PackingLoan packingLoan, String strClass) {
        Voucher voucher = new Voucher();

        UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

        // 业务编号
        voucher.setBusinessid(packingLoan.getPackingid());
        // 业务类型
        voucher.setBusinesstype("10");
        // 凭证类型
        voucher.setVouchertype("KZ");
        // 凭证抬头文本
        voucher.setVouchertext("清帐凭证");
        // 过账日期
        voucher.setCheckdate(packingLoan.getAccountdate().replace("-", ""));
        // 公司代码
        String bukrs = getCompanyIDByDeptID(packingLoan.getDept_id());
        voucher.setCompanycode(bukrs); // 公司代码
        // 货币
        voucher.setCurrency(packingLoan.getPackingBankItem().iterator().next().getCurrency());
        // 凭证日期
        voucher.setVoucherdate(packingLoan.getVoucherdate().replace("-", ""));
        // 会计年度
        voucher.setFiyear(packingLoan.getAccountdate().substring(0, 4));
        // 会计期间
        voucher.setFiperiod(packingLoan.getAccountdate().substring(5, 7));
        // 输入日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        voucher.setImportdate(dateFormat.format(new Date()));
        // 输入者
        voucher.setImporter(userContext.getSysUser().getUserName());
        // 预制者
        voucher.setPreparer(userContext.getSysUser().getUserName());
        // 银行科目号
        Iterator<PackingBankItem> bankItemit = packingLoan.getPackingBankItem().iterator();
        // 计息日
        voucher.setValut(dateFormat.format(new Date()));
        // 科目类型
        voucher.setAgkoa("K");
        // 业务范围
        voucher.setGsber(this.sysDeptJdbcDao.getDeptCodeById(packingLoan.getDept_id()));
        // 付款清帐标识
        voucher.setFlag("P");
        // 凭证分类
        voucher.setVoucherclass(strClass);
        // 汇率
        voucher.setExchangerate(packingLoan.getPackingreate());
        // 清帐凭证状态
        voucher.setBstat("A");

        return voucher;
    }

    /**
     * 处理清帐凭证行项目
     * 
     * @param strVoucherNo
     * @param strRowNo
     * @param strYear
     * @return
     */
    public VoucherItem getClearVoucherItem(String strVoucherNo, String strRowNo, String strYear, String strBusVoucherId) {
        VoucherItem voucherItem = new VoucherItem();
        voucherItem.setVoucherno(strVoucherNo);
        voucherItem.setRownumber(strRowNo);
        voucherItem.setFiyear(strYear);
        voucherItem.setFlag("P");
        voucherItem.setAmount(new BigDecimal("0"));
        voucherItem.setAmount2(new BigDecimal("0"));
        voucherItem.setBusvoucherid(strBusVoucherId);

        return voucherItem;
    }

    /**
     * 根据部门ID获得公司ID
     */
    public String getCompanyIDByDeptID(String deptID) {
        return this.sysDeptJdbcDao.getCompanyCode(deptID);
    }

    /**
     * 得到结算科目的凭证行项目
     * 
     * @param importSettSubj
     * @return
     */
    public Set<VoucherItem> getSettSubjVoucherItem(PackingLoan packingLoan) {
        Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
        BigDecimal amount = new BigDecimal(0);
        BigDecimal standardAmount = new BigDecimal(0);
        SettleSubject settleSubjectPay = packingLoan.getSettleSubject();
        String bukrs = getCompanyIDByDeptID(packingLoan.getDept_id());
        Set<PackingBankItem> bankItems = packingLoan.getPackingBankItem();
        PackingBankItem bankItem = bankItems.iterator().next();

        if (null != settleSubjectPay) {
            amount = settleSubjectPay.getAmount1();
            standardAmount = settleSubjectPay.getStandardamount1();
            if (amount.abs().compareTo(new BigDecimal(0)) == 1
                    || standardAmount.abs().compareTo(new BigDecimal(0)) == 1) {
                // 开始添加凭证行项目
                VoucherItem voucherItem = new VoucherItem();

                // 客户记账码
                if ("S".equals(settleSubjectPay.getDebtcredit1())) {
                    voucherItem.setCheckcode("40");
                    // 借/贷标识符
                    voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit1());
                }

                // 客户记账码
                if ("H".equals(settleSubjectPay.getDebtcredit1())) {
                    voucherItem.setCheckcode("50");
                    // 借/贷标识符
                    voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit1());
                }
                // 特殊G/L标识
                voucherItem.setGlflag("");
                String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(
                        settleSubjectPay.getSettlesubject1(), bukrs);
                // 科目
                voucherItem.setSubject(settleSubjectPay.getSettlesubject1());
                // 科目说明
                voucherItem.setSubjectdescribe(subjectname);
                // 统驭项目
                voucherItem.setControlaccount(settleSubjectPay.getSettlesubject1());
                // 统驭科目说明
                voucherItem.setCaremark(subjectname);
                // 货币
                voucherItem.setCurrency(bankItem.getCurrency());
                // 货币金额
                voucherItem.setAmount(settleSubjectPay.getAmount1());
                // 本位币金额
                voucherItem.setAmount2(settleSubjectPay.getStandardamount1());
                // 部门
                voucherItem.setDepid(settleSubjectPay.getDepid1());
                // 文本
                voucherItem.setText(packingLoan.getText());
                // 反记账标识
                if (settleSubjectPay.getAntiaccount1() != null && settleSubjectPay.getAntiaccount1().equals("Y")) {
                    voucherItem.setUncheckflag("X");
                } else {
                    voucherItem.setUncheckflag("");
                }
                // 销售订单
                voucherItem.setSalesorder(settleSubjectPay.getOrderno1());
                // 销售订单行项目号
                voucherItem.setOrderrowno(settleSubjectPay.getRowno1());
                // 成本中心
                voucherItem.setCostcenter(settleSubjectPay.getCostcenter1());
                voucherItem.setBusinessitemid(settleSubjectPay.getSettlesubjectid());
                voucherItemList.add(voucherItem);
            }

            amount = settleSubjectPay.getAmount2();
            standardAmount = settleSubjectPay.getStandardamount2();
            if (amount.abs().compareTo(new BigDecimal(0)) == 1
                    || standardAmount.abs().compareTo(new BigDecimal(0)) == 1) {
                // 开始添加凭证行项目
                VoucherItem voucherItem = new VoucherItem();

                // 客户记账码
                if ("S".equals(settleSubjectPay.getDebtcredit2())) {
                    voucherItem.setCheckcode("40");
                    // 借/贷标识符
                    voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit2());
                }

                // 客户记账码
                if ("H".equals(settleSubjectPay.getDebtcredit2())) {
                    voucherItem.setCheckcode("50");
                    // 借/贷标识符
                    voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit2());
                }
                // 特殊G/L标识
                voucherItem.setGlflag("");
                String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(
                        settleSubjectPay.getSettlesubject2(), bukrs);
                // 科目
                voucherItem.setSubject(settleSubjectPay.getSettlesubject2());
                // 科目说明
                voucherItem.setSubjectdescribe(subjectname);
                // 统驭项目
                voucherItem.setControlaccount(settleSubjectPay.getSettlesubject2());
                // 统驭科目说明
                voucherItem.setCaremark(subjectname);
                // 货币
                voucherItem.setCurrency(bankItem.getCurrency());
                // 货币金额
                voucherItem.setAmount(settleSubjectPay.getAmount2());
                // 本位币金额
                voucherItem.setAmount2(settleSubjectPay.getStandardamount2());
                // 部门
                voucherItem.setDepid(settleSubjectPay.getDepid2());
                // 文本
                voucherItem.setText(packingLoan.getText());
                // 反记账标识
                if (settleSubjectPay.getAntiaccount2() != null && settleSubjectPay.getAntiaccount2().equals("Y")) {
                    voucherItem.setUncheckflag("X");
                } else {
                    voucherItem.setUncheckflag("");
                }
                // 销售订单
                voucherItem.setSalesorder(settleSubjectPay.getOrderno2());
                // 销售订单行项目号
                voucherItem.setOrderrowno(settleSubjectPay.getRowno2());
                // 成本中心
                voucherItem.setCostcenter(settleSubjectPay.getCostcenter2());
                voucherItem.setBusinessitemid(settleSubjectPay.getSettlesubjectid());
                voucherItemList.add(voucherItem);
            }

            amount = settleSubjectPay.getAmount3();
            standardAmount = settleSubjectPay.getStandardamount3();
            if (amount.abs().compareTo(new BigDecimal(0)) == 1
                    || standardAmount.abs().compareTo(new BigDecimal(0)) == 1) {
                // 开始添加凭证行项目
                VoucherItem voucherItem = new VoucherItem();

                // 客户记账码
                if ("S".equals(settleSubjectPay.getDebtcredit3())) {
                    voucherItem.setCheckcode("40");
                    // 借/贷标识符
                    voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit3());
                }

                // 客户记账码
                if ("H".equals(settleSubjectPay.getDebtcredit3())) {
                    voucherItem.setCheckcode("50");
                    // 借/贷标识符
                    voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit3());
                }
                // 特殊G/L标识
                voucherItem.setGlflag("");
                String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(
                        settleSubjectPay.getSettlesubject3(), bukrs);
                // 科目
                voucherItem.setSubject(settleSubjectPay.getSettlesubject3());
                // 科目说明
                voucherItem.setSubjectdescribe(subjectname);
                // 统驭项目
                voucherItem.setControlaccount(settleSubjectPay.getSettlesubject3());
                // 统驭科目说明
                voucherItem.setCaremark(subjectname);
                // 货币
                voucherItem.setCurrency(bankItem.getCurrency());
                // 货币金额
                voucherItem.setAmount(settleSubjectPay.getAmount3());
                // 本位币金额
                voucherItem.setAmount2(settleSubjectPay.getStandardamount3());
                // 部门
                voucherItem.setDepid(settleSubjectPay.getDepid3());
                // 文本
                voucherItem.setText(packingLoan.getText());
                // 反记账标识
                if (settleSubjectPay.getAntiaccount3() != null && settleSubjectPay.getAntiaccount3().equals("Y")) {
                    voucherItem.setUncheckflag("X");
                } else {
                    voucherItem.setUncheckflag("");
                }
                // 销售订单
                voucherItem.setSalesorder(settleSubjectPay.getOrderno3());
                // 销售订单行项目号
                voucherItem.setOrderrowno(settleSubjectPay.getRowno3());
                // 成本中心
                voucherItem.setCostcenter(settleSubjectPay.getCostcenter3());
                voucherItem.setBusinessitemid(settleSubjectPay.getSettlesubjectid());
                voucherItemList.add(voucherItem);
            }

            amount = settleSubjectPay.getAmount4();
            standardAmount = settleSubjectPay.getStandardamount4();
            if (amount.abs().compareTo(new BigDecimal(0)) == 1
                    || standardAmount.abs().compareTo(new BigDecimal(0)) == 1) {
                // 开始添加凭证行项目
                VoucherItem voucherItem = new VoucherItem();

                // 客户记账码
                if ("S".equals(settleSubjectPay.getDebtcredit4())) {
                    voucherItem.setCheckcode("40");
                    // 借/贷标识符
                    voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit4());
                }

                // 客户记账码
                if ("H".equals(settleSubjectPay.getDebtcredit4())) {
                    voucherItem.setCheckcode("50");
                    // 借/贷标识符
                    voucherItem.setDebitcredit(settleSubjectPay.getDebtcredit4());
                }
                // 特殊G/L标识
                voucherItem.setGlflag("");
                String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById(
                        settleSubjectPay.getSettlesubject4(), bukrs);
                // 科目
                voucherItem.setSubject(settleSubjectPay.getSettlesubject4());
                // 科目说明
                voucherItem.setSubjectdescribe(subjectname);
                // 统驭项目
                voucherItem.setControlaccount(settleSubjectPay.getSettlesubject4());
                // 统驭科目说明
                voucherItem.setCaremark(subjectname);
                // 货币
                voucherItem.setCurrency(bankItem.getCurrency());
                // 货币金额
                voucherItem.setAmount(settleSubjectPay.getAmount4());
                // 本位币金额
                voucherItem.setAmount2(settleSubjectPay.getStandardamount4());
                // 部门
                voucherItem.setDepid(settleSubjectPay.getDepid4());
                // 文本
                voucherItem.setText(packingLoan.getText());
                // 反记账标识
                if (settleSubjectPay.getAntiaccount4() != null && settleSubjectPay.getAntiaccount4().equals("Y")) {
                    voucherItem.setUncheckflag("X");
                } else {
                    voucherItem.setUncheckflag("");
                }
                // 销售订单
                voucherItem.setSalesorder(settleSubjectPay.getOrderno4());
                // 销售订单行项目号
                voucherItem.setOrderrowno(settleSubjectPay.getRowno4());
                // 利润中心
                voucherItem.setProfitcenter(settleSubjectPay.getProfitcenter());
                voucherItem.setBusinessitemid(settleSubjectPay.getSettlesubjectid());
                voucherItemList.add(voucherItem);
            }
        }
        return voucherItemList;
    }

    /**
     * 纯资金往来
     * 
     * @param fundFlowPay
     * @return
     */
    public Set<VoucherItem> getFundFlowVoucherItem(PackingLoan packingLoan) {
        Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
        FundFlow fundFlowPay = packingLoan.getFundFlow();
        String bukrs = getCompanyIDByDeptID(packingLoan.getDept_id());
        BigDecimal amount = new BigDecimal(0);
        BigDecimal standardAmount = new BigDecimal(0);
        Set<PackingBankItem> bankItems = packingLoan.getPackingBankItem();
        PackingBankItem bankItem = bankItems.iterator().next();
        if (null == fundFlowPay) {
            return voucherItemList;
        }
        amount = fundFlowPay.getAmount1();
        standardAmount = fundFlowPay.getStandardamount1();
        if (amount.abs().compareTo(new BigDecimal(0)) == 1 || standardAmount.abs().compareTo(new BigDecimal(0)) == 1) {
            VoucherItem voucherItem = new VoucherItem();

            // 客户记账码
            if ("S".equals(fundFlowPay.getDebtcredit1())) {
                if (StringUtils.isNullBlank(fundFlowPay.getSpecialaccount1())) {
                    voucherItem.setCheckcode("01");
                } else {
                    voucherItem.setCheckcode("09");
                }
                // 借/贷标识符
                voucherItem.setDebitcredit(fundFlowPay.getDebtcredit1());
            }

            // 客户记账码
            if ("H".equals(fundFlowPay.getDebtcredit1())) {
                if (StringUtils.isNullBlank(fundFlowPay.getSpecialaccount1())) {
                    voucherItem.setCheckcode("11");
                } else {
                    voucherItem.setCheckcode("19");
                }
                // 借/贷标识符
                voucherItem.setDebitcredit(fundFlowPay.getDebtcredit1());
            }
            // 客户记账码
            // voucherItem.setCheckcode("29");
            // 借/贷标识符
            // voucherItem.setDebitcredit(fundFlowPay.getDebtcredit1());
            // 特殊G/L标识
            voucherItem.setGlflag(fundFlowPay.getSpecialaccount1());
            // 科目
            voucherItem.setSubject(fundFlowPay.getCustomer1());
            // 科目说明
            if (!StringUtils.isNullBlank(fundFlowPay.getDepid1())) {
                String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(
                        fundFlowPay.getCustomer1(), bukrs);
                voucherItem.setSubjectdescribe(Subjectdescribe);

                String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(fundFlowPay.getCustomer1(), bukrs);
                subject = this.customerRefundItemJdbcDao.getSkont(voucherItem, subject);
                // 统驭项目
                voucherItem.setControlaccount(subject);
                // 统驭科目说明
                voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject, bukrs));
            }
            // 货币
            voucherItem.setCurrency(bankItem.getCurrency());
            // 货币金额
            voucherItem.setAmount(fundFlowPay.getAmount1());
            // 本位币金额
            voucherItem.setAmount2(fundFlowPay.getStandardamount1());
            // 部门
            voucherItem.setDepid(fundFlowPay.getDepid1());
            // 文本
            voucherItem.setText(packingLoan.getText());
            // 反记账标识
            if (fundFlowPay.getAntiaccount1() != null && fundFlowPay.getAntiaccount1().equals("Y")) {
                voucherItem.setUncheckflag("X");
            } else {
                voucherItem.setUncheckflag("");
            }
            voucherItem.setBusinessitemid(fundFlowPay.getFundflowid());
            voucherItemList.add(voucherItem);
        }

        amount = fundFlowPay.getAmount2();
        standardAmount = fundFlowPay.getStandardamount2();
        if (amount.abs().compareTo(new BigDecimal(0)) == 1 || standardAmount.abs().compareTo(new BigDecimal(0)) == 1) {
            VoucherItem voucherItem = new VoucherItem();

            // 客户记账码
            if ("S".equals(fundFlowPay.getDebtcredit2())) {
                if (StringUtils.isNullBlank(fundFlowPay.getSpecialaccount2())) {
                    voucherItem.setCheckcode("01");
                } else {
                    voucherItem.setCheckcode("09");
                }
                // 借/贷标识符
                voucherItem.setDebitcredit(fundFlowPay.getDebtcredit2());
            }

            // 客户记账码
            if ("H".equals(fundFlowPay.getDebtcredit2())) {
                if (StringUtils.isNullBlank(fundFlowPay.getSpecialaccount2())) {
                    voucherItem.setCheckcode("11");
                } else {
                    voucherItem.setCheckcode("19");
                }
                // 借/贷标识符
                voucherItem.setDebitcredit(fundFlowPay.getDebtcredit2());
            }
            // 客户记账码
            // voucherItem.setCheckcode("29");
            // 借/贷标识符
            // voucherItem.setDebitcredit(fundFlowPay.getDebtcredit2());
            // 特殊G/L标识
            voucherItem.setGlflag(fundFlowPay.getSpecialaccount2());
            // 科目
            voucherItem.setSubject(fundFlowPay.getCustomer2());
            // 科目说明
            if (!StringUtils.isNullBlank(fundFlowPay.getDepid2())) {
                String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(
                        fundFlowPay.getCustomer2(), bukrs);
                voucherItem.setSubjectdescribe(Subjectdescribe);
                String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(fundFlowPay.getCustomer2(), bukrs);
                subject = this.customerRefundItemJdbcDao.getSkont(voucherItem, subject);
                // 统驭项目
                voucherItem.setControlaccount(subject);
                // 统驭科目说明
                voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject, bukrs));
            }
            // 货币
            voucherItem.setCurrency(bankItem.getCurrency());
            // 货币金额
            voucherItem.setAmount(fundFlowPay.getAmount2());
            // 本位币金额
            voucherItem.setAmount2(fundFlowPay.getStandardamount2());
            // 部门
            voucherItem.setDepid(fundFlowPay.getDepid2());
            // 文本
            voucherItem.setText(packingLoan.getText());
            // 反记账标识
            if (fundFlowPay.getAntiaccount1() != null && fundFlowPay.getAntiaccount1().equals("Y")) {
                voucherItem.setUncheckflag("X");
            } else {
                voucherItem.setUncheckflag("");
            }
            voucherItem.setBusinessitemid(fundFlowPay.getFundflowid());
            voucherItemList.add(voucherItem);
        }

        amount = fundFlowPay.getAmount3();
        standardAmount = fundFlowPay.getStandardamount3();
        if (amount.abs().compareTo(new BigDecimal(0)) == 1 || standardAmount.abs().compareTo(new BigDecimal(0)) == 1) {
            VoucherItem voucherItem = new VoucherItem();

            // 客户记账码
            if ("S".equals(fundFlowPay.getDebtcredit3())) {
                if (StringUtils.isNullBlank(fundFlowPay.getSpecialaccount3())) {
                    voucherItem.setCheckcode("01");
                } else {
                    voucherItem.setCheckcode("09");
                }
                // 借/贷标识符
                voucherItem.setDebitcredit(fundFlowPay.getDebtcredit3());
            }

            // 客户记账码
            if ("H".equals(fundFlowPay.getDebtcredit3())) {
                if (StringUtils.isNullBlank(fundFlowPay.getSpecialaccount3())) {
                    voucherItem.setCheckcode("11");
                } else {
                    voucherItem.setCheckcode("19");
                }
                // 借/贷标识符
                voucherItem.setDebitcredit(fundFlowPay.getDebtcredit3());
            }
            // 客户记账码
            // voucherItem.setCheckcode("29");
            // 借/贷标识符
            // voucherItem.setDebitcredit(fundFlowPay.getDebtcredit3());
            // 特殊G/L标识
            voucherItem.setGlflag(fundFlowPay.getSpecialaccount3());
            // 科目
            voucherItem.setSubject(fundFlowPay.getCustomer3());
            // 科目说明
            if (!StringUtils.isNullBlank(fundFlowPay.getDepid3())) {
                String Subjectdescribe = this.customerRefundItemJdbcDao.getCustomerDescByCustomerId(
                        fundFlowPay.getCustomer3(), bukrs);
                voucherItem.setSubjectdescribe(Subjectdescribe);
                String subject = this.customerRefundItemJdbcDao.getSubjectByCustomer(fundFlowPay.getCustomer3(), bukrs);
                subject = this.customerRefundItemJdbcDao.getSkont(voucherItem, subject);
                // 统驭项目
                voucherItem.setControlaccount(subject);
                // 统驭科目说明
                voucherItem.setCaremark(this.customerRefundItemJdbcDao.getSubjectNameById(subject, bukrs));
            }
            // 货币
            voucherItem.setCurrency(bankItem.getCurrency());
            // 货币金额
            voucherItem.setAmount(fundFlowPay.getAmount3());
            // 本位币金额
            voucherItem.setAmount2(fundFlowPay.getStandardamount3());
            // 部门
            voucherItem.setDepid(fundFlowPay.getDepid3());
            // 文本
            voucherItem.setText(packingLoan.getText());
            // 反记账标识
            if (fundFlowPay.getAntiaccount1() != null && fundFlowPay.getAntiaccount1().equals("Y")) {
                voucherItem.setUncheckflag("X");
            } else {
                voucherItem.setUncheckflag("");
            }
            voucherItem.setBusinessitemid(fundFlowPay.getFundflowid());
            voucherItemList.add(voucherItem);
        }

        return voucherItemList;
    }

    /**
     * 得到短期借款清帐凭证
     * 
     * @param packingLoan
     * @return
     */
    public List<Voucher> getReturnLoanClearVendor(PackingLoan packingLoan) {

        // 判断是否生成清帐凭证 beg
        BigDecimal docryAmount = new BigDecimal(0);
        for (PackingBankItem bankItem : packingLoan.getPackingBankItem()) {
            docryAmount = docryAmount.add(bankItem.getApplyamount()); // 实际打包金额
        }

        BigDecimal reDocryAmount = new BigDecimal(0);
        for (PackingReBankItem reBankItem : packingLoan.getPackingReBankItem()) {
            reDocryAmount = reDocryAmount.add(reBankItem.getApplyamount()); // 实际还打包金额
        }

        if (reDocryAmount.compareTo(docryAmount) < 0) {
            // 部分还打包 不产生清帐凭证
            return new ArrayList();
        }
        // 判断是否生成清帐凭证 end

        List<Voucher> voucherList = new ArrayList<Voucher>();
        String bukrs = getCompanyIDByDeptID(packingLoan.getDept_id());
        String deptCode = this.sysDeptJdbcDao.getDeptCodeById(packingLoan.getDept_id());
        Set<PackingBankItem> bankItems = packingLoan.getPackingBankItem();
        PackingBankItem bankItem = bankItems.iterator().next();
        String strSubject = "";
        // 得到打包的借款项目
        List<VoucherItem> voucheritemList2 = this.voucherItemJdbcDao.getVoucherItemList2(packingLoan.getPackingid(),
                "10", "2");
        for (VoucherItem _voucherItem : voucheritemList2) {
            if (_voucherItem.getCheckcode().equals("50")
            // 取押汇银行对应的凭证行项
                    && bankItem.getBankitemid().equals(_voucherItem.getBusinessitemid())) {
                strSubject = _voucherItem.getSubject();
                break;
            }
        }
        // 得到打包的还打包凭证
        List<Voucher> payVoucherList = this.voucherJdbcDao.getVoucherList(packingLoan.getPackingid(), "10", "3");

        // 得到打包的借款凭证
        List<ClearVoucherItemStruct> clearVoucherItemStructList = this.vendorTitleJdbcDao.getLoanItemInfo(
                packingLoan.getPackingid(), "50", strSubject, "2");

        for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList) {

            Voucher voucher = new Voucher();
            // 凭证抬头
            voucher = getClearVoucher(packingLoan, "7");
            voucher.setAgkoa("S");
            voucher.setAgkon(clearVoucherItemStruct.getSubject());
            // 凭证行项目
            Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();

            // 得到票的本位币之和
            BigDecimal billValue = new BigDecimal("0");
            // 得到分配项的本位币之和
            BigDecimal itemValue = new BigDecimal("0");
            // 分配项之和和票之和的差
            BigDecimal subtractVlaue = new BigDecimal("0");

            // 清帐－打包凭证号
            VoucherItem voucherItem = getClearVoucherItem(clearVoucherItemStruct.getVoucherno(),
                    clearVoucherItemStruct.getRowno(), clearVoucherItemStruct.getYear(), " ");

            voucherItem.setVoucher(voucher);
            voucherItemList.add(voucherItem);

            itemValue = clearVoucherItemStruct.getBwbje();

            for (Voucher payVoucher : payVoucherList) {
                for (VoucherItem payItem : payVoucher.getVoucherItem()) {
                    if (payItem.getSubject().equals(clearVoucherItemStruct.getSubject())
                            && payItem.getCheckcode().equals("40")) {

                        VoucherItem voucherItem2 = getClearVoucherItem(payItem.getVoucherno(), payItem.getRownumber(),
                                payItem.getVoucher().getFiyear(), payItem.getVoucher().getVoucherid());

                        voucherItem2.setVoucher(voucher);
                        voucherItemList.add(voucherItem2);
                        billValue = billValue.add(payItem.getAmount2());
                    }
                }

            }

            subtractVlaue = itemValue.subtract(billValue);

            voucher.setVoucherItem(voucherItemList);

            voucherList.add(voucher);
        }

        for (int k = 0; k < voucherList.size(); k++) {
            Voucher returnClearvoucher = voucherList.get(k);
            this.voucherHibernateDao.save(returnClearvoucher);
        }

        return voucherList;
    }

    /**
     * 得到付款的凭证抬头
     * 
     * @param packingLoan
     * @param strClass
     *            凭证分类
     * @return
     */
    public Voucher getVoucher(PackingLoan packingLoan, String strClass) {
        Voucher voucher = new Voucher();

        UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

        // 业务编号
        voucher.setBusinessid(packingLoan.getPackingid());
        // 业务类型
        voucher.setBusinesstype("10");
        // 过账日期
        voucher.setCheckdate(packingLoan.getAccountdate().replace("-", ""));
        // 公司代码
        String bukrs = getCompanyIDByDeptID(packingLoan.getDept_id());
        voucher.setCompanycode(bukrs); // 公司代码
        // 货币
        voucher.setCurrency(packingLoan.getPackingBankItem().iterator().next().getCurrency());
        // 汇率
        voucher.setExchangerate(packingLoan.getPackingreate());
        if ("3".equals(strClass)) { // 还打包填写还打包汇率
            if (packingLoan.getPackingReBankItem().iterator().hasNext()) {
                PackingReBankItem reItem = packingLoan.getPackingReBankItem().iterator().next();
                voucher.setExchangerate(reItem.getRebillpurrate());
            }
        }

        // 会计期间
        voucher.setFiperiod(packingLoan.getAccountdate().substring(5, 7));
        if ("3".equals(strClass)) { // 还打包填写还打包记帐日期
            voucher.setFiperiod(packingLoan.getReaccountdate().substring(5, 7));
        }
        // 会计年度
        voucher.setFiyear(packingLoan.getAccountdate().substring(0, 4));
        if ("3".equals(strClass)) { // 还打包填写还打包记帐日期
            voucher.setFiyear(packingLoan.getReaccountdate().substring(0, 4));
        }
        // 输入日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        voucher.setImportdate(dateFormat.format(new Date()));
        // 输入者
        voucher.setImporter(userContext.getSysUser().getUserName());
        // 预制者
        voucher.setPreparer(userContext.getSysUser().getUserName());
        // 凭证日期
        voucher.setVoucherdate(packingLoan.getVoucherdate().replace("-", ""));
        if ("3".equals(strClass)) { // 还打包填写还打包凭证日期
            voucher.setVoucherdate(packingLoan.getRevoucherdate().replace("-", ""));
        }
        // 凭证抬头文本
        voucher.setVouchertext(packingLoan.getText());
        if ("3".equals(strClass)) { // 还打包填写还打包凭证日期
            voucher.setVouchertext(packingLoan.getRetext());
        }
        // 凭证类型
        voucher.setVouchertype("SA");
        // 凭证分类
        voucher.setVoucherclass(strClass);

        return voucher;
    }

    /**
     * 打包凭证
     * 
     * @param packingLoan
     * @return
     */
    public List<String> shortTimepaySaveVoucher(PackingLoan packingLoan) {
        List<String> retList = new ArrayList<String>();
        Set<PackingBankItem> bankItems = packingLoan.getPackingBankItem();
        String bukrs = getCompanyIDByDeptID(packingLoan.getDept_id()); // 公司ID
                                                                       // 2100
        String deptId = this.sysDeptJdbcDao.getDeptCodeById(packingLoan.getDept_id()); // 公司ID
                                                                                       // 2105
        // 凭证抬头
        Voucher voucher = new Voucher();
        // 凭证行项目
        Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
        voucher = this.getVoucher(packingLoan, "2");
        voucher.setCurrency(bankItems.iterator().next().getCurrency()); // 实际币别

        int rowNo = 0;
        // ******************打包银行行项目凭证行项目
        // beg**********************************//
        for (PackingBankItem bankItem : bankItems) {
            rowNo++;

            VoucherItem bankVoucherItem = new VoucherItem();
            bankVoucherItem.setVoucher(voucher);
            // 客户记账码
            bankVoucherItem.setCheckcode("50");
            // 特殊G/L标识
            bankVoucherItem.setGlflag("");
            // 科目
            bankVoucherItem.setSubject(bankItem.getBanksubj()); // 获得打包银行科目
            String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(bankItem.getBanksubj(), bukrs); // 银行存款-招商银行110002清算
            // 科目说明
            bankVoucherItem.setSubjectdescribe(subjectdescribe);
            // 货币
            bankVoucherItem.setCurrency(bankItem.getCurrency());
            // 货币金额
            bankVoucherItem.setAmount(bankItem.getApplyamount());
            // 本位币金额
            bankVoucherItem.setAmount2(bankItem.getApplyamount2());
            // 部门
            bankVoucherItem.setDepid(deptId);
            // 文本
            bankVoucherItem.setText(packingLoan.getText());
            // 现金流项目
            bankVoucherItem.setCashflowitem("");
            // 反记账标识
            bankVoucherItem.setUncheckflag("");
            // 统驭项目
            bankVoucherItem.setControlaccount(bankItem.getBanksubj());
            // 统驭科目说明
            bankVoucherItem.setCaremark(subjectdescribe);
            // 销售订单
            bankVoucherItem.setSalesorder("");
            // 销售订单行项目号
            bankVoucherItem.setOrderrowno("");
            // 利润中心
            bankVoucherItem.setProfitcenter("");
            // 成本中心
            bankVoucherItem.setCostcenter("");
            // 借/贷标识符
            bankVoucherItem.setDebitcredit("H");
            // 行号
            bankVoucherItem.setRownumber(String.valueOf(rowNo));
            bankVoucherItem.setBusinessitemid(bankItem.getBankitemid()); // 打包银行ID
            voucherItemList.add(bankVoucherItem);
        }
        // ******************打包银行行项目凭证行项目
        // end**********************************//
        // ******************付款银行行项目凭证行项目 beg*******************************//
        for (PackingBankItem bankItemit : bankItems) {
            rowNo++;

            VoucherItem bankVoucherItem = new VoucherItem();
            bankVoucherItem.setVoucher(voucher);
            // 客户记账码
            bankVoucherItem.setCheckcode("40");
            // 特殊G/L标识
            bankVoucherItem.setGlflag("");
            String subject = this.bankInfoJdbcDao.getBankInfo(bankItemit.getBankacc()); // 打包银行帐号
            String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(subject, bukrs);
            // 科目
            bankVoucherItem.setSubject(subject);
            // 科目说明
            bankVoucherItem.setSubjectdescribe(subjectdescribe);
            // 货币
            bankVoucherItem.setCurrency(bankItemit.getCurrency());
            // 货币金额
            bankVoucherItem.setAmount(bankItemit.getRealmoney());
            // 本位币金额
            bankVoucherItem.setAmount2(bankItemit.getRealmoney1());
            // 部门
            bankVoucherItem.setDepid(deptId);
            // 文本
            bankVoucherItem.setText(packingLoan.getText());
            // 现金流项目
            bankVoucherItem.setCashflowitem(bankItemit.getCashflowitem());
            // 反记账标识
            bankVoucherItem.setUncheckflag("");
            // 统驭项目
            bankVoucherItem.setControlaccount(subject);
            // 统驭科目说明
            bankVoucherItem.setCaremark(subjectdescribe);
            // 销售订单
            bankVoucherItem.setSalesorder("");
            // 销售订单行项目号
            bankVoucherItem.setOrderrowno("");
            // 利润中心
            bankVoucherItem.setProfitcenter("");
            // 成本中心
            bankVoucherItem.setCostcenter("");
            // 借/贷标识符
            bankVoucherItem.setDebitcredit("S");
            // 行号
            bankVoucherItem.setRownumber(String.valueOf(rowNo));
            bankVoucherItem.setBusinessitemid(bankItemit.getBankitemid()); // 打包银行ID

            voucherItemList.add(bankVoucherItem);
        }
        // ******************付款银行行项目凭证行项目 end*******************************//

        // 凭证行项目
        Set<VoucherItem> proLostItemList = new HashSet<VoucherItem>();

        // ******************结算科目行项目凭证行项目*******************************//
        // 结算科目凭证行项目
        Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();

        SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(packingLoan);

        Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();
        while (Settlesubjectit.hasNext()) {
            rowNo++;
            VoucherItem setvoucherItem = Settlesubjectit.next();
            setvoucherItem.setRownumber(String.valueOf(rowNo));
            setvoucherItem.setVoucher(voucher);

            proLostItemList.add(setvoucherItem);
        }
        // ******************结算科目行项目凭证行项目*******************************//

        // ******************特殊总帐行项目凭证行项目*******************************//
        // 结算科目凭证行项目
        Set<VoucherItem> FundFlowVoucherItemList = new HashSet<VoucherItem>();

        FundFlowVoucherItemList = this.getFundFlowVoucherItem(packingLoan);

        Iterator<VoucherItem> FundFlowit = FundFlowVoucherItemList.iterator();
        while (FundFlowit.hasNext()) {
            rowNo++;
            VoucherItem funvoucherItem = FundFlowit.next();
            funvoucherItem.setRownumber(String.valueOf(rowNo));
            funvoucherItem.setVoucher(voucher);

            proLostItemList.add(funvoucherItem);
        }
        // ******************特殊总帐行项目凭证行项目*******************************//

        if (proLostItemList.size() > 0) {
            voucherItemList.addAll(proLostItemList);
        }

        voucher.setVoucherItem(voucherItemList);
        this.voucherHibernateDao.save(voucher);
        retList.add(voucher.getVoucherid());
        return retList;
    }

    /**
     * 还打包生成
     * 
     * @param packingLoan
     * @return
     */
    public List<String> returnShortTimeSaveVoucher(PackingLoan packingLoan) {

        // 判断是否生成部分还 汇损 beg
        BigDecimal docryAmount = new BigDecimal(0);
        for (PackingBankItem bankItem : packingLoan.getPackingBankItem()) {
            docryAmount = docryAmount.add(bankItem.getApplyamount()); // 合计实际打包金额
                                                                      // 用于计算是否完全还款
        }

        BigDecimal reDocryAmount = new BigDecimal(0);
        for (PackingReBankItem reBankItem : packingLoan.getPackingReBankItem()) {
            reDocryAmount = reDocryAmount.add(reBankItem.getApplyamount()); // 合计实际还打包金额
        }
        boolean isGenPart = false;
        if (reDocryAmount.compareTo(docryAmount) < 0) {
            isGenPart = true;
        }
        // 判断是否生成部分还 汇损 beg

        // 得到票的本位币之和
        BigDecimal hAmount = new BigDecimal("0");
        // 得到分配项的本位币之和
        BigDecimal sAmount = new BigDecimal("0");

        List<String> retList = new ArrayList<String>();
        String deptCode = this.sysDeptJdbcDao.getDeptCodeById(packingLoan.getDept_id());
        String bukrs = getCompanyIDByDeptID(packingLoan.getDept_id());
        Set<PackingReBankItem> reBankItems = packingLoan.getPackingReBankItem();
        PackingBankItem bankItem = packingLoan.getPackingBankItem().iterator().next();
        BigDecimal reBillpurRate = reBankItems.iterator().next().getRebillpurrate();
        String reCurrency = reBankItems.iterator().next().getCurrency();
        // 取得所有凭证
        List<VoucherItem> existVoucherList = this.voucherItemJdbcDao.getVoucherItemList(packingLoan.getPackingid(),
                "10");
        // 凭证抬头
        Voucher voucher = new Voucher();
        // 凭证行项目
        Set<VoucherItem> voucherItemList = new HashSet<VoucherItem>();
        Set<VoucherItem> profOrLossVoucherItemList = new HashSet<VoucherItem>();
        BigDecimal subtractTotalVlaue = new BigDecimal(0);
        voucher = this.getVoucher(packingLoan, "3");
        voucher.setCurrency(reCurrency); // 打包币别
        voucher.setExchangerate(reBillpurRate); // 还打包汇率
        String strSubject = "";
        int rowNo = 1;

        // 待产生凭证行项列表
        List<String> genList = new ArrayList();
        // ******************打包银行行项目凭证行项目 beg
        // **********************************//
        for (PackingReBankItem reBankItem : packingLoan.getPackingReBankItem()) {
            boolean conFlag = false; // 是否已生成过凭证
            boolean zeroFlag = false; // 是否金额为零
            for (VoucherItem iter : existVoucherList) {
                if (reBankItem.getBankitemid().equals(iter.getBusinessitemid())) {
                    conFlag = true;
                }
            }

            if ((new BigDecimal(0)).compareTo(reBankItem.getApplyamount()) == 0) {
                zeroFlag = true;
            }
            if (conFlag) {
                continue;
            } else {
                genList.add(reBankItem.getBankitemid());
                if (zeroFlag) {
                    continue;
                }
            }

            VoucherItem voucherItem = new VoucherItem();
            voucherItem.setVoucher(voucher);
            // 客户记账码
            voucherItem.setCheckcode("40");
            // 特殊G/L标识
            voucherItem.setGlflag("");
            String subject = bankItem.getBanksubj(); // 打包银行科目
            String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(subject, bukrs);

            // 货币金额
            voucherItem.setAmount(reBankItem.getApplyamount());
            // 本位币金额
            voucherItem.setAmount2(reBankItem.getApplyamount2());
            // 科目
            voucherItem.setSubject(subject);
            // 科目说明
            voucherItem.setSubjectdescribe(subjectdescribe);
            // 统驭项目
            voucherItem.setControlaccount(bankItem.getBanksubj());
            // 统驭科目说明
            voucherItem.setCaremark(subjectdescribe);

            // 货币
            voucherItem.setCurrency(reBankItem.getCurrency());
            // 部门
            voucherItem.setDepid(deptCode);
            // 文本
            voucherItem.setText(packingLoan.getRetext());
            // 现金流项目
            voucherItem.setCashflowitem("");
            // 反记账标识
            voucherItem.setUncheckflag("");
            // 销售订单
            voucherItem.setSalesorder("");
            // 销售订单行项目号
            voucherItem.setOrderrowno("");
            // 利润中心
            voucherItem.setProfitcenter("");
            // 成本中心
            voucherItem.setCostcenter("");
            // 借/贷标识符
            voucherItem.setDebitcredit("S");
            // 行号
            voucherItem.setRownumber(String.valueOf(rowNo++));
            voucherItem.setBusinessitemid(reBankItem.getBankitemid());
            voucherItemList.add(voucherItem);
        }
        // ******************打包银行行项目凭证行项目
        // end**********************************//

        // ******************还款银行行项目凭证行项目 beg*******************************//
        for (PackingReBankItem reBankItem : packingLoan.getPackingReBankItem()) {
            if (genList.indexOf(reBankItem.getBankitemid()) < 0) {
                // 不在生产列表中，跳过
                continue;
            }

            VoucherItem reBankVoucherItem = new VoucherItem();
            reBankVoucherItem.setVoucher(voucher);
            // 客户记账码
            reBankVoucherItem.setCheckcode("50");
            // 特殊G/L标识
            reBankVoucherItem.setGlflag("");

            String subject = this.bankInfoJdbcDao.getBankInfo(reBankItem.getBankacc()); // 打包银行帐号
            String subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(subject, bukrs);

            // 科目
            reBankVoucherItem.setSubject(subject);
            // 科目说明
            reBankVoucherItem.setSubjectdescribe(subjectdescribe);
            // 货币
            reBankVoucherItem.setCurrency(reBankItem.getCurrency());
            // 货币金额
            reBankVoucherItem.setAmount(reBankItem.getRealmoney());
            // 本位币金额
            reBankVoucherItem.setAmount2(reBankItem.getRealmoney2());
            // 部门
            reBankVoucherItem.setDepid(deptCode);
            // 文本
            reBankVoucherItem.setText(packingLoan.getRetext());
            // 现金流项目
            reBankVoucherItem.setCashflowitem(reBankItem.getCashflowitem());
            // 反记账标识
            reBankVoucherItem.setUncheckflag("");
            // 统驭项目
            reBankVoucherItem.setControlaccount(bankItem.getBanksubj());
            // 统驭科目说明
            reBankVoucherItem.setCaremark(subjectdescribe);
            // 销售订单
            reBankVoucherItem.setSalesorder("");
            // 销售订单行项目号
            reBankVoucherItem.setOrderrowno("");
            // 利润中心
            reBankVoucherItem.setProfitcenter("");
            // 成本中心
            reBankVoucherItem.setCostcenter("");
            // 借/贷标识符
            reBankVoucherItem.setDebitcredit("H");
            // 行号
            reBankVoucherItem.setRownumber(String.valueOf(rowNo++));
            reBankVoucherItem.setBusinessitemid(reBankItem.getBankitemid());
            voucherItemList.add(reBankVoucherItem);
            hAmount = hAmount.add(reBankItem.getRealmoney2());
            sAmount = sAmount.add(reBankItem.getApplyamount2());

            // ******************银行存款2 beg*******************************//
            for (PackingReBankTwo itemTwo : packingLoan.getPackingReBankTwo()) {
                // 取得同一行记录
                if (itemTwo.getBankitemid().equals(reBankItem.getBankitemid())
                        && StringUtils.isNotBlank(itemTwo.getDebtcredit())) {
                    VoucherItem rebank2 = new VoucherItem();
                    rebank2.setVoucher(voucher);
                    // 客户记账码
                    if ("H".equals(itemTwo.getDebtcredit())) {
                        rebank2.setCheckcode("50");
                        hAmount = hAmount.add(itemTwo.getApplyamount4());
                    } else if ("S".equals(itemTwo.getDebtcredit())) {
                        rebank2.setCheckcode("40");
                        sAmount = sAmount.add(itemTwo.getApplyamount4());
                    }

                    // 借/贷标识符
                    rebank2.setDebitcredit(itemTwo.getDebtcredit());
                    // 特殊G/L标识
                    rebank2.setGlflag("");

                    subject = this.bankInfoJdbcDao.getBankInfo(itemTwo.getBankacc3()); // 押汇银行帐号
                    subjectdescribe = this.customerRefundItemJdbcDao.getSubjectNameById(subject, bukrs);
                    // 科目
                    rebank2.setSubject(subject);
                    // 科目说明
                    rebank2.setSubjectdescribe(subjectdescribe);
                    // 货币
                    rebank2.setCurrency(itemTwo.getCurrency3());
                    // 货币金额
                    rebank2.setAmount(itemTwo.getApplyamount3());
                    // 本位币金额
                    rebank2.setAmount2(itemTwo.getApplyamount4());
                    // 部门
                    rebank2.setDepid(deptCode);
                    // 文本
                    rebank2.setText(packingLoan.getRetext());
                    // 现金流项目
                    rebank2.setCashflowitem(itemTwo.getCashflowitem3());
                    // 反记账标识
                    rebank2.setUncheckflag("");
                    // 统驭项目
                    rebank2.setControlaccount(bankItem.getBanksubj());
                    // 统驭科目说明
                    rebank2.setCaremark(subjectdescribe);
                    // 销售订单
                    rebank2.setSalesorder("");
                    // 销售订单行项目号
                    rebank2.setOrderrowno("");
                    // 利润中心
                    rebank2.setProfitcenter("");
                    // 成本中心
                    rebank2.setCostcenter("");
                    // 行号
                    rebank2.setRownumber(String.valueOf(rowNo++));
                    rebank2.setBusinessitemid(reBankItem.getBankitemid());
                    voucherItemList.add(rebank2);
                }
            }
            // ******************银行存款2 end*******************************//

        }

        // 不需要生成其它行项
        if (genList.size() < 1) {
            return retList;
        }
        // ******************结算科目行项目凭证行项目 beg*******************************//
        Set<VoucherItem> SettlesubjectVoucherItemList = new HashSet<VoucherItem>();

        SettlesubjectVoucherItemList = this.getSettSubjVoucherItem(packingLoan);

        Iterator<VoucherItem> Settlesubjectit = SettlesubjectVoucherItemList.iterator();
        while (Settlesubjectit.hasNext()) {
            VoucherItem setvoucherItem = Settlesubjectit.next();
            setvoucherItem.setText(packingLoan.getRetext());
            setvoucherItem.setRownumber(String.valueOf(rowNo++));
            setvoucherItem.setVoucher(voucher);
            if ("H".equals(setvoucherItem.getDebitcredit())) {
                hAmount = hAmount.add(setvoucherItem.getAmount2());
            } else if ("S".equals(setvoucherItem.getDebitcredit())) {
                sAmount = sAmount.add(setvoucherItem.getAmount2());
            }
            voucherItemList.add(setvoucherItem);
        }
        // ******************结算科目行项目凭证行项目 end*******************************//

        // ******************特殊总帐行项目凭证行项目 beg *******************************//
        // 结算科目凭证行项目
        Set<VoucherItem> fundFlowVoucherItemList = new HashSet<VoucherItem>();
        fundFlowVoucherItemList = this.getFundFlowVoucherItem(packingLoan);
        Iterator<VoucherItem> FundFlowit = fundFlowVoucherItemList.iterator();
        while (FundFlowit.hasNext()) {
            VoucherItem funvoucherItem = FundFlowit.next();
            funvoucherItem.setRownumber(String.valueOf(rowNo++));
            funvoucherItem.setVoucher(voucher);
            funvoucherItem.setText(packingLoan.getRetext());
            if ("H".equals(funvoucherItem.getDebitcredit())) {
                hAmount = hAmount.add(funvoucherItem.getAmount2());
            } else if ("S".equals(funvoucherItem.getDebitcredit())) {
                sAmount = sAmount.add(funvoucherItem.getAmount2());
            }
            voucherItemList.add(funvoucherItem);
        }
        // ******************特殊总帐行项目凭证行项目 end*******************************//

        // ******************部分还汇损 beg*******************************//
        // BigDecimal billpurrate =
        // reBankItem.getpackingLoan().getBillpurrate(); // 押汇汇率
        // BigDecimal applyamount = reBankItem.getApplyamount(); // 还押汇金额
        // 汇差计算方法：还打包金额(本位币) - ( 还打包金额 * 抬头.打包汇率 )
        BigDecimal subtractVlaue = hAmount.subtract(sAmount); // .getApplyamount2().subtract((billpurrate).multiply(applyamount)).setScale(2,
                                                              // BigDecimal.ROUND_HALF_UP);
        if ((new BigDecimal(0)).compareTo(subtractVlaue) != 0) { // isGenPart：是否完全还款，!=0：是否有汇差
            subtractTotalVlaue = subtractTotalVlaue.add(subtractVlaue);

            List<ClearVoucherItemStruct> clearVoucherItemStructList = this.vendorTitleJdbcDao.getLoanItemInfo(
                    packingLoan.getPackingid(), "50", strSubject, "2");
            String Subjectdescribe = "";
            String controlaccount = "";
            String caremark = "";
            for (ClearVoucherItemStruct clearVoucherItemStruct : clearVoucherItemStructList) {
                Subjectdescribe = clearVoucherItemStruct.getSubjectdescribe();
                controlaccount = clearVoucherItemStruct.getSubject();
                caremark = clearVoucherItemStruct.getSubjectdescribe();
            }

            int rownumber = 1;
            voucher.setCurrency(reCurrency); // 押汇币别
            voucher.setExchangerate(reBillpurRate); // 还打包汇率

            VoucherItem profOrLossVoucherItem = new VoucherItem();
            // profOrLossVoucherItem.setRownumber(String.valueOf(rowNo++));
            if ("2199".equals(deptCode)) {
                if (subtractVlaue.signum() == 1) {
                    // 记帐码
                    profOrLossVoucherItem.setCheckcode("40");
                    // 借/贷标识符
                    profOrLossVoucherItem.setDebitcredit("S");
                    String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050101", bukrs);
                    // 科目
                    profOrLossVoucherItem.setSubject("6603050101");
                    // 科目说明
                    profOrLossVoucherItem.setSubjectdescribe(subjectname);
                    // 统驭项目
                    profOrLossVoucherItem.setControlaccount("6603050101");
                    // 统驭科目说明
                    profOrLossVoucherItem.setCaremark(subjectname);
                } else {
                    // 记帐码
                    profOrLossVoucherItem.setCheckcode("50");
                    // 借/贷标识符
                    profOrLossVoucherItem.setDebitcredit("H");
                    String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050201", bukrs);
                    // 科目
                    profOrLossVoucherItem.setSubject("6603050201");
                    // 科目说明
                    profOrLossVoucherItem.setSubjectdescribe(subjectname);
                    // 统驭项目
                    profOrLossVoucherItem.setControlaccount("6603050201");
                    // 统驭科目说明
                    profOrLossVoucherItem.setCaremark(subjectname);
                }
            } else {
                if (subtractVlaue.signum() == 1) {
                    // 记帐码
                    profOrLossVoucherItem.setCheckcode("40");
                    // 借/贷标识符
                    profOrLossVoucherItem.setDebitcredit("S");
                    String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050101", bukrs);
                    // 科目
                    profOrLossVoucherItem.setSubject("6603050101");
                    // 科目说明
                    profOrLossVoucherItem.setSubjectdescribe(subjectname);
                    // 统驭项目
                    profOrLossVoucherItem.setControlaccount("6603050101");
                    // 统驭科目说明
                    profOrLossVoucherItem.setCaremark(subjectname);
                } else {
                    // 记帐码
                    profOrLossVoucherItem.setCheckcode("50");
                    // 借/贷标识符
                    profOrLossVoucherItem.setDebitcredit("H");
                    String subjectname = this.customerRefundItemJdbcDao.getSubjectNameById("6603050201", bukrs);
                    // 科目
                    profOrLossVoucherItem.setSubject("6603050201");
                    // 科目说明
                    profOrLossVoucherItem.setSubjectdescribe(subjectname);
                    // 统驭项目
                    profOrLossVoucherItem.setControlaccount("6603050201");
                    // 统驭科目说明
                    profOrLossVoucherItem.setCaremark(subjectname);
                }
            }

            // 货币
            profOrLossVoucherItem.setCurrency(reCurrency);
            // 货币金额
            profOrLossVoucherItem.setAmount(new BigDecimal("0"));
            // 本位币金额
            profOrLossVoucherItem.setAmount2(subtractVlaue.abs());
            // 部门
            profOrLossVoucherItem.setDepid(deptCode);
            // 文本
            profOrLossVoucherItem.setText(packingLoan.getRetext());
            profOrLossVoucherItem.setRownumber(String.valueOf(rowNo++));
            profOrLossVoucherItemList.add(profOrLossVoucherItem);
            profOrLossVoucherItem.setVoucher(voucher);

            // 清帐

        }
        // ******************部分还汇损 end*******************************//

        if (voucherItemList.size() > 0) {
            voucher.setVoucherItem(voucherItemList);
            if (profOrLossVoucherItemList.size() > 0) {
                voucherItemList.addAll(profOrLossVoucherItemList);
            }
            this.voucherHibernateDao.save(voucher);
            retList.add(voucher.getVoucherid());
        }
        return retList;
    }

    public void updateReBankTwo(String billpurid) {
        this.packingLoanJdbcDao.updateReBankTwo(billpurid);
    }


    /**
     * 删除 出口押汇 关联对象
     * 
     * @param billPurchased
     */
    public void deleteSubObject(String packingid) {
        this.packingLoanJdbcDao.deleteSubObject(packingid);
    }

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.packingloanGen.service.PackingLoanServiceGen#_get(java.lang.String)
	 */
	@Override
	public PackingLoan _get(String id) {
		// TODO Auto-generated method stub
		return packingLoanJdbcDao.getPackingLoanByid(id);
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.packingloanGen.service.PackingLoanServiceGen#_getDetached(java.lang.String)
	 */
	@Override
	public PackingLoan _getDetached(String id) {
		// TODO Auto-generated method stub
		return packingLoanJdbcDao.getPackingLoanByid(id);
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.packingloanGen.service.PackingLoanServiceGen#_getEntityCopy(java.lang.String)
	 */
	@Override
	public PackingLoan _getEntityCopy(String id) {
		 PackingLoan packingLoan = new PackingLoan();
	        PackingLoan packingLoanOld = packingLoanJdbcDao.getPackingLoanByid(id);
	        try {
	            BeanUtils.copyProperties(packingLoan, packingLoanOld);
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	        }
	        packingLoan.setPacking_no(null);
	        // packingLoan.setPackingid(null);
	        packingLoan.setProcessstate(" ");
	        return packingLoan;
	}

	/* (non-Javadoc)
	 * @see com.infolion.xdss3.packingloanGen.service.PackingLoanServiceGen#_getForEdit(java.lang.String)
	 */
	@Override
	public PackingLoan _getForEdit(String id) {
		// TODO Auto-generated method stub
		return packingLoanJdbcDao.getPackingLoanByid(id);
	}
    
    
}