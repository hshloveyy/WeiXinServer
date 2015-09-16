package com.hy.wxserver.web.service;


/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-4-8 下午05:03:18
 * 类说明
 */
public interface IpService {

	/** 
	 * 根据ip获取地址  
	 * @param request 
	 * @return 
	 */
	public abstract String getDataFromIp(String ip);

}