package com.infolion.sapss.finReport.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.org.domain.SysDept;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.finReport.dao.FinReportJdbcDao;

@Service
public class FinReportService extends BaseJdbcService {
	
	@Autowired
	private FinReportJdbcDao finReportJdbcDao;
	
	public FinReportJdbcDao getFinReportJdbcDao() {
		return finReportJdbcDao;
	}

	public void setFinReportJdbcDao(FinReportJdbcDao finReportJdbcDao) {
		this.finReportJdbcDao = finReportJdbcDao;
	}

	public List<SysDept> queryDeptByParentId(String in_strParentId){
		return finReportJdbcDao.queryDeptByParentId(in_strParentId);
	}
	
	public Map<String,BigDecimal> queryGenneralBudget(String year,String month,String comCodes,String busCodes){
		return finReportJdbcDao.queryGenneralBudget(year,month,comCodes,busCodes);
	}
	
	public Map<String,String> dealDeptBs(String deptId){
		return finReportJdbcDao.dealDeptBs(deptId);
	}

}
