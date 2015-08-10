package com.hy.wxserver.message.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.hy.wxserver.message.response.model.Image;

/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version 创建时间：2014-3-30 下午01:54:16
 * 图片消息
 */
@XmlRootElement(name = "xml")
public class ImageMessage extends RespBaseMessage {

	private Image image;
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String parseXML() {
		String xml = "<xml><ToUserName><![CDATA["
			+ ToUserName
			+ "]]></ToUserName><FromUserName><![CDATA["
			+ FromUserName
			+ "]]></FromUserName><CreateTime>"
			+ CreateTime
			+ "</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA["
			+ image.getMediaId()
			+ "]]></MediaId></Image></xml>";
		return xml;
	}

}
