package com.infolion.xdss3.payment.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "T_PAYMENT_IMPORTS_MATERIAL")
public class PaymentImportMaterial {
	@Id
	@Column(name="PURCHASE_ROWS_ID")
	private String purchaseRowsID;
	
	@Column(name="TOTAL_VALUE")
	private String totalVaule;

	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="PICK_LIST_ID",insertable=false,updatable=false)
	private PlickListInfo plickListInfo;
	
	public PlickListInfo getPlickListInfo() {
		return plickListInfo;
	}

	public void setPlickListInfo(PlickListInfo plickListInfo) {
		this.plickListInfo = plickListInfo;
	}

	public String getPurchaseRowsID() {
		return purchaseRowsID;
	}

	public void setPurchaseRowsID(String purchaseRowsID) {
		this.purchaseRowsID = purchaseRowsID;
	}

	public String getTotalVaule() {
		return totalVaule;
	}

	public void setTotalVaule(String totalVaule) {
		this.totalVaule = totalVaule;
	}

	
}
