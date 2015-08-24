/*
 * @(#)CommonDataAuth.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-9-7
 *  描　述：创建
 */

package com.infolion.xdss3;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.domain.Property;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.dpframework.util.AssertUtil;

/**
 * <pre>
 * 信达3期公用数据权限处理
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
public class CommonDataAuth
{
	private static Log log = LogFactory.getFactory().getLog(CommonDataAuth.class);

	/**
	 * 取得数据权限SQL语句
	 * 
	 * @param businessObject
	 * @return
	 */
	public static String getDataAuthSql(BusinessObject businessObject)
	{
		String strWhereSql = "";
		StringBuffer sb = new StringBuffer();
		AssertUtil.notNull(businessObject, "业务对象");
		UserContext userContext = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext();
		AssertUtil.notNull(userContext, "用户上下文");
		String dept_id = userContext.getSysUser().getDeptId();
		AssertUtil.notNullBlank(dept_id, "部门ID");
		Set<Property> properties = businessObject.getProperties();
		boolean isHaveDeptId = false;
		Property deptProperty = null;
		for (Property property : properties)
		{
			if ("dept_id".equalsIgnoreCase(property.getPropertyName()) || 
				"DEPT_ID".equalsIgnoreCase(property.getColumnName()) || 
				"deptid".equalsIgnoreCase(property.getPropertyName()) ||
				"DEPTID".equalsIgnoreCase(property.getColumnName())
				)
			{
				deptProperty = property;
				isHaveDeptId = true;
				break;
			}
		}

		if (isHaveDeptId)
		{
			sb.append(" and " + businessObject.getTableName().toUpperCase() + "." + deptProperty.getColumnName());
			// 是职能部门
			if ("1".equals(userContext.getSysDept().getIsFuncDept()))
			{
				sb.append(" in(" + userContext.getGrantedDepartmentsId() + ")");
			}
			else
			{
				sb.append(" = '" + dept_id + "'");
			}
			strWhereSql = sb.toString();
		}
		else
		{
			strWhereSql = "";
		}

		log.debug("取得数据权限SQL语句:" + strWhereSql);
		return strWhereSql;
	}
}
