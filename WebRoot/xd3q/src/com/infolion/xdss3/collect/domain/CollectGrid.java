package com.infolion.xdss3.collect.domain;

import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Toolbar;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ColumnEditor;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ComboBoxEditor;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

public class CollectGrid extends GridTagHandler {
	
	private String collectColumn[] = {
			"collectid",
			"businessstate",
			"collectno",
			"name1",
			"customer", 
			"incsuretybond",
			"applyamount", 
			"currency", 
			"billcurrency", 
			"convertamount",
			"voucherdate", 
			"accountdate", 
			"settlerate", 
			"draft", 
			"draftdate", 
			"sendgoodsdate",
			"billcheckdate", 
			"goodsamount", 
			"replaceamount", 
			"supplieramount",
			"dept_id", 
			"text", 
			"remark",
			
			"processstate",
			"creator", 
			"createtime", 
			"lastmodifyer", 
			"trade_type",
			
			"oldcollectno", 
			"oldcontract_no",
			"oldproject_no", 
			"remainsuretybond", 
			"actamount", 
			"actcurrency", 
			"collectrate",
			//-----------
			"mandt",
			"unnamercollectid",
			"oldcollectitemid",
			"oldcollectid",
			"export_apply_no",
			"lastmodifytime",
			"collecttype"
			};
	private String collectColumnTitle[] = {
			"操作",
			"业务状态",  
			"收款单号",
			"客户名称",
			"客户",							
			"是否包含保证金",				
			"申请收款金额",	
			"币别",    
			"开票币别",						
			"折算金额/保证金转货款金额",	
			"凭证日期",						
			"记账日期",	
			"结算汇率",						
			"单据号码",						
			"银行承兑汇票/国内信用证到期日",
			"预计发货日",					
			"应开票日",						
			"清放货额度金额（本位币）",		
			"清代垫额度（本位币）",			
			"清供应商额度（本位币）",		
			"部门",							
			"抬头文本（收款用途）",			
			"备注",							
								
			"流程状态",						
			"创建人",						
			"创建日期",						
			"最后修改者",					
			"贸易方式",						
									
			"原收款单号",					
			"外部纸质合同号",				
			"原项目编号",					
			"剩余保证金",					
			"实际收款金额",					
			"实际收款币别",					
			"收款汇率",	
			//-----------
			"客户端", 
			"未名户收款", 
			"原收款单行项目",
			"原收款单ID", 
			"出单发票号", 
			"最后修改日期", 
			"收款方式"
			};
	private String collectColumnName[] = {
			"collectid",
			"businessstate", 
			"collectno", 
			"name1",
			"customer", 
			"incsuretybond",
			"applyamount",
			"currency", 
			"billcurrency", 
			"convertamount",
			"voucherdate", 
			"accountdate", 
			"settlerate", 
			"draft", 
			"draftdate", 
			"sendgoodsdate",
			"billcheckdate", 
			"goodsamount", 
			"replaceamount", 
			"supplieramount",
			"dept_id", 
			"text", 
			"remark",
			
			"processstate",
			"creator", 
			"createtime", 
			"lastmodifyer", 
			"trade_type",
			
			"oldcollectno", 
			"oldcontract_no",
			"oldproject_no", 
			"remainsuretybond", 
			"actamount", 
			"actcurrency", 
			"collectrate",
			//-----------
			"client",
			"unnamercollectid",
			"oldcollectitemid",
			"oldcollectid",
			"export_apply_no",
			"lastmodifytime",
			"collecttype"
			};
	
	private String collectColumnWidth[] = {
			"110",	// 操作
			"80",   // 业务状态 
			"100",	// 收款单号
			"150", 	// 客户名称 
			"100",	// 客户						
			"50",	// 是否包含保证金			
			"90",	// 申请收款金额
			"80",    // 币别       
			"60",	// 开票币别
			"90",	// 折算金额/保证金转货款金额
			"80",	// 凭证日期			
			"80",	// 记账日期	
			"60",	// 结算汇率
			"100",	// 单据号码
			"80",	// 银行承兑汇票/国内信用证到期日
			"80",	// 预计发货日
			"80",	// 应开票日
			"80",	// 清放货额度金额（本位币）
			"80",	// 清代垫额度（本位币）			
			"80",	// 清供应商额度（本位币）
			"200",	// 部门				
			"80",	// 抬头文本（收款用途）
			"80",	// 备注					
						
			"80",	// 流程状态				
			"80",	// 创建人				
			"80",	// 创建日期				
			"80",	// 最后修改者			
			"80",	// 贸易方式				
						
			"80",	// 原收款单号			
			"80",	// 外部纸质合同号		
			"80",	// 原项目编号			
			"80",	// 剩余保证金			
			"80",	// 实际收款金额			
			"80",	// 实际收款币别			
			"80",	// 收款汇率
			//-----------
			"80", 	// 客户端
			"80", 	// 未名户收款
			"80",	// 原收款单行项目
			"80", 	// 原收款单ID
			"80", 	// 出单发票号
			"80", 	// 最后修改日期
			"80"	// 收款方式
			};

	public CollectGrid() {
		buildGrid();
	}

	public void buildGrid() {
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < collectColumn.length; i++) {
			Column column = new Column();
			column.setColumnName(collectColumn[i]);
			column.setName(collectColumnName[i]);
			column.setTitle(collectColumnTitle[i]);
			column.setWidth(collectColumnWidth[i]);
			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			if(collectColumnTitle[i].equals("操作")){
				column.setRenderer("_manager");
			}else if(collectColumnTitle[i].equals("客户端")){
			    column.setVisibility(false);
			}else if(collectColumnTitle[i].equals("收款ID")){
				column.setVisibility(false);
			}else if(collectColumnTitle[i].equals("原收款单行项目")){
				column.setVisibility(false);
			}else if(collectColumnTitle[i].equals("未名户收款")){
				column.setVisibility(false);
			}else if(collectColumnTitle[i].equals("原收款单ID")){
				column.setVisibility(false);
			}else if(collectColumnTitle[i].equals("贸易方式")){
				column.setVisibility(false);
			}else if(collectColumnTitle[i].equals("原收款单号")){
				column.setVisibility(false);
			}else if(collectColumnTitle[i].equals("原项目编号")){
				column.setVisibility(false);
			}else if(collectColumnTitle[i].equals("是否包含保证金")){	// 设置成字典表
				column.setColumnType("06");
				column.setColumnTypeCode("06");
				column.setDictTableName("YDYESORNO");
				ColumnEditor columnEditor = new ComboBoxEditor();
				columnEditor.setColumn(column);
				columnEditor.setEditable(false);
				columnEditor.setName(collectColumn[i]);
				column.setColumnEditor(columnEditor);
			}else if(collectColumnTitle[i].equals("部门")){
				column.setVisibility(false);
//				column.setColumnType("string");
//				column.setColumnTypeCode("11");
//				SearchHelpFieldEditor searchHelpFieldEditor = new SearchHelpFieldEditor();
//				searchHelpFieldEditor.setBoName("Collect");
//				searchHelpFieldEditor.setPropertyName("dept_id");
//				searchHelpFieldEditor.setShlpName("YHORGANIZATION");
//				searchHelpFieldEditor.setShlpIdColumnName("ORGANIZATIONID");
//				searchHelpFieldEditor.setShlpTextColumnName("ORGANIZATIONNAME");
//				column.setColumnEditor(searchHelpFieldEditor);
			}else if(collectColumnTitle[i].equals("业务状态")){
				column.setColumnType("06");
				column.setColumnTypeCode("06");
				column.setDictTableName("YDCOLLECTBUZSTATE");
				ColumnEditor columnEditor = new ComboBoxEditor();
				columnEditor.setColumn(column);
				columnEditor.setEditable(false);
				columnEditor.setName(collectColumn[i]);
				column.setColumnEditor(columnEditor);
			}else if(collectColumnTitle[i].equals("收款方式")){
				column.setColumnType("06");
				column.setColumnTypeCode("06");
				column.setDictTableName("YDCOLLECTTYPE");
				ColumnEditor columnEditor = new ComboBoxEditor();
				columnEditor.setColumn(column);
				columnEditor.setEditable(false);
				columnEditor.setName(collectColumn[i]);
				column.setColumnEditor(columnEditor);
			}else if(collectColumnTitle[i].equals("创建人")){
				column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
				column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
			}else if(collectColumnTitle[i].equals("最后修改者")){
				column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
				column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
			}else if(collectColumnTitle[i].contains("日")){
				column.setRenderer("_dateManager");
			}else if(collectColumnTitle[i].contains("外部纸质合同号")){
			    column.setVisibility(false);
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
