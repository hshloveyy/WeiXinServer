/*
 * @(#)ParticularProcessFailureActionHandler.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：陈喜平
 *  时　间：2009-6-16
 *  描　述：创建
 */

package com.infolion.sapss.workflow.particular.handler;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.util.EasyApplicationContextUtils;
import com.infolion.sapss.contract.service.ContractService;
import com.infolion.sapss.credit.dao.CreditEntryJdbcDao;
import com.infolion.sapss.payment.service.PaymentImportsInfoJdbcService;
import com.infolion.sapss.payment.service.PaymentInnerInfoJDBCService;
import com.infolion.sapss.receipts.dao.ReceiptsJdbcDao;
import com.infolion.sapss.ship.dao.ShipInfoJdbcDao;
import com.infolion.sapss.workflow.particular.domain.TParticularWorkflow;
import com.infolion.sapss.workflow.particular.service.ParticularWorkflowService;

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
public class ParticularProcessFailureActionHandler implements ActionHandler {
	private static final long serialVersionUID = 7670968334841962808L;

	public void execute(ExecutionContext ctxt) throws Exception {
		String particularId = (String)ctxt.getVariable("PARTICULAR_ID");
		ParticularWorkflowService service =(ParticularWorkflowService) EasyApplicationContextUtils.getBeanByType(ParticularWorkflowService.class);
		TParticularWorkflow info = service.load(particularId);
		String module = info.getModuleName();
		if(module!=null && !"".equals(module)){
			if(module.indexOf("销售合同")!=-1){
				ContractService cs = (ContractService)EasyApplicationContextUtils.getBeanByType(ContractService.class);
				cs.updateSaleOrderState(info.getOriginalBizId(), "12");
			}else if(module.indexOf("采购合同")!=-1){
				ContractService cs = (ContractService)EasyApplicationContextUtils.getBeanByType(ContractService.class);
				cs.updatePurchaserOrderState(info.getOriginalBizId(), "12");
			}else if(module.indexOf("进口货物付款")!=-1){
				PaymentImportsInfoJdbcService cs = (PaymentImportsInfoJdbcService)EasyApplicationContextUtils.getBeanByType(PaymentImportsInfoJdbcService.class);
				cs.updateStatus(info.getOriginalBizId(), "9");
			}else if(module.indexOf("内贸付款")!=-1){
				PaymentInnerInfoJDBCService cs = (PaymentInnerInfoJDBCService)EasyApplicationContextUtils.getBeanByType(PaymentInnerInfoJDBCService.class);
				cs.updateStatus(info.getOriginalBizId(), "9");
			}else if(module.indexOf("入仓")!=-1){
				ReceiptsJdbcDao cs = (ReceiptsJdbcDao)EasyApplicationContextUtils.getBeanByType(ReceiptsJdbcDao.class);
				cs.updateReceipts(info.getOriginalBizId(),"examine_state", "9");
			}else if(module.indexOf("出仓")!=-1){
				//ShipInfoJdbcDao cs = (ShipInfoJdbcDao)EasyApplicationContextUtils.getBeanByType(ShipInfoJdbcDao.class);
				//cs.updateReceipts(info.getOriginalBizId(),"examine_state", "9");
			}else if(module.indexOf("信用证开证")!=-1){
				CreditEntryJdbcDao cs = (CreditEntryJdbcDao)EasyApplicationContextUtils.getBeanByType(CreditEntryJdbcDao.class);
				cs.updateState(info.getOriginalBizId(), "14");
			}
		}
		ctxt.getProcessInstance().signal();
	}

}
