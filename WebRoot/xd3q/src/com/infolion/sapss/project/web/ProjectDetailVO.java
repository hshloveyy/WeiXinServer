/*
 * @(#)ProjectDetailVO.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-2-17
 *  描　述：创建
 */

package com.infolion.sapss.project.web;

import com.infolion.platform.core.annotation.ValidateRule;
import com.infolion.platform.core.domain.BaseObject;
import com.infolion.platform.core.validation.DataType;

public class ProjectDetailVO  extends BaseObject{
	private static final long serialVersionUID = 6791675185726710743L;
	private String projectId;
	@ValidateRule(dataType = DataType.STRING, label ="品名", maxLength = 200, required = true)
	private String className;
	private String spec;
	private Double no;
	@ValidateRule(dataType = DataType.NUMBER, label ="金额",maxValue="1000000",required = true)
	private String sum;
	private String shipmentPort;
	private String destinationPort;
	private String shipmentDate;
	@ValidateRule(dataType = DataType.STRING, label ="币别",required = true)
	private String currency;
	@ValidateRule(dataType = DataType.STRING, label ="汇率",required = true)
	private String exchangeRate;
	private String mask;
	private String ymatGroup;
	private String placeOfProduction;
	
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public Double getNo() {
		return no;
	}
	public void setNo(Double no) {
		this.no = no;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getShipmentPort() {
		return shipmentPort;
	}
	public void setShipmentPort(String shipmentPort) {
		this.shipmentPort = shipmentPort;
	}
	public String getDestinationPort() {
		return destinationPort;
	}
	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}
	public String getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getYmatGroup() {
		return ymatGroup;
	}
	public void setYmatGroup(String ymatGroup) {
		this.ymatGroup = ymatGroup;
	}
	public String getPlaceOfProduction() {
		return placeOfProduction;
	}
	public void setPlaceOfProduction(String placeOfProduction) {
		this.placeOfProduction = placeOfProduction;
	}
	
}
