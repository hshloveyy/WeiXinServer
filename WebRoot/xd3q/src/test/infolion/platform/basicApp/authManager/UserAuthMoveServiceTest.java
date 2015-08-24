/*
 * @(#)UserAuthMoveServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-8-10
 *  描　述：创建
 */

package test.infolion.platform.basicApp.authManager;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.basicApp.authManage.service.UserAuthMoveService;

public class UserAuthMoveServiceTest extends JdbcServiceTest
{
	@Autowired
	private UserAuthMoveService userAuthMoveService;

	/**
	 * @param userAuthMoveService the userAuthMoveService to set
	 */
	public void setUserAuthMoveService(UserAuthMoveService userAuthMoveService)
	{
		this.userAuthMoveService = userAuthMoveService;
	}
    
	@Test
	public void testGetAllUserTaskConditionNodeId()
	{
		String  temp = "" ;
		temp = this.userAuthMoveService.getAllUserTaskConditionNodeId("'1','2'");
		String temp1 = "";
		temp1 = this.userAuthMoveService.getAllUserTaskActorNodeId("'1','2'");
		
		String temp2 = "";
		temp2 = this.userAuthMoveService.getAllUserTaskActorNodeId("'1','2'");
		
		System.out.println("取得用户所拥有的流程节点权限(动态配置)：" + temp);
		System.out.println("取得用户所拥有的流程节点权限(静态配置)：" + temp1);
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
