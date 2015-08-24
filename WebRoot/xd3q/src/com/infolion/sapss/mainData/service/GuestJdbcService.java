package com.infolion.sapss.mainData.service;

import org.springframework.stereotype.Service;

import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.export.domain.TBaleLoan;
import com.infolion.sapss.payment.domain.TPaymentImportsInfo;
import com.infolion.sapss.workflow.ProcessCallBack;

@Service
public class GuestJdbcService extends BaseJdbcService implements ProcessCallBack{
	/**
	 * 更新记录状态
	 */
	public void updateBusinessRecord(String businessRecordId,String examineResult, String flag) 
	{

	}
}
