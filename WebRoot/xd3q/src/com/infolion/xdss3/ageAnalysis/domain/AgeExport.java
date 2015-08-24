package com.infolion.xdss3.ageAnalysis.domain;

public class AgeExport {
	private String mandt;
	private String vbeln;
	private String expires_date;
	
	public AgeExport() {
		mandt="800";
		vbeln="";
		expires_date="";
	}
	public String getMandt() {
		return mandt;
	}
	public void setMandt(String mandt) {
		this.mandt = mandt;
	}
	public String getVbeln() {
		return vbeln;
	}
	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}
	public String getExpires_date() {
		return expires_date;
	}
	public void setExpires_date(String expires_date) {
		this.expires_date = expires_date;
	}
	
}
