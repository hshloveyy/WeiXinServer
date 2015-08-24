/**
 * 
 */
package com.infolion.xdss3.customerSupplierMaster.service;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.org.dao.SysUserJdbcDao;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.util.BeanUtils;

import com.infolion.sapss.mainData.dao.SupplyHibernateDao;
import com.infolion.sapss.mainData.domain.TSuppliers;

import com.infolion.xdss3.customerSupplierMaster.dao.SupplierMasterHibernateDao;
import com.infolion.xdss3.customerSupplierMaster.dao.SupplierMasterJdbcDao;

import com.infolion.xdss3.customerSupplierMaster.domain.SupplierMaster;


/**
 * @author user
 *
 */
@Service
public class SupplierMasterService extends BaseService{

	@Autowired
	private SupplierMasterHibernateDao supplierMasterHibernateDao;
	
	public void setSupplierMasterHibernateDao(
			SupplierMasterHibernateDao supplierMasterHibernateDao) {
		this.supplierMasterHibernateDao = supplierMasterHibernateDao;
	}

	@Autowired
	private SupplierMasterJdbcDao supplierMasterJdbcDao;
	
	public void setSupplierMasterJdbcDao(
			SupplierMasterJdbcDao supplierMasterJdbcDao) {
		this.supplierMasterJdbcDao = supplierMasterJdbcDao;
	}
	@Autowired
	private SupplyHibernateDao supplyHibernateDao;
	
	public void setSupplyHibernateDao(
			SupplyHibernateDao supplyHibernateDao) {
		this.supplyHibernateDao = supplyHibernateDao;
	}
	@Autowired	
	private SysDeptJdbcDao sysDeptJdbcDao;
	
	
	public void setSysDeptJdbcDao(SysDeptJdbcDao sysDeptJdbcDao) {
		this.sysDeptJdbcDao = sysDeptJdbcDao;
	}
	@Autowired	
	private SysUserJdbcDao sysUserJdbcDao;
	
	/**
	 * @param sysUserJdbcDao the sysUserJdbcDao to set
	 */
	public void setSysUserJdbcDao(SysUserJdbcDao sysUserJdbcDao) {
		this.sysUserJdbcDao = sysUserJdbcDao;
	}
	/**
	 * 保存或更新
	 * @param info
	 */
	public void saveOrUpdate(SupplierMaster info) {
		this.supplierMasterHibernateDao.saveOrUpdate(info);
		
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(String id)
	{
		SupplierMaster supplierMaster = this.supplierMasterHibernateDao.get(id);
		supplierMaster.setIsAvailable("0");
		this.supplierMasterHibernateDao.update(supplierMaster);
	}
	/**
	 * 查找
	 * @param id
	 */
	public SupplierMaster find(String id) {
		return this.supplierMasterHibernateDao.get(id);
		
	}
	public TSuppliers getTSuppliersByCode(String code){
		String id=this.supplierMasterJdbcDao.getTSuppliersByCode(code);
		return this.supplyHibernateDao.get(id);
	}
	public SupplierMaster copyMaster(TSuppliers info) {
		SupplierMaster master = new SupplierMaster();
		
			
			try {
				BeanUtils.copyProperties(master, info);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
			
			master.setSuppliersId("");
			master.setOldSupplierid(info.getSuppliersId());
			String deptcode=sysDeptJdbcDao.getDeptCodeById(info.getDeptId());
			master.setCreateDept(deptcode);
			SysUser username = this.sysUserJdbcDao.queryUserById(info.getCreator(),info.getDeptId());
			master.setCreateor(username.getUserName());
		return master;
	}
	public TSuppliers copyMaster(SupplierMaster info) {
		TSuppliers master = new TSuppliers();
			try {
				BeanUtils.copyProperties(master, info);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
			
			master.setSuppliersId("");
			
		return master;
	}
	public String isExist(String no){		
		return this.supplierMasterJdbcDao.getMasterByNo(no);
	}
	public void updateDeptNo(String newno,String oldno){
		this.supplierMasterJdbcDao.updateDeptNo(newno, oldno);
	}
}
