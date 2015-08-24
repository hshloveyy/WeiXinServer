package com.infolion.xdss3.fundBill.paymentBill;

import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;
import java.util.ArrayList;
import java.util.List;

public class PaymentBillGrid extends GridTagHandler {
	
	private String paymentBillColumn[] = {"billno","belnr", "iscleared", "paymentno", "billisclear", "clearamount"};
	private String paymentBillColumnTitle[] = {"发票号","发票凭证号", "发票结清标识", "付款单号", "付款结清标识","清账金额"};
	private String paymentBillColumnName[] = {"billno","belnr", "iscleared", "paymentno", "billisclear","clearamount"};

	public PaymentBillGrid() {
		buildGrid();
	}

	public void buildGrid() {
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < paymentBillColumn.length; i++) {
			Column column = new Column();
			column.setColumnName(paymentBillColumn[i]);
			column.setName(paymentBillColumnName[i]);
			column.setTitle(paymentBillColumnTitle[i]);
//			将"结清标识"、"票清款结标识"设置成字典表形式
//			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_COMBOBOX);
//			column.setDictTableName("YDBILLCLEAR");
			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			column.setWidth("150");
			if(paymentBillColumn[i].equals("clearamount")){    
				 column.setRenderer("_clearamountRender");
			}
			columnSet.add(column);
		}
		grid.setColumns(columnSet);
		setGrid(grid);
	}

}
