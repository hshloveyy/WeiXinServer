/*
 * @(#)ClearVoucherItemStruct.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2010-7-28
 *  描　述：创建
 */

package com.infolion.xdss3.payment.domain;

import java.math.BigDecimal;

public class ClearVoucherItemStruct
{
	private String voucherno;
	private String rowno;
	private String year;
	private BigDecimal bwbje;
	private String subject;
	private String subjectdescribe;
	private String businessitemid;
	private String voucherid;
	
	public String getSubjectdescribe()
	{
		return subjectdescribe;
	}
	public void setSubjectdescribe(String subjectdescribe)
	{
		this.subjectdescribe = subjectdescribe;
	}
	public String getSubject()
	{
		return subject;
	}
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	public BigDecimal getBwbje()
	{
		return bwbje;
	}
	public void setBwbje(BigDecimal bwbje)
	{
		this.bwbje = bwbje;
	}
	public String getVoucherno()
	{
		return voucherno;
	}
	public void setVoucherno(String voucherno)
	{
		this.voucherno = voucherno;
	}
	public String getRowno()
	{
		return rowno;
	}
	public void setRowno(String rowno)
	{
		this.rowno = rowno;
	}
	public String getYear()
	{
		return year;
	}
	public void setYear(String year)
	{
		this.year = year;
	}
	public String getBusinessitemid() {
		return businessitemid;
	}
	public void setBusinessitemid(String businessitemid) {
		this.businessitemid = businessitemid;
	}
    /**
     * @return the voucherid
     */
    public String getVoucherid() {
        return voucherid;
    }
    /**
     * @param voucherid the voucherid to set
     */
    public void setVoucherid(String voucherid) {
        this.voucherid = voucherid;
    }
	
}
