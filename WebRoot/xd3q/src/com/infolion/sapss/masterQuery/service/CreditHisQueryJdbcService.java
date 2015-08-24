/*
 * @(#)BillApplyJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：王懿璞
 *  时　间：2009-6-12
 *  描　述：创建
 */

package com.infolion.sapss.masterQuery.service;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.masterQuery.dao.CreditHisQueryJdbcDao;


/**
 * 
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 王懿璞
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class CreditHisQueryJdbcService extends BaseJdbcService {
	@Autowired
	private CreditHisQueryJdbcDao creditHisQueryJdbcDao;

	
	/**
	 *  取得 查询的SQL语句
	 * @param map
	 * @return
	 */	
	public String getQueryCreditHisInfoSql(Map<String, String> filter){
		String applyTime = filter.get("applyTime");
		String applyTime1 = filter.get("applyTime1");
		StringBuffer bf = new StringBuffer("");
		bf.append("select h.dept_id,d.dept_name,b.title BUSINESS_TYPE,h.project_no,h.contract_no,p1.ekko_unsez,m.title MATERIAL_GROUP,");
		bf.append("h.benefit,p.customer_link_man,h.credit_info,h.create_date,");
		bf.append("h.approved_time,h.create_bank,h.credit_no,h.avail_date,h.currency,h.amount,");
		bf.append("getUSDAmount(h.stime,h.currency,h.amount) USDAMOUNT,h.loading_period,h.valid_date,");
		bf.append("s.title CREDIT_STATE ,h.pick_list_total pick_list_total,h.amount-nvl(h.pick_list_total,0) AMOUNT_PICKTOTAL,getUSDAmount(h.stime,h.currency,h.amount-nvl(h.pick_list_total,0)) USDAMOUNT_PICKTOTAL,");
		bf.append("h.amount-nvl(h.payment_total,0) AMOUNT_PAYMENTTOTAL,");
		bf.append("getUSDAmount(h.stime,h.currency,h.amount-nvl(h.payment_total,0)) USDAMOUNT_PAYMENTTOTAL ");
		bf.append("from");
		bf.append("(");
		bf.append("  select * from (");
	  //bf.append("  -----信用证查询");
		bf.append("    select b1.*,a1.stime From (");
		bf.append("    select max(stime) stime ,credit_id from (");
		bf.append("    select nvl(substr(replace(h.approved_time,'-',''),0,8),replace(h.create_date,'-','')) stime ,h.*");
		bf.append("    from t_credit_his_info h where h.create_or_rec=1 and h.is_available=1");
		if(StringUtils.isNotBlank(filter.get("projectNo"))){
		bf.append("    and h.project_no like '%"+filter.get("projectNo")+"%'");
		}
		if(StringUtils.isNotBlank(filter.get("contractNo"))){
		bf.append("    and h.contract_no like '%"+filter.get("contractNo")+"%'");
		}
		if(StringUtils.isNotBlank(filter.get("creditNo"))){
		bf.append("    and h.credit_No like '%"+filter.get("creditNo")+"%'");
		}
		if(StringUtils.isNotBlank(filter.get("createBank"))){
		bf.append("    and h.create_bank like '%"+filter.get("createBank")+"%'");
		}
		if(StringUtils.isNotBlank(applyTime)){
		bf.append("    and nvl(substr(replace(h.approved_time,'-',''),0,8),replace(h.create_date,'-',''))>='"+applyTime.replaceAll("-", "")+"'");
		}
		if(StringUtils.isNotBlank(applyTime1)){
		bf.append("    and nvl(substr(replace(h.approved_time,'-',''),0,8),replace(h.create_date,'-',''))<='"+applyTime1.replaceAll("-", "")+"'");
		}
		bf.append("    )     group by credit_id");
		bf.append("    ) a1 left outer join t_credit_his_info b1 on  a1.stime =  nvl(substr(replace(b1.approved_time,'-',''),0,8),replace(b1.create_date,'-',''))");
		bf.append("    and a1.credit_id =b1.credit_id");
		bf.append("  )  F1");
	  //bf.append("  ----到单金额统计");
		bf.append("  left outer join (");
		bf.append("    select sum(g.totalvalue) pick_list_total,g.lc_no");
		bf.append("    from t_pick_list_info g");
		bf.append("    where g.is_available='1'");
		bf.append("          and g.examine_state in('2','3')");
		bf.append("          and g.pick_list_type in ('1','2')");
		if(StringUtils.isNotBlank(applyTime)){
		bf.append("          and g.pick_list_rec_date>='"+applyTime+"'");
		}
		if(StringUtils.isNotBlank(applyTime1)){
		bf.append("          and g.pick_list_rec_date<='"+applyTime1+"'");
		}
		bf.append("    group by g.lc_no");
		bf.append("  ) F2 ON F1.CREDIT_NO=F2.LC_NO");
		bf.append("  LEFT OUTER JOIN (");
	  //bf.append("  ---二三期付款金额统计");
		bf.append("    select sum(k.applyamount) payment_total,k.lc_no from(");
	  //bf.append("    ---三期付款统计扣除供应商退款部分");
		bf.append("    select i.assignamount-nvl(r1.assignamount,0) applyamount,g.lc_no from ypaymentitem i ");
		bf.append("    left outer join ypayment p2 on i.paymentid=p2.paymentid ");
		bf.append("    left outer join t_pick_list_info g on g.pick_list_id=i.pick_list_no ");
		bf.append("    left outer join (");
		bf.append("      select sum(ri.refundmentamount) assignamount,ri.paymentitemid from yrefundmentitem ri left outer join YREFUNDMENT r on ri.refundmentid=r.refundmentid ");
		bf.append("      where trim(ri.paymentitemid) is not null and r.businessstate >=2");
		if(StringUtils.isNotBlank(applyTime)){
		bf.append("      and substr(r.createtime,0,8)>='"+applyTime.replaceAll("-", "")+"'");
		}
		if(StringUtils.isNotBlank(applyTime1)){
		bf.append("      and substr(r.createtime,0,8)<='"+applyTime1.replaceAll("-", "")+"'");
		}
		bf.append("      group by ri.paymentitemid");
		bf.append("    ) r1 on i.paymentitemid = r1.paymentitemid");
		bf.append("    where trim(p2.paydate) is not null");
		if(StringUtils.isNotBlank(applyTime)){
		bf.append("    and p2.paydate>='"+applyTime.replaceAll("-", "")+"'");
		}
		if(StringUtils.isNotBlank(applyTime1)){
		bf.append("    and p2.paydate<='"+applyTime1.replaceAll("-", "")+"'");
		}
		bf.append("    and g.lc_no is not null and p2.businessstate>2");
	  //bf.append("    ---二期付款统计");
		bf.append("    union all");
		bf.append("    select  i2.pay_value,g.lc_no from t_payment_imports_info i2");
		bf.append("    left outer join t_pick_list_info g on g.pick_list_id=i2.pick_list_id");
		bf.append("    where trim(i2.pay_Time) is not null");
		if(StringUtils.isNotBlank(applyTime)){
		bf.append("    and i2.pay_Time>='"+applyTime+"'");
		}
		if(StringUtils.isNotBlank(applyTime1)){
		bf.append("    and i2.pay_Time<='"+applyTime1+"'");
		}
		bf.append("    and g.lc_no is not null and i2.is_available=1");
		bf.append("    and i2.examine_state=3");
		bf.append("    ) k group by k.lc_no");
		bf.append("  ) F3 ON F1.CREDIT_NO=F3.lc_no");

		bf.append(") h ");
		bf.append("left outer join t_sys_dept d on h.dept_id=d.dept_id ");
		bf.append("left outer join bm_business_type b on h.trade_type=b.id ");
		bf.append("left outer join t_project_info p on h.project_no=p.project_no ");
		bf.append("left outer join t_contract_purchase_info p1 on h.contract_id=p1.contract_purchase_id ");
		bf.append("left outer join bm_material_group m on h.ymat_group=m.id ");
		bf.append("left outer join bm_credit_state s on h.credit_state=s.id ");

		bf.append("where 1=1 ");		
		
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		String deptId = filter.get("deptId");
		String deptSql = "";
		// 部门条件判断
		if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
			// 处理多选
			if (StringUtils.isNotBlank(deptId)) {
				String[] dp = deptId.split(",");
				deptId = "";
				for (int i = 0; i < dp.length; i++) {
					if (i == (dp.length - 1))
						deptId = deptId + "'" + dp[i] + "'";
					else
						deptId = deptId + "'" + dp[i] + "',";
				}
				deptSql = " and h.dept_id in (" + deptId + ")";
				bf.append(deptSql);
			}
			bf.append(" and h.dept_id in ( "+ userContext.getGrantedDepartmentsId() + ",");
			bf.append("'" + userContext.getSysDept().getDeptid() + "')");
		} else {
			bf.append(" and h.dept_id = '"	+ userContext.getSysDept().getDeptid() + "'");
		}

		return bf.append(" order by d.dept_name desc").toString();
		
}
	
	/**
		select d.dept_name,a.dept_id,
		a.create_date,a.create_bank,a.credit_no,a.project_no,a.amount,a.currency,a.valid_Date,cs.title esTitle,
		b.pick_list_no,b.acceptance_date,b.pay_date,
		p.paymentno,pt.ddtext ptTitle,bs.ddtext bsTitle,p.paydate ppaydate,p.currency pcurrency,
		p.documentarydate,p.applyamount,p.collectbankid,p.documentarylimit,p.doctaryinterest,p.documentaryrate
		
		from t_credit_info a 
		left outer join t_sys_dept d on a.dept_id=d.dept_id
		left outer join bm_credit_state cs on a.credit_state=cs.id
		left outer join t_pick_list_info b on a.credit_no=b.lc_no and b.is_available=1 and  b.examine_state=3 and b.issuing_Bank=a.create_bank
		left outer join ypaymentitem pi on pi.pick_list_no=b.pick_list_id
		left outer join ypayment p on p.paymentid=pi.paymentid and  p.businessstate!=-1 
		left outer join (select domvalue_l,ddTEXT from DD07T where DOMNAME='YDPAYTYPE') PT ON p.pay_type = pt.domvalue_l
		left outer join (select domvalue_l,ddTEXT from DD07T where DOMNAME='YDPAYMENTBUZSTATE') bs on p.Businessstate = bs.domvalue_l
		where a.is_available=1  and a.create_or_rec=1 
		and a.credit_state not in (1,2,3,7,9)
		order by a.dept_id,a.create_date desc,a.credit_no
	 * @param filter
	 * @return
	 */
	public String getQueryCreditPayInfoSql(Map<String, String> filter){
		String applyTime = filter.get("applyTime");
		String applyTime1 = filter.get("applyTime1");
		String payTime = filter.get("payTime");
		String payTime1 = filter.get("payTime1");
		StringBuffer bf = new StringBuffer("");
		bf.append("select d.dept_name,a.dept_id,");		
		bf.append("a.create_date,a.create_bank,a.credit_no,a.project_no,a.amount,a.currency,a.valid_Date,cs.title esTitle,");
		bf.append("b.pick_list_no,b.acceptance_date,b.pay_date,");
		bf.append("p.paymentno,pt.ddtext ptTitle,bs.ddtext bsTitle,p.paydate ppaydate,p.currency pcurrency, ");
		bf.append("p.documentarydate,p.applyamount,p.collectbankid,p.documentarylimit,p.doctaryinterest,p.documentaryrate ");

		bf.append("from t_credit_info a ");
		bf.append("left outer join t_sys_dept d on a.dept_id=d.dept_id ");
		bf.append("left outer join bm_credit_state cs on a.credit_state=cs.id ");
		bf.append("left outer join t_pick_list_info b on a.credit_no=b.lc_no and b.is_available=1 and  b.examine_state=3 and b.issuing_Bank=a.create_bank ");
		bf.append("left outer join ypaymentitem pi on pi.pick_list_no=b.pick_list_id ");
		bf.append("left outer join ypayment p on p.paymentid=pi.paymentid and  p.businessstate!=-1  ");
		bf.append("left outer join (select domvalue_l,ddTEXT from DD07T where DOMNAME='YDPAYTYPE') PT ON p.pay_type = pt.domvalue_l ");
		bf.append("left outer join (select domvalue_l,ddTEXT from DD07T where DOMNAME='YDPAYMENTBUZSTATE') bs on p.Businessstate = bs.domvalue_l ");
		bf.append("where a.is_available=1  and a.create_or_rec=1  ");
		bf.append("and a.credit_state not in (1,2,3,7,9) ");
		
		
		if(StringUtils.isNotBlank(applyTime)){
			bf.append("      and a.create_date >='"+applyTime+"'");
		}
		if(StringUtils.isNotBlank(applyTime1)){
			bf.append("      and a.create_date <='"+applyTime1+"'");
		}
		
		if(StringUtils.isNotBlank(payTime)){
			bf.append("      and p.paydate >='"+payTime.replaceAll("-", "")+"'");
		}
		if(StringUtils.isNotBlank(payTime1)){
			bf.append("      and p.paydate <='"+payTime1.replaceAll("-", "")+"'");
		}
		if(StringUtils.isNotBlank(filter.get("projectNo"))){
			bf.append("      and a.project_no  like '%"+filter.get("projectNo")+"%'");
		}	
		if(StringUtils.isNotBlank(filter.get("createBank"))){
			bf.append("      and a.create_bank  like '%"+filter.get("createBank")+"%'");
		}
		if(StringUtils.isNotBlank(filter.get("creditNo"))){
			bf.append("      and a.credit_No  like '%"+filter.get("creditNo")+"%'");
		}
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
		.getUserContext();
		String deptId = filter.get("deptId");
		String deptSql = "";
		// 部门条件判断
		if ("1".equals(userContext.getSysDept().getIsFuncDept())) {
			// 处理多选
			if (StringUtils.isNotBlank(deptId)) {
				String[] dp = deptId.split(",");
				deptId = "";
				for (int i = 0; i < dp.length; i++) {
					if (i == (dp.length - 1))
						deptId = deptId + "'" + dp[i] + "'";
					else
						deptId = deptId + "'" + dp[i] + "',";
				}
				deptSql = " and a.dept_id in (" + deptId + ")";
				bf.append(deptSql);
			}
			bf.append(" and a.dept_id in ( "+ userContext.getGrantedDepartmentsId() + ",");
			bf.append("'" + userContext.getSysDept().getDeptid() + "')");
		} else {
			bf.append(" and a.dept_id = '"	+ userContext.getSysDept().getDeptid() + "'");
		}
		
		return bf.append(" order by  a.dept_id,a.create_date desc,a.credit_no ").toString();
		
}

	public void dealOutToExcel(String sql ,ExcelObject excel){
		creditHisQueryJdbcDao.dealOutToExcel(sql ,excel);
	}	
	
	public void dealOutToExcelPay(String sql ,ExcelObject excel){
		creditHisQueryJdbcDao.dealOutToExcelPay(sql ,excel);
	}
}
