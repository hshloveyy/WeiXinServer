/*
 * @(#)AdvanceWarnObjectService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月13日 16点08分37秒
 *  描　述：创建
 */
package com.infolion.xdss3.advanceWarn.service;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.dpframework.language.LangConstants;
import com.infolion.platform.dpframework.language.LanguageService;
import com.infolion.platform.dpframework.util.DateUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.dpframework.uicomponent.number.service.NumberService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.lock.LockException;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.basicApp.authManage.domain.User;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.dpframework.core.service.AdvanceService;
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnInfo;
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnObject;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnObjectService;
import com.infolion.xdss3.advanceWarn.dao.AdvanceWarnCondJDBCDao;
import com.infolion.xdss3.advanceWarn.dao.AdvanceWarnInfoHibernateDao;
import com.infolion.xdss3.advanceWarn.dao.AdvanceWarnObjectHibernateDao;
import com.infolion.xdss3.advanceWarn.dao.AdvanceWarnRecvHibernateDao;
import com.infolion.xdss3.advanceWarnGen.service.AdvanceWarnObjectServiceGen;
          
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnRecv;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnRecvService;
          
import com.infolion.xdss3.advanceWarn.domain.AdvanceWarnCond;
import com.infolion.xdss3.advanceWarn.service.AdvanceWarnCondService;
import com.infolion.xdss3.mail.MailException;
import com.infolion.xdss3.mail.MailSenderInfo;
import com.infolion.xdss3.mail.SimpleMailSender;

/**
 * <pre>
 * 预警对像配置(AdvanceWarnObject)服务类
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
@Service
public class AdvanceWarnObjectService extends AdvanceWarnObjectServiceGen
{
	private Log log = LogFactory.getFactory().getLog(this.getClass());
	
	@Autowired
	private AdvanceWarnCondJDBCDao advanceWarnCondJDBCDao;
	
	public void setAdvanceWarnCondJDBCDao(AdvanceWarnCondJDBCDao advanceWarnCondJDBCDao)
	{
		this.advanceWarnCondJDBCDao = advanceWarnCondJDBCDao;
	}
	
	@Autowired
	private AdvanceWarnInfoHibernateDao advanceWarnInfoHibernateDao;
	public void AdvanceWarnInfoHibernateDao(AdvanceWarnInfoHibernateDao advanceWarnInfoHibernateDao)
	{
		this.advanceWarnInfoHibernateDao = advanceWarnInfoHibernateDao;
	}
	
	@Autowired
	private AdvanceWarnRecvHibernateDao advanceWarnRecvHibernateDao;
	public void setAdvanceWarnRecvHibernateDao(AdvanceWarnRecvHibernateDao advanceWarnRecvHibernateDao)
	{
		this.advanceWarnRecvHibernateDao = advanceWarnRecvHibernateDao;
	}
	
	/**
	 * 定时检查预警对象
	 */
	public void doAlarmCheckWork()
	{
		// 检查是否有需要再次提醒的预警信息
		doAgainWarnCheck();

		//遍历预警对象配置表
		List<AdvanceWarnObject> lstRet = this.advanceWarnObjectHibernateDao.getAll();
		
		// 接收人邮件列表
		List<String> recvMailList = new ArrayList<String>();
		recvMailList.clear();
		
		for(int i=0; i<lstRet.size(); i++)
		{
			AdvanceWarnObject warnObject = lstRet.get(i);
			
			//List receiverList = this.advanceWarnRecvHibernateDao.getWarnReceiver(warnObject.getWarnid());
			//若没有配置接收人，则不预警
//			if( receiverList == null || receiverList.size() == 0)
//			{
//				continue;
//			}
			
			// 拼装sql语句
			String sql = makeupSql(warnObject);
						
			List<Map<String,String>> alarmList = null;
			try
			{
				alarmList = this.advanceWarnCondJDBCDao.getAlarmList(warnObject.getDealfunc(), warnObject.getPrimarykey(), sql);
			}
			catch( SQLException sqlEx)
			{
				log.error("检查预警条件时出错：" + sql);
			}
			
			// 需要预警
			if( alarmList!=null && alarmList.size() > 0 )
			{
				// 遍历查询到的预警条目
				for(Map<String,String> map : alarmList)
				//for(int j=0; j<alarmList.size(); j++ )
				{			
					String strAlarmInfo = this.advanceWarnCondJDBCDao.getAlarmInfo(warnObject.getWarninfo()
																		,warnObject.getBoid(), warnObject.getPrimarykey(), map.get("primarykey"));										
					
					//为creator保存预警信息
					// 将预警信息插入预警信息表
					UserContext userContext = new UserContext();
					User user = new User();
					user.setUserId("1");
					userContext.setClient("800");	
					userContext.setUser(user);
					UserContextHolder.setCurrentContext(userContext);
					
					AdvanceWarnInfo warnInfo = new AdvanceWarnInfo();
					warnInfo.setWarnid(warnObject.getWarnid());				//预警ID
					warnInfo.setWarninfo(strAlarmInfo);						//预警提示信息
					warnInfo.setUrl(warnObject.getViewurl());				//查看URL
					warnInfo.setBoid(warnObject.getBoid());					//业务对象
					warnInfo.setObjectinstanceid(map.get("primarykey"));			//关键属性的值
					warnInfo.setReceiveuserid(map.get("creator"));	//接收人ID
					warnInfo.setState("1"); 								//状态：默认设置为在用
					warnInfo.setAgainwarntime("0");
					this.advanceWarnInfoHibernateDao.save(warnInfo);
					
					
					List receiverList = this.advanceWarnRecvHibernateDao.getWarnReceiver(warnObject.getWarnid(),map.get("deptid"));
					// 遍历接收人
					for( int iRec=0; iRec<receiverList.size(); iRec++ )
					{
						AdvanceWarnRecv warnRecv= (AdvanceWarnRecv)receiverList.get(iRec);
						
						// 判断预警接收人部门配置是否正确，不正确则不预警。
						//if(! this.advanceWarnCondJDBCDao.checkUserDepid(map.get("deptid"), warnRecv.getReceiveuserid(),warnRecv.getAdvanceWarnObject().getWarnid()) )
						//{
						//	continue;
						//}
												
						// 将预警信息插入预警信息表
						//UserContext userContext = new UserContext();
						//User user = new User();
						//user.setUserId("1");
						//userContext.setClient("800");	
						//userContext.setUser(user);
						//UserContextHolder.setCurrentContext(userContext);
						
						AdvanceWarnInfo warnMiddleInfo = new AdvanceWarnInfo();
						warnMiddleInfo.setWarnid(warnObject.getWarnid());				//预警ID
						warnMiddleInfo.setWarninfo(strAlarmInfo);						//预警提示信息
						warnMiddleInfo.setUrl(warnObject.getViewurl());				//查看URL
						warnMiddleInfo.setBoid(warnObject.getBoid());					//业务对象
						warnMiddleInfo.setObjectinstanceid(map.get("primarykey"));			//关键属性的值
						warnMiddleInfo.setReceiveuserid(warnRecv.getReceiveuserid());	//接收人ID
						warnMiddleInfo.setState("1"); 								//状态：默认设置为在用
						warnMiddleInfo.setAgainwarntime("0");
						this.advanceWarnInfoHibernateDao.save(warnMiddleInfo);
						
						/**
						 * 保存接收人邮件列表
						 */						
						if(warnRecv.getReceivetype().equals("1")) // 邮件方式
						{
							recvMailList.add(warnRecv.getReceiveaddr());
						}
					}	
					
					/**
					 * 发送邮件
					 */
					if( recvMailList.size() > 0)
					{
						MailSenderInfo mail = new MailSenderInfo();					
						mail.setHost(SimpleMailSender.getProperty(SimpleMailSender.HOST));
						mail.setFrom(SimpleMailSender.getProperty(SimpleMailSender.FROM_ADDRESS));
						mail.setTo(recvMailList.toArray(new String[recvMailList.size()]));
						mail.setAuth(true);
						mail.setUsername(SimpleMailSender.getProperty(SimpleMailSender.USER_NAME));
						mail.setPassword(SimpleMailSender.getProperty(SimpleMailSender.PASSWORD));
						mail.setSubject(strAlarmInfo);
						mail.setText(strAlarmInfo);
						mail.setUseHtml(true);

						SimpleMailSender mailSender= new SimpleMailSender();
						mailSender.sendMail(mail);
					}
					
				}							
			}
			
		}
	}
	
	/**
	 * 拼装预警条件
	 */
	private String makeupSql(AdvanceWarnObject warnObject)
	{
		Set<AdvanceWarnCond> condSet = warnObject.getAdvanceWarnCondition();
		
		List<AdvanceWarnCond> sortCondByNo = sortCondByNo(condSet);
		
		String strPrimaryKey = warnObject.getPrimarykey().substring(warnObject.getPrimarykey().indexOf("[")+1,
				warnObject.getPrimarykey().indexOf("]") );
		
		String strObjectFieldCode = warnObject.getFieldcode().substring(warnObject.getFieldcode().indexOf("[")+1,
				warnObject.getFieldcode().indexOf("]") );
		
		// 根据预警字段和预警条件拼装sql语句
		String sql = "select " + strPrimaryKey + " primarykey, DEPT_ID deptid, creator creator from " + warnObject.getBoid() 
					+ " a where a." + strPrimaryKey  + " in ( select primarykey from ("+ "select '"+ warnObject.getWarnid() +  "' warnid" + "," 
				+ strPrimaryKey + " primarykey from ";
		
		sql += warnObject.getBoid() + " tb where (";
		
		//提前（用于日期字段）
		if(warnObject.getWarnrole().equals("1"))
		{
			sql +=  "to_char(sysdate +" + warnObject.getWarnvalue() + ",'yyyymmdd')" +  
			" >= replace(" + strObjectFieldCode + ", '-', '')";
		}
		//推后（用于日期字段）
		if(warnObject.getWarnrole().equals("2"))
		{
			sql +=  "sysdate >= " + "to_date( replace(" + strObjectFieldCode + ", '-', '' )" + ",'yyyymmdd') + " 
			+  warnObject.getWarnvalue();
		}
		
		//大于(用于金额等字段)
		if(warnObject.getWarnrole().equals("3"))
		{
			sql +=  strObjectFieldCode + " > " 
			+  warnObject.getWarnvalue();
		}
		
		//小于(用于金额等字段)
		if(warnObject.getWarnrole().equals("3"))
		{
			sql +=  strObjectFieldCode + " < " 
			+  warnObject.getWarnvalue();
		}
		
		sql += " ) ";
		
		//提前（用于日期字段）超过当前时间就不用预警
		if(warnObject.getWarnrole().equals("1"))
		{
			sql +=  " and ( to_char(sysdate ,'yyyymmdd')" +  
			" <= replace(" + strObjectFieldCode + ", '-', '')";
			
			sql += " ) ";
		}	
		
		
		
		// 预警条件
		Iterator it = sortCondByNo.iterator();
		int iNum = 0;
		
		while(it.hasNext())
		{
			// 第一个条件
			if( iNum == 0 )
			{
				sql += " and ";
			}
			
			iNum++;			

			AdvanceWarnCond warnCond = (AdvanceWarnCond)it.next();
			
			String strCondFieldCode = warnCond.getFieldcode().substring(warnCond.getFieldcode().indexOf("[")+1, 
					warnCond.getFieldcode().indexOf("]"));
			
			String connStr = warnCond.getConnectcond().equals("1") ? " and " : " or ";
			String leftP = "";		// 左括号
			String rightP = "";		// 右括号
			
			if (warnCond.getLeftparentheses().equals("1") )
			{
				leftP = "(";
			}
			else if(warnCond.getLeftparentheses().equals("2") )
			{
				leftP = "((";
			}
			else if(warnCond.getLeftparentheses().equals("3") )
			{
				leftP = "(((";
			}
			
			if (warnCond.getRightparentheses().equals("1") )
			{
				rightP = ")";
			}
			else if(warnCond.getRightparentheses().equals("2") )
			{
				rightP = "))";
			}
			else if(warnCond.getRightparentheses().equals("3") )
			{
				rightP = ")))";
			}
			
			//String[] warnCondFieldcodeList = warnCond.getFieldcode().split("|");
			// 大于
			if(warnCond.getCondrole().equals("1"))
			{
				sql += leftP + strCondFieldCode 
					+ " > " + warnCond.getCondvalue();
			}
			// 大于等于
			if(warnCond.getCondrole().equals("2"))
			{
				sql += leftP + strCondFieldCode 
				+ " >= " + warnCond.getCondvalue();
			}
			// 小于
			if(warnCond.getCondrole().equals("3"))
			{
				sql += leftP + strCondFieldCode 
				+ " < " + warnCond.getCondvalue();
			}
			// 小于等于
			if(warnCond.getCondrole().equals("4"))
			{
				sql += leftP + strCondFieldCode
				+ " <= " + warnCond.getCondvalue();
			}
			// 等于
			if(warnCond.getCondrole().equals("5"))
			{
				sql += leftP + strCondFieldCode 
				+ " = " + warnCond.getCondvalue();
			}
			// in
			if(warnCond.getCondrole().equals("6"))
			{
				sql += leftP + strCondFieldCode 
				+ " in (" + warnCond.getCondvalue() + ")";
			}
			// like
			if(warnCond.getCondrole().equals("7"))
			{
				sql += leftP + strCondFieldCode 
				+ " like " + "'%"  + warnCond.getCondvalue() + "%'";
			}
			sql += rightP;
			
			// 不是最后一个条件，需要加入并列条件
			if( iNum < condSet.size() )
			{
				sql += connStr;
			}						
		}		
		
		// 判断预警字段是否为null或""
		sql += "and ( replace(" + strObjectFieldCode
			+ ", ' ', '') is not null )";
				
		// 过滤已经预警的信息
		sql += " minus select WARNID, OBJECTINSTANCEID primarykey from YADVAWARNINFO ) a )";		
		return sql;
	}


	/**
	 * 根据序号排序
	 * @param warnCondSet
	 * @return
	 */
	private List<AdvanceWarnCond> sortCondByNo( Set<AdvanceWarnCond> warnCondSet )
	{
		int iCondNum = warnCondSet.size();
		List<AdvanceWarnCond> sortedCondList = new ArrayList<AdvanceWarnCond>();
		int iIndex = 1;
		
		while( iIndex <= iCondNum )
		{
			for(AdvanceWarnCond warnCond : warnCondSet )
			{
				int condNo = Integer.parseInt(warnCond.getCondno());
				if( condNo == iIndex )
				{
					sortedCondList.add(warnCond);
					iIndex ++;
				}						
			}
		}
		return sortedCondList;
	}

	/**
	 * 遍历预警信息表，再次预警信息
	 */
	private void doAgainWarnCheck()
	{
		List<Map<String,String>> warnInfoList = this.advanceWarnCondJDBCDao.getNeedAgainWarnInfo();
		for (Map map : warnInfoList) 
		{
			AdvanceWarnInfo advanceWarnInfo = new AdvanceWarnInfo();
			ExBeanUtils.setBeanValueFromMap(advanceWarnInfo, map);
			
			// 重新设置状态为在用
			advanceWarnInfo.setState("1");
			advanceWarnInfo.setAgainwarntime("0");
			
			this.advanceWarnInfoHibernateDao.saveOrUpdate(advanceWarnInfo);
			
			// 取得预警接收人，判断是否需要邮件通知			
			List receiver = this.advanceWarnRecvHibernateDao.getWarnReceiverByReceiverId(advanceWarnInfo.getReceiveuserid());
			AdvanceWarnRecv warnRecv = (AdvanceWarnRecv)receiver.get(0);
			
			// 如果配置为邮件通知，则需再次发送邮件
			if(warnRecv.getReceivetype().equals("1")) // 邮件方式
			{
				String[] receiverMail = new String[1];
				receiverMail[0] = warnRecv.getReceiveaddr();
							
				/**
				 * 发送邮件
				 */
				MailSenderInfo mail = new MailSenderInfo();					
				mail.setHost(SimpleMailSender.getProperty(SimpleMailSender.HOST));
				mail.setFrom(SimpleMailSender.getProperty(SimpleMailSender.FROM_ADDRESS));
				mail.setTo(receiverMail);
				mail.setAuth(true);
				mail.setUsername(SimpleMailSender.getProperty(SimpleMailSender.USER_NAME));
				mail.setPassword(SimpleMailSender.getProperty(SimpleMailSender.PASSWORD));
				mail.setSubject(advanceWarnInfo.getWarninfo());
				mail.setText(advanceWarnInfo.getWarninfo());
				mail.setUseHtml(true);
				
				
				SimpleMailSender mailSender= new SimpleMailSender();
				mailSender.sendMail(mail);
				
			}			
		}	
	}
	
}