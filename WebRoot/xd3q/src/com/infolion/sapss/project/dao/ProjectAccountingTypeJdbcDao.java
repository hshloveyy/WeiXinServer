package com.infolion.sapss.project.dao;

import org.springframework.stereotype.Repository;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.dao.BaseDao;
import com.infolion.platform.util.CodeGenerator;
import com.infolion.platform.util.DateUtils;
import com.infolion.sapss.project.domain.TProjectAccountingType;
/**
 * 
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 陈喜平
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Repository
public class ProjectAccountingTypeJdbcDao extends BaseDao{
	/**
	 * 增加项目核算类型记录
	 * @param pa
	 */
	public void addProjectAccountingType(TProjectAccountingType pat) {
		String strSql = "insert into T_PROJECT_ACCOUNTING_TYPE "+
						"(ACCOUNTING_ITEM_ID, " +
						"ACCOUNTING_ITEM, " +
						"CMD, " +
						"IS_AVAILABLE, " +
						"creator_time, " +
						"creator) " +
						"values " +
						"(?,?,?,?,?,?)";

		UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();

		Object[] insertparams = new Object[] {
				CodeGenerator.getUUID(),
				pat.getAccountingItem(),
				pat.getCmd(),
				pat.getIsAvailable(),
				DateUtils.getCurrTime(DateUtils.DB_STORE_DATE),
				userContext.getSysUser().getUserName() };
		getJdbcTemplate().update(strSql, insertparams);
	}
}
