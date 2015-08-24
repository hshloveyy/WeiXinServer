package com.infolion.xdss3.vatdetail.web;

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
 * 增值税商品明细汇总表
 */

public class VatGoodDetailGrid extends GridTagHandler {

    private String colums[] = {
            "bukrs",
            "gsber",
            "matnr",
            "matnr_name",
            "matgroup",

            "matgroup_name",
            "taxp",
            "saptype",
            "qcyjcwdp",
            "qcydpwjc",

            "qcydswjc",
            "qcysckts",
            "bqrkje",
            "bqjyje",
            "inputtax",

            "inputurn",
            "taxturn",
            "outboundm",
            "busin",
            "salesin",

            "outputtax",
            "buscost",
            "salescost",
            "exporttax",
            "accexport",

            "qmyjcwdp",
            "qmydpwjc",
            "qmydswjc",
            "qmysckts"
    };
    private String columnTitle[] = {
            "公司代码",
            "部门",
            "物料号",
            "物料名称",
            "物料组编码",

            "物料组名称",
            "税率",
            "业务类型",
            "期初已进仓未到票",
            "期初已到票未进仓(货值)",

            "期初已到税票未进仓(税额)",
            "期初应收出口退税",
            "本期入库金额",
            "本期校验金额",
            "进项税额",

            "进项转出",
            "进项合计",
            "本期出库金额",
            "其他主营收入",
            "开票收入",

            "销项税额",
            "其他主营成本",
            "开票成本",
            "出口退税",
            "收出口退税",

            "期末已进仓未到票",
            "期末已到票未进仓(货值)",
            "期末已到票未进仓(税额)",
            "期末应收出口退税"
    };
    private String columnName[] = {
            "bukrs",
            "gsber",
            "matnr",
            "matnr_name",
            "matgroup",

            "matgroup_name",
            "taxp",
            "saptype",
            "qcyjcwdp",
            "qcydpwjc",

            "qcydswjc",
            "qcysckts",
            "bqrkje",
            "bqjyje",
            "inputtax",

            "inputurn",
            "taxturn",
            "outboundm",
            "busin",
            "salesin",

            "outputtax",
            "buscost",
            "salescost",
            "exporttax",
            "accexport",

            "qmyjcwdp",
            "qmydpwjc",
            "qmydswjc",
            "qmysckts"
    };

	public VatGoodDetailGrid() {
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
            
//            if ("creator".equalsIgnoreCase(colums[i])) {
//                column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
//                column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
//                column.setDictTableName("YUSER");
//            } else if ("lastmodiyer".equals(colums[i])) {
//                column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
//                column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
//            } else if ("businessstate".equalsIgnoreCase(colums[i])) {
//                column.setColumnType("06");
//                column.setColumnTypeCode("06");
//                column.setDictTableName("YDPAYMENTBUZSTATE");
//                ColumnEditor columnEditor = new ComboBoxEditor();
//                columnEditor.setColumn(column);
//                columnEditor.setEditable(false);
//                column.setColumnEditor(columnEditor);
//            }
            
            columnSet.add(column);
        }
        grid.setColumns(columnSet);
        
//      "FLAG ='4' 期末税金明细
//      "FLAG ='3' 期末入库明细
//      "FLAG ='2' 期初税金明细
//      "FLAG ='1' 期初入库明细
        
        Toolbar endvat = new Toolbar();
        List toolbarList = new ArrayList();
        endvat.setHandler("_view4");
        endvat.setTitle("期末税金明细");
        endvat.setIconCls("icon-view");
        toolbarList.add(endvat);
        
        endvat = new Toolbar();
        endvat.setHandler("_view3");
        endvat.setTitle("期末入库明细");
        endvat.setIconCls("icon-view");
        toolbarList.add(endvat);
        
        endvat = new Toolbar();
        endvat.setHandler("_view2");
        endvat.setTitle("期初税金明细");
        endvat.setIconCls("icon-view");
        toolbarList.add(endvat);
        
        endvat = new Toolbar();
        endvat.setHandler("_view1");
        endvat.setTitle("期初入库明细");
        endvat.setIconCls("icon-view");
        toolbarList.add(endvat);
        
        
        
        grid.setToolbar(toolbarList);
        
        
        setGrid(grid);
    
        
    }
}
