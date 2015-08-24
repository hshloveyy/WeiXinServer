/*
 * @(#)VatDetailController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2011年12月12日 15点58分46秒
 *  描　述：创建
 */
package com.infolion.xdss3.vatdetail.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.console.sys.context.UserContext;
import com.infolion.platform.console.sys.context.UserContextHolder;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.xdss3.vatdetail.domain.VatGoodDetail;
import com.infolion.xdss3.vatdetail.service.VatDetailService;
import com.infolion.xdss3.vatdetailGen.web.VatDetailControllerGen;

/**
 * <pre>
 * 期初已到税票未进仓(税额)(VatDetail)控制器类
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
public class VatDetailController extends VatDetailControllerGen {
    @Autowired
    private VatDetailService vatDetailService;

    public void setVatDetailService(VatDetailService vatDetailService) {
        this.vatDetailService = vatDetailService;
    }

    public ModelAndView vatGoodDetailQuery(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("xdss3/vatdetail/vatGoodDetailQuery");
    }

    /**
     * 参数及说明 
     * ① 公司代码：单选 BUKRS(必填) 
     * ② 业务范围：多选 模糊 T_GSBER(不输入,则传入空串,*号则传入所有业务范围) 
     * ③ 日期（期间）：开始日期BEGDA，结束日期， ENDDA(必填) 
     * ④ 物料号：多选 模糊T_MATNR(不输入则不传入任何参数；默认*号则传入所有+空格；输入“空”、“无”则传入空格) 
     * ⑤ 物料组：多选 模糊T_MATGROUP(不输入则不传入任何参数；默认*号则传入所有+空格；输入“空”、“无”则传入空格) 
     * ⑥ 业务类型：多选T_TYPE(不输入则不传入任何参数；默认*号则传入所有+空格；输入“空”、“无”则传入空格)
     * ⑦ 税率：多选T_TAXP(不输入则不传入任何参数；默认*号传入*号;)
     * 
     * @param request
     * @param response
     * @param gridQueryCondition
     * @throws Exception
     */
    public void queryvatGoodDetailGrid(HttpServletRequest request, HttpServletResponse response,
            GridQueryCondition gridQueryCondition) throws Exception {
        String bukrs = request.getParameter("bukrs");
        String t_gsber = request.getParameter("t_gsber");
        String begda = request.getParameter("begda");
        String endda = request.getParameter("endda");
        String t_matnr = request.getParameter("t_matnr");
        String t_matgroup = request.getParameter("t_matgroup");
        String t_type = request.getParameter("t_type");
        String t_taxp = request.getParameter("t_taxp");
        if (StringUtils.isNotBlank(t_gsber)) {
            t_gsber = URLDecoder.decode(t_gsber,"UTF-8");
            t_gsber = t_gsber.trim();
        }
        if (StringUtils.isNotBlank(t_matnr)) {
            t_matnr = URLDecoder.decode(t_matnr,"UTF-8");
            t_matnr = t_matnr.trim();
        }
        if (StringUtils.isNotBlank(t_type)) {
            t_type = URLDecoder.decode(t_type,"UTF-8");
        }
        if (StringUtils.isNotBlank(t_matgroup)) {
            t_matgroup = URLDecoder.decode(t_matgroup,"UTF-8");
        }

        List<VatGoodDetail> vatGoodDetail = vatDetailService.fetchSAPData(bukrs, t_gsber, begda, endda, t_matnr,
                t_matgroup, t_type, t_taxp);
        MultyGridData gridJsonData = new MultyGridData();
        gridJsonData.setData(vatGoodDetail.toArray());
        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonList);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
    }
    
    
    /**
     * 管理  
     *   
     * @param request
     * @param response
     */
    public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
    { 
        request.setAttribute("vt", getBusinessObject().getViewText());
        UserContext userContext = UserContextHolder.getLocalUserContext().getUserContext();
        String userid = userContext.getSysUser().getUserId();
        request.setAttribute("userid", userid);
        request.setAttribute("saptype", request.getParameter("saptype"));
        return new ModelAndView("xdss3/vatdetail/vatDetailManage");
    }
    
}
