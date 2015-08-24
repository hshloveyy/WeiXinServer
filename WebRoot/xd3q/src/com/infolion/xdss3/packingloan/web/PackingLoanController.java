/*
 * @(#)PackingLoanController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年05月19日 16点48分19秒
 *  描　述：创建
 */
package com.infolion.xdss3.packingloan.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.packingloan.domain.PackingBankItem;
import com.infolion.xdss3.packingloan.domain.PackingLoan;
import com.infolion.xdss3.packingloan.domain.PackingReBankItem;
import com.infolion.xdss3.packingloan.domain.PackingReBankTwo;
import com.infolion.xdss3.packingloanGen.web.PackingLoanControllerGen;
import com.infolion.xdss3.voucher.domain.Voucher;
import com.infolion.xdss3.voucher.service.VoucherService;

/**
 * <pre>
 * 打包贷款(PackingLoan)控制器类
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
public class PackingLoanController extends PackingLoanControllerGen {

    @Autowired
    private VoucherService voucherService;

    public void setVoucherService(VoucherService voucherService) {
        this.voucherService = voucherService;
    }
    
    @Autowired
    private GridService gridService;
    
    public void setGridService(GridService gridService) {
        this.gridService = gridService;
    }
    /**
     * 现金日记帐
     * 
     * @param request
     * @param response
     */
    public void _cashJournal(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        // 绑定主对象值
        PackingLoan packingLoan = (PackingLoan) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
                PackingLoan.class, true, request.getMethod(), true);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<PackingBankItem> packingBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingBankItem.class, null);
        packingLoan.setPackingBankItem(packingBankItemmodifyItems);
        Set<PackingBankItem> packingBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingBankItem.class, null);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<PackingReBankItem> packingReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { packingLoan }, PackingReBankItem.class, null);
        packingLoan.setPackingReBankItem(packingReBankItemmodifyItems);
        Set<PackingReBankItem> packingReBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { packingLoan }, PackingReBankItem.class, null);

        Set<PackingReBankTwo> packingReBankTwomodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingReBankTwo.class, null);
        packingLoan.setPackingReBankTwo(packingReBankTwomodifyItems);
        Set<PackingReBankTwo> packingReBankTwodeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingReBankTwo.class, null);
        
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
                SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null) {
            packingLoan.setSettleSubject(settleSubject);
            settleSubject.setPackingLoan(packingLoan);
        }
        // 绑定子对象(一对一关系)
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class,
                false, request.getMethod(), true);
        if (fundFlow != null) {
            packingLoan.setFundFlow(fundFlow);
            fundFlow.setPackingLoan(packingLoan);
        }
        this.packingLoanService._saveOrUpdate(packingLoan, packingBankItemdeleteItems, packingReBankItemdeleteItems,
                getBusinessObject());
        this.packingLoanService.updateReBankTwo(packingLoan.getPackingid());
        packingLoan = this.packingLoanService._get(packingLoan.getPackingid());
        /*
         * 将现金日记账所需要的参数拼装成URL
         */
        com.infolion.platform.console.sys.context.UserContext userContext = UserContextHolder.getLocalUserContext()
                .getUserContext();
        String username = userContext.getSysUser().getUserName();
        String xjrj = this.getProperties("config/config.properties").getProperty("xjrj");
        String journalType = "2"; // journalType,参数说明：流转到资金部的，则参数传2；流转到综合二部的，传1。
        String bus_id = ""; // 存放所有银行行项ID
        String bus_type = ""; // 业务类型

        // 9:借,押汇；10:货,还押汇
        String ps = packingLoan.getProcessstate();
        if ("资金部出纳确认还打包贷款".equals(ps)) {
            for (PackingReBankItem rebank : packingLoan.getPackingReBankItem()) {
                if (!"已做账".equals(rebank.getBusinesstype())) {
                    bus_id += rebank.getBankitemid() + ",";
                    bus_type += "10,";
                }
            }
        } else if ("资金部出纳确认打包贷款收款".equals(ps)) {
            for (PackingBankItem bank : packingLoan.getPackingBankItem()) {
                bus_id += bank.getBankitemid() + ",";
                bus_type += "9,";
            }
        }

        bus_id = org.apache.commons.lang.StringUtils.substringBeforeLast(bus_id, ",");
        bus_type = org.apache.commons.lang.StringUtils.substringBeforeLast(bus_type, ",");

        String cashJournalURl = xjrj + "/xjrj/journal.do?method=preAdd" + "&journalType=" + journalType + "&bus_id="
                + bus_id + "&bus_type=" + bus_type + "&userName=" + username + "&isFromXdss=1";
        // 将链接放入JSON中
        jo.put("cashJournalURl", cashJournalURl);

        jo.put("packing_no", packingLoan.getPacking_no());
        jo.put("packingid", packingLoan.getPackingid());
        String creator = packingLoan.getCreator();
        String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
        jo.put("creator_text", creator_text);
        jo.put("creator", creator);
        jo.put("settlesubjectid", packingLoan.getSettleSubject() != null ? packingLoan.getSettleSubject()
                .getSettlesubjectid() : "");
        jo.put("fundflowid", packingLoan.getFundFlow() != null ? packingLoan.getFundFlow().getFundflowid() : "");
        jo.put("createtime", packingLoan.getCreatetime());
        jo.put("lastmodifytime", packingLoan.getLastmodifytime());
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
    public void _voucherPreview(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        // 绑定主对象值
        PackingLoan packingLoan = (PackingLoan) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
                PackingLoan.class, true, request.getMethod(), true);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<PackingBankItem> packingBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingBankItem.class, null);
        packingLoan.setPackingBankItem(packingBankItemmodifyItems);
        Set<PackingBankItem> packingBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingBankItem.class, null);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<PackingReBankItem> packingReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { packingLoan }, PackingReBankItem.class, null);
        packingLoan.setPackingReBankItem(packingReBankItemmodifyItems);
        Set<PackingReBankItem> packingReBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { packingLoan }, PackingReBankItem.class, null);
      
        Set<PackingReBankTwo> packingReBankTwomodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingReBankTwo.class, null);
        packingLoan.setPackingReBankTwo(packingReBankTwomodifyItems);
        Set<PackingReBankTwo> packingReBankTwodeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingReBankTwo.class, null);
        
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
                SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null) {
            packingLoan.setSettleSubject(settleSubject);
            settleSubject.setPackingLoan(packingLoan);
        }
        // 绑定子对象(一对一关系)
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class,
                false, request.getMethod(), true);
        if (fundFlow != null) {
            packingLoan.setFundFlow(fundFlow);
            fundFlow.setPackingLoan(packingLoan);
        }
        this.packingLoanService._saveOrUpdate(packingLoan, packingBankItemdeleteItems, packingReBankItemdeleteItems,
                getBusinessObject());

        this.packingLoanService.updateReBankTwo(packingLoan.getPackingid());

        // 重新取得数据库数据
        packingLoan = this.packingLoanService._get(packingLoan.getPackingid());

        String ps = packingLoan.getProcessstate();
        List<String> retList = new ArrayList();

        // 保存前先删除
        this.voucherService.deleteVoucherByBusinessid(packingLoan.getPackingid());

        // 财务会计确认押汇过账
        if ("财务会计确认打包贷款过账".equals(ps)) {
            retList = this.packingLoanService.shortTimepaySaveVoucher(packingLoan);

            // 判断是否需要删除
            this.voucherService.judgeVoucherNeedDel(packingLoan.getPackingid());
        }

        // 还短期外汇借款
        if ("财务会计确认还打包贷款并做帐".equals(ps)) {
            retList = this.packingLoanService.returnShortTimeSaveVoucher(packingLoan);
            this.voucherService.judgeVoucherNeedDel_2(packingLoan.getPackingid());
            // ************************处理短期外汇借款清帐凭证***************************
            List<Voucher> voucherList = this.packingLoanService.getReturnLoanClearVendor(packingLoan);
            for (int k = 0; k < voucherList.size(); k++) {
                Voucher returnClearvoucher = voucherList.get(k);
                retList.add(returnClearvoucher.getVoucherid());
            }
            // ************************处理短期外汇借款清帐凭证***************************
        }

        // 判断是否需要删除
        this.voucherService.judgeVoucherNeedDel_2(packingLoan.getPackingid());

        try {
            String vouchids = "";
            for (String _vouchid : retList) {
                vouchids = vouchids + _vouchid + "&";
            }
            vouchids = StringUtils.substringBeforeLast(vouchids, "&");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(vouchids);
            System.out.println(vouchids);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }

    }
    
    /**
     * 中转综合查询页面
     */
    public ModelAndView _conditionQuery(HttpServletRequest request, HttpServletResponse response)
    {
        return new ModelAndView("xdss3/packingloan/packingLoanQuery");
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
        BusinessObject businessObject = BusinessObjectService.getBusinessObject("PackingLoan");
        String strAuthSql = "";
        try {
            AuthSql authSql = (AuthSql) Class.forName("com.infolion.xdss3.CommonDataAuthSql").newInstance();
            strAuthSql = authSql.getAuthSql(businessObject);
            // 替换权限默认的前缀表名
            strAuthSql = strAuthSql.replace("YPACKINGLOAN", "t");
        } catch (Exception ex) {
            throw new SQLException("类不存在：" + ex.getMessage());
        }
        String signRegExp="[\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\`\\-\\=\\～\\！\\＠\\＃\\＄\\" +
        "％\\＾\\＆\\＊\\\\（\\）\\＿\\＋\\＝\\－\\｀\\[\\]\\\\'\\;\\/\\.\\,\\<\\>\\?\\:" +
        "\"\\{\\}\\|\\，\\．\\／\\；\\＇\\［\\］\\＼\\＜\\＞\\？\\：\\＂\\｛\\｝\\｜\\“\\”\\" +
        "‘\\’\\。\r+\n+\t+\\s\\ ]";
        
        StringBuffer sql = new StringBuffer("SELECT DISTINCT A.*, O.ORGANIZATIONNAME AS DEPT_NAME" +
        		"  FROM YPACKINGLOAN A" +
        		"  LEFT JOIN YORGANIZATION O ON (A.DEPT_ID = O.ORGANIZATIONID)" +
                "  LEFT JOIN yuser u ON  (u.userid=a.creator)" +
        		" WHERE 1 = 1 ");
        
        
        
        // 获取SQL参数
        String deptId = request.getParameter("dept_id");                // 部门ID
        String packing_no = request.getParameter("packing_no");     // 出口押汇单号
        String contractno = request.getParameter("contractno");        // 关联合同号
        
        String credit_no = request.getParameter("credit_no");          // 信用证号
        String createbank = request.getParameter("createbank");          // 开证行
        String titleText = request.getParameter("text");          //抬头文本（押汇用途）
        if (StringUtils.isNotBlank(titleText)) {
            titleText = URLDecoder.decode(titleText, "UTF-8");
        }
        
        String retext = request.getParameter("retext");          // 抬头文本（还押汇用途）
        if (StringUtils.isNotBlank(retext)) {
            retext = URLDecoder.decode(retext, "UTF-8");
        }
        String creator = request.getParameter("creator");             // 创建人
        String voucherNo = request.getParameter("voucherno");           // 凭证号
        

        String dealine1 = request.getParameter("dealine1");  // 审批通过时间1
        String dealine2 = request.getParameter("dealine2");  // 审批通过时间2
        String applyDate1 = request.getParameter("apply_date1");        //申请时间1
        String applyDate2 = request.getParameter("apply_date2");        // 申请时间2
        
        
        // 设置客户编号条件
        if(StringUtils.isNotBlank(creator)){
            sql.append("AND u.username = '" + creator + "'");
        }
        // 设置凭证号条件
        if(StringUtils.isNotBlank(voucherNo)){
            sql.append("AND EXISTS(SELECT 1 FROM YVOUCHER d WHERE d.BUSINESSID=a.packingid AND d.VOUCHERNO LIKE '%" + voucherNo + "%')");
        }
        // 设置部门ID条件
        if(StringUtils.isNotBlank(deptId)){
            sql.append("AND a.dept_id = '" + deptId + "'");
        }
        
        if(StringUtils.isNotBlank(packing_no)){
            sql.append("AND a.packing_no LIKE '%" + packing_no + "%'");
        }
        if(StringUtils.isNotBlank(contractno)){
            sql.append("AND a.contractno LIKE '%" + contractno + "%'");
        }
        if(StringUtils.isNotBlank(credit_no)){
            sql.append("AND a.creditno LIKE '%" + credit_no + "%'");
        }
        if(StringUtils.isNotBlank(createbank)){
            sql.append("AND a.createbank LIKE '%" + createbank + "%'");
        }
        // 设置抬头文本条件
        if(StringUtils.isNotBlank(titleText)){
            sql.append("AND a.TEXT LIKE '%" + titleText + "%'");
        }
        // 设置抬头文本条件
        if(StringUtils.isNotBlank(retext)){
            sql.append("AND a.retext LIKE '%" + retext + "%'");
        }
        
        
        
        // 设置申请时间条件
        if(StringUtils.isNotBlank(applyDate1) && StringUtils.isNotBlank(applyDate2)){
            sql.append("AND substr(a.CREATETIME,0,8) BETWEEN '" + applyDate1.replaceAll("-", "") + "' AND '" + applyDate2.replaceAll("-", "") + "'");
        }else if(StringUtils.isNotBlank(applyDate1)){
            sql.append("AND substr(a.CREATETIME,0,8) > '" + applyDate1 + "'");
        }else if(StringUtils.isNotBlank(applyDate2)){
            sql.append("AND substr(a.CREATETIME,0,8) < '" + applyDate2 + "'");
        }
        // 打包到期日
        if(StringUtils.isNotBlank(dealine1) && StringUtils.isNotBlank(dealine2)){
            sql.append("AND a.dealine BETWEEN '" + dealine1.replaceAll("-", "") + "' AND '" + dealine2.replaceAll("-", "") + "'");
        }else if(StringUtils.isNotBlank(dealine1)){
            sql.append("AND a.dealine > '" + dealine1.replaceAll("-", "") + "'");
        }else if(StringUtils.isNotBlank(dealine2)){
            sql.append("AND a.dealine < '" + dealine2.replaceAll("-", "") + "'");
        }
        
        
        gridQueryCondition.setBoName("");
        gridQueryCondition.setTableSql("");
        gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
        gridQueryCondition.setWhereSql("");
        gridQueryCondition.setOrderSql("LASTMODIFYTIME desc");
        gridQueryCondition.setGroupBySql("");
        gridQueryCondition.setTableName("(" + sql.toString() + ") t");
        gridQueryCondition.setHandlerClass("com.infolion.xdss3.packingloan.web.PackingloanGrid");
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
}