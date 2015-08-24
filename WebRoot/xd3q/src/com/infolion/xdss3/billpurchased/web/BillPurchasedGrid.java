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
public class BillPurchasedGrid extends GridTagHandler{

    private String colums[] = {"billpurid", "billpur_no", "DEPT_NAME", "applyamount", "currency", "billpurrate", "documentaryinter", "bill_no", "billtype", "documentarylimit", "payrealdate", "maturity", "voucherdate", "accountdate", "text", "revoucherdate", "reaccountdate", "retext", "remark", "creator", "createtime", "businessstate", "processstate", "lastmodiyer", "lastmodifytime"};
	private String columnTitle[] = {"操作", "出口押汇单号", "部门", "申请押汇金额", "押汇币别", "押汇汇率", "押汇利率", "出单发票号", "单据类型", "押汇期限", "押汇收款日", "押汇到期日", "凭证日期", "记账日期", "抬头文本(押汇用途)", "还押汇凭证日期", "还押汇记帐日期", "抬头文本(还押汇用途)", "备注", "创建人", "创建日期", "业务状态", "流程状态", "最后修改者", "最后修改日期"};
	private String columnName[] = {"billpurid", "billpur_no", "DEPT_NAME", "applyamount", "currency", "billpurrate", "documentaryinter", "bill_no",  "billtype", "documentarylimit", "payrealdate", "maturity", "voucherdate", "accountdate", "text", "revoucherdate", "reaccountdate", "retext", "remark", "creator", "createtime", "businessstate", "processstate", "lastmodiyer", "lastmodifytime"};
	
	public BillPurchasedGrid(){
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
			
			if ("creator".equalsIgnoreCase(colums[i])) {
                column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
                column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
                column.setDictTableName("YUSER");
            } else if ("lastmodiyer".equals(colums[i])) {
                column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
                column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
            } else if ("businessstate".equalsIgnoreCase(colums[i])) {
                column.setColumnType("06");
                column.setColumnTypeCode("06");
                column.setDictTableName("YDPAYMENTBUZSTATE");
                ColumnEditor columnEditor = new ComboBoxEditor();
                columnEditor.setColumn(column);
                columnEditor.setEditable(false);
                column.setColumnEditor(columnEditor);
            }
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

