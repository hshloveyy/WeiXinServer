package com.hy.wxserver.message.request;

/** 
 * ͼƬ��Ϣ 
 *  
 * @author heshaohua 
 * @date 2014-3-28 
 */  
public class ImageMessage extends ReqBaseMessage {  
    // ͼƬ����  
    private String PicUrl;  
  
    public String getPicUrl() {  
        return PicUrl;  
    }  
  
    public void setPicUrl(String picUrl) {  
        PicUrl = picUrl;  
    }  
}  
