package com.hy.wxserver.message.response;

import com.hy.wxserver.message.response.model.Video;

/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version ����ʱ�䣺2014-3-30 ����01:53:34
 * ��˵��
 */
public class VideoMessage extends RespBaseMessage {

	private Video video;
	
	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public String parseXML() {
		String xml = "<xml><ToUserName><![CDATA["
			+ ToUserName
			+ "]]></ToUserName><FromUserName><![CDATA["
			+ FromUserName
			+ "]]></FromUserName><CreateTime>"
			+ CreateTime
			+ "</CreateTime><MsgType><![CDATA[music]]></MsgType><Video><MediaId><![CDATA["
			+ video.getMediaId()
			+ "]]></MediaId><Title><![CDATA["
			+ video.getTitle()
			+ "]]></Title><Description><![CDATA["
			+ video.getDescription()
			+ "]]></Description></Video>";
		return xml;
	}

}
