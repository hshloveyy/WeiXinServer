package com.infolion.xdss3.ageAnalysis.domain;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Toolbar;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ColumnEditor;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ComboBoxEditor;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

public class AgeAnalysisGrid extends GridTagHandler {
	
	private String ageAnalysisColumn[] = {
	        "mandt",           // 客户端       
	        "ageanalysisId",   // 账龄ID
	        "customerNo",      // 项目代码（实际上是"客户代码 "） 
	        "customerName",    // 项目名称（实际上是"客户名称 "）
	        "customerType",    // 类型（1为客户，2为供应商）
	        "bukrs",           // 公司代码        
	        "gsber",           // 业务范围    
	        "augdt",           // 分析日期
	        "hkont",           // 分析项目      
	        "subjectCode",     // 总账科目代码  
	        "subjectName",     // 总账科目名称 
	        "date1_3",         // 1~3月 
	        "date4_6",         // 4~6月  
	        "date7_12",        // 7~12月    
	        "year1_2",         // 1~2年   
	        "year2_3",         // 2~3年  
	        "year3_4",         // 3~4年  
	        "year4_5",         // 4~5年    
	        "year5_above",     // 5年以上
	        "total",	//小计
	        "isExceed",        // 是否含逾期  
	        "vbeltype"         // 业务类型 
			};
	private String ageAnalysisColumnTitle[] = {
	        "客户端",                       
	        "账龄ID",                        
	        "项目代码",         // 实际上是"客户代码 "                      
	        "项目名称",         // 实际上是"客户名称 "              
	        "类型",
	        "公司代码",                      
	        "业务范围",                     
	        "分析日期",                      
	        "分析项目",                      
	        "总账科目代码",                  
	        "总账科目名称",                  
	        "1~3月",                         
	        "4~6月",                         
	        "7~12月",                        
	        "1~2年",                         
	        "2~3年",                        
	        "3~4年",                         
	        "4~5年",                         
	        "5年以上", 
	        "合计",
	        "是否含逾期", 
	        "业务类型" 
			};
	
	private String ageAnalysisColumnWidth[] = {
	        "80", // 客户端
            "80", // 账龄ID
            "80", // 项目代码（实际上是"客户代码 "）
            "80", // 项目名称（实际上是"客户名称 "）
            "80", // 类型（1为客户，2为供应商）
            "80", // 公司代码
            "80", // 业务范围
            "80", // 分析日期
            "80", // 分析项目
            "80", // 总账科目代码
            "80", // 总账科目名称
            "80", // 1~3月
            "80", // 4~6月
            "80", // 7~12月
            "80", // 1~2年
            "80", // 2~3年
            "80", // 3~4年
            "80", // 4~5年
            "80", // 5年以上
            "80", // 合计
            "80", // 是否含逾期
            "80", // 业务类型
			};

	public AgeAnalysisGrid() {
		buildGrid();
	}

	public void buildGrid() {
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < ageAnalysisColumn.length; i++) {
			Column column = new Column();
			column.setColumnName(ageAnalysisColumn[i]);
			column.setName(ageAnalysisColumn[i]);
			column.setTitle(ageAnalysisColumnTitle[i]);
			column.setWidth(ageAnalysisColumnWidth[i]);
			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			if(ageAnalysisColumn[i].equals("mandt")){                // 客户端
			    column.setVisibility(false);
			}else if(ageAnalysisColumn[i].equals("ageanalysisId")){  // 账龄ID
				column.setVisibility(false);
			}else if(ageAnalysisColumn[i].equals("customerNo")){     // 项目代码（实际上是"客户代码 "）
			    column.setRenderer("_customerNoRender");
			}else if(ageAnalysisColumn[i].equals("customerType")){   // 类型（1为客户，2为供应商） 
			    column.setVisibility(false);
			}else if(ageAnalysisColumn[i].equals("augdt")){          // 分析日期
                column.setRenderer("_dateRender");
            }else if(ageAnalysisColumn[i].equals("isExceed")){       // 是否含逾期
                column.setRenderer("_isExceedRender");
            }else if(ageAnalysisColumn[i].contains("date") || ageAnalysisColumn[i].contains("year")){
                column.setIssum(true);
            }else if(ageAnalysisColumn[i].equals("hkont")){   //  
			    column.setVisibility(false);
			}else if(ageAnalysisColumn[i].equals("bukrs")){     //公司代码
			    column.setRenderer("_customerNoRender");
			}else if(ageAnalysisColumn[i].equals("total")){     // 合计
//				 column.setRenderer("_setTotal");
			    
			}/*else if(ageAnalysisColumn[i].equals("是否含逾期")){
				column.setColumnType("06");
				column.setColumnTypeCode("06");
				column.setDictTableName("YDYESORNO");
				ColumnEditor columnEditor = new ComboBoxEditor();
				columnEditor.setColumn(column);
				columnEditor.setEditable(false);
				columnEditor.setName(ageAnalysisColumn[i]);
				column.setColumnEditor(columnEditor);
			}else if(ageAnalysisColumn[i].equals("部门")){
				column.setVisibility(false);
//				column.setColumnType("string");
//				column.setColumnTypeCode("11");
//				SearchHelpFieldEditor searchHelpFieldEditor = new SearchHelpFieldEditor();
//				searchHelpFieldEditor.setBoName("AgeAnalysis");
//				searchHelpFieldEditor.setPropertyName("dept_id");
//				searchHelpFieldEditor.setShlpName("YHORGANIZATION");
//				searchHelpFieldEditor.setShlpIdColumnName("ORGANIZATIONID");
//				searchHelpFieldEditor.setShlpTextColumnName("ORGANIZATIONNAME");
//				column.setColumnEditor(searchHelpFieldEditor);
			}else if(ageAnalysisColumn[i].equals("业务状态")){
				column.setColumnType("06");
				column.setColumnTypeCode("06");
				column.setDictTableName("YDCOLLECTBUZSTATE");
				ColumnEditor columnEditor = new ComboBoxEditor();
				columnEditor.setColumn(column);
				columnEditor.setEditable(false);
				columnEditor.setName(ageAnalysisColumn[i]);
				column.setColumnEditor(columnEditor);
			}else if(ageAnalysisColumn[i].equals("收款方式")){
				column.setColumnType("06");
				column.setColumnTypeCode("06");
				column.setDictTableName("YDCOLLECTTYPE");
				ColumnEditor columnEditor = new ComboBoxEditor();
				columnEditor.setColumn(column);
				columnEditor.setEditable(false);
				columnEditor.setName(ageAnalysisColumn[i]);
				column.setColumnEditor(columnEditor);
			}else if(ageAnalysisColumn[i].equals("创建人")){
				column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
				column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
			}else if(ageAnalysisColumn[i].equals("最后修改者")){
				column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
				column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
			}else if(ageAnalysisColumn[i].contains("日")){
				column.setRenderer("_dateManager");
			}else if(ageAnalysisColumn[i].contains("外部纸质合同号")){
			    column.setVisibility(false);
			}*/
			columnSet.add(column);
		}
		grid.setColumns(columnSet);
		setGrid(grid);
	}

}
