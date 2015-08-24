package com.infolion.xdss3.collect.workflow;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.collectbankitem.domain.CollectBankItem;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collect.service.CollectService;

public class CollectTypeJudge implements DecisionHandler {

	public String decide(ExecutionContext context) throws Exception {
		// TODO Auto-generated method stub
		String businessId = (String)context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
		CollectService collectService = (CollectService)EasyApplicationContextUtils.getBeanByName("collectService");
		Collect collect = collectService._get(businessId);
		
		String strRet = "本币";	// 流程路径名称
		
		if (!StringUtils.isNullBlank(collect.getUnnamercollectid())){
			strRet = "未名户认领";
		}
		
		if (!StringUtils.isNullBlank(collect.getOldcollectitemid())){
			strRet = "保证金转货款";
			return strRet;
		}
		
		String currency = collect.getCurrency();
		
		if(strRet.equals("本币") && !currency.equals("CNY"))
		{
			strRet = "外币";
		}
		
		/**
		 * 判断重分配方式
		 */
		String collectType = collect.getCollecttype();
		
		/**
		 * 不需要流程的情况:
		 * 1: 重置（财务部直接解除分配关系）
		 * 2:冲销（财务部冲销并作废）
		 */
		if(currency.equals("CNY")&& (collectType.equals("11") || collectType.equals("12")) )
		{
			strRet = "票据";
		}

		Set<CollectBankItem> collectbankitems = collect.getCollectbankitem();
		if (null != collectbankitems)
		{
			Iterator<CollectBankItem> itCollectBankItem = collectbankitems.iterator();
			while (itCollectBankItem.hasNext())
			{
				CollectBankItem collectItem = (CollectBankItem) itCollectBankItem.next();
				if(collectItem.getCollectbankacc().equals("310066399018010033466")){
					strRet = "本币上海账户";
					break;
				}
			}
		}
		
		return strRet;
	}
}
