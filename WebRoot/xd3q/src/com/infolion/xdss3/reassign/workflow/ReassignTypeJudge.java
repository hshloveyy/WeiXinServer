package com.infolion.xdss3.reassign.workflow;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.collect.domain.Collect;
import com.infolion.xdss3.collect.service.CollectService;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;

public class ReassignTypeJudge implements DecisionHandler{

    ReassignService reassignService;

    public String decide(ExecutionContext context) throws Exception{
        // TODO Auto-generated method stub
        String businessId = (String) context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
        reassignService = (ReassignService) EasyApplicationContextUtils.getBeanByName("reassignService");
        Reassign reassign = reassignService.getReassignById(businessId);

        String strRet = ""; // 流程路径名称

        // /**
        // * 判断财务人员是否已经手动清帐凭证
        // * 若没有，流程直接结束
        // */
        // String vouchno =
        // reassignService.isVoucherReseted(reassign.getReassigntype(),
        // reassign.getReassigntmethod(),
        // reassign.getReassignboid());
        //		
        // /**
        // * 已手工清帐
        // */
        // if( vouchno != null )
        // {
        // strRet = "未手工清帐流程结束";
        //			
        // return strRet;
        // }

        /**
         * 判断重分配方式
         */
        String reassignMethod = reassign.getReassigntmethod();

        /**
         * 不需要流程的情况: 1: 重置（财务部直接解除分配关系） 2:冲销（财务部冲销并作废）
         */
        if(reassignMethod.equals(ReassignConstant.RESET_TO_FI) || reassignMethod.equals(ReassignConstant.FI_CLEAR)){
            if(reassignMethod.equals(ReassignConstant.FI_CLEAR)
                    && (reassign.getReassigntype().equals(ReassignConstant.COLLECT) || 
                            reassign.getReassigntype().equals(ReassignConstant.PAYMENT) || 
                            reassign.getReassigntype().equals(ReassignConstant.CUSTOMERREFUNDMENT) || 
                            reassign.getReassigntype().equals(ReassignConstant.SUPPLIERREFUNDMENT))){
                strRet = "冲销（财务部冲销并作废）";
            }else{
                // 若为"客户单清"或"供应商单清"，直接走"财务直接结束"（因为没有资金变动）
                strRet = "财务直接结束";
            }
            return strRet;
        }

        /**
         * 判断重分配类型
         */
        if(reassign.getReassigntype().equals(ReassignConstant.COLLECT)){
            strRet = "重分配收款单";
        }else if(reassign.getReassigntype().equals(ReassignConstant.PAYMENT)){
            String tradeType = reassignService.getPaymentTradeType(reassign.getReassignboid());
            if(tradeType.equals("01")){
                strRet = "重分配内贸付款单";
            }else{
                strRet = "重分配进口付款单";
            }
        }else if(reassign.getReassigntype().equals(ReassignConstant.BILLCLEARCOLLECT)){
            strRet = "重分配票清收款单";
        }else if(reassign.getReassigntype().equals(ReassignConstant.BILLCLEARPAYMENT)){
            strRet = "重分配票清付款单";
        }else if(reassign.getReassigntype().equals(ReassignConstant.CUSTOMERREFUNDMENT)){
            strRet = "重分配客户退款单";
        }else if(reassign.getReassigntype().equals(ReassignConstant.SUPPLIERREFUNDMENT)){
            strRet = "重分配供应商退款单";
        }
//        else if(reassign.getReassigntype().equals(ReassignConstant.CUSTOMERSINGLECLEAR)){
//            strRet = "重分配供应商退款单";
//        }else if(reassign.getReassigntype().equals(ReassignConstant.SUPPLIERSINGLECLEAR)){
//            strRet = "重分配供应商退款单";
//        }
        

        return strRet;
    }

}
