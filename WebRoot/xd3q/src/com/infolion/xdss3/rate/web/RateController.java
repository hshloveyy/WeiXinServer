/*
 * @(#)RateController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年11月24日 16点19分06秒
 *  描　述：创建
 */
package com.infolion.xdss3.rate.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.xdss3.rate.dao.RateJdbcDao;
import com.infolion.xdss3.rate.domain.Rate;
import com.infolion.xdss3.rateGen.web.RateControllerGen;
import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.DateUtils;
import com.infolion.platform.workflow.engine.WorkflowService;

/**
 * <pre>
 * 利率(Rate)控制器类
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
@BDPController(parent = "baseMultiActionController")
public class RateController extends RateControllerGen {
    
    @Autowired
    private RateJdbcDao rateJdbcDao;
    
    public void setRateJdbcDao(RateJdbcDao rateJdbcDao) {
        this.rateJdbcDao = rateJdbcDao;
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
        Rate rate = new Rate();
        String strLastDate = this.rateJdbcDao.getLastRateDate();
        if (StringUtils.isNotBlank(strLastDate)) {
            try {
                rate.setStartdate(DateUtils.getDateStr(DateUtils.addDate(strLastDate, Calendar.DATE, 1),DateUtils.DB_STORE_DATE));
            } catch (ParseException e) {
            }
        }
        String workflowTaskId = request.getParameter("workflowTaskId");
        String workflowNodeDefId = request.getParameter("workflowNodeDefId");
        request.setAttribute("vt", getBusinessObject().getViewText());
        if (null != getBusinessObject().getSubBusinessObject()) {
            // 取得子业务对象
            for (BusinessObject bo : getBusinessObject().getSubBusinessObject()) {
                request.setAttribute(bo.getBeanAttribute().getBeanInstanceName() + "Vt", bo.getViewText());
            }
        }
        request.setAttribute("rate", rate);
        boolean haveBindWorkFlow = WorkflowService.getHaveBindWorkFlow("000000000359");
        request.setAttribute("haveBindWorkFlow", haveBindWorkFlow);
        request.setAttribute("workflowTaskId", workflowTaskId);
        request.setAttribute("workflowNodeDefId", workflowNodeDefId);
        return new ModelAndView("xdss3/rate/rateAdd");
    }
}