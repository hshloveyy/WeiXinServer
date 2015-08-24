/*
 * @(#)PackingReBankItemControllerGen.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年05月26日 10点55分57秒
 *  描　述：创建
 */
package com.infolion.xdss3.packingloanGen.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import java.io.IOException;
import java.util.Set;
import java.math.BigDecimal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.dpframework.util.JsonUtils;
import com.infolion.platform.dpframework.core.BusinessException;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.dpframework.uicomponent.lock.LockService;
import com.infolion.platform.basicApp.authManage.domain.OperationType;
import com.infolion.platform.dpframework.core.cache.SysCachePoolUtils;
import com.infolion.platform.workflow.engine.WorkflowService;
import com.infolion.platform.dpframework.util.EasyApplicationContextUtils;
import com.infolion.platform.calendar.domain.CalActivity;
import com.infolion.platform.calendar.service.CalActivityService;
import com.infolion.xdss3.packingloan.domain.PackingReBankItem;
import com.infolion.xdss3.packingloan.service.PackingReBankItemService;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.core.web.AbstractGenController;

/**
 * <pre>
 * 还打包贷款银行(PackingReBankItem)控制器类
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
public class PackingReBankItemControllerGen extends AbstractGenController {
    private final String boName = "PackingReBankItem";

    public String getBoName() {
        return this.boName;
    }

    @Autowired
    protected PackingReBankItemService packingReBankItemService;

    public void setPackingReBankItemService(PackingReBankItemService packingReBankItemService) {
        this.packingReBankItemService = packingReBankItemService;
    }

    /**
     * 添加还打包银行
     * 
     * @param request
     * @param response
     */
    public void _addReBank(HttpServletRequest request, HttpServletResponse response) {

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
        PackingReBankItem packingReBankItem = new PackingReBankItem();
        String workflowTaskId = request.getParameter("workflowTaskId");
        String workflowNodeDefId = request.getParameter("workflowNodeDefId");
        request.setAttribute("vt", getBusinessObject().getViewText());
        request.setAttribute("packingReBankItem", packingReBankItem);
        boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000355");
        request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
        request.setAttribute("workflowTaskId", workflowTaskId);
        request.setAttribute("workflowNodeDefId", workflowNodeDefId);
        return new ModelAndView("xdss3/packingloan/packingReBankItemAdd");
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
        PackingReBankItem packingReBankItem = new PackingReBankItem();
        packingReBankItem = (PackingReBankItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
                PackingReBankItem.class, false, request.getMethod(), false);
        request.setAttribute("packingReBankItem", packingReBankItem);
        request.setAttribute("vt", getBusinessObject().getViewText());
        boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000355");
        request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
        request.setAttribute("workflowTaskId", "");
        request.setAttribute("workflowNodeDefId", "");
        return new ModelAndView("xdss3/packingloan/packingReBankItemAdd");
    }

    /**
     * 删除
     * 
     * @param request
     * @param response
     */
    public void _delete(HttpServletRequest request, HttpServletResponse response) {
        String bankitemid = request.getParameter("bankitemid");
        packingReBankItemService._delete(bankitemid, getBusinessObject());
        this.operateSuccessfully(response);
    }

    /**
     * 删除
     * 
     * @param request
     * @param response
     */
    public void _deletes(HttpServletRequest request, HttpServletResponse response) {
        String packingReBankItemIds = request.getParameter("packingReBankItemIds");
        packingReBankItemService._deletes(packingReBankItemIds, getBusinessObject());
        this.operateSuccessfully(response);
    }

    /**
     * 查看
     * 
     * @param request
     * @param response
     */
    public ModelAndView _view(HttpServletRequest request, HttpServletResponse response) {
        PackingReBankItem packingReBankItem = new PackingReBankItem();
        packingReBankItem = (PackingReBankItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
                PackingReBankItem.class, false, request.getMethod(), false);
        request.setAttribute("vt", getBusinessObject().getViewText());

        request.setAttribute("packingReBankItem", packingReBankItem);
        return new ModelAndView("xdss3/packingloan/packingReBankItemView");
    }

    /**
     * 提交
     * 
     * @param request
     * @param response
     */

    public void _submitProcess(HttpServletRequest request, HttpServletResponse response) {
        // 绑定主对象值
        PackingReBankItem packingReBankItem = (PackingReBankItem) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), PackingReBankItem.class, true, request.getMethod(), true);
        // 类型标识是从那个页面提交，view 表示从view页面提交流程。
        String type = request.getParameter("type");
        if (!"view".equalsIgnoreCase(type)) {
            this.packingReBankItemService._saveOrUpdate(packingReBankItem, getBusinessObject());
        }
        this.packingReBankItemService._submitProcess(packingReBankItem, getBusinessObject());
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
        return new ModelAndView("xdss3/packingloan/packingReBankItemManage");
    }

    /**
     * 取消后的解锁
     * 
     * @param request
     * @param response
     */
    public void _cancel(HttpServletRequest request, HttpServletResponse response) {
        // 绑定主对象值
        PackingReBankItem packingReBankItem = new PackingReBankItem();
        String bankitemid = request.getParameter("bankitemid");
        packingReBankItem.setBankitemid(bankitemid);
        LockService.unLockBOData(packingReBankItem);
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
        PackingReBankItem packingReBankItem = (PackingReBankItem) ExBeanUtils.bindBusinessObjectData(
                request.getParameterMap(), PackingReBankItem.class, true, request.getMethod(), true);
        this.packingReBankItemService._saveOrUpdate(packingReBankItem, getBusinessObject());

        // 如果返回参数带有calActivityId，将业务对象保存到相应的CalActivity中
        String calActivityId = request.getParameter("calActivityId");
        if (StringUtils.isNotBlank(calActivityId)) {
            CalActivityService calActivityService = (CalActivityService) EasyApplicationContextUtils
                    .getBeanByName("calActivityService");
            CalActivity calActivity = calActivityService._get(calActivityId);
            if (calActivity != null) {
                calActivity.setBoid(this.getBusinessObject().getBoId());
                calActivity.setBoname(this.getBoName());
                calActivity.setBusid(packingReBankItem.getBankitemid());
                calActivityService._update2(calActivity, BusinessObjectService.getBusinessObject("CalActivity"));
            }
            this.operateClose(response);
        } else {
            jo.put("bankitemid", packingReBankItem.getBankitemid());
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
        PackingReBankItem packingReBankItem = new PackingReBankItem();
        packingReBankItem = (PackingReBankItem) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(),
                PackingReBankItem.class, false, request.getMethod(), false);
        request.setAttribute("vt", getBusinessObject().getViewText());
        request.setAttribute("packingReBankItem", packingReBankItem);
        return new ModelAndView("xdss3/packingloan/packingReBankItemEdit");
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
}