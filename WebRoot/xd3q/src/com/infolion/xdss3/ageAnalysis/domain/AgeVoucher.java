package com.infolion.xdss3.ageAnalysis.domain;

import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * 
 * @author 钟志华
 *
 */
public class AgeVoucher {
	private String mandt;
	private String belnr;
	private String gjahr;
	private String bukrs;
	private String buzei;
	private String gsber;
	private String bname;
	private String bstkd;
	private String ihrez;
	private String matkl;
	private String vbeltype;
	private String trade_type;
	private String expires_date;
	
	public AgeVoucher() {
		this.mandt="800";
		this.belnr="";
		this.gjahr="";
		this.bukrs="";
		this.buzei="";
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
	 * 财务会计中的凭证号
	 * @return
	 */
	public String getBelnr() {
		return belnr;
	}
	/**
	 * 财务会计中的凭证号
	 * @param belnr
	 */
	public void setBelnr(String belnr) {
		this.belnr = belnr;
	}
	/**
	 * 会计年度
	 * @return
	 */
	public String getGjahr() {
		return gjahr;
	}
	/**
	 * 会计年度
	 * @param gjahr
	 */
	public void setGjahr(String gjahr) {
		this.gjahr = gjahr;
	}
	/**
	 * 公司代码
	 * @return
	 */
	public String getBukrs() {
		return bukrs;
	}
	/**
	 * 公司代码
	 * @param bukrs
	 */
	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}
	/**
	 * 会计凭证中的行项目数
	 * @return
	 */
	public String getBuzei() {
		return buzei;
	}
	/**
	 * 会计凭证中的行项目数
	 * @param buzei
	 */
	public void setBuzei(String buzei) {
		this.buzei = buzei;
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
		result = prime * result + ((belnr == null) ? 0 : belnr.hashCode());
		result = prime * result + ((bukrs == null) ? 0 : bukrs.hashCode());
		result = prime * result + ((buzei == null) ? 0 : buzei.hashCode());
		result = prime * result + ((gjahr == null) ? 0 : gjahr.hashCode());
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
		final AgeVoucher other = (AgeVoucher) obj;
		if (belnr == null) {
			if (other.belnr != null)
				return false;
		} else if (!belnr.equals(other.belnr))
			return false;
		if (bukrs == null) {
			if (other.bukrs != null)
				return false;
		} else if (!bukrs.equals(other.bukrs))
			return false;
		if (buzei == null) {
			if (other.buzei != null)
				return false;
		} else if (!buzei.equals(other.buzei))
			return false;
		if (gjahr == null) {
			if (other.gjahr != null)
				return false;
		} else if (!gjahr.equals(other.gjahr))
			return false;
		if (mandt == null) {
			if (other.mandt != null)
				return false;
		} else if (!mandt.equals(other.mandt))
			return false;
		return true;
	}
	
}
