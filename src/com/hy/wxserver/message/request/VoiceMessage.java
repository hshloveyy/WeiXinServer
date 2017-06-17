package com.hy.wxserver.message.request;

/** 
 * 语音消息 
 *  
 * @author heshaohua 
 * @date 2014-3-28 
 */  
public class VoiceMessage extends ReqBaseMessage {  
    // 媒体ID  
    private String MediaId;  
    // 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据  
    private String ThumbMediaId;  
    //识别结果
    private String Recognition;
    //音频格式
    private String Format;
  
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

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}  
  
}