/*
 * @(#)ScrappedProcessSuccessActionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-17
 *  描　述：创建
 */

package com.infolion.sapss.workflow.scrapped;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.scrapped.dao.ScrappedJdbcDao;
import com.infolion.sapss.scrapped.domain.TScrapped;
import com.infolion.sapss.scrapped.service.ScrappedService;

public class ScrappedProcessSuccessActionHandler implements ActionHandler {

	public void execute(ExecutionContext txt) throws Exception {
		Object obj = txt.getVariable("SCRAPPED_ID");
		String scrappedId ="";
		if(obj!=null){
			scrappedId= (String)obj;
			ScrappedJdbcDao dao = (ScrappedJdbcDao)EasyApplicationContextUtils.getBeanByType(ScrappedJdbcDao.class);
			TScrapped ts = new TScrapped();
			ts.setScrappedId(scrappedId);
			dao.updateState(ts, "3",true);
		}
		txt.getProcessInstance().signal();
	}

}
