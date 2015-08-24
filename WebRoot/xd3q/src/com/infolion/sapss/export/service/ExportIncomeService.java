/*
 * @(#)ExportIncomeService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-5-25
 *  描　述：创建
 */

package com.infolion.sapss.export.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.export.dao.ExportIncomeHibernateDao;
import com.infolion.sapss.export.dao.ExportJdbcDao;
import com.infolion.sapss.export.domain.TExportIncomeInfo;
@Service
public class ExportIncomeService extends  BaseHibernateService{
	@Autowired
	private ExportIncomeHibernateDao exportIncomeHibernateDao;
	@Autowired
	private ExportJdbcDao exportJdbcDao;
	
	public void setExportIncomeHibernateDao(
			ExportIncomeHibernateDao exportIncomeHibernateDao) {
		this.exportIncomeHibernateDao = exportIncomeHibernateDao;
	}
	/**
	 * 保存或更新
	 * @param info
	 */
	public void saveOrUpdate(TExportIncomeInfo info) {
		this.exportIncomeHibernateDao.saveOrUpdate(info);
		
	}
	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		TExportIncomeInfo info = this.exportIncomeHibernateDao.get(id);
		info.setIsAvailable("0");
		this.exportIncomeHibernateDao.update(info);
	}
	/**
	 * 查找
	 * @param id
	 */
	public TExportIncomeInfo find(String id) {
		return this.exportIncomeHibernateDao.get(id);
		
	}
	/**
	 * 组装收汇查询条件
	 * @param request
	 * @return
	 */
	public String querySQL(HttpServletRequest request) {
		
		String projectNo = request.getParameter("projectNo");
		String contractNo = request.getParameter("contractNo");
		String invNo = request.getParameter("invNo");
		String start = request.getParameter("startDate");
		String end = request.getParameter("endDate");
		StringBuffer sb = new StringBuffer();
		sb.append("select a.* from t_export_income_info a left outer join t_export_bill_exam b on a.export_bill_exam_id=b.export_bill_exam_id where a.is_available='1'");
		if(StringUtils.isNotEmpty(projectNo))
			sb.append(" and a.project_no like '%").append(projectNo).append("%'");
		if(StringUtils.isNotEmpty(contractNo))
			sb.append(" and a.contract_no like '%").append(contractNo).append("%'");
		if(StringUtils.isNotEmpty(invNo))
			sb.append(" and a.inv_no like '%").append(invNo).append("%'");
		if(StringUtils.isNotEmpty(start) && StringUtils.isNotEmpty(end)){
			sb.append(" and a.accept_date>='").append(start+"'");
			sb.append(" and a.accept_date<'").append(end+"'");
		}		
		if(StringUtils.isNotEmpty(request.getParameter("deptId")))
			sb.append(" and b.dept_ID = '").append(request.getParameter("deptId")).append("'");
		if(StringUtils.isNotEmpty(request.getParameter("total")))
			sb.append(" and a.total >=  '").append(request.getParameter("total")).append("'");
		if(StringUtils.isNotEmpty(request.getParameter("total1")))
			sb.append(" and a.total <=  '").append(request.getParameter("total1")).append("'");

		sb.append(" order by a.creator_time desc");
		return sb.toString();
	}
	/**
	 * 组装出口通知单查询条件
	 * @param request
	 * @return
	 */
	public String queryExportApplySQL(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.*,k.total as total_money,k.TOTAL_QUANTITY from t_export_apply t");
		sb.append(" left outer join (select round(sum(m.total),2) as total,sum(m.quantity) as TOTAL_QUANTITY,m.export_apply_id from t_export_apply_material m group by m.export_apply_id ) k on k.export_apply_id=t.export_apply_id");
		sb.append(" where t.is_available='1'");
		if(StringUtils.isNotEmpty(request.getParameter("projectNo")))
			sb.append(" and t.project_no like '%").append(request.getParameter("projectNo")).append("%'");
		if(StringUtils.isNotEmpty(request.getParameter("salesNo")))
			sb.append(" and t.sales_no like '%").append(request.getParameter("salesNo")).append("%'");
		if(StringUtils.isNotEmpty(request.getParameter("purchaseNo")))
			sb.append(" and t.purchase_no like '%").append(request.getParameter("purchaseNo")).append("%'");
		if(StringUtils.isNotEmpty(request.getParameter("sapOrderNo")))
			sb.append(" and t.sap_order_no like '%").append(request.getParameter("sapOrderNo")).append("%'");
		if(StringUtils.isNotEmpty(request.getParameter("tradeType")))
			sb.append(" and t.trade_type= '").append(request.getParameter("tradeType")).append("'");
		if(StringUtils.isNotEmpty(request.getParameter("writeNo")))
			sb.append(" and t.write_no like '%").append(request.getParameter("writeNo")).append("%'");
		// 部门的过滤
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		sb.append(" and t.dept_id in (" + userContext.getGrantedDepartmentsId()
				+ ",");
		sb.append("'" + userContext.getSysDept().getDeptid() + "')");	
		
		sb.append(" order by t.creator_time desc");
		return sb.toString();
	}
	/**
	 * 组装销售合同查询条件
	 * @param request
	 * @return
	 */
	public String querySalesSql(HttpServletRequest request) {
		String sql = "select c.project_no,b.contract_group_no,b.contract_group_name,a.*,a.order_state order_state_d_order_state"
			+ "  from t_contract_sales_info a, t_contract_group b, t_project_info c"
			+ " where a.contract_group_id = b.contract_group_id and a.project_id = c.project_id";
	String contractGroupNo = request.getParameter("contractGroupNo");
	if (StringUtils.isNotEmpty(contractGroupNo))
	{
		sql = sql + " and b.contract_group_no like '%" + contractGroupNo
				+ "%'";
	}
	String contractGroupName = request.getParameter("contractGroupName");
	if (StringUtils.isNotEmpty(contractGroupName))
	{
		sql = sql + " and b.contract_group_name like '%"
				+ contractGroupName + "%'";
	}
	String contractNo = request.getParameter("contractNo");
	if (StringUtils.isNotEmpty(contractNo))
	{
		sql = sql + " and a.contract_no like '%" + contractNo + "%'";
	}
	String contractName = request.getParameter("contractName");
	if (StringUtils.isNotEmpty(contractName))
	{
		sql = sql + " and a.contract_name like '%" + contractName + "%'";
	}
	String orderState = request.getParameter("orderState");
	if (StringUtils.isNotEmpty(orderState))
	{
		sql = sql + " and a.order_state like '%" + orderState + "%'";
	}else{
		sql = sql + " and a.order_state in ('3','5','7','8','9')";
	}
	String tradeType = request.getParameter("tradeType");
	if (StringUtils.isNotEmpty(tradeType))
	{
		sql = sql + " and a.trade_type = " + tradeType;
	}

	// 部门的过滤
	UserContext userContext = UserContextHolder.getLocalUserContext()
			.getUserContext();
	sql += " and a.dept_id in (" + userContext.getGrantedDepartmentsId()
			+ ",";
	sql += "'" + userContext.getSysDept().getDeptid() + "')";	
	
	return sql;
	}
	
	public void dealOutToExcel(String sql ,ExcelObject excel){
		exportJdbcDao.dealOutToExcelIncome(sql ,excel);
	}
	public void setExportJdbcDao(ExportJdbcDao exportJdbcDao) {
		this.exportJdbcDao = exportJdbcDao;
	}
}
