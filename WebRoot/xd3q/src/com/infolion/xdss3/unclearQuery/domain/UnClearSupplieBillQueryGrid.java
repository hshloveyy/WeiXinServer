package com.infolion.xdss3.unclearQuery.domain;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

public class UnClearSupplieBillQueryGrid extends GridTagHandler {

    private String unclearCustomerColumn[] = { "UNCLEARSBILLID", "SUPPLIERSCLEARID", "CONTRACT_NO",
            "PROJECT_NO", "VOUCHERNO", "ACCOUNTDATE", "INVOICE", "TEXT", "OLD_CONTRACT_NO", "SAP_ORDER_NO", "CURRENCY",
            "RECEIVABLEAMOUNT", "RECEIVABLEDATE", "PAIDAMOUNT", "UNPAIDAMOUNT", "ONROADAMOUNT", "CLEARAMOUNT",
            "ADJUSTAMOUNT", "VENDORTITLEID", "BWBJE", "UNBWBJE", "EXCHANGERATE" };
    private String unclearCustomerColumnTitle[] = { "操作", "供应商单清帐ID", "合同编号", "项目编号", "财务凭证号", "过账日期",
            "SAP开票号", "文本", "原合同编号", "SAP订单号", "货币", "应付款", "应付款日", "已付款", "未付款", "在批金额", "清帐金额", "调整差额",
            "供应商未清数据抬头ID", "本位币金额(判断汇兑损益用)", "未清本位币金额", "汇率" };

    private String unclearCustomerColumnWidth[] = { "80", "80", "80", "80", "80", "80", "80", "80", "80", "80",
            "80", "80", "80", "80", "80", "80", "80", "80", "80", "80", "80", "80" };

    public UnClearSupplieBillQueryGrid() {
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
            if (unclearCustomerColumn[i].equals("UNCLEARSBILLID")) { 
                column.setVisibility(false);
            }
            if (unclearCustomerColumn[i].equals("SUPPLIERSCLEARID")) { //连接
                column.setRenderer("_viewSupplierSinglClear");                
            }
                
            columnSet.add(column);
        }
        grid.setColumns(columnSet);
        setGrid(grid);
    }

}
