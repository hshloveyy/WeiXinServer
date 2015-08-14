package com.hy.wxserver.message.response;

import com.hy.wxserver.message.response.model.Voice;

/**
 * @author heshaohua E-mail:386184368@qq.com
 * @version ����ʱ�䣺2014-3-30 ����01:53:58
 * ������Ϣ
 */
public class VoiceMessage extends RespBaseMessage {

	private Voice voice;
	
	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	public String parseXML() {
		String xml = "<xml><ToUserName><![CDATA["
			+ ToUserName
			+ "]]></ToUserName><FromUserName><![CDATA["
			+ FromUserName
			+ "]]></FromUserName><CreateTime>"
			+ CreateTime
			+ "</CreateTime><MsgType><![CDATA[music]]></MsgType><Voice><MediaId><![CDATA["
			+ voice.getMediaId()
			+ "]]></MediaId></Voice>";
		return xml;
	}

}
