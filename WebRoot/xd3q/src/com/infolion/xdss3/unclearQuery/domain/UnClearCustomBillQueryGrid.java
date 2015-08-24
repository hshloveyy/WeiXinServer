package com.infolion.xdss3.unclearQuery.domain;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

public class UnClearCustomBillQueryGrid extends GridTagHandler {

    private String unclearCustomerColumn[] = { "UNCLEARCUSBILLID", "CUSTOMSCLEARID", "CONTRACT_NO", "PROJECT_NO",
            "VOUCHERNO", "ACCOUNTDATE", "BILLNO", "TEXT", "OLD_CONTRACT_NO", "SAP_ORDER_NO", "CURRENCY",
            "EXCHANGERATE", "RECEIVABLEAMOUNT", "RECEIVABLEDATE", "ONROADAMOUNT", "CLEARAMOUNT", "ADJUSTAMOUNT",
            "RECEIVEDAMOUNT", "UNRECEIVABLEAMOU", "PAYMENTNO", "PAYMENTID", "PAYMENTITEMID", "CUSTOMERTITLEID",
            "BWBJE", "UNBWBJE" };
    private String unclearCustomerColumnTitle[] = { "操作", "客户单清帐ID", "合同编号", "立项号", "财务凭证号", "记账日期", "SAP开票号", "清帐用途",
            "原合同号", "SAP订单号", "货币", "押汇汇率", "应收款", "应收款日", "在批金额", "清帐金额", "调整差额", "已收款", "未收款", "付款单号", "付款ID",
            "付款金额分配ID", "未清客户数据抬头ID", "本位币金额(判断汇兑损益用)", "未清本位币金额" };

    private String unclearCustomerColumnWidth[] = { "80", "80", "80", "80", "80", "80", "80", "80", "80", "80", "80",
            "80", "80", "80", "80", "80", "80", "80", "80", "80", "80", "80", "80", "80", "80" };

    public UnClearCustomBillQueryGrid() {
        buildGrid();
    }

    public void buildGrid() {
        Grid grid = new Grid();
        List<Column> columnSet = new ArrayList<Column>();
        for (int i = 0; i < unclearCustomerColumn.length; i++) {
            Column column = new Column();
            column.setColumnName(unclearCustomerColumn[i]);
            column.setName(unclearCustomerColumn[i]);
            column.setTitle(unclearCustomerColumnTitle[i]);
            column.setWidth(unclearCustomerColumnWidth[i]);
            column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
            if (unclearCustomerColumn[i].equals("mandt")) { // 客户端
                column.setVisibility(false);
            }
            if (unclearCustomerColumn[i].equals("UNCLEARCUSBILLID")) { // 客户端
                column.setVisibility(false);
            }
            if (unclearCustomerColumn[i].equals("CUSTOMSCLEARID")) { //连接
                column.setRenderer("_viewCustomSingleClear");                
            }
            columnSet.add(column);
        }
        grid.setColumns(columnSet);
        setGrid(grid);
    }

}
