package com.hy.wxserver.message.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.hy.wxserver.message.response.model.Image;

/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version ����ʱ�䣺2014-3-30 ����01:54:16
 * ͼƬ��Ϣ
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
