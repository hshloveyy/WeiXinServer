/*
 * @(#)BusinessLogServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-5-26
 *  描　述：创建
 */

package test.infolion.platform.basicApp.authManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.basicApp.authManage.domain.BusinessLog;
import com.infolion.platform.basicApp.authManage.service.BusinessLogService;


/**
 * <pre>
 * 业务日志测试类
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
public class BusinessLogServiceTest extends JdbcServiceTest
{

	@Autowired
	private BusinessLogService businessLogService;

	public void setBusinessLogService(BusinessLogService businessLogService)
	{
		this.businessLogService = businessLogService;
	}
    
	public void testSave()
	{
		BusinessLog businessLog = new BusinessLog();
		List<BusinessLog> list = new ArrayList<BusinessLog>();
		//开始填入测试数据：
		businessLog.setClient("1");
		businessLog.setLogId("3");
		businessLog.setLogMessage("业务日志测试，次描述由单元测试工具生成，请注意查收！！");
		businessLog.setLogType("1");
		businessLog.setModelId("SystemManagerModel");
		businessLog.setModelName("系统管理模块");
		businessLog.setOperateTime("20090530011212");
		businessLog.setOperRecord("1");
		businessLog.setOperTable("YSYSTEMMANAGER");
		businessLog.setUserId("2");
		businessLog.setUserName("业务日志测试用户");
	
		this.businessLogService.save(businessLog) ;
		
		list = this.businessLogService.getBusinessLogs();
		Iterator< BusinessLog > itList = list.iterator();
		Integer i = 1;
		
		System.out.println("S---开始测试业务日志----------------------------------------------------");

		while(itList.hasNext())
		{
			System.out.println("第" + i.toString() + " 笔  数据 ：");
			
			BusinessLog businessLogTemp = itList.next();
			System.out.println("businessLog.getClient:" + businessLogTemp.getClient());
			System.out.println("businessLog.getLogId:" + businessLogTemp.getLogId());
			System.out.println("businessLog.getLogMessage:" + businessLogTemp.getLogMessage());
			System.out.println("businessLog.getLogType:" + businessLogTemp.getLogType());
			System.out.println("businessLog.getModelId:" + businessLogTemp.getModelId());
			System.out.println("businessLog.getModelName:" + businessLogTemp.getModelName());
			System.out.println("businessLog.getOperateTime:" + businessLogTemp.getOperateTime());
			System.out.println("businessLog.getOperRecord:" + businessLogTemp.getOperRecord());
			System.out.println("businessLog.getOperTable:" + businessLogTemp.getOperTable());
			System.out.println("businessLog.getUserId:" + businessLogTemp.getUserId());
			System.out.println("businessLog.getUserId:" + businessLogTemp.getUserName());
			
			i = i + 1;
		}
		
		System.out.println("E---结束测试业务日志----------------------------------------------------");
	
	}

}
