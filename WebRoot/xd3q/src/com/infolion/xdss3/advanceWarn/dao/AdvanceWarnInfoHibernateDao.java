/*
 * @(#)AdvanceWarnInfoHibernateDao.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点17分58秒
 *  描　述：创建
 */
package com.infolion.xdss3.advanceWarn.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnInfo;
import com.infolion.xdss3.advanceWarnGen.dao.AdvanceWarnInfoHibernateDaoGen;


/**
 * <pre>
 * 业务预警信息(AdvanceWarnInfo),HibernateDao 操作类
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
public class AdvanceWarnInfoHibernateDao extends AdvanceWarnInfoHibernateDaoGen<AdvanceWarnInfo>
{	
	/**
	 * 办理业务预警
	 * @param warnInfoId
	 * @param againWarnDay
	 */
	public void modifyWarnInfoState(String warnInfoId, String againWarnDay )
	{
		String selHql = "from AdvanceWarnInfo advanceWarnInfo where warninfoid = ? ";
		List advanceWarnInfoList =  this.find(selHql, warnInfoId);
		AdvanceWarnInfo warnInfo = (AdvanceWarnInfo)advanceWarnInfoList.get(0);
		
		//关闭预警信息
		warnInfo.setState("2");
		warnInfo.setAgainwarntime(againWarnDay);	
		
		// 设置关闭日期
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		warnInfo.setClosetime(df.format(new Date())); // new Date()为获取当前系统时间
		
		this.saveOrUpdate(warnInfo);
	}
	
	/**
	 * 用于清除方式为发起人时，关闭相关的预警信息
	 * @param warnId
	 */
	public void closeWarnInfoByWarnId(String warnid, String primaryKey)
	{
		String hql = "from AdvanceWarnInfo advanceWarnInfo where warnid = ? and objectinstanceid = ?";
		List advanceWarnInfoList =  this.find(hql, warnid, primaryKey);
		
		for( int i = 0; i< advanceWarnInfoList.size(); i++ )
		{
			AdvanceWarnInfo warnInfo = (AdvanceWarnInfo)advanceWarnInfoList.get(i);
			//关闭预警信息
			warnInfo.setState("2");		
			
			// 设置关闭日期
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
			warnInfo.setClosetime(df.format(new Date())); // new Date()为获取当前系统时间
			
			this.saveOrUpdate(warnInfo);
		}
	}
	
}