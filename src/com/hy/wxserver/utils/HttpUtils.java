package com.hy.wxserver.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
/** 
 * HTTP工具�?
 *  
 * @author lixiangyang 
 *  
 */  
public class HttpUtils {  
  
    private static Logger log = LoggerFactory.getLogger(HttpUtils.class); 
    /** 
     * 定义编码格式 UTF-8 
     */  
    public static final String URL_PARAM_DECODECHARSET_UTF8 = "UTF-8";  
      
    /** 
     * 定义编码格式 GBK 
     */  
    public static final String URL_PARAM_DECODECHARSET_GBK = "GBK";  
      
    private static final String URL_PARAM_CONNECT_FLAG = "&";  
      
    private static final String EMPTY = "";  
  
    private static MultiThreadedHttpConnectionManager connectionManager = null;  
  
    private static int connectionTimeOut = 25000;  
  
    private static int socketTimeOut = 25000;  
  
    private static int maxConnectionPerHost = 20;  
  
    private static int maxTotalConnections = 20;  
  
    private static HttpClient client;  
  
    static{  
        connectionManager = new MultiThreadedHttpConnectionManager();  
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);  
        connectionManager.getParams().setSoTimeout(socketTimeOut);  
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);  
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);  
        client = new HttpClient(connectionManager);  
    }  
      
    /** 
     * POST方式提交数据 
     * @param url 
     *          待请求的URL 
     * @param params 
     *          要提交的数据 
     * @param enc 
     *          编码 
     * @return 
     *          响应结果 
     * @throws IOException 
     *          IO异常 
     */  
    public static String URLPost(String url, Map<String, Object> params, String charset){  
  
        String response = EMPTY;          
        PostMethod postMethod = null;  
        try {  
            postMethod = new PostMethod(url);  
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);  
            //将表单的值放入postMethod�? 
            Set<String> keySet = params.keySet();  
            for(String key : keySet){  
                String value = params.get(key) != null ? params.get(key).toString() : "";  
                postMethod.addParameter(key, value);  
            }             
            
            //执行postMethod  
            //log.debug("网络请求地址�? + url + "?" + getUrl(params, charset));
            int statusCode = client.executeMethod(postMethod);  
            if(statusCode == HttpStatus.SC_OK) {  
                response = postMethod.getResponseBodyAsString();  
            }else{  
                log.error("响应状�?�?= " + postMethod.getStatusCode());  
            }  
        }catch(HttpException e){  
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);  
            e.printStackTrace();  
        }catch(IOException e){  
            log.error("发生网络异常", e);  
            e.printStackTrace();  
        }finally{  
            if(postMethod != null){  
                postMethod.releaseConnection();  
                postMethod = null;  
            }  
        }  
          
        return response;  
    }  
    
    public static String URLPost(String url, Map<String, Object> params){
    	return URLPost(url, params, URL_PARAM_DECODECHARSET_UTF8);
    }
      
    /** 
     * GET方式提交数据 
     * @param url 
     *          待请求的URL 
     * @param params 
     *          要提交的数据 
     * @param enc 
     *          编码 
     * @return 
     *          响应结果 
     * @throws IOException 
     *          IO异常 
     */  
    public static String URLGet(String url, Map<String, Object> params, String charset){  
  
        String response = EMPTY;  
        GetMethod getMethod = null;       
        StringBuffer strtTotalURL = new StringBuffer(url);  
          
        if(strtTotalURL.indexOf("?") == -1) {  
          strtTotalURL.append("?").append(getUrl(params, charset));  
        } else {  
            strtTotalURL.append("&").append(getUrl(params, charset));  
        }  
        log.debug("GET请求URL = \n" + strtTotalURL.toString());  
          
        try {  
            getMethod = new GetMethod(strtTotalURL.toString());  
            getMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);  
            //执行getMethod  
            //log.debug("网络请求地址�? + url + "/" + getUrl(params, charset));
            int statusCode = client.executeMethod(getMethod);  
            if(statusCode == HttpStatus.SC_OK) {  
                response = getMethod.getResponseBodyAsString();  
            }else{  
                log.debug("响应状�?�?= " + getMethod.getStatusCode());  
            }  
        }catch(HttpException e){  
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);  
            e.printStackTrace();  
        }catch(IOException e){  
            log.error("发生网络异常", e);  
            e.printStackTrace();  
        }finally{  
            if(getMethod != null){  
                getMethod.releaseConnection();  
                getMethod = null;  
            }  
        }  
          
        return response;  
    }     
  
    /** 
     * 据Map生成URL字符�?
     * @param map 
     *          Map 
     * @param valueEnc 
     *          URL编码 
     * @return 
     *          URL 
     */  
    private static String getUrl(Map<String, Object> map, String charset) {  
          
        if (null != map && map.keySet().size() > 0) {  
            
	        StringBuffer url = new StringBuffer();  
	        Set<String> keys = map.keySet();  
	        for (Iterator<String> it = keys.iterator(); it.hasNext();) {  
	            String key = it.next();  
	            if (map.containsKey(key)) {  
	                String val = map.get(key).toString();  
	                String str = val != null ? val : EMPTY;  
	                try {  
	                    str = URLEncoder.encode(str, charset);  
	                } catch (UnsupportedEncodingException e) {  
	                    e.printStackTrace();  
	                }  
	                url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);  
	            }  
	        }  
	        String strURL = url.toString();
	        if (URL_PARAM_CONNECT_FLAG.equals(EMPTY + strURL.charAt(strURL.length() - 1))) {  
	            strURL = strURL.substring(0, strURL.length() - 1);  
	        }  
	          
	        return (strURL);  
        }else{
        	return EMPTY;
        }
    }  
    
    public static String URLGet(String url, Map<String, Object> params){  
    	return URLGet(url, params, URL_PARAM_DECODECHARSET_UTF8);
    }
    
    /** 
     * Delete方式提交数据 
     * @param url 
     *          待请求的URL 
     * @param params 
     *          要提交的数据 
     * @param enc 
     *          编码 
     * @return 
     *          响应结果 
     * @throws IOException 
     *          IO异常 
     */  
    public static String URLDelete(String url, Map<String, Object> params, String charset){  
        String response = EMPTY;  
        DeleteMethod deleteMethod = null;       
        StringBuffer strtTotalURL = new StringBuffer(EMPTY);  
          
        if(strtTotalURL.indexOf("?") == -1) {  
          strtTotalURL.append(url).append("?").append(getUrl(params, charset));  
        } else {  
            strtTotalURL.append(url).append("&").append(getUrl(params, charset));  
        }  
        log.debug("DELETE请求URL = \n" + strtTotalURL.toString());  
          
        try {  
        	deleteMethod = new DeleteMethod(strtTotalURL.toString());  
        	deleteMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);  
            //执行getMethod  
            log.debug("网络请求地址:" + url);
            int statusCode = client.executeMethod(deleteMethod);  
            if(statusCode == HttpStatus.SC_OK) {  
                response = deleteMethod.getResponseBodyAsString();  
            }else{  
                log.debug("响应状�?�?= " + deleteMethod.getStatusCode());  
            }  
        }catch(HttpException e){  
            log.error("发生致命的异常，可能是协议不对或者返回的内容有问题", e);  
            e.printStackTrace();  
        }catch(IOException e){  
            log.error("发生网络异常", e);  
            e.printStackTrace();  
        }finally{  
            if(deleteMethod != null){  
            	deleteMethod.releaseConnection();  
            	deleteMethod = null;  
            }  
        }  
          
        return response;  
    }

	public static String URLDelete(String url, Map<String, Object> params) {
		return URLDelete(url, params, URL_PARAM_DECODECHARSET_UTF8);
	}  
}  