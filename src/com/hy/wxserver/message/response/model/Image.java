package com.hy.wxserver.message.response.model;
/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version ����ʱ�䣺2014-3-30 ����01:59:52
 * ͼƬ
 */
public class Image {
	//ý��ID
	private String mediaId;

	public Image(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
}
