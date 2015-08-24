package com.infolion.xdss3.payment.domain;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "T_PICK_LIST_INFO")
public class PlickListInfo {
	@Id
	@Column(name = "PICK_LIST_ID")
	private String pickListID;

	@Column(name = "PICK_LIST_NO")
	private String plickListNo;

	@Column(name = "ARRIVAL_DATE")
	private String arrivalDate;// 预计到货日

	@Column(name = "PAY_DATE")
	private String payDate;// （即期/承兑到期）付款日

	@Column(name = "WRITE_LIST_NO")
	private String writeListNo;// 核销单号

	@Column(name = "CURRENCY_ID")
	private String currencyId;// 实际币别

	@Column(name = "ISSUING_BANK")
	private String issuingBank;

	public String getIssuingBank() {
		return issuingBank;
	}

	public void setIssuingBank(String issuingBank) {
		this.issuingBank = issuingBank;
	}

	@Transient
	private String totalCurrency;

	public String getTotalCurrency() {
		if (paymentImportMaterials != null && paymentImportMaterials.size() > 0) {
			BigDecimal tCurrency = new BigDecimal(0);
			for (PaymentImportMaterial pm : paymentImportMaterials) {
				tCurrency = tCurrency.add(new BigDecimal(pm.getTotalVaule()));
			}
			this.totalCurrency = tCurrency.toString();
		}
		return totalCurrency;
	}

	public void setTotalCurrency(String totalCurrency) {
		this.totalCurrency = totalCurrency;
	}

	@OneToMany(mappedBy = "plickListInfo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private Set<PaymentImportMaterial> paymentImportMaterials;

	public Set<PaymentImportMaterial> getPaymentImportMaterials() {
		return paymentImportMaterials;
	}

	public void setPaymentImportMaterials(
			Set<PaymentImportMaterial> paymentImportMaterials) {
		this.paymentImportMaterials = paymentImportMaterials;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getWriteListNo() {
		return writeListNo;
	}

	public void setWriteListNo(String writeListNo) {
		this.writeListNo = writeListNo;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getPlickListNo() {
		return plickListNo;
	}

	public void setPlickListNo(String plickListNo) {
		this.plickListNo = plickListNo;
	}

	public String getPickListID() {
		return pickListID;
	}

	public void setPickListID(String pickListID) {
		this.pickListID = pickListID;
	}
}
