package com.infolion.sapss.bapi;

import com.infolion.platform.core.BusinessException;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * 收发货BAPI测试类
 * @author Administrator
 *
 */
public class ShipReceiptTest {
	private static String trim(String chr){
		return chr==null?"":chr.trim();
	}
	public static void receiptBapi(){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		
		JCO.Client client = null;
		JCO.Table table = null;
		
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate("ZF_SETMIGO");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				//client.setAbapDebug(true);
				//client.setSapGui(1);
				//
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue("20100120", "PPSTNG_DATE");//凭证中的过帐日期
				input.setValue("20100120", "PDOC_DATE");//凭证中的凭证日期
				input.setValue("0000000075", "PHEADER_TXT");//凭证抬头文本
				input.setValue("01", "PGM_CODE");//为 BAPI 货物移动分配事务代码
				
				table = function.getTableParameterList().getTable("GM_ITEM");
				table.appendRow();
				table.setValue(trim("2101"), "PLANT");//工厂2101
				table.setValue(trim("9999"), "STGE_LOC");//库存地点
				table.setValue(trim("X011_001"), "BATCH");//批号
				table.setValue(trim(""), "VAL_TYPE");//评估类型
				table.setValue(trim("4100000145"), "PO_NUMBER");//采购订单编号  4100000145
				table.setValue(trim("20"), "PO_ITEM");//采购凭证的项目编号20
				table.setValue(trim("1.63"), "ENTRY_QNT");//以输入单位计的数量
				table.setValue(trim("101"), "MOVE_TYPE");//移动类型（库存管理）
				table.setValue(trim("B"), "MVT_IND");//移动标识
				table.setValue(trim("+"), "STCK_TYPE");//库存类型
				table.setValue(trim("X011_001 进仓"), "ITEM_TEXT");//文本
				
				
				//执行更新
				client.execute(function);
				//处理返回信息
				JCO.ParameterList outPut = function.getExportParameterList();
				String returnNo = (String)outPut.getValue("P_MAT_DOC");//物料凭证编号			
				String msg = (String)outPut.getValue("P_ERR_MSG");//标准文本，返回信息提示
				System.out.println("--->"+returnNo+"---->"+msg);
			}
		} catch (Exception ex) {
			
			throw new BusinessException("生成SAP销售订单错误！"+ex.getMessage());
		} finally{
			if(table != null) table.clear();
			manager.cleanUp();
		}
	}
	
	
	public static void shipBapi(){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		
		JCO.Client client = null;
		JCO.Table table = null;
		
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate("ZF_SETVL01N");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");

				JCO.ParameterList input = function.getImportParameterList();
				input.setValue("", "P_SHIP_POINT");//装运点/接收点：默认为空
				input.setValue("", "P_HEAD_TEXT");//SAPscript: 文字行
				
				
				table = function.getTableParameterList().getTable("IT_ITEM");
				table.appendRow();
				table.setValue(trim("0030000257"), "REF_DOC");//销售订单号
				table.setValue(trim("10"), "REF_ITEM");//行项目编号
				table.setValue(trim("1.2"), "DLV_QTY");//交货数量
				table.setValue(trim("MT"), "SALES_UNIT");//物料单位
				table.setValue(trim(""), "SALES_UNIT_ISO");//销售单位的ISO 代码 默认为空
				table.setValue(trim(""), "DELIV_NUMB");//交货 默认为空
				
				//执行更新
				client.execute(function);
				//处理返回信息
				JCO.ParameterList outPut = function.getExportParameterList();
				String returnNo = (String)outPut.getValue("P_DELIV");//物料凭证编号			
				String msg = (String)outPut.getValue("P_ERR_MSG");//标准文本，返回信息提示
				System.out.println("--->"+returnNo+"---->"+msg);
			}
		} catch (Exception ex) {
			
			throw new BusinessException("生成SAP销售订单错误！"+ex.getMessage());
		} finally{
			if(table != null) table.clear();
			manager.cleanUp();
		}
	}

	
	public static void main(String[] args){
		ShipReceiptTest t = new ShipReceiptTest();
		t.receiptBapi();
	    //t.shipBapi();
	}

}
