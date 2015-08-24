package com.infolion.xdss3.payment.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.infolion.platform.dpframework.uicomponent.grid.ColumnUITypeRule;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Column;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.grid.domain.Toolbar;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ColumnEditor;
import com.infolion.platform.dpframework.uicomponent.grid.editor.ComboBoxEditor;
import com.infolion.platform.dpframework.uicomponent.grid.editor.SearchHelpFieldEditor;
import com.infolion.platform.dpframework.uicomponent.grid.web.GridTagHandler;

public class ImportPaymentGrid extends GridTagHandler {
	
    protected String boName = "ImportPayment";
    
	private String homePaymentColumn[] = {
	        "paymentid", "businessstate", "paymentno", "supplier_text", "supplier",
            "paymentstate", "applyamount", "currency_text", "currency", "collectbankid", "payer",
            "dept_id_text", "dept_id", "arrivegoodsdate", "accountdate", "voucherdate", "paydate",
            "factamount", "factcurrency_text", "factcurrency", "pay_type", "collectbankacc",
            "istradesubsist", "musttaketickleda", "finumber", "isoverrepay", "creator",
            "paymenttype", "expiredate", "billbc", "documentarydate",
            "documentarylimit", "payrealdate", "doctaryreallimit", "doctaryinterest", "redocarybc",
            "redocarybc_text", "redocaryrate", "redocaryamount", "collectbanksubje", "repaymentid",
            "replacedate", "draftdate", "draft", "ticketbanksubjec", "ticketbankname",
            "ticketbankid", "closeexchangerat", "representpaycust", "isrepresentpay",
            "collectbankname", "lastmodifytime", "lastmodifyer", "createtime", "processstate",
            "factexchangerate", "exchangerate", "remark", "text", "writelistno",
            "doctaryrealrate", "payrealamount", "documentaryrate", "documentarypaydt", "currency2",
            "trade_type", "mandt" };
	
    private String homePaymentColumnTitle[] = {
            "操作", "业务状态", "付款单号", "供应商名称", "供应商", "付款单状态", 
            "申请金额", "币别名称", "币别", "收款 银行", "付款人", 
            
            "部门名称", "部门", "预计到货日", "记账日期", "凭证日期", "银行对外付款日",
            "实际付款金额", "实际币别名称", "实际币别", "付款类型", "收款银行账号", 
            
            "是否贸管预付款", "应收票日", "财务编号", "是否海外代付", "创建人", 
            "付款方式", "付款基准日", "票据业务范围", 
            
            "押汇日(押汇)", "押汇期限", "实际付款日期（押汇）", "实际押汇期限", "押汇利率",
            "押汇业务范围", "押汇业务范围名称", "还押汇汇率", "还押汇金额", "收款银行科目", "被重做付款ID", 
            
            "代垫到期日", "银行承兑汇票/国内信用证到期日", "单据号码", "票据做账银行科目", "票据做账银行", 
            "票据银行(国内证)", "结算汇率", "纯代理付款客户", "是否纯代理付款", "收款银行", 
            
            "最后修改日期", "最后修改者", "创建日期", "流程状态", 
            "实际汇率", "付款汇率", "备注", "抬头文本", "核销单号",
            
            "实际押汇汇率", "实际付款金额（押汇）", "押汇汇率", "押汇到期付款日", "押汇币别", 
            "贸易方式", "客户端" };

    private String name[] = { "paymentid", "businessstate", "paymentno", "supplier_text", "supplier", "paymentstate",
            "applyamount", "currency_text", "currency", "collectbankid", "payer", "dept_id_text",
            "dept_id", "arrivegoodsdate", "accountdate", "voucherdate", "paydate","factamount",
            "factcurrency_text", "factcurrency", "pay_type", "collectbankacc", "istradesubsist",
            "musttaketickleda", "finumber", "isoverrepay", "creator", "paymenttype", "expiredate",
            "billbc", "documentarydate", "documentarylimit", "payrealdate",
            "doctaryreallimit", "doctaryinterest", "redocarybc", "redocarybc_text", "redocaryrate",
            "redocaryamount", "collectbanksubje", "repaymentid", "replacedate", "draftdate",
            "draft", "ticketbanksubjec", "ticketbankname", "ticketbankid", "closeexchangerat",
            "representpaycust", "isrepresentpay", "collectbankname", "lastmodifytime",
            "lastmodifyer", "createtime", "processstate", "factexchangerate",
            "exchangerate", "remark", "text", "writelistno", "doctaryrealrate", "payrealamount",
            "documentaryrate", "documentarypaydt", "currency2", "trade_type", "client" };

    private String columnWidth[] = {
            "100", // "操作"
            "80", // "业务状态"
            "100", // "付款单号"
            "100", // "供应商名称"
            "80", // "供应商"
            "80", // "付款单状态"

            "80", // "申请金额"
            "80", // "币别名称"
            "80", // "币别"
            "80", // "收款银行"
            "80", // "付款人"


            "60", // "部门名称"
            "80", // "部门"
            "80", // "预计到货日"
            "80", // "记账日期"
            "80", // "凭证日期"
            "90", // "银行对外付款日"
            
            "80", // "实际付款金额"
            "80", // "实际币别名称"
            "80", // "实际币别"
            "80", // "付款类型"
            "80", // "收款银行账号"


            "80", // "是否贸管预付款"
            "80", // "应收票日"
            "80", // "财务编号"
            "80", // "是否海外代付"
            "80", // "创建人"

            "80", // "付款方式"
            "80", // "付款基准日"
            "80", // "票据业务范围"
            //"80", // "付款ID"
            


            "80", // "押汇日(押汇)"
            "80", // "押汇期限"
            "80", // "实际付款日期（押汇）"
            "80", // "实际押汇期限"
            "80", // "押汇利率"

            "80", // "押汇业务范围"
			"80", // "押汇业务范围名称"
            "80", // "还押汇汇率"
            "80", // "还押汇金额"
            "80", // "收款银行科目"
            "80", // "被重做付款ID"


            "80", // "代垫到期日"
            "80", // "银行承兑汇票/国内信用证到期日"
            "80", // "单据号码"
            "80", // "票据做账银行科目"
            "80", // "票据做账银行"

            "80", // "票据银行(国内证)"
            "80", // "结算汇率"
            "80", // "纯代理付款客户"
            "80", // "是否纯代理付款"
            "80", // "收款银行"


            "80", // "最后修改日期"
            "80", // "最后修改者"
            "80", // "创建日期"
            "80", // "流程状态"
            "80", // "实际汇率"
            "80", // "付款汇率"
            "80", // "备注"
            "80", // "抬头文本"
            "80", // "核销单号"


            "80", // "实际押汇汇率"
            "80", // "实际付款金额（押汇）"
            "80", // "押汇汇率"
            "80", // "押汇到期付款日"
            "80", // "押汇币别"

            "80", // "贸易方式"
            "80", // "客户端" 
            };
    
	public ImportPaymentGrid() {
		buildGrid();
	}

	public void buildGrid() {
	    // 判断是否在name[]存在
        List showColumn = Arrays.asList("操作", "部门名称", "记账日期", "凭证日期", "实际付款金额", "实际币别名称", "业务状态",
                "创建人", "创建日期", "付款类型","银行对外付款日",
                //"是否贸管预付款", 
                "是否纯代理付款", "收款 银行", "付款单号", "供应商名称", "币别名称");
	    
		Grid grid = new Grid();
		List<Column> columnSet = new ArrayList<Column>();
		for (int i = 0; i < homePaymentColumn.length; i++) {
			Column column = new Column();
			column.setColumnName(homePaymentColumn[i]);
			column.setName(name[i]);
			column.setTitle(homePaymentColumnTitle[i]);
			column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			column.setWidth(columnWidth[i]);
			
			if (showColumn.contains(homePaymentColumnTitle[i])) {
			    column.setVisibility(true);
			} else {
			    column.setVisibility(false);
			}
			if ( "paymenttype".equalsIgnoreCase(name[i]) ) {
			    column.setColumnType("06");
                column.setColumnTypeCode("06");
                column.setDictTableName("YDPAYTRADETYPE");
                ColumnEditor columnEditor = new ComboBoxEditor();
                columnEditor.setColumn(column);
                columnEditor.setEditable(false);
                columnEditor.setName(homePaymentColumn[i]);
                column.setColumnEditor(columnEditor);
//			} else if ("supplier".equalsIgnoreCase(name[i])) {
//                column.setColumnType(ColumnUITypeRule.TYPE_CODE_SEARCHHELPFIELD);
//                column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_SEARCHHELPFIELD);
//                SearchHelpFieldEditor shlpcolumnEditor = new SearchHelpFieldEditor();
//                shlpcolumnEditor.setBoName("ImportPayment");
//                shlpcolumnEditor.setPropertyName("supplier");
//                shlpcolumnEditor.setShlpName("YHGETLIFNR");
//                shlpcolumnEditor.setShlpIdColumnName("LIFNR");
//                shlpcolumnEditor.setShlpTextColumnName("NAME1");
//                column.setColumnEditor(shlpcolumnEditor);
			    
            } else if ("creator".equalsIgnoreCase(name[i])) {
                column.setColumnType(ColumnUITypeRule.TYPE_CODE_USER);
                column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_USER);
                column.setDictTableName("YUSER");
//                ColumnEditor columnEditor = new ComboBoxEditor();
//                columnEditor.setColumn(column);
//                columnEditor.setEditable(false);
//                columnEditor.setName(homePaymentColumn[i]);
//                column.setColumnEditor(columnEditor);
			} else if ("paymentstate".equalsIgnoreCase(name[i])) {
			    column.setColumnType("06");
			    column.setColumnTypeCode("06");
			    column.setDictTableName("YDPAYMENTSTATE");
			    ColumnEditor columnEditor = new ComboBoxEditor();
			    columnEditor.setColumn(column);
			    columnEditor.setEditable(false);
			    columnEditor.setName(homePaymentColumn[i]);
			    column.setColumnEditor(columnEditor);
			} else if ("pay_type".equalsIgnoreCase(name[i])) {
			    column.setColumnType(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			    column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			    column.setDictTableName("YDPAYTYPE");
			    ColumnEditor columnEditor = new ComboBoxEditor();
			    columnEditor.setColumn(column);
			    columnEditor.setEditable(false);
			    columnEditor.setName(homePaymentColumn[i]);
			    column.setColumnEditor(columnEditor);
			} else if ("istradesubsist".equalsIgnoreCase(name[i])) {
			    column.setColumnType(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			    column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			    column.setDictTableName("YDPAYMENTYESORNO");
			    ColumnEditor columnEditor = new ComboBoxEditor();
			    columnEditor.setColumn(column);
			    columnEditor.setEditable(false);
			    columnEditor.setName(homePaymentColumn[i]);
			    column.setColumnEditor(columnEditor);
			} else if ("isoverrepay".equalsIgnoreCase(name[i])) {
			    column.setColumnType(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			    column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			    column.setDictTableName("YDPAYMENTYESORNO");
			    ColumnEditor columnEditor = new ComboBoxEditor();
			    columnEditor.setColumn(column);
			    columnEditor.setEditable(false);
			    columnEditor.setName(homePaymentColumn[i]);
			    column.setColumnEditor(columnEditor);
			} else if ("isrepresentpay".equalsIgnoreCase(name[i])) {
			    column.setColumnType(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			    column.setColumnTypeCode(ColumnUITypeRule.TYPE_CODE_TEXTFIELD);
			    column.setDictTableName("YDPAYMENTYESORNO");
			    ColumnEditor columnEditor = new ComboBoxEditor();
			    columnEditor.setColumn(column);
			    columnEditor.setEditable(false);
			    columnEditor.setName(homePaymentColumn[i]);
			    column.setColumnEditor(columnEditor);
			} else if ("businessstate".equalsIgnoreCase(name[i])) {
			    column.setColumnType("06");
                column.setColumnTypeCode("06");
                column.setDictTableName("YDPAYMENTBUZSTATE");
                ColumnEditor columnEditor = new ComboBoxEditor();
                columnEditor.setColumn(column);
                columnEditor.setEditable(false);
                columnEditor.setName(homePaymentColumn[i]);
                column.setColumnEditor(columnEditor);
			}
			// 操作列填入paymentid
			if (i==0) {
			    column.setRenderer("operaRD");
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

    public String getBoName() {
        return boName;
    }

    public void setBoName(String boName) {
        this.boName = boName;
    }

}
