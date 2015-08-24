/*
 * @(#)ProjectJdbcService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2009-3-04
 *  描　述：创建
 */

package com.infolion.sapss.export.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jbpm.JbpmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.component.workflow.ext.TaskInstanceContext;
import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.core.BusinessException;
import com.infolion.platform.core.service.BaseJdbcService;
import com.infolion.sapss.common.ExcelObject;
import com.infolion.sapss.export.domain.TExportBillExam;
import com.infolion.sapss.export.domain.TExportBills;
import com.infolion.sapss.export.dao.ExportJdbcDao;

/**
 * 
 * <pre></pre>
 *
 * <br>
 * JDK版本:1.5
 *
 * @author 王懿璞
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class ExportJdbcService extends BaseJdbcService {
	@Autowired
	private ExportJdbcDao exportJdbcDao;

	/**
	 *  取得最新的ExportBillNo单据流水号
	 * @param map
	 * @return
	 */
	public String getExportBillNo()
	{
		return exportJdbcDao.getExportBillNo();
	}
	
	public void dealOutToExcel(String sql , ExcelObject excel){
		exportJdbcDao.dealOutToExcel(sql,excel);
	}
	
	public void assemExportBillExamPrint(TExportBillExam exam){
		exportJdbcDao.assemExportBillExamPrint(exam);
	}
}
