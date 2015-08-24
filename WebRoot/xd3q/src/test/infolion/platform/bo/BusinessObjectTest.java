/*
 * @(#)QueryConditionServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-5-7
 *  描　述：创建
 */

package test.infolion.platform.bo;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.domain.BusinessObjectText;
import com.infolion.platform.bo.domain.FormColumn;
import com.infolion.platform.bo.domain.Method;
import com.infolion.platform.bo.domain.StandardMethod;
import com.infolion.platform.bo.domain.StandardMethodText;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.BusinessException;

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
public class BusinessObjectTest extends JdbcServiceTest
{
	@Autowired
	private BusinessObjectService businessObjectService;

	public void setBusinessObjectService(BusinessObjectService businessObjectService)
	{
		this.businessObjectService = businessObjectService;
	}

	@Test
	public void testGetBusinessObjectTexts() throws SQLException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		BusinessObject businessObject;
		String boId = "000000000053";
		businessObject = businessObjectService.queryBusinessObject(boId);
		businessObjectService.setBusinessObjectRef(businessObject);

		Set<BusinessObjectText> businessObjectTexts = businessObject.getBusinessObjectTexts();
		Iterator<BusinessObjectText> itTemp = businessObjectTexts.iterator();

		System.out.println("开始打印，业务对象文本：" + businessObjectTexts.size());
		while (itTemp.hasNext())
		{
			BusinessObjectText businessObjectText = itTemp.next();
			System.out.println("businessObjectText.getBoId:" + businessObjectText.getBoId());
			System.out.println("businessObjectText.getBoName:" + businessObjectText.getBoName());
			System.out.println("businessObjectText.getLanguageCode:" + businessObjectText.getLanguageCode());
			System.out.println("businessObjectText.getDescription:" + businessObjectText.getDescription());
		}

		Set<Method> methods = businessObject.getMethods();
		Iterator<Method> itTemp1 = methods.iterator();

		System.out.println("开始打印，业务对象方法：" + businessObjectTexts.size());
		while (itTemp1.hasNext())
		{
			Method method = itTemp1.next();
			StandardMethod standardMethod = method.getStandardMethod();
			Set<StandardMethodText> standardMethodTexts = standardMethod.getStandardMethodTexts();

			Iterator<StandardMethodText> itTemp2 = standardMethodTexts.iterator();
			while (itTemp2.hasNext())
			{
				StandardMethodText standardMethodText = itTemp2.next();
				System.out.println("standardMethodText.getSmId:" + standardMethodText.getSmId());
				System.out.println("standardMethodText.getLanguageCode:" + standardMethodText.getLanguageCode());
				System.out.println("standardMethodText.getSmText:" + standardMethodText.getSmText());
			}
		}

		for (FormColumn formColumn : businessObject.getFormColumnList())
		{
			System.out.println("######");
			System.out.println(formColumn.getPropertyName());
			System.out.println(formColumn.getPropertyType());
			System.out.println(formColumn.getProperty());
			System.out.println(BusinessObjectService.getBoPropertyText(businessObject, formColumn.getPropertyName()));
		}

	}

	//	
	// @Test
	// public void testGetTabsSize()
	// {
	// BusinessObject businessObject1=
	// BusinessObjectService.getBusinessObject("PurchaseOrder");
	// Integer aa = businessObject1.getTabsSize();
	// Integer bb = businessObject1.getTabsSize();
	// System.out.println("businessObject.getTabsSize():" + aa.toString()+ ";" +
	// bb.toString());
	// }
	//
	// @Test
	// public void testGetBusinessObjectFormColumns() throws SQLException,
	// ClassNotFoundException, SecurityException, NoSuchMethodException,
	// IllegalArgumentException, IllegalAccessException,
	// InvocationTargetException
	// {
	// BusinessObject businessObject;
	// BusinessObject businessObject1;
	// String boId = "000000000006";
	// businessObject = businessObjectService.queryBusinessObject(boId);
	//		
	// businessObject1 =
	// BusinessObjectService.getBusinessObject("PurchaseOrder");
	//         
	// List<FormColumn> formColumnListHeader =
	// businessObject.getFormColumnListHeader();
	// List<FormColumn> formColumnListTab =
	// businessObject.getFormColumnListTab();
	//		
	// List<FormColumn> formColumnListHeader1 =
	// businessObject1.getFormColumnListHeader();
	// List<FormColumn> formColumnListTab1 =
	// businessObject1.getFormColumnListTab();
	//		
	// System.out.println("1:" + formColumnListHeader.size());
	// System.out.println("2:" + formColumnListTab.size());
	// System.out.println("3:" + formColumnListHeader1.size());
	// System.out.println("4:" + formColumnListTab1.size());
	//		
	//
	// pintFormColumns(formColumnListHeader);
	// pintFormColumns(formColumnListHeader1);
	//		
	// businessObject.pintFormColumns();
	// businessObject1.pintFormColumns();
	// }
	//
	// /**
	// * 测试使用： 打印出当前业务对象下有BusinessObject.formColumns的所有数据:
	// *
	// */
	// public void pintFormColumns(List<FormColumn> list)
	// {
	// // 测试代码
	// Integer n22 = 1;
	//	
	// Iterator<FormColumn> itTemp = list.iterator();
	//
	// System.out.println("开始打印，数据总笔数：" + list.size());
	// while (itTemp.hasNext())
	// {
	// System.out.println("第" + n22.toString() + " 笔 formColumns ：");
	// com.infolion.platform.bo.domain.FormColumn formColumn11 = itTemp.next();
	//
	// System.out.println("formColumn.getFproId:" + formColumn11.getFproId());
	// System.out.println("formColumn.getCaNote:" + formColumn11.getCaNote());
	// System.out.println("formColumn.getRowNo:" + formColumn11.getRowNo());
	// System.out.println("formColumn.getFormRowNo:" +
	// formColumn11.getFormRowNo());
	// System.out.println("formColumn.getColumnNo:" +
	// formColumn11.getColumnNo());
	// System.out.println("formColumn.getVisibility:" +
	// formColumn11.getVisibility());
	// System.out.println("formColumn.getNullable:" +
	// formColumn11.getNullable());
	// System.out.println("formColumn.getPropertyName:" +
	// formColumn11.getPropertyName());
	// System.out.println("formColumn.getPropertyType:" +
	// formColumn11.getPropertyType());
	//
	// if (formColumn11.getProperty() != null)
	// {
	// System.out.println("formColumn.getProperty().getDicTableName():" +
	// formColumn11.getProperty().getDicTableName());
	// System.out.println("formColumn.getProperty().getDataType():" +
	// formColumn11.getProperty().getDataType());
	// System.out.println("formColumn.getProperty().getFieldText():" +
	// formColumn11.getProperty().getFieldText());
	// System.out.println("formColumn.getProperty().getProId():" +
	// formColumn11.getProperty().getProId());
	// System.out.println("formColumn.getProperty().getPropertyName():" +
	// formColumn11.getProperty().getPropertyName());
	//
	// System.out.println("formColumn.getProperty().getRollName():" +
	// formColumn11.getProperty().getRollName());
	// System.out.println("formColumn.getProperty().getTableName():" +
	// formColumn11.getProperty().getTableName());
	// }
	//
	// n22 = n22 + 1;
	// }
	//		
	// System.out.println("结束打印，数据总笔数：" + list.size());
	// }

	@Override
	protected List<String> customConfigLocatioins()
	{
		List<String> confs = new ArrayList();
		confs.add("classpath:context/infolion-cache.xml");
		confs.add("classpath:testContext/infolion-core.xml");
		return confs;
	}
}
