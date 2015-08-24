package com.infolion.xdss3.mail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.infolion.platform.dpframework.language.LangConstants;
import com.infolion.platform.dpframework.language.LanguageService;
import com.infolion.platform.dpframework.uicomponent.systemMessage.SysMsgConstants;
import com.infolion.platform.dpframework.util.StringUtils;

public class SimpleMailSender {
	
	public static final String HOST = "host";
	public static final String USER_NAME = "username";
	public static final String PASSWORD = "password";
	public static final String USE_AUTH = "useanth";
	public static final String FROM_ADDRESS = "fromAddress";
	public static final String TEMP_NAMES = "tempNames";
	public static final String SUBJECT = "subject";
	public static final String MESSAGE = "message";
	public static final String PARAMS = "params";

	private static Properties mailConfigs;
	private static String propertiesPath = "config/mail.properties";

	private static List<String> mailTempNames;

	private static Properties getMailConfigs()
	{
		if (mailConfigs == null)
		{
			try
			{
				mailConfigs = PropertiesLoaderUtils.loadAllProperties(propertiesPath);
				mailTempNames = new ArrayList<String>();
				String mailTemps = mailConfigs.getProperty(TEMP_NAMES);
				for (String tn : mailTemps.split(","))
				{
					tn = tn.trim();
					mailTempNames.add(tn);
				}
			}
			catch (IOException e)
			{
				// throw new MailException("读取邮件配置文件失败,配置文件路径:" +
				// propertiesPath);
				throw new MailException(SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.FailToLoadConfigFile, LanguageService.getText(LangConstants.MAIL_MAIL), propertiesPath);
			}
		}
		return mailConfigs;
	}
	
	public static String getProperty(String key)
	{
		String str = getMailConfigs().getProperty(key);
		if (str != null)
			try
			{
				str = new String(str.getBytes("ISO8859-1"), "UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{
				throw new MailException(e, SysMsgConstants.MSG_CLASS_DP, SysMsgConstants.UnsupportedEncodingException);
			}
		return str;
	}
	
	/**
	 * 发送指定的邮件
	 * 
	 * @param mail
	 */
	public void sendMail(MailSenderInfo mail)
	{
		validate(mail);
		try
		{
			// 配置邮件发送方信息
			JavaMailSenderImpl javaMail = new JavaMailSenderImpl();
			javaMail.setHost(mail.getHost());
			javaMail.setPort(25);
			javaMail.setPassword(mail.getPassword());
			javaMail.setUsername(mail.getUsername());
			Properties prop = new Properties();
			prop.setProperty("mail.smtp.auth", mail.isAuth() ? "true" : "false");
			javaMail.setJavaMailProperties(prop);
			MimeMessage message = javaMail.createMimeMessage();
			MimeMessageHelper messageHelp = new MimeMessageHelper(message, true, "GBK");
			messageHelp.setFrom(mail.getFrom());
			messageHelp.setTo(mail.getTo());
			if (mail.getCc() != null)
				messageHelp.setCc(mail.getCc());
			messageHelp.setSubject(mail.getSubject());
			messageHelp.setText(mail.getText(), mail.isUseHtml());
			if (mail.getInlineElements() != null)
			{
				for (Map.Entry<String, Object> ent : mail.getInlineElements().entrySet())
				{
					if (ent.getValue() instanceof DataSource)
					{
						messageHelp.addInline(ent.getKey(), (DataSource) ent.getValue());
					}
					else if (ent.getValue() instanceof File)
					{
						messageHelp.addInline(ent.getKey(), (File) ent.getValue());
					}
				}
			}
			if (mail.getAttachments() != null)
			{
				for (Map.Entry<String, Object> ent : mail.getAttachments().entrySet())
				{
					if (ent.getValue() instanceof DataSource)
					{
						messageHelp.addAttachment(ent.getKey(), (DataSource) ent.getValue());
					}
					else if (ent.getValue() instanceof File)
					{
						messageHelp.addAttachment(ent.getKey(), (File) ent.getValue());
					}
				}
			}
			javaMail.send(message);
		}
		catch (MessagingException e)
		{
			throw new MailException(e, SysMsgConstants.MSG_CLASS_DP, MailException.FailToSendMail);
		}
		catch (org.springframework.mail.MailException e)
		{
			if (e instanceof org.springframework.mail.MailAuthenticationException)
			{
				throw new MailException(SysMsgConstants.MSG_CLASS_DP, MailException.MailAuthenticationException);
			}
			else if (e.getRootCause() instanceof java.net.UnknownHostException)
			{
				throw new MailException(SysMsgConstants.MSG_CLASS_DP, MailException.UnknownHostException);
			}
			else
			{
				e.printStackTrace();
				throw new MailException(e, SysMsgConstants.MSG_CLASS_DP, MailException.FailToSendMail);
			}
		}
	}
	
	/**
	 * 验证mail的内容是否有效
	 * 
	 * @param mail
	 */
	private void validate(MailSenderInfo mail)
	{
		if (StringUtils.isNullBlank(mail.getHost()))
		{
			throw new MailException(SysMsgConstants.MSG_CLASS_DP, MailException.ValidateNotBlank, LanguageService.getText(LangConstants.MAIL_HOST_NAME));
		}
		if (StringUtils.isNullBlank(mail.getFrom()))
		{
			// throw new MailException("邮件验证失败:发件人不能为空！");
			throw new MailException(SysMsgConstants.MSG_CLASS_DP, MailException.ValidateNotBlank, LanguageService.getText(LangConstants.MAIL_FROM));
		}
		if (StringUtils.isNullBlank(mail.getUsername()))
		{
			// throw new MailException("邮件验证失败:用户名不能为空！");
			throw new MailException(SysMsgConstants.MSG_CLASS_DP, MailException.ValidateNotBlank, LanguageService.getText(LangConstants.MAIL_USER_NAME));
		}
		if (StringUtils.isNullBlank(mail.getPassword()))
		{
			// throw new MailException("邮件验证失败:密码不能为空！");
			throw new MailException(SysMsgConstants.MSG_CLASS_DP, MailException.ValidateNotBlank, LanguageService.getText(LangConstants.MAIL_PASSWORD));
		}
		if (StringUtils.isNullBlank(mail.getSubject()))
		{
			// throw new MailException("邮件验证失败:邮件主题不能为空！");
			throw new MailException(SysMsgConstants.MSG_CLASS_DP, MailException.ValidateNotBlank, LanguageService.getText(LangConstants.MAIL_SUBJECT));
		}
		if (mail.getText() == null)
		{
			// throw new MailException("邮件验证失败:邮件内容不能为null！");
			throw new MailException(SysMsgConstants.MSG_CLASS_DP, MailException.ValidateNotBlank, LanguageService.getText(LangConstants.MAIL_CONTENT));
		}
		if (mail.getTo() == null || mail.getTo().length == 0)
		{
			// throw new MailException("邮件验证失败:至少需要指定一个收件人地址！");
			throw new MailException(SysMsgConstants.MSG_CLASS_DP, MailException.ValidateAtLeastOneToAddress);
		}
	}
}
