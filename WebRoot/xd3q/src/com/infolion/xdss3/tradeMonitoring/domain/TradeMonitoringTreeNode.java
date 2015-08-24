/*
 * @(#)TradeMonitoringTreeNode.java
 * 版权声明 福建讯盟软件有限公司, 版权所有 违者必究
 *
 *修订记录:
 *1)更改者：林进旭
 *  时　间：2010-5-6
 *  描　述：创建
 */

package com.infolion.xdss3.tradeMonitoring.domain;

import javax.persistence.Column;

/**
 * <pre>
 * 贸易监控树节点对象
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
public class TradeMonitoringTreeNode
{

	/**
	 * 节点ID
	 */
	@Column(name = "NODEID")
	private String nodeId;

	/**
	 * 父节点ID
	 */
	@Column(name = "PARENTNODEID")
	private String parentNodeId;

	/**
	 * 节点名称
	 */
	@Column(name = "NODENAME")
	private String nodeName;

	/**
	 * 贸易类型
	 */
	@Column(name = "TRADETYPE")
	private String tradeType;

	/**
	 * 业务类型
	 */
	@Column(name = "BUSINESSTYPE")
	private String businessType;

	/**
	 * 业务类型中文名称
	 */
	@Column(name = "BUSINESSTYPENAME")
	private String businessTypeName;

	/**
	 * Url地址
	 */
	@Column(name = "URL")
	private String url;

	/**
	 * 业务编号
	 */
	@Column(name = "BUSINESSNO")
	private String businessNo;

	/**
	 * 业务名称
	 */
	@Column(name = "BUSINESSNAME")
	private String businessName;

	/**
	 * 图标样式名称
	 */
	private String iconCls;

	/**
	 * 是否叶子节点
	 */
	private boolean leaf;

	/**
	 * 是否叶子节点
	 */
	private boolean _is_leaf;

	private Object _parent;

	private int _lft;

	private int _rgt;

	private int _level;

	/**
	 * @return the _level
	 */
	public int get_level()
	{
		return _level;
	}

	/**
	 * @param _level
	 *            the _level to set
	 */
	public void set_level(int _level)
	{
		this._level = _level;
	}

	/**
	 * @return the _parent
	 */
	public Object get_parent()
	{
		return _parent;
	}

	/**
	 * @param _parent
	 *            the _parent to set
	 */
	public void set_parent(Object _parent)
	{
		this._parent = _parent;
	}

	/**
	 * @return the _lft
	 */
	public int get_lft()
	{
		return _lft;
	}

	/**
	 * @param _lft
	 *            the _lft to set
	 */
	public void set_lft(int _lft)
	{
		this._lft = _lft;
	}

	/**
	 * @return the _rgt
	 */
	public int get_rgt()
	{
		return _rgt;
	}

	/**
	 * @param _rgt
	 *            the _rgt to set
	 */
	public void set_rgt(int _rgt)
	{
		this._rgt = _rgt;
	}

	/**
	 * @return the _is_leaf
	 */
	public boolean is_is_leaf()
	{
		return _is_leaf;
	}

	/**
	 * @param _is_leaf
	 *            the _is_leaf to set
	 */
	public void set_is_leaf(boolean _is_leaf)
	{
		this._is_leaf = _is_leaf;
	}

	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;
	
	/**
	 * 备注1
	 */
	@Column(name = "REMARK1")
	private String remark1;

	/**
	 * 流程状态
	 */
	@Column(name = "PROCESSSTATE")
	private String processState;

	/**
	 * 日期
	 */
	@Column(name = "SDATE")
	private String sdate;

	/**
	 * @return the nodeId
	 */
	public String getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId
	 *            the nodeId to set
	 */
	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return the parentNodeId
	 */
	public String getParentNodeId()
	{
		return parentNodeId;
	}

	/**
	 * @param parentNodeId
	 *            the parentNodeId to set
	 */
	public void setParentNodeId(String parentNodeId)
	{
		this.parentNodeId = parentNodeId;
	}

	/**
	 * @return the nodeName
	 */
	public String getNodeName()
	{
		return nodeName;
	}

	/**
	 * @param nodeName
	 *            the nodeName to set
	 */
	public void setNodeName(String nodeName)
	{
		this.nodeName = nodeName;
	}

	/**
	 * @return the tradeType
	 */
	public String getTradeType()
	{
		return tradeType;
	}

	/**
	 * @param tradeType
	 *            the tradeType to set
	 */
	public void setTradeType(String tradeType)
	{
		this.tradeType = tradeType;
	}

	/**
	 * @return the businessType
	 */
	public String getBusinessType()
	{
		return businessType;
	}

	/**
	 * @param businessType
	 *            the businessType to set
	 */
	public void setBusinessType(String businessType)
	{
		this.businessType = businessType;
	}

	/**
	 * @return the businessTypeName
	 */
	public String getBusinessTypeName()
	{
		return businessTypeName;
	}

	/**
	 * @param businessTypeName
	 *            the businessTypeName to set
	 */
	public void setBusinessTypeName(String businessTypeName)
	{
		this.businessTypeName = businessTypeName;
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return the businessNo
	 */
	public String getBusinessNo()
	{
		return businessNo;
	}

	/**
	 * @param businessNo
	 *            the businessNo to set
	 */
	public void setBusinessNo(String businessNo)
	{
		this.businessNo = businessNo;
	}

	/**
	 * @return the businessName
	 */
	public String getBusinessName()
	{
		return businessName;
	}

	/**
	 * @param businessName
	 *            the businessName to set
	 */
	public void setBusinessName(String businessName)
	{
		this.businessName = businessName;
	}

	/**
	 * @return the iconCls
	 */
	public String getIconCls()
	{
		return iconCls;
	}

	/**
	 * @param iconCls
	 *            the iconCls to set
	 */
	public void setIconCls(String iconCls)
	{
		this.iconCls = iconCls;
	}

	/**
	 * @return the leaf
	 */
	public boolean isLeaf()
	{
		return leaf;
	}

	/**
	 * @param leaf
	 *            the leaf to set
	 */
	public void setLeaf(boolean leaf)
	{
		this.leaf = leaf;
	}

	/**
	 * @return the remark
	 */
	public String getRemark()
	{
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	/**
	 * @return the processState
	 */
	public String getProcessState()
	{
		return processState;
	}

	/**
	 * @param processState
	 *            the processState to set
	 */
	public void setProcessState(String processState)
	{
		this.processState = processState;
	}

	/**
	 * @return the sdate
	 */
	public String getSdate()
	{
		return sdate;
	}

	/**
	 * @param sdate
	 *            the sdate to set
	 */
	public void setSdate(String sdate)
	{
		this.sdate = sdate;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
}
