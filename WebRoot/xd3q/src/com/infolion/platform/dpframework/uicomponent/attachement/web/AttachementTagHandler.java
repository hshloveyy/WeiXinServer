/**
 * @(#) AttachementTagHandler.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 * 修订记录:
 *  1)更改者：MagicDraw V16
 *    时　间：May 14, 2009 1:04:05 PM
 *    描　述：创建
 */

package com.infolion.platform.dpframework.uicomponent.attachement.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.infolion.platform.dpframework.uicomponent.attachement.service.AttachementService;

/**
 * 
 * <pre>
 * 业务附件tag辅助类
 * </pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class AttachementTagHandler
{
	@Autowired
	private AttachementService attachementService;

	public void setAttachementService(AttachementService attachementService)
	{
		this.attachementService = attachementService;
	}
	
	/**
	 * 是否允许增加权限控制
	 * @param resource
	 * @return
	 */
	public boolean addPermissions( String resource )
	{
		return false;
	}
	
	/**
	 * 是否允许删除权限控制
	 * @param resource
	 * @return 
	 */
	public boolean deletePermissions( String resource )
	{
		return false;
	}
	
	/**
	 * 判断是否是记录创建者，如果是创建者，拥有业务记录附件的增加删除功能
	 * @param recordCreator
	 * @return 
	 */
	public boolean isRecordCreator( String recordCreator )
	{
		return false;
	}
	
//	/**
//	 * 根据业务对象ID，取得业务附件主表ID(attachementBusinessId)
//	 * @param boId
//	 * @return
//	 */
//	public String getAttachementBusinessId(String boId)
//	{
//		return this.attachementService.getAttachementBusinessId(boId);
//	}
//	
	
}
