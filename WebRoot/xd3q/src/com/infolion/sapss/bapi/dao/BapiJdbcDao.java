package com.infolion.sapss.bapi.dao;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Repository;

import com.infolion.sapss.bapi.BapiConstant;
import com.infolion.sapss.bapi.ExtractSapData;
import com.sap.mw.jco.JCO;


@Repository
public class BapiJdbcDao extends com.infolion.platform.core.dao.BaseDao{


	
	public void synchronizeAllData() throws IOException{
		String pathChar = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		File file = new File(pathChar.substring(1,(pathChar.length()-1))+BapiConstant.DICTIONARY_PATH);
        String[] fileNames = file.list(new FilenameFilter(){
        	private Pattern pattern=Pattern.compile(BapiConstant.DICTIONARY_REGEX);
			public boolean accept(File dir, String name) {
				return pattern.matcher(name).matches();
			}
        });
        for(String filename :fileNames){
        	this.synchronizeData(filename.substring(0,filename.indexOf(".")), null);
        }
   	}

	public void synchronizeData(String srcTableName,Map<String,Object> para) throws IOException{
		Properties pro = PropertiesLoaderUtils.loadAllProperties(BapiConstant.DICTIONARY_PATH+
				srcTableName+"."+BapiConstant.DICTIONARY_CONFIGFILE_EXTEND_NAME);
		String sap_function = pro.getProperty(BapiConstant.DICTIONARY_FUNCTION_NAME);
		String desTableName = pro.getProperty(BapiConstant.DICTIONARY_DES_TABLE_NAME);
		String sap_innnerTable = pro.getProperty(BapiConstant.DICTIONARY_INNERTABLE_NAME);
		if(para==null)  para = new HashMap<String,Object>();
		para.putAll(BapiConstant.getParaMeter(pro.getProperty(BapiConstant.DICTIONARY_PARAMETER_NAME)));
		JCO.Table data = ExtractSapData.getDicData(sap_function, sap_innnerTable, para);
		String delSQL = "delete from " + desTableName;
		this.log.info("----------开始删除表:"+desTableName+"数据------------");
		this.getJdbcTemplate().update(delSQL);
		this.log.info("----------删除表："+desTableName+"数据完成------------");

		String inssqlfield = "Insert into "+desTableName+"(";
		String inssqlvalue = " values(";
		String oafieldname = "";
		String oafieldvalue = "";
		String sapfieldvalue = "";
		if(data.getNumRows()>0){
			this.log.info("---------开始插入表："+desTableName+"数据---------");
			do {
				for (JCO.FieldIterator fieldIte = data.fields(); fieldIte.hasMoreElements();) {
					JCO.Field field = fieldIte.nextField();
					oafieldname = pro.getProperty(field.getName().trim());
					sapfieldvalue = field.getString().trim();
					sapfieldvalue = sapfieldvalue.replaceAll("'", "''");
					inssqlfield = inssqlfield + oafieldname + ",";
					oafieldvalue = "'"+sapfieldvalue+"'";
					inssqlvalue = inssqlvalue + oafieldvalue + ",";
				}
				inssqlfield = inssqlfield.substring(0, inssqlfield.length() - 1)
				+ ")";
				inssqlvalue = inssqlvalue.substring(0, inssqlvalue.length() - 1)
				+ ")";
				log.info(inssqlfield+inssqlvalue);
				//System.out.println(inssqlfield+inssqlvalue);
				this.getJdbcTemplate().update(inssqlfield+inssqlvalue);
				inssqlfield = "Insert into "+desTableName+"(";
				inssqlvalue = " values(";
			} while (data.nextRow());
			this.log.info("共插入："+data.getNumRows()+"条数据");
			if(data != null){
				data.clear();
			}

		}
	}
}
