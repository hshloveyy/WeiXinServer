/*
 * @(#)QueryConditionServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-5-7
 *  描　述：创建
 */

package test.infolion.platform.sap.sales;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import test.infolion.platform.core.service.ServiceTest;

import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.sample.sales.dao.SalesDocHibernateDao;
import com.infolion.sample.sales.domain.SalesDoc;
import com.infolion.sample.sales.domain.SalesDocItem;
import com.infolion.sample.sales.domain.SalesDocKey;

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
public class SalesDocHibernateDaoTest extends ServiceTest
{

	@Test
	public void testGetOutsideInterface()
	{
		SalesDocHibernateDao salesDocHibernateDao = (SalesDocHibernateDao) EasyApplicationContextUtils.getBeanByName("salesDocHibernateDao");
		SalesDoc salesDoc = new SalesDoc();
		SalesDocKey key = new SalesDocKey();
		key.setClient("800");
		key.setVbeln("0000005365");
		//purchasingDoc.setPurchasingDocKey(key);
		
		salesDoc = (SalesDoc) salesDocHibernateDao.get(key);
		
		System.out.println("salesDoc.getClient:" + salesDoc.getClient());
		System.out.println("salesDoc.getVbeln:" + salesDoc.getVbeln());
		System.out.println("salesDoc.getErdat:" + salesDoc.getErdat());
		System.out.println("salesDoc.getKunnr:" + salesDoc.getKunnr());
		System.out.println("salesDoc.getLastappname:" + salesDoc.getLastappname());
		System.out.println("salesDoc.getLastapptime:" + salesDoc.getLastapptime());
		System.out.println("salesDoc.getZterm:" + salesDoc.getZterm());
	
		
		System.out.println("salesDoc.getsalesDocItems().size():" + salesDoc.getSalesDocItems().size());
 
		Set<SalesDocItem> salesDocItems = salesDoc.getSalesDocItems();
		Iterator<SalesDocItem> it = salesDocItems.iterator();
		
		while(it.hasNext())
		{
			SalesDocItem salesDocItem = (SalesDocItem) it.next();
			System.out.println("salesDocItem.getClient:" + salesDocItem.getClient());
			System.out.println("salesDocItem.getVbeln:" + salesDocItem.getVbeln());
			System.out.println("salesDocItem.getPosnr:" + salesDocItem.getPosnr());
			System.out.println("salesDocItem.getVerpr:" + salesDocItem.getVerpr());
			System.out.println("salesDocItem.getArktx:" + salesDocItem.getArktx());
			System.out.println("salesDocItem.getClabs:" + salesDocItem.getClabs());
			System.out.println("salesDocItem.getKunnr:" + salesDocItem.getKunnr());
			System.out.println("salesDocItem.getKwmeng:" + salesDocItem.getKwmeng());
			System.out.println("salesDocItem.getLfimg:" + salesDocItem.getLfimg());
			System.out.println("salesDocItem.getMatnr:" + salesDocItem.getMatnr());
			System.out.println("salesDocItem.getNetwr:" + salesDocItem.getNetwr());
			System.out.println("salesDocItem.getSslab:" + salesDocItem.getSslab());
			System.out.println("salesDocItem.getVdatu:" + salesDocItem.getVdatu());
			System.out.println("salesDocItem.getWaerk:" + salesDocItem.getWaerk());
			System.out.println("salesDocItem.getWqsl:" + salesDocItem.getWqsl());
			System.out.println("salesDocItem.getWqzj:" + salesDocItem.getWqzj());			
		}
	}


	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}
}
