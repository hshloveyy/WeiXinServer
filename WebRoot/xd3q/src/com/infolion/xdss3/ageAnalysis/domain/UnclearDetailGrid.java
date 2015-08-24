package com.infolion.xdss3.ageAnalysis.domain;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

public class UnclearDetailGrid extends GridTagHandler {
	
	private String unclearDetailColumn[] = {
	        "mandt",            
	        "unclear_deailed_id",
	        "customerNo",        
	        "deptId",            
	        "deptName",          
	        "isclear",           
	        "projectNo",         
	        "projectName",       
	        "subjectCode",       
	        "subjectName",       
	        "analysisDate",      
	        "accountDate",       
	        "voucherNo",         
	        "voucher_amount",    
	        "voucher_bwbje",     
	        "bktxt",             
	        "offAmount",         
	        "offAmountBwbje",    
	        "projectNo2",        
	        "projectName2",      
	        "old_contract_no",   
	        "orderNo",           
	        "businessstate",     
	        "content",           
	        "voucher_currency",  
	        "wbje1_3_date",      
	        "bwbje1_3_date",     
	        "wbje4_6_date",      
	        "bwbje4_6_date",     
	        "wbje7_12_date",     
	        "bwbje7_12_date",    
	        "wbje1_2_year",      
	        "bwbje1_2_year",     
	        "wbje2_3_year",      
	        "bwbje2_3_year",     
	        "wbje3_4_year",      
	        "bwbje3_4_year",     
	        "wbje4_5_year",      
	        "bwbje4_5_year",     
	        "wbje5_year_above",  
	        "bwbje5_year_above", 
	        "total",	//外币小计
	        "total2",	//本位币小计
	        "expires_date",      
	        "isExceed",          
	        "exceed_time"        
			};
	private String unclearDetailColumnTitle[] = {
	        "客户端",            
            "ID",
            "客户代码",        
            "部门id",            
            "部门",          
            "清帐状态",           
            "项目代码",         
            "项目名称",       
            "总账科目代码",       
            "总账科目名称",       
            "分析日期",      
            "记账日期",       
            "财务凭证号",         
            "凭证金额",    
            "凭证金额(本位币)",     
            "文本",             
            "已清金额",         
            "已清金额（本位币）",    
            "项目代码2",        
            "项目名称2",      
            "纸质合同号",   
            "订单号",           
            "业务状态",     
            "经济内容",           
            "凭证货币",  
            "1~3月外币",      
            "1~3月本币",     
            "4~6月外币",      
            "4~6月本币",     
            "7~12月外币",     
            "7~12月本币",    
            "1~2年外币",      
            "1~2年本币",     
            "2~3年外币",      
            "2~3年本币",     
            "3~4年外币",      
            "3~4年本币",     
            "4~5年外币",      
            "4~5年本币",     
            "5年以上外币",  
            "5年以上本币", 
            "外币合计",
            "本位币合计",
            "到期日",      
            "是否含逾期",          
            "逾期时间" 
			};
	
	private String unclearDetailColumnWidth[] = {
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
	        "80" 
			};

	public UnclearDetailGrid() {
		buildGrid();
	}

	public void buildGrid() {
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < unclearDetailColumn.length; i++) {
			Column column = new Column();
			column.setColumnName(unclearDetailColumn[i]);
			column.setName(unclearDetailColumn[i]);
			column.setTitle(unclearDetailColumnTitle[i]);
			column.setWidth(unclearDetailColumnWidth[i]);
			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			if(unclearDetailColumn[i].equals("mandt")){                // 客户端
			    column.setVisibility(false);
			}else if(unclearDetailColumn[i].equals("unclear_deailed_id")){
			    column.setVisibility(false);
			}else if(unclearDetailColumn[i].equals("total")){     // 外币合计
//				 column.setRenderer("_setTotal");
			    
			}else if(unclearDetailColumn[i].equals("total2")){     // 本位币合计
//				 column.setRenderer("_setTotal2");
			    
			}/*else if(unclearDetailColumn[i].equals("是否含逾期")){unclear_deailed_id
				column.setColumnType("06");
				column.setColumnTypeCode("06");
				column.setDictTableName("YDYESORNO");
				ColumnEditor columnEditor = new ComboBoxEditor();
				columnEditor.setColumn(column);
				columnEditor.setEditable(false);
				columnEditor.setName(unclearDetailColumn[i]);
				column.setColumnEditor(columnEditor);
			}else if(unclearDetailColumn[i].equals("部门")){
				column.setVisibility(false);
//				column.setColumnType("string");
//				column.setColumnTypeCode("11");
//				SearchHelpFieldEditor searchHelpFieldEditor = new SearchHelpFieldEditor();
//				searchHelpFieldEditor.setBoName("UnclearDetail");
//				searchHelpFieldEditor.setPropertyName("dept_id");
//				searchHelpFieldEditor.setShlpName("YHORGANIZATION");
//				searchHelpFieldEditor.setShlpIdColumnName("ORGANIZATIONID");
//				searchHelpFieldEditor.setShlpTextColumnName("ORGANIZATIONNAME");
//				column.setColumnEditor(searchHelpFieldEditor);
			}else if(unclearDetailColumn[i].equals("业务状态")){
				column.setColumnType("06");
				column.setColumnTypeCode("06");
				column.setDictTableName("YDCOLLECTBUZSTATE");
				ColumnEditor columnEditor = new ComboBoxEditor();
				columnEditor.setColumn(column);
				columnEditor.setEditable(false);
				columnEditor.setName(unclearDetailColumn[i]);
				column.setColumnEditor(columnEditor);
			}else if(unclearDetailColumn[i].equals("收款方式")){
				column.setColumnType("06");
				column.setColumnTypeCode("06");
				column.setDictTableName("YDCOLLECTTYPE");
				ColumnEditor columnEditor = new ComboBoxEditor();
				columnEditor.setColumn(column);
				columnEditor.setEditable(false);
				columnEditor.setName(unclearDetailColumn[i]);
				column.setColumnEditor(columnEditor);
			}else if(unclearDetailColumn[i].equals("创建人")){
				column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
				column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
			}else if(unclearDetailColumn[i].equals("最后修改者")){
				column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
				column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
			}else if(unclearDetailColumn[i].contains("日")){
				column.setRenderer("_dateManager");
			}else if(unclearDetailColumn[i].contains("外部纸质合同号")){
			    column.setVisibility(false);
			}*/
			columnSet.add(column);
		}
		grid.setColumns(columnSet);
		setGrid(grid);
	}

}
