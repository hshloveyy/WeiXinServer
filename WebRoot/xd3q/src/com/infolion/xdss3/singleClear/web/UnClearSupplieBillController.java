/*
 * @(#)UnClearSupplieBillController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：java业务平台代码生成工具
 *  时　间：2010年07月08日 14点42分19秒
 *  描　述：创建
 */
package com.infolion.xdss3.singleClear.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.bo.domain.BusinessObject;
import com.infolion.platform.bo.service.BusinessObjectService;
import com.infolion.platform.dpframework.core.annotation.BDPController;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.AuthSql;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.xdss3.singleClearGen.web.UnClearSupplieBillControllerGen;

/**
 * <pre>
 * 未清应付（贷方）(UnClearSupplieBill)控制器类
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
public class UnClearSupplieBillController extends UnClearSupplieBillControllerGen
{    
    @Autowired
    private GridService gridService;
    
    public void setGridService(GridService gridService) {
        this.gridService = gridService;
    }
    
	/**
	 * 增加  
	 *   
	 * @param request
	 * @param response
	 */
	public void _add (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
	/**
	 * 删除 
	 *   
	 * @param request
	 * @param response
	 */
	public void _del (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
	/**
	 * 取消激活
	 *   
	 * @param request
	 * @param response
	 */
	public void _reset (HttpServletRequest request, HttpServletResponse response)
	{
       
	}
	
	/**
	 * 激活 
	 *   
	 * @param request
	 * @param response
	 */
	public void _assignSet (HttpServletRequest request, HttpServletResponse response)
	{
       
    }

    
    /**
     * 中转综合查询页面
     */
    public ModelAndView _conditionQuery(HttpServletRequest request, HttpServletResponse response)
    {
        return new ModelAndView("xdss3/unclearquery/unClearSupplieBillQuery");
    }
    
	/**
     * 多条件查询
     * @param request
     * @param response
     * @param gridQueryCondition
     * @throws Exception
     */
    public void queryGrid(HttpServletRequest request,
            HttpServletResponse response, GridQueryCondition gridQueryCondition)
            throws Exception{
        BusinessObject businessObject = BusinessObjectService.getBusinessObject("UnClearSupplieBill");
        String strAuthSql = "";
        try {
            AuthSql authSql = (AuthSql) Class.forName("com.infolion.xdss3.CommonDataAuthSql").newInstance();
            strAuthSql = authSql.getAuthSql(businessObject);
            // 替换权限默认的前缀表名
            strAuthSql = strAuthSql.replace("YUNCLEARSUPPBILL", "t");
        } catch (Exception ex) {
            throw new SQLException("类不存在：" + ex.getMessage());
        }
        
        StringBuffer sql = new StringBuffer("SELECT A.*  FROM YUNCLEARSUPPBILL A, YSUPPLIERSICLEAR B ");
        sql.append("  WHERE TRIM(A.CONTRACT_NO) IS NULL   AND TRIM(A.PROJECT_NO) IS NULL AND A.SUPPLIERSCLEARID = B.SUPPLIERSCLEARID ");
        
        
        
        // 获取SQL参数
        String bukrs = request.getParameter("BUKRS");
        String gsber = request.getParameter("GSBER"); 
        String supplier = request.getParameter("LIFNR"); 
        String waers = request.getParameter("WAERS");                // 
        String voucherNo = request.getParameter("voucherno");           // 凭证号
        String accountdate1 = request.getParameter("augdt_to");  // 审批通过时间1
        String accountdata2 = request.getParameter("augdt_from");  // 审批通过时间2
        if (StringUtils.isNotBlank(accountdate1)) {
            accountdate1 = accountdate1.replaceAll("-", "");
        }
        if (StringUtils.isNotBlank(accountdata2)) {
            accountdata2 = accountdata2.replaceAll("-", "");
        }
        
        
        // 设置凭证号条件
        if(StringUtils.isNotBlank(voucherNo)){
            sql.append("AND A.VOUCHERNO LIKE '%" + voucherNo + "%'");
        }
        // 设置公司
        if(StringUtils.isNotBlank(bukrs)){
            sql.append(" AND B.COMPANYNO = '" + bukrs + "'");
        }
        if(StringUtils.isNotBlank(gsber)){
            sql.append(" AND B.DEPID = '" + gsber + "'");
        }
        if(StringUtils.isNotBlank(supplier)){
            sql.append(" AND B.SUPPLIER = '" + supplier + "'");
        }
        if(StringUtils.isNotBlank(waers)){
            sql.append(" AND B.CURRENCYTEXT = '" + waers + "'");
        }
        
        // 设置审批通过时间条件
        if(StringUtils.isNotBlank(accountdate1) && StringUtils.isNotBlank(accountdata2)){
            sql.append(" AND REPLACE(B.ACCOUNTDATE, '-', '') BETWEEN '" + accountdate1 + "' AND '" + accountdata2 + "'");
        }else if(StringUtils.isNotBlank(accountdate1)){
            sql.append(" AND REPLACE(B.ACCOUNTDATE, '-', '') > '" + accountdate1 + "'");
        }else if(StringUtils.isNotBlank(accountdata2)){
            sql.append(" AND REPLACE(B.ACCOUNTDATE, '-', '') < '" + accountdata2 + "'");
        }
        
        
        gridQueryCondition.setBoName("");
        gridQueryCondition.setTableSql("");
        gridQueryCondition.setDefaultCondition("1=1 " + strAuthSql);
        gridQueryCondition.setWhereSql("");
        gridQueryCondition.setOrderSql("");
        gridQueryCondition.setGroupBySql("");
        gridQueryCondition.setTableName("(" + sql.toString() + ") t");
        gridQueryCondition.setHandlerClass("com.infolion.xdss3.unclearQuery.domain.UnClearSupplieBillQueryGrid");
        String editable = "false";
        String needAuthentication = "true";
        GridData gridJsonData = this.gridService.getGridData(gridQueryCondition, editable, needAuthentication);
        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(jsonList);
        } catch (IOException e) {
            logger.error("输出json失败," + e.getMessage(), e.getCause());
        }
    }
}