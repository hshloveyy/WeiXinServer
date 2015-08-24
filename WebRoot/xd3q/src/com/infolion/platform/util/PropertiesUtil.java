package com.infolion.platform.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;


public class PropertiesUtil{
	public PropertiesUtil(String path){
		this.path = path;
	}
	private static String path;
	private static Properties p;
	public static String getProperty(String property){
		if(p==null)
			load();
		return p.getProperty(property);
		
	}
	private static void load(){
		p = new Properties();
		try {
			p.load(PropertiesUtil.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Properties sysProperties;
	
	static {
		try {
			//sysProperties.load(PropertiesUtil.class.getResourceAsStream("\\config\\config.properties"));
			sysProperties = PropertiesLoaderUtils.loadAllProperties("\\config\\config.properties");
		} catch (IOException e) {
			sysProperties = new Properties(); 
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		System.out.println("10.2.2.11".substring(0,"10.2.2.11".lastIndexOf(".")));
	}
}
