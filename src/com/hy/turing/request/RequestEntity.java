package com.hy.turing.request;

public class RequestEntity {

	private String key;
	private String info;
	private String loc;
	private String userid;
	
	public RequestEntity() {
	}
	
	public RequestEntity(String key, String info, String loc, String userid) {
		super();
		this.key = key;
		this.info = info;
		this.loc = loc;
		this.userid = userid;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
