/*
 * @(#)TradeMonitoringController.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-5-7
 *  描　述：创建
 */

package com.infolion.xdss3.tradeMonitoring.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.infolion.platform.dpframework.core.web.BaseMultiActionController;
import com.infolion.platform.dpframework.util.StringUtils;
import com.infolion.xdss3.tradeMonitoring.domain.TradeMonitoringTreeNode;
import com.infolion.xdss3.tradeMonitoring.service.TradeMonitoringService;

/**
 * <pre>
 * 贸易监控,控制器
 * </pre>
 * 
 * <br>
 * JDK版本:1.5
 * 
 * @author 林进旭
 * @version 1.0
 * @see The author for more details
 * @since 1.0
 */
public class TradeMonitoringController extends BaseMultiActionController
{

	@Autowired
	private TradeMonitoringService tradeMonitoringService;

	/**
	 * @param tradeMonitoringService
	 *            the tradeMonitoringService to set
	 */
	public void setTradeMonitoringService(TradeMonitoringService tradeMonitoringService)
	{
		this.tradeMonitoringService = tradeMonitoringService;
	}

	/**
	 * 管理
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _manage(HttpServletRequest request, HttpServletResponse response)
	{
		String strIsDefaultCondition = request.getParameter("isdefault");
		if (strIsDefaultCondition == null)
		{
			strIsDefaultCondition = "";
		}
		request.setAttribute("isdefault", strIsDefaultCondition);

		String strQueryValue = request.getParameter("queryValue");
		if (strQueryValue == null)
		{
			strQueryValue = "";
		}
		request.setAttribute("queryValue", strQueryValue);

		return new ModelAndView("xdss3/tradeMonitoring/tradeMonitoringManage");
	}

	public ModelAndView _conditionManage(HttpServletRequest request, HttpServletResponse response)
	{
		String strIsDefaultCondition = request.getParameter("isdefault");
		if (strIsDefaultCondition == null)
		{
			strIsDefaultCondition = "";
		}
		request.setAttribute("isdefault", strIsDefaultCondition);

		String strQueryValue = request.getParameter("queryValue");
		if (strQueryValue == null)
		{
			strQueryValue = "";
		}
		request.setAttribute("queryValue", strQueryValue);

		return new ModelAndView("xdss3/tradeMonitoring/tradeMonitoringManage");
	}

	/**
	 * 测试管理
	 * 
	 * @param request
	 * @param response
	 */
	public ModelAndView _manager(HttpServletRequest request, HttpServletResponse response)
	{
		return new ModelAndView("xdss3/tradeMonitoring/tradeMonitoringManager");
	}

	/**
	 * 取得贸易监控树。
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getTradeMonitoringColumnTree(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String nodeId = request.getParameter("nodeId"); // request.getParameter("id");
		String tradeType = request.getParameter("tradeType");
		String businessType = request.getParameter("businessType");
		String dept_id = request.getParameter("dept_id");
		String apply_time = request.getParameter("apply_time");
		String apply_time1 = request.getParameter("apply_time1");
		String cGroupTime = request.getParameter("cGroupTime");
		String cGroupTime1 = request.getParameter("cGroupTime1");

		String queryField = StringUtils.urlEncode(request.getParameter("queryField"));
		String queryValue = StringUtils.urlEncode(request.getParameter("queryValue"));

		log.debug("queryField:" + queryField + ",queryValue:" + queryValue);
		log.debug("dept_id:" + dept_id + ",apply_time:" + apply_time);
		log.debug("nodeId:" + nodeId + ",tradeType:" + tradeType + ",businessType:" + businessType);
		String jsontext = "";
		if (!StringUtils.isNullBlank(queryField) && !StringUtils.isNullBlank(nodeId))
		{
			jsontext = this.tradeMonitoringService.getTradeMonitoringColumnTreeJsonText(nodeId, tradeType, businessType, queryField, queryValue, dept_id, apply_time,apply_time1,cGroupTime,cGroupTime1);
		}
		log.debug("取得贸易监控树JSON串:" + jsontext);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsontext);
	}

	/**
	 * 测试
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getTradeMonitoringGridData(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String anode = request.getParameter("anode");
		int intStart = start == null ? 0 : Integer.valueOf(start);
		int intLimit = limit == null ? 0 : Integer.valueOf(limit);

		JSONObject jo = new JSONObject();

		List<TradeMonitoringTreeNode> list = new ArrayList<TradeMonitoringTreeNode>();
		TradeMonitoringTreeNode node = new TradeMonitoringTreeNode();

		if (anode == null)
		{
			node.setBusinessName("BB");
			node.setBusinessNo("BB");
			node.setBusinessType("BB");
			node.setBusinessTypeName("BB");
			node.setSdate("2010-05-17");
			node.setIconCls(" ");
			node.setLeaf(false);
			node.set_is_leaf(false);
			node.setNodeId("BBBB-BBBB-BBBB-BBB-1ADFD-BBBB");
			node.setNodeName("BB");
			node.setParentNodeId(null);
			node.set_parent(JSONNull.getInstance());
			node.set_lft(1);
			node.set_rgt(1);
			node.setProcessState("BB");
			node.setRemark("BB");
			node.setTradeType("BB");
			node.setUrl("BB");

			list.add(node);

			node = new TradeMonitoringTreeNode();

			node.setBusinessName("AA");
			node.setBusinessNo("AA");
			node.setBusinessType("AAA");
			node.setBusinessTypeName("AA");
			node.setSdate("2010-05-17");
			node.setIconCls(" ");
			node.set_is_leaf(true);
			node.setLeaf(true);
			node.setNodeId("BBBB-BBBB-BBBB-BBB-1ADFD-AAAA");
			node.setNodeName("AA");
			node.setParentNodeId(null);
			node.set_parent(JSONNull.getInstance());
			node.setProcessState("AA");
			node.setRemark("AA");
			node.setTradeType("AA");
			node.setUrl("AA");

			list.add(node);

			jo.put("total", 2);
		}

		else if (anode.equals("BBBB-BBBB-BBBB-BBB-1ADFD-BBBB"))
		{
			if (intStart == 0)
			{
				node = new TradeMonitoringTreeNode();
				node.setBusinessName("BB");
				node.setBusinessNo("BB");
				node.setBusinessType("BB");
				node.setBusinessTypeName("BB");
				node.setSdate("2010-05-17");
				node.setIconCls(" ");
				node.setLeaf(false);
				node.set_is_leaf(true);
				node.setNodeId("BBBB-BBBB-BBBB-BBB-1ADFD-AAAAAa");
				node.setNodeName("BB");
				node.setParentNodeId("BBBB-BBBB-BBBB-BBB-1ADFD-BBBB");
				node.set_parent("BBBB-BBBB-BBBB-BBB-1ADFD-BBBB");
				node.setProcessState("BB");
				node.setRemark("BB");
				node.setTradeType("BB");
				node.setUrl("BB");
				list.add(node);

				node = new TradeMonitoringTreeNode();
				node.setBusinessName("E");
				node.setBusinessNo("E");
				node.setBusinessType("E");
				node.setBusinessTypeName("E");
				node.setSdate("2010-05-17");
				node.setIconCls(" ");
				node.setLeaf(false);
				node.set_is_leaf(true);
				node.setNodeId("BBBB-BBBB-BBBB-BBB-1ADFD-EEEE");
				node.setNodeName("BB");
				node.setParentNodeId("BBBB-BBBB-BBBB-BBB-1ADFD-BBBB");
				node.set_parent("BBBB-BBBB-BBBB-BBB-1ADFD-BBBB");
				node.setProcessState("E");
				node.setRemark("E");
				node.setTradeType("E");
				node.setUrl("E");
				list.add(node);

			}
			else
			{
				node = new TradeMonitoringTreeNode();
				node.setBusinessName("F");
				node.setBusinessNo("F");
				node.setBusinessType("F");
				node.setBusinessTypeName("F");
				node.setSdate("2010-05-17");
				node.setIconCls(" ");
				node.setLeaf(false);
				node.set_is_leaf(true);
				node.setNodeId("BBBB-BBBB-BBBB-BBB-1ADFD-FFFFF");
				node.setNodeName("FF");
				node.setParentNodeId("BBBB-BBBB-BBBB-BBB-1ADFD-BBBB");
				node.set_parent("BBBB-BBBB-BBBB-BBB-1ADFD-BBBB");
				node.setProcessState("F");
				node.setRemark("F");
				node.setTradeType("E");
				node.setUrl("E");
				list.add(node);
			}

			jo.put("total", 3);
		}

		JSONArray ja = JSONArray.fromObject(list);
		log.debug("start:" + start + ",limit:" + limit + ",anode:" + anode);

		jo.put("data", ja);
		jo.put("success", true);

		log.debug("jo.toString:" + jo.toString());
		String jsontext = jo.toString();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsontext);
	}
}
