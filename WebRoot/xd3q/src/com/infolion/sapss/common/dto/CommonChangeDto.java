package com.infolion.sapss.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.infolion.platform.console.workflow.dto.TaskHisDto;

public class CommonChangeDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String changeId;

	private String changeDesc;
	
	private String changeNo;
	
	private String changeTime;
	
	private List<TaskHisDto> tasklist = new ArrayList<TaskHisDto>();

	public String getChangeDesc() {
		return changeDesc;
	}

	public void setChangeDesc(String changeDesc) {
		this.changeDesc = changeDesc;
	}

	public String getChangeNo() {
		return changeNo;
	}

	public void setChangeNo(String changeNo) {
		this.changeNo = changeNo;
	}

	public String getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
	}

	public List<TaskHisDto> getTasklist() {
		return tasklist;
	}

	public void setTasklist(List<TaskHisDto> tasklist) {
		this.tasklist = tasklist;
	}

	public String getChangeId() {
		return changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

}
