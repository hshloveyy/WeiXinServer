package com.infolion.xdss3.unclearQuery.domain;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Toolbar;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ColumnEditor;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ComboBoxEditor;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

public class UnclearCustomerQueryGrid extends GridTagHandler {
	
	private String unclearCustomerColumn[] = {
	        "mandt",           // 客户端       
	        "customerTitleId",   // ID
	        "iscleared",         // 清账标识  	        
	        "budat",           // 过账日期
	        "belnr",           // 凭证号   	       
	        "gjahr",     // 会计年度
	        "shkzg",         // 借贷方
	        "waers",         // 币别
	        "rat",			//汇率
	        "bktxt",        // 摘要
	        "dmbtr",         // 金额   
	        "bwbje",         // 本位币
	      
	        "unAmount",		//未清金额
	        "unbwbje", //未清本位币金额	
	        "offAmount",		//已清金额
	        "offbwbje", //已清本位币金额	
	        "buzei",     // 行项目
	        "kunnr",      // 客户代码  	        
	        "bukrs",           // 公司代码        
	        "gsber",           // 业务范围 
	        "saknr"        // 总账科目	 
			};
	private String unclearCustomerColumnTitle[] = {
	        "客户端",                       
	        "ID",                        
	        "清账标识",           
	        "过账日期",                     
	        "凭证号",  
	        "会计年度",                  
	        "借贷方",                  
	        "币别",   
	        "汇率",
	        "摘要",                         
	        "金额",                        
	        "本位币",  
	        
	        "余额外币",
	        "余额本币",	
	        "已清金额",
	        "已清本位币金额",
	        "行项目",             
	        "客户代码",                  
	        "公司代码",
	        "业务范围",  
	        "总账科目"
			};
	
	private String unclearCustomerColumnWidth[] = {
	        "80", // 
            "80", // 
            "80", // 
            "80", //
            "80", //
            "80", // 
            "80", //
            "80", //
            "80", //
            "80", //
            "80", // 
            "80", // 
            "80", // 
            "80", // 
            "80", // 
            "80", // 
            "80", // 
            "80", // 
            "80", // 
            "80", // 
            "80"
			};

	public UnclearCustomerQueryGrid() {
		buildGrid();
	}

	public void buildGrid() {
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < unclearCustomerColumn.length; i++) {
			Column column = new Column();
			column.setColumnName(unclearCustomerColumn[i]);
			column.setName(unclearCustomerColumn[i]);
			column.setTitle(unclearCustomerColumnTitle[i]);
			column.setWidth(unclearCustomerColumnWidth[i]);
			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			if(unclearCustomerColumn[i].equals("mandt")){                // 客户端
			    column.setVisibility(false);
			}else if(unclearCustomerColumn[i].equals("customerTitleId")){  // 
				column.setVisibility(false);
			}else if(unclearCustomerColumn[i].contains("date") || unclearCustomerColumn[i].contains("year")){
                column.setIssum(true);
            }else if(unclearCustomerColumn[i].equals("iscleared")){     // 
				 column.setRenderer("_isClearRender");
            }else if(unclearCustomerColumn[i].equals("shkzg")){     // 
				 column.setRenderer("_shkzgRender");
            }else if(unclearCustomerColumn[i].equals("offAmount")){     // 
				 column.setRenderer("_offAmountRender");
            }else if(unclearCustomerColumn[i].equals("offbwbje")){     // 
				 column.setRenderer("_offbwbjeRender");
            }
			columnSet.add(column);
		}
		grid.setColumns(columnSet);
		setGrid(grid);
	}

}
