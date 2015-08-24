/*
 * @(#)TestTableService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：傅渊源
 *  时　间：2009-1-15
 *  描　述：创建
 */

package com.infolion.platform.console.workflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.workflow.dao.TestTableJdbcDao;
import com.infolion.platform.core.service.BaseService;
@Service
public class TestTableService extends BaseService {
	@Autowired
	private TestTableJdbcDao testTableJdbcDao;

	public void setTestTableJdbcDao(TestTableJdbcDao testTableJdbcDao) {
		this.testTableJdbcDao = testTableJdbcDao;
	}
	
	public List queryTestTable(){
		return testTableJdbcDao.queryTestTable();
	}
}
