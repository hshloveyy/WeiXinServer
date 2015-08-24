/*
 * @(#)ScrappedProcessSuccessActionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-17
 *  描　述：创建
 */

package com.infolion.sapss.workflow.storageProtocol;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.component.workflow.WorkflowService;
import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.storageProtocol.dao.StorageProtocolJdbcDao;
import com.infolion.sapss.storageProtocol.domain.TStorageProtocol;

public class StorageProtocolSuccessActionHandler implements ActionHandler {

	public void execute(ExecutionContext txt) throws Exception {
		Object obj = txt.getVariable("INFO_ID");
		String infoId ="";
		if(obj!=null){
			infoId= (String)obj;
			StorageProtocolJdbcDao dao = (StorageProtocolJdbcDao)EasyApplicationContextUtils.getBeanByType(StorageProtocolJdbcDao.class);
			TStorageProtocol ts = new TStorageProtocol();
			ts.setInfoId(infoId);
			long processId = txt.getProcessInstance().getId();
			WorkflowService.updateProcessInstanceEndState(processId, "仓储协议审批通过");
			dao.updateState(ts, "3",true);
		}
		txt.getProcessInstance().signal();
	}

}
