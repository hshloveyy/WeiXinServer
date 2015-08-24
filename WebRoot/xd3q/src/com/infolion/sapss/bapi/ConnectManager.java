package com.infolion.sapss.bapi;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;

public class ConnectManager {
	
    private static Log log;
	
	private Log getLogger() {
		if (log == null) {
			log = LogFactory.getLog(this.getClass());
		}
		return log;
	}
	
	//private static String propertiesPah ="D:\\java\\workspace\\xindecoSAPSS\\src\\config\\config.properties";
	
	private static String propertiesPah ="\\config\\config.properties";
	
	public static String poolName = "R3";

	
	public IRepository repository;

	private static String CLIENT = "";

	private static String RFC_USER = "";

	private static String RFC_PASS = "";

	private static String HOST = "";
	
	private static String SID = "";
	
	private static Properties properties = null;
	
	public static ConnectManager manager = new ConnectManager();
	
	private ConnectManager(){
		init();
	}
	
	private void init() {
		properties = new Properties();
		try{
			properties = PropertiesLoaderUtils.loadAllProperties(propertiesPah);
		}
		catch (Exception e) {
			this.getLogger().error("加载SAP配置文件失败:"+e.getMessage());
			// TODO: handle exception
		}
	    CLIENT = properties.getProperty("CLIENT").trim();
	    RFC_USER=properties.getProperty("RFC_USER").trim();
	    RFC_PASS=properties.getProperty("RFC_PASS").trim();
	    HOST=properties.getProperty("HOST").trim();
	    SID=properties.getProperty("SID").trim();
	    
	}
	
	/**
	 * 连接SAP
	 */
	public void getClient() {
		try {
			JCO.addClientPool(poolName, // Alias for this pool
					100, // Max. number of connections
					CLIENT, // SAP client
					RFC_USER, // userid
					RFC_PASS, // password
					"ZH", // language
					HOST, // host name
					SID);//SID
 			repository = JCO.createRepository("MYRepository", poolName);
		} catch (JCO.Exception ex) {
			this.getLogger().error("链接SAP错误："+ex.getMessage());
		}
	}
	/**
	 * 关闭连接
	 */
	public void cleanUp() {
		JCO.removeClientPool(poolName);
	}
	public static void main(String[] args){
	}

}
