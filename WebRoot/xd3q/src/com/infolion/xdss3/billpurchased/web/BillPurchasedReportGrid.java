package com.infolion.xdss3.billpurchased.web;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Toolbar;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ColumnEditor;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ComboBoxEditor;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

/**
 * @author HONG
 */
public class BillPurchasedReportGrid extends GridTagHandler {

	private String colums[] = { "BILLPURID", "PROJECT_NO", "PROJECT_NAME", "CONTRACT_NO",
            //"BUSINESSCONTENT",            
	        "VBAK_AUART",            //"SUPPLIERCODE",            
	        "SUPPLIERNAME",            "VBKD_BZIRK",
            "BILLPUR_NO",            "BANKACC",
            "BANKSUBJ",            "BILLPURRATE",            "CURRENCY",            "BEGBALANCE",            "BEGBALANCE2",
            "DOCUMENTARYDATE",            "APPLYAMOUNT",            "APPLYAMOUNT2",            "MATURITY",            "REDOCARYAMOUNT",
            "REDOCARYAMOUNT2",            "ENDBALANCE",            "ENDBALANCE2", "DEPT_NAME" };
	private String columnTitle[] = { "操作", "立项号","立项名称","纸质合同号",
            //"业务内容",
	        "业务类型", //"客户编码",
	        "客户名称","地区",
            "出口押汇单号","押汇银行",
            "短期外汇科目","汇率","币别","期初余额（外币）","期初余额（本币）",
            "押汇日","押汇金额（外币）","押汇金额（本币）","到期还款日","还款（外币）",
            "还款（本币）","期末余额（外币）","期末余额（本币）", "部门"  };
	private String columnName[] = { "BILLPURID", "PROJECT_NO", "PROJECT_NAME", "CONTRACT_NO",
            //"BUSINESSCONTENT",            
	        "VBAK_AUART",            //"SUPPLIERCODE",            
	        "SUPPLIERNAME",            "VBKD_BZIRK",
            "BILLPUR_NO",            "BANKACC",
            "BANKSUBJ",            "BILLPURRATE",            "CURRENCY",            "BEGBALANCE",            "BEGBALANCE2",
            "DOCUMENTARYDATE",            "APPLYAMOUNT",            "APPLYAMOUNT2",            "MATURITY",            "REDOCARYAMOUNT",
            "REDOCARYAMOUNT2",            "ENDBALANCE",            "ENDBALANCE2", "DEPT_NAME" };

	public BillPurchasedReportGrid() {
		buildGrid();
	}

	@Override
	public void buildGrid() {
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < colums.length; i++) {
			Column column = new Column();
			column.setColumnName(colums[i]);
			column.setTitle(columnTitle[i]);
			column.setName(columnName[i]);
			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			column.setWidth("100");
			if (i == 0) {
				column.setRenderer("_manager");
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

}
