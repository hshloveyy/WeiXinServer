package com.infolion.sapss.bapi;

import java.util.List;
import java.util.Map;

public interface IExtractData {
	
	public List<Map<String,String>> getList(String functionName,String innerTableName,Map<String,String> para);

}
