package com.hy.wxserver.message.request;

/** 
 * ��Ƶ��Ϣ 
 *  
 * @author heshaohua 
 * @date 2014-3-28 
 */  
public class VoiceMessage extends ReqBaseMessage {  
    // ý��ID  
    private String MediaId;  
    // ��Ƶ��Ϣ����ͼ��ý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ����  
    private String ThumbMediaId;  
  
    public String getMediaId() {  
        return MediaId;  
    }  
  
    public void setMediaId(String mediaId) {  
        MediaId = mediaId;  
    }

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}  
  
}