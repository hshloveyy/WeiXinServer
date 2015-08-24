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

import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.export.dao.ExportDrawbackHibernateDao;
import com.infolion.sapss.export.dao.ExportJdbcDao;
import com.infolion.sapss.export.domain.TExportDrawback;
import com.infolion.sapss.receiptLog.dao.ReceiptLogJdbcDao;
@Service
public class ExportDrawbackService extends  BaseHibernateService{
	@Autowired
	private ExportDrawbackHibernateDao exportDrawbackHibernateDao;
	@Autowired
	private ExportJdbcDao exportJdbcDao;
	
	public void setExportDrawbackHibernateDao(
			ExportDrawbackHibernateDao exportDrawbackHibernateDao) {
		this.exportDrawbackHibernateDao = exportDrawbackHibernateDao;
	}

	/**
	 * 保存或更新
	 * @param info
	 */
	public void saveOrUpdate(TExportDrawback info) {
		this.exportDrawbackHibernateDao.saveOrUpdate(info);
		
	}
	/**
	 * 删除
	 * @param id
	 */
	public void delete(String id) {
		TExportDrawback info = this.exportDrawbackHibernateDao.get(id);
		info.setIsAvailable("0");
		this.exportDrawbackHibernateDao.update(info);
	}
	/**
	 * 查找
	 * @param id
	 */
	public TExportDrawback find(String id) {
		return this.exportDrawbackHibernateDao.get(id);
		
	}

	public String querySQL(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.* from t_export_drawback t left outer join t_export_apply a on t.export_apply_id=a.export_apply_id " +
				" where t.is_available=1 ");
		if(StringUtils.isNotEmpty(request.getParameter("tradeType")))
			sb.append(" and t.trade_type='").append(request.getParameter("tradeType")).append("'");
		if(StringUtils.isNotEmpty(request.getParameter("noticeNo")))
			sb.append(" and t.EXPORT_APPLY_NO like '%").append(request.getParameter("noticeNo")).append("%'");
		if(StringUtils.isNotEmpty(request.getParameter("startDate"))&&StringUtils.isNotEmpty(request.getParameter("endDate"))){
			sb.append(" and t.DRAWBACK_DATE>='").append(request.getParameter("startDate")).append("'");
			sb.append(" and t.DRAWBACK_DATE<'").append(request.getParameter("endDate")).append("'");
		}
		if(StringUtils.isNotEmpty(request.getParameter("writeNo")))
			sb.append(" and t.write_no like '%").append(request.getParameter("writeNo")).append("%'");
		
		if(StringUtils.isNotEmpty(request.getParameter("hexrq")))
			sb.append(" and t.WRITE_DATE >= '").append(request.getParameter("hexrq")).append("'");
		
		if(StringUtils.isNotEmpty(request.getParameter("hexrq1")))
			sb.append(" and t.WRITE_DATE<=  '").append(request.getParameter("hexrq1")).append("'");
		
		if(StringUtils.isNotEmpty(request.getParameter("skhkrq")))
			sb.append(" and t.SKDZR >= '").append(request.getParameter("skhkrq")).append("'");
		if(StringUtils.isNotEmpty(request.getParameter("skhkrq1")))
			sb.append(" and t.SKDZR <= '").append(request.getParameter("skhkrq1")).append("'");
		
		if(StringUtils.isNotEmpty(request.getParameter("ckDate")))
			sb.append(" and t.ckrq >= '").append(request.getParameter("ckDate")).append("'");
		if(StringUtils.isNotEmpty(request.getParameter("ckDate1")))
			sb.append(" and t.ckrq <= '").append(request.getParameter("ckDate1")).append("'");
		
		if(StringUtils.isNotEmpty(request.getParameter("deptId")))
			sb.append(" and a.dept_ID = '").append(request.getParameter("deptId")).append("'");
		
		sb.append("order by t.creator_time desc");
		return sb.toString();
	}

	public void setExportJdbcDao(ExportJdbcDao exportJdbcDao) {
		this.exportJdbcDao = exportJdbcDao;
	}
	
	public void dealOutToExcel(String sql ,ExcelObject excel){
		exportJdbcDao.dealOutToExcelDrawBack(sql ,excel);
	}
	
	/**
	 * 得到五联单的部门信息
	 * @param strExportApplyId
	 * @return
	 */
	public String getExportDeptId(String strExportApplyId){
		return this.exportJdbcDao.getExportDeptId(strExportApplyId);
	}
}
