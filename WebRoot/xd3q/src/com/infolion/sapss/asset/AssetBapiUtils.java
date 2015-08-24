package com.infolion.sapss.asset;

import java.util.HashMap;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infolion.platform.core.BusinessException;

import com.infolion.sapss.asset.domain.AssetInfo;
import com.infolion.sapss.bapi.ConnectManager;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

public class AssetBapiUtils {
	
	private static Log log;
		
	private static Log getLogger() {
		if (log == null) {
			log = LogFactory.getLog(AssetBapiUtils.class);
		}
		return log;
	}
		
		
	private static String trim(String chr){
		return chr==null?"":chr.trim();
	}
	public static Map<String,String> createBapi(AssetInfo info,String userMan){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		
		JCO.Client client = null;
		JCO.Table table = null;
		Map<String,String> result = new HashMap<String, String>();
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate("ZF_GUDINGZICHAN_CREATE");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(info.getAssetType(), "ANLKL_001");//资产分类 必填
				input.setValue(info.getComCode(), "BUKRS_002");//公司代码 必填
				input.setValue("01", "NASSETS_003");//类似资产的编号
				input.setValue(info.getAssetName()+"-"+userMan, "TXT50_004");//固定资产名称 必填 
				input.setValue(info.getAssetConfig(), "TXA50_005");//型号配置 必填 
				input.setValue(info.getAssetSerialNo(), "SERNR_006");//序列号 
				input.setValue(info.getOutsideNo(), "INVNR_007");//外部固定资产编号 存货号
				input.setValue(info.getLocation(), "INVZU_008");//固定资产存放位置
				input.setValue(info.getBusinessScope(), "GSBER_009");//业务范围
				input.setValue(info.getCostCenter(), "KOSTL_010");//成本中心 必填

				//执行更新
				client.execute(function);
				//处理返回信息
				JCO.ParameterList outPut = function.getExportParameterList();
				result.put("SUBRC", outPut.getValue("SUBRC").toString());//系统返回值,0：成功,0以外：不成功
			
				result.put("WK_ANLN1",(String)outPut.getValue("WK_ANLN1"));//WK_ANLN1
				
				table = function.getTableParameterList().getTable("MESSTABLE");
				getLogger().debug(result);
				getLogger().debug(table);
				
			}
			return result;
		} catch (Exception ex) {
			getLogger().error(ex);
			throw new BusinessException("写入固定资产错误！"+ex.getMessage());
		} finally{
			if(table != null) table.clear();
			manager.cleanUp();
		}
	}
	
	public static Map<String,String> updateBapi(AssetInfo info,String userMan){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		
		JCO.Client client = null;
		JCO.Table table = null;
		Map<String,String> result = new HashMap<String, String>();
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate("ZF_GUDINGZICHAN_CHANGE");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue("0000"+info.getSapAssetNo(), "ANLN1_001");//主资产号
				input.setValue("0000", "ANLN2_002");//资产次级编号
				input.setValue(info.getComCode(), "BUKRS_003");//公司代码
				input.setValue(info.getAssetName()+"-"+userMan, "TXT50_004");//资产描述 info.getAssetName()+"-"+userMan  描述1
				input.setValue(info.getAssetConfig(), "TXA50_005");//附加资产描述 使用人或保管人 描述2
				input.setValue(info.getAssetName(), "ANLHTXT_006");//资产主号说明 资产主号文本
				input.setValue(info.getAssetSerialNo(), "SERNR_007");//序列号 序列号
				input.setValue(info.getOutsideNo(), "INVNR_008");//存货号 外部固定资产编号 存货号
				input.setValue(info.getLocation(), "INVZU_009");//补充库存说明 固定资产存放位置 库存注记
				input.setValue("", "ADATU_012");//有效期起始日期 新间隔的起始日期 格式如 23.07.2009 无用
				input.setValue(info.getBusinessScope(), "GSBER_01_013");//业务范围
				input.setValue(info.getCostCenter(), "KOSTL_01_014");//成本中心
				//执行更新
				client.execute(function);
				//处理返回信息
				JCO.ParameterList outPut = function.getExportParameterList();
				result.put("SUBRC", outPut.getValue("SUBRC").toString());//系统返回值,0：成功,0以外：不成功
			
				table = function.getTableParameterList().getTable("MESSTABLE");
				
				getLogger().debug(result);
				getLogger().debug(table);				
			}
			return result;
		} catch (Exception ex) {
			getLogger().error(ex);
			throw new BusinessException("更改固定资产错误！"+ex.getMessage());
		} finally{
			if(table != null) table.clear();
			manager.cleanUp();
		}
	}
	
	
	public static Map<String,String> lockAsset(AssetInfo info){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();		
		JCO.Client client = null;
		JCO.Table returnTable = null;
		Map<String,String> result = new HashMap<String, String>();
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate("ZF_GUDINGZICHAN_LOCK");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				//
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(info.getSapAssetNo(),"ANLN1_001" );//主资产号
				input.setValue("0","ANLN2_002");//资产次级编号
				input.setValue(info.getComCode(),"BUKRS_003");//公司代码
				input.setValue("","NO_SELECTION_004" );//
				input.setValue("X","XSPEB_005");//标识: 冻结资产以进行购置记帐

				//执行更新
				client.execute(function);
				//resultMarik=0创建成功，resultMarik！=0创建失败
				Object resultMarik = function.getExportParameterList().getValue("SUBRC");
				result.put("SUBRC", resultMarik.toString());
				//
				returnTable= function.getTableParameterList().getTable("MESSTABLE");
				getLogger().debug(result);
				getLogger().debug(returnTable);	
			}
		} catch (Exception ex) {
			getLogger().error("冻结固定资产错误！",ex);
			getLogger().error(returnTable);
			throw new BusinessException("冻结固定资产错误！"+ex.getMessage());
		} finally{
			if(returnTable != null) returnTable.clear();
			manager.cleanUp();
		}
		return result;
	}
	
	
	public static Map<String,String> unlockAsset(AssetInfo info){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();		
		JCO.Client client = null;
		JCO.Table returnTable = null;
		Map<String,String> result = new HashMap<String, String>();
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate("ZF_GUDINGZICHAN_UNLOCK");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				//
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(info.getSapAssetNo(),"ANLN1_001" );//主资产号
				input.setValue("0","ANLN2_002");//资产次级编号
				input.setValue(info.getComCode(),"BUKRS_003");//公司代码
				input.setValue("X","NO_SELECTION_004" );//
				input.setValue("","XSPEB_005");//标识: 冻结资产以进行购置记帐

				//执行更新
				client.execute(function);
				//resultMarik=0创建成功，resultMarik！=0创建失败
				Object resultMarik = function.getExportParameterList().getValue("SUBRC");
				result.put("SUBRC", resultMarik.toString());
				//
				returnTable= function.getTableParameterList().getTable("MESSTABLE");
				log.debug(result);
				log.debug(returnTable);	
			}
		} catch (Exception ex) {
			log.error("解冻固定资产错误！",ex);
			log.error(returnTable);
			throw new BusinessException("解冻固定资产错误！"+ex.getMessage());
		} finally{
			if(returnTable != null) returnTable.clear();
			manager.cleanUp();
		}
		return result;
	}


		
    public static void main(String[] args){
    	
    	AssetBapiUtils.updateBapi(null,null);
	}

}
