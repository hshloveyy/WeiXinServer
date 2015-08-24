package com.infolion.sapss.masterQuery.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.bapi.dto.MasterDataDto;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.masterQuery.dao.MasterQueryJdbcDao;
@Service
public class MasterQueryJdbcService extends BaseJdbcService{
	
	@Autowired
	private MasterQueryJdbcDao masterQueryJdbcDao;
	
	public void dealOutToExcel(String sql , ExcelObject excel){
		masterQueryJdbcDao.dealOutToExcel(sql,excel);
	}

	public MasterQueryJdbcDao getMasterQueryJdbcDao() {
		return masterQueryJdbcDao;
	}

	public void setMasterQueryJdbcDao(MasterQueryJdbcDao masterQueryJdbcDao) {
		this.masterQueryJdbcDao = masterQueryJdbcDao;
	}
	
	public MasterDataDto getCustomerMaster(String customerCd,String customerName,String salerOrg){
		MasterDataDto dto = new MasterDataDto();
		List<Map<String, String>>  list =this.masterQueryJdbcDao.getCustomerMaster(customerCd, customerName, salerOrg);
		dto.setData(list);
		dto.setTotalNumber(String.valueOf(list.size()));
		return dto;
	}
	public MasterDataDto getSupplierMaster(String supplierCd,String supplierName,String purchase_org){
		MasterDataDto dto = new MasterDataDto();
		List<Map<String, String>>  list =this.masterQueryJdbcDao.getSupplierMaster(supplierCd, supplierName, purchase_org);
		dto.setData(list);
		dto.setTotalNumber(String.valueOf(list.size()));
		return dto;
	}
}
