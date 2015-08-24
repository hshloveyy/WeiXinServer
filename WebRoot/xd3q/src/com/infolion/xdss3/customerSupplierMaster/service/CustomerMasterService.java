package com.infolion.xdss3.customerSupplierMaster.service;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.org.dao.SysDeptJdbcDao;
import com.infolion.platform.console.org.dao.SysUserJdbcDao;
import com.infolion.platform.console.org.domain.SysUser;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.util.BeanUtils;
import com.infolion.sapss.mainData.dao.GuestHibernateDao;
import com.infolion.sapss.mainData.domain.TGuest;
import com.infolion.xdss3.customerSupplierMaster.dao.CustomerMasterHibernateDao;
import com.infolion.xdss3.customerSupplierMaster.dao.CustomerMasterJdbcDao;
import com.infolion.xdss3.customerSupplierMaster.domain.CustomerMaster;


@Service
public class CustomerMasterService extends BaseService{
	@Autowired
	private CustomerMasterHibernateDao customerMasterHibernateDao;
	
	public void setCustomerMasterHibernateDao(
			CustomerMasterHibernateDao customerMasterHibernateDao) {
		this.customerMasterHibernateDao = customerMasterHibernateDao;
	}

	@Autowired
	private CustomerMasterJdbcDao customerMasterJdbcDao;
	
	public void setCustomerMasterJdbcDao(
			CustomerMasterJdbcDao customerMasterJdbcDao) {
		this.customerMasterJdbcDao = customerMasterJdbcDao;
	}
	
	@Autowired
	private GuestHibernateDao guestHibernateDao;
	
	public void setGuestHibernateDao(
			GuestHibernateDao guestHibernateDao) {
		this.guestHibernateDao = guestHibernateDao;
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
	public void saveOrUpdate(CustomerMaster info) {
		this.customerMasterHibernateDao.saveOrUpdate(info);
		
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(String id)
	{
		CustomerMaster customerMaster = this.customerMasterHibernateDao.get(id);
		customerMaster.setIsAvailable("0");
		this.customerMasterHibernateDao.update(customerMaster);
	}
	/**
	 * 查找
	 * @param id
	 */
	public CustomerMaster find(String id) {
		return this.customerMasterHibernateDao.get(id);
		
	}
	public TGuest getTGuestBycode(String guestCode){
		String id=this.customerMasterJdbcDao.getTGuestBycode(guestCode);
		return guestHibernateDao.get(id);
	}
	public CustomerMaster copyMaster(TGuest info) {
		CustomerMaster master = new CustomerMaster();
		
			try {
				BeanUtils.copyProperties(master, info);				
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			master.setCustomerId("");
			master.setGuestId(info.getGuestId());
			String deptcode=sysDeptJdbcDao.getDeptCodeById(info.getDeptId());
			master.setCreateDept(deptcode);
			SysUser username = this.sysUserJdbcDao.queryUserById(info.getCreator(),info.getDeptId());
			master.setCreateor(username.getUserName());
		return master;
	}
	public TGuest copyMaster(CustomerMaster info) {
		TGuest guest = new TGuest();
		
			try {
				BeanUtils.copyProperties(guest, info);				
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		return guest;
	}
	public String isExist(String no){		
		return this.customerMasterJdbcDao.getMasterByNo(no);
	}
	 public void updateDeptNo(String newno,String oldno){
		 customerMasterJdbcDao.updateDeptNo(newno, oldno);
	 }
	 
}
