package com.hy.wxserver.message.request;

/** 
 * Í¼Æ¬ÏûÏ¢ 
 *  
 * @author heshaohua 
 * @date 2014-3-28 
 */  
public class ImageMessage extends ReqBaseMessage {  
    // Í¼Æ¬Á´½Ó  
    private String PicUrl;  
  
    public String getPicUrl() {  
        return PicUrl;  
    }  
  
    public void setPicUrl(String picUrl) {  
        PicUrl = picUrl;  
    }  
}  
