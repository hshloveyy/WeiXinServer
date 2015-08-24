/**
 * 
 */
package com.infolion.xdss3.exchangeRate.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;


import com.infolion.platform.core.BusinessException;
import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.uicomponent.grid.GridService;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridData;
import com.infolion.platform.dpframework.uicomponent.grid.data.GridQueryCondition;
import com.infolion.platform.dpframework.util.ExBeanUtils;
import com.infolion.platform.util.MultyGridData;
import com.infolion.xdss3.exchangeRate.domain.ExchangeRate;
import com.infolion.xdss3.exchangeRate.service.ExchangeRateService;
import com.infolion.platform.dpframework.util.StringUtils;



/**
 * @author user
 *
 */
public class ExchangeRateController extends BaseMultiActionController {

	@Autowired
    private ExchangeRateService exchangeRateService;
	
	 /**
	 * @param exchangeRateService the exchangeRateService to set
	 */
	public void setExchangeRateService(ExchangeRateService exchangeRateService) {
		this.exchangeRateService = exchangeRateService;
	}
	@Autowired
    private GridService gridService;
    
    public void setGridService(GridService gridService) {
        this.gridService = gridService;
    }
    
	public ModelAndView exchangeRateManage(HttpServletRequest request,
	            HttpServletResponse response) {
	        return new ModelAndView("xdss3/exchangeRate/exchangeRateManage");
	}
	public ModelAndView create(HttpServletRequest request,
	            HttpServletResponse response) {
	        return new ModelAndView("xdss3/exchangeRate/exchangeRateAdd");
	}
	   public void exchangeRateGrid(HttpServletRequest request, HttpServletResponse response, GridQueryCondition gridQueryCondition) throws Exception{
	        StringBuffer sql = new StringBuffer("SELECT A.*, '' total FROM yexchangeRate A WHERE 1=1");
	        // 获取SQL参数
	        String ratedateto = request.getParameter("ratedateto");
	        String ratedatefrom = request.getParameter("ratedatefrom");
	        String currency = request.getParameter("currency");
	        String currency2 = request.getParameter("currency2");
	        if(StringUtils.isNotBlank(ratedateto) && StringUtils.isNotBlank(ratedatefrom)){
	            ratedateto = ratedateto.replaceAll("-", "");
	            ratedatefrom = ratedatefrom.replaceAll("-", "");
	            sql.append(" AND A.rate_date between '" + ratedateto + "' and '"+ratedatefrom+"'");
	        }
	        if(StringUtils.isNotBlank(currency)){
	        	sql.append(" AND A.currency ='"+currency+"'" );
	        }
	        if(StringUtils.isNotBlank(currency2)){
	        	sql.append(" AND A.currency2 ='"+currency2+"'" );
	        }
	        List<ExchangeRate> li = this.exchangeRateService._query(sql.toString());
//	        gridQueryCondition.setBoName("");
//	        gridQueryCondition.setTableSql("");
//	        gridQueryCondition.setWhereSql("");
//	        gridQueryCondition.setOrderSql("");
//	        gridQueryCondition.setGroupBySql("");
//	        gridQueryCondition.setLimit("50");
//	        gridQueryCondition.setStart("1");
//	        gridQueryCondition.setTableName("(" + sql.toString() + ") t");
//	        gridQueryCondition.setHandlerClass("com.infolion.xdss3.exchangeRate.domain.ExchangeRateGrid");
//	        String editable = "false";
//	        String needAuthentication = "false";
//	        GridData gridJsonData = this.gridService.getGridData(gridQueryCondition, editable, needAuthentication);
	    	MultyGridData gridJsonData = new MultyGridData();
			gridJsonData.setData(li.toArray());
	        JSONObject jsonList = JSONObject.fromObject(gridJsonData);
	        try {
	            response.setContentType("text/html;charset=UTF-8");
	            response.getWriter().print(jsonList);
	        } catch (IOException e) {
	            logger.error("输出json失败," + e.getMessage(), e.getCause());
	        }
	    }
	   
	   public void save(HttpServletRequest request,HttpServletResponse response,ExchangeRate exchangeRate) throws IllegalAccessException, InvocationTargetException, IOException{
		   JSONObject jo = new JSONObject();
			// 绑定主对象值
//		   ExchangeRate exchangeRate = (ExchangeRate) ExBeanUtils.bindBusinessObjectData(request.getParameterMap(), ExchangeRate.class , true , request.getMethod(), true);
		
		   
	                     
	        
			   if(StringUtils.isBlank(exchangeRate.getRate_date())){
				   jo.put("error", "日期不能为空");
				   this.operateSuccessfullyWithString(response,jo.toString());
			   }else{
				   String da = exchangeRate.getRate_date().replaceAll("-", "");
				   exchangeRate.setRate_date(da);
			   }
			   if(StringUtils.isBlank(exchangeRate.getCurrency())){
				   jo.put("error", "币别不能为空");
				   this.operateSuccessfullyWithString(response,jo.toString());
				   return ;
			   }
			   if(StringUtils.isBlank(exchangeRate.getCurrency2())){
				   jo.put("error", "本位币币别不能为空");
				   this.operateSuccessfullyWithString(response,jo.toString());
				   return ;
			   }
			   if(null == exchangeRate.getBuying_rate()){
				   jo.put("error", "买入价不能为空");
				   this.operateSuccessfullyWithString(response,jo.toString());
				   return ;
			   }
			   if(null == exchangeRate.getSelling_rate()){
				   jo.put("error", "卖出价不能为空");
				   this.operateSuccessfullyWithString(response,jo.toString());
				   return ;
			   }
			   if(null == exchangeRate.getMiddle_rate()){
				   jo.put("error", "中间价不能为空");
				   this.operateSuccessfullyWithString(response,jo.toString());
				   return ;
			   }
		  
		   this.exchangeRateService._saveOrUpdate(exchangeRate);
		   this.operateSuccessfullyWithString(response,jo.toString());
	   }
	   
	   
		public ModelAndView view(HttpServletRequest request,
	            HttpServletResponse response) {
			 String id = request.getParameter("id");
			ExchangeRate rate =this.exchangeRateService._get(id);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式		
			try {
				Date dae =df.parse(rate.getRate_date());
				SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式	
				String d=df2.format(dae);
				rate.setRate_date(d);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("exchangeRate", rate);
			return new ModelAndView("xdss3/exchangeRate/exchangeRateAdd");
		}
		
		public void del(HttpServletRequest request,
	            HttpServletResponse response) {
			 String id = request.getParameter("id");					
			JSONObject jo = new JSONObject();
			exchangeRateService._delete(id, null);
			this.operateSuccessfullyWithString(response,jo.toString());
		}
		
}
