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
 * @version ����ʱ�䣺2014-3-28 ����05:04:15
 * XML����������
 */
public class MessageUtils {
	
	/************************* ��Ӧ��Ϣ Start*************************/
	/** 
     * ������Ϣ���ͣ��ı� 
     */  
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * ������Ϣ���ͣ����� 
     */  
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";  
  
    /** 
     * ������Ϣ���ͣ�ͼƬ 
     */  
    public static final String RESP_MESSAGE_TYPE_IMAGE = "image";  
    
    /** 
     * ������Ϣ���ͣ���Ƶ 
     */  
    public static final String RESP_MESSAGE_TYPE_VIDEO = "video";  
    
    /** 
     * ������Ϣ���ͣ����� 
     */  
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";  
    
    /** 
     * ������Ϣ���ͣ�ͼ�� 
     */  
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";
    /************************* ��Ӧ��Ϣ End*************************/
  
    /************************* ������Ϣ Start*************************/
    /** 
     * ������Ϣ���ͣ��ı� 
     */  
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * ������Ϣ���ͣ�ͼƬ 
     */  
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";  
  
    /** 
     * ������Ϣ���ͣ����� 
     */  
    public static final String REQ_MESSAGE_TYPE_LINK = "link";  
  
    /** 
     * ������Ϣ���ͣ�����λ�� 
     */  
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";  
  
    /** 
     * ������Ϣ���ͣ���Ƶ 
     */  
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";  
  
    /**
     * ������Ϣ���ͣ���Ƶ
     */
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    
    /**
     * ������Ϣ���ͣ���Ƶ
     */
    public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
    
    /** 
     * ������Ϣ���ͣ����� 
     */  
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";  
    /************************* ������Ϣ End*************************/
    
    /************************* �¼����� Start*************************/
    /** 
     * �¼����ͣ�subscribe(����) 
     */  
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";  
  
    /** 
     * �¼����ͣ�unsubscribe(ȡ������) 
     */  
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";  
  
    /** 
     * �¼����ͣ�CLICK(�Զ���˵�����¼�) 
     */  
    public static final String EVENT_TYPE_CLICK = "CLICK";  
    /************************* �¼����� Start*************************/
	
	/**
	 * ����΢�ŷ���������XML��
	 *@author heshaohua	
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
	    // ����������洢��HashMap��  
	    Map<String, String> map = new HashMap<String, String>();  
	  
	    // ��request��ȡ��������  
	    InputStream inputStream = request.getInputStream();  
	    // ��ȡ������  
	    SAXReader reader = new SAXReader();  
	    Document document = reader.read(inputStream);  
	    // �õ�xml��Ԫ��  
	    Element root = document.getRootElement();  
	    // �õ���Ԫ�ص������ӽڵ�  
	    List<Element> elementList = root.elements();  
	  
	    // ���������ӽڵ�  
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
	    // �ͷ���Դ  
	    inputStream.close();  
	    inputStream = null;  
	  
	    return map;  
	} 
	
	/** 
     * �ı���Ϣ����ת����xml 
     *  
     * @param textMessage �ı���Ϣ���� 
     * @return xml 
     */  
    public static String textMessageToXml(TextMessage textMessage) {  
    	return parseEntity(textMessage);
    }  
  
    /** 
     * ������Ϣ����ת����xml 
     *  
     * @param musicMessage ������Ϣ���� 
     * @return xml 
     */  
    public static String musicMessageToXml(MusicMessage musicMessage) {  
//        xstream.alias("xml", musicMessage.getClass());  
//        return xstream.toXML(musicMessage); 
        return null;
    }  
  
    /** 
     * ͼ����Ϣ����ת����xml 
     *  
     * @param newsMessage ͼ����Ϣ���� 
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
    	//����dom
    	Document doc = DocumentHelper.createDocument();
    	//����rootԪ��
    	Element root = doc.addElement("xml");
//    	//��ӽ��շ��ʺţ��յ���OpenID��
//    	Element toUserName = root.addElement("ToUserName");
//    	//��ӿ�����΢�ź�
//    	Element fromUserName = root.addElement("FromUserName");
//    	//��Ϣ����ʱ�� �����ͣ�
//    	Element createTime = root.addElement("CreateTime");
//    	//��Ϣ����text
//    	Element msgType = root.addElement("MsgType");
//    	//�ظ�����Ϣ���ݣ����У���content���ܹ����У�΢�ſͻ��˾�֧�ֻ�����ʾ��
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
