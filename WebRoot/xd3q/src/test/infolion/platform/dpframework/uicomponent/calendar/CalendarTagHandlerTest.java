/*
 * @(#)CalendarTagHandlerTest.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-5-14
 *  描　述：创建
 */

package test.infolion.platform.dpframework.uicomponent.calendar;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.infolion.platform.core.service.JdbcServiceTest;

import com.infolion.platform.dpframework.uicomponent.calendar.domain.Calendar;
import com.infolion.platform.dpframework.uicomponent.calendar.web.CalendarTagHandler;

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
public class CalendarTagHandlerTest extends JdbcServiceTest
{
	//private final static Log log = LogFactory.getLog("CalendarTagHandlerTest");
	private Log log = LogFactory.getLog(this.getClass());
	
	public void testGetExtCode() throws SQLException
	{
		boolean isReadOnly = false;
		boolean isDisable = false;
		String strFormat = "";
		String strMinValue = "";
		String strMinText = "";
		String strMaxValue = "";
		String strMaxText = "";
		boolean isMoreThanToday = false;
		boolean isLessThanToday = false;
		boolean isShowTime = true;
		String fieldName = "bbbb";
		String strWidth = "180";
		String strApplyTo = "dddd";
		String strDivId = "";
		String strExtendField = "allowBlank:false,emptyText:'请选择日期!',blankText:'3',";
		
		String strDefaultValue  ="";
		Calendar calendar = new Calendar(strFormat,fieldName,strMinValue, strMinText,
				strMaxValue,  strMaxText,  isMoreThanToday,
				isLessThanToday,isShowTime,strWidth,strDefaultValue,
                 isReadOnly, isDisable,strApplyTo,strDivId,strExtendField);
		
		 CalendarTagHandler calendarTagHandler = new CalendarTagHandler(calendar);
		    //取得Ext对像生成代码
		    String strExtCode = calendarTagHandler.getExtCode();
		    
		  log.debug("取得Ext对像生成代码:" + strExtCode);
		  System.out.println("取得Ext对像生成代码:" + strExtCode);
		  
	}

}
