package com.infolion.sapss.bapi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.infolion.platform.core.BusinessException;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.receipts.domain.TReceiptInfo;
import com.infolion.sapss.receipts.domain.TReceiptMaterial;
import com.infolion.sapss.ship.domain.TShipInfo;
import com.infolion.sapss.ship.domain.TShipMaterial;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

public class ShipReceiptUtils {
	private static String trim(String chr){
		return chr==null?"":chr.trim();
	}
	public static Map<String,String> receiptBapi(TReceiptInfo info,List<TReceiptMaterial> materials,String factory){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		
		JCO.Client client = null;
		JCO.Table table = null;
		Map<String,String> result = new HashMap<String, String>();
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate("ZF_SETMIGO");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(info.getPostDate().replaceAll("-", ""), "PPSTNG_DATE");//凭证中的过帐日期
				input.setValue(DateUtils.getCurrDate(2).replaceAll("-", ""), "PDOC_DATE");//凭证中的凭证日期：默认当天
				input.setValue(info.getSerialNo(), "PHEADER_TXT");//凭证抬头文本
				input.setValue("01", "PGM_CODE");//为 BAPI 货物移动分配事务代码
				table = function.getTableParameterList().getTable("GM_ITEM");
				for(Iterator<TReceiptMaterial> it = materials.iterator();it.hasNext();){
					TReceiptMaterial ma = it.next();
					table.appendRow();					
					table.setValue(trim(factory), "PLANT");//工厂2101
					table.setValue(trim(info.getWarehouse()), "STGE_LOC");//库存地点
					table.setValue(trim(ma.getBatchNo().toUpperCase()), "BATCH");//批号
					table.setValue(trim(""), "VAL_TYPE");//评估类型
					table.setValue(trim(info.getSapOrderNo()), "PO_NUMBER");//采购订单编号  4100000145
					table.setValue(trim(ma.getSapRowNo()), "PO_ITEM");//采购凭证的项目编号20
					table.setValue(trim(ma.getQuantity().toString()), "ENTRY_QNT");//以输入单位计的数量
					table.setValue(trim("101"), "MOVE_TYPE");//移动类型（库存管理）
					table.setValue(trim("B"), "MVT_IND");//移动标识
					table.setValue(trim("+"), "STCK_TYPE");//库存类型
					table.setValue(trim(ma.getBatchNo())+" 进仓", "ITEM_TEXT");//文本
				}
				//执行更新
				client.execute(function);
				//处理返回信息
				JCO.ParameterList outPut = function.getExportParameterList();
				result.put("SAP_RETURN_NO", (String)outPut.getValue("P_MAT_DOC"));//物料凭证编号			
				result.put("SAP_RETURN_MSG",(String)outPut.getValue("P_ERR_MSG"));//标准文本，返回信息提示
                //System.out.println(result.get("P_ERR_MSG"));
			}
			return result;
		} catch (Exception ex) {
			throw new BusinessException("写入进仓单错误！"+ex.getMessage());
		} finally{
			if(table != null) table.clear();
			manager.cleanUp();
		}
	}
	
	
	public static Map<String,String> shipBapi(TShipInfo info,List<TShipMaterial> materials,String factory){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		
		JCO.Client client = null;
		JCO.Table table = null;
		Map<String,String> result = new HashMap<String, String>();
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
				for(Iterator<TShipMaterial> it = materials.iterator();it.hasNext();){
					TShipMaterial ma = it.next();
					table.appendRow();
					table.setValue(trim(info.getSapOrderNo()), "REF_DOC");//销售订单号
					table.setValue(trim(ma.getSapRowNo()), "REF_ITEM");//行项目编号
					table.setValue(trim(ma.getQuantity().toString()), "DLV_QTY");//交货数量
					table.setValue(trim(ma.getMaterialUnit()), "SALES_UNIT");//物料单位
					table.setValue(trim(""), "SALES_UNIT_ISO");//销售单位的ISO 代码 默认为空
					table.setValue(trim(""), "DELIV_NUMB");//交货 默认为空
				}
				//执行更新
				client.execute(function);
				//处理返回信息
				JCO.ParameterList outPut = function.getExportParameterList();
				result.put("SAP_RETURN_NO", (String)outPut.getValue("P_DELIV"));//物料凭证编号			
				result.put("SAP_RETURN_MSG",(String)outPut.getValue("P_ERR_MSG"));//标准文本，返回信息提示
			}
			return result;
		} catch (Exception ex) {
			throw new BusinessException("写入出仓单错误！"+ex.getMessage());
		} finally{
			if(table != null) table.clear();
			manager.cleanUp();
		}
		
	}

	
	public static void main(String[] args){
		ShipReceiptTest t = new ShipReceiptTest();
		t.shipBapi();
	    //t.shipBapi();
	}

}
