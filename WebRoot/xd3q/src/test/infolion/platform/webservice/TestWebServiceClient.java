package test.infolion.platform.webservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class TestWebServiceClient extends TestCase
{
	public TestWebServiceClient(String name)
	{
		super(name);
	}

	public void test() throws ClassNotFoundException
	{
		try
		{
			String soapBindingAddress = "http://192.168.39.206:8080/xmdp/services/TestService?wsdl";

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

			// 下面创建一个method对象，"test"为方法名
			OMElement method = factory.createOMElement("getUser", namespace);
			method.addChild(nameElement1);

			Options options = new Options();
			options.setAction("getUser"); // 此处对应于@WebMethod(action =
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
			System.out.println("result1:" + result.getLocalName());
			System.out.println("result2:" + result.getType());
			System.out.println("result3:" + result.getFirstOMChild().toString());

			String aa = "rO0ABXNyACZ0ZXN0LmluZm9saW9uLnBsYXRmb3JtLndlYnNlcnZpY2UuVXNlcgAAAAAAAAABAwAD&#xd;TAAIcGFzc1dvcmR0ABJMamF2YS9sYW5nL1N0cmluZztMAAZ1c2VySWRxAH4AAUwACHVzZXJOYW1l&#xd";
			byte[] DSbuff = new sun.misc.BASE64Decoder().decodeBuffer(aa);
			ByteArrayInputStream BAIS = new ByteArrayInputStream(DSbuff);
			ObjectInputStream OIS = new ObjectInputStream(BAIS);
			HashMap myHM2 = (HashMap) (OIS.readObject());

			System.out.println("result6:" + result.getLocalName());
		}
		catch (AxisFault ex)
		{
			ex.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
