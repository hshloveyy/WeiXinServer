package com.infolion.sapss.bapi;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.bapi.domain.TBapiLog;
import com.infolion.sapss.bapi.domain.TBapiLogDetail;
import com.infolion.sapss.contract.domain.TContractBom;
import com.infolion.sapss.contract.domain.TContractPuMaterielCase;
import com.infolion.sapss.contract.domain.TContractPurchaseInfo;
import com.infolion.sapss.contract.domain.TContractPurchaseMateriel;
import com.infolion.sapss.contract.domain.TContractSalesInfo;
import com.infolion.sapss.contract.domain.TContractSalesMateriel;
import com.infolion.sapss.contract.domain.TContractSeMaterielCase;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

public class UpdataSapData {
	
    private static Log log;
	
	private static Log getLogger() {
		if (log == null) {
			log = LogFactory.getLog(UpdataSapData.class);
		}
		return log;
	}
	
	public static TBapiLog insertSalesOrder(TContractSalesInfo order){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		
		JCO.Client client = null;
		JCO.Table table = null;
		JCO.Table table1= null;
		JCO.Table table2 = null;
		TBapiLog log = null;
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate(BapiConstant.BAPI_SALES_ORDER_FUNCTION_NAME);
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				//client.setAbapDebug(true);
				//client.setSapGui(1);
				//销售订单抬头数据
				table = function.getTableParameterList().getTable(BapiConstant.BAPI_SALES_ORDER_HEAD_TABLE_NAME);
				table.appendRow();
				table.setValue(trim(order.getVbakAuart()), "AUART");//订单类型
				table.setValue(trim(order.getVbakVkorg()), "VKORG");//销售组织
				table.setValue(trim(order.getVbakVtweg()), "VKORG_F");//分销渠道
				table.setValue(trim(order.getVbakSpart()), "SPART");//产品组
				table.setValue(trim(order.getVbakVkbur()), "VKBUR");//销售部门
				table.setValue(trim(order.getVbakVkgrp()), "VKGRP");//销售组
				table.setValue(trim(order.getKuagvKunnr()), "KUNNR");//售达方
				table.setValue(trim(order.getKuwevKunnr()), "KUNNR_Y");//送达方
				table.setValue(trim(order.getVbakAudat()), "AUDAT");//单据日期、凭证日期
				table.setValue(trim(order.getVbakWaerk()), "WAERK");//货币码,SD 凭证货币
				table.setValue(trim(order.getVbkdInco1()), "INCO1");//国际贸易条件 (部分1)
				table.setValue(trim(order.getVbkdInco2()), "INCO2");//国际贸易条件(部分2)
				table.setValue(trim(order.getVbkdZterm()), "ZTERM");//付款条件，付款条件代码
				table.setValue(trim(order.getVbkdZlsch()), "ZLSCH");//付款方式
				table.setValue(order.getVbkdKurrf(), "KURRF");//会计汇率，FI 过帐的汇率
				table.setValue(trim(order.getVbkdIhrez()), "BSTKD");//合同号，外部合同号
				table.setValue(trim(order.getVbakBname()), "BNAME");//信达项目号，分支名称
				table.setValue(trim(order.getContractNo()), "IHREZ");//客户采购号
				table.setValue(trim(order.getVbkdBstkdE()), "BSTKD_E");//手册号
				table.setValue(trim(order.getVbkdBzirk()), "BZIRK");//销售地区
				//销售订单行项目数据
				table1 = function.getTableParameterList().getTable(BapiConstant.BAPI_SALES_ORDER_ROW_TABLE_NAME);
				//销售订单行项目的条件数据
				table2 = function.getTableParameterList().getTable(BapiConstant.BAPI_SALES_ORDER_CASE_TABLE_NAME);
				for(Iterator<TContractSalesMateriel> it = order.getContractSalesMateriels().iterator();it.hasNext();){
					TContractSalesMateriel material = it.next();
					table1.appendRow();
					table1.setValue(trim(material.getVbapPstyv()), "PSTYV");//销售订单中的项目类别
					table1.setValue(trim(material.getVbapMatnr()), "MATNR");//物料号
					table1.setValue(trim(material.getVbapArktx()), "ARKTX");//物料描述
					table1.setValue(trim(material.getVbapVrkme()), "VRKME");//销售订单的计量单位
					table1.setValue(material.getVbapZmeng(), "WMENG");//以销售单位表示的累计订单数量
					table1.setValue(trim(material.getRv45aEtdat()), "DELCO");//项目交货日期
					table1.setValue(trim(material.getVbapWerks()), "WERKS");//工厂
					table1.setValue(material.getVbapUebto(), "UEBTO");//过量交货限度（百分率）
					table1.setValue(material.getVbapUntto(), "UNTTO");//交货不足限度
					table1.setValue(material.getVbkdZterm(), "ZTERM");//付款条件
					table1.setValue(material.getVbapKpein(), "KPEIN");//条件定价单位
					table1.setValue(trim(material.getVbapKmein()), "KMEIN");//在凭证中的条件单位
					table1.setValue(trim(material.getPayer()), "KUNNR_FK");//付款方
					table1.setValue(trim(material.getBillToParty()), "KUNNR_SP");//收票方
					table1.setValue(trim(material.getSapRowNo()), "ITM_NUMBER");//编号					
					if(null!=material.getKonvKbetr()&&material.getKonvKbetr()>0){
						table2.appendRow();
						table2.setValue(trim(material.getSapRowNo()), "ITM_NUMBER");//行项目编号
						table2.setValue("PR01", "KSCHL");//条件类型
						table2.setValue(trim(order.getVbakWaerk()), "WAERK");//币别
						table2.setValue(material.getKonvKbetr(), "BAPIKBETR1");//金额
					}
					for(Iterator<TContractSeMaterielCase> it1 = material.getContractSeMaterielCases().iterator();it1.hasNext();){
						TContractSeMaterielCase materialCase = it1.next();
						table2.appendRow();
						table2.setValue(trim(materialCase.getSapRowNo()), "ITM_NUMBER");//行项目编号
						table2.setValue(trim(materialCase.getKonvKschl()), "KSCHL");//条件类型
						table2.setValue(trim(materialCase.getKonvWears()), "WAERK");//币别
						table2.setValue(materialCase.getKonvKbetr(), "BAPIKBETR1");//金额
					}
				}

				//执行更新
				client.execute(function);
				//处理返回信息
				log = new TBapiLog();
				String returnNo = (String)function.getExportParameterList().getValue(BapiConstant.BAPI_SALES_ORDER_RETURN_NNUMBER_PARA_NAME);				
				getLogger().info("返回销售订单号：----------------:"+returnNo);
				log.setLogId(CodeGenerator.getUUID());
				log.setSapOrderNo(returnNo);
				log.setBusinessRecordId(order.getContractSalesId());
				log.setContractName(order.getContractName());
				log.setCreator(UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserName());
				log.setCreatorTime(DateUtils.getCurrTimeStr(DateUtils.HYPHEN_DISPLAY_DATE));
				log.setOrderCreator(order.getCreator());
				log.setOrderCreteDept(order.getDeptId());
				log.setOrderType("2");
				//
				JCO.Table returnTable= function.getTableParameterList().getTable(BapiConstant.BAPI_SALES_ORDER_RETURN_MESSAGE_TABLE_NAME);
				if(returnTable.getNumRows()>0){
					try {
						do {
							TBapiLogDetail detail = new TBapiLogDetail();
							StringBuilder buider = new StringBuilder("");
							for (JCO.FieldIterator fieldIte = returnTable.fields(); fieldIte.hasMoreElements();) {
								JCO.Field field = fieldIte.nextField();
								String sapfieldName = field.getName();
								String sapfieldvalue = field.getString();
								if(BapiConstant.BAPI_SALES_ORDER_MSG_FIELDS.keySet().contains(sapfieldName))
									assembleBapiLogDetail(detail, BapiConstant.BAPI_SALES_ORDER_MSG_FIELDS.get(sapfieldName), sapfieldvalue);
								buider.append(sapfieldName+":"+sapfieldvalue+",");
							}
							detail.setLogDetailId(CodeGenerator.getUUID());
							detail.setLogText(buider.toString());
							detail.setCreator(UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserName());
							detail.setCreatorTime(DateUtils.getCurrTimeStr(DateUtils.HYPHEN_DISPLAY_DATE));
						    getLogger().info(buider.toString());
						    detail.setTBapiLog(log);
						    log.getTBapiLogDetails().add(detail);
						} while (returnTable.nextRow());

					}finally{
						if(returnTable != null){
							returnTable.clear();
						}
					}
				}

			}
		} catch (Exception ex) {
			getLogger().error("生成SAP销售订单错误！",ex);
			throw new BusinessException("生成SAP销售订单错误！"+ex.getMessage());
		} finally{
			if(table != null) table.clear();
			if(table1 != null) table1.clear();
			if(table2 != null) table2.clear();
			manager.cleanUp();
		}
		return log;
	}
	
	
	
	public static TBapiLog insertPurcharseOrder(TContractPurchaseInfo order){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		JCO.Table table = null;
		JCO.Table table1= null;
		JCO.Table table2 = null;
		JCO.Table table3= null;
		TBapiLog log = null;
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate(BapiConstant.BAPI_PURCHARSE_ORDER_FUNCTION_NAME);
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				//client.setAbapDebug(true);
				//client.setSapGui(1);
				//采购订单抬头数据
				table = function.getTableParameterList().getTable(BapiConstant.BAPI_PURCHARSE_ORDER_HEAD_TABLE_NAME);
				table.appendRow();
				table.setValue(trim(order.getEkkoBsart()), "BSART");//采购凭证类型
				table.setValue(trim(order.getEkkoBstyp()), "BSTYP");//采购凭证类别
				table.setValue(trim(order.getEkkoLifnr()), "LIFNR");//供应商帐户号
				//by zhangchzh from "EBDAT" to "BEDAT"
				table.setValue(trim(order.getEkkoBedat()), "EBDAT");//采购凭证日期
				table.setValue(trim(order.getEkkoEkorg()), "EKORG");//采购组织
				table.setValue(trim(order.getEkkoEkgrp()), "EKGRP");//采购组
				table.setValue(getCOMcode(order.getEkkoEkorg()), "BUKRS");//公司代码
				table.setValue("0", "PINCR");//项目编号间隔 1 为从1开始累增
				table.setValue(trim(order.getEkkoZterm()), "ZTERM");//付款条件代码
				table.setValue(trim(order.getEkkoInco1()), "INCO1");//国际贸易条款 (部分1)
				table.setValue(trim(order.getEkkoInco2()), "INCO2");//国际贸易条件(部分2)
				table.setValue(trim(order.getEkkoWaers()), "WAERS");//货币码
				table.setValue(order.getEkkoWkurs(), "WKURS");//汇率
				table.setValue(trim(order.getContractNo().length()>12?order.getContractNo().substring(order.getContractNo().length()-12,order.getContractNo().length()):order.getContractNo()), "UNSEZ");//采购合同号
				table.setValue(trim(order.getEkkoUnsez()), "VERKF");//外部纸质合同号
				table.setValue(trim(order.getEkkoTelf1()), "TELF1");//手册号
				//采购订单行项目数据
				table1 = function.getTableParameterList().getTable(BapiConstant.BAPI_PURCHARSE_ORDER_ROW_TABLE_NAME);
				//采购订单行项目的条件数据
				table2 = function.getTableParameterList().getTable(BapiConstant.BAPI_PURCHARSE_ORDER_CASE_TABLE_NAME);
				table3=function.getTableParameterList().getTable("IT_LINEDATA3");
				for(Iterator<TContractPurchaseMateriel> it = order.getContractPurchaseMateriels().iterator();it.hasNext();){
					TContractPurchaseMateriel material = it.next();
					table1.appendRow();
					table1.setValue(trim(material.getEkpoPstyp()), "PSTYP");//采购凭证中的项目类别
					table1.setValue(trim(material.getSapRowNo()), "EBELP");//采购凭证的项目编号
					table1.setValue("1", "CONV_NUM1");//订单价格单位转换为订单单位的分子
					table1.setValue("1", "CONV_DEN1");//订单价格单位转换为订单单位的分母
					table1.setValue(trim(material.getEkpoMatnr()), "MATNR");//物料号
					table1.setValue(trim(material.getEkpoTxz01()), "TXZ01");//物料描述
					////////////////////
					table1.setValue(material.getEkpoMeins(), "MEINS");//采购订单的计量单位
					table1.setValue(material.getEkpoMeins(), "BPRME");//货币////订单价格单位(采购)
					table1.setValue(material.getEkpoMenge(), "MENGE");//采购订单数量
					table1.setValue(material.getEkpoNetpr(), "NETPR");//采购凭证中的净价格(以凭证货币计)
					table1.setValue(material.getEkpoPeinh(), "PEINH");//每价格单位
					table1.setValue(material.getEkpoBprme(), "BPRME");//货币
					//PEINH 每价格单位
					//BPRME 货币
					////////////////////
					table1.setValue(trim(material.getEkpoWerks()), "WERKS");//工厂
					table1.setValue(trim(material.getFactoryLocal()), "LGORT");//库存地点
//					table1.setValue(trim(material.getEketEindt()), "EINDT");//项目交货日期
					table1.setValue(trim(material.getEkpoWebre()), "WEBRE");//标识：基于收货的发票验证
					table1.setValue(trim(material.getEkpoMwskz()), "MWSKZ");//销售税代码
					table1.setValue("D", "LPEIN");//交货日期的类别
					
					
					for(Iterator<TContractPuMaterielCase> it1 = material.getContractPuMaterielCases().iterator();it1.hasNext();){
						TContractPuMaterielCase materialCase = it1.next();
						table2.appendRow();
						table2.setValue(trim(material.getSapRowNo()), "EBELP");;//行项目编号
						table2.setValue(trim(materialCase.getKonvKschl()), "KSCHL");//条件类型
						table2.setValue(materialCase.getKonvKbetr(), "KBETR");//价格( 条件金额或百分数 )
						table2.setValue(trim(materialCase.getKonvLifnr()), "LIFNR");//供应商或债权人的帐号
						table2.setValue(trim(materialCase.getKonvWears()), "WAERS");//货币码
					}
					int itemNo = 1;
					//BOM信息。。。。。在这里添加。。
					for(Iterator<TContractBom> it1 = material.getContractBoms().iterator();it1.hasNext();){
						TContractBom bom = it1.next();
						table3.appendRow();
						table3.setValue(trim(material.getSapRowNo()), "EBELP");;//行项目编号
						table3.setValue(String.valueOf(itemNo), "ITEM_NO");//当前流水号
						table3.setValue(trim(bom.getMateriel()), "MATERIAL");//物料号
						table3.setValue(trim(bom.getEntryQuantity()), "ENTRY_QUANTITY");//组件的需求数量
						table3.setValue(trim(bom.getEntryUom()), "ENTRY_UOM");//条目单位
						table3.setValue(trim(bom.getPlant()), "PLANT");//工厂
						itemNo++;
					}
				}

				//执行更新
				client.execute(function);
				//处理返回信息
				log = new TBapiLog();
				String returnNo = (String)function.getExportParameterList().getValue(BapiConstant.BAPI_PURCHARSE_ORDER_RETURN_NNUMBER_PARA_NAME);				
				getLogger().info("返回采购订单号：----------------:"+returnNo);
				log.setLogId(CodeGenerator.getUUID());
				log.setSapOrderNo(returnNo);
				log.setBusinessRecordId(order.getContractPurchaseId());
				log.setContractName(order.getContractName());
				log.setCreator(UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserName());
				log.setCreatorTime(DateUtils.getCurrTimeStr(DateUtils.HYPHEN_DISPLAY_DATE));
				log.setOrderCreator(order.getCreator());
				log.setOrderCreteDept(order.getDeptId());
				log.setOrderType("1");
				//
				JCO.Table returnTable= function.getTableParameterList().getTable(BapiConstant.BAPI_PURCHARSE_ORDER_RETURN_MESSAGE_TABLE_NAME);
				if(returnTable.getNumRows()>0){
					try {
						do {
							TBapiLogDetail detail = new TBapiLogDetail();
							StringBuilder buider = new StringBuilder("");
							for (JCO.FieldIterator fieldIte = returnTable.fields(); fieldIte.hasMoreElements();) {
								JCO.Field field = fieldIte.nextField();
								String sapfieldName = field.getName();
								String sapfieldvalue = field.getString();
								if(BapiConstant.BAPI_PURCHARSE_ORDER_MSG_FIELDS.keySet().contains(sapfieldName))
									assembleBapiLogDetail(detail, BapiConstant.BAPI_PURCHARSE_ORDER_MSG_FIELDS.get(sapfieldName), sapfieldvalue);
								buider.append(sapfieldName+":"+sapfieldvalue+",");
							}
							detail.setLogDetailId(CodeGenerator.getUUID());
							detail.setLogText(buider.toString());
							detail.setCreator(UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getUserName());
							detail.setCreatorTime(DateUtils.getCurrTimeStr(DateUtils.HYPHEN_DISPLAY_DATE));
						    getLogger().info(buider.toString());
						    detail.setTBapiLog(log);
						    log.getTBapiLogDetails().add(detail);
						} while (returnTable.nextRow());

					}finally{
						if(returnTable != null){
							returnTable.clear();
						}
					}
				}

			}
		} catch (Exception ex) {
			getLogger().error("生成SAP销售订单错误！",ex);
			throw new BusinessException(ex.getMessage());
		} finally{
			if(table != null) table.clear();
			if(table1 != null) table1.clear();
			if(table2 != null) table2.clear();
			if(table3 !=null) table3.clear();
			manager.cleanUp();
		}
		return log;
	}
	
	private static void assembleBapiLogDetail(Object detail,String filedName,Object value){
		Method[] methods = detail.getClass().getMethods();
		for(Method method:methods){
			if(method.getName().equalsIgnoreCase("set"+filedName)){
				try{
					method.setAccessible(true);
					method.invoke(detail, value);
				}
				catch (Exception e) {
					e.printStackTrace();
					getLogger().error("BAPI生成日志明细产生错误！",e);
					throw new BusinessException("BAPI生成日志明细产生错误！"+e.getMessage());
				}
			}
		}
	}
	
	private static String trim(String chr){
		return chr==null?"":chr.trim();
	}
	
	
	public static String getCOMcode(String saleCode){
		if(saleCode.equals("3011")) return "3010";
		return saleCode.substring(0,2)+"00";
	}
	
	
	public static TBapiLog insertAsset(){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		
		JCO.Client client = null;

		TBapiLog log = null;
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate("ZF_GUDINGZICHAN_CREATE");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				//
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue("1201","ANLKL_001" );//资产分类
				input.setValue("2100","BUKRS_002");//公司代码
				input.setValue("1","NASSETS_003");//类似资产的编号
				input.setValue("测试数据01","TXT50_004" );//固定资产名称
				input.setValue("测试数据02","TXA50_005");//使用人或保管人
				input.setValue("测试数据03","SERNR_006" );//序列号
				input.setValue("测试数据04","INVNR_007");//外部固定资产编号
				input.setValue("测试数据05","INVZU_008");//固定资产存放位置
				input.setValue("2103","GSBER_009");//业务范围
				input.setValue("21001033","KOSTL_010");//成本中心


				//执行更新
				client.execute(function);
				//resultMarik=0创建成功，resultMarik！=0创建失败
				Object resultMarik = function.getExportParameterList().getValue("SUBRC");				
				System.out.println(resultMarik);
				
				Object resultNo = function.getExportParameterList().getValue("WK_ANLN1");				
				System.out.println(resultNo);
				//
				JCO.Table returnTable= function.getTableParameterList().getTable("MESSTABLE");
				if(returnTable.getNumRows()>0){
					try {
						do {
							StringBuilder buider = new StringBuilder("");
							for (JCO.FieldIterator fieldIte = returnTable.fields(); fieldIte.hasMoreElements();) {
								JCO.Field field = fieldIte.nextField();
								String sapfieldName = field.getName();
								String sapfieldvalue = field.getString();
								buider.append(sapfieldName+":"+sapfieldvalue+",");
							}
							System.out.println(buider.toString());
						} while (returnTable.nextRow());

					}finally{
						if(returnTable != null){
							returnTable.clear();
						}
					}
				}

			}
		} catch (Exception ex) {
			getLogger().error("xxxxx！",ex);
			throw new BusinessException("xxxxxxxxxxxxx！"+ex.getMessage());
		} finally{
			/*if(table != null) table.clear();
			if(table1 != null) table1.clear();
			if(table2 != null) table2.clear();*/
			manager.cleanUp();
		}
		return log;
	}
	
	
	public static TBapiLog updateAsset(){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		
		JCO.Client client = null;

		TBapiLog log = null;
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate("ZF_GUDINGZICHAN_CHANGE");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				//
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue("000012010018","ANLN1_001" );//主资产号
				input.setValue("0000","ANLN2_002");//资产次级编号
				input.setValue("2100","BUKRS_003");//公司代码
				input.setValue("固定资产名称1","TXT50_004" );//固定资产名称
				input.setValue("使用人或保管人2","TXA50_005");//使用人或保管人
				input.setValue("XULIEHA3","ANLHTXT_006" );//资产主号说明
				input.setValue("BIANHA4","SERNR_007");//序列号
				input.setValue("存放位置5","INVNR_008");//外部固定资产编号
				input.setValue("固定资产存放位置6","INVZU_009");//固定资产存放位置
				//更改成本中心，必须输入间隔时间
				input.setValue("25.09.2009","ADATU_012");//新间隔的起始日期
				input.setValue("","GSBER_01_013");//新的业务范围
				input.setValue("21001081","KOSTL_01_014");//新的成本中心


				//执行更新
				client.execute(function);
				//resultMarik=0创建成功，resultMarik！=0创建失败
				Object resultMarik = function.getExportParameterList().getValue("SUBRC");				
				System.out.println(resultMarik);
				//
				JCO.Table returnTable= function.getTableParameterList().getTable("MESSTABLE");
				if(returnTable.getNumRows()>0){
					try {
						do {
							StringBuilder buider = new StringBuilder("");
							for (JCO.FieldIterator fieldIte = returnTable.fields(); fieldIte.hasMoreElements();) {
								JCO.Field field = fieldIte.nextField();
								String sapfieldName = field.getName();
								String sapfieldvalue = field.getString();
								buider.append(sapfieldName+":"+sapfieldvalue+",");
							}
							System.out.println(buider.toString());
						} while (returnTable.nextRow());

					}finally{
						if(returnTable != null){
							returnTable.clear();
						}
					}
				}

			}
		} catch (Exception ex) {
			getLogger().error("xxxxx！",ex);
			throw new BusinessException("xxxxxxxxxxxxx！"+ex.getMessage());
		} finally{
			/*if(table != null) table.clear();
			if(table1 != null) table1.clear();
			if(table2 != null) table2.clear();*/
			manager.cleanUp();
		}
		return log;
	}
	
	
	
	public static TBapiLog lockAsset(){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		
		JCO.Client client = null;

		TBapiLog log = null;
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate("ZF_GUDINGZICHAN_LOCK");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				//
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue("12010018","ANLN1_001" );//主资产号
				input.setValue("0","ANLN2_002");//资产次级编号
				input.setValue("2100","BUKRS_003");//公司代码
				input.setValue("","NO_SELECTION_004" );//
				input.setValue("X","XSPEB_005");//标识: 冻结资产以进行购置记帐


				//执行更新
				client.execute(function);
				//resultMarik=0创建成功，resultMarik！=0创建失败
				Object resultMarik = function.getExportParameterList().getValue("SUBRC");				
				System.out.println(resultMarik);
				//
				JCO.Table returnTable= function.getTableParameterList().getTable("MESSTABLE");
				if(returnTable.getNumRows()>0){
					try {
						do {
							StringBuilder buider = new StringBuilder("");
							for (JCO.FieldIterator fieldIte = returnTable.fields(); fieldIte.hasMoreElements();) {
								JCO.Field field = fieldIte.nextField();
								String sapfieldName = field.getName();
								String sapfieldvalue = field.getString();
								buider.append(sapfieldName+":"+sapfieldvalue+",");
							}
							System.out.println(buider.toString());
						} while (returnTable.nextRow());

					}finally{
						if(returnTable != null){
							returnTable.clear();
						}
					}
				}

			}
		} catch (Exception ex) {
			getLogger().error("xxxxx！",ex);
			throw new BusinessException("xxxxxxxxxxxxx！"+ex.getMessage());
		} finally{
			/*if(table != null) table.clear();
			if(table1 != null) table1.clear();
			if(table2 != null) table2.clear();*/
			manager.cleanUp();
		}
		return log;
	}
	
	public static TBapiLog unlockAsset(){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		
		JCO.Client client = null;

		TBapiLog log = null;
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate("ZF_GUDINGZICHAN_UNLOCK");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				//
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue("12010018","ANLN1_001" );//主资产号
				input.setValue("0","ANLN2_002");//资产次级编号,默认都为0
				input.setValue("2100","BUKRS_003");//公司代码
				input.setValue("X","NO_SELECTION_004" );//
				input.setValue("","XSPEB_005");//标识: 冻结资产以进行购置记帐


				//执行更新
				client.execute(function);
				//resultMarik=0创建成功，resultMarik！=0创建失败
				Object resultMarik = function.getExportParameterList().getValue("SUBRC");				
				System.out.println(resultMarik);
				//
				JCO.Table returnTable= function.getTableParameterList().getTable("MESSTABLE");
				if(returnTable.getNumRows()>0){
					try {
						do {
							StringBuilder buider = new StringBuilder("");
							for (JCO.FieldIterator fieldIte = returnTable.fields(); fieldIte.hasMoreElements();) {
								JCO.Field field = fieldIte.nextField();
								String sapfieldName = field.getName();
								String sapfieldvalue = field.getString();
								buider.append(sapfieldName+":"+sapfieldvalue+",");
							}
							System.out.println(buider.toString());
						} while (returnTable.nextRow());

					}finally{
						if(returnTable != null){
							returnTable.clear();
						}
					}
				}

			}
		} catch (Exception ex) {
			getLogger().error("xxxxx！",ex);
			throw new BusinessException("xxxxxxxxxxxxx！"+ex.getMessage());
		} finally{
			/*if(table != null) table.clear();
			if(table1 != null) table1.clear();
			if(table2 != null) table2.clear();*/
			manager.cleanUp();
		}
		return log;
	}
	
	
	public static Map<String,Object> updateSaleContractRate(String orderNo,String rate){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		
		JCO.Client client = null;
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate("ZM_MODIFY_VA02D");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");
				//
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(orderNo,"VBELN_001" );//订单号
				input.setValue(rate,"KURRF_002");//更改汇率
				//执行更新
				client.execute(function);
				//resultMarik=0创建成功，resultMarik！=0创建失败
				Object resultMarik = function.getExportParameterList().getValue("SUBRC");
				result.put("isSuccess",resultMarik);
				//
				JCO.Table returnTable= function.getTableParameterList().getTable("MESSTAB");
				if(returnTable.getNumRows()>0){
					StringBuilder buider = new StringBuilder("");
					try {
						do {
							
							for (JCO.FieldIterator fieldIte = returnTable.fields(); fieldIte.hasMoreElements();) {
								JCO.Field field = fieldIte.nextField();
								String sapfieldName = field.getName();
								String sapfieldvalue = field.getString();
								buider.append(sapfieldName+":"+sapfieldvalue+",");
							}
							buider.append("<br>");
						} while (returnTable.nextRow());
					}finally{
						result.put("msg",buider.toString());
						if(returnTable != null){
							returnTable.clear();
						}
					}
				}

			}
		} catch (Exception ex) {
			getLogger().error("更改销售订单汇率错误！",ex);
			throw new BusinessException("更改销售订单汇率错误！"+ex.getMessage());
		} finally{
			manager.cleanUp();
		}
		return result;
	}

	
	public static void main(String[] args){
		Map map = UpdataSapData.updateSaleContractRate("0010000119","5.98665");
		System.out.println(map);
	}



}
