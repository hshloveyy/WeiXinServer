/*
 * @(#)LockServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-8-11
 *  描　述：创建
 */

package test.infolion.platform.dpframework.uicomponent.lock;

import java.lang.reflect.InvocationTargetException;

import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.uicomponent.lock.domain.LockObject;

import test.infolion.platform.core.service.JdbcServiceTest;

public class LockServiceTest extends JdbcServiceTest{
	public void testLockBOData() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		LockObject lo = new LockObject();
		lo.setTableName("");
		lo.setLockObjectId("1");
		LockService.lockBOData(lo);
	}
}
