/*
 * @(#)SettleSubjectPay.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月01日 15点05分53秒
 *  描　述：创建
 */
package com.infolion.xdss3.billClear.domain;

import javax.persistence.Transient;

import com.infolion.platform.dpframework.core.domain.BaseObject;

/**
 * <pre>
 * 结算科目(SettleSubject)实体类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class SettleSubject extends BaseObject
{
	/*
	 * 结算科目1
	 */

	@Transient
	private String settlesubject1_htext;

	/*
	 * 结算科目2
	 */
	@Transient
	private String settlesubject2_htext;

	/*
	 * 结算科目1
	 */
	@Transient
	private String settlesubject3_htext;

	/*
	 * 结算科目1
	 */
	@Transient
	private String settlesubject4_htext;

	/*
	 * 成本中心
	 */
	private String costcenter1_htext;

	/*
	 * 成本中心
	 */
	@Transient
	private String costcenter2_htext;

	/*
	 * 成本中心
	 */
	@Transient
	private String costcenter3_htext;

	/*
	 * 利润中心
	 */
	@Transient
	private String profitcenter_htext;

	/*
	 * 业务范围
	 */
	@Transient
	private String depid1_htext;

	/*
	 * 业务范围
	 */
	@Transient
	private String depid2_htext;

	/*
	 * 业务范围
	 */
	@Transient
	private String depid3_htext;

	/*
	 * 业务范围
	 */
	@Transient
	private String depid4_htext;

	/*
	 * 销售订单1
	 */
	@Transient
	private String orderno1_htext;

	/*
	 * 销售订单2
	 */
	@Transient
	private String orderno2_htext;

	/*
	 * 销售订单1
	 */
	@Transient
	private String orderno3_htext;

	/*
	 * 销售订单1
	 */
	@Transient
	private String orderno4_htext;

	/**
	 * @return the settlesubject1_htext
	 */
	public String getSettlesubject1_htext()
	{
		return settlesubject1_htext;
	}

	/**
	 * @param settlesubject1_htext
	 *            the settlesubject1_htext to set
	 */
	public void setSettlesubject1_htext(String settlesubject1_htext)
	{
		this.settlesubject1_htext = settlesubject1_htext;
	}

	/**
	 * @return the settlesubject2_htext
	 */
	public String getSettlesubject2_htext()
	{
		return settlesubject2_htext;
	}

	/**
	 * @param settlesubject2_htext
	 *            the settlesubject2_htext to set
	 */
	public void setSettlesubject2_htext(String settlesubject2_htext)
	{
		this.settlesubject2_htext = settlesubject2_htext;
	}

	/**
	 * @return the settlesubject3_htext
	 */
	public String getSettlesubject3_htext()
	{
		return settlesubject3_htext;
	}

	/**
	 * @param settlesubject3_htext
	 *            the settlesubject3_htext to set
	 */
	public void setSettlesubject3_htext(String settlesubject3_htext)
	{
		this.settlesubject3_htext = settlesubject3_htext;
	}

	/**
	 * @return the settlesubject4_htext
	 */
	public String getSettlesubject4_htext()
	{
		return settlesubject4_htext;
	}

	/**
	 * @param settlesubject4_htext
	 *            the settlesubject4_htext to set
	 */
	public void setSettlesubject4_htext(String settlesubject4_htext)
	{
		this.settlesubject4_htext = settlesubject4_htext;
	}

	/**
	 * @return the costcenter1_htext
	 */
	public String getCostcenter1_htext()
	{
		return costcenter1_htext;
	}

	/**
	 * @param costcenter1_htext
	 *            the costcenter1_htext to set
	 */
	public void setCostcenter1_htext(String costcenter1_htext)
	{
		this.costcenter1_htext = costcenter1_htext;
	}

	/**
	 * @return the costcenter2_htext
	 */
	public String getCostcenter2_htext()
	{
		return costcenter2_htext;
	}

	/**
	 * @param costcenter2_htext
	 *            the costcenter2_htext to set
	 */
	public void setCostcenter2_htext(String costcenter2_htext)
	{
		this.costcenter2_htext = costcenter2_htext;
	}

	/**
	 * @return the costcenter3_htext
	 */
	public String getCostcenter3_htext()
	{
		return costcenter3_htext;
	}

	/**
	 * @param costcenter3_htext
	 *            the costcenter3_htext to set
	 */
	public void setCostcenter3_htext(String costcenter3_htext)
	{
		this.costcenter3_htext = costcenter3_htext;
	}

	/**
	 * @return the profitcenter_htext
	 */
	public String getProfitcenter_htext()
	{
		return profitcenter_htext;
	}

	/**
	 * @param profitcenter_htext
	 *            the profitcenter_htext to set
	 */
	public void setProfitcenter_htext(String profitcenter_htext)
	{
		this.profitcenter_htext = profitcenter_htext;
	}

	/**
	 * @return the depid1_htext
	 */
	public String getdepid1_htext()
	{
		return depid1_htext;
	}

	/**
	 * @param depid1_htext
	 *            the depid1_htext to set
	 */
	public void setdepid1_htext(String depid1_htext)
	{
		this.depid1_htext = depid1_htext;
	}

	/**
	 * @return the depid2_htext
	 */
	public String getdepid2_htext()
	{
		return depid2_htext;
	}

	/**
	 * @param depid2_htext
	 *            the depid2_htext to set
	 */
	public void setdepid2_htext(String depid2_htext)
	{
		this.depid2_htext = depid2_htext;
	}

	/**
	 * @return the depid3_htext
	 */
	public String getdepid3_htext()
	{
		return depid3_htext;
	}

	/**
	 * @param depid3_htext
	 *            the depid3_htext to set
	 */
	public void setdepid3_htext(String depid3_htext)
	{
		this.depid3_htext = depid3_htext;
	}

	/**
	 * @return the depid4_htext
	 */
	public String getdepid4_htext()
	{
		return depid4_htext;
	}

	/**
	 * @param depid4_htext
	 *            the depid4_htext to set
	 */
	public void setdepid4_htext(String depid4_htext)
	{
		this.depid4_htext = depid4_htext;
	}

	/**
	 * @return the orderno1_htext
	 */
	public String getOrderno1_htext()
	{
		return orderno1_htext;
	}

	/**
	 * @param orderno1_htext
	 *            the orderno1_htext to set
	 */
	public void setOrderno1_htext(String orderno1_htext)
	{
		this.orderno1_htext = orderno1_htext;
	}

	/**
	 * @return the orderno2_htext
	 */
	public String getOrderno2_htext()
	{
		return orderno2_htext;
	}

	/**
	 * @param orderno2_htext
	 *            the orderno2_htext to set
	 */
	public void setOrderno2_htext(String orderno2_htext)
	{
		this.orderno2_htext = orderno2_htext;
	}

	/**
	 * @return the orderno3_htext
	 */
	public String getOrderno3_htext()
	{
		return orderno3_htext;
	}

	/**
	 * @param orderno3_htext
	 *            the orderno3_htext to set
	 */
	public void setOrderno3_htext(String orderno3_htext)
	{
		this.orderno3_htext = orderno3_htext;
	}

	/**
	 * @return the orderno4_htext
	 */
	public String getOrderno4_htext()
	{
		return orderno4_htext;
	}

	/**
	 * @param orderno4_htext
	 *            the orderno4_htext to set
	 */
	public void setOrderno4_htext(String orderno4_htext)
	{
		this.orderno4_htext = orderno4_htext;
	}

	/**
	 * 默认构造器
	 */
	public SettleSubject()
	{
		super();
	}
}
