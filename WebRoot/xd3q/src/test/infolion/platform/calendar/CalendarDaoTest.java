/*
 * @(#)CalendarDaoTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2009-12-1
 *  描　述：创建
 */

package test.infolion.platform.calendar;

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.domain.Calendar;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.platform.calendar.service.CalendarService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;

import test.infolion.platform.core.service.ServiceTest;

public class CalendarDaoTest extends ServiceTest
{

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

	public void testA()
	{
		System.out.println(EasyApplicationContextUtils.getBeanByName("sysParams").getClass());
		Map map = (Map) EasyApplicationContextUtils.getBeanByName("sysParams");
	}

}
