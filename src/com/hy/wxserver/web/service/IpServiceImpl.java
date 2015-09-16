/*
 * �ļ�����IpServiceImpl.java
 * ��Ȩ��Copyright 2011-2018 Kerrunt Tech. Co. Ltd. All Rights Reserved. 
 * ������KURRENTϵͳϵ��
 */
package com.hy.wxserver.web.service;

import java.util.List;

import com.hy.wxserver.web.dao.IPInfoDao;
import com.hy.wxserver.web.pojo.IpInfo;

/**
 * �޸��ˣ� Heshaohua
 * �޸�ʱ�䣺2015��9��16�� ����11:53:41 
 * �޸����ݣ����� 
 * ��˵����
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
