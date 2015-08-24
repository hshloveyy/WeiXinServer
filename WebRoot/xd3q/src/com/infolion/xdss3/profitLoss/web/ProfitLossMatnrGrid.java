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
public class ProfitLossMatnrGrid extends GridTagHandler{

    private String colums[] = {
            "datevalue", "companyid_text", "dept_id_text", "type", "project_no", 
            "contractno", "ekko_unsez", "orderid", "providername", "material_group_text", "material_code", 
            "meterial_name", "batch_no","storagetime", "unit", 
            "quantity", "unitprice", "totalvalue", "maxlosscomment" };
    
	private String columnTitle[] = {
	        "日期", "公司名称", "部门名称", "业务类型","立项号", 
	        "合同号", "外部纸质合同号", "采购订单", "供应商", "物料组名称", "物料号",
	        "物料名称", "批次","库龄(天)", "单位", 
	        "库存数量",  "采购成本单价(不含税)", "总金额（成本）", "备注"
	        };
	
    private String columnName[] = {
            "datevalue", "companyid_text", "dept_id_text", "type", "project_no", 
            "contractno", "ekko_unsez", "orderid", "providername", "material_group_text", "material_code", 
            "meterial_name", "batch_no","storagetime", "unit", 
            "quantity", "unitprice", "totalvalue", "maxlosscomment" };
	public ProfitLossMatnrGrid(){
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

