package com.infolion.sapss.bapi.dto;

import java.util.List;
import java.util.Map;

public class MasterDataDto {
	
	private List<Map<String,String>> data;
	
	private List<Map<String,String>> errData;
	
	
	private String totalNumber;

	public List<Map<String, String>> getData() {
		return data;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	public String getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(String totalNumber) {
		this.totalNumber = totalNumber;
	}

	public List<Map<String, String>> getErrData() {
		return errData;
	}

	public void setErrData(List<Map<String, String>> errData) {
		this.errData = errData;
	}



}
