package com.infolion.xdss3.customerDrawback.web;

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
 * JDK版本:1.5
 * 
 * @author 陈非
 * @version 创建时间：2011-1-21 上午10:59:23
 * 
 */
public class CustomerGrid extends GridTagHandler {
	/*
	 * 退款单号、客户名称、退款货币、退款金额、业务范围名称、抬头文本、汇率、记账日期、凭证日期、现金流项目、流程状态、创建人、创建日期、最后修改者、最后修改日期
	 * 业务状态、贸易方式、
	 */
	private String customerRefundColumn[] = {"refundmentid", "customer","customer_name", "refundmentno",
			"refundcurrency", "refundamount", "DEPT_NAME", "businessstate",
			"text", "remark", "exchangerate", "accountdate", "voucherdate",
			"cashflowitem", "processstate", "tradetype", "creator",
			"createtime", "lastmodiyer", "lastmodifytime" };
	private String customerRefundColumnTitle[] = {"操作", "客户","客户名称", "退款单号", "退款货币",
			"退款金额", "部门", "业务状态", "抬头文本", "备注", "汇率", "记账日期", "凭证日期",
			"现金流项目", "流程状态", "贸易方式", "创建人", "创建日期", "最后修改者", "最后修改日期" };
	private String customerRefundColumnName[] = {"refundmentid", "customer","customer_name", "refundmentno",
			"refundcurrency", "refundamount", "DEPT_ID", "businessstate",
			"text", "remark", "exchangerate", "accountdate", "voucherdate",
			"cashflowitem", "processstate", "tradetype", "creator",
			"createtime", "lastmodiyer", "lastmodifytime" };

	public CustomerGrid() {
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
//			if (customerRefundColumn[i].equals("DEPT_ID")) {
//				column.setVisibility(false);
//			}

			if ("creator".equalsIgnoreCase(customerRefundColumn[i])) {
                column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
                column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
                column.setDictTableName("YUSER");
			} else if ("businessstate".equalsIgnoreCase(customerRefundColumn[i])) {
			    column.setColumnType("06");
                column.setColumnTypeCode("06");
                column.setDictTableName("YDCOLLECTBUZSTATE");
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
		List<Toolbar> toolbarList = new ArrayList<Toolbar>();
		toolbarList.add(viewVoucher);
		grid.setToolbar(toolbarList);
		setGrid(grid);
	}

}
