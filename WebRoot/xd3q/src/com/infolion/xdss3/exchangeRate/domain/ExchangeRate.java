package com.infolion.xdss3.exchangeRate.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.infolion.platform.dpframework.core.annotation.ValidateRule;
import com.infolion.platform.dpframework.core.domain.BaseObject;

@Entity
@Table(name = "YEXCHANGERATE")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class ExchangeRate  extends BaseObject{
	// fields
	/*
	 * 客户端
	 */
	@Column(name = "MANDT")
	private String client;
	
	
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid.hex")
    @Column(name="ID")
    private String id;
    
	@Column(name = "RATE_DATE")
	@ValidateRule(dataType = 8, label = "汇率时间", required = false)
	private String rate_date;
	
	@Column(name = "CURRENCY")
	@ValidateRule(dataType = 8, label = "币别", required = false)
	private String currency;
	
	@Column(name = "CURRENCY2")
	@ValidateRule(dataType = 8, label = "本位币币别", required = false)
	private String currency2;
	
	@Column(name = "BUYING_RATE")
	@ValidateRule(dataType = 0, label = "买入价", required = false)
	private BigDecimal buying_rate;
	
	@Column(name = "SELLING_RATE")
	@ValidateRule(dataType = 0, label = "卖出价", required = false)
	private BigDecimal selling_rate;
	
	@Column(name = "MIDDLE_RATE")
	@ValidateRule(dataType = 0, label = "中间价", required = false)
	private BigDecimal middle_rate;
	/**
	 * @return the client
	 */
	public String getClient() {
		return client;
	}
	/**
	 * @param client the client to set
	 */
	public void setClient(String client) {
		this.client = client;
	}
	/**
	 * @return the rate_date
	 */
	public String getRate_date() {
		return rate_date;
	}
	/**
	 * @param rate_date the rate_date to set
	 */
	public void setRate_date(String rate_date) {
		this.rate_date = rate_date;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the currency2
	 */
	public String getCurrency2() {
		return currency2;
	}
	/**
	 * @param currency2 the currency2 to set
	 */
	public void setCurrency2(String currency2) {
		this.currency2 = currency2;
	}
	/**
	 * @return the buying_rate
	 */
	public BigDecimal getBuying_rate() {
		return buying_rate;
	}
	/**
	 * @param buying_rate the buying_rate to set
	 */
	public void setBuying_rate(BigDecimal buying_rate) {
		this.buying_rate = buying_rate;
	}
	/**
	 * @return the selling_rate
	 */
	public BigDecimal getSelling_rate() {
		return selling_rate;
	}
	/**
	 * @param selling_rate the selling_rate to set
	 */
	public void setSelling_rate(BigDecimal selling_rate) {
		this.selling_rate = selling_rate;
	}
	/**
	 * @return the middle_rate
	 */
	public BigDecimal getMiddle_rate() {
		return middle_rate;
	}
	/**
	 * @param middle_rate the middle_rate to set
	 */
	public void setMiddle_rate(BigDecimal middle_rate) {
		this.middle_rate = middle_rate;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
