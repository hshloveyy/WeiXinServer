package com.hy.wxserver.message.request;

/** 
 * 音频消息 
 *  
 * @author heshaohua 
 * @date 2014-3-28 
 */  
public class VideoMessage extends ReqBaseMessage {  
    // 媒体ID  
    private String MediaId;  
    // 语音格式  
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