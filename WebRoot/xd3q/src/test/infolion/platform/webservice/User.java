/*
 * @(#)User.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-3-10
 *  描　述：创建
 */

package test.infolion.platform.webservice;

import java.io.IOException;
import java.io.Serializable;

public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;
	private String userName;
	private String passWord;

	/**
	 * @return the userId
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	/**
	 * @return the passWord
	 */
	public String getPassWord()
	{
		return passWord;
	}

	/**
	 * @param passWord
	 *            the passWord to set
	 */
	public void setPassWord(String passWord)
	{
		this.passWord = passWord;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{

	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
	}
}
