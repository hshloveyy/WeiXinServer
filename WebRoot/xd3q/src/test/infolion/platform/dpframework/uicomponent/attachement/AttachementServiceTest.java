/*
 * @(#)CalendarTagHandlerTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-5-14
 *  描　述：创建
 */

package test.infolion.platform.dpframework.uicomponent.attachement;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.dpframework.uicomponent.attachement.service.AttachementService;

/**
 * 
 * <pre>
 *  CalendarTagHandler测试类
 * </pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class AttachementServiceTest extends JdbcServiceTest
{
	//private final static Log log = LogFactory.getLog("CalendarTagHandlerTest");
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private AttachementService attachementService ;
	
	public void setAttachementService(AttachementService attachementService)
	{
		this.attachementService = attachementService;
	}

//	public void testGetfileListSql() throws SQLException
//	{
//		String strSql = "";
//        String boId = "2";
//		strSql = attachementService.getfileListSql(boId);
// 
//		log.debug("取得业务对象附件列表数据的SQL:" + strSql);
//		System.out.println("取得业务对象附件列表数据的SQL:" + strSql);	
//		
//		String attachementId = "1550E1D8-18D5-4D16-8890-959E5B436B64";
//		
//       boolean isPass =  this.attachementService.deleteAttachement(attachementId);
//       System.out.println("删除业务对象:" + isPass);
//	}

}
