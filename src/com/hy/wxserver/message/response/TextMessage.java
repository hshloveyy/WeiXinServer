package com.hy.wxserver.message.response;


/** 
 * 文本消息 
 *  
 * @author heshaohua 
 * @date 2014-3-28 
 */  
public class TextMessage extends RespBaseMessage {  
    // 消息内容  
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
