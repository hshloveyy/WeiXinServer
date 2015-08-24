/*
 * @(#)AuthenticationRuleEncodeTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：黄登辉
 *  时　间：2009-5-22
 *  描　述：创建
 */

package test.infolion.platform.basicApp.authentication;

//import static org.junit.Assert.*;

import test.infolion.platform.core.service.JdbcServiceTest;
import org.junit.Test;

import com.infolion.platform.basicApp.authManage.AuthenticationRuleEncode;

/**
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.4
 *
 * @author 黄登辉
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class AuthenticationRuleEncodeTest extends JdbcServiceTest
{

	@Test
	public void _testGetSqlCondition()
	{
		String value1 = "-1,2,3,4-7,-6--3";
		String value2 = "a,b,c,d-g,j-n";
		String value3 = "a";
		log.info(AuthenticationRuleEncode.getSqlCondition("myfield", value1));
		log.info(AuthenticationRuleEncode.getSqlCondition("myfield", value2));
		log.info(AuthenticationRuleEncode.getSqlCondition("myfield", value3));
	}
	
	@Test
	public void testGetSqlCondition2()
	{
		String value1 = " ${user.userId},";
		//String value1 = "${user.userId},";

		log.info(AuthenticationRuleEncode.getSqlCondition("myfield", value1));
	}
	

}
