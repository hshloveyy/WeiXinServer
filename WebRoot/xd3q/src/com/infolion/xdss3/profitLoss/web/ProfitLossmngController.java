/*
 * @(#)ProfitLossmngController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年05月21日 11点46分24秒
 *  描　述：创建
 */
package com.infolion.xdss3.profitLoss.web;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.infolion.xdss3.profitLoss.domain.ProfitLossmng;
import com.infolion.xdss3.profitLossGen.web.ProfitLossmngControllerGen;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.util.ExBeanUtils;

/**
 * <pre>
 * 存货浮动盈亏调查表(ProfitLossmng)控制器类
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
public class ProfitLossmngController extends ProfitLossmngControllerGen
{
	/**
	 * 保存  
	 *   
	 * @param request
	 * @param response
	 */
	public void _saveOrUpdate(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jo = new JSONObject();
		//Set<ProfitLossmng> profitLossmngSet = ExBeanUtils.bindModifyBoData(request.getParameterMap(), ProfitLossmng.class);
		String strProfitlossidList = request.getParameter("profitlossidlist");
		String strBackcommentList = request.getParameter("backcommentlist");
		String strMaxlosscommentList = request.getParameter("maxlosscommentlist");
		String strRemaintotalvalueList = request.getParameter("remaintotalvaluelist");
		String strPositionvalueList = request.getParameter("positionvaluelist");
		
        this.profitLossmngService.saveChange(strProfitlossidList, strBackcommentList,
                strMaxlosscommentList, strRemaintotalvalueList, strPositionvalueList);
		
		//this.profitLossmngService._saveOrUpdate(profitLossmngSet, getBusinessObject());
		this.operateSuccessfullyWithString(response,jo.toString());
	}	
	

    /**
     * 导出Excel
     * 
     * @param request
     * @param response
     */
    public void _expExcel(HttpServletRequest request, HttpServletResponse response)
    {
        response.setHeader("Content-Type","application/force-download");
        response.setHeader("Content-Type","application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment;filename=export.xls");
        String exportContent = request.getParameter("exportContent");
        try
        {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(exportContent); 
            System.out.println(exportContent);
        }
        catch (IOException e)
        {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
    }
}