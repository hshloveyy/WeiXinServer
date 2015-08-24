package com.infolion.xdss3.ageAnalysis.domain;

import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * 
 * @author 钟志华
 *
 */
public class AgeInvoice {
	private String mandt;
	private String invoice;
	private String gsber;
	private String bname;
	private String bstkd;
	private String ihrez;
	private String matkl;
	private String vbeltype;
	private String trade_type;
	private String expires_date;
	
	public AgeInvoice() {
		this.mandt="800";
		this.invoice="";
		this.gsber="";
		this.bname="";
		this.bstkd="";
		this.ihrez="";
		this.matkl="";
		this.vbeltype="";
		this.trade_type="";
		this.expires_date="";
	}
	/**
	 * 客户端
	 * @return
	 */
	public String getMandt() {
		return mandt;
	}
	/**
	 * 客户端
	 * @param mandt
	 */
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	/**
	 * sap开票号
	 * @return
	 */
	public String getInvoice() {
		return invoice;
	}
	/**
	 * sap开票号
	 * @param invoice
	 */
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	/**
	 * 业务范围
	 * @return
	 */
	public String getGsber() {
		return gsber;
	}
	/**
	 * 业务范围
	 * @param gsber
	 */
	public void setGsber(String gsber) {
		this.gsber = gsber;
	}
	/**
	 * 项目名
	 * @return
	 */
	public String getBname() {
		return bname;
	}
	/**
	 * 项目名
	 * @param bname
	 */
	public void setBname(String bname) {
		this.bname = bname;
	}
	/**
	 * 纸质合同号
	 * @return
	 */
	public String getBstkd() {
		return bstkd;
	}
	/**
	 * 纸质合同号
	 * @param bstkd
	 */
	public void setBstkd(String bstkd) {
		this.bstkd = bstkd;
	}
	/**
	 *  合同号
	 * @return
	 */
	public String getIhrez() {
		return ihrez;
	}
	/**
	 * 合同号
	 * @param ihrez
	 */
	public void setIhrez(String ihrez) {
		this.ihrez = ihrez;
	}
	/**
	 * 物料类型 (物料组)
	 * @return
	 */
	public String getMatkl() {
		return matkl;
	}
	/**
	 * 物料类型 (物料组)
	 * @param matkl
	 */
	public void setMatkl(String matkl) {
		this.matkl = matkl;
	}
	/**
	 * 业务类型SAP
	 * @return
	 */
	public String getVbeltype() {
		return vbeltype;
	}
	/**
	 * 业务类型SAP
	 * @param vbeltype
	 */
	public void setVbeltype(String vbeltype) {
		this.vbeltype = vbeltype;
	}
	/**
	 * 业务类型(外围)
	 * @return
	 */
	public String getTrade_type() {
		return trade_type;
	}
	/**
	 * 业务类型(外围)
	 * @param trade_type
	 */
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	/**
	 * 逾期日
	 * @return
	 */
	public String getExpires_date() {
		return expires_date;
	}
	/**
	 * 逾期日
	 * @param expires_date
	 */
	public void setExpires_date(String expires_date) {
		this.expires_date = expires_date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((invoice == null) ? 0 : invoice.hashCode());
		result = prime * result + ((mandt == null) ? 0 : mandt.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AgeInvoice other = (AgeInvoice) obj;
		if (invoice == null) {
			if (other.invoice != null)
				return false;
		} else if (!invoice.equals(other.invoice))
			return false;
		if (mandt == null) {
			if (other.mandt != null)
				return false;
		} else if (!mandt.equals(other.mandt))
			return false;
		return true;
	}
	
}
