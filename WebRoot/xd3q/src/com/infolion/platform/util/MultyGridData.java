/*
 * @(#)GridJsonData.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：黄虎
 *  时　间：2009-05-19
 *  描　述：创建
 */
package com.infolion.platform.util;

import java.io.Serializable;

/**
 * 
 * <pre></pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 黄虎
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class MultyGridData implements Serializable
{

	private static final long serialVersionUID = 2382373919516352009L;
	/**
	 * grid数据
	 */
	private Object[] data;
	/**
	 * 总记录条数
	 */
	private Object[] data2;

	public Object[] getData()
	{
		return data;
	}

	public void setData(Object[] data)
	{
		this.data = data;
	}

	public Object[] getData2() {
		return data2;
	}

	public void setData2(Object[] data2) {
		this.data2 = data2;
	}

}
