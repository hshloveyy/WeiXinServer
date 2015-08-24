/*
 * @(#)SystemMessageServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：黄登辉
 *  时　间：2009-7-23
 *  描　述：创建
 */

package test.infolion.platform.dpframework.core.systemMessage.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.dpframework.uicomponent.systemMessage.service.SystemMessageService;

public class SystemMessageServiceTest extends JdbcServiceTest
{
	@Autowired
	private SystemMessageService systemMessageService;

	/**
	 * @param systemMessageService
	 *            the systemMessageService to set
	 */
	public void setSystemMessageService(SystemMessageService systemMessageService)
	{
		this.systemMessageService = systemMessageService;
	}

	@Test
	public void testGetMessage()
	{
		List<String> messageParameters = new ArrayList<String>();
		String[] aaa = new String[10];
		for (int i = 0; i < 10; i++)
		{
			messageParameters.add("参数" + i);
			aaa[i] = "参数" + i;
		}
		
		// for (int i = 0; i < 31; i++)
		// {
		String messageNo = "001";
		// DecimalFormat df = new DecimalFormat("000");
		// messageNo = df.format(i);
		systemMessageService.getMessage("DP", "000");
		String aa = systemMessageService.getMessage("DP", messageNo, messageParameters);

		String bb = systemMessageService.getMessage("DP", messageNo, aaa);
		String cc = systemMessageService.getMessage("DP", messageNo, "canshu1", "canshu2", "canshu3");
		
		System.out.println("aa:" + aa);
		System.out.println("bb:" + bb);
		System.out.println("bb:" + cc);
		
		
	
		// }
		// throw new BusinessException("101",messageParameters);
	}

	@Override
	protected List<String> customConfigLocatioins()
	{
		List<String> confs = new ArrayList<String>();
		confs.add("classpath:context/infolion-cache.xml");
		confs.add("classpath:testContext/infolion-core.xml");
		return confs;
	}

}
