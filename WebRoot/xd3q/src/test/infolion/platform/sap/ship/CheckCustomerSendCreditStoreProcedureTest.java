package test.infolion.platform.sap.ship;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.infolion.platform.util.StringUtils;
import com.infolion.sapss.ship.dao.CheckCustomerSendCreditStoreProcedure;



import test.infolion.platform.util.ConnOracle;

public class CheckCustomerSendCreditStoreProcedureTest {
	private static final String SEND_SQL = "check_customer_send_credit";
	private static final String PROVIDER_SQL = "check_provider_credit";
	private static final String CUSTOMER_SQL = "check_customer_credit";
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		CheckCustomerSendCreditStoreProcedureTest t = new CheckCustomerSendCreditStoreProcedureTest();
//		t.testSend();
//		t.testCustomer();
//		t.testProvider();
//		if(StringUtils.isNullBlank(" "))
//			System.out.println("true");
//		System.out.println("Done!");
//		String rowNumber="010";
//		String rowNumber2 = rowNumber.replaceAll("^0+(?!$)", "");
//		System.out.println("rowNumber2=" + rowNumber2);
		//==========
//		BigDecimal subtractVlaue2 = new BigDecimal(0);	
//		BigDecimal collectAmount = new BigDecimal(101);	
//		BigDecimal sumcollectAmount = new BigDecimal(23);	
//		BigDecimal sumoffsetAmount = new BigDecimal(31);	
//		BigDecimal sumclearAmount = new BigDecimal(20);	
//		subtractVlaue2 = collectAmount.divide(sumcollectAmount,3,BigDecimal.ROUND_HALF_EVEN).multiply(sumoffsetAmount).subtract(sumclearAmount);
//		System.out.println(subtractVlaue2);
		//===============
//		String rowNumber="100";
//		if(rowNumber.length()==2)rowNumber="0" + rowNumber;
//		System.out.println("rowNumber2=" + rowNumber);
//		if(rowNumber.length()==1)rowNumber="00" + rowNumber;
//		System.out.println("rowNumber2=" + rowNumber);
		float v1 = 12983873.69f;
		BigDecimal value = new BigDecimal(245413873.69);
		value = value.setScale(2,BigDecimal.ROUND_HALF_UP); 
		float v2 = 0;
		v2 = value.floatValue();
//		v2=Float.parseFloat(value.toString());
		System.out.println(value);
		DecimalFormat df = new DecimalFormat("###0.00");
		
		System.out.println(df.format(v2));
		
		float   f   =   343431231.5585f;  
		BigDecimal   b   =   new   BigDecimal(f);  
		float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue();  
		
		System.out.println(f1);
	}
	public void testSend() {
		DriverManagerDataSource ds=ConnOracle.getInst();
		float va=1;
		CheckCustomerSendCreditStoreProcedure sproc = new CheckCustomerSendCreditStoreProcedure(
				ds,"0040000425","5018FB65-EB4E-4268-9E72-12D51395A93E",va,SEND_SQL);
		Map results = sproc.execute();
		Object rt=results.get(CheckCustomerSendCreditStoreProcedure.OUT_RETURN);		
		System.out.println(Double.valueOf(rt.toString()));
		//sproc.printMap(results);
	}
	public void testCustomer() {
		DriverManagerDataSource ds=ConnOracle.getInst();
		float va=1;
		CheckCustomerSendCreditStoreProcedure sproc = new CheckCustomerSendCreditStoreProcedure(
				ds,"0040000425","5018FB65-EB4E-4268-9E72-12D51395A93E",va,CUSTOMER_SQL);
		Map results = sproc.execute();
		Object rt=results.get(CheckCustomerSendCreditStoreProcedure.OUT_RETURN);		
		System.out.println(Double.valueOf(rt.toString()));
		//sproc.printMap(results);
	}
	public void testProvider() {
		DriverManagerDataSource ds=ConnOracle.getInst();
		float va=1;
		CheckCustomerSendCreditStoreProcedure sproc = new CheckCustomerSendCreditStoreProcedure(
				ds,"0040000425","5018FB65-EB4E-4268-9E72-12D51395A93E",va,PROVIDER_SQL);
		Map results = sproc.execute();
		Object rt=results.get(CheckCustomerSendCreditStoreProcedure.OUT_RETURN);		
		System.out.println(Double.valueOf(rt.toString()));
		//sproc.printMap(results);
	}
	
}
