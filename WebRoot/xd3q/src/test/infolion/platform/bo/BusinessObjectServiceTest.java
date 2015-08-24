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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.domain.MethodParameter;
import com.infolion.platform.bo.domain.Property;
import com.infolion.platform.bo.domain.ProtertyColumnMaping;
import com.infolion.platform.bo.service.BusinessObjectService;

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
public class BusinessObjectServiceTest extends JdbcServiceTest
{
	@Autowired
	private BusinessObjectService businessObjectService;

	public void setBusinessObjectService(BusinessObjectService businessObjectService)
	{
		this.businessObjectService = businessObjectService;
	}

	public void testGetBusinessObject() throws SQLException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		BusinessObject businessObject;
		String boName = "Partner"; // "NEWTESTA" "NEWTESTC" buyOrder
		businessObject = businessObjectService.getBusinessObjectByBoName(boName);

		Class newClass = Class.forName("com.infolion.platform.bo.domain.BusinessObject");
		Field[] fieldList = newClass.getDeclaredFields();

		int i = fieldList.length;

		if (i > 0)
		{
			System.out.println("S-----------------------------------------------------------------------");
			System.out.println("开始打印业务对象名称为" + boName + " 的BusinessObject业务对象的各属性的值：");
			for (int n = 0; n < i; n++)
			{
				Field field = fieldList[n];

				String fieldName = fieldList[n].getName();
				String fir = fieldName.substring(0, 1);
				int nLen = fieldName.length();
				String oth = fieldName.substring(1, nLen);

				log.debug(fir + oth + " : " + fieldName);

				String name = fir.toUpperCase() + oth;

				String type = fieldList[n].getGenericType().toString(); // 获取属性的类型

				if (type.equals("class java.lang.String"))
				{ // 如果type是类类型，则前面包含"class "，后面跟类名
					Method m = businessObject.getClass().getMethod("get" + name);
					String value = (String) m.invoke(businessObject); // 调用getter方法获取属性值
					if (value != null)
					{
						System.out.println("BusinessObject." + fieldName + " :" + value);
					}
				}
				else if (type.equals("class java.lang.Integer"))
				{
					Method m = businessObject.getClass().getMethod("get" + name);
					Integer value = (Integer) m.invoke(businessObject);
					if (value != null)
					{
						System.out.println("BusinessObject." + fieldName + " :" + value);
					}
				}
				else if (type.equals("class java.lang.Short"))
				{
					Method m = businessObject.getClass().getMethod("get" + name);
					Short value = (Short) m.invoke(businessObject);
					if (value != null)
					{
						System.out.println("BusinessObject." + fieldName + " :" + value);
					}
				}
				else if (type.equals("class java.lang.Double"))
				{
					Method m = businessObject.getClass().getMethod("get" + name);
					Double value = (Double) m.invoke(businessObject);
					if (value != null)
					{
						System.out.println("BusinessObject." + fieldName + " :" + value);
					}
				}
				else if (type.equals("class java.lang.Boolean"))
				{
					Method m = businessObject.getClass().getMethod("get" + name);
					Boolean value = (Boolean) m.invoke(businessObject);
					if (value != null)
					{
						System.out.println("BusinessObject." + fieldName + " :" + value);
					}
				}
				else if (type.equals("class java.util.Date"))
				{
					Method m = businessObject.getClass().getMethod("get" + name);
					Date value = (Date) m.invoke(businessObject);
					if (value != null)
					{
						System.out.println("BusinessObject." + fieldName + " :" + value.toLocaleString());
					}
				}
				else if (type.equals("java.util.Set<com.infolion.platform.bo.domain." + name + ">") || type.equals("java.util.Set<com.infolion.platform.bo.domain." + name.substring(0, name.length() - 1) + ">") || type.equals("java.util.Set<com.infolion.platform.bo.domain.Property>"))
				{
					System.out.println("开始遍历businessObject." + fieldName + "下的所有属性：");

					// Set<Properties> pro = businessObject.getProperties() ;
					Method m;
					if ("Property".equals(name) || name.substring(0, name.length() - 1).equals("Property"))
					{
						m = businessObject.getClass().getMethod("getProperties");
					}
					else
					{
						m = businessObject.getClass().getMethod("get" + name);
					}

					Set obj = (Set) m.invoke(businessObject);

					int nObj = obj.size();
					if (obj != null)
					{
						Iterator it = obj.iterator();
						Integer jj = 1;

						while (it.hasNext())
						{
							Object objPara = it.next();

							Class paraClass;
							try
							{
								paraClass = Class.forName("com.infolion.platform.bo.domain." + name);
							}
							catch (Exception e)
							{
								try
								{
									paraClass = Class.forName("com.infolion.platform.bo.domain." + name.substring(0, name.length() - 1));
								}
								catch (Exception e1)
								{
									paraClass = Class.forName("com.infolion.platform.bo.domain.Property");
								}
							}

							// paraClass = (Class) it.next();

							Field[] fieldList1 = paraClass.getDeclaredFields();

							int ii = fieldList1.length;

							if (i > 0)
							{
								System.out.println("@@@@-----------------------------------------------------------------------");
								System.out.println(" 第" + jj.toString() + " 笔   BussinessObject." + name + "数据：");
								// System.out.println("    开始打印业务对象名称为"+ boName
								// +" 的各属性的值：");

								for (int nn = 0; nn < ii; nn++)
								{
									Field field1 = fieldList1[nn];
									String fieldName1 = fieldList1[nn].getName();
									String fir1 = fieldName1.substring(0, 1);
									int nLen1 = fieldName1.length();
									String oth1 = fieldName1.substring(1, nLen1);

									log.debug(fir + oth1 + " : " + fieldName1);

									String name1 = fir1.toUpperCase() + oth1;

									String type1 = fieldList1[nn].getGenericType().toString(); // 获取属性的类型

									if ("BusinessObject".equals(name1))
									{
										continue;
									}

									if (type1.equals("class java.lang.String"))
									{ // 如果type是类类型，则前面包含"class "，后面跟类名
										Method m1 = objPara.getClass().getMethod("get" + name1);
										String value1 = (String) m1.invoke(objPara); // 调用getter方法获取属性值
										if (value1 != null)
										{
											System.out.println(fieldName + "." + fieldName1 + " :" + value1);
										}
									}
									else if (type1.equals("class java.lang.Integer"))
									{
										Method m1 = objPara.getClass().getMethod("get" + name1);
										Integer value1 = (Integer) m1.invoke(objPara);
										if (value1 != null)
										{
											System.out.println(fieldName + "." + fieldName1 + " :" + value1);
										}
									}
									else if (type1.equals("class java.lang.Short"))
									{
										Method m1 = objPara.getClass().getMethod("get" + name1);
										Short value1 = (Short) m1.invoke(objPara);
										if (value1 != null)
										{
											System.out.println(fieldName + "." + fieldName1 + " :" + value1);
										}
									}
									else if (type1.equals("class java.lang.Double"))
									{
										Method m1 = objPara.getClass().getMethod("get" + name1);
										Double value1 = (Double) m1.invoke(objPara);
										if (value1 != null)
										{
											System.out.println(fieldName + "." + fieldName1 + " :" + value1);
										}
									}
									else if (type1.equals("class java.lang.Boolean"))
									{
										Method m1 = objPara.getClass().getMethod("get" + name1);
										Boolean value1 = (Boolean) m1.invoke(objPara);
										if (value1 != null)
										{
											System.out.println(fieldName + "." + fieldName1 + " :" + value1);
										}
									}
									else if (type1.equals("class java.util.Date"))
									{
										Method m1 = objPara.getClass().getMethod("get" + name1);
										Date value1 = (Date) m1.invoke(objPara);
										if (value1 != null)
										{
											System.out.println(fieldName + "." + fieldName1 + " :" + value1.toLocaleString());
										}
									}
								}
							}

							jj = jj + 1;
						}
					}
				}
			}

		}

		System.out.println("E-----------------------------------------------------------------------");
		System.out.println("业务对象名称为" + boName + "的businessObject实例:" + businessObject.toString());

		System.out.println("开始打印Method对象数据:");

		Set<com.infolion.platform.bo.domain.Method> method = businessObject.getMethods();

		Iterator it1 = method.iterator();
		Integer nnnn = 1;
		while (it1.hasNext())
		{
			System.out.println("第" + nnnn.toString() + " 笔 Method对象数据：");
			com.infolion.platform.bo.domain.Method method1 = (com.infolion.platform.bo.domain.Method) it1.next();
			
			System.out.println("method1.getMedId:" + method1.getMedId());
			System.out.println("method1.getMemo:" + method1.getMemo());
			System.out.println("method1.getMethodDesc:" + method1.getMethodDesc());
			System.out.println("method1.getMethodName:" + method1.getMethodName());
			System.out.println("method1.getMethodType:" + method1.getMethodType());
			System.out.println("method1.getUrl:" + method1.getUrl());
			System.out.println("method1.getVisibility:" + method1.getVisibility());
			System.out.println("method1.getBusinessObject().getBoId():" + method1.getBusinessObject().getBoId());
			System.out.println("method1.getBusinessObject().getBoName():" + method1.getBusinessObject().getBoName());
			
			if (method1.getGridColumn() != null)
			{
				System.out.println("method1.getGridColumn().getGrdId():" + method1.getGridColumn().getGrdId());
				System.out.println("method1.getGridColumn().getColumnNo():" + method1.getGridColumn().getColumnNo());
				System.out.println("method1.getGridColumn().getMethodName():" + method1.getGridColumn().getMethodName());
				System.out.println("method1.getGridColumn().getPropertyName():" + method1.getGridColumn().getPropertyName());
				System.out.println("method1.getGridColumn().getRowNo():" + method1.getGridColumn().getRowNo());
			}
			
			nnnn = nnnn +1;
		}

		System.out.println("结束打印打印Method对象数据:");
		
		
		System.out.println("---------------------------------------------");
		System.out.println("开始打印Property数据getDataElement:");

		Set<Property> property = businessObject.getProperties();
		Iterator itproperty = property.iterator();
		Integer n = 1;
		while (itproperty.hasNext())
		{
			System.out.println("第" + n.toString() + " 笔  Property数据：");

			Property property1 = (Property) itproperty.next();
			if (property1.getDataElement() != null)
			{
				System.out.println("property1.getDataElement().getRollName():" + property1.getDataElement().getRollName());
				System.out.println("property1.getDataElement().getDecimals():" + property1.getDataElement().getDecimals());
				System.out.println("property1.getDataElement().getAs4local():" + property1.getDataElement().getAs4local());
				System.out.println("property1.getDataElement().getDataType():" + property1.getDataElement().getDataType());
				System.out.println("property1.getDataElement().getLeng():" + property1.getDataElement().getLeng());
				System.out.println("property1.getShTable():" + property1.getShTable());
			}
			n = n + 1;
		}
		
		
		Iterator it = method.iterator();

		while (it.hasNext())
		{
			
			com.infolion.platform.bo.domain.Method method1 = (com.infolion.platform.bo.domain.Method) it.next();
			Set<com.infolion.platform.bo.domain.MethodParameter> methodParameter1 = method1.getMethodParameters();
			Iterator it11 = methodParameter1.iterator();
			Integer ii = 1;
			
			while (it11.hasNext())
			{
				System.out.println("第" + ii.toString() + " 笔  MethodParameter数据：");
				MethodParameter methodParameter = (MethodParameter) it11.next();
				System.out.println("MethodParameter.methodParId:" + methodParameter.getMethodParId());
				System.out.println("MethodParameter.methodName:" + methodParameter.getMethodName());
				System.out.println("MethodParameter.parameterName:" + methodParameter.getParameterName());
				System.out.println("MethodParameter.getParameterRef:" + methodParameter.getParameterRef());
				System.out.println("MethodParameter.getParameterType:" + methodParameter.getParameterType());
				ii = ii + 1;
			}
		}

		System.out.println("结束打印MethodParameter数据:");
		System.out.println("---------------------------------------------");
		
		Map<String, ProtertyColumnMaping> protertyColumnMaps = businessObject.getProtertyColumnMaps();

		Iterator<Entry<String, ProtertyColumnMaping>> itProt = protertyColumnMaps.entrySet().iterator();

		System.out.println("---------------------------------------------");
		System.out.println("开始打印ProtertyColumnMaping,属性字段映射关系数据:");

		n = 1;
		while (itProt.hasNext())
		{
			System.out.println("第" + n.toString() + " 笔  Property数据：");
			Map.Entry<String, ProtertyColumnMaping> e = (Map.Entry<String, ProtertyColumnMaping>) itProt.next();
			// ProtertyColumnMaping protertyColumnMaping =
			// (ProtertyColumnMaping) it.next();
			System.out.println("key: " + e.getKey());
			System.out.println("getBoName: " + e.getValue().getBoName());
			System.out.println("getColumnName: " + e.getValue().getColumnName());
			System.out.println("getPropertyName: " + e.getValue().getPropertyName());
			System.out.println("getTableName: " + e.getValue().getTableName());
			n = n + 1;
		}

		System.out.println("结束打印Property数据:");
		
		
		System.out.println("开始打印formColumns对象数据:");

		Set<com.infolion.platform.bo.domain.FormColumn> formColumns = businessObject.getFormColumns();

		Iterator it2 = formColumns.iterator();
		Integer n22 = 1;
		while (it2.hasNext())
		{
			System.out.println("第" + n22.toString() + " 笔 formColumns ：");
			com.infolion.platform.bo.domain.FormColumn formColumn = (com.infolion.platform.bo.domain.FormColumn) it2.next();
			
			System.out.println("formColumn.getFproId:" + formColumn.getFproId());
			System.out.println("formColumn.getCaNote:" + formColumn.getCaNote());
			System.out.println("formColumn.getColumnNo:" + formColumn.getColumnNo());
			System.out.println("formColumn.getNullable:" + formColumn.getNullable());
			System.out.println("formColumn.getPropertyName:" + formColumn.getPropertyName());
			System.out.println("formColumn.getPropertyType:" + formColumn.getPropertyType());
			System.out.println("formColumn.getRowNo:" + formColumn.getRowNo());
			
			if (formColumn.getProperty() != null)
			{
				System.out.println("formColumn.getProperty().getDicTableName():" + formColumn.getProperty().getDicTableName());
				System.out.println("formColumn.getProperty().getDataType():" + formColumn.getProperty().getDataType());
				System.out.println("formColumn.getProperty().getFieldText():" + formColumn.getProperty().getFieldText());
				System.out.println("formColumn.getProperty().getProId():" + formColumn.getProperty().getProId());
				System.out.println("formColumn.getProperty().getPropertyName():" + formColumn.getProperty().getPropertyName());
				
				System.out.println("formColumn.getProperty().getRollName():" + formColumn.getProperty().getRollName());
				System.out.println("formColumn.getProperty().getTableName():" + formColumn.getProperty().getTableName());
			}
			
			n22 = n22 +1;
		}

		System.out.println("结束打印打印formColumns:");

	}

	@Override
	protected List<String> customConfigLocatioins()
	{
		List<String> confs = new ArrayList();
		confs.add("classpath:context/infolion-cache.xml");
		confs.add("classpath:testContext/infolion-core.xml");
		return confs;
	}
}
