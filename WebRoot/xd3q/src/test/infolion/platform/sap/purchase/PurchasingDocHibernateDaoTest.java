/*
 * @(#)QueryConditionServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-5-7
 *  描　述：创建
 */

package test.infolion.platform.sap.purchase;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import test.infolion.platform.core.service.ServiceTest;

import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.sample.purchase.dao.PurchasingDocHibernateDao;
import com.infolion.sample.purchase.domain.PurchasingDoc;
import com.infolion.sample.purchase.domain.PurchasingDocItem;
import com.infolion.sample.purchase.domain.PurchasingDocKey;

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
public class PurchasingDocHibernateDaoTest extends ServiceTest
{

	@Test
	public void testGetOutsideInterface()
	{
		PurchasingDocHibernateDao purchasingDocHibernateDao = (PurchasingDocHibernateDao) EasyApplicationContextUtils.getBeanByName("purchasingDocHibernateDao");
		PurchasingDoc purchasingDoc = new PurchasingDoc();
		PurchasingDocKey key = new PurchasingDocKey();
		key.setClient("800");
		key.setEbeln("4151503222");
		//purchasingDoc.setPurchasingDocKey(key);
		
		purchasingDoc = (PurchasingDoc) purchasingDocHibernateDao.get(key);
		
		System.out.println("purchasingDoc.getClient:" + purchasingDoc.getClient());
		System.out.println("purchasingDoc.getEbeln:" + purchasingDoc.getEbeln());
		System.out.println("purchasingDoc.getBukrs:" + purchasingDoc.getBukrs());
		System.out.println("purchasingDoc.getKunnr:" + purchasingDoc.getKunnr());
		System.out.println("purchasingDoc.getLastappname:" + purchasingDoc.getLastappname());
		System.out.println("purchasingDoc.getLastapptime:" + purchasingDoc.getLastapptime());
		System.out.println("purchasingDoc.getStatu:" + purchasingDoc.getStatu());
		System.out.println("purchasingDoc.getWaers:" + purchasingDoc.getWaers());
	
		
		System.out.println("purchasingDoc.getPurchasingDocItems().size():" + purchasingDoc.getPurchasingDocItems().size());
 
		Set<PurchasingDocItem> purchasingDocItems = purchasingDoc.getPurchasingDocItems();
		Iterator<PurchasingDocItem> it = purchasingDocItems.iterator();
		
		while(it.hasNext())
		{
			PurchasingDocItem purchasingDocItem = (PurchasingDocItem) it.next();
			System.out.println("purchasingDocItem.getClient:" + purchasingDocItem.getClient());
			System.out.println("purchasingDocItem.getEbeln:" + purchasingDocItem.getEbeln());
			System.out.println("purchasingDocItem.getEbelp:" + purchasingDocItem.getEbelp());
			System.out.println("purchasingDocItem.getMatnr:" + purchasingDocItem.getMatnr());
			System.out.println("purchasingDocItem.getMenge:" + purchasingDocItem.getMenge());
			System.out.println("purchasingDocItem.getNetpr:" + purchasingDocItem.getNetpr());
			System.out.println("purchasingDocItem.getNetwr:" + purchasingDocItem.getNetwr());
			System.out.println("purchasingDocItem.getTxz01:" + purchasingDocItem.getTxz01());
		}
	}


	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}
}
