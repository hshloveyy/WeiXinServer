package com.infolion.platform.component.processor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


import com.infolion.platform.console.menu.domain.SysResource;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.util.PropertiesUtil;
/**
 * 反射处理类
 * @author cxp
 *
 */
public class ReflectionHandler {
	private static PropertiesUtil p;
	ReflectionHandler(){
		 p = new PropertiesUtil("/config/config.properties");
	}
	private static List<SysResource> list;
	/**
	 * 页面调用
	 * @param type
	 * @param model
	 * @param pageResourceName
	 * @return
	 */
	public static Object process(String type,String model,String pageResourceName){
		list = UserContextHolder.getLocalUserContext().getUserContext().getGrantedResources();
		type = p.getProperty("authority_process_path")+"."+type;
		return execute(type,new Object[]{new String(model),list,new String(pageResourceName)});

	}
	/**
	 * 
	 * @param type
	 * @param model
	 * @param pageResourceName
	 * @return
	 */
	public static Object process(String type,String[] model,String[] pageResourceName){
		list = UserContextHolder.getLocalUserContext().getUserContext().getGrantedResources();
		type = p.getProperty("authority_process_path")+"."+type;
		return execute(type,new Object[]{model,list,pageResourceName});

	}

	/**
	 * 执行反射类的方法
	 * @param className 类的全路径名
	 * @param method 方法名
	 * @param args 方法的参数
	 * @return	执行方法返回的值
	 */
	private static Object execute(String className, Object[] args) {
		Class aa = null;
		Method mt = null;
		try {
			aa = Class.forName(className);
			Class[] argsClass = null;
			if (args != null) {
				int len = args.length;
				argsClass = new Class[len];
				for (int i = 0; i < len; i++) {
					argsClass[i] = args[i].getClass();
				}
			}
			mt = aa.getMethod("processor", argsClass);
			return mt.invoke(aa.newInstance(), args);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
