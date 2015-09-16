/*
 * �ļ�����IpInfo.java
 * ��Ȩ��Copyright 2011-2018 Kerrunt Tech. Co. Ltd. All Rights Reserved. 
 * ������KURRENTϵͳϵ��
 */
package com.hy.wxserver.web.pojo;
/**
 * �޸��ˣ� Heshaohua
 * �޸�ʱ�䣺2015��9��15�� ����12:30:40 
 * �޸����ݣ����� 
 * ��˵����
 */

public class IpInfo {

	private int id;
	private String ipStart;
	private String ipEnd;
	private String region;
	private String comments;
	
	public IpInfo() {
		// TODO Auto-generated constructor stub
	}

	public IpInfo(int id, String ipStart, String ipEnd, String region,
			String comments) {
		super();
		this.id = id;
		this.ipStart = ipStart;
		this.ipEnd = ipEnd;
		this.region = region;
		this.comments = comments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIpStart() {
		return ipStart;
	}

	public void setIpStart(String ipStart) {
		this.ipStart = ipStart;
	}

	public String getIpEnd() {
		return ipEnd;
	}

	public void setIpEnd(String ipEnd) {
		this.ipEnd = ipEnd;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(region);
		builder.append(" ");
		builder.append(comments);
		return builder.toString();
	}
	
	
}
