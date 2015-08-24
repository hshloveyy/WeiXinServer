package com.infolion.xdss3.supplierDrawback.web;

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
 * SupplierGrid
 */
public class SupplierGrid extends GridTagHandler{
	/*
	 * 退款表ID、退款单号 、被重分配退款ID、客户、供应商 、部门 、退款币别、退款金额、抬头文本、备注、
	 * 汇率、记账日期、凭证日期、现金流项目 、流程状态 、业务状态 、贸易方式、创建人、创建日期、最后修改者 、
	 * 最后修改日期
	 */
	private String customerRefundColumn[] = {"refundmentid", "refundmentno", "supplier_name", "DEPT_NAME", "refundcurrency", "refundamount", "text", "remark", "exchangerate", "accountdate", "voucherdate", "cashflowitem", "processstate", "businessstate", "tradetype", "creator", "createtime", "lastmodiyer", "lastmodifytime", "DEPT_ID"};
	private String customerRefundColumnTitle[] = {"操作", "退款单号",  "供应商", "部门", "退款币别", "退款金额", "抬头文本", "备注", "汇率", "记账日期", "凭证日期", "现金流项目", "流程状态", "业务状态", "贸易方式", "创建人", "创建日期", "最后修改者", "最后修改日期", "部门ID"};
	private String customerRefundColumnName[] = {"refundmentid", "refundmentno", "supplier_name", "DEPT_NAME", "refundcurrency", "refundamount", "text", "remark", "exchangerate", "accountdate", "voucherdate", "cashflowitem", "processstate", "businessstate", "tradetype", "creator", "createtime", "lastmodiyer", "lastmodifytime", "DEPT_ID"};
	
	public SupplierGrid(){
		buildGrid();
	}
	
	@Override
	public void buildGrid() {
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < customerRefundColumn.length; i++) {
			Column column = new Column();
			column.setColumnName(customerRefundColumn[i]);
			column.setTitle(customerRefundColumnTitle[i]);
			column.setName(customerRefundColumnName[i]);
			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			column.setWidth("150");
			
			if ("creator".equalsIgnoreCase(customerRefundColumn[i])) {
                column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
                column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
                column.setDictTableName("YUSER");
            } else if ("lastmodiyer".equals(customerRefundColumn[i])) {
                column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
                column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
            } else if ("businessstate".equalsIgnoreCase(customerRefundColumn[i])) {
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
		
		
		Toolbar viewVoucher = new Toolbar();
        viewVoucher.setHandler("_viewVoucher");
        viewVoucher.setTitle("查看凭证");
        viewVoucher.setIconCls("icon-view");
        List<Toolbar> toolbarList = new ArrayList<Toolbar>();
        toolbarList.add(viewVoucher);
        grid.setToolbar(toolbarList);
        
        
		grid.setColumns(columnSet);
		setGrid(grid);
	}

}

