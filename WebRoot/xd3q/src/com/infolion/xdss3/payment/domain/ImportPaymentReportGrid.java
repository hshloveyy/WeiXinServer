package com.infolion.xdss3.payment.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Toolbar;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ColumnEditor;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ComboBoxEditor;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

public class ImportPaymentReportGrid extends GridTagHandler {

    protected String boName = "ImportPayment";

    private String homePaymentColumn[] = { "PAYMENTID", "PROJECT_NO",            "PROJECT_NAME", 
            "SAP_ORDER_NO",            "OLD_CONTRACT_NO",
            //"BUSINESSCONTENT",            
            "VBAK_AUART",            "EKKO_LIFNR",            "EKKO_LIFNR_NAME",            "VBKD_BZIRK",
            "ISOVERREPAY",                       "ISREPRESENTPAY",       "REPRESENTPAYCUST",            "NAME1",            "PAYMENTNO",            "DOCUARYBANKACCO",
            "DOCUARYBANKSUBJ",       "DOCUMENTARYRATE",            "CURRENCY2",            "DOCUMENTARYDATE",            "BEGBALANCE",            "BEGBALANCE2",
            "APPLYAMOUNT",                "APPLYAMOUNT2",            "DOCUMENTARYPAYDT",            "REDOCARYAMOUNT",
            "REDOCARYAMOUNT2",            "ENDBALANCE",            "ENDBALANCE2", "Dept_Name" };

    private String homePaymentColumnTitle[] = { "操作", "立项号","立项名称",//"物料",
            "采购订单号","纸质合同号",
            //"业务内容",
            "业务类型","供应商编码","供应商名称","地区",
            "是否为海外代付","是否纯代理","纯代理客户编号","纯代理客户名称","进口押汇单号","押汇银行",
            "短期外汇科目","汇率","币别","押汇日","期初余额（外币）","期初余额（本币）",
            "押汇金额（外币）","押汇金额（本币）","到期还款日","还款（外币）",
            "还款（本币）","期末余额（外币）","期末余额（本币）", "部门" };

    private String name[] = { "PAYMENTID", "PROJECT_NO",            "PROJECT_NAME", 
            "SAP_ORDER_NO",            "OLD_CONTRACT_NO",
            //"BUSINESSCONTENT",            
            "VBAK_AUART",            "EKKO_LIFNR",            "EKKO_LIFNR_NAME",            "VBKD_BZIRK",
            "ISOVERREPAY",                       "ISREPRESENTPAY",            "REPRESENTPAYCUST",            "NAME1",            "PAYMENTNO",            "DOCUARYBANKACCO",
            "DOCUARYBANKSUBJ",            "DOCUMENTARYRATE",            "CURRENCY2",            "DOCUMENTARYDATE",            "BEGBALANCE",            "BEGBALANCE2",
            "APPLYAMOUNT",            "APPLYAMOUNT2",            "DOCUMENTARYPAYDT",            "REDOCARYAMOUNT",
            "REDOCARYAMOUNT2",            "ENDBALANCE",            "ENDBALANCE2", "Dept_Name" };

    private String columnWidth[] = { 
            "80", // "业务状态"
            "80", // "业务状态"
            "100", // "付款单号"
            "80", // "供应商"
            "80", // "付款单状态"

            //"80", // 业务内容
            "80", // 业务类型
            "80", // 供应商编码
            "80", // "收款银行"
            "80", // "付款人"

            "60", // 是否海外代付
            "60", // "部门名称"
            "80", // "部门"
            "80", // "预计到货日"
            "100", // "记账日期"
            "80", // "凭证日期"
            
            "90", // "银行对外付款日"
            "80", // "实际付款金额"
            "80", // "实际币别名称"
            "80", // "实际币别"
            "80", // "付款类型"
            
            "80", // "收款银行账号"
            "80", // "是否贸管预付款"
            "80", // "应收票日"
            "80", // "财务编号"
            "80", // "是否海外代付"
            
            "80", // "创建人"
            "80", // "付款方式"
            "80", // "付款基准日"
            "80", // "付款基准日"
    };

    public ImportPaymentReportGrid() {
        buildGrid();
    }

    public void buildGrid() {
        // 判断是否在name[]存在
        List showColumn = Arrays.asList("");

        Grid grid = new Grid();
        List<Column> columnSet = new ArrayList<Column>();
        for (int i = 0; i < homePaymentColumn.length; i++) {
            Column column = new Column();
            column.setColumnName(homePaymentColumn[i]);
            column.setName(name[i]);
            column.setTitle(homePaymentColumnTitle[i]);
            column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
            column.setWidth(columnWidth[i]);

//            if (showColumn.contains(homePaymentColumnTitle[i])) {
                column.setVisibility(true);
//            } else {
//                column.setVisibility(false);
//            }
            if ("paymenttype".equalsIgnoreCase(name[i])) {
                column.setColumnType("06");
                column.setColumnTypeCode("06");
                column.setDictTableName("YDPAYTRADETYPE");
                ColumnEditor columnEditor = new ComboBoxEditor();
                columnEditor.setColumn(column);
                columnEditor.setEditable(false);
                columnEditor.setName(homePaymentColumn[i]);
                column.setColumnEditor(columnEditor);

            }
            // 操作列填入paymentid
            if (i == 0) {
                column.setRenderer("operaRD");
            }
            columnSet.add(column);
        }
        grid.setColumns(columnSet);

        Toolbar viewVoucher = new Toolbar();
        viewVoucher.setHandler("_viewVoucher");
        viewVoucher.setTitle("查看凭证");
        viewVoucher.setIconCls("icon-view");
        List toolbarList = new ArrayList();
        toolbarList.add(viewVoucher);
        grid.setToolbar(toolbarList);

        setGrid(grid);
    }

    public String getBoName() {
        return boName;
    }

    public void setBoName(String boName) {
        this.boName = boName;
    }

}
