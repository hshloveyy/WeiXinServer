package com.infolion.sapss.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jasson.im.api.APIClient;

/**
 * 移动信息机开发工具包
 * @author Administrator
 *
 */
public class MASUtils {
	protected static Log log = LogFactory.getFactory().getLog(MASUtils.class);

	static APIClient apiClient = null;
	
	public static void init(){
		String ip="172.16.18.9";
		String name="xdss";
		String pwd="xdss";
		String apiId="xdss";
		String dbName="mas";
		if(apiClient==null) apiClient = new APIClient();
		int connectRe = apiClient.init(ip, name, pwd, apiId, dbName);
		if(connectRe == APIClient.IMAPI_SUCC)
		log.info("初始化成功");
		else if(connectRe == APIClient.IMAPI_CONN_ERR)
		log.error("连接失败");
		else if(connectRe == APIClient.IMAPI_API_ERR)
		log.error("apiID不存在");
	}
	
	public static void sendSM(String mobileStr,String content)
	{
		//多个手机号码逗号隔开
		ArrayList mobileList = new ArrayList();
        StringTokenizer st = new StringTokenizer(mobileStr, ",");
        while(st.hasMoreElements())
        {
                String tmp = (String)st.nextElement();
                mobileList.add(tmp);
        }
        String[] mobiles = new String[0];
        mobiles = (String[]) mobileList.toArray(mobiles);
        String sendTime="";
        long srcID=10;
		long smID=10;
	    int	result = apiClient.sendSM(mobiles, content, sendTime, smID, srcID);
	    if(result == APIClient.IMAPI_SUCC)
        {
            log.info("发送成功\n");
        }
        else if(result == APIClient.IMAPI_INIT_ERR)
            log.warn("未初始化");
        else if(result == APIClient.IMAPI_CONN_ERR)
            log.error("数据库连接失败");
        else if(result == APIClient.IMAPI_DATA_ERR)
        	log.error("参数错误");
        else if(result == APIClient.IMAPI_DATA_TOOLONG)
        	log.warn("消息内容太长");
        else if(result == APIClient.IMAPI_INS_ERR)
        	log.error("数据库插入错误");
        else if(result == APIClient.IMAPI_IFSTATUS_INVALID)
        	log.error("接口处于暂停或失效状态");
        else if(result == APIClient.IMAPI_GATEWAY_CONN_ERR)
        	log.error("短信网关未连接");
        else
        	log.error("出现其他错误");
	}
	
	public static void release(){
		if(apiClient!=null) apiClient.release();
		log.info("release成功");
	}
	
	public static void main(String[] args){
		MASUtils.init();
		MASUtils.sendSM("13003934012,15960230210", "短信发送测试内容 via  IM JAVA API ");
		MASUtils.release();
	}
	

}
