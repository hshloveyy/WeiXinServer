/*
 * @(#)ScrappedAmount10DecisionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-17
 *  描　述：创建
 */

package com.infolion.sapss.workflow.scrapped;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.scrapped.dao.ScrappedJdbcDao;
import com.infolion.sapss.scrapped.domain.TScrapped;

public class ScrappedAmount10DecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext txt) throws Exception {
		Object obj = txt.getVariable("SCRAPPED_ID");
		String scrappedId ="";
		if(obj!=null){
			scrappedId= (String)obj;
			ScrappedJdbcDao dao = (ScrappedJdbcDao)EasyApplicationContextUtils.getBeanByType(ScrappedJdbcDao.class);
			int sum = dao.sumMoney(scrappedId);
			if(sum>100000)//大于10万
				return "金额大于10万CNY";
			if(sum<=100000)//小于10万
				return "金额小于10万CNY";
		}
		return null;
	}

}
