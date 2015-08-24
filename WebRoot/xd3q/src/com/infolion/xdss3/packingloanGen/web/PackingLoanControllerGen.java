/*
 * @(#)PackingLoanControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年05月26日 10点55分54秒
 *  描　述：创建
 */
package com.infolion.xdss3.packingloanGen.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.dpframework.core.web.AbstractGenController;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.xdss3.financeSquare.domain.FundFlow;
import com.infolion.xdss3.financeSquare.domain.SettleSubject;
import com.infolion.xdss3.packingloan.domain.PackingBankItem;
import com.infolion.xdss3.packingloan.domain.PackingLoan;
import com.infolion.xdss3.packingloan.domain.PackingReBankItem;
import com.infolion.xdss3.packingloan.domain.PackingReBankTwo;
import com.infolion.xdss3.packingloan.service.PackingLoanService;

/**
 * <pre>
 * 打包贷款(PackingLoan)控制器类
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author java业务平台代码生成工具
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class PackingLoanControllerGen extends AbstractGenController {
    private final String boName = "PackingLoan";

    public String getBoName() {
        return this.boName;
    }

    @Autowired
    protected PackingLoanService packingLoanService;

    public void setPackingLoanService(PackingLoanService packingLoanService) {
        this.packingLoanService = packingLoanService;
    }

    /**
     * 创建
     * 
     * @param request
     * @param response
     */
    public ModelAndView _create(HttpServletRequest request, HttpServletResponse response) {
        String calActivityId = request.getParameter("calActivityId");
        if (StringUtils.isNotBlank(calActivityId))
            request.setAttribute("calActivityId", calActivityId);
        PackingLoan packingLoan = new PackingLoan();
        String workflowTaskId = request.getParameter("workflowTaskId");
        String workflowNodeDefId = request.getParameter("workflowNodeDefId");
        request.setAttribute("vt", getBusinessObject().getViewText());
        if (null != getBusinessObject().getSubBusinessObject()) {
            // 取得子业务对象
            for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
                request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
            }
        }
        // 部门需要代码设置
        com.infolion.platform.console.sys.context.UserContext xdssUserContext = com.infolion.platform.console.sys.context.UserContextHolder
                .getLocalUserContext().getUserContext();
        if (null != xdssUserContext)
            packingLoan.setDept_id(xdssUserContext.getSysUser().getDeptId());
        
        request.setAttribute("packingLoan", packingLoan);
        boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000353");
        request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
        request.setAttribute("workflowTaskId", workflowTaskId);
        request.setAttribute("workflowNodeDefId", workflowNodeDefId);
        return new ModelAndView("xdss3/packingloan/packingLoanAdd");
    }

    /**
     * 复制创建
     * 
     * @param request
     * @param response
     */
    public ModelAndView _copyCreate(HttpServletRequest request, HttpServletResponse response) {
        String calActivityId = request.getParameter("calActivityId");
        if (StringUtils.isNotBlank(calActivityId))
            request.setAttribute("calActivityId", calActivityId);
        String id = request.getParameter("packingid");
        PackingLoan packingLoan = this.packingLoanService._getEntityCopy(id);
        request.setAttribute("isCreateCopy", "true");
        request.setAttribute("packingLoan", packingLoan);
        request.setAttribute("vt", getBusinessObject().getViewText());
        if (null != getBusinessObject().getSubBusinessObject()) {
            // 取得子业务对象
            for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
                request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
            }
        }
        boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000353");
        request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
        request.setAttribute("workflowTaskId", "");
        request.setAttribute("workflowNodeDefId", "");
        return new ModelAndView("xdss3/packingloan/packingLoanAdd");
    }

    /**
     * 删除
     * 
     * @param request
     * @param response
     */
    public void _delete(HttpServletRequest request, HttpServletResponse response) {
        String packingid = request.getParameter("packingid");
        packingLoanService._delete(packingid, getBusinessObject());
        this.operateSuccessfully(response);
    }

    /**
     * 删除
     * 
     * @param request
     * @param response
     */
    public void _deletes(HttpServletRequest request, HttpServletResponse response) {
        String packingLoanIds = request.getParameter("packingLoanIds");
        packingLoanService._deletes(packingLoanIds, getBusinessObject());
        this.operateSuccessfully(response);
    }

    /**
     * 查看
     * 
     * @param request
     * @param response
     */
    public ModelAndView _view(HttpServletRequest request, HttpServletResponse response) {
        PackingLoan packingLoan = new PackingLoan();
        String workflowTaskId = request.getParameter("workflowTaskId");
        String workflowNodeDefId = request.getParameter("workflowNodeDefId");
        String id = request.getParameter("packingid");
        String businessId = request.getParameter("businessId");
        if (null == id)
            id = businessId;

        if (StringUtils.isNullBlank(id)) {
            packingLoan = this.packingLoanService._get(id);
        } else {
            packingLoan = this.packingLoanService._get(id);
        }

        boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000353");
        request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
        request.setAttribute("businessId", id);
        request.setAttribute("workflowTaskId", workflowTaskId);
        request.setAttribute("workflowNodeDefId", workflowNodeDefId);
        request.setAttribute("vt", getBusinessObject().getViewText());
        if (null != getBusinessObject().getSubBusinessObject()) {
            // 取得子业务对象
            for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
                request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
            }
        }

        request.setAttribute("packingLoan", packingLoan);
        return new ModelAndView("xdss3/packingloan/packingLoanView");
    }

    /**
     * 提交
     * 
     * @param request
     * @param response
     */

    public void _submitProcess(HttpServletRequest request, HttpServletResponse response) {
        // 绑定主对象值
        PackingLoan packingLoan = (PackingLoan) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
                PackingLoan.class, true, request.getMethod(), true);
        // 类型标识是从那个页面提交，view 表示从view页面提交流程。
        String type = request.getParameter("type");
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<PackingBankItem> packingBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingBankItem.class, null);
        Set<PackingBankItem> deletedPackingBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingBankItem.class, null);
        packingLoan.setPackingBankItem(packingBankItemmodifyItems);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<PackingReBankItem> packingReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { packingLoan }, PackingReBankItem.class, null);
        Set<PackingReBankItem> deletedPackingReBankItemSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingReBankItem.class, null);
        packingLoan.setPackingReBankItem(packingReBankItemmodifyItems);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<PackingReBankTwo> packingReBankTwomodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingReBankTwo.class, null);
        Set<PackingReBankTwo> deletedPackingReBankTwoSet = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingReBankTwo.class, null);
        packingLoan.setPackingReBankTwo(packingReBankTwomodifyItems);
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
                SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null) {
            packingLoan.setSettleSubject(settleSubject);
            settleSubject.setPackingLoan(packingLoan);
        }
        // 绑定子对象(一对一关系)
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class,
                false, request.getMethod(), true);
        if (fundFlow != null) {
            packingLoan.setFundFlow(fundFlow);
            fundFlow.setPackingLoan(packingLoan);
        }
        
        // HONGJJ 添加待办信息
        String workflowBusinessNote = "";
        String deptName = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getDeptName();
        String creator = com.infolion.platform.console.sys.context.UserContextHolder.getLocalUserContext().getUserContext().getSysUser().getRealName();
        String bankName = "";
        workflowBusinessNote = packingLoan.getPacking_no() + "|" + deptName + "|" + creator + "|"
                + packingLoan.getCurrency() + "|金额" + packingLoan.getApplyamount() + "|" + bankName;
        packingLoan.setWorkflowBusinessNote(workflowBusinessNote);
        
        
        if (!"view".equalsIgnoreCase(type)) {
            this.packingLoanService._saveOrUpdate(packingLoan, deletedPackingBankItemSet, deletedPackingReBankItemSet,
                    getBusinessObject());

            this.packingLoanService.updateReBankTwo(packingLoan.getPackingid());
        }
        this.packingLoanService._submitProcess(packingLoan, deletedPackingBankItemSet, deletedPackingReBankItemSet,
                getBusinessObject());
        this.operateSuccessfully(response);
    }

    /**
     * 管理
     * 
     * @param request
     * @param response
     */
    public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("vt", getBusinessObject().getViewText());
        return new ModelAndView("xdss3/packingloan/packingLoanManage");
    }

    /**
     * 取消后的解锁
     * 
     * @param request
     * @param response
     */
    public void _cancel(HttpServletRequest request, HttpServletResponse response) {
        // 绑定主对象值
        PackingLoan packingLoan = new PackingLoan();
        String packingid = request.getParameter("packingid");
        packingLoan.setPackingid(packingid);
        LockService.unLockBOData(packingLoan);
        this.operateSuccessfullyHiddenInfo(response);
    }

    /**
     * 分配
     * 
     * @param request
     * @param response
     */
    public void _assign(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 保存
     * 
     * @param request
     * @param response
     */
    public void _saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jo = new JSONObject();
        // 绑定主对象值
        PackingLoan packingLoan = (PackingLoan) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
                PackingLoan.class, true, request.getMethod(), true);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<PackingBankItem> packingBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingBankItem.class, null);
        packingLoan.setPackingBankItem(packingBankItemmodifyItems);
        Set<PackingBankItem> packingBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingBankItem.class, null);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<PackingReBankItem> packingReBankItemmodifyItems = ExBeanUtils.bindModifySubBoData(
                request.getParameterMap(), new Object[] { packingLoan }, PackingReBankItem.class, null);
        packingLoan.setPackingReBankItem(packingReBankItemmodifyItems);
        Set<PackingReBankItem> packingReBankItemdeleteItems = ExBeanUtils.bindDeleteSubBoData(
                request.getParameterMap(), new Object[] { packingLoan }, PackingReBankItem.class, null);
        // 绑定需要修改（包括新增和修改）的子对象集合(一对多关系)
        Set<PackingReBankTwo> packingReBankTwomodifyItems = ExBeanUtils.bindModifySubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingReBankTwo.class, null);
        packingLoan.setPackingReBankTwo(packingReBankTwomodifyItems);
        Set<PackingReBankTwo> packingReBankTwodeleteItems = ExBeanUtils.bindDeleteSubBoData(request.getParameterMap(),
                new Object[] { packingLoan }, PackingReBankTwo.class, null);
        // 绑定子对象(一对一关系)
        SettleSubject settleSubject = (SettleSubject) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
                SettleSubject.class, false, request.getMethod(), true);
        if (settleSubject != null) {
            packingLoan.setSettleSubject(settleSubject);
            settleSubject.setPackingLoan(packingLoan);
        }
        // 绑定子对象(一对一关系)
        FundFlow fundFlow = (FundFlow) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), FundFlow.class,
                false, request.getMethod(), true);
        if (fundFlow != null) {
            packingLoan.setFundFlow(fundFlow);
            fundFlow.setPackingLoan(packingLoan);
        }
        this.packingLoanService._saveOrUpdate(packingLoan, packingBankItemdeleteItems, packingReBankItemdeleteItems,
                getBusinessObject());

        this.packingLoanService.updateReBankTwo(packingLoan.getPackingid());

        // 如果返回参数带有calActivityId，将业务对象保存到相应的CalActivity中
        String calActivityId = request.getParameter("calActivityId");
        if (StringUtils.isNotBlank(calActivityId)) {
            CalActivityService calActivityService = (CalActivityService) EasyApplicationContextUtils
                    .getBeanByName("calActivityService");
            CalActivity calActivity = calActivityService._get(calActivityId);
            if (calActivity != null) {
                calActivity.setBoid(this.getBusinessObject().getBoId());
                calActivity.setBoname(this.getBoName());
                calActivity.setBusid(packingLoan.getPackingid());
                calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
            }
            this.operateClose(response);
        } else {
            jo.put("packing_no", packingLoan.getPacking_no());
            jo.put("packingid", packingLoan.getPackingid());
            jo.put("settlesubjectid", packingLoan.getSettleSubject() != null ? packingLoan.getSettleSubject()
                    .getSettlesubjectid() : "");
            jo.put("fundflowid", packingLoan.getFundFlow() != null ? packingLoan.getFundFlow().getFundflowid() : "");
            String creator = packingLoan.getCreator();
            String creator_text = SysCachePoolUtils.getDictDataValue("YUSER", creator);
            jo.put("creator_text", creator_text);
            jo.put("creator", creator);
            jo.put("createtime", packingLoan.getCreatetime());
            jo.put("lastmodifytime", packingLoan.getLastmodifytime());
            this.operateSuccessfullyWithString(response, jo.toString());
        }
    }

    /**
     * 附加空行
     * 
     * @param request
     * @param response
     */
    public void _appendLine(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 插入行
     * 
     * @param request
     * @param response
     */
    public void _insertLine(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 增加同级节点
     * 
     * @param request
     * @param response
     */
    public void _addNode(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 增加下级节点
     * 
     * @param request
     * @param response
     */
    public void _addSubNode(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 删除节点
     * 
     * @param request
     * @param response
     */
    public void _deleteNode(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 图表查询
     * 
     * @param request
     * @param response
     */
    public void _queryChart(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 图表明细查询
     * 
     * @param request
     * @param response
     */
    public void _queryChartDetail(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 下载
     * 
     * @param request
     * @param response
     */
    public void _download(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 上传
     * 
     * @param request
     * @param response
     */
    public void _upload(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 查询
     * 
     * @param request
     * @param response
     */
    public void _query(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 编辑
     * 
     * @param request
     * @param response
     */
    public ModelAndView _edit(HttpServletRequest request, HttpServletResponse response) {
        PackingLoan packingLoan = new PackingLoan();
        String id = request.getParameter("packingid");
        String workflowTaskId = request.getParameter("workflowTaskId");
        String workflowNodeDefId = request.getParameter("workflowNodeDefId");
        String businessId = request.getParameter("businessId");
        if (null == id)
            id = businessId;
        packingLoan = this.packingLoanService._get(id);
        if ( "业务填写还打包贷款信息".equals(packingLoan.getProcessstate()) ) {
            packingLoan.setReaccountdate(" ");
            packingLoan.setRevoucherdate(" ");
            packingLoan.setRetext(" ");
            packingLoan.setSettleSubject(null);
            packingLoan.setFundFlow(null);
            this.packingLoanService.updatePackingLoan(packingLoan);
            this.packingLoanService.deleteSubObject(packingLoan.getPackingid());
        }
        
        if (StringUtils.isNullBlank(id)) {
            packingLoan = this.packingLoanService._getForEdit(id);
        } else {
            packingLoan = this.packingLoanService._getForEdit(id);
        }

        boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000353");
        request.setAttribute("businessId", id);
        request.setAttribute("workflowTaskId", workflowTaskId);
        request.setAttribute("workflowNodeDefId", workflowNodeDefId);
        request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
        request.setAttribute("vt", getBusinessObject().getViewText());
        if (null != getBusinessObject().getSubBusinessObject()) {
            // 取得子业务对象
            for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
                request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
            }
        }
        request.setAttribute("packingLoan", packingLoan);
        return new ModelAndView("xdss3/packingloan/packingLoanEdit");
    }

    /**
     * 批量解锁
     * 
     * @param request
     * @param response
     */
    public void _unlock(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 锁定
     * 
     * @param request
     * @param response
     */
    public void _locked(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 现金日记帐
     * 
     * @param request
     * @param response
     */
    public void _cashJournal(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 模拟凭证
     * 
     * @param request
     * @param response
     */
    public void _voucherPreview(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 查看流程状态
     * 
     * @param request
     * @param response
     */
    public void _viewProcessState(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 查看凭证
     * 
     * @param request
     * @param response
     */
    public void _viewVoucher(HttpServletRequest request, HttpServletResponse response) {

    }
}