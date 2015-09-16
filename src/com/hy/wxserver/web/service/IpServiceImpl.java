/*
 * 文件名：IpServiceImpl.java
 * 版权：Copyright 2011-2018 Kerrunt Tech. Co. Ltd. All Rights Reserved. 
 * 描述：KURRENT系统系列
 */
package com.hy.wxserver.web.service;

import java.util.List;

import com.hy.wxserver.web.dao.IPInfoDao;
import com.hy.wxserver.web.pojo.IpInfo;

/**
 * 修改人： Heshaohua
 * 修改时间：2015年9月16日 上午11:53:41 
 * 修改内容：新增 
 * 类说明：
 */

public class IpServiceImpl implements IpService {

	private IPInfoDao ipInfoDao;
	
	@Override
	public String getDataFromIp(String ip) {
		String resultString = "";
		List<IpInfo> list = ipInfoDao.findByIP(ip);
		if(list != null && list.size() > 0){
			resultString = list.get(0).toString();
		}
		return resultString;
	}

	public void setIpInfoDao(IPInfoDao ipInfoDao) {
		this.ipInfoDao = ipInfoDao;
	}
}
