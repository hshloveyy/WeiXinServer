/*
 * @(#)QueryConditionServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-5-7
 *  描　述：创建
 */

package test.infolion.platform.basicApp.authManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.basicApp.authManage.UserContextHolder;
import com.infolion.platform.basicApp.authManage.domain.AuthResource;
import com.infolion.platform.basicApp.authManage.domain.DataAUTHXobjectValue;
import com.infolion.platform.basicApp.authManage.domain.MenuTree;
import com.infolion.platform.basicApp.authManage.domain.Role;
import com.infolion.platform.basicApp.authManage.domain.User;
import com.infolion.platform.basicApp.authManage.domain.UserContext;
import com.infolion.platform.basicApp.authManage.service.UserService;
import com.infolion.platform.basicApp.index.domain.Favorites;

/**
 * 
 * <pre>
 * 业务对象服务测试类
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
public class UserServiceTest extends JdbcServiceTest
{
	
	@Autowired
	private UserService userService;

	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}

	
	@Test
	public void testGetRoles() throws SQLException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Set<Role>  roles = null;
		String userId = "1";
		
		roles = this.userService.getRoles(userId);
	
		//Iterator<AuthResource> itAuthResource = allAuthResources.iterator();
		System.out.println("S---开始打印用户所有权限资源列表----------------------------------------------------------");

		Iterator<Role> itAllAuthResourceList = roles.iterator();

		Integer i = 1;
		while (itAllAuthResourceList.hasNext())
		{
			Role role = itAllAuthResourceList.next();
			System.out.println("role.getClient :" + role.getClient() + "");
			System.out.println("role.getRoleDesc :" + role.getRoleDesc());
			System.out.println("role.getRoleId :" + role.getRoleId());
			i = i + 1;
		}

		System.out.println("E---结束打印用户所有权限资源列表----------------------------------------------------------");

	}
	/**
	 * 测试 UserService.testGetUserAuths(userId)方法。
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	public void testGetUserAuths() throws SQLException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
//		Set<AuthResource>  allAuthResources = null;
//		String userId = "1";
//		User user = this.userService.getUser(userId);
//		allAuthResources = this.userService.getUserAuths(user);
//	
//		//Iterator<AuthResource> itAuthResource = allAuthResources.iterator();
//		System.out.println("S---开始打印用户所有权限资源列表----------------------------------------------------------");
//
//		Iterator<AuthResource> itAllAuthResourceList = allAuthResources.iterator();
//
//		Integer i = 1;
//		while (itAllAuthResourceList.hasNext())
//		{
//			System.out.println("第" + i.toString() + " 笔  资源权限 ：");
//
//			AuthResource authResource = itAllAuthResourceList.next();
//			System.out.println("authResource.getAuthResourceDesc :" + authResource.getAuthResourceDesc() + "");
//			System.out.println("authResource.getAuthResourceId :" + authResource.getAuthResourceId());
//			System.out.println("authResource.getAuthResourceType :" + authResource.getAuthResourceType());
//			System.out.println("authResource.getMethodDesc :" + authResource.getMethodDesc());
//			System.out.println("authResource.getMethodType :" + authResource.getMethodType());
//			System.out.println("authResource.getObject :" + authResource.getObject());
//			System.out.println("authResource.getObjectDesc :" + authResource.getObjectDesc());
//			System.out.println("authResource.getObjectType :" + authResource.getObjectType());
//			System.out.println("authResource.getParentAuthResourceId :" + authResource.getParentAuthResourceId());
//			System.out.println("authResource.getTcode :" + authResource.getTcode());
//			System.out.println("authResource.getUrl :" + authResource.getUrl());
//			i = i + 1;
//		}
//
//		System.out.println("E---结束打印用户所有权限资源列表----------------------------------------------------------");

	}
	/**
	 * 测试 UserService.getUserMenuTrees(userId)方法
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Test
	public void testGetUserMenuTrees() throws SQLException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Set<MenuTree>  allMenuTreeS = null;
		String userId = "1";
		User user = this.userService.getUser(userId);
		allMenuTreeS = this.userService.getUserMenuTrees(user);
	
		Iterator<MenuTree> itMenuTree = allMenuTreeS.iterator();
	
		System.out.println("S---开始打印用户目录树----------------------------------------------------------");

		Integer i = 1;
		while(itMenuTree.hasNext())
		{
			MenuTree menuTree = itMenuTree.next();
			System.out.println("第" + i.toString() + " 笔 树：");
			
			System.out.println("menuTree.getNodeId :" + menuTree.getNodeId());
			System.out.println("menuTree.getNodeDesc :" + menuTree.getNodeDesc());
			System.out.println("menuTree.getNodeType :" + menuTree.getNodeType());
			System.out.println("menuTree.getParentNodeId :" + menuTree.getParentNodeId());
			System.out.println("menuTree.getRefMenuId :" + menuTree.getRefMenuId());
			System.out.println("menuTree.getOrderNo :" + menuTree.getOrderNo());
			System.out.println("menuTree.getIcon :" + menuTree.getIcon());                
			//System.out.println("menuTree.getAuthResource().getAuthResourceDesc() :" + menuTree.getAuthResource().getAuthResourceDesc());
			i = i + 1 ;
			System.out.println("                                                    ");  
		}
		
		System.out.println("E---开始打印用户目录树----------------------------------------------------------");

	}
	
	public void testCheckUser()
	{
		String strUserName = "admin";
		String strPassword = "admin";
		JSONObject jo = new JSONObject();
		String jsontext = "";
		String client ="800";
		jo = this.userService.checkUser(strUserName, strPassword,client);	
		
		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
		
		Set<AuthResource> allAuthResourceList = userContext.getMyResource();
		
		System.out.println(jo.toString());
		
		System.out.println("S---开始测试用户上下文信息----------------------------------------------------------");

		Iterator<AuthResource> itAllAuthResourceList = allAuthResourceList.iterator();

		Integer i = 1;
		while (itAllAuthResourceList.hasNext())
		{
			System.out.println("第" + i.toString() + " 笔  用户上下文  资源权限  ：");

			AuthResource authResource = itAllAuthResourceList.next();
			System.out.println("authResource.getAuthResourceDesc :" + authResource.getAuthResourceDesc() + "");
			System.out.println("authResource.getAuthResourceId :" + authResource.getAuthResourceId());
			System.out.println("authResource.getAuthResourceType :" + authResource.getAuthResourceType());
			System.out.println("authResource.getMethodDesc :" + authResource.getMethodDesc());
			System.out.println("authResource.getMethodType :" + authResource.getMethodType());
			System.out.println("authResource.getObject :" + authResource.getObject());
			System.out.println("authResource.getObjectDesc :" + authResource.getObjectDesc());
			System.out.println("authResource.getObjectType :" + authResource.getObjectType());
			System.out.println("authResource.getParentAuthResourceId :" + authResource.getParentAuthResourceId());
			System.out.println("authResource.getTcode :" + authResource.getTcode());
			System.out.println("authResource.getUrl :" + authResource.getUrl());
			i = i + 1;
		}

		System.out.println("E---结束测试用户上下文信息----------------------------------------------------------");
		
	}
	
	/**
	 * 测试 用户权限转移
	 */
	public void testGetUserAuthMoveAuthResourceList()
	{
		Set<AuthResource>  allAuthResources = null;
		String userId = "1";
		User user =  this.userService.getUser(userId);
		
		allAuthResources = this.userService.getOtherUserAuthMoveAuthResources(user);
		Iterator<AuthResource> itAllAuthResourceList = allAuthResources.iterator();

		System.out.println("S---开始测试用户权限转移----------------------------------------------------------");

		Integer i = 1;
		while (itAllAuthResourceList.hasNext())
		{
			System.out.println("第" + i.toString() + " 笔  用户权限转移 ：");

			AuthResource authResource = itAllAuthResourceList.next();
			System.out.println("authResource.getAuthResourceDesc :" + authResource.getAuthResourceDesc() + "");
			System.out.println("authResource.getAuthResourceId :" + authResource.getAuthResourceId());
			System.out.println("authResource.getAuthResourceType :" + authResource.getAuthResourceType());
			System.out.println("authResource.getMethodDesc :" + authResource.getMethodDesc());
			System.out.println("authResource.getMethodType :" + authResource.getMethodType());
			System.out.println("authResource.getObject :" + authResource.getObject());
			System.out.println("authResource.getObjectDesc :" + authResource.getObjectDesc());
			System.out.println("authResource.getObjectType :" + authResource.getObjectType());
			System.out.println("authResource.getParentAuthResourceId :" + authResource.getParentAuthResourceId());
			System.out.println("authResource.getTcode :" + authResource.getTcode());
			System.out.println("authResource.getUrl :" + authResource.getUrl());
			i = i + 1;
		}

		System.out.println("E---结束测试用户权限转移----------------------------------------------------------");

	}
	
	/**
	 * 测试权限转移 数据权限 部分：
	 */
	public void testGetUserAuthMoveDataAUTHXobjectValueList()
	{
		// 所有数据权限对象集合
		String userId = "1";
		User user =  this.userService.getUser(userId);
		
		Set<DataAUTHXobjectValue> allDataAUTHXobjectValueList = new HashSet<DataAUTHXobjectValue>();
		allDataAUTHXobjectValueList = this.userService.getUserAuthMoveDataAUTHXobjectValues(user);
		
		Iterator<DataAUTHXobjectValue> itDataAUTHXobjectValues = allDataAUTHXobjectValueList.iterator();

		System.out.println("S---开始测试用户权限转移   数据权限部分----------------------------------------------------------");

		Integer i = 1;
		while (itDataAUTHXobjectValues.hasNext())
		{
			System.out.println("第" + i.toString() + " 笔  用户权限转移 ：");

			DataAUTHXobjectValue dataAUTHXobjectValue = itDataAUTHXobjectValues.next();
			
			System.out.println("authResource.getClient :" + dataAUTHXobjectValue.getClient());
			System.out.println("authResource.getDataAUTHField :" + dataAUTHXobjectValue.getDataAUTHField());
			System.out.println("authResource.getDataAUTHXobjectValueId :" + dataAUTHXobjectValue.getDataAUTHXobjectValueId());
			System.out.println("authResource.getRoleId :" + dataAUTHXobjectValue.getRoleId());
			System.out.println("authResource.getValue :" + dataAUTHXobjectValue.getValue());
			
			i = i + 1;
		}

		System.out.println("E---结束测试用户权限转移  数据权限部分----------------------------------------------------------");

	}
	
	
	/**
	 * 测试权限转移 数据权限 部分：
	 */
	public void testGetUserDataAUTHXobjectValues()
	{
		// 所有数据权限对象集合
		String userId = "1";
		User user =  this.userService.getUser(userId);
		
		Set<DataAUTHXobjectValue> allDataAUTHXobjectValueList = new HashSet<DataAUTHXobjectValue>();
		allDataAUTHXobjectValueList = this.userService.getUserDataAUTHXobjectValues(user);
		
		Iterator<DataAUTHXobjectValue> itDataAUTHXobjectValues = allDataAUTHXobjectValueList.iterator();

		System.out.println("S---开始打印用户数据授权对象集合----------------------------------------------------------");

		Integer i = 1;
		while (itDataAUTHXobjectValues.hasNext())
		{
			System.out.println("第" + i.toString() + " 笔  用户权限转移 ：");

			DataAUTHXobjectValue dataAUTHXobjectValue = itDataAUTHXobjectValues.next();
			
			System.out.println("authResource.getClient :" + dataAUTHXobjectValue.getClient());
			System.out.println("authResource.getDataAUTHField :" + dataAUTHXobjectValue.getDataAUTHField());
			System.out.println("authResource.getDataAUTHXobjectValueId :" + dataAUTHXobjectValue.getDataAUTHXobjectValueId());
			System.out.println("authResource.getRoleId :" + dataAUTHXobjectValue.getRoleId());
			System.out.println("authResource.getValue :" + dataAUTHXobjectValue.getValue());
			
			i = i + 1;
		}

		System.out.println("E---开始打印用户数据授权对象集合----------------------------------------------------------");

	}
	
	/**
	 * 测试用户收藏夹
	 */
	public void testGetFavorites()
	{
		// 所有数据权限对象集合
		String userId = "1";
		User user =  this.userService.getUser(userId);
		
		Set<Favorites> allFavoritesList = new HashSet<Favorites>();
		allFavoritesList = user.getFavorites();
		
		if(allFavoritesList != null)
		{

			Iterator<Favorites> itFavorites = allFavoritesList.iterator();

			System.out.println("S---开始打印用户收藏夹对象集合----------------------------------------------------------");

			Integer i = 1;
			while (itFavorites.hasNext())
			{
				Favorites favorites = itFavorites.next();
				if(favorites!= null)
				{
					System.out.println("第" + i.toString() + " 笔  用户收藏夹对象：");
					System.out.println("favorites.getClient :" + favorites.getClient());
					System.out.println("favorites.getFavoritesId :" + favorites.getFavoritesId());
					System.out.println("favorites.getNodeDesc :" + favorites.getNodeDesc());
					System.out.println("favorites.getNodeId :" + favorites.getNodeId());
					System.out.println("favorites.getNodeType :" + favorites.getNodeType());
					System.out.println("favorites.getOrderNo :" + favorites.getOrderNo());
					System.out.println("favorites.getUserId :" + favorites.getUserId());
					//System.out.println("favorites.getUserId :" + favorites.getUser().getUserId() + ": " + favorites.getUser().getUserName());
					i = i + 1;
				}
			}

			System.out.println("E---开始打印用户收藏夹对象集合----------------------------------------------------------");
		}
	}

	
//	/**
//	 * 测试 UserService.getUserAuthResources(userId)方法
//	 * @throws SQLException
//	 * @throws ClassNotFoundException
//	 * @throws SecurityException
//	 * @throws NoSuchMethodException
//	 * @throws IllegalArgumentException
//	 * @throws IllegalAccessException
//	 * @throws InvocationTargetException
//	 */
//	@Test
//	public void testGetUserRoleMethodAuthResources() throws SQLException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
//	{
//		Set<AuthResource>  allAuthResources = null;
//		String userId = "1";
//		
//		allAuthResources = this.userService.getUserRoleMethodAuthResources(userId);
//	
//		Iterator<AuthResource> itAuthResource = allAuthResources.iterator();
//	
//		System.out.println("S---开始打印用户用户方法资源权限列表----------------------------------------------------------");
//
//		Integer i = 1;
//		while(itAuthResource.hasNext())
//		{
//			AuthResource authResource = itAuthResource.next();
//			System.out.println("第" + i.toString() + " 笔 树：");
//			
//			System.out.println("authResource.getAuthResourceDesc :" + authResource.getAuthResourceDesc());
//			System.out.println("authResource.getAuthResourceId :" + authResource.getAuthResourceId());
//			System.out.println("authResource.getAuthResourceType :" + authResource.getAuthResourceType());
//			System.out.println("authResource.getMethodDesc :" + authResource.getMethodDesc());
//			System.out.println("authResource.getMethodType :" + authResource.getMethodType());
//			System.out.println("authResource.getObject :" + authResource.getObject());
//			System.out.println("authResource.getObjectDesc :" + authResource.getObjectDesc());
//			System.out.println("authResource.getObjectType :" + authResource.getObjectType());
//			System.out.println("authResource.getParentAuthResourceid :" + authResource.getParentAuthResourceid());
//			System.out.println("authResource.getTcode :" + authResource.getTcode());
//			System.out.println("authResource.getUrl :" + authResource.getUrl());
//			 
//			i = i + 1 ;
//			System.out.println("                                                    ");
//		}
//		
//		System.out.println("E---结合打印用户用户方法资源权限列表----------------------------------------------------------");
//
//	}
//
//	@Test
//	public void testGetUserDataAUTHXobjects() throws SQLException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
//	{
//		Set<DataAUTHXobject>  allDataAUTHXobjects = null;
//		String userId = "1";
//		
//		allDataAUTHXobjects = this.userService.getUserDataAUTHXobjects(userId);
//		
//
//		Iterator<DataAUTHXobject> itDataAUTHXobject = allDataAUTHXobjects.iterator();
//	
//		System.out.println("S---开始打印用户数据授权对象集合----------------------------------------------------------");
//
//		Integer i = 1;
//		while(itDataAUTHXobject.hasNext())
//		{
//			System.out.println("第" + i.toString() + " 笔 树：");			
//			DataAUTHXobject dataAUTHXobject = itDataAUTHXobject.next();
//
//			System.out.println("dataAUTHXobject.getBNAME :" + dataAUTHXobject.getBNAME());
//			System.out.println("dataAUTHXobject.getDataAUTHXobjectName :" + dataAUTHXobject.getDataAUTHXobjectName());
//			System.out.println("dataAUTHXobject.getFIEL1 :" + dataAUTHXobject.getFIEL1());
//			System.out.println("dataAUTHXobject.getFIEL2 :" + dataAUTHXobject.getFIEL2());
//			System.out.println("dataAUTHXobject.getFIEL3 :" + dataAUTHXobject.getFIEL3());
//			System.out.println("dataAUTHXobject.getFIEL4 :" + dataAUTHXobject.getFIEL4());
//			System.out.println("dataAUTHXobject.getFIEL5 :" + dataAUTHXobject.getFIEL5());
//			System.out.println("dataAUTHXobject.getOCLSS :" + dataAUTHXobject.getOCLSS());
//			System.out.println("dataAUTHXobject.getDataAUTHXobjectValue :" + dataAUTHXobject.getDataAUTHXobjectValue());
//			
//			i = i + 1 ;
//			System.out.println("                                                    ");
//		}
//		
//		System.out.println("E---结束打印用户数据授权对象集合----------------------------------------------------------");
//
//	}
//	
//	@Test
//	public void testGetUserMenuTreesAuthResources() throws SQLException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
//	{
//		Set<AuthResource>  allAuthResources = null;
//		String userId = "1";
//		
//		allAuthResources = this.userService.getUserMenuTreesAuthResources(userId);
//	
//		Iterator<AuthResource> itAuthResource = allAuthResources.iterator();
//	
//		System.out.println("S---开始打印用户菜单树资源权限列表----------------------------------------------------------");
//
//		Integer i = 1;
//		while(itAuthResource.hasNext())
//		{
//			AuthResource authResource = itAuthResource.next();
//			System.out.println("第" + i.toString() + " 笔 树：");
//			
//			System.out.println("authResource.getAuthResourceDesc :" + authResource.getAuthResourceDesc());
//			System.out.println("authResource.getAuthResourceId :" + authResource.getAuthResourceId());
//			System.out.println("authResource.getAuthResourceType :" + authResource.getAuthResourceType());
//			System.out.println("authResource.getMethodDesc :" + authResource.getMethodDesc());
//			System.out.println("authResource.getMethodType :" + authResource.getMethodType());
//			System.out.println("authResource.getObject :" + authResource.getObject());
//			System.out.println("authResource.getObjectDesc :" + authResource.getObjectDesc());
//			System.out.println("authResource.getObjectType :" + authResource.getObjectType());
//			System.out.println("authResource.getParentAuthResourceid :" + authResource.getParentAuthResourceid());
//			System.out.println("authResource.getTcode :" + authResource.getTcode());
//			System.out.println("authResource.getUrl :" + authResource.getUrl());
//			 
//			i = i + 1 ;
//			System.out.println("                                                    ");
//		}
//		
//
//	}
//	
	@Override
	protected List<String> customConfigLocatioins()
	{
		List<String> confs = new ArrayList<String>();
		confs.add("classpath:context/infolion-cache.xml");
		confs.add("classpath:testContext/infolion-core.xml");
		return confs;
	}
}
