package com.infolion.xdss3.collect.domain;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Toolbar;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ColumnEditor;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ComboBoxEditor;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

public class CollectSuretyBondContractGrid extends GridTagHandler {
	
    private String collectColumn[] = { 
            "DEPT_ID", "DEPT_NAME", "TRADE_TYPE", "TRADE_TYPE_TEXT", "PROJECT_NO",
            "CONTRACT_NO", "VBKD_IHREZ", "VBAK_WAERK", "ORDER_TOTAL", "MARGIN_RATIO","ISPROMISE", "SURETYBOND", 
            "TITLE" 
            };
    private String collectColumnTitle[] = { 
            "部门代码", "部门", "业务类型代码", "业务类型", "立项号", 
            "销售合同号", "外部纸质合同号", "合同币别", "合同金额", "保证金比例%","是否约定跌价保证金", "合同应收保证金金额", 
            "物料组（合同）" };
	private String collectColumnName[] = {
	        "DEPT_ID", "DEPT_NAME", "TRADE_TYPE", "TRADE_TYPE_TEXT", "PROJECT_NO",
            "CONTRACT_NO", "VBKD_IHREZ", "VBAK_WAERK", "ORDER_TOTAL", "MARGIN_RATIO","ISPROMISE", "SURETYBOND", 
            "TITLE" 
			};
	
	private String collectColumnWidth[] = {
			"110",	// 操作
			"80",   // 业务状态 
			"100",	// 收款单号
			"100", 	// 客户名称 
			"100",	// 客户	
			
			"100",	// 申请收款金额
			"100",	// 申请收款金额
			"60",	// 申请收款金额
			"100",	// 申请收款金额
			"100",	// 申请收款金额
			"100",	// 申请收款金额
			"100",	// 申请收款金额
			"100",	// 申请收款金额
			};

	public CollectSuretyBondContractGrid() {
		buildGrid();
	}

	public void buildGrid() {
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < collectColumn.length; i++) {
			Column column = new Column();
			column.setColumnName(collectColumn[i]);
			column.setName(collectColumnName[i]);
			column.setTitle(collectColumnTitle[i]);
			column.setWidth(collectColumnWidth[i]);
			if( "DEPT_ID".equals(collectColumn[i])  || "TRADE_TYPE".equals(collectColumn[i]) ){
				column.setVisibility(false);
			} else if ( "ORDER_TOTAL".equals(collectColumn[i]) ){
//			    column.setColumnType(columnType)
			}
//			if(collectColumnTitle[i].equals("操作")){
//				column.setRenderer("_manager");
//			}
//			else if(collectColumnTitle[i].equals("是否包含保证金")){	// 设置成字典表
//				column.setColumnType("06");
//				column.setColumnTypeCode("06");
//				column.setDictTableName("YDYESORNO");
//				ColumnEditor columnEditor = new ComboBoxEditor();
//				columnEditor.setColumn(column);
//				columnEditor.setEditable(false);
//				columnEditor.setName(collectColumn[i]);
//				column.setColumnEditor(columnEditor);
//			}
			columnSet.add(column);
		}
		grid.setColumns(columnSet);
		Toolbar viewVoucher = new Toolbar();
		viewVoucher.setHandler("_viewVoucher");
		viewVoucher.setTitle("查看凭证");
		viewVoucher.setIconCls("icon-view");
		List toolbarList = new ArrayList();
		toolbarList.add(viewVoucher);
//		grid.setToolbar(toolbarList);
		setGrid(grid);
	}

}
