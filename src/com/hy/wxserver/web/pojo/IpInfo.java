/*
 * 文件名：IpInfo.java
 * 版权：Copyright 2011-2018 Kerrunt Tech. Co. Ltd. All Rights Reserved. 
 * 描述：KURRENT系统系列
 */
package com.hy.wxserver.web.pojo;
/**
 * 修改人： Heshaohua
 * 修改时间：2015年9月15日 下午12:30:40 
 * 修改内容：新增 
 * 类说明：
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
