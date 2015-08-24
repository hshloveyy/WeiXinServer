package com.infolion.xdss3.mail;

import java.util.Map;
import java.util.Properties;
public class MailSenderInfo {

	/**
	 * 邮件服务器地址
	 */
	private String host;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 发件人
	 */
	private String from;

	/**
	 * 收件人列表
	 */
	private String[] to;

	/**
	 * 抄送人列表
	 */
	private String[] cc;

	/**
	 * 主题
	 */
	private String subject;

	/**
	 * 内容
	 */
	private String text;

	/**
	 * 内容是否使用html格式
	 */
	private boolean useHtml;

	/**
	 * 服务器是否需要身份验证
	 */
	private boolean auth = true;

	/**
	 * 内部元素key为元素id，value为元素内容,
	 * value的有效类型为：javax.activation.DataSource,java.io.File
	 */
	private Map<String, Object> inlineElements;

	/**
	 * 附件,key为文件名，value为文件内容, 有效类型为：javax.activation.DataSource,java.io.File
	 */
	private Map<String, Object> attachments;

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String[] getTo()
	{
		return to;
	}

	public void setTo(String[] to)
	{
		this.to = to;
	}

	public String[] getCc()
	{
		return cc;
	}

	public void setCc(String[] cc)
	{
		this.cc = cc;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public boolean isUseHtml()
	{
		return useHtml;
	}

	public void setUseHtml(boolean useHtml)
	{
		this.useHtml = useHtml;
	}

	public boolean isAuth()
	{
		return auth;
	}

	public void setAuth(boolean auth)
	{
		this.auth = auth;
	}

	public Map<String, Object> getInlineElements()
	{
		return inlineElements;
	}

	public void setInlineElements(Map<String, Object> inlineElements)
	{
		this.inlineElements = inlineElements;
	}

	public Map<String, Object> getAttachments()
	{
		return attachments;
	}

	public void setAttachments(Map<String, Object> attachments)
	{
		this.attachments = attachments;
	}

}
