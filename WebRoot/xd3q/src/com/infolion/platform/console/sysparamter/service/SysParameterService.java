/*
 * @(#)SysParameterService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：张崇镇
 *  时　间：2008-12-1
 *  描　述：创建
 */

package com.infolion.platform.console.sysparamter.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.console.sysparamter.dao.SysParameterDao;
import com.infolion.platform.console.sysparamter.domain.SysParameter;
import com.infolion.platform.core.service.BaseJdbcService;

/**
 * 
 * <pre>
 * 系统参数服务
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 张崇镇
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
@Service
public class SysParameterService extends BaseJdbcService {
	@Autowired
	private SysParameterDao sysParameterDao;

	public void setSysParameterDao(SysParameterDao sysParameterDao) {
		this.sysParameterDao = sysParameterDao;
	}

	public Map<String, SysParameter> getAllSysParameters() {
		return null;
	}
}
