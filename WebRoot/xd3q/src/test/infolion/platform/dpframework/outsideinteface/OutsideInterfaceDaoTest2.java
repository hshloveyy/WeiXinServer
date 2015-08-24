/*
 * @(#)OutsideInterfaceDaoTest2.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2009-11-25
 *  描　述：创建
 */

package test.infolion.platform.dpframework.outsideinteface;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import test.infolion.platform.core.service.ServiceTest;

import com.infolion.platform.dpframework.outsideInterface.OutsidePersistenceService;

public class OutsideInterfaceDaoTest2 extends ServiceTest
{

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

	// @Test
	// public void testA1Dao()
	// {
	// // A1Dao a1Dao = new A1Dao();
	// // BusinessObject boInst =
	// // BusinessObjectService.getBusinessObject("KnowledgeBase");
	// PurchaseOrder po = new PurchaseOrder();
	// po.setClient("800");
	// po.setPurchaseOrderNo("4");
	// po.setAddress("2000");
	// po.setMemo("hello");
	// po.setLastModifyTime("20091211");
	// // a1Dao.save(po, "_save");
	//
	// OrderItems oi = new OrderItems();
	// oi.setClient("800");
	// oi.setMaterielNo("1");
	// oi.setAddress("aass");
	// // oi.setQuantity(new BigDecimal(10));
	// Set<OrderItems> set = new HashSet<OrderItems>();
	// set.add(oi);
	// oi = new OrderItems();
	// oi.setClient("800");
	// oi.setMaterielNo("2");
	// oi.setAddress("aass2");
	// // oi.setQuantity(new BigDecimal(12));
	// set.add(oi);
	// po.setOrderItems(set);
	//
	// OutsidePersistenceService.save(po);
	// System.out.println("client:" + po.getClient());
	// System.out.println("memo:" + po.getMemo());
	// System.out.println("getPurchaseOrderNo:" + po.getPurchaseOrderNo());
	// System.out.println("getAddress:" + po.getAddress());
	// System.out.println("getLastModifyTime:" + po.getLastModifyTime());
	// System.out.println("getCreator:" + po.getCreator());
	//
	// oi.getItemNo();
	// }
	//	

	// 测试B1连接。
	// @Test
	// public void testConnB1()
	// {
	// ICompany iCompany = null;
	// iCompany = B1CompanyManager.getInstance().getCompany();
	// }

	@Test
	public void testB1Dao()
	{

		/**
		 * 供应商编码：OPOR.cardCode='V10000' 单据日期：OPOR.taxDate='2010/03/18'
		 * 过帐日期：OPOR.docDate='2010/03/18' 到期日期：OPOR.docDueDate='2010/03/18'
		 * 
		 * 物料编码：POR1.itemCode='A00001' 数量：POR1.quantity=1 单价：POR1.unitPrice=100
		 * 税码：POR1.vatGroup='J1' 仓库：POR1.whsCode='01'
		 **/

		// ICompany iCompany = null;
		// iCompany = B1CompanyManager.getInstance().getCompany();
		// IDocuments oDocuments;
		// try
		// {
		// oDocuments = SBOCOMUtil.newDocuments(iCompany,
		// SBOCOMConstants.BoObjectTypes_Document_oPurchaseOrders);
		// oDocuments.setDocObjectCode(SBOCOMConstants.BoObjectTypes_Document_oPurchaseOrders);
		// oDocuments.setCardCode("V10000");
		// oDocuments.setCardName(" ");
		// oDocuments.setComments(" ");
		// oDocuments.setDocDate(StrToDate("2010-03-22"));
		// oDocuments.setDocDueDate(StrToDate("2010-03-22"));
		// oDocuments.setTaxDate(StrToDate("2010-03-22"));
		// oDocuments.getUserFields().getFields().item("U_LC").setValue("CAN");
		//
		// IDocument_Lines oDocLines = oDocuments.getLines();
		// oDocLines.setItemCode("A00001");
		// oDocLines.setQuantity(new Double(2.00));
		// oDocLines.setUnitPrice(new Double(200.00));
		// oDocLines.setVatGroup("J1");
		// oDocLines.setWarehouseCode("01");
		// oDocLines.getUserFields().getFields().item("U_QC").setValue(new
		// Double(14.00));
		//
		// @SuppressWarnings("unused")
		// long lResult = oDocuments.add();
		// }
		// catch (SBOCOMException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str)
	{

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try
		{
			date = format.parse(str);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return date;
	}

}
