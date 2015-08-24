package com.infolion.xdss3.customerSupplierMaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.xdss3.customerSupplierMaster.dao.DeptByCusuHibernateDao;
import com.infolion.xdss3.customerSupplierMaster.dao.DeptByCusuJdbcDao;
import com.infolion.xdss3.customerSupplierMaster.domain.DeptByCusu;





@Service
public class DeptByCusuService extends BaseService {
	@Autowired
	private DeptByCusuHibernateDao deptByCusuHibernateDao;
	/**
	 * @param deptByCusuHibernateDao the deptByCusuHibernateDao to set
	 */
	public void setDeptByCusuHibernateDao(
			DeptByCusuHibernateDao deptByCusuHibernateDao) {
		this.deptByCusuHibernateDao = deptByCusuHibernateDao;
	}
	@Autowired
	private DeptByCusuJdbcDao deptByCusuJdbcDao;

	/**
	 * @param deptByCusuJdbcDao the deptByCusuJdbcDao to set
	 */
	public void setDeptByCusuJdbcDao(DeptByCusuJdbcDao deptByCusuJdbcDao) {
		this.deptByCusuJdbcDao = deptByCusuJdbcDao;
	}
	
	public void save(DeptByCusu deptByCusu){
		this.deptByCusuHibernateDao.save(deptByCusu);
	}
	public void delete(String id){
		DeptByCusu deptByCusu=this.deptByCusuHibernateDao.get(id);
		this.deptByCusuHibernateDao.remove(deptByCusu);
	}
	public void update(DeptByCusu deptByCusu){
		this.deptByCusuHibernateDao.update(deptByCusu);
		
	}
	public void saveOrUpdate(DeptByCusu deptByCusu){
		this.deptByCusuHibernateDao.saveOrUpdate(deptByCusu);
	}
	public DeptByCusu find(String id){
		return this.deptByCusuHibernateDao.get(id);
	}
	
}
	
