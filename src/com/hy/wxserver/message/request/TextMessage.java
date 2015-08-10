package com.hy.wxserver.message.request;

/** 
 * 文本消息 
 *  
 * @author heshaohua 
 * @date 2014-3-28 
 */  
public class TextMessage extends ReqBaseMessage {  
    // 消息内容  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}  
