/*
 * @(#)SpreadSheetDaoTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2010-3-4
 *  描　述：创建
 */

package test.infolion.platform.dpframework.spreadsheet;

import org.springframework.beans.factory.annotation.Autowired;

import com.infolion.XDSS.budget.sheet.dao.BudgetSheetDao;
import com.infolion.platform.spreadsheet.flex.Header;
import com.infolion.platform.spreadsheet.flex.ViewDef;

import test.infolion.platform.core.service.ServiceTest;

public class SpreadSheetDaoTest extends ServiceTest
{
	@Autowired
	private BudgetSheetDao spreadSheetDao;

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

	public void testSaveOrUpdate()
	{
		ViewDef viewDef = new ViewDef();
		viewDef.setBoName("boNameAAA");
		Header header = new Header();
		header.setColNum(2);
		viewDef.setHeader(header);
		this.spreadSheetDao.saveOrUpdateViewDef("0002", "001", viewDef, "EN", 1000);
	}

	public void setSpreadSheetDao(BudgetSheetDao spreadSheetDao)
	{
		this.spreadSheetDao = spreadSheetDao;
	}

	public BudgetSheetDao getSpreadSheetDao()
	{
		return spreadSheetDao;
	}

}
