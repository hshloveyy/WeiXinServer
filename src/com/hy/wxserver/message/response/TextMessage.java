package com.hy.wxserver.message.response;


/** 
 * �ı���Ϣ 
 *  
 * @author heshaohua 
 * @date 2014-3-28 
 */  
public class TextMessage extends RespBaseMessage {  
    // ��Ϣ����  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }

	public String parseXML() {
		String xml = "<xml><ToUserName><![CDATA["
				+ ToUserName
				+ "]]></ToUserName><FromUserName><![CDATA["
				+ FromUserName
				+ "]]></FromUserName><CreateTime>"
				+ CreateTime
				+ "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["
				+ Content + "]]></Content></xml>";
		return xml;
	}

}  
