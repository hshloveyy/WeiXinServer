/**
 * 
 */
package com.infolion.xdss3.cashFlow.domain;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

/**
 * @author 钟志华
 *
 */
public class CashFlowGrid extends GridTagHandler {
	
	private String cashFlowColumn[] = {	
			"bukrs",
			"bname",
			"bussinessType",
			"gsber",
			"kunnr",
			"customerName",
			"collectno",
			"paymentno",
			"refmentno",
			"lifnr",
			"supplierName",
			"belnr",
			"budat",
			"augdt",
			"currency",
			"dmbtr",
			"wrbtr",
			"onroadamount",
			"onroadamountBwb",
			"sgtxt", 
			"rstgr",
			"txt40",
			"hkont",			
			"subjectCode"
			};
	private String cashFlowColumnTitle[] = {	         
			"公司代码",
			 "立项号"  ,
			 "业务类型",
			 "业务范围" ,
			 "客户编号" ,
			 "客户名称"	,
			 "收款单号" ,
			 "付款单号" ,
			 "退款单号" ,
			 "供应商或债权人的帐号" ,
			 "供应商名称" ,
			 "会计凭证编号"  ,
			 "凭证中的过帐日期" ,
			 "申请日期",
			 "币别",
			 "按本位币计的金额" ,
			 "凭证贷币金额" ,
			 "在批金额" ,
			 "在批本位金额" ,
			 "项目文本" ,
			 "原因代码",
			 "原因代码",
			 "总分类帐帐目",			 
			 "对方科目"
			};
	
	private String cashFlowColumnWidth[] = {
			 	"80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "80",
		        "250"
			};

	public CashFlowGrid() {
		buildGrid();
	}

	public void buildGrid() {
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < cashFlowColumn.length; i++) {
			Column column = new Column();
			column.setColumnName(cashFlowColumn[i]);
			column.setName(cashFlowColumn[i]);
			column.setTitle(cashFlowColumnTitle[i]);
			column.setWidth(cashFlowColumnWidth[i]);
			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			
			columnSet.add(column);
		}
		grid.setColumns(columnSet);
		setGrid(grid);
	}


}
