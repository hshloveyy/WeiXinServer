/*
 * @(#)AdvanceWarnRecvHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点08分37秒
 *  描　述：创建
 */
package com.infolion.xdss3.advanceWarn.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnRecv;
import com.infolion.xdss3.advanceWarnGen.dao.AdvanceWarnRecvHibernateDaoGen;


/**
 * <pre>
 * 预警对像接收人(AdvanceWarnRecv),HibernateDao 操作类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class AdvanceWarnRecvHibernateDao extends AdvanceWarnRecvHibernateDaoGen<AdvanceWarnRecv>
{
	/**
	 * 根据预警信息获得接收人对象
	 * @param warnid
	 * @return
	 */
	public List getWarnReceiver(String warnid,String createdeptid)
	{
		String hql = "from AdvanceWarnRecv where advanceWarnObject.warnid = ?" + " and CREATEDEPTID = '" +createdeptid+ "'";
		return this.find(hql,warnid);
	}
	
	/**
	 * 根据接收人id获得接收人对象
	 * @param receiverid
	 * @return
	 */
	public List getWarnReceiverByReceiverId(String receiverid)
	{
		String hql = "from AdvanceWarnRecv where receiveuserid = ?";
		return this.find(hql,receiverid);
	}
}