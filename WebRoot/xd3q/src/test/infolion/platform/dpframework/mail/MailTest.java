/*
 * @(#)MailTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：刘俊杰
 *  时　间：2009-12-1
 *  描　述：创建
 */

package test.infolion.platform.dpframework.mail;

import java.io.File;
import java.util.HashMap;

import com.infolion.platform.dpframework.mail.MailContent;
import com.infolion.platform.dpframework.mail.MailService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;

import test.infolion.platform.core.service.ServiceTest;

public class MailTest extends ServiceTest
{

	String host = "mail.ffcs.cn";
	// 发件人
	String from = "";
	// 收件人
	String[] to = new String[] {  };
	// 用户名
	String username = "";
	// 密码
	String password = "";
	String subject = "邮件测试";

	@Override
	protected void flush()
	{
		// TODO Auto-generated method stub

	}

	public void testSimpleSendMail()
	{
		MailService mailService = (MailService) EasyApplicationContextUtils
				.getBeanByName("mailService");
		String text = "hello there!";
		mailService.simpleSendMail(host, username, password, from, to, null,
				subject, text);
		System.out.println("邮件发送成功");
	}

	public void testSendMail()
	{
		MailContent mail = new MailContent();
		mail.setHost(host);
		mail.setFrom(from);
		mail.setTo(to);
		mail.setCc(new String[] { "liujj@ffcs.cn" });
		mail.setUsername(username);
		mail.setPassword(password);
		mail.setSubject(subject);
		mail.setUseHtml(true);
		mail.setText("hello Jay:<br>" + "  <b>This is Text</b><br>"
				+ "  This is a picture:<img src=\"cid:a\" /><br>"
				+ "  <a href=\"http://www.baidu.com\">This is a link</a>");
		// 加图片元素
		File file = new File("D:\\My Documents\\My Pictures\\等级\\level3.GIF");
		HashMap<String, Object> inLineMap = new HashMap<String, Object>();
		inLineMap.put("a", file);
		mail.setInlineElements(inLineMap);

		// 加附件
		HashMap<String, Object> attachmentMap = new HashMap<String, Object>();
		File file2 = new File("D:\\My Documents\\My Pictures\\等级\\level16.GIF");
		attachmentMap.put("sun.gif", file2);
		mail.setAttachments(attachmentMap);

		MailService mailService = (MailService) EasyApplicationContextUtils
				.getBeanByName("mailService");
		mailService.sendMail(mail);
		System.out.println("邮件发送成功");

	}

}
