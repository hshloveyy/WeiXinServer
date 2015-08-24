/*
 * @(#)QueryConditionServiceTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-5-7
 *  描　述：创建
 */

package test.infolion.platform.dpframework.outsideinteface;

import org.junit.Test;

import test.infolion.platform.core.service.ServiceTest;

import com.infolion.platform.dpframework.outsideInterface.dao.OutsideInterfaceJdbcDao;
import com.infolion.platform.dpframework.outsideInterface.domain.OutsideInterface;
import com.infolion.platform.dpframework.outsideInterface.domain.PropertyMapping;
import com.infolion.platform.dpframework.outsideInterface.domain.WebServiceInfo;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.WebServicesHelper;

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
public class OutsideInterfaceDaoTest extends ServiceTest
{

	@Test
	public void testGetOutsideInterface()
	{
		OutsideInterfaceJdbcDao outsideInterfaceJdbcDao = (OutsideInterfaceJdbcDao) EasyApplicationContextUtils.getBeanByName("outsideInterfaceJdbcDao");
		OutsideInterface outsideInterface = new OutsideInterface();
		String boName = "KnowledgeBase";
		String methodName = "_save";
		outsideInterface = outsideInterfaceJdbcDao.getOutsideInterface(boName, methodName);

		System.out.println("开始打印， 外部接口(OutsideInterface)数据：");

		System.out.println("outsideInterface.getOiId:" + outsideInterface.getOiId());
		System.out.println("outsideInterface.getBoId:" + outsideInterface.getBoId());
		System.out.println("outsideInterface.getBoMethodId:" + outsideInterface.getBoMethodId());
		System.out.println("outsideInterface.getBoMethodName:" + outsideInterface.getBoMethodName());
		System.out.println("outsideInterface.getBoName:" + outsideInterface.getBoName());
		System.out.println("outsideInterface.getWsAddress:" + outsideInterface.getWsAddress());

		System.out.println("outsideInterface.getWsMethod:" + outsideInterface.getWsMethod());
		System.out.println("outsideInterface.getWsObjectName:" + outsideInterface.getWsObjectName());

		System.out.println("开始打印， 外部接口(inputMapping)数据：");
		for (PropertyMapping propertyMapping : outsideInterface.getInputMapping())
		{
			System.out.println("propertyMapping.getOiid:" + propertyMapping.getOiid());
			System.out.println("propertyMapping.getInputOrOutput:" + propertyMapping.getInputOrOutput());
			System.out.println("propertyMapping.getInterfaceType:" + propertyMapping.getInterfaceType());
			System.out.println("propertyMapping.getOutsidePropertyName:" + propertyMapping.getOutsidePropertyName());
			System.out.println("propertyMapping.getPropertyName:" + propertyMapping.getPropertyName());
			System.out.println("propertyMapping.getDataConverter:" + propertyMapping.getDataConverter());
		}

		System.out.println("开始打印， 外部接口(outputMapping)数据：");
		for (PropertyMapping propertyMapping : outsideInterface.getOutputMapping())
		{
			System.out.println("propertyMapping.getOiid:" + propertyMapping.getOiid());
			System.out.println("propertyMapping.getInputOrOutput:" + propertyMapping.getInputOrOutput());
			System.out.println("propertyMapping.getInterfaceType:" + propertyMapping.getInterfaceType());
			System.out.println("propertyMapping.getOutsidePropertyName:" + propertyMapping.getOutsidePropertyName());
			System.out.println("propertyMapping.getPropertyName:" + propertyMapping.getPropertyName());
			System.out.println("propertyMapping.getDataConverter:" + propertyMapping.getDataConverter());
		}

	}

	@Test
	public void testWebServiceDaoSave()
	{
		String webServiceUrl = "http://192.168.39.204:52080/sap/bc/srt/rfc/sap/ZWEBSERVICE4?sap-client=800&wsdl=1.1";
		webServiceUrl = "http://192.168.39.204:52080/sap/bc/srt/rfc/sap/ZWEBSERVICE6?sap-client=800&wsdl=1.1";
		webServiceUrl="http://192.168.39.204:52080/sap/bc/srt/rfc/sap/ZWEBSERVICE7?sap-client=800&wsdl=1.1";
		
		WebServicesHelper webServicesHelper = new WebServicesHelper();
		WebServiceInfo webServiceInfo = webServicesHelper.getWebServiceInfo(webServiceUrl);
		WebServicesHelper.codeGenWebServiceClient(webServiceInfo);

		OutsideInterfaceJdbcDao outsideInterfaceJdbcDao = (OutsideInterfaceJdbcDao) EasyApplicationContextUtils.getBeanByName("outsideInterfaceJdbcDao");
		// KnowledgeBase po = new KnowledgeBase();
		// po.setClient("800");
		// po.setProblem("2000");
		// po.setSolution("2000");
		// OutsidePersistenceService.save(po);

		// WebServiceDao webServiceDao = new WebServiceDao();
		// BusinessObject boInst =
		// BusinessObjectService.getBusinessObject("KnowledgeBase");
		// OutsideInterface outsideInterface = new OutsideInterface();
		// String boName = "KnowledgeBase";
		// String methodName = "_saveOrUpdate";
		// outsideInterface =
		// outsideInterfaceJdbcDao.getOutsideInterface(boName, methodName);
		// webServiceDao.save(boInst, outsideInterface);
	}

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}
}
