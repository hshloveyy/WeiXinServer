package com.infolion.sapss.payment.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import com.infolion.platform.component.ExtComponentServlet;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.payment.dao.PaymentContractJdbcDao;
import com.infolion.sapss.payment.domain.TPaymentInnerInfo;
import com.infolion.sapss.workflow.ProcessCallBack;
@Service
public class PaymentInnerInfoJDBCService extends BaseJdbcService implements ProcessCallBack {
	@Autowired
	private PaymentContractJdbcDao paymentContractJdbcDao;
	
	public void setPaymentContractJdbcDao(
			PaymentContractJdbcDao paymentContractJdbcDao) {
		this.paymentContractJdbcDao = paymentContractJdbcDao;
	}

	public void updateBusinessRecord(String businessRecordId,
			String examineResult, String sapOrderNo) {
		// TODO Auto-generated method stub
		
	}
	
	public Map<String, String> getParameterMap(HttpServletRequest request)
	{
		// 贸易类型
		Map<String, String> parameter = new HashMap<String,String>();
		Map<?, ?> parameterMap = request.getParameterMap();
		parameter = this.getParameterMap(parameterMap);
		return parameter;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param sql
	 * @param gridColumns
	 * @param gridSize
	 * @throws IOException
	 * @throws ServletException
	 */
	public void queryProcess(HttpServletRequest request, HttpServletResponse response,String sql,String gridColumns,String gridSize) throws IOException, ServletException
	{
		request.setAttribute("grid_sql", sql);
		request.setAttribute("grid_columns", gridColumns);
		request.setAttribute("grid_size", gridSize);
		ExtComponentServlet extComponentServlet = new ExtComponentServlet();
		extComponentServlet.processGrid(request, response, true);
	}
	/**
	 * 取得内贸付款浏览查询SQL语句
	 * 
	 * @param map
	 * @return
	 */
	public String getQuerySql(Map<String, String> map)
	{
		String deptId = getDeptId(map);
		String payType = map.get("payType");
		//默认字典表为内贸付款方式字典表
		String dictTable = "bm_inner_pay_method";
		String contractTable = "T_CONTRACT_PURCHASE_INFO";
		//如果是非货付款取非货付款方式字典表
		if("2".equals(payType)){
			dictTable = "bm_inner_pay_method_un";
			contractTable = "V_CONTRACT_PURCHASE_AND_SALES";
		}
		StringBuffer sb = new StringBuffer();
		//StringBuffer subSql = new StringBuffer();
		//子查询,用于组合内贸付款单所对应的合同的合同编号
		//-------性能问题，取消合同号在主信息的显示
		/*subSql.append("(Select payment_id,LTRIM(MAX(SYS_CONNECT_BY_PATH(contract_no, ',')), ',') AS contract_no ");
		subSql.append("from (Select payment_id,contract_no,");
		subSql.append("ROW_NUMBER() OVER(PARTITION BY payment_id ORDER BY contract_no) as CURR,");
		subSql.append("ROW_NUMBER() OVER(PARTITION BY payment_id ORDER BY contract_no) - 1 as PREV ");
		subSql.append("from (Select a.payment_id,d.contract_no ");
		subSql.append("from T_PAYMENT_INNER_INFO a,T_PAYMENT_CONTRACT b,"+contractTable+" d ");
		subSql.append("where a.is_available = '1' and d.order_state in ('3','5','7','8','9') and b.payment_id = a.payment_id");
		subSql.append(" and b.contract_purchase_id = d.contract_purchase_id))");
		subSql.append(" START WITH CURR = 1 CONNECT BY PREV = PRIOR CURR ");
		subSql.append(" and payment_id = PRIOR payment_id GROUP BY payment_id) p1 on p1.payment_id=p.payment_id");*/
		
		//子查询,用于组合内贸付款单所对应的合同的合同时间
		//-------性能问题，取消合同号在主信息的显示
		/*subSql.append(" left outer join (Select payment_id,LTRIM(MAX(SYS_CONNECT_BY_PATH(CONTRACT_TIME, ',')), ',') AS CONTRACT_TIME ");
		subSql.append("from (Select payment_id,CONTRACT_TIME,");
		subSql.append("ROW_NUMBER() OVER(PARTITION BY payment_id ORDER BY CONTRACT_TIME) as CURR1,");
		subSql.append("ROW_NUMBER() OVER(PARTITION BY payment_id ORDER BY CONTRACT_TIME) - 1 as PREV1 ");
		subSql.append("from (Select a.payment_id,nvl(d.approved_time,'0000-00-00') as CONTRACT_TIME ");
		subSql.append("from T_PAYMENT_INNER_INFO a,T_PAYMENT_CONTRACT b,"+contractTable+" d ");
		subSql.append("where a.is_available = '1' and d.order_state in ('3','5','7','8','9') and b.payment_id = a.payment_id");
		subSql.append(" and b.contract_purchase_id = d.contract_purchase_id))");
		subSql.append(" START WITH CURR1 = 1 CONNECT BY PREV1 = PRIOR CURR1");//and PREV1 = PRIOR CURR1
		subSql.append(" and payment_id = PRIOR payment_id GROUP BY payment_id) p2 on p2.payment_id=p.payment_id");*/

		// CREDIT_ID,CREDIT_NO,CUSTOM_CREATE_DATE,CREDIT_REC_DATE,DEPT_ID,PROJECT_NO,CONTRACT_NO,SAP_ORDER_NO,CREATE_BANK,CREDIT_STATE_D_CREDIT_STATE,CREATOR
		//                             -------性能问题，取消合同号在主信息的显示  p1.contract_no,p2.CONTRACT_TIME,
		sb.append(" Select distinct d.dept_name,u.real_name as creator_name,p.currency,");
		sb.append(" m.title as pay_method,p.apply_time,p.approved_time,p.pay_real_time,p.pay_value,p.rec_bank,p.pay_method pay_method_view,");
		sb.append(" p.open_account_bank,p.open_account_no,p.EXAMINE_STATE,p.EXAMINE_STATE as");
		sb.append(" EXAMINE_STATE_D_EXAMINE_STATE,p.payment_id,p.creator,p.creator_time ");
		sb.append(" from T_PAYMENT_INNER_INFO p left outer join T_PAYMENT_CONTRACT c on p.payment_id=c.payment_id left outer join "+contractTable+" i on i.contract_purchase_id=c.contract_purchase_id ");
		sb.append(" left outer join T_SYS_DEPT d on d.dept_id=p.dept_id left outer join T_SYS_USER u on u.user_id=p.creator left outer join "+dictTable+" m on m.id=p.pay_method ");
		//sb.append(" left outer join ");-------性能问题，取消合同号在主信息的显示
		//sb.append(subSql);-------性能问题，取消合同号在主信息的显示
		sb.append(" where p.is_available = '1' and p.dept_id in ("+deptId);
		sb.append(") ");

		if(map.get("deptId")!=null&&map.get("deptId").length()>0){
			String ds =map.get("deptId");
			String[] dss = ds.split(",");
			String deptIds="";
			for (int i = 0; i < dss.length; i++) {
				if(i==(dss.length-1))
					deptIds = deptIds+"'"+dss[i]+"'";
				else
					deptIds = deptIds+"'"+dss[i]+"',";
			}
			sb.append(" and p.dept_id in (" + deptIds + ")");
		}
		// 申请人
		if (map.get("realName") != null)
			sb.append(" and p.Creator_Name like '%" + map.get("realName") + "%'");
		// 合同时间
		if (map.get("contractTime") != null || map.get("endContractTime") != null)
			sb.append(getDateSql(map.get("contractTime"),map.get("endContractTime"),"i.APPROVED_TIME"));
		// 合同编码
		if (map.get("contractNo") != null)
			sb.append(" and i.CONTRACT_NO like '%" + map.get("contractNo").toUpperCase()
					+ "%'");
		// 付款形式
		if (map.get("payMethod") != null)
			sb.append(" and p.PAY_METHOD in (" + map.get("payMethod")
					+ ")");
		// 付款类别
		if (payType != null)
			sb.append(" and p.PAY_TYPE = '" + map.get("payType")
					+ "'");
		// 付款用途
		if (map.get("payUse") != null)
			sb.append(" and p.PAY_USE like '%" + map.get("payUse") + "%'");
		// 申请时间
		if (map.get("applyTime") != null || map.get("endApplyTime") != null)
			sb.append(getDateSql(map.get("applyTime"),map.get("endApplyTime"),"p.APPLY_TIME"));
		// 审批通过时间
		if (map.get("approvedTime") != null || map.get("endApprovedTime") != null)
			sb.append(getDateSql(map.get("approvedTime"),map.get("endApprovedTime"),"p.APPROVED_TIME"));
		// 实际付款日
		if (map.get("payRealTime") != null || map.get("endPayRealTime") != null)
			sb.append(getDateSql(map.get("payRealTime"),map.get("endPayRealTime"),"p.PAY_REAL_TIME"));
		// 收款单位名
		if (map.get("recBank") != null)
			sb.append(" and p.REC_BANK like '%" + map.get("recBank") + "%'");
		if (map.get("payValue") != null)
			sb.append(" and p.pay_value >= '" + map.get("payValue") + "'");
		if (map.get("payValue1") != null)
			sb.append(" and p.pay_value <= '" + map.get("payValue1") + "'");
		// 财务编号
		if (map.get("financeNo") != null)
			sb.append(" and p.FINANCE_NO like '%" + map.get("financeNo") + "%'");
		if (map.get("projectNo") != null)
			sb.append(" and p.project_no like '%" + map.get("projectNo") + "%'");
		if (map.get("examState") != null)
			sb.append(" and p.examine_state = '" + map.get("examState") + "'");
		sb.append(" order by p.creator_time desc");
		//System.out.println("内贸付款查询功能SQL语句:" + sb.toString());
		return sb.toString();
	}

	/**
	 * 取得(内贸、进口)付款浏览查询SQL语句
	 * 
	 * @param map
	 * @return
	 */
	public String getPaymentQuerySql(Map<String, String> map)
	{
	    String deptId = getDeptId(map);
	    String payType = map.get("payType");
	    StringBuffer sb = new StringBuffer();
	    sb.append(" select distinct ");
	    sb.append(" a.*, ");
	    sb.append(" a.MANDT             as client,");
	    sb.append(" dp.name1            as supplier_text,");
	    sb.append(" o.organizationname  as dept_id_text,");
	    sb.append(" yt.ktext                as currency_text,");
	    sb.append(" yt2.ktext              as factcurrency_text,");
	    sb.append(" yd.deptname       as redocarybc_text,");
	    sb.append(" pi.old_contract_no       ,");
	    sb.append(" pi.project_no       ,");
	    sb.append(" pi.contract_no       ,");
	    sb.append(" b.ddtext       ,");
	    sb.append(" '_view,_viewProcessState'  as authenticationStr ,");
	    sb.append(" pi.ymat_group,bt.title, ");
	    sb.append(" (select DDtext From DD07T  where domname='YDPAYTRADETYPE' and domvalue_l=a.paymenttype ) paymentTypeText, ");
	    sb.append(" (select DDtext From DD07T  where domname='YDPAYMENTBUZSTATE' and domvalue_l=a.businessstate ) businessstateText");
	    sb.append(" from YPAYMENT a left join YGETLIFNR dp on ( a.supplier=dp.lifnr ) ");
	    sb.append("                               left join YORGANIZATION o on ( a.dept_id=o.organizationid ) ");
	    sb.append("                               left join YTCURT yt on ( a.currency=yt.wears ) ");
	    sb.append("                               left join YTCURT yt2 on ( a.factcurrency=yt2.wears ) ");
	    sb.append("                               left join YDEPTINFO yd on ( a.redocarybc=yd.deptid ) ");
	    sb.append("                               left join (select * from DD07T where DOMNAME='YDPAYTYPE' ) b on a.pay_type=b.domvalue_l");
	    sb.append("                               left join YPAYMENTITEM pi on a.paymentid=pi.paymentid");
	    sb.append("                               left join t_project_info p on p.project_no=pi.project_no");
	    sb.append("                               left join bm_business_type bt on p.trade_type=bt.id");
	    sb.append(" where 1=1");
	    
        if (map.get("contract_no.fieldValue") != null || map.get("project_no.fieldValue") != null
                || map.get("pickListNo.fieldValue") != null
                || map.get("old_contract_no.fieldValue") != null
                || map.get("ymatGroup.fieldValue") != null) {
    	    sb.append(" and exists ( select 'x' from YPAYMENTITEM b left outer join t_pick_list_info x on b.pick_list_no=x.pick_list_id where a.paymentid=b.paymentid ");
    	    if (map.get("contract_no.fieldValue") != null) {
    	        sb.append("                                 and b.contract_no like '%"+map.get("contract_no.fieldValue") +"%'");
    	    }
    	    if (map.get("project_no.fieldValue") != null) {
    	        sb.append("                                 and b.project_no like '%"+map.get("project_no.fieldValue") +"%'");
    	    }
    	    if (map.get("pickListNo.fieldValue") != null) {
    	        sb.append("                                 and x.pick_list_no like '%"+map.get("pickListNo.fieldValue") +"%' ");
    	    }
    	    if (map.get("old_contract_no.fieldValue") != null) {
    	        sb.append("                                 and b.old_contract_no like '%"+map.get("old_contract_no.fieldValue") +"%'");
    	    }
    	    if (map.get("ymatGroup.fieldValue") != null&&!"请选择".equals(map.get("ymatGroup.fieldValue"))) {
    	        sb.append("                                 and b.ymat_group like '%"+map.get("ymatGroup.fieldValue") +"%'");
    	    }
    	    sb.append("                      )");
	    }
	    
	    if (map.get("voucherno.fieldValue") != null) {
	        sb.append(" and exists (select 'x' from yvoucher c where a.paymentid = c.businessid");
	            sb.append("                                and c.voucherno like '%"+map.get("voucherno.fieldValue")+"%'");
            sb.append("                      )");
	    }

        if (map.get("dp_da_no.fieldValue") != null || map.get("collection_bank.fieldValue") != null
                || map.get("pick_list_rec_date.fieldValue") != null
                || map.get("lc_no.fieldValue") != null
                || map.get("issuing_date.fieldValue") != null
                || map.get("issuing_bank.fieldValue") != null) {
            
            sb.append(" and exists ( select 'x' from YPAYMENTITEM b2, T_PICK_LIST_INFO c  " +
            		" where a.paymentid=b2.paymentid" +
            		"    and c.pick_list_id =b2.pick_list_no ");
            if (map.get("dp_da_no.fieldValue") != null) {
                sb.append("                                 and c.dp_da_no like '%"+map.get("dp_da_no.fieldValue") +"%'");
            }
            if (map.get("collection_bank.fieldValue") != null) {
                sb.append("                                 and c.collection_bank like '%"+map.get("collection_bank.fieldValue") +"%'");
            }
            if (map.get("pick_list_rec_date.fieldValue") != null || map.get("pick_list_rec_dateEnd.fieldValue")!=null) {
                sb.append(this.getDateStrNoRepalceSql(map.get("pick_list_rec_date.fieldValue"), map.get("pick_list_rec_dateEnd.fieldValue"), "c.pick_list_rec_date"));
            }
            if (map.get("lc_no.fieldValue") != null) {
                sb.append("                                 and c.lc_no like '%"+map.get("lc_no.fieldValue") +"%'");
            }
            if (map.get("issuing_date.fieldValue") != null || map.get("issuing_dateEnd.fieldValue")!=null) {
                sb.append(this.getDateStrNoRepalceSql(map.get("issuing_date.fieldValue"), map.get("issuing_dateEnd.fieldValue"), "c.issuing_date"));
            }
            if (map.get("issuing_bank.fieldValue") != null) {
                sb.append("                                 and c.issuing_bank like '%"+map.get("issuing_bank.fieldValue") +"%'");
            }
            sb.append("                      )");
        }
        
	    // 付款单号
	    if (map.get("paymentno.fieldValue") != null)
	        sb.append(" and a.paymentno like '%" + map.get("paymentno.fieldValue") + "%'");
	    // 供应商
	    if (map.get("supplier.fieldValue") != null)
	        sb.append(" and a.supplier like '%" + map.get("supplier.fieldValue") + "%'");
	    // 付款方式
	    if (map.get("paymenttype.fieldValue") != null)
	        sb.append(" and a.paymenttype = '" + map.get("paymenttype.fieldValue") + "'");
	    // 付款类型
	    if (map.get("pay_type.fieldValue") != null)
	        sb.append(" and a.pay_type = '" + map.get("pay_type.fieldValue") + "'");
	    // 创建人
	    if (map.get("creator.fieldValue") != null)
            try {
                String a = URLDecoder.decode(map.get("creator.fieldValue"), "UTF-8");
                sb.append(" and a.creator like '%" + URLDecoder.decode(map.get("creator.fieldValue"), "UTF-8") + "%'");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
	    // 部门
	    if (map.get("dept_id.fieldValue") != null)
	        sb.append(" and a.dept_id like '%" + map.get("dept_id.fieldValue") + "%'");
	    // 创建日期
	    if (map.get("createtime.fieldValue") != null )
	    	sb.append(" and a.createtime >= '" + map.get("createtime.fieldValue").replaceAll("-", "") + "000000'");
	    if (map.get("createtimeEnd.fieldValue") != null)
	    	sb.append(" and a.createtime <= '" + map.get("createtimeEnd.fieldValue").replaceAll("-", "") + "240000'");
	    // 最后修改日期
	    if (map.get("lastmodifytime.fieldValue") != null)
	    	sb.append(" and a.lastmodifytime >= '" + map.get("lastmodifytime.fieldValue").replaceAll("-", "") + "000000'");
	    if (map.get("lastmodifytimeEnd.fieldValue") != null)
	    	sb.append(" and a.lastmodifytime <= '" + map.get("lastmodifytime.fieldValue").replaceAll("-", "") + "240000'");
        // 银行对外付款日
        if (map.get("paydate.fieldValue") != null || map.get("paydateEnd.fieldValue") != null){
            sb.append(this.getDateStrSql(map.get("paydate.fieldValue"), map.get("paydateEnd.fieldValue"), "a.paydate"));
        }
        // 押汇到期付款日
        if (map.get("documentarypaydt.fieldValue") != null || map.get("documentarypaydtEnd.fieldValue") != null){
            sb.append(this.getDateStrSql(map.get("documentarypaydt.fieldValue"), map.get("documentarypaydtEnd.fieldValue"), "a.documentarypaydt"));
        }
	    // 抬头文本
	    if (map.get("text.fieldValue") != null)
	        sb.append(" and a.text like '%" + map.get("text.fieldValue") + "%'");
	    // 申请金额
	    if (map.get("applyamount.fieldValue") != null)
	        sb.append(" and a.applyamount = '" + map.get("applyamount.fieldValue") + "'");
	    // 币别
	    if (map.get("currency.fieldValue") != null)
	        sb.append(" and a.currency = '" + map.get("currency.fieldValue") + "'");
	    // 贸易类别
	    if (map.get("trade_type.fieldValue") != null)
	        sb.append(" and a.trade_type = '" + map.get("trade_type.fieldValue") + "'");
	 // 预计到货日
        if (map.get("arrivegoodsdate.fieldValue") != null || map.get("arrivegoodsdateEnd.fieldValue") != null){
            sb.append(this.getDateStrSql(map.get("arrivegoodsdate.fieldValue"), map.get("arrivegoodsdateEnd.fieldValue"), "a.arrivegoodsdate"));
        }
	 // 是否海外代付
	    if (map.get("isoverrepay.fieldValue") != null)
	        sb.append(" and a.isoverrepay = '" + map.get("isoverrepay.fieldValue") + "'");
	 // 预付天数
	    if (map.get("yufuTS.fieldValue") != null)
	        sb.append(" and to_date(a.arrivegoodsdate,'yyyymmdd')-to_date(a.paydate,'yyyymmdd')>=" + map.get("yufuTS.fieldValue") + "");
	    if (map.get("yufuTS1.fieldValue") != null)
	        sb.append(" and to_date(a.arrivegoodsdate,'yyyymmdd')-to_date(a.paydate,'yyyymmdd')<=" + map.get("yufuTS1.fieldValue") + "");
	 // 延付天数
	    if (map.get("yanfuTS.fieldValue") != null)
	    	sb.append(" and to_date(a.paydate,'yyyymmdd')-to_date(a.arrivegoodsdate,'yyyymmdd')>=" + map.get("yanfuTS.fieldValue") + "");
	    if (map.get("yanfuTS1.fieldValue") != null)
	    	sb.append(" and to_date(a.paydate,'yyyymmdd')-to_date(a.arrivegoodsdate,'yyyymmdd')<=" + map.get("yanfuTS1.fieldValue") + "");
	    if (map.get("tradeType") != null) {
	    	sb.append(" and exists (select '' from t_project_info where  project_no=pi.project_no and trade_type ='" + map.get("tradeType") + "') ");
		}
	    return sb.toString();
	}
	
	private String getDateSql(String startDate,String endDate,String column){
		StringBuffer sb = new StringBuffer();
		if (startDate != null && endDate != null)
			sb.append(" and to_date("+column+",'yyyy-mm-dd hh24:mi:ss') between to_date('" 
					+ startDate + "','yyyy-mm-dd hh24:mi:ss') and to_date('" 
					+ endDate + "','yyyy-mm-dd hh24:mi:ss')");
		else if (startDate != null)
			sb.append(" and "+column+" like '%" + startDate + "%'");
		else if (endDate != null)
			sb.append(" and to_date("+column+",'yyyy-mm-dd hh24:mi:ss') < to_date('"
					+ endDate + "','yyyy-mm-dd hh24:mi:ss')");
		else
			return "";
		return sb.toString();
			
	}
	
	   
    /**
     * 生成日期字符格式SQL串
     * @param startDate
     * @param endDate
     * @param column
     * @return
     */
    private String getDateStrSql(String startDate, String endDate, String column) {
        StringBuffer sb = new StringBuffer();
        if (startDate != null)
            sb.append(" and " + column + " >= '" + startDate.replaceAll("-", "") + "'");
        if (endDate != null)
            sb.append(" and " + column + " <= '" + endDate.replaceAll("-", "") + "'");
        else
            return "";
        return sb.toString();

    }
    
    
    /**
     * 生成日期字符格式SQL串 不替换-号
     * @param startDate
     * @param endDate
     * @param column
     * @return
     */
    private String getDateStrNoRepalceSql(String startDate, String endDate, String column) {
        StringBuffer sb = new StringBuffer();
        if (startDate != null && endDate != null)
            sb.append(" and " + column + " between '" + startDate
                    + "'  and '" + endDate + "'");
        else if (startDate != null)
            sb.append(" and " + column + " = '" + startDate + "'");
        else if (endDate != null)
            sb.append(" and " + column + " < '" + endDate + "'");
        else
            return "";
        return sb.toString();
        
    }
    
    
	/**
	 * 查询付款相关合同
	 * @param map
	 * @return
	 */
	public String getInnerPayContractQuerySql(Map<String,String> map){
		String deptId = getDeptId(map);
		String contractType = map.get("contractType");
		String tableTag = "purchase";
		String provider ="EKKO_LIFNR_NAME";
		String totalTag = "total";
		String paperNo = "ekko_Unsez";
		String contractTag = "'采购合同'";
		if("2".equals(contractType)){
			tableTag = "sales";
			totalTag = "order_total as total ";
			paperNo = "vbkd_Ihrez";
			contractTag = "'销售合同'";
			provider = "KUAGV_KUNNR_NAME";
		}
		String contractGroupNo = map.get("contractGroupNo");
		String contractGroupName = map.get("contractGroupName");
		String contractNo = map.get("contractNo");
		String contractName = map.get("contractName");
		String orderState = map.get("orderState");
		String tradeType = map.get("tradeType");
		String projectId = map.get("projectId");
		String sql = "select c.project_no, c.project_name,b.contract_group_no,b.contract_group_name,a.contract_"+tableTag+"_id as CONTRACT_PURCHASE_ID,"
				+ "a.contract_no,a.contract_name,a.sap_order_no,a.approved_time,a."+totalTag+",a.mask as cmd,a."+paperNo+" as paper_No,"
				+ contractTag+" contract_type,'详情' CONTRACT_INFO,a.order_state as ORDER_STATE_D_ORDER_STATE,"+provider+" provider"
				+ "  from t_contract_"+tableTag+"_info a, t_contract_group b, t_project_info c"
				+ " where a.order_state in ('3','5','7','8','9') and a.contract_group_id = b.contract_group_id and a.project_id = c.project_id";
	
		if (StringUtils.isNotBlank(contractGroupNo))
		{
			sql = sql + " and b.contract_group_no like '%" + contractGroupNo
					+ "%'";
		}
		if (StringUtils.isNotBlank(contractGroupName))
		{
			sql = sql + " and b.contract_group_name like '%"
					+ contractGroupName + "%'";
		}
		if (StringUtils.isNotBlank(contractNo))
		{
			sql = sql + " and a.contract_no like '%" + contractNo + "%'";
		}
		if (StringUtils.isNotBlank(contractName))
		{
			sql = sql + " and a.contract_name like '%" + contractName + "%'";
		}
		if (StringUtils.isNotBlank(deptId))
		{
			sql = sql + " and a.dept_id in (" + deptId + ")";
		}
		if (StringUtils.isNotBlank(orderState))
		{
			sql = sql + " and a.order_state like '%" + orderState + "%'";
		}
		if (StringUtils.isNotBlank(tradeType))
		{
			sql = sql + " and a.trade_type = " + tradeType;
		}
		if(StringUtils.isNotBlank(projectId)){
			sql+=" and a.project_id='"+projectId+"'";
		}
		return sql;
	}
	
	/**
	 * 查询某个内贸付款单对应的合同信息
	 * @param parameterMap
	 * @return
	 */
	public String getPaymentContractQuerySql(Map<String, String> parameterMap){
		String paymentId = parameterMap.get("paymentId");
		paymentId = paymentId == null?"":paymentId;
		StringBuffer sb = new StringBuffer();
		String payType = parameterMap.get("payType");
		String contractTable = "t_contract_purchase_info";
		sb.append("Select p.contract_purchase_id,p.contract_no,p.pay_value,p.sap_order_no,p.contract_value as total,p.cmd,c.approved_time,p.paper_no,");
		sb.append("c.contract_name,'详情' contract_info,c.order_state as ORDER_STATE_D_ORDER_STATE");
		if("2".equals(payType)){
			contractTable = "V_CONTRACT_PURCHASE_AND_SALES";
			sb.append(",c.contract_type");
		}else{
			sb.append(",'采购合同' contract_type");
		}
		sb.append(" from t_payment_contract p, "+contractTable+" c where p.contract_purchase_id = c.contract_purchase_id");
		sb.append(" and p.payment_id='"+paymentId+"'");
		return sb.toString();
	}
	
	/**
	 * 查询传入参数编码转换,对点击查询后传入的参数进行编码转换
	 * @param parameterMap
	 * @return
	 */
	public Map<String,String> getParameterMap(Map<?, ?> parameterMap){
		Iterator<?> keySet = parameterMap.keySet().iterator();
		HashMap<String , String> parameter = new HashMap<String , String>();
		for(;keySet.hasNext();){
			String key = keySet.next().toString();
			if(parameterMap.get(key)!=null){
				try{
					String value[]=(String[])parameterMap.get(key);
					if(value.length>0&&value[0].length()>0){
						value[0] = java.net.URLDecoder.decode(value[0], "UTF-8");
						//value[0] = new String(value[0].getBytes("ISO-8859-1"),"UTF-8");
						parameter.put(key, value[0]);
					}
				}catch(Exception e){
					
				}
			}
		}
		return parameter;
	}
	
	/**
	 * 得到当前用户所能查看到的部门
	 * @param map
	 * @return 返回可以用于sql in查询的部门ID串
	 */
	private String getDeptId(Map<String,String> map){
		UserContext userContext = UserContextHolder.getLocalUserContext()
			.getUserContext();
		String deptId;
		
		if("1".equals(userContext.getSysDept().getIsFuncDept()))
			deptId = userContext.getGrantedDepartmentsId();
		else{
			deptId = "'"+userContext.getSysUser().getDeptId()+"'";
		}
		return deptId;
	}
	/**
	 * 
	 * @param bizId
	 * @param statusCode
	 */
	public void updateStatus(String bizId,String statusCode){
		this.paymentContractJdbcDao.updateStatus(bizId, statusCode);
	}
	
	public String queryContractNoByPayMentId(String paymentId){
		return paymentContractJdbcDao.queryContractNoByPayMentId(paymentId);
	}
	
	public String checkPayValue(String[] contractPurchaseIds,TPaymentInnerInfo info){
		if("2".equals(info.getPayType())) return null;
		/***控制立项金额***/
		String projectMsg = paymentContractJdbcDao.checkProjectPayValue(info);
		String contractMsg = paymentContractJdbcDao.checkContractsPayValue(contractPurchaseIds, info);
		return projectMsg+"<br>"+contractMsg;
	}
	public String queryParticularId(String paymentId){
		return paymentContractJdbcDao.queryParticularId(paymentId);
	}
	
	public Map<String,String>queryBankAndAccount(String recBank){
		return paymentContractJdbcDao.queryBankAndAccount(recBank);
	}
	
	public void dealOutToExcel(String sql ,ExcelObject excel){
		paymentContractJdbcDao.dealOutToExcel(sql ,excel);
	}
	
	public void dealOutToExcel1(String sql,String strAuthSql ,ExcelObject excel){
		paymentContractJdbcDao.dealOutToExcel1(sql,strAuthSql ,excel);
	}
	
	
}
