/*
 * @(#)TradeMonitoringService.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-5-7
 *  描　述：创建
 */

package com.infolion.xdss3.tradeMonitoring.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infolion.platform.dpframework.core.service.BaseService;
import com.infolion.platform.util.StringUtils;
import com.infolion.xdss3.tradeMonitoring.dao.TradeMonitoringJdbcDao;
import com.infolion.xdss3.tradeMonitoring.domain.TradeMonitoringTreeNode;
import com.infolion.xdss3.tradeMonitoring.domain.TradeType;

/**
 * <pre>
 * 贸易监控服务类
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
@Service
public class TradeMonitoringService extends BaseService
{

	@Autowired
	private TradeMonitoringJdbcDao tradeMonitoringJdbcDao;

	/**
	 * @param tradeMonitoringJdbcDao
	 *            the tradeMonitoringJdbcDao to set
	 */
	public void setTradeMonitoringJdbcDao(TradeMonitoringJdbcDao tradeMonitoringJdbcDao)
	{
		this.tradeMonitoringJdbcDao = tradeMonitoringJdbcDao;
	}

	/**
	 * 取得贸易监控树根节点下第一层树节点。
	 * 
	 * @return
	 */
	private List<TradeMonitoringTreeNode> getTradeMonitoringColumnTreeRoot(String queryField, String queryValue, String dept_id, String apply_time,String apply_time1)
	{
		String jsontext = "";
		List<TradeMonitoringTreeNode> list = new ArrayList<TradeMonitoringTreeNode>();

		List<TradeType> listTradeType = tradeMonitoringJdbcDao.getTradeType(queryField, queryValue, dept_id, apply_time,apply_time1);

		for (TradeType type : listTradeType)
		{
			TradeMonitoringTreeNode node = new TradeMonitoringTreeNode();
			node.setNodeId(type.getTradeTypeId());
			node.setNodeName(type.getTitle());
			node.setTradeType(type.getTradeTypeId());
			node.setParentNodeId("-1");
			node.setBusinessNo("");
			node.setBusinessName("");
			node.setBusinessType("");
			node.setBusinessTypeName("");
			node.setSdate("");
			// 节点图标
			node.setIconCls("");
			node.setLeaf(false);
			node.setProcessState("");
			node.setRemark("");
			list.add(node);
		}

		return list;
	}

	/**
	 * 取得贸易监控菜单树JSON串。
	 * 
	 * @return
	 */
	public String getTradeMonitoringColumnTreeJsonText(String nodeId, String tradeType, String businessType, String queryField, String queryValue, String dept_id, String apply_time,String apply_time1,String cGroupTime,String cGroupTime1)
	{
		List<TradeMonitoringTreeNode> list = new ArrayList<TradeMonitoringTreeNode>();

		String jsontext = "";
		if (nodeId.equals("-1"))
		{
			list = getTradeMonitoringColumnTreeRoot(queryField, queryValue, dept_id, apply_time,apply_time1);
		}
		else if (!StringUtils.isNullBlank(tradeType) && StringUtils.isNullBlank(businessType))
		{
			// 取得贸易监控立项节点。
			list = this.tradeMonitoringJdbcDao.getProjectColumnTree(tradeType, queryField, queryValue, dept_id, apply_time,apply_time1);
		}
		else if (!StringUtils.isNullBlank(tradeType) && !StringUtils.isNullBlank(businessType))
		{
			// 取得贸易监控树节点
			list = this.tradeMonitoringJdbcDao.getTradeMonitoringColumnTree(nodeId, tradeType, businessType, queryField, queryValue, dept_id, apply_time,apply_time1,cGroupTime,cGroupTime1);
		}

		if (null != list && list.size() > 0)
		{
			JSONArray ja = JSONArray.fromObject(list);
			jsontext = ja.toString();
		}

		return jsontext;
	}

	/**
	 * 取得贸易监控树根节点下第一层树节点。
	 * 
	 * @return
	 */
	private List<TradeMonitoringTreeNode> getTradeMonitoringTreeGridRoot(String queryField, String queryValue, String dept_id, String apply_time,String apply_time1)
	{
		String jsontext = "";
		List<TradeMonitoringTreeNode> list = new ArrayList<TradeMonitoringTreeNode>();

		List<TradeType> listTradeType = tradeMonitoringJdbcDao.getTradeType(queryField, queryValue, dept_id, apply_time,apply_time1);

		for (TradeType type : listTradeType)
		{
			TradeMonitoringTreeNode node = new TradeMonitoringTreeNode();
			node.setNodeId(type.getTradeTypeId());
			node.setNodeName(type.getTitle());
			node.setTradeType(type.getTradeTypeId());
			node.setParentNodeId(null);
			node.setBusinessNo("");
			node.setBusinessName("");
			node.setBusinessType("");
			node.setBusinessTypeName("");
			node.setSdate("");
			// 节点图标
			node.setIconCls("");
			node.setLeaf(false);
			node.setProcessState("");
			node.setRemark("");
			list.add(node);
		}

		return list;
	}

	/**
	 * 取得贸易监控菜单树JSON串。
	 * 
	 * @return
	 */
	public JSONArray getTradeMonitoringTreeGridJson(String nodeId, String tradeType, String businessType, String queryField, String queryValue, String dept_id, String apply_time,String apply_time1)
	{
		List<TradeMonitoringTreeNode> list = new ArrayList<TradeMonitoringTreeNode>();

		String jsontext = "";
		if (nodeId.equals("-1"))
		{
			list = getTradeMonitoringTreeGridRoot(queryField, queryValue, dept_id, apply_time,apply_time1);
		}
		else if (!StringUtils.isNullBlank(tradeType) && StringUtils.isNullBlank(businessType))
		{
			// 取得贸易监控立项节点。
			list = this.tradeMonitoringJdbcDao.getProjectColumnTree(tradeType, queryField, queryValue, dept_id, apply_time,apply_time1);
		}
		else if (!StringUtils.isNullBlank(tradeType) && !StringUtils.isNullBlank(businessType))
		{
			// 取得贸易监控树节点
			list = this.tradeMonitoringJdbcDao.getTradeMonitoringColumnTree(nodeId, tradeType, businessType, queryField, queryValue, dept_id, apply_time,apply_time1,"","");
		}

		JSONArray ja = JSONArray.fromObject(list);

		return ja;
	}
}
