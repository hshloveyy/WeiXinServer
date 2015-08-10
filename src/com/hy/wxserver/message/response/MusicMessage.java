package com.hy.wxserver.message.response;

import com.hy.wxserver.message.response.model.Music;

/** 
 * “Ù¿÷œ˚œ¢ 
 *  
 * @author heshaohua 
 * @date 2014-3-28 
 */  
public class MusicMessage extends RespBaseMessage {  
    // “Ù¿÷  
    private Music Music;  
  
    public Music getMusic() {  
        return Music;  
    }  
  
    public void setMusic(Music music) {  
        Music = music;  
    }

	public String parseXML() {
		String xml = "<xml><ToUserName><![CDATA["
			+ ToUserName
			+ "]]></ToUserName><FromUserName><![CDATA["
			+ FromUserName
			+ "]]></FromUserName><CreateTime>"
			+ CreateTime
			+ "</CreateTime><MsgType><![CDATA[music]]></MsgType><Music><Title><![CDATA["
			+ Music.getTitle()
			+ "]]></Title><Description><![CDATA["
			+ Music.getDescription()
			+ "]]></Description><MusicUrl><![CDATA["
			+ Music.getMusicUrl()
			+ "]]></MusicUrl><HQMusicUrl><![CDATA["
			+ Music.getHQMusicUrl()
			+ "]]></HQMusicUrl><ThumbMediaId><![CDATA["
			+ Music.getThumbMediaId()
			+ "]]></ThumbMediaId></Music></xml>";
		return xml;
	}

}