package com.hy.wxserver.message.response.model;
/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version ����ʱ�䣺2014-3-30 ����02:04:54
 * ��Ƶmodel
 */
public class Video {

	//ý��ID
	private String mediaId;
	//��Ƶ����
	private String title;
	//��Ƶ����
	private String description;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
