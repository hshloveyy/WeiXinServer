package com.hy.wxserver.message.request;

/** 
 * �ı���Ϣ 
 *  
 * @author heshaohua 
 * @date 2014-3-28 
 */  
public class TextMessage extends ReqBaseMessage {  
    // ��Ϣ����  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}  
