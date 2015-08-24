/*
 * @(#)TaskHistoryTableSql.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-10-13
 *  描　述：创建
 */

package com.infolion.platform.workflow.engine.web;

import org.springframework.stereotype.Repository;
import com.infolion.platform.dpframework.uicomponent.grid.data.TableSql;

/**
 * <pre>
 * 我的授权-方法，Grid数据源SQL语句
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
@Repository
public class TaskHistoryTableSql implements TableSql
{

	public String getTableSql()
	{
		String sql = "(select DISTINCT T.TASKINSID,T.TASKID,T.PROCESSID,T.BUSINESSID,T.TASKNAME,T.TASKDESCRIPTION,T.TASKCREATETIME,T.TASKENDTIME," +
		"T.EXAMINE,T.EXAMINEPERSON,T.EXAMINERESULT,T.WITHTIME,T.PARENTBUSINESSID,T.PARENTPROCESSID,U.REAL_NAME" +
		" from (select * from WF_TASKINS union select * From WF_TASKINS_O ) T LEFT OUTER JOIN T_SYS_USER U ON T.EXAMINEPERSON = U.USER_NAME)";
		
		//System.out.println("我的授权-方法：Grid数据源SQL语句:" + sql);
		return sql;
	}

}
