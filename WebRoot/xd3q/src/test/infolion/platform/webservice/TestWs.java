package test.infolion.platform.webservice;

import junit.framework.TestCase;

import com.infolion.platform.webservice.XmdpService;

public class TestWs extends TestCase
{
	public TestWs(String name)
	{
		super(name);
	}

	public void test()
	{

		XmdpService xmdpService = new XmdpService();
		double a = xmdpService.isOnlineForPortlet("admin", "127.0.0.1", "");
		System.out.println("A:" + a);
	}
}
