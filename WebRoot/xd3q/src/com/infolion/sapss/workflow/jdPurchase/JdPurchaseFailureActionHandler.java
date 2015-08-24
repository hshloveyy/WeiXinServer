/*
 * @(#)JdPurchaseFailureActionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-26
 *  描　述：创建
 */

package com.infolion.sapss.workflow.jdPurchase;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.jdPurchase.dao.JdPurchaseJdbcDao;
import com.infolion.sapss.jdPurchase.domain.TJindaPurchase;

public class JdPurchaseFailureActionHandler  implements ActionHandler{
	public void execute(ExecutionContext txt) throws Exception {
		String purchaseId =(String)txt.getVariable("PURCHASE_ID");
		TJindaPurchase t = new TJindaPurchase();
		t.setPurchaseId(purchaseId);
		JdPurchaseJdbcDao dao = (JdPurchaseJdbcDao)EasyApplicationContextUtils.getBeanByType(JdPurchaseJdbcDao.class);
		dao.updateState(t, "4",true);
		txt.getProcessInstance().signal();
	}

}
