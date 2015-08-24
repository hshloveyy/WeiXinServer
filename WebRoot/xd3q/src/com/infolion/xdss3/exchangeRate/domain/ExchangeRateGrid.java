package com.infolion.xdss3.exchangeRate.domain;



import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Toolbar;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ColumnEditor;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ComboBoxEditor;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

public class ExchangeRateGrid extends GridTagHandler {
	
	private String exchangeRateColumn[] = {
	        "client",           // 客户端       
	        "id",
	        "rate_date",
	        "currency",
	        "currency2",
	        "buying_rate",
	        "selling_rate",
	        "middle_rate"
			};
	private String exchangeRateColumnTitle[] = {
	        "客户端",                       
	        "操作",
	        "汇率时间",
	        "币别",
	        "本位币币别",
	        "买入价",
	        "卖出价",
	        "中间价"	        
			};
	
	private String exchangeRateColumnWidth[] = {
	        "80", // 客户端
	        "110",
	        "100",
	        "100",
	        "100",
	        "180",
	        "180",
	        "180"
			};

	public ExchangeRateGrid() {
		buildGrid();
	}

	public void buildGrid() {
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < exchangeRateColumn.length; i++) {
			Column column = new Column();
			column.setColumnName(exchangeRateColumn[i]);
			column.setName(exchangeRateColumn[i]);
			column.setTitle(exchangeRateColumnTitle[i]);
			column.setWidth(exchangeRateColumnWidth[i]);
			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			if(exchangeRateColumn[i].equals("client")){                // 客户端
			    column.setVisibility(false);
			}else if(exchangeRateColumn[i].equals("id")){
				column.setRenderer("_manager");
			}
			columnSet.add(column);
		}
		grid.setColumns(columnSet);
		Toolbar viewVoucher = new Toolbar();
		viewVoucher.setHandler("_create");
		viewVoucher.setTitle("创建");
		viewVoucher.setIconCls("icon-view");
		List toolbarList = new ArrayList();
		toolbarList.add(viewVoucher);
		grid.setToolbar(toolbarList);		
		setGrid(grid);
	}

}
