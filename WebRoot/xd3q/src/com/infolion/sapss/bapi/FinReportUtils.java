package com.infolion.sapss.bapi;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infolion.platform.core.BusinessException;
import com.infolion.platform.util.StringUtils;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

/**
 * 财务分析报告SAPBAPI取数工具类
 * @author cat
 *
 */
public class FinReportUtils {

	private static Log log;

	private static Log getLogger() {
		if (log == null) {
			log = LogFactory.getLog(ExtractSapData.class);
		}
		return log;
	}

	/**
	 * 经营情况概述BAPI
	 * @param year
	 * @param month
	 * @param comCodes
	 * @param busCodes
	 */
	public static Map<String,String> queryGenneral(String year,String month,String comCodes,String busCodes){
		ConnectManager manager = ConnectManager.manager;
		manager.getClient();		
		JCO.Client client = null;
		JCO.Table table = null;
		JCO.Table table1= null;
		JCO.Table jco_data = null;
		Map<String,String> map = new HashMap<String,String>();
		try {
			IFunctionTemplate ftemplate = manager.repository.getFunctionTemplate("ZF_JYQK");
			if (ftemplate != null) {
				JCO.Function function = ftemplate.getFunction();
				client = JCO.getClient("R3");		
				JCO.ParameterList input = function.getImportParameterList();
				input.setValue(year, "P_GJAHR");//会计年度
				input.setValue(month, "P_MONAT");//会计期间

				table = function.getTableParameterList().getTable("PT_BUKRS");
				for(String comCode : comCodes.split(",")){
					table.appendRow();
					table.setValue(comCode, "STR");//公司代码
				}
				table1 = function.getTableParameterList().getTable("PT_GSBER");
				if(!StringUtils.isNullBlank(busCodes)){
					for(String busCode : busCodes.split(",")){
						table1.appendRow();
						table1.setValue(busCode, "STR");//业务范围
					}
				}
				client.execute(function);
				jco_data = function.getTableParameterList().getTable("OT_JYQK");
				getLogger().info("从SAP上获取经营情况概述：" + jco_data.getNumRows() + "条记录");
				if(jco_data.getNumRows()!=1) throw new BusinessException("返回："+jco_data.getNumRows()+"条数据！");
				do {
					for (JCO.FieldIterator fieldIte = jco_data.fields(); fieldIte.hasMoreElements();) {
						JCO.Field field = fieldIte.nextField();
						String sapfieldName = field.getName();
						String sapfieldValue = field.getString();
						map.put(sapfieldName, sapfieldValue);
					}
				} while (jco_data.nextRow());
			}
		} catch (Exception ex) {
			getLogger().error("从SAP上获取经营情况概述错误！",ex);
			throw new BusinessException("从SAP上获取经营情况概述错误！"+ex.getMessage());
		} finally{
			if(table != null) table.clear();
			if(table1 != null) table1.clear();
			if(jco_data != null) table1.clear();
			manager.cleanUp();
		}
		return map;
	}

}
