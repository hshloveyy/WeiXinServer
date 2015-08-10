package com.hy.wxserver.message.response.model;
/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-3-30 下午02:04:54
 * 视频model
 */
public class Video {

	//媒体ID
	private String mediaId;
	//视频标题
	private String title;
	//视频描述
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
