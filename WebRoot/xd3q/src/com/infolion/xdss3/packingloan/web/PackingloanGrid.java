package com.infolion.xdss3.packingloan.web;

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
public class PackingloanGrid extends GridTagHandler{

    private String colums[] = {"packingid","packing_no","dept_name", "currency","applyamount","creditno","createbank","contractno","dealine","mature","dealdate","interestrate","packingreate","clearvendorvalue","repackingreate","voucherdate","accountdate","text","revoucherdate","reaccountdate","retext","remark","creator","createtime","creditrecdate","businessstate","processstate","lastmodifytime"};
	private String columnTitle[] = {"操作", "打包贷款单号","部门", "货币","申请金额","信用证号","开证行","关联合同号" ,"打包到期日" ,"打包期限","应收款日","打包利率","打包汇率","扣含税利润金额（清供应商额度）（本位币）","还打包汇率" ,"凭证日期","记账日期","打包贷款文本","还款凭证日期","还款记帐日期","还打包贷款文本" ,"备注","创建人" ,"创建日期","到证日期(到证)","业务状态" ,"流程状态" ,"最后修改日期"};
	private String columnName[] = {"packingid","packing_no","dept_name", "currency","applyamount","creditno","createbank","contractno","dealine","mature","dealdate","interestrate","packingreate","clearvendorvalue","repackingreate","voucherdate","accountdate","text","revoucherdate","reaccountdate","retext","remark","creator","createtime","creditrecdate","businessstate","processstate","lastmodifytime"};
	
	public PackingloanGrid(){
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

