/*
 * @(#)QueryConditionServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-5-7
 *  描　述：创建
 */

package test.infolion.platform.dpframework.uicomponent.searchHelp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.dpframework.uicomponent.grid.domain.Grid;
import com.infolion.platform.dpframework.uicomponent.searchHelp.domain.SearchHelp;
import com.infolion.platform.dpframework.uicomponent.searchHelp.service.SearchHelpService;

/**
 * <pre>
 * 搜索帮助服务测试类
 * </pre>
 * 
 * <br>
 * JDK版本:1.4
 * 
 * @author 黄虎
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class SearchHelpServiceTest extends JdbcServiceTest
{
	@Autowired
	private SearchHelpService searchHelpService;
	public void setSearchHelpService(SearchHelpService searchHelpService) {
		this.searchHelpService = searchHelpService;
	}

	public void testGetSearchHelp() throws SQLException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		SearchHelp searchHelp;
		String shlpName = "ZH_XREF1"; 
		searchHelp = null;// searchHelpService.getSearchHelp(shlpName);
		Grid grid = searchHelpService.getShlpGrid(shlpName);
		
		Class newClass = Class.forName("com.infolion.platform.dpframework.uicomponent.searchHelp.domain.SearchHelp");
		Field[] fieldList = newClass.getDeclaredFields();

		int i = fieldList.length;

		if (i > 0)
		{
			System.out.println("S-----------------------------------------------------------------------");
			System.out.println("开始打印业务对象名称为" + shlpName
					+ " 的SearchHelp业务对象的各属性的值：");
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
                    Method m = searchHelp.getClass().getMethod("get"+name);
                    String value = (String) m.invoke(searchHelp); // 调用getter方法获取属性值
                    if(value != null){
                        System.out.println("SearchHelp." + fieldName + " :" +value);
                    }
                }
	            else if (type.equals("class java.lang.Integer"))
				{
					Method m = searchHelp.getClass().getMethod("get" + name);
					Integer value = (Integer) m.invoke(searchHelp);
					if (value != null)
					{
						System.out.println("SearchHelp." + fieldName + " :" + value);
					}
				}
	            else if (type.equals("class java.lang.Short"))
				{
					Method m = searchHelp.getClass().getMethod("get" + name);
					Short value = (Short) m.invoke(searchHelp);
					if (value != null)
					{
						System.out.println("SearchHelp." + fieldName + " :" + value);
					}
				}
	            else if (type.equals("class java.lang.Double"))
				{
					Method m = searchHelp.getClass().getMethod("get" + name);
					Double value = (Double) m.invoke(searchHelp);
					if (value != null)
					{
						System.out.println("SearchHelp." + fieldName + " :" + value);
					}
				}
	            else if (type.equals("class java.lang.Boolean"))
				{
					Method m = searchHelp.getClass().getMethod("get" + name);
					Boolean value = (Boolean) m.invoke(searchHelp);
					if (value != null)
					{
						System.out.println("SearchHelp." + fieldName + " :" + value);
					}
				}
	            else if (type.equals("class java.util.Date"))
				{
					Method m = searchHelp.getClass().getMethod("get" + name);
					Date value = (Date) m.invoke(searchHelp);
					if (value != null)
					{
						System.out.println("SearchHelp." + fieldName + " :" + value.toLocaleString());
					}
				}
	            else if (type.equals("java.util.Set<com.infolion.platform.dpframework.uicomponent.searchHelp.domain."+ name+">") || type.equals("java.util.Set<com.infolion.platform.dpframework.uicomponent.searchHelp.domain."+ name.substring(0, name.length() -1 )+ ">"))
				{
	            	System.out.println("开始遍历SearchHelp." + fieldName
							+ "下的所有属性：");
	            	
	            	//Set<Properties>  pro = businessObject.getProperties() ; 
	            	
					Method m = searchHelp.getClass().getMethod("get" + name);
					
					Set obj = (Set) m.invoke(searchHelp);

					int nObj  = obj.size() ; 
					if (obj != null)
					{
						Iterator  it = obj.iterator();
						
						while(it.hasNext())
						{
							Object objPara  = it.next();
							
							
							Class paraClass;
							try
							{
								paraClass = Class.forName("com.infolion.platform.dpframework.uicomponent.searchHelp.domain." + name);
							}
							catch (Exception e)
							{
								paraClass = Class.forName("com.infolion.platform.dpframework.uicomponent.searchHelp.domain." + name.substring(0, name.length() -1 ));
							}
						    
							//paraClass = (Class) it.next();
							
							Field[] fieldList1 = paraClass.getDeclaredFields();

							int ii = fieldList1.length;
							
							if (i > 0)
							{
								System.out.println("@@@@-----------------------------------------------------------------------");
								// System.out.println("    开始打印业务对象名称为"+ boName
								// +" 的各属性的值：");
								for (int nn = 0; nn <ii; nn++)
								{
									Field field1 = fieldList1[nn];

									String fieldName1 = fieldList1[nn].getName();
									String fir1 = fieldName1.substring(0, 1);
									int nLen1 = fieldName1.length();
									String oth1 = fieldName1.substring(1, nLen1);

									log.debug(fir + oth1 + " : " + fieldName1);

									String name1 = fir1.toUpperCase() + oth1;

									String type1 = fieldList1[nn].getGenericType().toString(); // 获取属性的类型
									
									if("BusinessObject".equals(name1))
									{
										continue;
									}
									
									   if (type1.equals("class java.lang.String"))
									{ // 如果type是类类型，则前面包含"class "，后面跟类名
						                    Method m1 = objPara.getClass().getMethod("get"+name1);
						                    String value1 = (String) m1.invoke(objPara); // 调用getter方法获取属性值
						                    if(value1 != null){
						                        System.out.println(fieldName + "." + fieldName1 + " :" +value1);
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
						}
					}
				} 
			}

		}
		System.out.println("E-----------------------------------------------------------------------");
		System.out.println("业务对象名称为" + shlpName + "的businessObject实例:"
				+ searchHelp.toString());
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
