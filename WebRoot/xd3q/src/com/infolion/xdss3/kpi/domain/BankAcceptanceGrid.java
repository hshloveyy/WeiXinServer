package com.infolion.xdss3.kpi.domain;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Toolbar;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

/**
 * 银行承兑汇票
 * @author user
 *
 */
public class BankAcceptanceGrid extends GridTagHandler {
	
	private String bankAcceptanceColumn[] = {	       
	      //  "orderno",
	        "checkdate",
	        "amount",
	        "amount2",
	        "amount3",
	        "amount4"	
			};
	private String bankAcceptanceColumnTitle[] = {	                        
	      //  "采购合同号",
	        "开立日期",
	        "银承金额",
	        "还款金额",
	        "当天银承还款后余额",
	        "银承还款后余额"	
			};
	
	private String bankAcceptanceColumnWidth[] = {	       
	     //   "110",
	        "100",
	        "100",
	        "100",	
	        "100",
	        "180"
			};

	public BankAcceptanceGrid() {
		buildGrid();
	}

	public void buildGrid() {
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < bankAcceptanceColumn.length  ; i++) {
			Column column = new Column();
			column.setColumnName(bankAcceptanceColumn[i]);
			column.setName(bankAcceptanceColumn[i]);
			column.setTitle(bankAcceptanceColumnTitle[i]);
			column.setWidth(bankAcceptanceColumnWidth[i]);
			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			
			columnSet.add(column);
		}
		grid.setColumns(columnSet);
	//	Toolbar viewVoucher = new Toolbar();
		
	//	List toolbarList = new ArrayList();
	//	toolbarList.add(viewVoucher);
	//	grid.setToolbar(toolbarList);		
		setGrid(grid);
	}

}
