package com.infolion.xdss3.fundBill.collectBill;

import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;
import java.util.ArrayList;
import java.util.List;

public class CollectBillGrid extends GridTagHandler {
	
	private String collectBillColumn[] = {"billno","belnr", "iscleared", "collectno", "isclear","clearamount"};
	private String collectBillColumnTitle[] = {"发票号","发票凭证号", "发票结清标识", "收款单号", "收款结清标识","清账金额"};
	private String collectBillColumnName[] = {"billno","belnr", "iscleared", "collectno", "isclear","clearamount"};

	public CollectBillGrid() {
		buildGrid();
	}

	public void buildGrid() {
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < collectBillColumn.length; i++) {
			Column column = new Column();
			column.setColumnName(collectBillColumn[i]);
			column.setName(collectBillColumnName[i]);
			column.setTitle(collectBillColumnTitle[i]);
//			将"结清标识"、"票清款结标识"设置成字典表形式
//			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_COMBOBOX);
//			column.setDictTableName("YDBILLCLEAR");
			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			column.setWidth("150");
			if(collectBillColumn[i].equals("clearamount")){    
				 column.setRenderer("_clearamountRender");
			}
			columnSet.add(column);
		}
		grid.setColumns(columnSet);
		setGrid(grid);
	}

}
