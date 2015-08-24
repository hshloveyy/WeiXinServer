/*
 * @(#)CacheItemType.java
 * 版权声明 ：福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-11-30
 *  描　述：创建
 */
package com.infolion.platform.sys.cache.core;

/**
 * 
 * <pre>
 * 缓存项目类型
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class CacheItemType {
	/**
	 * 系统参数
	 */
	public static final int SYS_PARAM = 0;

	/**
	 * 字典表
	 */
	public static final int DICT_TABLE = 2;

	/**
	 * 自定义缓存项目(Bean形式)
	 */
	public static final int CUSTOM_BEAN_ITEM = 3;

}
