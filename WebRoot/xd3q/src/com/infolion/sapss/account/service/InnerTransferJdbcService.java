package com.infolion.sapss.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.account.dao.InnerTransferDetailJdbcDao;
import com.infolion.sapss.account.domain.TInnerTransferDetail;

@Service
public class InnerTransferJdbcService extends BaseJdbcService {
	@Autowired
	private InnerTransferDetailJdbcDao innerTransferDetailJdbcDao;

	public void setInnerTransferDetailJdbcDao(
			InnerTransferDetailJdbcDao innerTransferDetailJdbcDao) {
		this.innerTransferDetailJdbcDao = innerTransferDetailJdbcDao;
	}

	public int updateTInnerTransferDetail(String detailID, String colName,
			String colValue) {
		return this.innerTransferDetailJdbcDao.updateTInnerTransferDetail(
				detailID, colName, colValue);
	}

	public List<TInnerTransferDetail> getDetailList(String transferID) {
		return this.innerTransferDetailJdbcDao.getDetailList(transferID);
	}
}
