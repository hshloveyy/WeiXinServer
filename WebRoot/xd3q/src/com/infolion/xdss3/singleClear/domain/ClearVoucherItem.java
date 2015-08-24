package com.infolion.xdss3.singleClear.domain;

import com.infolion.xdss3.payment.domain.ClearVoucherItemStruct;

public class ClearVoucherItem extends ClearVoucherItemStruct{
	private String companycode;
	private String businessid;

	/**
	 * @return the companycode
	 */
	public String getCompanycode() {
		return companycode;
	}

	/**
	 * @param companycode the companycode to set
	 */
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	/**
	 * @return the businessid
	 */
	public String getBusinessid() {
		return businessid;
	}

	/**
	 * @param businessid the businessid to set
	 */
	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}
	
}
