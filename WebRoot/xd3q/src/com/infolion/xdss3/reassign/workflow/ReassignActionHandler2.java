package com.infolion.xdss3.reassign.workflow;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.infolion.platform.dpframework.core.Constants;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.xdss3.BusinessState;
import com.infolion.xdss3.reassign.ReassignConstant;
import com.infolion.xdss3.reassign.domain.Reassign;
import com.infolion.xdss3.reassign.service.ReassignService;
/**
 * 判断并设置结清标识
 */
public class ReassignActionHandler2 implements ActionHandler{

    public void execute(ExecutionContext context) throws Exception{
        String businessId = (String) context.getContextInstance().getVariable(Constants.WORKFLOW_USER_KEY_VALUE);
        ReassignService reassignService = (ReassignService) EasyApplicationContextUtils.getBeanByName("reassignService");

        Reassign reassign = reassignService.getReassignById(businessId);

        /*
         * 设置重分配状态为审核通过
         */
        reassignService.updateReassignState(businessId, BusinessState.SUBMITPASS);

        if(reassign.getReassigntmethod().equals(ReassignConstant.RESET_TO_FI)){     // 【重置（财务部直接解除分配关系）】
            // 复制新单据
//            reassignService.copyCreateNewNo(reassign.getReassigntype(), reassign.getReassignboid(), reassign.getReassignid());
//            // 废除旧单据
//            reassignService.abolishOldNo(reassign.getReassigntype(), reassign.getReassignboid());
//            // 取消结清标志
//            reassignService.resetClearFlag(reassign.getReassigntype(), reassign.getReassignboid());
        }else if(reassign.getReassigntmethod().equals(ReassignConstant.FI_CLEAR)){   // 【冲销（财务部冲销并作废）】
            // 废除旧单据
            reassignService.abolishOldNo(reassign.getReassigntype(), reassign.getReassignboid());
            // 取消结清标志
            reassignService.resetClearFlag(reassign.getReassigntype(), reassign.getReassignboid(),"0");
        }else{
            // 设置结清标志
            reassignService.setIsClearFlag(reassign.getReassigntype(), reassign.getReassignboid());
            /*
             * 判断并更新重分配对应单据的表的状态（"客户单清"、"供应商单清"不会进来）
             */
            reassignService.finishProcess(reassign);
        }
        context.getProcessInstance().signal();
    }

}
