package com.hy.wxserver.message.request;

/** 
 * ��Ƶ��Ϣ 
 *  
 * @author heshaohua 
 * @date 2014-3-28 
 */  
public class VideoMessage extends ReqBaseMessage {  
    // ý��ID  
    private String MediaId;  
    // ������ʽ  
    private String Format;  
  
    public String getMediaId() {  
        return MediaId;  
    }  
  
    public void setMediaId(String mediaId) {  
        MediaId = mediaId;  
    }  
  
    public String getFormat() {  
        return Format;  
    }  
  
    public void setFormat(String format) {  
        Format = format;  
    }  
}