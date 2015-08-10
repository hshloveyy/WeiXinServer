package com.hy.wxserver.message.response;

import java.util.List;

import com.hy.wxserver.message.response.model.Article;

/**
 * �ı���Ϣ
 * 
 * @author heshaohua
 * @date 2014-3-28
 */
public class NewsMessage extends RespBaseMessage {
	// ͼ����Ϣ����������Ϊ10������
	private int ArticleCount;
	// ����ͼ����Ϣ��Ϣ��Ĭ�ϵ�һ��itemΪ��ͼ
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

	public String parseXML() {
		StringBuffer xml = new StringBuffer(
				"<xml><ToUserName><![CDATA["
						+ ToUserName
						+ "]]></ToUserName><FromUserName><![CDATA["
						+ FromUserName
						+ "]]></FromUserName><CreateTime>"
						+ CreateTime
						+ "</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>"
						+ Articles.size() + "</ArticleCount><Articles>");
		for (Article item : Articles) {
			xml.append("<item><Title><![CDATA[");
			xml.append(item.getTitle());
			xml.append("]]></Title><Description><![CDATA[");
			xml.append(item.getDescription());
			xml.append("]]></Description><PicUrl><![CDATA[");
			xml.append(item.getPicUrl());
			xml.append("]]></PicUrl><Url><![CDATA[");
			xml.append(item.getUrl());
			xml.append("]]></Url></item>");
		}
		xml.append("</Articles></xml>");

		return xml.toString();
	}

}
