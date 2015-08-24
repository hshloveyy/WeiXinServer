package test.infolion.platform.webservice;

import junit.framework.TestCase;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class TestWsClient extends TestCase
{
	public TestWsClient(String name)
	{
		super(name);
	}

	public void test()
	{
		try
		{
			String soapBindingAddress = "http://192.168.39.206:8080/xmdp/services/XmdpService?wsdl";

			EndpointReference endpointReference = new EndpointReference(soapBindingAddress);

			// 创建一个OMFactory，下面的namespace、方法与参数均需由它创建
			OMFactory factory = OMAbstractFactory.getOMFactory();

			// 下面创建命名空间，如果你的WebService指定了targetNamespace属性的话，就要用这个
			// 对应于@WebService(targetNamespace = "http://www.mycompany.com")
			OMNamespace namespace = factory.createOMNamespace("http://webservice.platform.infolion.com", "xsd");

			// 下面创建的是参数对数，对应于@WebParam(name = "name")
			// 由于@WebParam没有指定targetNamespace，所以下面创建name参数时，用了null，否则你得赋一个namespace给它
			OMElement nameElement1 = factory.createOMElement("userName", namespace);
			nameElement1.addChild(factory.createOMText(nameElement1, "admin"));

			OMElement nameElement2 = factory.createOMElement("ipAddres", namespace);
			nameElement2.addChild(factory.createOMText(nameElement2, "127.0.0.1"));

			OMElement nameElement3 = factory.createOMElement("sessionId", namespace);
			nameElement3.addChild(factory.createOMText(nameElement3, ""));

			// 下面创建一个method对象，"test"为方法名
			OMElement method = factory.createOMElement("isOnlineForPortlet", namespace);
			method.addChild(nameElement1);
			method.addChild(nameElement2);
			method.addChild(nameElement3);

			Options options = new Options();
			options.setAction("isOnlineForPortlet"); // 此处对应于@WebMethod(action =
			// "http://www.mycompany.com/test")
			options.setTo(endpointReference);

			ServiceClient sender = new ServiceClient();
			sender.setOptions(options);

			// 下面的输出结果为<xsd:test
			// xmlns:xsd="http://www.mycompany.com"><name>java</name></xsd:test>
			System.out.println(method.toString());

			// 发送并得到结果，至此，调用成功，并得到了结果
			OMElement result = sender.sendReceive(method);

			// 下面的输出结果为<ns2:testResponse
			// xmlns:ns2="http://www.mycompany.com"><greeting>hello
			// java</greeting></ns2:testResponse>
			System.out.println("调用WS成功！result:" + result.toString());
		}
		catch (AxisFault ex)
		{
			ex.printStackTrace();
		}

	}
}
