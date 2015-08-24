package com.infolion.xdss3.profitLoss.web;

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
public class ProfitLossMatklGrid extends GridTagHandler{

    private String colums[] = { 
            "dept_id_text", "type","storagetime", "project_no", "meterial_name", "ekko_waers_name", 
            "totalvalue", "totalvalue2","maxlosscomment" };
	private String columnTitle[] = {
	        "部门", "业务类型","库龄(天)", "立项号", "物料组", "币别", 
	        "库存金额", "库存金额（本位币）", "备注"
	        };
	private String columnName[] = {
	        "dept_id_text", "type","storagetime", "project_no", "meterial_name", "ekko_waers_name", 
	        "totalvalue", "totalvalue2", "maxlosscomment" };
	
	public ProfitLossMatklGrid(){
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
			columnSet.add(column);
		}
		grid.setColumns(columnSet);
		
		setGrid(grid);
	}

}

