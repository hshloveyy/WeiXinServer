/*
 * @(#)AuthenticationServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：黄登辉
 *  时　间：2009-5-25
 *  描　述：创建
 */

package test.infolion.platform.basicApp.authentication;

//import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.basicApp.authManage.service.AuthenticationService;
import com.infolion.platform.basicApp.authManage.service.UserService;
import com.infolion.platform.dpframework.util.CodeGenerator;

import test.infolion.platform.core.service.HibernateServiceTest;

public class AuthenticationServiceTest extends HibernateServiceTest
{
	@Autowired
	private UserService userService;

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	public void initUserContext()
	{
		String strUserName = "dpadmin";
		String strPassword = "ilovedp";
		String client = "800";
		this.userService.checkUser(strUserName,CodeGenerator.getMD5Digest(strPassword),client);
		UserContext userContext = UserContextHolder.getLocalUserContext()
				.getUserContext();
		log.info(userContext);
	}

	@Test
	public void testIsAttributePermission()
	{
		initUserContext();
		String resource = "YAUTEST1";
		String boName = "buyOrder";
		log.info(AuthenticationService.isAttributePermission(boName + "."
				+ resource, null));
		//log.info(AuthenticationService.isAttributePermission(boName + resource));
		//log.info(AuthenticationService.isAttributePermission("." + resource));
		log.info(AuthenticationService.isAttributePermission(resource+"."));
	}
	
	public void testIsMethodPermission()
	{
		String resource = "buyOrder.order";
		//log.info(AuthenticationService.isMethodPermission(resource));
		Pattern pattern = Pattern.compile("((/[0-9a-z_!~\\*'\\(\\)\\.;\\?:@&=\\+\\$,%#-]+)+/?)$",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(resource);
		boolean isUrl= matcher.matches();
		// resource = "/basicApp/authManage/userGroupController.spr?action=userGroupManageView";
		resource = "/xmdp//orderManage//purchaseOrder//purchaseOrderController.spr?action=_manager&menuid=91001FA6-2754-4F93-801B-25EF71D88C22";
		log.info(AuthenticationService.isMethodPermission(resource));
		pattern = Pattern.compile("((/+[0-9a-z_!~\\*'\\(\\)\\.;\\?:@&=\\+\\$,%#-]+)+/?)$",Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(resource);
		isUrl= matcher.matches();
		pattern = Pattern.compile("(/{1,2}xmdp/{1,2})?(\\S+)(&([\\-\\.\\w]+)=([\\-\\.\\w]+))+", Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(resource);
		for (; matcher.find();)
		{
			resource = matcher.group(2);
		}
	}

	@Test
	public void testGetDataAuthSql()
	{
		String boName = "buyOrder";
		log.info(AuthenticationService.getDataAuthSql(boName));
	}

	@Test
	public void testAuthObjectCheck()
	{
		String authObjectName = "YAUTEST1";
		Map<String, String> authFieldValues = new HashMap<String, String>();
		authFieldValues.put("YAUTEST1", "1");
		log.info(AuthenticationService.authObjectCheck(authObjectName,
				authFieldValues));
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
