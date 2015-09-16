/*
 * �ļ�����CharacterUtils.java
 * ��Ȩ��Copyright 2011-2018 Kerrunt Tech. Co. Ltd. All Rights Reserved. 
 * ������KURRENTϵͳϵ��
 */
package com.hy.wxserver.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * �޸��ˣ� Heshaohua
 * �޸�ʱ�䣺2015��9��15�� ����12:58:20 
 * �޸����ݣ����� 
 * ��˵����
 */

public class CharacterUtils {

	public static boolean isIpAddress(String ipAddress){
		Pattern pattern = Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
		Matcher matcher = pattern.matcher(ipAddress);
		boolean b = matcher.matches();
		return b;
	}
}
