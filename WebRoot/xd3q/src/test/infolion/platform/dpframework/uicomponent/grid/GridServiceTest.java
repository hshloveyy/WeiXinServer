/*
 * @(#)GridServiceTest.java
 * 版权声明： 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2009-5-12
 *  描　述：创建
 */

package test.infolion.platform.dpframework.uicomponent.grid;

import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.web.BoGridTagHandler;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;

public class GridServiceTest extends JdbcServiceTest
{
	@Autowired
	protected GridService gridService;

	public void testBuildGrid()
	{
		BoGridTagHandler handler = new BoGridTagHandler("test");
		handler.buildGrid();
		GridService gridServices = (GridService) EasyApplicationContextUtils.getBeanByName("gridService");
		this.gridService.getBoGrid("test");
	}
}
