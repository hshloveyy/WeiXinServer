/**
 * 
 */
package test.infolion.platform.util;

import java.io.FileInputStream;
import java.util.Properties;


import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author User
 *
 */
public class ConnOracle {

	/**
	 * @param args
	 */
	private static DriverManagerDataSource ds=null;
	private ConnOracle(){
		
	}
	public static DriverManagerDataSource getInst(){
		ds = new DriverManagerDataSource();
		//加载配置文件  
        Properties props = new Properties();  
        try  
        {  
            props.load(new FileInputStream("src/config/jdbc.properties"));  
        } catch (Exception e)  
        {  
  
            e.printStackTrace();  
        }  
        System.out.println(props.getProperty("jdbc.driverClassName"));
		ds.setDriverClassName(props.getProperty("jdbc.driverClassName"));
		ds.setUrl(props.getProperty("jdbc.url"));
		ds.setUsername(props.getProperty("jdbc.username"));
		ds.setPassword(props.getProperty("jdbc.password"));
		return ds;
	}
	
}
