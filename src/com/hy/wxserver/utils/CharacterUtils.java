/*
 * 文件名：CharacterUtils.java
 * 版权：Copyright 2011-2018 Kerrunt Tech. Co. Ltd. All Rights Reserved. 
 * 描述：KURRENT系统系列
 */
package com.hy.wxserver.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 修改人： Heshaohua
 * 修改时间：2015年9月15日 下午12:58:20 
 * 修改内容：新增 
 * 类说明：
 */

public class CharacterUtils {

	public static boolean isIpAddress(String ipAddress){
		Pattern pattern = Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
		Matcher matcher = pattern.matcher(ipAddress);
		boolean b = matcher.matches();
		return b;
	}
}
