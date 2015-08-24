package test.infolion.platform.sap.ship;



import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.sapss.ship.service.CheckCustomerSendCreditSerice;
import com.infolion.xdss3.masterData.dao.TcurrHibernateDao;


import test.infolion.platform.core.service.ServiceTest;


public class CheckCustomerSendCreditSericeTest extends ServiceTest{
	@Autowired
	private CheckCustomerSendCreditSerice checkCustomerSendCreditSerice;
	@Test
	public void testConvertTcurr(){
//		TcurrHibernateDao tcurrHibernateDao = (TcurrHibernateDao) EasyApplicationContextUtils.getBeanByName("tcurrHibernateDao");
//		tcurrHibernateDao.get("11");
		checkCustomerSendCreditSerice.convertTcurr(100, "USD");
	}
	@Override
	protected void flush() {
		// TODO Auto-generated method stub
		
	}
	@Test
	public void testCheckCustomer(){
		checkCustomerSendCreditSerice.checkCustomerSendCredit("45748E54-8C18-4EA6-88B4-DC6F30C1AD8E", "FF8C7AC4-C2B2-4DCC-9B4B-F6D33AD5286F");
	}
	
	@Test
	public void testCheckCredit(){
		checkCustomerSendCreditSerice.checkCredit("0040000425", "5018FB65-EB4E-4268-9E72-12D51395A93E", 100);
	}
}
