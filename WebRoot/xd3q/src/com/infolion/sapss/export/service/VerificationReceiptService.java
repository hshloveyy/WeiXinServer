package com.infolion.sapss.export.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseHibernateService;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.export.dao.ExportJdbcDao;
import com.infolion.sapss.export.dao.VerificationReceiptHibernateDao;
import com.infolion.sapss.export.domain.TVerificationReceipt;

@Service
public class VerificationReceiptService extends BaseHibernateService{
	
	@Autowired
	private ExportJdbcDao exportJdbcDao;
	@Autowired
	private VerificationReceiptHibernateDao verificationReceiptHibernateDao;
	public ExportJdbcDao getExportJdbcDao() {
		return exportJdbcDao;
	}
	public void setExportJdbcDao(ExportJdbcDao exportJdbcDao) {
		this.exportJdbcDao = exportJdbcDao;
	}
	public VerificationReceiptHibernateDao getVerificationReceiptHibernateDao() {
		return verificationReceiptHibernateDao;
	}
	public void setVerificationReceiptHibernateDao(
			VerificationReceiptHibernateDao verificationReceiptHibernateDao) {
		this.verificationReceiptHibernateDao = verificationReceiptHibernateDao;
	}
	
	public TVerificationReceipt getVerificationReceipt(String id){
		return verificationReceiptHibernateDao.get(id);
	}
	
	public void saveOrUpdateVerificationReceipt(TVerificationReceipt re){
		this.verificationReceiptHibernateDao.saveOrUpdate(re);
	}
	
	public void dealOutToExcel(String sql ,ExcelObject excel){
		exportJdbcDao.dealOutToExcelVerificationReceipt(sql ,excel);
	}
	
	
}
