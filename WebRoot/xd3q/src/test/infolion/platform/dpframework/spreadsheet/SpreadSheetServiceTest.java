/*
 * @(#)SpreadSheetServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2010-3-8
 *  描　述：创建
 */

package test.infolion.platform.dpframework.spreadsheet;

import org.springframework.beans.factory.annotation.Autowired;

import com.infolion.XDSS.budget.sheet.service.BudgetSheetService;
import com.infolion.platform.spreadsheet.flex.ViewDef;

import test.infolion.platform.core.service.ServiceTest;

public class SpreadSheetServiceTest extends ServiceTest
{
	@Autowired
	private BudgetSheetService spreadSheetService;

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

	public void testGetViewDef()
	{
		String budsortid = "4028a760272c5fed01272c6417bc0008";
		ViewDef viewDef = spreadSheetService.getViewDef(budsortid, false);
		System.out.println(viewDef);
	}

	public void setSpreadSheetService(BudgetSheetService spreadSheetService)
	{
		this.spreadSheetService = spreadSheetService;
	}

	public BudgetSheetService getSpreadSheetService()
	{
		return spreadSheetService;
	}

}
