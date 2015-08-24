/*
 * @(#)OutsideInterfaceTest4.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2009-12-7
 *  描　述：创建
 */

package test.infolion.platform.dpframework.outsideinteface;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.ServiceTest;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.outsideInterface.A1ConnectManager;
import com.infolion.platform.dpframework.outsideInterface.Constants;
import com.infolion.platform.dpframework.outsideInterface.OutsidePersistenceService;
import com.infolion.platform.dpframework.outsideInterface.dao.assemblers.AssembleContext;
import com.infolion.platform.dpframework.outsideInterface.domain.OutsideInterface;
import com.infolion.platform.dpframework.outsideInterface.proAssemble.AssemblerFactory;
import com.infolion.platform.dpframework.outsideInterface.proAssemble.IBOAssembler;
import com.infolion.sample.purchase.dao.PurchasingDocHibernateDao;
import com.infolion.sample.purchase.domain.PurchaseInfo;
import com.infolion.sample.purchase.domain.PurchaseRows;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

public class OutsideInterfaceTest4 extends ServiceTest
{
	@Autowired
	private PurchasingDocHibernateDao purchasingDocHibernateDao;

	public void setPurchasingDocHibernateDao(PurchasingDocHibernateDao purchasingDocHibernateDao)
	{
		this.purchasingDocHibernateDao = purchasingDocHibernateDao;
	}

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

	public void _testAll()
	{
		PurchaseInfo purchaseInfo = (PurchaseInfo) BusinessObjectService.getBoInstance("PurchaseInfo", "4028a74e260d0c3c01260d34a0f20006");
		OutsidePersistenceService.execute(purchaseInfo, "_submitProcess");
	}

	public void testJCO()
	{
		PurchaseInfo purchaseInfo = (PurchaseInfo) BusinessObjectService.getBoInstance("PurchaseInfo", "4028a74e260d0c3c01260d34a0f20006");
 
		String functionName = "ZMPO_CREATE";
		IFunctionTemplate ftemplate = A1ConnectManager.getInstance().getIFunctionTemplate(functionName);
		JCO.Client client = null;
		
		if (ftemplate != null)
		{
			// 取得外部对象实例
			JCO.Function function = ftemplate.getFunction();
			JCO.Table table = function.getTableParameterList().getTable("IT_EKKO");
			
			table.appendRow();
			table.setValue((purchaseInfo.getBsart()), "BSART");// 采购凭证类型
			table.setValue((purchaseInfo.getBstyp()), "BSTYP");// 采购凭证类别
			table.setValue((purchaseInfo.getLifnr()), "LIFNR");// 供应商帐户号
			//by zhangchzh from "EBDAT" to "BEDAT"
			table.setValue((purchaseInfo.getBedat()), "EBDAT");// 采购凭证日期
			table.setValue((purchaseInfo.getEkorg()), "EKORG");// 采购组织
			table.setValue((purchaseInfo.getEkgrp()), "EKGRP");// 采购组
			table.setValue(purchaseInfo.getBukrs(), "BUKRS");// 公司代码
			table.setValue("1", "PINCR");// 项目编号间隔
			table.setValue((purchaseInfo.getZterm()), "ZTERM");// 付款条件代码
			table.setValue((purchaseInfo.getInco1()), "INCO1");// 国际贸易条款
																// (部分1)
			table.setValue((purchaseInfo.getInco2()), "INCO2");// 国际贸易条件(部分2)
			table.setValue((purchaseInfo.getWaers()), "WAERS");// 货币码
			table.setValue(purchaseInfo.getWkurs(), "WKURS");// 汇率
			table.setValue((purchaseInfo.getContractNo().length() > 12 ? purchaseInfo.getContractNo().substring(purchaseInfo.getContractNo().length() - 12, purchaseInfo.getContractNo().length())
					: purchaseInfo.getContractNo()), "UNSEZ");// 采购合同号
			table.setValue((purchaseInfo.getUnsez()), "VERKF");// 外部纸质合同号
			table.setValue((purchaseInfo.getTelf1()), "TELF1");// 手册号
			// 采购订单行项目数据
			JCO.Table table1 = function.getTableParameterList().getTable("IT_LINEDATA");
			// 采购订单行项目的条件数据
			for(Iterator<PurchaseRows> it = purchaseInfo.getPurchaseRows().iterator();it.hasNext();){
				PurchaseRows material = it.next();
				table1.appendRow();
				table1.setValue((material.getPstyp()), "PSTYP");// 采购凭证中的项目类别
				table1.setValue((material.getSapRowNo()), "EBELP");// 采购凭证的项目编号
				table1.setValue("1", "CONV_NUM1");// 订单价格单位转换为订单单位的分子
				table1.setValue("1", "CONV_DEN1");// 订单价格单位转换为订单单位的分母
				table1.setValue((material.getMatnr()), "MATNR");// 物料号
				table1.setValue((material.getTxzo1()), "TXZ01");// 物料描述
				////////////////////
				table1.setValue(material.getMeins(), "MEINS");// 采购订单的计量单位
				table1.setValue(material.getMeins(), "BPRME");// 货币////订单价格单位(采购)
				table1.setValue(material.getMenge(), "MENGE");// 采购订单数量
				table1.setValue(material.getNetpr(), "NETPR");// 采购凭证中的净价格(以凭证货币计)
				table1.setValue(material.getPeinh(), "PEINH");// 每价格单位
				table1.setValue(material.getBprme(), "BPRME");// 货币
				// PEINH 每价格单位
				// BPRME 货币
				////////////////////
				table1.setValue((material.getWerks()), "WERKS");// 工厂
				table1.setValue((material.getFactorylocal()), "LGORT");// 库存地点
				// table1.setValue((material.getEketEindt()),
				// "EINDT");//项目交货日期
				table1.setValue((material.getWebre()), "WEBRE");// 标识：基于收货的发票验证
				table1.setValue((material.getMwskz()), "MWSKZ");// 销售税代码
				table1.setValue("D", "LPEIN");// 交货日期的类别
			}
			
			client = A1ConnectManager.getInstance().getClient();
			client.execute(function);
			JCO.Table rt = function.getTableParameterList().getTable("IT_BAPIRETURN");
			System.out.println("TYPE:" + rt.getString("TYPE"));
			System.out.println("MESSAGE:" + rt.getString("MESSAGE"));
			System.out.println("PARAMETER:" + rt.getString("PARAMETER"));
			System.out.println("ROW:" + rt.getString("ROW"));
			System.out.println("FIELD:" + rt.getString("FIELD"));
			// Object result = returnTable.getValue("RESULT");
			// log.debug("向A1接口执行操作结果：" + result);
			// log.debug("MESSAGE:" + returnTable.getValue("MESSAGE"));
			// 数据回添到平台业务对象中
			// 执行context中用runAfterAssembled(Runner runner)注册的所有Runner

			table.clear();
		}
	}

		
		
//	}

	// public void testPurchasingDoc()
	// {
	// // PurchasingDoc purchasingDoc = (PurchasingDoc)
	// // BusinessObjectService.getBoInstance("PurchasingDoc", "4500017230");
	//
	// String id = "4500017230";
	// String client = "800";
	// PurchasingDocKey key = new PurchasingDocKey();
	// key.setClient(client);
	// key.setEbeln(id);
	//
	// PurchasingDoc purchasingDoc = this.purchasingDocHibernateDao.get(key);
	//
	// String lastapptime = DateUtils.getCurrTime(DateUtils.DB_STORE_DATE);
	// String lastappname = "fdsafdsa";
	//
	// purchasingDoc.setLastappname(lastappname);
	// purchasingDoc.setLastapptime(lastapptime);
	// // purchasingDoc.setFrgzu("X");
	//
	// OutsidePersistenceService.execute(purchasingDoc, "_submitProcess");
	// }
}
