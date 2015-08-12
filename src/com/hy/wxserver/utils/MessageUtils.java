package com.hy.wxserver.utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hy.wxserver.message.Message;
import com.hy.wxserver.message.response.MusicMessage;
import com.hy.wxserver.message.response.NewsMessage;
import com.hy.wxserver.message.response.RespBaseMessage;
import com.hy.wxserver.message.response.TextMessage;

/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-3-28 下午05:04:15
 * XML解析工具类
 */
public class MessageUtils {
	
	/************************* 响应消息 Start*************************/
	/** 
     * 返回消息类型：文本 
     */  
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 返回消息类型：音乐 
     */  
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";  
  
    /** 
     * 返回消息类型：图片 
     */  
    public static final String RESP_MESSAGE_TYPE_IMAGE = "image";  
    
    /** 
     * 返回消息类型：视频 
     */  
    public static final String RESP_MESSAGE_TYPE_VIDEO = "video";  
    
    /** 
     * 返回消息类型：语音 
     */  
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";  
    
    /** 
     * 返回消息类型：图文 
     */  
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";
    /************************* 响应消息 End*************************/
  
    /************************* 请求消息 Start*************************/
    /** 
     * 请求消息类型：文本 
     */  
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 请求消息类型：图片 
     */  
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";  
  
    /** 
     * 请求消息类型：链接 
     */  
    public static final String REQ_MESSAGE_TYPE_LINK = "link";  
  
    /** 
     * 请求消息类型：地理位置 
     */  
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";  
  
    /** 
     * 请求消息类型：音频 
     */  
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";  
  
    /**
     * 请求消息类型：视频
     */
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    
    /**
     * 请求消息类型：视频
     */
    public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
    
    /** 
     * 请求消息类型：推送 
     */  
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";  
    /************************* 请求消息 End*************************/
    
    /************************* 事件类型 Start*************************/
    /** 
     * 事件类型：subscribe(订阅) 
     */  
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";  
  
    /** 
     * 事件类型：unsubscribe(取消订阅) 
     */  
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";  
  
    /** 
     * 事件类型：CLICK(自定义菜单点击事件) 
     */  
    public static final String EVENT_TYPE_CLICK = "CLICK";  
    /************************* 事件类型 Start*************************/
	
	/**
	 * 解析微信发来的请求（XML）
	 *@author heshaohua	
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
	    // 将解析结果存储在HashMap中  
	    Map<String, String> map = new HashMap<String, String>();  
	  
	    // 从request中取得输入流  
	    InputStream inputStream = request.getInputStream();  
	    // 读取输入流  
	    SAXReader reader = new SAXReader();  
	    Document document = reader.read(inputStream);  
	    // 得到xml根元素  
	    Element root = document.getRootElement();  
	    // 得到根元素的所有子节点  
	    List<Element> elementList = root.elements();  
	  
	    // 遍历所有子节点  
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
	    // 释放资源  
	    inputStream.close();  
	    inputStream = null;  
	  
	    return map;  
	} 
	
	/** 
     * 文本消息对象转换成xml 
     *  
     * @param textMessage 文本消息对象 
     * @return xml 
     */  
    public static String textMessageToXml(TextMessage textMessage) {  
    	return parseEntity(textMessage);
    }  
  
    /** 
     * 音乐消息对象转换成xml 
     *  
     * @param musicMessage 音乐消息对象 
     * @return xml 
     */  
    public static String musicMessageToXml(MusicMessage musicMessage) {  
//        xstream.alias("xml", musicMessage.getClass());  
//        return xstream.toXML(musicMessage); 
        return null;
    }  
  
    /** 
     * 图文消息对象转换成xml 
     *  
     * @param newsMessage 图文消息对象 
     * @return xml 
     */  
    public static String newsMessageToXml(NewsMessage newsMessage) {  
//        xstream.alias("xml", newsMessage.getClass());  
//        xstream.alias("item", new Article().getClass());  
//        return xstream.toXML(newsMessage);  
        return null;
    }  
    
    /**
     * 
     *@author heshaohua	
     * @param message
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static String parseEntity(Message message){
    	//创建dom
    	Document doc = DocumentHelper.createDocument();
    	//创建root元素
    	Element root = doc.addElement("xml");
//    	//添加接收方帐号（收到的OpenID）
//    	Element toUserName = root.addElement("ToUserName");
//    	//添加开发者微信号
//    	Element fromUserName = root.addElement("FromUserName");
//    	//消息创建时间 （整型）
//    	Element createTime = root.addElement("CreateTime");
//    	//消息类型text
//    	Element msgType = root.addElement("MsgType");
//    	//回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
//    	Element content = root.addElement("Content");
    	
    	try {
			Class clazz = message.getClass();
			Class superClass = clazz.getSuperclass();
			
			Field[] superFields = superClass.getDeclaredFields();
			
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : superFields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				Element element = root.addElement(fieldName);
				Method method = superClass.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
				String result = method.invoke(message).toString();
				if("createtime".equals(fieldName.toLowerCase())){
					element.setText(result);
				}else{
					element.addCDATA(result);
				}
			}
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				Element element = root.addElement(fieldName);
				Method method = clazz.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
				String result = method.invoke(message).toString();
				element.addCDATA(result);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    	
		System.out.println(doc.asXML());
    	return doc.asXML();
    }

	public static RespBaseMessage responseMessage(String content) {
		return null;
	}
}
