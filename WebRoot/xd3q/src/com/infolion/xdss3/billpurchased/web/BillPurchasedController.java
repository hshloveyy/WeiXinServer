/*
 * @(#)BillPurchasedController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年12月15日 10点37分16秒
 *  描　述：创建
 */
package com.infolion.xdss3.billpurchased.web;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.AuthSql;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.sapss.payment.service.PaymentInnerInfoJDBCService;
import com.infolion.xdss3.billPurBankItem.domain.BillPurBankItem;
import com.infolion.xdss3.billPurBillItem.domain.BillPurBillItem;
import com.infolion.xdss3.billPurReBankItem.domain.BillPurReBankItem;
import com.infolion.xdss3.billPurReBankItem.domain.BillPurReBankTwo;
import com.infolion.xdss3.billpurchased.dao.BillPurchasedJdbcDao;
import com.infolion.xdss3.billpurchased.domain.BillPurchased;
import com.infolion.xdss3.billpurchased.service.BillPurchasedService;
import com.infolion.xdss3.billpurchasedGen.web.BillPurchasedControllerGen;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;
import com.infolion.xdss3.voucheritem.domain.VoucherItem;

/**
 * <pre>
 * 出口押汇(BillPurchased)控制器类
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
@BDPController(parent = "baseMultiActionController")
public class BillPurchasedController extends BillPurchasedControllerGen
{
    
    
    
    @Autowired
    private PaymentInnerInfoJDBCService paymentInnerInfoJDBCService;

    public PaymentInnerInfoJDBCService getPaymentInnerInfoJDBCService() {
        return paymentInnerInfoJDBCService;
    }

    public void setPaymentInnerInfoJDBCService(
            PaymentInnerInfoJDBCService paymentInnerInfoJDBCService) {
        this.paymentInnerInfoJDBCService = paymentInnerInfoJDBCService;
    }
    
    
    @Autowired
    private GridService gridService;
    
    public void setGridService(GridService gridService) {
        this.gridService = gridService;
    }
    
    @Autowired
    protected BillPurchasedService billPurchasedService;
    
    /**
     * @param billPurchasedService the billPurchasedService to set
     */
    public void setBillPurchasedService(BillPurchasedService billPurchasedService) {
        this.billPurchasedService = billPurchasedService;
    }

    @Autowired
    private VoucherService voucherService;
    
    public void setVoucherService(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Autowired
    private BillPurchasedJdbcDao billPurchasedJdbcDao;
    
    public void setBillPurchasedJdbcDao(BillPurchasedJdbcDao billPurchasedJdbcDao) {
        this.billPurchasedJdbcDao = billPurchasedJdbcDao;
    }

    /**
     * 
     * @创建日期：根据出单审单ID来判断该项目是否已经授信，若已经授信则反回授信类型
     * @返回结果： 0 - 无授信 1 - 客户放货 2 - 客户代垫 3 - 客户放货+代垫 4 - 供应商授信
     */
    public void checkProjCreditType(HttpServletRequest request,
            HttpServletResponse response) {
        JSONObject jsonObj = new JSONObject();
        String bill_no = request.getParameter("bill_no");
        
        String creditTypes = this.billPurchasedService.checkProjCreditType(bill_no);
        jsonObj.put("credittypes", creditTypes);
        this.operateSuccessfullyWithString(response, jsonObj.toString());
    }
    
    /**
     * 现金日记帐  
     *   
     * @param request
     * @param response
     */
    public void _cashJournal (HttpServletRequest request, HttpServletResponse response)
    {
        JSONObject jo = new JSONObject();
        // 绑定主对象值
        BillPurchased billPurchased = (BillPurchased) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), BillPurchased.class, true, request.getMethod(), true);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurBillItem> billPurBillItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBillItem.class,
                null);
        billPurchased.setBillPurBillItem(billPurBillItemmodifyItems);
        Set<BillPurBillItem> billPurBillItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBillItem.class,
                null);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurBankItem> billPurBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBankItem.class,
                null);
        billPurchased.setBillPurBankItem(billPurBankItemmodifyItems);
        Set<BillPurBankItem> billPurBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBankItem.class,
                null);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurReBankItem> billPurReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurReBankItem.class,
                null);
        billPurchased.setBillPurReBankItem(billPurReBankItemmodifyItems);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurReBankTwo> billPurReBankTwomodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurReBankTwo.class,
                null);
        billPurchased.setBillPurReBankTwo(billPurReBankTwomodifyItems);
        Set<BillPurReBankItem> billPurReBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurReBankItem.class,
                null);
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null) {
            billPurchased.setSettleSubject(settleSubject);
            settleSubject.setBillPurchased(billPurchased);
        }
        // 绑定子对象(一对一关系)
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
        if (fundFlow != null) {
            billPurchased.setFundFlow(fundFlow);
            fundFlow.setBillPurchased(billPurchased);
        }
        this.billPurchasedService._saveOrUpdate(billPurchased, billPurBillItemdeleteItems,
                billPurBankItemdeleteItems, billPurReBankItemdeleteItems, getBusinessObject());
        
        this.billPurchasedService.updateReBankTwo(billPurchased.getBillpurid()); // 更新还押汇银行2
        
        billPurchased = this.billPurchasedService._get(billPurchased.getBillpurid());
        /*
         * 将现金日记账所需要的参数拼装成URL
         */
        com.infolion.platform.console.sys.context.UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
        String username = userContext.getSysUser().getUserName();
        String xjrj = this.getProperties("config/config.properties").getProperty("xjrj");
        String journalType = "2"; // journalType,参数说明：流转到资金部的，则参数传2；流转到综合二部的，传1。
        String bus_id   = "";       // 存放所有银行行项ID
        String bus_type = "";       // 业务类型
        
        // 7:借,押汇；8:货,还押汇
        String ps = billPurchased.getProcessstate();
        if ("资金部出纳确认还押汇".equals(ps)) {
            for(BillPurReBankItem rebank : billPurchased.getBillPurReBankItem()){
                if ( ! "已做账".equals(rebank.getBusinesstype()) ) {
                    bus_id   += rebank.getBankitemid() + ",";
                    bus_type += "8,";
                }
            }
        } else if ("资金部出纳确认押汇收款".equals(ps)) {
            for (BillPurBankItem bank : billPurchased.getBillPurBankItem()) {
                bus_id += bank.getBankitemid() + ",";
                bus_type += "7,";
            }
        }
        
        bus_id   = org.apache.commons.lang.StringUtils.substringBeforeLast(bus_id, ",");
        bus_type = org.apache.commons.lang.StringUtils.substringBeforeLast(bus_type, ",");
        
        String cashJournalURl = xjrj + "/xjrj/journal.do?method=preAdd" +
                "&journalType=" + journalType +
                "&bus_id=" + bus_id +
                "&bus_type=" + bus_type +
                "&userName=" + username +
                "&isFromXdss=1";
        // 将链接放入JSON中
        jo.put("cashJournalURl", cashJournalURl);
        
        jo.put("billpur_no", billPurchased.getBillpur_no());
        jo.put("billpurid", billPurchased.getBillpurid());
        String creator = billPurchased.getCreator();
        String creator_text = SysCachePoolUtils.getDictDataValue("YUSER",creator);
        jo.put("creator_text", creator_text);
        jo.put("creator", creator);
        jo.put("settlesubjectid", billPurchased.getSettleSubject() != null ? billPurchased.getSettleSubject().getSettlesubjectid() : "");
        jo.put("fundflowid", billPurchased.getFundFlow() != null ? billPurchased.getFundFlow().getFundflowid() : "");
        jo.put("createtime", billPurchased.getCreatetime());
        jo.put("lastmodifytime", billPurchased.getLastmodifytime());
        this.operateSuccessfullyHiddenInfoWithString(response, jo.toString());
       
    }

    /**
     * 
     * @param absolutePath
     */
    public Properties getProperties(String absolutePath) {
        Properties p = new Properties();
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(absolutePath);
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }
    
    /**
     * 模拟凭证  
     *   
     * @param request
     * @param response
     */
    public void _voucherPreview (HttpServletRequest request, HttpServletResponse response)
    {
        JSONObject jo = new JSONObject();
        // 绑定主对象值
        BillPurchased billPurchased = (BillPurchased) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), BillPurchased.class, true, request.getMethod(), true);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurBillItem> billPurBillItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBillItem.class,
                null);
        billPurchased.setBillPurBillItem(billPurBillItemmodifyItems);
        Set<BillPurBillItem> billPurBillItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBillItem.class,
                null);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurBankItem> billPurBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBankItem.class,
                null);
        billPurchased.setBillPurBankItem(billPurBankItemmodifyItems);
        Set<BillPurBankItem> billPurBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurBankItem.class,
                null);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurReBankItem> billPurReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurReBankItem.class,
                null);
        billPurchased.setBillPurReBankItem(billPurReBankItemmodifyItems);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<BillPurReBankTwo> billPurReBankTwomodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurReBankTwo.class,
                null);
        billPurchased.setBillPurReBankTwo(billPurReBankTwomodifyItems);
        Set<BillPurReBankItem> billPurReBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { billPurchased }, BillPurReBankItem.class,
                null);
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null) {
            billPurchased.setSettleSubject(settleSubject);
            settleSubject.setBillPurchased(billPurchased);
        }
        // 绑定子对象(一对一关系)
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), FundFlow.class, false, request.getMethod(), true);
        if (fundFlow != null) {
            billPurchased.setFundFlow(fundFlow);
            fundFlow.setBillPurchased(billPurchased);
        }
        this.billPurchasedService._saveOrUpdate(billPurchased, billPurBillItemdeleteItems,
                billPurBankItemdeleteItems, billPurReBankItemdeleteItems, getBusinessObject());
        this.billPurchasedService.updateReBankTwo(billPurchased.getBillpurid()); // 更新还押汇银行2
        
        // 重新取得数据库数据
        billPurchased = this.billPurchasedService._get(billPurchased.getBillpurid());
        
        String ps = billPurchased.getProcessstate();
        List<String> retList = new ArrayList();

        // 保存前先删除
        this.voucherService.deleteVoucherByBusinessid(billPurchased.getBillpurid());
        
        // 财务会计确认押汇过账
        if ("财务会计确认押汇过账".equals(ps))
        {
            retList = this.billPurchasedService.shortTimepaySaveVoucher(billPurchased);
            
            //判断是否需要删除
            this.voucherService.judgeVoucherNeedDel(billPurchased.getBillpurid());
            Voucher voucher = this.voucherService.getVoucher(billPurchased.getBillpurid());
            this.billPurchasedService.dealClearAccountVoucher(billPurchased,voucher,"Y");
        }

        // 还短期外汇借款
        if ("财务会计确认还押汇并做帐".equals(ps))
        {
            retList = this.billPurchasedService.returnShortTimeSaveVoucher(billPurchased);
            this.voucherService.judgeVoucherNeedDel_2(billPurchased.getBillpurid());
            //************************处理短期外汇借款清帐凭证***************************
            List<Voucher> voucherList = this.billPurchasedService.getReturnLoanClearVendor(billPurchased);
            for (int k=0;k<voucherList.size();k++){
                Voucher returnClearvoucher = voucherList.get(k);
                retList.add(returnClearvoucher.getVoucherid());
            }
            //************************处理短期外汇借款清帐凭证***************************
        }
        
      //判断是否需要删除
        this.voucherService.judgeVoucherNeedDel_2(billPurchased.getBillpurid());
        
        try{
            String vouchids = "";
            for(String _vouchid : retList){
                vouchids = vouchids + _vouchid + "&";
            }
            vouchids = StringUtils.substringBeforeLast(vouchids, "&");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(vouchids);
            System.out.println(vouchids);
        }catch(IOException e){
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
       
    }
    
    
    public void _hasCashJournal(HttpServletRequest request, HttpServletResponse response)
    {
        String businessid = request.getParameter("BUSINESSID");
        List list = billPurchasedJdbcDao.getCashJournalList(businessid);
        
//        System.out.println(businessid); //YCASHDIARY BUSINESSID
        response.setContentType("text/html;charset=UTF-8");
        try {
            if (list.size()>0) {
                response.getWriter().print("true");
            } else {
                response.getWriter().print("false");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 中转综合查询页面
     */
    public ModelAndView _conditionQuery(HttpServletRequest request, HttpServletResponse response)
    {
        return new ModelAndView("xdss3/billpurchased/billPurchasedQuery");
    }
    
    /**
     * 多条件查询
     * @param request
     * @param response
     * @param gridQueryCondition
     * @throws Exception
     */
    public void queryGrid(HttpServletRequest request,
            HttpServletResponse response, GridQueryCondition gridQueryCondition)
            throws Exception{
        BusinessObject businessObject = BusinessObjectService.getBusinessObject("BillPurchased");
        String strAuthSql = "";
        try {
            AuthSql authSql = (AuthSql) Class.forName("com.infolion.xdss3.CommonDataAuthSql").newInstance();
            strAuthSql = authSql.getAuthSql(businessObject);
            // 替换权限默认的前缀表名
            strAuthSql = strAuthSql.replace("YBILLPURCHASED", "t");
        } catch (Exception ex) {
            throw new SQLException("类不存在：" + ex.getMessage());
        }
        String signRegExp="[\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\`\\-\\=\\～\\！\\＠\\＃\\＄\\" +
        "％\\＾\\＆\\＊\\\\（\\）\\＿\\＋\\＝\\－\\｀\\[\\]\\\\'\\;\\/\\.\\,\\<\\>\\?\\:" +
        "\"\\{\\}\\|\\，\\．\\／\\；\\＇\\［\\］\\＼\\＜\\＞\\？\\：\\＂\\｛\\｝\\｜\\“\\”\\" +
        "‘\\’\\。\r+\n+\t+\\s\\ ]";

        
        // 获取SQL参数
        String deptId = request.getParameter("dept_id");                // 部门ID
        String billpur_no = request.getParameter("billpur_no");     // 出口押汇单号
        String billno = request.getParameter("billno");        // 出单发票号、
        
        String lc_no = request.getParameter("lcno");          // L/C No
        String amount1 = request.getParameter("amount1");               // 申请押汇金额1
        String amount2 = request.getParameter("amount2");               // 申请押汇金额2
        String titleText = request.getParameter("text");          //抬头文本（押汇用途）

        String creator = request.getParameter("creator");             // 创建人
        String voucherNo = request.getParameter("voucherno");           // 凭证号
        

        String maturity1 = request.getParameter("maturity1");  // 审批通过时间1
        String maturity2 = request.getParameter("maturity2");  // 审批通过时间2
        String applyDate1 = request.getParameter("apply_date1");        //申请时间1
        String applyDate2 = request.getParameter("apply_date2");        // 申请时间2
        String billtype = request.getParameter("billtype");        // 申请时间2
        
        StringBuffer sql = null;
        sql = new StringBuffer("SELECT AB.* FROM (SELECT A.*, B.BILLTYPE, B.BILL_NO, B.BILLITEMID, O.ORGANIZATIONNAME AS DEPT_NAME  FROM ybillpurchased A" +
        		"  LEFT JOIN yorganization O" +
        		"    ON (A.DEPT_ID = O.ORGANIZATIONID)" +
        		"  LEFT JOIN ybillpurbillitem B ON  (B.BILLPURID=A.BILLPURID)" +
        		"  LEFT JOIN yuser U ON  (U.USERID=A.CREATOR)" +
        		"  WHERE 1 = 1 ");

        if (StringUtils.isNotBlank(titleText)) {
            titleText = URLDecoder.decode(titleText, "UTF-8");
        }
        
        String retext = request.getParameter("retext");          // 抬头文本（还押汇用途）
        if (StringUtils.isNotBlank(retext)) {
            retext = URLDecoder.decode(retext, "UTF-8");
        }
        
        // 设置客户编号条件
        if(StringUtils.isNotBlank(creator)){
            sql.append("AND u.username = '" + creator + "'");
        }
        // 设置凭证号条件
        if(StringUtils.isNotBlank(voucherNo)){
            sql.append("AND EXISTS(SELECT 1 FROM YVOUCHER d WHERE d.BUSINESSID=a.billpurid AND d.VOUCHERNO LIKE '%" + voucherNo + "%')");
        }
        // 设置部门ID条件
        if(StringUtils.isNotBlank(deptId)){
            sql.append("AND a.dept_id = '" + deptId + "'");
        }
        
        if(StringUtils.isNotBlank(billpur_no)){
            billpur_no = URLDecoder.decode(billpur_no, "UTF-8");
            sql.append("AND a.billpur_no LIKE '%" + billpur_no + "%'");
        }
        if(StringUtils.isNotBlank(billno)){
            billno = URLDecoder.decode(billno, "UTF-8");
            sql.append("AND b.bill_no LIKE '%" + billno + "%'");
        }
        if(StringUtils.isNotBlank(billtype)){
            sql.append("AND b.billtype LIKE '%" + billtype + "%'");
        }
        if(StringUtils.isNotBlank(lc_no)){
            lc_no = URLDecoder.decode(lc_no, "UTF-8");
            sql.append("AND b.lcdpdano LIKE '%" + lc_no + "%'");
        }
        // 设置抬头文本条件
        if(StringUtils.isNotBlank(titleText)){
            sql.append("AND a.TEXT LIKE '%" + titleText + "%'");
        }
        // 设置抬头文本条件
        if(StringUtils.isNotBlank(retext)){
            sql.append("AND a.retext LIKE '%" + retext + "%'");
        }
        
        
        // 设置金额条件
        if(StringUtils.isNotBlank(amount1) && StringUtils.isNotBlank(amount2)){
            sql.append("AND a.applyamount BETWEEN '" + amount1 + "' AND '" + amount2 + "'");
        }else if(StringUtils.isNotBlank(amount1)){
            sql.append("AND a.applyamount > '" + amount1 + "'");
        }else if(StringUtils.isNotBlank(amount2)){
            sql.append("AND a.applyamount < '" + amount2 + "'");
        }
        
        // 设置申请时间条件
        if(StringUtils.isNotBlank(applyDate1) && StringUtils.isNotBlank(applyDate2)){
            sql.append("AND a.CREATETIME BETWEEN '" + applyDate1 + "' AND '" + applyDate2 + "'");
        }else if(StringUtils.isNotBlank(applyDate1)){
            sql.append("AND a.CREATETIME > '" + applyDate1 + "'");
        }else if(StringUtils.isNotBlank(applyDate2)){
            sql.append("AND a.CREATETIME < '" + applyDate2 + "'");
        }
        // 设置审批通过时间条件
        if(StringUtils.isNotBlank(maturity1) && StringUtils.isNotBlank(maturity2)){
            sql.append("AND a.maturity BETWEEN '" + maturity1 + "' AND '" + maturity2 + "'");
        }else if(StringUtils.isNotBlank(maturity1)){
            sql.append("AND a.maturity > '" + maturity1 + "'");
        }else if(StringUtils.isNotBlank(maturity2)){
            sql.append("AND a.maturity < '" + maturity2 + "'");
        }
        sql.append(" ) ab, (SELECT BILLPURID, MAX(BILLITEMID) BILLITEMID FROM YBILLPURBILLITEM WHERE 1=1 " );
        if (StringUtils.isNotBlank(billno)) {
            sql.append(" AND bill_no LIKE '%" + billno + "%'");
        }
        if(StringUtils.isNotBlank(billtype)){
            sql.append("AND billtype LIKE '%" + billtype + "%'");
        }
        sql.append(" GROUP BY BILLPURID) TT WHERE 1 = 1 AND AB.BILLITEMID = TT.BILLITEMID ");
        
        
        gridQueryCondition.setBoName("");
        gridQueryCondition.setTableSql("");
        gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
        gridQueryCondition.setWhereSql("");
        gridQueryCondition.setOrderSql("LASTMODIFYTIME desc");
        gridQueryCondition.setGroupBySql("");
        gridQueryCondition.setTableName("(" + sql.toString() + ") t");
        gridQueryCondition.setHandlerClass("com.infolion.xdss3.billpurchased.web.BillPurchasedGrid");
        String editable = "false";
        String needAuthentication = "true";
        GridData gridJsonData = this.gridService.getGridData(gridQueryCondition, editable, needAuthentication);
        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonList);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
    }
    
    /**
     * 中转报表查询页面
     */
    public ModelAndView _forwardReportQuery(HttpServletRequest request, HttpServletResponse response)
    {
    	return new ModelAndView("xdss3/billpurchased/billPurchasedReportQuery");
    }
    
    /**
     * 报表查询
     * @param request
     * @param response
     * @param gridQueryCondition
     * @throws Exception
     */
    public void reportQuery(HttpServletRequest request,
    		HttpServletResponse response, GridQueryCondition gridQueryCondition)
    throws Exception{
        
        BusinessObject businessObject = BusinessObjectService.getBusinessObject("BillPurchased");
        String strAuthSql = "";
        try {
            AuthSql authSql = (AuthSql) Class.forName("com.infolion.xdss3.CommonDataAuthSql").newInstance();
            strAuthSql = authSql.getAuthSql(businessObject);
            // 替换权限默认的前缀表名
            strAuthSql = strAuthSql.replace("YBILLPURCHASED", "t");
        } catch (Exception ex) {
            throw new SQLException("类不存在：" + ex.getMessage());
        }
        
        Map<String, String> map = this.paymentInnerInfoJDBCService.getParameterMap(request);

        String issuing_date = map.get("issuing_date");
        String issuing_dateEnd = map.get("issuing_dateEnd");
        if (StringUtils.isNotBlank(issuing_date)) {
            issuing_date = issuing_date.replaceAll("-", "");
            map.put("issuing_date", issuing_date);
        }
        if (StringUtils.isNotBlank(issuing_dateEnd)) {
            issuing_dateEnd = issuing_dateEnd.replaceAll("-", "");
            map.put("issuing_dateEnd", issuing_dateEnd);
        }
        
        String bukrs = map.get("bukrs");
        
        String project_no = map.get("project_no");
        String old_contract_no = map.get("old_contract_no");
        String customer = map.get("customer");
        String supplier = map.get("supplier");
        
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT distinct P.BILLPURID,"); 
        sb.append("       '' AS PROJECT_NO,"); 
        sb.append("       '' as PROJECT_NAME,"); //TP.PROJECT_NAME 
        sb.append("       '' as CONTRACT_NO,"); 
        sb.append("       '' AS BUSINESSCONTENT,"); 
        sb.append("       '' AS VBAK_AUART,"); 
        sb.append("       '' AS SUPPLIERCODE,"); 
        sb.append("       '' AS SUPPLIERNAME,"); 
        sb.append("       '' AS VBKD_BZIRK,"); 
        sb.append("       P.BILLPUR_NO,"); 
        sb.append("       DB.BANKACC,"); 
        sb.append("       DB.BANKSUBJ,"); 
        sb.append("       P.BILLPURRATE,"); 
        sb.append("       P.CURRENCY,"); 
        sb.append("       '' AS BEGBALANCE2,"); 
        sb.append("       '' AS BEGBALANCE,"); 
        sb.append("       P.payrealdate AS DOCUMENTARYDATE,"); 
        sb.append("       '' AS APPLYAMOUNT2,"); 
        sb.append("       '' AS APPLYAMOUNT,"); 
        sb.append("       P.MATURITY,"); 
        sb.append("       '' AS REDOCARYAMOUNT2,"); 
        sb.append("       '' AS REDOCARYAMOUNT,"); 
        sb.append("       '' AS ENDBALANCE2,"); 
        sb.append("       '' AS ENDBALANCE,"); 
        sb.append("       P.DEPT_ID,");
        sb.append("       DP.Dept_Name ");
        sb.append("  FROM YBILLPURCHASED P"); 
        sb.append("  LEFT JOIN YBILLPURBANKITEM DB"); 
        sb.append("    ON (P.BILLPURID = DB.BILLPURID) "); 
        sb.append("  LEFT JOIN YBILLPURBILLITEM PI"); 
        sb.append("    ON (P.BILLPURID = PI.BILLPURID) "); 
        sb.append("  LEFT JOIN T_SYS_DEPT DP");
        sb.append("    ON (DP.Dept_Id = P.DEPT_ID) ");
        sb.append(" WHERE 1=1 "); 
        
        // 合同号
        if (StringUtils.isNotBlank(old_contract_no)){
            sb.append(" and pi.contract_no like '%" + old_contract_no + "%'");
        }
        String deptId = map.get("dept_id");
        // 部门
        if (StringUtils.isNotBlank(deptId)){
            sb.append(" and p.dept_id like '%" + deptId + "%'");
        }
        


        // 公司
        if (StringUtils.isNotBlank(bukrs)) {
            sb.append("   AND exists (select 'x' from YVOUCHER V, YVOUCHERITEM VI where  P.Billpurid = V.BUSINESSID AND V.VOUCHERID = VI.VOUCHERID");
            sb.append(" AND trim(v.voucherno) IS NOT NULL ");
            sb.append(" and v.companycode like '%" + bukrs + "%' ) ");
        }
        // 日期
        if (StringUtils.isNotBlank(issuing_date) || StringUtils.isNotBlank(issuing_dateEnd)) {
            sb.append("   AND not exists (select 'x' from YVOUCHER V, YVOUCHERITEM VI where  P.Billpurid = V.BUSINESSID AND V.VOUCHERID = VI.VOUCHERID ");
            // 有起止日期且不相等
            if (StringUtils.isNotBlank(issuing_date) && StringUtils.isNotBlank(issuing_dateEnd)) {
                sb.append("   AND  ((V.CHECKDATE < '" + issuing_date + "' AND V.VOUCHERCLASS = '7') OR (V.CHECKDATE > '" + issuing_dateEnd + "' AND V.VOUCHERCLASS = '2')) ");
            } else if (StringUtils.isNotBlank(issuing_date)) {
                issuing_date = issuing_date.replaceAll("-", "");
                sb.append("   AND  ((V.CHECKDATE < '" + issuing_date + "' AND V.VOUCHERCLASS = '7') OR (V.CHECKDATE > '" + issuing_date + "' AND V.VOUCHERCLASS = '2')) ");
            }
            sb.append(" )");
        }

        gridQueryCondition.setBoName("");
        gridQueryCondition.setTableSql("");
        gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
        gridQueryCondition.setWhereSql("");
//      gridQueryCondition.setOrderSql("CREATETIME DESC");
        gridQueryCondition.setGroupBySql("");
        gridQueryCondition.setTableName("(" + sb.toString() + ") t");
        gridQueryCondition.setHandlerClass("com.infolion.xdss3.billpurchased.web.BillPurchasedReportGrid");
        String editable = "false";
        String needAuthentication = "true";
        GridData gridJsonData = this.gridService.getGridData(gridQueryCondition, editable, needAuthentication);
        Object[] list = (Object [])gridJsonData.getData();
        for (Object o : list) {
            ListOrderedMap lomap = (ListOrderedMap)o;
            String billpurid = (String)lomap.get("BILLPURID");
            if (!StringUtils.isNotBlank(issuing_dateEnd)) {
                issuing_dateEnd = issuing_date;
            }
            Map exportMap = this.billPurchasedJdbcDao.getExportApply(billpurid);
            lomap.put("PROJECT_NO", exportMap.get("PROJECT_NO"));
            lomap.put("PROJECT_NAME", exportMap.get("PROJECT_NAME"));
            lomap.put("CONTRACT_NO", exportMap.get("CONTRACT_PAPER_NO"));
            lomap.put("SUPPLIERNAME", exportMap.get("CUSTOMER"));
            lomap.put("VBKD_BZIRK", exportMap.get("DESTINATIONS"));
            lomap.put("VBAK_AUART", exportMap.get("VBAK_AUART"));
            
            // 期初
            Map<String,String> amountBegMap = this.billPurchasedJdbcDao.getAmount(billpurid, issuing_date, null, "押汇");
            Map<String,String> reAmountBegMap = this.billPurchasedJdbcDao.getAmount(billpurid, issuing_date, null, null);
            Map<String,String> amountPeriodMap = this.billPurchasedJdbcDao.getAmount(billpurid, issuing_date, issuing_dateEnd, "押汇");
            Map<String,String> reAmountPeriodMap = this.billPurchasedJdbcDao.getAmount(billpurid, issuing_date, issuing_dateEnd, null);
            
            BigDecimal begAmount = new BigDecimal((String)amountBegMap.get("AMOUNT"));
            BigDecimal reAmount = new BigDecimal((String)reAmountBegMap.get("AMOUNT"));
            
            lomap.put("BEGBALANCE",  begAmount.subtract(reAmount) );
            lomap.put("BEGBALANCE2", (new BigDecimal((String)amountBegMap.get("AMOUNT2"))).subtract(new BigDecimal((String)reAmountBegMap.get("AMOUNT2"))));
            
            
            BigDecimal perAmount = new BigDecimal((String)amountPeriodMap.get("AMOUNT"));
            BigDecimal perAmount2 = new BigDecimal((String)amountPeriodMap.get("AMOUNT2"));
            
            lomap.put("APPLYAMOUNT",  perAmount );
            lomap.put("APPLYAMOUNT2", perAmount2);
            
            BigDecimal reperAmount = new BigDecimal((String)reAmountPeriodMap.get("AMOUNT"));
            BigDecimal reperAmount2 = new BigDecimal((String)reAmountPeriodMap.get("AMOUNT2"));
            
            lomap.put("REDOCARYAMOUNT",  reperAmount );
            lomap.put("REDOCARYAMOUNT2", reperAmount2);
            
            lomap.put("ENDBALANCE",  ((BigDecimal)lomap.get("BEGBALANCE")).add(perAmount).subtract(reperAmount) );
            lomap.put("ENDBALANCE2", ((BigDecimal)lomap.get("BEGBALANCE2")).add(perAmount2).subtract(reperAmount2));
            
        }

        
        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonList);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
        
    }
    
    
    /**
     * @创建作者：hongjj
     * @创建日期：2011-09-21 增加预确认功能
     */
    public void _prepConfirmBillPurchased(HttpServletRequest request,
            HttpServletResponse response) {
        String id = request.getParameter("businessid");
        this.billPurchasedJdbcDao.prepConfirm(id, "2");
        this.operateSuccessfullyWithString(response, "");
    }
    
    
}