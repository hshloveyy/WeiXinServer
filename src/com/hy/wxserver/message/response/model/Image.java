package com.hy.wxserver.message.response.model;
/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-3-30 下午01:59:52
 * 图片
 */
public class Image {
	//媒体ID
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
