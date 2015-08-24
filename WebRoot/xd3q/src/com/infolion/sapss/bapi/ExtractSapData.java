package com.infolion.sapss.bapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infolion.sapss.bapi.dto.MasterDataDto;
import com.infolion.sapss.common.NumberUtils;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

public class ExtractSapData {

	private static Log log;

	private static Log getLogger() {
		if (log == null) {
			log = LogFactory.getLog(ExtractSapData.class);
		}
		return log;
	}

	/**
	 * 根据SAP系统函数名称，表名查询SAP数据
	 * @param functionName SAP函数名称
	 * @param innerTableName SAP输出数据的内表名称
	 * @param para 参数
	 * @return
	 */
	public static JCO.Table getDicData(String functionName,
			String innerTableName, Map<String, Object> para) {
		JCO.Table jco_data = null;
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try {
			IFunctionTemplate ftemplate = manager.repository
					.getFunctionTemplate(functionName);
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				if (para != null) {
					for (Iterator<String> it = para.keySet().iterator(); it
							.hasNext();) {
						String key = it.next();
						String value = (String) para.get(key);
						// System.out.println(key+" "+value);
						input.setValue(value, key);
					}
					// function.setImportParameterList(input);
				}

				client.execute(function);
				jco_data = function.getTableParameterList().getTable(
						innerTableName);
				getLogger().info("从SAP上获取到" + jco_data.getNumRows() + "条记录");
			}
		} catch (Exception ex) {
			getLogger().error("", ex);
		} finally {
			manager.cleanUp();
		}
		return jco_data;
	}
	/**
	 * 
	 * @param functionName
	 * @param para
	 * @return
	 */
	public static Map getDicData(String functionName,Map<String, String> para) {
		Map map = new HashMap();
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try {
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate(functionName);
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				if (para != null) {
					for (Iterator<String> it = para.keySet().iterator(); it
							.hasNext();) {
						String key = it.next();
						String value = (String) para.get(key);
						input.setValue(value, key);
					}
				}
				client.execute(function);
				if(function.getExportParameterList()!=null){
					map.put("HSLVT1",function.getExportParameterList().getValue("HSLVT1"));//应收
					map.put("HSLVT2",function.getExportParameterList().getValue("HSLVT2"));//应付
				}
			}
		} catch (Exception ex) {
			getLogger().error("", ex);
		} finally {
			manager.cleanUp();
		}
		return map;
	}
	
	/**
	 * 
	 * @param functionName
	 * @param innerTableName
	 * @param para
	 * @param totalRecord_name
	 * @return
	 */
	private static MasterDataDto getMasterData(String functionName,
			String innerTableName, Map<String, Object> para,
			String totalRecord_name) {
		MasterDataDto dto = new MasterDataDto();

		JCO.Table jco_data = null;
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try {
			IFunctionTemplate ftemplate = manager.repository
					.getFunctionTemplate(functionName);
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				if (para != null) {
					for (Iterator<String> it = para.keySet().iterator(); it
							.hasNext();) {
						String key = it.next();
						Object value = para.get(key);
						input.setValue(value, key);
					}
					// function.setImportParameterList(input);
				}
				client.execute(function);
				jco_data = function.getTableParameterList().getTable(
						innerTableName);
				getLogger().info("从SAP上获取到" + jco_data.getNumRows() + "条记录");
				dto.setData(getList(jco_data));
				Integer totalNumber = (Integer) function
						.getExportParameterList().getValue(totalRecord_name);

				dto.setTotalNumber(totalNumber.intValue() + "");
				getLogger().info("总记录数为：" + totalNumber + "条记录");
			}
		} catch (Exception ex) {
			getLogger().error("获取主数据发生错误", ex);
		} finally {
			manager.cleanUp();
		}
		return dto;

	}
	/**
	 * 修改获取异常信息及对不传totalRecord_name的处理
	 * @param functionName
	 * @param innerTableName
	 * @param para
	 * @param totalRecord_name
	 * @return
	 */
	public static MasterDataDto getMasterData_(String functionName,
			String innerTableName, Map<String, Object> para,
			String errorTable) {
		MasterDataDto dto = new MasterDataDto();
		
		JCO.Table jco_data = null;
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		JCO.Function function = null;
		try {
			IFunctionTemplate ftemplate = manager.repository
			.getFunctionTemplate(functionName);
			if (ftemplate != null) {
				function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				if (para != null) {
					for (Iterator<String> it = para.keySet().iterator(); it.hasNext();) {
						String key = it.next();
						Object value = para.get(key);
						input.setValue(value, key);
					}
					// function.setImportParameterList(input);
				}
				client.execute(function);
				jco_data = function.getTableParameterList().getTable(innerTableName);//innerTableName
				getLogger().info("从SAP上获取到" + jco_data.getNumRows() + "条记录");
				List list = getList(jco_data);
				dto.setData(list);
				dto.setTotalNumber(list.size()+"");
				getLogger().info("总记录数为：" + list.size()+ "条记录");
				if(errorTable!=null){
					jco_data = function.getTableParameterList().getTable(errorTable);//
					List elist = getList(jco_data);
					dto.setErrData(elist);
				}
			}
		} catch (Exception ex) {
			jco_data = function.getTableParameterList().getTable(errorTable);//
			List elist = getList(jco_data);
			dto.setErrData(elist);
			getLogger().error("获取主数据发生错误", ex);
		} finally {
			manager.cleanUp();
		}
		return dto;
	}
	/**
	 * 组装JCO.TABLE 数据
	 * @param data
	 * @return
	 */
	public static List<Map<String, String>> getList(JCO.Table data) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (data.getNumRows() > 0) {
			try {
				do {
					Map<String, String> map = new HashMap<String, String>();
					for (JCO.FieldIterator fieldIte = data.fields(); fieldIte
							.hasMoreElements();) {
						JCO.Field field = fieldIte.nextField();
						String sapfieldName = field.getName();
						String sapfieldValue = field.getString();
						map.put(sapfieldName, sapfieldValue);
					}
					list.add(map);
				} while (data.nextRow());
			} finally {
				if (data != null) {
					data.clear();
				}
			}
		}
		return list;
	}
	
	/**
	 * 组装JCO.TABLE数据，在循环遍历内调用该函数，同时自动计算总计
	 * @param data
	 * @param list 循环外部必须先初始化该变量
	 * @param sumMap 总计列、循环外部必须先初始化该变量
	 */
	public static void assembleJcoTable(JCO.Table data,List<Map<String, String>> list,Map<String, String> sumMap) {
		if (data.getNumRows() > 0) {
			try {
				do {
					Map<String, String> map = new HashMap<String, String>();
					for (JCO.FieldIterator fieldIte = data.fields(); fieldIte
							.hasMoreElements();) {
						JCO.Field field = fieldIte.nextField();
						String sapfieldName = field.getName();
						String sapfieldValue = field.getString();
						map.put(sapfieldName, sapfieldValue);
						parseNumMap(sumMap, sapfieldName, sapfieldValue);
					}
					list.add(map);
				} while (data.nextRow());
			} finally {
				if (data != null) {
					data.clear();
				}
			}
		}
	}
	/**
	 * 合计处理、自动跨过非数值域
	 * @param map
	 * @param key
	 * @param assValue
	 */
	private static void parseNumMap(Map<String,String> map ,String key ,String assValue){
		String value = map.get(key);
		try{
			Double d = Double.parseDouble(value);
			Double aDouble  = Double.parseDouble(assValue);
			map.put(key, NumberUtils.round(d+aDouble,2));
		}
		catch(Exception e){
			map.put(key, assValue);
		}
		
	}

	/**
	 * 供应商主数据查询方法
	 * 
	 * @param para
	 *            Map<String,Object> 类型，查询传参。key值描述如下 <br>
	 *            I_LIFNR 供应商或债权人的帐号 <br>
	 *            I_NAME1 名称 <br>
	 *            I_SORTL 排序字段 <br>
	 *            I_EKORG 采购组织 <br>
	 *            I_NEEDSUBDATA 是否需要分段数据（如需分页，值为1） <br>
	 *            I_STARTROWNUM 起始行数 <br>
	 *            I_ENDROWNUM 结束行数
	 * @return
	 */
	public static MasterDataDto getSupplierMasterData(Map<String, Object> para) {
		return getMasterData(BapiConstant.SUPPLIER_MASTER_DATA_FUNCTION_NAME,
				BapiConstant.SUPPLIER_MASTER_DATA_INNERTABLE_NAME, para,
				BapiConstant.SUPPLIER_MASTER_DATA_TOTALRECORDS);
	}

	/**
	 * 客户主数据查询方法
	 * 
	 * @param para
	 *            Map<String,Object>类型，查询传参。key值描述如下： <br>
	 *            I_KUNNR 客户编号 <br>
	 *            I_NAME 名称 <br>
	 *            I_SORTL 排序字段 <br>
	 *            I_VKORG 销售组织 <br>
	 *            I_VTWEG 分销渠道 <br>
	 *            I_SPART 产品组 <br>
	 *            I_FLAG 是否需要分段数据（如需分页，值为1） <br>
	 *            I_FROM 起始行 <br>
	 *            I_END 结束行
	 * @return
	 */
	public static MasterDataDto getCustomerMasterData(Map<String, Object> para) {
		return getMasterData(BapiConstant.CUSTOMER_MASTER_DATA_FUNCTION_NAME,
				BapiConstant.CUSTOMER_MASTER_DATA_INNERTABLE_NAME, para,
				BapiConstant.CUSTOMER_MASTER_DATA_TOTALRECORDS);
	}

	/**
	 * 物料主数据查询方法
	 * 
	 * @param para
	 *            Map<String,Object>类型，查询传参。key值描述如下： <br>
	 *            I_MATNR 物料号 <br>
	 *            I_MAKTX 物料描述（短文本） <br>
	 *            I_MTART 物料类型 <br>
	 *            I_WERKS 关于国家(集中批准)合同的工厂表 <br>
	 *            I_VKORG 销售组织 <br>
	 *            I_EKGRP 采购组 <br>
	 *            I_MTVER 出口/进口物料组 <br>
	 *            I_BKLAS 评估类 <br>
	 *            I_BWTAR 有关估价记录的物料主记录视图 <br>
	 *            I_NEEDSUBDATA 是否需要分段数据（如需分页，值为1） <br>
	 *            I_STARTROWNUM 起始行数 <br>
	 *            I_ENDROWNUM 结束行数
	 * @return
	 */
	public static MasterDataDto getMaterialMasterData(Map<String, Object> para) {
		return getMasterData(BapiConstant.MATERIAL_MASTER_DATA_FUNCTION_NAME,
				BapiConstant.MATERIAL_MASTER_DATA_INNERTABLE_NAME, para,
				BapiConstant.MATERIAL_MASTER_DATA_TOTALRECORDS);
	}

	/**
	 * 存货浮动盈亏主数据主数据查询方法
	 * 
	 * @param para
	 *            Map<String,Object>类型，查询传参。key值描述如下： <br>
	 *            BUKRS 公司 <br>
	 *            BUDAT 日期 <br>
	 *                  部门 <br>
	 * @return
	 */
	public static MasterDataDto getProfitLossData(Map<String, Object> para, Map<String, Object> tbPara) {
		MasterDataDto dto = new MasterDataDto();

		JCO.Table jco_data = null;
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();
		JCO.Client client = null;
		try {
			IFunctionTemplate ftemplate = manager.repository
					.getFunctionTemplate(BapiConstant.PROFITLOSS_DATA_FUNCTION_NAME);
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient(manager.poolName);
				JCO.ParameterList input = function.getImportParameterList();
				if (para != null) {
					for (Iterator<String> it = para.keySet().iterator(); it
							.hasNext();) {
						String key = it.next();
						Object value = para.get(key);
						input.setValue(value, key);
					}
					// function.setImportParameterList(input);
				}
				if (tbPara != null) {
					JCO.Table table = function.getTableParameterList().getTable("T_DEPARTMENT");
					for (Iterator<String> it = tbPara.keySet().iterator(); it
							.hasNext();) {
						String key = it.next();
						
						Object value = tbPara.get(key);
						table.appendRow();
						table.setValue(value, key);
						//table.setValue("1501","WERKS");
					}
					// function.setImportParameterList(input);
				}
				client.execute(function);
				jco_data = function.getTableParameterList().getTable(
						BapiConstant.PROFITLOSS_DATA_INNERTABLE_NAME);
				getLogger().info("从SAP上获取到" + jco_data.getNumRows() + "条记录");
				dto.setData(getList(jco_data));
				//Integer totalNumber = (Integer) function
				//		.getExportParameterList().getValue(totalRecord_name);

				//dto.setTotalNumber(totalNumber.intValue() + "");
				//getLogger().info("总记录数为：" + totalNumber + "条记录");
			}
		} catch (Exception ex) {
			getLogger().error("获取主数据发生错误", ex);
		} finally {
			manager.cleanUp();
		}
		return dto;
		/*
		return getMasterData(BapiConstant.PROFITLOSS_DATA_FUNCTION_NAME,
				BapiConstant.PROFITLOSS_DATA_INNERTABLE_NAME, para,
				BapiConstant.PROFITLOSS_DATA_TOTALRECORDS);
		*/		
	}	
	/**
	 * 根据销售部门编码查询销售组
	 * 
	 * @param para
	 *            传参，传销售部门号码：key值为：I_VKBUR
	 * @return
	 */
	public static List<Map<String, String>> getSaleGroupList(
			Map<String, Object> para) {
		return getList(getDicData("ZM_VKGRP", "IT_RETAB", para));
	}

	public static void main(String[] args) {
		ExtractSapData dat = new ExtractSapData();
		Map map = new HashMap<String, String>();
//		//map.put("XABKR", "10");
//		map.put("WERKS", "1001");
//		map.put("PERNR", "280004");
//		map.put("ORGEH", "10000003");
//		map.put("PABRJ", "2009");
//		map.put("PABRP", "04");
//		/*
//		 * map.put("I_NAME1",""); map.put("I_LIFNR", ""); map.put("I_EKORG",
//		 * "");
//		 */
//		List<Map<String, String>> list = dat.getList(dat.getDicData("ZM_GETPAYRESULT", "IT_ALVOUT", map));
//		//List<Map<String, String>> list = getSaleGroupList(null);
//		System.out.println(list);
		map.put("I_RLDNR", "0L");
		map.put("I_BUKRS", "2101");
		map.put("I_GJAHR", "2009");
		map.put("I_MONAT", "06");
		MasterDataDto dto = getMasterData_("ZF_ZFIRPT026B","IT_OUTPUT", map,"IT_BAPIRET2");
		System.out.println(dto.getErrData().size());
	}
	private void test(){
		Map map=new HashMap();
//		map.put("I_RLDNR", "0L");
//		map.put("I_BUKRS", "2100");
//		map.put("I_GJAHR", "2009");
//		map.put("I_MONAT", "06");
		MasterDataDto dto = this.getMasterData_("ZF_ZFIRPT002","IT_PRINT01", map,null);
		System.out.println(dto.getData().size());
	}
	/**
	 * 根据工厂，物料号，批号，动态查询批次号
	 * 
	 * @param para
	 *            传参 <br>
	 *            WERKS 工厂 <br>
	 *            MATNR 物料号 <br>
	 *            CHARG 批号
	 * @return
	 */
	public static List<String> getBatchNo(Map<String, Object> para) {
		JCO.Table table = getDicData("ZM_BATCH", "IT_RETAB", para);
		List<String> list = new ArrayList<String>();
		if (table.getNumRows() > 0) {
			try {
				do {
					for (JCO.FieldIterator fieldIte = table.fields(); fieldIte
							.hasMoreElements();) {
						JCO.Field field = fieldIte.nextField();
						String sapfieldValue = field.getString();
						list.add(sapfieldValue);
					}
				} while (table.nextRow());
			} finally {
				if (table != null) {
					table.clear();
				}
			}
		}
		return list;
	}

}
