/*
 * @(#)OutsideInterfaceTest3.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2009-9-29
 *  描　述：创建
 */

package test.infolion.platform.dpframework.outsideinteface;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//import com.infolion.CRM.masterData.domain.MaterielInfo;
//import com.infolion.CRM.masterData.domain.Partner;
//import com.infolion.CRM.masterData.domain.Product;
//import com.infolion.CRM.masterData.domain.SalesUnit;
//import com.infolion.CRM.salesManage.domain.ProductSales;
//import com.infolion.CRM.salesManage.domain.SalesOrder;
//import com.infolion.CRM.salesManage.domain.Shipment;
import com.infolion.platform.basicApp.authManage.domain.Address;
import com.infolion.platform.basicApp.authManage.domain.Personnel;
import com.infolion.platform.dpframework.outsideInterface.OutsideInterfaceUtils;
import com.infolion.platform.dpframework.outsideInterface.OutsidePersistenceService;
import com.infolion.platform.dpframework.outsideInterface.dao.B1Dao;
import com.infolion.platform.dpframework.outsideInterface.dao.DataConversionUtil;
import com.infolion.platform.dpframework.outsideInterface.domain.B1PropertiesMapping;
import com.infolion.platform.dpframework.util.DateUtils;

import test.infolion.platform.core.service.ServiceTest;

/**
 * 
 * <pre>
 * Save方法测试
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 刘俊杰
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class OutsideInterfaceTest3 extends ServiceTest
{

	public void initTest()
	{
		// 构造虚拟数据
		OutsideInterfaceUtils
				.registerBoClassName("BOneTestItem",
						"test.infolion.platform.dpframework.outsideinteface.domain.BOneTestItem");
		OutsideInterfaceUtils
				.registerBoClassName("BOneTest",
						"test.infolion.platform.dpframework.outsideinteface.domain.BOneTest");
	}

	public void testSave()
	{
		initTest();
		savePersonnel();
	}
	
//	private void saveMaterielInfo(){
//		// 构造测试数据
//		MaterielInfo materielInfo=new MaterielInfo();
//		Product product=new Product();
//		product.setTaxgroup("X1");
//		product.setMaterielInfo(materielInfo);
//		materielInfo.setProduct(product);
//		SalesUnit salesUnit=new SalesUnit();
//		salesUnit.setEachsalesnum((BigDecimal)DataConversionUtil.convert(BigDecimal.class, 1));
//		salesUnit.setNumofeachpack((BigDecimal)DataConversionUtil.convert(BigDecimal.class, 12));
//		materielInfo.setSalesUnit(salesUnit);
//		materielInfo.setMaterielname("IBM Infoprint 1312 喷墨打印机");
//		materielInfo.setDefaultprice("3500.0");
//		materielInfo.setDefpurchaseprice("2520.0");
//		materielInfo.setMaterielgroup("101");
//		materielInfo.setPurchaserefprice("2520.5");
//		materielInfo.setMemo("TTTT");
//		materielInfo.setMaterielno("A00022");
//		OutsidePersistenceService.save(materielInfo);
//		System.out.println(materielInfo);
//	}
	
//	private void savePartners(){
//		// 构造测试数据
//		Partner partner=new Partner();
//		Address address=new Address();
//		address.setFax("010 65498588");
//		address.setWebsite("www.acme.net");
//		address.setAddress("人民路");
//		partner.setAddress(address);
//		partner.setNationaltaxno("s001");
//		partner.setPartnername("北京海龙电子公司2");
//		partner.setPartnerno("V10001");
//		
//		OutsidePersistenceService.save(partner);
//		System.out.println(partner);
//		
//	}
	
	private void savePersonnel(){
		// 构造测试数据
		Personnel personnel=new Personnel();
		Address address=new Address();
		address.setAddress("望海路 11号");
		address.setCity("厦门");
		address.setPhone("13900000001");
		personnel.setAddress(address);
		personnel.setPersonnelname("刘 芳2");
		personnel.setBirthday("20091008");
		
		OutsidePersistenceService.save(personnel);
		System.out.println(personnel);
	}
	
//	private void saveSalseOrder(){
//		// 构造测试数据
//		SalesOrder salseOrder = new SalesOrder();
//		salseOrder.setCreatetime(DateUtils.getCurrTimeStr(DateUtils.DB_STORE_DATE));
//		salseOrder.setDocumentdate("20091005");
//		salseOrder.setPostingdate("20091005");
//		salseOrder.setShiptoparty("运达到");
//		salseOrder.setCurrency("RMB");
//		salseOrder.setBilltoparty("开票到");
//		salseOrder.setTaxamount((BigDecimal)DataConversionUtil.convert(BigDecimal.class, 13387.5));
//		salseOrder.setTotalamount((BigDecimal)DataConversionUtil.convert(BigDecimal.class, 92137.5));
//		salseOrder.setStatus("1");
//		salseOrder.setMemo("Based On Sales Quotations 1.");
//		salseOrder.setSoldtoparty("C20000");
//		
//		// salseOrder.setTotalamount(BigDecimal.valueOf(99.9));
//		Set<ProductSales> set = new HashSet<ProductSales>();
//		ProductSales ps;
//		ps = new ProductSales();
//		ps.setMaterielno("A00001");
//		ps.setQuantity((BigDecimal)DataConversionUtil.convert(BigDecimal.class, 5));
//		set.add(ps);
//		ps = new ProductSales();
//		ps.setMaterielno("A00002");
//		ps.setQuantity((BigDecimal)DataConversionUtil.convert(BigDecimal.class, 15));
//		set.add(ps);
//		salseOrder.setProductSales(set);
//
//		OutsidePersistenceService.save(salseOrder);
//		
//	}

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

}
