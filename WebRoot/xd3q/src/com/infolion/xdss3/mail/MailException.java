package com.infolion.xdss3.mail;

import java.util.List;
import com.infolion.platform.dpframework.core.BusinessException;

public class MailException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6481511247292057487L;

	/**
	 * 构造模板邮件失败，未配置指定名称(&1)的邮件模板
	 */
	public static final String CreateMailFail_MailTempNotExist = "PDM0001W";

	/**
	 * 读取邮件配置文件失败,配置文件路径:&1
	 */
	public static final String FailToLoadMailConfigFile = "PDM0002W";

	/**
	 * 邮件发送失败
	 */
	public static final String FailToSendMail = "PDM0003W";

	/**
	 * 服务器验证失败，请确保输入的用户名或密码正确!
	 */
	public static final String MailAuthenticationException = "PDM0004W";

	/**
	 * 连接邮件服务器失败!
	 */
	public static final String UnknownHostException = "PDM0005W";

	/**
	 * 邮件验证失败:&1不能为空！
	 */
	public static final String ValidateNotBlank = "PDM0006W";

	/**
	 * 邮件验证失败:至少需要指定一个收件人地址！
	 */
	public static final String ValidateAtLeastOneToAddress = "PDM0007W";;

	public MailException(String msgClass, String messageNo,
			List<String> messageParameters)
	{
		super(msgClass, messageNo, messageParameters);
	}

	public MailException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public MailException(String message)
	{
		super(message);
	}

	public MailException(Throwable cause)
	{
		super(cause);
	}

	public MailException(String msgClass, String messageNo,
			Object... messageParameters)
	{
		super(msgClass, messageNo, messageParameters);
	}

	public MailException(Throwable cause, String msgClass, String messageNo,
			Object... messageParameters)
	{
		super(cause, msgClass, messageNo, messageParameters);
	}
}
